package com.cn.rocketmq.producer.affair;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.*;

/**
 * TransactionProducer
 * <p>
 * 事务消息
 *
 * 事务消息不支持延时消息和批量消息。
 * 为了避免单个消息被检查太多次而导致半队列消息累积，我们默认将单个消息的检查次数限制为 15 次，
 * 但是用户可以通过 Broker 配置文件的 transactionCheckMax参数来修改此限制。
 *
 * 如果已经检查某条消息超过 N 次的话（ N = transactionCheckMax ）
 *  则 Broker 将丢弃此消息，并在默认情况下同时打印错误日志。
 *  用户可以通过重写 AbstractTransactionalMessageCheckListener 类来修改这个行为。
 *
 * 事务消息将在 Broker 配置文件中的参数 transactionTimeout 这样的特定时间长度之后被检查。
 * 当发送事务消息时，用户还可以通过设置用户属性 CHECK_IMMUNITY_TIME_IN_SECONDS 来改变这个限制，
 * 该参数优先于 transactionTimeout 参数。
 *
 * 事务性消息可能不止一次被检查或消费。
 *
 * 提交给用户的目标主题消息可能会失败，目前这依日志的记录而定。
 * 它的高可用性通过 RocketMQ 本身的高可用性机制来保证，
 * 如果希望确保事务消息不丢失、并且事务完整性得到保证，建议使用同步的双重写入机制。
 *
 * 事务消息的生产者 ID 不能与其他类型消息的生产者 ID 共享。
 * 与其他类型的消息不同，事务消息允许反向查询、MQ服务器能通过它们的生产者 ID 查询到消费者。
 *
 *
 * @author wupw
 * @date 2020/12/16
 */
public class TransactionProducer {

    public static void main(String[] args) throws MQClientException, InterruptedException {
        TransactionListener transactionListener = new TransactionListenerImpl();
        TransactionMQProducer producer = new TransactionMQProducer("TransactionProducer");
        ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2000), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("client-transaction-msg-check-thread");
                return thread;
            }
        });
        producer.setExecutorService(executorService);
        producer.setTransactionListener(transactionListener);
        producer.start();
        String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};
        for (int i = 0; i < 10; i++) {
            try {
                Message msg =
                        new Message("TransactionTopic", tags[i % tags.length], "KEY" + i,
                                ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                SendResult sendResult = producer.sendMessageInTransaction(msg, null);
                System.out.printf("%s%n", sendResult);
                Thread.sleep(10);
            } catch (MQClientException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < 100000; i++) {
            Thread.sleep(1000);
        }
        producer.shutdown();
    }
}

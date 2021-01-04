package com.cn.rocketmq.producer.async;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.CountDownLatch2;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.TimeUnit;

/**
 * AsyncProducer
 *
 * 异步发送消息
 *
 * 异步消息通常用在对响应时间敏感的业务场景，即发送端不能容忍长时间地等待Broker的响应。
 *
 * 开启新线程去发送消息，成功后执行回调
 * 主线程干自己的事儿就可以了
 *
 * @author wupw
 * @date 2020/12/15
 */
public class AsyncProducer {

    public static void main(String[] args) throws Exception {
        // 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer("AsyncProducerGroup");
        // 设置NameServer的地址
        producer.setNamesrvAddr("localhost:9876");
        // 启动Producer实例
        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(0);

        int messageCount = 10;
        // 根据消息数量实例化倒计时计算器
        //final CountDownLatch2 countDownLatch = new CountDownLatch2(messageCount);
        CountDownLatch2 countDownLatch = new CountDownLatch2(messageCount);
        for (int i = 0; i < messageCount; i++) {
            final int index = i;
            // 创建消息，并指定Topic，Tag和消息体
            Message msg = new Message("asyncProducerTopic","asyncProducerTag","asyncProducerKey",
                    ("Hello world,this is a async message "+i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            // SendCallback接收异步返回结果的回调
//            producer.send(msg, new SendCallback() {
//                @Override
//                public void onSuccess(SendResult sendResult) {
//                    System.out.printf("%-10d OK %s %n", index,
//                            sendResult.getMsgId());
//                }
//                @Override
//                public void onException(Throwable e) {
//                    System.out.printf("%-10d Exception %s %n", index, e);
//                    e.printStackTrace();
//                }
//            });
            producer.send(msg,new AsyncThread(countDownLatch,index));
        }
        // 等待5s
        //countDownLatch.await(5, TimeUnit.SECONDS);
        countDownLatch.await();

        System.out.println("消息已经全部发送完毕。。。");

        // 如果不再发送消息，关闭Producer实例。
        producer.shutdown();
    }

}

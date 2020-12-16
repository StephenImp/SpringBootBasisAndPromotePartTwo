package com.cn.rocketmq.producer.sync;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * SyncProducer
 *
 * 同步发送消息
 *
 * 这种可靠性同步地发送方式使用的比较广泛，比如：重要的消息通知，短信通知。
 *
 * @author wupw
 * @date 2020/12/1
 *
 * 证明：
 *      1.生产者组是生产者组，消费者组是消费者组，两个组的名称可以不相同，但是是可以消费的。
 *      2.tag是怎么用的？ tag 的说明详见 OrderProducer -- readme.txt
 *      3.keys是怎么使用的？
 *
 */
public class SyncProducer {

    public static void main(String[] args) throws Exception {
        // 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer("SyncProducer");
        // 设置NameServer的地址
        producer.setNamesrvAddr("127.0.0.1:9876");
        // 启动Producer实例
        producer.start();
        for (int i = 0; i < 5; i++) {
        // 创建消息，并指定Topic，Tag和消息体
        Message msg = new Message("syncTopic", "syncTag",
                ("Hello RocketMQ ,This is sync msg"+i).getBytes(RemotingHelper.DEFAULT_CHARSET)
        );
        // 发送消息到一个Broker
        SendResult sendResult = producer.send(msg);
        // 通过sendResult返回消息是否成功送达
        System.out.printf("%s%n", sendResult);
        }
        // 如果不再发送消息，关闭Producer实例。
        producer.shutdown();
    }
}

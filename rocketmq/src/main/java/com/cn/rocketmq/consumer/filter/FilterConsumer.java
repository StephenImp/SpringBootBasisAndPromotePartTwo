package com.cn.rocketmq.consumer.filter;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * Consumer
 *
 * 消息过滤，需要增加配置，但是还是没有收到消息，不知道是什么鬼。
 *
 * @author wupw
 * @date 2020/12/1
 */
public class FilterConsumer {

    public static void main(String[] args) throws InterruptedException, MQClientException {

        // 实例化消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("filterConsumerGroup");

        // 设置NameServer的地址
        consumer.setNamesrvAddr("localhost:9876");

        // 只有订阅的消息有这个属性a, a >=0 and a <= 3
        consumer.subscribe("FilterTopic", MessageSelector.bySql("a between 0 and 3"));
        // 注册回调实现类来处理从broker拉取回来的消息
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {

            for (MessageExt msg : msgs) {
                System.out.printf("%s Receive New sync Messages: %s %n", Thread.currentThread().getName(), new String(msg.getBody()));
            }
            // 标记该消息已经被成功消费
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        // 启动消费者实例
        consumer.start();
        System.out.printf("Consumer Started.%n");
    }
}

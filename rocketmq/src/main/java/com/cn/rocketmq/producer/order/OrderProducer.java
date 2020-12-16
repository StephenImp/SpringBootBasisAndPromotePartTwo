package com.cn.rocketmq.producer.order;

import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * OrderProducer
 * <p>
 * 顺序消息生产
 *
 * @author wupw
 * @date 2020/12/16
 * <p>
 * 消息有序指的是可以按照消息的发送顺序来消费(FIFO)。
 * RocketMQ可以严格的保证消息有序，可以分为分区有序或者全局有序。
 * <p>
 * 顺序消费的原理解析，在默认的情况下消息发送会采取Round Robin轮询方式把消息发送到不同的queue(分区队列)；
 * 而消费消息的时候从多个queue上拉取消息，这种情况发送和消费是不能保证顺序。
 * 但是如果控制发送的顺序消息只依次发送到同一个queue中，消费的时候只从这个queue上依次拉取，则就保证了顺序。
 * 当发送和消费参与的queue只有一个，则是全局有序；
 *
 *
 * 如果多个queue参与，则为分区有序，即相对每个queue，消息都是有序的。
 * <p>
 * 下面用订单进行分区有序的示例。
 * <p>
 * 一个订单的顺序流程是：创建、付款、推送、完成。订单号相同的消息会被先后发送到同一个队列中，
 * 消费时，同一个OrderId获取到的肯定是同一个队列。
 *
 *
 */
public class OrderProducer {

    public static void main(String[] args) throws Exception {

        DefaultMQProducer producer = new DefaultMQProducer("order_group_name");

        producer.setNamesrvAddr("127.0.0.1:9876");

        producer.start();

        String[] tags = new String[]{"TagA", "TagB", "TagC"};

        // 订单列表
        List<OrderStep> orderList = new OrderProducer().buildOrders();

        System.out.println("orderList size: " +orderList.size());

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdf.format(date);
        for (int i = 0; i < 10; i++) {
            // 加个时间前缀
            String body = dateStr + " Hello RocketMQ " + orderList.get(i);

            //tag 将消息分别发送到3个tag中  不同的消息通过不同的 tag 去 发送
            Message msg = new Message("OrderTopic", tags[i % tags.length], "KEY" + i, body.getBytes());

            System.out.println(String.format("orderProducer 发送第 %s 条消息,发送的TAG: %s",i,tags[i % tags.length]));

            //保证相同订单ID的各种消息发往同一个MessageQueue（同一个Topic下的某一个queue）
            //根据订单id 选择指定的队列 发送
            SendResult sendResult = producer.send(msg, new MessageQueueSelector() {

                //根据订单id(send()的第二个入参指定) 选择指定的队列
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {

                    //order mqs :
                    // [{"brokerName":"imp","queueId":0,"topic":"OrderTopic"},
                    // {"brokerName":"imp","queueId":1,"topic":"OrderTopic"},
                    // {"brokerName":"imp","queueId":2,"topic":"OrderTopic"},
                    // {"brokerName":"imp","queueId":3,"topic":"OrderTopic"}]

                    // 从mqs 的数据来看，一共有4个队列
                    System.out.println("order mqs :"+ JSONObject.toJSONString(mqs));

                    //order msg :
                    // {"body":"MjAyMC0xMi0xNiAxMDo1ODowOCBIZWxsbyBSb2NrZXRNUSBPcmRlclN0ZXAob3JkZXJJZD0xNTEwMzExMTAzOSwgZGVzYz3liJvlu7op",
                    // "delayTimeLevel":0,"flag":0,"keys":"KEY0",
                    // "properties":{"KEYS":"KEY0","WAIT":"true","TAGS":"TagA"},
                    // "tags":"TagA","topic":"OrderTopic","waitStoreMsgOK":true}

                    //从msg 来看，第一条消息发送给 TagA
                    System.out.println("order msg :"+ JSONObject.toJSONString(msg));

                    //order arg :15103111039  这个是模拟的订单订单编号  这个是怎么获取到的，牛逼了
                    System.out.println("order arg :"+ JSONObject.toJSONString(arg));
                    System.out.println("body:"+JSONObject.toJSONString(body));

                    Long id = (Long) arg;  //根据订单id选择发送queue

                    long index = id % mqs.size();// mqs.size() -- 队列数量

                    System.out.println("index:"+ index);


                    MessageQueue messageQueue = mqs.get((int) index);
                    System.out.println("messageQueue:"+JSONObject.toJSON(messageQueue));

                    return mqs.get((int) index);
                }
            }, orderList.get(i).getOrderId());//订单id  这里传入的订单id就是 上面 arg的入参

            System.out.println(String.format("Order SendResult status:%s, queueId:%d, body:%s",
                    sendResult.getSendStatus(),
                    sendResult.getMessageQueue().getQueueId(),
                    body));


            System.out.println();
            System.out.println();
        }

        producer.shutdown();
    }


    /**
     * 生成模拟订单数据
     */
    private List<OrderStep> buildOrders() {
        List<OrderStep> orderList = new ArrayList<OrderStep>();

        OrderStep orderDemo = new OrderStep();
        orderDemo.setOrderId(15103111039L);
        orderDemo.setId(1L);
        orderDemo.setDesc("创建");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setId(2L);
        orderDemo.setOrderId(15103111065L);
        orderDemo.setDesc("创建");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setId(3L);
        orderDemo.setOrderId(15103111039L);
        orderDemo.setDesc("付款");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setId(4L);
        orderDemo.setOrderId(15103117235L);
        orderDemo.setDesc("创建");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setId(5L);
        orderDemo.setOrderId(15103111065L);
        orderDemo.setDesc("付款");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setId(6L);
        orderDemo.setOrderId(15103117235L);
        orderDemo.setDesc("付款");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setId(7L);
        orderDemo.setOrderId(15103111065L);
        orderDemo.setDesc("完成");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setId(8L);
        orderDemo.setOrderId(15103111039L);
        orderDemo.setDesc("推送");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setId(9L);
        orderDemo.setOrderId(15103117235L);
        orderDemo.setDesc("完成");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setId(10L);
        orderDemo.setOrderId(15103111039L);
        orderDemo.setDesc("完成");
        orderList.add(orderDemo);

        return orderList;
    }
}

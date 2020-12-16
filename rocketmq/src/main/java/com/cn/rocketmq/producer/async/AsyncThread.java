package com.cn.rocketmq.producer.async;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.CountDownLatch2;

/**
 * AsyncThread
 *
 * @author wupw
 * @date 2020/12/15
 */
public class AsyncThread implements SendCallback {

    private CountDownLatch2 countDownLatch;
    private Integer index;

    public AsyncThread(CountDownLatch2 countDownLatch, Integer index) {
        this.countDownLatch = countDownLatch;
        this.index = index;
    }

    @Override
    public void onSuccess(SendResult sendResult) {
        System.out.printf("%-10d OK %s %n", index,
                sendResult.getMsgId());
        try {
            Thread.sleep(1*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        countDownLatch.countDown();
    }

    @Override
    public void onException(Throwable e) {
        System.out.printf("%-10d Exception %s %n", index, e);
                    e.printStackTrace();
    }
}

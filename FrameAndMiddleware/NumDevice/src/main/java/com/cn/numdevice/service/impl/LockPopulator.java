package com.cn.numdevice.service.impl;

import com.cn.numdevice.entity.Id;
import com.cn.numdevice.entity.IdMeta;
import com.cn.numdevice.service.IdPopulator;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 通过lock  获取 id
 */
public class LockPopulator implements IdPopulator {

    private long sequence = 0;
    private long lastTimestamp = -1;
    private Lock lock = new ReentrantLock();

    public LockPopulator(){
        super();
    }

    @Override
    public void populateId(Id id, IdMeta idMeta) {
        lock.lock();

        try{
            // ① 生成时间戳  这里肯定是有位数控制的
            long timestamp = 0 ;

            //②  校验时间戳  估计是跟之前生成的时间戳进行对比

            /**
             * 首先查看当前时间时候已经到了下一个时间单位，
             * 如果已经到了下一个时间单位，则将序列号清零
             * 如果还在上一个时间单位，就对序列号进行累加
             * 如果累加后越界，就需要等待下一秒再产生唯一ID
             *
             * 这个时间单位可以是秒级别的，也可以是毫秒级别的。
             */

            id.setSeq(sequence);
            id.setTime(timestamp);

        }catch (Exception e ){

        }finally {
            lock.unlock();
        }

    }
}

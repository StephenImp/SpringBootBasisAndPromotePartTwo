package com.cn.numdevice.service.impl;

import com.cn.numdevice.entity.Id;
import com.cn.numdevice.entity.IdMeta;
import com.cn.numdevice.service.IdPopulator;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 这里是CAS算法来保证唯一性的
 */
public class AtomicIdPopulator implements IdPopulator {

    public class Variant {

        private long sequence = 0;
        private long lastTimestamp = -1;
    }

    private AtomicReference<Variant> variant = new AtomicReference<>(new Variant());

    public AtomicIdPopulator(){
        super();
    }

    @Override
    public void populateId(Id id, IdMeta idMeta) {

        Variant varOld,varNew;
        long timestamp,sequence;

        while (true){

            //Save Old variant
            varOld = variant.get();

            //populate the current variant(填充当前变量)
            timestamp = 0;

            sequence = varOld.sequence;


            //私有变量的值与主存中的值相同时
            if (timestamp == varOld.lastTimestamp ){
                //更新主存中的值
            }else {
                //不更新
            }

        }

    }
}

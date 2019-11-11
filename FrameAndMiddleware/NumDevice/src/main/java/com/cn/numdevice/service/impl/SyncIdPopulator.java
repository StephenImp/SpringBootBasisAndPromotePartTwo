package com.cn.numdevice.service.impl;

import com.cn.numdevice.entity.Id;
import com.cn.numdevice.entity.IdMeta;
import com.cn.numdevice.service.IdPopulator;

public class SyncIdPopulator implements IdPopulator {

    private long sequence = 0;
    private long lastTimestamp = -1;

    public SyncIdPopulator(){
        super();
    }

    /**
     * 这里方法上面加了一个同步锁 ,逻辑和Lock是一样的
     * @param id
     * @param idMeta
     */
    @Override
    public synchronized void populateId(Id id, IdMeta idMeta) {

    }


}

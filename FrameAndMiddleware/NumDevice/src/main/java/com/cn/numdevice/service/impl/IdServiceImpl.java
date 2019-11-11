package com.cn.numdevice.service.impl;

import com.cn.numdevice.entity.Id;
import com.cn.numdevice.service.AbstractIdServiceImpl;
import com.cn.numdevice.service.IdPopulator;

/**
 * 通过代理模式代理到某个IdPopulator接口的一个实现来计算时间字段和序列号字段
 */
public class IdServiceImpl extends AbstractIdServiceImpl {

    private static final String SYNC_LOCK_IMPL_KEY = "vesta.sync.lock.impl.key";

    private static final String ATOMIC_IMPL_KEY = "vesta.atomic.impl.key";

    private IdPopulator idPopulator;

    public IdServiceImpl(){
        super();
        initPoupulator();
    }

    public IdServiceImpl(String type){
        //super(type);
        initPoupulator();
    }

    @Override
    protected void populatedId(Id id) {

    }

    /**
     * 自定义ID生成方式
     */
    public void initPoupulator(){

        //工厂模式，

        //idPopulator =  Sync     同步锁生成方式

        //idPopulator = ReetrantLock   CAS算法生成方式

    }}

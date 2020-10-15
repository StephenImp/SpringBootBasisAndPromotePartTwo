package com.cn.demoApp.demotest.scheduledb;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * 任务缓存类
 */
public class SingletonFuture {

    private static volatile SingletonFuture ONLY;

    private Map<String, ScheduledFuture<?>> futureMap = new ConcurrentHashMap<String, ScheduledFuture<?>>();

    private SingletonFuture() {}

    public static SingletonFuture getInstance() {
        if(null == ONLY) {
            synchronized(SingletonFuture.class) {
                if(null == ONLY) {
                    ONLY = new SingletonFuture();
                }
            }
        }
        return ONLY;
    }

    public Map<String, ScheduledFuture<?>> getFutureMap() {
        return futureMap;
    }

    public void setFutureMap(Map<String, ScheduledFuture<?>> futureMap) {
        this.futureMap = futureMap;
    }
}

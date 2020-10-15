package com.cn.demoApp.demotest.scheduledb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.concurrent.ScheduledFuture;

/**
 * 任务调度组件用来开启/关闭/重启计划任务
 */
@Component
public class DynamicScheduleTask {

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    //关闭所有任务
    public void shutDown() {
        Collection<ScheduledFuture<?>> values = SingletonFuture.getInstance().getFutureMap().values();
        for(ScheduledFuture<?> future:values) {
            if (future != null) {
                future.cancel(true);
            }
        }
        SingletonFuture.getInstance().getFutureMap().clear();
    }

    public void start(AbstractTask task,TaskEntity entity) {
        ScheduledTrigger trigger = new ScheduledTrigger(entity.getTask_cron());
        ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(task, trigger);
        String className = entity.getTask_class_name();
        SingletonFuture.getInstance().getFutureMap().put(className, future);
    }

    public void stop(String className) {
        if(!StringUtils.isEmpty(className)) {
            ScheduledFuture<?> future = SingletonFuture.getInstance().getFutureMap().get(className);
            if (future != null) {
                future.cancel(true);
                SingletonFuture.getInstance().getFutureMap().remove(className);
            }
        }
    }

    public void restart(AbstractTask task,TaskEntity entity) {
        stop(entity.getTask_class_name());
        start(task, entity);
    }
}

package com.cn.demoApp.demotest.scheduledb;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.CronTrigger;

import java.util.Date;

/**
 * 任务调度触发器
 */
public class ScheduledTrigger implements Trigger {

    private String cron;

    @SuppressWarnings("unused")
    private ScheduledTrigger() {}

    public ScheduledTrigger(String cron) {
        this.cron = cron;
    }

    @Override
    public Date nextExecutionTime(TriggerContext triggerContext) {
        CronTrigger cronTrigger = new CronTrigger(cron);
        Date nextExecutionTime = cronTrigger.nextExecutionTime(triggerContext);
        return nextExecutionTime;
    }
}

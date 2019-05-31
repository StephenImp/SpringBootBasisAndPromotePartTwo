package com.cn.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by MOZi on 2019/2/21.
 */
@Component
public class ScheduledTask {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedDelayString = "${jobs.fixedDelay}")
    //@Scheduled(fixedDelayString = "2000")
    public void getTask1() {
        System.out.println("任务1,从配置文件加载任务信息，当前时间：" + dateFormat.format(new Date()));
    }

    @Scheduled(cron = "${jobs.cron}")
    public void getTask2() {
        System.out.println("任务2,从配置文件加载任务信息，当前时间：" + dateFormat.format(new Date()));
    }

}

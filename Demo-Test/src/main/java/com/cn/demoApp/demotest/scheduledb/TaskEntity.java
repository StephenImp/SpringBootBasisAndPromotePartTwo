package com.cn.demoApp.demotest.scheduledb;

public class TaskEntity {


    private String task_id;

    private String task_cron;

    private String task_class_name;

    private String task_name;

    private String task_description;

    private Integer task_enable;

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getTask_cron() {
        return task_cron;
    }

    public void setTask_cron(String task_cron) {
        this.task_cron = task_cron;
    }

    public String getTask_class_name() {
        return task_class_name;
    }

    public void setTask_class_name(String task_class_name) {
        this.task_class_name = task_class_name;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getTask_description() {
        return task_description;
    }

    public void setTask_description(String task_description) {
        this.task_description = task_description;
    }

    public Integer getTask_enable() {
        return task_enable;
    }

    public void setTask_enable(Integer task_enable) {
        this.task_enable = task_enable;
    }
}

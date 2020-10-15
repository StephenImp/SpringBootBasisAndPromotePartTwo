package com.cn.demoApp.demotest.mapper;

import com.cn.demoApp.demotest.scheduledb.TaskEntity;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TaskMapper {

    @Select("SELECT task_id,task_cron,task_class_name,task_name,task_description,task_enable FROM task WHERE task_enable = 1")
    List<TaskEntity> findAll();

    @Select("SELECT task_id,task_cron,task_class_name,task_name,task_description,task_enable FROM task WHERE task_class_name=#{task_class_name}")
    TaskEntity getTaskEntity(String task_class_name);

    @Select("SELECT task_cron FROM task WHERE task_id=#{task_id}")
    String getTaskCronById(String task_id);
}

package com.cn.demoApp.demotest.service;

import com.cn.demoApp.demotest.mapper.TaskMapper;
import com.cn.demoApp.demotest.scheduledb.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TaskService {

    @Resource
    private TaskMapper taskMapper;

    public List<TaskEntity> findAll() {
        List<TaskEntity> tasks = taskMapper.findAll();
        if(tasks.isEmpty()) {
            throw new RuntimeException("查询异常");
        }
        return tasks;
    }

    public TaskEntity getTaskEntity(String className) {
        TaskEntity task = taskMapper.getTaskEntity(className);
        if(null == task) {
            throw new RuntimeException("查询异常");
        }
        return task;
    }

    public String getTaskCronById(String task_id) {
        String cron = taskMapper.getTaskCronById(task_id);
        if(StringUtils.isEmpty(task_id)) {
            throw new RuntimeException("查询异常");
        }
        return cron;
    }
}

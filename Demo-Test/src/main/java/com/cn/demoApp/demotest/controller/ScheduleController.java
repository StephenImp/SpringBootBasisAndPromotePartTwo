package com.cn.demoApp.demotest.controller;

import com.cn.demoApp.demotest.scheduledb.AbstractTask;
import com.cn.demoApp.demotest.scheduledb.DynamicScheduleTask;
import com.cn.demoApp.demotest.scheduledb.TaskEntity;
import com.cn.demoApp.demotest.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据库动态定时任务
 *
 * https://blog.csdn.net/l_learning/article/details/103568610
 */
@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private DynamicScheduleTask scheduleTask;

    @Autowired
    private TaskService taskService;

    @GetMapping("/startAll")
    @ResponseBody
    public void startAll() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        List<TaskEntity> taskEntitys = taskService.findAll();
        for(TaskEntity entity:taskEntitys) {
            Object newInstance = Class.forName(entity.getTask_class_name()).newInstance();
            AbstractTask task = (AbstractTask) newInstance;
            scheduleTask.start(task, entity);
        }
    }

    @GetMapping("/shutDown")
    @ResponseBody
    public void shutDown() {
        scheduleTask.shutDown();
    }

    @GetMapping("/start/{className}")
    @ResponseBody
    public void start(@PathVariable("className") String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        TaskEntity entity = taskService.getTaskEntity(className);
        Object newInstance = Class.forName(className).newInstance();
        AbstractTask task = (AbstractTask) newInstance;
        scheduleTask.start(task, entity);
    }

    @GetMapping("stop")
    @ResponseBody
    public void stop(@RequestParam String className) {
        scheduleTask.stop(className);
    }

    @GetMapping("/restart")
    @ResponseBody
    public void restart(@RequestParam String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        TaskEntity entity = taskService.getTaskEntity(className);
        Object newInstance = Class.forName(className).newInstance();
        AbstractTask task = (AbstractTask) newInstance;
        scheduleTask.restart(task, entity);
    }
}

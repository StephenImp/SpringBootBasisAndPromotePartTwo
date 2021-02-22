package com.cn.demoApp.demotest.controller;

import com.cn.demoApp.demotest.dispatcher.IDispatchHandler;
import com.cn.demoApp.demotest.entity.DepartmentEntity;
import com.cn.demoApp.demotest.entity.UserEntity;
import com.cn.demoApp.demotest.enums.UserSexEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestHeavyLoadController
 *
 * 测试Service 重载，Spring容器调度Controller
 *
 * @author wupw
 * @date 2021/2/22
 */
@RestController
@RequestMapping("/testHeavyLoad")
public class TestHeavyLoadController {

    @Autowired
    private IDispatchHandler dispatchHandlerService1;
    //private IDispatchHandler handler1;  // 报错，根据类型和名称都匹配不到

    @Autowired
    private IDispatchHandler dispatchHandlerService2;

    @RequestMapping("/handler")
    public void testHandler(){

        UserEntity u = new UserEntity();
        DepartmentEntity d = new DepartmentEntity();

        dispatchHandlerService1.test(u,d);
        //dispatchHandlerService2.test(u,d);

    }

}

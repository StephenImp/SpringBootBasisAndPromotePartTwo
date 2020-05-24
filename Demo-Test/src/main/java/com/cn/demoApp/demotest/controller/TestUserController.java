package com.cn.demoApp.demotest.controller;

import com.cn.demoApp.demotest.entity.UserEntity;
import com.cn.demoApp.demotest.enums.UserSexEnum;
import com.cn.demoApp.demotest.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class TestUserController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/insert")
    public void testInsert(){

        for (int i = 1; i <1000 ; i++) {
            String name = String.valueOf(i);
            String age = String.valueOf(i);
            userMapper.insert(new UserEntity(name, age, UserSexEnum.MAN));
        }

        System.out.println("插入完成");

    }


    @RequestMapping("/delTest1")
    public void delTest1(){

        List<Integer> ids = new ArrayList<>();

        for (int i = 1; i <1000 ; i++) {
            ids.add(i);
        }

        long funcStartTime = System.currentTimeMillis();

        userMapper.deleteByIdsEquals(ids);

        long funcEndTime = System.currentTimeMillis();
        float excTime = (float) (funcEndTime - funcStartTime) / 1000;
        System.out.println("delTest1删除data_customer用时"+excTime+"秒");
    }


    @RequestMapping("/delTest2")
    public void delTest2(){

        List<Integer> ids = new ArrayList<>();

        for (int i = 1; i <1000 ; i++) {
            ids.add(i);
        }

        long funcStartTime = System.currentTimeMillis();
        userMapper.deleteByIds(ids);

        long funcEndTime = System.currentTimeMillis();
        float excTime = (float) (funcEndTime - funcStartTime) / 1000;
        System.out.println("delTest2删除data_customer用时"+excTime+"秒");
    }

}

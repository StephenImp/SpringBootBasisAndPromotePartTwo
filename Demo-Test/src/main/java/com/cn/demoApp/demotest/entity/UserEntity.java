package com.cn.demoApp.demotest.entity;

import com.cn.demoApp.demotest.enums.UserSexEnum;
import lombok.Data;

@Data
public class UserEntity {

    private Integer id;
    private String name;
    private String age;
    private UserSexEnum sex;

    public UserEntity(String name, String age, UserSexEnum sexEnum) {
        this.name = name;
        this.age = age;
        this.sex = sexEnum;
    }
}

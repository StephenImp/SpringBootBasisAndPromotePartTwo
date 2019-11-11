package com.cn.demotest.entity;

import com.cn.demotest.enums.UserSexEnum;
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

package com.cn.demoApp.entity;

import lombok.Data;

@Data
public class User {

    private Integer id;
    private String  userName;
    private String  passWord;
    private String  realName;
    private String  remark;
}

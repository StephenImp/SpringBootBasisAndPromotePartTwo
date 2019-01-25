package com.cn.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by MOZi on 2019/1/24.
 */
@RestController
public class HelloWorldController {

    @GetMapping("index")
    public String index(){


        return "hello,world";
    }




}

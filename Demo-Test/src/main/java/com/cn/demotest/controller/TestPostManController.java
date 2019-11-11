package com.cn.demotest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/test")
public class TestPostManController {

    @RequestMapping("/postMan")
    private void testPostMan(){

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");

        System.out.println(list);

    }

    public static void main(String[] args) {

        String s = "01POSITION_01_082"+"\\\n"+"019102920191008175549017113410W";

        String s1 = replaceBlank(s);

        System.out.println("#####"+s);

        System.out.println("@@@@@"+s1);


    }


    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

}

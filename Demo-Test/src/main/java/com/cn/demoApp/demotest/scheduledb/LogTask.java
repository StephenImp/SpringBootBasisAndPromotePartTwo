package com.cn.demoApp.demotest.scheduledb;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogTask extends AbstractTask  {

    @Override
    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        System.out.println(date+" --- : i come");
    }
}

package com.cn.demoApp.zkclient;

import org.I0Itec.zkclient.ZkClient;

/**
 * 连接zookeeper
 */
public class SessionDemo {

    private final static String CONNECTSTRING="192.168.11.129:2181,192.168.11.134:2181," +
            "192.168.11.135:2181,192.168.11.136:2181";

    public static void main(String[] args) {

        ZkClient zkClient=new ZkClient(CONNECTSTRING,4000);

        System.out.println(zkClient+" - > success");
    }
}

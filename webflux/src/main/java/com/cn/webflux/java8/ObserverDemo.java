package com.cn.webflux.java8;

import java.util.Observable;

/**
 * 响应式编程本质上用到的一种设计模式叫做观察者模式
 */
public class ObserverDemo extends Observable {

    public static void main(String[] args) {
        ObserverDemo observerDemo = new ObserverDemo();
        // 添加观察者
        observerDemo.addObserver((o,arg)->{
            System.out.println("发生变化"+arg);
        });
        observerDemo.addObserver((o,arg)->{
            System.out.println("收到被观察者通知，准备改变"+arg);
        });
        observerDemo.setChanged();// 数据变化
        observerDemo.notifyObservers();//通知
    }
}

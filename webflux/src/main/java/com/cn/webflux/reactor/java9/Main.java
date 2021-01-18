package com.cn.webflux.reactor.java9;

//import java.util.concurrent.Flow;

/**
 * 3)Mono和Flux 都是数据流的发布者，使用Flux和Mono都可以发出三种信号：
 * 元素值，错误信号，完成信号。其中的错误信号和完成信号都代表终止信号，
 * 终止信号用于告诉订阅者数据流结束了。
 * 错误信号在终止数据流的同时把错误信息传递给订阅者。
 */
public class Main {

//    public static void main(String[] args) {
//        Flow.Publisher<String> publisher = subscriber -> {
//            subscriber.onNext("1"); // 1
//            subscriber.onNext("2");
//            subscriber.onError(new RuntimeException("出错")); // 2
//            // subscriber.onComplete();
//        };
//
//
//        publisher.subscribe(new Flow.Subscriber<>() {
//            @Override
//            public void onSubscribe(Flow.Subscription subscription) {
//                subscription.cancel();
//            }
//
//            @Override
//            public void onNext(String item) {
//                System.out.println(item);
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//                System.out.println("出错了");
//            }
//
//            @Override
//            public void onComplete() {
//                System.out.println("publish complete");
//            }
//        });
//    }
}

package com.cn.webflux.reactor.java8;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class TestReactor {

    public static void main(String[] args) {

        //just方法直接声明元素
        //调用just或者其他方法只是声明数据流，数据流并没有发出，只有进行订阅之后才会触发数据流，不订阅声明都不会发生的
        Flux.just(1,2,3,4).subscribe(System.out::print);
        Mono.just(1).subscribe(System.out::print);

        //其他的方法

        //声明数组元素
        Integer[] array = {1,2,3,4};
        Flux.fromArray(array);

        //集合
        List<Integer> list = Arrays.asList(array);
        Flux.fromIterable(list);

        //流
        Stream<Integer> stream = list.stream();
        Flux.fromStream(stream);

    }
}

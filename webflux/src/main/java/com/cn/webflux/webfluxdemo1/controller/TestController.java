package com.cn.webflux.webfluxdemo1.controller;

import com.cn.webflux.webfluxdemo1.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Slf4j
@RestController
public class TestController {


    @GetMapping("/test1")
    public String test1() {

        log.info("get1 start");
        String s = createSrt();
        log.info("get1 end");
        return s;
    }

    @GetMapping("/test2")
    public Mono<String> test2() {
        log.info("get2 start");

        /**
         *
         * 这里是惰性求值
         * 惰性求值？
         * 断言？
         * 四大函数式接口
         *
         * 这里执行业务代码的过程中异常了怎么办?
         * 异常还是正常异常，就是说处理请求的线程还是被释放掉了，这样的话是不影响业务的。
         *
         */
        Mono<String> result = Mono.fromSupplier(()->createSrt());
        log.info("get2 end");

        return result;
    }

    /**
     * Flux  返回0-N 个元素
     * @return
     */
    @GetMapping(value = "/test3",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> test3() {
        log.info("get3 start");
        Flux<String> result = Flux.fromStream(IntStream.range(1,5).mapToObj(i -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "flux data --" +i;
        }));

        log.info("get3 end");

        return result;
    }




    private String createSrt() {

//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//
//        int i = 1/0;

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "test";
    }

}

https://zhuanlan.zhihu.com/p/36160025

spring webflux是基于reactor来实现响应式的。

那么reactor是什么呢？我是这样理解的

reactor = jdk8的stream + jdk9的flow响应式流。理解了这句话，reactor就很容易掌握。

reactor里面Flux和Mono就是stream，他的最终操作就是 subscribe/block 2种。
reactor里面说的不订阅将什么也不会方法就是我们最开始学习的惰性求值。
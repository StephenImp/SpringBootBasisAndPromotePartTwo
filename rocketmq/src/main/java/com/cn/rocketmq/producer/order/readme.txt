通过打印日志可以看出
    同一个订单号分配在同一个队列中，分配规则自定义
    同一个订单号的数据 可以通过不同的tag 发送，但是一定是相同的 queueId
    这说明，相同的topic下，tag 和 queueId 并不存在直接的绑定关系，都是直接与 topic 进行关联

queue:
    topic 只是消息的逻辑分类，内部实现其实是由 queue 组成。当 producer 把消息发送到某个 topic 时，
    默认是会消息发送到具体的 queue 上。由于一个 topic 可以有多个 queue，所以在性能比全局有序高得多。

tag:
    以天猫交易平台为例，订单消息和支付消息属于不同业务类型的消息，分别创建 Topic_Order 和 Topic_Pay，
    其中订单消息根据商品品类以不同的 Tag 再进行细分，列如电器类、男装类、女装类、化妆品类等被各个不同的系统所接收。
    通过合理的使用 Topic 和 Tag，可以让业务结构清晰，更可以提高效率



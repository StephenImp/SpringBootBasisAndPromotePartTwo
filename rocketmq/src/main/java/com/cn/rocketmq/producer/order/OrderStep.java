package com.cn.rocketmq.producer.order;

import lombok.Data;

/**
 * OrderStep
 *
 * 订单的步骤
 *
 * @author wupw
 * @date 2020/12/16
 */
@Data
public class OrderStep {

    private long id;
    private long orderId;
    private String desc;
}

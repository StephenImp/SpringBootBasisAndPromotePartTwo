package com.cn.demoApp;

/**
 * 暴露在外面的接口
 */
public interface IOrderServices {

    /*
     * 下单操作
     */
    DoOrderResponse doOrder(DoOrderRequest request);

}

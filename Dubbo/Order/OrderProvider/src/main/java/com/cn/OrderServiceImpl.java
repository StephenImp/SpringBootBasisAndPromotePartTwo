package com.cn;

/**
 * order-Api 的实现
 */
public class OrderServiceImpl implements IOrderServices{


    public DoOrderResponse doOrder(DoOrderRequest request) {
        System.out.println("曾经来过："+request);
        DoOrderResponse response=new DoOrderResponse();
        response.setCode("1000");
        response.setMemo("处理成功");
        return response;
    }
}

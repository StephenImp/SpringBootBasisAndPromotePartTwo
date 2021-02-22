package com.cn.demoApp.demotest.dispatcher.impl;

import com.cn.demoApp.demotest.dispatcher.IDispatchHandler;
import org.springframework.stereotype.Service;

/**
 * DispatchHandlerSerivce1
 *
 * @author wupw
 * @date 2021/2/22
 */
@Service
public class DispatchHandlerService1  implements IDispatchHandler {

    @Override
    public void test(Object UserEntity, Object DepartmentEntity) {
        System.out.println("DispatchHandlerService1.test()");
    }
}

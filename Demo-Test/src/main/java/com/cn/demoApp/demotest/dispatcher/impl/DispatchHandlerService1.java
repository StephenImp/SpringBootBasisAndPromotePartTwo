package com.cn.demoApp.demotest.dispatcher.impl;

import com.cn.demoApp.demotest.dispatcher.IDispatchHandler;
import com.cn.demoApp.demotest.entity.DepartmentEntity;
import com.cn.demoApp.demotest.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

/**
 * DispatchHandlerSerivce1
 *
 * @author wupw
 * @date 2021/2/22
 */
@Service
public class DispatchHandlerService1  implements IDispatchHandler<UserEntity, DepartmentEntity> {

    @Override
    public void test(UserEntity userEntity, DepartmentEntity departmentEntity) {
        System.out.println("DispatchHandlerService1.test()");
    }
}

package com.cn.demoApp.demotest.dispatcher;

import com.cn.demoApp.demotest.entity.UserEntity;

/**
 * @author Steph
 */
public interface IDispatchHandler<A,E> {


    void test(A UserEntity, E DepartmentEntity);

}

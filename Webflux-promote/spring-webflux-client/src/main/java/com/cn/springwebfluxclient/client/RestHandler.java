package com.cn.springwebfluxclient.client;

import com.cn.springwebfluxclient.beans.MethodInfo;
import com.cn.springwebfluxclient.beans.ServerInfo;

/**
 * @description: Rest请求处理类
 * @author: 星际一哥
 * @create: 2019-12-17 19:59
 */
public interface RestHandler {

    /**
     * 初始化服务器信息
     *
     * @param serverInfo
     */
    void init(ServerInfo serverInfo);


    /**
     * rest客户端远程调用接口
     *
     * @param methodInfo
     * @return
     */
    Object invokeRest(MethodInfo methodInfo);
}


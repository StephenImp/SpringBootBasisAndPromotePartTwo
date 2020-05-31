package com.cn.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Administrator on 2020/5/30 0030.
 */
@Component
public class EsConfig {

    /**
     *
     * 这里的端口是TCP的9300端口
     * @return
     * @throws UnknownHostException
     */
    @Bean
    public TransportClient client() throws UnknownHostException {
        //添加的节点名称
        InetSocketTransportAddress node = new InetSocketTransportAddress(
                InetAddress.getByName("localhost"),9300
        );

        //指定集群名称
        Settings settings = Settings.builder().put("cluster.name","imp").build();

        //把节点都交给client管理
        TransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(node);

        return client;
    }
}

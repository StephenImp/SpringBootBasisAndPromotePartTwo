package com.cn.springwebfluxclient;

import com.cn.springwebfluxclient.proxy.JdkDynamicAopProxy;
import com.cn.springwebfluxclient.proxy.ProxyCreator;
import com.cn.springwebfluxclient.service.IUserService;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringWebfluxClientApplication {

    @Bean
    ProxyCreator JdkDynamicAopProxy() {
        return new JdkDynamicAopProxy();
    }

    @Bean
    FactoryBean<IUserService> userAPI(ProxyCreator proxyCreator) {
        return new FactoryBean<IUserService>() {
            @Override
            public Class<?> getObjectType() {
                return IUserService.class;
            }

            /**
             * @return 返回代理对象
             * @throws Exception
             */
            @Override
            public IUserService getObject() throws Exception {
                return ((IUserService) proxyCreator.createProxy(this.getObjectType()));
            }
        };
    }


    public static void main(String[] args) {
        SpringApplication.run(SpringWebfluxClientApplication.class, args);
    }

}

package com.cn.demoApp.tomcat;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * Created by MOZi on 2019/1/24.
 *
 * Tomcat 配置Class
 */
@Configuration
public class TomcatConfiguration implements EmbeddedServletContainerCustomizer{


    /**
     * 这里是回调调用?看怎么理解一下
     * @param configurableEmbeddedServletContainer
     *
     * 在 TomcatEmbeddedServletContainerFactory 初始化之前，调用customize进行配置
     *
     */
    @Override
    public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {
        System.err.println(configurableEmbeddedServletContainer.getClass());

        if (configurableEmbeddedServletContainer instanceof TomcatEmbeddedServletContainerFactory){

            TomcatEmbeddedServletContainerFactory factory =
                    (TomcatEmbeddedServletContainerFactory) configurableEmbeddedServletContainer;


            /**
             * 自定义connector
             */
            Connector connector = new Connector();
            connector.setPort(9090);
            connector.setURIEncoding("UTF-8");
            connector.setProtocol("HTTP/1.1");
            factory.addAdditionalTomcatConnectors(connector);


            /**
             * 自定义context
             *
             * 这里是像注册了监听器一样的东西。
             */
            factory.addContextCustomizers((context -> { // lambda 相当于new TomcatContextCustomizer(){}

                if (context instanceof StandardContext){
                    StandardContext standardContext =   (StandardContext)context;
                    //standardContext.setDefaultWebXml();设置
                }

            }));


            /**
             * 自定义connector
             */
//            factory.addConnectorCustomizers(connector -> {
//                connector.setPort(12345);
//            });

        }
    }
}

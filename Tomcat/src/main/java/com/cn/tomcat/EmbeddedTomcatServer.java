package com.cn.tomcat;

import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

/**
 * Created by MOZi on 2019/1/24.
 *
 * 嵌入式Tomcat
 * 这个要百度一下，什么意思，也不是特别清楚
 */
public class EmbeddedTomcatServer {

    public static void main(String[] args) throws Exception {

        //classes 绝对目录：
        // D:\workspace\SpringBootBasisAndPromote-2019\Tomcat\target\classes

        String path = "target\\classes";

        //用户目录，即当前执行java命令的目录
        String userDir = System.getProperty("user.dir")+File.separator+"Tomcat";
        System.out.println(userDir);

        //1.classes的绝对路径
        String classesPath = userDir + File.separator+path;
        System.out.println(classesPath);

        Tomcat tomcat = new Tomcat();
        //2.设置端口  9090
        tomcat.setPort(9090);

        //3.设置host
        Host host = tomcat.getHost();
        host.setName("localhost");
        host.setAppBase("webapps");

        //D:\workspace\SpringBootBasisAndPromote-2019\Tomcat\src\main\webapp
        String webappPath = userDir +File.separator+ "src"+File.separator+"main"+File.separator+"webapp";
        String contextPath = "/";
        //获取context  设置webapp的绝对路径到它的docBase
        Context context = tomcat.addWebapp(contextPath, webappPath);

        if (context instanceof StandardContext){
            StandardContext standardContext = (StandardContext) context;

            //设置默认的web.xml到Context
            standardContext.setDefaultWebXml(webappPath+File.separator+"conf"+File.separator+"web.xml");

            //设置classPath到Context
            //......以后有机会再看吧。。
        }
        //host.addChild(context);  Child name [] is not unique

        tomcat.start();

        //强制 Tomcat Server 等待，避免 main 线程执行结束关闭
        tomcat.getServer().await();


    }
}

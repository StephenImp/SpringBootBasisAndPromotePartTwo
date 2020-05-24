package com.cn.demoApp;

import com.cn.demoApp.tomcat.TomcatConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SpringbootTomcatApplication {


	/**
	 * 将需要装配的对象  放到 SpringbootTomcatApplication 这个对象中。
	 * @param args
	 */
	public static void main(String[] args) {

		/**
		 * 将一份配置 变成多分配置
		 */
		new SpringApplicationBuilder()
				.sources(SpringbootTomcatApplication.class,TomcatConfiguration.class)
				.run(args);


		//SpringApplication.run(SpringbootTomcatApplication.class, args);
	}

}


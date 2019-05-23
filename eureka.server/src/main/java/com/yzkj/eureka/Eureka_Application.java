package com.yzkj.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 注册服务
 */
//容灾，无损上线
//region   avilibal-zones
@EnableEurekaServer
@SpringBootApplication
public class Eureka_Application {

	public static void main(String[] args) {
		SpringApplication.run(Eureka_Application.class, args);
	}
}

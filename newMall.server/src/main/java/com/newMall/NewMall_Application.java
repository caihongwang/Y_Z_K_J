package com.newMall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients(basePackages = {"com.newMall"})
@EnableCaching
@EnableScheduling
@SpringBootApplication(scanBasePackages = {"com.newMall"})
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class NewMall_Application {

    public static void main(String[] args) {
        SpringApplication.run(NewMall_Application.class, args);
    }

}

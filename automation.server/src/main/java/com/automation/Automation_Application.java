package com.automation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients(basePackages = {"com.automation"})
@EnableAsync
@EnableCaching
@EnableScheduling
@SpringBootApplication(scanBasePackages = {"com.automation"})
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class Automation_Application {

    public static void main(String[] args) {
        SpringApplication.run(Automation_Application.class, args);
    }

}

package com.oilStationMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients(basePackages = {"com.oilStationMap"})
@EnableCaching
@EnableScheduling
@SpringBootApplication(scanBasePackages = {"com.oilStationMap"})
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class OilStationMap_Application {

    public static void main(String[] args) {
        SpringApplication.run(OilStationMap_Application.class, args);
    }

}

package com.newMall.utils;

import com.google.common.collect.Maps;
import com.newMall.NewMall_Application;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by caihongwang on 2019/5/23.
 */
@EnableFeignClients(basePackages = {"com.newMall"})
@EnableCaching
@EnableScheduling
@SpringBootApplication(scanBasePackages = {"com.newMall"})
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class SpriderFor7DingdongProductUtilTest {

    public static void main(String[] args) {
        SpringApplication.run(NewMall_Application.class, args);
        TEST();
    }

    @Test
    public static void TEST(){
        Map<String, Object> paramMap = Maps.newHashMap();
        //获取所有企叮咚的商品
        SpriderFor7DingdongProductUtil.get7DingdongProduct(paramMap);
    }
}
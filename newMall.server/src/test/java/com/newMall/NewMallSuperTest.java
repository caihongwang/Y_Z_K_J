package com.newMall;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import redis.clients.jedis.JedisPool;

/**
 * @ClassName NewMallSuperTest
 * @Description TODO
 * @Author caihongwang
 * @Date 2019/5/29 1:31 PM
 * @Version 1.0.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class NewMallSuperTest {

    private static final Logger logger = LoggerFactory.getLogger(NewMallSuperTest.class);

    @Autowired
    private JedisPool jedisPool;

    @Before
    public void init() {
        System.out.println("-----------------开始单元测试-----------------");
    }

    @After
    public void after() {
        System.out.println("-----------------测试单元结束-----------------");
    }

}

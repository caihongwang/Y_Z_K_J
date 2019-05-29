package com.oilStationMap;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @ClassName MySuperTest
 * @Description TODO
 * @Author caihongwang
 * @Date 2019/5/29 1:31 PM
 * @Version 1.0.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class MySuperTest {

    @Before
    public void init() {
        System.out.println("-----------------开始单元测试-----------------");
    }

    @After
    public void after() {
        System.out.println("-----------------测试单元结束-----------------");
    }

}

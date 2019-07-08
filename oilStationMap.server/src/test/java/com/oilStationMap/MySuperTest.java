package com.oilStationMap;

import com.oilStationMap.code.OilStationMapCode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

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

    private static final Logger logger = LoggerFactory.getLogger(MySuperTest.class);

    @Autowired
    private JedisPool jedisPool;

    @Before
    public void init() {
        System.out.println("-----------------开始单元测试-----------------");
    }

    @Test
    public void Test(){
        String uid = "1762";
        try (Jedis jedis = jedisPool.getResource()) {
            //松桃南坪
//            String newLon = "109.17935";
//            String newLat = "28.108028";
//            //爱明石化
//            String newLon = "109.12081";
//            String newLat = "28.10569";
            //贵州松桃大坪加油站
            String newLon = "109.10414";
            String newLat = "28.1062";

            jedis.set(OilStationMapCode.CURRENT_LON_UID + uid,
                    newLon);
            jedis.set(OilStationMapCode.CURRENT_LAT_UID + uid,
                    newLat);
            String currentLon = jedis.get(OilStationMapCode.CURRENT_LON_UID + uid);
            String currentLat = jedis.get(OilStationMapCode.CURRENT_LAT_UID + uid);
            logger.info("uid = " + uid + " , currentLon = " + currentLon + " , currentLat = " + currentLat);
        }
    }

    @After
    public void after() {
        System.out.println("-----------------测试单元结束-----------------");
    }

}

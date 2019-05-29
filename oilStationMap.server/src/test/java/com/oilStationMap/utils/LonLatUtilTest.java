package com.oilStationMap.utils;

import com.oilStationMap.MySuperTest;
import com.oilStationMap.service.WX_OilStationService;
import com.oilStationMap.dao.WX_OilStationDao;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

/**
 * Created by caihongwang on 2018/10/12.
 */
public class LonLatUtilTest extends MySuperTest {

    public static double EARTH_RADIUS = 6371.393;

    public static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    public static final Logger logger = LoggerFactory.getLogger(LonLatUtil.class);

    public static ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:center-context.xml");
    public static WX_OilStationService wxOilStationService = context.getBean("WX_OilStationServiceImpl", WX_OilStationService.class);
    public static WX_OilStationDao wxOilStationDao = context.getBean("WX_OilStationDao", WX_OilStationDao.class);

    public static Integer getDetailAddressNum = 300000;
    public static Integer callDetailAddressNum = 0;

    @Test
    public void Test() {

        Map<String, Object> paramMap = Maps.newHashMap();
        LonLatUtil.getOilPriceFromOilUsdCnyCom(paramMap);

    }


}

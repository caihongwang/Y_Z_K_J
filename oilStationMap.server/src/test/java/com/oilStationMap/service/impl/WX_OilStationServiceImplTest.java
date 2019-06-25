package com.oilStationMap.service.impl;

import com.oilStationMap.MySuperTest;
import com.oilStationMap.service.WX_OilStationService;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by caihongwang on 2018/6/16.
 */
public class WX_OilStationServiceImplTest extends MySuperTest {

    private static final Logger logger = LoggerFactory.getLogger(WX_OilStationServiceImplTest.class);


    @Autowired
    private WX_OilStationService wxOilStationService;

    @Test
    public void Test(){
        Map<String, Object> paramMap = Maps.newHashMap();
//        paramMap.put("lat", 39.873776);
//        paramMap.put("lon", 116.51015);
//        paramMap.put("r", 5000);
//        wxOilStationService.getOilStationList(paramMap);

//        Map<String, Object> paramMap = Maps.newHashMap();
//        paramMap.put("uid", "1");
//        paramMap.put("oilStationCode", "117578");
//        paramMap.put("oilStationName", "中国石化松桃大路田坝加油站");
//        paramMap.put("oilStationAdress", "贵州省铜仁市松桃苗族自治县304省道东50米");
//        paramMap.put("oilStationLat", "28.12299");
//        paramMap.put("oilStationLon", "108.95828");
//        paramMap.put("oilStationPrice", "[{\"oilNameLabel\": \"柴油\", \"oilModelLabel\": \"0\", \"oilPriceLabel\": \"6.90\"},{\"oilNameLabel\": \"汽油\", \"oilModelLabel\": \"92\", \"oilPriceLabel\": \"7.33\"},{\"oilNameLabel\": \"汽油\", \"oilModelLabel\": \"95\", \"oilPriceLabel\": \"7.70\"},{\"oilNameLabel\": \"饮料\", \"oilModelLabel\": \"红牛\", \"oilPriceLabel\": \"6.00\"},{\"oilNameLabel\": \"饮料\", \"oilModelLabel\": \"芬达\", \"oilPriceLabel\": \"2.50\"},{\"oilNameLabel\": \"饮料\", \"oilModelLabel\": \"百事\", \"oilPriceLabel\": \"2.50\"},{\"oilNameLabel\": \"零食\", \"oilModelLabel\": \"槟榔\", \"oilPriceLabel\": \"10.00\"},{\"oilNameLabel\": \"饮料\", \"oilModelLabel\": \"娃哈哈\", \"oilPriceLabel\": \"2.00\"},{\"oilNameLabel\": \"饮料\", \"oilModelLabel\": \"冰红茶\", \"oilPriceLabel\": \"3.00\"},{\"oilNameLabel\": \"饮料\", \"oilModelLabel\": \"美年达\", \"oilPriceLabel\": \"2.50\"},{\"oilNameLabel\": \"饮料\", \"oilModelLabel\": \"美年达\", \"oilPriceLabel\": \"2.50\"}]");
//        wxOilStationService.addOrUpdateOilStation(paramMap);

//        Map<String, Object> paramMap = Maps.newHashMap();
//        wxOilStationService.addOrUpdateOilStationByTencetMap(paramMap);

//        Map<String, Object> paramMap = Maps.newHashMap();
//        wxOilStationService.getOilPriceFromOilUsdCnyCom(paramMap);

//        Map<String, Object> paramMap = Maps.newHashMap();
//        paramMap.put("uid", "3616");
//        paramMap.put("lon", "106.70722");
//        paramMap.put("lat", "26.5982");
//        paramMap.put("dis", "2");
//        wxOilStationService.getOneOilStationByCondition(paramMap);

//        String oilStationName = "中国石油加油站(天源二站)";
//        String oilStationAddress = "山西省长治市潞城区国道007乡道交叉口向北路东";
//        wxOilStationService.createOilStationHireInfoUrl(oilStationName, oilStationAddress);

        paramMap.put("lat", 34.481738);
        paramMap.put("lon", 113.78742);
        paramMap.put("dis", 2);
        wxOilStationService.getOneOilStationByCondition(paramMap);


    }

}

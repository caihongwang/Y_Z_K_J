package com.oilStationMap.service.impl;

import com.google.common.collect.Maps;
import com.oilStationMap.MySuperTest;
import com.oilStationMap.service.WX_LeagueService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by caihongwang on 2019/6/24.
 */
public class WX_LeagusServiceImplTest extends MySuperTest {

    private static final Logger logger = LoggerFactory.getLogger(WX_MessageServiceImpl.class);

    @Autowired
    private WX_LeagueService wxLeagueService;

    @Test
    public void Test(){
        try{
            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.clear();
            paramMap.put("uid", "1");
            paramMap.put("name", "蔡红旺");
            paramMap.put("phone", "17701359899");
            paramMap.put("remark", "加油员");
            paramMap.put("leagueTypeCode", "1");
            wxLeagueService.addLeague(paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
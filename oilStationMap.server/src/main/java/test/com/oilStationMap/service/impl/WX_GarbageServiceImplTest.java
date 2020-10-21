package com.oilStationMap.service.impl;

import com.google.common.collect.Maps;
import com.oilStationMap.MySuperTest;
import com.oilStationMap.service.WX_GarbageService;
import com.oilStationMap.service.WX_LeagueService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by caihongwang on 2019/7/9.
 */
public class WX_GarbageServiceImplTest extends MySuperTest {

    @Autowired
    private WX_GarbageService wxGarbageService;

    @Test
    public void Test(){
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("garbageName", "胶水");
        wxGarbageService.getSimpleGarbageByCondition(paramMap);
    }
}
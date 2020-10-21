package com.oilStationMap.controller;

import com.google.common.collect.Maps;
import com.oilStationMap.MySuperTest;
import com.oilStationMap.handler.WX_AdExtensionHistoryHandler;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by caihongwang on 2019/6/8.
 */
public class WX_AdExtensionHistoryControllerTest extends MySuperTest {

    @Autowired
    private WX_AdExtensionHistoryHandler wxAdExtensionHistoryHandler;

    @Test
    public void TEST() throws Exception {
        Map<String, String> paramMap = Maps.newHashMap();
        paramMap.put("mediaAppId", "asdfgh");
        paramMap.put("adAppId", "asdfg");
        paramMap.put("adExtensionRandomNum", "asdfgh");
        paramMap.put("createTime", new Date().getTime()+"");
        wxAdExtensionHistoryHandler.addAdExtensionHistory(paramMap);
    }

}
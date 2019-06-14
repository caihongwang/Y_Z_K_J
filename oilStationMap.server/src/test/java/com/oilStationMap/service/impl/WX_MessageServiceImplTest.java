package com.oilStationMap.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oilStationMap.MySuperTest;
import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.service.WX_CommonService;
import com.oilStationMap.service.WX_DicService;
import com.oilStationMap.service.WX_MessageService;
import com.oilStationMap.service.WX_SourceMaterialService;
import com.oilStationMap.utils.*;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by caihongwang on 2018/12/11.
 */
public class WX_MessageServiceImplTest extends MySuperTest {


    private static final Logger logger = LoggerFactory.getLogger(WX_MessageServiceImpl.class);

    @Autowired
    private WX_MessageService wxMessageService;

    @Test
    public void Test(){
        try{
            Map<String, Object> paramMap = Maps.newHashMap();
            wxMessageService.dailyMessageSend(paramMap);
        } catch (Exception e) {

        }

//        Map<String, Object> paramMap = Maps.newHashMap();
//        wxMessageService.redActivityMessageSend(paramMap);
    }

}

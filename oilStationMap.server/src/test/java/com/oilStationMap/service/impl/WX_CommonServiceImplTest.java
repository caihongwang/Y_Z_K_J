package com.oilStationMap.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.oilStationMap.MySuperTest;
import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.service.WX_CommonService;
import com.oilStationMap.service.WX_DicService;
import com.oilStationMap.service.WX_RedPacketService;
import com.oilStationMap.utils.*;
import com.oilStationMap.dao.WX_CustomMessageHistoryDao;
import com.oilStationMap.dao.WX_DicDao;
import com.oilStationMap.dao.WX_UserDao;
import com.google.common.collect.Maps;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

public class WX_CommonServiceImplTest extends MySuperTest {

    private static final Logger logger = LoggerFactory.getLogger(WX_CommonServiceImpl.class);

    @Autowired
    private WX_CommonService wxCommonService;

    @Test
    public void TEST() throws Exception {
//        Map<String, Object> paramMap = Maps.newHashMap();
//        Map<String, Object> dataMap = Maps.newHashMap();
//
//        Map<String, Object> firstMap = Maps.newHashMap();
//        firstMap.put("value", "蔡红旺");
//        firstMap.put("color", "#0017F5");
//        dataMap.put("first", firstMap);
//
//        Map<String, Object> keyword1Map = Maps.newHashMap();
//        keyword1Map.put("value", "蔡红旺");
//        keyword1Map.put("color", "#0017F5");
//        dataMap.put("keyword1", keyword1Map);
//
//        Map<String, Object> keyword2Map = Maps.newHashMap();
//        keyword2Map.put("value", "【油价地图】");
//        keyword2Map.put("color", "#0017F5");
//        dataMap.put("keyword2", keyword2Map);
//
//        Map<String, Object> keyword3Map = Maps.newHashMap();
//        keyword3Map.put("value", "只为专注油价资讯，为车主省钱.");
//        keyword3Map.put("color", "#0017F5");
//        dataMap.put("keyword3", keyword3Map);
//
//        Map<String, Object> remarkMap = Maps.newHashMap();
//        remarkMap.put("value", "蔡红旺");
//        remarkMap.put("color", "#0017F5");
//        dataMap.put("remark", remarkMap);
//
//        paramMap.put("data", JSONObject.toJSONString(dataMap));
//        paramMap.put("url", "https://www.91caihongwang.com/oilStationMap");
//
//        paramMap.put("openId", "o8-g249hJL8mmxq6MGsxIAAz4ZaM");
//        paramMap.put("template_id", "v4tKZ7kAwI6VrXzAJyAxi5slILLRBibZg-G3kRwNIKQ");
//        wxCommonService.sendTemplateMessageForWxPublicNumber(paramMap);

//        Map<String, Object> paramMap = Maps.newHashMap();
//        paramMap.put("appId", "gh_bcce99ab0079");
//        paramMap.put("openId", "oJcI1wt-ibRdgri1y8qKYCRQaq8g");
//        paramMap.put("wxCustomMessageCode", "25");
//        wxCommonService.sendCustomCardMessageWxPublicNumber(paramMap);

//        Map<String, Object> paramMap = Maps.newHashMap();
//        paramMap.put("ToUserName", "gh_bcce99ab0079");
//        paramMap.put("Event", "subscribe");
//        paramMap.put("Content", "");
//        paramMap.put("FromUserName", "oJcI1wt-ibRdgri1y8qKYCRQaq8g");
//        wxCommonService.receviceAndSendCustomMessage(paramMap);

    }


}
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
//            wxMessageService.dailyMessageSend(paramMap);
//


//            paramMap.clear();
//            wxMessageService.dailyLuckDrawMessageSend(paramMap);



//            paramMap.clear();
//            wxMessageService.redActivityMessageSend(paramMap);



//            paramMap.clear();
//            paramMap.put("updateNum", "123");
//            wxMessageService.dailyUpdateOilPriceMessageSend(paramMap);



//            paramMap.clear();
//            paramMap.put("name", "蔡红旺");
//            paramMap.put("phone", "17701359899");
//            paramMap.put("remark", "加油员");
//            wxMessageService.dailyLeagueMessageSend(paramMap);



//            paramMap.clear();
//            Map<String, Object> dataMap = Maps.newHashMap();
//            //标题
//            Map<String, Object> firstMap = Maps.newHashMap();
//            firstMap.put("value", "监测到油站信息操作服务出现问题");
//            firstMap.put("color", "#0017F5");
//            dataMap.put("keyword1", firstMap);
//            //错误描述
//            Map<String, Object> keyword1Map = Maps.newHashMap();
//            keyword1Map.put("value", "aaa 添加或者更新异常");
//            keyword1Map.put("color", "#0017F5");
//            dataMap.put("first", keyword1Map);
//            //错误详情
//            Map<String, Object> keyword2Map = Maps.newHashMap();
//            keyword2Map.put("value", "从[百度地图]获取爬取加油站信息入库失败");
//            keyword2Map.put("color", "#0017F5");
//            dataMap.put("keyword2", keyword2Map);
//            //时间
//            Map<String, Object> keyword3Map = Maps.newHashMap();
//            keyword3Map.put("value", "1234567890");
//            keyword3Map.put("color", "#0017F5");
//            dataMap.put("keyword3", keyword3Map);
//            //备注
//            Map<String, Object> remarkMap = Maps.newHashMap();
//            remarkMap.put("value", "1234567890987654321");
//            remarkMap.put("color", "#0017F5");
//            dataMap.put("remark", remarkMap);
//            //整合
//            paramMap.put("data", JSONObject.toJSONString(dataMap));
//            wxMessageService.dailyUpdateOrAddOilStationMessageSend(paramMap);



            paramMap.clear();//清空参数，重新准备参数
            Map<String, Object> dataMap = Maps.newHashMap();

            Map<String, Object> firstMap = Maps.newHashMap();
            firstMap.put("value", "警告:恶意修改加油站-油价");
            firstMap.put("color", "#8B0000");
            dataMap.put("first", firstMap);

            //获取当前时间
            Date currentDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Map<String, Object> keyword1Map = Maps.newHashMap();
            keyword1Map.put("value", sdf.format(currentDate));
            keyword1Map.put("color", "#0017F5");
            dataMap.put("keyword1", keyword1Map);

            Map<String, Object> keyword2Map = Maps.newHashMap();
            keyword2Map.put("value", "【油价地图】");
            keyword2Map.put("color", "#0017F5");
            dataMap.put("keyword2", keyword2Map);

            Map<String, Object> keyword3Map = Maps.newHashMap();
            keyword3Map.put("value", "只为专注油价资讯，为车主省钱.");
            keyword3Map.put("color", "#0017F5");
            dataMap.put("keyword3", keyword3Map);

            Map<String, Object> remarkMap = Maps.newHashMap();
            remarkMap.put("value", "用户uid:" + 1234 + "对加油站code:" + 123456 + "在乱改油价来恶意竞争,对该用户进行锁定并观察后期用户行为,急急急...");
            remarkMap.put("color", "#8B0000");
            dataMap.put("remark", remarkMap);

            paramMap.put("data", JSONObject.toJSONString(dataMap));
            wxMessageService.dailyIllegalUpdateOilPriceMessageSend(paramMap);
        } catch (Exception e) {

        }
    }

}

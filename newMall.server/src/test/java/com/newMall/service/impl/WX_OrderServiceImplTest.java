package com.newMall.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.newMall.NewMallSuperTest;
import com.newMall.service.WX_OrderService;
import com.newMall.utils.SpriderFor7DingdongProductUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by caihongwang on 2019/6/30.
 */
public class WX_OrderServiceImplTest extends NewMallSuperTest {

    @Autowired
    private WX_OrderService wxOrderService;

    @Test
    public void TEST(){
        try {
            Map<String, Object> paramMap = Maps.newHashMap();
            Map<String, Object> attachMap = Maps.newHashMap();
            attachMap.put("balance", "0");
            attachMap.put("integral", "0");
            attachMap.put("shopId", "1");
            attachMap.put("payMoney", "1");
            attachMap.put("wxOrderId", "6d1315b5a8704ffd9286df3c6e40f651");
            paramMap.put("attach", JSONObject.toJSONString(attachMap));
            paramMap.put("openid", "oAzim5MEDsI9fBi88cT_zovkw2L8");
            wxOrderService.wxPayNotifyForPayTheBillInMiniProgram(paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
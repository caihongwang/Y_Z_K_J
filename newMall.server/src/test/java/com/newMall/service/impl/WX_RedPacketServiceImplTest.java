package com.newMall.service.impl;

import com.google.common.collect.Maps;
import com.newMall.NewMallSuperTest;
import com.newMall.code.NewMallCode;
import com.newMall.service.WX_RedPacketService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by caihongwang on 2019/7/31.
 */
public class WX_RedPacketServiceImplTest extends NewMallSuperTest {

    @Autowired
    private WX_RedPacketService wxRedPacketService;

    @Test
    public void Test(){
        Map<String, Object> enterprisePaymentMap = Maps.newHashMap();
        Double shopAmount = 1.0;
        String shopOpenId = "oWU4s5Nk_CHaSeBQM-tmaWlP1MyE";
        String customDesc = "AAAAAAAA";
        enterprisePaymentMap.put("amount", ((int) (shopAmount * 100)) + "");
        enterprisePaymentMap.put("openId", shopOpenId);
        enterprisePaymentMap.put("reUserName", NewMallCode.WX_MINI_PROGRAM_NAME);
        enterprisePaymentMap.put("wxPublicNumGhId", "gh_051743a1ae1f");
        enterprisePaymentMap.put("customDesc", customDesc);
        wxRedPacketService.enterprisePayment(enterprisePaymentMap);
    }
}
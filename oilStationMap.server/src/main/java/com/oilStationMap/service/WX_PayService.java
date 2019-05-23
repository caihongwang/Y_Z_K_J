package com.oilStationMap.service;

import com.oilStationMap.dto.ResultMapDTO;

import java.util.Map;

public interface WX_PayService {

    /**
     * 统一下单 支付
     */
    public ResultMapDTO unifiedOrderPay(Map<String, Object> paramMap);

    /**
     * 获取支付页面
     */
    public ResultMapDTO getPaymentPage(Map<String, Object> paramMap);

    /**
     * 获取oauth的url
     */
    public ResultMapDTO toOauthUrlForPaymentPage(Map<String, Object> paramMap);

}

package com.newMall.service;

import com.newMall.dto.ResultMapDTO;

import java.util.Map;

public interface WX_PayService {

    /**
     * 统一下单 支付
     */
    public ResultMapDTO unifiedOrderPay(Map<String, Object> paramMap);

    /**
     * 获取oauth
     */
    public ResultMapDTO getOauth(Map<String, Object> paramMap);

    /**
     * 获取oauth的url
     */
    public ResultMapDTO getToOauthUrl(Map<String, Object> paramMap);

}

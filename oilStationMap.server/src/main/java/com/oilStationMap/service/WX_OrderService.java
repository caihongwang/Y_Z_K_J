package com.oilStationMap.service;

import com.oilStationMap.dto.ResultMapDTO;

import java.util.Map;

public interface WX_OrderService {


    /**
     * 创建统一统一订单
     */
    public ResultMapDTO requestWxPayUnifiedOrder(Map<String, Object> paramMap) throws Exception;
}

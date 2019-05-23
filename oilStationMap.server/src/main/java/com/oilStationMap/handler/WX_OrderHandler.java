package com.oilStationMap.handler;

import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.service.WX_OrderService;
import com.oilStationMap.utils.MapUtil;
import com.google.common.collect.Maps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 订单Handler
 */
@Component
public class WX_OrderHandler {

    private static final Logger logger = LoggerFactory.getLogger(WX_OrderHandler.class);

    @Autowired
    private WX_OrderService wxOrderService;


    public ResultMapDTO requestWxPayUnifiedOrder(Map<String, String> paramMap) {
        logger.info("在hanlder中使用统一订单的方式进行下订单，发起微信支付-wxPayUnifiedOrder,请求-paramMap:" + paramMap);
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultMapDTO = wxOrderService.requestWxPayUnifiedOrder(objectParamMap);
            } catch (Exception e) {
                Map<String, String> resultMap = Maps.newHashMap();
                resultMapDTO.setResultListTotal(0);
                resultMapDTO.setResultMap(resultMap);
                resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                logger.error("在hanlder中使用统一订单的方式进行下订单，发起微信支付-wxPayUnifiedOrder is error, paramMap : " + paramMap + ", e : " + e);
            }
        } else {
            resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在hanlder中使用统一订单的方式进行下订单，发起微信支付-wxPayUnifiedOrder,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

}

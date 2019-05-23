package com.oilStationMap.handler;

import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.service.WX_PayService;
import com.oilStationMap.utils.MapUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 红包Handler
 */
@Component
public class WX_PayHandler {

    private static final Logger logger = LoggerFactory.getLogger(WX_PayHandler.class);

    @Autowired
    private WX_PayService wxPayService;

    /**
     * 获取Oauth
     * @param paramMap
     * @return
     */
    public ResultMapDTO getPaymentPage(Map<String, String> paramMap) {
        logger.info("在hanlder中获取oauth-getPaymentPage,请求-paramMap:" + paramMap);
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        try {
            resultMapDTO = wxPayService.getPaymentPage(objectParamMap);
        } catch (Exception e) {
            resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
            logger.error("在hanlder中获取oauth-getPaymentPage is error, paramMap : " + paramMap + ", e : " + e);
        }
        logger.info("在hanlder中获取oauth-getPaymentPage,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 获取Oauth的url
     * @param paramMap
     * @return
     */
    public ResultMapDTO toOauthUrlForPaymentPage(Map<String, String> paramMap) {
        logger.info("在hanlder中获取oauth的url-toOauthUrlForPaymentPage,请求-paramMap:" + paramMap);
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultMapDTO = wxPayService.toOauthUrlForPaymentPage(objectParamMap);
            } catch (Exception e) {
                resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                logger.error("在hanlder中获取oauth的url-toOauthUrlForPaymentPage is error, paramMap : " + paramMap + ", e : " + e);
            }
        } else {
            resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在hanlder中获取oauth的url-toOauthUrlForPaymentPage,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 统一订单支付请求
     * @param paramMap
     * @return
     */
    public ResultMapDTO unifiedOrderPay(Map<String, String> paramMap) {
        logger.info("在hanlder中统一订单支付请求-unifiedOrderPay,请求-paramMap:" + paramMap);
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultMapDTO = wxPayService.unifiedOrderPay(objectParamMap);
            } catch (Exception e) {
                resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                logger.error("在hanlder中统一订单支付请求-unifiedOrderPay is error, paramMap : " + paramMap + ", e : " + e);
            }
        } else {
            resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在hanlder中统一订单支付请求-unifiedOrderPay,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }
}

package com.oilStationMap.handler;

import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.service.WX_CommonService;
import com.oilStationMap.service.WX_RedPacketService;
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
public class WX_RedPacketHandler {

    private static final Logger logger = LoggerFactory.getLogger(WX_RedPacketHandler.class);

    @Autowired
    private WX_CommonService wxCommonService;

    @Autowired
    private WX_RedPacketService wxRedPacketService;

    /**
     * 获取红包二维码
     * @param paramMap
     * @return
     */
    public ResultMapDTO getRedPacketQrCode(Map<String, String> paramMap) {
        logger.info("在hanlder中获取红包二维码-getRedPacketQrCode,请求-paramMap:" + paramMap);
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        try {
            resultMapDTO = wxRedPacketService.getRedPacketQrCode(objectParamMap);
        } catch (Exception e) {
            resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
            logger.error("在hanlder中获取红包二维码-getRedPacketQrCode is error, paramMap : " + paramMap + ", e : " + e);
        }
        logger.info("在hanlder中获取红包二维码-getRedPacketQrCode,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 获取Oauth
     * @param paramMap
     * @return
     */
    public ResultMapDTO getOauth(Map<String, String> paramMap) {
        logger.info("在hanlder中获取oauth-getOauth,请求-paramMap:" + paramMap);
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        try {
            resultMapDTO = wxRedPacketService.getOauth(objectParamMap);
        } catch (Exception e) {
            resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
            logger.error("在hanlder中获取oauth-getOauth is error, paramMap : " + paramMap + ", e : " + e);
        }
        logger.info("在hanlder中获取oauth-getOauth,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 获取Oauth的url
     * @param paramMap
     * @return
     */
    public ResultMapDTO getToOauthUrl(Map<String, String> paramMap) {
        logger.info("在hanlder中获取oauth的url-getToOauthUrl,请求-paramMap:" + paramMap);
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultMapDTO = wxRedPacketService.getToOauthUrl(objectParamMap);
            } catch (Exception e) {
                resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                logger.error("在hanlder中获取oauth的url-getToOauthUrl is error, paramMap : " + paramMap + ", e : " + e);
            }
        } else {
            resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在hanlder中获取oauth的url-getToOauthUrl,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 企业付款，直达微信零钱
     * @param paramMap
     * @return
     */
    public ResultMapDTO enterprisePayment(Map<String, String> paramMap) {
        logger.info("在hanlder中企业付款，直达微信零钱-enterprisePayment,请求-paramMap:" + paramMap);
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultMapDTO = wxRedPacketService.enterprisePayment(objectParamMap);
            } catch (Exception e) {
                resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                logger.error("在hanlder中企业付款，直达微信零钱-enterprisePayment is error, paramMap : " + paramMap + ", e : " + e);
            }
        } else {
            resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在hanlder中企业付款，直达微信零钱-enterprisePayment,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 发送普通红包
     * @param paramMap
     * @return
     */
    public ResultMapDTO sendRedPacket(Map<String, String> paramMap) {
        logger.info("在hanlder中发送普通红包-sendRedPacket,请求-paramMap:" + paramMap);
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultMapDTO = wxRedPacketService.sendRedPacket(objectParamMap);
            } catch (Exception e) {
                resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                logger.error("在hanlder中发送普通红包-sendRedPacket is error, paramMap : " + paramMap + ", e : " + e);
            }
        } else {
            resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在hanlder中发送普通红包-sendRedPacket,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 发送分裂红包
     * @param paramMap
     * @return
     */
    public ResultMapDTO sendGroupRedPacket(Map<String, String> paramMap) {
        logger.info("在hanlder中发送分裂红包-sendGroupRedPacket,请求-paramMap:" + paramMap);
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultMapDTO = wxRedPacketService.sendGroupRedPacket(objectParamMap);
            } catch (Exception e) {
                resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                logger.error("在hanlder中发送分裂红包-sendGroupRedPacket is error, paramMap : " + paramMap + ", e : " + e);
            }
        } else {
            resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在hanlder中发送分裂红包-sendGroupRedPacket,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }
}

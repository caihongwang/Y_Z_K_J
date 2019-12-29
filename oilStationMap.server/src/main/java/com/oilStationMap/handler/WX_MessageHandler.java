package com.oilStationMap.handler;

import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.service.WX_MessageService;
import com.oilStationMap.utils.MapUtil;
import com.google.common.collect.Maps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 微信公众号消息Handler
 */
@Component
public class WX_MessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(WX_MessageHandler.class);

    @Autowired
    private WX_MessageService wxMessageService;

    /**
     * 向微信公众号粉丝群发红包模板消息
     * @param paramMap
     * @return
     */
    public ResultMapDTO redActivityMessageSend(Map<String, String> paramMap) {
        logger.info("【hanlder】发送红包资讯-redActivityMessageSend,请求-paramMap:" + paramMap);
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        new Thread(){
            public void run(){
                try {
                    wxMessageService.redActivityMessageSend(objectParamMap);
                } catch (Exception e) {
                    logger.error("在【hanlder】发送红包资讯-redActivityMessageSend is error, paramMap : " + paramMap + ", e : " + e);
                }
            }
        }.start();
        resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
        resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        logger.info("【hanlder】发送红包资讯-redActivityMessageSend,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 根据OpenID列表群发
     * @param paramMap
     * @return
     */
    public ResultMapDTO dailyMessageSend(Map<String, String> paramMap) {
        logger.info("【hanlder】发送红包资讯-dailyMessageSend,请求-paramMap:" + paramMap);
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        new Thread(){
            public void run(){
                Map<String, Object> objectParamMap = Maps.newHashMap();
                try {
                    wxMessageService.dailyMessageSend(objectParamMap);
                } catch (Exception e) {
                    resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                    resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                    logger.error("【hanlder】发送红包资讯-dailyMessageSend is error, paramMap : " + paramMap + ", e : " + e);
                }
            }
        }.start();
        resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
        resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        logger.info("【hanlder】发送红包资讯-dailyMessageSend,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 根据OpenID列表群发
     * @param paramMap
     * @return
     */
    public ResultMapDTO dailyLuckDrawMessageSend(Map<String, String> paramMap) {
        logger.info("【hanlder】发送抽奖资讯-dailyLuckDrawMessageSend,请求-paramMap:" + paramMap);
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        new Thread(){

            public void run(){
                Map<String, Object> objectParamMap = Maps.newHashMap();
                try {
                    wxMessageService.dailyLuckDrawMessageSend(objectParamMap);
                } catch (Exception e) {
                    resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                    resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                    logger.error("【hanlder】发送抽奖资讯-dailyLuckDrawMessageSend is error, paramMap : " + paramMap + ", e : " + e);
                }
            }
        }.start();
        resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
        resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        logger.info("【hanlder】发送抽奖资讯-dailyLuckDrawMessageSend,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 根据OpenID列表群发-发送车主福利for车用尿素资讯
     * @param paramMap
     * @return
     */
    public ResultMapDTO dailyCarUreaMessageSend(Map<String, String> paramMap) {
        logger.info("【hanlder】发送车主福利for车用尿素资讯-dailyCarUreaMessageSend,请求-paramMap:" + paramMap);
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        new Thread(){
            public void run(){
                Map<String, Object> objectParamMap = Maps.newHashMap();
                try {
                    wxMessageService.dailyCarUreaMessageSend(objectParamMap);
                } catch (Exception e) {
                    resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                    resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                    logger.error("【hanlder】发送车主福利for车用尿素资讯-dailyCarUreaMessageSend is error, paramMap : " + paramMap + ", e : " + e);
                }
            }
        }.start();
        resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
        resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        logger.info("【hanlder】发送车主福利for车用尿素资讯-dailyCarUreaMessageSend,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 根据粉象生活Json获取【粉象生活Excel】福利
     * @param paramMap
     * @return
     */
    public ResultMapDTO dailyGetFenXiangShengHuoProduct(Map<String, String> paramMap) {
        logger.info("【hanlder】根据粉象生活Json获取【粉象生活Excel】福利-dailyGetFenXiangShengHuoProduct,请求-paramMap:" + paramMap);
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        new Thread(){
            public void run(){
                Map<String, Object> objectParamMap = Maps.newHashMap();
                try {
                    wxMessageService.dailyGetFenXiangShengHuoProduct(objectParamMap);
                } catch (Exception e) {
                    resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                    resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                    logger.error("【hanlder】发送车主福利for车用尿素资讯-dailyGetFenXiangShengHuoProduct is error, paramMap : " + paramMap + ", e : " + e);
                }
            }
        }.start();
        resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
        resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        logger.info("【hanlder】根据粉象生活Json获取【粉象生活Excel】福利-dailyGetFenXiangShengHuoProduct,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 微信广告自动化过程中的异常设备
     * @param paramMap
     * @return
     */
    public ResultMapDTO exceptionDevicesMessageSend(Map<String, String> paramMap) {
        logger.info("【hanlder】微信广告自动化过程中的异常设备-exceptionDevicesMessageSend,请求-paramMap:" + paramMap);
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        new Thread(){
            public void run(){
                Map<String, Object> objectParamMap = Maps.newHashMap();
                try {
                    wxMessageService.exceptionDevicesMessageSend(objectParamMap);
                } catch (Exception e) {
                    resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                    resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                    logger.error("【hanlder】微信广告自动化过程中的异常设备-exceptionDevicesMessageSend is error, paramMap : " + paramMap + ", e : " + e);
                }
            }
        }.start();
        resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
        resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        logger.info("【hanlder】微信广告自动化过程中的异常设备-exceptionDevicesMessageSend,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

}

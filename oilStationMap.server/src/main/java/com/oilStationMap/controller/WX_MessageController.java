package com.oilStationMap.controller;

import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.handler.WX_MessageHandler;
import com.oilStationMap.utils.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信公众号消息Controller
 */
@Controller
@RequestMapping(value = "/wxMessage", produces = "application/json;charset=utf-8")
public class WX_MessageController {

    private static final Logger logger = LoggerFactory.getLogger(WX_MessageController.class);

    @Autowired
    private WX_MessageHandler wx_MessageHandler;

    @RequestMapping("/redActivityMessageSend")
    @ResponseBody
    public Map<String, Object> redActivityMessageSend(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("【controller】发送红包资讯-redActivityMessageSend,请求-paramMap:" + paramMap);
        try {
            ResultMapDTO resultMapDTO = wx_MessageHandler.redActivityMessageSend(paramMap);
            resultMap.put("recordsFiltered", resultMapDTO.getResultListTotal());
            resultMap.put("data", resultMapDTO.getResultMap());
            resultMap.put("code", resultMapDTO.getCode());
            resultMap.put("message", resultMapDTO.getMessage());
        } catch (Exception e) {
            logger.error("【controller】发送红包资讯-redActivityMessageSend is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("【controller】发送红包资讯-redActivityMessageSend,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/dailyMessageSend")
    @ResponseBody
    public Map<String, Object> dailyMessageSend(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("【controller】发送红包资讯-dailyMessageSend,请求-paramMap:" + paramMap);
        try {
            ResultMapDTO resultMapDTO = wx_MessageHandler.dailyMessageSend(paramMap);
            resultMap.put("recordsFiltered", resultMapDTO.getResultListTotal());
            resultMap.put("data", resultMapDTO.getResultMap());
            resultMap.put("code", resultMapDTO.getCode());
            resultMap.put("message", resultMapDTO.getMessage());
        } catch (Exception e) {
            logger.error("【controller】发送红包资讯-dailyMessageSend is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("【controller】发送红包资讯-dailyMessageSend,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/dailyLuckDrawMessageSend")
    @ResponseBody
    public Map<String, Object> dailyLuckDrawMessageSend(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("【controller】发送抽奖资讯-dailyLuckDrawMessageSend,请求-paramMap:" + paramMap);
        try {
            ResultMapDTO resultMapDTO = wx_MessageHandler.dailyLuckDrawMessageSend(paramMap);
            resultMap.put("recordsFiltered", resultMapDTO.getResultListTotal());
            resultMap.put("data", resultMapDTO.getResultMap());
            resultMap.put("code", resultMapDTO.getCode());
            resultMap.put("message", resultMapDTO.getMessage());
        } catch (Exception e) {
            logger.error("【controller】发送抽奖资讯-dailyLuckDrawMessageSend is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("【controller】发送抽奖资讯-dailyLuckDrawMessageSend,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/dailyCarUreaMessageSend")
    @ResponseBody
    public Map<String, Object> dailyCarUreaMessageSend(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("【controller】发送车主福利for车用尿素资讯-dailyCarUreaMessageSend,请求-paramMap:" + paramMap);
        try {
            ResultMapDTO resultMapDTO = wx_MessageHandler.dailyCarUreaMessageSend(paramMap);
            resultMap.put("recordsFiltered", resultMapDTO.getResultListTotal());
            resultMap.put("data", resultMapDTO.getResultMap());
            resultMap.put("code", resultMapDTO.getCode());
            resultMap.put("message", resultMapDTO.getMessage());
        } catch (Exception e) {
            logger.error("【controller】发送车主福利for车用尿素资讯-dailyCarUreaMessageSend is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("【controller】发送车主福利for车用尿素资讯-dailyCarUreaMessageSend,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/dailyGetFenXiangShengHuoProduct")
    @ResponseBody
    public Map<String, Object> dailyGetFenXiangShengHuoProduct(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("【controller】根据粉象生活Json获取【粉象生活Excel】福利-dailyGetFenXiangShengHuoProduct,请求-paramMap:" + paramMap);
        try {
            ResultMapDTO resultMapDTO = wx_MessageHandler.dailyGetFenXiangShengHuoProduct(paramMap);
            resultMap.put("recordsFiltered", resultMapDTO.getResultListTotal());
            resultMap.put("data", resultMapDTO.getResultMap());
            resultMap.put("code", resultMapDTO.getCode());
            resultMap.put("message", resultMapDTO.getMessage());
        } catch (Exception e) {
            logger.error("【controller】根据粉象生活Json获取【粉象生活Excel】福利-dailyGetFenXiangShengHuoProduct is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("【controller】根据粉象生活Json获取【粉象生活Excel】福利-dailyGetFenXiangShengHuoProduct,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/exceptionDevicesMessageSend")
    @ResponseBody
    public Map<String, Object> exceptionDevicesMessageSend(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("【controller】微信广告自动化过程中的异常设备-exceptionDevicesMessageSend,请求-paramMap:" + paramMap);
        try {
            ResultMapDTO resultMapDTO = wx_MessageHandler.exceptionDevicesMessageSend(paramMap);
            resultMap.put("recordsFiltered", resultMapDTO.getResultListTotal());
            resultMap.put("data", resultMapDTO.getResultMap());
            resultMap.put("code", resultMapDTO.getCode());
            resultMap.put("message", resultMapDTO.getMessage());
        } catch (Exception e) {
            logger.error("【controller】微信广告自动化过程中的异常设备-exceptionDevicesMessageSend is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("【controller】微信广告自动化过程中的异常设备-exceptionDevicesMessageSend,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/exceptionDomainMessageSend")
    @ResponseBody
    public Map<String, Object> exceptionDomainMessageSend(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("【controller】小寨家里公网IP地址发生变化-exceptionDomainMessageSend,请求-paramMap:" + paramMap);
        try {
            ResultMapDTO resultMapDTO = wx_MessageHandler.exceptionDomainMessageSend(paramMap);
            resultMap.put("recordsFiltered", resultMapDTO.getResultListTotal());
            resultMap.put("data", resultMapDTO.getResultMap());
            resultMap.put("code", resultMapDTO.getCode());
            resultMap.put("message", resultMapDTO.getMessage());
        } catch (Exception e) {
            logger.error("【controller】小寨家里公网IP地址发生变化-exceptionDomainMessageSend is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("【controller】小寨家里公网IP地址发生变化-exceptionDomainMessageSend,响应-response:" + resultMap);
        return resultMap;
    }


    @RequestMapping("/lidezhushou")
    @ResponseBody
    public Map<String, Object> lidezhushou(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("code", "000");
        resultMap.put("data", "");
        resultMap.put("errorMessage", "");
        logger.info("【controller】给里德助手发送心跳激活信息-exceptionDomainMessageSend,响应-response:" + resultMap);
        return resultMap;
    }

}

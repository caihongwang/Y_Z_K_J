package com.oilStationMap.controller;

import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.handler.WX_RedPacketHandler;
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

@Controller
@RequestMapping(value = "/wxRedPacket", produces = "application/json;charset=utf-8")
public class WX_RedPacketController {

    private static final Logger logger = LoggerFactory.getLogger(WX_RedPacketController.class);

    @Autowired
    private WX_RedPacketHandler wx_RedPacketHandler;

    @RequestMapping("/getRedPacketQrCode")
    @ResponseBody
    public Map<String, Object> getRedPacketQrCode(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中获取红包二维码-getRedPacketQrCode,请求-paramMap:" + paramMap);
        try {
            ResultMapDTO resultMapDTO = wx_RedPacketHandler.getRedPacketQrCode(paramMap);
            resultMap.put("recordsFiltered", resultMapDTO.getResultListTotal());
            resultMap.put("data", resultMapDTO.getResultMap());
            resultMap.put("code", resultMapDTO.getCode());
            resultMap.put("message", resultMapDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中获取红包二维码-getRedPacketQrCode is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中获取红包二维码-getRedPacketQrCode,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/getToOauthUrl")
    public String getToOauthUrl(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中获取oauth的url-getToOauthUrl,请求-paramMap:" + paramMap);
        String toOauthUrl = " https://www.91caihongwang.com/oilStationMap/wx_RedPacket/getOauth.do";
        try {
            ResultMapDTO resultMapDTO = wx_RedPacketHandler.getToOauthUrl(paramMap);
            Map<String, String> dataMap = resultMapDTO.getResultMap();
            toOauthUrl = dataMap.get("toOauthUrl");
            resultMap.put("recordsFiltered", resultMapDTO.getResultListTotal());
            resultMap.put("data", dataMap);
            resultMap.put("code", resultMapDTO.getCode());
            resultMap.put("message", resultMapDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中获取oauth的url-getToOauthUrl is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中获取oauth的url-getToOauthUrl,响应-response:" + resultMap);
        return "redirect:" + toOauthUrl;//重定向;
    }

    @RequestMapping("/getOauth")
    @ResponseBody
    public Map<String, Object> getOauth(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中获取oauth-getOauth,请求-paramMap:" + paramMap);
        try {
            ResultMapDTO resultMapDTO = wx_RedPacketHandler.getOauth(paramMap);
            resultMap.put("recordsFiltered", resultMapDTO.getResultListTotal());
            resultMap.put("data", resultMapDTO.getResultMap());
            resultMap.put("code", resultMapDTO.getCode());
            resultMap.put("message", resultMapDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中获取oauth-getOauth is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中获取oauth-getOauth,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/redActivityRulePage")
    public String redActivityRulePage(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中获取oauth-红包活动规则,请求-paramMap:" + paramMap);
        String redActivityRuleUrl = "https://www.91caihongwang.com/oilStationMap/redActivity/redActivityRule/index.html";
        logger.info("在controller中获取oauth-redActivityRulePage,红包活动规则，整合之后 paymentUrl = " + redActivityRuleUrl);
        logger.info("在controller中获取oauth-redActivityRulePage,响应-response:" + resultMap);
        return "redirect:" + redActivityRuleUrl;//重定向;
    }

    @RequestMapping("/enterprisePayment")
    @ResponseBody
    public Map<String, Object> enterprisePayment(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中企业付款，直达微信零钱-enterprisePayment,请求-paramMap:" + paramMap);
        try {
            ResultMapDTO resultMapDTO = wx_RedPacketHandler.enterprisePayment(paramMap);
            resultMap.put("recordsFiltered", resultMapDTO.getResultListTotal());
            resultMap.put("data", resultMapDTO.getResultMap());
            resultMap.put("code", resultMapDTO.getCode());
            resultMap.put("message", resultMapDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中企业付款，直达微信零钱-enterprisePayment is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中企业付款，直达微信零钱-enterprisePayment,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/sendRedPacket")
    @ResponseBody
    public Map<String, Object> sendRedPacket(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中发送普通红包-sendRedPacket,请求-paramMap:" + paramMap);
        try {
            ResultMapDTO resultMapDTO = wx_RedPacketHandler.sendRedPacket(paramMap);
            resultMap.put("recordsFiltered", resultMapDTO.getResultListTotal());
            resultMap.put("data", resultMapDTO.getResultMap());
            resultMap.put("code", resultMapDTO.getCode());
            resultMap.put("message", resultMapDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中发送普通红包-sendRedPacket is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中发送普通红包-sendRedPacket,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/sendGroupRedPacket")
    @ResponseBody
    public Map<String, Object> sendGroupRedPacket(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中发送分裂红包-sendGroupRedPacket,请求-paramMap:" + paramMap);
        try {
            ResultMapDTO resultMapDTO = wx_RedPacketHandler.sendGroupRedPacket(paramMap);
            resultMap.put("recordsFiltered", resultMapDTO.getResultListTotal());
            resultMap.put("data", resultMapDTO.getResultMap());
            resultMap.put("code", resultMapDTO.getCode());
            resultMap.put("message", resultMapDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中发送分裂红包-sendGroupRedPacket is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中发送分裂红包-sendGroupRedPacket,响应-response:" + resultMap);
        return resultMap;
    }
}

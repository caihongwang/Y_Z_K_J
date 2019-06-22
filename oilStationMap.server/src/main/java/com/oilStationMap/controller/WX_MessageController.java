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
        logger.info("在【controller】中向微信公众号粉丝群发红包模板消息-redActivityMessageSend,请求-paramMap:" + paramMap);
        try {
            ResultMapDTO resultMapDTO = wx_MessageHandler.redActivityMessageSend(paramMap);
            resultMap.put("recordsFiltered", resultMapDTO.getResultListTotal());
            resultMap.put("data", resultMapDTO.getResultMap());
            resultMap.put("code", resultMapDTO.getCode());
            resultMap.put("message", resultMapDTO.getMessage());
        } catch (Exception e) {
            logger.error("在【controller】中向微信公众号粉丝群发红包模板消息-redActivityMessageSend is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在【controller】中向微信公众号粉丝群发红包模板消息-redActivityMessageSend,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/dailyMessageSend")
    @ResponseBody
    public Map<String, Object> dailyMessageSend(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在【controller】中根据OpenID列表群发-dailyMessageSend,请求-paramMap:" + paramMap);
        try {
            ResultMapDTO resultMapDTO = wx_MessageHandler.dailyMessageSend(paramMap);
            resultMap.put("recordsFiltered", resultMapDTO.getResultListTotal());
            resultMap.put("data", resultMapDTO.getResultMap());
            resultMap.put("code", resultMapDTO.getCode());
            resultMap.put("message", resultMapDTO.getMessage());
        } catch (Exception e) {
            logger.error("在【controller】中根据OpenID列表群发-dailyMessageSend is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在【controller】中根据OpenID列表群发-dailyMessageSend,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/dailyLuckDrawMessageSend")
    @ResponseBody
    public Map<String, Object> dailyLuckDrawMessageSend(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在【controller】中根据OpenID列表群发-dailyLuckDrawMessageSend,请求-paramMap:" + paramMap);
        try {
            ResultMapDTO resultMapDTO = wx_MessageHandler.dailyLuckDrawMessageSend(paramMap);
            resultMap.put("recordsFiltered", resultMapDTO.getResultListTotal());
            resultMap.put("data", resultMapDTO.getResultMap());
            resultMap.put("code", resultMapDTO.getCode());
            resultMap.put("message", resultMapDTO.getMessage());
        } catch (Exception e) {
            logger.error("在【controller】中根据OpenID列表群发-dailyLuckDrawMessageSend is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在【controller】中根据OpenID列表群发-dailyLuckDrawMessageSend,响应-response:" + resultMap);
        return resultMap;
    }

}

package com.oilStationMap.controller;

import com.alibaba.fastjson.JSONObject;
import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.BoolDTO;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.handler.WX_AdExtensionHistoryHandler;
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
 * @Description 广告推广历史Controller
 * @author caihongwang
 */
@Controller
@RequestMapping(value = "/wxAdExtensionHistory", produces = "application/json;charset=utf-8")
public class WX_AdExtensionHistoryController {

    private static final Logger logger = LoggerFactory.getLogger(WX_AdExtensionHistoryController.class);

    @Autowired
    private WX_AdExtensionHistoryHandler wxAdExtensionHistoryHandler;

    /**
     * 添加广告推广历史
     * @param request
     * @return
     */
    @RequestMapping("/addAdExtensionHistory")
    @ResponseBody
    public Map<String, Object> addAdExtensionHistory(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("【controller】添加广告推广历史-addAdExtensionHistory,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        try {
            BoolDTO boolDTO = wxAdExtensionHistoryHandler.addAdExtensionHistory(paramMap);
            resultMap.put("success", true);
            resultMap.put("code", boolDTO.getCode());
            resultMap.put("message", boolDTO.getMessage());
        } catch (Exception e) {
            logger.error("【controller】添加广告推广历史-addAdExtensionHistory is error, paramMap : {}", JSONObject.toJSONString(paramMap), " , e : {}", e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("【controller】添加广告推广历史-addAdExtensionHistory,响应-resultMap = {}", JSONObject.toJSONString(resultMap));
        return resultMap;
    }

    /**
     * 删除广告推广历史
     * @param request
     * @return
     */
    @RequestMapping("/deleteAdExtensionHistory")
    @ResponseBody
    public Map<String, Object> deleteAdExtensionHistory(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("【controller】删除广告推广历史-deleteAdExtensionHistory,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        try {
            BoolDTO boolDTO = wxAdExtensionHistoryHandler.deleteAdExtensionHistory(paramMap);
            resultMap.put("success", true);
            resultMap.put("code", boolDTO.getCode());
            resultMap.put("message", boolDTO.getMessage());
        } catch (Exception e) {
            logger.error("【controller】删除广告推广历史-deleteAdExtensionHistory is error, paramMap : {}", JSONObject.toJSONString(paramMap), " , e : {}", e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("【controller】删除广告推广历史-deleteAdExtensionHistory,响应-resultMap = {}", JSONObject.toJSONString(resultMap));
        return resultMap;
    }

    /**
     * 修改广告推广历史
     * @param request
     * @return
     */
    @RequestMapping("/updateAdExtensionHistory")
    @ResponseBody
    public Map<String, Object> updateAdExtensionHistory(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("【controller】修改广告推广历史-updateAdExtensionHistory,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        try {
            BoolDTO boolDTO = wxAdExtensionHistoryHandler.updateAdExtensionHistory(paramMap);
            resultMap.put("success", true);
            resultMap.put("code", boolDTO.getCode());
            resultMap.put("message", boolDTO.getMessage());
        } catch (Exception e) {
            logger.error("【controller】修改广告推广历史-updateAdExtensionHistory is error, paramMap : {}", JSONObject.toJSONString(paramMap), " , e : {}", e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("【controller】修改广告推广历史-updateAdExtensionHistory,响应-resultMap = {}", JSONObject.toJSONString(resultMap));
        return resultMap;
    }

    /**
     * 获取单一的广告推广历史
     * @param request
     * @return
     */
    @RequestMapping("/getSimpleAdExtensionHistoryByCondition")
    @ResponseBody
    public Map<String, Object> getSimpleAdExtensionHistoryByCondition(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("【controller】获取单一的广告推广历史-getSimpleAdExtensionHistoryByCondition,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        try {
            ResultDTO resultDTO = wxAdExtensionHistoryHandler.getSimpleAdExtensionHistoryByCondition(paramMap);
            resultMap.put("recordsFiltered", resultDTO.getResultListTotal());
            resultMap.put("data", resultDTO.getResultList());
            resultMap.put("code", resultDTO.getCode());
            resultMap.put("message", resultDTO.getMessage());
        } catch (Exception e) {
            logger.error("【controller】获取单一的广告推广历史-getSimpleAdExtensionHistoryByCondition is error, paramMap : {}", JSONObject.toJSONString(paramMap), " , e : {}", e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("【controller】获取单一的广告推广历史-getSimpleAdExtensionHistoryByCondition,响应-resultMap = {}", JSONObject.toJSONString(resultMap));
        return resultMap;
    }

}

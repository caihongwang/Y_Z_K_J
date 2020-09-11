package com.oilStationMap.controller;

import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.BoolDTO;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.service.WX_AdInfoService;
import com.oilStationMap.utils.HttpUtil;
import com.oilStationMap.utils.MapUtil;
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
@RequestMapping(value = "/wxAdInfo", produces = "application/json;charset=utf-8")
public class WX_AdInfoController {

    private static final Logger logger = LoggerFactory.getLogger(WX_AdInfoController.class);

    @Autowired
    private WX_AdInfoService wxAdInfoService;

    @RequestMapping("/addAdInfo")
    @ResponseBody
    public Map<String, Object> addAdInfo(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中添加广告-addAdInfo,请求-paramMap:" + paramMap);
        try {
            Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
            BoolDTO boolDTO = wxAdInfoService.addAdInfo(objectParamMap);
            resultMap.put("success", true);
            resultMap.put("code", boolDTO.getCode());
            resultMap.put("message", boolDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中添加广告-addAdInfo is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中添加广告-addAdInfo,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/deleteAdInfo")
    @ResponseBody
    public Map<String, Object> deleteAdInfo(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中删除广告-deleteAdInfo,请求-paramMap:" + paramMap);
        try {
            Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
            BoolDTO boolDTO = wxAdInfoService.deleteAdInfo(objectParamMap);
            resultMap.put("success", true);
            resultMap.put("code", boolDTO.getCode());
            resultMap.put("message", boolDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中删除广告-deleteAdInfo is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中删除广告-deleteAdInfo,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/updateAdInfo")
    @ResponseBody
    public Map<String, Object> updateAdInfo(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中修改广告-updateAdInfo,请求-paramMap:" + paramMap);
        try {
            Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
            BoolDTO boolDTO = wxAdInfoService.updateAdInfo(objectParamMap);
            resultMap.put("success", true);
            resultMap.put("code", boolDTO.getCode());
            resultMap.put("message", boolDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中修改广告-updateAdInfo is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中修改广告-updateAdInfo,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/getSimpleAdInfoByCondition")
    @ResponseBody
    public Map<String, Object> getSimpleAdInfoByCondition(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中获取单一的广告-getSimpleAdInfoByCondition,请求-paramMap:" + paramMap);
        try {
            Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
            ResultDTO resultDTO = wxAdInfoService.getSimpleAdInfoByCondition(objectParamMap);
            resultMap.put("recordsFiltered", resultDTO.getResultListTotal());
            resultMap.put("data", resultDTO.getResultList());
            resultMap.put("code", resultDTO.getCode());
            resultMap.put("message", resultDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中修改广告-getSimpleAdInfoByCondition is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中修改广告-getSimpleAdInfoByCondition,响应-response:" + resultMap);
        return resultMap;
    }

}

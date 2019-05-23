package com.oilStationMap.controller;

import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.BoolDTO;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.handler.WX_OilStationHandler;
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
@RequestMapping(value = "/wxOilStation", produces = "application/json;charset=utf-8")
public class WX_OilStationController {

    private static final Logger logger = LoggerFactory.getLogger(WX_OilStationController.class);

    @Autowired
    private WX_OilStationHandler wxOilStationHandler;

    @RequestMapping("/addOrUpdateOilStationAllCountry")
    @ResponseBody
    public Map<String, Object> addOrUpdateOilStationAllCountry(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中定时更新全国加油站信息-addOrUpdateOilStationAllCountry,请求-paramMap:" + paramMap);
        try {
            BoolDTO boolDTO = wxOilStationHandler.addOrUpdateOilStationAllCountry(paramMap);
            resultMap.put("success", true);
            resultMap.put("code", boolDTO.getCode());
            resultMap.put("message", boolDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中定时更新全国加油站信息-addOrUpdateOilStationAllCountry is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中定时更新全国加油站信息-addOrUpdateOilStationAllCountry,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/getOilPriceFromOilUsdCnyCom")
    @ResponseBody
    public Map<String, Object> getOilPriceFromOilUsdCnyCom(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中定时更新全国油价-getOilPriceFromOilUsdCnyCom,请求-paramMap:" + paramMap);
        try {
            BoolDTO boolDTO = wxOilStationHandler.getOilPriceFromOilUsdCnyCom(paramMap);
            resultMap.put("success", true);
            resultMap.put("code", boolDTO.getCode());
            resultMap.put("message", boolDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中定时更新全国油价-getOilPriceFromOilUsdCnyCom is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中定时更新全国油价-getOilPriceFromOilUsdCnyCom,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/addOrUpdateOilStation")
    @ResponseBody
    public Map<String, Object> addOrUpdateOilStation(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中添加或者更新加油站-addOrUpdateOilStation,请求-paramMap:" + paramMap);
        try {
            BoolDTO boolDTO = wxOilStationHandler.addOrUpdateOilStation(paramMap);
            resultMap.put("success", true);
            resultMap.put("code", boolDTO.getCode());
            resultMap.put("message", boolDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中添加或者更新加油站-addOrUpdateOilStation is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中添加或者更新加油站-addOrUpdateOilStation,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/getOilStationList")
    @ResponseBody
    public Map<String, Object> getOilStationList(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中获取加油站列表-getOilStationList,请求-paramMap:" + paramMap);
        try {
            ResultDTO resultDTO = wxOilStationHandler.getOilStationList(paramMap);
            resultMap.put("recordsFiltered", resultDTO.getResultListTotal());
            resultMap.put("data", resultDTO.getResultList());
            resultMap.put("code", resultDTO.getCode());
            resultMap.put("message", resultDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中获取加油站列表-getOilStationList is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中获取加油站列表-getOilStationList,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/getOilStationByLonLat")
    @ResponseBody
    public Map<String, Object> getOilStationByLonLat(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中根据经纬度地址获取所处的加油站-getOilStationByLonLat,请求-paramMap:" + paramMap);
        try {
            ResultDTO resultDTO = wxOilStationHandler.getOilStationByLonLat(paramMap);
            resultMap.put("recordsFiltered", resultDTO.getResultListTotal());
            resultMap.put("data", resultDTO.getResultList());
            resultMap.put("code", resultDTO.getCode());
            resultMap.put("message", resultDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中根据经纬度地址获取所处的加油站-getOilStationByLonLat is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中根据经纬度地址获取所处的加油站-getOilStationByLonLat,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/getOneOilStationByCondition")
    @ResponseBody
    public Map<String, Object> getOneOilStationByCondition(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中获取一个加油站信息-getOneOilStationByCondition,请求-paramMap:" + paramMap);
        try {
            ResultMapDTO resultMapDTO = wxOilStationHandler.getOneOilStationByCondition(paramMap);
            resultMap.put("recordsFiltered", resultMapDTO.getResultListTotal());
            resultMap.put("data", resultMapDTO.getResultMap());
            resultMap.put("code", resultMapDTO.getCode());
            resultMap.put("message", resultMapDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中获取一个加油站信息-getOneOilStationByCondition is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中获取一个加油站信息-getOneOilStationByCondition,响应-response:" + resultMap);
        return resultMap;
    }

}

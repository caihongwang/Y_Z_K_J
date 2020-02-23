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
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/wxOilStation", produces = "application/json;charset=utf-8")
public class WX_OilStationController {

    private static final Logger logger = LoggerFactory.getLogger(WX_OilStationController.class);

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private WX_OilStationHandler wxOilStationHandler;

    @RequestMapping("/setLocaltionByUid")
    @ResponseBody
    public Map<String, Object> setLocaltionByUid(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中根据经纬度设置用户位置中心-setLocaltionByUid,请求-paramMap:" + paramMap);
        try (Jedis jedis = jedisPool.getResource()) {
            // 经度，纬度，用户uid；分别默认：松桃南坪加油站的经纬度和御景西城贵公子的uid
            String newLon = paramMap.get("lon")!=null?paramMap.get("lon").toString():"109.17935";
            String newLat = paramMap.get("lat")!=null?paramMap.get("lat").toString():"28.108028";
            String newUid = paramMap.get("uid")!=null?paramMap.get("uid").toString():"1762";
            jedis.set(OilStationMapCode.CURRENT_LON_UID + newUid, newLon);
            jedis.set(OilStationMapCode.CURRENT_LAT_UID + newUid, newLat);
            logger.info("用户 ----->>> uid = " + newUid + " ； 设置加油站经纬度 ----->>> newLon = " + newLon + " , newLat = " + newLat);
            resultMap.put("code", OilStationMapCode.SUCCESS.getNo());
            resultMap.put("message", OilStationMapCode.SUCCESS.getMessage());
        } catch (Exception e) {
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中根据经纬度设置用户位置中心-setLocaltionByUid,响应-response:" + resultMap);
        return resultMap;
    }

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

    @RequestMapping("/updateOilStationHireInfo")
    @ResponseBody
    public Map<String, Object> updateOilStationHireInfo(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中更新加油站的招聘信息-updateOilStationHireInfo,请求-paramMap:" + paramMap);
        try {
            BoolDTO boolDTO = wxOilStationHandler.updateOilStationHireInfo(paramMap);
            resultMap.put("success", true);
            resultMap.put("code", boolDTO.getCode());
            resultMap.put("message", boolDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中更新加油站的招聘信息-updateOilStationHireInfo is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中更新加油站的招聘信息-updateOilStationHireInfo,响应-response:" + resultMap);
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



    @RequestMapping("/getOilStationListForAdmin")
    @ResponseBody
    public Map<String, Object> getOilStationListForAdmin(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);

        Integer start = paramMap.get("start")!=null?Integer.parseInt(paramMap.get("start")):0;
        Integer size = paramMap.get("size")!=null?Integer.parseInt(paramMap.get("size")):10;
        paramMap.put("start", start.toString());
        paramMap.put("size", size.toString());

        logger.info("在controller中获取加油站列表For管理中心-getOilStationListForAdmin,请求-paramMap:" + paramMap);
        try {
            ResultDTO resultDTO = wxOilStationHandler.getOilStationListForAdmin(paramMap);
            resultMap.put("recordsTotal", resultDTO.getResultListTotal());      // 总记录数
            resultMap.put("recordsFiltered", resultDTO.getResultList().size()); // 过滤后的总记录数
            resultMap.put("data", resultDTO.getResultList());                   // 分页列表
            resultMap.put("code", resultDTO.getCode());
            resultMap.put("message", resultDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中获取加油站列表For管理中心-getOilStationListForAdmin is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中获取加油站列表For管理中心-getOilStationListForAdmin,响应-response:" + resultMap);
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

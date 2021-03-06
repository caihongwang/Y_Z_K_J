package com.oilStationMap.controller;

import com.alibaba.fastjson.JSONObject;
import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.BoolDTO;
import com.oilStationMap.dto.MessageDTO;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.handler.WX_UserHandler;
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
@RequestMapping(value = "/wxUser", produces = "application/json;charset=utf-8")
public class WX_UserController {

    private static final Logger logger = LoggerFactory.getLogger(WX_UserController.class);

    @Autowired
    private WX_UserHandler wxUserHandler;

    @RequestMapping("/login")
    @ResponseBody
    public Map<String, Object> login(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中添加用户-addUser,请求-paramMap:" + paramMap);
        try {
            ResultMapDTO resultMapDTO = wxUserHandler.login(paramMap);
            resultMap.put("success", true);
            resultMap.put("code", resultMapDTO.getCode());
            resultMap.put("message", resultMapDTO.getMessage());
            resultMap.put("data", resultMapDTO.getResultMap());
        } catch (Exception e) {
            logger.error("在controller中添加用户-addUser is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中添加用户-addUser,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/updateUser")
    @ResponseBody
    public Map<String, Object> updateUser(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中更新用户信息-updateUser,请求-paramMap:" + paramMap);
        try {
            BoolDTO boolDTO = wxUserHandler.updateUser(paramMap);
            resultMap.put("success", true);
            resultMap.put("code", boolDTO.getCode());
            resultMap.put("message", boolDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中更新用户信息-updateUser is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中更新用户信息期-updateUser,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/checkSession")
    @ResponseBody
    public Map<String, Object> checkSession(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中检测用户会话是否过期-checkSession,请求-paramMap:" + paramMap);
        try {
            BoolDTO boolDTO = wxUserHandler.checkSession(paramMap);
            resultMap.put("success", true);
            resultMap.put("code", boolDTO.getCode());
            resultMap.put("message", boolDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中检测用户会话是否过期-checkSession is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中检测用户会话是否过期-checkSession,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/getCheckVerificationCode")
    @ResponseBody
    public Map<String, Object> getCheckVerificationCode(HttpServletRequest request) {
        String resultStr = "";
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        int pageSize = 20;//每页多少行
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中校验手机验证码-getCheckVerificationCode,请求-paramMap:" + paramMap);
        try {
            MessageDTO messageDTO = wxUserHandler.getCheckVerificationCode(paramMap);
            resultMap.put("success", true);
            resultMap.put("code", messageDTO.getCode());
            resultMap.put("message", messageDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中校验手机验证码-getCheckVerificationCode is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中校验手机验证码-getCheckVerificationCode,响应-response:" + resultMap);
        return resultMap;
    }

    /**
     * 根据用户uid创建其用户的小程序码
     * @param request
     * @return
     */
    @RequestMapping("/getUserMiniProgramCode")
    @ResponseBody
    public Map<String, Object> getUserMiniProgramCode(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("【controller】根据用户uid创建其用户的小程序码-getUserMiniProgramCode,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        try {
            ResultMapDTO resultMapDTO = wxUserHandler.getUserMiniProgramCode(paramMap);
            resultMap.put("data", resultMapDTO.getResultMap());
            resultMap.put("code", resultMapDTO.getCode());
            resultMap.put("message", resultMapDTO.getMessage());
        } catch (Exception e) {
            logger.error("【controller】根据用户uid创建其用户的小程序码-getUserMiniProgramCode is error, paramMap : {}", JSONObject.toJSONString(paramMap), " , e : {}", e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("【controller】根据用户uid创建其用户的小程序码-getUserMiniProgramCode,响应-resultMap = {}", JSONObject.toJSONString(resultMap));
        return resultMap;
    }

    /**
     * 获取微信的AccessToken
     * @param request
     * @return
     */
    @RequestMapping("/getWxAccessToken")
    @ResponseBody
    public Map<String, Object> getWxAccessToken(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("【controller】获取微信的AccessToken-getWxAccessToken,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        try {
            ResultMapDTO resultMapDTO = wxUserHandler.getWxAccessToken(paramMap);
            resultMap.put("data", resultMapDTO.getResultMap());
            resultMap.put("code", resultMapDTO.getCode());
            resultMap.put("message", resultMapDTO.getMessage());
        } catch (Exception e) {
            logger.error("【controller】获取微信的AccessToken-getWxAccessToken is error, paramMap : {}", JSONObject.toJSONString(paramMap), " , e : {}", e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("【controller】获取微信的AccessToken-getWxAccessToken,响应-resultMap = {}", JSONObject.toJSONString(resultMap));
        return resultMap;
    }

}

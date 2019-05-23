package com.oilStationMap.handler;

import com.alibaba.fastjson.JSONObject;
import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.BoolDTO;
import com.oilStationMap.dto.MessageDTO;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.service.WX_UserService;
import com.oilStationMap.utils.MapUtil;
import com.google.common.collect.Lists;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 用户Handler
 */
@Component
public class WX_UserHandler {

    private static final Logger logger = LoggerFactory.getLogger(WX_UserHandler.class);

    @Autowired
    private WX_UserService wxUserService;

    /**
     * 更新用户信息
     * @param paramMap
     * @return
     */
    public BoolDTO updateUser(Map<String, String> paramMap) {
        logger.info("在hanlder中更新用户信息-updateUser,请求-paramMap:" + paramMap);
        BoolDTO boolDTO = new BoolDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        try {
            boolDTO = wxUserService.updateUser(objectParamMap);
        } catch (Exception e) {
            boolDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
            boolDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
            logger.error("在hanlder中更新用户信息-updateUser is error, paramMap : " + paramMap + ", e : " + e);
        }
        logger.info("在hanlder中更新用户信息-updateUser,响应-response:" + boolDTO);
        return boolDTO;
    }

    /**
     * 添加用户：第一次获得微信授权，则使用openId创建用户
     * @param paramMap
     * @return
     */
    public ResultMapDTO login(Map<String, String> paramMap) {
        logger.info("在hanlder中添加用户-login,请求-paramMap:" + paramMap);
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        try {
            resultMapDTO = wxUserService.login(objectParamMap);
        } catch (Exception e) {
            resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
            logger.error("在hanlder中添加用户-login is error, paramMap : " + paramMap + ", e : " + e);
        }
        logger.info("在hanlder中添加用户-login,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }


    public BoolDTO checkSession(Map<String, String> paramMap) {
        logger.info("在hanlder中检测用户会话是否过期-checkSession,请求-paramMap:" + paramMap);
        BoolDTO boolDTO = new BoolDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        try {
            boolDTO = wxUserService.checkSession(objectParamMap);
        } catch (Exception e) {
            boolDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
            boolDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
            logger.error("在hanlder中检测用户会话是否过期-checkSession is error, paramMap : " + paramMap + ", e : " + e);
        }
        logger.info("在hanlder中检测用户会话是否过期-checkSession,响应-response:" + boolDTO);
        return boolDTO;
    }


    public ResultDTO getSimpleUserByCondition(Map<String, String> paramMap) {
        logger.info("在hanlder中获取单一的用户信息-getSimpleUserByCondition,请求-paramMap:" + paramMap);
        ResultDTO resultDTO = new ResultDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultDTO = wxUserService.getSimpleUserByCondition(objectParamMap);
            } catch (Exception e) {
                List<Map<String, String>> resultList = Lists.newArrayList();
                resultDTO.setResultListTotal(0);
                resultDTO.setResultList(resultList);
                resultDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                logger.error("在hanlder中获取单一的字典-getSimpleDicByCondition is error, paramMap : " + paramMap + ", e : " + e);
            }
        } else {
            resultDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在hanlder中获取单一的用户信息-getSimpleUserByCondition,响应-response:" + resultDTO);
        return resultDTO;
    }

    /**
     * 根据手机号校验验证码是否正确,并将这个手机号的创建用户
     * @param paramMap
     * @return
     */
    public MessageDTO getCheckVerificationCode(Map<String, String> paramMap) {
        logger.info("在hanlder中校验手机验证码-getCheckVerificationCode,请求-paramMap:" + paramMap);
        MessageDTO messageDTO = new MessageDTO();
        if (paramMap.size() > 0) {
            Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
            try {
                messageDTO = wxUserService.getCheckVerificationCode(objectParamMap);
            } catch (Exception e) {
                messageDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                messageDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                logger.error("在hanlder中校验手机验证码-getCheckVerificationCode is error, paramMap : " + paramMap + ", e : " + e);
            }
        } else {
            messageDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            messageDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在hanlder中获取校验手机验证码-getCheckVerificationCode,响应-response:" + messageDTO);
        return messageDTO;
    }

    /**
     * 根据用户uid创建其用户的小程序码
     * @param paramMap
     * @return
     */
    public ResultMapDTO getUserMiniProgramCode(Map<String, String> paramMap) {
        logger.info("【hanlder】根据用户uid创建其用户的小程序码-getUserMiniProgramCode,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultMapDTO = wxUserService.getUserMiniProgramCode(objectParamMap);
            } catch (Exception e) {
                logger.error("【hanlder】根据用户uid创建其用户的小程序码-getUserMiniProgramCode is error, paramMap : {}", JSONObject.toJSONString(paramMap), " , e : {}", e);
                resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
            }
        } else {
            resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("【hanlder】根据用户uid创建其用户的小程序码-getUserMiniProgramCode,响应-resultMapDTO = {}", JSONObject.toJSONString(resultMapDTO));
        return resultMapDTO;
    }

}

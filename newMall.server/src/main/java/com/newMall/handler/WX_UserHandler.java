package com.newMall.handler;

import com.alibaba.fastjson.JSONObject;
import com.newMall.code.NewMallCode;
import com.newMall.dto.BoolDTO;
import com.newMall.dto.MessageDTO;
import com.newMall.dto.ResultDTO;
import com.newMall.dto.ResultMapDTO;
import com.newMall.service.WX_UserService;
import com.newMall.utils.MapUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Description 用户Handler
 * @author caihongwang
 */
@Component
public class WX_UserHandler {

    private static final Logger logger = LoggerFactory.getLogger(com.newMall.handler.WX_UserHandler.class);

    @Autowired
    private WX_UserService userService;

    /**
     * 获取用户的基本信息
     * @param tid
     * @param paramMap
     * @return
     * @throws
     */
    public ResultMapDTO getUserBaseInfo(int tid, Map<String, String> paramMap) {
        logger.info("【hanlder】获取用户的基本信息-getUserBaseInfo,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        try {
            resultMapDTO = userService.getUserBaseInfo(objectParamMap);
        } catch (Exception e) {
            resultMapDTO.setCode(NewMallCode.SERVER_INNER_ERROR.getNo());
            resultMapDTO.setMessage(NewMallCode.SERVER_INNER_ERROR.getMessage());
            logger.error("【hanlder】获取用户的基本信息-getUserBaseInfo is error, paramMap : {}", JSONObject.toJSONString(paramMap), " , e : {}", e);
        }
        logger.info("【hanlder】获取用户的基本信息-getUserBaseInfo,响应-resultMapDTO = {}", JSONObject.toJSONString(resultMapDTO));
        return resultMapDTO;
    }

    /**
     * 登录(首次微信授权，则使用openId创建用户)
     * @param tid
     * @param paramMap
     * @return
     * @throws
     */
    public ResultMapDTO login(int tid, Map<String, String> paramMap) {
        logger.info("【hanlder】登录(首次微信授权，则使用openId创建用户)-login,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        try {
            resultMapDTO = userService.login(objectParamMap);
        } catch (Exception e) {
            resultMapDTO.setCode(NewMallCode.SERVER_INNER_ERROR.getNo());
            resultMapDTO.setMessage(NewMallCode.SERVER_INNER_ERROR.getMessage());
            logger.error("【hanlder】登录(首次微信授权，则使用openId创建用户)-login is error, paramMap : {}", JSONObject.toJSONString(paramMap), " , e : {}", e);
        }
        logger.info("【hanlder】登录(首次微信授权，则使用openId创建用户)-login,响应-resultMapDTO = {}", JSONObject.toJSONString(resultMapDTO));
        return resultMapDTO;
    }

    /**
     * 更新用户信息
     * @param tid
     * @param paramMap
     * @return
     * @throws
     */
    public BoolDTO updateUser(int tid, Map<String, String> paramMap) {
        logger.info("【hanlder】更新用户信息-updateUser,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        BoolDTO boolDTO = new BoolDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        try {
            boolDTO = userService.updateUser(objectParamMap);
        } catch (Exception e) {
            boolDTO.setCode(NewMallCode.SERVER_INNER_ERROR.getNo());
            boolDTO.setMessage(NewMallCode.SERVER_INNER_ERROR.getMessage());
            logger.error("【hanlder】更新用户信息-updateUser is error, paramMap : {}", JSONObject.toJSONString(paramMap), " , e : {}", e);
        }
        logger.info("【hanlder】更新用户信息-updateUser,响应-boolDTO = {}", JSONObject.toJSONString(boolDTO));
        return boolDTO;
    }

    /**
     * 检测用户会话是否过期
     * @param tid
     * @param paramMap
     * @return
     * @throws
     */
    public BoolDTO checkSession(int tid, Map<String, String> paramMap) {
        logger.info("【hanlder】检测用户会话是否过期-checkSession,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        BoolDTO boolDTO = new BoolDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        try {
            boolDTO = userService.checkSession(objectParamMap);
        } catch (Exception e) {
            logger.error("【hanlder】检测用户会话是否过期-checkSession is error, paramMap : {}", JSONObject.toJSONString(paramMap), " , e : {}", e);
            boolDTO.setCode(NewMallCode.SERVER_INNER_ERROR.getNo());
            boolDTO.setMessage(NewMallCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("【hanlder】检测用户会话是否过期-checkSession,响应-boolDTO = {}", JSONObject.toJSONString(boolDTO));
        return boolDTO;
    }

    /**
     * 校验手机验证码-[暂时未使用]
     */
    public MessageDTO getCheckVerificationCode(int tid, Map<String, String> paramMap) {
        logger.info("【hanlder】校验手机验证码-getCheckVerificationCode,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        MessageDTO messageDTO = new MessageDTO();
        if (paramMap.size() > 0) {
            Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
            try {
                messageDTO = userService.getCheckVerificationCode(objectParamMap);
            } catch (Exception e) {
                logger.error("【hanlder】校验手机验证码-getCheckVerificationCode is error, paramMap : {}", JSONObject.toJSONString(paramMap), " , e : {}", e);
                messageDTO.setCode(NewMallCode.SERVER_INNER_ERROR.getNo());
                messageDTO.setMessage(NewMallCode.SERVER_INNER_ERROR.getMessage());
            }
        } else {
            messageDTO.setCode(NewMallCode.PARAM_IS_NULL.getNo());
            messageDTO.setMessage(NewMallCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("【hanlder】获取校验手机验证码-getCheckVerificationCode,响应-messageDTO = {}", JSONObject.toJSONString(messageDTO));
        return messageDTO;
    }

    /**
     * 获取单一的用户信息
     * @param tid
     * @param paramMap
     * @return
     * @throws
     */
    public ResultDTO getSimpleUserByCondition(int tid, Map<String, String> paramMap) {
        logger.info("【hanlder】获取单一的用户信息-getSimpleUserByCondition,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        ResultDTO resultDTO = new ResultDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultDTO = userService.getSimpleUserByCondition(objectParamMap);
            } catch (Exception e) {
                List<Map<String, String>> resultList = Lists.newArrayList();
                resultDTO.setResultListTotal(0);
                resultDTO.setResultList(resultList);
                resultDTO.setCode(NewMallCode.SERVER_INNER_ERROR.getNo());
                resultDTO.setMessage(NewMallCode.SERVER_INNER_ERROR.getMessage());
                logger.error("【hanlder】获取单一的字典-getSimpleDicByCondition is error, paramMap : {}", JSONObject.toJSONString(paramMap), " , e : {}", e);
            }
        } else {
            resultDTO.setCode(NewMallCode.PARAM_IS_NULL.getNo());
            resultDTO.setMessage(NewMallCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("【hanlder】获取单一的用户信息-getSimpleUserByCondition,响应-resultDTO = {}", JSONObject.toJSONString(resultDTO));
        return resultDTO;
    }

    /**
     * 设置用户余额是否自动提现
     * @param tid
     * @param paramMap
     * @return
     * @throws
     */
    public BoolDTO checkUserAutoCashBalance(int tid, Map<String, String> paramMap) {
        logger.info("【hanlder】设置用户余额是否自动提现-checkUserAutoCashBalance,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        BoolDTO boolDTO = new BoolDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        try {
            boolDTO = userService.checkUserAutoCashBalance(objectParamMap);
        } catch (Exception e) {
            boolDTO.setCode(NewMallCode.SERVER_INNER_ERROR.getNo());
            boolDTO.setMessage(NewMallCode.SERVER_INNER_ERROR.getMessage());
            logger.error("【hanlder】设置用户余额是否自动提现-checkUserAutoCashBalance is error, paramMap : {}", JSONObject.toJSONString(paramMap), " , e : {}", e);
        }
        logger.info("【hanlder】设置用户余额是否自动提现-checkUserAutoCashBalance,响应-boolDTO = {}", JSONObject.toJSONString(boolDTO));
        return boolDTO;
    }
}

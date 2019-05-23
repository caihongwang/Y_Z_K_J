package com.newMall.handler;

import com.alibaba.fastjson.JSONObject;
import com.newMall.code.NewMallCode;
import com.newMall.dto.BoolDTO;
import com.newMall.dto.ResultDTO;
import com.newMall.service.WX_BalanceLogService;
import com.newMall.utils.MapUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Description 余额日志Handler
 * @author caihongwang
 */
@Component
public class WX_BalanceLogHandler {

    private static final Logger logger = LoggerFactory.getLogger(WX_BalanceLogHandler.class);

    @Autowired
    private WX_BalanceLogService wxBalanceLogService;

    /**
     * 添加余额日志
     * @param tid
     * @param paramMap
     * @return
     * @throws
     */
    public BoolDTO addBalanceLog(int tid, Map<String, String> paramMap) {
        logger.info("【handler】添加余额日志-addBalanceLog,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        BoolDTO boolDTO = new BoolDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                boolDTO = wxBalanceLogService.addBalanceLog(objectParamMap);
            } catch (Exception e) {
                logger.error("【handler】添加余额日志-addBalanceLog is error, paramMap : {}", JSONObject.toJSONString(paramMap), " , e : {}", e);
                boolDTO.setCode(NewMallCode.SERVER_INNER_ERROR.getNo());
                boolDTO.setMessage(NewMallCode.SERVER_INNER_ERROR.getMessage());
            }
        } else {
            boolDTO.setCode(NewMallCode.PARAM_IS_NULL.getNo());
            boolDTO.setMessage(NewMallCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("【handler】添加余额日志-addBalanceLog,响应-boolDTO = {}", JSONObject.toJSONString(boolDTO));
        return boolDTO;
    }

    /**
     * 删除余额日志
     * @param tid
     * @param paramMap
     * @return
     * @throws
     */
    public BoolDTO deleteBalanceLog(int tid, Map<String, String> paramMap) {
        logger.info("【handler】删除余额日志-deleteBalanceLog,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        BoolDTO boolDTO = new BoolDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        try {
            boolDTO = wxBalanceLogService.deleteBalanceLog(objectParamMap);
        } catch (Exception e) {
            logger.error("【handler】删除余额日志-deleteBalanceLog is error, paramMap : {}", JSONObject.toJSONString(paramMap), " , e : {}", e);
            boolDTO.setCode(NewMallCode.SERVER_INNER_ERROR.getNo());
            boolDTO.setMessage(NewMallCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("【handler】删除余额日志-deleteBalanceLog,响应-boolDTO = {}", JSONObject.toJSONString(boolDTO));
        return boolDTO;
    }

    /**
     * 修改余额日志
     * @param tid
     * @param paramMap
     * @return
     * @throws
     */
    public BoolDTO updateBalanceLog(int tid, Map<String, String> paramMap) {
        logger.info("【handler】修改余额日志-updateBalanceLog,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        BoolDTO boolDTO = new BoolDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        try {
            boolDTO = wxBalanceLogService.updateBalanceLog(objectParamMap);
        } catch (Exception e) {
            logger.error("【handler】修改余额日志-updateBalanceLog is error, paramMap : {}", JSONObject.toJSONString(paramMap), " , e : {}", e);
            boolDTO.setCode(NewMallCode.SERVER_INNER_ERROR.getNo());
            boolDTO.setMessage(NewMallCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("【handler】修改余额日志-updateBalanceLog,响应-boolDTO = {}", JSONObject.toJSONString(boolDTO));
        return boolDTO;
    }

    /**
     * 获取单一的余额日志
     * @param tid
     * @param paramMap
     * @return
     * @throws
     */
    public ResultDTO getSimpleBalanceLogByCondition(int tid, Map<String, String> paramMap) {
        logger.info("【hanlder】获取单一的余额日志-getSimpleBalanceLogByCondition,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        ResultDTO resultDTO = new ResultDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultDTO = wxBalanceLogService.getSimpleBalanceLogByCondition(objectParamMap);
            } catch (Exception e) {
                logger.error("【hanlder】获取单一的余额日志-getSimpleBalanceLogByCondition is error, paramMap : {}", JSONObject.toJSONString(paramMap), " , e : {}", e);
                List<Map<String, String>> resultList = Lists.newArrayList();
                resultDTO.setResultListTotal(0);
                resultDTO.setResultList(resultList);
                resultDTO.setCode(NewMallCode.SERVER_INNER_ERROR.getNo());
                resultDTO.setMessage(NewMallCode.SERVER_INNER_ERROR.getMessage());
            }
        } else {
            resultDTO.setCode(NewMallCode.PARAM_IS_NULL.getNo());
            resultDTO.setMessage(NewMallCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("【hanlder】获取单一的余额日志-getSimpleBalanceLogByCondition,响应-resultDTO = {}", JSONObject.toJSONString(resultDTO));
        return resultDTO;
    }

}

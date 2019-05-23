package com.newMall.handler;

import com.alibaba.fastjson.JSONObject;
import com.newMall.code.NewMallCode;
import com.newMall.dto.BoolDTO;
import com.newMall.dto.ResultDTO;
import com.newMall.service.WX_IntegralLogService;
import com.newMall.utils.MapUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Description 积分日志Handler
 * @author caihongwang
 */
@Component
public class WX_IntegralLogHandler {

    private static final Logger logger = LoggerFactory.getLogger(WX_IntegralLogHandler.class);

    @Autowired
    private WX_IntegralLogService wxIntegralLogService;

    /**
     * 添加积分日志
     * @param tid
     * @param paramMap
     * @return
     * @throws
     */
    public BoolDTO addIntegralLog(int tid, Map<String, String> paramMap) {
        logger.info("【handler】添加积分日志-addIntegralLog,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        BoolDTO boolDTO = new BoolDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                boolDTO = wxIntegralLogService.addIntegralLog(objectParamMap);
            } catch (Exception e) {
                logger.error("【handler】添加积分日志-addIntegralLog is error, paramMap : {}", JSONObject.toJSONString(paramMap), " , e : {}", e);
                boolDTO.setCode(NewMallCode.SERVER_INNER_ERROR.getNo());
                boolDTO.setMessage(NewMallCode.SERVER_INNER_ERROR.getMessage());
            }
        } else {
            boolDTO.setCode(NewMallCode.PARAM_IS_NULL.getNo());
            boolDTO.setMessage(NewMallCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("【handler】添加积分日志-addIntegralLog,响应-boolDTO = {}", JSONObject.toJSONString(boolDTO));
        return boolDTO;
    }

    /**
     * 删除积分日志
     * @param tid
     * @param paramMap
     * @return
     * @throws
     */
    public BoolDTO deleteIntegralLog(int tid, Map<String, String> paramMap) {
        logger.info("【handler】删除积分日志-deleteIntegralLog,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        BoolDTO boolDTO = new BoolDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        try {
            boolDTO = wxIntegralLogService.deleteIntegralLog(objectParamMap);
        } catch (Exception e) {
            logger.error("【handler】删除积分日志-deleteIntegralLog is error, paramMap : {}", JSONObject.toJSONString(paramMap), " , e : {}", e);
            boolDTO.setCode(NewMallCode.SERVER_INNER_ERROR.getNo());
            boolDTO.setMessage(NewMallCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("【handler】删除积分日志-deleteIntegralLog,响应-boolDTO = {}", JSONObject.toJSONString(boolDTO));
        return boolDTO;
    }

    /**
     * 修改积分日志
     * @param tid
     * @param paramMap
     * @return
     * @throws
     */
    public BoolDTO updateIntegralLog(int tid, Map<String, String> paramMap) {
        logger.info("【handler】修改积分日志-updateIntegralLog,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        BoolDTO boolDTO = new BoolDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        try {
            boolDTO = wxIntegralLogService.updateIntegralLog(objectParamMap);
        } catch (Exception e) {
            logger.error("【handler】修改积分日志-updateIntegralLog is error, paramMap : {}", JSONObject.toJSONString(paramMap), " , e : {}", e);
            boolDTO.setCode(NewMallCode.SERVER_INNER_ERROR.getNo());
            boolDTO.setMessage(NewMallCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("【handler】修改积分日志-updateIntegralLog,响应-boolDTO = {}", JSONObject.toJSONString(boolDTO));
        return boolDTO;
    }

    /**
     * 获取单一的积分日志
     * @param tid
     * @param paramMap
     * @return
     * @throws
     */
    public ResultDTO getSimpleIntegralLogByCondition(int tid, Map<String, String> paramMap) {
        logger.info("【hanlder】获取单一的积分日志-getSimpleIntegralLogByCondition,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        ResultDTO resultDTO = new ResultDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultDTO = wxIntegralLogService.getSimpleIntegralLogByCondition(objectParamMap);
            } catch (Exception e) {
                logger.error("【hanlder】获取单一的积分日志-getSimpleIntegralLogByCondition is error, paramMap : {}", JSONObject.toJSONString(paramMap), " , e : {}", e);
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
        logger.info("【hanlder】获取单一的积分日志-getSimpleIntegralLogByCondition,响应-resultDTO = {}", JSONObject.toJSONString(resultDTO));
        return resultDTO;
    }

}

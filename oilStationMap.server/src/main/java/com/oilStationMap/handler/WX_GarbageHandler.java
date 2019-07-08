package com.oilStationMap.handler;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.service.WX_GarbageService;
import com.oilStationMap.utils.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Description 垃圾Handler
 * @author caihongwang
 */
@Component
public class WX_GarbageHandler {

    private static final Logger logger = LoggerFactory.getLogger(WX_GarbageHandler.class);

    @Autowired
    private WX_GarbageService wxGarbageService;

    /**
     * 获取垃圾类型列表
     * @param paramMap
     * @return
     */
    public ResultDTO getGarbageList(Map<String, String> paramMap) {
        logger.info("【hanlder】获取垃圾类型列表-getGarbageList,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        ResultDTO resultDTO = new ResultDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultDTO = wxGarbageService.getGarbageList(objectParamMap);
            } catch (Exception e) {
                logger.error("【hanlder】获取垃圾类型列表-getGarbageList is error, paramMap : {}", JSONObject.toJSONString(paramMap), " , e : {}", e);
                List<Map<String, String>> resultList = Lists.newArrayList();
                resultDTO.setResultListTotal(0);
                resultDTO.setResultList(resultList);
                resultDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
            }
        } else {
            resultDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("【hanlder】获取垃圾类型列表-getGarbageList,响应-resultDTO = {}", JSONObject.toJSONString(resultDTO));
        return resultDTO;
    }

    /**
     * 获取单一的垃圾
     * @param paramMap
     * @return
     */
    public ResultDTO getSimpleGarbageByCondition(Map<String, String> paramMap) {
        logger.info("【hanlder】获取单一的垃圾-getSimpleGarbageByCondition,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        ResultDTO resultDTO = new ResultDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultDTO = wxGarbageService.getSimpleGarbageByCondition(objectParamMap);
            } catch (Exception e) {
                logger.error("【hanlder】获取单一的垃圾-getSimpleGarbageByCondition is error, paramMap : {}", JSONObject.toJSONString(paramMap), " , e : {}", e);
                List<Map<String, String>> resultList = Lists.newArrayList();
                resultDTO.setResultListTotal(0);
                resultDTO.setResultList(resultList);
                resultDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
            }
        } else {
            resultDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("【hanlder】获取单一的垃圾-getSimpleGarbageByCondition,响应-resultDTO = {}", JSONObject.toJSONString(resultDTO));
        return resultDTO;
    }
}

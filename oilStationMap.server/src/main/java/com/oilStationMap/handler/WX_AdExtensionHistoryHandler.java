package com.oilStationMap.handler;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.BoolDTO;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.service.WX_AdExtensionHistoryService;
import com.oilStationMap.utils.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Description 广告推广历史Handler
 * @author caihongwang
 */
@Component
public class WX_AdExtensionHistoryHandler {

    private static final Logger logger = LoggerFactory.getLogger(WX_AdExtensionHistoryHandler.class);

    @Autowired
    private WX_AdExtensionHistoryService wxAdExtensionHistoryService;

    /**
     * 添加广告推广历史
     * @param paramMap
     * @return
     */
    public BoolDTO addAdExtensionHistory(Map<String, String> paramMap) {
        logger.info("【handler】添加广告推广历史-addAdExtensionHistory,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        BoolDTO boolDTO = new BoolDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                boolDTO = wxAdExtensionHistoryService.addAdExtensionHistory(objectParamMap);
            } catch (Exception e) {
                logger.error("【handler】添加广告推广历史-addAdExtensionHistory is error, paramMap : {}", JSONObject.toJSONString(paramMap), " , e : {}", e);
                boolDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                boolDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
            }
        } else {
            boolDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            boolDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("【handler】添加广告推广历史-addAdExtensionHistory,响应-boolDTO = {}", JSONObject.toJSONString(boolDTO));
        return boolDTO;
    }

    /**
     * 删除广告推广历史
     * @param paramMap
     * @return
     */
    public BoolDTO deleteAdExtensionHistory(Map<String, String> paramMap) {
        logger.info("【handler】删除广告推广历史-deleteAdExtensionHistory,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        BoolDTO boolDTO = new BoolDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        try {
            boolDTO = wxAdExtensionHistoryService.deleteAdExtensionHistory(objectParamMap);
        } catch (Exception e) {
            logger.error("【handler】删除广告推广历史-deleteAdExtensionHistory is error, paramMap : {}", JSONObject.toJSONString(paramMap), " , e : {}", e);
            boolDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
            boolDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("【handler】删除广告推广历史-deleteAdExtensionHistory,响应-boolDTO = {}", JSONObject.toJSONString(boolDTO));
        return boolDTO;
    }

    /**
     * 修改广告推广历史
     * @param paramMap
     * @return
     */
    public BoolDTO updateAdExtensionHistory(Map<String, String> paramMap) {
        logger.info("【handler】修改广告推广历史-updateAdExtensionHistory,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        BoolDTO boolDTO = new BoolDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        try {
            boolDTO = wxAdExtensionHistoryService.updateAdExtensionHistory(objectParamMap);
        } catch (Exception e) {
            logger.error("【handler】修改广告推广历史-updateAdExtensionHistory is error, paramMap : {}", JSONObject.toJSONString(paramMap), " , e : {}", e);
            boolDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
            boolDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("【handler】修改广告推广历史-updateAdExtensionHistory,响应-boolDTO = {}", JSONObject.toJSONString(boolDTO));
        return boolDTO;
    }

    /**
     * 获取单一的广告推广历史
     * @param paramMap
     * @return
     */
    public ResultDTO getSimpleAdExtensionHistoryByCondition(Map<String, String> paramMap) {
        logger.info("【hanlder】获取单一的广告推广历史-getSimpleAdExtensionHistoryByCondition,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        ResultDTO resultDTO = new ResultDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultDTO = wxAdExtensionHistoryService.getSimpleAdExtensionHistoryByCondition(objectParamMap);
            } catch (Exception e) {
                logger.error("【hanlder】获取单一的广告推广历史-getSimpleAdExtensionHistoryByCondition is error, paramMap : {}", JSONObject.toJSONString(paramMap), " , e : {}", e);
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
        logger.info("【hanlder】获取单一的广告推广历史-getSimpleAdExtensionHistoryByCondition,响应-resultDTO = {}", JSONObject.toJSONString(resultDTO));
        return resultDTO;
    }
}

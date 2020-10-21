package com.oilStationMap.handler;

import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.BoolDTO;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.service.WX_DicService;
import com.oilStationMap.utils.MapUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 字典Handler
 */
@Component
public class WX_DicHandler {

    private static final Logger logger = LoggerFactory.getLogger(WX_DicHandler.class);

    @Autowired
    private WX_DicService wxDicService;


    public BoolDTO addDic(Map<String, String> paramMap) {
        logger.info("在hanlder中添加字典-addDic,请求-paramMap:" + paramMap);
        BoolDTO boolDTO = new BoolDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                boolDTO = wxDicService.addDic(objectParamMap);
            } catch (Exception e) {
                boolDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                boolDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                logger.error("在hanlder中添加字典-addDic is error, paramMap : " + paramMap + ", e : " + e);
            }
        } else {
            boolDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            boolDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在hanlder中添加字典-addDic,响应-response:" + boolDTO);
        return boolDTO;
    }


    public BoolDTO deleteDic(Map<String, String> paramMap) {
        logger.info("在hanlder中删除字典-deleteDic,请求-paramMap:" + paramMap);
        BoolDTO boolDTO = new BoolDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        try {
            boolDTO = wxDicService.deleteDic(objectParamMap);
        } catch (Exception e) {
            boolDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
            boolDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
            logger.error("在hanlder中删除字典-deleteDic is error, paramMap : " + paramMap + ", e : " + e);
        }
        logger.info("在hanlder中删除字典-deleteDic,响应-response:" + boolDTO);
        return boolDTO;
    }


    public BoolDTO updateDic(Map<String, String> paramMap) {
        logger.info("在hanlder中修改字典-updateDic,请求-paramMap:" + paramMap);
        BoolDTO boolDTO = new BoolDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        try {
            boolDTO = wxDicService.updateDic(objectParamMap);
        } catch (Exception e) {
            boolDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
            boolDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
            logger.error("在hanlder中修改字典-updateDic is error, paramMap : " + paramMap + ", e : " + e);
        }
        logger.info("在hanlder中修改字典-updateDic,响应-response:" + boolDTO);
        return boolDTO;
    }


    public ResultDTO getSimpleDicByCondition(Map<String, String> paramMap) {
        logger.info("在hanlder中获取单一的字典-getSimpleDicByCondition,请求-paramMap:" + paramMap);
        ResultDTO resultDTO = new ResultDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultDTO = wxDicService.getSimpleDicByCondition(objectParamMap);
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
        logger.info("在hanlder中获取单一的字典-getSimpleDicByCondition,响应-response:" + resultDTO);
        return resultDTO;
    }


    public ResultMapDTO getMoreDicByCondition(Map<String, String> paramMap) {
        logger.info("在hanlder中获取多个的字典-getMoreDicByCondition,请求-paramMap:" + paramMap);
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultMapDTO = wxDicService.getMoreDicByCondition(objectParamMap);
            } catch (Exception e) {
                Map<String, String> resultMap = Maps.newHashMap();
                resultMapDTO.setResultListTotal(0);
                resultMapDTO.setResultMap(resultMap);

                resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                logger.error("在hanlder中获取多个的字典-getMoreDicByCondition is error, paramMap : " + paramMap + ", e : " + e);
            }
        } else {

            resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在hanlder中获取多个的字典-getMoreDicByCondition,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

    public ResultDTO getDicListByConditionForAdmin(Map<String, String> paramMap) {
        logger.info("在【hanlder】获取单一的字典列表For管理中心-getDicListByConditionForAdmin,请求-paramMap:" + paramMap);
        ResultDTO resultDTO = new ResultDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultDTO = wxDicService.getDicListByConditionForAdmin(objectParamMap);
            } catch (Exception e) {
                List<Map<String, String>> resultList = Lists.newArrayList();
                resultDTO.setResultListTotal(0);
                resultDTO.setResultList(resultList);
                resultDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                logger.error("在【hanlder】获取单一的字典列表For管理中心-getDicListByConditionForAdmin is error, paramMap : " + paramMap + ", e : " + e);
            }
        } else {
            resultDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在【hanlder】获取单一的字典列表For管理中心-getDicListByConditionForAdmin,响应-response:" + resultDTO);
        return resultDTO;
    }

}

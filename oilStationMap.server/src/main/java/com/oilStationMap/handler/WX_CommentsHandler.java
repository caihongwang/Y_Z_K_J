package com.oilStationMap.handler;

import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.BoolDTO;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.service.WX_CommentsService;
import com.oilStationMap.utils.MapUtil;
import com.google.common.collect.Lists;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 意见Handler
 */
@Component
public class WX_CommentsHandler {

    private static final Logger logger = LoggerFactory.getLogger(WX_CommentsHandler.class);

    @Autowired
    private WX_CommentsService wxCommentsService;


    public BoolDTO addComments(Map<String, String> paramMap) {
        logger.info("在hanlder中添加意见-addComments,请求-paramMap:" + paramMap);
        BoolDTO boolDTO = new BoolDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        try {
            boolDTO = wxCommentsService.addComments(objectParamMap);
        } catch (Exception e) {
            boolDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
            boolDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
            logger.error("在hanlder中添加意见-addComments is error, paramMap : " + paramMap + ", e : " + e);
        }
        logger.info("在hanlder中添加意见-addComments,响应-response:" + boolDTO);
        return boolDTO;
    }


    public BoolDTO deleteComments(Map<String, String> paramMap) {
        logger.info("在hanlder中删除意见-deleteComments,请求-paramMap:" + paramMap);
        BoolDTO boolDTO = new BoolDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        try {
            boolDTO = wxCommentsService.deleteComments(objectParamMap);
        } catch (Exception e) {
            boolDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
            boolDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
            logger.error("在hanlder中删除意见-deleteComments is error, paramMap : " + paramMap + ", e : " + e);
        }
        logger.info("在hanlder中删除意见-deleteComments,响应-response:" + boolDTO);
        return boolDTO;
    }


    public BoolDTO updateComments(Map<String, String> paramMap) {
        logger.info("在hanlder中修改意见-updateComments,请求-paramMap:" + paramMap);
        BoolDTO boolDTO = new BoolDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        try {
            boolDTO = wxCommentsService.updateComments(objectParamMap);
        } catch (Exception e) {
            boolDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
            boolDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
            logger.error("在hanlder中修改意见-updateComments is error, paramMap : " + paramMap + ", e : " + e);
        }
        logger.info("在hanlder中修改意见-updateComments,响应-response:" + boolDTO);
        return boolDTO;
    }


    public ResultDTO getSimpleCommentsByCondition(Map<String, String> paramMap) {
        logger.info("在hanlder中获取单一的意见-getSimpleCommentsByCondition,请求-paramMap:" + paramMap);
        ResultDTO resultDTO = new ResultDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        try {
            resultDTO = wxCommentsService.getSimpleCommentsByCondition(objectParamMap);
        } catch (Exception e) {
            List<Map<String, String>> resultList = Lists.newArrayList();
            resultDTO.setResultListTotal(0);
            resultDTO.setResultList(resultList);
            resultDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
            logger.error("在hanlder中获取单一的意见-getSimpleCommentsByCondition is error, paramMap : " + paramMap + ", e : " + e);
        }
        logger.info("在hanlder中获取单一的意见-getSimpleCommentsByCondition,响应-response:" + resultDTO);
        return resultDTO;
    }

}

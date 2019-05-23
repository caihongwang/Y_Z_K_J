package com.newMall.handler;

import com.newMall.code.NewMallCode;
import com.newMall.dto.BoolDTO;
import com.newMall.dto.ResultDTO;
import com.newMall.service.CommentsService;
import com.newMall.utils.MapUtil;
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
public class CommentsHandler {

    private static final Logger logger = LoggerFactory.getLogger(CommentsHandler.class);

    @Autowired
    private CommentsService commentsService;

    public BoolDTO addComments(int tid, Map<String, String> paramMap) {
        logger.info("在hanlder中添加意见-addComments,请求-paramMap:" + paramMap);
        BoolDTO boolDTO = new BoolDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        try {
            boolDTO = commentsService.addComments(objectParamMap);
        } catch (Exception e) {
            boolDTO.setCode(NewMallCode.SERVER_INNER_ERROR.getNo());
            boolDTO.setMessage(NewMallCode.SERVER_INNER_ERROR.getMessage());
            logger.error("在hanlder中添加意见-addComments is error, paramMap : " + paramMap + ", e : " + e);
        }
        logger.info("在hanlder中添加意见-addComments,响应-response:" + boolDTO);
        return boolDTO;
    }

    public BoolDTO deleteComments(int tid, Map<String, String> paramMap) {
        logger.info("在hanlder中删除意见-deleteComments,请求-paramMap:" + paramMap);
        BoolDTO boolDTO = new BoolDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        try {
            boolDTO = commentsService.deleteComments(objectParamMap);
        } catch (Exception e) {
            boolDTO.setCode(NewMallCode.SERVER_INNER_ERROR.getNo());
            boolDTO.setMessage(NewMallCode.SERVER_INNER_ERROR.getMessage());
            logger.error("在hanlder中删除意见-deleteComments is error, paramMap : " + paramMap + ", e : " + e);
        }
        logger.info("在hanlder中删除意见-deleteComments,响应-response:" + boolDTO);
        return boolDTO;
    }

    public BoolDTO updateComments(int tid, Map<String, String> paramMap) {
        logger.info("在hanlder中修改意见-updateComments,请求-paramMap:" + paramMap);
        BoolDTO boolDTO = new BoolDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        try {
            boolDTO = commentsService.updateComments(objectParamMap);
        } catch (Exception e) {
            boolDTO.setCode(NewMallCode.SERVER_INNER_ERROR.getNo());
            boolDTO.setMessage(NewMallCode.SERVER_INNER_ERROR.getMessage());
            logger.error("在hanlder中修改意见-updateComments is error, paramMap : " + paramMap + ", e : " + e);
        }
        logger.info("在hanlder中修改意见-updateComments,响应-response:" + boolDTO);
        return boolDTO;
    }

    public ResultDTO getSimpleCommentsByCondition(int tid, Map<String, String> paramMap) {
        logger.info("在hanlder中获取单一的意见-getSimpleCommentsByCondition,请求-paramMap:" + paramMap);
        ResultDTO resultDTO = new ResultDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        try {
            resultDTO = commentsService.getSimpleCommentsByCondition(objectParamMap);
        } catch (Exception e) {
            List<Map<String, String>> resultList = Lists.newArrayList();
            resultDTO.setResultListTotal(0);
            resultDTO.setResultList(resultList);
            resultDTO.setCode(NewMallCode.SERVER_INNER_ERROR.getNo());
            resultDTO.setMessage(NewMallCode.SERVER_INNER_ERROR.getMessage());
            logger.error("在hanlder中获取单一的意见-getSimpleCommentsByCondition is error, paramMap : " + paramMap + ", e : " + e);
        }
        logger.info("在hanlder中获取单一的意见-getSimpleCommentsByCondition,响应-response:" + resultDTO);
        return resultDTO;
    }

}

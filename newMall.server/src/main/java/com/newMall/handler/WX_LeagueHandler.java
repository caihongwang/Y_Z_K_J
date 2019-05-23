package com.newMall.handler;

import com.alibaba.fastjson.JSONObject;
import com.newMall.code.NewMallCode;
import com.newMall.dto.BoolDTO;
import com.newMall.dto.ResultDTO;
import com.newMall.service.WX_LeagueService;
import com.newMall.utils.MapUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Description 加盟Handler
 * @author caihongwang
 */
@Component
public class WX_LeagueHandler {

    private static final Logger logger = LoggerFactory.getLogger(com.newMall.handler.WX_LeagueHandler.class);

    @Autowired
    private WX_LeagueService wxLeagueService;

    /**
     * 获取加盟类型列表
     * @param tid
     * @param paramMap
     * @return
     * @throws
     */
    public ResultDTO getLeagueTypeList(int tid, Map<String, String> paramMap) {
        logger.info("【hanlder】获取加盟类型列表-getLeagueTypeList,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        ResultDTO resultDTO = new ResultDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultDTO = wxLeagueService.getLeagueTypeList(objectParamMap);
            } catch (Exception e) {
                logger.error("【hanlder】获取加盟类型列表-getLeagueTypeList is error, paramMap : {}", JSONObject.toJSONString(paramMap), " , e : {}", e);
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
        logger.info("【hanlder】获取加盟类型列表-getLeagueTypeList,响应-resultDTO = {}", JSONObject.toJSONString(resultDTO));
        return resultDTO;
    }

    /**
     * 添加加盟
     * @param tid
     * @param paramMap
     * @return
     * @throws
     */
    public BoolDTO addLeague(int tid, Map<String, String> paramMap) {
        logger.info("【handler】添加加盟-addLeague,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        BoolDTO boolDTO = new BoolDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                boolDTO = wxLeagueService.addLeague(objectParamMap);
            } catch (Exception e) {
                logger.error("【handler】添加加盟-addLeague is error, paramMap : {}", JSONObject.toJSONString(paramMap), " , e : {}", e);
                boolDTO.setCode(NewMallCode.SERVER_INNER_ERROR.getNo());
                boolDTO.setMessage(NewMallCode.SERVER_INNER_ERROR.getMessage());
            }
        } else {
            boolDTO.setCode(NewMallCode.PARAM_IS_NULL.getNo());
            boolDTO.setMessage(NewMallCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("【handler】添加加盟-addLeague,响应-boolDTO = {}", JSONObject.toJSONString(boolDTO));
        return boolDTO;
    }

    /**
     * 删除加盟
     * @param tid
     * @param paramMap
     * @return
     * @throws
     */
    public BoolDTO deleteLeague(int tid, Map<String, String> paramMap) {
        logger.info("【handler】删除加盟-deleteLeague,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        BoolDTO boolDTO = new BoolDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        try {
            boolDTO = wxLeagueService.deleteLeague(objectParamMap);
        } catch (Exception e) {
            logger.error("【handler】删除加盟-deleteLeague is error, paramMap : {}", JSONObject.toJSONString(paramMap), " , e : {}", e);
            boolDTO.setCode(NewMallCode.SERVER_INNER_ERROR.getNo());
            boolDTO.setMessage(NewMallCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("【handler】删除加盟-deleteLeague,响应-boolDTO = {}", JSONObject.toJSONString(boolDTO));
        return boolDTO;
    }

    /**
     * 修改加盟
     * @param tid
     * @param paramMap
     * @return
     * @throws
     */
    public BoolDTO updateLeague(int tid, Map<String, String> paramMap) {
        logger.info("【handler】修改加盟-updateLeague,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        BoolDTO boolDTO = new BoolDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        try {
            boolDTO = wxLeagueService.updateLeague(objectParamMap);
        } catch (Exception e) {
            logger.error("【handler】修改加盟-updateLeague is error, paramMap : {}", JSONObject.toJSONString(paramMap), " , e : {}", e);
            boolDTO.setCode(NewMallCode.SERVER_INNER_ERROR.getNo());
            boolDTO.setMessage(NewMallCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("【handler】修改加盟-updateLeague,响应-boolDTO = {}", JSONObject.toJSONString(boolDTO));
        return boolDTO;
    }

    /**
     * 获取单一的加盟
     * @param tid
     * @param paramMap
     * @return
     * @throws
     */
    public ResultDTO getSimpleLeagueByCondition(int tid, Map<String, String> paramMap) {
        logger.info("【hanlder】获取单一的加盟-getSimpleLeagueByCondition,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        ResultDTO resultDTO = new ResultDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultDTO = wxLeagueService.getSimpleLeagueByCondition(objectParamMap);
            } catch (Exception e) {
                logger.error("【hanlder】获取单一的加盟-getSimpleLeagueByCondition is error, paramMap : {}", JSONObject.toJSONString(paramMap), " , e : {}", e);
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
        logger.info("【hanlder】获取单一的加盟-getSimpleLeagueByCondition,响应-resultDTO = {}", JSONObject.toJSONString(resultDTO));
        return resultDTO;
    }
}

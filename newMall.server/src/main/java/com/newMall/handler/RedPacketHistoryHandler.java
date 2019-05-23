package com.newMall.handler;

import com.newMall.code.NewMallCode;
import com.newMall.dto.ResultDTO;
import com.newMall.dto.ResultMapDTO;
import com.newMall.service.RedPacketHistoryService;
import com.newMall.utils.MapUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 红包领取Handler
 */
@Component
public class RedPacketHistoryHandler {

    private static final Logger logger = LoggerFactory.getLogger(RedPacketHistoryService.class);

    @Autowired
    private RedPacketHistoryService redPacketHistoryService;

    public ResultMapDTO getAllRedPacketMoneyTotal(int tid, Map<String, String> paramMap) {
        logger.info("在hanlder中获取已领取红包总额-getAllRedPacketMoneyTotal,请求-paramMap:" + paramMap);
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultMapDTO = redPacketHistoryService.getAllRedPacketMoneyTotal(objectParamMap);
            } catch (Exception e) {
                resultMapDTO.setCode(NewMallCode.SERVER_INNER_ERROR.getNo());
                resultMapDTO.setMessage(NewMallCode.SERVER_INNER_ERROR.getMessage());
                logger.error("在hanlder中获取已领取红包总额-getAllRedPacketMoneyTotal is error, paramMap : " + paramMap + ", e : " + e);
            }
        } else {
            resultMapDTO.setCode(NewMallCode.PARAM_IS_NULL.getNo());
            resultMapDTO.setMessage(NewMallCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在hanlder中获取已领取红包总额-getAllRedPacketMoneyTotal,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

    public ResultDTO getRedPacketHistoryList(int tid, Map<String, String> paramMap) {
        logger.info("在hanlder中获取红包领取记录-getRedPacketHistoryList,请求-paramMap:" + paramMap);
        ResultDTO resultDTO = new ResultDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultDTO = redPacketHistoryService.getRedPacketHistoryList(objectParamMap);
            } catch (Exception e) {
                List<Map<String, String>> resultList = Lists.newArrayList();
                resultDTO.setResultListTotal(0);
                resultDTO.setResultList(resultList);
                resultDTO.setCode(NewMallCode.SERVER_INNER_ERROR.getNo());
                resultDTO.setMessage(NewMallCode.SERVER_INNER_ERROR.getMessage());
                logger.error("在hanlder中获取红包领取记录-getRedPacketHistoryList is error, paramMap : " + paramMap + ", e : " + e);
                e.printStackTrace();
            }
        } else {
            resultDTO.setCode(NewMallCode.PARAM_IS_NULL.getNo());
            resultDTO.setMessage(NewMallCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在hanlder中获取红包领取记录-getRedPacketHistoryList,响应-response:" + resultDTO);
        return resultDTO;
    }

}

package com.oilStationMap.handler;

import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.service.WX_RedPacketDrawCashHistoryService;
import com.oilStationMap.utils.MapUtil;
import com.google.common.collect.Lists;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 红包提现Handler
 */
@Component
public class WX_RedPacketDrawCashHistoryHandler {

    private static final Logger logger = LoggerFactory.getLogger(WX_RedPacketDrawCashHistoryHandler.class);

    @Autowired
    private WX_RedPacketDrawCashHistoryService wxRedPacketDrawCashHistoryService;

    public ResultMapDTO getDrawCashMoneyTotal(Map<String, String> paramMap) {
        logger.info("在hanlder中获取已提现红包总额-getDrawCashMoneyTotal,请求-paramMap:" + paramMap);
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultMapDTO = wxRedPacketDrawCashHistoryService.getDrawCashMoneyTotal(objectParamMap);
            } catch (Exception e) {
                resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                logger.error("在hanlder中获取已提现红包总额-getDrawCashMoneyTotal is error, paramMap : " + paramMap + ", e : " + e);
            }
        } else {
            resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在hanlder中获取已提现红包总额-getDrawCashMoneyTotal,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }


    public ResultDTO getRedPacketDrawCashHistory(Map<String, String> paramMap) {
        logger.info("在hanlder中获取红包提现记录-getRedPacketDrawCashHistory,请求-paramMap:" + paramMap);
        ResultDTO resultDTO = new ResultDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultDTO = wxRedPacketDrawCashHistoryService.getRedPacketDrawCashHistory(objectParamMap);
            } catch (Exception e) {
                List<Map<String, String>> resultList = Lists.newArrayList();
                resultDTO.setResultListTotal(0);
                resultDTO.setResultList(resultList);
                resultDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                logger.error("在hanlder中获取红包提现记录-getRedPacketDrawCashHistory is error, paramMap : " + paramMap + ", e : " + e);
            }
        } else {
            resultDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在hanlder中获取红包提现记录-getRedPacketDrawCashHistory,响应-response:" + resultDTO);
        return resultDTO;
    }

}

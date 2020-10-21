package com.oilStationMap.handler;

import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.service.WX_RedPacketHistoryService;
import com.oilStationMap.utils.MapUtil;
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
public class WX_RedPacketHistoryHandler {

    private static final Logger logger = LoggerFactory.getLogger(WX_RedPacketHistoryService.class);

    @Autowired
    private WX_RedPacketHistoryService wxRedPacketHistoryService;

    public ResultMapDTO getAllRedPacketMoneyTotal(Map<String, String> paramMap) {
        logger.info("在hanlder中获取已领取红包总额-getAllRedPacketMoneyTotal,请求-paramMap:" + paramMap);
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultMapDTO = wxRedPacketHistoryService.getAllRedPacketMoneyTotal(objectParamMap);
            } catch (Exception e) {
                resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                logger.error("在hanlder中获取已领取红包总额-getAllRedPacketMoneyTotal is error, paramMap : " + paramMap + ", e : " + e);
            }
        } else {
            resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在hanlder中获取已领取红包总额-getAllRedPacketMoneyTotal,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }


    public ResultDTO getRedPacketHistoryList(Map<String, String> paramMap) {
        logger.info("在hanlder中获取红包领取记录-getRedPacketHistoryList,请求-paramMap:" + paramMap);
        ResultDTO resultDTO = new ResultDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultDTO = wxRedPacketHistoryService.getRedPacketHistoryList(objectParamMap);
            } catch (Exception e) {
                List<Map<String, String>> resultList = Lists.newArrayList();
                resultDTO.setResultListTotal(0);
                resultDTO.setResultList(resultList);
                resultDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                logger.error("在hanlder中获取红包领取记录-getRedPacketHistoryList is error, paramMap : " + paramMap + ", e : " + e);
                e.printStackTrace();
            }
        } else {
            resultDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在hanlder中获取红包领取记录-getRedPacketHistoryList,响应-response:" + resultDTO);
        return resultDTO;
    }

}

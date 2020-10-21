package com.oilStationMap.controller;

import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.handler.WX_RedPacketDrawCashHistoryHandler;
import com.oilStationMap.utils.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/wxRedPacketDrawCashHistory", produces = "application/json;charset=utf-8")
public class WX_RedPacketDrawCashHistoryController {

    private static final Logger logger = LoggerFactory.getLogger(WX_RedPacketDrawCashHistoryController.class);

    @Autowired
    private WX_RedPacketDrawCashHistoryHandler wxRedPacketDrawCashHistoryHandler;

    @RequestMapping("/getDrawCashMoneyTotal")
    @ResponseBody
    public Map<String, Object> getDrawCashMoneyTotal(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中获取已提现红包总额-getDrawCashMoneyTotal,请求-paramMap:" + paramMap);
        try {
            ResultMapDTO resultMapDTO = wxRedPacketDrawCashHistoryHandler.getDrawCashMoneyTotal(paramMap);
            resultMap.put("data", resultMapDTO.getResultMap());
            resultMap.put("code", resultMapDTO.getCode());
            resultMap.put("message", resultMapDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中获取已提现红包总额-getDrawCashMoneyTotal is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中获取已提现红包总额-getDrawCashMoneyTotal,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/getRedPacketDrawCashHistory")
    @ResponseBody
    public Map<String, Object> getRedPacketDrawCashHistory(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中获取红包提现记录-getRedPacketDrawCashHistory,请求-paramMap:" + paramMap);
        try {
            ResultDTO resultDTO = wxRedPacketDrawCashHistoryHandler.getRedPacketDrawCashHistory(paramMap);
            resultMap.put("recordsFiltered", resultDTO.getResultListTotal());
            resultMap.put("data", resultDTO.getResultList());
            resultMap.put("code", resultDTO.getCode());
            resultMap.put("message", resultDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中获取红包提现记录-getRedPacketDrawCashHistory is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中获取红包提现记录-getRedPacketDrawCashHistory,响应-response:" + resultMap);
        return resultMap;
    }

}

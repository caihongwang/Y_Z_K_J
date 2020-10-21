package com.oilStationMap.controller;

import com.alibaba.fastjson.JSONObject;
import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.handler.WX_LiveBroadcastHandler;
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
@RequestMapping(value = "/wxLiveBroadcast", produces = "application/json;charset=utf-8")
public class WX_LiveBroadcastController {

    private static final Logger logger = LoggerFactory.getLogger(WX_LiveBroadcastController.class);

    @Autowired
    private WX_LiveBroadcastHandler wxLiveBroadcastHandler;

    /**
     * 获取直播房间列表
     * @param request
     * @return
     */
    @RequestMapping("/getLiveInfoList")
    @ResponseBody
    public Map<String, Object> getLiveInfoList(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("【controller】获取直播房间列表-getLiveInfoList,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        try {
            ResultMapDTO resultMapDTO = wxLiveBroadcastHandler.getLiveInfoList(paramMap);
            resultMap.put("data", resultMapDTO.getResultMap());
            resultMap.put("code", resultMapDTO.getCode());
            resultMap.put("message", resultMapDTO.getMessage());
        } catch (Exception e) {
            logger.error("【controller】获取直播房间列表-getLiveInfoList is error, paramMap : {}", JSONObject.toJSONString(paramMap), " , e : {}", e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("【controller】获取直播房间列表-getLiveInfoList,响应-resultMap = {}", JSONObject.toJSONString(resultMap));
        return resultMap;
    }

}

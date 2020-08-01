package com.oilStationMap.handler;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.BoolDTO;
import com.oilStationMap.dto.MessageDTO;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.service.WX_LiveBroadcastService;
import com.oilStationMap.service.WX_UserService;
import com.oilStationMap.utils.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 用户Handler
 */
@Component
public class WX_LiveBroadcastHandler {

    private static final Logger logger = LoggerFactory.getLogger(WX_LiveBroadcastHandler.class);

    @Autowired
    private WX_LiveBroadcastService wxLiveBroadcastService;

    /**
     * 获取直播房间列表
     * @param paramMap
     * @return
     */
    public ResultMapDTO getLiveInfoList(Map<String, String> paramMap){
        logger.info("【hanlder】获取直播房间列表-getLiveInfoList,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultMapDTO = wxLiveBroadcastService.getLiveInfoList(objectParamMap);
            } catch (Exception e) {
                logger.error("【hanlder】获取直播房间列表-getLiveInfoList is error, paramMap : {}", JSONObject.toJSONString(paramMap), " , e : {}", e);
                resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
            }
        } else {
            resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("【hanlder】获取直播房间列表-getLiveInfoList,响应-resultMapDTO = {}", JSONObject.toJSONString(resultMapDTO));
        return resultMapDTO;
    }

}

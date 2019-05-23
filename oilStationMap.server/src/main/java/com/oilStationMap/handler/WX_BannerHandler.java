package com.oilStationMap.handler;

import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.service.WX_BannerService;
import com.oilStationMap.utils.MapUtil;
import com.google.common.collect.Lists;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * banner Handler
 */
@Component
public class WX_BannerHandler {

    private static final Logger logger = LoggerFactory.getLogger(WX_BannerHandler.class);

    @Autowired
    private WX_BannerService wxBannerService;


    public ResultDTO getActivityBanner(Map<String, String> paramMap) {
        logger.info("在hanlder中获取活跃的banner-getActivityBanner,请求-paramMap:" + paramMap);
        ResultDTO resultDTO = new ResultDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultDTO = wxBannerService.getActivityBanner(objectParamMap);
            } catch (Exception e) {
                List<Map<String, String>> resultList = Lists.newArrayList();
                resultDTO.setResultListTotal(0);
                resultDTO.setResultList(resultList);
                resultDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                logger.error("在hanlder中获取活跃的banner-getActivityBanner is error, paramMap : " + paramMap + ", e : " + e);
            }
        } else {
            resultDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在hanlder中获取活跃的banner-getActivityBanner,响应-response:" + resultDTO);
        return resultDTO;
    }
}

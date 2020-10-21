package com.newMall.handler;

import com.newMall.code.NewMallCode;
import com.newMall.dto.ResultDTO;
import com.newMall.service.BannerService;
import com.newMall.utils.MapUtil;
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
public class BannerHandler {

    private static final Logger logger = LoggerFactory.getLogger(BannerHandler.class);

    @Autowired
    private BannerService bannerService;

    public ResultDTO getActivityBanner(int tid, Map<String, String> paramMap) {
        logger.info("在hanlder中获取活跃的banner-getActivityBanner,请求-paramMap:" + paramMap);
        ResultDTO resultDTO = new ResultDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultDTO = bannerService.getActivityBanner(objectParamMap);
            } catch (Exception e) {
                List<Map<String, String>> resultList = Lists.newArrayList();
                resultDTO.setResultListTotal(0);
                resultDTO.setResultList(resultList);
                resultDTO.setCode(NewMallCode.SERVER_INNER_ERROR.getNo());
                resultDTO.setMessage(NewMallCode.SERVER_INNER_ERROR.getMessage());
                logger.error("在hanlder中获取活跃的banner-getActivityBanner is error, paramMap : " + paramMap + ", e : " + e);
            }
        } else {
            resultDTO.setCode(NewMallCode.PARAM_IS_NULL.getNo());
            resultDTO.setMessage(NewMallCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在hanlder中获取活跃的banner-getActivityBanner,响应-response:" + resultDTO);
        return resultDTO;
    }
}

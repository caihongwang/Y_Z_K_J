package com.oilStationMap.handler;

import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.service.WX_SpiderService;
import com.oilStationMap.utils.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;

/**
 * banner Handler
 */
@Component
public class WX_SpiderHandler {

    private static final Logger logger = LoggerFactory.getLogger(WX_SpiderHandler.class);

    @Autowired
    private WX_SpiderService wxSpiderService;


    public ResultDTO getContactFrom58ErShouFang(Map<String, String> paramMap) {
        logger.info("在hanlder中爬取58二手房爬取手机号并整合入库-getContactFrom58ErShouFang,请求-paramMap:" + paramMap);
        ResultDTO resultDTO = new ResultDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        new Thread(){
            public void run(){
                try {
                    wxSpiderService.getContactFrom58ErShouFang(objectParamMap);
                } catch (Exception e) {
                    logger.error("在hanlder中爬取58二手房爬取手机号并整合入库-getContactFrom58ErShouFang is error, paramMap : " + paramMap + ", e : " + e);
                }
            }
        }.start();
        logger.info("在hanlder中爬取58二手房爬取手机号并整合入库-getContactFrom58ErShouFang,响应-response:" + resultDTO);
        return resultDTO;
    }
}

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

    public ResultDTO getContactFromWeb(Map<String, String> paramMap) {
        logger.info("在hanlder中从网络：５８同城、美团等网络进行爬取房产人员、美食店铺等联系方式-getContactFromWeb,请求-paramMap:" + paramMap);
        ResultDTO resultDTO = new ResultDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        new Thread(){
            public void run(){
                try {
                    wxSpiderService.getContactFromWeb(objectParamMap);
                } catch (Exception e) {
                    logger.error("在hanlder中从网络：５８同城、美团等网络进行爬取房产人员、美食店铺等联系方式-getContactFromWeb is error, paramMap : " + paramMap + ", e : " + e);
                }
            }
        }.start();
        logger.info("在hanlder中从网络：５８同城、美团等网络进行爬取房产人员、美食店铺等联系方式-getContactFromWeb,响应-response:" + resultDTO);
        return resultDTO;
    }

    public ResultDTO sendFriendCircle(Map<String, String> paramMap) {
        logger.info("在hanlder中启动appium,进行自动化发送微信朋友圈-sendFriendCircle,请求-paramMap:" + paramMap);
        ResultDTO resultDTO = new ResultDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        new Thread(){
            public void run(){
                try {
                    wxSpiderService.sendFriendCircle(objectParamMap);
                } catch (Exception e) {
                    logger.error("在hanlder中启动appium,进行自动化发送微信朋友圈-sendFriendCircle is error, paramMap : " + paramMap + ", e : " + e);
                }
            }
        }.start();
        logger.info("在hanlder中启动appium,进行自动化发送微信朋友圈-sendFriendCircle,响应-response:" + resultDTO);
        return resultDTO;
    }
}

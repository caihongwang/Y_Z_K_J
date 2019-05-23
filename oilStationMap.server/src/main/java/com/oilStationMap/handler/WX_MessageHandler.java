package com.oilStationMap.handler;

import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.service.WX_MessageService;
import com.oilStationMap.utils.MapUtil;
import com.google.common.collect.Maps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 微信公众号消息Handler
 */
@Component
public class WX_MessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(WX_MessageHandler.class);

    @Autowired
    private WX_MessageService wxMessageService;

    /**
     * 向微信公众号粉丝群发红包模板消息
     * @param paramMap
     * @return
     */
    public ResultMapDTO redActivityMessageSend(Map<String, String> paramMap) {
        logger.info("在【hanlder】中向微信公众号粉丝群发红包模板消息-redActivityMessageSend,请求-paramMap:" + paramMap);
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        try {
            resultMapDTO = wxMessageService.redActivityMessageSend(objectParamMap);
        } catch (Exception e) {
            resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
            logger.error("在【hanlder】中向微信公众号粉丝群发红包模板消息-redActivityMessageSend is error, paramMap : " + paramMap + ", e : " + e);
        }
        logger.info("在【hanlder】中向微信公众号粉丝群发红包模板消息-redActivityMessageSend,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 根据OpenID列表群发
     * @param paramMap
     * @return
     */
    public ResultMapDTO dailyMessageSend(Map<String, String> paramMap) {
        logger.info("在【hanlder】中根据OpenID列表群发-dailyMessageSend,请求-paramMap:" + paramMap);
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        new Thread(){

            public void run(){
                Map<String, Object> objectParamMap = Maps.newHashMap();
                try {
                    wxMessageService.dailyMessageSend(objectParamMap);
                } catch (Exception e) {
                    resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                    resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                    logger.error("在【hanlder】中根据OpenID列表群发-dailyMessageSend is error, paramMap : " + paramMap + ", e : " + e);
                }
            }
        }.start();
        resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
        resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        logger.info("在【hanlder】中根据OpenID列表群发-dailyMessageSend,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

}

package com.oilStationMap.handler;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.BoolDTO;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.service.MailService;
import com.oilStationMap.service.WX_LeagueService;
import com.oilStationMap.utils.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Description 加盟Handler
 * @author caihongwang
 */
@Component
public class MailHandler {

    private static final Logger logger = LoggerFactory.getLogger(MailHandler.class);

    @Autowired
    private MailService mailService;

    /**
     * 发送文本邮件
     * @param paramMap
     * @return
     */
    public ResultDTO sendSimpleMail(Map<String, String> paramMap) {
        logger.info("【hanlder】发送文本邮件-sendSimpleMail,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        ResultDTO resultDTO = new ResultDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                String to = objectParamMap.get("to").toString();
                String subject = objectParamMap.get("subject").toString();
                String content = objectParamMap.get("content").toString();
                mailService.sendSimpleMail(to, subject, content);
                resultDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                resultDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
            } catch (Exception e) {
                logger.error("【hanlder】发送文本邮件-sendSimpleMail is error, paramMap : {}", JSONObject.toJSONString(paramMap), " , e : {}", e);
                List<Map<String, String>> resultList = Lists.newArrayList();
                resultDTO.setResultListTotal(0);
                resultDTO.setResultList(resultList);
                resultDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
            }
        } else {
            resultDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("【hanlder】发送文本邮件-sendSimpleMail,响应-resultDTO = {}", JSONObject.toJSONString(resultDTO));
        return resultDTO;
    }
}

package com.automation.controller;

import com.alibaba.fastjson.JSONObject;
import com.automation.code.Automation_Code;
import com.automation.dto.MessageDTO;
import com.automation.service.Automation_MailService;
import com.automation.utils.HttpUtil;
import com.automation.utils.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 邮件Controller
 * @author caihongwang
 */
@Controller
@RequestMapping(value = "/mail", produces = "application/json;charset=utf-8")
public class Automation_MailController {

    private static final Logger logger = LoggerFactory.getLogger(Automation_MailController.class);

    @Autowired
    private Automation_MailService automation_MailService;

    /**
     * 发送文本邮件
     * @param request
     * @return
     */
    @RequestMapping("/sendSimpleMail")
    @ResponseBody
    public Map<String, Object> sendSimpleMail(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("【controller】【发送文本邮件】，请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        try {
            Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
            MessageDTO messageDTO = automation_MailService.sendSimpleMail(objectParamMap);
            messageDTO.setCode(Automation_Code.SUCCESS.getNo());
            messageDTO.setMessage(Automation_Code.SUCCESS.getMessage());
        } catch (Exception e) {
            logger.error("【controller】【发送文本邮件】 is error, paramMap : {}", JSONObject.toJSONString(paramMap), " , e : {}", e);
            resultMap.put("success", false);
            resultMap.put("code", Automation_Code.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", Automation_Code.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("【controller】【发送文本邮件】，响应-resultMap = {}", JSONObject.toJSONString(resultMap));
        return resultMap;
    }

}

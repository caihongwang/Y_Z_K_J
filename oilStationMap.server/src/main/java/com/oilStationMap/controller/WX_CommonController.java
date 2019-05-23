package com.oilStationMap.controller;

import com.alibaba.fastjson.JSONObject;
import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.handler.WX_CommonHandler;
import com.oilStationMap.utils.HttpUtil;
import com.oilStationMap.utils.MapUtil;
import com.oilStationMap.utils.StringTypeUtil;
import com.oilStationMap.utils.XmlUtil;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/wxCommon", produces = "application/json;charset=utf-8")
public class WX_CommonController {

    private static final Logger logger = LoggerFactory.getLogger(WX_CommonController.class);

    @Autowired
    private WX_CommonHandler wxCommonHandler;

    @RequestMapping("/getDecryptPhone")
    @ResponseBody
    public Map<String, Object> getDecryptPhone(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中在获取微信手机号时获取解密后手机号-getDecryptPhone,请求-paramMap:" + paramMap);
        try {
            ResultMapDTO resultMapDTO = wxCommonHandler.getDecryptPhone(paramMap);
            resultMap.put("success", true);
            resultMap.put("data", resultMapDTO.getResultMap());
            resultMap.put("code", resultMapDTO.getCode());
            resultMap.put("message", resultMapDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中在获取微信手机号时获取解密后手机号-getDecryptPhone is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中在获取微信手机号时获取解密后手机号-getDecryptPhone,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/sendTemplateMessageForMiniProgram")
    @ResponseBody
    public Map<String, Object> sendTemplateMessageForMiniProgram(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中发送小程序名片的模板消息-sendTemplateMessageForMiniProgram,请求-paramMap:" + paramMap);
        try {
            ResultMapDTO resultMapDTO = wxCommonHandler.sendTemplateMessageForMiniProgram(paramMap);
            resultMap.put("success", true);
            resultMap.put("data", resultMapDTO.getResultMap());
            resultMap.put("code", resultMapDTO.getCode());
            resultMap.put("message", resultMapDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中发送小程序名片的模板消息-sendTemplateMessageForMiniProgram is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中发送小程序名片的模板消息-sendTemplateMessageForMiniProgram,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/sendTemplateMessageForWxPublicNumber")
    @ResponseBody
    public Map<String, Object> sendTemplateMessageForWxPublicNumber(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中发送公众号的模板消息-sendTemplateMessageForWxPublicNumber,请求-paramMap:" + paramMap);
        try {
            ResultMapDTO resultMapDTO = wxCommonHandler.sendTemplateMessageForWxPublicNumber(paramMap);
            resultMap.put("success", true);
            resultMap.put("data", resultMapDTO.getResultMap());
            resultMap.put("code", resultMapDTO.getCode());
            resultMap.put("message", resultMapDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中发送公众号的模板消息-sendTemplateMessageForWxPublicNumber is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中发送公众号的模板消息-sendTemplateMessageForWxPublicNumber,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/sendCustomCardMessageWxPublicNumber")
    @ResponseBody
    public Map<String, Object> sendCustomCardMessageWxPublicNumber(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中在微信公众号内发送卡片类的小程序-sendCustomCardMessageWxPublicNumber,请求-paramMap:" + paramMap);
        try {
            ResultMapDTO resultMapDTO = wxCommonHandler.sendCustomCardMessageWxPublicNumber(paramMap);
            resultMap.put("success", true);
            resultMap.put("data", resultMapDTO.getResultMap());
            resultMap.put("code", resultMapDTO.getCode());
            resultMap.put("message", resultMapDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中在微信公众号内发送卡片类的小程序-sendCustomCardMessageWxPublicNumber is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中在微信公众号内发送卡片类的小程序-sendCustomCardMessageWxPublicNumber,响应-response:" + resultMap);
        return resultMap;
    }

    /**
     * 获取openId
     *
     * @param request
     * @return
     */
    @RequestMapping("/getOpenIdAndSessionKeyForWX")
    @ResponseBody
    public Map<String, Object> getOpenIdAndSessionKeyForWX(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中获取openId和sessionKey-getOpenIdAndSessionKeyForWX,请求-paramMap:" + paramMap);
        try {
            ResultMapDTO resultMapDTO = wxCommonHandler.getOpenIdAndSessionKeyForWX(paramMap);
            resultMap.put("recordsFiltered", resultMapDTO.getResultListTotal());
            resultMap.put("data", resultMapDTO.getResultMap());
            resultMap.put("code", resultMapDTO.getCode());
            resultMap.put("message", resultMapDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中获取openId和sessionKey-getOpenIdAndSessionKeyForWX is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中获取openId和sessionKey-getOpenIdAndSessionKeyForWX,响应-response:" + resultMap);
        return resultMap;
    }

    /**
     * 获取SignatureAndJsapiTicketAndNonceStr
     *
     * @param request
     * @return
     */
    @RequestMapping("/getSignatureAndJsapiTicketAndNonceStrForWxPublicNumber")
    @ResponseBody
    public Map<String, Object> getSignatureAndJsapiTicketAndNonceStrForWxPublicNumber(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中获取SignatureAndJsapiTicketAndNonceStr-getSignatureAndJsapiTicketAndNonceStrForWxPublicNumber,请求-paramMap:" + paramMap);
        try {
            ResultMapDTO resultMapDTO = wxCommonHandler.getSignatureAndJsapiTicketAndNonceStrForWxPublicNumber(paramMap);
            resultMap.put("recordsFiltered", resultMapDTO.getResultListTotal());
            resultMap.put("data", resultMapDTO.getResultMap());
            resultMap.put("code", resultMapDTO.getCode());
            resultMap.put("message", resultMapDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中获取SignatureAndJsapiTicketAndNonceStr-getSignatureAndJsapiTicketAndNonceStrForWxPublicNumber is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中获取SignatureAndJsapiTicketAndNonceStr-getSignatureAndJsapiTicketAndNonceStrForWxPublicNumber,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/receviceAndSendCustomMessage")
    @ResponseBody
    public String receviceAndSendCustomMessage(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中接受小程序端发送过来的消息，同时对特定的消息进行回复小程序的固定客服消息-receviceAndSendCustomMessage,请求-paramMap:" + paramMap);
        try {
            ServletInputStream
                    stream = request.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line = new String("");
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            //此处需要进行适配
            Map<String, Object> customMessageMap = Maps.newHashMap();
            if (StringTypeUtil.isJson(buffer.toString())) {         //json格式
                logger.info("客服消息推送数据是json格式");
                customMessageMap.putAll(JSONObject.parseObject(buffer.toString(), Map.class));
            } else if (StringTypeUtil.isXML(buffer.toString())) {  //xml格式
                logger.info("客服消息推送数据是xml格式");
                customMessageMap.putAll(XmlUtil.xml2mapWithAttr(buffer.toString(), false));
            } else {                                              //json格式
                logger.info("客服消息推送数据是【未知】格式，默认按照json格式处理.");
                customMessageMap.putAll(JSONObject.parseObject(buffer.toString(), Map.class));
            }
            paramMap.putAll(MapUtil.getStringMap(customMessageMap));
            logger.info("在controller中接受小程序端发送过来的消息-receviceAndSendCustomMessage,响应-response:" + JSONObject.toJSON(paramMap));
            ResultMapDTO resultMapDTO = wxCommonHandler.receviceAndSendCustomMessage(paramMap);
            resultMap.put("success", true);
            resultMap.put("data", resultMapDTO.getResultMap());
            resultMap.put("code", resultMapDTO.getCode());
            resultMap.put("message", resultMapDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中接受小程序端发送过来的消息，同时对特定的消息进行回复小程序的固定客服消息-receviceAndSendCustomMessage is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中接受小程序端发送过来的消息，同时对特定的消息进行回复小程序的固定客服消息-receviceAndSendCustomMessage,响应-response:" + resultMap);
        return paramMap.get("echostr") != null ? paramMap.get("echostr").toString() : "success";
    }

    @RequestMapping("/receviceAndSendCustomMessage_For_XSXJ")
    @ResponseBody
    public String receviceAndSendCustomMessage_For_XSXJ(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中接受【像素星舰】小程序端发送过来的消息，同时对特定的消息进行回复小程序的固定客服消息-receviceAndSendCustomMessage,请求-paramMap:" + paramMap);
        try {
            ServletInputStream
                    stream = request.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line = new String("");
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            //此处需要进行适配
            Map<String, Object> customMessageMap = Maps.newHashMap();
            if (StringTypeUtil.isJson(buffer.toString())) {         //json格式
                logger.info("客服消息推送数据是json格式");
                customMessageMap.putAll(JSONObject.parseObject(buffer.toString(), Map.class));
            } else if (StringTypeUtil.isXML(buffer.toString())) {  //xml格式
                logger.info("客服消息推送数据是xml格式");
                customMessageMap.putAll(XmlUtil.xml2mapWithAttr(buffer.toString(), false));
            } else {                                              //json格式
                logger.info("客服消息推送数据是【未知】格式，默认按照json格式处理.");
                customMessageMap.putAll(JSONObject.parseObject(buffer.toString(), Map.class));
            }
            paramMap.putAll(MapUtil.getStringMap(customMessageMap));
            logger.info("在controller中接受【像素星舰】小程序端发送过来的消息-receviceAndSendCustomMessage_For_XSXJ,响应-response:" + JSONObject.toJSON(paramMap));
            ResultMapDTO resultMapDTO = wxCommonHandler.receviceAndSendCustomMessage_For_XSXJ(paramMap);
            resultMap.put("success", true);
            resultMap.put("data", resultMapDTO.getResultMap());
            resultMap.put("code", resultMapDTO.getCode());
            resultMap.put("message", resultMapDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中接受【像素星舰】小程序端发送过来的消息，同时对特定的消息进行回复小程序的固定客服消息-receviceAndSendCustomMessage is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中接受【像素星舰】小程序端发送过来的消息，同时对特定的消息进行回复小程序的固定客服消息-receviceAndSendCustomMessage,响应-response:" + resultMap);
        return paramMap.get("echostr") != null ? paramMap.get("echostr").toString() : "success";
    }

}

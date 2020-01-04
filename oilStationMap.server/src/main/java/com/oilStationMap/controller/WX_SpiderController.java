package com.oilStationMap.controller;

import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.handler.WX_SpiderHandler;
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
@RequestMapping(value = "/wxSpider", produces = "application/json;charset=utf-8")
public class WX_SpiderController {

    private static final Logger logger = LoggerFactory.getLogger(WX_SpiderController.class);

    @Autowired
    private WX_SpiderHandler wxSpiderHandler;

    @RequestMapping("/getContactFromWeb")
    @ResponseBody
    public Map<String, Object> getContactFromWeb(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中从网络：５８同城、美团等网络进行爬取房产人员、美食店铺等联系方式-getContactFromWeb,请求-paramMap:" + paramMap);
        try {
            ResultDTO resultDTO = wxSpiderHandler.getContactFromWeb(paramMap);
            resultMap.put("code", resultDTO.getCode());
            resultMap.put("message", resultDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中从网络：５８同城、美团等网络进行爬取房产人员、美食店铺等联系方式-getContactFromWeb is error, paramMap : " + paramMap + ", e : ", e);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中从网络：５８同城、美团等网络进行爬取房产人员、美食店铺等联系方式-getContactFromWeb,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/sendFriendCircle")
    @ResponseBody
    public Map<String, Object> sendFriendCircle(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中启动appium,自动化发送微信朋友圈-sendFriendCircle,请求-paramMap:" + paramMap);
        try {
            ResultDTO resultDTO = wxSpiderHandler.sendFriendCircle(paramMap);
            resultMap.put("code", resultDTO.getCode());
            resultMap.put("message", resultDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中启动appium,自动化发送微信朋友圈-sendFriendCircle is error, paramMap : " + paramMap + ", e : ", e);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中启动appium,自动化发送微信朋友圈-sendFriendCircle,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/chatByNickName")
    @ResponseBody
    public Map<String, Object> chatByNickName(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中启动appium,根据微信昵称进行聊天-chatByNickName,请求-paramMap:" + paramMap);
        try {
            ResultDTO resultDTO = wxSpiderHandler.chatByNickName(paramMap);
            resultMap.put("code", resultDTO.getCode());
            resultMap.put("message", resultDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中启动appium,根据微信昵称进行聊天-chatByNickName is error, paramMap : " + paramMap + ", e : ", e);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中启动appium,根据微信昵称进行聊天-chatByNickName,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/shareArticleToFriendCircle")
    @ResponseBody
    public Map<String, Object> shareArticleToFriendCircle(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中启动appium,分享微信文章到微信朋友圈-shareArticleToFriendCircle,请求-paramMap:" + paramMap);
        try {
            ResultDTO resultDTO = wxSpiderHandler.shareArticleToFriendCircle(paramMap);
            resultMap.put("code", resultDTO.getCode());
            resultMap.put("message", resultDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中启动appium,分享微信文章到微信朋友圈-shareArticleToFriendCircle is error, paramMap : " + paramMap + ", e : ", e);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中启动appium,分享微信文章到微信朋友圈-shareArticleToFriendCircle,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/clickArticleAd")
    @ResponseBody
    public Map<String, Object> clickArticleAd(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中启动appium,点击微信文章中的广告-clickArticleAd,请求-paramMap:" + paramMap);
        try {
            ResultDTO resultDTO = wxSpiderHandler.clickArticleAd(paramMap);
            resultMap.put("code", resultDTO.getCode());
            resultMap.put("message", resultDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中启动appium,点击微信文章中的广告-clickArticleAd is error, paramMap : " + paramMap + ", e : ", e);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中启动appium,点击微信文章中的广告-clickArticleAd,响应-response:" + resultMap);
        return resultMap;
    }

}

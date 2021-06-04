package com.automation.controller;

import com.automation.code.Automation_Code;
import com.automation.dto.MessageDTO;
import com.automation.service.Automation_WxService;
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

@Controller
@RequestMapping(value = "/wx", produces = "application/json;charset=utf-8")
public class Automation_WxController {

    private static final Logger logger = LoggerFactory.getLogger(Automation_WxController.class);

    @Autowired
    private Automation_WxService automation_WxService;

    @RequestMapping("/relayTheWxMessage")
    @ResponseBody
    public Map<String, Object> relayTheWxMessage(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在【controller】中启动appium,转发微信消息-relayTheWxMessage,请求-paramMap:" + paramMap);
        try {
            Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
            MessageDTO messageDTO = automation_WxService.relayTheWxMessage(objectParamMap);
            resultMap.put("code", messageDTO.getCode());
            resultMap.put("message", messageDTO.getMessage());
        } catch (Exception e) {
            logger.error("在【controller】中启动appium,转发微信消息-relayTheWxMessage is error, paramMap : " + paramMap + ", e : ", e);
            resultMap.put("code", Automation_Code.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", Automation_Code.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在【controller】中启动appium,转发微信消息-relayTheWxMessage,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/praiseAndCommentFriendsCircle")
    @ResponseBody
    public Map<String, Object> praiseAndCommentFriendsCircle(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在【controller】中启动appium,点赞和评论朋友圈-praiseAndCommentFriendsCircle,请求-paramMap:" + paramMap);
        try {
            Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
            MessageDTO messageDTO = automation_WxService.praiseAndCommentFriendsCircle(objectParamMap);
            resultMap.put("code", messageDTO.getCode());
            resultMap.put("message", messageDTO.getMessage());
        } catch (Exception e) {
            logger.error("在【controller】中启动appium,点赞和评论朋友圈-praiseAndCommentFriendsCircle is error, paramMap : " + paramMap + ", e : ", e);
            resultMap.put("code", Automation_Code.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", Automation_Code.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在【controller】中启动appium,点赞和评论朋友圈-praiseAndCommentFriendsCircle,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/agreeToJoinTheGroup")
    @ResponseBody
    public Map<String, Object> agreeToJoinTheGroup(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在【controller】中启动appium,同意进群-agreeToJoinTheGroup,请求-paramMap:" + paramMap);
        try {
            Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
            MessageDTO messageDTO = automation_WxService.agreeToJoinTheGroup(objectParamMap);
            resultMap.put("code", messageDTO.getCode());
            resultMap.put("message", messageDTO.getMessage());
        } catch (Exception e) {
            logger.error("在【controller】中启动appium,同意进群-agreeToJoinTheGroup is error, paramMap : " + paramMap + ", e : ", e);
            resultMap.put("code", Automation_Code.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", Automation_Code.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在【controller】中启动appium,同意进群-agreeToJoinTheGroup,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/saveToAddressBook")
    @ResponseBody
    public Map<String, Object> saveToAddressBook(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在【controller】中启动appium,将群保存到通讯录工具-saveToAddressBook,请求-paramMap:" + paramMap);
        try {
            Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
            MessageDTO messageDTO = automation_WxService.saveToAddressBook(objectParamMap);
            resultMap.put("code", messageDTO.getCode());
            resultMap.put("message", messageDTO.getMessage());
        } catch (Exception e) {
            logger.error("在【controller】中启动appium,将群保存到通讯录工具-saveToAddressBook is error, paramMap : " + paramMap + ", e : ", e);
            resultMap.put("code", Automation_Code.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", Automation_Code.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在【controller】中启动appium,将群保存到通讯录工具-saveToAddressBook,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/addGroupMembersAsFriends")
    @ResponseBody
    public Map<String, Object> addGroupMembersAsFriends(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在【controller】中启动appium,根据微信群昵称添加群成员为好友-addGroupMembersAsFriends,请求-paramMap:" + paramMap);
        try {
            Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
            MessageDTO messageDTO = automation_WxService.addGroupMembersAsFriends(objectParamMap);
            resultMap.put("code", messageDTO.getCode());
            resultMap.put("message", messageDTO.getMessage());
        } catch (Exception e) {
            logger.error("在【controller】中启动appium,根据微信群昵称添加群成员为好友-addGroupMembersAsFriends is error, paramMap : " + paramMap + ", e : ", e);
            resultMap.put("code", Automation_Code.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", Automation_Code.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在【controller】中启动appium,根据微信群昵称添加群成员为好友-addGroupMembersAsFriends,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/agreeToFriendRequest")
    @ResponseBody
    public Map<String, Object> agreeToFriendRequest(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在【controller】中启动appium,同意好友请求-agreeToFriendRequest,请求-paramMap:" + paramMap);
        try {
            Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
            MessageDTO messageDTO = automation_WxService.agreeToFriendRequest(objectParamMap);
            resultMap.put("code", messageDTO.getCode());
            resultMap.put("message", messageDTO.getMessage());
        } catch (Exception e) {
            logger.error("在【controller】中启动appium,同意好友请求-agreeToFriendRequest is error, paramMap : " + paramMap + ", e : ", e);
            resultMap.put("code", Automation_Code.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", Automation_Code.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在【controller】中启动appium,同意好友请求-agreeToFriendRequest,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/sendFriendCircle")
    @ResponseBody
    public Map<String, Object> sendFriendCircle(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在【controller】中启动appium,自动化发送微信朋友圈-sendFriendCircle,请求-paramMap:" + paramMap);
        try {
            Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
            MessageDTO messageDTO = automation_WxService.sendFriendCircle(objectParamMap);
            resultMap.put("code", messageDTO.getCode());
            resultMap.put("message", messageDTO.getMessage());
        } catch (Exception e) {
            logger.error("在【controller】中启动appium,自动化发送微信朋友圈-sendFriendCircle is error, paramMap : " + paramMap + ", e : ", e);
            resultMap.put("code", Automation_Code.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", Automation_Code.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在【controller】中启动appium,自动化发送微信朋友圈-sendFriendCircle,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/chatByNickName")
    @ResponseBody
    public Map<String, Object> chatByNickName(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在【controller】中启动appium,根据微信昵称进行聊天-chatByNickName,请求-paramMap:" + paramMap);
        try {
            Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
            MessageDTO messageDTO = automation_WxService.chatByNickName(objectParamMap);
            resultMap.put("code", messageDTO.getCode());
            resultMap.put("message", messageDTO.getMessage());
        } catch (Exception e) {
            logger.error("在【controller】中启动appium,根据微信昵称进行聊天-chatByNickName is error, paramMap : " + paramMap + ", e : ", e);
            resultMap.put("code", Automation_Code.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", Automation_Code.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在【controller】中启动appium,根据微信昵称进行聊天-chatByNickName,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/shareArticleToFriendCircle")
    @ResponseBody
    public Map<String, Object> shareArticleToFriendCircle(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在【controller】中启动appium,分享微信文章到微信朋友圈-shareArticleToFriendCircle,请求-paramMap:" + paramMap);
        try {
            Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
            MessageDTO messageDTO = automation_WxService.shareArticleToFriendCircle(objectParamMap);
            resultMap.put("code", messageDTO.getCode());
            resultMap.put("message", messageDTO.getMessage());
        } catch (Exception e) {
            logger.error("在【controller】中启动appium,分享微信文章到微信朋友圈-shareArticleToFriendCircle is error, paramMap : " + paramMap + ", e : ", e);
            resultMap.put("code", Automation_Code.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", Automation_Code.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在【controller】中启动appium,分享微信文章到微信朋友圈-shareArticleToFriendCircle,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/clickArticleAd")
    @ResponseBody
    public Map<String, Object> clickArticleAd(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在【controller】中启动appium,点击微信文章中的广告-clickArticleAd,请求-paramMap:" + paramMap);
        try {
            Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
            MessageDTO messageDTO = automation_WxService.clickArticleAd(objectParamMap);
            resultMap.put("code", messageDTO.getCode());
            resultMap.put("message", messageDTO.getMessage());
        } catch (Exception e) {
            logger.error("在【controller】中启动appium,点击微信文章中的广告-clickArticleAd is error, paramMap : " + paramMap + ", e : ", e);
            resultMap.put("code", Automation_Code.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", Automation_Code.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在【controller】中启动appium,点击微信文章中的广告-clickArticleAd,响应-response:" + resultMap);
        return resultMap;
    }

}

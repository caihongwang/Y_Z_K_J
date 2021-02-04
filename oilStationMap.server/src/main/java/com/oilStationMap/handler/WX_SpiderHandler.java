package com.oilStationMap.handler;

import com.oilStationMap.code.OilStationMapCode;
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
        new Thread() {
            public void run() {
                try {
                    wxSpiderService.getContactFromWeb(objectParamMap);
                } catch (Exception e) {
                    logger.error("在hanlder中从网络：５８同城、美团等网络进行爬取房产人员、美食店铺等联系方式-getContactFromWeb is error, paramMap : " + paramMap + ", e : ", e);
                }
            }
        }.start();
        resultDTO.setCode(OilStationMapCode.SUCCESS.getNo());
        resultDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        logger.info("在hanlder中从网络：５８同城、美团等网络进行爬取房产人员、美食店铺等联系方式-getContactFromWeb,响应-response:" + resultDTO);
        return resultDTO;
    }

    public ResultDTO sendFriendCircle(Map<String, String> paramMap) {
        logger.info("在hanlder中启动appium,自动化发送微信朋友圈-sendFriendCircle,请求-paramMap:" + paramMap);
        ResultDTO resultDTO = new ResultDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        new Thread() {
            public void run() {
                try {
                    wxSpiderService.sendFriendCircle(objectParamMap);
                } catch (Exception e) {
                    logger.error("在hanlder中启动appium,自动化发送微信朋友圈-sendFriendCircle is error, paramMap : " + paramMap + ", e : ", e);
                }
            }
        }.start();
        resultDTO.setCode(OilStationMapCode.SUCCESS.getNo());
        resultDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        logger.info("在hanlder中启动appium,自动化发送微信朋友圈-sendFriendCircle,响应-response:" + resultDTO);
        return resultDTO;
    }

    public ResultDTO chatByNickName(Map<String, String> paramMap) {
        logger.info("在hanlder中启动appium,根据微信昵称进行聊天-chatByNickName,请求-paramMap:" + paramMap);
        ResultDTO resultDTO = new ResultDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        new Thread() {
            public void run() {
                try {
                    wxSpiderService.chatByNickName(objectParamMap);
                } catch (Exception e) {
                    logger.error("在hanlder中启动appium,根据微信昵称进行聊天-chatByNickName is error, paramMap : " + paramMap + ", e : ", e);
                }
            }
        }.start();
        resultDTO.setCode(OilStationMapCode.SUCCESS.getNo());
        resultDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        logger.info("在hanlder中启动appium,根据微信昵称进行聊天-chatByNickName,响应-response:" + resultDTO);
        return resultDTO;
    }

    public ResultDTO praiseAndCommentFriendsCircle(Map<String, String> paramMap) {
        logger.info("在hanlder中启动appium,点赞和评论朋友圈-praiseAndCommentFriendsCircle,请求-paramMap:" + paramMap);
        ResultDTO resultDTO = new ResultDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        new Thread() {
            public void run() {
                try {
                    wxSpiderService.praiseAndCommentFriendsCircle(objectParamMap);
                } catch (Exception e) {
                    logger.error("在hanlder中启动appium,点赞和评论朋友圈-praiseAndCommentFriendsCircle is error, paramMap : " + paramMap + ", e : ", e);
                }
            }
        }.start();
        resultDTO.setCode(OilStationMapCode.SUCCESS.getNo());
        resultDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        logger.info("在hanlder中启动appium,点赞和评论朋友圈-praiseAndCommentFriendsCircle,响应-response:" + resultDTO);
        return resultDTO;
    }

    public ResultDTO saveToAddressBook(Map<String, String> paramMap) {
        logger.info("在hanlder中启动appium,将群保存到通讯录工具-saveToAddressBook,请求-paramMap:" + paramMap);
        ResultDTO resultDTO = new ResultDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        new Thread() {
            public void run() {
                try {
                    wxSpiderService.saveToAddressBook(objectParamMap);
                } catch (Exception e) {
                    logger.error("在hanlder中启动appium,将群保存到通讯录工具-saveToAddressBook is error, paramMap : " + paramMap + ", e : ", e);
                }
            }
        }.start();
        resultDTO.setCode(OilStationMapCode.SUCCESS.getNo());
        resultDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        logger.info("在hanlder中启动appium,将群保存到通讯录工具-saveToAddressBook,响应-response:" + resultDTO);
        return resultDTO;
    }

    public ResultDTO agreeToJoinTheGroup(Map<String, String> paramMap) {
        logger.info("在hanlder中启动appium,同意进群-agreeToJoinTheGroup,请求-paramMap:" + paramMap);
        ResultDTO resultDTO = new ResultDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        new Thread() {
            public void run() {
                try {
                    wxSpiderService.agreeToJoinTheGroup(objectParamMap);
                } catch (Exception e) {
                    logger.error("在hanlder中启动appium,同意进群-agreeToJoinTheGroup is error, paramMap : " + paramMap + ", e : ", e);
                }
            }
        }.start();
        resultDTO.setCode(OilStationMapCode.SUCCESS.getNo());
        resultDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        logger.info("在hanlder中启动appium,同意进群-agreeToJoinTheGroup,响应-response:" + resultDTO);
        return resultDTO;
    }

    public ResultDTO addGroupMembersAsFriends(Map<String, String> paramMap) {
        logger.info("在hanlder中启动appium,根据微信群昵称添加群成员为好友-addGroupMembersAsFriends,请求-paramMap:" + paramMap);
        ResultDTO resultDTO = new ResultDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        new Thread() {
            public void run() {
                try {
                    wxSpiderService.addGroupMembersAsFriends(objectParamMap);
                } catch (Exception e) {
                    logger.error("在hanlder中启动appium,根据微信群昵称添加群成员为好友-addGroupMembersAsFriends is error, paramMap : " + paramMap + ", e : ", e);
                }
            }
        }.start();
        resultDTO.setCode(OilStationMapCode.SUCCESS.getNo());
        resultDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        logger.info("在hanlder中启动appium,根据微信群昵称添加群成员为好友-addGroupMembersAsFriends,响应-response:" + resultDTO);
        return resultDTO;
    }

    public ResultDTO agreeToFriendRequest(Map<String, String> paramMap) {
        logger.info("在hanlder中启动appium,同意好友请求-agreeToFriendRequest,请求-paramMap:" + paramMap);
        ResultDTO resultDTO = new ResultDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        new Thread() {
            public void run() {
                try {
                    wxSpiderService.agreeToFriendRequest(objectParamMap);
                } catch (Exception e) {
                    logger.error("在hanlder中启动appium,同意好友请求-agreeToFriendRequest is error, paramMap : " + paramMap + ", e : ", e);
                }
            }
        }.start();
        resultDTO.setCode(OilStationMapCode.SUCCESS.getNo());
        resultDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        logger.info("在hanlder中启动appium,同意好友请求-agreeToFriendRequest,响应-response:" + resultDTO);
        return resultDTO;
    }

    public ResultDTO shareArticleToFriendCircle(Map<String, String> paramMap) {
        logger.info("在hanlder中启动appium,分享微信文章到微信朋友圈-shareArticleToFriendCircle,请求-paramMap:" + paramMap);
        ResultDTO resultDTO = new ResultDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        new Thread() {
            public void run() {
                try {
                    wxSpiderService.shareArticleToFriendCircle(objectParamMap);
                } catch (Exception e) {
                    logger.error("在hanlder中启动appium,分享微信文章到微信朋友圈-shareArticleToFriendCircle is error, paramMap : " + paramMap + ", e : ", e);
                }
            }
        }.start();
        resultDTO.setCode(OilStationMapCode.SUCCESS.getNo());
        resultDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        logger.info("在hanlder中启动appium,分享微信文章到微信朋友圈-shareArticleToFriendCircle,响应-response:" + resultDTO);
        return resultDTO;
    }

    public ResultDTO clickArticleAd(Map<String, String> paramMap) {
        logger.info("在hanlder中启动appium,点击微信文章中的广告-clickArticleAd,请求-paramMap:" + paramMap);
        ResultDTO resultDTO = new ResultDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        new Thread() {
            public void run() {
                try {
                    wxSpiderService.clickArticleAd(objectParamMap);
                } catch (Exception e) {
                    logger.error("在hanlder中启动appium,点击微信文章中的广告-clickArticleAd is error, paramMap : " + paramMap + ", e : ", e);
                }
            }
        }.start();
        resultDTO.setCode(OilStationMapCode.SUCCESS.getNo());
        resultDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        logger.info("在hanlder中启动appium,点击微信文章中的广告-clickArticleAd,响应-response:" + resultDTO);
        return resultDTO;
    }
}

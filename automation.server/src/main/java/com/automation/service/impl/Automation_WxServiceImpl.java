package com.automation.service.impl;

import com.automation.code.Automation_Code;
import com.automation.dto.MessageDTO;
import com.automation.service.*;
import com.automation.utils.wei_xin.addGroupMembersAsFriends.AddGroupMembersAsFriendsUtils;
import com.automation.utils.wei_xin.agreeToFriendRequest.AgreeToFriendRequestUtils;
import com.automation.utils.wei_xin.agreeToJoinTheGroup.AgreeToJoinTheGroupUtils;
import com.automation.utils.wei_xin.chatByNickName.ChatByNickNameUtils;
import com.automation.utils.wei_xin.praiseAndCommentFriendsCircle.PraiseAndCommentFriendsCircleUtils;
import com.automation.utils.wei_xin.relayTheWxMessage.RelayTheWxMessageUtils;
import com.automation.utils.wei_xin.saveToAddressBook.SaveToAddressBookUtils;
import com.automation.utils.wei_xin.sendFriendCircle.SendFriendCircleUtils;
import com.automation.utils.wei_xin.shareArticleToFriendCircleUtils.ShareArticleToFriendCircleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

/**
 * 字典service
 */
@Service
public class Automation_WxServiceImpl implements Automation_WxService {

    private static final Logger logger = LoggerFactory.getLogger(Automation_WxServiceImpl.class);

    /**
     * 启动appium,转发微信消息
     * @param paramMap
     * @return
     */
    @Autowired
    private RelayTheWxMessageUtils relayTheWxMessageUtils;
    @Override
    public MessageDTO relayTheWxMessage(Map<String, Object> paramMap) {
        new Thread() {
            public void run() {
                try {
                    relayTheWxMessageUtils.relayTheWxMessage(paramMap);
                } catch (Exception e) {
                    logger.error("在【service】中启动appium,转发微信消息-relayTheWxMessage is error, paramMap : " + paramMap + ", e : ", e);
                }
            }
        }.start();
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setCode(Automation_Code.SUCCESS.getNo());
        messageDTO.setMessage(Automation_Code.SUCCESS.getMessage());
        logger.info("在【service】中启动appium,转发微信消息-relayTheWxMessage,结果-result:" + messageDTO);
        return messageDTO;
    }

    /**
     * 启动appium,自动化发送微信朋友圈
     * @param paramMap
     * @return
     */
    @Autowired
    private SendFriendCircleUtils sendFriendCircleUtils;
    @Override
    public MessageDTO sendFriendCircle(Map<String, Object> paramMap) {
        new Thread() {
            public void run() {
                try {
                    sendFriendCircleUtils.sendFriendCircle(paramMap);
                } catch (Exception e) {
                    logger.error("在【service】中启动appium,自动化发送微信朋友圈-sendFriendCircle is error, paramMap : " + paramMap + ", e : ", e);
                }
            }
        }.start();
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setCode(Automation_Code.SUCCESS.getNo());
        messageDTO.setMessage(Automation_Code.SUCCESS.getMessage());
        logger.info("在【service】中启动appium,自动化发送微信朋友圈-sendFriendCircle,结果-result:" + messageDTO);
        return messageDTO;
    }

    /**
     * 同意进群
     * @param paramMap
     * @throws Exception
     */
    @Autowired
    private AgreeToJoinTheGroupUtils agreeToJoinTheGroupUtils;
    @Override
    public MessageDTO agreeToJoinTheGroup(Map<String, Object> paramMap){
        new Thread() {
            public void run() {
                try {
                    agreeToJoinTheGroupUtils.agreeToJoinTheGroup(paramMap);
                } catch (Exception e) {
                    logger.error("在【service】中启动appium,同意进群-agreeToJoinTheGroup is error, paramMap : " + paramMap + ", e : ", e);
                }
            }
        }.start();
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setCode(Automation_Code.SUCCESS.getNo());
        messageDTO.setMessage(Automation_Code.SUCCESS.getMessage());
        logger.info("在【service】中启动appium,同意进群-saveToAddressBook,结果-result:" + messageDTO);
        return messageDTO;
    }

    /**
     * 点赞和评论朋友圈
     * @param paramMap
     * @throws Exception
     */
    @Autowired
    private PraiseAndCommentFriendsCircleUtils praiseAndCommentFriendsCircleUtils;
    @Override
    public MessageDTO praiseAndCommentFriendsCircle(Map<String, Object> paramMap){
        new Thread() {
            public void run() {
                try {
                    praiseAndCommentFriendsCircleUtils.praiseAndCommentFriendsCircle(paramMap);
                } catch (Exception e) {
                    logger.error("在【service】中启动appium,点赞和评论朋友圈-praiseAndCommentFriendsCircle is error, paramMap : " + paramMap + ", e : ", e);
                }
            }
        }.start();
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setCode(Automation_Code.SUCCESS.getNo());
        messageDTO.setMessage(Automation_Code.SUCCESS.getMessage());
        logger.info("在【service】中启动appium,点赞和评论朋友圈-saveToAddressBook,结果-result:" + messageDTO);
        return messageDTO;
    }

    /**
     * 将群保存到通讯录
     * @param paramMap
     * @throws Exception
     */
    @Autowired
    private SaveToAddressBookUtils saveToAddressBookUtils;
    @Override
    public MessageDTO saveToAddressBook(Map<String, Object> paramMap){
        new Thread() {
            public void run() {
                try {
                    saveToAddressBookUtils.saveToAddressBook(paramMap);
                } catch (Exception e) {
                    logger.error("在【service】中启动appium,将群保存到通讯录工具-saveToAddressBook is error, paramMap : " + paramMap + ", e : ", e);
                }
            }
        }.start();
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setCode(Automation_Code.SUCCESS.getNo());
        messageDTO.setMessage(Automation_Code.SUCCESS.getMessage());
        logger.info("在【service】中启动appium,将群保存到通讯录-saveToAddressBook,结果-result:" + messageDTO);
        return messageDTO;
    }

    /**
     * 同意好友请求
     * @param paramMap
     * @throws Exception
     */
    @Autowired
    private AgreeToFriendRequestUtils agreeToFriendRequestUtils;
    @Override
    public MessageDTO agreeToFriendRequest(Map<String, Object> paramMap){
        new Thread() {
            public void run() {
                try {
                    agreeToFriendRequestUtils.agreeToFriendRequest(paramMap);
                } catch (Exception e) {
                    logger.error("在【service】中启动appium,同意好友请求-agreeToFriendRequest is error, paramMap : " + paramMap + ", e : ", e);
                }
            }
        }.start();
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setCode(Automation_Code.SUCCESS.getNo());
        messageDTO.setMessage(Automation_Code.SUCCESS.getMessage());
        logger.info("在【service】中启动appium,同意好友请求-agreeToFriendRequest,结果-result:" + messageDTO);
        return messageDTO;
    }

    /**
     * 根据微信群昵称添加群成员为好友
     * @param paramMap
     * @return
     */
    @Autowired
    private AddGroupMembersAsFriendsUtils addGroupMembersAsFriendsUtils;
    @Override
    public MessageDTO addGroupMembersAsFriends(Map<String, Object> paramMap) {
        new Thread() {
            public void run() {
                try {
                    addGroupMembersAsFriendsUtils.addGroupMembersAsFriends(paramMap);
                } catch (Exception e) {
                    logger.error("在【service】中启动appium,根据微信群昵称添加群成员为好友-addGroupMembersAsFriends is error, paramMap : " + paramMap + ", e : ", e);
                }
            }
        }.start();
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setCode(Automation_Code.SUCCESS.getNo());
        messageDTO.setMessage(Automation_Code.SUCCESS.getMessage());
        logger.info("在【service】中启动appium,根据微信群昵称添加群成员为好友-addGroupMembersAsFriends,结果-result:" + messageDTO);
        return messageDTO;
    }

    /**
     * 根据微信昵称进行聊天
     * @param paramMap
     * @return
     */
    @Autowired
    private ChatByNickNameUtils chatByNickNameUtils;
    @Override
    public MessageDTO chatByNickName(Map<String, Object> paramMap) {
        new Thread() {
            public void run() {
                try {
                    chatByNickNameUtils.chatByNickName(paramMap);
                } catch (Exception e) {
                    logger.error("在【service】中启动appium,根据微信昵称进行聊天-chatByNickName is error, paramMap : " + paramMap + ", e : ", e);
                }
            }
        }.start();
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setCode(Automation_Code.SUCCESS.getNo());
        messageDTO.setMessage(Automation_Code.SUCCESS.getMessage());
        logger.info("在【service】中启动appium,根据微信昵称进行聊天-addGroupMembersAsFriends,结果-result:" + messageDTO);
        return messageDTO;
    }

    /**
     * 前置条件：将微信文章群发到【油站科技-内部交流群】里面
     * 启动appium,分享微信文章到微信朋友圈
     * @param paramMap
     * @return
     */
    @Autowired
    private ShareArticleToFriendCircleUtils shareArticleToFriendCircleUtils;
    @Override
    public MessageDTO shareArticleToFriendCircle(Map<String, Object> paramMap) {
        new Thread() {
            public void run() {
                try {
                    shareArticleToFriendCircleUtils.shareArticleToFriendCircle(paramMap);
                } catch (Exception e) {
                    logger.error("在【service】中启动appium,分享微信文章到微信朋友圈-shareArticleToFriendCircle is error, paramMap : " + paramMap + ", e : ", e);
                }
            }
        }.start();
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setCode(Automation_Code.SUCCESS.getNo());
        messageDTO.setMessage(Automation_Code.SUCCESS.getMessage());
        logger.info("在【service】中启动appium,分享微信文章到微信朋友圈-shareArticleToFriendCircle,结果-result:" + messageDTO);
        return messageDTO;
    }

    /**
     * 前置条件：将微信文章群发到【油站科技-内部交流群】里面
     * 启动appium,点击微信文章中的广告
     * @param paramMap
     * @return
     */
    @Override
    public MessageDTO clickArticleAd(Map<String, Object> paramMap) {
        new Thread() {
            public void run() {
                try {
                    //待开发.....
                    //待开发.....
                    //待开发.....
                } catch (Exception e) {
                    logger.error("在【service】中启动appium,点击微信文章中的广告-clickArticleAd is error, paramMap : " + paramMap + ", e : ", e);
                }
            }
        }.start();
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setCode(Automation_Code.SUCCESS.getNo());
        messageDTO.setMessage(Automation_Code.SUCCESS.getMessage());
        logger.info("在【service】中启动appium,点击微信文章中的广告-clickArticleAd,结果-result:" + messageDTO);
        return messageDTO;
    }
}

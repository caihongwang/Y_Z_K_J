package com.automation.service.impl;

import com.automation.code.Automation_Code;
import com.automation.dto.MessageDTO;
import com.automation.service.*;
import com.automation.utils.wei_xin.addGroupMembersAsFriends.AddGroupMembersAsFriendsUtils;
import com.automation.utils.wei_xin.agreeToFriendRequest.AgreeToFriendRequestUtils;
import com.automation.utils.wei_xin.agreeToJoinTheGroup.AgreeToJoinTheGroupUtils;
import com.automation.utils.wei_xin.chatByNickName.ChatByNickNameUtils;
import com.automation.utils.wei_xin.inviteToJoinTheGroup.InviteToJoinTheGroupUtils;
import com.automation.utils.wei_xin.praiseAndCommentFriendsCircle.PraiseAndCommentFriendsCircleUtils;
import com.automation.utils.wei_xin.relayTheWxMessage.RelayTheWxMessageUtils;
import com.automation.utils.wei_xin.saveToAddressBook.SaveToAddressBookUtils;
import com.automation.utils.wei_xin.sendFriendCircle.SendFriendCircleUtils;
import com.automation.utils.wei_xin.shareArticleToFriendCircleUtils.ShareArticleToFriendCircleUtils;
import com.automation.utils.wei_xin.shareVideoNumToFriendCircle.ShareVideoNumToFriendCircleUtils;
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
     * 邀请进群
     * @param paramMap
     * @throws Exception
     */
    @Autowired
    private InviteToJoinTheGroupUtils inviteToJoinTheGroupUtils;
    @Override
    public MessageDTO inviteToJoinTheGroup(Map<String, Object> paramMap){
        new Thread() {
            public void run() {
                try {
                    inviteToJoinTheGroupUtils.inviteToJoinTheGroup(paramMap);
                } catch (Exception e) {
                    logger.error("【service】【启动appium，邀请进群】 is error, paramMap : " + paramMap + ", e : ", e);
                }
            }
        }.start();
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setCode(Automation_Code.SUCCESS.getNo());
        messageDTO.setMessage(Automation_Code.SUCCESS.getMessage());
        logger.info("【service】【启动appium，邀请进群】，结果-result:" + messageDTO);
        return messageDTO;
    }

    /**
     * 启动appium，分享视频号到朋友圈
     * @param paramMap
     * @return
     */
    @Autowired
    private ShareVideoNumToFriendCircleUtils shareVideoNumToFriendCircleUtils;
    @Override
    public MessageDTO shareVideoNumToFriendCircle(Map<String, Object> paramMap) {
        new Thread() {
            public void run() {
                try {
                    shareVideoNumToFriendCircleUtils.shareVideoNumToFriendCircle(paramMap);
                } catch (Exception e) {
                    logger.error("【service】【启动appium，分享视频号到朋友圈】 is error, paramMap : " + paramMap + ", e : ", e);
                }
            }
        }.start();
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setCode(Automation_Code.SUCCESS.getNo());
        messageDTO.setMessage(Automation_Code.SUCCESS.getMessage());
        logger.info("【service】【启动appium，分享视频号到朋友圈】，结果-result:" + messageDTO);
        return messageDTO;
    }

    /**
     * 启动appium，转发微信消息
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
                    logger.error("【service】【启动appium，转发微信消息-】 is error, paramMap : " + paramMap + ", e : ", e);
                }
            }
        }.start();
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setCode(Automation_Code.SUCCESS.getNo());
        messageDTO.setMessage(Automation_Code.SUCCESS.getMessage());
        logger.info("【service】【启动appium，转发微信消息-】，结果-result:" + messageDTO);
        return messageDTO;
    }

    /**
     * 启动appium，自动化发送微信朋友圈
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
                    logger.error("【service】【启动appium，自动化发送微信朋友圈】 is error, paramMap : " + paramMap + ", e : ", e);
                }
            }
        }.start();
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setCode(Automation_Code.SUCCESS.getNo());
        messageDTO.setMessage(Automation_Code.SUCCESS.getMessage());
        logger.info("【service】【启动appium，自动化发送微信朋友圈】，结果-result:" + messageDTO);
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
                    logger.error("【service】【启动appium，同意进群】 is error, paramMap : " + paramMap + ", e : ", e);
                }
            }
        }.start();
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setCode(Automation_Code.SUCCESS.getNo());
        messageDTO.setMessage(Automation_Code.SUCCESS.getMessage());
        logger.info("【service】【启动appium，同意进群】，结果-result:" + messageDTO);
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
                    logger.error("【service】【启动appium，点赞和评论朋友圈】 is error, paramMap : " + paramMap + ", e : ", e);
                }
            }
        }.start();
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setCode(Automation_Code.SUCCESS.getNo());
        messageDTO.setMessage(Automation_Code.SUCCESS.getMessage());
        logger.info("【service】【启动appium，点赞和评论朋友圈】，结果-result:" + messageDTO);
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
                    logger.error("【service】【启动appium，将群保存到通讯录工具】 is error, paramMap : " + paramMap + ", e : ", e);
                }
            }
        }.start();
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setCode(Automation_Code.SUCCESS.getNo());
        messageDTO.setMessage(Automation_Code.SUCCESS.getMessage());
        logger.info("【service】【启动appium，将群保存到通讯录工具】，结果-result:" + messageDTO);
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
                    logger.error("【service】【启动appium，同意好友请求】 is error, paramMap : " + paramMap + ", e : ", e);
                }
            }
        }.start();
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setCode(Automation_Code.SUCCESS.getNo());
        messageDTO.setMessage(Automation_Code.SUCCESS.getMessage());
        logger.info("【service】【启动appium，同意好友请求】，结果-result:" + messageDTO);
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
                    logger.error("【service】【启动appium，根据微信群昵称添加群成员为好友】 is error, paramMap : " + paramMap + ", e : ", e);
                }
            }
        }.start();
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setCode(Automation_Code.SUCCESS.getNo());
        messageDTO.setMessage(Automation_Code.SUCCESS.getMessage());
        logger.info("【service】【启动appium，根据微信群昵称添加群成员为好友】，结果-result:" + messageDTO);
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
                    logger.error("【service】【启动appium，根据微信昵称进行聊天】 is error, paramMap : " + paramMap + ", e : ", e);
                }
            }
        }.start();
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setCode(Automation_Code.SUCCESS.getNo());
        messageDTO.setMessage(Automation_Code.SUCCESS.getMessage());
        logger.info("【service】【启动appium，根据微信昵称进行聊天】，结果-result:" + messageDTO);
        return messageDTO;
    }

    /**
     * 前置条件：将微信文章群发到【油站科技-内部交流群】里面
     * 启动appium，分享微信文章到微信朋友圈
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
                    logger.error("【service】【启动appium，分享微信文章到微信朋友圈】 is error, paramMap : " + paramMap + ", e : ", e);
                }
            }
        }.start();
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setCode(Automation_Code.SUCCESS.getNo());
        messageDTO.setMessage(Automation_Code.SUCCESS.getMessage());
        logger.info("【service】【启动appium，分享微信文章到微信朋友圈】，结果-result:" + messageDTO);
        return messageDTO;
    }

    /**
     * 前置条件：将微信文章群发到【油站科技-内部交流群】里面
     * 启动appium，点击微信文章中的广告
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
                    logger.error("【service】【启动appium，点击微信文章中的广告】 is error, paramMap : " + paramMap + ", e : ", e);
                }
            }
        }.start();
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setCode(Automation_Code.SUCCESS.getNo());
        messageDTO.setMessage(Automation_Code.SUCCESS.getMessage());
        logger.info("【service】【启动appium，点击微信文章中的广告】，结果-result:" + messageDTO);
        return messageDTO;
    }
}

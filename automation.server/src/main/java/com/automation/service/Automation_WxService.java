package com.automation.service;

import com.automation.dto.MessageDTO;
import com.automation.dto.MessageDTO;
import com.automation.utils.wei_xin.shareVideoNumToFriendCircle.ShareVideoNumToFriendCircleUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public interface Automation_WxService {



    /**
     * 启动appium,邀请进群
     * @param paramMap
     * @return
     */
    public MessageDTO inviteToJoinTheGroup(Map<String, Object> paramMap);

    /**
     * 启动appium,分享视频号到朋友圈
     * @param paramMap
     * @return
     */
    public MessageDTO shareVideoNumToFriendCircle(Map<String, Object> paramMap);

    /**
     * 启动appium,转发微信消息
     * @param paramMap
     * @return
     */
    public MessageDTO relayTheWxMessage(Map<String, Object> paramMap);

    /**
     * 启动appium,点赞和评论朋友圈
     * @param paramMap
     * @return
     */
    public MessageDTO praiseAndCommentFriendsCircle(Map<String, Object> paramMap);

    /**
     * 启动appium,同意进群
     * @param paramMap
     * @return
     */
    public MessageDTO agreeToJoinTheGroup(Map<String, Object> paramMap);

    /**
     * 启动appium,将群保存到通讯录
     * @param paramMap
     * @return
     */
    public MessageDTO saveToAddressBook(Map<String, Object> paramMap);

    /**
     * 启动appium,进行自动化发送微信朋友圈
     * @param paramMap
     * @return
     */
    public MessageDTO sendFriendCircle(Map<String, Object> paramMap);

    /**
     * 同意好友请求
     * @param paramMap
     * @throws Exception
     */
    public MessageDTO agreeToFriendRequest(Map<String, Object> paramMap);

    /**
     * 根据微信群昵称添加群成员为好友
     * @param paramMap
     * @throws Exception
     */
    public MessageDTO addGroupMembersAsFriends(Map<String, Object> paramMap);

    /**
     * 根据微信昵称进行聊天
     * @param paramMap
     * @throws Exception
     */
    public MessageDTO chatByNickName(Map<String, Object> paramMap);

    /**
     * 前置条件：将微信文章群发到【油站科技-内部交流群】里面
     * 分享微信文章到微信朋友圈
     * @param paramMap
     * @return
     */
    public MessageDTO shareArticleToFriendCircle(Map<String, Object> paramMap);

    /**
     * 前置条件：将微信文章群发到【油站科技-内部交流群】里面
     * 点击微信文章中的广告
     * @param paramMap
     * @return
     */
    public MessageDTO clickArticleAd(Map<String, Object> paramMap);
}

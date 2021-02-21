package com.oilStationMap.service;

import com.oilStationMap.dto.BoolDTO;
import com.oilStationMap.dto.MessageDTO;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.dto.ResultMapDTO;

import java.util.Map;

/**
 * 爬虫 service
 */
public interface WX_SpiderService {

    /**
     * 从网络：５８同城、美团等网络进行爬取房产人员、美食店铺等联系方式
     * @param paramMap
     * @return
     */
    public ResultMapDTO getContactFromWeb(Map<String, Object> paramMap);

    /**
     * 启动appium,转发微信消息
     * @param paramMap
     * @return
     */
    public ResultMapDTO relayTheWxMessage(Map<String, Object> paramMap);

    /**
     * 启动appium,点赞和评论朋友圈
     * @param paramMap
     * @return
     */
    public ResultMapDTO praiseAndCommentFriendsCircle(Map<String, Object> paramMap);

    /**
     * 启动appium,同意进群
     * @param paramMap
     * @return
     */
    public ResultMapDTO agreeToJoinTheGroup(Map<String, Object> paramMap);

    /**
     * 启动appium,将群保存到通讯录
     * @param paramMap
     * @return
     */
    public ResultMapDTO saveToAddressBook(Map<String, Object> paramMap);

    /**
     * 启动appium,进行自动化发送微信朋友圈
     * @param paramMap
     * @return
     */
    public ResultMapDTO sendFriendCircle(Map<String, Object> paramMap);

    /**
     * 同意好友请求
     * @param paramMap
     * @throws Exception
     */
    public ResultMapDTO agreeToFriendRequest(Map<String, Object> paramMap);

    /**
     * 根据微信群昵称添加群成员为好友
     * @param paramMap
     * @throws Exception
     */
    public ResultMapDTO addGroupMembersAsFriends(Map<String, Object> paramMap);

    /**
     * 根据微信昵称进行聊天
     * @param paramMap
     * @throws Exception
     */
    public ResultMapDTO chatByNickName(Map<String, Object> paramMap);

    /**
     * 前置条件：将微信文章群发到【油站科技-内部交流群】里面
     * 分享微信文章到微信朋友圈
     * @param paramMap
     * @return
     */
    public ResultMapDTO shareArticleToFriendCircle(Map<String, Object> paramMap);

    /**
     * 前置条件：将微信文章群发到【油站科技-内部交流群】里面
     * 点击微信文章中的广告
     * @param paramMap
     * @return
     */
    public ResultMapDTO clickArticleAd(Map<String, Object> paramMap);
}

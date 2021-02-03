package com.oilStationMap.service.impl;

import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.service.*;
import com.oilStationMap.utils.SpiderForMeiTuanUtil;
import com.oilStationMap.utils.wxAdAutomation.addGroupMembersAsFriends.AddGroupMembersAsFriendsUtils;
import com.oilStationMap.utils.wxAdAutomation.agreeToFriendRequest.AgreeToFriendRequestUtils;
import com.oilStationMap.utils.wxAdAutomation.agreeToJoinTheGroup.AgreeToJoinTheGroupUtils;
import com.oilStationMap.utils.wxAdAutomation.chatByNickName.ChatByNickNameUtils;
import com.oilStationMap.utils.wxAdAutomation.saveToAddressBook.SaveToAddressBookUtils;
import com.oilStationMap.utils.wxAdAutomation.sendFriendCircle.SendFriendCircleUtils;
import com.oilStationMap.utils.SpiderFor58Util;
import com.oilStationMap.utils.wxAdAutomation.shareArticleToFriendCircleUtils.ShareArticleToFriendCircleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.Map;

/**
 * 爬虫service
 */
@Service
public class WX_SpiderServiceImpl implements WX_SpiderService {

    private static final Logger logger = LoggerFactory.getLogger(WX_SpiderServiceImpl.class);

    /**
     * 从网络：５８同城、美团等网络进行爬取房产人员、美食店铺等联系方式
     * @param paramMap
     * @return
     */
    @Override
    public ResultMapDTO getContactFromWeb(Map<String, Object> paramMap) {
        SpiderForMeiTuanUtil.getContactFromMeiTuanMeiShi();
        SpiderFor58Util.getContactFrom58ErShouFang();
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
        resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        logger.info("在service中从网络：５８同城、美团等网络进行爬取房产人员、美食店铺等联系方式-getContactFromWeb,结果-result:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 启动appium,自动化发送微信朋友圈
     * @param paramMap
     * @return
     */
    @Override
    public ResultMapDTO sendFriendCircle(Map<String, Object> paramMap) {
        try {
            SendFriendCircleUtils.sendFriendCircle(paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
        resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        logger.info("在service中启动appium,自动化发送微信朋友圈-sendFriendCircle,结果-result:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 同意进群
     * @param paramMap
     * @throws Exception
     */
    @Override
    public ResultMapDTO agreeToJoinTheGroup(Map<String, Object> paramMap){
        try {
            AgreeToJoinTheGroupUtils.agreeToJoinTheGroup(paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
        resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        logger.info("在service中启动appium,同意进群-saveToAddressBook,结果-result:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 将群保存到通讯录
     * @param paramMap
     * @throws Exception
     */
    @Override
    public ResultMapDTO saveToAddressBook(Map<String, Object> paramMap){
        try {
            SaveToAddressBookUtils.saveToAddressBook(paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
        resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        logger.info("在service中启动appium,将群保存到通讯录-saveToAddressBook,结果-result:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 同意好友请求
     * @param paramMap
     * @throws Exception
     */
    @Override
    public ResultMapDTO agreeToFriendRequest(Map<String, Object> paramMap){
        try {
            AgreeToFriendRequestUtils.agreeToFriendRequest(paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
        resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        logger.info("在service中启动appium,同意好友请求-agreeToFriendRequest,结果-result:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 根据微信群昵称添加群成员为好友
     * @param paramMap
     * @return
     */
    @Override
    public ResultMapDTO addGroupMembersAsFriends(Map<String, Object> paramMap) {
        try {
            AddGroupMembersAsFriendsUtils.addGroupMembersAsFriends(paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
        resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        logger.info("在service中启动appium,根据微信群昵称添加群成员为好友-addGroupMembersAsFriends,结果-result:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 根据微信昵称进行聊天
     * @param paramMap
     * @return
     */
    @Override
    public ResultMapDTO chatByNickName(Map<String, Object> paramMap) {
        ChatByNickNameUtils.chatByNickName(paramMap);
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
        resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        logger.info("在service中启动appium,根据微信昵称进行聊天-addGroupMembersAsFriends,结果-result:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 前置条件：将微信文章群发到【油站科技-内部交流群】里面
     * 启动appium,分享微信文章到微信朋友圈
     * @param paramMap
     * @return
     */
    @Override
    public ResultMapDTO shareArticleToFriendCircle(Map<String, Object> paramMap) {
        try {
            ShareArticleToFriendCircleUtils.shareArticleToFriendCircle(paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
        resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        logger.info("在service中启动appium,分享微信文章到微信朋友圈-shareArticleToFriendCircle,结果-result:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 前置条件：将微信文章群发到【油站科技-内部交流群】里面
     * 启动appium,点击微信文章中的广告
     * @param paramMap
     * @return
     */
    @Override
    public ResultMapDTO clickArticleAd(Map<String, Object> paramMap) {
//        ClickArticleAdUtils.clickArticleAd(paramMap);
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
        resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        logger.info("在service中启动appium,点击微信文章中的广告-clickArticleAd,结果-result:" + resultMapDTO);
        return resultMapDTO;
    }
}

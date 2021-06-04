package com.automation.quartz;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.automation.dto.ResultDTO;
import com.automation.service.*;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 定时任务
 */
@Component
public class TimeTaskOfXxl {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TimeTaskOfXxl.class);

    //使用环境
    @Value("${spring.profiles.active}")
    private String useEnvironmental;

    @Autowired
    private Automation_DicService automation_DicService;

    @Autowired
    private Automation_WxService automation_WxService;

    private int num = 1;

    /**
     * 测试任务
     * @param param
     * @return
     * @throws Exception
     */
    @XxlJob("do_Test")
    public ReturnT<String> do_Test(String param) throws Exception {
        logger.info("XXL-JOB, Test Hello World. num = " + this.num);
        this.num++;
        return ReturnT.SUCCESS;
    }

    /**
     * 同意好友请求-当阿里云主机迁移到部署机器时可以使用当前任务
     * @param param
     * @return
     * @throws Exception
     */
    @XxlJob("do_agreeToFriendRequest")
    public ReturnT<String> do_agreeToFriendRequest(String param) throws Exception {
        if (useEnvironmental != null && "prepub".equals(useEnvironmental)) {
            Map<String, Object> paramMap = Maps.newHashMap();
            List<String> nickNameList = Lists.newArrayList();
            try {
                //通过任务参数进行启动-发布朋友圈
                String currentDateListStr = param;
                paramMap.put("currentDateListStr", currentDateListStr);
                automation_WxService.agreeToFriendRequest(paramMap);
            } catch (Exception e) {
                logger.error("在hanlder中启动appium,同意好友请求-do_SendFriendCircle is error,即将同意好友请求 paramMap : " + param + ", e : ", e);
                automation_WxService.agreeToFriendRequest(paramMap);
            }
        }
        return ReturnT.SUCCESS;
    }

    /**
     * 添加群成员为好友的V群-当阿里云主机迁移到部署机器时可以使用当前任务
     * @param param
     * @return
     * @throws Exception
     */
    @XxlJob("do_addGroupMembersAsFriends")
    public ReturnT<String> do_addGroupMembersAsFriends(String param) throws Exception {
        if (useEnvironmental != null && "prepub".equals(useEnvironmental)) {
            Map<String, Object> paramMap = Maps.newHashMap();
            List<String> nickNameList = Lists.newArrayList();
            try {
                //通过任务参数进行启动-发布朋友圈
                String nickNameListStr = param;
                nickNameList = JSONObject.parseObject(nickNameListStr, List.class);
                paramMap.put("nickNameListStr", nickNameListStr);
                automation_WxService.sendFriendCircle(paramMap);
            } catch (Exception e) {
                logger.error("在hanlder中启动appium,添加群成员为好友的V群-do_SendFriendCircle is error,即将通过数据库添加群成员为好友的V群 paramMap : " + param + ", e : ", e);
                //直接从现有的数据库中获取数据启动-发布朋友圈
                paramMap.put("dicType", "addGroupMembersAsFriends");
                ResultDTO resultDTO = automation_DicService.getSimpleDicByCondition(paramMap);
                if(resultDTO != null && resultDTO.getResultList() != null && resultDTO.getResultList().size() > 0){
                    for(Map<String, String> addGroupMembersAsFriendsMap : resultDTO.getResultList()){
                        nickNameList.add(addGroupMembersAsFriendsMap.get("dicCode"));
                    }
                }
                paramMap.clear();
                paramMap.put("nickNameListStr", JSONObject.toJSONString(nickNameList));
                automation_WxService.addGroupMembersAsFriends(paramMap);
            }
        }
        return ReturnT.SUCCESS;
    }

    /**
     * 发布朋友圈-当阿里云主机迁移到部署机器时可以使用当前任务
     * @param param
     * @return
     * @throws Exception
     */
    @XxlJob("do_SendFriendCircle")
    public ReturnT<String> do_SendFriendCircle(String param) throws Exception {
        if (useEnvironmental != null && "prepub".equals(useEnvironmental)) {
            Map<String, Object> paramMap = Maps.newHashMap();
            List<String> nickNameList = Lists.newArrayList();
            try {
                //通过任务参数进行启动-发布朋友圈
                String nickNameListStr = param;
                nickNameList = JSONObject.parseObject(nickNameListStr, List.class);
                paramMap.put("nickNameListStr", nickNameListStr);
                automation_WxService.sendFriendCircle(paramMap);
            } catch (Exception e) {
                logger.error("在hanlder中启动appium,自动化发送微信朋友圈-do_SendFriendCircle is error,即将通过数据库获取数据发送朋友圈 paramMap : " + param + ", e : ", e);
                //直接从现有的数据库中获取数据启动-发布朋友圈
                paramMap.put("dicType", "sendFriendCircle");
                ResultDTO resultDTO = automation_DicService.getSimpleDicByCondition(paramMap);
                if(resultDTO != null && resultDTO.getResultList() != null && resultDTO.getResultList().size() > 0){
                    for(Map<String, String> sendFriendCircleMap : resultDTO.getResultList()){
                        nickNameList.add(sendFriendCircleMap.get("dicCode"));
                    }
                }
                paramMap.clear();
                paramMap.put("nickNameListStr", JSONObject.toJSONString(nickNameList));
                automation_WxService.sendFriendCircle(paramMap);
            }
        }
        return ReturnT.SUCCESS;
    }

    /**
     * 分享微信文章到微信朋友圈-当阿里云主机迁移到部署机器时可以使用当前任务
     * @param param
     * @return
     * @throws Exception
     */
    @XxlJob("do_ShareArticleToFriendCircle")
    public ReturnT<String> do_ShareArticleToFriendCircle(String param) throws Exception {
        if (useEnvironmental != null && "prepub".equals(useEnvironmental)) {
            Map<String, Object> paramMap = Maps.newHashMap();
            List<String> nickNameList = Lists.newArrayList();
            try {
                //通过任务参数进行启动-分享微信文章到微信朋友圈
                String nickNameListStr = param;
                nickNameList = JSONObject.parseObject(nickNameListStr, List.class);
                paramMap.put("nickNameListStr", nickNameListStr);
                automation_WxService.shareArticleToFriendCircle(paramMap);
            } catch (Exception e) {
                logger.error("在hanlder中启动appium,分享微信文章到微信朋友圈-shareArticleToFriendCircle is error, 即将通过数据库获取数据分享微信文章到微信朋友圈 paramMap : " + param + ", e : ", e);//直接从现有的数据库中获取数据启动-发布朋友圈
                paramMap.put("dicType", "shareArticleToFriendCircle");
                ResultDTO resultDTO = automation_DicService.getSimpleDicByCondition(paramMap);
                if(resultDTO != null && resultDTO.getResultList() != null && resultDTO.getResultList().size() > 0){
                    for(Map<String, String> sendFriendCircleMap : resultDTO.getResultList()){
                        nickNameList.add(sendFriendCircleMap.get("dicCode"));
                    }
                }
                paramMap.clear();
                paramMap.put("nickNameListStr", JSONObject.toJSONString(nickNameList));
                automation_WxService.shareArticleToFriendCircle(paramMap);
            }
        }
        return ReturnT.SUCCESS;
    }
}

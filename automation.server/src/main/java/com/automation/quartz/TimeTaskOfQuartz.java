package com.automation.quartz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.automation.config.GlobalVariableConfig;
import com.automation.dao.XXL_JobInfoDao;
import com.automation.dto.ResultDTO;
import com.automation.service.*;
import com.automation.utils.CommandUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 定时任务
 *
 * @Scheduled下次任务执行开始将在本次任务执行完毕后才开始
 */
@Component
@Lazy(false)
public class TimeTaskOfQuartz {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TimeTaskOfQuartz.class);

    //使用环境
    @Value("${spring.profiles.active}")
    private String useEnvironmental;

    @Autowired
    private Automation_DicService automation_DicService;

    @Autowired
    private XXL_JobInfoDao xxl_JobInfoDao;

    @Autowired
    private Automation_WxService automation_WxService;

    @Autowired
    private GlobalVariableConfig globalVariableConfig;

    /**
     * 每天小时第1分钟执行一次，根据数据库的配置：从第06个小时开始执行第一个设备
     * 分享微信文章到微信朋友圈，同时，根据微信昵称进行聊天，通知对方
     */
    @Scheduled(cron = "0 01 */1 * * ?")
    public void do_shareArticleToFriendCircle() {
        if ("develop".equals(useEnvironmental)) {
            Map<String, Object> paramMap = Maps.newHashMap();
            try {
                paramMap.clear();
                List<String> nickNameList = Lists.newArrayList();
                paramMap.put("start", 0);
                paramMap.put("size", 10);
                paramMap.put("id", "14");       // jobDesc --->>> 分享文章l链接到朋友圈
                List<Map<String, Object>> list = xxl_JobInfoDao.getSimpleJobInfoByCondition(paramMap);
                if (list != null && list.size() > 0) {
                    Map<String, Object> sendFriendCircleJobInfoMap = list.get(0);
                    String nickNameListStr = sendFriendCircleJobInfoMap.get("executorParam") != null ? sendFriendCircleJobInfoMap.get("executorParam").toString() : "";
                    paramMap.clear();
                    nickNameList = JSONObject.parseObject(nickNameListStr, List.class);
                    paramMap.put("nickNameListStr", nickNameListStr);
                    automation_WxService.shareArticleToFriendCircle(paramMap);
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                logger.error("在hanlder中启动appium,分享微信文章到微信朋友圈-shareArticleToFriendCircle is error, 即将通过数据库获取数据分享微信文章到微信朋友圈 paramMap : " + paramMap);
                try{
                    //获取设备列表
                    LinkedList<String> currentDeviceList = Lists.newLinkedList();
                    String currentHour = new SimpleDateFormat("HH").format(new Date());
                    {
                        paramMap.clear();
                        paramMap.put("dicType", "deviceNameListAndLocaltion");
                        paramMap.put("dicCode", "HuaWeiListAndShareArticleToFriendCircleLocaltion");
                        ResultDTO resultDTO = automation_DicService.getSimpleDicByCondition(paramMap);
                        if (resultDTO != null && resultDTO.getResultList() != null && resultDTO.getResultList().size() > 0) {
                            for (Map<String, String> sendFriendCircleMap : resultDTO.getResultList()) {
                                String deviceNameListStr = sendFriendCircleMap.get("deviceNameList");
                                List<HashMap<String, Object>> deviceNameList = JSONObject.parseObject(deviceNameListStr, List.class);
                                if (deviceNameList != null && deviceNameList.size() > 0) {
                                    for (Map<String, Object> deviceNameMap : deviceNameList) {
                                        //当前设备描述
                                        String deviceNameDesc = deviceNameMap.get("deviceNameDesc") != null ? deviceNameMap.get("deviceNameDesc").toString() : null;
                                        String deviceStartHour = deviceNameDesc.contains("_") ? deviceNameDesc.split("_")[1] : null;
                                        if(currentHour.equals(deviceStartHour)){
                                            currentDeviceList.add(deviceNameDesc);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    //获取昵称列表
                    List<String> nickNameList = Lists.newArrayList();
//                    {
//                        paramMap.clear();
//                        paramMap.put("dicType", "shareArticleToFriendCircle");
//                        ResultDTO resultDTO = automation_DicService.getSimpleDicByCondition(paramMap);
//                        if (resultDTO != null && resultDTO.getResultList() != null && resultDTO.getResultList().size() > 0) {
//                            for (Map<String, String> sendFriendCircleMap : resultDTO.getResultList()) {
//                                nickNameList.add(sendFriendCircleMap.get("dicCode"));
//                            }
//                        }
//                    }

                    //准备参数
                    paramMap.clear();
                    paramMap.put("nickNameListStr", JSONObject.toJSONString(nickNameList));
                    paramMap.put("currentDeviceListStr", JSONObject.toJSONString(currentDeviceList));
                    automation_WxService.shareArticleToFriendCircle(paramMap);
                } catch (Exception error) {
                    error.printStackTrace();
                }
            }
        }
    }

    /**
     * 每天小时第1分钟执行一次，根据数据库的配置：从第08个小时开始执行第一个设备
     * 发送朋友圈，同时，根据微信昵称进行聊天，通知对方
     */
    @Scheduled(cron = "0 01 */1 * * ?")
    public void do_sendFriendCircle() {
        if ("develop".equals(useEnvironmental)) {
            Map<String, Object> paramMap = Maps.newHashMap();
            try {
                paramMap.clear();
                List<String> nickNameList = Lists.newArrayList();
                paramMap.put("start", 0);
                paramMap.put("size", 10);
                paramMap.put("id", "10");       // jobDesc --->>> 发布图片/文字到朋友圈
                List<Map<String, Object>> list = xxl_JobInfoDao.getSimpleJobInfoByCondition(paramMap);
                if (list != null && list.size() > 0) {
                    Map<String, Object> sendFriendCircleJobInfoMap = list.get(0);
                    String nickNameListStr = sendFriendCircleJobInfoMap.get("executorParam") != null ? sendFriendCircleJobInfoMap.get("executorParam").toString() : "";
                    paramMap.clear();
                    nickNameList = JSONObject.parseObject(nickNameListStr, List.class);
                    paramMap.put("nickNameListStr", nickNameListStr);
                    automation_WxService.sendFriendCircle(paramMap);
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                logger.error("在hanlder中启动appium,自动化发送微信朋友圈-sendFriendCircle is error, 即将通过数据库获取数据发送朋友圈 paramMap : " + paramMap);
                try {
                    //获取设备列表
                    LinkedList<String> currentDeviceList = Lists.newLinkedList();
                    String currentHour = new SimpleDateFormat("HH").format(new Date());
                    {
                        paramMap.clear();
                        paramMap.put("dicType", "deviceNameListAndLocaltion");
                        paramMap.put("dicCode", "HuaWeiListAndSendFriendCircleLocaltion");
                        ResultDTO resultDTO = automation_DicService.getSimpleDicByCondition(paramMap);
                        if (resultDTO != null && resultDTO.getResultList() != null && resultDTO.getResultList().size() > 0) {
                            for (Map<String, String> sendFriendCircleMap : resultDTO.getResultList()) {
                                String deviceNameListStr = sendFriendCircleMap.get("deviceNameList");
                                List<HashMap<String, Object>> deviceNameList = JSONObject.parseObject(deviceNameListStr, List.class);
                                if (deviceNameList != null && deviceNameList.size() > 0) {
                                    for (Map<String, Object> deviceNameMap : deviceNameList) {
                                        //当前设备描述
                                        String deviceNameDesc = deviceNameMap.get("deviceNameDesc") != null ? deviceNameMap.get("deviceNameDesc").toString() : null;
                                        String deviceStartHour = deviceNameDesc.contains("_") ? deviceNameDesc.split("_")[1] : null;
                                        if(currentHour.equals(deviceStartHour)){
                                            currentDeviceList.add(deviceNameDesc);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    //获取昵称列表，默所有朋友圈
                    List<String> nickNameList = Lists.newArrayList();
//                    {
//                        paramMap.clear();
//                        paramMap.put("dicType", "sendFriendCircle");
//                        ResultDTO resultDTO = automation_DicService.getSimpleDicByCondition(paramMap);
//                        if (resultDTO != null && resultDTO.getResultList() != null && resultDTO.getResultList().size() > 0) {
//                            for (Map<String, String> sendFriendCircleMap : resultDTO.getResultList()) {
//                                nickNameList.add(sendFriendCircleMap.get("dicCode"));
//                            }
//                        }
//                    }

                    //准备参数
                    paramMap.clear();
                    paramMap.put("currentDeviceListStr", JSONObject.toJSONString(currentDeviceList));
                    paramMap.put("nickNameListStr", JSONObject.toJSONString(nickNameList));
                    automation_WxService.sendFriendCircle(paramMap);
                } catch (Exception error) {
                    error.printStackTrace();
                }
            }
        }
    }

    /**
     * 每天小时第1分钟执行一次，根据数据库的配置：从第10个小时开始执行第一个设备
     * 添加群成员为好友的V群
     */
    @Scheduled(cron = "0 01 */1 * * ?")
    public void do_addGroupMembersAsFriends() {
        if ("develop".equals(useEnvironmental)) {
            Map<String, Object> paramMap = Maps.newHashMap();
            try {
                paramMap.clear();
                List<String> nickNameList = Lists.newArrayList();
                paramMap.put("start", 0);
                paramMap.put("size", 10);
                paramMap.put("id", "12");       // jobDesc --->>> 添加群成员为好友的V群
                List<Map<String, Object>> list = xxl_JobInfoDao.getSimpleJobInfoByCondition(paramMap);
                if (list != null && list.size() > 0) {
                    Map<String, Object> addGroupMembersAsFriendsMap = list.get(0);
                    String nickNameListStr = addGroupMembersAsFriendsMap.get("executorParam") != null ? addGroupMembersAsFriendsMap.get("executorParam").toString() : "";
                    paramMap.clear();
                    nickNameList = JSONObject.parseObject(nickNameListStr, List.class);
                    paramMap.put("nickNameListStr", nickNameListStr);
                    automation_WxService.addGroupMembersAsFriends(paramMap);
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                logger.error("在hanlder中启动appium,添加群成员为好友的V群-addGroupMembersAsFriends is error, 即将通过数据库添加群成员为好友的V群 paramMap : " + JSON.toJSONString(paramMap));
                try{
                    //获取设备列表
                    LinkedList<String> currentDeviceList = Lists.newLinkedList();
                    String currentHour = new SimpleDateFormat("HH").format(new Date());
                    {
                        paramMap.clear();
                        paramMap.put("dicType", "deviceNameListAndLocaltion");
                        paramMap.put("dicCode", "HuaWeiListAndAddGroupMembersAsFriendsLocaltion");
                        ResultDTO resultDTO = automation_DicService.getSimpleDicByCondition(paramMap);
                        if (resultDTO != null && resultDTO.getResultList() != null && resultDTO.getResultList().size() > 0) {
                            for (Map<String, String> sendFriendCircleMap : resultDTO.getResultList()) {
                                String deviceNameListStr = sendFriendCircleMap.get("deviceNameList");
                                List<HashMap<String, Object>> deviceNameList = JSONObject.parseObject(deviceNameListStr, List.class);
                                if (deviceNameList != null && deviceNameList.size() > 0) {
                                    for (Map<String, Object> deviceNameMap : deviceNameList) {
                                        //当前设备描述
                                        String deviceNameDesc = deviceNameMap.get("deviceNameDesc") != null ? deviceNameMap.get("deviceNameDesc").toString() : null;
                                        String deviceStartHour = deviceNameDesc.contains("_") ? deviceNameDesc.split("_")[1] : null;
                                        if(currentHour.equals(deviceStartHour)){
                                            currentDeviceList.add(deviceNameDesc);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    //获取昵称列表
                    List<String> nickNameList = Lists.newArrayList();
                    {
                        paramMap.clear();
                        nickNameList.clear();
                        paramMap.put("dicType", "addGroupMembersAsFriends");
                        ResultDTO resultDTO = automation_DicService.getSimpleDicByCondition(paramMap);
                        if (resultDTO != null && resultDTO.getResultList() != null && resultDTO.getResultList().size() > 0) {
                            for (Map<String, String> sendFriendCircleMap : resultDTO.getResultList()) {
                                nickNameList.add(sendFriendCircleMap.get("dicCode"));
                            }
                        }
                    }

                    paramMap.clear();
                    paramMap.put("currentDeviceListStr", JSONObject.toJSONString(currentDeviceList));
                    paramMap.put("nickNameListStr", JSONObject.toJSONString(nickNameList));
                    automation_WxService.addGroupMembersAsFriends(paramMap);
                } catch (Exception error) {
                    error.printStackTrace();
                }
            }
        }
    }

    /**
     * 每周五下午15点开始执行
     * 同意好友请求
     */
    @Scheduled(cron = "0 0 15 * * FRI")
    public void do_agreeToFriendRequest() {
        Date currentDate = new Date();
        if ("develop".equals(useEnvironmental)) {
            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("currentDate", currentDate);
            try {
                paramMap.clear();
                List<String> currentDeviceList = Lists.newArrayList();
                paramMap.put("start", 0);
                paramMap.put("size", 10);
                paramMap.put("id", "13");       // jobDesc --->>> 同意好友请求
                List<Map<String, Object>> list = xxl_JobInfoDao.getSimpleJobInfoByCondition(paramMap);
                if (list != null && list.size() > 0) {
                    Map<String, Object> addGroupMembersAsFriendsMap = list.get(0);
                    String currentDeviceListStr = addGroupMembersAsFriendsMap.get("executorParam") != null ? addGroupMembersAsFriendsMap.get("executorParam").toString() : "";
                    paramMap.clear();
                    paramMap.put("currentDateListStr", currentDeviceListStr);
                    automation_WxService.addGroupMembersAsFriends(paramMap);
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                logger.error("在hanlder中启动appium,同意好友请求-agreeToFriendRequest is error, 即将通过数据库同意好友请求 paramMap : " + JSON.toJSONString(paramMap));
                try{
                    paramMap.clear();
                    LinkedList<String> currentDeviceList = Lists.newLinkedList();
                    String currentDeviceListStr = "[\n" +
                            "    \"小米Max3_10\",\n" +
                            "    \"华为Mate8_11\",\n" +
                            "    \"华为Mate8_12\",\n" +
                            "    \"华为Mate8_13\",\n" +
                            "    \"华为Mate8_14\",\n" +
                            "    \"华为Mate8_15\",\n" +
                            "    \"华为Mate8_16\",\n" +
                            "    \"华为Mate8_17\",\n" +
                            "    \"华为Mate8海外版_18\",\n" +
                            "    \"华为Mate8_19\",\n" +
                            "    \"华为Mate8_20\",\n" +
                            "    \"华为Mate8_21\"\n" +
                            "]";
                    currentDeviceList = JSON.parseObject(currentDeviceListStr, LinkedList.class);
                    paramMap.put("currentDeviceListStr", JSONObject.toJSONString(currentDeviceList));
                    automation_WxService.saveToAddressBook(paramMap);            //将群保存到通讯录
                    automation_WxService.agreeToFriendRequest(paramMap);         //同意好友请求
                } catch (Exception error) {
                    error.printStackTrace();
                }
            }
        }
    }

    /**
     * 每分钟检测GlobalVariableConfig的使用情况
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void do_checkGlobalVariableConfig() {
        globalVariableConfig.initGlobalVariableAndServer();
        logger.info("每分钟检测GlobalVariableConfig的使用情况：" + JSON.toJSONString(GlobalVariableConfig.appiumPortMap));
    }

    /**
     * 每天凌晨5点重启所有Android设备
     */
    @Scheduled(cron = "0 0 5 * * ?")
    public void do_rebootAllAndroidDevices() {
        try{
            CommandUtil.run("/opt/defaultCommodPath/rebootAllAndroidDevices.sh");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            logger.info("每天凌晨5点重启所有Android设备.");
        }
    }

    /**
     * 每天凌晨5点30分息屏所有Android设备
     */
    @Scheduled(cron = "0 30 5 * * ?")
    public void do_turnOffTheScreenForAllAndroidDevices() {
        try{
            CommandUtil.run("/opt/defaultCommodPath/turnOffTheScreenForAllAndroidDevices.sh");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            logger.info("每天凌晨5点30分息屏所有Android设备.");
        }
    }

    /**
     * 每间隔10分钟息屏对当前电脑
     */
    @Scheduled(cron = "0 30 5 * * ?")
    public void do_turnOffTheScreenForComputer() {
        try{
            CommandUtil.run("/opt/defaultCommodPath/turnOffTheScreenForComputer.sh");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            logger.info("每间隔10分钟息屏对当前电脑.");
        }
    }
}


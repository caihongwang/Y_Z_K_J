package com.automation.utils.wei_xin.addGroupMembersAsFriends;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.automation.service.Automation_MailService;
import com.automation.utils.EmojiUtil;
import com.automation.utils.MapUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.automation.config.GlobalVariableConfig;
import com.automation.dao.Automation_DicDao;
import com.automation.dto.ResultDTO;
import com.automation.service.Automation_DicService;
import com.automation.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 根据微信群昵称添加群成员为好友
 * 添加群成员为好友的V群
 * appium -p 4723 -bp 4724 --session-override --command-timeout 600
 */
@Component
public class AddGroupMembersAsFriendsUtils {

    public static final Logger logger = LoggerFactory.getLogger(AddGroupMembersAsFriendsUtils.class);

    @Autowired
    public Automation_DicDao automation_DicDao;

    @Autowired
    public Automation_MailService automation_MailService;

    @Autowired
    public Automation_DicService automation_DicService;

    /**
     * 根据微信群昵称添加群成员为好友工具for所有设备
     */
    public void addGroupMembersAsFriends(Map<String, Object> paramMap) throws Exception {
        String nickNameListStr = paramMap.get("nickNameListStr") != null ? paramMap.get("nickNameListStr").toString() : "";
        String currentDeviceListStr = paramMap.get("currentDeviceListStr") != null ? paramMap.get("currentDeviceListStr").toString() : "";
        LinkedList<String> currentDeviceList = Lists.newLinkedList();
        try {
            currentDeviceList = JSON.parseObject(currentDeviceListStr, LinkedList.class);
        } catch (Exception e) {
            throw new Exception("解析json设备列表失败，currentDeviceListStr = " + currentDeviceListStr + " ， e : ", e);
        } finally {
            if (currentDeviceList.size() <= 0) {
                currentDeviceList.add("小米Max3_10");
            }
        }
        LinkedList<String> nickNameList = Lists.newLinkedList();
        try {
            nickNameList = JSONObject.parseObject(nickNameListStr, LinkedList.class);
        } catch (Exception e) {
            logger.info("解析json失败，nickNameListStr = " + nickNameListStr);
            nickNameList = Lists.newLinkedList();
        }

        //appiumPort
        String appiumPort = null;
        //设备编码
        String deviceName = "未知-设备编码";
        //设备描述
        String deviceNameDesc = "未知-设备描述";
        //设备执行小时
        String deviceStartHour = "未知-设备时间";
        //当前 自动化操作 添加群成员为好友的V群
        String action = "addGroupMembersAsFriends";
        //获取 添加群成员为好友的V群 设备列表和配套的坐标配置
        String deviceNameListAnddeviceLocaltionOfCode = "HuaWeiListAndAddGroupMembersAsFriendsLocaltion";
        //获取群的昵称，循环遍历，默认发送数据库中的添加群成员为好友的V群
        List<Map<String, String>> addGroupMembersAsFriendList = Lists.newLinkedList();
        if (nickNameList == null || nickNameList.size() <= 0) {
            paramMap.clear();
            paramMap.put("dicType", "addGroupMembersAsFriends");
            ResultDTO resultDTO = automation_DicService.getSimpleDicByCondition(paramMap);
            addGroupMembersAsFriendList = resultDTO.getResultList();
        } else {
            for (String nickName : nickNameList) {
                paramMap.clear();
                paramMap.put("dicType", "addGroupMembersAsFriends");
                paramMap.put("dicCode", nickName);        //指定 某一个分享微信文章到微信朋友圈 发送
                ResultDTO resultDTO = automation_DicService.getSimpleDicByCondition(paramMap);
                addGroupMembersAsFriendList.addAll(resultDTO.getResultList());
            }
        }
        //获取设备列表和配套的坐标配置
        paramMap.clear();
        paramMap.put("dicType", "deviceNameListAndLocaltion");
        paramMap.put("dicCode", deviceNameListAnddeviceLocaltionOfCode);
        List<Map<String, Object>> deviceNameAndLocaltionList = automation_DicDao.getSimpleDicByCondition(paramMap);
        if (deviceNameAndLocaltionList == null && deviceNameAndLocaltionList.size() <= 0) {
            logger.info("【添加群成员为好友的V群】" + deviceNameListAnddeviceLocaltionOfCode + " 设备列表和配套的坐标配置 不存在，请使用adb命令查询设备号并入库.");
            throw new Exception("【添加群成员为好友的V群】" + deviceNameListAnddeviceLocaltionOfCode + " 设备列表和配套的坐标配置 不存在，请使用adb命令查询设备号并入库.");
        }
        Map<String, Object> deviceNameAndLocaltionMap = deviceNameAndLocaltionList.get(0);
        for (String currentDevice : currentDeviceList) {
            try {
                //获取当前时间，用于校验【那台设备】在【当前时间】执行【当前自动化操作】
                String currentHour = currentDevice.contains("_") ? currentDevice.split("_")[1] : null;
                for (Map<String, String> addGroupMembersAsFriends : addGroupMembersAsFriendList) {
                    Map<String, Object> addGroupMembersAsFriendsParam = Maps.newHashMap();
                    HashMap<String, Object> reboot_addGroupMembersAsFriendsParam = Maps.newHashMap();
                    try {
                        boolean isOperatedFlag = false;     //当前设备是否操作【添加群成员为好友的V群】的标志位
                        String nickName = addGroupMembersAsFriends.get("dicCode");
                        //根据微信群昵称添加群成员为好友.
                        addGroupMembersAsFriendsParam.putAll(MapUtil.getObjectMap(addGroupMembersAsFriends));
                        addGroupMembersAsFriendsParam.put("nickName", nickName);
                        String theId = addGroupMembersAsFriendsParam.get("id").toString();
                        //获取dicRemark
                        String deviceNameAndLocaltionStr = deviceNameAndLocaltionMap.get("dicRemark") != null ? deviceNameAndLocaltionMap.get("dicRemark").toString() : "";
                        JSONObject deviceNameAndLocaltionJSONObject = JSONObject.parseObject(deviceNameAndLocaltionStr);
                        //获取设备坐标
                        String deviceLocaltionStr = deviceNameAndLocaltionJSONObject.getString("deviceLocaltion");
                        Map<String, Object> deviceLocaltionMap = JSONObject.parseObject(deviceLocaltionStr, Map.class);
                        addGroupMembersAsFriendsParam.putAll(deviceLocaltionMap);
                        //获取设备列表
                        String deviceNameListStr = deviceNameAndLocaltionJSONObject.getString("deviceNameList");
                        List<HashMap<String, Object>> deviceNameList = JSONObject.parseObject(deviceNameListStr, List.class);
                        if (deviceNameList != null && deviceNameList.size() > 0) {
                            for (Map<String, Object> deviceNameMap : deviceNameList) {
                                addGroupMembersAsFriendsParam.putAll(deviceNameMap);
                                //当前设备编码
                                deviceName = addGroupMembersAsFriendsParam.get("deviceName") != null ? addGroupMembersAsFriendsParam.get("deviceName").toString() : null;
                                //当前设备描述
                                deviceNameDesc = addGroupMembersAsFriendsParam.get("deviceNameDesc") != null ? addGroupMembersAsFriendsParam.get("deviceNameDesc").toString() : null;
                                //当前设备执行小时
                                deviceStartHour = deviceNameDesc.contains("_") ? deviceNameDesc.split("_")[1] : null;
                                //目标设备描述-即群对应设备描述targetDeviceNameDesc
                                String targetDeviceNameDesc = addGroupMembersAsFriendsParam.get("targetDeviceNameDesc") != null ? addGroupMembersAsFriendsParam.get("targetDeviceNameDesc").toString() : null;
                                //群的指定目标的设备与当前的设备不符合直接continue
                                if (deviceStartHour == null || deviceNameDesc == null || targetDeviceNameDesc == null || !targetDeviceNameDesc.equals(deviceNameDesc)) {
                                    continue;
                                }

                                //判断当前设备的执行小时时间是否与当前时间匹配
                                boolean isExecuteFlag = false;
                                if (deviceStartHour.equals(currentHour)) {    //当前设备在规定的执行时间才执行自动化操作，同时获取对应的appium端口号
                                    if (CommandUtil.isOnline4AndroidDevice(deviceName)) {
                                        try {
                                            //获取appium端口号
                                            appiumPort = GlobalVariableConfig.getAppiumPort(action, deviceNameDesc);
                                            addGroupMembersAsFriendsParam.put("appiumPort", appiumPort);
                                            //设置当前这杯可执行的标志位
                                            isExecuteFlag = true;
                                        } catch (Exception e) {
                                            //获取appium端口号失败
                                            logger.error("【添加群成员为好友的V群】" + e.getMessage());
                                            //设置当前这杯可被行的标志位
                                            isExecuteFlag = false;
                                            continue;
                                        }
                                    } else {
                                        //邮件通知，当前设备不在线，Usb接口不稳定断电或者手机被关机或者断电点，需要人工进行排查...
                                        StringBuffer mailMessageBuf = new StringBuffer();
                                        mailMessageBuf.append("蔡红旺，您好：\n");
                                        mailMessageBuf.append("        ").append("\t操作名称：添加群成员为好友的V群").append("\n");
                                        mailMessageBuf.append("        ").append("\t微信群名：").append(nickName).append("\n");
                                        mailMessageBuf.append("        ").append("\t操作设备：").append(deviceNameDesc).append("\n");
                                        mailMessageBuf.append("        ").append("\t异常时间：").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("\n");
                                        mailMessageBuf.append("        ").append("\t异常地点：").append("北京市昌平区").append("\n");
                                        mailMessageBuf.append("        ").append("\t温馨提示：").append("请检查以下手机的接口，并手动辅助自动化操作.").append("\n");
                                        mailMessageBuf.append("        ").append("\t异常原因描述：").append("当前设备不在线，Usb接口不稳定断电或者手机被关机或者断电点，需要人工进行排查...").append("\n");
                                        paramMap.clear();
                                        paramMap.put("to", "caihongwang@dingtalk.com");
                                        paramMap.put("subject", "【服务异常通知】添加群成员为好友的V群");
                                        paramMap.put("content", mailMessageBuf.toString());
                                        automation_MailService.sendSimpleMail(paramMap);
                                        logger.info("【邮件通知】【服务异常通知】添加群成员为好友的V群 ......");
                                    }
                                } else {
                                    isExecuteFlag = false;
                                    logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】，当前设备的执行时间第【" + deviceStartHour + "】小时，当前时间是第【" + currentHour + "】小时....");
                                    continue;
                                }
                                try {
                                    if (isExecuteFlag) {
                                        //开始【添加群成员为好友的V群】
                                        logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】即将开始....");
                                        isOperatedFlag = new RealMachineDevices().addGroupMembersAsFriends(addGroupMembersAsFriendsParam);
                                        Thread.sleep(5000);
//                                        //测试
//                                        isOperatedFlag = true;
//                                        reboot_addGroupMembersAsFriendsParam.putAll(addGroupMembersAsFriendsParam);
//                                        Thread.sleep(1000);
                                        break;      //后面时间段的设备不需要执行，因为每个时间段只有个设备可被执行
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    reboot_addGroupMembersAsFriendsParam.putAll(addGroupMembersAsFriendsParam);
                                    break;      //后面时间段的设备不需要执行，因为每个时间段只有个设备可被执行
                                }
                            }
                        }

                        //4.对执行失败的设备进行重新执行【添加群成员为好友的V群】,最多重复执行15次，每间隔4次重启一次手机
                        Integer index = 1;
                        while (reboot_addGroupMembersAsFriendsParam.size() > 0) {
                            //等待所有设备重启
                            Thread.sleep(45000);
//                            //测试
//                            Thread.sleep(1000);
                            if (index > 15) {
                                break;
                            }
                            logger.info("【添加群成员为好友的V群】第【" + index + "】次重新执行设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】...");
                            try {
                                logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】即将开始....");
                                isOperatedFlag = new RealMachineDevices().addGroupMembersAsFriends(reboot_addGroupMembersAsFriendsParam);
                                reboot_addGroupMembersAsFriendsParam.clear();
                                Thread.sleep(5000);
//                                //测试
//                                if(index==15){
//                                    isOperatedFlag = true;
//                                    reboot_addGroupMembersAsFriendsParam.clear();
//                                }
//                                Thread.sleep(1000);
                            } catch (Exception e) {     //当运行设备异常之后，就会对当前设备进行记录，准备重启，后续再对此设备进行重新执行
                                e.printStackTrace();
//                                try {
//                                    if (index % 4 == 0) {
//                                        //【添加群成员为好友的V群】过程中，出现不会对设备进行重启，所以在重新执行的单个过程出现异常则重启
//                                        CommandUtil.run("/opt/android_sdk/platform-tools/adb -s " + deviceName + " reboot");
//                                        logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】重启成功...");
//                                    }
//                                } catch (Exception e1) {
//                                    logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】重启失败...");
//                                }
                            }
                            index++;
                        }

                        //回收-appiumPort
                        GlobalVariableConfig.recoveryAppiumPort(appiumPort);

                        //6.发送微信通知消息进行手动录入.
                        if (reboot_addGroupMembersAsFriendsParam.size() > 0) {
                            logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                            logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                            logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                            logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                            logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                            String exceptionDevices = "异常设备列表" + "【" + deviceNameDesc + "】";
                            logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                            logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                            logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                            logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                            logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                            //邮件通知
                            StringBuffer mailMessageBuf = new StringBuffer();
                            mailMessageBuf.append("蔡红旺，您好：\n");
                            mailMessageBuf.append("        ").append("\t操作名称：添加群成员为好友的V群").append("\n");
                            mailMessageBuf.append("        ").append("\t微信群名：").append(nickName).append("\n");
                            mailMessageBuf.append("        ").append("\t操作设备：").append(exceptionDevices).append("\n");
                            mailMessageBuf.append("        ").append("\t异常时间：").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("\n");
                            mailMessageBuf.append("        ").append("\t异常地点：").append("北京市昌平区").append("\n");
                            mailMessageBuf.append("        ").append("\t温馨提示：").append("请检查以下手机的接口，并手动辅助自动化操作.").append("\n");
                            mailMessageBuf.append("        ").append("\t异常原因描述：").append("Usb接口不稳定断电或者微信版本已被更新导致坐标不匹配").append("\n");
                            paramMap.clear();
                            paramMap.put("to", "caihongwang@dingtalk.com");
                            paramMap.put("subject", "【服务异常通知】添加群成员为好友的V群");
                            paramMap.put("content", mailMessageBuf.toString());
                            automation_MailService.sendSimpleMail(paramMap);
                            logger.info("【邮件通知】【服务异常通知】添加群成员为好友的V群 ......");
                        } else {
                            if (isOperatedFlag) {            //当前设备是否操作【添加群成员为好友的V群】的标志位
                                //获取当前群的更新新消息
                                String groupMembersMapStr = addGroupMembersAsFriendsParam.get("groupMembersMapStr").toString();
                                //更新这个群的信息
                                try {
                                    Map<String, Object> tempMap = Maps.newHashMap();
                                    tempMap.put("id", theId);
                                    LinkedHashMap<String, Object> dicRemarkMap = Maps.newLinkedHashMap();
                                    dicRemarkMap.put("targetDeviceNameDesc", addGroupMembersAsFriendsParam.get("targetDeviceNameDesc"));
                                    dicRemarkMap.put("startAddFrirndTotalNumStr", addGroupMembersAsFriendsParam.get("startAddFrirndTotalNumStr"));
                                    dicRemarkMap.put("nickName", nickName);
                                    dicRemarkMap.put("action", addGroupMembersAsFriendsParam.get("action"));
                                    dicRemarkMap.put("addFrirndTotalNumStr", addGroupMembersAsFriendsParam.get("addFrirndTotalNumStr"));
                                    dicRemarkMap.put("groupMembersMapStr", EmojiUtil.emojiConvert(addGroupMembersAsFriendsParam.get("groupMembersMapStr").toString()));
                                    tempMap.put("dicRemark", EmojiUtil.emojiConvert(JSON.toJSONString(dicRemarkMap)));
                                    tempMap.put("dicStatus", 1);
                                    LinkedHashMap<String, Map<String, String>> groupMembersMap = JSON.parseObject(groupMembersMapStr, LinkedHashMap.class);
                                    if (groupMembersMap.size() > 0) {
                                        //循环遍历所有群成员的isAddFlag，判断时候都已经添加过.
                                        for (String key : groupMembersMap.keySet()) {
                                            Map<String, String> groupMember = groupMembersMap.get(key);
                                            String isAddFlag = groupMember.get("isAddFlag");
                                            if ("false".equals(isAddFlag)) {
                                                tempMap.put("dicStatus", 0);
                                                break;
                                            }
                                        }
                                        automation_DicService.updateDic(tempMap);    //更新这个群信息
                                        if ("1".equals(tempMap.get("dicStatus").toString())) {            //状态为1，则认为当前群成员都已经加过一遍了，发送微信消息通知群主给该设备换群
                                            String targetDeviceNameDesc = addGroupMembersAsFriendsParam.get("targetDeviceNameDesc").toString();
                                            //邮件通知
                                            StringBuffer mailMessageBuf = new StringBuffer();
                                            mailMessageBuf.append("蔡红旺，您好：\n");
                                            mailMessageBuf.append("        ").append("\t操作名称：添加群成员为好友的V群").append("\n");
                                            mailMessageBuf.append("        ").append("\t微信群名：").append(nickName).append("\n");
                                            mailMessageBuf.append("        ").append("\t操作设备：").append(targetDeviceNameDesc).append("\n");
                                            mailMessageBuf.append("        ").append("\t完成时间：").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("\n");
                                            mailMessageBuf.append("        ").append("\t完成地点：").append("北京市昌平区").append("\n");
                                            mailMessageBuf.append("        ").append("\t服务状态：").append("已完成对【" + nickName + "】添加群成员为好友.").append("\n");
                                            mailMessageBuf.append("        ").append("\t温馨提示：").append("当前设备【" + targetDeviceNameDesc + "】已经将【" + nickName + "】群成员已全部申请添加为好友，请管理员为该设备绑定新的群进行当前自动化操作.").append("\n");
                                            paramMap.clear();
                                            paramMap.put("to", "caihongwang@dingtalk.com");
                                            paramMap.put("subject", "【服务异常通知】添加群成员为好友的V群");
                                            paramMap.put("content", mailMessageBuf.toString());
                                            automation_MailService.sendSimpleMail(paramMap);
                                            logger.info("【邮件通知】【服务完成通知】添加群成员为好友的V群 ......");
                                        }
                                    }
                                } catch (Exception e) {
                                    logger.error("【添加群成员为好友的V群】设备编码【" + deviceName + "】设备描述【" + deviceNameDesc + "】操作【" + action + "】昵称【" + nickName + "】时异常，e : ", e);
                                }
                                logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】成功....");
                                logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】成功....");
                                logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】成功....");
                                logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】成功....");
                                logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】成功....");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
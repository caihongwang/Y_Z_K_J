package com.automation.utils.wei_xin.shareArticleToFriendCircleUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.automation.service.Automation_MailService;
import com.automation.utils.DateUtil;
import com.automation.utils.MapUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.automation.config.GlobalVariableConfig;
import com.automation.dao.Automation_DicDao;
import com.automation.dto.ResultDTO;
import com.automation.service.Automation_DicService;
import com.automation.utils.*;
import com.automation.utils.wei_xin.chatByNickName.ChatByNickNameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 分享微信文章到微信朋友圈工具
 * appium -p 4723 -bp 4724 --session-override --command-timeout 600
 */
@Component
public class ShareArticleToFriendCircleUtils {

    public static final Logger logger = LoggerFactory.getLogger(ShareArticleToFriendCircleUtils.class);

    @Autowired
    public Automation_DicDao automation_DicDao;

    @Autowired
    public Automation_MailService automation_MailService;

    @Autowired
    public Automation_DicService automation_DicService;

    @Autowired
    public GlobalVariableConfig globalVariableConfig;

    @Autowired
    public ChatByNickNameUtils chatByNickNameUtils;

    /**
     * 前置条件：将微信文章群发到【油站科技-内部交流群】里面
     * 分享微信文章到微信朋友圈
     *
     * @param paramMap
     * @throws Exception
     */
    public void shareArticleToFriendCircle(Map<String, Object> paramMap) throws Exception {
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
        //当前 自动化操作 分享微信文章到微信朋友圈
        String action = "shareArticleToFriendCircle";
        //获取 分享微信文章到微信朋友圈 设备列表和配套的坐标配置
        String deviceNameListAnddeviceLocaltionOfCode = "HuaWeiListAndShareArticleToFriendCircleLocaltion";
        //当未指定发送某个朋友圈时，则默认发送数据库中的所有朋友圈
        List<Map<String, String>> shareArticleToFriendCircleList = Lists.newLinkedList();
        if (nickNameList == null || nickNameList.size() <= 0) {
            paramMap.clear();
            paramMap.put("dicType", "shareArticleToFriendCircle");
            ResultDTO resultDTO = automation_DicService.getSimpleDicByCondition(paramMap);
            shareArticleToFriendCircleList = resultDTO.getResultList();
        } else {
            for (String nickName : nickNameList) {
                paramMap.clear();
                paramMap.put("dicType", "shareArticleToFriendCircle");
                paramMap.put("dicCode", nickName);        //指定 某一个分享微信文章到微信朋友圈 发送
                ResultDTO resultDTO = automation_DicService.getSimpleDicByCondition(paramMap);
                shareArticleToFriendCircleList.addAll(resultDTO.getResultList());
            }
        }
        //获取设备列表和配套的坐标配置
        paramMap.clear();
        paramMap.put("dicType", "deviceNameListAndLocaltion");
        paramMap.put("dicCode", deviceNameListAnddeviceLocaltionOfCode);
        List<Map<String, Object>> deviceNameAndLocaltionList = automation_DicDao.getSimpleDicByCondition(paramMap);
        if (deviceNameAndLocaltionList == null && deviceNameAndLocaltionList.size() <= 0) {
            logger.info("【分享微信文章到微信朋友圈】" + deviceNameListAnddeviceLocaltionOfCode + " 设备列表和配套的坐标配置 不存在，请使用adb命令查询设备号并入库.");
            throw new Exception("【分享微信文章到微信朋友圈】" + deviceNameListAnddeviceLocaltionOfCode + " 设备列表和配套的坐标配置 不存在，请使用adb命令查询设备号并入库.");
        }
        Map<String, Object> deviceNameAndLocaltionMap = deviceNameAndLocaltionList.get(0);
        for (String currentDevice : currentDeviceList) {
            try {
                //获取当前时间，用于校验【那台设备】在【当前时间】执行【当前自动化操作】
                String currentHour = currentDevice.contains("_") ? currentDevice.split("_")[1] : null;
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(currentHour));
                Date currentDate = calendar.getTime();
                for (Map<String, String> shareArticleToFriendCircle : shareArticleToFriendCircleList) {
                    Map<String, Object> shareArticleToFriendCircleParam = Maps.newHashMap();
                    HashMap<String, Object> reboot_shareArticleToFriendCircleParam = Maps.newHashMap();
                    try {
                        boolean isOperatedFlag = false;     //当前设备是否操作【已经添加过好友】的标志位
                        String nickName = shareArticleToFriendCircle.get("dicCode");
                        shareArticleToFriendCircleParam.putAll(MapUtil.getObjectMap(shareArticleToFriendCircle));//获取分享微信文章到微信朋友圈的内容信息.
                        shareArticleToFriendCircleParam.put("nickName", nickName);
                        String theId = shareArticleToFriendCircleParam.get("id").toString();
                        //获取dicRemark
                        String deviceNameAndLocaltionStr = deviceNameAndLocaltionMap.get("dicRemark") != null ? deviceNameAndLocaltionMap.get("dicRemark").toString() : "";
                        JSONObject deviceNameAndLocaltionJSONObject = JSONObject.parseObject(deviceNameAndLocaltionStr);
                        //获取设备坐标
                        String deviceLocaltionStr = deviceNameAndLocaltionJSONObject.getString("deviceLocaltion");
                        Map<String, Object> deviceLocaltionMap = JSONObject.parseObject(deviceLocaltionStr, Map.class);
                        shareArticleToFriendCircleParam.putAll(deviceLocaltionMap);
                        //获取设备列表
                        String deviceNameListStr = deviceNameAndLocaltionJSONObject.getString("deviceNameList");
                        List<Map<String, Object>> deviceNameList = JSONObject.parseObject(deviceNameListStr, List.class);
                        if (deviceNameList != null && deviceNameList.size() > 0) {
                            for (Map<String, Object> deviceNameMap : deviceNameList) {
                                shareArticleToFriendCircleParam.putAll(deviceNameMap);
                                //设备名称
                                deviceName = shareArticleToFriendCircleParam.get("deviceName") != null ? shareArticleToFriendCircleParam.get("deviceName").toString() : null;
                                //当前设备描述
                                deviceNameDesc = shareArticleToFriendCircleParam.get("deviceNameDesc") != null ? shareArticleToFriendCircleParam.get("deviceNameDesc").toString() : null;
                                //当前设备执行小时
                                deviceStartHour = deviceNameDesc.contains("_") ? deviceNameDesc.split("_")[1] : null;
                                if (deviceStartHour == null || deviceNameDesc == null) {
                                    continue;
                                }

                                //判断当前设备的执行小时时间是否与当前时间匹配
                                boolean isExecuteFlag = false;
                                //判断推广时间是否还在推广期内
                                String startTimeStr = shareArticleToFriendCircleParam.get("startTime") != null ? shareArticleToFriendCircleParam.get("startTime").toString() : "";
                                String endTimeStr = shareArticleToFriendCircleParam.get("endTime") != null ? shareArticleToFriendCircleParam.get("endTime").toString() : "";
                                if (!"".equals(startTimeStr) && !"".equals(endTimeStr)) {
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date startTime = sdf.parse(startTimeStr);
                                    Date endTime = sdf.parse(endTimeStr);
                                    if (DateUtil.isEffectiveDate(currentDate, startTime, endTime)) {
                                        //判断当前设备的执行小时时间是否与当前时间匹配
                                        if (deviceStartHour.equals(currentHour)) {
                                            if (CommandUtil.isOnline4AndroidDevice(deviceName)) {
                                                try {
                                                    //获取appium端口号
                                                    appiumPort = GlobalVariableConfig.getAppiumPort(action, deviceNameDesc);
                                                    shareArticleToFriendCircleParam.put("appiumPort", appiumPort);
                                                    //设置当前这杯可执行的标志位
                                                    isExecuteFlag = true;
                                                } catch (Exception e) {
                                                    //获取appium端口号失败
                                                    logger.error("【分享微信文章到微信朋友圈】" + e.getMessage());
                                                    //设置当前这杯可被行的标志位
                                                    isExecuteFlag = false;
                                                    continue;
                                                }
                                            } else {
                                                //邮件通知，当前设备不在线，Usb接口不稳定断电或者手机被关机或者断电点，需要人工进行排查...
                                                StringBuffer mailMessageBuf = new StringBuffer();
                                                mailMessageBuf.append("蔡红旺，您好：\n");
                                                mailMessageBuf.append("        ").append("\t操作名称：分享微信文章到微信朋友圈").append("\n");
                                                mailMessageBuf.append("        ").append("\t微信昵称：").append(nickName).append("\n");
                                                mailMessageBuf.append("        ").append("\t操作设备：").append(deviceNameDesc).append("\n");
                                                mailMessageBuf.append("        ").append("\t异常时间：").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("\n");
                                                mailMessageBuf.append("        ").append("\t异常地点：").append("北京市昌平区").append("\n");
                                                mailMessageBuf.append("        ").append("\t温馨提示：").append("请检查以下手机的接口，并手动辅助自动化操作.").append("\n");
                                                mailMessageBuf.append("        ").append("\t异常原因描述：").append("当前设备不在线，Usb接口不稳定断电或者手机被关机或者断电点，需要人工进行排查...").append("\n");
                                                paramMap.clear();
                                                paramMap.put("to", "caihongwang@dingtalk.com");
                                                paramMap.put("subject", "【服务异常通知】分享微信文章到微信朋友圈");
                                                paramMap.put("content", mailMessageBuf.toString());
                                                automation_MailService.sendSimpleMail(paramMap);
                                                logger.info("【邮件通知】【服务异常通知】分享微信文章到微信朋友圈 ......");
                                            }
                                        } else {
                                            logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】，当前设备的执行时间第【" + deviceStartHour + "】小时，当前时间是第【" + currentHour + "】小时....");
                                            continue;
                                        }
                                    } else if (DateUtil.isBeforeDate(currentDate, startTime)) {
                                        logger.info("【分享微信文章到微信朋友圈】尚未开始，暂不处理....");
                                    } else {
                                        Map<String, Object> tempMap = Maps.newHashMap();
                                        tempMap.put("id", theId);
                                        tempMap.put("dicStatus", 1);
                                        automation_DicService.updateDic(tempMap);    //更新这条转发微信昵称朋友圈内容为已删除
                                        logger.info("昵称【" + nickName + "】的转发微信昵称朋友圈内容已到期....");
                                    }
                                    try {
                                        if (isExecuteFlag) {
                                            //开始【将群保存到通讯录】
                                            logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】即将开始....");
                                            isOperatedFlag = new RealMachineDevices().shareArticleToFriendCircle(shareArticleToFriendCircleParam);
                                            Thread.sleep(5000);
//                                            //测试
//                                            isOperatedFlag = true;
//                                            reboot_shareArticleToFriendCircleParam.putAll(shareArticleToFriendCircleParam);
//                                            Thread.sleep(1000);
                                            break;      //后面时间段的设备不需要执行，因为每个时间段只有个设备可被执行
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        reboot_shareArticleToFriendCircleParam.putAll(shareArticleToFriendCircleParam);
                                        break;      //后面时间段的设备不需要执行，因为每个时间段只有个设备可被执行
                                    }
                                } else {
                                    logger.info("【分享微信文章到微信朋友圈】昵称【" + nickName + "】的转发微信昵称朋友圈内容推广日期不能为空....");
                                }
                            }
                        }

                        //对执行失败的设备列表进行重新执行,最多循环执行5遍
                        Integer index = 1;
                        while (reboot_shareArticleToFriendCircleParam.size() > 0) {
                            //等待所有设备重启
                            Thread.sleep(45000);
//                            //测试
//                            Thread.sleep(1000);
                            if (index > 6) {
                                break;
                            }
                            logger.info("【分享微信文章到微信朋友圈】第【" + index + "】次重新执行设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】...");
                            try {
                                logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】即将开始....");
                                isOperatedFlag = new RealMachineDevices().shareArticleToFriendCircle(reboot_shareArticleToFriendCircleParam);
                                reboot_shareArticleToFriendCircleParam.clear();       //清空需要重新执行的设备参数
                                Thread.sleep(5000);
//                                //测试
//                                if (index == 6) {
//                                    isOperatedFlag = true;
//                                    reboot_shareArticleToFriendCircleParam.clear();
//                                }
//                                Thread.sleep(1000);
                            } catch (Exception e) {     //当运行设备异常之后，就会对当前设备进行记录，准备重启，后续再对此设备进行重新执行
                                e.printStackTrace();
//                                try {
//                                    if (index % 2 == 0) {
//                                        //【分享微信文章到微信朋友圈】过程中，出现不会对设备进行重启，所以在重新执行的单个过程出现异常则重启
//                                        CommandUtil.run("/opt/android_sdk/platform-tools/adb -s " + deviceName + " reboot");
//                                        logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】重启成功...");
//                                    }
//                                } catch (Exception e1) {
//                                    logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】重启失败...");
//                                }
                            }
                            index++;
                        }

                        //回收-appiumPort
                        GlobalVariableConfig.recoveryAppiumPort(appiumPort);

                        if (reboot_shareArticleToFriendCircleParam.size() > 0) {
                            logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                            logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                            logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                            logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                            logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                            String exceptionDevices = "异常设备列表" + "【" + deviceNameDesc + "】";
                            logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                            logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                            logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                            logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                            logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                            //邮件通知
                            StringBuffer mailMessageBuf = new StringBuffer();
                            mailMessageBuf.append("蔡红旺，您好：\n");
                            mailMessageBuf.append("        ").append("\t操作名称：分享微信文章到微信朋友圈").append("\n");
                            mailMessageBuf.append("        ").append("\t微信昵称：").append(nickName).append("\n");
                            mailMessageBuf.append("        ").append("\t操作设备：").append(exceptionDevices).append("\n");
                            mailMessageBuf.append("        ").append("\t异常时间：").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("\n");
                            mailMessageBuf.append("        ").append("\t异常地点：").append("北京市昌平区").append("\n");
                            mailMessageBuf.append("        ").append("\t温馨提示：").append("请检查以下手机的接口，并手动辅助自动化操作.").append("\n");
                            mailMessageBuf.append("        ").append("\t异常原因描述：").append("Usb接口不稳定断电或者微信版本已被更新导致坐标不匹配").append("\n");
                            paramMap.clear();
                            paramMap.put("to", "caihongwang@dingtalk.com");
                            paramMap.put("subject", "【服务异常通知】分享微信文章到微信朋友圈");
                            paramMap.put("content", mailMessageBuf.toString());
                            automation_MailService.sendSimpleMail(paramMap);
                            logger.info("【邮件通知】【服务异常通知】分享微信文章到微信朋友圈 ......");
                        } else {
                            if (isOperatedFlag) {
                                logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】成功....");
                                logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】成功....");
                                logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】成功....");
                                logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】成功....");
                                logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】成功....");
                                //向nickName对象发送聊天消息进行通知
                                nextOperator_chatByNickName(deviceNameDesc, deviceName, nickName);
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

    /**
     * 向nickName对象发送聊天消息进行通知
     *
     * @param deviceNameDesc
     * @param deviceName
     * @param nickName
     */
    public void nextOperator_chatByNickName(String deviceNameDesc, String deviceName, String nickName) {
        try {
            logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】昵称【" + nickName + "】即将开始根据微信昵称进行聊天....");
            logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】昵称【" + nickName + "】即将开始根据微信昵称进行聊天....");
            logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】昵称【" + nickName + "】即将开始根据微信昵称进行聊天....");
            logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】昵称【" + nickName + "】即将开始根据微信昵称进行聊天....");
            logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】昵称【" + nickName + "】即将开始根据微信昵称进行聊天....");
            //获取设备列表
            LinkedList<String> currentDeviceList_fro_chatByNickName = Lists.newLinkedList();
            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("dicType", "deviceNameListAndLocaltion");
            paramMap.put("dicCode", "HuaWeiListAndChatByNickNameLocaltion");
            ResultDTO resultDTO = automation_DicService.getSimpleDicByCondition(paramMap);
            if (resultDTO != null && resultDTO.getResultList() != null && resultDTO.getResultList().size() > 0) {
                for (Map<String, String> sendFriendCircleMap : resultDTO.getResultList()) {
                    String deviceNameListStr = sendFriendCircleMap.get("deviceNameList");
                    List<HashMap<String, Object>> deviceNameList = JSONObject.parseObject(deviceNameListStr, List.class);
                    if (deviceNameList != null && deviceNameList.size() > 0) {
                        for (Map<String, Object> deviceNameMap : deviceNameList) {
                            //当前设备描述
                            String deviceName_temp = deviceNameMap.get("deviceName") != null ? deviceNameMap.get("deviceName").toString() : null;
                            if(deviceName.equals(deviceName_temp)){
                                String deviceNameDesc_temp = deviceNameMap.get("deviceNameDesc") != null ? deviceNameMap.get("deviceNameDesc").toString() : null;
                                currentDeviceList_fro_chatByNickName.add(deviceNameDesc_temp);
                                break;
                            }
                        }
                    }
                }
            }
            //获取昵称列表
            List<String> nickNameList_fro_chatByNickName = Lists.newArrayList();
            nickNameList_fro_chatByNickName.add(nickName);

            //准备参数，开始根据微信昵称进行聊天
            if(currentDeviceList_fro_chatByNickName.size() > 0){
                Map<String, Object> paramMap_fro_chatByNickName = Maps.newHashMap();
                paramMap_fro_chatByNickName.put("nickNameListStr", JSONObject.toJSONString(nickNameList_fro_chatByNickName));
                paramMap_fro_chatByNickName.put("currentDeviceListStr", JSONObject.toJSONString(currentDeviceList_fro_chatByNickName));
                chatByNickNameUtils.chatByNickName(paramMap_fro_chatByNickName);
            }
        } catch (Exception e) {
            logger.info("【分享微信文章到微信朋友圈】根据微信昵称进行聊天失败.");
        }
    }

//    public static void main(String[] args) throws Exception{
//        String dateStr = "2020-10-25 14";
//        System.out.println("分享微信文章到微信朋友圈，dateStr = " + dateStr + "，执行的设备是：华为 Mate 8海外版 _ 1，对应的【根据微信昵称进行聊天】时间应该是：2020-10-25 18");
//        System.out.println("时间矫正：" + DateUtil.getPostponeTimesOradvanceTimes(dateStr, -4));
//
//        dateStr = "2020-10-25 14";
//        System.out.println("发送朋友圈，dateStr = " + dateStr + "，执行的设备是：华为 Mate 8 _ 6，对应的【根据微信昵称进行聊天】时间应该是：2020-10-25 16");
//        System.out.println("时间矫正：" + DateUtil.getPostponeTimesOradvanceTimes(dateStr, -2));
//    }
}

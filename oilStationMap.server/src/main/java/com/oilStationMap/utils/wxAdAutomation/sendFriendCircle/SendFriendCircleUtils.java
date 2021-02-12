package com.oilStationMap.utils.wxAdAutomation.sendFriendCircle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.oilStationMap.config.GlobalVariableConfig;
import com.oilStationMap.dao.WX_DicDao;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.service.MailService;
import com.oilStationMap.service.WX_DicService;
import com.oilStationMap.service.WX_MessageService;
import com.oilStationMap.service.impl.MailServiceImpl;
import com.oilStationMap.service.impl.WX_DicServiceImpl;
import com.oilStationMap.service.impl.WX_MessageServiceImpl;
import com.oilStationMap.utils.*;
import com.oilStationMap.utils.wxAdAutomation.chatByNickName.ChatByNickNameUtils;
import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 发布朋友圈工具
 * appium -p 4723 -bp 4724 --session-override --command-timeout 600
 */
public class SendFriendCircleUtils {

    public static final Logger logger = LoggerFactory.getLogger(SendFriendCircleUtils.class);

    public static WX_DicDao wxDicDao = (WX_DicDao) ApplicationContextUtils.getBeanByClass(WX_DicDao.class);

    public static MailService mailService = (MailService) ApplicationContextUtils.getBeanByClass(MailServiceImpl.class);

    public static WX_DicService wxDicService = (WX_DicService) ApplicationContextUtils.getBeanByClass(WX_DicServiceImpl.class);

    public static WX_MessageService wxMessageService = (WX_MessageService) ApplicationContextUtils.getBeanByClass(WX_MessageServiceImpl.class);

    public static GlobalVariableConfig globalVariableConfig = (com.oilStationMap.config.GlobalVariableConfig) ApplicationContextUtils.getBeanByClass(GlobalVariableConfig.class);

    /**
     * 发布朋友圈for所有设备
     */
    public static void sendFriendCircle(Map<String, Object> paramMap) throws Exception {
        String nickNameListStr = paramMap.get("nickNameListStr") != null ? paramMap.get("nickNameListStr").toString() : "";
        String currentDateListStr = paramMap.get("currentDateListStr") != null ? paramMap.get("currentDateListStr").toString() : "";
        LinkedList<String> currentDateList = Lists.newLinkedList();
        try {
            currentDateList = JSON.parseObject(currentDateListStr, LinkedList.class);
        } catch (Exception e) {
            throw new Exception("解析json时间列表失败，currentDateListStr = " + currentDateListStr + " ， e : ", e);
        }
        if (currentDateList.size() <= 0) {
            currentDateList.add(new SimpleDateFormat("yyyy-MM-dd HH").format(new Date()));
        }
        //appiumPort
        String appiumPort = null;
        //设备编码
        String deviceName = "未知-设备编码";
        //设备描述
        String deviceNameDesc = "未知-设备描述";
        //当前 自动化操作 发送朋友圈
        String action = null;
        //获取 发送朋友圈 设备列表和配套的坐标配置
        String deviceNameListAnddeviceLocaltionOfCode = "HuaWeiListAndSendFriendCircleLocaltion";
        for (String currentDateStr : currentDateList) {
            Map<String, Object> sendFriendCircleParam = Maps.newHashMap();
            HashMap<String, Object> reboot_sendFriendCircleParam = Maps.newHashMap();
            //获取当前时间，用于校验【那台设备】在【当前时间】执行【当前自动化操作】
            Date currentDate = new SimpleDateFormat("yyyy-MM-dd HH").parse(currentDateStr);
            List<String> nickNameList = JSONObject.parseObject(nickNameListStr, List.class);
            for (String nickName : nickNameList) {
                boolean isOperatedFlag = false;     //当前设备是否操作【已经添加过好友】的标志位
                paramMap.put("dicType", "sendFriendCircle");
                paramMap.put("dicCode", nickName);        //指定转发微信昵称朋友圈内容
                ResultDTO resultDTO = wxDicService.getLatelyDicByCondition(paramMap);
                List<Map<String, String>> resultList = resultDTO.getResultList();
                if (resultList != null && resultList.size() > 0) {
                    //获取发送朋友圈的内容信息.
                    sendFriendCircleParam.putAll(MapUtil.getObjectMap(resultList.get(0)));
                    sendFriendCircleParam.put("nickName", nickName);
                    sendFriendCircleParam.put("phoneLocalPath", "/storage/emulated/0/tencent/MicroMsg/WeiXin/");
                    String theId = sendFriendCircleParam.get("id").toString();
                    //获取设备列表和配套的坐标配置wxDic
                    List<HashMap<String, Object>> deviceNameList = Lists.newArrayList();
                    paramMap.clear();
                    paramMap.put("dicType", "deviceNameListAndLocaltion");
                    paramMap.put("dicCode", deviceNameListAnddeviceLocaltionOfCode);
                    List<Map<String, Object>> list = wxDicDao.getSimpleDicByCondition(paramMap);
                    if (list != null && list.size() > 0) {
                        //获取dicRemark
                        String deviceNameAndLocaltionStr = list.get(0).get("dicRemark") != null ? list.get(0).get("dicRemark").toString() : "";
                        JSONObject deviceNameAndLocaltionJSONObject = JSONObject.parseObject(deviceNameAndLocaltionStr);
                        //获取设备坐标
                        String deviceLocaltionStr = deviceNameAndLocaltionJSONObject.getString("deviceLocaltion");
                        Map<String, Object> deviceLocaltionMap = JSONObject.parseObject(deviceLocaltionStr, Map.class);
                        sendFriendCircleParam.putAll(deviceLocaltionMap);
                        //获取设备列表
                        String deviceNameListStr = deviceNameAndLocaltionJSONObject.getString("deviceNameList");
                        deviceNameList = JSONObject.parseObject(deviceNameListStr, List.class);
                    } else {
                        logger.info("【发送朋友圈】" + deviceNameListAnddeviceLocaltionOfCode + " 设备列表和配套的坐标配置 不存在，请使用adb命令查询设备号并入库.");
                    }
                    //当前时间
                    sendFriendCircleParam.put("currentDate", currentDate);

                    //1.将 图片文件 push 到安卓设备里面
                    action = sendFriendCircleParam.get("action") != null ? sendFriendCircleParam.get("action").toString() : "textMessageFriendCircle";
                    if (action.equals("imgMessageFriendCircle")) {
                        boolean imgExistFlag = false;
                        //将 图片文件 push 到安卓设备里面
                        if (pushImgFileToDevice(deviceNameList, sendFriendCircleParam)) {
                            imgExistFlag = true;
                        }
                        if (imgExistFlag) {          //如果 图片 不存在则直接下一个, 同时将 图片文件 remove 到安卓设备里面
                            //2.沉睡等待2分钟，确保USB传输文件到达手机相册
                            logger.info("【发送朋友圈】将图片保存到【手机本地的微信图片路径】成功，沉睡等待2分钟，确保USB传输文件到达手机相册....");
                            Thread.sleep(1000 * 60 * 2);       //沉睡等待10分钟
                        } else {
                            logger.error("【发送朋友圈】昵称【" + nickName + "】未在广告排期已过期或者没有图片，异常退出.");
                            continue;
                        }
                    }

                    //3.发送朋友圈
                    if (deviceNameList != null && deviceNameList.size() > 0) {
                        for (Map<String, Object> deviceNameMap : deviceNameList) {
                            sendFriendCircleParam.putAll(deviceNameMap);//判断推广时间是否还在推广期内//获取设备编码
                            //当前设备编码
                            deviceName = sendFriendCircleParam.get("deviceName") != null ? sendFriendCircleParam.get("deviceName").toString() : null;
                            //当前设备描述
                            deviceNameDesc = sendFriendCircleParam.get("deviceNameDesc") != null ? sendFriendCircleParam.get("deviceNameDesc").toString() : null;
                            //判断当前设备的执行小时时间是否与当前时间匹配
                            boolean isExecuteFlag = false;
                            String startTimeStr = sendFriendCircleParam.get("startTime") != null ? sendFriendCircleParam.get("startTime").toString() : "";
                            String endTimeStr = sendFriendCircleParam.get("endTime") != null ? sendFriendCircleParam.get("endTime").toString() : "";
                            if (!"".equals(startTimeStr) && !"".equals(endTimeStr)) {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                Date startTime = sdf.parse(startTimeStr);
                                Date endTime = sdf.parse(endTimeStr);
                                if (DateUtil.isEffectiveDate(currentDate, startTime, endTime)) {      //确保当前朋友圈信息是在上午谈判的推广时间段之内
                                    //判断当前设备的执行小时时间是否与当前时间匹配
                                    String startHour =
                                            sendFriendCircleParam.get("startHour") != null ?
                                                    sendFriendCircleParam.get("startHour").toString() :
                                                    "";
                                    String currentHour = new SimpleDateFormat("HH").format(currentDate);
                                    if (startHour.equals(currentHour)) {
                                        try {
                                            //获取appium端口号
                                            appiumPort = GlobalVariableConfig.getAppiumPort(action, deviceNameDesc);
                                            sendFriendCircleParam.put("appiumPort", appiumPort);
                                            //设置当前这杯可执行的标志位
                                            isExecuteFlag = true;
                                        } catch (Exception e) {
                                            //获取appium端口号失败
                                            logger.error("【发送朋友圈】" + e.getMessage());
                                            //设置当前这杯可被行的标志位
                                            isExecuteFlag = false;
                                            continue;
                                        }
                                    } else {
                                        logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】，当前设备的执行时间第【" + startHour + "】小时，当前时间是第【" + currentHour + "】小时....");
                                        continue;
                                    }
                                } else if (DateUtil.isBeforeDate(currentDate, startTime)) {
                                    logger.info("发送朋友圈】尚未开始，暂不处理....");
                                } else {
                                    Map<String, Object> tempMap = Maps.newHashMap();
                                    tempMap.put("id", theId);
                                    tempMap.put("dicStatus", 1);
                                    wxDicService.updateDic(tempMap);    //更新这条朋友圈数据为已删除
                                    logger.info("发送朋友圈】昵称【" + nickName + "】的转发朋友圈业务已到期....");
                                }
                                try {
                                    if (isExecuteFlag) {
                                        //开始【将群保存到通讯录】
                                        logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】即将开始....");
                                        isOperatedFlag = new RealMachineDevices().sendFriendCircle(sendFriendCircleParam);
                                        Thread.sleep(5000);
//                                            //测试
//                                            isOperatedFlag = true;
//                                            reboot_sendFriendCircleParam.putAll(sendFriendCircleParam);
//                                            Thread.sleep(1000);
                                        break;      //后面时间段的设备不需要执行，因为每个时间段只有个设备可被执行
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    reboot_sendFriendCircleParam.putAll(sendFriendCircleParam);
                                    break;
                                }
                            } else {
                                logger.info("【发送朋友圈】昵称【" + nickName + "】的转发朋友圈业务推广日期不能为空....");
                            }
                        }
                    }

                    //4.对执行失败的设备进行重新执行【发送朋友圈】,最多重复执行15次，每间隔4次重启一次手机
                    Integer index = 1;
                    while (reboot_sendFriendCircleParam.size() > 0) {
                        //等待所有设备重启
                        Thread.sleep(45000);
//                        //测试
//                        Thread.sleep(1000);
                        if (index > 6) {
                            break;
                        }
                        logger.info("【发送朋友圈】第【" + index + "】次重新执行设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】...");
                        if (action.equals("imgMessageFriendCircle")) {  //重操作的时候，再次对图片进行操作
                            pushImgFileToDevice(deviceNameList, sendFriendCircleParam);
                        }
                        try {
                            logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】即将开始....");
                            isOperatedFlag = new RealMachineDevices().sendFriendCircle(reboot_sendFriendCircleParam);
                            reboot_sendFriendCircleParam.clear();       //清空需要重新执行的设备参数
                            Thread.sleep(5000);
//                            //测试
//                            if (index == 15) {
//                                isOperatedFlag = true;
//                                reboot_sendFriendCircleParam.clear();
//                            }
//                            Thread.sleep(1000);
                        } catch (Exception e) {     //当运行设备异常之后，就会对当前设备进行记录，准备重启，后续再对此设备进行重新执行
                            e.printStackTrace();
                            try {
                                if (index % 2 == 0) {
                                    //【发送朋友圈】过程中，出现不会对设备进行重启，所以在重新执行的单个过程出现异常则重启
                                    CommandUtil.run("/opt/android_sdk/platform-tools/adb -s " + deviceName + " reboot");
                                    logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】重启成功...");
                                }
                            } catch (Exception e1) {
                                logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】重启失败...");
                            }
                        }
                        index++;
                    }

                    //5.将 图片文件 push 到安卓设备里面
                    if (action.equals("imgMessageFriendCircle")) {
                        //将 图片文件  从安卓设备里面 删除
                        removeImgFileToDevice(deviceNameList, sendFriendCircleParam);
                    }

                    //回收-appiumPort
                    GlobalVariableConfig.recoveryAppiumPort(appiumPort);

                    //6.发送微信通知消息
                    if (reboot_sendFriendCircleParam.size() > 0) {
                        logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                        logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                        logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                        logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                        logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                        String exceptionDevices = "异常设备列表" + "【" + deviceNameDesc + "】";
                        logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                        logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                        logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                        logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                        logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");

                        //建议使用http协议访问阿里云，通过阿里元来完成此操作.
                        HttpsUtil httpsUtil = new HttpsUtil();
                        Map<String, String> exceptionDevicesParamMap = Maps.newHashMap();
                        exceptionDevicesParamMap.put("nickName", nickName);
                        exceptionDevicesParamMap.put("operatorName", "发布朋友圈");
                        exceptionDevicesParamMap.put("exceptionDevices", exceptionDevices);
                        String exceptionDevicesNotifyUrl = "https://www.yzkj.store/oilStationMap/wxMessage/exceptionDevicesMessageSend";
                        String resultJson = httpsUtil.post(exceptionDevicesNotifyUrl, exceptionDevicesParamMap);
                        logger.info("微信消息异常发送反馈：" + resultJson);
                        //邮件通知
                        StringBuffer mailMessageBuf = new StringBuffer();
                        mailMessageBuf.append("蔡红旺，您好：\n");
                        mailMessageBuf.append("        ").append("\t操作名称：发布朋友圈").append("\n");
                        mailMessageBuf.append("        ").append("\t微信群：").append(nickName).append("\n");
                        mailMessageBuf.append("        ").append("\t操作设备：").append(exceptionDevices).append("\n");
                        mailMessageBuf.append("        ").append("\t异常时间：").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("\n");
                        mailMessageBuf.append("        ").append("\t异常地点：").append("北京市昌平区").append("\n");
                        mailMessageBuf.append("        ").append("\t温馨提示：").append("请检查以下手机的接口，并手动辅助自动化操作.").append("\n");
                        mailMessageBuf.append("        ").append("\t异常原因描述：").append("Usb接口不稳定断电或者微信版本已被更新导致坐标不匹配").append("\n");
                        mailService.sendSimpleMail("caihongwang@dingtalk.com", "【服务异常通知】发布朋友圈", mailMessageBuf.toString());
                    } else {
                        if (isOperatedFlag) {
                            logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】成功....");
                            logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】成功....");
                            logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】成功....");
                            logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】成功....");
                            logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】成功....");

                            //向nickName对象发送聊天消息进行通知
                            nextOperator_chatByNickName(deviceNameDesc, deviceName, nickName, currentDateStr);
                        }
                    }
                } else {
                    logger.info("【发送朋友圈】 失败.");
                }
            }
        }
    }

    /**
     * 向nickName对象发送聊天消息进行通知
     *
     * @param deviceNameDesc
     * @param deviceName
     * @param nickName
     * @param currentDateStr
     */
    public static void nextOperator_chatByNickName(String deviceNameDesc, String deviceName, String nickName,
                                                   String currentDateStr) {
        try {
            logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】昵称【" + nickName + "】即将开始根据微信昵称进行聊天....");
            logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】昵称【" + nickName + "】即将开始根据微信昵称进行聊天....");
            logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】昵称【" + nickName + "】即将开始根据微信昵称进行聊天....");
            logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】昵称【" + nickName + "】即将开始根据微信昵称进行聊天....");
            logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】昵称【" + nickName + "】即将开始根据微信昵称进行聊天....");
            List<String> nickNameList_fro_chatByNickName = Lists.newArrayList();
            nickNameList_fro_chatByNickName.add(nickName);
            LinkedList<String> currentDateList_fro_chatByNickName = Lists.newLinkedList();
            currentDateList_fro_chatByNickName.add(DateUtil.getPostponeTimesOradvanceTimes(currentDateStr, 2));
            Map<String, Object> paramMap_fro_chatByNickName = Maps.newHashMap();
            paramMap_fro_chatByNickName.put("nickNameListStr", JSONObject.toJSONString(nickNameList_fro_chatByNickName));
            paramMap_fro_chatByNickName.put("currentDateListStr", JSONObject.toJSONString(currentDateList_fro_chatByNickName));
            ChatByNickNameUtils.chatByNickName(paramMap_fro_chatByNickName);
        } catch (Exception e) {

            logger.info("【发送朋友圈】根据微信昵称进行聊天失败.");
        }
    }

    /**
     * 将 图片文件 push 到安卓设备里面
     * 顺序导入，如: 1.jpg，2.jpg，3.jpg，4.jpg ......
     * adb -s QVM0216331002197 push 1.jpg /storage/emulated/0/DCIM/Camera/
     * adb -s QVM0216331002197 push 2.jpg /storage/emulated/0/DCIM/Camera/
     * adb -s QVM0216331002197 push 3.jpg /storage/emulated/0/DCIM/Camera/
     * <p>
     * <p>
     * adb -s 5LM0216122009385 push 1.jpg /storage/emulated/0/tencent/MicroMsg/WeiXin/
     * adb -s 5LM0216122009385 push 2.jpg /storage/emulated/0/tencent/MicroMsg/WeiXin/
     * adb -s 5LM0216122009385 push 3.jpg /storage/emulated/0/tencent/MicroMsg/WeiXin/
     *
     * @param deviceNameList
     * @param sendFriendCircleParam
     * @return
     */
    public static boolean pushImgFileToDevice(List<HashMap<String, Object>> deviceNameList, Map<String, Object> sendFriendCircleParam) {
        boolean flag = false;
        //安卓设备的微信图片目录
        String phoneLocalPath = sendFriendCircleParam.get("phoneLocalPath") != null ? sendFriendCircleParam.get("phoneLocalPath").toString() : "/storage/emulated/0/tencent/MicroMsg/WeiXin/";
        File[] imgFiles = null;
        if (deviceNameList != null && deviceNameList.size() > 0) {
            for (Map<String, Object> deviceNameMap : deviceNameList) {
                sendFriendCircleParam.putAll(deviceNameMap);//判断推广时间是否还在推广期内
                //广告投放-开始时间
                String startTimeStr = sendFriendCircleParam.get("startTime") != null ? sendFriendCircleParam.get("startTime").toString() : "";
                //广告投放-结束时间
                String endTimeStr = sendFriendCircleParam.get("endTime") != null ? sendFriendCircleParam.get("endTime").toString() : "";
                //当前时间
                Date currentDate = sendFriendCircleParam.get("currentDate") != null ? (Date) sendFriendCircleParam.get("currentDate") : new Date();
                //设备描述
                String deviceNameDesc = deviceNameMap.get("deviceNameDesc") != null ? deviceNameMap.get("deviceNameDesc").toString() : "";
                //设备编码
                String deviceName = deviceNameMap.get("deviceName") != null ? deviceNameMap.get("deviceName").toString() : "";
                //图片文件路径，总路径+微信昵称
                String imgDirPath = sendFriendCircleParam.get("imgDirPath") != null ? sendFriendCircleParam.get("imgDirPath").toString() : "";
                if (!"".equals(startTimeStr) && !"".equals(endTimeStr)) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date startTime = sdf.parse(startTimeStr);
                        Date endTime = sdf.parse(endTimeStr);
                        if (DateUtil.isEffectiveDate(currentDate, startTime, endTime)) {      //确保当前朋友圈信息是在上午谈判的推广时间段之内
                            //判断当前设备的执行小时时间是否与当前时间匹配
                            String startHour =
                                    sendFriendCircleParam.get("startHour") != null ?
                                            sendFriendCircleParam.get("startHour").toString() :
                                            "";
                            String currentHour = new SimpleDateFormat("HH").format(currentDate);
                            if (startHour.equals(currentHour)) {
                                imgDirPath = imgDirPath + "/" + sendFriendCircleParam.get("nickName");
                                if (!"".equals(imgDirPath)) {
                                    File imgDir = new File(imgDirPath);
                                    if ("今日油价".equals(imgDir.getName())) {
                                        imgFiles = new File[1];
                                        try {
                                            File imgFile = new File(imgDir.getPath() + "/今日油价_" + new SimpleDateFormat("yyyy_MM_dd").format(currentDate) + ".jpeg");
                                            if (!imgFile.exists()) {
                                                imgFile = new File(imgDir.getPath() + "/今日油价_" + new SimpleDateFormat("yyyy_MM_dd").format(currentDate) + ".jpg");
                                            }
                                            if (imgFile.exists()) {
                                                imgFiles[0] = imgFile;
                                            } else {
                                                return false;
                                            }
                                        } catch (Exception e) {
                                            logger.error("【发送朋友圈】获取 今日油价 图片失败.");
                                        }
                                    } else {
                                        imgFiles = imgDir.listFiles();
                                    }
                                }

                                //移除imgFiles中的非图片格式文件
                                List<File> imgFileList = new ArrayList(Arrays.asList(imgFiles));
                                Iterator<File> iterator = imgFileList.iterator();
                                while (iterator.hasNext()) {
                                    File imgFile = iterator.next();
                                    String[] fileNameArr = imgFile.getName().split("\\.");
                                    //图片格式必须在 GlobalVariableConfig.imgFormatList范围之内
                                    if (fileNameArr != null && fileNameArr.length >= 2 && !GlobalVariableConfig.imgFormatList.contains(fileNameArr[1])) {
                                        iterator.remove();
                                        logger.info("【发送朋友圈】非图片格式，imgFile = " + imgFile.getPath());
                                    }
                                }
                                imgFiles = new File[imgFileList.size()];
                                imgFileList.toArray(imgFiles);

                                //确保 文件件存在
                                CommandUtil.run("/opt/android_sdk/platform-tools/adb -s " + deviceName + " mkdir " + phoneLocalPath);

                                if (imgFiles != null && imgFiles.length > 0 && !"".equals(deviceName)) {
                                    for (int i = 0; i < imgFiles.length; i++) {
                                        try {
                                            //1.使用adb传输文件到手机，并发起广播，广播不靠谱，添加图片到文件系统里面去，但是在相册里面不确定能看得见.
                                            File imgFile = imgFiles[i];
                                            if (imgFile.getName().startsWith(".")) {          //过滤部分操作系统的隐藏文件
                                                continue;
                                            }
                                            for (File tempFile : imgFiles) {          //确保文件顺序导出.
                                                String[] fileNameArr = tempFile.getName().split("\\.");
                                                if (fileNameArr[0].equals(i + 1 + "")) {
                                                    imgFile = tempFile;
                                                    break;
                                                }
                                            }
                                            //将图片push到设备
                                            String pushCommandStr = "/opt/android_sdk/platform-tools/adb -s " + deviceName + " push " + imgFile.getPath() + " " + phoneLocalPath;
                                            CommandUtil.run(pushCommandStr);
                                            Thread.sleep(1000);
                                            //每张图片 发送100次push通知，确保图片在微信的图片预览中出现
                                            for (int j = 1; j <= 100; j++) {
                                                String refreshCommandStr = "";
                                                refreshCommandStr = "/opt/android_sdk/platform-tools/adb -s " + deviceName + " shell am broadcast -a android.intent.action.MEDIA_SCANNER_SCAN_FILE -d file://" + phoneLocalPath + imgFile.getName();
                                                CommandUtil.run(refreshCommandStr);
//                                                try{
//                                                    refreshCommandStr = "/opt/android_sdk/platform-tools/adb -s " + deviceName + " shell am broadcast -a android.intent.action.MEDIA_SCANNER_SCAN_FILE -d file://" + phoneLocalPath + i + ".jpg";
//                                                    CommandUtil.run(new String[]{"/bin/sh", "-c", refreshCommandStr});
//                                                } catch (Exception e) {
//                                                    logger.info("点击坐标【选择图片】失败，第【"+j+"】次更新【jpg】图片失败，即将重启..... , refreshCommandStr = " + refreshCommandStr + " , e : ", e);
//                                                }
//                                                try{
//                                                    refreshCommandStr = "/opt/android_sdk/platform-tools/adb -s " + deviceName + " shell am broadcast -a android.intent.action.MEDIA_SCANNER_SCAN_FILE -d file://" + phoneLocalPath + i + ".jpeg";
//                                                    CommandUtil.run(new String[]{"/bin/sh", "-c", refreshCommandStr});
//                                                } catch (Exception e) {
//                                                    logger.info("点击坐标【选择图片】失败，第【"+j+"】次更新【jpeg】图片失败，即将重启..... , refreshCommandStr = " + refreshCommandStr + " , e : ", e);
//                                                }
//                                                try{
//                                                    refreshCommandStr = "/opt/android_sdk/platform-tools/adb -s " + deviceName + " shell am broadcast -a android.intent.action.MEDIA_SCANNER_SCAN_FILE -d file://" + phoneLocalPath;
//                                                    CommandUtil.run(new String[]{"/bin/sh", "-c", refreshCommandStr});
//                                                } catch (Exception e) {
//                                                    logger.info("点击坐标【选择图片】失败，第【"+j+"】次更新【文件夹】图片失败，即将重启..... , refreshCommandStr = " + refreshCommandStr + " , e : ", e);
//                                                }
                                                logger.info("【发送朋友圈】将 图片文件 push 从安卓设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】，第【" + j + "】次发送通知更新【" + sendFriendCircleParam.get("nickName") + "】" + imgFile.getName() + " 图片成功..... ");
                                            }
                                            logger.info("【发送朋友圈】将 图片文件 push 从安卓设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】成功，imgFile = " + imgFile.getPath());
                                            if (!flag) {
                                                flag = true;
                                            }
                                        } catch (Exception e) {
                                            logger.info("【发送朋友圈】将 图片文件 push 到安卓设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】失败，设备未连接到电脑上, e : ", e);
                                        }
                                    }
                                }
                            } else {
                                logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】，当前设备的执行时间第【" + startHour + "】小时，当前时间是第【" + currentHour + "】小时....");
                                continue;
                            }
                        }
                    } catch (Exception e) {
                        logger.error("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】解析设备的执行时间段是异常， e : ", e);
                    }
                }
            }
        }
        return flag;
    }

    /**
     * 将 图片文件  从安卓设备里面 删除
     * 无顺讯讲究，直接书删除
     *
     * @param deviceNameList
     * @param sendFriendCircleParam
     */
    public static void removeImgFileToDevice(List<HashMap<String, Object>> deviceNameList, Map<String, Object> sendFriendCircleParam) {
        //安卓设备的微信图片目录
        String phoneLocalPath = sendFriendCircleParam.get("phoneLocalPath") != null ? sendFriendCircleParam.get("phoneLocalPath").toString() : "/storage/emulated/0/tencent/MicroMsg/WeiXin/";
        File[] imgFiles = null;
        if (deviceNameList != null && deviceNameList.size() > 0) {
            for (Map<String, Object> deviceNameMap : deviceNameList) {
                sendFriendCircleParam.putAll(deviceNameMap);//判断推广时间是否还在推广期内
                //广告投放-开始时间
                String startTimeStr = sendFriendCircleParam.get("startTime") != null ? sendFriendCircleParam.get("startTime").toString() : "";
                //广告投放-结束时间
                String endTimeStr = sendFriendCircleParam.get("endTime") != null ? sendFriendCircleParam.get("endTime").toString() : "";
                //当前时间
                Date currentDate = sendFriendCircleParam.get("currentDate") != null ? (Date) sendFriendCircleParam.get("currentDate") : new Date();
                //设备描述
                String deviceNameDesc = deviceNameMap.get("deviceNameDesc") != null ? deviceNameMap.get("deviceNameDesc").toString() : "";
                //设备编码
                String deviceName = deviceNameMap.get("deviceName") != null ? deviceNameMap.get("deviceName").toString() : "";
                //图片文件路径，总路径+微信昵称
                String imgDirPath = sendFriendCircleParam.get("imgDirPath") != null ? sendFriendCircleParam.get("imgDirPath").toString() : "";
                if (!"".equals(startTimeStr) && !"".equals(endTimeStr)) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date startTime = sdf.parse(startTimeStr);
                        Date endTime = sdf.parse(endTimeStr);
                        if (DateUtil.isEffectiveDate(currentDate, startTime, endTime)) {      //确保当前朋友圈信息是在上午谈判的推广时间段之内
                            //判断当前设备的执行小时时间是否与当前时间匹配
                            String startHour =
                                    sendFriendCircleParam.get("startHour") != null ?
                                            sendFriendCircleParam.get("startHour").toString() :
                                            "";
                            String currentHour = new SimpleDateFormat("HH").format(currentDate);
                            if (startHour.equals(currentHour)) {
                                imgDirPath = imgDirPath + "/" + sendFriendCircleParam.get("nickName");
                                if (!"".equals(imgDirPath)) {
                                    File imgDir = new File(imgDirPath);
                                    if ("今日油价".equals(imgDir.getName())) {
                                        imgFiles = new File[1];
                                        try {
                                            File imgFile = new File(imgDir.getPath() + "/今日油价_" + new SimpleDateFormat("yyyy_MM_dd").format(currentDate) + ".jpeg");
                                            if (!imgFile.exists()) {
                                                imgFile = new File(imgDir.getPath() + "/今日油价_" + new SimpleDateFormat("yyyy_MM_dd").format(currentDate) + ".jpg");
                                            }
                                            imgFiles[0] = imgFile;
                                        } catch (Exception e) {
                                            logger.error("【发送朋友圈】获取 今日油价 图片失败.");
                                        }
                                    } else {
                                        imgFiles = imgDir.listFiles();
                                    }
                                }
                                if (imgFiles != null && imgFiles.length > 0 && !"".equals(deviceName)) {
                                    for (int i = 0; i < imgFiles.length; i++) {
                                        try {
                                            //1.使用adb传输文件到手机，并发起广播，广播不靠谱，添加图片到文件系统里面去，但是在相册里面不确定能看得见.
                                            File imgFile = imgFiles[i];
                                            String removeCommandStr = "/opt/android_sdk/platform-tools/adb -s " + deviceName + " shell rm " + phoneLocalPath + "*";
                                            CommandUtil.run(removeCommandStr);
                                            Thread.sleep(1000);
                                            for (int j = 1; j <= 100; j++) {
                                                String refreshCommandStr = "/opt/android_sdk/platform-tools/adb -s " + deviceName + " shell am broadcast -a android.intent.action.MEDIA_SCANNER_SCAN_FILE -d file://" + phoneLocalPath + imgFile.getName();
                                                CommandUtil.run(refreshCommandStr);
                                                logger.info("【发送朋友圈】将 图片文件 remove 从安卓设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】，第【" + j + "】次发送通知更新【" + sendFriendCircleParam.get("nickName") + "】" + imgFile.getName() + " 图片成功..... ");
                                            }
                                            logger.info("【发送朋友圈】将 图片文件 remove 从安卓设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】成功，imgFile = " + imgFile.getPath());
                                        } catch (Exception e) {
                                            logger.info("【发送朋友圈】将 图片文件 remove 从安卓设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】失败，设备未连接到电脑上, e : ", e);
                                        }
                                    }
                                }
                            } else {
                                logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】，当前设备的执行时间第【" + startHour + "】小时，当前时间是第【" + currentHour + "】小时....");
                                continue;
                            }
                        }
                    } catch (Exception e) {
                        logger.error("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】解析手背的执行时间段是异常， e : ", e);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        File imgDir = new File("/opt/nextcloud/铜仁市碧江区智惠加油站科技服务工作室/微信朋友圈广告业务/铜仁市_玉屏县_车友群");
        File[] imgFiles = imgDir.listFiles();
        for (int i = 0; i < imgFiles.length; i++) {
            File imgFile = imgFiles[i];
            for (File tempFile : imgFiles) {          //确保文件顺序导出.
                String[] fileNameArr = tempFile.getName().split("\\.");
                if (fileNameArr[0].equals(i + 1 + "")) {
                    imgFile = tempFile;
                    System.out.println("imgFile.getName() = " + imgFile.getName());
                    break;
                }
            }
        }
    }
}

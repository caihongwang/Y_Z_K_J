package com.oilStationMap.utils.wxAdAutomation.addGroupMembersAsFriends;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 根据微信群昵称添加群成员为好友
 * 添加群成员为好友的V群
 * appium -p 4723 -bp 4724 --session-override --command-timeout 600
 */
public class AddGroupMembersAsFriendsUtils {

    public static final Logger logger = LoggerFactory.getLogger(AddGroupMembersAsFriendsUtils.class);

    public static WX_DicDao wxDicDao = (WX_DicDao) ApplicationContextUtils.getBeanByClass(WX_DicDao.class);

    public static MailService mailService = (MailService) ApplicationContextUtils.getBeanByClass(MailServiceImpl.class);

    public static WX_DicService wxDicService = (WX_DicService) ApplicationContextUtils.getBeanByClass(WX_DicServiceImpl.class);

    public static WX_MessageService wxMessageService = (WX_MessageService) ApplicationContextUtils.getBeanByClass(WX_MessageServiceImpl.class);

    /**
     * 根据微信群昵称添加群成员为好友工具for所有设备
     */
    public static void addGroupMembersAsFriends(Map<String, Object> paramMap) throws Exception {
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
        //当前 自动化操作 添加群成员为好友的V群
        String action = "addGroupMembersAsFriends";
        //获取 添加群成员为好友的V群 设备列表和配套的坐标配置
        String deviceNameListAnddeviceLocaltionOfCode = "HuaWeiListAndAddGroupMembersAsFriendsLocaltion";
        for (String currentDateStr : currentDateList) {
            Map<String, Object> addGroupMembersAsFriendsParam = Maps.newHashMap();
            HashMap<String, Object> reboot_addGroupMembersAsFriendsParam = Maps.newHashMap();
            //获取当前时间，用于校验【那台设备】在【当前时间】执行【当前自动化操作】
            Date currentDate = new SimpleDateFormat("yyyy-MM-dd HH").parse(currentDateStr);
            //获取群的昵称，循环遍历
            List<String> nickNameList = JSONObject.parseObject(nickNameListStr, List.class);
            for (String nickName : nickNameList) {
                boolean isOperatedFlag = false;     //当前设备是否操作【添加群成员为好友的V群】的标志位
                //根据【群的昵称】获取，对应的执行设备
                paramMap.clear();
                paramMap.put("dicType", "addGroupMembersAsFriends");
                paramMap.put("dicCode", EmojiUtil.emojiConvert(nickName));
                ResultDTO resultDTO = wxDicService.getLatelyDicByCondition(paramMap);
                List<Map<String, String>> resultList = resultDTO.getResultList();
                if (resultList != null && resultList.size() > 0) {
                    //根据微信群昵称添加群成员为好友.
                    addGroupMembersAsFriendsParam.putAll(MapUtil.getObjectMap(resultList.get(0)));
                    addGroupMembersAsFriendsParam.put("nickName", nickName);
                    String theId = addGroupMembersAsFriendsParam.get("id").toString();
                    //获取设备列表和配套的坐标配置
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
                        addGroupMembersAsFriendsParam.putAll(deviceLocaltionMap);
                        //获取设备列表
                        String deviceNameListStr = deviceNameAndLocaltionJSONObject.getString("deviceNameList");
                        List<HashMap<String, Object>> deviceNameList = JSONObject.parseObject(deviceNameListStr, List.class);
                        if (deviceNameList != null && deviceNameList.size() > 0) {
                            for (Map<String, Object> deviceNameMap : deviceNameList) {
                                addGroupMembersAsFriendsParam.putAll(deviceNameMap);
                                //获取设备编码
                                deviceName =
                                        addGroupMembersAsFriendsParam.get("deviceName") != null ?
                                                addGroupMembersAsFriendsParam.get("deviceName").toString() :
                                                null;
                                //当前设备描述
                                deviceNameDesc =
                                        addGroupMembersAsFriendsParam.get("deviceNameDesc") != null ?
                                                addGroupMembersAsFriendsParam.get("deviceNameDesc").toString() :
                                                null;
                                //目标设备描述-即群对应设备描述
                                String targetDeviceNameDesc =
                                        addGroupMembersAsFriendsParam.get("targetDeviceNameDesc") != null ?
                                                addGroupMembersAsFriendsParam.get("targetDeviceNameDesc").toString() :
                                                null;
                                //群的指定目标的设备与当前的设备不符合直接continue
                                if (deviceNameDesc == null || targetDeviceNameDesc == null || !targetDeviceNameDesc.equals(deviceNameDesc)) {
                                    continue;
                                }
                                //判断当前设备的执行小时时间是否与当前时间匹配
                                boolean isExecuteFlag = false;
                                String startHour =
                                        addGroupMembersAsFriendsParam.get("startHour") != null ?
                                                addGroupMembersAsFriendsParam.get("startHour").toString() :
                                                "";
                                String currentHour = new SimpleDateFormat("HH").format(currentDate);
                                if (startHour.equals(currentHour)) {    //当前设备在规定的执行时间才执行自动化操作，同时获取对应的appium端口号
                                    //设置当前这杯可执行的标志位
                                    isExecuteFlag = true;
                                    //获取appium端口号
                                    appiumPort = GlobalVariableConfig.getAppiumPort(action, deviceNameDesc);
                                    addGroupMembersAsFriendsParam.put("appiumPort", appiumPort);
                                } else {
                                    isExecuteFlag = false;
                                    logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】，当前设备的执行时间第【" + startHour + "】小时，当前时间是第【" + currentHour + "】小时....");
                                    continue;
                                }
                                try {
                                    if (isExecuteFlag) {
                                        //开始【添加群成员为好友的V群】
                                        logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc+ "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】即将开始....");
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
                    } else {
                        logger.info("【添加群成员为好友的V群】" + deviceNameListAnddeviceLocaltionOfCode + " 设备列表和配套的坐标配置 不存在，请使用adb命令查询设备号并入库.");
                    }

                    //4.对执行失败的设备进行重新执行【添加群成员为好友的V群】,最多重复执行15次，每间隔4次重启一次手机
                    Integer index = 1;
                    while (reboot_addGroupMembersAsFriendsParam.size() > 0) {
                        //等待所有设备重启
                        Thread.sleep(45000);
//                        //测试
//                        Thread.sleep(1000);
                        if (index > 15) {
                            break;
                        }
                        logger.info("【添加群成员为好友的V群】第【" + index + "】次重新执行设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】...");
                        try {
                            logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc+ "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】即将开始....");
                            isOperatedFlag = new RealMachineDevices().addGroupMembersAsFriends(reboot_addGroupMembersAsFriendsParam);
                            reboot_addGroupMembersAsFriendsParam.clear();
                            Thread.sleep(5000);
//                            //测试
//                            if(index==15){
//                                isOperatedFlag = true;
//                                reboot_addGroupMembersAsFriendsParam.clear();
//                            }
//                            Thread.sleep(1000);
                        } catch (Exception e) {     //当运行设备异常之后，就会对当前设备进行记录，准备重启，后续再对此设备进行重新执行
                            e.printStackTrace();
                            try {
                                if (index % 4 == 0) {
                                    //【添加群成员为好友的V群】过程中，出现不会对设备进行重启，所以在重新执行的单个过程出现异常则重启
                                    CommandUtil.run("/opt/android_sdk/platform-tools/adb -s " + deviceName + " reboot");
                                    logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】重启成功...");
                                }
                            } catch (Exception e1) {
                                logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】重启失败...");
                            }
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
                        String exceptionDevices = "异常设备列表"+ "【" + deviceNameDesc + "】";
                        logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                        logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                        logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                        logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                        logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");

                        //建议使用http协议访问阿里云，通过阿里元来完成此操作.
                        HttpsUtil httpsUtil = new HttpsUtil();
                        Map<String, String> exceptionDevicesParamMap = Maps.newHashMap();
                        exceptionDevicesParamMap.put("nickName", nickName);
                        exceptionDevicesParamMap.put("operatorName", "添加群成员为好友的V群");
                        exceptionDevicesParamMap.put("exceptionDevices", exceptionDevices);
                        String exceptionDevicesNotifyUrl = "https://www.yzkj.store/oilStationMap/wxMessage/exceptionDevicesMessageSend";
                        String resultJson = httpsUtil.post(exceptionDevicesNotifyUrl, exceptionDevicesParamMap);
                        logger.info("微信消息异常发送反馈：" + resultJson);
                        //邮件通知
                        StringBuffer mailMessageBuf = new StringBuffer();
                        mailMessageBuf.append("蔡红旺，您好：\n");
                        mailMessageBuf.append("        ").append("\t操作名称：添加群成员为好友的V群").append("\n");
                        mailMessageBuf.append("        ").append("\t微信群：").append(nickName).append("\n");
                        mailMessageBuf.append("        ").append("\t操作设备：").append(exceptionDevices).append("\n");
                        mailMessageBuf.append("        ").append("\t异常时间：").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("\n");
                        mailMessageBuf.append("        ").append("\t异常地点：").append("北京市昌平区").append("\n");
                        mailMessageBuf.append("        ").append("\t温馨提示：").append("请检查以下手机的接口，并手动辅助自动化操作.").append("\n");
                        mailMessageBuf.append("        ").append("\t异常原因描述：").append("Usb接口不稳定断电或者微信版本已被更新导致坐标不匹配").append("\n");
                        mailService.sendSimpleMail("caihongwang@dingtalk.com", "【服务异常通知】添加群成员为好友的V群", mailMessageBuf.toString());
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
                                    wxDicService.updateDic(tempMap);    //更新这个群信息
                                    if ("1".equals(tempMap.get("dicStatus").toString())) {            //状态为1，则认为当前群成员都已经加过一遍了，发送微信消息通知群主给该设备换群
                                        String targetDeviceNameDesc = addGroupMembersAsFriendsParam.get("targetDeviceNameDesc").toString();
                                        //建议使用http协议访问阿里云，通过阿里元来完成此操作.
                                        HttpsUtil httpsUtil = new HttpsUtil();
                                        Map<String, String> exceptionDevicesParamMap = Maps.newHashMap();
                                        exceptionDevicesParamMap.put("serviceProgress_first", "自动化【添加群成员为好友的V群】");
                                        exceptionDevicesParamMap.put("serviceProgress_keyword1", "设备【" + targetDeviceNameDesc + "】群成员添加");
                                        exceptionDevicesParamMap.put("serviceProgress_keyword2", "已完成，请给该设备微信更换新的群来进行添加群成员");
                                        exceptionDevicesParamMap.put("serviceProgress_remark", "当前设备【" + targetDeviceNameDesc + "】已经将【" + nickName + "】群成员已全部申请添加为好友，请管理员为该设备绑定新的群进行当前自动化操作.");
                                        String exceptionDevicesNotifyUrl = "https://www.yzkj.store/oilStationMap/wxMessage/dailyServiceProgressMessageSend";
                                        String resultJson = httpsUtil.post(exceptionDevicesNotifyUrl, exceptionDevicesParamMap);
                                        logger.info("微信消息异常发送反馈：" + resultJson);

                                        //邮件通知
                                        StringBuffer mailMessageBuf = new StringBuffer();
                                        mailMessageBuf.append("蔡红旺，您好：\n");
                                        mailMessageBuf.append("        ").append("\t操作名称：添加群成员为好友的V群").append("\n");
                                        mailMessageBuf.append("        ").append("\t微信群：").append(nickName).append("\n");
                                        mailMessageBuf.append("        ").append("\t操作设备：").append(targetDeviceNameDesc).append("\n");
                                        mailMessageBuf.append("        ").append("\t完成时间：").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("\n");
                                        mailMessageBuf.append("        ").append("\t完成地点：").append("北京市昌平区").append("\n");
                                        mailMessageBuf.append("        ").append("\t服务状态：").append("已完成对【" + nickName + "】添加群成员为好友.").append("\n");
                                        mailMessageBuf.append("        ").append("\t温馨提示：").append("当前设备【" + targetDeviceNameDesc + "】已经将【" + nickName + "】群成员已全部申请添加为好友，请管理员为该设备绑定新的群进行当前自动化操作.").append("\n");
                                        mailService.sendSimpleMail("caihongwang@dingtalk.com", "【服务完成通知】添加群成员为好友的V群", mailMessageBuf.toString());
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
                } else {
                    logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】失败....");
                }
            }
        }
    }
}
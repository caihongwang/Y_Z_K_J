package com.automation.utils.wei_xin.saveToAddressBook;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.automation.dto.ResultDTO;
import com.automation.service.Automation_DicService;
import com.automation.service.Automation_MailService;
import com.automation.utils.EmojiUtil;
import com.automation.utils.TimestampUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.automation.config.GlobalVariableConfig;
import com.automation.dao.Automation_DicDao;
import com.automation.utils.CommandUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 将群保存到通讯录工具
 * appium -p 4725 -bp 4726 --session-override --command-timeout 600
 */
@Component
public class SaveToAddressBookUtils {

    public static final Logger logger = LoggerFactory.getLogger(SaveToAddressBookUtils.class);

    @Autowired
    public Automation_DicDao automation_DicDao;

    @Autowired
    public Automation_MailService automation_MailService;

    @Autowired
    public Automation_DicService automation_DicService;

    /**
     * 将群保存到通讯录
     *
     * @param paramMap
     * @throws Exception
     */
    public void saveToAddressBook(Map<String, Object> paramMap) throws Exception {
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
        //appiumPort
        String appiumPort = null;
        //设备编码
        String deviceName = "未知-设备编码";
        //设备描述
        String deviceNameDesc = "未知-设备描述";
        //设备执行小时
        String deviceStartHour = "未知-设备时间";
        //当前 自动化操作 将群保存到通讯录
        String action = "saveToAddressBook";
        //获取 将群保存到通讯录 设备列表和配套的坐标配置
        String deviceNameListAnddeviceLocaltionOfCode = "HuaWeiListAndSaveToAddressBookLocaltion";
        //获取设备列表和配套的坐标配置
        paramMap.clear();
        paramMap.put("dicType", "deviceNameListAndLocaltion");
        paramMap.put("dicCode", deviceNameListAnddeviceLocaltionOfCode);
        List<Map<String, Object>> deviceNameAndLocaltionList = automation_DicDao.getSimpleDicByCondition(paramMap);
        if (deviceNameAndLocaltionList == null && deviceNameAndLocaltionList.size() <= 0) {
            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】" + deviceNameListAnddeviceLocaltionOfCode + " 设备列表和配套的坐标配置 不存在，请使用adb命令查询设备号并入库.");
            throw new Exception("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】" + deviceNameListAnddeviceLocaltionOfCode + " 设备列表和配套的坐标配置 不存在，请使用adb命令查询设备号并入库.");
        }
        Map<String, Object> deviceNameAndLocaltionMap = deviceNameAndLocaltionList.get(0);
        for (String currentDevice : currentDeviceList) {
            try {
                boolean isOperatedFlag = false;     //当前设备是否操作【已经添加过好友】的标志位
                Map<String, Object> saveToAddressBookParam = Maps.newHashMap();
                HashMap<String, Object> reboot_saveToAddressBookParam = Maps.newHashMap();
                //获取当前时间，用于校验【那台设备】在【当前时间】执行【当前自动化操作】
                String currentHour = currentDevice.contains("_") ? currentDevice.split("_")[1] : null;
                //获取dicRemark
                String deviceNameAndLocaltionStr = deviceNameAndLocaltionMap.get("dicRemark") != null ? deviceNameAndLocaltionMap.get("dicRemark").toString() : "";
                JSONObject deviceNameAndLocaltionJSONObject = JSONObject.parseObject(deviceNameAndLocaltionStr);
                //获取设备坐标
                String deviceLocaltionStr = deviceNameAndLocaltionJSONObject.getString("deviceLocaltion");
                Map<String, Object> deviceLocaltionMap = JSONObject.parseObject(deviceLocaltionStr, Map.class);
                saveToAddressBookParam.putAll(deviceLocaltionMap);
                //获取设备列表
                String deviceNameListStr = deviceNameAndLocaltionJSONObject.getString("deviceNameList");
                List<Map<String, Object>> deviceNameList = JSONObject.parseObject(deviceNameListStr, List.class);
                if (deviceNameList != null && deviceNameList.size() > 0) {
                    for (Map<String, Object> deviceNameMap : deviceNameList) {
                        saveToAddressBookParam.putAll(deviceNameMap);
                        //获取设备编码
                        deviceName = saveToAddressBookParam.get("deviceName") != null ? saveToAddressBookParam.get("deviceName").toString() : null;
                        //当前设备描述
                        deviceNameDesc = saveToAddressBookParam.get("deviceNameDesc") != null ? saveToAddressBookParam.get("deviceNameDesc").toString() : null;
                        //当前设备执行小时
                        deviceStartHour = deviceNameDesc.contains("_") ? deviceNameDesc.split("_")[1] : null;
                        if (deviceStartHour == null || deviceNameDesc == null) {
                            continue;
                        }

                        //判断当前设备的执行小时时间是否与当前时间匹配
                        boolean isExecuteFlag = false;
                        if (deviceStartHour.equals(currentHour)) {    //当前设备在规定的执行时间才执行自动化操作，同时获取对应的appium端口号
                            if (CommandUtil.isOnline4AndroidDevice(deviceName)) {
                                try {
                                    //获取appium端口号
                                    appiumPort = GlobalVariableConfig.getAppiumPort(action, deviceNameDesc);
                                    saveToAddressBookParam.put("appiumPort", appiumPort);
                                    //设置当前这杯可执行的标志位
                                    isExecuteFlag = true;
                                } catch (Exception e) {
                                    //获取appium端口号失败
                                    logger.error("【将群保存到通讯录】" + e.getMessage());
                                    //设置当前这杯可被行的标志位
                                    isExecuteFlag = false;
                                    continue;
                                }
                            } else {
                                //邮件通知，当前设备不在线，Usb接口不稳定断电或者手机被关机或者断电点，需要人工进行排查...
                                StringBuffer mailMessageBuf = new StringBuffer();
                                mailMessageBuf.append("蔡红旺，您好：\n");
                                mailMessageBuf.append("        ").append("\t操作名称：将群保存到通讯录").append("\n");
                                mailMessageBuf.append("        ").append("\t操作设备：").append(deviceNameDesc).append("\n");
                                mailMessageBuf.append("        ").append("\t异常时间：").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("\n");
                                mailMessageBuf.append("        ").append("\t异常地点：").append("北京市昌平区").append("\n");
                                mailMessageBuf.append("        ").append("\t温馨提示：").append("请检查以下手机的接口，并手动辅助自动化操作.").append("\n");
                                mailMessageBuf.append("        ").append("\t异常原因描述：").append("当前设备不在线，Usb接口不稳定断电或者手机被关机或者断电点，需要人工进行排查...").append("\n");
                                paramMap.clear();
                                paramMap.put("to", "caihongwang@dingtalk.com");
                                paramMap.put("subject", "【服务异常通知】将群保存到通讯录");
                                paramMap.put("content", mailMessageBuf.toString());
                                automation_MailService.sendSimpleMail(paramMap);
                                logger.info("【邮件通知】【服务异常通知】将群保存到通讯录 ......");
                            }
                        } else {
                            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】，当前设备的执行时间第【" + deviceStartHour + "】小时，当前时间是第【" + currentHour + "】小时....");
                            continue;
                        }
                        try {
                            if (isExecuteFlag) {
                                //开始【将群保存到通讯录】
                                logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】即将开始....");
                                isOperatedFlag = new RealMachineDevices().saveToAddressBook(saveToAddressBookParam);
                                Thread.sleep(5000);
//                                //测试
//                                isOperatedFlag = true;
//                                reboot_saveToAddressBookParam.putAll(saveToAddressBookParam);
//                                Thread.sleep(1000);
                                break;      //后面时间段的设备不需要执行，因为每个时间段只有个设备可被执行
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            reboot_saveToAddressBookParam.putAll(saveToAddressBookParam);
                            break;      //后面时间段的设备不需要执行，因为每个时间段只有个设备可被执行
                        }
                    }
                }

                //4.对执行失败的设备进行重新执行【将群保存到通讯录】,最多重复执行15次，每间隔4次重启一次手机
                Integer index = 1;
                while (reboot_saveToAddressBookParam.size() > 0) {
                    //等待所有设备重启
                    Thread.sleep(45000);
//                    //测试
//                    Thread.sleep(1000);
                    if (index > 6) {
                        break;
                    }
                    logger.info("【将群保存到通讯录】第【" + index + "】次重新执行设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】...");
                    try {
                        logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】即将开始....");
                        new RealMachineDevices().saveToAddressBook(reboot_saveToAddressBookParam);
                        reboot_saveToAddressBookParam.clear();       //清空需要重新执行的设备参数
                        Thread.sleep(5000);
//                        //测试
//                        if (index == 15) {
//                            isOperatedFlag = true;
//                            reboot_saveToAddressBookParam.clear();
//                        }
//                        Thread.sleep(1000);
                    } catch (Exception e) {     //当运行设备异常之后，就会对当前设备进行记录，准备重启，后续再对此设备进行重新执行
                        e.printStackTrace();
//                        try {
//                            if (index % 4 == 0) {
//                                //【将群保存到通讯录】过程中，出现不会对设备进行重启，所以在重新执行的单个过程出现异常则重启
//                                CommandUtil.run("/opt/android_sdk/platform-tools/adb -s " + deviceName + " reboot");
//                                logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】重启成功...");
//                            }
//                        } catch (Exception e1) {
//                            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】重启失败...");
//                        }
                    }
                    index++;
                }

                //回收-appiumPort
                GlobalVariableConfig.recoveryAppiumPort(appiumPort);

                if (reboot_saveToAddressBookParam.size() > 0) {
                    logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】15次重新执行均失败....");
                    logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】15次重新执行均失败....");
                    logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】15次重新执行均失败....");
                    logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】15次重新执行均失败....");
                    logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】15次重新执行均失败....");
                    String exceptionDevices = "异常设备列表" + "【" + deviceNameDesc + "】";
                    logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】15次重新执行均失败....");
                    logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】15次重新执行均失败....");
                    logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】15次重新执行均失败....");
                    logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】15次重新执行均失败....");
                    logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】15次重新执行均失败....");
                    //邮件通知
                    StringBuffer mailMessageBuf = new StringBuffer();
                    mailMessageBuf.append("蔡红旺，您好：\n");
                    mailMessageBuf.append("        ").append("\t操作名称：将群保存到通讯录").append("\n");
                    mailMessageBuf.append("        ").append("\t操作设备：").append(deviceNameDesc).append("\n");
                    mailMessageBuf.append("        ").append("\t异常时间：").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("\n");
                    mailMessageBuf.append("        ").append("\t异常地点：").append("北京市昌平区").append("\n");
                    mailMessageBuf.append("        ").append("\t温馨提示：").append("请检查以下手机的接口，并手动辅助自动化操作.").append("\n");
                    mailMessageBuf.append("        ").append("\t异常原因描述：").append("Usb接口不稳定断电或者微信版本已被更新导致坐标不匹配").append("\n");
                    paramMap.clear();
                    paramMap.put("to", "caihongwang@dingtalk.com");
                    paramMap.put("subject", "【服务异常通知】将群保存到通讯录");
                    paramMap.put("content", mailMessageBuf.toString());
                    automation_MailService.sendSimpleMail(paramMap);
                    logger.info("【邮件通知】【服务异常通知】将群保存到通讯录 ......");
                } else {
                    if (isOperatedFlag) {
                        //获取当前设备的群昵称ListStr
                        String groupNickNameMapStr = saveToAddressBookParam.get("groupNickNameMapStr").toString();
                        try {
                            //根据设备描述获取
                            Map<String, Object> tempMap = Maps.newHashMap();
                            tempMap.put("dicType", "deviceNameDescToGroupNameMapStr");
                            tempMap.put("dicName", "设备对应的所有群信息");
                            tempMap.put("dicCode", deviceNameDesc);
                            ResultDTO resultDTO = automation_DicService.getSimpleDicByCondition(tempMap);
                            if (resultDTO != null && resultDTO.getResultList() != null && resultDTO.getResultList().size() > 0) {
                                LinkedHashMap<String, Object> dicRemarkMap = Maps.newLinkedHashMap();
                                dicRemarkMap.put("deviceNameDesc", deviceNameDesc);
                                dicRemarkMap.put("groupNickNameMapStr", groupNickNameMapStr);
                                tempMap.put("dicRemark", EmojiUtil.emojiConvert(JSON.toJSONString(dicRemarkMap)));
                                automation_DicService.updateDic(tempMap);   //新增当前设备的群昵称ListStr
                                logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】更新-群昵称列表入库-成功....");
                            } else {
                                LinkedHashMap<String, Object> dicRemarkMap = Maps.newLinkedHashMap();
                                dicRemarkMap.put("deviceNameDesc", deviceNameDesc);
                                dicRemarkMap.put("groupNickNameMapStr", groupNickNameMapStr);
                                tempMap.put("dicRemark", EmojiUtil.emojiConvert(JSON.toJSONString(dicRemarkMap)));
                                automation_DicService.addDic(tempMap);      //更新当前设备的群昵称ListStr
                                logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】保存-群昵称列表入库-成功....");
                            }
                        } catch (Exception e) {
                            logger.error("【将群保存到通讯录】设备编码【" + deviceName + "】设备描述【" + deviceNameDesc + "】操作【" + action + "】更新当前设备的群昵称ListStr时异常，e : ", e);
                        } finally {
                            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】成功....");
                            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】成功....");
                            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】成功....");
                            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】成功....");
                            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】成功....");
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

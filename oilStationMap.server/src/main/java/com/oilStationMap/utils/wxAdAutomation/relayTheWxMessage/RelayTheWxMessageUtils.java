package com.oilStationMap.utils.wxAdAutomation.relayTheWxMessage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.oilStationMap.config.GlobalVariableConfig;
import com.oilStationMap.dao.WX_DicDao;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.service.MailService;
import com.oilStationMap.service.WX_DicService;
import com.oilStationMap.utils.CommandUtil;
import com.oilStationMap.utils.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 转发微信消息工具
 * appium -p 4723 -bp 4724 --session-override --command-timeout 600
 */
@Component
public class RelayTheWxMessageUtils {

    public static final Logger logger = LoggerFactory.getLogger(RelayTheWxMessageUtils.class);

    @Autowired
    public WX_DicDao wxDicDao;

    @Autowired
    public MailService mailService;

    @Autowired
    public WX_DicService wxDicService;

    /**
     * 转发微信消息for所有设备
     */
    public void relayTheWxMessage(Map<String, Object> paramMap) throws Exception {
        String currentDateListStr = paramMap.get("currentDateListStr") != null ? paramMap.get("currentDateListStr").toString() : "";
        LinkedList<String> currentDateList = Lists.newLinkedList();
        try {
            currentDateList = JSON.parseObject(currentDateListStr, LinkedList.class);
        } catch (Exception e) {
            throw new Exception("解析json时间列表失败，currentDateListStr = " + currentDateListStr + " ， e : ", e);
        } finally {
            if (currentDateList.size() <= 0) {
                currentDateList.add(new SimpleDateFormat("yyyy-MM-dd HH").format(new Date()));
            }
        }
        //appiumPort
        String appiumPort = null;
        //设备编码
        String deviceName = "未知-设备编码";
        //设备描述
        String deviceNameDesc = "未知-设备描述";
        //当前 自动化操作 转发微信消息
        String action = "relayTheWxMessage";
        //获取 转发微信消息 设备列表和配套的坐标配置
        String deviceNameListAnddeviceLocaltionOfCode = "HuaWeiListAndRelayTheWxMessageLocaltion";
        //指定 某一个微信昵称 聊天，并获取即将要发送聊天信息
        paramMap.clear();
        paramMap.put("dicType", "relayTheWxMessage");
        ResultDTO resultDTO = wxDicService.getSimpleDicByCondition(paramMap);
        List<Map<String, String>> relayTheWxMessageList = resultDTO.getResultList();
        //获取设备列表和配套的坐标配置wxDic
        paramMap.clear();
        paramMap.put("dicType", "deviceNameListAndLocaltion");
        paramMap.put("dicCode", deviceNameListAnddeviceLocaltionOfCode);
        List<Map<String, Object>> deviceNameAndLocaltionList = wxDicDao.getSimpleDicByCondition(paramMap);
        if (deviceNameAndLocaltionList == null && deviceNameAndLocaltionList.size() <= 0) {
            logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】" + deviceNameListAnddeviceLocaltionOfCode + " 设备列表和配套的坐标配置 不存在，请使用adb命令查询设备号并入库.");
            throw new Exception("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】" + deviceNameListAnddeviceLocaltionOfCode + " 设备列表和配套的坐标配置 不存在，请使用adb命令查询设备号并入库.");
        }
        Map<String, Object> deviceNameAndLocaltionMap = deviceNameAndLocaltionList.get(0);
        for (String currentDateStr : currentDateList) {
            try {
                //获取当前时间，用于校验【那台设备】在【当前时间】执行【当前自动化操作】
                Date currentDate = new SimpleDateFormat("yyyy-MM-dd HH").parse(currentDateStr);
                Map<String, Object> relayTheWxMessageParam = Maps.newHashMap();
                HashMap<String, Object> reboot_relayTheWxMessageParam = Maps.newHashMap();
                try {
                    for (Map<String, String> relayTheWxMessage : relayTheWxMessageList) {
                        boolean isOperatedFlag = false;     //当前设备是否操作【转发微信消息】的标志位
                        relayTheWxMessageParam.putAll(MapUtil.getObjectMap(relayTheWxMessage));
                        //获取dicRemark
                        String deviceNameAndLocaltionStr = deviceNameAndLocaltionMap.get("dicRemark") != null ? deviceNameAndLocaltionMap.get("dicRemark").toString() : "";
                        JSONObject deviceNameAndLocaltionJSONObject = JSONObject.parseObject(deviceNameAndLocaltionStr);
                        //获取设备坐标
                        String deviceLocaltionStr = deviceNameAndLocaltionJSONObject.getString("deviceLocaltion");
                        Map<String, Object> deviceLocaltionMap = JSONObject.parseObject(deviceLocaltionStr, Map.class);
                        relayTheWxMessageParam.putAll(deviceLocaltionMap);
                        //获取设备列表
                        String deviceNameListStr = deviceNameAndLocaltionJSONObject.getString("deviceNameList");
                        List<Map<String, Object>> deviceNameList = JSONObject.parseObject(deviceNameListStr, List.class);
                        if (deviceNameList != null && deviceNameList.size() > 0) {
                            for (Map<String, Object> deviceNameMap : deviceNameList) {
                                relayTheWxMessageParam.putAll(deviceNameMap);
                                //获取设备编码
                                deviceName = relayTheWxMessageParam.get("deviceName") != null ? relayTheWxMessageParam.get("deviceName").toString() : null;
                                //当前设备描述
                                deviceNameDesc = relayTheWxMessageParam.get("deviceNameDesc") != null ? relayTheWxMessageParam.get("deviceNameDesc").toString() : null;
                                //目标设备描述-即转发群对应设备描述
                                String targetDeviceNameDesc = relayTheWxMessageParam.get("targetDeviceNameDesc") != null ? relayTheWxMessageParam.get("targetDeviceNameDesc").toString() : null;
                                //判断当前设备的执行小时时间是否与当前时间匹配
                                String startHour = relayTheWxMessageParam.get("startHour") != null ? relayTheWxMessageParam.get("startHour").toString() : "";
                                String currentHour = new SimpleDateFormat("HH").format(currentDate);

                                boolean isExecuteFlag = false;      //判断是指定设备及指定时间
                                if (targetDeviceNameDesc.equals(deviceNameDesc) && startHour.equals(currentHour)) {    //当前设备在规定的执行时间才执行自动化操作，同时获取对应的appium端口号
                                    if(CommandUtil.isOnline4AndroidDevice(deviceName)){
                                        try {
                                            //获取appium端口号
                                            appiumPort = GlobalVariableConfig.getAppiumPort(action, deviceNameDesc);
                                            relayTheWxMessageParam.put("appiumPort", appiumPort);
                                            //设置当前这杯可执行的标志位
                                            isExecuteFlag = true;
                                        } catch (Exception e) {
                                            //获取appium端口号失败
                                            logger.error("【转发微信消息】" + e.getMessage());
                                            //设置当前这杯可被行的标志位
                                            isExecuteFlag = false;
                                            continue;
                                        }
                                    } else {
                                        //邮件通知，当前设备不在线，Usb接口不稳定断电或者手机被关机或者断电点，需要人工进行排查...
                                        StringBuffer mailMessageBuf = new StringBuffer();
                                        mailMessageBuf.append("蔡红旺，您好：\n");
                                        mailMessageBuf.append("        ").append("\t操作名称：转发微信消息").append("\n");
                                        mailMessageBuf.append("        ").append("\t操作设备：").append(deviceNameDesc).append("\n");
                                        mailMessageBuf.append("        ").append("\t异常时间：").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("\n");
                                        mailMessageBuf.append("        ").append("\t异常地点：").append("北京市昌平区").append("\n");
                                        mailMessageBuf.append("        ").append("\t温馨提示：").append("请检查以下手机的接口，并手动辅助自动化操作.").append("\n");
                                        mailMessageBuf.append("        ").append("\t异常原因描述：").append("当前设备不在线，Usb接口不稳定断电或者手机被关机或者断电点，需要人工进行排查...").append("\n");
                                        mailService.sendSimpleMail("caihongwang@dingtalk.com", "【服务异常通知】转发微信消息", mailMessageBuf.toString());
                                        logger.info("【邮件通知】【服务异常通知】转发微信消息 ......");
                                    }
                                } else {
//                                    logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】，当前设备的执行时间第【" + startHour + "】小时，当前时间是第【" + currentHour + "】小时....");
                                    continue;
                                }
                                try {
                                    if (isExecuteFlag) {
                                        //开始【转发微信消息】
                                        logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】即将开始....");
                                        isOperatedFlag = new RealMachineDevices().relayTheWxMessage(relayTheWxMessageParam);
                                        Thread.sleep(5000);
//                                        //测试
//                                        isOperatedFlag = true;
//                                        reboot_relayTheWxMessageParam.putAll(relayTheWxMessageParam);
//                                        Thread.sleep(5000);
                                        break;      //后面时间段的设备不需要执行，因为每个时间段只有个设备可被执行
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    reboot_relayTheWxMessageParam.putAll(relayTheWxMessageParam);
                                    break;      //后面时间段的设备不需要执行，因为每个时间段只有个设备可被执行
                                }
                            }
                        }
                        //4.对执行失败的设备进行重新执行【转发微信消息】,最多重复执行15次，每间隔4次重启一次手机
                        Integer index = 1;
                        while (reboot_relayTheWxMessageParam.size() > 0) {
                            //等待所有设备重启
                            Thread.sleep(45000);
//                            //测试
//                            Thread.sleep(1000);
                            if (index > 15) {
                                break;
                            }
                            logger.info("【转发微信消息】第【" + index + "】次重新执行设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】...");
                            try {
                                logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】即将开始....");
                                new RealMachineDevices().relayTheWxMessage(reboot_relayTheWxMessageParam);
                                reboot_relayTheWxMessageParam.clear();       //清空需要重新执行的设备参数
                                Thread.sleep(5000);
//                                //测试
//                                if (index == 15) {
//                                    isOperatedFlag = true;
//                                    reboot_relayTheWxMessageParam.clear();
//                                }
//                                Thread.sleep(1000);
                            } catch (Exception e) {     //当运行设备异常之后，就会对当前设备进行记录，准备重启，后续再对此设备进行重新执行
                                e.printStackTrace();
//                                try {
//                                    if (index % 4 == 0) {
//                                        //【转发微信消息】过程中，出现不会对设备进行重启，所以在重新执行的单个过程出现异常则重启
//                                        CommandUtil.run("/opt/android_sdk/platform-tools/adb -s " + deviceName + " reboot");
//                                        logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】重启成功...");
//                                    }
//                                } catch (Exception e1) {
//                                    logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】重启失败...");
//                                }
                            }
                            index++;
                        }

                        //回收-appiumPort
                        GlobalVariableConfig.recoveryAppiumPort(appiumPort);

                        if (reboot_relayTheWxMessageParam.size() > 0) {
                            logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】15次重新执行均失败....");
                            logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】15次重新执行均失败....");
                            logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】15次重新执行均失败....");
                            logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】15次重新执行均失败....");
                            logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】15次重新执行均失败....");
                            String exceptionDevices = "异常设备列表" + "【" + deviceNameDesc + "】";
                            logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】15次重新执行均失败....");
                            logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】15次重新执行均失败....");
                            logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】15次重新执行均失败....");
                            logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】15次重新执行均失败....");
                            logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】15次重新执行均失败....");
                            //邮件通知
                            StringBuffer mailMessageBuf = new StringBuffer();
                            mailMessageBuf.append("蔡红旺，您好：\n");
                            mailMessageBuf.append("        ").append("\t操作名称：转发微信消息").append("\n");
                            mailMessageBuf.append("        ").append("\t操作设备：").append(exceptionDevices).append("\n");
                            mailMessageBuf.append("        ").append("\t异常时间：").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("\n");
                            mailMessageBuf.append("        ").append("\t异常地点：").append("北京市昌平区").append("\n");
                            mailMessageBuf.append("        ").append("\t温馨提示：").append("请检查以下手机的接口，并手动辅助自动化操作.").append("\n");
                            mailMessageBuf.append("        ").append("\t异常原因描述：").append("Usb接口不稳定断电或者微信版本已被更新导致坐标不匹配").append("\n");
                            mailService.sendSimpleMail("caihongwang@dingtalk.com", "【服务异常通知】转发微信消息", mailMessageBuf.toString());
                            logger.info("【邮件通知】【服务异常通知】转发微信消息 ......");
                        } else {
                            if (isOperatedFlag) {
                                logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + action + "】成功....");
                                logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + action + "】成功....");
                                logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + action + "】成功....");
                                logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + action + "】成功....");
                                logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + action + "】成功....");
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】已处理完毕...");
        logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】已处理完毕...");
        logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】已处理完毕...");
        logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】已处理完毕...");
        logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】已处理完毕...");
    }
}

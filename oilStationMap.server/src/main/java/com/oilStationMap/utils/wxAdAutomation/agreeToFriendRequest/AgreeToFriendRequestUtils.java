package com.oilStationMap.utils.wxAdAutomation.agreeToFriendRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.oilStationMap.config.GlobalVariableConfig;
import com.oilStationMap.dao.WX_DicDao;
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
 * 同意好友请求工具
 * appium -p 4725 -bp 4726 --session-override --command-timeout 600
 */
public class AgreeToFriendRequestUtils {

    public static final Logger logger = LoggerFactory.getLogger(AgreeToFriendRequestUtils.class);

    public static WX_DicDao wxDicDao = (WX_DicDao) ApplicationContextUtils.getBeanByClass(WX_DicDao.class);

    public static MailService mailService = (MailService) ApplicationContextUtils.getBeanByClass(MailServiceImpl.class);

    public static WX_DicService wxDicService = (WX_DicService) ApplicationContextUtils.getBeanByClass(WX_DicServiceImpl.class);

    public static WX_MessageService wxMessageService = (WX_MessageService) ApplicationContextUtils.getBeanByClass(WX_MessageServiceImpl.class);

    public static GlobalVariableConfig globalVariableConfig = (com.oilStationMap.config.GlobalVariableConfig) ApplicationContextUtils.getBeanByClass(GlobalVariableConfig.class);

    /**
     * 同意好友请求
     *
     * @param paramMap
     * @throws Exception
     */
    public static void agreeToFriendRequest(Map<String, Object> paramMap) throws Exception {
        String currentDateListStr = paramMap.get("currentDateListStr") != null ? paramMap.get("currentDateListStr").toString() : "";
        LinkedList<String> currentDateList = Lists.newLinkedList();
        try {
            currentDateList = JSON.parseObject(currentDateListStr, LinkedList.class);
        } catch (Exception e) {
            logger.error("解析json时间列表失败，currentDateListStr = " + currentDateListStr + " ， e : ", e);
            currentDateList.add(new SimpleDateFormat("yyyy-MM-dd HH").format(new Date()));
        }
        if (currentDateList.size() <= 0) {
            currentDateList.add(new SimpleDateFormat("yyyy-MM-dd HH").format(new Date()));
        }
        //appium端口号
        String appiumPort = null;
        try{
            appiumPort = globalVariableConfig.appiumPortList.get(0);     //从全局变量中获取appium端口号并移除，避免其他线程抢端口号
            globalVariableConfig.appiumPortList.remove(appiumPort);
        } catch (Exception e) {
            throw new Exception("当前没有空闲的appium端口号.");
        }
        for (String currentDateStr : currentDateList) {
            String deviceName = "未知-设备编码";
            String deviceNameDesc = "未知-设备描述";
            Map<String, Object> agreeToFriendRequestParam = Maps.newHashMap();
            Date currentDate = new SimpleDateFormat("yyyy-MM-dd HH").parse(currentDateStr);
            HashMap<String, Object> reboot_agreeToFriendRequestParam = Maps.newHashMap();
            //获取设备列表和配套的坐标配置
            List<String> dicCodeList = Lists.newArrayList();
            dicCodeList.add("HuaWeiListAndAgreeToFriendRequestLocaltion");//获取 华为 Mate 8 设备列表和配套的坐标配置
            for (String dicCode : dicCodeList) {
                paramMap.clear();
                paramMap.put("dicType", "deviceNameListAndLocaltion");
                paramMap.put("dicCode", dicCode);
                List<Map<String, Object>> list = wxDicDao.getSimpleDicByCondition(paramMap);        //当前设备列表和配套的坐标配置
                if (list != null && list.size() > 0) {
                    String deviceNameAndLocaltionStr = list.get(0).get("dicRemark") != null ? list.get(0).get("dicRemark").toString() : "";
                    JSONObject deviceNameAndLocaltionJSONObject = JSONObject.parseObject(deviceNameAndLocaltionStr);
                    //获取设备坐标
                    String deviceLocaltionStr = deviceNameAndLocaltionJSONObject.getString("deviceLocaltion");
                    Map<String, Object> deviceLocaltionMap = JSONObject.parseObject(deviceLocaltionStr, Map.class);
                    agreeToFriendRequestParam.putAll(deviceLocaltionMap);
                    //获取设备列表
                    String deviceNameListStr = deviceNameAndLocaltionJSONObject.getString("deviceNameList");
                    List<Map<String, Object>> deviceNameList = JSONObject.parseObject(deviceNameListStr, List.class);
                    //appium端口号
//                    String appiumPort = deviceNameAndLocaltionJSONObject.getString("appiumPort");
                    agreeToFriendRequestParam.put("appiumPort", appiumPort);
                    if (deviceNameList != null && deviceNameList.size() > 0) {
                        for (Map<String, Object> deviceNameMap : deviceNameList) {
                            agreeToFriendRequestParam.putAll(deviceNameMap);
                            try {
                                //判断当前设备的执行小时时间是否与当前时间匹配
                                String startHour =
                                        agreeToFriendRequestParam.get("startHour") != null ?
                                                agreeToFriendRequestParam.get("startHour").toString() :
                                                "";
                                String currentHour = new SimpleDateFormat("HH").format(currentDate);
                                if (startHour.equals(currentHour)) {
                                    deviceName = agreeToFriendRequestParam.get("deviceName").toString();
                                    deviceNameDesc = agreeToFriendRequestParam.get("deviceNameDesc").toString();
                                    //开始同意好友请求
                                    logger.info("设备编码【" + agreeToFriendRequestParam.get("deviceName") + "】设备描述【" + agreeToFriendRequestParam.get("deviceNameDesc") + "】将同意好友请求即将开始发送....");
                                    new RealMachineDevices().agreeToFriendRequest(agreeToFriendRequestParam);
                                    Thread.sleep(5000);
//                                    //重新操作
//                                    reboot_agreeToFriendRequestParam.putAll(agreeToFriendRequestParam);
                                } else {
                                    //下一个设备
                                    logger.info("设备编码【" + agreeToFriendRequestParam.get("deviceName") + "】设备描述【" + agreeToFriendRequestParam.get("deviceNameDesc") + "】，当前设备的执行时间第【" + startHour + "】小时，当前时间是第【" + currentHour + "】小时....");
                                    continue;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                reboot_agreeToFriendRequestParam.putAll(agreeToFriendRequestParam);
                            }
                        }
                    }
                } else {
                    logger.info(dicCode + " 设备列表和配套的坐标配置 不存在，请使用adb命令查询设备号并入库.");
                }
            }

            //对执行失败的设备列表进行重新执行,最多循环执行5遍
            Integer index = 1;
            while (reboot_agreeToFriendRequestParam.size() > 0) {
                //等待所有设备重启
                try {
                    Thread.sleep(60000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (index > 15) {
                    break;
                }
                logger.info("第【" + index + "】设备编码【" + deviceName + "】设备描述【" + deviceNameDesc + "】次重新执行失败的设备....");
                try {
                    logger.info("设备编码【" + deviceName + "】设备描述【" + deviceNameDesc + "】将同意好友请求即将开始发送....");
                    new RealMachineDevices().agreeToFriendRequest(reboot_agreeToFriendRequestParam);
                    reboot_agreeToFriendRequestParam.clear();       //清空需要重新执行的设备参数
                    Thread.sleep(5000);
                } catch (Exception e) {     //当运行设备异常之后，就会对当前设备进行记录，准备重启，后续再对此设备进行重新执行
                    e.printStackTrace();
                    try {
                        if (index % 4 == 0) {
                            //【添加群成员为好友的V群】过程中，出现不会对设备进行重启，所以在重新执行的单个过程出现异常则重启
                            CommandUtil.run("/opt/android_sdk/platform-tools/adb -s " + deviceName + " reboot");
                            logger.info("重启成功，设备编码【" + deviceName + "】设备描述【" + deviceNameDesc + "】");
                        }
                    } catch (Exception e1) {
                        logger.info("重启失败，设备编码【" + deviceName + "】设备描述【" + deviceNameDesc + "】");
                    }
                }
                index++;
            }
            if (reboot_agreeToFriendRequestParam.size() > 0) {
                logger.info("设备编码【" + deviceName + "】设备描述【" + deviceNameDesc + "】【同意好友请求】5次次批量执行均失败的设备如下....");
                logger.info("设备编码【" + deviceName + "】设备描述【" + deviceNameDesc + "】【同意好友请求】5次次批量执行均失败的设备如下....");
                logger.info("设备编码【" + deviceName + "】设备描述【" + deviceNameDesc + "】【同意好友请求】5次次批量执行均失败的设备如下....");
                logger.info("设备编码【" + deviceName + "】设备描述【" + deviceNameDesc + "】【同意好友请求】5次次批量执行均失败的设备如下....");
                logger.info("设备编码【" + deviceName + "】设备描述【" + deviceNameDesc + "】【同意好友请求】5次次批量执行均失败的设备如下....");
                String exceptionDevices = "异常设备列表";
                exceptionDevices = exceptionDevices + "【" + deviceNameDesc + "】";
                logger.info("设备编码【" + deviceName + "】设备描述【" + deviceNameDesc + "】【同意好友请求】5次次批量执行均失败的设备如上....");
                logger.info("设备编码【" + deviceName + "】设备描述【" + deviceNameDesc + "】【同意好友请求】5次次批量执行均失败的设备如上....");
                logger.info("设备编码【" + deviceName + "】设备描述【" + deviceNameDesc + "】【同意好友请求】5次次批量执行均失败的设备如上....");
                logger.info("设备编码【" + deviceName + "】设备描述【" + deviceNameDesc + "】【同意好友请求】5次次批量执行均失败的设备如上....");
                logger.info("设备编码【" + deviceName + "】设备描述【" + deviceNameDesc + "】【同意好友请求】5次次批量执行均失败的设备如上....");
                //建议使用http协议访问阿里云，通过阿里元来完成此操作.
                HttpsUtil httpsUtil = new HttpsUtil();
                Map<String, String> exceptionDevicesParamMap = Maps.newHashMap();
                exceptionDevicesParamMap.put("nickName", "无");
                exceptionDevicesParamMap.put("operatorName", "同意好友请求");
                exceptionDevicesParamMap.put("exceptionDevices", exceptionDevices);
                String exceptionDevicesNotifyUrl = "https://www.yzkj.store/oilStationMap/wxMessage/exceptionDevicesMessageSend";
                String resultJson = httpsUtil.post(exceptionDevicesNotifyUrl, exceptionDevicesParamMap);
                logger.info("微信消息异常发送反馈：" + resultJson);

                //邮件通知
                StringBuffer mailMessageBuf = new StringBuffer();
                mailMessageBuf.append("蔡红旺，您好：\n");
                mailMessageBuf.append("        ").append("\t操作名称：同意好友请求").append("\n");
                mailMessageBuf.append("        ").append("\t操作设备：").append(deviceNameDesc).append("\n");
                mailMessageBuf.append("        ").append("\t异常时间：").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("\n");
                mailMessageBuf.append("        ").append("\t异常地点：").append("北京市昌平区").append("\n");
                mailMessageBuf.append("        ").append("\t温馨提示：").append("请检查以下手机的接口，并手动辅助自动化操作.").append("\n");
                mailMessageBuf.append("        ").append("\t异常原因描述：").append("Usb接口不稳定断电或者微信版本已被更新导致坐标不匹配").append("\n");
                mailService.sendSimpleMail("caihongwang@dingtalk.com", "【服务异常通知】同意好友请求", mailMessageBuf.toString());
            } else {
                logger.info("设备编码【" + deviceName + "】设备描述【" + deviceNameDesc + "】【同意好友请求】全部执行成功....");
                logger.info("设备编码【" + deviceName + "】设备描述【" + deviceNameDesc + "】【同意好友请求】全部执行成功....");
                logger.info("设备编码【" + deviceName + "】设备描述【" + deviceNameDesc + "】【同意好友请求】全部执行成功....");
                logger.info("设备编码【" + deviceName + "】设备描述【" + deviceNameDesc + "】【同意好友请求】全部执行成功....");
                logger.info("设备编码【" + deviceName + "】设备描述【" + deviceNameDesc + "】【同意好友请求】全部执行成功....");
            }
        }
        if(appiumPort != null){
            globalVariableConfig.appiumPortList.add(appiumPort);        //回收已使用完成的appium端口号
        }
    }
}

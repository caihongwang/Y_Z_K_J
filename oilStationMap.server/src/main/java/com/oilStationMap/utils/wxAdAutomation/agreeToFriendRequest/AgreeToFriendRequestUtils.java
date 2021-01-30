package com.oilStationMap.utils.wxAdAutomation.agreeToFriendRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.oilStationMap.dao.WX_DicDao;
import com.oilStationMap.service.WX_DicService;
import com.oilStationMap.service.WX_MessageService;
import com.oilStationMap.service.impl.WX_DicServiceImpl;
import com.oilStationMap.service.impl.WX_MessageServiceImpl;
import com.oilStationMap.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 同意好友请求工具
 * appium -p 4723 -bp 4724 --session-override --command-timeout 600
 */
public class AgreeToFriendRequestUtils {

    public static final Logger logger = LoggerFactory.getLogger(AgreeToFriendRequestUtils.class);

    public static WX_DicDao wxDicDao = (WX_DicDao) ApplicationContextUtils.getBeanByClass(WX_DicDao.class);

    public static WX_DicService wxDicService = (WX_DicService) ApplicationContextUtils.getBeanByClass(WX_DicServiceImpl.class);

    public static WX_MessageService wxMessageService = (WX_MessageService) ApplicationContextUtils.getBeanByClass(WX_MessageServiceImpl.class);

    /**
     * 同意好友请求
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
        for (String currentDateStr : currentDateList) {
            Map<String, Object> agreeToFriendRequestParam = Maps.newHashMap();
            Date currentDate = new SimpleDateFormat("yyyy-MM-dd HH").parse(currentDateStr);
            HashMap<String, Object> reboot_agreeToFriendRequestParam = Maps.newHashMap();
            //获取设备列表和配套的坐标配置
            List<String> dicCodeList = Lists.newArrayList();
            dicCodeList.add("HuaWeiListAndShareArticleToFriendCircleLocaltion");//获取 华为 Mate 8 设备列表和配套的坐标配置
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
                    if (deviceNameList != null && deviceNameList.size() > 0) {
                        for (Map<String, Object> deviceNameMap : deviceNameList) {
                            agreeToFriendRequestParam.putAll(deviceNameMap);
                            //判断推广时间是否还在推广期内
                            String startTimeStr = agreeToFriendRequestParam.get("startTime") != null ? agreeToFriendRequestParam.get("startTime").toString() : "";
                            String endTimeStr = agreeToFriendRequestParam.get("endTime") != null ? agreeToFriendRequestParam.get("endTime").toString() : "";
                            if (!"".equals(startTimeStr) && !"".equals(endTimeStr)) {
                                try {
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date startTime = sdf.parse(startTimeStr);
                                    Date endTime = sdf.parse(endTimeStr);
                                    if (DateUtil.isEffectiveDate(currentDate, startTime, endTime)) {
                                        //判断当前设备的执行小时时间是否与当前时间匹配
                                        String startHour =
                                                agreeToFriendRequestParam.get("startHour") != null ?
                                                        agreeToFriendRequestParam.get("startHour").toString() :
                                                        "";
                                        String currentHour = new SimpleDateFormat("HH").format(currentDate);
                                        if (startHour.equals(currentHour)) {
                                            //开始同意好友请求
                                            logger.info("设备描述【" + agreeToFriendRequestParam.get("deviceNameDesc") + "】设备编码【" + agreeToFriendRequestParam.get("deviceName") + "】操作【" + agreeToFriendRequestParam.get("action") + "】的将同意好友请求即将开始发送....");
                                            new RealMachineDevices().agreeToFriendRequest(agreeToFriendRequestParam);
                                            Thread.sleep(5000);
                                        } else {
                                            //下一个设备
                                            logger.info("设备描述【" + agreeToFriendRequestParam.get("deviceNameDesc") + "】设备编码【" + agreeToFriendRequestParam.get("deviceName") + "】，当前设备的执行时间第【" + startHour + "】小时，当前时间是第【" + currentHour + "】小时....");
                                            continue;
                                        }
//                                        logger.info( "设备描述【"+agreeToFriendRequestParam.get("deviceNameDesc")+"】设备编码【"+agreeToFriendRequestParam.get("deviceName")+"】操作【"+agreeToFriendRequestParam.get("action")+"】昵称【"+nickName+"】的将同意好友请求即将开始发送....");
//                                        new RealMachineDevices().shareArticleToFriendCircle(agreeToFriendRequestParam);
//                                        Thread.sleep(5000);
                                    } else if (DateUtil.isBeforeDate(currentDate, startTime)) {
                                        logger.info("尚未开始，暂不处理....");
                                    } else {
                                        logger.info("昵称【" + agreeToFriendRequestParam.get("deviceName") + "】的同意好友请求内容已到期....");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    reboot_agreeToFriendRequestParam.putAll(agreeToFriendRequestParam);
                                }
                            } else {
                                logger.info("设备【" + agreeToFriendRequestParam.get("deviceName") + "】的同意好友请求日期不能为空....");
                            }
                        }
                    }
                } else {
                    logger.info(dicCode + " 设备列表和配套的坐标配置 不存在，请使用adb命令查询设备号并入库.");
                }
            }

            String action = agreeToFriendRequestParam.get("action").toString();
            String deviceName = agreeToFriendRequestParam.get("deviceName").toString();
            String deviceNameDesc = agreeToFriendRequestParam.get("deviceNameDesc").toString();

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
                logger.info("第【" + index + "】次批量重新执行失败的设备....");
                try {
                    logger.info("设备描述【" + reboot_agreeToFriendRequestParam.get("deviceNameDesc") + "】设备编码【" + deviceName + "】操作【" + reboot_agreeToFriendRequestParam.get("action") + "】的将同意好友请求即将开始发送....");
                    new RealMachineDevices().agreeToFriendRequest(reboot_agreeToFriendRequestParam);
                    reboot_agreeToFriendRequestParam.clear();       //清空需要重新执行的设备参数
                    Thread.sleep(5000);
                } catch (Exception e) {     //当运行设备异常之后，就会对当前设备进行记录，准备重启，后续再对此设备进行重新执行
                    e.printStackTrace();
                    try {
                        if (index % 4 == 0) {
                            //【添加群成员为好友的V群】过程中，出现不会对设备进行重启，所以在重新执行的单个过程出现异常则重启
                            CommandUtil.run("/opt/android_sdk/platform-tools/adb -s " + deviceName + " reboot");
                            logger.info("重启成功，设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】");
                        }
                    } catch (Exception e1) {
                        logger.info("重启失败，设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】");
                    }
                }
                index++;
            }
            if (reboot_agreeToFriendRequestParam.size() > 0) {
                logger.info("【" + deviceName + "】【同意好友请求】5次次批量执行均失败的设备如下....");
                logger.info("【" + deviceName + "】【同意好友请求】5次次批量执行均失败的设备如下....");
                logger.info("【" + deviceName + "】【同意好友请求】5次次批量执行均失败的设备如下....");
                logger.info("【" + deviceName + "】【同意好友请求】5次次批量执行均失败的设备如下....");
                logger.info("【" + deviceName + "】【同意好友请求】5次次批量执行均失败的设备如下....");
                String exceptionDevices = "异常设备列表";
                exceptionDevices = exceptionDevices + "【" + deviceName + "】";
                logger.info("【" + deviceName + "】【同意好友请求】5次次批量执行均失败的设备如上....");
                logger.info("【" + deviceName + "】【同意好友请求】5次次批量执行均失败的设备如上....");
                logger.info("【" + deviceName + "】【同意好友请求】5次次批量执行均失败的设备如上....");
                logger.info("【" + deviceName + "】【同意好友请求】5次次批量执行均失败的设备如上....");
                logger.info("【" + deviceName + "】【同意好友请求】5次次批量执行均失败的设备如上....");
                //建议使用http协议访问阿里云，通过阿里元来完成此操作.
                HttpsUtil httpsUtil = new HttpsUtil();
                Map<String, String> exceptionDevicesParamMap = Maps.newHashMap();
                exceptionDevicesParamMap.put("nickName", "无");
                exceptionDevicesParamMap.put("operatorName", "同意好友请求");
                exceptionDevicesParamMap.put("exceptionDevices", exceptionDevices);
                String exceptionDevicesNotifyUrl = "https://www.yzkj.store/oilStationMap/wxMessage/exceptionDevicesMessageSend";
                String resultJson = httpsUtil.post(exceptionDevicesNotifyUrl, exceptionDevicesParamMap);
                logger.info("微信消息异常发送反馈：" + resultJson);
//                //本地发送，但是基于IP不断变化，不建议
//                try {
//                    Map<String, Object> exceptionDevicesParamMap = Maps.newHashMap();
//                    exceptionDevicesParamMap.put("operatorName", "同意好友请求");
//                    exceptionDevicesParamMap.put("exceptionDevices", exceptionDevices);
//                    wxMessageService.exceptionDevicesMessageSend(exceptionDevicesParamMap);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            } else {
                logger.info("【" + deviceName + "】【同意好友请求】全部执行成功....");
                logger.info("【" + deviceName + "】【同意好友请求】全部执行成功....");
                logger.info("【" + deviceName + "】【同意好友请求】全部执行成功....");
                logger.info("【" + deviceName + "】【同意好友请求】全部执行成功....");
                logger.info("【" + deviceName + "】【同意好友请求】全部执行成功....");
            }
        }
    }
}

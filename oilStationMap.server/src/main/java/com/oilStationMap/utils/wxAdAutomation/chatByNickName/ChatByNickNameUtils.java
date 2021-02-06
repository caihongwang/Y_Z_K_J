package com.oilStationMap.utils.wxAdAutomation.chatByNickName;

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
import com.oilStationMap.utils.ApplicationContextUtils;
import com.oilStationMap.utils.CommandUtil;
import com.oilStationMap.utils.HttpsUtil;
import com.oilStationMap.utils.MapUtil;
import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 根据微信昵称进行聊天工具
 * appium -p 4723 -bp 4724 --session-override --command-timeout 600
 */
public class ChatByNickNameUtils {

    public static final Logger logger = LoggerFactory.getLogger(ChatByNickNameUtils.class);

    public static WX_DicDao wxDicDao = (WX_DicDao) ApplicationContextUtils.getBeanByClass(WX_DicDao.class);

    public static MailService mailService = (MailService) ApplicationContextUtils.getBeanByClass(MailServiceImpl.class);

    public static WX_DicService wxDicService = (WX_DicService) ApplicationContextUtils.getBeanByClass(WX_DicServiceImpl.class);

    public static WX_MessageService wxMessageService = (WX_MessageService) ApplicationContextUtils.getBeanByClass(WX_MessageServiceImpl.class);

    public static GlobalVariableConfig globalVariableConfig = (com.oilStationMap.config.GlobalVariableConfig) ApplicationContextUtils.getBeanByClass(GlobalVariableConfig.class);

    /**
     * 根据微信昵称进行聊天for所有设备
     */
    public static void chatByNickName(Map<String, Object> paramMap) throws Exception{
        StopWatch sw = new StopWatch();
        sw.start();
        String nickNameListStr = paramMap.get("nickNameListStr") != null ? paramMap.get("nickNameListStr").toString() : "";
        List<String> nickNameList = JSONObject.parseObject(nickNameListStr, List.class);
        //appium端口号
        String appiumPort = null;
        try{
            appiumPort = globalVariableConfig.appiumPortList.get(0);     //从全局变量中获取appium端口号并移除，避免其他线程抢端口号
            globalVariableConfig.appiumPortList.remove(appiumPort);
        } catch (Exception e) {
            throw new Exception("当前没有空闲的appium端口号.");
        }
        for (String nickName : nickNameList) {
            List<HashMap<String, Object>> rebootDeviceNameList = Lists.newArrayList();          //执行失败的设备列表，待重新执行
            paramMap.put("dicType", "chatByNickName");
            paramMap.put("dicCode", nickName);        //指定 某一个微信昵称 聊天
            ResultDTO resultDTO = wxDicService.getLatelyDicByCondition(paramMap);
            List<Map<String, String>> resultList = resultDTO.getResultList();
            if (resultList != null && resultList.size() > 0) {
                //获取通过昵称聊天通知对方已发朋友圈的内容信息.
                Map<String, Object> chatByNickNameParam = MapUtil.getObjectMap(resultList.get(0));
                //获取设备列表和配套的坐标配置
                List<String> dicCodeList = Lists.newArrayList();
                dicCodeList.add("HuaWeiListAndChatByNickNameLocaltion");//获取 华为 Mate 8 设备列表和配套的坐标配置
                for (String dicCode : dicCodeList) {
                    paramMap.clear();
                    paramMap.put("dicType", "deviceNameListAndLocaltion");
                    paramMap.put("dicCode", dicCode);
                    List<Map<String, Object>> list = wxDicDao.getSimpleDicByCondition(paramMap);
                    if (list != null && list.size() > 0) {
                        String deviceNameAndLocaltionStr = list.get(0).get("dicRemark") != null ? list.get(0).get("dicRemark").toString() : "";
                        JSONObject deviceNameAndLocaltionJSONObject = JSONObject.parseObject(deviceNameAndLocaltionStr);
                        //获取设备坐标
                        String deviceLocaltionStr = deviceNameAndLocaltionJSONObject.getString("deviceLocaltion");
                        Map<String, Object> deviceLocaltionMap = JSONObject.parseObject(deviceLocaltionStr, Map.class);
                        chatByNickNameParam.putAll(deviceLocaltionMap);
                        //获取设备列表
                        String deviceNameListStr = deviceNameAndLocaltionJSONObject.getString("deviceNameList");
                        List<Map<String, Object>> deviceNameList = JSONObject.parseObject(deviceNameListStr, List.class);
                        //appium端口号
//                        String appiumPort = deviceNameAndLocaltionJSONObject.getString("appiumPort");
                        chatByNickNameParam.put("appiumPort", appiumPort);
                        if (deviceNameList != null && deviceNameList.size() > 0) {
                            for (Map<String, Object> deviceNameMap : deviceNameList) {
                                chatByNickNameParam.putAll(deviceNameMap);
                                try {
                                    sw.split();
                                    logger.info("设备描述【" + chatByNickNameParam.get("deviceNameDesc") + "】设备编码【" + chatByNickNameParam.get("deviceName") + "】操作【" + chatByNickNameParam.get("action") + "】昵称【" + nickName + "】的聊天即将开始发送，总共花费 " + sw.toSplitString() + " 秒....");
                                    new RealMachineDevices().chatByNickName(chatByNickNameParam, sw);
                                    Thread.sleep(5000);
                                } catch (Exception e) {     //当运行设备异常之后，就会对当前设备进行记录，准备重启，后续再对此设备进行重新执行
                                    e.printStackTrace();
                                    HashMap<String, Object> rebootDeviceNameMap = Maps.newHashMap();
                                    rebootDeviceNameMap.putAll(chatByNickNameParam);
                                    rebootDeviceNameList.add(rebootDeviceNameMap);      //当前设备执行失败，加入待重新执行的设备列表
                                }
                            }
                        }
                    } else {
                        logger.info(dicCode + " 设备列表和配套的坐标配置 不存在，请使用adb命令查询设备号并入库.");
                    }
                }
            } else {
                logger.info("根据微信昵称进行聊天 失败.");
            }
            //对执行失败的设备列表进行重新执行,最多循环执行5遍
            Integer index = 1;
            while (rebootDeviceNameList.size() > 0) {
                //等待所有设备重启
                try {
                    Thread.sleep(45000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(index > 5){
                    break;
                }
                logger.info("第【"+index+"】次批量重新执行失败的设备....");
                Iterator<HashMap<String, Object>> iterator = rebootDeviceNameList.iterator();
                while (iterator.hasNext()) {
                    Map<String, Object> deviceNameMap = iterator.next();
                    try {
                        sw.split();
                        logger.info("设备描述【" + deviceNameMap.get("deviceNameDesc") + "】设备编码【" + deviceNameMap.get("deviceName") + "】操作【" + deviceNameMap.get("action") + "】昵称【" + deviceNameMap.get("nickName") + "】的聊天即将开始发送，总共花费 " + sw.toSplitString() + " 秒....");
                        new RealMachineDevices().chatByNickName(deviceNameMap, sw);
                        Thread.sleep(5000);
                        iterator.remove();
                    } catch (Exception e) {     //当运行设备异常之后，就会对当前设备进行记录，准备重启，后续再对此设备进行重新执行
                        e.printStackTrace();
                    }
                }
                index++;
            }
            sw.split();
            if(rebootDeviceNameList.size() > 0){
                logger.info("【"+nickName+"】【根据微信昵称进行聊天】5次次批量执行均失败的设备如下，总共花费 " + sw.toSplitString() + " 秒....");
                logger.info("【"+nickName+"】【根据微信昵称进行聊天】5次次批量执行均失败的设备如下，总共花费 " + sw.toSplitString() + " 秒....");
                logger.info("【"+nickName+"】【根据微信昵称进行聊天】5次次批量执行均失败的设备如下，总共花费 " + sw.toSplitString() + " 秒....");
                logger.info("【"+nickName+"】【根据微信昵称进行聊天】5次次批量执行均失败的设备如下，总共花费 " + sw.toSplitString() + " 秒....");
                logger.info("【"+nickName+"】【根据微信昵称进行聊天】5次次批量执行均失败的设备如下，总共花费 " + sw.toSplitString() + " 秒....");
                String exceptionDevices = "异常设备列表";
                for(HashMap<String, Object> rebootDeviceNameMap : rebootDeviceNameList){
                    exceptionDevices = exceptionDevices + "【" + rebootDeviceNameMap.get("deviceNameDesc") + "】";
                    logger.info("【" + rebootDeviceNameMap.get("deviceNameDesc") + "】设备编码【" + rebootDeviceNameMap.get("deviceName") + "】操作【" + rebootDeviceNameMap.get("action") + "】昵称【" + rebootDeviceNameMap.get("nickName") + "】在最终在重新执行列表中失败......");
                }
                logger.info("【"+nickName+"】【根据微信昵称进行聊天】5次次批量执行均失败的设备如上，总共花费 " + sw.toSplitString() + " 秒....");
                logger.info("【"+nickName+"】【根据微信昵称进行聊天】5次次批量执行均失败的设备如上，总共花费 " + sw.toSplitString() + " 秒....");
                logger.info("【"+nickName+"】【根据微信昵称进行聊天】5次次批量执行均失败的设备如上，总共花费 " + sw.toSplitString() + " 秒....");
                logger.info("【"+nickName+"】【根据微信昵称进行聊天】5次次批量执行均失败的设备如上，总共花费 " + sw.toSplitString() + " 秒....");
                logger.info("【"+nickName+"】【根据微信昵称进行聊天】5次次批量执行均失败的设备如上，总共花费 " + sw.toSplitString() + " 秒....");
                if(rebootDeviceNameList != null && rebootDeviceNameList.size() > 0){
                    //建议使用http协议访问阿里云，通过阿里元来完成此操作.
                    HttpsUtil httpsUtil = new HttpsUtil();
                    Map<String, String> exceptionDevicesParamMap = Maps.newHashMap();
                    exceptionDevicesParamMap.put("nickName", nickName);
                    exceptionDevicesParamMap.put("operatorName", "根据微信昵称进行聊天");
                    exceptionDevicesParamMap.put("exceptionDevices", exceptionDevices);
                    String exceptionDevicesNotifyUrl = "https://www.yzkj.store/oilStationMap/wxMessage/exceptionDevicesMessageSend";
                    String resultJson = httpsUtil.post(exceptionDevicesNotifyUrl, exceptionDevicesParamMap);
                    logger.info("微信消息异常发送反馈：" + resultJson);

                    //邮件通知
                    StringBuffer mailMessageBuf = new StringBuffer();
                    mailMessageBuf.append("蔡红旺，您好：\n");
                    mailMessageBuf.append("        ").append("\t操作名称：根据微信昵称进行聊天").append("\n");
                    mailMessageBuf.append("        ").append("\t微信群：").append(nickName).append("\n");
                    mailMessageBuf.append("        ").append("\t操作设备：").append(exceptionDevices).append("\n");
                    mailMessageBuf.append("        ").append("\t异常时间：").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("\n");
                    mailMessageBuf.append("        ").append("\t异常地点：").append("北京市昌平区").append("\n");
                    mailMessageBuf.append("        ").append("\t温馨提示：").append("请检查以下手机的接口，并手动辅助自动化操作.").append("\n");
                    mailMessageBuf.append("        ").append("\t异常原因描述：").append("Usb接口不稳定断电或者微信版本已被更新导致坐标不匹配").append("\n");
                    mailService.sendSimpleMail("caihongwang@dingtalk.com", "【服务异常通知】根据微信昵称进行聊天", mailMessageBuf.toString());
                }
            } else {
                logger.info("【"+nickName+"】【根据微信昵称进行聊天】全部执行成功，总共花费 " + sw.toSplitString() + " 秒....");
                logger.info("【"+nickName+"】【根据微信昵称进行聊天】全部执行成功，总共花费 " + sw.toSplitString() + " 秒....");
                logger.info("【"+nickName+"】【根据微信昵称进行聊天】全部执行成功，总共花费 " + sw.toSplitString() + " 秒....");
                logger.info("【"+nickName+"】【根据微信昵称进行聊天】全部执行成功，总共花费 " + sw.toSplitString() + " 秒....");
                logger.info("【"+nickName+"】【根据微信昵称进行聊天】全部执行成功，总共花费 " + sw.toSplitString() + " 秒....");
            }
        }
        if(appiumPort != null){
            globalVariableConfig.appiumPortList.add(appiumPort);        //回收已使用完成的appium端口号
        }
    }
}

package com.oilStationMap.utils.wxAdAutomation.chatByNickName;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.oilStationMap.dao.WX_DicDao;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.service.WX_DicService;
import com.oilStationMap.service.WX_MessageService;
import com.oilStationMap.service.impl.WX_DicServiceImpl;
import com.oilStationMap.service.impl.WX_MessageServiceImpl;
import com.oilStationMap.utils.ApplicationContextUtils;
import com.oilStationMap.utils.MapUtil;
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

    public static WX_DicService wxDicService = (WX_DicService) ApplicationContextUtils.getBeanByClass(WX_DicServiceImpl.class);

    public static WX_MessageService wxMessageService = (WX_MessageService) ApplicationContextUtils.getBeanByClass(WX_MessageServiceImpl.class);

    /**
     * 根据微信昵称进行聊天for所有设备
     */
    public static void chatByNickName(Map<String, Object> paramMap) {
        String nickNameListStr = paramMap.get("nickNameListStr") != null ? paramMap.get("nickNameListStr").toString() : "";
        List<String> nickNameList = JSONObject.parseObject(nickNameListStr, List.class);
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
                dicCodeList.add("HuaWeiMate8ListAndChatByNickNameLocaltion");//获取 华为 Mate 8 设备列表和配套的坐标配置
                dicCodeList.add("HuaWeiMate8HListAndChatByNickNameLocaltion");//获取 华为 Mate 8 海外版 设备列表和配套的坐标配置
                dicCodeList.add("HuaWeiP20ProListAndChatByNickNameLocaltion");//获取 华为 P20 Pro 设备列表和配套的坐标配置
                dicCodeList.add("XiaoMiMax3ListAndChatByNickNameLocaltion");//获取 小米 Max 3 设备列表和配套的坐标配置
                dicCodeList.add("HuaWeiMate7ListAndChatByNickNameLocaltion");//获取 华为 Mate 7 设备列表和配套的坐标配置
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
                        if (deviceNameList != null && deviceNameList.size() > 0) {
                            for (Map<String, Object> deviceNameMap : deviceNameList) {
                                chatByNickNameParam.putAll(deviceNameMap);
                                try {
                                    logger.info("设备描述【" + chatByNickNameParam.get("deviceNameDesc") + "】设备编码【" + chatByNickNameParam.get("deviceName") + "】操作【" + chatByNickNameParam.get("action") + "】昵称【" + nickName + "】的聊天即将开始发送.....");
                                    new RealMachineDevices().chatByNickName(chatByNickNameParam);
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
                    Thread.sleep(90000);
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
                        logger.info("设备描述【" + deviceNameMap.get("deviceNameDesc") + "】设备编码【" + deviceNameMap.get("deviceName") + "】操作【" + deviceNameMap.get("action") + "】昵称【" + deviceNameMap.get("nickName") + "】的聊天即将开始发送.....");
                        new RealMachineDevices().chatByNickName(deviceNameMap);
                        Thread.sleep(5000);
                        iterator.remove();
                    } catch (Exception e) {     //当运行设备异常之后，就会对当前设备进行记录，准备重启，后续再对此设备进行重新执行
                        e.printStackTrace();
                    }
                }
                index++;
            }
            logger.info("【根据微信昵称进行聊天】已经执行完毕......");
            logger.info("【根据微信昵称进行聊天】已经执行完毕......");
            logger.info("【根据微信昵称进行聊天】已经执行完毕......");
            String exceptionDevices = "异常设备列表";
            for(HashMap<String, Object> rebootDeviceNameMap : rebootDeviceNameList){
                exceptionDevices = exceptionDevices + "【" + rebootDeviceNameMap.get("deviceNameDesc") + "】";
                logger.info("【" + rebootDeviceNameMap.get("deviceNameDesc") + "】设备编码【" + rebootDeviceNameMap.get("deviceName") + "】操作【" + rebootDeviceNameMap.get("action") + "】昵称【" + rebootDeviceNameMap.get("nickName") + "】在最终在重新执行列表中失败......");
            }
            try {
                Map<String, Object> exceptionDevicesParamMap = Maps.newHashMap();
                exceptionDevicesParamMap.put("operatorName", "根据微信昵称进行聊天");
                exceptionDevicesParamMap.put("exceptionDevices", exceptionDevices);
                wxMessageService.exceptionDevicesMessageSend(exceptionDevicesParamMap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

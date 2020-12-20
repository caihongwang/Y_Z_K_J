package com.oilStationMap.utils.wxAdAutomation.addGroupMembersAsFriends;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.oilStationMap.dao.WX_DicDao;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.service.WX_DicService;
import com.oilStationMap.service.WX_MessageService;
import com.oilStationMap.service.impl.WX_DicServiceImpl;
import com.oilStationMap.service.impl.WX_MessageServiceImpl;
import com.oilStationMap.utils.*;
import org.apache.commons.lang.time.StopWatch;
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

    public static WX_DicService wxDicService = (WX_DicService) ApplicationContextUtils.getBeanByClass(WX_DicServiceImpl.class);

    public static WX_MessageService wxMessageService = (WX_MessageService) ApplicationContextUtils.getBeanByClass(WX_MessageServiceImpl.class);

    /**
     * 根据微信群昵称添加群成员为好友工具for所有设备
     */
    public static void addGroupMembersAsFriends(Map<String, Object> paramMap) throws Exception {
        StopWatch sw = new StopWatch();
        sw.start();
//        try{
//            CommandUtil.run("sh /opt/resourceOfOilStationMap/webapp/rebootAllAndroidDevices/rebootAllAndroidDevices.sh");
//            Thread.sleep(30000);    //等待重启30秒
//        } catch (Exception e) {
//            logger.error(">>>>>>>>>>>>>>>>>>>重启所有手机异常<<<<<<<<<<<<<<<<<<<<<<");
//            logger.error("重启所有手机异常，e :", e);
//            logger.error(">>>>>>>>>>>>>>>>>>>重启所有手机异常<<<<<<<<<<<<<<<<<<<<<<");
//        }
        String nickNameListStr = paramMap.get("nickNameListStr") != null ? paramMap.get("nickNameListStr").toString() : "";
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
            Date currentDate = new SimpleDateFormat("yyyy-MM-dd HH").parse(currentDateStr);
            List<String> nickNameList = JSONObject.parseObject(nickNameListStr, List.class);
            for (String nickName : nickNameList) {
                List<HashMap<String, Object>> allDeviceNameList = Lists.newArrayList();                //所有的设备列表
                List<HashMap<String, Object>> rebootDeviceNameList = Lists.newArrayList();          //执行失败的设备列表，待重新执行
                paramMap.put("dicType", "addGroupMembersAsFriends");
                paramMap.put("dicCode", EmojiUtil.emojiConvert(nickName));        //根据微信群昵称添加群成员为好友
                ResultDTO resultDTO = wxDicService.getLatelyDicByCondition(paramMap);
                List<Map<String, String>> resultList = resultDTO.getResultList();
                if (resultList != null && resultList.size() > 0) {
                    //根据微信群昵称添加群成员为好友.
                    Map<String, Object> addGroupMembersAsFriendsParam = MapUtil.getObjectMap(resultList.get(0));
                    addGroupMembersAsFriendsParam.put("addGroupMembersFlag", "false");      //当前设备已经添加过好友的标志位
                    addGroupMembersAsFriendsParam.put("nickName", addGroupMembersAsFriendsParam.get("dicCode"));        //dicCode就是nickName
                    String theId = addGroupMembersAsFriendsParam.get("id").toString();
                    //获取设备列表和配套的坐标配置wxDic
                    List<String> dicCodeList = Lists.newArrayList();
                    dicCodeList.add("HuaWeiListAndAddGroupMembersAsFriendsLocaltion"); //获取 华为 设备列表和配套的坐标配置
                    //3.添加群成员为好友
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
                            addGroupMembersAsFriendsParam.putAll(deviceLocaltionMap);
                            //获取设备列表
                            String deviceNameListStr = deviceNameAndLocaltionJSONObject.getString("deviceNameList");
                            List<HashMap<String, Object>> deviceNameList = JSONObject.parseObject(deviceNameListStr, List.class);
                            if (deviceNameList != null && deviceNameList.size() > 0) {
                                for (Map<String, Object> deviceNameMap : deviceNameList) {
                                    addGroupMembersAsFriendsParam.putAll(deviceNameMap);
                                    try {
                                        //当前设备名称
                                        String deviceNameDesc =
                                                addGroupMembersAsFriendsParam.get("deviceNameDesc") != null ?
                                                        addGroupMembersAsFriendsParam.get("deviceNameDesc").toString() :
                                                        null;
                                        //目标设备名称-即群对应设备名称
                                        String targetDeviceNameDesc =
                                                addGroupMembersAsFriendsParam.get("targetDeviceNameDesc") != null ?
                                                        addGroupMembersAsFriendsParam.get("targetDeviceNameDesc").toString() :
                                                        null;
                                        if (deviceNameDesc == null || targetDeviceNameDesc == null || !targetDeviceNameDesc.equals(deviceNameDesc)) {       //群的指定目标的设备与当前的设备不符合直接continue
                                            continue;
                                        }
                                        //判断当前设备的执行小时时间是否与当前时间匹配
                                        String startHour =
                                                addGroupMembersAsFriendsParam.get("startHour") != null ?
                                                        addGroupMembersAsFriendsParam.get("startHour").toString() :
                                                        "";
                                        String currentHour = new SimpleDateFormat("HH").format(currentDate);
                                        if (startHour.equals(currentHour)) {
                                            //开始添加群成员为好友的V群
                                            sw.split();
                                            logger.info("设备描述【" + addGroupMembersAsFriendsParam.get("deviceNameDesc") + "】设备编码【" + addGroupMembersAsFriendsParam.get("deviceName") + "】操作【" + addGroupMembersAsFriendsParam.get("action") + "】昵称【" + nickName + "】的添加群成员为好友的V群即将开始发送，总共花费 " + sw.toSplitString() + " 秒....");
                                            addGroupMembersAsFriendsParam.put("index", 0);
                                            new RealMachineDevices().addGroupMembersAsFriends(addGroupMembersAsFriendsParam, sw);
                                            Thread.sleep(5000);
                                        } else {
                                            //下一个设备
                                            sw.split();
                                            logger.info("设备描述【" + addGroupMembersAsFriendsParam.get("deviceNameDesc") + "】设备编码【" + addGroupMembersAsFriendsParam.get("deviceName") + "】，当前设备的执行时间第【" + startHour + "】小时，当前时间是第【" + currentHour + "】小时，总共花费 " + sw.toSplitString() + " 秒....");
                                            continue;
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        HashMap<String, Object> rebootDeviceNameMap = Maps.newHashMap();
                                        rebootDeviceNameMap.putAll(addGroupMembersAsFriendsParam);
                                        rebootDeviceNameList.add(rebootDeviceNameMap);      //当前设备执行失败，加入待重新执行的设备列表
                                    }
                                }
                            }
                        } else {
                            logger.info(dicCode + " 设备列表和配套的坐标配置 不存在，请使用adb命令查询设备号并入库.");
                        }
                    }

                    //4.对执行失败的设备列表进行重新执行【重发朋友圈】,最多循环执行5遍
                    Integer index = 1;
                    while (rebootDeviceNameList.size() > 0) {
                        //等待所有设备重启
                        try {
                            Thread.sleep(45000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (index > 15) {
                            break;
                        }
                        logger.info("第【" + index + "】次批量重新执行【" + nickName + "】失败的设备，剩余设备数量： " + rebootDeviceNameList.size() + "....");
                        logger.info("第【" + index + "】次批量重新执行【" + nickName + "】失败的设备，剩余设备数量： " + rebootDeviceNameList.size() + "....");
                        logger.info("第【" + index + "】次批量重新执行【" + nickName + "】失败的设备，剩余设备数量： " + rebootDeviceNameList.size() + "....");
                        logger.info("第【" + index + "】次批量重新执行【" + nickName + "】失败的设备，剩余设备数量： " + rebootDeviceNameList.size() + "....");
                        logger.info("第【" + index + "】次批量重新执行【" + nickName + "】失败的设备，剩余设备数量： " + rebootDeviceNameList.size() + "....");
                        Iterator<HashMap<String, Object>> iterator = rebootDeviceNameList.iterator();
                        while (iterator.hasNext()) {
                            Map<String, Object> deviceNameMap = iterator.next();
                            try {
                                sw.split();
                                logger.info("设备描述【" + deviceNameMap.get("deviceNameDesc") + "】设备编码【" + deviceNameMap.get("deviceName") + "】操作【" + deviceNameMap.get("action") + "】昵称【" + deviceNameMap.get("nickName") + "】的添加群成员为好友的V群即将开始发送，总共花费 " + sw.toSplitString() + " 秒....");
                                deviceNameMap.put("index", index);
                                new RealMachineDevices().addGroupMembersAsFriends(deviceNameMap, sw);
                                addGroupMembersAsFriendsParam.putAll(deviceNameMap);
                                Thread.sleep(5000);
                                iterator.remove();
                            } catch (Exception e) {     //当运行设备异常之后，就会对当前设备进行记录，准备重启，后续再对此设备进行重新执行
                                e.printStackTrace();
                                try {
                                    if (index % 4 == 0) {
                                        //【添加群成员为好友的V群】过程中，出现不会对设备进行重启，所以在重新执行的单个过程出现异常则重启
                                        CommandUtil.run("/opt/android_sdk/platform-tools/adb -s " + deviceNameMap.get("deviceName").toString() + " reboot");
                                        logger.info("重启成功，设备描述【" + deviceNameMap.get("deviceNameDesc").toString() + "】设备编码【" + deviceNameMap.get("deviceName").toString() + "】");
                                    }
                                } catch (Exception e1) {
                                    logger.info("重启失败，设备描述【" + deviceNameMap.get("deviceNameDesc").toString() + "】设备编码【" + deviceNameMap.get("deviceName").toString() + "】");
                                }
                            }
                        }
                        index++;
                    }

                    //6.发送微信通知消息进行手动录入.
                    if (rebootDeviceNameList.size() > 0) {
                        sw.split();
                        logger.info("【添加群成员为好友的V群】5次次批量执行【" + nickName + "】均失败的设备如下，总共花费 " + sw.toSplitString() + " 秒....");
                        logger.info("【添加群成员为好友的V群】5次次批量执行【" + nickName + "】均失败的设备如下，总共花费 " + sw.toSplitString() + " 秒....");
                        logger.info("【添加群成员为好友的V群】5次次批量执行【" + nickName + "】均失败的设备如下，总共花费 " + sw.toSplitString() + " 秒....");
                        logger.info("【添加群成员为好友的V群】5次次批量执行【" + nickName + "】均失败的设备如下，总共花费 " + sw.toSplitString() + " 秒....");
                        logger.info("【添加群成员为好友的V群】5次次批量执行【" + nickName + "】均失败的设备如下，总共花费 " + sw.toSplitString() + " 秒....");
                        String exceptionDevices = "异常设备列表";
                        for (HashMap<String, Object> rebootDeviceNameMap : rebootDeviceNameList) {
                            exceptionDevices = exceptionDevices + "【" + rebootDeviceNameMap.get("deviceNameDesc") + "】";
                            logger.info("【" + rebootDeviceNameMap.get("deviceNameDesc") + "】设备编码【" + rebootDeviceNameMap.get("deviceName") + "】操作【" + rebootDeviceNameMap.get("action") + "】昵称【" + rebootDeviceNameMap.get("nickName") + "】在最终在重新执行列表中失败......");
                        }
                        logger.info("【添加群成员为好友的V群】5次次批量执行【" + nickName + "】均失败的设备如下，总共花费 " + sw.toSplitString() + " 秒....");
                        logger.info("【添加群成员为好友的V群】5次次批量执行【" + nickName + "】均失败的设备如下，总共花费 " + sw.toSplitString() + " 秒....");
                        logger.info("【添加群成员为好友的V群】5次次批量执行【" + nickName + "】均失败的设备如下，总共花费 " + sw.toSplitString() + " 秒....");
                        logger.info("【添加群成员为好友的V群】5次次批量执行【" + nickName + "】均失败的设备如下，总共花费 " + sw.toSplitString() + " 秒....");
                        logger.info("【添加群成员为好友的V群】5次次批量执行【" + nickName + "】均失败的设备如下，总共花费 " + sw.toSplitString() + " 秒....");
                        if (rebootDeviceNameList != null && rebootDeviceNameList.size() > 0) {
                            //建议使用http协议访问阿里云，通过阿里元来完成此操作.
                            HttpsUtil httpsUtil = new HttpsUtil();
                            Map<String, String> exceptionDevicesParamMap = Maps.newHashMap();
                            exceptionDevicesParamMap.put("nickName", nickName);
                            exceptionDevicesParamMap.put("operatorName", "添加群成员为好友的V群");
                            exceptionDevicesParamMap.put("exceptionDevices", exceptionDevices);
                            String exceptionDevicesNotifyUrl = "https://www.yzkj.store/oilStationMap/wxMessage/exceptionDevicesMessageSend";
                            String resultJson = httpsUtil.post(exceptionDevicesNotifyUrl, exceptionDevicesParamMap);
                            logger.info("微信消息异常发送反馈：" + resultJson);
//                        try {
//                            Map<String, Object> exceptionDevicesParamMap = Maps.newHashMap();
//                            exceptionDevicesParamMap.put("operatorName", "发布朋友圈");
//                            exceptionDevicesParamMap.put("exceptionDevices", exceptionDevices);
//                            wxMessageService.exceptionDevicesMessageSend(exceptionDevicesParamMap);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
                        }
                    } else {
                        if ("true".equals(addGroupMembersAsFriendsParam.get("addGroupMembersFlag"))) {            //当前设备已经添加过好友的标志位
                            //更新这个群的信息
                            try {
                                String groupMembersMapStr = addGroupMembersAsFriendsParam.get("groupMembersMapStr").toString();
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
                                    }
                                }
                            } catch (Exception e) {
                                logger.error("【更新这个群的信息】时异常，e : ", e);
                            }
                        }
                        sw.split();
                        logger.info("【添加群成员为好友的V群】全部执行【" + nickName + "】成功，总共花费 " + sw.toSplitString() + " 秒....");
                        logger.info("【添加群成员为好友的V群】全部执行【" + nickName + "】成功，总共花费 " + sw.toSplitString() + " 秒....");
                        logger.info("【添加群成员为好友的V群】全部执行【" + nickName + "】成功，总共花费 " + sw.toSplitString() + " 秒....");
                        logger.info("【添加群成员为好友的V群】全部执行【" + nickName + "】成功，总共花费 " + sw.toSplitString() + " 秒....");
                        logger.info("【添加群成员为好友的V群】全部执行【" + nickName + "】成功，总共花费 " + sw.toSplitString() + " 秒....");
                    }
                } else {
                    logger.info("添加群成员为好友的V群 失败.");
                }
            }

            sw.split();
            logger.info("【添加群成员为好友的V群】已完成，总共花费 " + sw.toSplitString() + " 秒，nickNameListStr = " + nickNameListStr + "....");
            logger.info("【添加群成员为好友的V群】已完成，总共花费 " + sw.toSplitString() + " 秒，nickNameListStr = " + nickNameListStr + "....");
            logger.info("【添加群成员为好友的V群】已完成，总共花费 " + sw.toSplitString() + " 秒，nickNameListStr = " + nickNameListStr + "....");
            logger.info("【添加群成员为好友的V群】已完成，总共花费 " + sw.toSplitString() + " 秒，nickNameListStr = " + nickNameListStr + "....");
            logger.info("【添加群成员为好友的V群】已完成，总共花费 " + sw.toSplitString() + " 秒，nickNameListStr = " + nickNameListStr + "....");
        }
    }
}

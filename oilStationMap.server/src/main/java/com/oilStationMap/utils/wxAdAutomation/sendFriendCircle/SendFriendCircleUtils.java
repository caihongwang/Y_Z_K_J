package com.oilStationMap.utils.wxAdAutomation.sendFriendCircle;

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

    public static WX_DicService wxDicService = (WX_DicService) ApplicationContextUtils.getBeanByClass(WX_DicServiceImpl.class);

    public static WX_MessageService wxMessageService = (WX_MessageService) ApplicationContextUtils.getBeanByClass(WX_MessageServiceImpl.class);

    /**
     * 发布朋友圈for所有设备
     */
    public static void sendFriendCircle(Map<String, Object> paramMap) throws Exception {
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
                paramMap.put("dicType", "sendFriendCircle");
                paramMap.put("dicCode", nickName);        //指定转发微信昵称朋友圈内容
                ResultDTO resultDTO = wxDicService.getLatelyDicByCondition(paramMap);
                List<Map<String, String>> resultList = resultDTO.getResultList();
                if (resultList != null && resultList.size() > 0) {
                    //获取发送朋友圈的内容信息.
                    Map<String, Object> sendFriendCircleParam = MapUtil.getObjectMap(resultList.get(0));
                    sendFriendCircleParam.put("nickName", sendFriendCircleParam.get("dicCode"));        //dicCode就是nickName
                    sendFriendCircleParam.put("phoneLocalPath", "/storage/emulated/0/tencent/MicroMsg/WeiXin/");
                    String theId = sendFriendCircleParam.get("id").toString();
                    //获取设备列表和配套的坐标配置wxDic
                    List<String> dicCodeList = Lists.newArrayList();
                    dicCodeList.add("HuaWeiListAndSendFriendCircleLocaltion"); //获取 华为 Mate 8 设备列表和配套的坐标配置
                    //1.将 图片文件 push 到安卓设备里面
                    String action =
                            sendFriendCircleParam.get("action") != null ?
                                    sendFriendCircleParam.get("action").toString() :
                                    "textMessageFriendCircle";
                    if (action.equals("imgMessageFriendCircle")) {
                        boolean imgExistFlag = false;
                        for (String dicCode : dicCodeList) {         //通过action来判断
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
                                sendFriendCircleParam.putAll(deviceLocaltionMap);
                                //获取设备列表
                                String deviceNameListStr = deviceNameAndLocaltionJSONObject.getString("deviceNameList");
                                List<HashMap<String, Object>> deviceNameList = JSONObject.parseObject(deviceNameListStr, List.class);
                                allDeviceNameList.addAll(deviceNameList);
                                //appium端口号
                                String appiumPort = deviceNameAndLocaltionJSONObject.getString("appiumPort");
                                sendFriendCircleParam.put("appiumPort", appiumPort);
                                //当前时间
                                sendFriendCircleParam.put("currentDate", currentDate);

                                //将 图片文件 push 到安卓设备里面
                                boolean tempFlag = pushImgFileToDevice(deviceNameList, sendFriendCircleParam);
                                if (!imgExistFlag && tempFlag) {
                                    imgExistFlag = tempFlag;
                                }
                            }
                        }

                        if (imgExistFlag) {          //如果 图片 不存在则直接下一个, 同时将 图片文件 remove 到安卓设备里面
                            //2.沉睡等待2分钟，确保USB传输文件到达手机相册
                            try {
                                sw.split();
                                logger.info("将图片保存到【手机本地的微信图片路径】成功，沉睡等待2分钟，确保USB传输文件到达手机相册，总共花费 " + sw.toSplitString() + " 秒....");
                                Thread.sleep(1000 * 60 * 2);       //沉睡等待10分钟
                            } catch (Exception e) {
                                logger.info("将图片保存到【手机本地的微信图片路径】成功，沉睡等待10分钟，失败...");
                            }
                        } else {
                            logger.error("发布朋友圈时，昵称【" + nickName + "】没有图片，异常退出.");
                            continue;
                        }
                    }

                    //3.发送朋友圈
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
                            sendFriendCircleParam.putAll(deviceLocaltionMap);
                            //获取设备列表
                            String deviceNameListStr = deviceNameAndLocaltionJSONObject.getString("deviceNameList");
                            List<HashMap<String, Object>> deviceNameList = JSONObject.parseObject(deviceNameListStr, List.class);
                            if (deviceNameList != null && deviceNameList.size() > 0) {
                                for (Map<String, Object> deviceNameMap : deviceNameList) {
                                    sendFriendCircleParam.putAll(deviceNameMap);//判断推广时间是否还在推广期内
                                    String startTimeStr = sendFriendCircleParam.get("startTime") != null ? sendFriendCircleParam.get("startTime").toString() : "";
                                    String endTimeStr = sendFriendCircleParam.get("endTime") != null ? sendFriendCircleParam.get("endTime").toString() : "";
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
                                                    //开始发送朋友圈
                                                    sw.split();
                                                    logger.info("设备描述【" + sendFriendCircleParam.get("deviceNameDesc") + "】设备编码【" + sendFriendCircleParam.get("deviceName") + "】操作【" + sendFriendCircleParam.get("action") + "】昵称【" + nickName + "】的发送朋友圈即将开始发送，总共花费 " + sw.toSplitString() + " 秒....");
                                                    sendFriendCircleParam.put("index", 0);
                                                    new RealMachineDevices().sendFriendCircle(sendFriendCircleParam, sw);
                                                    Thread.sleep(5000);
                                                } else {
                                                    //下一个设备
                                                    sw.split();
                                                    logger.info("设备描述【" + sendFriendCircleParam.get("deviceNameDesc") + "】设备编码【" + sendFriendCircleParam.get("deviceName") + "】，当前设备的执行时间第【" + startHour + "】小时，当前时间是第【" + currentHour + "】小时，总共花费 " + sw.toSplitString() + " 秒....");
                                                    continue;
                                                }
//                                            //开始发送朋友圈
//                                            sw.split();
//                                            logger.info( "设备描述【"+sendFriendCircleParam.get("deviceNameDesc")+"】设备编码【"+sendFriendCircleParam.get("deviceName")+"】操作【"+sendFriendCircleParam.get("action")+"】昵称【"+nickName+"】的发送朋友圈即将开始发送，总共花费 " + sw.toSplitString() + " 秒....");
//                                            sendFriendCircleParam.put("index", 0);
//                                            new RealMachineDevices().sendFriendCircle(sendFriendCircleParam, sw);
//                                            Thread.sleep(5000);
                                            } else if (DateUtil.isBeforeDate(currentDate, startTime)) {
                                                logger.info("尚未开始，暂不处理....");
                                            } else {
                                                Map<String, Object> tempMap = Maps.newHashMap();
                                                tempMap.put("id", theId);
                                                tempMap.put("dicStatus", 1);
                                                wxDicService.updateDic(tempMap);    //更新这条朋友圈数据为已删除
                                                logger.info("昵称【" + nickName + "】的转发朋友圈业务已到期....");
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            HashMap<String, Object> rebootDeviceNameMap = Maps.newHashMap();
                                            rebootDeviceNameMap.putAll(sendFriendCircleParam);
                                            rebootDeviceNameList.add(rebootDeviceNameMap);      //当前设备执行失败，加入待重新执行的设备列表
                                        }
                                    } else {
                                        logger.info("昵称【" + nickName + "】的转发朋友圈业务推广日期不能为空....");
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
                        logger.info("第【" + index + "】次批量重新执行【" + nickName + "】失败的设备，剩余： " + rebootDeviceNameList.size() + "....");
                        logger.info("第【" + index + "】次批量重新执行【" + nickName + "】失败的设备，剩余： " + rebootDeviceNameList.size() + "....");
                        logger.info("第【" + index + "】次批量重新执行【" + nickName + "】失败的设备，剩余： " + rebootDeviceNameList.size() + "....");
                        logger.info("第【" + index + "】次批量重新执行【" + nickName + "】失败的设备，剩余： " + rebootDeviceNameList.size() + "....");
                        logger.info("第【" + index + "】次批量重新执行【" + nickName + "】失败的设备，剩余： " + rebootDeviceNameList.size() + "....");
                        Iterator<HashMap<String, Object>> iterator = rebootDeviceNameList.iterator();
                        while (iterator.hasNext()) {

                            if (action.equals("imgMessageFriendCircle")) {
                                //重操作的时候，再次对图片进行操作
                                pushImgFileToDevice(rebootDeviceNameList, sendFriendCircleParam);
                            }

                            Map<String, Object> deviceNameMap = iterator.next();
                            try {
                                sw.split();
                                logger.info("设备描述【" + deviceNameMap.get("deviceNameDesc") + "】设备编码【" + deviceNameMap.get("deviceName") + "】操作【" + deviceNameMap.get("action") + "】昵称【" + deviceNameMap.get("nickName") + "】的发送朋友圈即将开始发送，总共花费 " + sw.toSplitString() + " 秒....");
                                deviceNameMap.put("index", index);
                                new RealMachineDevices().sendFriendCircle(deviceNameMap, sw);
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

                    //5.将 图片文件 push 到安卓设备里面
                    if (action.equals("imgMessageFriendCircle")) {
                        //将 图片文件  从安卓设备里面 删除
                        removeImgFileToDevice(allDeviceNameList, sendFriendCircleParam);
                    }

                    //6.发送微信通知消息进行手动录入.
                    if (rebootDeviceNameList.size() > 0) {
                        sw.split();
                        logger.info("【发送朋友圈】5次次批量执行【" + nickName + "】均失败的设备如下，总共花费 " + sw.toSplitString() + " 秒....");
                        logger.info("【发送朋友圈】5次次批量执行【" + nickName + "】均失败的设备如下，总共花费 " + sw.toSplitString() + " 秒....");
                        logger.info("【发送朋友圈】5次次批量执行【" + nickName + "】均失败的设备如下，总共花费 " + sw.toSplitString() + " 秒....");
                        logger.info("【发送朋友圈】5次次批量执行【" + nickName + "】均失败的设备如下，总共花费 " + sw.toSplitString() + " 秒....");
                        logger.info("【发送朋友圈】5次次批量执行【" + nickName + "】均失败的设备如下，总共花费 " + sw.toSplitString() + " 秒....");
                        String exceptionDevices = "异常设备列表";
                        for (HashMap<String, Object> rebootDeviceNameMap : rebootDeviceNameList) {
                            exceptionDevices = exceptionDevices + "【" + rebootDeviceNameMap.get("deviceNameDesc") + "】";
                            logger.info("【" + rebootDeviceNameMap.get("deviceNameDesc") + "】设备编码【" + rebootDeviceNameMap.get("deviceName") + "】操作【" + rebootDeviceNameMap.get("action") + "】昵称【" + rebootDeviceNameMap.get("nickName") + "】在最终在重新执行列表中失败......");
                        }
                        logger.info("【发送朋友圈】5次次批量执行【" + nickName + "】均失败的设备如下，总共花费 " + sw.toSplitString() + " 秒....");
                        logger.info("【发送朋友圈】5次次批量执行【" + nickName + "】均失败的设备如下，总共花费 " + sw.toSplitString() + " 秒....");
                        logger.info("【发送朋友圈】5次次批量执行【" + nickName + "】均失败的设备如下，总共花费 " + sw.toSplitString() + " 秒....");
                        logger.info("【发送朋友圈】5次次批量执行【" + nickName + "】均失败的设备如下，总共花费 " + sw.toSplitString() + " 秒....");
                        logger.info("【发送朋友圈】5次次批量执行【" + nickName + "】均失败的设备如下，总共花费 " + sw.toSplitString() + " 秒....");
                        if (rebootDeviceNameList != null && rebootDeviceNameList.size() > 0) {
                            //建议使用http协议访问阿里云，通过阿里元来完成此操作.
                            HttpsUtil httpsUtil = new HttpsUtil();
                            Map<String, String> exceptionDevicesParamMap = Maps.newHashMap();
                            exceptionDevicesParamMap.put("nickName", nickName);
                            exceptionDevicesParamMap.put("operatorName", "发布朋友圈");
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
                        logger.info("【发送朋友圈】全部执行【" + nickName + "】成功，总共花费 " + sw.toSplitString() + " 秒....");
                        logger.info("【发送朋友圈】全部执行【" + nickName + "】成功，总共花费 " + sw.toSplitString() + " 秒....");
                        logger.info("【发送朋友圈】全部执行【" + nickName + "】成功，总共花费 " + sw.toSplitString() + " 秒....");
                        logger.info("【发送朋友圈】全部执行【" + nickName + "】成功，总共花费 " + sw.toSplitString() + " 秒....");
                        logger.info("【发送朋友圈】全部执行【" + nickName + "】成功，总共花费 " + sw.toSplitString() + " 秒....");
                    }
                } else {
                    logger.info("发布朋友圈 失败.");
                }
            }
        }

        sw.split();
        logger.info("【发送朋友圈】已完成，总共花费 " + sw.toSplitString() + " 秒，nickNameListStr = " + nickNameListStr + "....");
        logger.info("【发送朋友圈】已完成，总共花费 " + sw.toSplitString() + " 秒，nickNameListStr = " + nickNameListStr + "....");
        logger.info("【发送朋友圈】已完成，总共花费 " + sw.toSplitString() + " 秒，nickNameListStr = " + nickNameListStr + "....");
        logger.info("【发送朋友圈】已完成，总共花费 " + sw.toSplitString() + " 秒，nickNameListStr = " + nickNameListStr + "....");
        logger.info("【发送朋友圈】已完成，总共花费 " + sw.toSplitString() + " 秒，nickNameListStr = " + nickNameListStr + "....");
    }


    /**
     * 将 图片文件 push 到安卓设备里面
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
                String startTimeStr = sendFriendCircleParam.get("startTime") != null ? sendFriendCircleParam.get("startTime").toString() : "";
                String endTimeStr = sendFriendCircleParam.get("endTime") != null ? sendFriendCircleParam.get("endTime").toString() : "";
                Date currentDate = sendFriendCircleParam.get("currentDate") != null ? (Date) sendFriendCircleParam.get("currentDate") : new Date();
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
                                //设备编码
                                String deviceName =
                                        deviceNameMap.get("deviceName") != null ?
                                                deviceNameMap.get("deviceName").toString() :
                                                "";
                                //图片文件路径，总路径+微信昵称
                                String imgDirPath =
                                        sendFriendCircleParam.get("imgDirPath") != null ?
                                                sendFriendCircleParam.get("imgDirPath").toString() :
                                                "";
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
                                            logger.error("获取 今日油价 图片失败.");
                                        }
                                    } else {
                                        imgFiles = imgDir.listFiles();
                                    }
                                }

                                //确保 文件件存在
                                CommandUtil.run("/opt/android_sdk/platform-tools/adb -s " + deviceName + " mkdir " + phoneLocalPath);

                                if (imgFiles != null && imgFiles.length > 0 && !"".equals(deviceName)) {
                                    for (int i = 0; i < imgFiles.length; i++) {
                                        try {
                                            //1.使用adb传输文件到手机，并发起广播，广播不靠谱，添加图片到文件系统里面去，但是在相册里面不确定能看得见.
                                            File imgFile = imgFiles[i];
                                            for(File tempFile : imgFiles){          //确保文件顺序导出.
                                                String[] fileNameArr = tempFile.getName().split("\\.");
                                                if(fileNameArr[0].equals(i+1+"")){
                                                    imgFile = tempFile;
                                                    break;
                                                }
                                            }
                                            if (imgFile.getName().startsWith(".")) {          //过滤部分操作系统的隐藏文件
                                                continue;
                                            }


                                            String pushCommandStr = "/opt/android_sdk/platform-tools/adb -s " + deviceName + " push " + imgFile.getPath() + " " + phoneLocalPath;
                                            CommandUtil.run(pushCommandStr);
                                            Thread.sleep(1000);
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
                                                logger.info("将 图片文件 push 从安卓设备，第【" + j + "】次发送通知更新【" + sendFriendCircleParam.get("nickName") + "】" + imgFile.getName() + " 图片成功..... ");
                                            }
                                            logger.info("将 图片文件 push 从安卓设备【" + deviceName + "】成功，imgFile = " + imgFile.getPath());
                                            if (!flag) {
                                                flag = true;
                                            }
                                        } catch (Exception e) {
                                            logger.info("将 图片文件 push 到安卓设备【" + deviceName + "】 失败，设备未连接到电脑上, e : ", e);
                                        }
                                    }
                                }
                            } else {
                                logger.info("设备描述【" + sendFriendCircleParam.get("deviceNameDesc") + "】设备编码【" + sendFriendCircleParam.get("deviceName") + "】，当前设备的执行时间第【" + startHour + "】小时，当前时间是第【" + currentHour + "】小时....");
                                continue;
                            }
                        }
                    } catch (Exception e) {
                        logger.error("解析设备的执行时间段是异常， e : ", e);
                    }
                }
            }
        }
        return flag;
    }

    /**
     * 将 图片文件  从安卓设备里面 删除
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
                String startTimeStr = sendFriendCircleParam.get("startTime") != null ? sendFriendCircleParam.get("startTime").toString() : "";
                String endTimeStr = sendFriendCircleParam.get("endTime") != null ? sendFriendCircleParam.get("endTime").toString() : "";
                Date currentDate = sendFriendCircleParam.get("currentDate") != null ? (Date) sendFriendCircleParam.get("currentDate") : new Date();
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
                                //设备编码
                                String deviceName =
                                        deviceNameMap.get("deviceName") != null ?
                                                deviceNameMap.get("deviceName").toString() :
                                                "";
                                //图片文件
                                String imgDirPath =
                                        sendFriendCircleParam.get("imgDirPath") != null ?
                                                sendFriendCircleParam.get("imgDirPath").toString() :
                                                "";
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
                                            logger.error("获取 今日油价 图片失败.");
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
                                                logger.info("将 图片文件 remove 从安卓设备，第【" + j + "】次发送通知更新【" + sendFriendCircleParam.get("nickName") + "】" + imgFile.getName() + " 图片成功..... ");
                                            }
                                            logger.info("将 图片文件 remove 从安卓设备【" + deviceName + "】成功，imgFile = " + imgFile.getPath());
                                        } catch (Exception e) {
                                            logger.info("将 图片文件 remove 从安卓设备【" + deviceName + "】失败，设备未连接到电脑上, e : ", e);
                                        }
                                    }
                                }
                            } else {
                                logger.info("设备描述【" + sendFriendCircleParam.get("deviceNameDesc") + "】设备编码【" + sendFriendCircleParam.get("deviceName") + "】，当前设备的执行时间第【" + startHour + "】小时，当前时间是第【" + currentHour + "】小时....");
                                continue;
                            }
                        }
                    } catch (Exception e) {
                        logger.error("解析手背的执行时间段是异常， e : ", e);
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
            for(File tempFile : imgFiles){          //确保文件顺序导出.
                String[] fileNameArr = tempFile.getName().split("\\.");
                if(fileNameArr[0].equals(i+1+"")){
                    imgFile = tempFile;
                    System.out.println("imgFile.getName() = " + imgFile.getName());
                    break;
                }
            }
        }
    }
}

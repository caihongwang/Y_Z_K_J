package com.oilStationMap.utils.wxAdAutomation.shareArticleToFriendCircleUtils;

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
 * 分享微信文章到微信朋友圈工具
 * appium -p 4723 -bp 4724 --session-override --command-timeout 600
 */
public class ShareArticleToFriendCircleUtils {

    public static final Logger logger = LoggerFactory.getLogger(ShareArticleToFriendCircleUtils.class);

    public static WX_DicDao wxDicDao = (WX_DicDao) ApplicationContextUtils.getBeanByClass(WX_DicDao.class);

    public static WX_DicService wxDicService = (WX_DicService) ApplicationContextUtils.getBeanByClass(WX_DicServiceImpl.class);

    public static WX_MessageService wxMessageService = (WX_MessageService) ApplicationContextUtils.getBeanByClass(WX_MessageServiceImpl.class);

    /**
     * 前置条件：将微信文章群发到【油站科技-内部交流群】里面
     * 分享微信文章到微信朋友圈
     *
     * @param paramMap
     * @throws Exception
     */
    public static void shareArticleToFriendCircle(Map<String, Object> paramMap) throws Exception {
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
                List<HashMap<String, Object>> rebootDeviceNameList = Lists.newArrayList();          //执行失败的设备列表，待重新执行
                paramMap.put("dicType", "shareArticleToFriendCircle");
                paramMap.put("dicCode", nickName);        //指定 某一个分享微信文章到微信朋友圈 发送
                ResultDTO resultDTO = wxDicService.getLatelyDicByCondition(paramMap);
                List<Map<String, String>> resultList = resultDTO.getResultList();
                if (resultList != null && resultList.size() > 0) {
                    //获取发送朋友圈的内容信息.
                    Map<String, Object> shareArticleToFriendCircleParam = MapUtil.getObjectMap(resultList.get(0));
                    String theId = shareArticleToFriendCircleParam.get("id").toString();
                    //获取设备列表和配套的坐标配置
                    List<String> dicCodeList = Lists.newArrayList();
                    dicCodeList.add("HuaWeiListAndShareArticleToFriendCircleLocaltion");//获取 华为 Mate 8 设备列表和配套的坐标配置
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
                            shareArticleToFriendCircleParam.putAll(deviceLocaltionMap);
                            //获取设备列表
                            String deviceNameListStr = deviceNameAndLocaltionJSONObject.getString("deviceNameList");
                            List<Map<String, Object>> deviceNameList = JSONObject.parseObject(deviceNameListStr, List.class);
                            //appium端口号
                            String appiumPort = deviceNameAndLocaltionJSONObject.getString("appiumPort");
                            shareArticleToFriendCircleParam.put("appiumPort", appiumPort);
                            if (deviceNameList != null && deviceNameList.size() > 0) {
                                for (Map<String, Object> deviceNameMap : deviceNameList) {
                                    shareArticleToFriendCircleParam.putAll(deviceNameMap);
                                    //判断推广时间是否还在推广期内
                                    String startTimeStr = shareArticleToFriendCircleParam.get("startTime") != null ? shareArticleToFriendCircleParam.get("startTime").toString() : "";
                                    String endTimeStr = shareArticleToFriendCircleParam.get("endTime") != null ? shareArticleToFriendCircleParam.get("endTime").toString() : "";
                                    if (!"".equals(startTimeStr) && !"".equals(endTimeStr)) {
                                        try {
                                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                            Date startTime = sdf.parse(startTimeStr);
                                            Date endTime = sdf.parse(endTimeStr);
                                            if (DateUtil.isEffectiveDate(currentDate, startTime, endTime)) {
                                                //判断当前设备的执行小时时间是否与当前时间匹配
                                                String startHour =
                                                        shareArticleToFriendCircleParam.get("startHour") != null ?
                                                                shareArticleToFriendCircleParam.get("startHour").toString() :
                                                                "";
                                                String currentHour = new SimpleDateFormat("HH").format(currentDate);
                                                if (startHour.equals(currentHour)) {
                                                    //开始转发微信昵称朋友圈
                                                    sw.split();
                                                    logger.info("设备描述【" + shareArticleToFriendCircleParam.get("deviceNameDesc") + "】设备编码【" + shareArticleToFriendCircleParam.get("deviceName") + "】操作【" + shareArticleToFriendCircleParam.get("action") + "】昵称【" + nickName + "】的将微信文章群发到朋友圈即将开始发送，总共花费 " + sw.toSplitString() + " 秒....");
                                                    new RealMachineDevices().shareArticleToFriendCircle(shareArticleToFriendCircleParam, sw);
                                                    Thread.sleep(5000);
                                                } else {
                                                    //下一个设备
                                                    sw.split();
                                                    logger.info("设备描述【" + shareArticleToFriendCircleParam.get("deviceNameDesc") + "】设备编码【" + shareArticleToFriendCircleParam.get("deviceName") + "】，当前设备的执行时间第【" + startHour + "】小时，当前时间是第【" + currentHour + "】小时，总共花费 " + sw.toSplitString() + " 秒....");
                                                    continue;
                                                }
//                                            sw.split();
//                                            logger.info( "设备描述【"+shareArticleToFriendCircleParam.get("deviceNameDesc")+"】设备编码【"+shareArticleToFriendCircleParam.get("deviceName")+"】操作【"+shareArticleToFriendCircleParam.get("action")+"】昵称【"+nickName+"】的将微信文章群发到朋友圈即将开始发送，总共花费 " + sw.toSplitString() + " 秒....");
//                                            new RealMachineDevices().shareArticleToFriendCircle(shareArticleToFriendCircleParam, sw);
//                                            Thread.sleep(5000);
                                            } else if (DateUtil.isBeforeDate(currentDate, startTime)) {
                                                logger.info("尚未开始，暂不处理....");
                                            } else {
                                                Map<String, Object> tempMap = Maps.newHashMap();
                                                tempMap.put("id", theId);
                                                tempMap.put("dicStatus", 1);
                                                wxDicService.updateDic(tempMap);    //更新这条转发微信昵称朋友圈内容为已删除
                                                logger.info("昵称【" + nickName + "】的转发微信昵称朋友圈内容已到期....");
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            HashMap<String, Object> rebootDeviceNameMap = Maps.newHashMap();
                                            rebootDeviceNameMap.putAll(shareArticleToFriendCircleParam);
                                            rebootDeviceNameList.add(rebootDeviceNameMap);      //当前设备执行失败，加入待重新执行的设备列表
                                        }
                                    } else {
                                        logger.info("昵称【" + nickName + "】的转发微信昵称朋友圈内容推广日期不能为空....");
                                    }
                                }
                            }
                        } else {
                            logger.info(dicCode + " 设备列表和配套的坐标配置 不存在，请使用adb命令查询设备号并入库.");
                        }
                    }
                } else {
                    logger.info("分享微信文章到微信朋友圈 失败.");
                }
                //对执行失败的设备列表进行重新执行,最多循环执行5遍
                Integer index = 1;
                while (rebootDeviceNameList.size() > 0) {
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
                    Iterator<HashMap<String, Object>> iterator = rebootDeviceNameList.iterator();
                    while (iterator.hasNext()) {
                        Map<String, Object> deviceNameMap = iterator.next();
                        try {
                            sw.split();
                            logger.info("设备描述【" + deviceNameMap.get("deviceNameDesc") + "】设备编码【" + deviceNameMap.get("deviceName") + "】操作【" + deviceNameMap.get("action") + "】昵称【" + deviceNameMap.get("nickName") + "】的将微信文章群发到朋友圈即将开始发送，总共花费 " + sw.toSplitString() + " 秒....");
                            new RealMachineDevices().shareArticleToFriendCircle(deviceNameMap, sw);
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
                sw.split();
                if (rebootDeviceNameList.size() > 0) {
                    logger.info("【" + nickName + "】【分享微信文章到微信朋友圈】5次次批量执行均失败的设备如下，总共花费 " + sw.toSplitString() + " 秒....");
                    logger.info("【" + nickName + "】【分享微信文章到微信朋友圈】5次次批量执行均失败的设备如下，总共花费 " + sw.toSplitString() + " 秒....");
                    logger.info("【" + nickName + "】【分享微信文章到微信朋友圈】5次次批量执行均失败的设备如下，总共花费 " + sw.toSplitString() + " 秒....");
                    logger.info("【" + nickName + "】【分享微信文章到微信朋友圈】5次次批量执行均失败的设备如下，总共花费 " + sw.toSplitString() + " 秒....");
                    logger.info("【" + nickName + "】【分享微信文章到微信朋友圈】5次次批量执行均失败的设备如下，总共花费 " + sw.toSplitString() + " 秒....");
                    String exceptionDevices = "异常设备列表";
                    for (HashMap<String, Object> rebootDeviceNameMap : rebootDeviceNameList) {
                        exceptionDevices = exceptionDevices + "【" + rebootDeviceNameMap.get("deviceNameDesc") + "】";
                        logger.info("【" + rebootDeviceNameMap.get("deviceNameDesc") + "】设备编码【" + rebootDeviceNameMap.get("deviceName") + "】操作【" + rebootDeviceNameMap.get("action") + "】昵称【" + rebootDeviceNameMap.get("nickName") + "】在最终在重新执行列表中失败......");
                    }
                    logger.info("【" + nickName + "】【分享微信文章到微信朋友圈】5次次批量执行均失败的设备如上，总共花费 " + sw.toSplitString() + " 秒....");
                    logger.info("【" + nickName + "】【分享微信文章到微信朋友圈】5次次批量执行均失败的设备如上，总共花费 " + sw.toSplitString() + " 秒....");
                    logger.info("【" + nickName + "】【分享微信文章到微信朋友圈】5次次批量执行均失败的设备如上，总共花费 " + sw.toSplitString() + " 秒....");
                    logger.info("【" + nickName + "】【分享微信文章到微信朋友圈】5次次批量执行均失败的设备如上，总共花费 " + sw.toSplitString() + " 秒....");
                    logger.info("【" + nickName + "】【分享微信文章到微信朋友圈】5次次批量执行均失败的设备如上，总共花费 " + sw.toSplitString() + " 秒....");
                    if (rebootDeviceNameList != null && rebootDeviceNameList.size() > 0) {
                        //建议使用http协议访问阿里云，通过阿里元来完成此操作.
                        HttpsUtil httpsUtil = new HttpsUtil();
                        Map<String, String> exceptionDevicesParamMap = Maps.newHashMap();
                        exceptionDevicesParamMap.put("nickName", nickName);
                        exceptionDevicesParamMap.put("operatorName", "分享微信文章到微信朋友圈");
                        exceptionDevicesParamMap.put("exceptionDevices", exceptionDevices);
                        String exceptionDevicesNotifyUrl = "https://www.yzkj.store/oilStationMap/wxMessage/exceptionDevicesMessageSend";
                        String resultJson = httpsUtil.post(exceptionDevicesNotifyUrl, exceptionDevicesParamMap);
                        logger.info("微信消息异常发送反馈：" + resultJson);
                        //本地发送，但是基于IP不断变化，不建议
//                try {
//                    Map<String, Object> exceptionDevicesParamMap = Maps.newHashMap();
//                    exceptionDevicesParamMap.put("operatorName", "分享微信文章到微信朋友圈");
//                    exceptionDevicesParamMap.put("exceptionDevices", exceptionDevices);
//                    wxMessageService.exceptionDevicesMessageSend(exceptionDevicesParamMap);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                    }
                } else {
                    logger.info("【" + nickName + "】【分享微信文章到微信朋友圈】全部执行成功，总共花费 " + sw.toSplitString() + " 秒....");
                    logger.info("【" + nickName + "】【分享微信文章到微信朋友圈】全部执行成功，总共花费 " + sw.toSplitString() + " 秒....");
                    logger.info("【" + nickName + "】【分享微信文章到微信朋友圈】全部执行成功，总共花费 " + sw.toSplitString() + " 秒....");
                    logger.info("【" + nickName + "】【分享微信文章到微信朋友圈】全部执行成功，总共花费 " + sw.toSplitString() + " 秒....");
                    logger.info("【" + nickName + "】【分享微信文章到微信朋友圈】全部执行成功，总共花费 " + sw.toSplitString() + " 秒....");
                }
            }

            sw.split();
            logger.info("【分享微信文章到微信朋友圈】已完成，总共花费 " + sw.toSplitString() + " 秒，nickNameListStr = " + nickNameListStr + "....");
            logger.info("【分享微信文章到微信朋友圈】已完成，总共花费 " + sw.toSplitString() + " 秒，nickNameListStr = " + nickNameListStr + "....");
            logger.info("【分享微信文章到微信朋友圈】已完成，总共花费 " + sw.toSplitString() + " 秒，nickNameListStr = " + nickNameListStr + "....");
            logger.info("【分享微信文章到微信朋友圈】已完成，总共花费 " + sw.toSplitString() + " 秒，nickNameListStr = " + nickNameListStr + "....");
            logger.info("【分享微信文章到微信朋友圈】已完成，总共花费 " + sw.toSplitString() + " 秒，nickNameListStr = " + nickNameListStr + "....");
        }
    }
}

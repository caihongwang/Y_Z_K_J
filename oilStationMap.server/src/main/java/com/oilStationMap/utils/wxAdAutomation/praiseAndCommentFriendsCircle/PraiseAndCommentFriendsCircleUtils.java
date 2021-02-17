package com.oilStationMap.utils.wxAdAutomation.praiseAndCommentFriendsCircle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.oilStationMap.config.GlobalVariableConfig;
import com.oilStationMap.dao.WX_DicDao;
import com.oilStationMap.service.MailService;
import com.oilStationMap.service.WX_DicService;
import com.oilStationMap.service.WX_MessageService;
import com.oilStationMap.utils.CommandUtil;
import com.oilStationMap.utils.HttpsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 点赞和评论朋友圈工具
 * appium -p 4725 -bp 4726 --session-override --command-timeout 600
 */
@Component
public class PraiseAndCommentFriendsCircleUtils {

    public static final Logger logger = LoggerFactory.getLogger(PraiseAndCommentFriendsCircleUtils.class);

    @Autowired
    public WX_DicDao wxDicDao;

    @Autowired
    public MailService mailService;

    @Autowired
    public WX_DicService wxDicService;

    @Autowired
    public WX_MessageService wxMessageService;

    /**
     * 点赞和评论朋友圈
     *
     * @param paramMap
     * @throws Exception
     */
    public void praiseAndCommentFriendsCircle(Map<String, Object> paramMap) throws Exception {
        String nickName = paramMap.get("nickName") != null ? paramMap.get("nickName").toString() : "";
        String commentContent = paramMap.get("commentContent") != null ? paramMap.get("commentContent").toString() : "";
        String allSwipeNum = paramMap.get("allSwipeNum") != null ? paramMap.get("allSwipeNum").toString() : "";
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
        //当前 自动化操作 点赞和评论朋友圈
        String action = "praiseAndCommentFriendsCircle";
        //获取 点赞和评论朋友圈 设备列表和配套的坐标配置
        String deviceNameListAnddeviceLocaltionOfCode = "HuaWeiListAndPraiseAndCommentFriendsCircleLocaltion";
        for (String currentDateStr : currentDateList) {
            try {
                boolean isOperatedFlag = false;     //当前设备是否操作【已经添加过好友】的标志位
                Map<String, Object> praiseAndCommentFriendsCircleParam = Maps.newHashMap();
                HashMap<String, Object> reboot_praiseAndCommentFriendsCircleParam = Maps.newHashMap();
                praiseAndCommentFriendsCircleParam.put("commentContent", commentContent);
                praiseAndCommentFriendsCircleParam.put("allSwipeNum", allSwipeNum);
                praiseAndCommentFriendsCircleParam.put("nickName", nickName);
                //获取当前时间，用于校验【那台设备】在【当前时间】执行【当前自动化操作】
                Date currentDate = new SimpleDateFormat("yyyy-MM-dd HH").parse(currentDateStr);
                //获取设备列表和配套的坐标配置
                paramMap.clear();
                paramMap.put("dicType", "deviceNameListAndLocaltion");
                paramMap.put("dicCode", deviceNameListAnddeviceLocaltionOfCode);
                List<Map<String, Object>> list = wxDicDao.getSimpleDicByCondition(paramMap);        //当前设备列表和配套的坐标配置
                if (list != null && list.size() > 0) {
                    //获取dicRemark
                    String deviceNameAndLocaltionStr = list.get(0).get("dicRemark") != null ? list.get(0).get("dicRemark").toString() : "";
                    JSONObject deviceNameAndLocaltionJSONObject = JSONObject.parseObject(deviceNameAndLocaltionStr);
                    //获取设备坐标
                    String deviceLocaltionStr = deviceNameAndLocaltionJSONObject.getString("deviceLocaltion");
                    Map<String, Object> deviceLocaltionMap = JSONObject.parseObject(deviceLocaltionStr, Map.class);
                    praiseAndCommentFriendsCircleParam.putAll(deviceLocaltionMap);
                    //获取设备列表
                    String deviceNameListStr = deviceNameAndLocaltionJSONObject.getString("deviceNameList");
                    List<Map<String, Object>> deviceNameList = JSONObject.parseObject(deviceNameListStr, List.class);
                    if (deviceNameList != null && deviceNameList.size() > 0) {
                        for (Map<String, Object> deviceNameMap : deviceNameList) {
                            praiseAndCommentFriendsCircleParam.putAll(deviceNameMap);
                            //获取设备编码
                            deviceName =
                                    praiseAndCommentFriendsCircleParam.get("deviceName") != null ?
                                            praiseAndCommentFriendsCircleParam.get("deviceName").toString() :
                                            null;
                            //当前设备描述
                            deviceNameDesc =
                                    praiseAndCommentFriendsCircleParam.get("deviceNameDesc") != null ?
                                            praiseAndCommentFriendsCircleParam.get("deviceNameDesc").toString() :
                                            null;
                            //判断当前设备的执行小时时间是否与当前时间匹配
                            boolean isExecuteFlag = false;
                            String startHour =
                                    praiseAndCommentFriendsCircleParam.get("startHour") != null ?
                                            praiseAndCommentFriendsCircleParam.get("startHour").toString() :
                                            "";
                            String currentHour = new SimpleDateFormat("HH").format(currentDate);
                            if (startHour.equals(currentHour)) {    //当前设备在规定的执行时间才执行自动化操作，同时获取对应的appium端口号
                                try {
                                    //获取appium端口号
                                    appiumPort = GlobalVariableConfig.getAppiumPort(action, deviceNameDesc);
                                    praiseAndCommentFriendsCircleParam.put("appiumPort", appiumPort);
                                    //设置当前这杯可执行的标志位
                                    isExecuteFlag = true;
                                } catch (Exception e) {
                                    //获取appium端口号失败
                                    logger.error("【点赞和评论朋友圈】" + e.getMessage());
                                    //设置当前这杯可被行的标志位
                                    isExecuteFlag = false;
                                    continue;
                                }
                            } else {
                                logger.info("【点赞和评论朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】，当前设备的执行时间第【" + startHour + "】小时，当前时间是第【" + currentHour + "】小时....");
                                continue;
                            }
                            try {
                                if (isExecuteFlag) {
                                    //开始【同意进群】
                                    logger.info("【点赞和评论朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】即将开始....");
                                    isOperatedFlag = new RealMachineDevices().praiseAndCommentFriendsCircle(praiseAndCommentFriendsCircleParam);
                                    Thread.sleep(5000);
//                                //测试
//                                isOperatedFlag = true;
//                                reboot_praiseAndCommentFriendsCircleParam.putAll(praiseAndCommentFriendsCircleParam);
//                                Thread.sleep(1000);
                                    break;      //后面时间段的设备不需要执行，因为每个时间段只有个设备可被执行
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                reboot_praiseAndCommentFriendsCircleParam.putAll(praiseAndCommentFriendsCircleParam);
                                break;      //后面时间段的设备不需要执行，因为每个时间段只有个设备可被执行
                            }
                        }
                    }
                } else {
                    logger.info("【点赞和评论朋友圈】" + deviceNameListAnddeviceLocaltionOfCode + " 设备列表和配套的坐标配置 不存在，请使用adb命令查询设备号并入库.");
                }

                //4.对执行失败的设备进行重新执行【点赞和评论朋友圈】,最多重复执行15次，每间隔4次重启一次手机
                Integer index = 1;
                while (reboot_praiseAndCommentFriendsCircleParam.size() > 0) {
                    //等待所有设备重启
                    Thread.sleep(60000);
//                //测试
//                Thread.sleep(1000);
                    if (index > 15) {
                        break;
                    }
                    logger.info("【点赞和评论朋友圈】第【" + index + "】次重新执行设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】...");
                    try {
                        logger.info("【点赞和评论朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】即将开始....");
                        isOperatedFlag = new RealMachineDevices().praiseAndCommentFriendsCircle(reboot_praiseAndCommentFriendsCircleParam);
                        reboot_praiseAndCommentFriendsCircleParam.clear();       //清空需要重新执行的设备参数
//                    //测试
//                    if (index == 15) {
//                        isOperatedFlag = true;
//                        reboot_praiseAndCommentFriendsCircleParam.clear();
//                    }
//                    Thread.sleep(1000);
                    } catch (Exception e) {     //当运行设备异常之后，就会对当前设备进行记录，准备重启，后续再对此设备进行重新执行
                        e.printStackTrace();
                        try {
                            if (index % 4 == 0) {
                                //【点赞和评论朋友圈】过程中，出现不会对设备进行重启，所以在重新执行的单个过程出现异常则重启
                                CommandUtil.run("/opt/android_sdk/platform-tools/adb -s " + deviceName + " reboot");
                                logger.info("【点赞和评论朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】重启成功...");
                            }
                        } catch (Exception e1) {
                            logger.info("【点赞和评论朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】重启失败...");
                        }
                    }
                    index++;
                }

                //回收-appiumPort
                GlobalVariableConfig.recoveryAppiumPort(appiumPort);

                if (reboot_praiseAndCommentFriendsCircleParam.size() > 0) {
                    logger.info("【点赞和评论朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                    logger.info("【点赞和评论朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                    logger.info("【点赞和评论朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                    logger.info("【点赞和评论朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                    logger.info("【点赞和评论朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                    String exceptionDevices = "异常设备列表" + "【" + deviceNameDesc + "】";
                    logger.info("【点赞和评论朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                    logger.info("【点赞和评论朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                    logger.info("【点赞和评论朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                    logger.info("【点赞和评论朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");
                    logger.info("【点赞和评论朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】15次重新执行均失败....");

                    //建议使用http协议访问阿里云，通过阿里元来完成此操作.
                    HttpsUtil httpsUtil = new HttpsUtil();
                    Map<String, String> exceptionDevicesParamMap = Maps.newHashMap();
                    exceptionDevicesParamMap.put("nickName", "无");
                    exceptionDevicesParamMap.put("operatorName", "点赞和评论朋友圈");
                    exceptionDevicesParamMap.put("exceptionDevices", exceptionDevices);
                    String exceptionDevicesNotifyUrl = "https://www.yzkj.store/oilStationMap/wxMessage/exceptionDevicesMessageSend";
                    String resultJson = httpsUtil.post(exceptionDevicesNotifyUrl, exceptionDevicesParamMap);
                    logger.info("微信消息异常发送反馈：" + resultJson);
                    //邮件通知
                    StringBuffer mailMessageBuf = new StringBuffer();
                    mailMessageBuf.append("蔡红旺，您好：\n");
                    mailMessageBuf.append("        ").append("\t操作名称：点赞和评论朋友圈").append("\n");
                    mailMessageBuf.append("        ").append("\t操作设备：").append(deviceNameDesc).append("\n");
                    mailMessageBuf.append("        ").append("\t异常时间：").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("\n");
                    mailMessageBuf.append("        ").append("\t异常地点：").append("北京市昌平区").append("\n");
                    mailMessageBuf.append("        ").append("\t温馨提示：").append("请检查以下手机的接口，并手动辅助自动化操作.").append("\n");
                    mailMessageBuf.append("        ").append("\t异常原因描述：").append("Usb接口不稳定断电或者微信版本已被更新导致坐标不匹配").append("\n");
                    mailService.sendSimpleMail("caihongwang@dingtalk.com", "【服务异常通知】点赞和评论朋友圈", mailMessageBuf.toString());
                    logger.info("【邮件通知】【服务完成通知】点赞和评论朋友圈 ......" );
                } else {
                    if (isOperatedFlag) {
                        logger.info("【点赞和评论朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】成功....");
                        logger.info("【点赞和评论朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】成功....");
                        logger.info("【点赞和评论朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】成功....");
                        logger.info("【点赞和评论朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】成功....");
                        logger.info("【点赞和评论朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】成功....");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

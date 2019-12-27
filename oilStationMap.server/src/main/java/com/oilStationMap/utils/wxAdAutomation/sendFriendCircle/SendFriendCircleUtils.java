package com.oilStationMap.utils.wxAdAutomation.sendFriendCircle;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.oilStationMap.dao.WX_DicDao;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.service.WX_DicService;
import com.oilStationMap.service.impl.WX_DicServiceImpl;
import com.oilStationMap.utils.ApplicationContextUtils;
import com.oilStationMap.utils.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 发布朋友圈工具
 * appium -p 4723 -bp 4724 --session-override --command-timeout 600
 */
public class SendFriendCircleUtils {

    public static final Logger logger = LoggerFactory.getLogger(SendFriendCircleUtils.class);

    public static WX_DicService wxDicService = (WX_DicService) ApplicationContextUtils.getBeanByClass(WX_DicServiceImpl.class);

    public static WX_DicDao wxDicDao = (WX_DicDao) ApplicationContextUtils.getBeanByClass(WX_DicDao.class);

    /**
     * 发布朋友圈for所有设备
     */
    public static void sendFriendCircle(Map<String, Object> paramMap){
        String nickNameListStr = paramMap.get("nickNameListStr")!=null?paramMap.get("nickNameListStr").toString():"";
        List<String> nickNameList = JSONObject.parseObject(nickNameListStr, List.class);
        for(String nickName : nickNameList){
            List<HashMap<String, Object>> rebootDeviceNameList = Lists.newArrayList();          //执行失败的设备列表，待重新执行
            paramMap.put("dicType", "sendFriendCircle");
            paramMap.put("dicCode", nickName);        //指定转发微信昵称朋友圈内容
            ResultDTO resultDTO = wxDicService.getLatelyDicByCondition(paramMap);
            List<Map<String, String>> resultList = resultDTO.getResultList();
            if(resultList != null && resultList.size() > 0){
                //获取发送朋友圈的内容信息.
                Map<String, Object> sendFriendCircleParam = MapUtil.getObjectMap(resultList.get(0));
                String theId = sendFriendCircleParam.get("id").toString();
                //获取设备列表和配套的坐标配置
                List<String> dicCodeList = Lists.newArrayList();
                dicCodeList.add("HuaWeiMate8ListAndSendFriendCircleLocaltion");//获取 华为 Mate 8 设备列表和配套的坐标配置
                dicCodeList.add("HuaWeiMate8HListAndSendFriendCircleLocaltion");//获取 华为 Mate 8 海外版 设备列表和配套的坐标配置
                dicCodeList.add("HuaWeiP20ProListAndSendFriendCircleLocaltion");//获取 华为 P20 Pro 设备列表和配套的坐标配置
                dicCodeList.add("XiaoMiMax3ListAndSendFriendCircleLocaltion");//获取 小米 Max 3 设备列表和配套的坐标配置
                dicCodeList.add("HuaWeiMate7ListAndSendFriendCircleLocaltion");//获取 华为 Mate 7 设备列表和配套的坐标配置
                for(String dicCode : dicCodeList){
                    paramMap.clear();
                    paramMap.put("dicType", "deviceNameListAndLocaltion");
                    paramMap.put("dicCode", dicCode);
                    List<Map<String, Object>> list = wxDicDao.getSimpleDicByCondition(paramMap);
                    if(list != null && list.size() > 0){
                        String deviceNameAndLocaltionStr = list.get(0).get("dicRemark")!=null?list.get(0).get("dicRemark").toString():"";
                        JSONObject deviceNameAndLocaltionJSONObject = JSONObject.parseObject(deviceNameAndLocaltionStr);
                        //获取设备坐标
                        String deviceLocaltionStr = deviceNameAndLocaltionJSONObject.getString("deviceLocaltion");
                        Map<String, Object> deviceLocaltionMap = JSONObject.parseObject(deviceLocaltionStr, Map.class);
                        sendFriendCircleParam.putAll(deviceLocaltionMap);
                        //获取设备列表
                        String deviceNameListStr = deviceNameAndLocaltionJSONObject.getString("deviceNameList");
                        List<Map<String, Object>> deviceNameList = JSONObject.parseObject(deviceNameListStr, List.class);
                        if(deviceNameList != null && deviceNameList.size() > 0){
                            for(Map<String, Object> deviceNameMap : deviceNameList){
                                sendFriendCircleParam.putAll(deviceNameMap);//判断推广时间是否还在推广期内
                                String startTimeStr = sendFriendCircleParam.get("startTime")!=null?sendFriendCircleParam.get("startTime").toString():"";
                                String endTimeStr = sendFriendCircleParam.get("endTime")!=null?sendFriendCircleParam.get("endTime").toString():"";
                                if(!"".equals(startTimeStr) && !"".equals(endTimeStr)){
                                    try{
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        Date startTime = sdf.parse(startTimeStr);
                                        Date endTime = sdf.parse(endTimeStr);
                                        Date currentDate = new Date();
                                        if(currentDate.after(startTime) && currentDate.before(endTime)){
                                            logger.info( "设备描述【"+sendFriendCircleParam.get("deviceNameDesc")+"】设备编码【"+sendFriendCircleParam.get("deviceName")+"】操作【"+sendFriendCircleParam.get("action")+"】昵称【"+nickName+"】的发送朋友圈即将开始发送.....");
                                            new RealMachineDevices().sendFriendCircle(sendFriendCircleParam);
                                            Thread.sleep(5000);
                                        } else {
                                            logger.info("昵称【" + nickName + "】的转发朋友圈业务已到期....");
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        HashMap<String, Object> rebootDeviceNameMap = Maps.newHashMap();
                                        rebootDeviceNameMap.putAll(sendFriendCircleParam);
                                        rebootDeviceNameList.add(rebootDeviceNameMap);      //当前设备执行失败，加入待重新执行的设备列表
                                    }
                                } else {
                                    Map<String, Object> tempMap = Maps.newHashMap();
                                    tempMap.put("id", theId);
                                    tempMap.put("status", 1);
                                    wxDicService.updateDic(tempMap);    //更新这条朋友圈数据为已删除
                                    logger.info("昵称【" + nickName + "】的转发朋友圈业务推广日期不能为空....");
                                }
                            }
                        }
                    } else {
                        logger.info(dicCode + " 设备列表和配套的坐标配置 不存在，请使用adb命令查询设备号并入库.");
                    }
                }
            } else {
                logger.info("发布朋友圈 失败.");
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
                        logger.info("设备描述【" + deviceNameMap.get("deviceNameDesc") + "】设备编码【" + deviceNameMap.get("deviceName") + "】操作【" + deviceNameMap.get("action") + "】昵称【" + deviceNameMap.get("nickName") + "】的发送朋友圈即将开始发送.....");
                        new RealMachineDevices().sendFriendCircle(deviceNameMap);
                        Thread.sleep(5000);
                        iterator.remove();
                    } catch (Exception e) {     //当运行设备异常之后，就会对当前设备进行记录，准备重启，后续再对此设备进行重新执行
                        e.printStackTrace();
                    }
                }
                index++;
            }
            logger.info("【发送朋友圈】已经执行完毕......");
            logger.info("【发送朋友圈】已经执行完毕......");
            logger.info("【发送朋友圈】已经执行完毕......");
            for(HashMap<String, Object> rebootDeviceNameMap : rebootDeviceNameList){
                logger.info("设备描述【" + rebootDeviceNameMap.get("deviceNameDesc") + "】设备编码【" + rebootDeviceNameMap.get("deviceName") + "】操作【" + rebootDeviceNameMap.get("action") + "】昵称【" + rebootDeviceNameMap.get("nickName") + "】在最终在重新执行列表中失败......");
            }
        }
    }
}

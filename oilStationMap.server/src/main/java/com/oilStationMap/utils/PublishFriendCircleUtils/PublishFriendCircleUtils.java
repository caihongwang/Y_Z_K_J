package com.oilStationMap.utils.PublishFriendCircleUtils;

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

import java.util.List;
import java.util.Map;

/**
 * 发布朋友圈工具
 * appium -p 4723 -bp 4724 --session-override
 */
public class PublishFriendCircleUtils {

    public static final Logger logger = LoggerFactory.getLogger(PublishFriendCircleUtils.class);

    public static WX_DicService wxDicService = (WX_DicService) ApplicationContextUtils.getBeanByClass(WX_DicServiceImpl.class);

    public static WX_DicDao wxDicDao = (WX_DicDao) ApplicationContextUtils.getBeanByClass(WX_DicDao.class);

    /**
     * 发布朋友圈for所有设备
     */
    public static void sendFriendCircle(){
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("dicType", "publishFriendCircle");
        ResultDTO resultDTO = wxDicService.getLatelyDicByCondition(paramMap);
        List<Map<String, String>> resultList = resultDTO.getResultList();
        if(resultList != null && resultList.size() > 0){
            //获取发送朋友圈的内容信息.
            Map<String, Object> sendFriendCircleParam = MapUtil.getObjectMap(resultList.get(0));
            //设备：华为 Mate 8 发送朋友圈
            paramMap.clear();
            paramMap.put("dicType", "deviceNameList");
            paramMap.put("dicCode", "HuaWeiMate8DeviceNameList");
            List<Map<String, Object>> list = wxDicDao.getSimpleDicByCondition(paramMap);
            if(list != null && list.size() > 0){
                String deviceNameListStr = list.get(0).get("dicRemark")!=null?list.get(0).get("dicRemark").toString():"";
                List<Map<String, Object>> deviceNameList = JSONObject.parseObject(deviceNameListStr, List.class);
                if(deviceNameList != null && deviceNameList.size() > 0){
                    for(Map<String, Object> deviceNameMap : deviceNameList){
                        sendFriendCircleParam.putAll(deviceNameMap);
                        try{
                            new HuaWeiMate8().sendFriendCircle(sendFriendCircleParam);
                            Thread.sleep(5000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                logger.info("华为 Mate 8 没有设备号，请使用adb命令查询设备号并入库.");
            }
//            设备：华为 P20 Pro
//            try{
//                new HuaWeiP20Pro().sendFriendCircle(paramMap);
//                Thread.sleep(5000);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            小米 Max 3
//            try{
//                new XiaoMiMax3().sendFriendCircle(paramMap);
//                Thread.sleep(5000);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            华为 Mate 7
//            try{
//                new HuaWeiMate7Index1().sendFriendCircle(paramMap);
//                Thread.sleep(5000);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            try{
//                new HuaWeiMate7Index2().sendFriendCircle(paramMap);
//                Thread.sleep(5000);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            try{
//                new HuaWeiMate7Index4().sendFriendCircle(paramMap);
//                Thread.sleep(5000);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            try{
//                new ZhongXing529Index5().sendFriendCircle(paramMap);            //未成功
//                Thread.sleep(5000);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        } else {
            logger.info("获取发送朋友圈信息失败.");
        }

        paramMap.clear();
        paramMap.put("dicType", "chatByNickName");
        resultDTO = wxDicService.getLatelyDicByCondition(paramMap);
        resultList = resultDTO.getResultList();
        if(resultList != null && resultList.size() > 0){
            //获取通过昵称聊天通知对方已发朋友圈的内容信息.
            Map<String, Object> chatByNickNameParam = MapUtil.getObjectMap(resultList.get(0));
            //设备：华为 Mate 8 通过昵称聊天通知对方已发朋友圈
            paramMap.clear();
            paramMap.put("dicType", "deviceNameList");
            paramMap.put("dicCode", "HuaWeiMate8DeviceNameList");
            List<Map<String, Object>> list = wxDicDao.getSimpleDicByCondition(paramMap);
            if(list != null && list.size() > 0){
                String deviceNameListStr = list.get(0).get("dicRemark")!=null?list.get(0).get("dicRemark").toString():"";
                List<Map<String, Object>> deviceNameList = JSONObject.parseObject(deviceNameListStr, List.class);
                if(deviceNameList != null && deviceNameList.size() > 0){
                    for(Map<String, Object> deviceNameMap : deviceNameList){
                        chatByNickNameParam.putAll(deviceNameMap);
                        try{
                            new HuaWeiMate8().chatByNickName(chatByNickNameParam);
                            Thread.sleep(5000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                logger.info("华为 Mate 8 没有设备号，请使用adb命令查询设备号并入库.");
            }
        } else {
            logger.info("通过昵称聊天通知对方已发朋友圈失败.");
        }
    }

    public static void main(String[] args) {
        new PublishFriendCircleUtils().sendFriendCircle();
    }
}

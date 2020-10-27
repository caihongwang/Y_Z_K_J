package com.oilStationMap.quartz;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.service.*;
import com.oilStationMap.utils.CommandUtil;
import com.oilStationMap.utils.LonLatUtil;
import com.oilStationMap.utils.SpiderForZhuangYitUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 定时任务
 */
@Component
public class TimeTaskOfXxl {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TimeTaskOfXxl.class);

    //使用环境
    @Value("${spring.profiles.active}")
    private String useEnvironmental;

    @Autowired
    private WX_DicService wxDicService;

    @Autowired
    private WX_SpiderService wxSpiderService;

    @Autowired
    private WX_MessageService wxMessageService;

    @Autowired
    private WX_RedPacketService wxRedPacketService;

    @Autowired
    private WX_OilStationService WXOilStationService;

    @Autowired
    private WX_RedPacketHistoryService WXRedPacketHistoryService;

    @Autowired
    private WX_OilStationOperatorService WXOilStationOperatorService;


    private int num = 1;

    /**
     * 测试任务
     * @param param
     * @return
     * @throws Exception
     */
    @XxlJob("do_Test")
    public ReturnT<String> do_Test(String param) throws Exception {
        logger.info("XXL-JOB, Test Hello World. num = " + this.num);
        this.num++;
        return ReturnT.SUCCESS;
    }

    /**
     * 添加群成员为好友的V群-当阿里云主机迁移到部署机器时可以使用当前任务
     * @param param
     * @return
     * @throws Exception
     */
    @XxlJob("do_addGroupMembersAsFriends")
    public ReturnT<String> do_addGroupMembersAsFriends(String param) throws Exception {
        if (useEnvironmental != null && "prepub".equals(useEnvironmental)) {
            Map<String, Object> paramMap = Maps.newHashMap();
            List<String> nickNameList = Lists.newArrayList();
            try {
                //通过任务参数进行启动-发布朋友圈
                String nickNameListStr = param;
                nickNameList = JSONObject.parseObject(nickNameListStr, List.class);
                paramMap.put("nickNameListStr", nickNameListStr);
                wxSpiderService.sendFriendCircle(paramMap);
            } catch (Exception e) {
                logger.error("在hanlder中启动appium,添加群成员为好友的V群-do_SendFriendCircle is error,即将通过数据库添加群成员为好友的V群 paramMap : " + param + ", e : ", e);
                //直接从现有的数据库中获取数据启动-发布朋友圈
                paramMap.put("dicType", "addGroupMembersAsFriends");
                ResultDTO resultDTO = wxDicService.getSimpleDicByCondition(paramMap);
                if(resultDTO != null && resultDTO.getResultList() != null && resultDTO.getResultList().size() > 0){
                    for(Map<String, String> addGroupMembersAsFriendsMap : resultDTO.getResultList()){
                        nickNameList.add(addGroupMembersAsFriendsMap.get("dicCode"));
                    }
                }
                paramMap.clear();
                paramMap.put("nickNameListStr", JSONObject.toJSONString(nickNameList));
                wxSpiderService.addGroupMembersAsFriends(paramMap);
            }
        }
        return ReturnT.SUCCESS;
    }

    /**
     * 发布朋友圈-当阿里云主机迁移到部署机器时可以使用当前任务
     * @param param
     * @return
     * @throws Exception
     */
    @XxlJob("do_SendFriendCircle")
    public ReturnT<String> do_SendFriendCircle(String param) throws Exception {
        if (useEnvironmental != null && "prepub".equals(useEnvironmental)) {
            Map<String, Object> paramMap = Maps.newHashMap();
            List<String> nickNameList = Lists.newArrayList();
            try {
                //通过任务参数进行启动-发布朋友圈
                String nickNameListStr = param;
                nickNameList = JSONObject.parseObject(nickNameListStr, List.class);
                paramMap.put("nickNameListStr", nickNameListStr);
                wxSpiderService.sendFriendCircle(paramMap);
            } catch (Exception e) {
                logger.error("在hanlder中启动appium,自动化发送微信朋友圈-do_SendFriendCircle is error,即将通过数据库获取数据发送朋友圈 paramMap : " + param + ", e : ", e);
                //直接从现有的数据库中获取数据启动-发布朋友圈
                paramMap.put("dicType", "sendFriendCircle");
                ResultDTO resultDTO = wxDicService.getSimpleDicByCondition(paramMap);
                if(resultDTO != null && resultDTO.getResultList() != null && resultDTO.getResultList().size() > 0){
                    for(Map<String, String> sendFriendCircleMap : resultDTO.getResultList()){
                        nickNameList.add(sendFriendCircleMap.get("dicCode"));
                    }
                }
                paramMap.clear();
                paramMap.put("nickNameListStr", JSONObject.toJSONString(nickNameList));
                wxSpiderService.sendFriendCircle(paramMap);
            }
        }
        return ReturnT.SUCCESS;
    }

    /**
     * 分享微信文章到微信朋友圈-当阿里云主机迁移到部署机器时可以使用当前任务
     * @param param
     * @return
     * @throws Exception
     */
    @XxlJob("do_ShareArticleToFriendCircle")
    public ReturnT<String> do_ShareArticleToFriendCircle(String param) throws Exception {
        if (useEnvironmental != null && "prepub".equals(useEnvironmental)) {
            Map<String, Object> paramMap = Maps.newHashMap();
            List<String> nickNameList = Lists.newArrayList();
            try {
                //通过任务参数进行启动-分享微信文章到微信朋友圈
                String nickNameListStr = param;
                nickNameList = JSONObject.parseObject(nickNameListStr, List.class);
                paramMap.put("nickNameListStr", nickNameListStr);
                wxSpiderService.shareArticleToFriendCircle(paramMap);
            } catch (Exception e) {
                logger.error("在hanlder中启动appium,分享微信文章到微信朋友圈-shareArticleToFriendCircle is error, 即将通过数据库获取数据分享微信文章到微信朋友圈 paramMap : " + param + ", e : ", e);//直接从现有的数据库中获取数据启动-发布朋友圈
                paramMap.put("dicType", "shareArticleToFriendCircle");
                ResultDTO resultDTO = wxDicService.getSimpleDicByCondition(paramMap);
                if(resultDTO != null && resultDTO.getResultList() != null && resultDTO.getResultList().size() > 0){
                    for(Map<String, String> sendFriendCircleMap : resultDTO.getResultList()){
                        nickNameList.add(sendFriendCircleMap.get("dicCode"));
                    }
                }
                paramMap.clear();
                paramMap.put("nickNameListStr", JSONObject.toJSONString(nickNameList));
                wxSpiderService.shareArticleToFriendCircle(paramMap);
            }
        }
        return ReturnT.SUCCESS;
    }

    /**
     * 每天早上09:00，定时发送红包
     * 对小程序(油价地图)上操作【添加油站】和【纠正油价】的用户直接发送红包
     */
    @XxlJob("do_SendRedPacket")
    public ReturnT<String> do_SendRedPacket(String param) {
        if (useEnvironmental != null && "prepub".equals(useEnvironmental)) {
            //当昨天时间
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -1);
            Date yesterDate = calendar.getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String yesterDateStr = formatter.format(yesterDate);
            //1.整合参数
            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("createTime", yesterDateStr);
            ResultDTO resultDTO = WXOilStationOperatorService.getSimpleOilStationOperatorByCondition(paramMap);
            if (resultDTO.getResultList() != null && resultDTO.getResultList().size() > 0) {
                List<Map<String, String>> oilStationOperatorList = resultDTO.getResultList();
                for (Map<String, String> oilStationOperatorMap : oilStationOperatorList) {
                    Object idObj = oilStationOperatorMap.get("id");
                    Object uidObj = oilStationOperatorMap.get("uid");
                    Object openIdObj = oilStationOperatorMap.get("openId");
                    Object redPacketTotalObj = oilStationOperatorMap.get("redPacketTotal");
                    if (uidObj != null && openIdObj != null
                            && redPacketTotalObj != null && !"".equals(redPacketTotalObj.toString())
                            && !"0".equals(redPacketTotalObj.toString())) {
                        //2.整合发送红包的参数
                        Map<String, Object> redPacketParamMap = Maps.newHashMap();
                        float redPacketTotalFloat = Float.parseFloat(redPacketTotalObj.toString() != "" ? redPacketTotalObj.toString() : "10");
                        String redPacketTotal = ((int) (redPacketTotalFloat * 100)) + "";
                        redPacketParamMap.put("amount", redPacketTotal);
                        redPacketParamMap.put("openId", openIdObj.toString());
                        redPacketParamMap.put("reUserName", OilStationMapCode.WX_MINI_PROGRAM_NAME);
                        redPacketParamMap.put("wxPublicNumGhId", "gh_417c90af3488");
                        redPacketParamMap.put("desc", OilStationMapCode.WX_MINI_PROGRAM_NAME + "发红包了，快来看看吧.");
                        ResultMapDTO resultMapDTO = wxRedPacketService.enterprisePayment(redPacketParamMap);
                        //3.将加油站操作记录表的状态变更为已处理
                        Map<String, Object> oilStationOperatorMap_updateParam = Maps.newHashMap();
                        oilStationOperatorMap_updateParam.put("id", oilStationOperatorMap.get("id"));
                        oilStationOperatorMap_updateParam.put("status", "1");
                        //4.发送成功，将已发送的红包进行记录，并保存.
                        if (OilStationMapCode.SUCCESS.getNo() == resultMapDTO.getCode()) {
                            //更新加油站操作的红包状态
                            Map<String, Object> paramMap_temp = Maps.newHashMap();
                            paramMap_temp.clear();      //清空参数，重新传参
                            paramMap_temp.put("id", idObj.toString());
                            paramMap_temp.put("status", "2");
                            WXOilStationOperatorService.updateOilStationOperator(paramMap_temp);
                            //插入红包操作记录
                            paramMap_temp.clear();      //清空参数，重新传参
                            paramMap_temp.put("uid", uidObj.toString());
                            paramMap_temp.put("operatorId", idObj.toString());
                            paramMap_temp.put("redPacketMoney", redPacketTotalObj.toString());
                            paramMap_temp.put("remark", "红包正常发送");
                            paramMap_temp.put("status", "1");
                            WXRedPacketHistoryService.addRedPacketHistory(paramMap_temp);
                        } else {
                            //更新加油站操作的红包状态
                            Map<String, Object> paramMap_temp = Maps.newHashMap();
                            paramMap_temp.clear();      //清空参数，重新传参
                            paramMap_temp.put("id", idObj.toString());
                            paramMap_temp.put("status", "1");
                            WXOilStationOperatorService.updateOilStationOperator(paramMap_temp);
                            //插入红包操作记录
                            paramMap_temp.clear();      //清空参数，重新传参
                            paramMap_temp.put("uid", uidObj.toString());
                            paramMap_temp.put("operatorId", idObj.toString());
                            paramMap_temp.put("redPacketMoney", redPacketTotalObj.toString());
                            paramMap_temp.put("remark", resultMapDTO.getMessage());
                            paramMap_temp.put("status", "0");
                            WXRedPacketHistoryService.addRedPacketHistory(paramMap_temp);
                        }
                    } else {
                        continue;
                    }
                }
            }
        } else {
            logger.info("当前环境不是【预发环境】，不执行任务：每天早上09:00，定时发送红包");
        }
        return ReturnT.SUCCESS;
    }

    /**
     * 每天早上10:00，装一网 爬取站点 以及 发起预约
     */
    @XxlJob("do_SpiderForZhuangYitUtil")
    public ReturnT<String> do_SpiderForZhuangYitUtil(String param) {
        SpiderForZhuangYitUtil.subscribeRenovation("");
        return ReturnT.SUCCESS;
    }

    /**
     * 每月第一天02:30:30，定时更新https协议证书
     */
    @XxlJob("do_RenewCertbot")
    public ReturnT<String> do_RenewCertbot(String param) {
        Map<String, Object> paramMap = Maps.newHashMap();
        try{
            CommandUtil.run("sh /opt/certbot/renewCertbot.sh");
        } catch (Exception e) {
            logger.error(">>>>>>>>>>>>>>>>>>>更新https协议证书时异常<<<<<<<<<<<<<<<<<<<<<<");
            logger.error(">>>>>>>>>>>>>>>>>>>更新https协议证书时异常<<<<<<<<<<<<<<<<<<<<<<");
            logger.error(">>>>>>>>>>>>>>>>>>>更新https协议证书时异常<<<<<<<<<<<<<<<<<<<<<<");
            logger.error("更新https协议证书时异常，e :", e);
            logger.error(">>>>>>>>>>>>>>>>>>>更新https协议证书时异常<<<<<<<<<<<<<<<<<<<<<<");
            logger.error(">>>>>>>>>>>>>>>>>>>更新https协议证书时异常<<<<<<<<<<<<<<<<<<<<<<");
            logger.error(">>>>>>>>>>>>>>>>>>>更新https协议证书时异常<<<<<<<<<<<<<<<<<<<<<<");
        }
        return ReturnT.SUCCESS;
    }

    /**
     * 每天上午05:00，mysql数据库备份
     */
    @XxlJob("do_MysqlDataBak")
    public ReturnT<String> do_MysqlDataBak(String param) {
        Map<String, Object> paramMap = Maps.newHashMap();
        try{
            CommandUtil.run("sh /opt/resourceOfOilStationMap/webapp/mysqlDataBak/mysqldump.sh");
        } catch (Exception e) {
            logger.error(">>>>>>>>>>>>>>>>>>>mysql数据库备份异常<<<<<<<<<<<<<<<<<<<<<<");
            logger.error(">>>>>>>>>>>>>>>>>>>mysql数据库备份异常<<<<<<<<<<<<<<<<<<<<<<");
            logger.error(">>>>>>>>>>>>>>>>>>>mysql数据库备份异常<<<<<<<<<<<<<<<<<<<<<<");
            logger.error("mysql数据库备份异常，e :", e);
            logger.error(">>>>>>>>>>>>>>>>>>>mysql数据库备份异常<<<<<<<<<<<<<<<<<<<<<<");
            logger.error(">>>>>>>>>>>>>>>>>>>mysql数据库备份异常<<<<<<<<<<<<<<<<<<<<<<");
            logger.error(">>>>>>>>>>>>>>>>>>>mysql数据库备份异常<<<<<<<<<<<<<<<<<<<<<<");
        }
        return ReturnT.SUCCESS;
    }

    /**
     * 每天下午17:00，定时油价资讯通知粉丝
     */
    @XxlJob("do_DailyMessageSend")
    public ReturnT<String> do_DailyMessageSend(String param) {
        Map<String, Object> paramMap = Maps.newHashMap();
        new Thread(){
            public void run(){
                try {
                    wxMessageService.dailyMessageSend(paramMap);
                } catch (Exception e) {
                    logger.error(">>>>>>>>>>>>>>>>>>>发送油价资讯通知时异常<<<<<<<<<<<<<<<<<<<<<<");
                    logger.error(">>>>>>>>>>>>>>>>>>>发送油价资讯通知时异常<<<<<<<<<<<<<<<<<<<<<<");
                    logger.error(">>>>>>>>>>>>>>>>>>>发送油价资讯通知时异常<<<<<<<<<<<<<<<<<<<<<<");
                    logger.error("发送油价资讯通知时异常，e :", e);
                    logger.error(">>>>>>>>>>>>>>>>>>>发送油价资讯通知时异常<<<<<<<<<<<<<<<<<<<<<<");
                    logger.error(">>>>>>>>>>>>>>>>>>>发送油价资讯通知时异常<<<<<<<<<<<<<<<<<<<<<<");
                    logger.error(">>>>>>>>>>>>>>>>>>>发送油价资讯通知时异常<<<<<<<<<<<<<<<<<<<<<<");
                }
            }
        }.start();
        return ReturnT.SUCCESS;
    }

    /**
     * 每天23:00，定时更新全国油价
     */
    @XxlJob("do_getOilPriceFromOilUsdCnyCom")
    public ReturnT<String> do_getOilPriceFromOilUsdCnyCom(String param) {
        Map<String, Object> paramMap = Maps.newHashMap();
        try {
            LonLatUtil.getOilPriceFromOilUsdCnyCom(paramMap);
        } catch (Exception e) {
            logger.error(">>>>>>>>>>>>>>>>>>>定时更新全国油价时异常<<<<<<<<<<<<<<<<<<<<<<");
            logger.error(">>>>>>>>>>>>>>>>>>>定时更新全国油价时异常<<<<<<<<<<<<<<<<<<<<<<");
            logger.error(">>>>>>>>>>>>>>>>>>>定时更新全国油价时异常<<<<<<<<<<<<<<<<<<<<<<");
            logger.error("定时更新全国油价时异常，e :", e);
            logger.error(">>>>>>>>>>>>>>>>>>>定时更新全国油价时异常<<<<<<<<<<<<<<<<<<<<<<");
            logger.error(">>>>>>>>>>>>>>>>>>>定时更新全国油价时异常<<<<<<<<<<<<<<<<<<<<<<");
            logger.error(">>>>>>>>>>>>>>>>>>>定时更新全国油价时异常<<<<<<<<<<<<<<<<<<<<<<");
        }
        return ReturnT.SUCCESS;
    }

    /**
     * 每天22:00，执行任务，定时获取最新的全国加油站数据
     * 通过腾讯地图获取所有城市的加油站
     */
    @XxlJob("do_getOilStationByBaiduMap")
    public ReturnT<String> do_getOilStationByBaiduMap(String param) {
        Map<String, Object> paramMap = Maps.newHashMap();
        try {
            //从百度地图获取
            //暂时停止从百度地图获取数据，因为百度地图的坐标与腾讯地图的坐标存在不兼容性
            //WXOilStationService.addOrUpdateOilStationByBaiduMap(paramMap);
        } catch (Exception e) {
            logger.error(">>>>>>>>>>>>>>>>>>>定时获取最新的全国加油站数据<<<<<<<<<<<<<<<<<<<<<<");
            logger.error(">>>>>>>>>>>>>>>>>>>定时获取最新的全国加油站数据<<<<<<<<<<<<<<<<<<<<<<");
            logger.error(">>>>>>>>>>>>>>>>>>>定时获取最新的全国加油站数据<<<<<<<<<<<<<<<<<<<<<<");
            logger.error("定时获取最新的全国加油站数据异常，e :", e);
            logger.error(">>>>>>>>>>>>>>>>>>>定时获取最新的全国加油站数据<<<<<<<<<<<<<<<<<<<<<<");
            logger.error(">>>>>>>>>>>>>>>>>>>定时获取最新的全国加油站数据<<<<<<<<<<<<<<<<<<<<<<");
            logger.error(">>>>>>>>>>>>>>>>>>>定时获取最新的全国加油站数据<<<<<<<<<<<<<<<<<<<<<<");
        }
        return ReturnT.SUCCESS;
    }

    /**
     * 每周日下午20点30分45秒执行任务，定时获取最新的全国加油站数据
     * 通过腾讯地图获取所有城市的加油站
     */
    @XxlJob("do_getOilStationByTencetMap")
    public ReturnT<String> do_getOilStationByTencetMap(String param) {
        //当获取当天20点时间
        Calendar calendar_20 = Calendar.getInstance();
        calendar_20.setTime(new Date());
        calendar_20.set(Calendar.HOUR_OF_DAY, 20);
        calendar_20.set(Calendar.MINUTE, 0);
        calendar_20.set(Calendar.SECOND, 0);
        Date calendar_20_date = calendar_20.getTime();
        //获取当前时间
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.info("当获取当天【20点】时间 : " + sdf.format(calendar_20_date));
        logger.info("当获取当天【当前】时间 : " + sdf.format(currentDate));
        if (currentDate.after(calendar_20_date)) {
            Map<String, Object> paramMap = Maps.newHashMap();
            WXOilStationService.addOrUpdateOilStationByTencetMap(paramMap);
        } else {
            logger.info("每周日下午20点才执行任务【定时获取最新的全国加油站数据】");
        }
        return ReturnT.SUCCESS;
    }

}

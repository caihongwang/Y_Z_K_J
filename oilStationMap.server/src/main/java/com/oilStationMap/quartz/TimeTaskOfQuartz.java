package com.oilStationMap.quartz;

import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.BoolDTO;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.service.*;
import com.oilStationMap.utils.CommandUtil;
import com.oilStationMap.utils.LonLatUtil;
import com.oilStationMap.utils.TimestampUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.http.*;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 定时任务
 */
@Component
@Lazy(false)
public class TimeTaskOfQuartz {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TimeTaskOfQuartz.class);

    //使用环境
    @Value("${useEnvironmental}")
    private String useEnvironmental;

    @Autowired
    private WX_DicService WXDicService;

    @Autowired
    private WX_CommonService wxCommonService;

    @Autowired
    private WX_CommentsService WXCommentsService;

    @Autowired
    private WX_OilStationService WXOilStationService;

    @Autowired
    private WX_MessageService wxMessageService;

    @Autowired
    private WX_RedPacketService wxRedPacketService;

    @Autowired
    private WX_OilStationOperatorService WXOilStationOperatorService;

    @Autowired
    private WX_RedPacketHistoryService WXRedPacketHistoryService;

    /**
     * 每天早上09:00，定时发送红包
     * 对小程序(油价地图)上操作【添加油站】和【纠正油价】的用户直接发送红包
     */
//    @Scheduled(cron = "0 0 9 * * ?")
    public void do_SendRedPacket_For_OilStationMap() {
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
    }

//    /**
//     * 每天早上09:00，装一网 爬取站点 以及 发起预约
//     */
//    @Scheduled(cron = "0 0 10 * * ?")
//    public void do_SpiderForZhuangYitUtil_For_OilStationMap() {
//        SpiderForZhuangYitUtil.subscribeRenovation("");
//    }

    /**
     * 每月第一天02:30:30，定时更新https协议证书
     */
    @Scheduled(cron = "30 30 2 1 * ?")
    public void do_renewCertbot_For_OilStationMap() {
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
    }

    /**
     * 每天上午05:00，mysql数据库备份异常
     */
    @Scheduled(cron = "0 0 5 * * ?")
    public void do_mysqlDataBak_For_OilStationMap() {
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
    }

    /**
     * 每天下午17:00，定时油价资讯通知
     */
    @Scheduled(cron = "0 0 17 * * ?")
    public void do_OilPrizeMessage_For_OilStationMap() {
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
    }

    /**
     * 每天23:00，定时更新全国油价
     */
    @Scheduled(cron = "0 0 23 * * ?")
    public void do_getOilPriceFromOilUsdCnyCom_For_OilStationMap() {
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
    }

    /**
     * 每天22:00，执行任务，从百度地图获取定时获取最新的全国加油站数据
     */
    @Scheduled(cron = "0 0 22 * * ?")
    public void do_getOilStationByBaiduMap_For_OilStationMap() {
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
    }

    /**
     * 每周日下午20点30分45秒执行任务，定时获取最新的全国加油站数据
     * 通过腾讯地图获取所有城市的加油站
     */
    @Scheduled(cron = "45 30 20 ? * SUN")
    public void do_getOilStationByTencetMap_For_OilStationMap() {
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
        BoolDTO boolDTO = new BoolDTO();
        boolDTO.setCode(OilStationMapCode.SUCCESS.getNo());
        boolDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
    }

}

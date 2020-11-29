package com.oilStationMap.quartz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dao.XXL_JobInfoDao;
import com.oilStationMap.dto.BoolDTO;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.service.*;
import com.oilStationMap.utils.CommandUtil;
import com.oilStationMap.utils.HttpsUtil;
import com.oilStationMap.utils.LonLatUtil;
import com.oilStationMap.utils.TimestampUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 定时任务
 *
 * @Scheduled下次任务执行开始将在本次任务执行完毕后才开始
 */
@Component
@Lazy(false)
public class TimeTaskOfQuartz {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TimeTaskOfQuartz.class);

    //使用环境
    @Value("${spring.profiles.active}")
    private String useEnvironmental;

    @Autowired
    private WX_DicService wxDicService;

    @Autowired
    private XXL_JobInfoDao xxlJobInfoDao;

    @Autowired
    private WX_SpiderService wxSpiderService;

    private Date currentDate = new Date();

    /**
     * 每天小时第1分钟执行一次
     * 发送朋友圈，包括 文字朋友圈、图片朋友圈、文章朋友圈、添加群成员为好友的V群
     */
//    @Scheduled(cron = "0 */1 * * * ?")
    @Scheduled(cron = "0 01 */1 * * ?")
    public void do_sendFriendCircle_and_shareArticleToFriendCircle_and_addGroupMembersAsFriends() {
        if (!new SimpleDateFormat("yyyy-MM-dd HH").format(currentDate).equals(new SimpleDateFormat("yyyy-MM-dd HH").format(new Date()))) {
            currentDate = new Date();
        }
        if ("develop".equals(useEnvironmental)) {
            Map<String, Object> paramMap = Maps.newHashMap();
            List<String> nickNameList = Lists.newArrayList();
            paramMap.put("currentDate", currentDate);
//            try {
//                paramMap.clear();
//                nickNameList.clear();
//                paramMap.put("start", 0);
//                paramMap.put("size", 10);
//                paramMap.put("id", "13");       // jobDesc --->>> 发布图片/文字到朋友圈
//                List<Map<String, Object>> list = xxlJobInfoDao.getSimpleJobInfoByCondition(paramMap);
//                if (list != null && list.size() > 0) {
//                    Map<String, Object> sendFriendCircleJobInfoMap = list.get(0);
//                    String nickNameListStr = sendFriendCircleJobInfoMap.get("executorParam") != null ? sendFriendCircleJobInfoMap.get("executorParam").toString() : "";
//                    paramMap.clear();
//                    nickNameList = JSONObject.parseObject(nickNameListStr, List.class);
//                    paramMap.put("nickNameListStr", nickNameListStr);
//                    wxSpiderService.sendFriendCircle(paramMap);
//                } else {
//                    throw new Exception();
//                }
//            } catch (Exception e) {
//                logger.error("在hanlder中启动appium,自动化发送微信朋友圈-sendFriendCircle is error, 即将通过数据库获取数据发送朋友圈 paramMap : " + paramMap);
//                try{
//                    //直接从现有的数据库中获取数据启动-发布朋友圈
//                    paramMap.clear();
//                    nickNameList.clear();
//                    paramMap.put("dicType", "sendFriendCircle");
//                    ResultDTO resultDTO = wxDicService.getSimpleDicByCondition(paramMap);
//                    if(resultDTO != null && resultDTO.getResultList() != null && resultDTO.getResultList().size() > 0){
//                        for(Map<String, String> sendFriendCircleMap : resultDTO.getResultList()){
//                            nickNameList.add(sendFriendCircleMap.get("dicCode"));
//                        }
//                    }
//                    paramMap.clear();
//                    paramMap.put("nickNameListStr", JSONObject.toJSONString(nickNameList));
//                    wxSpiderService.sendFriendCircle(paramMap);
//                } catch (Exception eee) {
//                    eee.printStackTrace();
//                }
//            }
//            try {
//                paramMap.clear();
//                nickNameList.clear();
//                paramMap.put("start", 0);
//                paramMap.put("size", 10);
//                paramMap.put("id", "14");       // jobDesc --->>> 分享文章l链接到朋友圈
//                List<Map<String, Object>> list = xxlJobInfoDao.getSimpleJobInfoByCondition(paramMap);
//                if (list != null && list.size() > 0) {
//                    Map<String, Object> sendFriendCircleJobInfoMap = list.get(0);
//                    String nickNameListStr = sendFriendCircleJobInfoMap.get("executorParam") != null ? sendFriendCircleJobInfoMap.get("executorParam").toString() : "";
//                    paramMap.clear();
//                    nickNameList = JSONObject.parseObject(nickNameListStr, List.class);
//                    paramMap.put("nickNameListStr", nickNameListStr);
//                    wxSpiderService.shareArticleToFriendCircle(paramMap);
//                } else {
//                    throw new Exception();
//                }
//            } catch (Exception e) {
//                logger.error("在hanlder中启动appium,分享微信文章到微信朋友圈-shareArticleToFriendCircle is error, 即将通过数据库获取数据分享微信文章到微信朋友圈 paramMap : " + paramMap);
//                paramMap.clear();
//                nickNameList.clear();
//                paramMap.put("dicType", "shareArticleToFriendCircle");
//                ResultDTO resultDTO = wxDicService.getSimpleDicByCondition(paramMap);
//                if(resultDTO != null && resultDTO.getResultList() != null && resultDTO.getResultList().size() > 0){
//                    for(Map<String, String> sendFriendCircleMap : resultDTO.getResultList()){
//                        nickNameList.add(sendFriendCircleMap.get("dicCode"));
//                    }
//                }
//                paramMap.clear();
//                paramMap.put("nickNameListStr", JSONObject.toJSONString(nickNameList));
//                wxSpiderService.shareArticleToFriendCircle(paramMap);
//            }
            try {
                paramMap.clear();
                nickNameList.clear();
                paramMap.put("start", 0);
                paramMap.put("size", 10);
                paramMap.put("id", "15");       // jobDesc --->>> 添加群成员为好友的V群
                List<Map<String, Object>> list = xxlJobInfoDao.getSimpleJobInfoByCondition(paramMap);
                if (list != null && list.size() > 0) {
                    Map<String, Object> addGroupMembersAsFriendsMap = list.get(0);
                    String nickNameListStr = addGroupMembersAsFriendsMap.get("executorParam") != null ? addGroupMembersAsFriendsMap.get("executorParam").toString() : "";
                    paramMap.clear();
                    nickNameList = JSONObject.parseObject(nickNameListStr, List.class);
                    paramMap.put("nickNameListStr", nickNameListStr);
                    wxSpiderService.addGroupMembersAsFriends(paramMap);
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                logger.error("在hanlder中启动appium,添加群成员为好友的V群-addGroupMembersAsFriends is error, 即将通过数据库添加群成员为好友的V群 paramMap : " + JSON.toJSONString(paramMap));
                paramMap.clear();
                nickNameList.clear();
                paramMap.put("dicType", "addGroupMembersAsFriends");
                ResultDTO resultDTO = wxDicService.getSimpleDicByCondition(paramMap);
                if (resultDTO != null && resultDTO.getResultList() != null && resultDTO.getResultList().size() > 0) {
                    for (Map<String, String> addGroupMembersAsFriendsMap : resultDTO.getResultList()) {
                        nickNameList.add(addGroupMembersAsFriendsMap.get("dicCode"));
                    }
                }
                paramMap.clear();
//                List<String> currentDateList = Lists.newArrayList();
//                currentDateList.add("2020-10-25 20");    //华为 Mate 8 _ 10
//                currentDateList.add("2020-10-25 19");    //华为 Mate 8 _ 9
//                currentDateList.add("2020-10-25 18");    //华为 Mate 8海外版 _ 1
//                currentDateList.add("2020-10-25 17");    //华为 Mate 8 _ 7
//                currentDateList.add("2020-10-25 16");    //华为 Mate 8 _ 6
//                currentDateList.add("2020-10-25 15");    //华为 Mate 8 _ 5
//                currentDateList.add("2020-10-25 14");    //华为 Mate 7 _ 4
//                currentDateList.add("2020-10-25 13");    //华为 Mate 8 _ 3
//                currentDateList.add("2020-10-25 12");    //华为 Mate 8 _ 2
//                currentDateList.add("2020-10-25 11");    //华为 Mate 8 _ 1
//                currentDateList.add("2020-10-25 21");    //小米 Max 3
//                System.out.println(JSON.toJSONString(currentDateList));
//                String currentDateListStr =
//                        "[\n" +
//                        "    \"2020-10-25 20\"," +
//                        "    \"2020-10-25 19\"," +
//                        "    \"2020-10-25 18\"," +
//                        "    \"2020-10-25 17\"," +
//                        "    \"2020-10-25 16\"," +
//                        "    \"2020-10-25 15\"," +
//                        "    \"2020-10-25 14\"," +
//                        "    \"2020-10-25 13\"," +
//                        "    \"2020-10-25 12\"," +
//                        "    \"2020-10-25 11\"," +
//                        "    \"2020-10-25 21\"" +
//                        "]";
//                paramMap.put("currentDateListStr", currentDateListStr);
//                paramMap.put("nickNameListStr", JSONObject.toJSONString(nickNameList));
//                wxSpiderService.addGroupMembersAsFriends(paramMap);

                paramMap.put("nickNameListStr", JSONObject.toJSONString(nickNameList));
                wxSpiderService.addGroupMembersAsFriends(paramMap);
            }
        }
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.print("当前时间：" + new SimpleDateFormat("yyyy-MM-dd HH").format(currentDate) + " , ");
        Calendar c = new GregorianCalendar();
        c.setTime(currentDate);
        c.add(Calendar.HOUR, 1);
        this.currentDate = c.getTime();
        System.out.println("下一次时间：" + new SimpleDateFormat("yyyy-MM-dd HH").format(currentDate));
        System.out.println();
        System.out.println();
        System.out.println();
    }

    /**
     * 每天4个小时检测一次，注：域名已使用花生壳域名进行解决
     * 检测域名是否可以访问
     */
    @Scheduled(cron = "0 0 */4 * * ?")
    public void do_checkDomain() {
//        logger.info("每天早上07:00 , 获取公网IP...");
//        //方式1
//        if("prepub".equals(useEnvironmental)){
//            String publicIp = "公网IP获取失败";
//            try{
//                String url = "https://www.ip.cn";
//                Document ipDoc = null;
//                ipDoc = Jsoup.connect(url)
//                        .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36")
//                        .timeout(15000).get();
//                String ipHtml = ipDoc.html();
//                String[] ipHtmlArr = ipHtml.split("您现在的 IP：<code>");
//                if(ipHtmlArr.length >= 2){
//                    String tempStr = ipHtmlArr[1];
//                    String[] tempStrArr = tempStr.split("</code></p><p>所在地理位置");
//                    publicIp = tempStrArr[0];
//                    logger.info("公网IP = " + publicIp);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            //建议使用http协议访问阿里云，通过阿里元来完成此操作.
//            HttpsUtil httpsUtil = new HttpsUtil();
//            Map<String, String> exceptionDevicesParamMap = Maps.newHashMap();
//            exceptionDevicesParamMap.put("publicIp", publicIp);
//            String exceptionDevicesNotifyUrl = "https://www.yzkj.store/oilStationMap/wxMessage/exceptionDomainMessageSend";
//            String resultJson = httpsUtil.post(exceptionDevicesNotifyUrl, exceptionDevicesParamMap);
//        }
//        //方式2
//        if("prepub".equals(useEnvironmental)){
//            Integer timeOutMillSeconds = 30000;     //超时时间30秒
//            String urlString = "http://www.yzkj.store:3380/owncloud";
//            long lo = System.currentTimeMillis();
//            URL url;
//            try {
//                url = new URL(urlString);
//                URLConnection co =  url.openConnection();
//                co.setConnectTimeout(timeOutMillSeconds);
//                co.connect();
//                logger.info(urlString + " , 连接可用");
//            } catch (Exception e2) {
//                logger.info("路由器的公网IP地址已经发生变化，即将微信模板消息进行通知. e : ", e2);
//                String publicIp = "";   //公网IP
//                InputStream ins = null;
//                try {
//                    url = new URL("http://2000019.ip138.com/");
//                    URLConnection con = url.openConnection();
//                    ins = con.getInputStream();
//                    InputStreamReader isReader = new InputStreamReader(ins, "GB2312");
//                    BufferedReader bReader = new BufferedReader(isReader);
//                    StringBuffer webContent = new StringBuffer();
//                    String str = null;
//                    while ((str = bReader.readLine()) != null) {
//                        webContent.append(str);
//                    }
//                    int start = webContent.indexOf("[") + 1;
//                    int end = webContent.indexOf("]");
//                    publicIp =  webContent.substring(start, end);
//                } catch (Exception e1) {
//                    publicIp = "公网IP查询失败，teamview登陆查询";
//                    e1.printStackTrace();
//                } finally {
//                    if (ins != null) {
//                        try {
//                            ins.close();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    //建议使用http协议访问阿里云，通过阿里元来完成此操作.
//                    HttpsUtil httpsUtil = new HttpsUtil();
//                    Map<String, String> exceptionDevicesParamMap = Maps.newHashMap();
//                    exceptionDevicesParamMap.put("publicIp", publicIp);
//                    String exceptionDevicesNotifyUrl = "https://www.yzkj.store/oilStationMap/wxMessage/exceptionDomainMessageSend";
//                    String resultJson = httpsUtil.post(exceptionDevicesNotifyUrl, exceptionDevicesParamMap);
//                }
//            }
//        }
    }

//    /**
//     * 每天早上09:00，定时发送红包
//     * 对小程序(油价地图)上操作【添加油站】和【纠正油价】的用户直接发送红包
//     */
////    @Scheduled(cron = "0 0 9 * * ?")
//    public void do_SendRedPacket_For_OilStationMap() {
//        if (useEnvironmental != null && "prepub".equals(useEnvironmental)) {
//            //当昨天时间
//            Calendar calendar = Calendar.getInstance();
//            calendar.add(Calendar.DATE, -1);
//            Date yesterDate = calendar.getTime();
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//            String yesterDateStr = formatter.format(yesterDate);
//            //1.整合参数
//            Map<String, Object> paramMap = Maps.newHashMap();
//            paramMap.put("createTime", yesterDateStr);
//            ResultDTO resultDTO = WXOilStationOperatorService.getSimpleOilStationOperatorByCondition(paramMap);
//            if (resultDTO.getResultList() != null && resultDTO.getResultList().size() > 0) {
//                List<Map<String, String>> oilStationOperatorList = resultDTO.getResultList();
//                for (Map<String, String> oilStationOperatorMap : oilStationOperatorList) {
//                    Object idObj = oilStationOperatorMap.get("id");
//                    Object uidObj = oilStationOperatorMap.get("uid");
//                    Object openIdObj = oilStationOperatorMap.get("openId");
//                    Object redPacketTotalObj = oilStationOperatorMap.get("redPacketTotal");
//                    if (uidObj != null && openIdObj != null
//                            && redPacketTotalObj != null && !"".equals(redPacketTotalObj.toString())
//                            && !"0".equals(redPacketTotalObj.toString())) {
//                        //2.整合发送红包的参数
//                        Map<String, Object> redPacketParamMap = Maps.newHashMap();
//                        float redPacketTotalFloat = Float.parseFloat(redPacketTotalObj.toString() != "" ? redPacketTotalObj.toString() : "10");
//                        String redPacketTotal = ((int) (redPacketTotalFloat * 100)) + "";
//                        redPacketParamMap.put("amount", redPacketTotal);
//                        redPacketParamMap.put("openId", openIdObj.toString());
//                        redPacketParamMap.put("reUserName", OilStationMapCode.WX_MINI_PROGRAM_NAME);
//                        redPacketParamMap.put("wxPublicNumGhId", "gh_417c90af3488");
//                        redPacketParamMap.put("desc", OilStationMapCode.WX_MINI_PROGRAM_NAME + "发红包了，快来看看吧.");
//                        ResultMapDTO resultMapDTO = wxRedPacketService.enterprisePayment(redPacketParamMap);
//                        //3.将加油站操作记录表的状态变更为已处理
//                        Map<String, Object> oilStationOperatorMap_updateParam = Maps.newHashMap();
//                        oilStationOperatorMap_updateParam.put("id", oilStationOperatorMap.get("id"));
//                        oilStationOperatorMap_updateParam.put("status", "1");
//                        //4.发送成功，将已发送的红包进行记录，并保存.
//                        if (OilStationMapCode.SUCCESS.getNo() == resultMapDTO.getCode()) {
//                            //更新加油站操作的红包状态
//                            Map<String, Object> paramMap_temp = Maps.newHashMap();
//                            paramMap_temp.clear();      //清空参数，重新传参
//                            paramMap_temp.put("id", idObj.toString());
//                            paramMap_temp.put("status", "2");
//                            WXOilStationOperatorService.updateOilStationOperator(paramMap_temp);
//                            //插入红包操作记录
//                            paramMap_temp.clear();      //清空参数，重新传参
//                            paramMap_temp.put("uid", uidObj.toString());
//                            paramMap_temp.put("operatorId", idObj.toString());
//                            paramMap_temp.put("redPacketMoney", redPacketTotalObj.toString());
//                            paramMap_temp.put("remark", "红包正常发送");
//                            paramMap_temp.put("status", "1");
//                            WXRedPacketHistoryService.addRedPacketHistory(paramMap_temp);
//                        } else {
//                            //更新加油站操作的红包状态
//                            Map<String, Object> paramMap_temp = Maps.newHashMap();
//                            paramMap_temp.clear();      //清空参数，重新传参
//                            paramMap_temp.put("id", idObj.toString());
//                            paramMap_temp.put("status", "1");
//                            WXOilStationOperatorService.updateOilStationOperator(paramMap_temp);
//                            //插入红包操作记录
//                            paramMap_temp.clear();      //清空参数，重新传参
//                            paramMap_temp.put("uid", uidObj.toString());
//                            paramMap_temp.put("operatorId", idObj.toString());
//                            paramMap_temp.put("redPacketMoney", redPacketTotalObj.toString());
//                            paramMap_temp.put("remark", resultMapDTO.getMessage());
//                            paramMap_temp.put("status", "0");
//                            WXRedPacketHistoryService.addRedPacketHistory(paramMap_temp);
//                        }
//                    } else {
//                        continue;
//                    }
//                }
//            }
//        } else {
//            logger.info("当前环境不是【预发环境】，不执行任务：每天早上09:00，定时发送红包");
//        }
//    }
//
////    /**
////     * 每天早上10:00，装一网 爬取站点 以及 发起预约
////     */
////    @Scheduled(cron = "0 0 9 * * ?")
////    public void do_SpiderForZhuangYitUtil_For_OilStationMap() {
////        SpiderForZhuangYitUtil.subscribeRenovation("");
////    }
//
//    /**
//     * 每月第一天02:30:30，定时更新https协议证书
//     */
//    @Scheduled(cron = "30 30 2 1 * ?")
//    public void do_renewCertbot_For_OilStationMap() {
//        Map<String, Object> paramMap = Maps.newHashMap();
//        try{
//            CommandUtil.run("sh /opt/certbot/renewCertbot.sh");
//        } catch (Exception e) {
//            logger.error(">>>>>>>>>>>>>>>>>>>更新https协议证书时异常<<<<<<<<<<<<<<<<<<<<<<");
//            logger.error(">>>>>>>>>>>>>>>>>>>更新https协议证书时异常<<<<<<<<<<<<<<<<<<<<<<");
//            logger.error(">>>>>>>>>>>>>>>>>>>更新https协议证书时异常<<<<<<<<<<<<<<<<<<<<<<");
//            logger.error("更新https协议证书时异常，e :", e);
//            logger.error(">>>>>>>>>>>>>>>>>>>更新https协议证书时异常<<<<<<<<<<<<<<<<<<<<<<");
//            logger.error(">>>>>>>>>>>>>>>>>>>更新https协议证书时异常<<<<<<<<<<<<<<<<<<<<<<");
//            logger.error(">>>>>>>>>>>>>>>>>>>更新https协议证书时异常<<<<<<<<<<<<<<<<<<<<<<");
//        }
//    }
//
//    /**
//     * 每天上午05:00，mysql数据库备份异常
//     */
//    @Scheduled(cron = "0 0 5 * * ?")
//    public void do_mysqlDataBak_For_OilStationMap() {
//        Map<String, Object> paramMap = Maps.newHashMap();
//        try{
//            CommandUtil.run("sh /opt/resourceOfOilStationMap/webapp/mysqlDataBak/mysqldump.sh");
//        } catch (Exception e) {
//            logger.error(">>>>>>>>>>>>>>>>>>>mysql数据库备份异常<<<<<<<<<<<<<<<<<<<<<<");
//            logger.error(">>>>>>>>>>>>>>>>>>>mysql数据库备份异常<<<<<<<<<<<<<<<<<<<<<<");
//            logger.error(">>>>>>>>>>>>>>>>>>>mysql数据库备份异常<<<<<<<<<<<<<<<<<<<<<<");
//            logger.error("mysql数据库备份异常，e :", e);
//            logger.error(">>>>>>>>>>>>>>>>>>>mysql数据库备份异常<<<<<<<<<<<<<<<<<<<<<<");
//            logger.error(">>>>>>>>>>>>>>>>>>>mysql数据库备份异常<<<<<<<<<<<<<<<<<<<<<<");
//            logger.error(">>>>>>>>>>>>>>>>>>>mysql数据库备份异常<<<<<<<<<<<<<<<<<<<<<<");
//        }
//    }
//
//    /**
//     * 每天下午17:00，定时油价资讯通知
//     */
//    @Scheduled(cron = "0 0 17 * * ?")
//    public void do_OilPrizeMessage_For_OilStationMap() {
//        Map<String, Object> paramMap = Maps.newHashMap();
//        new Thread(){
//            public void run(){
//                try {
//                    wxMessageService.dailyMessageSend(paramMap);
//                } catch (Exception e) {
//                    logger.error(">>>>>>>>>>>>>>>>>>>发送油价资讯通知时异常<<<<<<<<<<<<<<<<<<<<<<");
//                    logger.error(">>>>>>>>>>>>>>>>>>>发送油价资讯通知时异常<<<<<<<<<<<<<<<<<<<<<<");
//                    logger.error(">>>>>>>>>>>>>>>>>>>发送油价资讯通知时异常<<<<<<<<<<<<<<<<<<<<<<");
//                    logger.error("发送油价资讯通知时异常，e :", e);
//                    logger.error(">>>>>>>>>>>>>>>>>>>发送油价资讯通知时异常<<<<<<<<<<<<<<<<<<<<<<");
//                    logger.error(">>>>>>>>>>>>>>>>>>>发送油价资讯通知时异常<<<<<<<<<<<<<<<<<<<<<<");
//                    logger.error(">>>>>>>>>>>>>>>>>>>发送油价资讯通知时异常<<<<<<<<<<<<<<<<<<<<<<");
//                }
//            }
//        }.start();
//    }
//
//    /**
//     * 每天23:00，定时更新全国油价
//     */
//    @Scheduled(cron = "0 0 23 * * ?")
//    public void do_getOilPriceFromOilUsdCnyCom_For_OilStationMap() {
//        Map<String, Object> paramMap = Maps.newHashMap();
//        try {
//            LonLatUtil.getOilPriceFromOilUsdCnyCom(paramMap);
//        } catch (Exception e) {
//            logger.error(">>>>>>>>>>>>>>>>>>>定时更新全国油价时异常<<<<<<<<<<<<<<<<<<<<<<");
//            logger.error(">>>>>>>>>>>>>>>>>>>定时更新全国油价时异常<<<<<<<<<<<<<<<<<<<<<<");
//            logger.error(">>>>>>>>>>>>>>>>>>>定时更新全国油价时异常<<<<<<<<<<<<<<<<<<<<<<");
//            logger.error("定时更新全国油价时异常，e :", e);
//            logger.error(">>>>>>>>>>>>>>>>>>>定时更新全国油价时异常<<<<<<<<<<<<<<<<<<<<<<");
//            logger.error(">>>>>>>>>>>>>>>>>>>定时更新全国油价时异常<<<<<<<<<<<<<<<<<<<<<<");
//            logger.error(">>>>>>>>>>>>>>>>>>>定时更新全国油价时异常<<<<<<<<<<<<<<<<<<<<<<");
//        }
//    }
//
//    /**
//     * 每天22:00，执行任务，从百度地图获取定时获取最新的全国加油站数据
//     */
//    @Scheduled(cron = "0 0 22 * * ?")
//    public void do_getOilStationByBaiduMap_For_OilStationMap() {
//        Map<String, Object> paramMap = Maps.newHashMap();
//        try {
//            //从百度地图获取
//            //暂时停止从百度地图获取数据，因为百度地图的坐标与腾讯地图的坐标存在不兼容性
//            //WXOilStationService.addOrUpdateOilStationByBaiduMap(paramMap);
//        } catch (Exception e) {
//            logger.error(">>>>>>>>>>>>>>>>>>>定时获取最新的全国加油站数据<<<<<<<<<<<<<<<<<<<<<<");
//            logger.error(">>>>>>>>>>>>>>>>>>>定时获取最新的全国加油站数据<<<<<<<<<<<<<<<<<<<<<<");
//            logger.error(">>>>>>>>>>>>>>>>>>>定时获取最新的全国加油站数据<<<<<<<<<<<<<<<<<<<<<<");
//            logger.error("定时获取最新的全国加油站数据异常，e :", e);
//            logger.error(">>>>>>>>>>>>>>>>>>>定时获取最新的全国加油站数据<<<<<<<<<<<<<<<<<<<<<<");
//            logger.error(">>>>>>>>>>>>>>>>>>>定时获取最新的全国加油站数据<<<<<<<<<<<<<<<<<<<<<<");
//            logger.error(">>>>>>>>>>>>>>>>>>>定时获取最新的全国加油站数据<<<<<<<<<<<<<<<<<<<<<<");
//        }
//    }
//
//    /**
//     * 每周日下午20点30分45秒执行任务，定时获取最新的全国加油站数据
//     * 通过腾讯地图获取所有城市的加油站
//     */
//    @Scheduled(cron = "45 30 20 ? * SUN")
//    public void do_getOilStationByTencetMap_For_OilStationMap() {
//        //当获取当天20点时间
//        Calendar calendar_20 = Calendar.getInstance();
//        calendar_20.setTime(new Date());
//        calendar_20.set(Calendar.HOUR_OF_DAY, 20);
//        calendar_20.set(Calendar.MINUTE, 0);
//        calendar_20.set(Calendar.SECOND, 0);
//        Date calendar_20_date = calendar_20.getTime();
//        //获取当前时间
//        Date currentDate = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        logger.info("当获取当天【20点】时间 : " + sdf.format(calendar_20_date));
//        logger.info("当获取当天【当前】时间 : " + sdf.format(currentDate));
//        if (currentDate.after(calendar_20_date)) {
//            Map<String, Object> paramMap = Maps.newHashMap();
//            WXOilStationService.addOrUpdateOilStationByTencetMap(paramMap);
//        } else {
//            logger.info("每周日下午20点才执行任务【定时获取最新的全国加油站数据】");
//        }
//        BoolDTO boolDTO = new BoolDTO();
//        boolDTO.setCode(OilStationMapCode.SUCCESS.getNo());
//        boolDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
//    }

}


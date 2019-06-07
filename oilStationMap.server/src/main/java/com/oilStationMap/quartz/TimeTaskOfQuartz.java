package com.oilStationMap.quartz;

import com.alibaba.fastjson.JSONObject;
import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.BoolDTO;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.service.*;
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

    /**
     * 每月第一天02:30:30，定时更新https协议证书
     */
    @Scheduled(cron = "30 30 2 1 * ?")
    public void do_renewCertbot_For_OilStationMap() {
        Map<String, Object> paramMap = Maps.newHashMap();
        try{
            CommandUtil.run("/opt/certbot/renewCertbot.sh");

            //向 管理员汇报 已更新油价
            paramMap.clear();//清空参数，重新准备参数
            Map<String, Object> dataMap = Maps.newHashMap();

            Map<String, Object> firstMap = Maps.newHashMap();
            firstMap.put("value", "定时每月第一天02:30:30更新https协议证书");
            firstMap.put("color", "#0017F5");
            dataMap.put("first", firstMap);

            //获取当前时间
            Date currentDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Map<String, Object> keyword1Map = Maps.newHashMap();
            keyword1Map.put("value", sdf.format(currentDate));
            keyword1Map.put("color", "#0017F5");
            dataMap.put("keyword1", keyword1Map);

            Map<String, Object> keyword2Map = Maps.newHashMap();
            keyword2Map.put("value", "【油价地图】");
            keyword2Map.put("color", "#0017F5");
            dataMap.put("keyword2", keyword2Map);

            Map<String, Object> keyword3Map = Maps.newHashMap();
            keyword3Map.put("value", "只为专注油价资讯，为车主省钱.");
            keyword3Map.put("color", "#0017F5");
            dataMap.put("keyword3", keyword3Map);

            Map<String, Object> remarkMap = Maps.newHashMap();
            remarkMap.put("value", "定时每月第一天02:30:30更新https协议证书【成功】");
            remarkMap.put("color", "#0017F5");
            dataMap.put("remark", remarkMap);

            paramMap.put("data", JSONObject.toJSONString(dataMap));
            paramMap.put("url", "https://www.91caihongwang.com/oilStationMap");

            paramMap.put("openId", "oJcI1wt-ibRdgri1y8qKYCRQaq8g");
            paramMap.put("template_id", "v4tKZ7kAwI6VrXzAJyAxi5slILLRBibZg-G3kRwNIKQ");
            wxCommonService.sendTemplateMessageForWxPublicNumber(paramMap);
        } catch (Exception e) {
            logger.error(">>>>>>>>>>>>>>>>>>>更新https协议证书时异常<<<<<<<<<<<<<<<<<<<<<<");
            logger.error(">>>>>>>>>>>>>>>>>>>更新https协议证书时异常<<<<<<<<<<<<<<<<<<<<<<");
            logger.error(">>>>>>>>>>>>>>>>>>>更新https协议证书时异常<<<<<<<<<<<<<<<<<<<<<<");
            logger.error("更新https协议证书时异常，e :", e);
            logger.error(">>>>>>>>>>>>>>>>>>>更新https协议证书时异常<<<<<<<<<<<<<<<<<<<<<<");
            logger.error(">>>>>>>>>>>>>>>>>>>更新https协议证书时异常<<<<<<<<<<<<<<<<<<<<<<");
            logger.error(">>>>>>>>>>>>>>>>>>>更新https协议证书时异常<<<<<<<<<<<<<<<<<<<<<<");




            //向 管理员汇报 已更新油价
            paramMap.clear();//清空参数，重新准备参数
            Map<String, Object> dataMap = Maps.newHashMap();

            Map<String, Object> firstMap = Maps.newHashMap();
            firstMap.put("value", "定时每月第一天02:30:30更新https协议证书");
            firstMap.put("color", "#0017F5");
            dataMap.put("first", firstMap);

            //获取当前时间
            Date currentDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Map<String, Object> keyword1Map = Maps.newHashMap();
            keyword1Map.put("value", sdf.format(currentDate));
            keyword1Map.put("color", "#0017F5");
            dataMap.put("keyword1", keyword1Map);

            Map<String, Object> keyword2Map = Maps.newHashMap();
            keyword2Map.put("value", "【油价地图】");
            keyword2Map.put("color", "#0017F5");
            dataMap.put("keyword2", keyword2Map);

            Map<String, Object> keyword3Map = Maps.newHashMap();
            keyword3Map.put("value", "只为专注油价资讯，为车主省钱.");
            keyword3Map.put("color", "#0017F5");
            dataMap.put("keyword3", keyword3Map);

            Map<String, Object> remarkMap = Maps.newHashMap();
            remarkMap.put("value", "定时每月第一天02:30:30更新https协议证书【失败】");
            remarkMap.put("color", "#0017F5");
            dataMap.put("remark", remarkMap);

            paramMap.put("data", JSONObject.toJSONString(dataMap));
            paramMap.put("url", "https://www.91caihongwang.com/oilStationMap");

            paramMap.put("openId", "oJcI1wt-ibRdgri1y8qKYCRQaq8g");
            paramMap.put("template_id", "v4tKZ7kAwI6VrXzAJyAxi5slILLRBibZg-G3kRwNIKQ");
        }
    }

    /**
     * 每天下午17:00，定时油价资讯通知
     */
    @Scheduled(cron = "0 0 17 * * ?")
    public void do_OilPrizeMessage_For_OilStationMap() {
        if (useEnvironmental != null && "prepub".equals(useEnvironmental)) {
            Map<String, Object> paramMap = Maps.newHashMap();
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
        } else {
            logger.info("当前环境不是【预发环境】，不执行任务：每天下午17:00，定时油价资讯通知");
        }
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

    /**
     * 每天下午16:00，定时提交企业安全管理平台的隐患排查表单
     * 企业安全管理平台的隐患排查
     */
    @Scheduled(cron = "0 0 16 * * ?")
    public void do_LoginAndOperator_For_EnterpriseSafetyManagementPlatform()     {
        logger.info("【定时任务】企业安全管理平台的隐患排查 当前环境 useEnvironmental = " + useEnvironmental);
        if (useEnvironmental != null && "prepub".equals(useEnvironmental)) {
            try {
                //1.准备参数
                String username = "caizhiwen";          //用户名
                String password = "caizhiwen";          //密码
                String loginUrl = "http://corp.unicom-ptt.com:8/html/user/login/main";          //登录url
                String operatorUrl = "http://corp.unicom-ptt.com:8/html/danger/add3/add3";      //操作url
                //准备 隐患排查 项目名称
                List<String> operatorList = Lists.newArrayList();
                //第一横排
                operatorList.add("资质证照");
                operatorList.add("安全生产管理机构及人员");
                operatorList.add("安全规章制度");
                operatorList.add("安全教育培训");
                operatorList.add("安全投入");
                operatorList.add("相关方管理");
                //第二横排
                operatorList.add("重大危险源管理");
                operatorList.add("个体防护");
                operatorList.add("职业健康");
                operatorList.add("应急管理");
                operatorList.add("隐患排查治理");
                operatorList.add("事故报告、调查和处理");
                //第三横排
                operatorList.add("作业场所");
                //operatorList.add("设备设施");
                operatorList.add("防护、保险、信号等装置设备");
                operatorList.add("原辅物料、产品");
                operatorList.add("安全技能");
                operatorList.add("作业许可");
                //当前天时间
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String currentDateStr = formatter.format(new Date());
                //2.开始模拟登陆并操作
                //准备联网的 httpClient
                RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
                CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
                //准备登录
                List<NameValuePair> valuePairs = new LinkedList<NameValuePair>();
                valuePairs.add(new BasicNameValuePair("username", username));
                valuePairs.add(new BasicNameValuePair("password", password));
                HttpPost postLogin = new HttpPost(loginUrl);
                postLogin.setEntity(new UrlEncodedFormEntity(valuePairs));
                postLogin.setEntity(new UrlEncodedFormEntity(valuePairs));
                HttpResponse loginResponse = httpClient.execute(postLogin);
                StatusLine loginResponseState = loginResponse.getStatusLine();
                logger.info("用户名密码登陆--->>状态:" + ("302".equals(loginResponseState) ? "登录成功" : "登录失败"));
                logger.info(loginResponseState.toString());
                //准备操作
                for (String operatorNameStr : operatorList) {
                    HttpPost postOperator = new HttpPost(operatorUrl);
                    Header[] headers = loginResponse.getHeaders("Set-Cookie");
                    for (int i = 0; i < headers.length; i++) {
                        postOperator.addHeader(headers[i]);
                    }
                    StringBody check_type = new StringBody(operatorNameStr, Charset.forName("utf-8"));
                    StringBody result = new StringBody("1", Charset.forName("utf-8"));
                    StringBody is_update = new StringBody("1", Charset.forName("utf-8"));
                    StringBody check_time = new StringBody(currentDateStr, Charset.forName("utf-8"));
                    StringBody organ_id = new StringBody("0", Charset.forName("utf-8"));
                    StringBody organ_name = new StringBody("企业本级", Charset.forName("utf-8"));
                    StringBody person_id = new StringBody("ee9de767-622d-43c6-9368-76443a049063", Charset.forName("utf-8"));
                    StringBody person_name = new StringBody("蔡红旺", Charset.forName("utf-8"));
                    StringBody addr = new StringBody("大路田坝加油站", Charset.forName("utf-8"));
                    StringBody duty_unit = new StringBody("企业本级", Charset.forName("utf-8"));
                    StringBody workshop = new StringBody("良好，一切正常.", Charset.forName("utf-8"));
                    StringBody factory = new StringBody("良好，一切正常.", Charset.forName("utf-8"));
                    HttpEntity reqEntity = MultipartEntityBuilder.create()
                            .addPart("check_type", check_type)
                            .addPart("result", result)
                            .addPart("is_update", is_update)
                            .addPart("check_time", check_time)
                            .addPart("organ_id", organ_id)
                            .addPart("organ_name", organ_name)
                            .addPart("person_id", person_id)
                            .addPart("person_name", person_name)
                            .addPart("addr", addr)
                            .addPart("duty_unit", duty_unit)
                            .addPart("workshop", workshop)
                            .addPart("factory", factory)
                            .build();
                    postOperator.setEntity(reqEntity);
                    HttpResponse postOperatorResponse = httpClient.execute(postOperator);
                    StatusLine operatorResponseState = postOperatorResponse.getStatusLine();
                    logger.info("用户名密码登陆--->>状态: " + ("302".equals(loginResponseState) ? "登录成功" : "登录失败"));
                    logger.info("操作:【 " + operatorNameStr + " 】--->>状态: " + ("302".equals(loginResponseState) ? "操作成功" : "操作失败"));
                    logger.info(operatorResponseState.toString());
                    //沉睡5秒
                    Thread.sleep(5000);
                }
                logger.info("============================================================");
                logger.info("============================================================");
                logger.info("============================================================");
                logger.info("时间：【 " + currentDateStr + " 】的企业安全管理平台的隐患排查已登记完毕=======");
                logger.info("时间：【 " + currentDateStr + " 】的企业安全管理平台的隐患排查已登记完毕=======");
                logger.info("时间：【 " + currentDateStr + " 】的企业安全管理平台的隐患排查已登记完毕=======");
                logger.info("============================================================");
                logger.info("============================================================");
                logger.info("============================================================");
            } catch (Exception e) {
                Map<String, Object> commentsMap = Maps.newHashMap();
                commentsMap.put("comments", e.getMessage());
                commentsMap.put("uid", "1");
                commentsMap.put("createTime", TimestampUtil.getTimestamp());
                commentsMap.put("updateTime", TimestampUtil.getTimestamp());
                WXCommentsService.addComments(commentsMap);
            }
        } else {
            logger.info("当前环境不是【预发环境】，不执行任务：每天下午16:00，定时提交企业安全管理平台的隐患排查表单");
        }
    }

}

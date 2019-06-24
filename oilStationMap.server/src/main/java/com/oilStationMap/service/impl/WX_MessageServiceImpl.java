package com.oilStationMap.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.service.WX_CommonService;
import com.oilStationMap.service.WX_DicService;
import com.oilStationMap.service.WX_MessageService;
import com.oilStationMap.utils.CustomMessageUtil;
import com.oilStationMap.utils.MediaTypeUtil;
import com.oilStationMap.utils.WX_PublicNumberUtil;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 微信公众号消息service
 */
@Service
@EnableAsync
public class WX_MessageServiceImpl implements WX_MessageService {

    private static final Logger logger = LoggerFactory.getLogger(WX_MessageServiceImpl.class);

    @Autowired
    private WX_DicService wxDicService;

    @Autowired
    private WX_CommonService wxCommonService;

    /**
     * 向微信公众号粉丝群发红包模板消息
     * @param paramMap
     */
    @Async
    @Override
    public ResultMapDTO redActivityMessageSend(Map<String, Object> paramMap) throws Exception {
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> resultMap = Maps.newHashMap();
        logger.info("在【service】中向微信公众号粉丝群发红包模板消息-redActivityMessageSend,请求-paramMap:" + paramMap);
        //1.获取所有的微信公众号账号
        paramMap.clear();
        List<Map<String, String>> customMessageAccountList = Lists.newArrayList();
        paramMap.put("dicType", "customMessageAccount");
        ResultDTO resultDTO = wxDicService.getSimpleDicByCondition(paramMap);
        customMessageAccountList = resultDTO.getResultList();
        if (customMessageAccountList != null && customMessageAccountList.size() > 0) {
            for (int i = 0; i < customMessageAccountList.size(); i++) {
                Map<String, String> customMessageAccountMap = customMessageAccountList.get(i);
                String dicName = customMessageAccountMap.get("dicName");
                if (dicName.contains("公众号")) {
                    String appId = customMessageAccountMap.get("customMessageAccountAppId") != null ? customMessageAccountMap.get("customMessageAccountAppId").toString() : "wxf768b49ad0a4630c";
                    String secret = customMessageAccountMap.get("customMessageAccountSecret") != null ? customMessageAccountMap.get("customMessageAccountSecret").toString() : "a481dd6bc40c9eec3e57293222e8246f";
                    String customMessageAccountName = customMessageAccountMap.get("customMessageAccountName")!=null?customMessageAccountMap.get("customMessageAccountName").toString():"油价地图";
                    if(!"".equals(appId) && !"".equals(secret)){
                        //1.获取所有微信用户openId
                        Map<String, Object> followersMap = WX_PublicNumberUtil.getFollowers(null, appId,  secret);
                        if(followersMap != null && followersMap.size() > 0){
                            JSONObject dataJSONObject = followersMap.get("data")!=null?(JSONObject)followersMap.get("data"):null;
                            if(dataJSONObject != null){
                                String title = "";
                                String digest = "";
                                String appid = "";
                                String pagePath = "";
                                String thumbMediaId = "";
                                JSONArray openIdJSONArray = dataJSONObject.get("openid")!=null?
                                        (JSONArray)dataJSONObject.get("openid"):null;
                                List<String> openIdList = JSONObject.parseObject(openIdJSONArray.toJSONString(), List.class);
                                Map<String, Object> customMessageParamMap = Maps.newHashMap();
                                customMessageParamMap.put("dicType", "wxCustomMessage");
                                customMessageParamMap.put("dicCode", "26");
                                ResultDTO customMessageResultDTO = wxDicService.getSimpleDicByCondition(customMessageParamMap);
                                if(customMessageResultDTO != null && customMessageResultDTO.getResultList() != null
                                        && customMessageResultDTO.getResultList().size() > 0) {
                                    Map<String, String> customMessageMap = customMessageResultDTO.getResultList().get(0);
                                    title = customMessageMap.get("title")!=null?customMessageMap.get("title").toString():"";
                                    digest = customMessageMap.get("digest")!=null?customMessageMap.get("digest").toString():"";
                                    appid = customMessageMap.get("appid")!=null?customMessageMap.get("appid").toString():"";
                                    pagePath = customMessageMap.get("pagePath")!=null?customMessageMap.get("pagePath").toString():"";
                                    thumbMediaId = customMessageMap.get("thumbMediaId")!=null?customMessageMap.get("thumbMediaId").toString():"";
                                } else {
                                    title = "抢红包了,抢红包了,抢红包了,重要的事情说三遍!!!";
                                    digest = "抢红包了!新年送福利，加油无烦扰，秒慢无...";
                                    appid = "wx07cf52be1444e4b7";
                                    pagePath = "pages/other/activity/redActivity/index";
                                    thumbMediaId = "nmOaXf9mW-kNJNTS6gdUSpRrRNcI9yU_TGI7yXpU_UM";
                                }
                                //2.发送模板消息-打开小程序
//                                openIdList.clear();  //模拟只向管理员发送消息
//                                openIdList.add("oJcI1wt-ibRdgri1y8qKYCRQaq8g");     //油价地图的openId
//                                openIdList.add("ovrxT5trVCVftVpNznW7Rz-oXP5k");     //智恵油站的openId
                                for(String openId : openIdList) {
                                    paramMap.clear();//清空参数，重新准备参数
                                    Map<String, Object> dataMap = Maps.newHashMap();

                                    Map<String, Object> firstMap = Maps.newHashMap();
                                    firstMap.put("value", "抢红包了，领红包了，快来拿红包吧...");
                                    firstMap.put("color", "#0017F5");
                                    dataMap.put("first", firstMap);

                                    Map<String, Object> keyword1Map = Maps.newHashMap();
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date currentDate = new Date();
                                    keyword1Map.put("value", sdf.format(currentDate));
                                    keyword1Map.put("color", "#0017F5");
                                    dataMap.put("keyword1", keyword1Map);

                                    Map<String, Object> keyword2Map = Maps.newHashMap();
                                    keyword2Map.put("value", "【"+customMessageAccountName+"】");
                                    keyword2Map.put("color", "#0017F5");
                                    dataMap.put("keyword2", keyword2Map);

                                    Map<String, Object> keyword3Map = Maps.newHashMap();
                                    keyword3Map.put("value", "只为专注油价资讯，为车主省钱.");
                                    keyword3Map.put("color", "#0017F5");
                                    dataMap.put("keyword3", keyword3Map);

                                    Map<String, Object> remarkMap = Maps.newHashMap();
                                    remarkMap.put("value", "抢红包了，领红包了，快来拿红包吧，加油无烦扰，秒慢无.");
                                    remarkMap.put("color", "#0017F5");
                                    dataMap.put("remark", remarkMap);

                                    paramMap.put("data", JSONObject.toJSONString(dataMap));

                                    Map<String, Object> miniprogramMap = Maps.newHashMap();
                                    miniprogramMap.put("appid", "wx07cf52be1444e4b7");
                                    miniprogramMap.put("pagepath", "pages/other/activity/redActivity/index");
                                    paramMap.put("miniprogram", JSONObject.toJSONString(miniprogramMap));

                                    paramMap.put("appId", appId);
                                    paramMap.put("secret", secret);
                                    paramMap.put("openId", openId);
                                    paramMap.put("template_id", "v4tKZ7kAwI6VrXzAJyAxi5slILLRBibZg-G3kRwNIKQ");

                                    Thread.sleep(2000);
                                    logger.info("每个用户之间缓冲两秒进行发送，报料成功通知，当前openId = " + openId);
                                    logger.info("每个用户之间缓冲两秒进行发送，报料成功通知，当前openId = " + openId);
                                    logger.info("每个用户之间缓冲两秒进行发送，报料成功通知，当前openId = " + openId);

                                    wxCommonService.sendTemplateMessageForWxPublicNumber(paramMap);
                                }
                            } else {
                                //获取微信公众所有openId
                                resultMapDTO.setCode(OilStationMapCode.CURRENT_PUBLIC_NUMBER_OPENID_IS_NOT_NULL.getNo());
                                resultMapDTO.setMessage(OilStationMapCode.CURRENT_PUBLIC_NUMBER_OPENID_IS_NOT_NULL.getMessage());
                            }
                        } else {
                            //获取微信公众所有openId
                            resultMapDTO.setCode(OilStationMapCode.CURRENT_PUBLIC_NUMBER_OPENID_IS_NOT_NULL.getNo());
                            resultMapDTO.setMessage(OilStationMapCode.CURRENT_PUBLIC_NUMBER_OPENID_IS_NOT_NULL.getMessage());
                        }
                    } else {
                        resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
                        resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
                    }
                }
            }
        }
        logger.info("在【service】中向微信公众号粉丝群发红包模板消息-redActivityMessageSend,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 根据appId和secret和templateId来确定使用哪个公众号的那个消息模板进行发送 日常报料通知
     * 根据OpenID列表群发【订阅号不可用，服务号认证后可用】
     * @param paramMap
     */
    @Async
    @Override
    public ResultMapDTO dailyMessageSend(Map<String, Object> paramMap) throws Exception {
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> resultMap = Maps.newHashMap();
        logger.info("在service中根据OpenID列表群发-dailyMessageSend,请求-paramMap:" + paramMap);
        //1.获取所有的微信公众号账号
        paramMap.clear();
        List<Map<String, String>> customMessageAccountList = Lists.newArrayList();
        paramMap.put("dicType", "customMessageAccount");
        ResultDTO resultDTO = wxDicService.getSimpleDicByCondition(paramMap);
        customMessageAccountList = resultDTO.getResultList();
        if (customMessageAccountList != null && customMessageAccountList.size() > 0) {
            for (int i = 0; i < customMessageAccountList.size(); i++) {
                Map<String, String> customMessageAccountMap = customMessageAccountList.get(i);
                String dicName = customMessageAccountMap.get("dicName");
                if(dicName.contains("公众号")){
                    String appId = customMessageAccountMap.get("customMessageAccountAppId")!=null?customMessageAccountMap.get("customMessageAccountAppId").toString():"wxf768b49ad0a4630c";
                    String secret = customMessageAccountMap.get("customMessageAccountSecret")!=null?customMessageAccountMap.get("customMessageAccountSecret").toString():"a481dd6bc40c9eec3e57293222e8246f";
                    String customMessageAccountName = customMessageAccountMap.get("customMessageAccountName")!=null?customMessageAccountMap.get("customMessageAccountName").toString():"油价地图";
                    String dailyMessageTemplateId = customMessageAccountMap.get("dailyMessageTemplateId")!=null?customMessageAccountMap.get("dailyMessageTemplateId").toString():"v4tKZ7kAwI6VrXzAJyAxi5slILLRBibZg-G3kRwNIKQ";
                    if(!"".equals(appId) && !"".equals(secret) && !"".equals(dailyMessageTemplateId)){
                        //1.获取所有微信用户openId
                        Map<String, Object> followersMap = WX_PublicNumberUtil.getFollowers(null, appId,  secret);
                        if(followersMap != null && followersMap.size() > 0){
                            JSONObject dataJSONObject = followersMap.get("data")!=null?(JSONObject)followersMap.get("data"):null;
                            if(dataJSONObject != null){
                                JSONArray openIdJSONArray = dataJSONObject.get("openid")!=null?
                                        (JSONArray)dataJSONObject.get("openid"):null;
                                List<String> openIdList = JSONObject.parseObject(openIdJSONArray.toJSONString(), List.class);
                                //2.获取最新的素材
                                List<Map<String, Object>> sourceMaterialList = WX_PublicNumberUtil.batchGetAllMaterial(MediaTypeUtil.NEWS, appId, secret, 0, 20);
                                if(sourceMaterialList.size() > 0){
                                    Map<String, Object> sourceMaterialMap = sourceMaterialList.get(0);      //获取最近发布的一篇文章
                                    String createTime = sourceMaterialMap.get("createTime")!=null?sourceMaterialMap.get("createTime").toString():"";
                                    if(!"".equals(createTime)){
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        Date createDate = sdf.parse(createTime);
                                        Date currentDate = new Date();
                                        //素材的创建时间与当前时间只有1天之隔才允许群发
                                        if((currentDate.getTime() - createDate.getTime()) < 24*60*60*1000){
                                            //3.发送模板消息
//                                            openIdList.clear();  //模拟只向管理员发送消息
//                                            openIdList.add("oJcI1wt-ibRdgri1y8qKYCRQaq8g");     //油价地图的openId
//                                            openIdList.add("ovrxT5trVCVftVpNznW7Rz-oXP5k");     //智恵油站的openId
                                            for(String openId : openIdList) {
                                                paramMap.clear();//清空参数，重新准备参数
                                                Map<String, Object> dataMap = Maps.newHashMap();

                                                Map<String, Object> firstMap = Maps.newHashMap();
                                                firstMap.put("value", sourceMaterialMap.get("title").toString());
                                                firstMap.put("color", "#0017F5");
                                                dataMap.put("first", firstMap);

                                                Map<String, Object> keyword1Map = Maps.newHashMap();
                                                keyword1Map.put("value", sdf.format(currentDate));
                                                keyword1Map.put("color", "#0017F5");
                                                dataMap.put("keyword1", keyword1Map);

                                                Map<String, Object> keyword2Map = Maps.newHashMap();
                                                keyword2Map.put("value", "【"+customMessageAccountName+"】");
                                                keyword2Map.put("color", "#0017F5");
                                                dataMap.put("keyword2", keyword2Map);

                                                Map<String, Object> keyword3Map = Maps.newHashMap();
                                                keyword3Map.put("value", "只为专注油价资讯，为车主省钱.");
                                                keyword3Map.put("color", "#0017F5");
                                                dataMap.put("keyword3", keyword3Map);

                                                Map<String, Object> remarkMap = Maps.newHashMap();
                                                remarkMap.put("value", sourceMaterialMap.get("digest").toString());
                                                remarkMap.put("color", "#0017F5");
                                                dataMap.put("remark", remarkMap);

                                                paramMap.put("data", JSONObject.toJSONString(dataMap));
                                                paramMap.put("url", sourceMaterialMap.get("url").toString());

                                                paramMap.put("appId", appId);
                                                paramMap.put("secret", secret);
                                                paramMap.put("openId", openId);
                                                paramMap.put("template_id", dailyMessageTemplateId);

                                                Thread.sleep(2000);
                                                logger.info("每个用户之间缓冲两秒进行发送，报料成功通知，当前openId = " + openId);

                                                wxCommonService.sendTemplateMessageForWxPublicNumber(paramMap);
                                            }
                                        } else {
                                            //发送模板消息（>>>>>>>>>>>>>>文案待定<<<<<<<<<<<<<<）
                                        }
                                    } else {
                                        //发送模板消息（>>>>>>>>>>>>>>文案待定<<<<<<<<<<<<<<）
                                    }
                                } else {
                                    //获取微信公众所有openId
                                    resultMapDTO.setCode(OilStationMapCode.CURRENT_PUBLIC_NUMBER_OPENID_IS_NOT_NULL.getNo());
                                    resultMapDTO.setMessage(OilStationMapCode.CURRENT_PUBLIC_NUMBER_OPENID_IS_NOT_NULL.getMessage());
                                }
                            }
                        } else {
                            //获取微信公众所有openId
                            resultMapDTO.setCode(OilStationMapCode.CURRENT_PUBLIC_NUMBER_OPENID_IS_NOT_NULL.getNo());
                            resultMapDTO.setMessage(OilStationMapCode.CURRENT_PUBLIC_NUMBER_OPENID_IS_NOT_NULL.getMessage());
                        }
                    } else {
                        resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
                        resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
                    }
                }
            }
        }
        logger.info("在service中根据OpenID列表群发-dailyMessageSend,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 根据OpenID列表群发【抽奖】福利
     * @param paramMap
     */
    @Async
    @Override
    public ResultMapDTO dailyLuckDrawMessageSend(Map<String, Object> paramMap) throws Exception {
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> resultMap = Maps.newHashMap();
        logger.info("在service中根据OpenID列表群发-dailyLuckDrawMessageSend,请求-paramMap:" + paramMap);
        //1.获取所有的微信公众号账号
        paramMap.clear();
        List<Map<String, String>> customMessageAccountList = Lists.newArrayList();
        paramMap.put("dicType", "customMessageAccount");
        ResultDTO resultDTO = wxDicService.getSimpleDicByCondition(paramMap);
        customMessageAccountList = resultDTO.getResultList();
        if (customMessageAccountList != null && customMessageAccountList.size() > 0) {
            for (int i = 0; i < customMessageAccountList.size(); i++) {
                Map<String, String> customMessageAccountMap = customMessageAccountList.get(i);
                String dicName = customMessageAccountMap.get("dicName");
                if (dicName.contains("公众号")) {
                    String appId = customMessageAccountMap.get("customMessageAccountAppId")!=null?customMessageAccountMap.get("customMessageAccountAppId").toString():"wxf768b49ad0a4630c";
                    String secret = customMessageAccountMap.get("customMessageAccountSecret")!=null?customMessageAccountMap.get("customMessageAccountSecret").toString():"a481dd6bc40c9eec3e57293222e8246f";
                    String customMessageAccountName = customMessageAccountMap.get("customMessageAccountName")!=null?customMessageAccountMap.get("customMessageAccountName").toString():"油价地图";
                    String luckDrawMessageTemplateId = customMessageAccountMap.get("luckDrawMessageTemplateId")!=null?customMessageAccountMap.get("luckDrawMessageTemplateId").toString():"PVazdd8e4agtaZGIT-G4erWcWbL1EFolT7kxw7Rcn7w";//逾期应收提醒
                    if(!"".equals(appId) && !"".equals(secret)){
                        //1.获取所有微信用户openId
                        Map<String, Object> followersMap = WX_PublicNumberUtil.getFollowers(null, appId,  secret);
                        if(followersMap != null && followersMap.size() > 0){
                            JSONObject dataJSONObject = followersMap.get("data")!=null?(JSONObject)followersMap.get("data"):null;
                            if(dataJSONObject != null){
                                JSONArray openIdJSONArray = dataJSONObject.get("openid")!=null?
                                        (JSONArray)dataJSONObject.get("openid"):null;
                                List<String> openIdList = JSONObject.parseObject(openIdJSONArray.toJSONString(), List.class);
                                //2.发送抽奖消息消息
//                                openIdList.clear();  //模拟只向管理员发送消息
//                                openIdList.add("oJcI1wt-ibRdgri1y8qKYCRQaq8g");     //油价地图的openId
//                                openIdList.add("ovrxT5trVCVftVpNznW7Rz-oXP5k");     //智恵油站的openId
                                for(String openId : openIdList) {
                                    paramMap.clear();//清空参数，重新准备参数
                                    Map<String, Object> dataMap = Maps.newHashMap();

                                    Map<String, Object> firstMap = Maps.newHashMap();
                                    Map<String, Object> userInfo = WX_PublicNumberUtil.getCgiBinUserInfo(openId, appId, secret);
                                    Random random = new Random();
                                    Integer num = random.nextInt(10000);
                                    firstMap.put("value", userInfo.get("nickname")+"，您是第"+num+"位有逾期应收的抽奖领红包机会，请及时点击查看！");
                                    firstMap.put("color", "#0017F5");
                                    dataMap.put("first", firstMap);

                                    Map<String, Object> keyword1Map = Maps.newHashMap();
                                    keyword1Map.put("value", "【"+customMessageAccountName+"】");
                                    keyword1Map.put("color", "#0017F5");
                                    dataMap.put("keyword1", keyword1Map);

                                    Map<String, Object> keyword2Map = Maps.newHashMap();
                                    keyword2Map.put("value", "5.96元");
                                    keyword2Map.put("color", "#0017F5");
                                    dataMap.put("keyword2", keyword2Map);

                                    Map<String, Object> keyword3Map = Maps.newHashMap();
                                    keyword3Map.put("value", "26天");
                                    keyword3Map.put("color", "#0017F5");
                                    dataMap.put("keyword3", keyword3Map);

                                    Map<String, Object> remarkMap = Maps.newHashMap();
                                    remarkMap.put("value", "感谢您的使用！");
                                    remarkMap.put("color", "#0017F5");
                                    dataMap.put("remark", remarkMap);

                                    paramMap.put("data", JSONObject.toJSONString(dataMap));
                                    paramMap.put("url", "https://engine.seefarger.com/index/activity?appKey=2mdmXx9VAtdWaZbHc9SeiD1DJaT6&adslotId=290262");

                                    paramMap.put("appId", appId);
                                    paramMap.put("secret", secret);
                                    paramMap.put("openId", openId);
                                    paramMap.put("template_id", luckDrawMessageTemplateId);//逾期应收提醒

                                    Thread.sleep(2000);
                                    logger.info("每个用户之间缓冲两秒进行发送，报料成功通知，当前openId = " + openId);

                                    wxCommonService.sendTemplateMessageForWxPublicNumber(paramMap);
                                }
                            } else {
                                //获取微信公众所有openId
                                resultMapDTO.setCode(OilStationMapCode.CURRENT_PUBLIC_NUMBER_OPENID_IS_NOT_NULL.getNo());
                                resultMapDTO.setMessage(OilStationMapCode.CURRENT_PUBLIC_NUMBER_OPENID_IS_NOT_NULL.getMessage());
                            }
                        } else {
                            //获取微信公众所有openId
                            resultMapDTO.setCode(OilStationMapCode.CURRENT_PUBLIC_NUMBER_OPENID_IS_NOT_NULL.getNo());
                            resultMapDTO.setMessage(OilStationMapCode.CURRENT_PUBLIC_NUMBER_OPENID_IS_NOT_NULL.getMessage());
                        }
                    } else {
                        resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
                        resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
                    }
                }
            }
        }
        logger.info("在service中根据OpenID列表群发-dailyLuckDrawMessageSend,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 根据OpenID向 管理员 发【更新油价】
     * @param paramMap
     */
    @Async
    @Override
    public ResultMapDTO dailyUpdateOilPriceMessageSend(Map<String, Object> paramMap) throws Exception {
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> resultMap = Maps.newHashMap();
        logger.info("在service中根据OpenID列表群发-dailyUpdateOilPriceMessageSend,请求-paramMap:" + paramMap);
        String updateNum = paramMap.get("updateNum")!=null?paramMap.get("updateNum").toString():"0";
        //1.获取所有的微信公众号账号
        paramMap.clear();
        List<Map<String, String>> customMessageAccountList = Lists.newArrayList();
        paramMap.put("dicType", "customMessageAccount");
        ResultDTO resultDTO = wxDicService.getSimpleDicByCondition(paramMap);
        customMessageAccountList = resultDTO.getResultList();
        if (customMessageAccountList != null && customMessageAccountList.size() > 0) {
            for (int i = 0; i < customMessageAccountList.size(); i++) {
                Map<String, String> customMessageAccountMap = customMessageAccountList.get(i);
                String dicName = customMessageAccountMap.get("dicName");
                if (dicName.contains("公众号")) {
                    String appId = customMessageAccountMap.get("customMessageAccountAppId")!=null?customMessageAccountMap.get("customMessageAccountAppId").toString():"wxf768b49ad0a4630c";
                    String secret = customMessageAccountMap.get("customMessageAccountSecret")!=null?customMessageAccountMap.get("customMessageAccountSecret").toString():"a481dd6bc40c9eec3e57293222e8246f";
                    String customMessageAccountName = customMessageAccountMap.get("customMessageAccountName")!=null?customMessageAccountMap.get("customMessageAccountName").toString():"油价地图";
                    String dailyMessageTemplateId = customMessageAccountMap.get("dailyMessageTemplateId")!=null?customMessageAccountMap.get("dailyMessageTemplateId").toString():"v4tKZ7kAwI6VrXzAJyAxi5slILLRBibZg-G3kRwNIKQ";
                    if(!"".equals(appId) && !"".equals(secret)){
                        //发送消息
                        List<String> openIdList = Lists.newArrayList();
                        openIdList.add("oJcI1wt-ibRdgri1y8qKYCRQaq8g");     //油价地图的openId
                        openIdList.add("ovrxT5trVCVftVpNznW7Rz-oXP5k");     //智恵油站的openId
                        for(String openId : openIdList) {
                            //向 管理员汇报 已更新油价
                            paramMap.clear();//清空参数，重新准备参数
                            Map<String, Object> dataMap = Maps.newHashMap();

                            Map<String, Object> firstMap = Maps.newHashMap();
                            firstMap.put("value", customMessageAccountName+"从http://oil.usd-cny.com/更新成品油价完成");
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
                            keyword2Map.put("value", "【"+customMessageAccountName+"】");
                            keyword2Map.put("color", "#0017F5");
                            dataMap.put("keyword2", keyword2Map);

                            Map<String, Object> keyword3Map = Maps.newHashMap();
                            keyword3Map.put("value", "只为专注油价资讯，为车主省钱.");
                            keyword3Map.put("color", "#0017F5");
                            dataMap.put("keyword3", keyword3Map);

                            Map<String, Object> remarkMap = Maps.newHashMap();
                            remarkMap.put("value", "共更新了加油站油价『"+updateNum+"』座");
                            remarkMap.put("color", "#0017F5");
                            dataMap.put("remark", remarkMap);

                            paramMap.put("data", JSONObject.toJSONString(dataMap));
                            paramMap.put("url", "https://engine.seefarger.com/index/activity?appKey=2mdmXx9VAtdWaZbHc9SeiD1DJaT6&adslotId=290262");

                            paramMap.put("appId", appId);
                            paramMap.put("secret", secret);
                            paramMap.put("openId", openId);
                            paramMap.put("template_id", dailyMessageTemplateId);

                            Thread.sleep(2000);
                            logger.info("每个用户之间缓冲两秒进行发送，报料成功通知，当前openId = " + openId);

                            wxCommonService.sendTemplateMessageForWxPublicNumber(paramMap);
                        }
                    } else {
                        resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
                        resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
                    }
                }
            }
        }
        logger.info("在service中根据OpenID列表群发-dailyUpdateOilPriceMessageSend,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 根据OpenID向 管理员 发【加盟】消息
     * @param paramMap
     */
    @Async
    @Override
    public ResultMapDTO dailyLeagueMessageSend(Map<String, Object> paramMap) throws Exception {
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> resultMap = Maps.newHashMap();
        logger.info("在service中根据OpenID列表群发-dailyLeagueMessageSend,请求-paramMap:" + paramMap);
        String name = paramMap.get("name")!=null?paramMap.get("name").toString():"";
        String phone = paramMap.get("phone")!=null?paramMap.get("phone").toString():"";
        String remark = paramMap.get("remark")!=null?paramMap.get("remark").toString():"";
        //1.获取所有的微信公众号账号
        paramMap.clear();
        List<Map<String, String>> customMessageAccountList = Lists.newArrayList();
        paramMap.put("dicType", "customMessageAccount");
        ResultDTO resultDTO = wxDicService.getSimpleDicByCondition(paramMap);
        customMessageAccountList = resultDTO.getResultList();
        if (customMessageAccountList != null && customMessageAccountList.size() > 0) {
            for (int i = 0; i < customMessageAccountList.size(); i++) {
                Map<String, String> customMessageAccountMap = customMessageAccountList.get(i);
                String dicName = customMessageAccountMap.get("dicName");
                if (dicName.contains("公众号")) {
                    String appId = customMessageAccountMap.get("customMessageAccountAppId")!=null?customMessageAccountMap.get("customMessageAccountAppId").toString():"wxf768b49ad0a4630c";
                    String secret = customMessageAccountMap.get("customMessageAccountSecret")!=null?customMessageAccountMap.get("customMessageAccountSecret").toString():"a481dd6bc40c9eec3e57293222e8246f";
                    String customMessageAccountName = customMessageAccountMap.get("customMessageAccountName")!=null?customMessageAccountMap.get("customMessageAccountName").toString():"油价地图";
                    String leagueMessageTemplateId = customMessageAccountMap.get("leagueMessageTemplateId")!=null?customMessageAccountMap.get("leagueMessageTemplateId").toString():"FvFWMDDOdH132QdU9shyzSOpLCt6VB_YpQ3k0T7b5uY";//加盟受理通知
                    if(!"".equals(appId) && !"".equals(secret)){
                        //发送消息
                        List<String> openIdList = Lists.newArrayList();
                        openIdList.add("oJcI1wt-ibRdgri1y8qKYCRQaq8g");     //油价地图的openId
                        openIdList.add("ovrxT5trVCVftVpNznW7Rz-oXP5k");     //智恵油站的openId
                        for(String openId : openIdList) {
                            paramMap.clear();//清空参数，重新准备参数
                            //获取当前时间
                            Date currentDate = new Date();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Map<String, Object> dataMap = Maps.newHashMap();
                            //标题
                            Map<String, Object> firstMap = Maps.newHashMap();
                            firstMap.put("value", "您有新的加盟对象来了...");
                            firstMap.put("color", "#0017F5");
                            dataMap.put("keyword1", firstMap);
                            //姓名
                            Map<String, Object> keyword1Map = Maps.newHashMap();
                            keyword1Map.put("value", name);
                            keyword1Map.put("color", "#0017F5");
                            dataMap.put("first", keyword1Map);
                            //手机
                            Map<String, Object> keyword2Map = Maps.newHashMap();
                            keyword2Map.put("value", phone);
                            keyword2Map.put("color", "#0017F5");
                            dataMap.put("keyword2", keyword2Map);
                            //受理时间
                            Map<String, Object> keyword3Map = Maps.newHashMap();
                            keyword3Map.put("value", sdf.format(currentDate));
                            keyword3Map.put("color", "#0017F5");
                            dataMap.put("keyword3", keyword3Map);
                            //受理详情
                            Map<String, Object> keyword4Map = Maps.newHashMap();
                            keyword4Map.put("value", "【"+customMessageAccountName+"】用户已经确认【"+remark+"】合作方式.");
                            keyword4Map.put("color", "#0017F5");
                            dataMap.put("keyword3", keyword4Map);
                            //备注
                            Map<String, Object> remarkMap = Maps.newHashMap();
                            remarkMap.put("value", "生意来了，快让客服进行处理工单吧，千万不要漏掉啊.");
                            remarkMap.put("color", "#0017F5");
                            dataMap.put("remark", remarkMap);
                            //整合
                            paramMap.put("data", JSONObject.toJSONString(dataMap));

                            paramMap.put("appId", appId);
                            paramMap.put("secret", secret);
                            paramMap.put("openId", openId);
                            paramMap.put("template_id", leagueMessageTemplateId);

                            Thread.sleep(2000);
                            logger.info("每个用户之间缓冲两秒进行发送，报料成功通知，当前openId = " + openId);

                            wxCommonService.sendTemplateMessageForWxPublicNumber(paramMap);
                        }
                    } else {
                        resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
                        resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
                    }
                }
            }
        }
        logger.info("在service中根据OpenID列表群发-dailyLeagueMessageSend,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 根据OpenID向 管理员 发【更新或者添加加油站报错】消息
     * @param paramMap
     */
    @Async
    @Override
    public ResultMapDTO dailyUpdateOrAddOilStationMessageSend(Map<String, Object> paramMap) throws Exception {
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> resultMap = Maps.newHashMap();
        logger.info("在service中根据OpenID列表群发-dailyUpdateOrAddOilStationMessageSend,请求-paramMap:" + paramMap);
        String data = paramMap.get("data")!=null?paramMap.get("data").toString():"";
        //1.获取所有的微信公众号账号
        paramMap.clear();
        List<Map<String, String>> customMessageAccountList = Lists.newArrayList();
        paramMap.put("dicType", "customMessageAccount");
        ResultDTO resultDTO = wxDicService.getSimpleDicByCondition(paramMap);
        customMessageAccountList = resultDTO.getResultList();
        if (customMessageAccountList != null && customMessageAccountList.size() > 0) {
            for (int i = 0; i < customMessageAccountList.size(); i++) {
                Map<String, String> customMessageAccountMap = customMessageAccountList.get(i);
                String dicName = customMessageAccountMap.get("dicName");
                if (dicName.contains("公众号")) {
                    String appId = customMessageAccountMap.get("customMessageAccountAppId")!=null?customMessageAccountMap.get("customMessageAccountAppId").toString():"wxf768b49ad0a4630c";
                    String secret = customMessageAccountMap.get("customMessageAccountSecret")!=null?customMessageAccountMap.get("customMessageAccountSecret").toString():"a481dd6bc40c9eec3e57293222e8246f";
                    String customMessageAccountName = customMessageAccountMap.get("customMessageAccountName")!=null?customMessageAccountMap.get("customMessageAccountName").toString():"油价地图";
                    String errorMessageTemplateId = customMessageAccountMap.get("errorMessageTemplateId")!=null?customMessageAccountMap.get("errorMessageTemplateId").toString():"TdKDrcNW934K0r1rtlDKCUI0XCQ5xb4GGb8ieHb0zug";//服务器报错提醒
                    if(!"".equals(appId) && !"".equals(secret)){
                        //发送消息
                        List<String> openIdList = Lists.newArrayList();
                        openIdList.add("oJcI1wt-ibRdgri1y8qKYCRQaq8g");     //油价地图的openId
                        openIdList.add("ovrxT5trVCVftVpNznW7Rz-oXP5k");     //智恵油站的openId
                        for(String openId : openIdList) {
                            paramMap.clear();//清空参数，重新准备参数
                            //整合
                            paramMap.put("data", data);

                            paramMap.put("appId", appId);
                            paramMap.put("secret", secret);
                            paramMap.put("openId", openId);
                            paramMap.put("template_id", errorMessageTemplateId);

                            Thread.sleep(2000);
                            logger.info("每个用户之间缓冲两秒进行发送，报料成功通知，当前openId = " + openId);

                            wxCommonService.sendTemplateMessageForWxPublicNumber(paramMap);
                        }
                    } else {
                        resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
                        resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
                    }
                }
            }
        }
        logger.info("在service中根据OpenID列表群发-dailyUpdateOrAddOilStationMessageSend,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 根据OpenID向 管理员 发【恶意篡改加油站油价】消息
     * @param paramMap
     */
    @Async
    @Override
    public ResultMapDTO dailyIllegalUpdateOilPriceMessageSend(Map<String, Object> paramMap) throws Exception {
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> resultMap = Maps.newHashMap();
        logger.info("在service中根据OpenID列表群发-dailyIllegalUpdateOilPriceMessageSend,请求-paramMap:" + paramMap);
        String data = paramMap.get("data")!=null?paramMap.get("data").toString():"";
        //1.获取所有的微信公众号账号
        paramMap.clear();
        List<Map<String, String>> customMessageAccountList = Lists.newArrayList();
        paramMap.put("dicType", "customMessageAccount");
        ResultDTO resultDTO = wxDicService.getSimpleDicByCondition(paramMap);
        customMessageAccountList = resultDTO.getResultList();
        if (customMessageAccountList != null && customMessageAccountList.size() > 0) {
            for (int i = 0; i < customMessageAccountList.size(); i++) {
                Map<String, String> customMessageAccountMap = customMessageAccountList.get(i);
                String dicName = customMessageAccountMap.get("dicName");
                if (dicName.contains("公众号")) {
                    String appId = customMessageAccountMap.get("customMessageAccountAppId")!=null?customMessageAccountMap.get("customMessageAccountAppId").toString():"wxf768b49ad0a4630c";
                    String secret = customMessageAccountMap.get("customMessageAccountSecret")!=null?customMessageAccountMap.get("customMessageAccountSecret").toString():"a481dd6bc40c9eec3e57293222e8246f";
                    String customMessageAccountName = customMessageAccountMap.get("customMessageAccountName")!=null?customMessageAccountMap.get("customMessageAccountName").toString():"油价地图";
                    String dailyMessageTemplateId = customMessageAccountMap.get("dailyMessageTemplateId")!=null?customMessageAccountMap.get("dailyMessageTemplateId").toString():"v4tKZ7kAwI6VrXzAJyAxi5slILLRBibZg-G3kRwNIKQ";//报料成功通知
                    if(!"".equals(appId) && !"".equals(secret)){
                        //发送消息
                        List<String> openIdList = Lists.newArrayList();
                        openIdList.add("oJcI1wt-ibRdgri1y8qKYCRQaq8g");     //油价地图的openId
                        openIdList.add("ovrxT5trVCVftVpNznW7Rz-oXP5k");     //智恵油站的openId
                        for(String openId : openIdList) {
                            paramMap.clear();//清空参数，重新准备参数
                            //整合
                            paramMap.put("data", data);

                            paramMap.put("appId", appId);
                            paramMap.put("secret", secret);
                            paramMap.put("openId", openId);
                            paramMap.put("template_id", dailyMessageTemplateId);

                            Thread.sleep(2000);
                            logger.info("每个用户之间缓冲两秒进行发送，报料成功通知，当前openId = " + openId);

                            wxCommonService.sendTemplateMessageForWxPublicNumber(paramMap);
                        }
                    } else {
                        resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
                        resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
                    }
                }
            }
        }
        logger.info("在service中根据OpenID列表群发-dailyIllegalUpdateOilPriceMessageSend,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

}

package com.oilStationMap.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.newMall.utils.SpriderForFenXiangShengHuoUtil;
import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dao.WX_UserDao;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.service.WX_CommonService;
import com.oilStationMap.service.WX_DicService;
import com.oilStationMap.service.WX_MessageService;
import com.oilStationMap.utils.MediaTypeUtil;
import com.oilStationMap.utils.WX_PublicNumberUtil;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class WX_MessageServiceImpl implements WX_MessageService {

    private static final Logger logger = LoggerFactory.getLogger(WX_MessageServiceImpl.class);

    @Autowired
    private WX_UserDao wxUserDao;

    @Autowired
    private WX_DicService wxDicService;

    @Autowired
    private WX_CommonService wxCommonService;

    /**
     * 向微信公众号粉丝群发红包模板消息
     * @param paramMap
     */
    @Override
    public ResultMapDTO redActivityMessageSend(Map<String, Object> paramMap) throws Exception {
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> resultMap = Maps.newHashMap();
        logger.info("【service】发送红包资讯-redActivityMessageSend,请求-paramMap:" + paramMap);
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
                                    logger.info("每个用户之间缓冲两秒进行发送，发送红包资讯，当前openId = " + openId);
                                    logger.info("每个用户之间缓冲两秒进行发送，发送红包资讯，当前openId = " + openId);
                                    logger.info("每个用户之间缓冲两秒进行发送，发送红包资讯，当前openId = " + openId);

                                    wxCommonService.sendTemplateMessageForWxPublicNumber(paramMap);
                                }
                                resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                                resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
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
        logger.info("【service】发送红包资讯-redActivityMessageSend,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 根据appId和secret和templateId来确定使用哪个公众号的那个消息模板进行发送 日常报料通知
     * 根据OpenID列表群发【订阅号不可用，服务号认证后可用】
     * @param paramMap
     */
    @Override
    public ResultMapDTO dailyMessageSend(Map<String, Object> paramMap) throws Exception {
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> resultMap = Maps.newHashMap();
        logger.info("【service】发送红包资讯-dailyMessageSend,请求-paramMap:" + paramMap);
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
                                                logger.info("每个用户之间缓冲两秒进行发送，发送红包资讯，当前openId = " + openId);
                                                logger.info("每个用户之间缓冲两秒进行发送，发送红包资讯，当前openId = " + openId);
                                                logger.info("每个用户之间缓冲两秒进行发送，发送红包资讯，当前openId = " + openId);

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
        logger.info("【service】发送红包资讯-dailyMessageSend,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 根据OpenID列表群发【抽奖】福利
     * @param paramMap
     */
    @Override
    public ResultMapDTO dailyLuckDrawMessageSend(Map<String, Object> paramMap) throws Exception {
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> resultMap = Maps.newHashMap();
        logger.info("【service】发送抽奖资讯-dailyLuckDrawMessageSend,请求-paramMap:" + paramMap);
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
                                    paramMap.put("url", "http://mp.weixin.qq.com/s?__biz=MzI1ODMwMzAxMw==&mid=100000851&idx=1&sn=2419d8b456bdc0e62c45ca77686639f3&chksm=6a0b71195d7cf80f63f95e6c133ddd0b190db6da4e09c4fe57c8e2e070317a9172a8e6fcb419#rd\\");

                                    paramMap.put("appId", appId);
                                    paramMap.put("secret", secret);
                                    paramMap.put("openId", openId);
                                    paramMap.put("template_id", luckDrawMessageTemplateId);//逾期应收提醒

                                    Thread.sleep(2000);
                                    logger.info("每个用户之间缓冲两秒进行发送，发送抽奖资讯，当前openId = " + openId);
                                    logger.info("每个用户之间缓冲两秒进行发送，发送抽奖资讯，当前openId = " + openId);
                                    logger.info("每个用户之间缓冲两秒进行发送，发送抽奖资讯，当前openId = " + openId);

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
        logger.info("【service】发送抽奖资讯-dailyLuckDrawMessageSend,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 根据OpenID列表群发【车主福利for车用尿素】福利
     * @param paramMap
     */
    @Override
    public ResultMapDTO dailyCarUreaMessageSend(Map<String, Object> paramMap) throws Exception {
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> resultMap = Maps.newHashMap();
        logger.info("【service】发送车主福利for车用尿素资讯-dailyCarUreaMessageSend,请求-paramMap:" + paramMap);
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
                        //1.获取所有微信用户openId
                        Map<String, Object> followersMap = WX_PublicNumberUtil.getFollowers(null, appId,  secret);
                        if(followersMap != null && followersMap.size() > 0){
                            JSONObject dataJSONObject = followersMap.get("data")!=null?(JSONObject)followersMap.get("data"):null;
                            if(dataJSONObject != null){
                                JSONArray openIdJSONArray = dataJSONObject.get("openid")!=null?
                                        (JSONArray)dataJSONObject.get("openid"):null;
                                List<String> openIdList = JSONObject.parseObject(openIdJSONArray.toJSONString(), List.class);
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                Date currentDate = new Date();
                                //2.发送抽奖消息消息
                                openIdList.clear();  //模拟只向管理员发送消息
                                openIdList.add("oJcI1wt-ibRdgri1y8qKYCRQaq8g");     //油价地图的openId
                                openIdList.add("ovrxT5trVCVftVpNznW7Rz-oXP5k");     //智恵油站的openId
                                for(String openId : openIdList) {
                                    paramMap.clear();//清空参数，重新准备参数
                                    Map<String, Object> dataMap = Maps.newHashMap();

                                    Map<String, Object> firstMap = Maps.newHashMap();
                                    firstMap.put("value", "车主福利：车用尿素，您的发动机与国标排放之间的距离");
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
                                    remarkMap.put("value", "近年随着机动车辆的迅猛增加，汽车尾气污染越来越成为与大众关心的话题...");
                                    remarkMap.put("color", "#0017F5");
                                    dataMap.put("remark", remarkMap);

                                    paramMap.put("data", JSONObject.toJSONString(dataMap));
                                    paramMap.put("url", "https://mp.weixin.qq.com/s?__biz=MzI1ODMwMzAxMw==&mid=100000914&idx=1&sn=9a1635f4c2b6063bf35c90193d00391d&chksm=6a0b71d85d7cf8ce5dff7581eb396525bfc384e60d2e2877a4690ca804ed2e7dcb7ba37e2fe7#rd");

                                    paramMap.put("appId", appId);
                                    paramMap.put("secret", secret);
                                    paramMap.put("openId", openId);
                                    paramMap.put("template_id", dailyMessageTemplateId);//逾期应收提醒

                                    Thread.sleep(2000);
                                    logger.info("每个用户之间缓冲两秒进行发送，发送车主福利for车用尿素资讯，当前openId = " + openId);
                                    logger.info("每个用户之间缓冲两秒进行发送，发送车主福利for车用尿素资讯，当前openId = " + openId);
                                    logger.info("每个用户之间缓冲两秒进行发送，发送车主福利for车用尿素资讯，当前openId = " + openId);

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
        logger.info("【service】发送车主福利for车用尿素资讯-dailyCarUreaMessageSend,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 根据OpenID向 管理员 发【更新油价】
     * @param paramMap
     */
    @Override
    public ResultMapDTO dailyUpdateOilPriceMessageSend(Map<String, Object> paramMap) throws Exception {
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> resultMap = Maps.newHashMap();
        logger.info("【service】更新油价资讯-dailyUpdateOilPriceMessageSend,请求-paramMap:" + paramMap);
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
                            paramMap.put("url", "https://engine.seefarger.com/index/activity?appKey=4Djteae9wZ9noyKnzd1VEeQD4Tiw&adslotId=294762");

                            paramMap.put("appId", appId);
                            paramMap.put("secret", secret);
                            paramMap.put("openId", openId);
                            paramMap.put("template_id", dailyMessageTemplateId);

                            Thread.sleep(2000);
                            logger.info("每个用户之间缓冲两秒进行发送，更新油价资讯，当前openId = " + openId);
                            logger.info("每个用户之间缓冲两秒进行发送，更新油价资讯，当前openId = " + openId);
                            logger.info("每个用户之间缓冲两秒进行发送，更新油价资讯，当前openId = " + openId);

                            wxCommonService.sendTemplateMessageForWxPublicNumber(paramMap);
                        }
                    } else {
                        resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
                        resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
                    }
                }
            }
        }
        logger.info("【service】更新油价资讯-dailyUpdateOilPriceMessageSend,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 根据OpenID向 管理员 发【加盟】消息
     * @param paramMap
     */
    @Override
    public ResultMapDTO dailyLeagueMessageSend(Map<String, Object> paramMap) throws Exception {
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> resultMap = Maps.newHashMap();
        logger.info("【service】发送加盟资讯-dailyLeagueMessageSend,请求-paramMap:" + paramMap);
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
                            firstMap.put("value", "您有新的加盟对象来了，快让客服进行处理工单吧...");
                            firstMap.put("color", "#0017F5");
                            dataMap.put("first", firstMap);
                            //姓名
                            Map<String, Object> keyword1Map = Maps.newHashMap();
                            keyword1Map.put("value", name);
                            keyword1Map.put("color", "#0017F5");
                            dataMap.put("keyword1", keyword1Map);
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
                            Map<String, Object> remarkMap = Maps.newHashMap();
                            remarkMap.put("value", "【"+customMessageAccountName+"】用户已经确认【"+remark+"】合作方式.");
                            remarkMap.put("color", "#0017F5");
                            dataMap.put("remark", remarkMap);
                            //整合
                            paramMap.put("data", JSONObject.toJSONString(dataMap));

                            paramMap.put("appId", appId);
                            paramMap.put("secret", secret);
                            paramMap.put("openId", openId);
                            paramMap.put("template_id", leagueMessageTemplateId);

                            Thread.sleep(2000);
                            logger.info("每个用户之间缓冲两秒进行发送，发送加盟资讯，当前openId = " + openId);
                            logger.info("每个用户之间缓冲两秒进行发送，发送加盟资讯，当前openId = " + openId);
                            logger.info("每个用户之间缓冲两秒进行发送，发送加盟资讯，当前openId = " + openId);

                            wxCommonService.sendTemplateMessageForWxPublicNumber(paramMap);
                        }
                    } else {
                        resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
                        resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
                    }
                }
            }
        }
        logger.info("【service】发送加盟资讯-dailyLeagueMessageSend,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 根据OpenID向 管理员 发【更新或者添加加油站报错】消息
     * @param paramMap
     */
    @Override
    public ResultMapDTO dailyUpdateOrAddOilStationMessageSend(Map<String, Object> paramMap) throws Exception {
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> resultMap = Maps.newHashMap();
        logger.info("【service】更新油站资讯-dailyUpdateOrAddOilStationMessageSend,请求-paramMap:" + paramMap);
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
                            logger.info("每个用户之间缓冲两秒进行发送，更新油站资讯，当前openId = " + openId);
                            logger.info("每个用户之间缓冲两秒进行发送，更新油站资讯，当前openId = " + openId);
                            logger.info("每个用户之间缓冲两秒进行发送，更新油站资讯，当前openId = " + openId);

                            wxCommonService.sendTemplateMessageForWxPublicNumber(paramMap);
                        }
                    } else {
                        resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
                        resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
                    }
                }
            }
        }
        logger.info("【service】更新油站资讯-dailyUpdateOrAddOilStationMessageSend,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 根据OpenID向 管理员 发【恶意篡改加油站油价】消息
     * @param paramMap
     */
    @Override
    public ResultMapDTO dailyIllegalUpdateOilPriceMessageSend(Map<String, Object> paramMap) throws Exception {
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> resultMap = Maps.newHashMap();
        logger.info("【service】发送恶意篡改加油站油价资讯-dailyIllegalUpdateOilPriceMessageSend,请求-paramMap:" + paramMap);
        String uid = paramMap.get("uid")!=null?paramMap.get("uid").toString():"";
        String nickName = paramMap.get("nickName")!=null?paramMap.get("nickName").toString():"用户";
        String oilStationCode = paramMap.get("oilStationCode")!=null?paramMap.get("oilStationCode").toString():"";
        String oilStationName = paramMap.get("oilStationName")!=null?paramMap.get("oilStationName").toString():"";

        //1.获取即将发送消息的对象
        List<String> openIdList = Lists.newArrayList();
        Map<String, Object> userParamMap = Maps.newHashMap();
        userParamMap.put("id", uid);
        List<Map<String, Object>> userList = wxUserDao.getSimpleUserByCondition(userParamMap);
        if(userList != null && userList.size() > 0){
            nickName = userList.get(0).get("nickName")!=null?userList.get(0).get("nickName").toString():"用户";
            String userRemark = userList.get(0).get("userRemark")!=null?userList.get(0).get("userRemark").toString():"";
            openIdList = JSONObject.parseObject(userRemark, List.class);
        }
        if(!openIdList.contains("oJcI1wt-ibRdgri1y8qKYCRQaq8g")){
            openIdList.add("oJcI1wt-ibRdgri1y8qKYCRQaq8g");     //油价地图的openId
        }
        if(!openIdList.contains("ovrxT5trVCVftVpNznW7Rz-oXP5k")){
            openIdList.add("ovrxT5trVCVftVpNznW7Rz-oXP5k");     //智恵油站的openId
        }

        //2.获取所有的微信公众号账号
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
                        for(String openId : openIdList) {
                            paramMap.clear();//清空参数，重新准备参数
                            Map<String, Object> dataMap = Maps.newHashMap();

                            Map<String, Object> firstMap = Maps.newHashMap();
                            firstMap.put("value", "警告:恶意修改加油站-油价");
                            firstMap.put("color", "#8B0000");
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
                            remarkMap.put("value", nickName+"(uid:"+uid+")对"+oilStationName+"(code:"+oilStationCode+")在乱改油价来进行恶意竞争,请联系管理员对该用户进行锁定并观察后期用户行为,急急急...");
                            remarkMap.put("color", "#8B0000");
                            dataMap.put("remark", remarkMap);

                            paramMap.put("data", JSONObject.toJSONString(dataMap));

                            paramMap.put("appId", appId);
                            paramMap.put("secret", secret);
                            paramMap.put("openId", openId);
                            paramMap.put("template_id", dailyMessageTemplateId);

                            Thread.sleep(2000);
                            logger.info("每个用户之间缓冲两秒进行发送，发送恶意篡改加油站油价资讯，当前openId = " + openId);
                            logger.info("每个用户之间缓冲两秒进行发送，发送恶意篡改加油站油价资讯，当前openId = " + openId);
                            logger.info("每个用户之间缓冲两秒进行发送，发送恶意篡改加油站油价资讯，当前openId = " + openId);

                            wxCommonService.sendTemplateMessageForWxPublicNumber(paramMap);
                        }
                    } else {
                        resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
                        resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
                    }
                }
            }
        }
        logger.info("【service】发送恶意篡改加油站油价资讯-dailyIllegalUpdateOilPriceMessageSend,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 根据OpenID向 管理员 发【恶意篡改管理员用户信息】消息
     * @param paramMap
     */
    @Override
    public ResultMapDTO dailyIllegalUpdateUserInfoMessageSend(Map<String, Object> paramMap) throws Exception {
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> resultMap = Maps.newHashMap();
        logger.info("【service】发送恶意篡改管理员用户信息-dailyIllegalUpdateOilPriceMessageSend,请求-paramMap:" + paramMap);
        String uid = paramMap.get("uid")!=null?paramMap.get("uid").toString():"";
        String admin_openId = paramMap.get("admin_openId")!=null?paramMap.get("admin_openId").toString():"";
        String new_openId = paramMap.get("new_openId")!=null?paramMap.get("new_openId").toString():"";

        //1.获取即将发送消息的对象
        List<String> openIdList = Lists.newArrayList();
        Map<String, Object> userParamMap = Maps.newHashMap();
        userParamMap.put("id", 3);
        List<Map<String, Object>> userList = wxUserDao.getSimpleUserByCondition(userParamMap);
        if(userList != null && userList.size() > 0){
            String userRemark = userList.get(0).get("userRemark")!=null?userList.get(0).get("userRemark").toString():"";
            openIdList = JSONObject.parseObject(userRemark, List.class);
        }
        if(!openIdList.contains("oJcI1wt-ibRdgri1y8qKYCRQaq8g")){
            openIdList.add("oJcI1wt-ibRdgri1y8qKYCRQaq8g");     //油价地图的openId
        }
        if(!openIdList.contains("ovrxT5trVCVftVpNznW7Rz-oXP5k")){
            openIdList.add("ovrxT5trVCVftVpNznW7Rz-oXP5k");     //智恵油站的openId
        }

        //2.获取所有的微信公众号账号
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
                        for(String openId : openIdList) {
                            paramMap.clear();//清空参数，重新准备参数
                            Map<String, Object> dataMap = Maps.newHashMap();

                            Map<String, Object> firstMap = Maps.newHashMap();
                            firstMap.put("value", "警告:发送恶意篡改管理员用户信息");
                            firstMap.put("color", "#8B0000");
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
                            remarkMap.put("value", "管理员用户(openId:"+admin_openId+")被恶意用户(openId:"+new_openId+")乱改，来获取管理员权限恶搞！！！急急急...");
                            remarkMap.put("color", "#8B0000");
                            dataMap.put("remark", remarkMap);

                            paramMap.put("data", JSONObject.toJSONString(dataMap));

                            paramMap.put("appId", appId);
                            paramMap.put("secret", secret);
                            paramMap.put("openId", openId);
                            paramMap.put("template_id", dailyMessageTemplateId);

                            Thread.sleep(2000);
                            logger.info("每个用户之间缓冲两秒进行发送，发送恶意篡改管理员用户信息，当前openId = " + openId);
                            logger.info("每个用户之间缓冲两秒进行发送，发送恶意篡改管理员用户信息，当前openId = " + openId);
                            logger.info("每个用户之间缓冲两秒进行发送，发送恶意篡改管理员用户信息，当前openId = " + openId);
                            wxCommonService.sendTemplateMessageForWxPublicNumber(paramMap);
                        }
                    } else {
                        resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
                        resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
                    }
                }
            }
        }
        logger.info("【service】发送恶意篡改管理员用户信息-dailyIllegalUpdateOilPriceMessageSend,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 根据粉象生活Json获取【粉象生活Excel】福利
     * @param paramMap
     */
    @Override
    public ResultMapDTO dailyGetFenXiangShengHuoProduct(Map<String, Object> paramMap) throws Exception {
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> resultMap = Maps.newHashMap();
        logger.info("【service】根据粉象生活Json获取【粉象生活Excel】福利-dailyGetFenXiangShengHuoProduct,请求-paramMap:" + paramMap);
        SpriderForFenXiangShengHuoUtil.getFenXiangShengHuoProduct(paramMap);
        resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
        resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        logger.info("【service】根据粉象生活Json获取【粉象生活Excel】福利-dailyGetFenXiangShengHuoProduct,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }



    /**
     * 根据OpenID向 管理员 发【微信广告自动化过程中的异常设备】
     * @param paramMap
     */
    @Override
    public ResultMapDTO exceptionDevicesMessageSend(Map<String, Object> paramMap) throws Exception {
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> resultMap = Maps.newHashMap();
        logger.info("【service】发布微信广告自动化过程中的异常设备-devicesExceptionMessageSend,请求-paramMap:" + paramMap);
        String nickName = paramMap.get("nickName")!=null?paramMap.get("nickName").toString():"未知";
        String operatorName = paramMap.get("operatorName")!=null?paramMap.get("operatorName").toString():"无";
        String exceptionDevices = paramMap.get("exceptionDevices")!=null?paramMap.get("exceptionDevices").toString():"无";
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
                    String exceptionDevicesTemplateId = customMessageAccountMap.get("exceptionDevicesTemplateId")!=null?customMessageAccountMap.get("exceptionDevicesTemplateId").toString():"jc3BrNm_fy3My21kgz8zWaKQNIMp7piMHTQDCw8kOEc";
                    if(!"".equals(appId) && !"".equals(secret)){
                        //发送消息
                        List<String> openIdList = Lists.newArrayList();
                        openIdList.add("oJcI1wt-ibRdgri1y8qKYCRQaq8g");     //油价地图的openId - 蔡红旺
                        openIdList.add("ovrxT5trVCVftVpNznW7Rz-oXP5k");     //智恵油站的openId - 蔡红旺

                        for(String openId : openIdList) {
                            //向 管理员汇报 已更新油价
                            paramMap.clear();//清空参数，重新准备参数
                            Map<String, Object> dataMap = Maps.newHashMap();

                            Map<String, Object> firstMap = Maps.newHashMap();
                            firstMap.put("value", nickName + "-" + operatorName + "-发生异常，按照提示操作...");
                            firstMap.put("color", "#0017F5");
                            dataMap.put("first", firstMap);

                            //异常原因
                            Map<String, Object> keyword1Map = Maps.newHashMap();
                            keyword1Map.put("value", "Usb接口不稳定断电或者微信版本已被更新导致坐标不匹配");
                            keyword1Map.put("color", "#0017F5");
                            dataMap.put("keyword1", keyword1Map);

                            //异常设备
                            Map<String, Object> keyword2Map = Maps.newHashMap();
                            keyword2Map.put("value", "请查看备注");
                            keyword2Map.put("color", "#0017F5");
                            dataMap.put("keyword2", keyword2Map);

                            //异常地点
                            Map<String, Object> keyword3Map = Maps.newHashMap();
                            keyword3Map.put("value", "北京市昌平区");
                            keyword3Map.put("color", "#0017F5");
                            dataMap.put("keyword3", keyword3Map);

                            //异常时间
                            Date currentDate = new Date();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Map<String, Object> keyword4Map = Maps.newHashMap();
                            keyword4Map.put("value", sdf.format(currentDate));
                            keyword4Map.put("color", "#0017F5");
                            dataMap.put("keyword4", keyword4Map);

                            //操作提示
                            Map<String, Object> keyword5Map = Maps.newHashMap();
                            keyword5Map.put("value", "请检查以下手机的接口，并手动完成广告操作.");
                            keyword5Map.put("color", "#0017F5");
                            dataMap.put("keyword5", keyword5Map);

                            //备注
                            Map<String, Object> remarkMap = Maps.newHashMap();
                            remarkMap.put("value", exceptionDevices);
                            remarkMap.put("color", "#0017F5");
                            dataMap.put("remark", remarkMap);

                            paramMap.put("data", JSONObject.toJSONString(dataMap));
                            paramMap.put("url", "https://mp.weixin.qq.com/s/_Na1Cq5Dt44T8DavY1fbgQ");       //智慧油站-公众号文章

                            paramMap.put("appId", appId);
                            paramMap.put("secret", secret);
                            paramMap.put("openId", openId);
                            paramMap.put("template_id", exceptionDevicesTemplateId);

                            Thread.sleep(2000);
                            logger.info("每个用户之间缓冲两秒进行发送，更新油价资讯，当前openId = " + openId);
                            logger.info("每个用户之间缓冲两秒进行发送，更新油价资讯，当前openId = " + openId);
                            logger.info("每个用户之间缓冲两秒进行发送，更新油价资讯，当前openId = " + openId);

                            wxCommonService.sendTemplateMessageForWxPublicNumber(paramMap);
                        }
                    } else {
                        resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
                        resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
                    }
                }
            }
        }
        logger.info("【service】发布微信广告自动化过程中的异常设备-devicesExceptionMessageSend,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }
}

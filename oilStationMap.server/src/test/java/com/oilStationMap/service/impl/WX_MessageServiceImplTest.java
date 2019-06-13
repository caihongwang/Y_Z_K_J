package com.oilStationMap.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oilStationMap.MySuperTest;
import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.service.WX_CommonService;
import com.oilStationMap.service.WX_DicService;
import com.oilStationMap.service.WX_SourceMaterialService;
import com.oilStationMap.utils.*;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by caihongwang on 2018/12/11.
 */
public class WX_MessageServiceImplTest extends MySuperTest {


    private static final Logger logger = LoggerFactory.getLogger(WX_MessageServiceImpl.class);

    @Autowired
    private WX_DicService WXDicService;

    @Autowired
    private WX_SourceMaterialService wxSourceMaterialService;

    @Autowired
    private WX_CommonService WXCommonService;

//    @Test
//    public void Test() throws Exception{
////        Map<String, Object> paramMap = Maps.newHashMap();
////        dailyMessageSend(paramMap);
//
//        Map<String, Object> paramMap = Maps.newHashMap();
//        redActivityMessageSend(paramMap);
//    }

    public ResultMapDTO redActivityMessageSend(Map<String, Object> paramMap) throws Exception {
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> resultMap = Maps.newHashMap();
        logger.info("在service中根据OpenID列表群发-dailyMessageSend,请求-paramMap:" + paramMap);
        //默认使用 【油价地图】公众号 来发送
        String appId = paramMap.get("appId")!=null?paramMap.get("appId").toString():"wxf768b49ad0a4630c";
        String secret = paramMap.get("secret")!=null?paramMap.get("secret").toString():"a481dd6bc40c9eec3e57293222e8246f";
        if(!"".equals(appId) && !"".equals(secret)){
            //1.获取所有微信用户openId
            Map<String, Object> followersMap = WX_PublicNumberUtil.getFollowers(null, appId,  secret);
            if(followersMap != null && followersMap.size() > 0){
                JSONObject dataJSONObject = followersMap.get("data")!=null?(JSONObject)followersMap.get("data"):null;
                if(dataJSONObject != null){
                    String title = "";
                    String digest = "";
                    String appIdStr = "";
                    String pagePath = "";
                    String templateId = "";
                    JSONArray openIdJSONArray = dataJSONObject.get("openid")!=null?
                            (JSONArray)dataJSONObject.get("openid"):null;
                    List<String> openIdList = JSONObject.parseObject(openIdJSONArray.toJSONString(), List.class);
                    Map<String, Object> customMessageParamMap = Maps.newHashMap();
                    customMessageParamMap.put("dicType", "wxCustomMessage");
                    customMessageParamMap.put("dicCode", "26");
                    ResultDTO customMessageResultDTO = WXDicService.getSimpleDicByCondition(customMessageParamMap);
                    if(customMessageResultDTO != null && customMessageResultDTO.getResultList() != null
                            && customMessageResultDTO.getResultList().size() > 0) {
                        Map<String, String> customMessageMap = customMessageResultDTO.getResultList().get(0);
                        title = customMessageMap.get("title")!=null?customMessageMap.get("title").toString():"";
                        digest = customMessageMap.get("digest")!=null?customMessageMap.get("digest").toString():"";
                        appIdStr = customMessageMap.get("appid")!=null?customMessageMap.get("appid").toString():"";
                        pagePath = customMessageMap.get("pagePath")!=null?customMessageMap.get("pagePath").toString():"";
                        templateId = customMessageMap.get("templateId")!=null?customMessageMap.get("templateId").toString():"";
                    } else {
                        title = "抢红包了,抢红包了,抢红包了,重要的事情说三遍!";
                        digest = "抢红包了!新年送福利，加油无烦扰，秒慢无.";
                        appIdStr = "wx07cf52be1444e4b7";
                        pagePath = "pages/other/activity/redActivity/index";
                        templateId = "v4tKZ7kAwI6VrXzAJyAxi5slILLRBibZg-G3kRwNIKQ";
                    }
                    //2.发送模板消息-打开小程序
                    for(String openId : openIdList) {
//                        openId = "oJcI1wt-ibRdgri1y8qKYCRQaq8g";
                        paramMap.clear();//清空参数，重新准备参数
                        Map<String, Object> dataMap = Maps.newHashMap();

                        Map<String, Object> firstMap = Maps.newHashMap();
                        firstMap.put("value", title);
                        firstMap.put("color", "#0017F5");
                        dataMap.put("first", firstMap);

                        Map<String, Object> keyword1Map = Maps.newHashMap();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date currentDate = new Date();
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
                        remarkMap.put("value", digest);
                        remarkMap.put("color", "#0017F5");
                        dataMap.put("remark", remarkMap);

                        paramMap.put("data", JSONObject.toJSONString(dataMap));

                        Map<String, Object> miniprogramMap = Maps.newHashMap();
                        miniprogramMap.put("appid", appIdStr);
                        miniprogramMap.put("pagepath", pagePath);
                        paramMap.put("miniprogram", JSONObject.toJSONString(miniprogramMap));

                        paramMap.put("openId", openId);
                        paramMap.put("template_id", templateId);

                        Thread.sleep(2000);
                        logger.info("每个用户之间缓冲两秒进行发送，报料成功通知，当前openId = " + openId);
                        logger.info("每个用户之间缓冲两秒进行发送，报料成功通知，当前openId = " + openId);
                        logger.info("每个用户之间缓冲两秒进行发送，报料成功通知，当前openId = " + openId);

                        WXCommonService.sendTemplateMessageForWxPublicNumber(paramMap);
//                        break;
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
            resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());        }
        logger.info("在service中根据OpenID列表群发-dailyMessageSend,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

}

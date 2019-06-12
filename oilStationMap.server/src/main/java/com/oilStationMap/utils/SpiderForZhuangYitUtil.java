package com.oilStationMap.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.service.WX_DicService;
import com.oilStationMap.service.impl.WX_DicServiceImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 装一网 爬取站点 以及 发起预约
 */
public class SpiderForZhuangYitUtil {

    public static final Logger logger = LoggerFactory.getLogger(SpiderForZhuangYitUtil.class);

    public static WX_DicService wxDicService = (WX_DicService)ApplicationContextUtils.getBeanByClass(WX_DicServiceImpl.class);

    public static void subscribeRenovation(String baseUrl){
        if(baseUrl == null || "".equals(baseUrl)){
            baseUrl = "http://tr.zhuangyi.com";
        }
        List<String> renovationCompanyUrlList = getRenovationCompanyList(baseUrl);

        Map<String, Object> dicParam = Maps.newHashMap();
        dicParam.put("dicType", "zhuangyi");
        dicParam.put("dicCode", "zhuangyi");
        ResultDTO resultDTO = wxDicService.getSimpleDicByCondition(dicParam);
        List<Map<String, String>> dicResultParam = resultDTO.getResultList();
        Integer positionIndex = Integer.parseInt(dicResultParam.get(0).get("positionIndex").toString());
        List<String> subList =
                renovationCompanyUrlList.subList(positionIndex, positionIndex+5);

        //每天只能预约5个，好惨.
        for(String renovationCompanyUrl : subList){
            String zbSd = "529";
            String zbCommunity = "蓝月清水湾";
            String zbBuiltArea = "135";
            String zbName = "蔡先生";
            String zbTel = "18685679555";
            SpiderForZhuangYitUtil.getSimpleRenovationCompany(renovationCompanyUrl,
                    zbSd, zbCommunity, zbBuiltArea, zbName, zbTel);
        }

        positionIndex = positionIndex + 5;
        Map<String, String> remarkMap = Maps.newHashMap();
        remarkMap.put("zhuangYiCode", "zhuangyi");
        remarkMap.put("zhuangYiName", "装一网");
        remarkMap.put("positionIndex", positionIndex.toString());
        dicParam.clear();
        dicParam.put("id", dicResultParam.get(0).get("id"));
        dicParam.put("dicRemark", JSONObject.toJSONString(remarkMap));
        wxDicService.updateDic(dicParam);

    }

    /**
     * 在【装一网】爬取地方站点的装修公司
     * @param baseUrl
     */
    public static List<String> getRenovationCompanyList(String baseUrl){
        Integer pn = 1;
        List<String> renovationCompanyUrlList = Lists.newArrayList();
        boolean loopFlag = true;
        while (loopFlag) {
            String getRenovationCompanyListUrl = baseUrl + "/zsgs/phb.aspx?pn="+pn;
            Document doc = null;
            try {
                doc = Jsoup.connect(getRenovationCompanyListUrl)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36")
                        .timeout(5000).get();
                //装修公司-链接
                Elements spanElements = doc.getElementsByTag("span");
                for (Element element : spanElements) {
                    String className = element.attr("class");
                    if ("tgqg_tit".equals(className)) {
                        List<Node> childNodes = element.childNodes();
                        for (Node node : childNodes) {
                            String href = node.attr("href");
                            if(href != null && !"".equals(href)){
                                if(!renovationCompanyUrlList.contains(href)){
                                    renovationCompanyUrlList.add(href);
                                } else {
                                    loopFlag = false;
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("在【装一网】爬取地方站点【url="+baseUrl+"】,发生异常，e : " , e);
            }
            pn++;
        }
        return renovationCompanyUrlList;
    }


    /**
     * 在【装一网】根据装修公司地址发起预约
     * @param renovationCompanyUrl
     * @return
     */
    public static void getSimpleRenovationCompany(String renovationCompanyUrl,
                                                  String zbSd, String zbCommunity, String zbBuiltArea, String zbName, String zbTel) {

        //装修公司预约页面链接
        String subscribeUrl = renovationCompanyUrl + "/yylf.aspx";
        //装修公司提交预约订单链接
        String orderUrl = renovationCompanyUrl + "/Ajax/BidCity.ashx";

        Document doc = null;
        String zbComeFrom = "LF";
        String zbCompany = "";
        try {
            doc = Jsoup.connect(subscribeUrl)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36")
                    .timeout(5000).get();

            //装修公司-中文名称
            zbComeFrom = "LF";
            Elements divElements = doc.getElementsByTag("div");
            for (Element element : divElements) {
                String className = element.attr("class");
                if ("clear s_bor s_tag mar-b10".equals(className)) {
                    List<Node> childNodes = element.childNodes();
                    for (Node node : childNodes) {
                        String href = node.attr("href");
                        Pattern p = Pattern.compile(".*\\d+.*");
                        Matcher m = p.matcher(href);
                        if (m.matches()) {
                            zbComeFrom = node.childNode(0).toString() + zbComeFrom;
                        }
                    }
                    break;
                }
            }
            logger.info("装修公司-中文名称 zbComeFrom = " + zbComeFrom);

            //装修公司-编码
            zbCompany = "";
            Elements inputElements = doc.getElementsByTag("input");
            for (Element element : inputElements) {
                String id = element.attr("id");
                if ("deCompany".equals(id)) {
                    zbCompany = element.attr("value");
                    break;
                }
            }
            logger.info("装修公司-编码 zbCompany = " + zbCompany);

            //地区-编码
            if (zbSd == null || "".equals(zbSd)) {
                zbSd = "529";
            }

            //小区名字
            if (zbCommunity == null || "".equals(zbCommunity)) {
                zbCommunity = "蓝月清水湾";
            }

            //房屋面积
            if (zbBuiltArea == null || "".equals(zbBuiltArea)) {
                zbBuiltArea = "135";
            }

            //您的称呼
            if (zbName == null || "".equals(zbName)) {
                zbName = "蔡先生";
            }

            //联系电话
            if (zbTel == null || "".equals(zbTel)) {
                zbTel = "18685679555";
            }

//            Thread.sleep(60000);

            HttpsUtil httpsUtil = new HttpsUtil();
            Map<String, String> paramMap = Maps.newHashMap();
            paramMap.put("zbComeFrom", zbComeFrom);
            paramMap.put("zbCompany", zbCompany);
            paramMap.put("zbSd", zbSd);
            paramMap.put("zbCommunity", zbCommunity);
            paramMap.put("zbBuiltArea", zbBuiltArea);
            paramMap.put("zbName", zbName);
            paramMap.put("zbTel", zbTel);
            String resultStr = httpsUtil.post(orderUrl, paramMap);
            logger.info("向【"+zbComeFrom+"】发起预约装修，响应结果："+resultStr);

        } catch (Exception e) {
            logger.error("在【装一网】【"+zbComeFrom+"】发起预约装修,发生异常，e : " , e);
        }
    }


}

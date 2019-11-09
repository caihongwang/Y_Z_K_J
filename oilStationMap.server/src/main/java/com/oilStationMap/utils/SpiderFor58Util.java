package com.oilStationMap.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.oilStationMap.dao.WX_ContactDao;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 装一网 爬取站点 以及 发起预约
 */
public class SpiderFor58Util {

    public static final Logger logger = LoggerFactory.getLogger(SpiderFor58Util.class);

    public static WX_ContactDao wxContactDao = (WX_ContactDao)ApplicationContextUtils.getBeanByClass(WX_ContactDao.class);

    /**
     * 从58二手房爬取手机号并整合入库
     */
    public static void getContactFrom58ErShouFang() {
//        List<Map<String, String>> ipList = IpDaiLiUtil.getDaiLiIpList();            //获取代理IP
        List<Map<String, String>> ipList = Lists.newLinkedList();
        List<String> distinctList = Lists.newLinkedList();
        distinctList.add("trbj");   //铜仁碧江
        distinctList.add("wangshan");   //铜仁万山
        distinctList.add("songtao");   //铜仁松桃
        distinctList.add("sinan");   //铜仁思南
        distinctList.add("dejiang");   //铜仁德江
        distinctList.add("shiqian");   //铜仁石阡
        distinctList.add("jiangkou");   //铜仁江口
        distinctList.add("yanhe");   //铜仁沿河
        distinctList.add("yuping");   //铜仁玉屏
        distinctList.add("yinjiang");   //铜仁印江
        List<String> phoneList = Lists.newArrayList();
        List<String> ershoufang58UrlList = Lists.newArrayList();
        for(String distinct : distinctList){
            String contactNameFlag = "tr_" + distinct;
            //1.获取信息列表
            for(int pageNum = 1; pageNum <= 30; pageNum++){
                String ershoufang58PageUrl = "https://tr.58.com/"+distinct+"/ershoufang/pn"+pageNum+"/?PGTID=0d30000c-03e6-587c-c89d-e153307aa116&ClickID=1";
                try {
                    //获取代理IP地址
                    Map ipMap = Maps.newHashMap();
                    if(ipList != null && ipList.size() > 0){
                        ipMap = ipList.get(0);
                        ipList.remove(0);
                    }
                    String ip = ipMap.get("ip")!=null?ipMap.get("ip").toString():"";
                    int port = ipMap.get("port")!=null?Integer.parseInt(ipMap.get("port").toString()):0;
                    //爬取网页
                    Document ershoufang58PageDoc = null;
                    Random rand = new Random();
                    Integer requestTime = rand.nextInt(4) + 3;
                    requestTime = requestTime * 1000;
                    if(!"".equals(ip) && port != 0){
                        ershoufang58PageDoc = Jsoup.connect(ershoufang58PageUrl)
                                .proxy(ip, port)
                                .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36")
                                .timeout(15000).get();
                    } else {
                        ershoufang58PageDoc = Jsoup.connect(ershoufang58PageUrl)
                                .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36")
                                .timeout(15000).get();
                    }
                    Thread.sleep(requestTime);
                    if(ershoufang58PageDoc == null) {
                        logger.error("打开【二手房列表页面】失败，页面打不开或者被58同城对IP地址进行暂时封掉，ershoufang58PageUrl = " + ershoufang58PageUrl);
                        continue;
                    }
                    Elements liElements = ershoufang58PageDoc.getElementsByTag("li");          //获取a标签
                    for (Element element : liElements) {
                        String className = element.attr("class");
                        if ("sendsoj".equals(className)) {
                            List<Node> divNodes = element.childNodes();
                            for(Node divNode : divNodes){
                                String classNameTemp = divNode.attr("class");
                                if ("pic".equals(classNameTemp)) {
                                    List<Node> hrefNodes = divNode.childNodes();
                                    for(Node hrefNode : hrefNodes){
                                        String href = hrefNode.attr("href");
                                        if(href != null && !"".equals(href)){
                                            if(href.startsWith("https")){
                                                ershoufang58UrlList.add(href);
                                            } else {
                                                href = "https:" + href;
                                                ershoufang58UrlList.add(href);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    logger.error("打开【二手房列表页面】失败，页面打开超时或者为连接不上网络，ershoufang58PageUrl = " + ershoufang58PageUrl);
                }
            }
            //2.抓取手机号
            for(String ershoufang58Url : ershoufang58UrlList){
                try{
                    //获取代理IP地址
                    Map ipMap = Maps.newHashMap();
                    if(ipList != null && ipList.size() > 0){
                        ipMap = ipList.get(0);
                        ipList.remove(0);
                    }
                    String ip = ipMap.get("ip")!=null?ipMap.get("ip").toString():"";
                    int port = ipMap.get("port")!=null?Integer.parseInt(ipMap.get("port").toString()):0;
                    //爬取网页
                    Document ershoufang58Doc = null;
                    Random rand = new Random();
                    Integer requestTime = rand.nextInt(4) + 3;
                    requestTime = requestTime * 1000;
                    if(!"".equals(ip) && port != 0){
                        ershoufang58Doc = Jsoup.connect(ershoufang58Url)
                                .proxy(ip, port)
                                .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36")
                                .timeout(15000).get();
                    } else {
                        ershoufang58Doc = Jsoup.connect(ershoufang58Url)
                                .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36")
                                .timeout(15000).get();
                    }
                    Thread.sleep(requestTime);
                    if(ershoufang58Doc == null) {
                        logger.error("打开【二手房详情页面】失败，页面打不开或者被58同城对IP地址进行暂时封掉，ershoufang58Url = " + ershoufang58Url);
                        continue;
                    }
                    Elements pElements = ershoufang58Doc.getElementsByTag("p");          //获取a标签
                    for (Element element : pElements) {
                        String className = element.attr("class");
                        if("phone-num".equals(className)){
                            String phone = element.html();
                            if(!phoneList.contains(phone)){
                                phoneList.add(phone);
                            }
                        }
                    }
                } catch (Exception e) {
                    logger.error("打开【二手房详情页面】失败，页面打开超时或者为连接不上网络，ershoufang58Url = " + ershoufang58Url);
                }
            }
            //3.入库
            for (String phone : phoneList) {
                System.out.println("phone = " + phone);
                Map<String, Object> paramMap = Maps.newHashMap();
                paramMap.put("phone", phone);
                Integer num = wxContactDao.checkContactByPhone(paramMap);
                if(num != null && num > 0){     //该联系人已存在
                    continue;
                } else {                        //该联系人不存在，直接插入
                    //3.1.获取最大联系人的ID
                    paramMap.clear();
                    paramMap.put("name", contactNameFlag);
                    Integer maxId = wxContactDao.getMaxIdByName(paramMap);
                    if(maxId == null){
                        maxId = 0;
                    }
                    maxId++;
                    String name = contactNameFlag + "_" + maxId.toString();
                    //3.2.插入联系人
                    paramMap.clear();
                    paramMap.put("name", name);
                    paramMap.put("phone", phone);
                    paramMap.put("remark", contactNameFlag);
                    paramMap.put("createTime", TimestampUtil.getTimestamp());
                    paramMap.put("updateTime", TimestampUtil.getTimestamp());
                    wxContactDao.addContact(paramMap);
                }
            }
        }
        logger.info("phoneList size = " + phoneList.size());
        logger.info("ershoufang58UrlList size = " + ershoufang58UrlList.size());
    }


}

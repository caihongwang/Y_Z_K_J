package com.oilStationMap.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 从58二手房爬取手机号并整合入库
 */
public class SpiderFor58Util {

    public static final Logger logger = LoggerFactory.getLogger(SpiderFor58Util.class);

    public static String contactPath = "/opt/resourceOfOilStationMap/webapp/contact/";

    public static WX_ContactDao wxContactDao = (WX_ContactDao)ApplicationContextUtils.getBeanByClass(WX_ContactDao.class);

    /**
     * 从网络：５８同城、美团等网络进行爬取房产人员、美食店铺等联系方式
     */
    public static void getContactFromWeb() {
        getContactFrom58ErShouFang();           //58同城-房产-中介和个人-联系电话
        getContactFromMeiTuanMeiShi();          //美团-美食-商家-联系电话
    }

    /**
     * 从美团美食爬取手机号并整合入库
     */
    public static void getContactFromMeiTuanMeiShi() {
        //List<Map<String, String>> ipList = IpDaiLiUtil.getDaiLiIpList();            //获取代理IP
        List<Map<String, String>> ipList = Lists.newLinkedList();

        Map<String, Object> cityAndDistinctMap = Maps.newHashMap();

        List<String> trDistinctList = Lists.newLinkedList();        //铜仁下属地区名称
        trDistinctList.add("b7415");       //碧江
        trDistinctList.add("b7416");   //万山
        trDistinctList.add("b4015");    //松桃
        trDistinctList.add("b4010");      //思南
        trDistinctList.add("b4011");    //德江
        trDistinctList.add("b4009");    //石阡
        trDistinctList.add("b4008");   //江口
        trDistinctList.add("b4014");      //沿河
        trDistinctList.add("b4012");     //玉屏
        trDistinctList.add("b4013");   //印江
        cityAndDistinctMap.put("tr", trDistinctList);               //铜仁市 美食

        for (Map.Entry<String, Object> tempMap : cityAndDistinctMap.entrySet()) {
            List<String> phoneList = Lists.newArrayList();
            String cityName = tempMap.getKey();     //城市简称
            String cityNameFlag = cityName + "_ms";     //城市标识
            List<String> distinctList = (List<String>)tempMap.getValue();     //城市所属地区
            List<String> meiShiUrlList = Lists.newArrayList();
            for(String distinct : distinctList){
                String contactNameFlag = cityName + "_ms_" + distinct;
                //1.获取信息列表
                for(int pageNum = 1; pageNum <= 1; pageNum++){
                    String meiShi58PageUrl = "https://"+cityName+".meituan.com/meishi/"+distinct+"/pn"+pageNum+"/";
                    logger.info("contactNameFlag = " + contactNameFlag + " ， 第 " + pageNum + " 页, meiShi58PageUrl = " + meiShi58PageUrl);
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
                        Document meiShiMeiTuanPageDoc = null;
                        Random rand = new Random();
                        Integer requestTime = rand.nextInt(6) + 3;
                        requestTime = requestTime * 1000;
                        if(!"".equals(ip) && port != 0){
                            meiShiMeiTuanPageDoc = Jsoup.connect(meiShi58PageUrl)
                                    .proxy(ip, port)
                                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36")
                                    .timeout(15000).get();
                        } else {
                            meiShiMeiTuanPageDoc = Jsoup.connect(meiShi58PageUrl)
                                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36")
                                    .timeout(15000).get();
                        }
                        Thread.sleep(requestTime);
                        if(meiShiMeiTuanPageDoc == null) {
                            logger.error("打开【美食列表】失败，页面打不开或者被58同城对IP地址进行暂时封掉，meiShi58PageUrl = " + meiShi58PageUrl);
                            continue;
                        }

                        String meiShiMeiTuanPageHtmlStr = meiShiMeiTuanPageDoc.html();        //获取网页源码字符串
                        String[] tempArr_1 = meiShiMeiTuanPageHtmlStr.split("window._appState =");
                        if(tempArr_1 != null && tempArr_1.length >= 2){
                            String[] tempArr_2 = tempArr_1[1].split(";</script>");
                            if(tempArr_2 != null && tempArr_2.length >= 2){
                                String meiShiUrlJsonStr = tempArr_2[0].trim();
                                JSONObject meiShiUrlJSONObject = JSON.parseObject(meiShiUrlJsonStr);
                                if(meiShiUrlJSONObject != null && meiShiUrlJSONObject.size() > 0){
                                    JSONObject poiListsJSONObject = meiShiUrlJSONObject.getJSONObject("poiLists");
                                    if(poiListsJSONObject != null && poiListsJSONObject.size() > 0){
                                        JSONArray poiInfosJSONArray = poiListsJSONObject.getJSONArray("poiInfos");
                                        if(poiInfosJSONArray != null && poiInfosJSONArray.size() > 0){
                                            List<Map<String, Object>> poiInfosList = JSONObject.parseObject(poiInfosJSONArray.toJSONString(), List.class);
                                            if(poiInfosList != null && poiInfosList.size() > 0){
                                                for(Map<String, Object> poiInfosMap : poiInfosList){
                                                    Integer poiId = poiInfosMap.get("poiId")!=null?((Integer)poiInfosMap.get("poiId")):-1;
                                                    if(poiId != -1){
                                                        String href = "https://www.meituan.com/meishi/"+poiId.toString()+"/";
                                                        meiShiUrlList.add(href);
                                                    } else {
                                                        logger.error("打开【美食列表】失败，页面解析美食商家列表Json，获取poiId失败，meiShi58PageUrl = " + meiShi58PageUrl);
                                                        continue;
                                                    }
                                                }
                                            }
                                        } else {
                                            logger.error("打开【美食列表】失败，页面解析美食商家列表Json，获取poiInfos对象失败，meiShi58PageUrl = " + meiShi58PageUrl);
                                            continue;
                                        }
                                    } else {
                                        logger.error("打开【美食列表】失败，页面解析美食商家列表Json，获取poiLists对象失败，meiShi58PageUrl = " + meiShi58PageUrl);
                                        continue;
                                    }
                                } else {
                                    logger.error("打开【美食列表】失败，页面解析美食商家列表Json失败，meiShi58PageUrl = " + meiShi58PageUrl);
                                    continue;
                                }
                            } else {
                                logger.error("打开【美食列表】失败，拆分【;</script><script】失败，meiShi58PageUrl = " + meiShi58PageUrl);
                                continue;
                            }
                        } else {
                            logger.error("打开【美食列表】失败，拆分【window._appState =】失败，meiShi58PageUrl = " + meiShi58PageUrl);
                            continue;
                        }
                    } catch (Exception e) {
                        logger.error("打开【美食列表】失败，美食列表页面打开超时或者为连接不上网络，meiShi58PageUrl = " + meiShi58PageUrl);
                    }
                }
                logger.info("contactNameFlag = " + contactNameFlag + " ， 美团美食详情页面总数 ， meiShiUrlList.size() = " + meiShiUrlList.size());
                //2.抓取手机号
                int i = 0;
                for(String meiShiUrl : meiShiUrlList){
                    logger.info("contactNameFlag = " + contactNameFlag + " ， 美团美食详情页面, meiShiUrl = " + meiShiUrl);
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
                        Document meiShiDoc = null;
                        Random rand = new Random();
                        Integer requestTime = rand.nextInt(6) + 3;
                        requestTime = requestTime * 1000;
                        if(!"".equals(ip) && port != 0){
                            meiShiDoc = Jsoup.connect(meiShiUrl)
                                    .proxy(ip, port)
                                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36")
                                    .timeout(15000).get();
                        } else {
                            meiShiDoc = Jsoup.connect(meiShiUrl)
                                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36")
                                    .timeout(15000).get();
                        }
                        Thread.sleep(requestTime);
                        if(meiShiDoc == null) {
                            logger.error("打开【美食列表】失败，页面打不开或者被58同城对IP地址进行暂时封掉，meiShiUrl = " + meiShiUrl);
                            continue;
                        }

                        String meiShiDocHtmlStr = meiShiDoc.html();        //获取网页源码字符串
                        String[] tempArr_1 = meiShiDocHtmlStr.split("window._appState =");
                        if(tempArr_1 != null && tempArr_1.length >= 2){
                            String[] tempArr_2 = tempArr_1[1].split(";</script>");
                            if(tempArr_2 != null && tempArr_2.length >= 2){
                                String meiShiUrlJsonStr = tempArr_2[0].trim();
                                JSONObject meiShiUrlJSONObject = JSON.parseObject(meiShiUrlJsonStr);
                                if(meiShiUrlJSONObject != null && meiShiUrlJSONObject.size() > 0){
                                    JSONObject detailInfoJSONObject = meiShiUrlJSONObject.getJSONObject("detailInfo");
                                    if(detailInfoJSONObject != null && detailInfoJSONObject.size() > 0){
                                        String name = detailInfoJSONObject.get("name")!=null?detailInfoJSONObject.get("name").toString():"";
                                        String phone = detailInfoJSONObject.get("phone")!=null?detailInfoJSONObject.get("phone").toString():"";
                                        if(!"".equals(phone)){
                                            String maxContinueNumStr = getMaxContinueNumStr(phone);
                                            if(maxContinueNumStr.length() == 11){
                                                logger.info("contactNameFlag = " + contactNameFlag + " ， phone = " + maxContinueNumStr + " , phoneList.size() = " + phoneList.size());
                                                if(!phoneList.contains(maxContinueNumStr)){
                                                    phoneList.add(maxContinueNumStr);
                                                }
                                            } else {
                                                logger.info("contactNameFlag = " + contactNameFlag + " ， phone = " + phone + " , phoneList.size() = " + phoneList.size());
                                                if(!phoneList.contains(phone)){
                                                    phoneList.add(phone);
                                                }
                                            }
                                        } else {
                                            logger.error("打开【美食商家】失败，页面解析美食商家Json，获取phone对象失败，meiShiUrl = " + meiShiUrl);
                                            continue;
                                        }
                                    } else {
                                        logger.error("打开【美食商家】失败，页面解析美食商家Json，获取poiLists对象失败，meiShiUrl = " + meiShiUrl);
                                        continue;
                                    }
                                } else {
                                    logger.error("打开【美食商家】失败，页面解析美食商家Json，meiShiUrl = " + meiShiUrl);
                                    continue;
                                }
                            } else {
                                logger.error("打开【美食商家】失败，拆分【;</script><script】失败，meiShiUrl = " + meiShiUrl);
                                continue;
                            }
                        } else {
                            logger.error("打开【美食商家】失败，拆分【window._appState =】失败，meiShiUrl = " + meiShiUrl);
                            continue;
                        }
                    } catch (Exception e) {
                        logger.error("打开【美食商家】失败，美食商家页面打开超时或者为连接不上网络，meiShiUrl = " + meiShiUrl);
                    }
                    if(i == 4) {
                        break;
                    }
                    i++;
                }
                //3.入库
                for (String phone : phoneList) {
                    Map<String, Object> paramMap = Maps.newHashMap();
                    paramMap.put("phone", phone);
                    Integer num = wxContactDao.checkContactByPhone(paramMap);
                    if(num != null && num > 0){     //该联系人已存在
                        continue;
                    } else {                        //该联系人不存在，直接插入
                        //3.1.获取最大联系人的ID
                        Integer maxId = 0;
                        paramMap.clear();
                        paramMap.put("remark", contactNameFlag);
                        Map<String, Object> maxContactMap = wxContactDao.getMaxIdByName(paramMap);
                        if(maxContactMap != null && maxContactMap.size() > 0){
                            String maxContactName = maxContactMap.get("name")!=null?maxContactMap.get("name").toString():"";
                            if(!"".equals(maxContactName)){
                                String maxIdStr = maxContactName.substring(maxContactName.length() - 6);
                                maxId = Integer.parseInt(maxIdStr);
                                if(maxId == null){
                                    maxId = 0;
                                }
                            }
                        }
                        maxId++;
                        String maxIdStr = "000000" + maxId.toString();
                        maxIdStr = maxIdStr.substring(maxIdStr.length() - 6);
                        String name = contactNameFlag + "_" + maxIdStr;
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
            //4.整合数据库中数据变成vcf文件
            StringBuffer contact_stringBuffer = new StringBuffer();
            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("remark", cityNameFlag);           //根据城市名称获取联系人
            List<Map<String, Object>> contactList = wxContactDao.getAllContactList(paramMap);
            for(Map<String, Object> contactMap : contactList){
                String name = contactMap.get("name")!=null?contactMap.get("name").toString():"";
                String phone = contactMap.get("phone")!=null?contactMap.get("phone").toString():"";
                String remark = contactMap.get("remark")!=null?contactMap.get("remark").toString():"";
                //整理vcf格式
                contact_stringBuffer.append("BEGIN:VCARD").append("\n");
                contact_stringBuffer.append("VERSION:3.0").append("\n");
                contact_stringBuffer.append("PRODID:-//Apple Inc.//Mac OS X 10.13.6//EN").append("\n");
                contact_stringBuffer.append("N:").append(name).append("\n");
                contact_stringBuffer.append("TEL;type=CELL;type=VOICE;type=pref:").append(phone).append("\n");
                contact_stringBuffer.append("UID:9017CA64-35DA-4663-889B-5C20D19A4722").append("\n");
                contact_stringBuffer.append("X-ABUID:625B028A-8EC5-419C-8F86-E4F0409AC125:ABPerson").append("\n");
                contact_stringBuffer.append("END:VCARD").append("\n");
            }
            try {
                Date currentDate = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
                String vcfFilePath = contactPath + cityNameFlag + "_联系人_" + formatter.format(currentDate) + ".vcf";
                File vcfFile = new File(vcfFilePath);
                vcfFile.createNewFile();
                writeFileContent(vcfFilePath, contact_stringBuffer.toString());
            } catch (Exception e) {
                logger.info("将数据库中的联系人转换为vcf,保存文件时发生错误。 e : " + e);
            }
        }
        //5.爬虫已结束
        logger.info("美团-美食-店主手机号已爬取完成...");
        logger.info("美团-美食-店主手机号已爬取完成...");
        logger.info("美团-美食-店主手机号已爬取完成...");
        logger.info("美团-美食-店主手机号已爬取完成...");
        logger.info("美团-美食-店主手机号已爬取完成...");
        logger.info("美团-美食-店主手机号已爬取完成...");
    }

    /**
     * 从58二手房爬取手机号并整合入库
     */
    public static void getContactFrom58ErShouFang() {
        //List<Map<String, String>> ipList = IpDaiLiUtil.getDaiLiIpList();            //获取代理IP
        List<Map<String, String>> ipList = Lists.newLinkedList();

        Map<String, Object> cityAndDistinctMap = Maps.newHashMap();

        List<String> gyDistinctList = Lists.newLinkedList();        //贵阳下属地区名称
        gyDistinctList.add("nanming");       //南明
        gyDistinctList.add("jinyangxinqu");   //观山湖
        gyDistinctList.add("yunyan");    //云岩
        gyDistinctList.add("huaxi");      //花溪
        gyDistinctList.add("baiyunqv");    //白云
        gyDistinctList.add("wudang");    //乌当
        gyDistinctList.add("qingzhengy");   //清镇
        gyDistinctList.add("xiuwenxw");      //修文
        gyDistinctList.add("kaiyanggy");     //开阳
        gyDistinctList.add("xifenggy");   //息烽
        gyDistinctList.add("xiaohequ");   //小河经开区
        cityAndDistinctMap.put("gy", gyDistinctList);               //贵阳市 二手房

        List<String> zyDistinctList = Lists.newLinkedList();        //遵义下属地区名称
        zyDistinctList.add("honghuagangqu");       //红花岗
        zyDistinctList.add("huichuanqu");   //汇川
        zyDistinctList.add("bozhouqu");       //播州
        zyDistinctList.add("xinpuxinqu");      //新浦新区
        zyDistinctList.add("zunyixian");    //南白
        zyDistinctList.add("tongzixian");   //桐梓
        zyDistinctList.add("meitanxian");      //湄潭
        zyDistinctList.add("chishuishi");     //习水
        zyDistinctList.add("xishuix");   //习水
        zyDistinctList.add("zhenganxian");   //正安
        zyDistinctList.add("suiyangxian");   //绥阳
        zyDistinctList.add("zunyirenhuai");   //仁怀市
        zyDistinctList.add("daozhenxian");   //道真
        zyDistinctList.add("wuchuanxian");   //务川
        zyDistinctList.add("yuqingxian");   //余庆
        zyDistinctList.add("fenggangxian");   //凤冈
        cityAndDistinctMap.put("zy", zyDistinctList);               //遵义市 二手房

        List<String> qdnDistinctList = Lists.newLinkedList();        //黔东南下属地区名称
        qdnDistinctList.add("kaili");       //凯里
        qdnDistinctList.add("liping");       //黎平
        qdnDistinctList.add("huangping");       //黄平
        qdnDistinctList.add("zhenyuan");       //镇远
        qdnDistinctList.add("tianzhux");       //天柱
        qdnDistinctList.add("congjiang");       //从江
        qdnDistinctList.add("leishan");       //雷山
        qdnDistinctList.add("majiang");       //麻江
        qdnDistinctList.add("rongjiang");       //榕江
        qdnDistinctList.add("sansui");       //三穗
        qdnDistinctList.add("taijiangx");       //台江
        qdnDistinctList.add("jinpingx");       //锦屏
        qdnDistinctList.add("cengong");       //岑巩
        qdnDistinctList.add("shibing");       //施秉
        qdnDistinctList.add("danzhai");       //丹寨
        qdnDistinctList.add("jianhe");       //剑河
        qdnDistinctList.add("qdnzhoubian");       //黔东南周边
        cityAndDistinctMap.put("qdn", qdnDistinctList);               //黔东南 二手房

        List<String> qnDistinctList = Lists.newLinkedList();        //黔南下属地区名称
        qnDistinctList.add("duyun");       //都匀
        qnDistinctList.add("wengan");       //瓮安
        qnDistinctList.add("longli");       //龙里
        qnDistinctList.add("fuquan");       //福泉
        qnDistinctList.add("guiding");       //贵定
        qnDistinctList.add("dushan");       //独山
        qnDistinctList.add("huishui");       //惠水
        qnDistinctList.add("pingtang");       //平塘
        qnDistinctList.add("luodian");       //罗甸
        qnDistinctList.add("sandushuizu");       //三都
        qnDistinctList.add("libo");       //荔波
        qnDistinctList.add("changshun");       //长顺
        qnDistinctList.add("qiannanzb");       //黔南周边
        cityAndDistinctMap.put("qn", qnDistinctList);               //黔南 二手房

        List<String> lpsDistinctList = Lists.newLinkedList();        //六盘水下属地区名称
        lpsDistinctList.add("zhongshanq");       //钟山
        lpsDistinctList.add("panx");       //盘州
        lpsDistinctList.add("shuicheng");       //水城
        lpsDistinctList.add("liuzhi");       //六枝特
        lpsDistinctList.add("liupanshuizb");       //六盘水周边
        cityAndDistinctMap.put("lps", lpsDistinctList);               //六盘水 二手房

        List<String> bijieDistinctList = Lists.newLinkedList();        //毕节下属地区名称
        bijieDistinctList.add("bjqxg");       //七星关
        bijieDistinctList.add("qianxi");       //黔西
        bijieDistinctList.add("zhijin");       //织金
        bijieDistinctList.add("jinshax");       //金沙
        bijieDistinctList.add("dafang");       //大方
        bijieDistinctList.add("weining");       //威宁
        bijieDistinctList.add("nayong");       //纳雍
        bijieDistinctList.add("hezhang");       //赫章
        bijieDistinctList.add("bjbldj");       //百里杜鹃
        bijieDistinctList.add("bijiezhoubian");       //毕节周边
        cityAndDistinctMap.put("bijie", bijieDistinctList);               //毕节 二手房

        List<String> trDistinctList = Lists.newLinkedList();        //铜仁下属地区名称
        trDistinctList.add("trbj");       //碧江
        trDistinctList.add("wangshan");   //万山
        trDistinctList.add("songtao");    //松桃
        trDistinctList.add("sinan");      //思南
        trDistinctList.add("dejiang");    //德江
        trDistinctList.add("shiqian");    //石阡
        trDistinctList.add("jiangkou");   //江口
        trDistinctList.add("yanhe");      //沿河
        trDistinctList.add("yuping");     //玉屏
        trDistinctList.add("yinjiang");   //印江
        cityAndDistinctMap.put("tr", trDistinctList);               //铜仁市 二手房

        List<String> anshunDistinctList = Lists.newLinkedList();        //安顺下属地区名称
        anshunDistinctList.add("xixiuqu");       //西秀
        anshunDistinctList.add("pingbaxian");       //平坝
        anshunDistinctList.add("aszhenning");       //镇宁
        anshunDistinctList.add("pudingxian");       //普定县
        anshunDistinctList.add("jjkfqas");       //经济开发区
        anshunDistinctList.add("asziyun");       //紫云
        anshunDistinctList.add("asguanling");       //关岭
        anshunDistinctList.add("anshunzhoubian");       //安顺周边
        cityAndDistinctMap.put("anshun", anshunDistinctList);               //安顺 二手房

        List<String> qxnDistinctList = Lists.newLinkedList();        //黔西南下属地区名称
        qxnDistinctList.add("xingyi");       //兴义
        qxnDistinctList.add("xingren");       //兴仁
        qxnDistinctList.add("anlong");       //安龙
        qxnDistinctList.add("zhenfeng");       //贞丰
        qxnDistinctList.add("puan");       //普安
        qxnDistinctList.add("qinglongx");       //晴隆
        qxnDistinctList.add("ceheng");       //册亨
        qxnDistinctList.add("wangmo");       //望谟
        qxnDistinctList.add("qianxinanzb");       //黔西南周边
        cityAndDistinctMap.put("qxn", qxnDistinctList);               //黔西南 二手房

        List<String> renhuaishiDistinctList = Lists.newLinkedList();        //仁怀市下属地区名称
        renhuaishiDistinctList.add("yanjinjiedao");       //盐津
        renhuaishiDistinctList.add("zhongshujiedao");       //中枢
        renhuaishiDistinctList.add("lubanjiedao");       //鲁班
        renhuaishiDistinctList.add("canglongjiedao");       //苍龙
        renhuaishiDistinctList.add("tanchangjiedao");       //坛厂
        renhuaishiDistinctList.add("renhuaishiqita");       //其他
        cityAndDistinctMap.put("renhuaishi", renhuaishiDistinctList);               //仁怀市 二手房

        List<String> qingzhenDistinctList = Lists.newLinkedList();        //清镇下属地区名称
        qingzhenDistinctList.add("qingzhencq");       //城区
        qingzhenDistinctList.add("qingzhenqt");       //其他
        cityAndDistinctMap.put("qingzhen", qingzhenDistinctList);               //清镇 二手房

        for (Map.Entry<String, Object> tempMap : cityAndDistinctMap.entrySet()) {
            List<String> phoneList = Lists.newArrayList();
            String cityName = tempMap.getKey();     //城市简称
            String cityNameFlag = cityName + "_esf";     //城市标识
            List<String> distinctList = (List<String>)tempMap.getValue();     //城市所属地区
            List<String> ershoufang58UrlList = Lists.newArrayList();
            for(String distinct : distinctList){
                String contactNameFlag = cityName + "_esf_" + distinct;
                //1.获取信息列表
                for(int pageNum = 1; pageNum <= 30; pageNum++){
                    String ershoufang58PageUrl = "https://"+cityName+".58.com/"+distinct+"/ershoufang/pn"+pageNum+"/?PGTID=0d30000c-03e6-587c-c89d-e153307aa116&ClickID=1";
                    logger.info("contactNameFlag = " + contactNameFlag + " ， 第 " + pageNum + " 页, ershoufang58PageUrl = " + ershoufang58PageUrl);
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
                        Integer requestTime = rand.nextInt(6) + 3;
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
                logger.info("contactNameFlag = " + contactNameFlag + " ， 58二手房详情页面总数 ， ershoufang58UrlList.size() = " + ershoufang58UrlList.size());
                //2.抓取手机号
                int i = 0;
                for(String ershoufang58Url : ershoufang58UrlList){
                    logger.info("contactNameFlag = " + contactNameFlag + " ， 58二手房详情页面, ershoufang58Url = " + ershoufang58Url);
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
                        Integer requestTime = rand.nextInt(6) + 3;
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
                                logger.info("contactNameFlag = " + contactNameFlag + " ， phone = " + phone + " , phoneList.size() = " + phoneList.size());
                                if(!phoneList.contains(phone)){
                                    phoneList.add(phone);
                                }
                            }
                        }
                    } catch (Exception e) {
                        logger.error("打开【二手房详情页面】失败，页面打开超时或者为连接不上网络，ershoufang58Url = " + ershoufang58Url);
                    }
//                    if(i == 4) {
//                        break;
//                    }
//                    i++;
                }
                //3.入库
                for (String phone : phoneList) {
                    Map<String, Object> paramMap = Maps.newHashMap();
                    paramMap.put("phone", phone);
                    Integer num = wxContactDao.checkContactByPhone(paramMap);
                    if(num != null && num > 0){     //该联系人已存在
                        continue;
                    } else {                        //该联系人不存在，直接插入
                        //3.1.获取最大联系人的ID
                        Integer maxId = 0;
                        paramMap.clear();
                        paramMap.put("remark", contactNameFlag);
                        Map<String, Object> maxContactMap = wxContactDao.getMaxIdByName(paramMap);
                        if(maxContactMap != null && maxContactMap.size() > 0){
                            String maxContactName = maxContactMap.get("name")!=null?maxContactMap.get("name").toString():"";
                            if(!"".equals(maxContactName)){
                                String maxIdStr = maxContactName.substring(maxContactName.length() - 6);
                                maxId = Integer.parseInt(maxIdStr);
                                if(maxId == null){
                                    maxId = 0;
                                }
                            }
                        }
                        maxId++;
                        String maxIdStr = "000000" + maxId.toString();
                        maxIdStr = maxIdStr.substring(maxIdStr.length() - 6);
                        String name = contactNameFlag + "_" + maxIdStr;
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
            //4.整合数据库中数据变成vcf文件
            StringBuffer contact_stringBuffer = new StringBuffer();
            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("remark", cityNameFlag);           //根据城市名称获取联系人
            List<Map<String, Object>> contactList = wxContactDao.getAllContactList(paramMap);
            for(Map<String, Object> contactMap : contactList){
                String name = contactMap.get("name")!=null?contactMap.get("name").toString():"";
                String phone = contactMap.get("phone")!=null?contactMap.get("phone").toString():"";
                String remark = contactMap.get("remark")!=null?contactMap.get("remark").toString():"";
                //整理vcf格式
                contact_stringBuffer.append("BEGIN:VCARD").append("\n");
                contact_stringBuffer.append("VERSION:3.0").append("\n");
                contact_stringBuffer.append("PRODID:-//Apple Inc.//Mac OS X 10.13.6//EN").append("\n");
                contact_stringBuffer.append("N:").append(name).append("\n");
                contact_stringBuffer.append("TEL;type=CELL;type=VOICE;type=pref:").append(phone).append("\n");
                contact_stringBuffer.append("UID:9017CA64-35DA-4663-889B-5C20D19A4722").append("\n");
                contact_stringBuffer.append("X-ABUID:625B028A-8EC5-419C-8F86-E4F0409AC125:ABPerson").append("\n");
                contact_stringBuffer.append("END:VCARD").append("\n");
            }
            try {
                Date currentDate = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
                String vcfFilePath = contactPath + cityNameFlag + "_联系人_" + formatter.format(currentDate) + ".vcf";
                File vcfFile = new File(vcfFilePath);
                vcfFile.createNewFile();
                writeFileContent(vcfFilePath, contact_stringBuffer.toString());
            } catch (Exception e) {
                logger.info("将数据库中的联系人转换为vcf,保存文件时发生错误。 e : " + e);
            }
        }
        //5.爬虫已结束
        logger.info("58同城-二手房-房主手机号已爬取完成...");
        logger.info("58同城-二手房-房主手机号已爬取完成...");
        logger.info("58同城-二手房-房主手机号已爬取完成...");
        logger.info("58同城-二手房-房主手机号已爬取完成...");
        logger.info("58同城-二手房-房主手机号已爬取完成...");
        logger.info("58同城-二手房-房主手机号已爬取完成...");
    }

    public static boolean writeFileContent(String filepath, String newstr) throws IOException {
        Boolean bool = false;
        String filein = newstr + "\r\n";//新写入的行，换行
        String temp = "";

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileOutputStream fos = null;
        PrintWriter pw = null;
        try {
            File file = new File(filepath);//文件路径(包括文件名称)
            //将文件读入输入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buffer = new StringBuffer();

            //文件原有内容
            for (int i = 0; (temp = br.readLine()) != null; i++) {
                buffer.append(temp);
                // 行与行之间的分隔符 相当于“\n”
                buffer = buffer.append(System.getProperty("line.separator"));
            }
            buffer.append(filein);

            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buffer.toString().toCharArray());
            pw.flush();
            bool = true;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            //不要忘记关闭
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return bool;
    }

    /**
     * 输出str中里连续最长的数字串
     * @return
     */
    public static String getMaxContinueNumStr(String str){
        String maxContinueNumStr = "";
        int count = 0;
        char [] arr = str.toCharArray();
        for (int i= 0 ;i<arr.length;i++){
            if('0'<=arr[i] && '9'>= arr[i]){//当前的是数字
                count = 1;//初始化计算器
                int index = i;//在后面的循环存储截至索引
                for(int j=i+1;j<arr.length;j++){
                    if('0'<=arr[j] && '9'>= arr[j]) {
                        count++;
                        index =j;
                    } else {
                        break;
                    }
                }
                if(count > maxContinueNumStr.length()){
                    maxContinueNumStr = str.substring(i, index+1);
                }
            }else {
                continue;
            }
        }
        return maxContinueNumStr;
    }

}

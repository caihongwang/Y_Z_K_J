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
public class SpiderForMeiTuanUtil {

    public static final Logger logger = LoggerFactory.getLogger(SpiderForMeiTuanUtil.class);

    public static String contactPath = "/opt/resourceOfOilStationMap/webapp/contact/";

    public static WX_ContactDao wxContactDao = (WX_ContactDao)ApplicationContextUtils.getBeanByClass(WX_ContactDao.class);

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

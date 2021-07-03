package com.automation.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;


public class IpUtil {

    public static final Logger logger = LoggerFactory.getLogger(IpUtil.class);

    public static String jiSuDaiLiUrl = "http://www.superfastip.com/welcome/freeip/";          //极速代理IP
    public static String kuaiDaiLiUrl = "https://www.kuaidaili.com/free/inha/";                //快代理IP
    public static String xiCiDaiLiUrl = "https://www.xicidaili.com/nn";                        //西刺代理IP
    public static String xiLaDaiLiUrl = "http://www.xiladaili.com/gaoni/";                     //西拉代理IP

    public static List<Map<String, String>> ipList = Lists.newArrayList();

    /**
     * 测试本机端口是否被使用
     *
     * @param port
     * @return
     */
    public static boolean isLocalPortUsing(int port) {
        boolean flag = false;
        try {
            //如果该端口还在使用则返回true,否则返回false,127.0.0.1代表本机
            flag = isPortUsing("127.0.0.1", port);
        } catch (Exception e) {

        }
        return flag;
    }

    public static String getLocalHost() {
        String localHost = "127.0.0.1";
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress ip = (InetAddress) addresses.nextElement();
                    if (ip != null
                            && ip instanceof Inet4Address
                            && !ip.isLoopbackAddress() //loopback地址即本机地址，IPv4的loopback范围是127.0.0.0 ~ 127.255.255.255
                            && ip.getHostAddress().indexOf(":") == -1) {
                        localHost = ip.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println("本机的IP = " + localHost);
        return localHost;
    }

    public static void main(String[] args) {
        IpUtil.getLocalHost();
    }

    /***
     * 测试主机Host的port端口是否被使用
     * @param host
     * @param port
     */
    public static boolean isPortUsing(String host, int port) {
        boolean flag = false;
        try {
            InetAddress address = InetAddress.getByName(host);
            Socket socket = new Socket(address, port);  //建立一个Socket连接
            flag = true;
        } catch (Exception e) {

        }
        return flag;
    }

    /**
     * 获取免费的IP，以便代理使用
     */
    public static List<Map<String, String>> getDaiLiIpList() {
        //极速代理IP
        for (int i = 1; i <= 10; i++) {
            String getIpUrl = jiSuDaiLiUrl + i;
            try {
                //解析页面的数据
                Thread.sleep(2000);//休眠2秒
                Document document = Jsoup.connect(getIpUrl)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36")
                        .timeout(6000).get();
                Elements items = document.select(".table").get(1).select("tbody").get(0).select("tr");
                for (int j = 0; j < items.size(); j++) {        //每页显示20条数据  从第一条开始
                    Elements elements = items.get(j).select("td");
                    String ip = elements.get(0).text();//IP
                    String port = elements.get(1).text();//端口
                    Map<String, String> ipMap = Maps.newHashMap();
                    ipMap.put("ip", ip);
                    ipMap.put("port", port);
                    ipList.add(ipMap);
                }
            } catch (Exception e) {
                logger.error("通过【极速IP代理】获取免费IP失败，getIpUrl = " + getIpUrl);
            }
        }
        //快代理IP
        for (int i = 1; i <= 200; i++) {        //15* 200   3000条
            String getIpUrl = kuaiDaiLiUrl + i;
            try {
                Thread.sleep(2000);//休眠2秒
                Document document = Jsoup.connect(getIpUrl)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36")
                        .timeout(6000).get();
                Elements items = document.select(".table").get(0).select("tbody").get(0).select("tr");
                for (int j = 0; j < items.size(); j++) {            //每页显示20条数据  从第一条开始
                    Elements elements = items.get(j).select("td");
                    String ip = elements.get(0).text();//IP
                    String port = elements.get(1).text();//端口
                    Map<String, String> ipMap = Maps.newHashMap();
                    ipMap.put("ip", ip);
                    ipMap.put("port", port);
                    ipList.add(ipMap);
                }
            } catch (Exception e) {
                logger.error("通过【快代理IP】获取免费IP失败，getIpUrl = " + getIpUrl);
            }
        }
        //西刺代理IP
        for (int i = 1; i <= 20; i++) {         //取20页数据
            String getIpUrl = xiCiDaiLiUrl + "/" + i;
            try {
                //解析页面的数据
                Thread.sleep(2000);//休眠2秒
                Document document = Jsoup.connect(getIpUrl)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36")
                        .timeout(6000).get();
                Elements items = document.select("#ip_list").get(0).select("tr");
                for (int j = 1; j < items.size(); j++) {//每页显示101条数据  从第一条开始
                    Elements elements = items.get(j).select("td");
                    String ip = elements.get(1).text();//IP
                    String port = elements.get(2).text();//端口
                    Map<String, String> ipMap = Maps.newHashMap();
                    ipMap.put("ip", ip);
                    ipMap.put("port", port);
                    ipList.add(ipMap);
                }
            } catch (Exception e) {
                logger.error("通过【西刺代理IP】获取免费IP失败，getIpUrl = " + getIpUrl);
            }
        }
        //西拉代理IP
        for (int i = 1; i <= 20; i++) {         //50* 20   1000条
            String getIpUrl = xiLaDaiLiUrl + i + "/";
            try {
                //解析页面的数据
                Thread.sleep(2000);//休眠2秒
                Document document = Jsoup.connect(getIpUrl)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36")
                        .timeout(6000).get();
                Elements items = document.select("table").select("tbody").select("tr");
                for (int j = 0; j < items.size(); j++) {//每页显示20条数据  从第一条开始
                    Elements elements = items.get(j).select("td");
                    String el = elements.get(0).text();
                    String ip = el.substring(0, el.indexOf(":"));//IP
                    String port = el.substring(el.indexOf(":") + 1);//端口
                    Map<String, String> ipMap = Maps.newHashMap();
                    ipMap.put("ip", ip);
                    ipMap.put("port", port);
                    ipList.add(ipMap);
                }
            } catch (Exception e) {
                logger.error("通过【西拉代理IP】获取免费IP失败，getIpUrl = " + getIpUrl);
            }
        }
        return ipList;
    }
}

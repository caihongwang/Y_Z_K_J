package com.oilStationMap.config;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.oilStationMap.utils.CommandUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class GlobalVariableConfig {

    @Value("${spring.profiles.active}")
    private String useEnvironmental;

    @Value("${spring.appiumPortListStr}")
    private String appiumPortListStr;

    @Value("${spring.stf_publicIp}")
    private String stf_publicIp;

    public static List<String> appiumPortList = Lists.newArrayList();

    /**
     * 初始化 全局变量
     * 初始化 启动服务：appium、rethinkdb、rethinkdb、stf
     */
    @PostConstruct
    public void initGlobalVariableAndServer() {
        appiumPortList = new ArrayList<>(Arrays.asList(appiumPortListStr.split(",")));
        //开发环境，启动服务：appium、rethinkdb、rethinkdb、stf
        if ("develop".equals(useEnvironmental)) {
            for(String appiumPort : appiumPortList){
                System.out.println("端口号为【"+appiumPort+"】的【appium】服务已启动....");
                StarAppiumThread starAppiumThread = new StarAppiumThread(appiumPort);
                Thread thread = new Thread(starAppiumThread);
                thread.start();
            }

            RethinkdbThread rethinkdbThread = new RethinkdbThread("");
            Thread A_thread = new Thread(rethinkdbThread);
            A_thread.start();
            System.out.println("【rethinkdb】服务已启动，即将等待15秒，确保rethinkdb服务完全启动成功....");

            try{
                Thread.sleep(15000);
            } catch (Exception e) {

            }

            StfThread stfThread = new StfThread(stf_publicIp);
            Thread B_thread = new Thread(stfThread);
            B_thread.start();
            System.out.println("【stf】服务已启动....");
        }
    }

    public class StarAppiumThread implements Runnable {
        private String appiumPort;

        public StarAppiumThread(String appiumPort) {
            this.appiumPort = appiumPort;
        }

        public void run() {
            try {
                String commondStr = "appium -p " + appiumPort + " -bp " + (Integer.parseInt(appiumPort) + 1) + " --session-override --command-timeout 600";
                CommandUtil.run(new String[]{"/bin/sh", "-c", commondStr});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class RethinkdbThread implements Runnable {
        private String rethinkdb;

        public RethinkdbThread(String rethinkdb) {
            this.rethinkdb = rethinkdb;
        }

        public void run() {
            try {
                String commondStr = "rethinkdb";
                CommandUtil.run(new String[]{"/bin/sh", "-c", commondStr});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class StfThread implements Runnable {
        private String stf_publicIp;

        public StfThread(String stf_publicIp) {
            this.stf_publicIp = stf_publicIp;
        }

        public void run() {
            try {
                String commondStr = "stf local --public-ip " + stf_publicIp;
                CommandUtil.run(new String[]{"/bin/sh", "-c", commondStr});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return "GlobalVariableConfig = " + JSON.toJSONString(this);
    }
}

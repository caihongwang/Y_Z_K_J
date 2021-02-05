package com.oilStationMap.config;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.oilStationMap.utils.CommandUtil;
import com.oilStationMap.utils.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class GlobalVariableConfig {

    @Value("${spring.profiles.active}")
    private String useEnvironmental;

    @Value("${spring.appiumPortListStr}")
    private String appiumPortListStr;

    @Value("${spring.defaultCommodPath}")
    private String defaultCommodPath;

    @Value("${spring.thePublicIp}")
    private String thePublicIp;

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
            for (String appiumPort : appiumPortList) {
                System.out.println("【appium】服务 端口号为【" + appiumPort + "】已启动....");
                StarAppiumThread starAppiumThread = new StarAppiumThread(appiumPort);
                Thread thread = new Thread(starAppiumThread);
                thread.start();
            }

            RethinkdbThread rethinkdbThread = new RethinkdbThread("");
            Thread A_thread = new Thread(rethinkdbThread);
            A_thread.start();
            System.out.println("【rethinkdb】服务已启动，即将等待15秒，确保rethinkdb服务完全启动成功....");

            try {
                Thread.sleep(15000);
            } catch (Exception e) {

            }

            StfThread stfThread = new StfThread(thePublicIp);
            Thread B_thread = new Thread(stfThread);
            B_thread.start();
            System.out.println("【stf】服务 IP为【" + thePublicIp + "】已启动....");
        }
    }

    public class StarAppiumThread implements Runnable {
        private String appiumPort;

        public StarAppiumThread(String appiumPort) {
            this.appiumPort = appiumPort;
        }

        public void run() {
            String source_commondFilePath = defaultCommodPath + "/1.Appium_start.sh";
            String temp_commondFilePath = defaultCommodPath + "/1.Appium_start_" + appiumPort + ".sh";
            File temp_commondFile = new File(temp_commondFilePath);
            if (temp_commondFile.exists()) {
                temp_commondFile.delete();
            }
            try {
                FileUtil.copyFile(source_commondFilePath, temp_commondFilePath);
                FileUtil.replaceStrInFile(temp_commondFilePath, "thePort", appiumPort);
                FileUtil.replaceStrInFile(temp_commondFilePath, "theBakPort", (Integer.parseInt(appiumPort) + 1 + ""));
                CommandUtil.run("sh " + temp_commondFilePath);
//                String commondStr = "appium -p " + appiumPort + " -bp " + (Integer.parseInt(appiumPort) + 1) + " --session-override --command-timeout 600";
//                String commondStr = "node . -p " + appiumPort + " -bp " + (Integer.parseInt(appiumPort) + 1) + " --session-override --command-timeout 600";
//                CommandUtil.run(new String[]{"/bin/sh", "-c", commondStr});
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
//                tempFile.delete();
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
//                String commondStr = "rethinkdb";
//                CommandUtil.run(new String[]{"/bin/sh", "-c", commondStr});
                String commondFilePath = defaultCommodPath + "/3.Rethinkdb_start.sh";
                CommandUtil.run("sh " + commondFilePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class StfThread implements Runnable {
        private String thePublicIp;

        public StfThread(String thePublicIp) {
            this.thePublicIp = thePublicIp;
        }

        public void run() {
            String source_commondFilePath = defaultCommodPath + "/4.STF_start.sh";
            String temp_commondFilePath = defaultCommodPath + "/4.STF_start_" + thePublicIp + ".sh";
            File temp_commondFile = new File(temp_commondFilePath);
            if (temp_commondFile.exists()) {
                temp_commondFile.delete();
            }
            try {
                FileUtil.copyFile(source_commondFilePath, temp_commondFilePath);
                FileUtil.replaceStrInFile(temp_commondFilePath, "thePublicIp", thePublicIp);
                CommandUtil.run("sh " + temp_commondFilePath);
//                String commondStr = "stf local --public-ip " + stf_publicIp;
//                CommandUtil.run(new String[]{"/bin/sh", "-c", commondStr});
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
//                tempFile.delete();
            }
        }
    }

    @Override
    public String toString() {
        return "GlobalVariableConfig = " + JSON.toJSONString(this);
    }
}

*/10 * * * * /bin/bash /opt/defaultCommod/1.Appium_start_4723.sh
*/10 * * * * /bin/bash /opt/defaultCommod/1.Appium_start_4725.sh
*/10 * * * * /bin/bash /opt/defaultCommod/1.Appium_start_4727.sh
*/10 * * * * /bin/bash /opt/defaultCommod/1.Appium_start_4729.sh

*/10 * * * * /bin/bash /opt/defaultCommod/3.Rethinkdb_start.sh
*/10 * * * * /bin/bash /opt/defaultCommod/4.STF_start_192.168.43.181.sh
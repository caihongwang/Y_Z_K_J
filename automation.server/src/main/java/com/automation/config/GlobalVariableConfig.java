package com.automation.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.automation.service.Automation_DicService;
import com.automation.service.Automation_WxService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.automation.dto.ResultDTO;
import com.automation.utils.CommandUtil;
import com.automation.utils.FileUtil;
import com.automation.utils.IpUtil;
import com.automation.utils.wei_xin.sendFriendCircle.SendFriendCircleUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Configuration
public class GlobalVariableConfig {

    @Value("${spring.profiles.active}")
    private String useEnvironmental;

    @Value("${spring.imgFormatStr}")
    private String imgFormatStr;

    @Value("${spring.appiumPortStr}")
    private String appiumPortStr;

    @Value("${spring.defaultCommodPath}")
    private String defaultCommodPath;

    @Value("${spring.theStfIp}")
    private String theStfIp;

    @Value("${spring.theStfPort}")
    private String theStfPort;

    @Value("${spring.theRethinkdbPort}")
    private String theRethinkdbPort;

    @Autowired
    private Automation_DicService automation_DicService;

    @Autowired
    private Automation_WxService automation_WxService;

    @Autowired
    private SendFriendCircleUtils sendFriendCircleUtils;

    public static Map<String, Map<String, String>> appiumPortMap = Maps.newHashMap();       //appium端口使用情况

    public static List<String> imgFormatList = Lists.newArrayList();

    /**
     * 初始化 全局变量
     * 初始化 启动服务：appium、rethinkdb、rethinkdb、stf
     */
    @PostConstruct
    public void initGlobalVariableAndServer() {
        imgFormatList = Arrays.asList(imgFormatStr.split(","));
        //整理 appium 端口号，【记录已启动的 appium 端口号】 同时 【记录未启动的 appium 端口号】
        Map<String, Map<String, String>> appiumPortMap_ToBeStart = Maps.newHashMap();
        Integer androidDeviceNum = 1;
        try {
            androidDeviceNum = CommandUtil.getAndroidDeviceNum();        //获取当前Android设备的在线数量，来决定启动多少appium端口
        } catch (Exception e) {
            androidDeviceNum = 1;
        } finally {
            Integer defaultAppiumPort = 4723;       //默认的appium端口
            for (int i = 0; i < androidDeviceNum; i++) {    //4723 4725 4727
                String appiumPort = (defaultAppiumPort + i * 2) + "";
                Map<String, String> appiumPortDetailMap = Maps.newHashMap();
                if(IpUtil.isLocalPortUsing(Integer.parseInt(appiumPort))){          //确认端口号被使用，才加入全局变量，等待使用
                    if(appiumPortMap.get(appiumPort) != null && appiumPortMap.get(appiumPort).size() > 0){
                        appiumPortDetailMap = appiumPortMap.get(appiumPort);
                    }
                    appiumPortMap.put(appiumPort, appiumPortDetailMap);
                } else {
                    appiumPortMap.remove(appiumPort);
                    appiumPortMap_ToBeStart.put(appiumPort, appiumPortDetailMap);
                }
            }
        }

        //开发环境，启动服务：appium、rethinkdb、rethinkdb、stf
        if ("develop".equals(useEnvironmental)) {
            for(Map.Entry<String, Map<String, String>> entry: appiumPortMap_ToBeStart.entrySet()){
                String appiumPort = entry.getKey();
                if(!IpUtil.isLocalPortUsing(Integer.parseInt(appiumPort))){
                    System.out.println("【appium】服务 端口号为【" + appiumPort + "】已启动....");
                    StarAppiumThread starAppiumThread = new StarAppiumThread(appiumPort);
                    Thread A_thread = new Thread(starAppiumThread);
                    A_thread.start();
                }
            }

            if(!IpUtil.isLocalPortUsing(Integer.parseInt(theRethinkdbPort))){
                try {
                    RethinkdbThread rethinkdbThread = new RethinkdbThread("");
                    Thread B_thread = new Thread(rethinkdbThread);
                    B_thread.start();
                    Thread.sleep(15000);
                } catch (Exception e) {

                }
                System.out.println("【rethinkdb】服务 启动成功，即将等待15秒，确保rethinkdb服务完全启动成功....");
            } else {
                System.out.println("【rethinkdb】服务 已启动，无需再次启动....");
            }

            if(!IpUtil.isLocalPortUsing(Integer.parseInt(theStfPort))){
                StfThread stfThread = new StfThread(theStfIp);
                Thread C_thread = new Thread(stfThread);
                C_thread.start();
                System.out.println("【stf】服务 IP为【" + theStfIp + "】启动成功....");
            } else {
                System.out.println("【stf】服务 IP为【" + theStfIp + "】已启动，无需再次启动....");
            }

            Demonstrate_4_SendFriendCircleThread demonstrate_4_SendFriendCircleThread = new Demonstrate_4_SendFriendCircleThread();
            Thread D_thread = new Thread(demonstrate_4_SendFriendCircleThread);
            D_thread.start();
            System.out.println("【演示模式：发送朋友圈】已启动，无需再次启动....");
        }
    }


    /**
     * 根据设备名称 获取 appium端口
     * @return
     */
    public synchronized static String getAppiumPort(String action, String deviceName) throws Exception{
        String appiumPort = null;
        //首先检测当前设备是否正在使用appoium端口号，即是否正在使用
        for(Map.Entry<String, Map<String, String>> entry: appiumPortMap.entrySet()){
            Map<String, String> appiumPortDetailMap = entry.getValue();
            String theAction = appiumPortDetailMap.get("action");           //appium端口-正在使用的自动化操作行为
            String theDeviceName = appiumPortDetailMap.get("deviceName");   //appium端口=正在使用的自动化操作设备
            if(deviceName.equals(theDeviceName)){   //设备正在是使用
                if(action.equals(theAction)){           //正在操作action
                    appiumPort = entry.getKey();
                } else {
                    appiumPort = "当前设备【"+deviceName+"】正在进行操作【"+action+"】自动化操作，请稍后再试.";
                    throw new Exception(appiumPort);
                }
            }
        }
        if(StringUtils.isEmpty(appiumPort)){
            //在检测是否存在可用的appium端口号
            for(Map.Entry<String, Map<String, String>> entry: appiumPortMap.entrySet()){
                appiumPort = entry.getKey();
                Map<String, String> appiumPortDetailMap = entry.getValue();
                if(appiumPortDetailMap.size() <= 0){
                    appiumPortDetailMap.put("action", action);
                    appiumPortDetailMap.put("deviceName", deviceName);
                    appiumPortMap.put(appiumPort, appiumPortDetailMap);
                    break;
                }
            }
        }
        if(StringUtils.isEmpty(appiumPort)){
            appiumPort = "当前设备【"+deviceName+"】准备操作【"+action+"】自动化操作，但是没有空闲的appium端口号，请稍后再试.";
            throw new Exception(appiumPort);
        }
        return appiumPort;
    }

    /**
     * 回收当前的端口号
     * @param appiumPort
     */
    public synchronized static void recoveryAppiumPort(String appiumPort){
        if(!StringUtils.isEmpty(appiumPort)){
            Map<String, String> appiumPortDetailMap = appiumPortMap.get(appiumPort);
            appiumPortDetailMap.clear();
            appiumPortMap.put(appiumPort, appiumPortDetailMap);
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
        private String theStfIp;

        public StfThread(String theStfIp) {
            this.theStfIp = theStfIp;
        }

        public void run() {
            String source_commondFilePath = defaultCommodPath + "/4.STF_start.sh";
            String temp_commondFilePath = defaultCommodPath + "/4.STF_start_" + theStfIp + ".sh";
            File temp_commondFile = new File(temp_commondFilePath);
            if (temp_commondFile.exists()) {
                temp_commondFile.delete();
            }
            try {
                FileUtil.copyFile(source_commondFilePath, temp_commondFilePath);
                FileUtil.replaceStrInFile(temp_commondFilePath, "theStfIp", theStfIp);
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

    /**
     * 演示模式：发送朋友圈
     */
    public class Demonstrate_4_SendFriendCircleThread implements Runnable {

        public void run() {
            if(sendFriendCircleUtils.checkNewDevicesConnected()){
                System.out.println("有新设备接入电脑了，即将启动【演示模式】的【发送朋友圈】....");
                //直接从现有的数据库中获取数据启动-发布朋友圈
                Map<String, Object> paramMap = Maps.newHashMap();
                List<String> nickNameList = Lists.newArrayList();
                paramMap.put("dicType", "sendFriendCircle");
                ResultDTO resultDTO = automation_DicService.getSimpleDicByCondition(paramMap);
                if (resultDTO != null && resultDTO.getResultList() != null && resultDTO.getResultList().size() > 0) {
                    for (Map<String, String> sendFriendCircleMap : resultDTO.getResultList()) {
                        nickNameList.add(sendFriendCircleMap.get("dicCode"));
                    }
                }
                paramMap.clear();
                LinkedList<String> currentDateList = Lists.newLinkedList();
                currentDateList.add(new SimpleDateFormat("yyyy-MM-dd HH").format(new Date()));
                paramMap.put("currentDateListStr", JSONObject.toJSONString(currentDateList));
                paramMap.put("nickNameListStr", JSONObject.toJSONString(nickNameList));
                automation_WxService.sendFriendCircle(paramMap);
            }
        }
    }

    @Override
    public String toString() {
        return "GlobalVariableConfig = " + JSON.toJSONString(this);
    }

}

//*/10 * * * * /bin/bash /opt/defaultCommod/1.Appium_start_4723.sh
//*/10 * * * * /bin/bash /opt/defaultCommod/1.Appium_start_4725.sh
//*/10 * * * * /bin/bash /opt/defaultCommod/1.Appium_start_4727.sh
//*/10 * * * * /bin/bash /opt/defaultCommod/1.Appium_start_4729.sh
//*/10 * * * * /bin/bash /opt/defaultCommod/3.Rethinkdb_start.sh
//*/10 * * * * /bin/bash /opt/defaultCommod/4.STF_start_192.168.43.181.sh
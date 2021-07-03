package com.automation.utils.wei_xin.inviteToJoinTheGroup;

import com.alibaba.fastjson.JSON;
import com.automation.utils.CommandUtil;
import com.automation.utils.EmojiUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 真机设备 邀请进群 策略
 * 默认 华为 P20 Pro
 */
public class RealMachineDevices implements InviteToJoinTheGroup {

    public static final Logger logger = LoggerFactory.getLogger(RealMachineDevices.class);

    /**
     * 邀请进群
     *
     * @param paramMap
     * @throws Exception
     */
    @Override
    public boolean inviteToJoinTheGroup(Map<String, Object> paramMap) throws Exception {
        //0.获取参数
        //设备编码
        String deviceName =
                paramMap.get("deviceName") != null ?
                        paramMap.get("deviceName").toString() :
                        "9f4eda95";
        //设备描述
        String deviceNameDesc =
                paramMap.get("deviceNameDesc") != null ?
                        paramMap.get("deviceNameDesc").toString() :
                        "小米 Max 3";
        //appium端口号
        String appiumPort =
                paramMap.get("appiumPort") != null ?
                        paramMap.get("appiumPort").toString() :
                        "4723";
        //操作
        String action =
                paramMap.get("action") != null ?
                        paramMap.get("action").toString() :
                        "saveToAddressBook";
        //点击坐标【微信(】
        String chatLocation =
                paramMap.get("chatLocation") != null ?
                        paramMap.get("chatLocation").toString() :
                        "微信(";
        //点击坐标【搜索】
        String searchLocaltionStr =
                paramMap.get("searchLocaltion") != null ?
                        paramMap.get("searchLocaltion").toString() :
                        "搜索";
        //点击坐标【搜索输入框：搜索】
        String searchInputLocaltion =
                paramMap.get("searchInputLocaltion") != null ?
                        paramMap.get("searchInputLocaltion").toString() :
                        "搜索";
        //点击坐标【群聊】
        String groupLocaltion =
                paramMap.get("groupLocaltion") != null ?
                        paramMap.get("groupLocaltion").toString() :
                        "群聊";
        //点击坐标【最常使用】
        String mostUsedLocaltion =
                paramMap.get("mostUsedLocaltion") != null ?
                        paramMap.get("mostUsedLocaltion").toString() :
                        "最常使用";
        //点击坐标【聊天信息】
        String chatInfoLocaltion =
                paramMap.get("chatInfoLocaltion") != null ?
                        paramMap.get("chatInfoLocaltion").toString() :
                        "聊天信息";
        //坐标【群成员总数：聊天信息(】
        String groupTotalNumLocation =
                paramMap.get("groupTotalNumLocation") != null ?
                        paramMap.get("groupTotalNumLocation").toString() :
                        "聊天信息(";
        //点击坐标【添加成员】
        String addMemberLocation =
                paramMap.get("addMemberLocation") != null ?
                        paramMap.get("addMemberLocation").toString() :
                        "添加成员";

        //点击坐标【完成】
        String compelteBtnLocation =
                paramMap.get("compelteBtnLocation") != null ?
                        paramMap.get("compelteBtnLocation").toString() :
                        "完成";
        //点击坐标【发送】
        String sendBtnLocation =
                paramMap.get("sendBtnLocation") != null ?
                        paramMap.get("sendBtnLocation").toString() :
                        "发送";
        //点击坐标【确定】
        String confirmBtnLocation =
                paramMap.get("confirmBtnLocation") != null ?
                        paramMap.get("confirmBtnLocation").toString() :
                        "确定";
        //被邀请的昵称列表
        String nickNameListStr =
                paramMap.get("nickNameListStr") != null ?
                        paramMap.get("nickNameListStr").toString() :
                        "{}";
        nickNameListStr = EmojiUtil.emojiRecovery(nickNameListStr);
        LinkedList<String> nickNameList = Lists.newLinkedList();
        try {
            nickNameList = JSON.parseObject(nickNameListStr, LinkedList.class);
        } catch (Exception e) {
            nickNameList.add("cai_hongwang");
            nickNameList.add("cai_hong_wang");
        }
        //群昵称列表
        String groupNickNameMapStr =
                paramMap.get("groupNickNameMapStr") != null ?
                        paramMap.get("groupNickNameMapStr").toString() :
                        "{}";
        groupNickNameMapStr = EmojiUtil.emojiRecovery(groupNickNameMapStr);
        LinkedHashMap<String, String> groupNickNameMap = Maps.newLinkedHashMap();
        try {
            groupNickNameMap = JSON.parseObject(groupNickNameMapStr, LinkedHashMap.class);
        } catch (Exception e) {
            groupNickNameMap.put("内部交流群", "40");
            groupNickNameMap.put("油站科技-内部交流群", "40");
        }
        //1.配置连接android驱动
        AndroidDriver driver = null;
        try {
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability("platformName", "Android");                               //Android设备
            desiredCapabilities.setCapability("deviceName", deviceName);                                //设备
            desiredCapabilities.setCapability("udid", deviceName);                                      //设备唯一标识
            desiredCapabilities.setCapability("appPackage", "com.tencent.mm");                          //打开 微信
            desiredCapabilities.setCapability("appActivity", ".ui.LauncherUI");                         //首个 页面
            desiredCapabilities.setCapability("noReset", true);                                         //不用重新安装APK
            desiredCapabilities.setCapability("sessionOverride", true);                                 //每次启动时覆盖session，否则第二次后运行会报错不能新建session
            desiredCapabilities.setCapability("automationName", "UiAutomator2");                        //UI定位器2
            desiredCapabilities.setCapability("newCommandTimeout", 20);                                 //在下一个命令执行之前的等待最大时长,单位为秒
            desiredCapabilities.setCapability("deviceReadyTimeout", 30);                                //等待设备就绪的时间,单位为秒
            desiredCapabilities.setCapability("uiautomator2ServerLaunchTimeout", 10000);                //等待uiAutomator2服务启动的超时时间，单位毫秒
            desiredCapabilities.setCapability("uiautomator2ServerInstallTimeout", 20000);               //等待uiAutomator2服务安装的超时时间，单位毫秒
            desiredCapabilities.setCapability("androidDeviceReadyTimeout", 30);                         //等待设备在启动应用后超时时间，单位秒
            desiredCapabilities.setCapability("autoAcceptAlerts", true);                                //默认选择接受弹窗的条款，有些app启动的时候，会有一些权限的弹窗
            desiredCapabilities.setCapability("waitForSelectorTimeout", 20000);
            URL remoteUrl = new URL("http://localhost:" + appiumPort + "/wd/hub");                            //连接本地的appium
            driver = new AndroidDriver(remoteUrl, desiredCapabilities);
            logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】连接Appium【" + appiumPort + "】成功....");
            Thread.sleep(10000);                                                                     //加载安卓页面10秒,保证xml树完全加载
        } catch (Exception e) {
            throw new Exception("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】配置连接android驱动出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】Appium端口号【" + appiumPort + "】的环境是否正常运行等原因....");
        } finally {
            //针对全局，在定位元素时，如果5秒内找不到的话调用隐式等待时间内一直找找，找到的话往结束，注：会极大拖延运行速度
//            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        }

        System.out.println("当前activity = " + driver.currentActivity());
        Activity chatActivity = new Activity("com.tencent.mm", ".ui.LauncherUI");

        int chatFriendsIndex = 1;
        for (Map.Entry<String, String> entry : groupNickNameMap.entrySet()) {
            System.out.println("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + chatFriendsIndex + "】个群昵称 " + " ---->>> " + entry.getKey());
            chatFriendsIndex++;
        }
        System.out.println("=============================================================================================");
        System.out.println("=============================================================================================");
        System.out.println("=============================================================================================");

        //循环遍历好友昵称列表，点击坐标【搜索】与【搜索框】
        for (Map.Entry<String, String> entry : groupNickNameMap.entrySet()) {
            String groupNickName = entry.getKey();      //群昵称
            Integer backChatPage_num = 1;
            boolean isBackChatPageFlag = true;
            try {
                //检测昵称是否末尾包含"…"，示例：A车～05.25-06.25 50米 沿河…
                if (groupNickName.endsWith("…")) {
                    logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测昵称末尾包含\"…\"，处理之前昵称【" + groupNickName + "】....");
                    groupNickName = groupNickName.substring(0, groupNickName.length() - 1);
                    logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测昵称末尾包含\"…\"，处理之后昵称【" + groupNickName + "】....");
                }
                for (int i = 1; i <= 30; i++) {     //每间隔5秒点击一次，持续90秒
                    //2.点击坐标【搜索】，当前坐标会引起微信对当前所有联系人和聊天对象进行建立索引，会有点慢，需要进行特别支持，暂时循环点击10次
                    try {
                        driver.findElementByAndroidUIAutomator("new UiSelector().description(\"" + searchLocaltionStr + "\")").click();
                        logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【搜索框】成功....");
                        Thread.sleep(5000);         //此处会创建索引，会比较费时间才能打开
                    } catch (Exception e) {
                        logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【搜索框】失败，因为微信正在建立索引....");
                        if (i == 30) {
                            throw new Exception("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【搜索框】均失败,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
                        } else if (i == 15) {        //当点击15次均无法成功点击坐标【搜索】，则通过重启当前应用【微信】处理
                            driver.startActivity(chatActivity);      //返回【当前页面聊天好友信息】
                            logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】次点击坐标【搜索框】失败，通过重启当前应用【微信】处理....");
                            continue;
                        } else {
                            Thread.sleep(5000);         //此处会创建索引，会比较费时间才能打开
                            logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】次点击坐标【搜索框】失败，因为微信正在建立索引....");
                            continue;
                        }
                    }
                    //3.点击坐标【搜索输入框】并输入昵称
                    try {
                        driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + searchInputLocaltion + "\")").sendKeys(groupNickName);
                        logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【输入昵称到搜索框:text/搜索】成功....");
                        Thread.sleep(1000);
                        break;
                    } catch (Exception e) {
                        logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【输入昵称到搜索框:text/搜索】失败....");
                        try {
                            driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.EditText\")").sendKeys(groupNickName);
                            logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【输入昵称到搜索框:className/android.widget.EditText】成功....");
                            Thread.sleep(1000);
                            break;
                        } catch (Exception e1) {
                            logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【输入昵称到搜索框:className/android.widget.EditText】失败....");
                            if (i == 30) {
                                throw new Exception("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【输入昵称到搜索框:text/搜索】与【输入昵称到搜索框:className/android.widget.EditText】均失败,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
                            } else if (i == 15) {        //当点击15次均无法成功点击坐标【搜索】，则通过重启当前应用【微信】处理
                                driver.startActivity(chatActivity);      //返回【当前页面聊天好友信息】
                                logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】次点击坐标【搜索框】失败，通过重启当前应用【微信】处理....");
                                continue;
                            } else {
                                Thread.sleep(5000);         //此处会创建索引，会比较费时间才能打开
                                logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】次点击坐标【输入昵称到搜索框:text/搜索】与【输入昵称到搜索框:className/android.widget.EditText】均失败，因为微信正在建立索引....");
                                continue;
                            }
                        }
                    }
                }
                //4.判断坐标【群聊】与【最常使用】是否存在
                boolean isChatGroupFlag = false;
                try {
                    WebElement group_WebElement = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + groupLocaltion + "\")");
                    logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测坐标【群聊】成功....");
                    Thread.sleep(1000);
                    isChatGroupFlag = true;
                } catch (Exception e) {
                    try {
                        WebElement contactor_WebElement = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + mostUsedLocaltion + "\")");
                        logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测坐标【最常使用】成功....");
                        Thread.sleep(1000);
                        isChatGroupFlag = true;
                    } catch (Exception e1) {
                        logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测坐标【群聊】与【最常使用】均不存在，当前昵称【" + groupNickName + "】对应的可能是【联系人】或者【公众号】或者【聊天记录】....");
                    }
                }
                if (!isChatGroupFlag) {         //非好友与联系人，返回【当前页面聊天好友信息】，继续下一个昵称
                    continue;   //重注：执行continue;立刻跳转到finally代码块，通过循环返回【微信聊天界面】...
                }

                //5.点击坐标【昵称对应的微信好友】
                try {
                    driver.findElementByXPath("//android.widget.TextView[@text=\"" + groupLocaltion + "\"]/../../../android.widget.RelativeLayout[2]").click();
                    logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【昵称对应的微信好友群】通过【联系人的xpath】成功....");
                    Thread.sleep(1000);
                } catch (Exception e) {
                    try {
                        driver.findElementByXPath("//android.widget.TextView[@text=\"" + mostUsedLocaltion + "\"]/../../../android.widget.RelativeLayout[2]").click();
                        logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【昵称对应的微信好友群】通过【最常使用的xpath】成功....");
                        Thread.sleep(1000);
                    } catch (Exception e1) {
                        throw new Exception("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】通过【联系人的xpath】与【最常使用的xpath】点击坐标【昵称对应的微信好友】均失败，当前昵称【\" + nickName + \"】对应的可能是【微信群】或者【公众号】或者【聊天记录】....");
                    }
                }

                //6.点击坐标【设置】new UiSelector().description("当前所在页面,与的聊天")
                try {
                    WebElement chatInfo_WebElement = driver.findElementByAndroidUIAutomator("new UiSelector().description(\"" + chatInfoLocaltion + "\")");
                    chatInfo_WebElement.click();
                    logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【设置】成功....");
                    Thread.sleep(2000);
                } catch (Exception e) {
                    continue;   //重注：执行continue;立刻跳转到finally代码块，通过循环返回【微信聊天界面】...
                }

                //7.查看坐标【群成员总数：聊天信息(】
                isChatGroupFlag = false;
                try {
                    WebElement groupTotalNumWebElement = driver.findElementByAndroidUIAutomator("new UiSelector().textStartsWith(\"" + groupTotalNumLocation + "\")");
                    String text = groupTotalNumWebElement.getAttribute("text");
                    String groupTotalNumStr = ((text.split("\\("))[1].split("\\)"))[0];
                    Integer groupTotalNum = Integer.parseInt(groupTotalNumStr);
                    if (groupTotalNum > 0) {
                        isChatGroupFlag = true;
                        logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测【是否为微信群】成功，【微信群】--->>>【" + groupNickName + "】群成员总数为：" + groupTotalNum + "个....");
                    }
                } catch (Exception e) {

                } finally {
                    if (!isChatGroupFlag) {         //非好友与联系人，返回【当前页面聊天好友信息】，继续下一个昵称
                        continue;   //重注：执行continue;立刻跳转到finally代码块，通过循环返回【微信聊天界面】...
                    }
                }

                //8.点击坐标【添加成员】
                try {
                    driver.findElementByAndroidUIAutomator("new UiSelector().description(\"" + addMemberLocation + "\")").click();
                    logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【添加成员】成功....");
                } catch (Exception e) {
                    logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【添加成员】异常....");
                }

                for (String theNickName : nickNameList) {            //循环遍历，被邀请的昵称列表
                    //9.点击坐标【搜索输入框】并输入昵称
                    try {
                        driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + searchInputLocaltion + "\")").clear();
                        logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【输入昵称到搜索框:text/搜索】清空成功....");
                        Thread.sleep(1000);
                        driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + searchInputLocaltion + "\")").sendKeys(theNickName);
                        logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【输入昵称到搜索框:text/搜索】查找微信用户成功....");
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【输入昵称到搜索框:text/搜索】查找微信用户异常....");
                        try {
                            driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.EditText\")").click();
                            logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【输入昵称到搜索框:className/android.widget.EditText】清空成功....");
                            Thread.sleep(1000);
                            driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.EditText\")").sendKeys(theNickName);
                            logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【输入昵称到搜索框:className/android.widget.EditText】查找微信用户成功....");
                            Thread.sleep(1000);
                        } catch (Exception e1) {
                            logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第点击坐标【输入昵称到搜索框:className/android.widget.EditText】查找微信用户异常....");
                        }
                    }

                    //10.点击坐标【昵称】对应微信好友并点击选中 new UiSelector().textContains("hjreven")
                    try {
                        driver.findElementByAndroidUIAutomator("new UiSelector().textContains(\"" + "微信号: " + theNickName + "\")").click();
                        logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【昵称】对应微信好友【" + theNickName + "】通过【微信号码】点击并选中 成功....");
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【昵称】对应微信好友【" + theNickName + "】通过【微信号码】点击并选中 异常....");
                        try {
                            driver.findElementByXPath("//android.widget.TextView[contains(@text, \"" + "theNickName" + "\")]").click();
                            logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【昵称】对应微信好友【" + theNickName + "】通过【微信昵称】【XPath定位】点击并选中 成功....");
                            Thread.sleep(1000);
                        } catch (Exception e1) {
                            logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【昵称】对应微信好友【" + theNickName + "】通过【微信昵称】【XPath定位】点击并选中 异常....");
                            logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【昵称】当前设备无对应微信好友....");
                        }
                    }
                }

                //11.点击坐标【完成】
                try {
                    driver.findElementByAndroidUIAutomator("new UiSelector().textStartsWith(\"" + compelteBtnLocation + "\")").click();
                    logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【完成】成功....");
                    Thread.sleep(1000);
                } catch (Exception e) {
                    logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【完成】异常....");
                    try {
                        driver.findElementByXPath("//android.widget.Button[contains(@text, \"" + compelteBtnLocation + "\")]").click();
                        logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【完成】【XPath定位】成功....");
                        Thread.sleep(1000);
                    } catch (Exception e1) {
                        logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【完成】【XPath定位】异常....");
                    }
                }

                //12.点击坐标【发送】
                try {
                    driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + sendBtnLocation + "\")").click();
                    logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【发送】邀请至该群主成功....");
                    Thread.sleep(1000);
                } catch (Exception e) {
                    logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【发送】邀请至该群主异常，当前群无需群主同意直接发送邀请成功....");
                    try {
                        driver.findElementByXPath("//android.widget.Button[@text=\"" + sendBtnLocation + "\"]").click();
                        logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【发送】【XPath定位】邀请至该群主成功....");
                        Thread.sleep(1000);
                    } catch (Exception e1) {
                        logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【发送】【XPath定位】邀请至该群主异常，当前群无需群主同意直接发送邀请成功....");
                    }
                }

                //12.点击坐标【确定】
                try {
                    driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + confirmBtnLocation + "\")").click();
                    logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【确定】返回群简介界面 成功....");
                    Thread.sleep(1000);
                } catch (Exception e) {
                    logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【确定】返回群简介界面 异常，当前群无需群主同意直接发送邀请成功....");
                    try {
                        driver.findElementByXPath("//android.widget.Button[@text=\"" + confirmBtnLocation + "\"]").click();
                        logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【确定】【XPath定位】返回群简介界面 成功....");
                        Thread.sleep(1000);
                    } catch (Exception e1) {
                        logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【确定】【XPath定位】返回群简介界面 异常，当前群无需群主同意直接发送邀请成功....");
                    }
                }
            } catch (Exception e) {

            } finally {
                while (true) {
                    try {
                        driver.findElementByAndroidUIAutomator("new UiSelector().textStartsWith(\"" + chatLocation + "\")");
                        logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + backChatPage_num + "】次 通过检测坐标【" + chatLocation + "】返回【微信聊天界面】成功....");
                        isBackChatPageFlag = false;
                        Thread.sleep(1000);
                        break;
                    } catch (Exception e) {
                        logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + backChatPage_num + "】次 通过检测坐标【" + chatLocation + "】返回【微信聊天界面】失败....");
                        try {
                            driver.findElementByAndroidUIAutomator("new UiSelector().description(\"" + searchLocaltionStr + "\")");
                            logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + backChatPage_num + "】次 通过检测坐标【" + searchLocaltionStr + "】返回【微信聊天界面】成功....");
                            isBackChatPageFlag = false;
                            Thread.sleep(1000);
                            break;
                        } catch (Exception e1) {
                            logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + backChatPage_num + "】次 通过检测坐标【" + chatLocation + "】与【" + searchLocaltionStr + "】返回【微信聊天界面】均失败....");
                        }
                    } finally {
                        if (backChatPage_num <= 10) {
                            if (isBackChatPageFlag) {
                                driver.pressKeyCode(AndroidKeyCode.BACK);
                                Thread.sleep(1000);
                                backChatPage_num++;
                            }
                        } else {
                            driver.startActivity(chatActivity);      //返回【当前页面聊天好友信息】
                            logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】通过【chatActivity】返回【当前页面聊天好友信息】....");
                            Thread.sleep(2000);
                            break;
                        }
                    }
                }
            }
        }
        logger.info("【邀请进群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】 邀请进群成功....");
        return true;
    }

    public static void main(String[] args) {
        try {
            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("deviceName", "S2D0219423001056");
            paramMap.put("deviceNameDesc", "华为Mate20Pro");
            paramMap.put("nickNameListStr", "[\n" +
                    "  \"hjreven\",\n" +
                    "  \"cai_hongwang\",\n" +
                    "  \"cai_hong_wang\",\n" +
                    "  \"mg00529\"\n" +
                    "]");
            paramMap.put("groupNickNameMapStr", "{\n" +
//                    "  \"内部交流群\": \"40\",\n" +
//                    "  \"油站科技-内部交流群\": \"40\"\n" +
                    "  \"贵阳帝景26栋家园群\": \"40\"\n" +
                    "}");
            new RealMachineDevices().inviteToJoinTheGroup(paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

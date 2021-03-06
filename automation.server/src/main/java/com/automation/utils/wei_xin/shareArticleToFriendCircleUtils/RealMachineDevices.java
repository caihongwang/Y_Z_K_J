package com.automation.utils.wei_xin.shareArticleToFriendCircleUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.automation.utils.CommandUtil;
import com.automation.utils.appiumUtil.SwipeUtil;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import org.apache.commons.lang.time.StopWatch;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 真机设备 分享微信文章到微信朋友圈 策略
 * 默认 小米Max3
 */
public class RealMachineDevices implements ShareArticleToFriendCircle {

    public static final Logger logger = LoggerFactory.getLogger(RealMachineDevices.class);

    /**
     * 前置条件：将微信文章群发到【油站科技-内部交流群】里面
     * 分享微信文章到微信朋友圈
     *
     * @param paramMap
     * @throws Exception
     */
    @Override
    public boolean shareArticleToFriendCircle(Map<String, Object> paramMap) throws Exception {
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
                        "shareArticleToFriendCircle";
        //微信分享文章标题
        String shareArticleTitle =
                paramMap.get("shareArticleTitle") != null ?
                        paramMap.get("shareArticleTitle").toString() :
                        "加油站计量有猫腻?!看看正规加油站是怎么做的!";
        //微信分享文章URL
        String shareArticleUrl =
                paramMap.get("shareArticleUrl") != null ?
                        paramMap.get("shareArticleUrl").toString() :
                        "https://mp.weixin.qq.com/s/gvF-7-uZcFc5MYbMQMtZSw";
        //微信群昵称
        String targetGroup =
                paramMap.get("targetGroup") != null ?
                        paramMap.get("targetGroup").toString() :
                        "油站科技-内部交流群";
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
        //坐标:聊天内容输入框
        String chatInputLocation =
                paramMap.get("chatInputLocation") != null ?
                        paramMap.get("chatInputLocation").toString() :
                        "android.widget.EditText";
        //坐标:发送
        String sendBtnLocaltion =
                paramMap.get("sendBtnLocaltion") != null ?
                        paramMap.get("sendBtnLocaltion").toString() :
                        "发送";
        //坐标:点击微信文章链接
        String shareArticleUrlLocaltion =
                paramMap.get("shareArticleUrlLocaltion") != null ?
                        paramMap.get("shareArticleUrlLocaltion").toString() :
                        "android.view.View";
        //坐标:右上角的横三点
        String rightThreePointLocaltion =
                paramMap.get("rightThreePointLocaltion") != null ?
                        paramMap.get("rightThreePointLocaltion").toString() :
                        "更多";
        //坐标:分享到朋友圈
        String shareFriendCircleLocaltion =
                paramMap.get("shareFriendCircleLocaltion") != null ?
                        paramMap.get("shareFriendCircleLocaltion").toString() :
                        "分享到朋友圈";
        //坐标:输入分享文本框
        String shareArticleTitleLocaltion =
                paramMap.get("shareArticleTitleLocaltion") != null ?
                        paramMap.get("shareArticleTitleLocaltion").toString() :
                        "这一刻的想法...";
        //坐标:发表
        String publishBtnLocaltion =
                paramMap.get("publishBtnLocaltion") != null ?
                        paramMap.get("publishBtnLocaltion").toString() :
                        "发表";

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
            logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】连接Appium【" + appiumPort + "】成功....");
            Thread.sleep(10000);                                                                     //加载安卓页面10秒,保证xml树完全加载
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】配置连接android驱动出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】Appium端口号【" + appiumPort + "】的环境是否正常运行等原因....");
        }

        System.out.println("当前activity = " + driver.currentActivity());
        Activity chatActivity = new Activity("com.tencent.mm", ".ui.LauncherUI");
        for (int i = 1; i <= 30; i++) {     //每间隔5秒点击一次，持续90秒
            //2.点击坐标【搜索】，当前坐标会引起微信对当前所有联系人和聊天对象进行建立索引，会有点慢，需要进行特别支持，暂时循环点击10次
            try {
                driver.findElementByAndroidUIAutomator("new UiSelector().description(\"" + searchLocaltionStr + "\")").click();
                logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【搜索框】成功....");
                Thread.sleep(5000);         //此处会创建索引，会比较费时间才能打开
            } catch (Exception e) {
                logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【搜索框】失败，因为微信正在建立索引....");
                if (i == 30) {
                    throw new Exception("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【搜索框】均失败,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
                } else if(i == 15){        //当点击15次均无法成功点击坐标【搜索】，则通过重启当前应用【微信】处理
                    driver.startActivity(chatActivity);      //返回【当前页面聊天好友信息】
                    logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】次点击坐标【搜索框】失败，通过重启当前应用【微信】处理....");
                    continue;
                } else {
                    Thread.sleep(5000);         //此处会创建索引，会比较费时间才能打开
                    logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】次点击坐标【搜索框】失败，因为微信正在建立索引....");
                    continue;
                }
            }
            //3.点击坐标【搜索输入框】并输入昵称
            try {
                driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + searchInputLocaltion + "\")").sendKeys(targetGroup);
                logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【输入昵称到搜索框:text/搜索】成功....");
                Thread.sleep(1000);
                break;
            } catch (Exception e) {
                logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【输入昵称到搜索框:text/搜索】失败....");
                try {
                    driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.EditText\")").sendKeys(targetGroup);
                    logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【输入昵称到搜索框:className/android.widget.EditText】成功....");
                    Thread.sleep(1000);
                    break;
                } catch (Exception e1) {
                    logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【输入昵称到搜索框:className/android.widget.EditText】失败....");
                    if (i == 30) {
                        throw new Exception("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【输入昵称到搜索框:text/搜索】与【输入昵称到搜索框:className/android.widget.EditText】均失败,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
                    } else if(i == 15){        //当点击15次均无法成功点击坐标【搜索】，则通过重启当前应用【微信】处理
                        driver.startActivity(chatActivity);      //返回【当前页面聊天好友信息】
                        logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】次点击坐标【搜索框】失败，通过重启当前应用【微信】处理....");
                        continue;
                    } else {
                        Thread.sleep(5000);         //此处会创建索引，会比较费时间才能打开
                        logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】次点击坐标【输入昵称到搜索框:text/搜索】与【输入昵称到搜索框:className/android.widget.EditText】均失败，因为微信正在建立索引....");
                        continue;
                    }
                }
            }
        }
        //4.判断坐标【联系人】与【最常使用】是否存在
        try {
            driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + groupLocaltion + "\")");
            Thread.sleep(1000);
        } catch (Exception e) {
            try {
                driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + mostUsedLocaltion + "\")");
                Thread.sleep(1000);
            } catch (Exception e1) {
                throw new Exception("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】判断坐标【联系人】与【最常使用】均不存在，当前昵称【" + targetGroup + "】对应的可能是【微信群】或者【公众号】或者【聊天记录】....");

            }
        }
        //5.点击坐标【昵称对应的微信好友群】
        try {
            driver.findElementByXPath("//android.widget.TextView[@text=\"" + groupLocaltion + "\"]/../../../android.widget.RelativeLayout[2]").click();
            logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【昵称对应的微信好友群】通过【联系人的xpath】成功....");
            Thread.sleep(1000);
        } catch (Exception e) {
            try {
                driver.findElementByXPath("//android.widget.TextView[@text=\"" + mostUsedLocaltion + "\"]/../../../android.widget.RelativeLayout[2]").click();
                logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【昵称对应的微信好友群】通过【最常使用的xpath】成功....");
                Thread.sleep(1000);
            } catch (Exception e1) {
                throw new Exception("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】通过【联系人的xpath】与【最常使用的xpath】点击坐标【昵称对应的微信好友】均失败，当前昵称【\" + nickName + \"】对应的可能是【微信群】或者【公众号】或者【聊天记录】....");
            }
        }
        for (int i = 0; i <= 10; i++) {     //发送 文章链接 发送十次，避免 点击坐标【点击微信文章链接】错乱，防止别人发的消息，获取到最左边的消息导致左边计算失败
            //6.点击坐标【聊天内容输入框】
            try {
                driver.findElementByAndroidUIAutomator("new UiSelector().className(\"" + chatInputLocation + "\")").click();
                logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【聊天输入框】成功....");
                Thread.sleep(1000);
                driver.findElementByAndroidUIAutomator("new UiSelector().className(\"" + chatInputLocation + "\")").sendKeys(shareArticleUrl);
                logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【聊天输入框】输入信息成功....");
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【聊天输入框】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
            }
            //7.点击坐标【发送】
            try {
                driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + sendBtnLocaltion + "\")").click();
                logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】findElementByAndroidUIAutomator 点击坐标【发送】成功....");
                Thread.sleep(1000);
            } catch (Exception e) {
                logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】findElementByAndroidUIAutomator 点击坐标【发送】失败....");
                try {
                    driver.findElementByXPath("//android.widget.Button[@text='" + sendBtnLocaltion + "']").click();
                    logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】findElementByXPath 点击坐标【发送】成功....");
                    Thread.sleep(1000);
                } catch (Exception e1) {
                    logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】findElementByAndroidUIAutomator 与 findElementByXPath 点击坐标【发送】均失败....");
                    e.printStackTrace();
                    throw new Exception("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【发送】出现异常,请检查设备描述[" + deviceNameDesc + "]设备编码[" + deviceName + "]的应用是否更新导致坐标变化等原因....");
                }
            }
        }

        //8.点击坐标【分享到朋友圈】
//        //方法一（废弃，不适用）：根据关键字【分享到朋友圈】进行定位
//        try {
//            driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + shareFriendCircleLocaltion + "\")").click();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new Exception("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【分享到朋友圈】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
//        }

//        //方法二（废弃，不精准）：根据不同手机屏幕的分辨率，设定点击区域，通过 adb 命令进行tab点击
//        try {
//            //华为 P20 Pro  :   (220,1460)与(365,1610)
//            //小米 Max3 Pro :   (220,1380)与(365,1530)
//            //通用坐标：              (300,1500)
//            double boundary_x1 = 240;
//            double boundary_x2 = 365;
//            double boundary_y1 = 1040;
//            double boundary_y2 = 1120;
//            int screenWidth = driver.manage().window().getSize().width;
//            int screenHeight = driver.manage().window().getSize().height;
//            if(screenWidth >= 1080 && screenHeight >= 2160){                //分辨率设备为：华为 Mate 8
//                boundary_x1 = 240;
//                boundary_x2 = 365;
//                boundary_y1 = 1040;
//                boundary_y2 = 1120;
//            } else if(screenWidth >= 1080 && screenHeight >= 2160) {        //分辨率设备为：小米 Max3 Pro
//                boundary_x1 = 240;
//                boundary_x2 = 365;
//                boundary_y1 = 1340;
//                boundary_y2 = 1535;
//            } else if(screenWidth >= 1080 && screenHeight >= 2240) {        //分辨率设备为：华为 P20 Pro
//                boundary_x1 = 240;
//                boundary_x2 = 365;
//                boundary_y1 = 1450;
//                boundary_y2 = 1610;
//            }
//            double rightThreePointLocaltion_X = Math.floor(Math.random() * (boundary_x2 - boundary_x1) + boundary_x1);
//            double rightThreePointLocaltion_Y = Math.floor(Math.random() * (boundary_y2 - boundary_y1) + boundary_y1);
//            String tabCommondStr = "/opt/android_sdk/platform-tools/adb -s " + deviceName + " shell input tap " + rightThreePointLocaltion_X + " " + rightThreePointLocaltion_Y;
//            CommandUtil.run(tabCommondStr);
//            logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【分享到朋友圈】成功....");
//            Thread.sleep(1000);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new Exception("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【分享到朋友圈】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
//        }

        //方法三：设定检测区域范围，循环遍历点击，检测出坐标【这一刻的想法...】则继续下一步，否则返回再次循环遍历点击，50次点击终止
        int findNum = 0;       //循环的次数
        int maxFindNum = 50;       //默认超过30次
        boolean isFindBtnLocaltionFlag = false;
        double screenWidth = driver.manage().window().getSize().width;         //1080
        double screenHeight = driver.manage().window().getSize().height;       //2030
        System.out.println("screenWidth = " + screenWidth + "，screenHeight = " + screenHeight);
        //检测区域范围
        double boundary_x1 = 0;
        double boundary_x2 = screenWidth;
        double boundary_y1 = screenHeight / 2;
        double boundary_y2 = screenHeight;
//      (0,1015)---------------------------|
//        |--------------------------------|
//        |--------------------------------|
//        |--------------------------------|
//        |--------------------------------|
//        |---------------------------(540,2030)
        double theLocaltion_X = boundary_x1;        //点击 X 坐标
        double theLocaltion_Y = boundary_y1;        //点击 Y 坐标
        while (true) {
            //8 点击坐标【点击微信文章链接】聊天信息无法被text与content-desc识别
            driver.hideKeyboard();      //隐藏键盘
            List<WebElement> chatContentRelativeLayoutList = driver.findElementsByXPath("//android.widget.ListView/android.widget.RelativeLayout");
            for (int i = 0; i < chatContentRelativeLayoutList.size(); i++) {
                if (i >= 2) {     //从上到下，从第二条消息开始点击，避免点击到android的消息push进行误点
                    try {
                        //方法一：通过click点击消息RelativeLayout，可能会存在某些机型无法命中点击
                        WebElement chatContent_RelativeLayout = chatContentRelativeLayoutList.get(i);
                        try {
                            chatContent_RelativeLayout.click();
                            logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】次 click 点击坐标【点击微信文章链接】成功....");
                            Thread.sleep(1500);
                        } catch (Exception e) {

                        } finally {
                            try {
                                WebElement chatInput_WebElement = driver.findElementByAndroidUIAutomator("new UiSelector().className(\"" + chatInputLocation + "\")");
                                logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测坐标【" + chatInputLocation + "】成功，即点击【点击微信文章链接】失败....");
                                Thread.sleep(1000);
                            } catch (Exception e1) {
                                logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测坐标【" + chatInputLocation + "】失败，即点击【点击微信文章链接】成功....");
                                break;
                            }
                        }
                        //方法二：通过公式[y = 0.21 * x + 299]Touch (x,y) 点击坐标【点击微信文章链接】，目前适配大多数机型
//                        WebElement chatContent_RelativeLayout = chatContentRelativeLayoutList.get(i);
                        Rectangle chatContent_Rectangle = chatContent_RelativeLayout.getRect();
                        int x = chatContent_Rectangle.getX();
                        int y = chatContent_Rectangle.getY();
                        double width = chatContent_Rectangle.getWidth();
                        double height = chatContent_Rectangle.getHeight();
                        //点击
                        for (double theTemp_x = x; theTemp_x <= (x + width); theTemp_x = theTemp_x + 50) {
                            try {
                                double theTemp_y = (height / width) * theTemp_x + y;    //获取直线的斜率，y = k * x + b  --->>> y = (height/width) * x + y
                                if (theTemp_y <= (y + height)) {
                                    new TouchAction(driver).tap((int) theTemp_x, (int) theTemp_y).release().perform();
                                    System.out.println("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】次 TouchAction 点击坐标(" + theTemp_x + "," + theTemp_y + ")【点击微信文章链接】成功...");
                                    Thread.sleep(200);
                                } else {
                                    break;
                                }
                            } catch (Exception e) {

                            } finally {
                                try {
                                    WebElement chatInput_WebElement = driver.findElementByAndroidUIAutomator("new UiSelector().className(\"" + chatInputLocation + "\")");
                                    logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测坐标【" + chatInputLocation + "】成功，即点击【点击微信文章链接】失败....");
                                    Thread.sleep(1000);
                                } catch (Exception e1) {
                                    logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测坐标【" + chatInputLocation + "】失败，即点击【点击微信文章链接】成功....");
                                    break;
                                }
                            }
                        }
                    } catch (Exception e) {
                        logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】次 点击坐标【点击微信文章链接】失败....");
                        e.printStackTrace();
                        break;
                    } finally {
                        try {
                            WebElement chatInput_WebElement = driver.findElementByAndroidUIAutomator("new UiSelector().className(\"" + chatInputLocation + "\")");
                            logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测坐标【" + chatInputLocation + "】成功，即点击【点击微信文章链接】失败....");
                            Thread.sleep(1000);
                        } catch (Exception e1) {
                            logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测坐标【" + chatInputLocation + "】失败，即点击【点击微信文章链接】成功....");
                            break;
                        }
                    }
                }
            }
            //9.向上滑动微信文章
            if (findNum == 0) {
                for (int i = 0; i < 3; i++) {
                    SwipeUtil.SwipeDown(driver);
                    logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】向上滑动【微信文章】成功....");
                    int max = 1500;
                    int min = 1000;
                    int sleppTime = (int) (min + Math.random() * (max - min + 1));
                    Thread.sleep(sleppTime);
                }
            }
            //10.点击坐标【右上角的横三点】
            try {
//                driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + rightThreePointLocaltion + "\")").click();
                //通过 adb 命令进行tab点击
                int rightThreePointLocaltion_X = 1000;
                int rightThreePointLocaltion_Y = 150;
                String tabCommondStr = "/opt/android_sdk/platform-tools/adb -s " + deviceName + " shell input tap " + rightThreePointLocaltion_X + " " + rightThreePointLocaltion_Y;
                CommandUtil.run(tabCommondStr);
                logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【右上角的横三点】成功....");
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【右上角的横三点】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
            }
            try {
                //11.1 通过公式[y = 1.88 * x + 1015]点击坐标（X，Y）坐标点击坐标【分享到朋友圈】...
                theLocaltion_X = theLocaltion_X + 20;
                if (theLocaltion_X > (boundary_x2 / 2)) {
                    theLocaltion_X = boundary_x2;
                }
                double k = ( ( (3*screenHeight - 2*screenHeight) / 4 ) / ( (screenWidth - 0) / 2 ) );
                theLocaltion_Y= k * theLocaltion_X + screenHeight / 2;    //获取直线的斜率，y = k * x + b  --->>> y = (height/width) * x + y
                if (theLocaltion_Y > boundary_y2) {
                    theLocaltion_Y = boundary_y2;
                }
                String tabCommondStr = "/opt/android_sdk/platform-tools/adb -s " + deviceName + " shell input tap " + theLocaltion_X + " " + theLocaltion_Y;
                CommandUtil.run(tabCommondStr);
                logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + findNum + "】点击坐标 (" + theLocaltion_X + "," + theLocaltion_Y + ") 成功....");
                //11.2 通过检测坐标【这一刻的想法...】检测是否成功点击坐标【分享到朋友圈】...
                try {
                    driver.findElementByAndroidUIAutomator("new UiSelector().textStartsWith(\"" + shareArticleTitleLocaltion + "\")");
                    logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + findNum + "】findElementByAndroidUIAutomator 寻找坐标【" + shareArticleTitleLocaltion + "】成功....");
                } catch (Exception e) {
                    logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + findNum + "】findElementByAndroidUIAutomator 寻找坐标【" + shareArticleTitleLocaltion + "】失败....");
                    driver.findElementByXPath("//android.widget.EditText[@text='" + shareArticleTitleLocaltion + "']");
                    logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + findNum + "】findElementByXPath 寻找坐标【" + shareArticleTitleLocaltion + "】成功....");
                }
                isFindBtnLocaltionFlag = true;
                Thread.sleep(1500);
            } catch (Exception e) {
                logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + findNum + "】findElementByAndroidUIAutomator 与 findElementByXPath 寻找坐标【" + shareArticleTitleLocaltion + "】失败....");
                //11.3 检测 聊天会话中的 输入框 android.widget.EditText 是否存在，最终返回【聊天会话】
                for (int index = 0; index < 5; index++) {
                    WebElement chatInput_WebElement = null;
                    try {
                        chatInput_WebElement = driver.findElementByAndroidUIAutomator("new UiSelector().className(\"" + chatInputLocation + "\")");
                        logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】【临时】第【" + index + "】次寻找坐标【" + chatInputLocation + "】返回聊天会话 成功....");
                        Thread.sleep(1500);
                        break;
                    } catch (Exception e1) {
                        logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】【临时】第【" + index + "】次寻找坐标【" + chatInputLocation + "】返回聊天会话 失败....");
                        driver.pressKeyCode(AndroidKeyCode.BACK);                   //返回【聊天会话】
                        Thread.sleep(1500);
                    }
                }
            } finally {
                findNum++;
                if (isFindBtnLocaltionFlag) {
                    break;
                } else {
                    logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + findNum + "】寻找坐标【" + shareArticleTitleLocaltion + "】失败....");
                    if (findNum > maxFindNum) {
                        throw new Exception("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】寻找坐标【" + shareArticleTitleLocaltion + "】失败，无法继续下一步，请调整检测区域范围及检测步长....");
                    }
                }
            }
        }
        //12.点击坐标【输入分享文本内容】
        try {
            driver.findElementByAndroidUIAutomator("new UiSelector().textStartsWith(\"" + shareArticleTitleLocaltion + "\")").sendKeys(shareArticleTitle);
            logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】findElementByAndroidUIAutomator 点击坐标【输入分享文本框】成功....");
            Thread.sleep(1000);
        } catch (Exception e) {
            logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】findElementByAndroidUIAutomator 点击坐标【输入分享文本框】失败....");
            try {
                driver.findElementByXPath("//android.widget.EditText[@text='" + shareArticleTitleLocaltion + "']").sendKeys(shareArticleTitle);
                logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】findElementByXPath 点击坐标【输入分享文本框】成功....");
            } catch (Exception e1) {
                e.printStackTrace();
                logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】findElementByAndroidUIAutomator与findElementByXPath 点击坐标【输入分享文本框】均失败....");
                throw new Exception("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【输入分享文本框】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
            }
        }
        //13.点击坐标【发表】
        try {
            driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + publishBtnLocaltion + "\")").click();
            logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【发表】成功....");
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【发表】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
        }
        logger.info("【分享微信文章到微信朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】 发送成功....");
        return true;
    }

    public static void main(String[] args) {
        try {
            StopWatch sw = new StopWatch();
            sw.start();
            Map<String, Object> paramMap = Maps.newHashMap();
//            paramMap.put("deviceName", "S2D0219423001056");
//            paramMap.put("deviceNameDesc", "华为 Mate 20 Pro");
            paramMap.put("deviceName", "9f4eda95");
            paramMap.put("deviceNameDesc", "小米 Max 3");
            paramMap.put("action", "shareArticleToFriendCircle");
            new RealMachineDevices().shareArticleToFriendCircle(paramMap);
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.oilStationMap.utils.wxAdAutomation.shareArticleToFriendCircleUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.oilStationMap.utils.CommandUtil;
import com.oilStationMap.utils.appiumUtil.SwipeUtil;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.lang.time.StopWatch;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
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
 * 默认 华为 Mate 8
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
    public void shareArticleToFriendCircle(Map<String, Object> paramMap, StopWatch sw) throws Exception {
        //0.获取参数
        //设备编码
        String deviceName =
                paramMap.get("deviceName") != null ?
                        paramMap.get("deviceName").toString() :
//                        "9f4eda95";
//                        "S2D0219423001056";
                        "Q8U0215511002364";
        //设备描述
        String deviceNameDesc =
                paramMap.get("deviceNameDesc") != null ?
                        paramMap.get("deviceNameDesc").toString() :
//                        "小米 Max 3";
//                        "华为 Mate 20 Pro";
                        "华为 Mate 7 _ 4";
        //操作
        String action =
                paramMap.get("action") != null ?
                        paramMap.get("action").toString() :
                        "shareArticleToFriendCircle";
        //微信群昵称
        String targetGroup =
                paramMap.get("targetGroup") != null ?
                        paramMap.get("targetGroup").toString() :
                        "油站科技-内部交流群";
        //微信分享文章URL
        String shareArticleUrl =
                paramMap.get("shareArticleUrl") != null ?
                        paramMap.get("shareArticleUrl").toString() :
                        "https://mp.weixin.qq.com/s/gvF-7-uZcFc5MYbMQMtZSw";
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
//        String shareArticleUrlLocaltionStr =
//                paramMap.get("shareArticleUrlLocaltion") != null ?
//                        paramMap.get("shareArticleUrlLocaltion").toString() :
//                        "{\n" +
//                        "            \"shareArticleUrlLocaltion_x1\":320,\n" +
//                        "            \"shareArticleUrlLocaltion_y1,\":1500,\n" +
//                        "            \"shareArticleUrlLocaltion_x2,\":800,\n" +
//                        "            \"shareArticleUrlLocaltion_y2\":1585\n" +
//                        "        }";
//        Map<String, Integer> shareArticleUrlLocaltion = JSONObject.parseObject(shareArticleUrlLocaltionStr, Map.class);
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
        //微信分享文章标题
        String shareArticleTitle =
                paramMap.get("shareArticleTitle") != null ?
                        paramMap.get("shareArticleTitle").toString() :
                        "加油站计量有猫腻?!看看正规加油站是怎么做的!";
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
            URL remoteUrl = new URL("http://localhost:" + 4723 + "/wd/hub");                            //连接本地的appium
            driver = new AndroidDriver(remoteUrl, desiredCapabilities);
            sw.split();
            logger.info("设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】连接Appium成功，总共花费 " + sw.toSplitString() + " 秒....");
            Thread.sleep(10000);                                                                     //加载安卓页面10秒,保证xml树完全加载
        } catch (Exception e) {
            sw.split();
            e.printStackTrace();
            this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
            throw new Exception("配置连接android驱动出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的环境是否正常运行等原因，总共花费 " + sw.toSplitString() + " 秒....");
        }
        for (int i = 1; i <= 18; i++) {     //每间隔5秒点击一次，持续90秒
            //2.点击坐标【搜索】，当前坐标会引起微信对当前所有联系人和聊天对象进行建立索引，会有点慢，需要进行特别支持，暂时循环点击10次
            try {
                driver.findElementByAndroidUIAutomator("new UiSelector().description(\"" + searchLocaltionStr + "\")").click();
                sw.split();
                logger.info("点击坐标【搜索框】成功，总共花费 " + sw.toSplitString() + " 秒....");
                Thread.sleep(5000);         //此处会创建索引，会比较费时间才能打开
            } catch (Exception e) {
                this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
                sw.split();
                logger.info("点击坐标【搜索框】失败，因为微信正在建立索引，总共花费 " + sw.toSplitString() + " 秒....");
                continue;
            }
            //3.点击坐标【搜索输入框】
            try {
                driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + searchInputLocaltion + "\")").sendKeys(targetGroup);
                sw.split();
                logger.info("点击坐标【输入昵称到搜索框:text/搜索】成功，总共花费 " + sw.toSplitString() + " 秒....");
                Thread.sleep(1000);
                break;
            } catch (Exception e) {
                sw.split();
                logger.info("点击坐标【输入昵称到搜索框:text/搜索】失败，总共花费 " + sw.toSplitString() + " 秒....");
                try {
                    driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.EditText\")").sendKeys(targetGroup);
                    sw.split();
                    logger.info("点击坐标【输入昵称到搜索框:className/android.widget.EditText】成功，总共花费 " + sw.toSplitString() + " 秒....");
                    Thread.sleep(1000);
                    break;
                } catch (Exception e1) {
                    sw.split();
                    logger.info("点击坐标【输入昵称到搜索框:className/android.widget.EditText】失败，总共花费 " + sw.toSplitString() + " 秒....");
                    this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
                    sw.split();
                    if (i == 18) {
                        throw new Exception("点击坐标【输入昵称到搜索框:text/搜索】与【输入昵称到搜索框:className/android.widget.EditText】均失败,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因，总共花费 " + sw.toSplitString() + " 秒....");
                    } else {
                        sw.split();
                        logger.info("第【" + i + "】次点击坐标【输入昵称到搜索框:text/搜索】与【输入昵称到搜索框:className/android.widget.EditText】均失败，因为微信正在建立索引，总共花费 " + sw.toSplitString() + " 秒....");
                        continue;
                    }
                }
            }
        }
        //4.点击坐标【昵称对应的微信好友群】
        try {
            List<WebElement> targetGroupElementList = driver.findElementsByAndroidUIAutomator("new UiSelector().textContains(\"" + targetGroup + "\")");
            for (WebElement targetGroupElement : targetGroupElementList) {
                if ("android.widget.TextView".equals(targetGroupElement.getAttribute("class"))) {
                    targetGroupElement.click();
                    break;
                }
            }
            sw.split();
            logger.info("点击坐标【昵称对应的微信好友群】成功，总共花费 " + sw.toSplitString() + " 秒....");
            Thread.sleep(1000);
        } catch (Exception e) {
            sw.split();
            e.printStackTrace();
            this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
            throw new Exception("长按坐标【昵称对应的微信好友群】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因，总共花费 " + sw.toSplitString() + " 秒....");
        }
        for (int i = 0; i <= 10; i++) {     //发送 文章链接 发送十次，避免 点击坐标【点击微信文章链接】错乱，防止别人发的消息，获取到最左边的消息导致左边计算失败

            //5.点击坐标【聊天内容输入框】
            try {
                driver.findElementByAndroidUIAutomator("new UiSelector().className(\"" + chatInputLocation + "\")").sendKeys(shareArticleUrl);
                sw.split();
                logger.info("点击坐标【聊天输入框】成功，总共花费 " + sw.toSplitString() + " 秒....");
                Thread.sleep(1000);
            } catch (Exception e) {
                sw.split();
                e.printStackTrace();
                this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
                throw new Exception("长按坐标【聊天输入框】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因，总共花费 " + sw.toSplitString() + " 秒....");
            }
            //6.点击坐标【发送】
            try {
                driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + sendBtnLocaltion + "\")").click();
                sw.split();
                logger.info("点击坐标【发送】成功，总共花费 " + sw.toSplitString() + " 秒....");
                Thread.sleep(1000);
            } catch (Exception e) {
                sw.split();
                e.printStackTrace();
                this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
                throw new Exception("点击坐标[发送]出现异常,请检查设备描述[" + deviceNameDesc + "]设备编码[" + deviceName + "]的应用是否更新导致坐标变化等原因，总共花费 " + sw.toSplitString() + " 秒....");
            }
        }
        //7.点击坐标【点击微信文章链接】
        boolean breakFlag = false;
        for (int num = 1; num <= 18; num++) {     //每间隔5秒点击一次，持续90秒
            try {
                //viewWebElementList是成双成对出现的，分别是聊天内容view和头像昵称view
                List<WebElement> viewWebElementList = driver.findElementsByAndroidUIAutomator("new UiSelector().className(\"" + shareArticleUrlLocaltion + "\")");
                System.out.println("第【" + num + "】次 首次获取 android.view.View 成功...");
                if (viewWebElementList != null && viewWebElementList.size() > 0) {
                    List<Integer> x_list_1 = Lists.newArrayList();
                    List<Integer> y_list_1 = Lists.newArrayList();
                    for (int i = 0; i < viewWebElementList.size(); i++) {
                        WebElement viewWebElement = viewWebElementList.get(i);
                        try {
                            Point point = viewWebElement.getLocation();
                            x_list_1.add(point.getX());
                            y_list_1.add(point.getY());
                        } catch (Exception e) {
                            System.out.println("第【" + num + "】次 首次获取 聊天记录坐标 失败，继续下一个坐标...");
                        }
                    }
                    try {
                        System.out.println("第【" + num + "】次 首次整理 min_x = " + Collections.min(x_list_1) + ", max_y = " + Collections.max(y_list_1));
                        viewWebElementList = driver.findElementsByAndroidUIAutomator("new UiSelector().className(\"" + shareArticleUrlLocaltion + "\")");
                        System.out.println("第【" + num + "】次 二次获取 android.view.View 成功...");
                        List<Integer> x_list_2 = Lists.newArrayList();
                        List<Integer> y_list_2 = Lists.newArrayList();
                        for (WebElement viewWebElement : viewWebElementList) {
                            try {
                                Point point = viewWebElement.getLocation();
                                x_list_2.add(point.getX());
                                y_list_2.add(point.getY());
                                if (Collections.min(x_list_1) == point.getX() && Collections.max(y_list_1) == point.getY()) {
                                    for (int i = 0; i <= 50; i++) {
                                        try {
                                            new TouchAction(driver).tap(point.getX() + i, point.getY() + i).release().perform();
                                            Thread.sleep(200);
                                            System.out.println("TouchAction点击微信文章链接 第【" + i + "】次 成功...");
                                        } catch (Exception e) {
                                            System.out.println("TouchAction点击微信文章链接 第【" + i + "】次 失败...");
                                        }
                                        try {
                                            viewWebElementList = driver.findElementsByAndroidUIAutomator("new UiSelector().className(\"" + shareArticleUrlLocaltion + "\")");
                                            if (viewWebElementList != null && viewWebElementList.size() > 0) {
                                                System.out.println("微信文章链接 第【" + i + "】次 尚未跳转...");
                                            } else {
                                                System.out.println("微信文章链接 第【" + i + "】次 跳转成功...");         //大概在第33次成功
                                                breakFlag = true;
                                            }
                                        } catch (Exception e) {
                                            System.out.println("微信文章链接 第【" + i + "】次 跳转成功...");             //大概在第33次成功
                                            breakFlag = true;
                                        } finally {
                                            if (breakFlag) {
                                                break;
                                            }
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("第【" + num + "】次 二次获取 聊天记录坐标 失败，继续下一个坐标...");
                            } finally {
                                if (breakFlag) {
                                    break;
                                } else {
                                    System.out.println("第【" + num + "】次 二次整理 min_x = " + Collections.min(x_list_2) + ", max_y = " + Collections.max(y_list_2));
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("第【" + num + "】次 二次获取 android.view.View 失败...");
                        e.printStackTrace();
                    } finally {
                        if (breakFlag) {
                            break;
                        }
                    }
                    System.out.println("--------------------------------------------------");
                }
            } catch (Exception e) {
                System.out.println("第【" + num + "】次 首次获取 android.view.View 失败...");
                e.printStackTrace();
            }
        }
//        try {
//            Integer shareArticleUrlLocaltion_x1 = shareArticleUrlLocaltion.get("shareArticleUrlLocaltion_x1")!=null?shareArticleUrlLocaltion.get("shareArticleUrlLocaltion_x1"):192;
//            Integer shareArticleUrlLocaltion_y1 = shareArticleUrlLocaltion.get("shareArticleUrlLocaltion_y1")!=null?shareArticleUrlLocaltion.get("shareArticleUrlLocaltion_y1"):1499;
//            Integer shareArticleUrlLocaltion_x2 = shareArticleUrlLocaltion.get("shareArticleUrlLocaltion_x2")!=null?shareArticleUrlLocaltion.get("shareArticleUrlLocaltion_x2"):642;
//            Integer shareArticleUrlLocaltion_y2 = shareArticleUrlLocaltion.get("shareArticleUrlLocaltion_y2")!=null?shareArticleUrlLocaltion.get("shareArticleUrlLocaltion_y2"):1587;
//            Integer shareArticleUrlLocaltion_x = (int)(Math.random()*(shareArticleUrlLocaltion_x2 - shareArticleUrlLocaltion_x1) + shareArticleUrlLocaltion_x1);
//            Integer shareArticleUrlLocaltion_y = (int)(Math.random()*(shareArticleUrlLocaltion_y2 - shareArticleUrlLocaltion_y1) + shareArticleUrlLocaltion_y1);
//            new TouchAction(driver).tap(shareArticleUrlLocaltion_x, shareArticleUrlLocaltion_y).release().perform();
//            sw.split();
//            logger.info("点击坐标【点击微信文章链接】,x = " + shareArticleUrlLocaltion_x + " , y = " + shareArticleUrlLocaltion_y + "成功，总共花费 " + sw.toSplitString() + " 秒....");
//            Thread.sleep(15000);
//        } catch (Exception e) {
//            sw.split();
//            e.printStackTrace();
//            this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
//            throw new Exception("长按坐标【点击微信文章链接】出现异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因，总共花费 " + sw.toSplitString() + " 秒....");
//        }
        //8.向上滑动微信文章
        for(int i = 0; i < 3; i++){
            SwipeUtil.SwipeDown(driver);
            sw.split();
            logger.info("向上滑动【微信文章】成功，总共花费 " + sw.toSplitString() + " 秒....");
            int max = 1500;
            int min = 1000;
            int sleppTime = (int)(min + Math.random() * (max - min + 1));
            Thread.sleep(sleppTime);
        }
//        //9.向上滑动微信文章
//        for(int i = 0; i < 3; i++){
//            SwipeUtil.SwipeUp(driver);
//            sw.split();
//            logger.info("向下滑动【微信文章】成功，总共花费 " + sw.toSplitString() + " 秒....");
//            int max = 1500;
//            int min = 1000;
//            int sleppTime = (int)(min + Math.random() * (max - min + 1));
//            Thread.sleep(sleppTime);
//        }
        //9.点击坐标【右上角的横三点】
        try {
            driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + rightThreePointLocaltion + "\")").click();
//            driver.findElementByAndroidUIAutomator("new UiSelector().description(\"" + rightThreePointLocaltion + "\")").click();
            sw.split();
            logger.info("点击坐标【右上角的横三点】成功，总共花费 " + sw.toSplitString() + " 秒....");
            Thread.sleep(1000);
        } catch (Exception e) {
            sw.split();
            e.printStackTrace();
            this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
            throw new Exception("长按坐标【右上角的横三点】出现异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因，总共花费 " + sw.toSplitString() + " 秒....");
        }
        //10.点击坐标【分享到朋友圈】
        try {
            driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + shareFriendCircleLocaltion + "\")").click();
            sw.split();
            logger.info("点击坐标【分享到朋友圈】成功，总共花费 " + sw.toSplitString() + " 秒....");
            Thread.sleep(1000);
        } catch (Exception e) {
            sw.split();
            e.printStackTrace();
            this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
            throw new Exception("长按坐标【分享到朋友圈】出现异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因，总共花费 " + sw.toSplitString() + " 秒....");
        }
        //11.点击坐标【输入分享文本内容】
        try {
            driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + shareArticleTitleLocaltion + "\")").sendKeys(shareArticleTitle);
            sw.split();
            logger.info("点击坐标【输入分享文本框】成功，总共花费 " + sw.toSplitString() + " 秒....");
            Thread.sleep(1000);
        } catch (Exception e) {
            sw.split();
            e.printStackTrace();
            this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
            throw new Exception("点击坐标【输入分享文本框】出现异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因，总共花费 " + sw.toSplitString() + " 秒....");
        }
        //12.点击坐标【发表】
        try {
            driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + publishBtnLocaltion + "\")").click();
            sw.split();
            logger.info("点击坐标【发表】成功，总共花费 " + sw.toSplitString() + " 秒....");
            Thread.sleep(3000);
        } catch (Exception e) {
            sw.split();
            e.printStackTrace();
            this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
            throw new Exception("长按坐标【发表】出现异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因，总共花费 " + sw.toSplitString() + " 秒....");
        }
        //13.退出驱动
        this.quitDriver(driver, deviceNameDesc, deviceName);
        sw.split();
        logger.info("设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】 发送成功，总共花费 " + sw.toSplitString() + " 秒....");
    }

    /**
     * 退出驱动并重启手机
     *
     * @param driver
     * @param deviceNameDesc
     * @param deviceName
     */
    public void quitDriver(AndroidDriver driver, String deviceNameDesc, String deviceName) {
//        try {
////            Thread.sleep(1000);
////            if (driver != null) {
////                driver.quit();
////            }
//            //关闭 appium 相关进程
//            CommandUtil.run("/opt/android_sdk/platform-tools/adb -s " + deviceName + " shell am force-stop io.appium.settings");
//            CommandUtil.run("/opt/android_sdk/platform-tools/adb -s " + deviceName + " shell am force-stop io.appium.uiautomator2.server");
//            CommandUtil.run("/opt/android_sdk/platform-tools/adb -s " + deviceName + " shell am force-stop io.appium.uiautomator2.test");
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.info("退出driver异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的连接等原因");
//        }
    }

    /**
     * 退出驱动并重启手机
     *
     * @param driver
     * @param deviceNameDesc
     * @param deviceName
     */
    public void quitDriverAndReboot(AndroidDriver driver, String deviceNameDesc, String deviceName) {
//        try {
////            Thread.sleep(1000);
////            if(driver!=null){
////                driver.quit();
////            }
//            try {
//                //关闭 appium 相关进程
//                CommandUtil.run("/opt/android_sdk/platform-tools/adb -s " + deviceName + " shell am force-stop io.appium.settings");
//                CommandUtil.run("/opt/android_sdk/platform-tools/adb -s " + deviceName + " shell am force-stop io.appium.uiautomator2.server");
//                CommandUtil.run("/opt/android_sdk/platform-tools/adb -s " + deviceName + " shell am force-stop io.appium.uiautomator2.test");
////                //重启android设备
////                Thread.sleep(2000);
////                CommandUtil.run("/opt/android_sdk/platform-tools/adb -s " + deviceName + " reboot");
////                logger.info("重启成功，设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】");
//            } catch (Exception e1) {
//                logger.info("重启失败，设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.info("退出driver异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的连接等原因");
////            try{
////                //重启android设备
////                Thread.sleep(2000);
////                CommandUtil.run("/opt/android_sdk/platform-tools/adb -s " + deviceName + " reboot");
////                logger.info("重启成功，设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】");
////            } catch (Exception e1) {
////                logger.info("重启失败，设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】");
////            }
//        }
    }

    public static void main(String[] args) {
        try {
            StopWatch sw = new StopWatch();
            sw.start();
            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("action", "shareArticleToFriendCircle");
            new RealMachineDevices().shareArticleToFriendCircle(paramMap, sw);
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

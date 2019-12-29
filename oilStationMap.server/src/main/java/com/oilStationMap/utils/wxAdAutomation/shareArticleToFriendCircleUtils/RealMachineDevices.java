package com.oilStationMap.utils.wxAdAutomation.shareArticleToFriendCircleUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.oilStationMap.utils.CommandUtil;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * 真机设备 根据微信昵称进行聊天 策略
 * 默认 华为 Mate 8
 */
public class RealMachineDevices implements ShareArticleToFriendCircle{

    public static final Logger logger = LoggerFactory.getLogger(RealMachineDevices.class);

    /**
     * 前置条件：将微信文章群发到【内部交流群】里面
     * 分享微信文章到微信朋友圈
     * @param paramMap
     * @throws Exception
     */
    @Override
    public void shareArticleToFriendCircle(Map<String, Object> paramMap) throws Exception {
        //0.获取参数
        //设备编码
        String deviceName =
                paramMap.get("deviceName")!=null?
                        paramMap.get("deviceName").toString():
                        "5LM0216122009385";
        //设备描述
        String deviceNameDesc =
                paramMap.get("deviceNameDesc")!=null?
                        paramMap.get("deviceNameDesc").toString():
                        "华为 Mate 8 _ 6";
        //操作
        String action =
                paramMap.get("action")!=null?
                        paramMap.get("action").toString():
                        "shareArticleToFriendCircle";
        //微信群昵称
        String targetGroup =
                paramMap.get("targetGroup")!=null?
                        paramMap.get("targetGroup").toString():
                        "内部交流群";
        //微信分享文章URL
        String shareArticleUrl =
                paramMap.get("shareArticleUrl")!=null?
                        paramMap.get("shareArticleUrl").toString():
                        "https://mp.weixin.qq.com/s/gvF-7-uZcFc5MYbMQMtZSw";
        //坐标:搜索
        String searchLocaltionStr =
                paramMap.get("searchLocaltion")!=null?
                        paramMap.get("searchLocaltion").toString():
                                "{\n" +
                                "        \"searchLocaltion_x1\":778,\n" +
                                "        \"searchLocaltion_y1\":72,\n" +
                                "        \"searchLocaltion_x2\":929,\n" +
                                "        \"searchLocaltion_y2\":202\n" +
                                "    }";
        Map<String, Integer> searchLocaltion = JSONObject.parseObject(searchLocaltionStr, Map.class);
        //坐标:搜索框
        String searchInputLocaltion =
                paramMap.get("searchInputLocaltion")!=null?
                        paramMap.get("searchInputLocaltion").toString():
                        "//android.widget.EditText[@resource-id='com.tencent.mm:id/m7']";
        //坐标:昵称对应的微信好友群
        String nickNameFriendLocationStr =
                paramMap.get("nickNameFriendLocation")!=null?
                        paramMap.get("nickNameFriendLocation").toString():
                                "{\n" +
                                "        \"nickNameFriendLocation_x1\":0,\n" +
                                "        \"nickNameFriendLocation_y1\":310,\n" +
                                "        \"nickNameFriendLocation_x2\":1080,\n" +
                                "        \"nickNameFriendLocation_y2\":483\n" +
                                "    }";
        Map<String, Integer> nickNameFriendLocation = JSONObject.parseObject(nickNameFriendLocationStr, Map.class);
        //坐标:昵称对应的微信好友群
        String chatInputLocation =
                paramMap.get("chatInputLocation")!=null?
                        paramMap.get("chatInputLocation").toString():
                        "//android.widget.EditText[@resource-id='com.tencent.mm:id/aqe']";
        //坐标:发送
//        String sendBtnLocaltionStr =
//                paramMap.get("sendBtnLocaltion")!=null?
//                        paramMap.get("sendBtnLocaltion").toString():
//                              "{\n" +
//                                "        \"sendBtnLocaltion_x1\":896,\n" +
//                                "        \"sendBtnLocaltion_y2\":1780,\n" +
//                                "        \"sendBtnLocaltion_x2\":1058,\n" +
//                                "        \"sendBtnLocaltion_y1\":1694\n" +
//                                "    }";
//        Map<String, Integer> sendBtnLocaltion = JSONObject.parseObject(sendBtnLocaltionStr, Map.class);
        String sendBtnLocaltion =
                paramMap.get("sendBtnLocaltion")!=null?
                        paramMap.get("sendBtnLocaltion").toString():
                                "com.tencent.mm:id/aql";
        //坐标:点击微信文章链接
        String shareArticleUrlLocaltionStr =
                paramMap.get("shareArticleUrlLocaltion")!=null?
                        paramMap.get("shareArticleUrlLocaltion").toString():
                                "{\n" +
                                "        \"shareArticleUrlLocaltion_x1\":192,\n" +
                                "        \"shareArticleUrlLocaltion_y1\":1499,\n" +
                                "        \"shareArticleUrlLocaltion_x2\":642,\n" +
                                "        \"shareArticleUrlLocaltion_y2\":1587\n" +
                                "    }";
        Map<String, Integer> shareArticleUrlLocaltion = JSONObject.parseObject(shareArticleUrlLocaltionStr, Map.class);
        //坐标:右上角的横三点
        String rightThreePointLocaltionStr =
                paramMap.get("rightThreePointLocaltion")!=null?
                        paramMap.get("rightThreePointLocaltion").toString():
                                "{\n" +
                                "        \"rightThreePointLocaltion_x1\":929,\n" +
                                "        \"rightThreePointLocaltion_y1\":72,\n" +
                                "        \"rightThreePointLocaltion_x2\":1080,\n" +
                                "        \"rightThreePointLocaltion_y2\":202\n" +
                                "    }";
        Map<String, Integer> rightThreePointLocaltion = JSONObject.parseObject(rightThreePointLocaltionStr, Map.class);
        //坐标:分享朋友圈
        String shareFriendCircleLocaltionStr =
                paramMap.get("shareFriendCircleLocaltion")!=null?
                        paramMap.get("shareFriendCircleLocaltion").toString():
                                "{\n" +
                                "        \"shareFriendCircleLocaltion_x1\":215,\n" +
                                "        \"shareFriendCircleLocaltion_y1\":981,\n" +
                                "        \"shareFriendCircleLocaltion_x2\":366,\n" +
                                "        \"shareFriendCircleLocaltion_y2\":1132\n" +
                                "    }";
        Map<String, Integer> shareFriendCircleLocaltion = JSONObject.parseObject(shareFriendCircleLocaltionStr, Map.class);
        //微信分享文章标题
        String shareArticleTitle =
                paramMap.get("shareArticleTitle")!=null?
                        paramMap.get("shareArticleTitle").toString():
                        "加油站计量有猫腻?!看看正规加油站是怎么做的!";
        //坐标:输入分享文本内容
        String shareArticleTitleLocaltion =
                paramMap.get("shareArticleTitleLocaltion")!=null?
                        paramMap.get("shareArticleTitleLocaltion").toString():
                        "//android.widget.EditText[@resource-id='com.tencent.mm:id/d41']";
        //坐标:发表
        String publishBtnLocaltionStr =
                paramMap.get("publishBtnLocaltion")!=null?
                        paramMap.get("publishBtnLocaltion").toString():
                                "{\n" +
                                "        \"publishBtnLocaltion_x1\":875,\n" +
                                "        \"publishBtnLocaltion_y1\":94,\n" +
                                "        \"publishBtnLocaltion_x2\":1037,\n" +
                                "        \"publishBtnLocaltion_y2\":180\n" +
                                "    }";
        Map<String, Integer> publishBtnLocaltion = JSONObject.parseObject(publishBtnLocaltionStr, Map.class);
        //1.配置连接android驱动
        AndroidDriver driver = null;
        try{
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability("platformName", "Android");           //Android设备
            desiredCapabilities.setCapability("deviceName", deviceName);                  //设备
            desiredCapabilities.setCapability("udid", deviceName);                        //设备唯一标识
            desiredCapabilities.setCapability("appPackage", "com.tencent.mm");      //打开 微信
            desiredCapabilities.setCapability("appActivity", "ui.LauncherUI");      //首个 页面
            desiredCapabilities.setCapability("noReset", true);                     //不用重新安装APK
            desiredCapabilities.setCapability("sessionOverride", true);             //每次启动时覆盖session，否则第二次后运行会报错不能新建session
            desiredCapabilities.setCapability("automationName", "UiAutomator2");
            desiredCapabilities.setCapability("newCommandTimeout", 30);                                  //在下一个命令执行之前的等待最大时长,单位为秒
            desiredCapabilities.setCapability("deviceReadyTimeout", 60);                                 //等待设备就绪的时间,单位为秒
            desiredCapabilities.setCapability("androidDeviceReadyTimeout", 75);                          //等待设备在启动应用后超时时间，单位秒
            desiredCapabilities.setCapability("autoAcceptAlerts", true);             //默认选择接受弹窗的条款，有些app启动的时候，会有一些权限的弹窗
            URL remoteUrl = new URL("http://localhost:"+4723+"/wd/hub");                           //连接本地的appium
            long startTime = System.currentTimeMillis();
            driver = new AndroidDriver(remoteUrl, desiredCapabilities);
            long endTime = System.currentTimeMillis();
            logger.info("设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】连接Appium成功，总共花费 "+((endTime-startTime)/1000)+" 秒....");
            Thread.sleep(10000);                                                                     //加载安卓页面10秒,保证xml树完全加载
        } catch (Exception e) {
            e.printStackTrace();
            this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
            throw new Exception("配置连接android驱动出现异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的环境是否正常运行等原因");
        }
        //2.点击坐标【搜索框】
        try {
            Integer searchLocaltion_x1 = searchLocaltion.get("searchLocaltion_x1")!=null?searchLocaltion.get("searchLocaltion_x1"):778;
            Integer searchLocaltion_y1 = searchLocaltion.get("searchLocaltion_y1")!=null?searchLocaltion.get("searchLocaltion_y1"):72;
            Integer searchLocaltion_x2 = searchLocaltion.get("searchLocaltion_x2")!=null?searchLocaltion.get("searchLocaltion_x2"):929;
            Integer searchLocaltion_y2 = searchLocaltion.get("searchLocaltion_y2")!=null?searchLocaltion.get("searchLocaltion_y2"):202;
            Integer searchLocaltion_x = (int)(Math.random()*(searchLocaltion_x2 - searchLocaltion_x1) + searchLocaltion_x1);
            Integer searchLocaltion_y = (int)(Math.random()*(searchLocaltion_y2 - searchLocaltion_y1) + searchLocaltion_y1);
            Duration duration = Duration.ofMillis(500);
            new TouchAction(driver).press(searchLocaltion_x, searchLocaltion_y).waitAction(WaitOptions.waitOptions(duration)).release().perform();
            logger.info("点击坐标【搜索框】,x = " + searchLocaltion_x + " , y = " + searchLocaltion_y + "成功....");
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
            this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
            throw new Exception("长按坐标【搜索】出现异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因");
        }
        //3.点击坐标【输入昵称到搜索框】
        try {
            driver.findElementByXPath(searchInputLocaltion).sendKeys(targetGroup);
            logger.info("点击坐标【输入昵称到搜索框】成功....");
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
            this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
            throw new Exception("长按坐标【输入昵称到搜索框】出现异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因");
        }
        //4.点击坐标【昵称对应的微信好友群】
        try {
            Integer nickNameFriendLocation_x1 = nickNameFriendLocation.get("nickNameFriendLocation_x1")!=null?nickNameFriendLocation.get("nickNameFriendLocation_x1"):0;
            Integer nickNameFriendLocation_y1 = nickNameFriendLocation.get("nickNameFriendLocation_y1")!=null?nickNameFriendLocation.get("nickNameFriendLocation_y1"):310;
            Integer nickNameFriendLocation_x2 = nickNameFriendLocation.get("nickNameFriendLocation_x2")!=null?nickNameFriendLocation.get("nickNameFriendLocation_x2"):1080;
            Integer nickNameFriendLocation_y2 = nickNameFriendLocation.get("nickNameFriendLocation_y2")!=null?nickNameFriendLocation.get("nickNameFriendLocation_y2"):483;
            Integer nickNameFriendLocation_x = (int)(Math.random()*(nickNameFriendLocation_x2 - nickNameFriendLocation_x1) + nickNameFriendLocation_x1);
            Integer nickNameFriendLocation_y = (int)(Math.random()*(nickNameFriendLocation_y2 - nickNameFriendLocation_y1) + nickNameFriendLocation_y1);
            Duration duration = Duration.ofMillis(500);
            new TouchAction(driver).press(nickNameFriendLocation_x, nickNameFriendLocation_y).waitAction(WaitOptions.waitOptions(duration)).release().perform();
            logger.info("点击坐标【昵称对应的微信好友群】,x = " + nickNameFriendLocation_x + " , y = " + nickNameFriendLocation_y + "成功....");
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
            this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
            throw new Exception("长按坐标【昵称对应的微信好友群】出现异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因");
        }
        //5.点击坐标【输入微信文章链接】
        try {
            driver.findElementByXPath(chatInputLocation).sendKeys(shareArticleUrl);
            logger.info("点击坐标【聊天输入框】成功....");
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
            this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
            throw new Exception("长按坐标【聊天输入框】出现异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因");
        }
        //6.点击坐标【发送】
        try{
            driver.findElementById(sendBtnLocaltion).click();
            logger.info("点击坐标【发送】成功....");
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
            this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
            throw new Exception("点击坐标[发送]出现异常,请检查设备描述["+deviceNameDesc+"]设备编码[" + deviceName + "]的应用是否更新导致坐标变化等原因");
        }
        //7.点击坐标【点击微信文章链接】
        try {
            Integer shareArticleUrlLocaltion_x1 = shareArticleUrlLocaltion.get("shareArticleUrlLocaltion_x1")!=null?shareArticleUrlLocaltion.get("shareArticleUrlLocaltion_x1"):192;
            Integer shareArticleUrlLocaltion_y1 = shareArticleUrlLocaltion.get("shareArticleUrlLocaltion_y1")!=null?shareArticleUrlLocaltion.get("shareArticleUrlLocaltion_y1"):1499;
            Integer shareArticleUrlLocaltion_x2 = shareArticleUrlLocaltion.get("shareArticleUrlLocaltion_x2")!=null?shareArticleUrlLocaltion.get("shareArticleUrlLocaltion_x2"):642;
            Integer shareArticleUrlLocaltion_y2 = shareArticleUrlLocaltion.get("shareArticleUrlLocaltion_y2")!=null?shareArticleUrlLocaltion.get("shareArticleUrlLocaltion_y2"):1587;
            Integer shareArticleUrlLocaltion_x = (int)(Math.random()*(shareArticleUrlLocaltion_x2 - shareArticleUrlLocaltion_x1) + shareArticleUrlLocaltion_x1);
            Integer shareArticleUrlLocaltion_y = (int)(Math.random()*(shareArticleUrlLocaltion_y2 - shareArticleUrlLocaltion_y1) + shareArticleUrlLocaltion_y1);
            new TouchAction(driver).tap(shareArticleUrlLocaltion_x, shareArticleUrlLocaltion_y).release().perform();
            logger.info("点击坐标【点击微信文章链接】,x = " + shareArticleUrlLocaltion_x + " , y = " + shareArticleUrlLocaltion_y + "成功....");
            Thread.sleep(15000);
        } catch (Exception e) {
            e.printStackTrace();
            this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
            throw new Exception("长按坐标【点击微信文章链接】出现异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因");
        }
        //8.点击坐标【右上角的横三点】
        try {
            Integer rightThreePointLocaltion_x1 = rightThreePointLocaltion.get("rightThreePointLocaltion_x1")!=null?rightThreePointLocaltion.get("rightThreePointLocaltion_x1"):929;
            Integer rightThreePointLocaltion_y1 = rightThreePointLocaltion.get("rightThreePointLocaltion_y1")!=null?rightThreePointLocaltion.get("rightThreePointLocaltion_y1"):72;
            Integer rightThreePointLocaltion_x2 = rightThreePointLocaltion.get("rightThreePointLocaltion_x2")!=null?rightThreePointLocaltion.get("rightThreePointLocaltion_x2"):1080;
            Integer rightThreePointLocaltion_y2 = rightThreePointLocaltion.get("rightThreePointLocaltion_y2")!=null?rightThreePointLocaltion.get("rightThreePointLocaltion_y2"):202;
            Integer rightThreePointLocaltion_x = (int)(Math.random()*(rightThreePointLocaltion_x2 - rightThreePointLocaltion_x1) + rightThreePointLocaltion_x1);
            Integer rightThreePointLocaltion_y = (int)(Math.random()*(rightThreePointLocaltion_y2 - rightThreePointLocaltion_y1) + rightThreePointLocaltion_y1);
            Duration duration = Duration.ofMillis(100);
            new TouchAction(driver).press(rightThreePointLocaltion_x, rightThreePointLocaltion_y).waitAction(WaitOptions.waitOptions(duration)).release().perform();
            logger.info("点击坐标【右上角的横三点】,x = " + rightThreePointLocaltion_x + " , y = " + rightThreePointLocaltion_y + "成功....");
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
            this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
            throw new Exception("长按坐标【右上角的横三点】出现异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因");
        }
        //9.点击坐标【分享朋友圈】
        try {
            Integer shareFriendCircleLocaltion_x1 = shareFriendCircleLocaltion.get("shareFriendCircleLocaltion_x1")!=null?shareFriendCircleLocaltion.get("shareFriendCircleLocaltion_x1"):215;
            Integer shareFriendCircleLocaltion_y1 = shareFriendCircleLocaltion.get("shareFriendCircleLocaltion_y1")!=null?shareFriendCircleLocaltion.get("shareFriendCircleLocaltion_y1"):981;
            Integer shareFriendCircleLocaltion_x2 = shareFriendCircleLocaltion.get("shareFriendCircleLocaltion_x2")!=null?shareFriendCircleLocaltion.get("shareFriendCircleLocaltion_x2"):366;
            Integer shareFriendCircleLocaltion_y2 = shareFriendCircleLocaltion.get("shareFriendCircleLocaltion_y2")!=null?shareFriendCircleLocaltion.get("shareFriendCircleLocaltion_y2"):1132;
            Integer shareFriendCircleLocaltion_x = (int)(Math.random()*(shareFriendCircleLocaltion_x2 - shareFriendCircleLocaltion_x1) + shareFriendCircleLocaltion_x1);
            Integer shareFriendCircleLocaltion_y = (int)(Math.random()*(shareFriendCircleLocaltion_y2 - shareFriendCircleLocaltion_y1) + shareFriendCircleLocaltion_y1);
            Duration duration = Duration.ofMillis(500);
            new TouchAction(driver).press(shareFriendCircleLocaltion_x, shareFriendCircleLocaltion_y).waitAction(WaitOptions.waitOptions(duration)).release().perform();
            logger.info("点击坐标【分享朋友圈】,x = " + shareFriendCircleLocaltion_x + " , y = " + shareFriendCircleLocaltion_y + "成功....");
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
            this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
            throw new Exception("长按坐标【分享朋友圈】出现异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因");
        }
        //10.点击坐标【输入分享文本内容】
        try {
            driver.findElementByXPath(shareArticleTitleLocaltion).sendKeys(shareArticleTitle);
            logger.info("点击坐标【输入分享文本内容】成功....");
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
            this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
            throw new Exception("长按坐标【输入分享文本内容】出现异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因");
        }
        //11.点击坐标【发表】
        try {
            Integer publishBtnLocaltion_x1 = publishBtnLocaltion.get("publishBtnLocaltion_x1")!=null?publishBtnLocaltion.get("publishBtnLocaltion_x1"):875;
            Integer publishBtnLocaltion_y1 = publishBtnLocaltion.get("publishBtnLocaltion_y1")!=null?publishBtnLocaltion.get("publishBtnLocaltion_y1"):94;
            Integer publishBtnLocaltion_x2 = publishBtnLocaltion.get("publishBtnLocaltion_x2")!=null?publishBtnLocaltion.get("publishBtnLocaltion_x2"):1037;
            Integer publishBtnLocaltion_y2 = publishBtnLocaltion.get("publishBtnLocaltion_y2")!=null?publishBtnLocaltion.get("publishBtnLocaltion_y2"):180;
            Integer publishBtnLocaltion_x = (int)(Math.random()*(publishBtnLocaltion_x2 - publishBtnLocaltion_x1) + publishBtnLocaltion_x1);
            Integer publishBtnLocaltion_y = (int)(Math.random()*(publishBtnLocaltion_y2 - publishBtnLocaltion_y1) + publishBtnLocaltion_y1);
            Duration duration = Duration.ofMillis(500);
            new TouchAction(driver).press(publishBtnLocaltion_x, publishBtnLocaltion_y).waitAction(WaitOptions.waitOptions(duration)).release().perform();
            logger.info("点击坐标【发表】,x = " + publishBtnLocaltion_x + " , y = " + publishBtnLocaltion_y + "成功....");
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
            this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
            throw new Exception("长按坐标【发表】出现异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因");
        }
        //12.退出驱动
        this.quitDriver(driver, deviceNameDesc, deviceName);
        logger.info( "设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】操作【" + action + "】 发送成功!!!");
    }

    /**
     * 退出驱动并重启手机
     * @param driver
     * @param deviceNameDesc
     * @param deviceName
     */
    public void quitDriver(AndroidDriver driver, String deviceNameDesc, String deviceName) {
        try {
            Thread.sleep(3000);
            if (driver != null) {
                driver.quit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("退出driver异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的连接等原因");
        }
    }

    /**
     * 退出驱动并重启手机
     * @param driver
     * @param deviceNameDesc
     * @param deviceName
     */
    public void quitDriverAndReboot(AndroidDriver driver, String deviceNameDesc, String deviceName){
        try {
            Thread.sleep(3000);
            if(driver!=null){
                driver.quit();
            }
            try{
                //重启android设备
                Thread.sleep(2000);
                CommandUtil.run("/Users/caihongwang/我的文件/android-sdk/platform-tools/adb -s " + deviceName + " reboot");
                logger.info("重启成功，设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】");
            } catch (Exception e1) {
                logger.info("重启失败，设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("退出driver异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的连接等原因");
            try{
                //重启android设备
                Thread.sleep(2000);
                CommandUtil.run("/Users/caihongwang/我的文件/android-sdk/platform-tools/adb -s " + deviceName + " reboot");
                logger.info("重启成功，设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】");
            } catch (Exception e1) {
                logger.info("重启失败，设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】");
            }
        }
    }

    public static void main(String[] args) {
        try{
            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("action", "shareArticleToFriendCircle");
            new RealMachineDevices().shareArticleToFriendCircle(paramMap);
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

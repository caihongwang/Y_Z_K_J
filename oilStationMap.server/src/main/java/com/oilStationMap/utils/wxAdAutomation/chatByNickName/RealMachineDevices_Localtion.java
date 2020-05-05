package com.oilStationMap.utils.wxAdAutomation.chatByNickName;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.oilStationMap.utils.CommandUtil;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import org.apache.commons.lang.time.StopWatch;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.time.Duration;
import java.util.Map;

/**
 * 真机设备 根据微信昵称进行聊天 策略
 * 默认 华为 Mate 8
 */
public class RealMachineDevices_Localtion implements ChatByNickName {

    public static final Logger logger = LoggerFactory.getLogger(RealMachineDevices_Localtion.class);

    /**
     * 根据微信昵称进行聊天
     *
     * @param paramMap
     * @throws Exception
     */
    @Override
    public void chatByNickName(Map<String, Object> paramMap, StopWatch sw) throws Exception {
        //0.获取参数
        //设备编码
        String deviceName =
                paramMap.get("deviceName") != null ?
                        paramMap.get("deviceName").toString() :
                        "5LM0216122009385";
        //设备描述
        String deviceNameDesc =
                paramMap.get("deviceNameDesc") != null ?
                        paramMap.get("deviceNameDesc").toString() :
                        "华为 Mate 8 _ 6";
        //操作
        String action =
                paramMap.get("action") != null ?
                        paramMap.get("action").toString() :
                        "chatByNickName";
        //微信昵称
        String nickName =
                paramMap.get("nickName") != null ?
                        paramMap.get("nickName").toString() :
                        "caihongwang976499921";
        //微信昵称
        String textMessage =
                paramMap.get("textMessage") != null ?
                        paramMap.get("textMessage").toString() :
                        "亲！您的内容已转发朋友圈，快去评论吧，评论可以置顶，更多人能看得到！";
        //坐标:搜索
        String searchLocaltionStr =
                paramMap.get("searchLocaltion") != null ?
                        paramMap.get("searchLocaltion").toString() :
                        "{\n" +
                                "        \"searchLocaltion_x1\":778,\n" +
                                "        \"searchLocaltion_y1\":72,\n" +
                                "        \"searchLocaltion_x2\":929,\n" +
                                "        \"searchLocaltion_y2\":202\n" +
                                "    }";
        Map<String, Integer> searchLocaltion = JSONObject.parseObject(searchLocaltionStr, Map.class);
        //坐标:搜索框
        String searchInputLocaltion =
                paramMap.get("searchInputLocaltion") != null ?
                        paramMap.get("searchInputLocaltion").toString() :
                        "//android.widget.EditText[@resource-id='com.tencent.mm:id/m7']";
        //坐标:昵称对应的微信好友
        String nickNameFriendLocationStr =
                paramMap.get("nickNameFriendLocation") != null ?
                        paramMap.get("nickNameFriendLocation").toString() :
                        "{\n" +
                                "        \"nickNameFriendLocation_x1\":0,\n" +
                                "        \"nickNameFriendLocation_y1\":310,\n" +
                                "        \"nickNameFriendLocation_x2\":1080,\n" +
                                "        \"nickNameFriendLocation_y2\":483\n" +
                                "    }";
        Map<String, Integer> nickNameFriendLocation = JSONObject.parseObject(nickNameFriendLocationStr, Map.class);
        //坐标:昵称对应的微信好友
        String chatInputLocation =
                paramMap.get("chatInputLocation") != null ?
                        paramMap.get("chatInputLocation").toString() :
                        "//android.widget.EditText[@resource-id='com.tencent.mm:id/aqe']";
        //坐标:发送
        String sendBtnLocaltion =
                paramMap.get("sendBtnLocaltion") != null ?
                        paramMap.get("sendBtnLocaltion").toString() :
                        "com.tencent.mm:id/aql";
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
            desiredCapabilities.setCapability("newCommandTimeout", 15);                                 //在下一个命令执行之前的等待最大时长,单位为秒
            desiredCapabilities.setCapability("deviceReadyTimeout", 30);                                //等待设备就绪的时间,单位为秒
            desiredCapabilities.setCapability("uiautomator2ServerLaunchTimeout", 15000);                //等待uiAutomator2服务启动的超时时间，单位毫秒
            desiredCapabilities.setCapability("uiautomator2ServerInstallTimeout", 15000);               //等待uiAutomator2服务安装的超时时间，单位毫秒
            desiredCapabilities.setCapability("androidDeviceReadyTimeout", 30);                         //等待设备在启动应用后超时时间，单位秒
            desiredCapabilities.setCapability("autoAcceptAlerts", true);                                //默认选择接受弹窗的条款，有些app启动的时候，会有一些权限的弹窗
            desiredCapabilities.setCapability("waitForSelectorTimeout", 10000);                         //寻找组件的超时时间，单位毫秒
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
        //2.点击坐标【搜索】
        try {
            Integer searchLocaltion_x1 = searchLocaltion.get("searchLocaltion_x1") != null ? searchLocaltion.get("searchLocaltion_x1") : 778;
            Integer searchLocaltion_y1 = searchLocaltion.get("searchLocaltion_y1") != null ? searchLocaltion.get("searchLocaltion_y1") : 72;
            Integer searchLocaltion_x2 = searchLocaltion.get("searchLocaltion_x2") != null ? searchLocaltion.get("searchLocaltion_x2") : 929;
            Integer searchLocaltion_y2 = searchLocaltion.get("searchLocaltion_y2") != null ? searchLocaltion.get("searchLocaltion_y2") : 202;
            Integer searchLocaltion_x = (int) (Math.random() * (searchLocaltion_x2 - searchLocaltion_x1) + searchLocaltion_x1);
            Integer searchLocaltion_y = (int) (Math.random() * (searchLocaltion_y2 - searchLocaltion_y1) + searchLocaltion_y1);
            Duration duration = Duration.ofMillis(500);
            new TouchAction(driver).press(searchLocaltion_x, searchLocaltion_y).waitAction(WaitOptions.waitOptions(duration)).release().perform();
            sw.split();
            logger.info("点击坐标【搜索】,x = " + searchLocaltion_x + " , y = " + searchLocaltion_y + "成功，总共花费 " + sw.toSplitString() + " 秒....");
            Thread.sleep(1500);
        } catch (Exception e) {
            sw.split();
            e.printStackTrace();
            this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
            throw new Exception("长按坐标【搜索】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因，总共花费 " + sw.toSplitString() + " 秒....");
        }
        //3.点击坐标【搜索框】
        try {
            driver.findElementByXPath(searchInputLocaltion).sendKeys(nickName);
            sw.split();
            logger.info("点击坐标【搜索框】成功，总共花费 " + sw.toSplitString() + " 秒....");
            Thread.sleep(1500);

        } catch (Exception e) {
            sw.split();
            e.printStackTrace();
            this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
            throw new Exception("长按坐标【搜索框】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因，总共花费 " + sw.toSplitString() + " 秒....");
        }
        //4.点击坐标【昵称对应的微信好友】
        try {
            Integer nickNameFriendLocation_x1 = nickNameFriendLocation.get("nickNameFriendLocation_x1") != null ? nickNameFriendLocation.get("nickNameFriendLocation_x1") : 0;
            Integer nickNameFriendLocation_y1 = nickNameFriendLocation.get("nickNameFriendLocation_y1") != null ? nickNameFriendLocation.get("nickNameFriendLocation_y1") : 310;
            Integer nickNameFriendLocation_x2 = nickNameFriendLocation.get("nickNameFriendLocation_x2") != null ? nickNameFriendLocation.get("nickNameFriendLocation_x2") : 1080;
            Integer nickNameFriendLocation_y2 = nickNameFriendLocation.get("nickNameFriendLocation_y2") != null ? nickNameFriendLocation.get("nickNameFriendLocation_y2") : 483;
            Integer nickNameFriendLocation_x = (int) (Math.random() * (nickNameFriendLocation_x2 - nickNameFriendLocation_x1) + nickNameFriendLocation_x1);
            Integer nickNameFriendLocation_y = (int) (Math.random() * (nickNameFriendLocation_y2 - nickNameFriendLocation_y1) + nickNameFriendLocation_y1);
            Duration duration = Duration.ofMillis(500);
            new TouchAction(driver).press(nickNameFriendLocation_x, nickNameFriendLocation_y).waitAction(WaitOptions.waitOptions(duration)).release().perform();
            sw.split();
            logger.info("点击坐标【昵称对应的微信好友】,x = " + nickNameFriendLocation_x + " , y = " + nickNameFriendLocation_y + "成功，总共花费 " + sw.toSplitString() + " 秒....");
            Thread.sleep(1500);
        } catch (Exception e) {
            sw.split();
            e.printStackTrace();
            this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
            throw new Exception("长按坐标【昵称对应的微信好友】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因，总共花费 " + sw.toSplitString() + " 秒....");
        }
        //5.点击坐标【聊天输入框】
        try {
            driver.findElementByXPath(chatInputLocation).sendKeys(textMessage);
            driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\""+sendBtnLocaltion+"\")").click();
            sw.split();
            logger.info("点击坐标【聊天输入框】成功，总共花费 " + sw.toSplitString() + " 秒....");
            Thread.sleep(1500);
        } catch (Exception e) {
            sw.split();
            e.printStackTrace();
            this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
            throw new Exception("长按坐标【聊天输入框】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因，总共花费 " + sw.toSplitString() + " 秒....");
        }
        //5.点击坐标【发送】
        try {
//            driver.findElementById(sendBtnLocaltion).click();
            driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\""+sendBtnLocaltion+"\")").click();
            sw.split();
            logger.info("点击坐标【发送】成功，总共花费 " + sw.toSplitString() + " 秒....");
            Thread.sleep(3000);
        } catch (Exception e) {
            sw.split();
            e.printStackTrace();
            this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
            throw new Exception("点击坐标[发送]出现异常,请检查设备描述[" + deviceNameDesc + "]设备编码[" + deviceName + "]的应用是否更新导致坐标变化等原因，总共花费 " + sw.toSplitString() + " 秒....");
        }
        //6.退出驱动
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
        try {
//            Thread.sleep(1000);
//            if (driver != null) {
//                driver.quit();
//            }
            //关闭 appium 相关进程
            CommandUtil.run("/Users/caihongwang/我的文件/android-sdk/platform-tools/adb -s " + deviceName + " shell am force-stop io.appium.settings");
            CommandUtil.run("/Users/caihongwang/我的文件/android-sdk/platform-tools/adb -s " + deviceName + " shell am force-stop io.appium.uiautomator2.server");
            CommandUtil.run("/Users/caihongwang/我的文件/android-sdk/platform-tools/adb -s " + deviceName + " shell am force-stop io.appium.uiautomator2.test");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("退出driver异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的连接等原因");
        }
    }

    /**
     * 退出驱动并重启手机
     *
     * @param driver
     * @param deviceNameDesc
     * @param deviceName
     */
    public void quitDriverAndReboot(AndroidDriver driver, String deviceNameDesc, String deviceName) {
        try {
//            Thread.sleep(1000);
//            if (driver != null) {
//                driver.quit();
//            }
            try {
                //关闭 appium 相关进程
                CommandUtil.run("/Users/caihongwang/我的文件/android-sdk/platform-tools/adb -s " + deviceName + " shell am force-stop io.appium.settings");
                CommandUtil.run("/Users/caihongwang/我的文件/android-sdk/platform-tools/adb -s " + deviceName + " shell am force-stop io.appium.uiautomator2.server");
                CommandUtil.run("/Users/caihongwang/我的文件/android-sdk/platform-tools/adb -s " + deviceName + " shell am force-stop io.appium.uiautomator2.test");
//                //重启android设备
//                Thread.sleep(2000);
//                CommandUtil.run("/Users/caihongwang/我的文件/android-sdk/platform-tools/adb -s " + deviceName + " reboot");
                logger.info("重启成功，设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】");
            } catch (Exception e1) {
                logger.info("重启失败，设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("退出driver异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的连接等原因");
//            try {
//                //重启android设备
//                Thread.sleep(2000);
//                CommandUtil.run("/Users/caihongwang/我的文件/android-sdk/platform-tools/adb -s " + deviceName + " reboot");
//                logger.info("重启成功，设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】");
//            } catch (Exception e1) {
//                logger.info("重启失败，设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】");
//            }
        }
    }

    public static void main(String[] args) {
        try {
            StopWatch sw = new StopWatch();
            sw.start();
            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("action", "chatByNickName");
            paramMap.put("deviceName", "AXS0216620000895");
            paramMap.put("deviceNameDesc", "华为 Mate 8 _ 2");
            new RealMachineDevices_Localtion().chatByNickName(paramMap, sw);
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

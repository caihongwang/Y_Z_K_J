package com.automation.utils.wei_xin.shareVideoNumToFriendCircle;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 真机设备 分享视频号到朋友圈 策略
 * 注：只支持最后一条消息是【视频号-小程序】
 * 默认 小米Max3
 */
public class RealMachineDevices implements ShareVideoNumToFriendCircle {

    public static final Logger logger = LoggerFactory.getLogger(RealMachineDevices.class);

    /**
     * 前置条件：将微信消息群发到【油站科技-内部交流群】里面
     * 分享视频号到朋友圈
     *
     * @param paramMap
     * @throws Exception
     */
    @Override
    public boolean shareVideoNumToFriendCircle(Map<String, Object> paramMap) throws Exception {
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
                        "shareVideoNumToFriendCircle";
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
        //点击坐标【获取所有的微信消息RelativeLayout】
        String chatContentRelativeLayoutLocaltion =
                paramMap.get("chatContentRelativeLayoutLocaltion") != null ?
                        paramMap.get("chatContentRelativeLayoutLocaltion").toString() :
                        "//android.widget.ListView/android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.LinearLayout";
        //点击坐标【转发】
        String relayLocaltion =
                paramMap.get("relayLocaltion") != null ?
                        paramMap.get("relayLocaltion").toString() :
                        "转发";
        //点击坐标【分享到朋友圈】
        String shareFriendCircleLocaltion =
                paramMap.get("shareFriendCircleLocaltion") != null ?
                        paramMap.get("shareFriendCircleLocaltion").toString() :
                        "分享到朋友圈";
        //分享朋友圈内容
        String shareFendCircleCentent =
                paramMap.get("shareFendCircleCentent") != null ?
                        paramMap.get("shareFendCircleCentent").toString() :
                        "恩，怎么说呢，你还是自己看吧，文字描述已无力...";
        //坐标:输入分享文本框
        String shareFendCircleCententLocaltion =
                paramMap.get("shareFendCircleCententLocaltion") != null ?
                        paramMap.get("shareFendCircleCententLocaltion").toString() :
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
            logger.info("【分享视频号到朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】连接Appium【" + appiumPort + "】成功....");
            Thread.sleep(10000);                                                                     //加载安卓页面10秒,保证xml树完全加载
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("【分享视频号到朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】配置连接android驱动出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】Appium端口号【" + appiumPort + "】的环境是否正常运行等原因....");
        }

        System.out.println("当前activity = " + driver.currentActivity());
        Activity chatActivity = new Activity("com.tencent.mm", ".ui.LauncherUI");
        for (int i = 1; i <= 30; i++) {     //每间隔5秒点击一次，持续90秒
            //2.点击坐标【搜索】，当前坐标会引起微信对当前所有联系人和聊天对象进行建立索引，会有点慢，需要进行特别支持，暂时循环点击10次
            try {
                driver.findElementByAndroidUIAutomator("new UiSelector().description(\"" + searchLocaltionStr + "\")").click();
                logger.info("【分享视频号到朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【搜索框】成功....");
                Thread.sleep(5000);         //此处会创建索引，会比较费时间才能打开
            } catch (Exception e) {
                logger.info("【分享视频号到朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【搜索框】失败，因为微信正在建立索引....");
                if (i == 30) {
                    throw new Exception("【分享视频号到朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【搜索框】均失败,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
                } else if(i == 15){        //当点击15次均无法成功点击坐标【搜索】，则通过重启当前应用【微信】处理
                    driver.startActivity(chatActivity);      //返回【当前页面聊天好友信息】
                    logger.info("【分享视频号到朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】次点击坐标【搜索框】失败，通过重启当前应用【微信】处理....");
                    continue;
                } else {
                    Thread.sleep(5000);         //此处会创建索引，会比较费时间才能打开
                    logger.info("【分享视频号到朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】次点击坐标【搜索框】失败，因为微信正在建立索引....");
                    continue;
                }
            }
            //3.点击坐标【搜索输入框】并输入昵称
            try {
                driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + searchInputLocaltion + "\")").sendKeys(targetGroup);
                logger.info("【分享视频号到朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【输入昵称到搜索框:text/搜索】成功....");
                Thread.sleep(1000);
                break;
            } catch (Exception e) {
                logger.info("【分享视频号到朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【输入昵称到搜索框:text/搜索】失败....");
                try {
                    driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.EditText\")").sendKeys(targetGroup);
                    logger.info("【分享视频号到朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【输入昵称到搜索框:className/android.widget.EditText】成功....");
                    Thread.sleep(1000);
                    break;
                } catch (Exception e1) {
                    logger.info("【分享视频号到朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【输入昵称到搜索框:className/android.widget.EditText】失败....");
                    if (i == 30) {
                        throw new Exception("【分享视频号到朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【输入昵称到搜索框:text/搜索】与【输入昵称到搜索框:className/android.widget.EditText】均失败,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
                    } else if(i == 15){        //当点击15次均无法成功点击坐标【搜索】，则通过重启当前应用【微信】处理
                        driver.startActivity(chatActivity);      //返回【当前页面聊天好友信息】
                        logger.info("【分享视频号到朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】次点击坐标【搜索框】失败，通过重启当前应用【微信】处理....");
                        continue;
                    } else {
                        Thread.sleep(5000);         //此处会创建索引，会比较费时间才能打开
                        logger.info("【分享视频号到朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】次点击坐标【输入昵称到搜索框:text/搜索】与【输入昵称到搜索框:className/android.widget.EditText】均失败，因为微信正在建立索引....");
                        continue;
                    }
                }
            }
        }
        //4.判断坐标【群聊】与【最常使用】是否存在
        try {
            driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + groupLocaltion + "\")");
            logger.info("【分享视频号到朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【群聊】成功....");
            Thread.sleep(1000);
        } catch (Exception e) {
            try {
                driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + mostUsedLocaltion + "\")");
                logger.info("【分享视频号到朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【最常使用】成功....");
                Thread.sleep(1000);
            } catch (Exception e1) {
                throw new Exception("【分享视频号到朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】判断坐标【联系人】与【最常使用】均不存在，当前昵称【" + targetGroup + "】对应的可能是【微信群】或者【公众号】或者【聊天记录】....");

            }
        }
        //5.点击坐标【昵称对应的微信好友群】
        try {
            driver.findElementByXPath("//android.widget.TextView[@text=\"" + groupLocaltion + "\"]/../../../android.widget.RelativeLayout[2]").click();
            logger.info("【分享视频号到朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【昵称对应的微信好友群】通过【联系人的xpath】成功....");
            Thread.sleep(1000);
        } catch (Exception e) {
            try {
                driver.findElementByXPath("//android.widget.TextView[@text=\"" + mostUsedLocaltion + "\"]/../../../android.widget.RelativeLayout[2]").click();
                logger.info("【分享视频号到朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【昵称对应的微信好友群】通过【最常使用的xpath】成功....");
                Thread.sleep(1000);
            } catch (Exception e1) {
                throw new Exception("【分享视频号到朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】通过【联系人的xpath】与【最常使用的xpath】点击坐标【昵称对应的微信好友】均失败，当前昵称【\" + nickName + \"】对应的可能是【微信群】或者【公众号】或者【聊天记录】....");
            }
        }
        //6.获取所有的微信消息RelativeLayout //android.widget.ListView/android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.LinearLayout
        List<WebElement> chatContentRelativeLayoutList = Lists.newArrayList();
        try {
            chatContentRelativeLayoutList = driver.findElementsByXPath(chatContentRelativeLayoutLocaltion);
            logger.info("【分享视频号到朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】获取所有的微信消息RelativeLayout 成功....");
            Thread.sleep(1000);
        } catch (Exception e) {
            throw new Exception("【分享视频号到朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】获取所有的微信消息RelativeLayout 异常....");
        }
        //7.点击最后一条聊天消息弹出视频内容
        try {
//            Duration duration = Duration.ofMillis(1000);
//            new TouchAction(driver).press(chatContentRelativeLayoutList.get(chatContentRelativeLayoutList.size() - 1)).waitAction(WaitOptions.waitOptions(duration)).release().perform();
            chatContentRelativeLayoutList.get(chatContentRelativeLayoutList.size() - 1).click();
            logger.info("【分享视频号到朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击最后一条聊天消息弹出视频内容成功....");
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("【分享视频号到朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击最后一条聊天消息弹出视频内容异常，" + e.getMessage());
        }
        //8.点击坐标【转发】
        try {
            driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + relayLocaltion + "\")").click();
            logger.info("【分享视频号到朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【转发】成功....");
            Thread.sleep(8000);     //此页面中存在从视频，分析整体Dom树比较慢
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("【分享视频号到朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【转发】出现异常，可能当前消息不是视频号，请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
        }
        //9.点击坐标【分享到朋友圈】
        try {
            driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + shareFriendCircleLocaltion + "\")").click();
            logger.info("【分享视频号到朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【分享到朋友圈】成功....");
            Thread.sleep(8000);     //此页面中存在从视频，分析整体Dom树比较慢
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("【分享视频号到朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【转发】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
        }
        //10.点击坐标【输入分享文本内容】
        try {
            driver.findElementByAndroidUIAutomator("new UiSelector().textStartsWith(\"" + shareFendCircleCententLocaltion + "\")").sendKeys(shareFendCircleCentent);
            logger.info("【分享视频号到朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】findElementByAndroidUIAutomator 点击坐标【输入分享文本框】成功....");
            Thread.sleep(1000);
        } catch (Exception e) {
            logger.info("【分享视频号到朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】findElementByAndroidUIAutomator 点击坐标【输入分享文本框】失败....");
            try {
                driver.findElementByXPath("//android.widget.EditText[@text='" + shareFendCircleCententLocaltion + "']").sendKeys(shareFendCircleCentent);
                logger.info("【分享视频号到朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】findElementByXPath 点击坐标【输入分享文本框】成功....");
            } catch (Exception e1) {
                e.printStackTrace();
                logger.info("【分享视频号到朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】findElementByAndroidUIAutomator与findElementByXPath 点击坐标【输入分享文本框】均失败....");
                throw new Exception("【分享视频号到朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【输入分享文本框】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
            }
        }
        //11.点击坐标【发表】
        try {
            driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + publishBtnLocaltion + "\")").click();
            logger.info("【分享视频号到朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【发表】成功....");
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("【分享视频号到朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【发表】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
        }
        logger.info("【分享视频号到朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】 发送成功....");
        return true;
    }

    public static void main(String[] args) {
        try {
            Map<String, Object> paramMap = Maps.newHashMap();
//            paramMap.put("deviceName", "S2D0219423001056");
//            paramMap.put("deviceNameDesc", "华为 Mate 20 Pro");
            paramMap.put("deviceName", "9f4eda95");
            paramMap.put("deviceNameDesc", "小米 Max 3");
            paramMap.put("action", "shareVideoNumToFriendCircle");
            new RealMachineDevices().shareVideoNumToFriendCircle(paramMap);
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

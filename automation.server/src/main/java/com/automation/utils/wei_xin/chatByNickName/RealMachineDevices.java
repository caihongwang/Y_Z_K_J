package com.automation.utils.wei_xin.chatByNickName;

import com.google.common.collect.Maps;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Map;

/**
 * 真机设备 根据微信昵称进行聊天 策略
 * 默认 华为 Mate 8
 */
public class RealMachineDevices implements ChatByNickName {

    public static final Logger logger = LoggerFactory.getLogger(RealMachineDevices.class);

    /**
     * 根据微信昵称进行聊天
     *
     * @param paramMap
     * @throws Exception
     */
    @Override
    public boolean chatByNickName(Map<String, Object> paramMap) throws Exception {
        //0.获取参数
        //设备编码
        String deviceName =
                paramMap.get("deviceName") != null ?
                        paramMap.get("deviceName").toString() :
                        "D5F0218325003946";
        //设备描述
        String deviceNameDesc =
                paramMap.get("deviceNameDesc") != null ?
                        paramMap.get("deviceNameDesc").toString() :
                        "华为 P20 Pro";
        //appium端口号
        String appiumPort =
                paramMap.get("appiumPort") != null ?
                        paramMap.get("appiumPort").toString() :
                        "4723";
        //操作
        String action =
                paramMap.get("action") != null ?
                        paramMap.get("action").toString() :
                        "chatByNickName";
        //微信昵称
        String nickName =
                paramMap.get("nickName") != null ?
                        paramMap.get("nickName").toString() :
                        "cai_hong_wang";
        //微信昵称
        String textMessage =
                paramMap.get("textMessage") != null ?
                        paramMap.get("textMessage").toString() :
                        "亲！您的内容已转发朋友圈，快去评论吧，评论有助于置顶，更多人能看得到！";
        //坐标:搜索框
        String searchLocaltion =
                paramMap.get("searchLocaltion") != null ?
                        paramMap.get("searchLocaltion").toString() :
                        "搜索";
        //坐标:搜索输入框
        String searchInputLocaltion =
                paramMap.get("searchInputLocaltion") != null ?
                        paramMap.get("searchInputLocaltion").toString() :
                        "搜索";
        //坐标:联系人
        String contactLocaltion =
                paramMap.get("contactLocaltion") != null ?
                        paramMap.get("contactLocaltion").toString() :
                        "联系人";
        //坐标:最常使用
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
            logger.info("【根据微信昵称进行聊天】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】连接Appium【" + appiumPort + "】成功....");
            Thread.sleep(10000);                                                                     //加载安卓页面10秒,保证xml树完全加载
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("【根据微信昵称进行聊天】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】配置连接android驱动出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】Appium端口号【" + appiumPort + "】的环境是否正常运行等原因....");
        }
        for (int i = 1; i <= 30; i++) {     //每间隔5秒点击一次，持续90秒
            //1.点击坐标【搜索】，当前坐标会引起微信对当前所有联系人和聊天对象进行建立索引，会有点慢，需要进行特别支持，暂时循环点击10次
            try {
                driver.findElementByAndroidUIAutomator("new UiSelector().description(\"" + searchLocaltion + "\")").click();
                logger.info("【根据微信昵称进行聊天】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【搜索框】成功....");
                Thread.sleep(5000);         //此处会创建索引，会比较费时间才能打开
            } catch (Exception e) {
                logger.info("【根据微信昵称进行聊天】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【搜索框】失败，因为微信正在建立索引....");
                if (i == 30) {
                    throw new Exception("【根据微信昵称进行聊天】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【搜索框】均失败,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
                } else {
                    Thread.sleep(5000);         //此处会创建索引，会比较费时间才能打开
                    logger.info("【根据微信昵称进行聊天】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】次点击坐标【搜索框】失败，因为微信正在建立索引....");
                    continue;
                }
            }
            //2.点击坐标【搜索输入框】并输入昵称
            try {
                driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + searchInputLocaltion + "\")").sendKeys(nickName);
                logger.info("【根据微信昵称进行聊天】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【输入昵称到搜索框:text/搜索】成功....");
                Thread.sleep(1000);
                break;
            } catch (Exception e) {
                logger.info("【根据微信昵称进行聊天】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【输入昵称到搜索框:text/搜索】失败....");
                try {
                    driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.EditText\")").sendKeys(nickName);
                    logger.info("【根据微信昵称进行聊天】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【输入昵称到搜索框:className/android.widget.EditText】成功....");
                    Thread.sleep(1000);
                    break;
                } catch (Exception e1) {
                    logger.info("【根据微信昵称进行聊天】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【输入昵称到搜索框:className/android.widget.EditText】失败....");
                    if (i == 30) {
                        throw new Exception("【根据微信昵称进行聊天】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【输入昵称到搜索框:text/搜索】与【输入昵称到搜索框:className/android.widget.EditText】均失败,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
                    } else {
                        Thread.sleep(5000);         //此处会创建索引，会比较费时间才能打开
                        logger.info("【根据微信昵称进行聊天】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】次点击坐标【输入昵称到搜索框:text/搜索】与【输入昵称到搜索框:className/android.widget.EditText】均失败，因为微信正在建立索引....");
                        continue;
                    }
                }
            }
        }
        //3.判断坐标【联系人】与【最常使用】是否存在
        try {
            WebElement contact_WebElement = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + contactLocaltion + "\")");
            Thread.sleep(1000);
        } catch (Exception e) {
            try {
                WebElement contactor_WebElement = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + mostUsedLocaltion + "\")");
                Thread.sleep(1000);
            } catch (Exception e1) {
                logger.info("【根据微信昵称进行聊天】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】判断坐标【联系人】与【最常使用】均不存在，当前昵称【" + nickName + "】对应的可能是【微信群】或者【公众号】或者【聊天记录】....");
                return false;
            }
        }
        //4.点击坐标【昵称对应的微信好友】
        try {
            driver.findElementByXPath("//android.widget.TextView[@text=\"" + contactLocaltion + "\"]/../../../android.widget.RelativeLayout[2]").click();
            logger.info("【根据微信昵称进行聊天】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【昵称对应的微信好友群】通过【联系人的xpath】成功....");
            Thread.sleep(1000);
        } catch (Exception e) {
            try {
                driver.findElementByXPath("//android.widget.TextView[@text=\"" + mostUsedLocaltion + "\"]/../../../android.widget.RelativeLayout[2]").click();
                logger.info("【根据微信昵称进行聊天】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【昵称对应的微信好友群】通过【最常使用的xpath】成功....");
                Thread.sleep(1000);
            } catch (Exception e1) {
                logger.info("【根据微信昵称进行聊天】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】通过【群聊的xpath】与【最常使用的xpath】点击坐标【昵称对应的微信好友】均失败，当前昵称【" + nickName + "】对应的可能是【微信群】或者【公众号】或者【聊天记录】....");
                return false;
            }
        }
//        try {
//            String str_0_of_9 = nickName;
//            List<WebElement> targetGroupElementList = Lists.newArrayList();
//            if (str_0_of_9.length() > 9) {                      //截取emoji字符串之后，长度还是超过9个字符，则截取前9个字符.
//                //启用模糊匹配
//                str_0_of_9 = str_0_of_9.substring(0, 9);
//                targetGroupElementList = driver.findElementsByAndroidUIAutomator("new UiSelector().textContains(\"" + str_0_of_9 + "\")");
//            } else {
//                targetGroupElementList = driver.findElementsByAndroidUIAutomator("new UiSelector().text(\"" + nickName + "\")");
//            }
//            for (WebElement targetGroupElement : targetGroupElementList) {
//                if ("android.widget.TextView".equals(targetGroupElement.getAttribute("class"))) {
//                    targetGroupElement.click();
//                    break;
//                }
//            }
//            logger.info("【根据微信昵称进行聊天】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【昵称对应的微信好友】成功....");
//            Thread.sleep(1000);
//        } catch (Exception e) {
//            throw new Exception("【根据微信昵称进行聊天】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【昵称对应的微信好友】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
//        }
        //5.点击坐标【聊天内容输入框】new UiSelector().className("android.widget.EditText")
        try {
            driver.findElementByAndroidUIAutomator("new UiSelector().className(\"" + chatInputLocation + "\")").sendKeys(textMessage);
            logger.info("【根据微信昵称进行聊天】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【聊天输入框】成功....");
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("【根据微信昵称进行聊天】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【聊天输入框】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
        }
        //6.点击坐标【发送】new UiSelector().text("发送")
        try {
            driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + sendBtnLocaltion + "\")").click();
            logger.info("【根据微信昵称进行聊天】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【发送】成功....");
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("【根据微信昵称进行聊天】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标[发送]出现异常,请检查设备描述[" + deviceNameDesc + "]设备编码[" + deviceName + "]的应用是否更新导致坐标变化等原因....");
        }
        logger.info("【根据微信昵称进行聊天】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】 发送成功....");
        return true;
    }

    public static void main(String[] args) {
        try {
            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("action", "chatByNickName");
            paramMap.put("nickName", "全国微帮总汇1⃣\uD83C\uDE35");
            new RealMachineDevices().chatByNickName(paramMap);
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.automation.utils.wei_xin.relayTheWxMessage;

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
 * 真机设备 转发微信消息 策略
 * 注：只支持最后一条消息是【图片】【小程序】【微信公众号文章】
 * 默认 小米Max3
 */
public class RealMachineDevices implements RelayTheWxMessage {

    public static final Logger logger = LoggerFactory.getLogger(RealMachineDevices.class);

    /**
     * 前置条件：将微信消息群发到【油站科技-内部交流群】里面
     * 转发微信消息
     *
     * @param paramMap
     * @throws Exception
     */
    @Override
    public boolean relayTheWxMessage(Map<String, Object> paramMap) throws Exception {
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
                        "relayTheWxMessage";
        //转发消息数量
        String relayTheWxMessageNumStr =
                paramMap.get("relayTheWxMessageNumStr") != null ?
                        paramMap.get("relayTheWxMessageNumStr").toString() :
                        "1";
        Integer relayTheWxMessageNum = Integer.parseInt(relayTheWxMessageNumStr);
        //转发的微信群昵称List
        String relayTargetGroupListStr =
                paramMap.get("relayTargetGroupList") != null ?
                        paramMap.get("relayTargetGroupList").toString() :
//                        "[\"内部交流群\"]";
                        "[\"内部交流群\",\"铜仁市～思南县～车友群\",\"铜仁市～松桃县～本地油价\",\"铜仁市～碧江区～车友群\",\"铜仁市～万山区～车友群\",\"铜仁市～德江县～车友群\",\"铜仁市～印江县～车友群\",\"铜仁市～沿河县～车友群\",\"铜仁市～江口县～车友群\",\"铜仁市～松桃县～车友群\",\"铜仁市～玉屏县～车友群\"]";
        List<String> relayTargetGroupList = JSON.parseObject(relayTargetGroupListStr, List.class);
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
        //点击坐标【多选】
        String multipleChoiceLocaltion =
                paramMap.get("multipleChoiceLocaltion") != null ?
                        paramMap.get("multipleChoiceLocaltion").toString() :
                        "多选";
        //点击坐标【获取所有单选框CheckBox】
        String singleCheckBoxLocaltion =
                paramMap.get("singleCheckBoxLocaltion") != null ?
                        paramMap.get("singleCheckBoxLocaltion").toString() :
                        "android.widget.CheckBox";
        //点击坐标【分享】
        String shareLocaltion =
                paramMap.get("shareLocaltion") != null ?
                        paramMap.get("shareLocaltion").toString() :
                        "分享";
        //点击坐标【逐条转发】
        String forwardOneByOneLocaltion =
                paramMap.get("forwardOneByOneLocaltion") != null ?
                        paramMap.get("forwardOneByOneLocaltion").toString() :
                        "逐条转发";
        //点击坐标【搜索[android.widget.EditText]并输入群名】
        String editTextLocaltion =
                paramMap.get("editTextLocaltion") != null ?
                        paramMap.get("editTextLocaltion").toString() :
                        "android.widget.EditText";
        //点击坐标【发送】
        String sendLocaltion =
                paramMap.get("sendLocaltion") != null ?
                        paramMap.get("sendLocaltion").toString() :
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
            logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】连接Appium【" + appiumPort + "】成功....");
            Thread.sleep(10000);                                                                     //加载安卓页面10秒,保证xml树完全加载
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】配置连接android驱动出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】Appium端口号【" + appiumPort + "】的环境是否正常运行等原因....");
        }

        System.out.println("当前activity = " + driver.currentActivity());
        Activity chatActivity = new Activity("com.tencent.mm", ".ui.LauncherUI");
        for (int i = 1; i <= 30; i++) {     //每间隔5秒点击一次，持续90秒
            //2.点击坐标【搜索】，当前坐标会引起微信对当前所有联系人和聊天对象进行建立索引，会有点慢，需要进行特别支持，暂时循环点击10次
            try {
                driver.findElementByAndroidUIAutomator("new UiSelector().description(\"" + searchLocaltionStr + "\")").click();
                logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【搜索框】成功....");
                Thread.sleep(5000);         //此处会创建索引，会比较费时间才能打开
            } catch (Exception e) {
                logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【搜索框】失败，因为微信正在建立索引....");
                if (i == 30) {
                    throw new Exception("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【搜索框】均失败,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
                } else if(i == 15){        //当点击15次均无法成功点击坐标【搜索】，则通过重启当前应用【微信】处理
                    driver.startActivity(chatActivity);      //返回【当前页面聊天好友信息】
                    logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】次点击坐标【搜索框】失败，通过重启当前应用【微信】处理....");
                    continue;
                } else {
                    Thread.sleep(5000);         //此处会创建索引，会比较费时间才能打开
                    logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】次点击坐标【搜索框】失败，因为微信正在建立索引....");
                    continue;
                }
            }
            //3.点击坐标【搜索输入框】并输入昵称
            try {
                driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + searchInputLocaltion + "\")").sendKeys(targetGroup);
                logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【输入昵称到搜索框:text/搜索】成功....");
                Thread.sleep(1000);
                break;
            } catch (Exception e) {
                logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【输入昵称到搜索框:text/搜索】失败....");
                try {
                    driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.EditText\")").sendKeys(targetGroup);
                    logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【输入昵称到搜索框:className/android.widget.EditText】成功....");
                    Thread.sleep(1000);
                    break;
                } catch (Exception e1) {
                    logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【输入昵称到搜索框:className/android.widget.EditText】失败....");
                    if (i == 30) {
                        throw new Exception("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【输入昵称到搜索框:text/搜索】与【输入昵称到搜索框:className/android.widget.EditText】均失败,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
                    } else if(i == 15){        //当点击15次均无法成功点击坐标【搜索】，则通过重启当前应用【微信】处理
                        driver.startActivity(chatActivity);      //返回【当前页面聊天好友信息】
                        logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】次点击坐标【搜索框】失败，通过重启当前应用【微信】处理....");
                        continue;
                    } else {
                        Thread.sleep(5000);         //此处会创建索引，会比较费时间才能打开
                        logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】次点击坐标【输入昵称到搜索框:text/搜索】与【输入昵称到搜索框:className/android.widget.EditText】均失败，因为微信正在建立索引....");
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
                throw new Exception("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】判断坐标【联系人】与【最常使用】均不存在，当前昵称【" + targetGroup + "】对应的可能是【微信群】或者【公众号】或者【聊天记录】....");

            }
        }
        //5.点击坐标【昵称对应的微信好友群】
        try {
            driver.findElementByXPath("//android.widget.TextView[@text=\"" + groupLocaltion + "\"]/../../../android.widget.RelativeLayout[2]").click();
            logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【昵称对应的微信好友群】通过【联系人的xpath】成功....");
            Thread.sleep(1000);
        } catch (Exception e) {
            try {
                driver.findElementByXPath("//android.widget.TextView[@text=\"" + mostUsedLocaltion + "\"]/../../../android.widget.RelativeLayout[2]").click();
                logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【昵称对应的微信好友群】通过【最常使用的xpath】成功....");
                Thread.sleep(1000);
            } catch (Exception e1) {
                throw new Exception("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】通过【联系人的xpath】与【最常使用的xpath】点击坐标【昵称对应的微信好友】均失败，当前昵称【\" + nickName + \"】对应的可能是【微信群】或者【公众号】或者【聊天记录】....");
            }
        }
        //根据 根据转发的群昵称List 进行遍历
        Iterator<String> iterator = relayTargetGroupList.iterator();
        while (iterator.hasNext()) {
            Integer relayNumOfOne = 0;  //一次多选，转发，最多选择9个目标群进行操作
            String relayTargetGroup = iterator.next();
            //获取所有的微信消息RelativeLayout //android.widget.ListView/android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.LinearLayout
            List<WebElement> chatContentRelativeLayoutList = Lists.newArrayList();
            try {
                chatContentRelativeLayoutList = driver.findElementsByXPath(chatContentRelativeLayoutLocaltion);
                logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】获取所有的微信消息RelativeLayout 成功....");
                Thread.sleep(1000);
            } catch (Exception e) {
                throw new Exception("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】获取所有的微信消息RelativeLayout 异常....");
            }
            //长按最后一条聊天消息等待弹出【多选等弹窗】
            try {
                Duration duration = Duration.ofMillis(2000);
                new TouchAction(driver).press(chatContentRelativeLayoutList.get(chatContentRelativeLayoutList.size() - 1)).waitAction(WaitOptions.waitOptions(duration)).release().perform();
                logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】长按最后一条消息等待弹出【多选等弹窗】成功....");
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】长按最后一条消息等待弹出【多选等弹窗】异常，" + e.getMessage());
            }
            //长按最后一条聊天消息等待弹出【多选等弹窗】new UiSelector().text("多选")
            try {
                driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + multipleChoiceLocaltion + "\")").click();
                logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】长按最后一条消息等待弹出【多选等弹窗】成功....");
                Thread.sleep(1000);
            } catch (Exception e) {
                try {
                    driver.findElementByAndroidUIAutomator("new UiSelector().description(\"" + multipleChoiceLocaltion + "\")").click();
                    logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】长按最后一条消息等待弹出【多选等弹窗】成功....");
                    Thread.sleep(1000);
                } catch (Exception e1) {
                    throw new Exception("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】长按最后一条消息等待弹出【多选等弹窗】异常....");
                }
            }
            //选择单选框 new UiSelector().className("android.widget.CheckBox")
            int getAllCheckBoxWebNum = 0;
            int relayTheWxMessageNum_selected = 1;      //当点击坐标【多选】的时候，就已经选中一条微信消息了
            while (true) {
                try {
                    List<WebElement> checkBoxWebElementList = driver.findElementsByAndroidUIAutomator("new UiSelector().className(\"" + singleCheckBoxLocaltion + "\")");
                    logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【获取所有单选框CheckBox】成功....");
                    for (int i = (checkBoxWebElementList.size() - 1); i >= 0; i--) {
                        try {
                            WebElement checkBoxWebElement = checkBoxWebElementList.get(i);
                            String checkedFlag = checkBoxWebElement.getAttribute("checked");
                            if ("false".equals(checkedFlag)) {
                                checkBoxWebElement.click();
                                relayTheWxMessageNum_selected++;
                                Thread.sleep(1000);
                            }
                        } catch (Exception e) {
                            logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【单条微信消息CheckBox】异常....");
                        } finally {
                            if (relayTheWxMessageNum_selected >= relayTheWxMessageNum) {
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + getAllCheckBoxWebNum + "】次获取所有【微信消息CheckBox】异常....");
                    if (getAllCheckBoxWebNum >= 10) {
                        logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + getAllCheckBoxWebNum + "】次获取所有【微信消息CheckBox】异常，" + e.getMessage());
                    }
                } finally {
                    if (relayTheWxMessageNum_selected >= relayTheWxMessageNum) {
                        break;
                    } else {
                        //向下滑动
                        try {
                            driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollBackward()");
                            logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】【下滑】显示更多需要转发的微信消息....");
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】【下滑】显示更多需要转发的微信消息....");
                        }
                    }
                }
            }
            //点击坐标【分享】
            try {
                driver.findElementByAndroidUIAutomator("new UiSelector().description(\"" + shareLocaltion + "\")").click();
                logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【分享】成功....");
                Thread.sleep(1000);
            } catch (Exception e) {
                throw new Exception("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【分享】异常....");
            }
            //点击坐标【逐条转发】
            try {
                driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + forwardOneByOneLocaltion + "\")").click();
                logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【逐条转发】成功....");
                Thread.sleep(1000);
            } catch (Exception e) {
                throw new Exception("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【逐条转发】异常....");
            }
            //点击坐标【多选】
            try {
                driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + multipleChoiceLocaltion + "\")").click();
                logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【多选】成功....");
                Thread.sleep(1000);
            } catch (Exception e) {
                throw new Exception("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【多选】异常....");
            }
            while (true) {
                //点击坐标【搜索】
                try {
                    driver.findElementByAndroidUIAutomator("new UiSelector().className(\"" + editTextLocaltion + "\")").clear();
                    driver.findElementByAndroidUIAutomator("new UiSelector().className(\"" + editTextLocaltion + "\")").sendKeys(relayTargetGroup);
                    logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【搜索[android.widget.EditText]并输入群名:" + relayTargetGroup + "】成功....");
                    Thread.sleep(1000);
                } catch (Exception e) {
                    throw new Exception("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【搜索[android.widget.EditText]并输入群名:" + relayTargetGroup + "】异常....");
                }
                //点击坐标【群名】           //android.widget.TextView[@text="内部交流群"]
                try {
                    driver.findElementByXPath("//android.widget.TextView[@text=\"" + relayTargetGroup + "\"]").click();
                    logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【群名：" + relayTargetGroup + "】【xpath】成功....");
                    Thread.sleep(1000);
                } catch (Exception e) {
                    logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【群名：" + relayTargetGroup + "】【xpath】异常....");
                }
                relayNumOfOne++;
                if (relayNumOfOne >= 9 || relayNumOfOne >= relayTargetGroupList.size() || !iterator.hasNext()) {
                    //点击坐标【发送(】
                    try {
                        driver.findElementByAndroidUIAutomator("new UiSelector().textContains(\"" + sendLocaltion + "\")").click();
                        logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【发送(】成功....");
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        throw new Exception("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【发送(】异常....");
                    }
                    //点击坐标【发送】
                    try {
                        driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + sendLocaltion + "\")").click();
                        logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【发送】成功....");
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        throw new Exception("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【发送】异常....");
                    } finally {
                        break;
                    }
                } else {
                    relayTargetGroup = iterator.next();
                }
            }
            iterator.remove();
        }
        logger.info("【转发微信消息】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】 发送成功....");
        return true;
    }

    public static void main(String[] args) {
        try {
            Map<String, Object> paramMap = Maps.newHashMap();
//            paramMap.put("deviceName", "S2D0219423001056");
//            paramMap.put("deviceNameDesc", "华为 Mate 20 Pro");
            paramMap.put("deviceName", "9f4eda95");
            paramMap.put("deviceNameDesc", "小米 Max 3");
            paramMap.put("relayTheWxMessageNumStr", "1");
            paramMap.put("action", "relayTheWxMessage");
            new RealMachineDevices().relayTheWxMessage(paramMap);
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

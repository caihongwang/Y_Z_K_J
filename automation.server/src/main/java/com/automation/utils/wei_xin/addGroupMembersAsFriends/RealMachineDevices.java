package com.automation.utils.wei_xin.addGroupMembersAsFriends;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.automation.utils.EmojiUtil;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 真机设备 添加群成员为好友的V群 策略
 * 默认 华为 P20 Pro
 */
public class RealMachineDevices implements AddGroupMembersAsFriends {

    public static final Logger logger = LoggerFactory.getLogger(RealMachineDevices.class);

    /**
     * 添加群成员为好友的V群
     *
     * @param paramMap
     * @throws Exception
     */
    @Override
    public boolean addGroupMembersAsFriends(Map<String, Object> paramMap) throws Exception {
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
                        "addGroupMembersAsFriends";
        //添加好友数量,默认添加 10 个好友
        String addFrirndTotalNumStr =
                paramMap.get("addFrirndTotalNumStr") != null ?
                        paramMap.get("addFrirndTotalNumStr").toString() :
                        "10";
        Integer addFrirndTotalNum = Integer.parseInt(addFrirndTotalNumStr);
        //设置默认添加好友的初始位置,默认从第 10 个开始添加好友
        String startAddFrirndTotalNumStr =
                paramMap.get("startAddFrirndTotalNumStr") != null ?
                        paramMap.get("startAddFrirndTotalNumStr").toString() :
                        "20";
        Integer startAddFrirndTotalNum = Integer.parseInt(startAddFrirndTotalNumStr);
        //群成员Map
        String groupMembersMapStr =
                paramMap.get("groupMembersMapStr") != null ?
                        paramMap.get("groupMembersMapStr").toString() :
                        "{}";
        groupMembersMapStr = EmojiUtil.emojiRecovery(groupMembersMapStr);
        LinkedHashMap<String, Map<String, String>> groupMembersMap = Maps.newLinkedHashMap();
        try {
            groupMembersMap = JSON.parseObject(groupMembersMapStr, LinkedHashMap.class);
        } catch (Exception e) {
            //不用处理，后面会重新整理一份新的groupMembersMapStr
        }
        //昵称
        String nickName =
                paramMap.get("nickName") != null ?
                        paramMap.get("nickName").toString() :
                        "铜仁推广商务群";
        nickName = EmojiUtil.emojiRecovery(nickName);
        //申请好友信息
        String requestMsg =
                paramMap.get("requestMsg") != null ?
                        paramMap.get("requestMsg").toString() :
                        "我是群主，拉你进大群";
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
        //坐标:群聊
        String groupLocaltion =
                paramMap.get("groupLocaltion") != null ?
                        paramMap.get("groupLocaltion").toString() :
                        "群聊";
        //坐标:最常使用
        String mostUsedLocaltion =
                paramMap.get("mostUsedLocaltion") != null ?
                        paramMap.get("mostUsedLocaltion").toString() :
                        "最常使用";
        //坐标【右上角的三个点：聊天信息】
        String threePointLocaltion =
                paramMap.get("threePointLocaltion") != null ?
                        paramMap.get("threePointLocaltion").toString() :
                        "聊天信息";
        //坐标【群成员总数：聊天信息(】
        String groupTotalNumLocation =
                paramMap.get("groupTotalNumLocation") != null ?
                        paramMap.get("groupTotalNumLocation").toString() :
                        "聊天信息(";
        //坐标【聊天成员(】
        String chatMemberLocation =
                paramMap.get("chatMemberLocation") != null ?
                        paramMap.get("chatMemberLocation").toString() :
                        "聊天成员(";
        //坐标【查看全部群成员】
        String checkAllGroupMembers =
                paramMap.get("checkAllGroupMembers") != null ?
                        paramMap.get("checkAllGroupMembers").toString() :
                        "查看全部群成员";
        //坐标【查看更多群成员】
        String checkMoreGroupMembers =
                paramMap.get("checkMoreGroupMembers") != null ?
                        paramMap.get("checkMoreGroupMembers").toString() :
                        "查看更多群成员";
        //坐标【发消息】
        String sendMessageBtnLocaltion =
                paramMap.get("sendMessageBtnLocaltion") != null ?
                        paramMap.get("sendMessageBtnLocaltion").toString() :
                        "发消息";
        //坐标【添加到通讯录】
        String aadToContactBookLocaltion =
                paramMap.get("aadToContactBookLocaltion") != null ?
                        paramMap.get("aadToContactBookLocaltion").toString() :
                        "添加到通讯录";
        //坐标【由于对方的隐私设置，你无法通过群聊将其添加至通讯录。】，注：如果这个坐标找不到则使用【确定】这个坐标
        String privacyContentLocaltion =
                paramMap.get("privacyContentLocaltion") != null ?
                        paramMap.get("privacyContentLocaltion").toString() :
                        "由于对方的隐私设置，你无法通过群聊将其添加至通讯录。";
        //坐标【发送添加朋友申请】
        String sendRequestMsgLocaltion =
                paramMap.get("sendRequestMsgLocaltion") != null ?
                        paramMap.get("sendRequestMsgLocaltion").toString() :
//                        "发送添加朋友申请";
                        "申请";
        //坐标【朋友圈】
        String friendCircleLocaltion =
                paramMap.get("friendCircleLocaltion") != null ?
                        paramMap.get("friendCircleLocaltion").toString() :
                        "朋友圈";
        //坐标【发送】
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
            logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】连接Appium【" + appiumPort + "】成功....");
            Thread.sleep(10000);                                                                     //加载安卓页面10秒,保证xml树完全加载
        } catch (Exception e) {
            throw new Exception("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】配置连接android驱动出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】Appium端口号【" + appiumPort + "】的环境是否正常运行等原因....");
        }

        System.out.println("当前activity = " + driver.currentActivity());
        Activity chatActivity = new Activity("com.tencent.mm", ".ui.LauncherUI");
        for (int i = 1; i <= 30; i++) {     //每间隔5秒点击一次，持续90秒
            //2.点击坐标【搜索】，当前坐标会引起微信对当前所有联系人和聊天对象进行建立索引，会有点慢，需要进行特别支持，暂时循环点击10次
            try {
                driver.findElementByAndroidUIAutomator("new UiSelector().description(\"" + searchLocaltionStr + "\")").click();
                logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【搜索框】成功....");
                Thread.sleep(5000);         //此处会创建索引，会比较费时间才能打开
            } catch (Exception e) {
                logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【搜索框】失败，因为微信正在建立索引....");
                if (i == 30) {
                    throw new Exception("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【搜索框】均失败,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
                } else if(i == 15){        //当点击15次均无法成功点击坐标【搜索】，则通过重启当前应用【微信】处理
                    driver.startActivity(chatActivity);      //返回【当前页面聊天好友信息】
                    logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】次点击坐标【搜索框】失败，通过重启当前应用【微信】处理....");
                    continue;
                } else {
                    Thread.sleep(5000);         //此处会创建索引，会比较费时间才能打开
                    logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】次点击坐标【搜索框】失败，因为微信正在建立索引....");
                    continue;
                }
            }
            //3.点击坐标【搜索输入框】并输入昵称
            try {
                driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + searchInputLocaltion + "\")").sendKeys(nickName);
                logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【输入昵称到搜索框:text/搜索】成功....");
                Thread.sleep(1000);
                break;
            } catch (Exception e) {
                logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【输入昵称到搜索框:text/搜索】失败....");
                try {
                    driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.EditText\")").sendKeys(nickName);
                    logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【输入昵称到搜索框:className/android.widget.EditText】成功....");
                    Thread.sleep(1000);
                    break;
                } catch (Exception e1) {
                    logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【输入昵称到搜索框:className/android.widget.EditText】失败....");
                    if (i == 30) {
                        throw new Exception("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】点击坐标【输入昵称到搜索框:text/搜索】与【输入昵称到搜索框:className/android.widget.EditText】均失败,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
                    } else if(i == 15){        //当点击15次均无法成功点击坐标【搜索】，则通过重启当前应用【微信】处理
                        driver.startActivity(chatActivity);      //返回【当前页面聊天好友信息】
                        logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】次点击坐标【搜索框】失败，通过重启当前应用【微信】处理....");
                        continue;
                    } else {
                        Thread.sleep(5000);         //此处会创建索引，会比较费时间才能打开
                        logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】次点击坐标【输入昵称到搜索框:text/搜索】与【输入昵称到搜索框:className/android.widget.EditText】均失败，因为微信正在建立索引....");
                        continue;
                    }
                }
            }
        }
        //4.判断坐标【群聊】与【最常使用】是否存在
        try {
            driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + groupLocaltion + "\")");
            logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测坐标【群聊】成功....");
            Thread.sleep(1000);
        } catch (Exception e) {
            logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测坐标【群聊】失败....");
            try {
                driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + mostUsedLocaltion + "\")");
                logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测坐标【最常使用】成功....");
                Thread.sleep(1000);
            } catch (Exception e1) {
                logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测坐标【群聊】与【最常使用】均失败....");
                throw new Exception("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】判断坐标【群聊】与【最常使用】均不存在，当前昵称【" + nickName + "】对应的可能是【微信群】或者【公众号】或者【聊天记录】....", e);
            } finally {
                logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】群名【" + nickName + "】在当前设备的微信中不存在....");
            }
        }
        //5.点击坐标【昵称对应的微信好友/群】 //android.widget.TextView[@text="群聊"]/../../../android.widget.RelativeLayout[2]
        try {
            driver.findElementByXPath("//android.widget.TextView[@text=\"" + groupLocaltion + "\"]/../../../android.widget.RelativeLayout[2]").click();
            logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【昵称对应的微信好友群】通过【群聊的xpath】成功....");
            Thread.sleep(1000);
        } catch (Exception e) {
            logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【昵称对应的微信好友群】通过【群聊的xpath】失败....");
            try {
                driver.findElementByXPath("//android.widget.TextView[@text=\"" + mostUsedLocaltion + "\"]/../../../android.widget.RelativeLayout[2]").click();
                logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【昵称对应的微信好友群】通过【最常使用的xpath】成功....");
                Thread.sleep(1000);
            } catch (Exception e1) {
                logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【昵称对应的微信好友群】通过【群聊的xpath】与【最常使用的xpath】均失败....");
                throw new Exception("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】通过【群聊的xpath】与【最常使用的xpath】点击坐标【昵称对应的微信好友群】均失败，当前昵称【\" + nickName + \"】对应的可能是【微信群】或者【公众号】或者【聊天记录】....");
            }
        }
//        try {
//            String str_0_of_9 = nickName;
//            int firstEmojiIndex = EmojiUtil.getFirstEmojiIndex(nickName);
//            if (firstEmojiIndex >= 0) {
//                str_0_of_9 = nickName.substring(0, firstEmojiIndex);        //截取emoji之前的字符串
//            }
//            if (str_0_of_9.length() > 9) {                      //截取emoji字符串之后，长度还是超过9个字符，则截取前9个字符.
//                str_0_of_9 = str_0_of_9.substring(0, 9);
//            }
//            //启用模糊匹配
//            List<WebElement> targetGroupElementList = driver.findElementsByAndroidUIAutomator("new UiSelector().textContains(\"" + str_0_of_9 + "\")");
//            for (WebElement targetGroupElement : targetGroupElementList) {
//                if ("android.widget.TextView".equals(targetGroupElement.getAttribute("class"))) {
//                    targetGroupElement.click();
//                    break;
//                }
//            }
//            logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【昵称对应的微信好友群】成功....");
//            Thread.sleep(1000);
//        } catch (Exception e) {
//            throw new Exception("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【昵称对应的微信好友群】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
//        }
        //6.点击坐标【右上角的三个点：聊天信息】new UiSelector().text("聊天信息")
        try {
            driver.findElementByAndroidUIAutomator("new UiSelector().description(\"" + threePointLocaltion + "\")").click();
            logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【右上角的三个点】成功....");
            Thread.sleep(1000);
        } catch (Exception e) {
            logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【右上角的三个点】失败....");
            throw new Exception("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【右上角的三个点】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
        }
        //7.查找坐标【聊天信息(】查看群成员总数
        Integer groupTotalNum = 100;
        try {
            WebElement groupTotalNumWebElement = driver.findElementByAndroidUIAutomator("new UiSelector().textStartsWith(\"" + groupTotalNumLocation + "\")");
            String text = groupTotalNumWebElement.getAttribute("text");
            String groupTotalNumStr = ((text.split("\\("))[1].split("\\)"))[0];
            groupTotalNum = Integer.parseInt(groupTotalNumStr);
            logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】查找坐标【聊天信息(】查看群成员总数 成功....");
            Thread.sleep(1000);
        } catch (Exception e) {
            logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】查找坐标【聊天信息(】查看群成员总数 失败....");
            throw new Exception("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】查找坐标【聊天信息(】查看群成员总数出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
        }
        Integer theAddNum = 1;         //添加过的好友数量
        if (groupTotalNum >= 40) {     //当群成员超过40人事，才会出现【查看全部群成员】
            //8.点击坐标【上滑同时检测坐标查看全部群成员】并点击
            try {
                WebElement checkAllGroupMembers_WebElement = driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollForward().scrollIntoView(new UiSelector().text(\"" + checkAllGroupMembers + "\"))");
                checkAllGroupMembers_WebElement.click();
                logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】上滑同时检测坐标【查看全部群成员】并点击成功...");
                Thread.sleep(1000);
            } catch (Exception e) {
                logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】上滑同时检测坐标【查看全部群成员】并点击失败...");
                try {//低版本微信中是【查看全部群成员】，高版本微信中是【查看更多群成员】
                    WebElement checkMoreGroupMembers_WebElement = driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollForward().scrollIntoView(new UiSelector().text(\"" + checkMoreGroupMembers + "\"))");
                    checkMoreGroupMembers_WebElement.click();
                    logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】上滑同时检测坐标【查看更多群成员】并点击成功...");
                } catch (Exception e1) {
                    logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】上滑同时检测坐标【查看全部群成员】与【查看更多群成员】均失败...");
                    throw new Exception("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】上滑同时检测坐标【查看全部群成员】与【查看更多群成员】均失败...");
                }
            }
            //9.下滑，回到群成员的顶部
            try {
                driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).flingToBeginning(5)");
                logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】【下滑】回到群成员的顶部成功....");
            } catch (Exception e) {
                logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】【下滑】回到群成员的顶部异常....");
            }
            //10.遍历群成员，并将其保存 groupMembersMap，如果groupMembersMap中有数据，则在循环遍历一次，就当查缺补漏
            if (groupMembersMap.size() != groupTotalNum) {
                int cyclesNumber = 1;       //循环下拉的次数, 默认超过30次，30次已足够查看500人的群成员了
                int groupMemberIndex = groupMembersMap.size();
                while (true) {      //循环遍历群成员好友，并将其加入groupMembersList
                    WebElement gridWebElement = driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.GridView\")");
                    List<WebElement> linearWebElementList = gridWebElement.findElements(By.className("android.widget.LinearLayout"));       //获取所有的群成员列表信息
                    if (linearWebElementList != null && linearWebElementList.size() > 0) {
                        for (WebElement webElement : linearWebElementList) {
                            String groupMemberNickName = "未知";                  //群成员-昵称
                            String groupMemberClass = "未知";                     //群成员-昵称
                            try {
                                groupMemberNickName = webElement.findElement(By.className("android.widget.TextView")).getAttribute("text");
                                groupMemberClass = webElement.getAttribute("class");
                                if (!groupMembersMap.containsKey(groupMemberNickName)) {        //群成员昵称不在groupMembersMap则加入
                                    groupMemberIndex++;
                                    Map<String, String> tempMap = Maps.newHashMap();
                                    tempMap.put("groupMemberIndex", groupMemberIndex + "");
                                    tempMap.put("groupMemberNickName", groupMemberNickName);
                                    tempMap.put("groupMemberClass", groupMemberClass);
                                    if (groupMemberIndex >= startAddFrirndTotalNum) {
                                        tempMap.put("isAddFlag", "false");                      //是否添加过，默认未添加过
                                    } else {
                                        tempMap.put("isAddFlag", "true");                       //前20个好友不添加，可能是群主，免得被踢
                                    }
                                    groupMembersMap.put(groupMemberNickName, tempMap);
                                }
                            } catch (Exception e) {
                                logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前不是群成员头像坐标....");
                            }
                        }
                        if (cyclesNumber >= 30) {           //当循环下拉的次数超过30次时，则强制终止循环，主要是群成员中存在昵称相同的人，导致groupMembersMap无法添加进去，导致数量达不到真实的群成员数量
                            //停止循环
                            logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】已滑到群成员的底部...");
                            break;
                        } else {
                            if (groupMembersMap.size() >= groupTotalNum) {        //当群成员数量全部统计完成时就跳出循环
                                //停止循环
                                logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】已滑到群成员的底部...");
                                break;
                            } else {                        //下滑，继续循环
                                try {
                                    logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + cyclesNumber + "】次，scroll上滑中,查看更过群成员，直到groupMembersMap的大小达到" + groupTotalNum + "，当前groupMembersMap的大小为：" + groupMembersMap.size() + "...");
                                    driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollForward()");
                                } catch (Exception e) {
                                    logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】scroll上滑中,查看更过群成员....");
                                }
                                cyclesNumber++;
                            }
                        }
                    }
                }
                for (String key : groupMembersMap.keySet()) {
                    logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】\t" + groupMembersMap.get(key).get("groupMemberIndex") + " ---->>> " + key + " ---->>> " + JSON.toJSONString(groupMembersMap.get(key)));
                }
                logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【上滑同时检测坐标查看全部群成员】成功....");
                Thread.sleep(1000);
            }
            //11.循环点击群成员
            Integer addFriendNum = 1;       //发送添加为好友请求的数量
            for (String key : groupMembersMap.keySet()) {
                Map<String, String> groupMember = groupMembersMap.get(key);
                String isAddFlag = groupMember.get("isAddFlag");
                String groupMemberNickName = groupMember.get("groupMemberNickName");
//                if (!groupMemberNickName.contains("文山卧龙百姓平台")) {
//                    continue;
//                }
                if ("false".equals(isAddFlag)) {
                    try {
                        theAddNum++;        //点击群成员添加还有的数量，包括已被封号的，设置隐私设置的
                        //11.1 点击坐标【搜索框】输入群成员昵称
                        try {
                            driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + searchInputLocaltion + "\")").sendKeys(groupMemberNickName);
                            logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【搜索:text】寻找群成员【" + groupMemberNickName + "】成功....");
                        } catch (Exception e) {
                            logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【搜索:text】寻找群成员【" + groupMemberNickName + "】失败....");
                            try {
                                driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.EditText\")").sendKeys(groupMemberNickName);
                                logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【搜索:className/android.widget.EditText】寻找群成员【" + groupMemberNickName + "】成功....");
                            } catch (Exception e1) {
                                logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【搜索:text】与【搜索:className/android.widget.EditText】寻找群成员【" + groupMemberNickName + "】均失败....");
                                throw new Exception("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【搜索:text】与【搜索:className/android.widget.EditText】寻找群成员【" + groupMemberNickName + "】均失败....");
                            }
                        } finally {
                            Thread.sleep(1500);
                        }
                        //11.2 检测坐标【android.widget.GridView】是否存在
                        List<WebElement> linearWebElementList = Lists.newArrayList();
                        try {
                            WebElement gridWebElement = driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.GridView\")");
                            if (gridWebElement != null) {
                                logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测坐标【android.widget.GridView】成功，获取昵称对应的群成员列表信息....");
                                Thread.sleep(1000);
                                try {
                                    linearWebElementList = gridWebElement.findElements(By.className("android.widget.TextView"));       //获取昵称对应的群成员列表信息
                                    if (linearWebElementList != null) {
                                        logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测坐标【android.widget.TextView】成功，获取昵称对应的群成员列表信息，共【" + linearWebElementList.size() + "】个....");
                                        Thread.sleep(1000);
                                    } else {
                                        logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测坐标【android.widget.TextView】成功，获取昵称对应的群成员列表信息，共【" + linearWebElementList.size() + "】个....");
                                    }
                                } catch (Exception e) {
                                    logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测坐标【android.widget.TextView】失败，获取昵称对应的群成员列表信息....");
                                }
                            }
                        } catch (Exception e) {
                            logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测坐标【android.widget.GridView】失败，获取昵称对应的群成员列表信息....");
                        }
                        if (linearWebElementList != null && linearWebElementList.size() > 0) {
                            for (int i = 0; i < linearWebElementList.size(); i++) {         //准备点击群成员头像，开始点击
                                WebElement webElement = linearWebElementList.get(i);
                                try {
                                    //11.3 点击【单个群成员】 通过循环遍历
                                    String groupMemberNickName_Temp = webElement.getAttribute("text");
                                    if (!groupMemberNickName_Temp.equals(groupMemberNickName)) {      //防止一个短昵称对应多个群成员，比如短昵称对应多个长昵称
                                        logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】未发现昵称对应的群成员【" + groupMemberNickName + "】，当前昵称【" + groupMemberNickName_Temp + "】....");
                                        if (i == (linearWebElementList.size() - 1)) {
                                            groupMember.put("isAddFlag", "true");           //根据群昵称循环遍历所有类似的群成员均找不到，当前群成员可能在这段期间更改过昵称或者群昵称导致找不到，则默认为已添加过，直接下一个
                                            logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】未发现群成员用户【" + groupMemberNickName + "】根据群昵称循环遍历所有类似的群成员均找不到，当前群成员可能在这段期间更改过昵称或者群昵称导致找不到，则默认为已添加过，直接下一个....");
                                        }
                                        continue;
                                    } else {
                                        logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】找到昵称对应的群成员【" + groupMemberNickName + "】准备添加为好友....");
                                    }
                                    webElement.click();         //当一个昵称对应太多群成员时，在遍历过程中可能会应为时间太长而无法定位致使点击异常
                                    logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击群成员【" + groupMemberNickName + "】成功....");
                                    Thread.sleep(4000);
                                    //11.4 检测坐标【发消息】
                                    try {
                                        WebElement sendMessageBtn_Element = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + sendMessageBtnLocaltion + "\")");
                                        if (sendMessageBtn_Element != null) {
                                            logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测坐标【发消息】成功，您与【" + groupMemberNickName + "】已是好友，则直接下一个群成员坐标....");
                                            groupMember.put("isAddFlag", "true");
                                            Thread.sleep(1000);
                                            break;
                                        }
                                    } catch (Exception e) {
                                        logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测坐标【发消息】异常，可能是当前群成员不是您的好友，从而没有找到【发消息】按钮....");
                                    }
                                    //11.5 点击坐标【添加到通讯录】
                                    try {
                                        driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + aadToContactBookLocaltion + "\")").click();
                                        logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【添加到通讯录】成功....");
                                        Thread.sleep(6000);
                                    } catch (Exception e) {
                                        logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【添加到通讯录】异常，可能是没有找到【添加到通讯录】按钮....");
                                    }
                                    //11.6 检测坐标【发消息】,点击坐标【添加到通讯录】直接被添加为好友，则检测坐标【发消息】
                                    try {
                                        WebElement sendMessageBtn_Element = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + sendMessageBtnLocaltion + "\")");
                                        if (sendMessageBtn_Element != null) {
                                            logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【添加到通讯录】后，检测坐标【发消息】成功，在点击坐标【添加到通讯录】直接被对方【" + groupMemberNickName + "】通过为好友（即通知发送成功），则直接下一个群成员坐标....");
                                            groupMember.put("isAddFlag", "true");
                                            addFriendNum++;
                                            Thread.sleep(1000);
                                            break;
                                        }
                                    } catch (Exception e) {
                                        logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【添加到通讯录】后，检测坐标【发消息】异常，当前用户没有在点击坐标【添加到通讯录】直接添加为好友....");
                                    }
                                    //11.7 显示弹窗【由于对方的隐私设置，你无法通过群聊将其添加至通讯录。】，注：如果这个坐标找不到则使用【确定】这个坐标 privacyContentLocaltion
                                    try {
                                        WebElement privacyContent_Element = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + privacyContentLocaltion + "\")");
                                        if (privacyContent_Element != null) {
                                            logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【添加到通讯录】后，显示内容【由于对方的隐私设置，你无法通过群聊将其添加至通讯录】成功，直接下一个群成员坐标....");
                                            try {       //退出【弹窗】
                                                driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + "确定" + "\")").click();
                                                logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【确定】退出【由于对方的隐私设置，你无法通过群聊将其添加至通讯录】的【弹窗】成功....");
                                            } catch (Exception e) {
                                                logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【确定】退出【由于对方的隐私设置，你无法通过群聊将其添加至通讯录】的【弹窗】失败....");
                                            }
                                            groupMember.put("isAddFlag", "true");
                                            Thread.sleep(4000);
                                            break;
                                        }
                                    } catch (Exception e) {
                                        try {           //隐私设置弹窗通过【由于对方的隐私设置，你无法通过群聊将其添加至通讯录】定位弹窗失败，在通过【弹窗】的【确定】按钮进行再次定位
                                            WebElement privacyContent_Element = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + "确定" + "\")");
                                            if (privacyContent_Element != null) {
                                                logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【添加到通讯录】后，显示内容【由于对方的隐私设置，你无法通过群聊将其添加至通讯录】的【确定】成功，直接下一个群成员坐标....");
                                                try {       //退出【弹窗】
                                                    driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + "确定" + "\")").click();
                                                    logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【确定】退出【由于对方的隐私设置，你无法通过群聊将其添加至通讯录】的【弹窗】成功....");
                                                } catch (Exception e1) {
                                                    logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【确定】退出【由于对方的隐私设置，你无法通过群聊将其添加至通讯录】的【弹窗】失败....");
                                                }
                                                groupMember.put("isAddFlag", "true");
                                                Thread.sleep(4000);
                                                break;
                                            }
                                        } catch (Exception e2) {
                                            logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【添加到通讯录】后，【未】显示内容【由于对方的隐私设置，你无法通过群聊将其添加至通讯录】，可能是没有找到【由于对方的隐私设置，你无法通过群聊将其添加至通讯录】....");
                                        }
                                    }
                                    //11.8 检测坐标【发送添加朋友申请】，避免出现：在【单个群成员简介】显示totast【对方账号异常，无法添加朋友。】，消失得很快，无法捕捉，致使本来应该在【发送页面】而实际停留在【单个群成员简介】
                                    try {
                                        driver.findElementByAndroidUIAutomator("new UiSelector().textContains(\"" + sendRequestMsgLocaltion + "\")");
                                        logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【添加到通讯录】后，检测坐标【发送添加朋友申请】成功....");
                                        Thread.sleep(1000);
                                    } catch (Exception e) {
                                        groupMember.put("isAddFlag", "true");
                                        logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【添加到通讯录】后，检测坐标【发送添加朋友申请】异常，以为【单个群成员简介】显示push【对方账号异常，无法添加朋友。】，消失得很快，无法捕捉，则直接下一个群成员坐标....");
                                        Thread.sleep(1000);
                                        break;
                                    }
                                    //11.9 点击坐标【发送添加朋友申请-输入框】并输入【添加对方为好友的打招呼内容】
                                    try {
                                        driver.findElementByXPath("//android.widget.TextView[contains(@text=\"" + sendRequestMsgLocaltion + "\")]/../android.widget.EditText").clear();
                                        Thread.sleep(1000);
                                        driver.findElementByXPath("//android.widget.TextView[contains(@text=\"" + sendRequestMsgLocaltion + "\")]/../android.widget.EditText").sendKeys(requestMsg);
                                        logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【发送添加朋友申请-输入框】【xpath】并填写内容成功....");
                                        Thread.sleep(1000);
                                    } catch (Exception e) {
                                        logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标并输入【发送添加朋友申请-输入框】【xpath】异常，直接进入下一步....");
                                    }
                                    //11.10 点击坐标【朋友圈】，主要是为了选择【权限】
                                    try {
                                        driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + friendCircleLocaltion + "\")").click();
                                        logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【朋友圈】成功....");
                                        Thread.sleep(1000);
                                    } catch (Exception e) {
                                        logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【朋友圈】异常，可能是当前微信版本过低，没有找到【朋友圈】按钮....");
                                    }
                                    //11.11 点击坐标【发送】
                                    try {
//                                        driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + sendBtnLocaltion + "\")").click();
                                        driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + sendBtnLocaltion + "\")");
                                        logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【发送】成功....");
                                        addFriendNum++;
                                        Thread.sleep(5000);
                                    } catch (Exception e) {
                                        logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【发送】异常，可能出现了【对方帐号异常，无法添加朋友。】或者【由于对方的隐私设置，你无法通过群聊将其添加至通讯录】....");
                                    } finally {
                                        groupMember.put("isAddFlag", "true");
                                    }
                                } catch (Exception e) {
                                    logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击群成员【" + groupMemberNickName + "】准备添加为好友异常，有可能一个昵称对应多个群成员，在循环遍历过程中WebElement超时了，无法定位点击....，e ：", e);
                                }
                                break;
                            }
                            //兜底：昵称在当前群中匹配太多群成员，可能命中的群成员在下拉框下面，导致坐标未被识别，即【当前昵称在此群中对应的群成员太多无法快速定位】
                            if (!"true".equals(groupMember.get("isAddFlag"))) {
                                groupMember.put("isAddFlag", "true");           //根据群昵称找不到群成员的，则默认为已添加过
                                logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】未发现群成员用户【" + groupMemberNickName + "】，当前昵称在此群中对应的群成员太多无法快速定位，直接下一个....");
                            }
                        } else {
                            groupMember.put("isAddFlag", "true");           //根据群昵称找不到群成员的，当前群成员可能在这段期间更改过昵称或者群昵称导致找不到，则默认为已添加过
                            logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】未发现群成员用户【" + groupMemberNickName + "】当前群成员可能在这段期间更改过昵称或者群昵称导致找不到，则默认为已添加过，直接下一个....");
                        }
                    } catch (Exception e) {
                        logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】在搜索框输入【" + groupMemberNickName + "】查找群成员异常....，e ：", e);
                    } finally {
                        // 在finall中循环返回并检测坐标【聊天成员(】来判断是否已回到输入框的聊天成员界面
                        // 但是 driver.pressKeyCode(AndroidKeyCode.BACK); 可能会存在执行不成功从而未真正返回，故在finally{...}进行循环兜底
                        for (int index = 0; index < 10; index++) {
                            try {
                                driver.findElementByAndroidUIAutomator("new UiSelector().textStartsWith(\"" + chatMemberLocation + "\")");
                                logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + index + "】次返回【聊天成员界面】成功....");
                                driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.EditText\")").clear();
                                logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【搜索:className/android.widget.EditText】清空【群成员搜索框】成功....");
                                break;
                            } catch (Exception e) {
                                logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + index + "】次返回【聊天成员界面】失败....");
                                driver.pressKeyCode(AndroidKeyCode.BACK);
                                Thread.sleep(1000);
                            }
                        }
                        if (addFriendNum > addFrirndTotalNum) {
                            break;
                        }
                    }
                }
            }
            logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】groupMembersMap = " + JSON.toJSON(groupMembersMap));
        } else {
            logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】群成员未超过40人，则不添加当前群的成员为好友....");
        }
        //更新群成员信息，便于统计那些群成员已经发起过添加好友申请.
        paramMap.put("groupMembersMapStr", JSON.toJSONString(groupMembersMap));
        logger.info("【添加群成员为好友的V群】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】昵称【" + nickName + "】添加好友【" + theAddNum + "】个，实质发送好友【" + addFrirndTotalNum + "】个申请 成功....");
        return true;
    }

    public static void main(String[] args) {
        try {
            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("deviceName", "S2D0219423001056");
            paramMap.put("deviceNameDesc", "华为 Mate 20 Pro");
//            paramMap.put("deviceName", "9f4eda95");
//            paramMap.put("deviceNameDesc", "小米 Max 3");
//            paramMap.put("nickName", "印江跑跑群");
            paramMap.put("nickName", "工商财务税务交流群");
            paramMap.put("startAddFrirndTotalNumStr", "100");
            paramMap.put("addFrirndTotalNumStr", "10");
            new RealMachineDevices().addGroupMembersAsFriends(paramMap);
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

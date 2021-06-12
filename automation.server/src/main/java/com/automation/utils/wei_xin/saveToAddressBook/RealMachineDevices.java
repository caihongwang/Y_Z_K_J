package com.automation.utils.wei_xin.saveToAddressBook;

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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 真机设备 将群保存到通讯录 策略，注：需要确保有一条未读消息
 * 默认 华为 P20 Pro
 */
public class RealMachineDevices implements SaveToAddressBook {

    public static final Logger logger = LoggerFactory.getLogger(RealMachineDevices.class);

    /**
     * 将群保存到通讯录
     *
     * @param paramMap
     * @throws Exception
     */
    @Override
    public boolean saveToAddressBook(Map<String, Object> paramMap) throws Exception {
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
        //坐标【保存到通讯录】
        String aadToAddressBookLocaltion =
                paramMap.get("aadToAddressBookLocaltion") != null ?
                        paramMap.get("aadToAddressBookLocaltion").toString() :
                        "保存到通讯录";
        //坐标【置顶聊天】
        String topChatLocaltion =
                paramMap.get("topChatLocaltion") != null ?
                        paramMap.get("topChatLocaltion").toString() :
                        "置顶聊天";
        //坐标【消息免打扰】
        String dontDisturbTheNewsLocaltion =
                paramMap.get("dontDisturbTheNewsLocaltion") != null ?
                        paramMap.get("dontDisturbTheNewsLocaltion").toString() :
                        "消息免打扰";
        //坐标【已关闭】
        String shutDownLocaltion =
                paramMap.get("shutDownLocaltion") != null ?
                        paramMap.get("shutDownLocaltion").toString() :
                        "已关闭";
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
            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】连接Appium【" + appiumPort + "】成功....");
            Thread.sleep(10000);                                                                     //加载安卓页面10秒,保证xml树完全加载
        } catch (Exception e) {
            throw new Exception("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】配置连接android驱动出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】Appium端口号【" + appiumPort + "】的环境是否正常运行等原因....");
        } finally {
            //针对全局，在定位元素时，如果5秒内找不到的话调用隐式等待时间内一直找找，找到的话往结束，注：会极大拖延运行速度
//            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        }

        System.out.println("当前activity = " + driver.currentActivity());
        Activity chatActivity = new Activity("com.tencent.mm", ".ui.LauncherUI");

        Integer theSaveToAddressBookNum = 0;
        Set<String> chatFriendsSet = Sets.newHashSet();

        //1.上滑同时检测坐标检测当前页面聊天好友信息
        int cyclesNumber = 0;       //循环下拉的次数
        int maxCyclesNumber = 60;       //默认超过30次
        while (true) {      //循环下拉当前好友聊天列表，并将其加入 chatFriendsSet
            try {
                WebElement listViewElement = driver.findElementByAndroidUIAutomator("new UiSelector().className(\"" + "android.widget.ListView" + "\")");
                List<WebElement> linearWebElementList = listViewElement.findElements(By.className("android.widget.LinearLayout"));       //获取所有的聊天好友信息
                if (linearWebElementList != null && linearWebElementList.size() > 0) {
                    for (WebElement webElement : linearWebElementList) {
                        String chatFriendNickName = "未知";                  //聊天好友-昵称
                        try {
                            chatFriendNickName = webElement.findElement(By.className("android.view.View")).getAttribute("text");
                            if (!StringUtils.isEmpty(chatFriendNickName)) {
                                chatFriendsSet.add(chatFriendNickName);
                            }
                        } catch (Exception e) {
                            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前不是聊天好友元素....");
                        }
                    }
                    if (cyclesNumber >= maxCyclesNumber) {           //当循环下拉的次数超过30次时，则强制终止循环，主要是群成员中存在昵称相同的人，导致groupMembersMap无法添加进去，导致数量达不到真实的群成员数量
                        //停止循环
                        logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】已滑到聊天界面的底部，第【" + cyclesNumber + "】次，scroll上滑中,检测当前页面聊天好友信息，当前 chatFriendsSet 的大小为：" + chatFriendsSet.size() + "...");
                        break;
                    } else {
                        try {
                            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + cyclesNumber + "】次，scroll上滑中,检测当前页面聊天好友信息，当前 chatFriendsSet 的大小为：" + chatFriendsSet.size() + "...");
                            driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollForward()");
                        } catch (Exception e) {
                            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】scroll上滑中,检测当前页面聊天好友信息....");
                        }
                        cyclesNumber++;
                    }
                }
            } catch (Exception e) {
                logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】上滑【当前页面聊天好友信息】到底部时，可能存在异常，忽视....");
            }
        }
        logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【上滑同时检测坐标检测当前页面聊天好友信息】成功....");
        Thread.sleep(1000);

        int chatFriendsIndex = 1;
        for (String chatFriendNickName : chatFriendsSet) {
            System.out.println("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + chatFriendsIndex + "】个好友 " + " ---->>> " + chatFriendNickName);
            chatFriendsIndex++;
        }
        System.out.println("=============================================================================================");
        System.out.println("=============================================================================================");
        System.out.println("=============================================================================================");

        //循环遍历好友昵称列表，点击坐标【搜索】与【搜索框】
        for (String chatFriendNickName : chatFriendsSet) {
//            if (!chatFriendNickName.contains("内部交流群")) {
//            if (!chatFriendNickName.contains("内部人脉推荐群")) {
//                continue;
//            }
            if (chatFriendNickName.contains("油站科技")) {
                logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + chatFriendNickName + "】包含【油站科技】对应的是【自己人】,继续下一个昵称....");
                continue;
            }
            if (chatFriendNickName.contains("[店员消息]")) {
                logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + chatFriendNickName + "】包含【[店员消息]】对应的是【微信群的聊天记录】,继续下一个昵称....");
                continue;
            }
            if (chatFriendNickName.contains("[链接]")) {
                logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + chatFriendNickName + "】包含【[链接]】对应的是【微信群的聊天记录】,继续下一个昵称....");
                continue;
            }
            if (chatFriendNickName.contains("[图片]")) {
                logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + chatFriendNickName + "】包含【[图片]】对应的是【微信群的聊天记录】,继续下一个昵称....");
                continue;
            }
            if (chatFriendNickName.contains("[小程序]")) {
                logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + chatFriendNickName + "】包含【[小程序]】对应的是【微信群的聊天记录】,继续下一个昵称....");
                continue;
            }
            if (chatFriendNickName.contains("[群待办]")) {
                logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + chatFriendNickName + "】包含【[群待办]】对应的是【微信群的聊天记录】,继续下一个昵称....");
                continue;
            }
            if (chatFriendNickName.contains("[有人@我]")) {
                logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + chatFriendNickName + "】包含【[有人@我]】对应的是【微信群的聊天记录】,继续下一个昵称....");
                continue;
            }
            if (chatFriendNickName.contains("我通过了你的朋友验证请求")) {
                logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + chatFriendNickName + "】包含【我通过了你的朋友验证请求】对应的是【微信群的聊天记录】,继续下一个昵称....");
                continue;
            }
            if (chatFriendNickName.contains("与群里其他人都不是")) {
                logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + chatFriendNickName + "】包含【与群里其他人都不是微信朋友关系】对应的是【微信群的聊天记录】,继续下一个昵称....");
                continue;
            }
            if (chatFriendNickName.contains("对方为企业微信用户")) {
                logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + chatFriendNickName + "】包含【对方为企业微信用户】对应的是【微信群的聊天记录】,继续下一个昵称....");
                continue;
            }
            if (chatFriendNickName.startsWith("你已添加了")) {
                logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + chatFriendNickName + "】包含【你已添加了*】对应的是【微信群的聊天记录】,继续下一个昵称....");
                continue;
            }
            String chatRecordNumStr = "-1";
            try {                                       //判断是否为-微信群的聊天记录，示例：[2条] 宋天宇: 宝润国际电梯房屋出售：，急售…
                Pattern pattern = Pattern.compile("(?<=\\[)(.+?)(?=条\\])");
                Matcher matcher = pattern.matcher(chatFriendNickName);
                while (matcher.find()) {
                    chatRecordNumStr = matcher.group();
                }
                Integer groupTotalNum = Integer.parseInt(chatRecordNumStr);
                if (groupTotalNum >= 0) {
                    logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + chatFriendNickName + "】包含【[*条]】对应的是【微信群的聊天记录】,继续下一个昵称....");
                    continue;
                }
            } catch (Exception e) {

            }

            //检测昵称是否末尾包含"…"，示例：A车～05.25-06.25 50米 沿河…
            if (chatFriendNickName.endsWith("…")) {
                logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测昵称末尾包含\"…\"，处理之前昵称【" + chatFriendNickName + "】....");
                chatFriendNickName = chatFriendNickName.substring(0, chatFriendNickName.length() - 1);
                logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测昵称末尾包含\"…\"，处理之后昵称【" + chatFriendNickName + "】....");
            }

            for (int i = 1; i <= 30; i++) {     //每间隔5秒点击一次，持续90秒
                //2.点击坐标【搜索】，当前坐标会引起微信对当前所有联系人和聊天对象进行建立索引，会有点慢，需要进行特别支持，暂时循环点击10次
                try {
                    driver.findElementByAndroidUIAutomator("new UiSelector().description(\"" + searchLocaltionStr + "\")").click();
                    logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【搜索框】成功....");
                    Thread.sleep(5000);         //此处会创建索引，会比较费时间才能打开
                } catch (Exception e) {
                    logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【搜索框】失败，因为微信正在建立索引....");
                    if (i == 30) {
                        throw new Exception("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【搜索框】均失败,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
                    } else {
                        Thread.sleep(5000);         //此处会创建索引，会比较费时间才能打开
                        logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】次点击坐标【搜索框】失败，因为微信正在建立索引....");
                        continue;
                    }
                }
                //3.点击坐标【搜索输入框】并输入昵称
                try {
                    driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + searchInputLocaltion + "\")").sendKeys(chatFriendNickName);
                    logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【输入昵称到搜索框:text/搜索】成功....");
                    Thread.sleep(1000);
                    break;
                } catch (Exception e) {
                    logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【输入昵称到搜索框:text/搜索】失败....");
                    try {
                        driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.EditText\")").sendKeys(chatFriendNickName);
                        logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【输入昵称到搜索框:className/android.widget.EditText】成功....");
                        Thread.sleep(1000);
                        break;
                    } catch (Exception e1) {
                        logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【输入昵称到搜索框:className/android.widget.EditText】失败....");
                        if (i == 30) {
                            throw new Exception("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【输入昵称到搜索框:text/搜索】与【输入昵称到搜索框:className/android.widget.EditText】均失败,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
                        } else {
                            Thread.sleep(5000);         //此处会创建索引，会比较费时间才能打开
                            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】次点击坐标【输入昵称到搜索框:text/搜索】与【输入昵称到搜索框:className/android.widget.EditText】均失败，因为微信正在建立索引....");
                            continue;
                        }
                    }
                }
            }

            //4.判断坐标【群聊】与【最常使用】是否存在
            boolean isChatGroupFlag = false;
            try {
                WebElement group_WebElement = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + groupLocaltion + "\")");
                logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测坐标【群聊】成功....");
                Thread.sleep(1000);
                isChatGroupFlag = true;
            } catch (Exception e) {
                try {
                    WebElement contactor_WebElement = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + mostUsedLocaltion + "\")");
                    logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测坐标【最常使用】成功....");
                    Thread.sleep(1000);
                    isChatGroupFlag = true;
                } catch (Exception e1) {
                    logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测坐标【群聊】与【最常使用】均不存在，当前昵称【" + chatFriendNickName + "】对应的可能是【联系人】或者【公众号】或者【聊天记录】....");
                }
            }
            if (!isChatGroupFlag) {         //非好友与联系人，返回【当前页面聊天好友信息】，继续下一个昵称
                Integer backChatPage_num = 1;
                while (true) {
                    try {
                        driver.findElementByAndroidUIAutomator("new UiSelector().textStartsWith(\"" + chatLocation + "\")");
                        logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + backChatPage_num + "】次返回【微信聊天界面】成功....");
                        Thread.sleep(1000);
                        break;
                    } catch (Exception e) {
                        logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + backChatPage_num + "】次返回【微信聊天界面】失败....");
                        if (backChatPage_num <= 10) {
                            driver.pressKeyCode(AndroidKeyCode.BACK);
                            Thread.sleep(1000);
                        } else {
                            driver.startActivity(chatActivity);      //返回【当前页面聊天好友信息】
                            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】通过【chatActivity】返回【当前页面聊天好友信息】....");
                            Thread.sleep(2000);
                            break;
                        }
                    } finally {
                        backChatPage_num++;
                    }
                }
//                logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + chatFriendNickName + "】不是【微信群】,继续下一个昵称....");
//                driver.startActivity(chatActivity);      //返回【当前页面聊天好友信息】
//                logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】返回【当前页面聊天好友信息】....");
//                Thread.sleep(2000);
                continue;
            }

            //5.点击坐标【昵称对应的微信好友】
            try {
                driver.findElementByXPath("//android.widget.TextView[@text=\"" + groupLocaltion + "\"]/../../../android.widget.RelativeLayout[2]").click();
                logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【昵称对应的微信好友群】通过【联系人的xpath】成功....");
                Thread.sleep(1000);
            } catch (Exception e) {
                try {
                    driver.findElementByXPath("//android.widget.TextView[@text=\"" + mostUsedLocaltion + "\"]/../../../android.widget.RelativeLayout[2]").click();
                    logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【昵称对应的微信好友群】通过【最常使用的xpath】成功....");
                    Thread.sleep(1000);
                } catch (Exception e1) {
                    throw new Exception("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】通过【联系人的xpath】与【最常使用的xpath】点击坐标【昵称对应的微信好友】均失败，当前昵称【\" + nickName + \"】对应的可能是【微信群】或者【公众号】或者【聊天记录】....");
                }
            }
//            try {
//                String str_0_of_9 = chatFriendNickName;
////                int firstEmojiIndex = EmojiUtil.getFirstEmojiIndex(chatFriendNickName);
////                if (firstEmojiIndex >= 0) {
////                    str_0_of_9 = chatFriendNickName.substring(0, firstEmojiIndex);        //截取emoji之前的字符串
////                }
//                List<WebElement> targetGroupElementList = Lists.newArrayList();
//                if (str_0_of_9.length() > 9) {                      //截取emoji字符串之后，长度还是超过9个字符，则截取前9个字符.
//                    //启用模糊匹配
//                    str_0_of_9 = str_0_of_9.substring(0, 9);
//                    targetGroupElementList = driver.findElementsByAndroidUIAutomator("new UiSelector().textContains(\"" + str_0_of_9 + "\")");
//                } else {
//                    targetGroupElementList = driver.findElementsByAndroidUIAutomator("new UiSelector().text(\"" + chatFriendNickName + "\")");
//                }
//                for (WebElement targetGroupElement : targetGroupElementList) {
//                    if ("android.widget.TextView".equals(targetGroupElement.getAttribute("class"))) {
//                        targetGroupElement.click();
//                        break;
//                    }
//                }
//                logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【昵称对应的微信好友】成功....");
//                Thread.sleep(1000);
//            } catch (Exception e) {
//                throw new Exception("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【昵称对应的微信好友】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
//            }

            //6.点击坐标【设置】new UiSelector().description("当前所在页面,与的聊天")
            try {
                WebElement chatInfo_WebElement = driver.findElementByAndroidUIAutomator("new UiSelector().description(\"" + chatInfoLocaltion + "\")");
                chatInfo_WebElement.click();
                logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【设置】成功....");
                Thread.sleep(2000);
            } catch (Exception e) {
                Integer backChatPage_num = 1;
                while (true) {
                    try {
                        driver.findElementByAndroidUIAutomator("new UiSelector().textStartsWith(\"" + chatLocation + "\")");
                        logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + backChatPage_num + "】次返回【微信聊天界面】成功....");
                        Thread.sleep(1000);
                        break;
                    } catch (Exception e1) {
                        logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + backChatPage_num + "】次返回【微信聊天界面】失败....");
                        if (backChatPage_num <= 10) {
                            driver.pressKeyCode(AndroidKeyCode.BACK);
                            Thread.sleep(1000);
                        } else {
                            driver.startActivity(chatActivity);      //返回【当前页面聊天好友信息】
                            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】通过【chatActivity】返回【当前页面聊天好友信息】....");
                            Thread.sleep(2000);
                            break;
                        }
                    } finally {
                        backChatPage_num++;
                    }
                }
//                driver.startActivity(chatActivity);         ////返回【当前页面聊天好友信息】
//                logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】返回【当前页面聊天好友信息】....");
//                Thread.sleep(2000);
                continue;
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
                    logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测【是否为微信群】成功，【微信群】--->>>【" + chatFriendNickName + "】群成员总数为：" + groupTotalNum + "个....");
                }
            } catch (Exception e) {

            } finally {
                if (!isChatGroupFlag) {         //非好友与联系人，返回【当前页面聊天好友信息】，继续下一个昵称
                    Integer backChatPage_num = 1;
                    while (true) {
                        try {
                            driver.findElementByAndroidUIAutomator("new UiSelector().textStartsWith(\"" + chatLocation + "\")");
                            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + backChatPage_num + "】次返回【微信聊天界面】成功....");
                            Thread.sleep(1000);
                            break;
                        } catch (Exception e1) {
                            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + backChatPage_num + "】次返回【微信聊天界面】失败....");
                            if (backChatPage_num <= 10) {
                                driver.pressKeyCode(AndroidKeyCode.BACK);
                                Thread.sleep(1000);
                            } else {
                                driver.startActivity(chatActivity);      //返回【当前页面聊天好友信息】
                                logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】通过【chatActivity】返回【当前页面聊天好友信息】....");
                                Thread.sleep(2000);
                                break;
                            }
                        } finally {
                            backChatPage_num++;
                        }
                    }
//                    driver.startActivity(chatActivity);      //返回【当前页面聊天好友信息】
//                    logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】返回【当前页面聊天好友信息】....");
//                    Thread.sleep(2000);
                    continue;
                }
            }

            for (int i = 0; i < 3; i++) {       //向上滑动三次
                try {
                    logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】次，scroll上滑中,检测【保存到通讯录】...");
                    driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollForward()");
                } catch (Exception e) {
                    logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】scroll上滑中,检测【保存到通讯录】....");
                }
            }

            try {
                //8.点击坐标【保存到通讯录】
                try {
                    WebElement aadToAddressBookLocalElement = driver.findElementByXPath("//android.widget.TextView[@text=\"" + aadToAddressBookLocaltion + "\"]/../../android.view.View");
                    //android.widget.TextView[@text="保存到通讯录"]/../../android.view.View
                    String switchName = aadToAddressBookLocalElement.getAttribute("content-desc");
                    if ("已关闭".equals(switchName)) {
                        aadToAddressBookLocalElement.click();
                        logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【保存到通讯录】【XPath定位】的【开关】成功....");
                    }
                } catch (Exception e) {
                    logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前不是【保存到通讯录】【XPath定位】的【开关】元素....");
                }
                Thread.sleep(2000);

                //9.点击坐标【置顶聊天】
                try {
                    WebElement topChatLocaltionElement = driver.findElementByXPath("//android.widget.TextView[@text=\"" + topChatLocaltion + "\"]/../../android.view.View");
                    String switchName = topChatLocaltionElement.getAttribute("content-desc");
                    if ("已开启".equals(switchName) && !chatFriendNickName.contains("内部交流群")) {
                        topChatLocaltionElement.click();
                        logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【置顶聊天】【XPath定位】的【开关】成功....");
                    }
                } catch (Exception e) {
                    logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前不是【置顶聊天】【XPath定位】的【开关】元素....");
                }
                Thread.sleep(2000);

                //10.点击坐标【消息免打扰】
                try {
                    WebElement dontDisturbTheNewsLocalElement = driver.findElementByXPath("//android.widget.TextView[@text=\"" + dontDisturbTheNewsLocaltion + "\"]/../../android.view.View");
                    String switchName = dontDisturbTheNewsLocalElement.getAttribute("content-desc");
                    if ("已关闭".equals(switchName)) {
                        dontDisturbTheNewsLocalElement.click();
                        logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【保存到通讯录】【XPath定位】的【开关】成功....");
                    }
                } catch (Exception e) {
                    logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前不是【保存到通讯录】【XPath定位】的【开关】元素....");
                }
                Thread.sleep(2000);

//                //8.点击坐标【已关闭】
//                List<WebElement> switchList_webElement = driver.findElementsByAndroidUIAutomator("new UiSelector().description(\"" + shutDownLocaltion + "\")");
//                if (switchList_webElement != null && switchList_webElement.size() > 0) {
//                    for (WebElement webElement : switchList_webElement) {
//                        webElement.click();
//                        Thread.sleep(2000);
//                        logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【已关闭】的【开关】成功....");
//                    }
//                }
            } catch (Exception e) {
                logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【消息免打扰】或者【消息免打扰】异常，当前昵称已是你的好友，继续下一个昵称....");
                e.printStackTrace();
            } finally {
                theSaveToAddressBookNum++;
                Integer backChatPage_num = 1;
                while (true) {
                    try {
                        driver.findElementByAndroidUIAutomator("new UiSelector().textStartsWith(\"" + chatLocation + "\")");
                        logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + backChatPage_num + "】次返回【微信聊天界面】成功....");
                        Thread.sleep(1000);
                        break;
                    } catch (Exception e1) {
                        logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + backChatPage_num + "】次返回【微信聊天界面】失败....");
                        if (backChatPage_num <= 10) {
                            driver.pressKeyCode(AndroidKeyCode.BACK);
                            Thread.sleep(1000);
                        } else {
                            driver.startActivity(chatActivity);      //返回【当前页面聊天好友信息】
                            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】通过【chatActivity】返回【当前页面聊天好友信息】....");
                            Thread.sleep(2000);
                            break;
                        }
                    } finally {
                        backChatPage_num++;
                    }
                }
//                driver.startActivity(chatActivity);         ////返回【当前页面聊天好友信息】
//                logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】返回【当前页面聊天好友信息】....");
//                Thread.sleep(2000);
            }
        }
        logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】 将群保存到通讯录【" + theSaveToAddressBookNum + "】个发送成功....");
        return true;
    }

    public static void main(String[] args) {
        try {
            Map<String, Object> paramMap = Maps.newHashMap();
            new RealMachineDevices().saveToAddressBook(paramMap);
//            Thread.sleep(5000);

//            String pre = "";
//            String chatFriendNickName = "[链接] 宋天宇: 宝润国际电梯房屋出售：，急售…";
//            Pattern pattern = Pattern.compile("(?<=\\[)链接(?=\\])");
//            Matcher matcher = pattern.matcher(chatFriendNickName);
//            while (matcher.find()) {
//                pre = matcher.group();
//                System.out.println("pre = " + pre);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

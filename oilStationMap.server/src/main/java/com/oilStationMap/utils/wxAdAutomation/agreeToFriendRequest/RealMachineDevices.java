package com.oilStationMap.utils.wxAdAutomation.agreeToFriendRequest;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.oilStationMap.utils.StringUtils;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
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
 * 真机设备 同意好友请求 策略
 * 默认 华为 P20 Pro
 */
public class RealMachineDevices implements AgreeToFriendRequest {

    public static final Logger logger = LoggerFactory.getLogger(RealMachineDevices.class);

    /**
     * 同意好友请求
     *
     * @param paramMap
     * @throws Exception
     */
    @Override
    public boolean agreeToFriendRequest(Map<String, Object> paramMap) throws Exception {
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
                        "agreeToFriendRequest";
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
        //点击坐标【联系人】
        String contactLocaltion =
                paramMap.get("contactLocaltion") != null ?
                        paramMap.get("contactLocaltion").toString() :
                        "联系人";
        //点击坐标【最常使用】
        String mostUsedLocaltion =
                paramMap.get("mostUsedLocaltion") != null ?
                        paramMap.get("mostUsedLocaltion").toString() :
                        "最常使用";
        //点击坐标【对方还不是你的朋友】
        String notYourFriendLocaltion =
                paramMap.get("notYourFriendLocaltion") != null ?
                        paramMap.get("notYourFriendLocaltion").toString() :
                        "对方还不是你的朋友";
        //坐标【添加到通讯录】
        String aadToContactBookLocaltion =
                paramMap.get("aadToContactBookLocaltion") != null ?
                        paramMap.get("aadToContactBookLocaltion").toString() :
                        "添加到通讯录";
        //坐标【发消息】
        String sendMessageBtnLocaltion =
                paramMap.get("sendMessageBtnLocaltion") != null ?
                        paramMap.get("sendMessageBtnLocaltion").toString() :
                        "发消息";
        //坐标【由于对方的隐私设置，你无法通过群聊将其添加至通讯录。】，注：如果这个坐标找不到则使用【确定】这个坐标
        String privacyContentLocaltion =
                paramMap.get("privacyContentLocaltion") != null ?
                        paramMap.get("privacyContentLocaltion").toString() :
                        "由于对方的隐私设置，你无法通过群聊将其添加至通讯录。";
        //坐标【发送添加朋友申请】
        String sendRequestMsgLocaltion =
                paramMap.get("sendRequestMsgLocaltion") != null ?
                        paramMap.get("sendRequestMsgLocaltion").toString() :
                        "发送添加朋友申请";
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
        //坐标【我】
        String meLocaltion =
                paramMap.get("meLocaltion") != null ?
                        paramMap.get("meLocaltion").toString() :
                        "我";
        //坐标【设置】
        String settingLocaltion =
                paramMap.get("settingLocaltion") != null ?
                        paramMap.get("settingLocaltion").toString() :
                        "设置";
        //坐标【聊天】
        String chatLocaltion =
                paramMap.get("chatLocaltion") != null ?
                        paramMap.get("chatLocaltion").toString() :
                        "聊天";
        //坐标【清空聊天记录】
        String clearChatRecordLocaltion =
                paramMap.get("clearChatRecordLocaltion") != null ?
                        paramMap.get("clearChatRecordLocaltion").toString() :
                        "清空聊天记录";
        //坐标【清空】
        String clearLocaltion =
                paramMap.get("clearLocaltion") != null ?
                        paramMap.get("clearLocaltion").toString() :
                        "清空";
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
            logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】连接Appium【" + appiumPort + "】成功....");
            Thread.sleep(10000);                                                                     //加载安卓页面10秒,保证xml树完全加载
        } catch (Exception e) {
            throw new Exception("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】配置连接android驱动出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】Appium端口号【" + appiumPort + "】的环境是否正常运行等原因....");
        } finally {
            //针对全局，在定位元素时，如果5秒内找不到的话调用隐式等待时间内一直找找，找到的话往结束，注：会极大拖延运行速度
//            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        }

        System.out.println("当前activity = " + driver.currentActivity());
        Activity chatActivity = new Activity("com.tencent.mm", ".ui.LauncherUI");

        Integer theAgreeNum = 0;
        Set<String> chatFriendsSet = Sets.newHashSet();

        //1.上滑同时检测坐标检测当前页面聊天好友信息
        int cyclesNumber = 0;       //循环下拉的次数
        int maxCyclesNumber = 60;       //默认超过30次
        while (true) {      //循环下拉当前好友聊天列表，并将其加入 chatFriendsSet
            try{
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
                            logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前不是聊天好友元素....");
                        }
                    }
                    if (cyclesNumber >= maxCyclesNumber) {           //当循环下拉的次数超过30次时，则强制终止循环，主要是群成员中存在昵称相同的人，导致groupMembersMap无法添加进去，导致数量达不到真实的群成员数量
                        //停止循环
                        logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】已滑到聊天界面的底部，第【" + cyclesNumber + "】次，scroll上滑中,检测当前页面聊天好友信息，当前 chatFriendsSet 的大小为：" + chatFriendsSet.size() + "...");
                        break;
                    } else {
                        try {
                            logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + cyclesNumber + "】次，scroll上滑中,检测当前页面聊天好友信息，当前 chatFriendsSet 的大小为：" + chatFriendsSet.size() + "...");
                            driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollForward()");
                        } catch (Exception e) {
                            logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】scroll上滑中,检测当前页面聊天好友信息....");
                        }
                        cyclesNumber++;
                    }
                }
            } catch (Exception e) {
                ////异常原因：因为微信好友与微信群比较多时，微信聊天页面加载比较多，会比较费时间才能打开，一直加载【地球与人】的界面，导致无法定位
                logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】上滑【当前页面聊天好友信息】到底部时，可能存在异常，忽视....");
            }
        }
        logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【上滑同时检测坐标检测当前页面聊天好友信息】成功....");
        Thread.sleep(1000);

        int chatFriendsIndex = 1;
        for (String chatFriendNickName : chatFriendsSet) {
            System.out.println("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + chatFriendsIndex + "】个好友 " + " ---->>> " + chatFriendNickName);
            chatFriendsIndex++;
        }
        System.out.println("=============================================================================================");
        System.out.println("=============================================================================================");
        System.out.println("=============================================================================================");

        //循环遍历好友昵称列表，点击坐标【搜索】与【搜索框】
        for (String chatFriendNickName : chatFriendsSet) {
//            if (!chatFriendNickName.contains("驰墨教育")) {//A车～09.08-09.05 1米 一曲       A车～秀山重庆往返17358385547田丰
//                continue;
//            }
            if (chatFriendNickName.contains("油站科技")) {      //确保坐标：微信( 存在
                logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + chatFriendNickName + "】包含【油站科技】对应的是【自己人】,继续下一个昵称....");
                continue;
            }
            if (chatFriendNickName.contains("[店员消息]")) {
                logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + chatFriendNickName + "】包含【[店员消息]】对应的是【微信群的聊天记录】,继续下一个昵称....");
                continue;
            }
            if (chatFriendNickName.contains("[链接]")) {
                logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + chatFriendNickName + "】包含【[链接]】对应的是【微信群的聊天记录】,继续下一个昵称....");
                continue;
            }
            if (chatFriendNickName.contains("[图片]")) {
                logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + chatFriendNickName + "】包含【[图片]】对应的是【微信群的聊天记录】,继续下一个昵称....");
                continue;
            }
            if (chatFriendNickName.contains("[小程序]")) {
                logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + chatFriendNickName + "】包含【[小程序]】对应的是【微信群的聊天记录】,继续下一个昵称....");
                continue;
            }
            if (chatFriendNickName.contains("[群待办]")) {
                logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + chatFriendNickName + "】包含【[群待办]】对应的是【微信群的聊天记录】,继续下一个昵称....");
                continue;
            }
            if (chatFriendNickName.contains("[有人@我]")) {
                logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + chatFriendNickName + "】包含【[有人@我]】对应的是【微信群的聊天记录】,继续下一个昵称....");
                continue;
            }
            if (chatFriendNickName.contains("我通过了你的朋友验证请求")) {
                logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + chatFriendNickName + "】包含【我通过了你的朋友验证请求】对应的是【微信群的聊天记录】,继续下一个昵称....");
                continue;
            }
            if (chatFriendNickName.contains("与群里其他人都不是")) {
                logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + chatFriendNickName + "】包含【与群里其他人都不是微信朋友关系】对应的是【微信群的聊天记录】,继续下一个昵称....");
                continue;
            }
            if (chatFriendNickName.contains("对方为企业微信用户")) {
                logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + chatFriendNickName + "】包含【对方为企业微信用户】对应的是【微信群的聊天记录】,继续下一个昵称....");
                continue;
            }
            if (chatFriendNickName.startsWith("你已添加了")) {
                logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + chatFriendNickName + "】包含【你已添加了*】对应的是【微信群的聊天记录】,继续下一个昵称....");
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
                    logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + chatFriendNickName + "】包含【[*条]】对应的是【微信群的聊天记录】,继续下一个昵称....");
                    continue;
                }
            } catch (Exception e) {

            }

            //检测昵称是否末尾包含"…"，示例：A车～05.25-06.25 50米 沿河…
            if (chatFriendNickName.endsWith("…")) {
                logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测昵称末尾包含\"…\"，处理之前昵称【" + chatFriendNickName + "】....");
                chatFriendNickName = chatFriendNickName.substring(0, chatFriendNickName.length() - 1);
                logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测昵称末尾包含\"…\"，处理之后昵称【" + chatFriendNickName + "】....");
            }

            for (int i = 1; i <= 30; i++) {     //每间隔5秒点击一次，持续90秒
                //2.点击坐标【搜索】，当前坐标会引起微信对当前所有联系人和聊天对象进行建立索引，会有点慢，需要进行特别支持，暂时循环点击10次
                try {
                    driver.findElementByAndroidUIAutomator("new UiSelector().description(\"" + searchLocaltionStr + "\")").click();
                    logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【搜索框】成功....");
                    Thread.sleep(5000);         //此处会创建索引，会比较费时间才能打开
                } catch (Exception e) {
                    logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【搜索框】失败，因为微信正在建立索引....");
                    if (i == 30) {
                        throw new Exception("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【搜索框】均失败,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
                    } else {
                        Thread.sleep(5000);         //此处会创建索引，会比较费时间才能打开
                        logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】次点击坐标【搜索框】失败，因为微信正在建立索引....");
                        continue;
                    }
                }
                //3.点击坐标【搜索输入框】
                try {
                    driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + searchInputLocaltion + "\")").sendKeys(chatFriendNickName);
                    logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【输入昵称到搜索框:text/搜索】成功....");
                    Thread.sleep(1000);
                    break;
                } catch (Exception e) {
                    logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【输入昵称到搜索框:text/搜索】失败....");
                    try {
                        driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.EditText\")").sendKeys(chatFriendNickName);
                        logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【输入昵称到搜索框:className/android.widget.EditText】成功....");
                        Thread.sleep(1000);
                        break;
                    } catch (Exception e1) {
                        logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【输入昵称到搜索框:className/android.widget.EditText】失败....");
                        if (i == 30) {
                            throw new Exception("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【输入昵称到搜索框:text/搜索】与【输入昵称到搜索框:className/android.widget.EditText】均失败,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
                        } else {
                            Thread.sleep(5000);         //此处会创建索引，会比较费时间才能打开
                            logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + i + "】次点击坐标【输入昵称到搜索框:text/搜索】与【输入昵称到搜索框:className/android.widget.EditText】均失败，因为微信正在建立索引....");
                            continue;
                        }
                    }
                }
            }

            //4.判断坐标【联系人】与【最常使用】是否存在
            boolean isChatFriendsFlag = false;
            try {
                driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + contactLocaltion + "\")");
                Thread.sleep(1000);
                isChatFriendsFlag = true;
            } catch (Exception e) {
                try {
                    driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + mostUsedLocaltion + "\")");
                    Thread.sleep(1000);
                    isChatFriendsFlag = true;
                } catch (Exception e1) {
                    logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】判断坐标【联系人】与【最常使用】均不存在，当前昵称【" + chatFriendNickName + "】对应的可能是【微信群】或者【公众号】或者【聊天记录】....");
                }
            }
            if (!isChatFriendsFlag) {         //非好友与联系人，返回【当前页面聊天好友信息】，继续下一个昵称
                Integer backChatPage_num = 1;
                while (true) {
                    try {
                        driver.findElementByAndroidUIAutomator("new UiSelector().textStartsWith(\"" + chatLocation + "\")");
                        logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + backChatPage_num + "】次返回【微信聊天界面】成功....");
                        Thread.sleep(1000);
                        break;
                    } catch (Exception e) {
                        logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + backChatPage_num + "】次返回【微信聊天界面】失败....");
                        if (backChatPage_num <= 10) {
                            driver.pressKeyCode(AndroidKeyCode.BACK);
                            Thread.sleep(1000);
                        } else {
                            driver.startActivity(chatActivity);      //返回【当前页面聊天好友信息】
                            logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】通过【chatActivity】返回【当前页面聊天好友信息】....");
                            Thread.sleep(2000);
                            break;
                        }
                    } finally {
                        backChatPage_num++;
                    }
                }
                continue;
            }


            //5.点击坐标【昵称对应的微信好友】
            try {
                driver.findElementByXPath("//android.widget.TextView[@text=\"" + contactLocaltion + "\"]/../../../android.widget.RelativeLayout[2]").click();
                logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【昵称对应的微信好友群】通过【联系人的xpath】成功....");
                Thread.sleep(1000);
            } catch (Exception e) {
                try {
                    driver.findElementByXPath("//android.widget.TextView[@text=\"" + mostUsedLocaltion + "\"]/../../../android.widget.RelativeLayout[2]").click();
                    logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【昵称对应的微信好友群】通过【最常使用的xpath】成功....");
                    Thread.sleep(1000);
                } catch (Exception e1) {
                    throw new Exception("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】通过【联系人的xpath】与【最常使用的xpath】点击坐标【昵称对应的微信好友】均失败，当前昵称【\" + nickName + \"】对应的可能是【微信群】或者【公众号】或者【聊天记录】....");
                }
            }
//            try {
//                String str_0_of_9 = chatFriendNickName;
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
//                logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【昵称对应的微信好友】成功....");
//                Thread.sleep(1000);
//            } catch (Exception e) {
//                throw new Exception("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【昵称对应的微信好友】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
//            }
            //判断到最后，则是微信好友
            logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测【当前页面聊天好友信息】成功，【微信好友】--->>>【" + chatFriendNickName + "】为....");

            //6.点击坐标【对方还不是你的朋友】，如果是好友则跳转activity继续下一个昵称，否则进行添加为好友操作.
            boolean isYourFriendFlag = false;
            try {
                driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + notYourFriendLocaltion + "\")").click();
                logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【对方还不是你的朋友】成功....");
                Thread.sleep(6000);
            } catch (Exception e) {
                try {
                    driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + "添加" + "\")").click();
                    logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【对方还不是你的朋友】的【添加】成功....");
                    Thread.sleep(6000);
                } catch (Exception e1) {
                    try {
                        driver.findElementByAndroidUIAutomator("new UiSelector().description(\"" + "添加" + "\")").click();
                        logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【对方还不是你的朋友】的【添加】成功....");
                        Thread.sleep(6000);
                    } catch (Exception e2) {
                        isYourFriendFlag = true;
                        logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【对方还不是你的朋友】异常，当前昵称已是你的好友，继续下一个昵称....");
                    }
                }
            } finally {
                if (isYourFriendFlag) {
                    Integer backChatPage_num = 1;
                    while (true) {
                        try {
                            driver.findElementByAndroidUIAutomator("new UiSelector().textStartsWith(\"" + chatLocation + "\")");
                            logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + backChatPage_num + "】次返回【微信聊天界面】成功....");
                            Thread.sleep(1000);
                            break;
                        } catch (Exception e) {
                            logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + backChatPage_num + "】次返回【微信聊天界面】失败....");
                            if (backChatPage_num <= 10) {
                                driver.pressKeyCode(AndroidKeyCode.BACK);
                                Thread.sleep(1000);
                            } else {
                                driver.startActivity(chatActivity);      //返回【当前页面聊天好友信息】
                                logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】通过【chatActivity】返回【当前页面聊天好友信息】....");
                                Thread.sleep(2000);
                                break;
                            }
                        } finally {
                            backChatPage_num++;
                        }
                    }
                    continue;
                }
            }
            //点击坐标【添加到通讯录】
            try {
                WebElement aadToContactBook_Element = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + aadToContactBookLocaltion + "\")");
                aadToContactBook_Element.click();
                logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【添加到通讯录】成功....");
                Thread.sleep(6000);
            } catch (Exception e) {
                logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【添加到通讯录】异常，可能是没有找到【添加到通讯录】按钮....");
            }
            //点击坐标【添加到通讯录】后检测坐标【发消息】,点击坐标【添加到通讯录】直接被添加为好友，则检测坐标【发消息】
            try {
                driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + sendMessageBtnLocaltion + "\")");
                logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【添加到通讯录】后，检测坐标【发消息】成功....");
                isYourFriendFlag = true;
                Thread.sleep(2000);
                theAgreeNum++;
            } catch (Exception e) {
                logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【添加到通讯录】后，检测坐标【发消息】异常，当前用户没有在点击坐标【添加到通讯录】直接添加为好友....");
            } finally {
                if (isYourFriendFlag) {
                    Integer backChatPage_num = 1;
                    while (true) {
                        try {
                            driver.findElementByAndroidUIAutomator("new UiSelector().textStartsWith(\"" + chatLocation + "\")");
                            logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + backChatPage_num + "】次返回【微信聊天界面】成功....");
                            Thread.sleep(1000);
                            break;
                        } catch (Exception e) {
                            logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + backChatPage_num + "】次返回【微信聊天界面】失败....");
                            if (backChatPage_num <= 10) {
                                driver.pressKeyCode(AndroidKeyCode.BACK);
                                Thread.sleep(1000);
                            } else {
                                driver.startActivity(chatActivity);      //返回【当前页面聊天好友信息】
                                logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】通过【chatActivity】返回【当前页面聊天好友信息】....");
                                Thread.sleep(2000);
                                break;
                            }
                        } finally {
                            backChatPage_num++;
                        }
                    }
                    continue;
                }
            }
            //点击坐标【添加到通讯录】后，后检测坐标【由于对方的隐私设置，你无法通过群聊将其添加至通讯录。】，注：如果这个坐标找不到则使用【确定】这个坐标 privacyContentLocaltion
            try {
                WebElement privacyContent_Element = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + privacyContentLocaltion + "\")");
                if (privacyContent_Element != null) {
                    logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【添加到通讯录】后，检测坐标【由于对方的隐私设置，你无法通过群聊将其添加至通讯录】成功，直接下一个群成员坐标....");
                    isYourFriendFlag = true;
                    theAgreeNum++;
                }
                Thread.sleep(2000);
            } catch (Exception e) {
                logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【添加到通讯录】后，检测坐标【由于对方的隐私设置，你无法通过群聊将其添加至通讯录】异常，继续下一步添加当前好友步骤....");
            } finally {
                if (isYourFriendFlag) {
                    Integer backChatPage_num = 1;
                    while (true) {
                        try {
                            driver.findElementByAndroidUIAutomator("new UiSelector().textStartsWith(\"" + chatLocation + "\")");
                            logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + backChatPage_num + "】次返回【微信聊天界面】成功....");
                            Thread.sleep(1000);
                            break;
                        } catch (Exception e) {
                            logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + backChatPage_num + "】次返回【微信聊天界面】失败....");
                            if (backChatPage_num <= 10) {
                                driver.pressKeyCode(AndroidKeyCode.BACK);
                                Thread.sleep(1000);
                            } else {
                                driver.startActivity(chatActivity);      //返回【当前页面聊天好友信息】
                                logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】通过【chatActivity】返回【当前页面聊天好友信息】....");
                                Thread.sleep(2000);
                                break;
                            }
                        } finally {
                            backChatPage_num++;
                        }
                    }
                    continue;
                }
            }
            //检测坐标【发送添加朋友申请】，避免出现：在【单个群成员简介】显示push【对方账号异常，无法添加朋友。】，消失得很快，无法捕捉，致使本来应该在【发送页面】而实际停留在【单个群成员简介】
            try {
                driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + sendRequestMsgLocaltion + "\")");
                logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【添加到通讯录】后，检测坐标【发送添加朋友申请】成功....");
                Thread.sleep(1000);
            } catch (Exception e) {
                logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【添加到通讯录】后，检测坐标【发送添加朋友申请】异常，以为【单个群成员简介】显示push【对方账号异常，无法添加朋友。】，消失得很快，无法捕捉，则直接下一个群成员坐标....");
                Integer backChatPage_num = 1;
                while (true) {
                    try {
                        driver.findElementByAndroidUIAutomator("new UiSelector().textStartsWith(\"" + chatLocation + "\")");
                        logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + backChatPage_num + "】次返回【微信聊天界面】成功....");
                        Thread.sleep(1000);
                        break;
                    } catch (Exception e1) {
                        logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + backChatPage_num + "】次返回【微信聊天界面】失败....");
                        if (backChatPage_num <= 10) {
                            driver.pressKeyCode(AndroidKeyCode.BACK);
                            Thread.sleep(1000);
                        } else {
                            driver.startActivity(chatActivity);      //返回【当前页面聊天好友信息】
                            logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】通过【chatActivity】返回【当前页面聊天好友信息】....");
                            Thread.sleep(2000);
                            break;
                        }
                    } finally {
                        backChatPage_num++;
                    }
                }
                continue;
            }
            //点击坐标【朋友圈】，主要是为了选择权限
            try {
                WebElement friendCircleBtn_Element = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + friendCircleLocaltion + "\")");
                friendCircleBtn_Element.click();
                logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【朋友圈】成功....");
                Thread.sleep(6000);
            } catch (Exception e) {
                logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【朋友圈】异常，可能是当前微信版本锅底，没有找到【朋友圈】按钮....");
            }
            //点击坐标【发送】
            try {
                WebElement sendBtn_Element = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + sendBtnLocaltion + "\")");
                sendBtn_Element.click();
                logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【发送】成功....");
                Thread.sleep(5000);
                theAgreeNum++;
            } catch (Exception e) {
                logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【发送】异常，可能出现了【对方帐号异常，无法添加朋友。】或者【由于对方的隐私设置，你无法通过群聊将其添加至通讯录】....");
            }
            //检测坐标【添加到通讯录】
            try {
                WebElement aadToContactBook_Element = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + aadToContactBookLocaltion + "\")");
                logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【发送】后，检测坐标【添加到通讯录】成功，同意好友请求【" + chatFriendNickName + "】通知发送成功....");
            } catch (Exception e) {
                logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【发送】后，检测坐标【添加到通讯录】时异常，可能是当前用户【" + chatFriendNickName + "】在发送阶段才显示【对方账号异常，无法添加朋友。】....");
            } finally {
                Integer backChatPage_num = 1;
                while (true) {
                    try {
                        driver.findElementByAndroidUIAutomator("new UiSelector().textStartsWith(\"" + chatLocation + "\")");
                        logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + backChatPage_num + "】次返回【微信聊天界面】成功....");
                        Thread.sleep(1000);
                        break;
                    } catch (Exception e) {
                        logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + backChatPage_num + "】次返回【微信聊天界面】失败....");
                        if (backChatPage_num <= 10) {
                            driver.pressKeyCode(AndroidKeyCode.BACK);
                            Thread.sleep(1000);
                        } else {
                            driver.startActivity(chatActivity);      //返回【当前页面聊天好友信息】
                            logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】通过【chatActivity】返回【当前页面聊天好友信息】....");
                            Thread.sleep(2000);
                            break;
                        }
                    } finally {
                        backChatPage_num++;
                    }
                }
                continue;
            }
        }
        Thread.sleep(10000);        //等待10秒页面加载完成...
        //点击坐标【我】
        try {
            driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + meLocaltion + "\")").click();
            logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【我】成功....");
            Thread.sleep(1000);
        } catch (Exception e) {
            logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【我】异常....");
        }
        //点击坐标【设置】
        try {
            driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + settingLocaltion + "\")").click();
            logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【设置】成功....");
            Thread.sleep(1000);
        } catch (Exception e) {
            logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【设置】异常....");
        }
        //点击坐标【聊天】
        try {
            driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + chatLocaltion + "\")").click();
            logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【聊天】成功....");
            Thread.sleep(1000);
        } catch (Exception e) {
            logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【聊天】异常....");
        }
        //点击坐标【清空聊天记录】
        try {
            driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + clearChatRecordLocaltion + "\")").click();
            logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【清空聊天记录】成功....");
            Thread.sleep(1000);
        } catch (Exception e) {
            logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【清空聊天记录】异常....");
        }
        //点击坐标【清空】
        try {
//            driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + clearLocaltion + "\")").click();
            logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【清空】成功，后续将要沉睡5分钟........");
            Thread.sleep(1000 * 60 * 5);
        } catch (Exception e) {
            logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【清空】异常....");
        }
        logger.info("【同意好友请求】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】 同意好友请求【" + theAgreeNum + "】个发送成功....");
        return true;
    }

    public static void main(String[] args) {
        try {
            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("deviceName", "5LM0216122009385");
            paramMap.put("deviceNameDesc", "华为 Mate8 _ 6");
            new RealMachineDevices().agreeToFriendRequest(paramMap);
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

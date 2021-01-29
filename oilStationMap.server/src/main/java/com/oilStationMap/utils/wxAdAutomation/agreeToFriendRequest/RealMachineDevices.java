package com.oilStationMap.utils.wxAdAutomation.agreeToFriendRequest;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.oilStationMap.utils.EmojiUtil;
import com.oilStationMap.utils.StringUtils;
import com.oilStationMap.utils.wxAdAutomation.addGroupMembersAsFriends.AddGroupMembersAsFriends;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import org.apache.commons.lang.time.StopWatch;
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
 * 默认 华为 Mate 8
 */
public class RealMachineDevices implements AddGroupMembersAsFriends {

    public static final Logger logger = LoggerFactory.getLogger(RealMachineDevices.class);

    /**
     * 同意好友请求
     *
     * @param paramMap
     * @throws Exception
     */
    @Override
    public void addGroupMembersAsFriends(Map<String, Object> paramMap, StopWatch sw) throws Exception {
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
        //操作
        String action =
                paramMap.get("action") != null ?
                        paramMap.get("action").toString() :
                        "agreeToFriendRequest";
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
        //坐标【查看全部群成员】
        String checkAllGroupMembers =
                paramMap.get("checkAllGroupMembers") != null ?
                        paramMap.get("checkAllGroupMembers").toString() :
                        "查看全部群成员";
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
            URL remoteUrl = new URL("http://localhost:" + 4723 + "/wd/hub");                            //连接本地的appium
            driver = new AndroidDriver(remoteUrl, desiredCapabilities);
            sw.split();
            logger.info("设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】连接Appium成功，总共花费 " + sw.toSplitString() + " 秒....");
            Thread.sleep(10000);                                                                     //加载安卓页面10秒,保证xml树完全加载
        } catch (Exception e) {
            sw.split();
            throw new Exception("配置连接android驱动出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的环境是否正常运行等原因，总共花费 " + sw.toSplitString() + " 秒....");
        }

        Integer theAddNum = 0;
        Set<String> chatFriendsSet = Sets.newHashSet();

        //上滑同时检测坐标检测当前页面聊天好友信息
        try {
            int cyclesNumber = 0;       //循环下拉的次数
            int maxCyclesNumber = 2;       //默认超过30次
            while (true) {      //循环下拉当前好友聊天列表，并将其加入 chatFriendsSet
                WebElement listViewElement = driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.ListView\")");
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
                            sw.split();
                            logger.info("当前不是聊天好友元素，总共花费 " + sw.toSplitString() + " 秒....");
                        }
                    }
                    if (cyclesNumber >= maxCyclesNumber) {           //当循环下拉的次数超过30次时，则强制终止循环，主要是群成员中存在昵称相同的人，导致groupMembersMap无法添加进去，导致数量达不到真实的群成员数量
                        //停止循环
                        logger.info("已滑到聊天界面的底部，第【" + cyclesNumber + "】次，scroll上滑中,检测当前页面聊天好友信息，当前 chatFriendsSet 的大小为：" + chatFriendsSet.size() + "...");
                        break;
                    } else {
                        try {
                            logger.info("第【" + cyclesNumber + "】次，scroll上滑中,检测当前页面聊天好友信息，当前 chatFriendsSet 的大小为：" + chatFriendsSet.size() + "...");
                            driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollForward()");
                        } catch (Exception e) {
                            sw.split();
                            logger.info("scroll上滑中,检测当前页面聊天好友信息，总共花费 " + sw.toSplitString() + " 秒....");
                        }
                        cyclesNumber++;
                    }
                }
            }
            int chatFriendsIndex = 1;
            for (String chatFriendNickName : chatFriendsSet) {
                System.out.println("第【" + chatFriendsIndex + "】个好友 " + " ---->>> " + chatFriendNickName);
                chatFriendsIndex++;
            }
            sw.split();
            logger.info("点击坐标【上滑同时检测坐标检测当前页面聊天好友信息】成功，总共花费 " + sw.toSplitString() + " 秒....");
        } catch (Exception e) {
            throw new Exception("点击坐标【上滑同时检测坐标检测当前页面聊天好友信息】均失败");
        } finally {
            Thread.sleep(1000);
        }

        //点击坐标【搜索】与【搜索框】
        for (String chatFriendNickName : chatFriendsSet) {
            for (int i = 1; i <= 30; i++) {     //每间隔5秒点击一次，持续90秒
                //2.点击坐标【搜索】，当前坐标会引起微信对当前所有联系人和聊天对象进行建立索引，会有点慢，需要进行特别支持，暂时循环点击10次
                try {
                    driver.findElementByAndroidUIAutomator("new UiSelector().description(\"" + searchLocaltionStr + "\")").click();
                    sw.split();
                    logger.info("点击坐标【搜索框】成功，总共花费 " + sw.toSplitString() + " 秒....");
                    Thread.sleep(5000);         //此处会创建索引，会比较费时间才能打开
                } catch (Exception e) {
                    sw.split();
                    logger.info("点击坐标【搜索框】失败，因为微信正在建立索引，总共花费 " + sw.toSplitString() + " 秒....");
                    if (i == 30) {
                        throw new Exception("点击坐标【搜索框】均失败,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因，总共花费 " + sw.toSplitString() + " 秒....");
                    } else {
                        Thread.sleep(5000);         //此处会创建索引，会比较费时间才能打开
                        sw.split();
                        logger.info("第【" + i + "】次点击坐标【搜索框】失败，因为微信正在建立索引，总共花费 " + sw.toSplitString() + " 秒....");
                        continue;
                    }
                }
                //3.点击坐标【搜索输入框】
                try {
                    driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + searchInputLocaltion + "\")").sendKeys(chatFriendNickName);
                    sw.split();
                    logger.info("点击坐标【输入昵称到搜索框:text/搜索】成功，总共花费 " + sw.toSplitString() + " 秒....");
                    Thread.sleep(1000);
                    break;
                } catch (Exception e) {
                    sw.split();
                    logger.info("点击坐标【输入昵称到搜索框:text/搜索】失败，总共花费 " + sw.toSplitString() + " 秒....");
                    try {
                        driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.EditText\")").sendKeys(chatFriendNickName);
                        sw.split();
                        logger.info("点击坐标【输入昵称到搜索框:className/android.widget.EditText】成功，总共花费 " + sw.toSplitString() + " 秒....");
                        Thread.sleep(1000);
                        break;
                    } catch (Exception e1) {
                        sw.split();
                        logger.info("点击坐标【输入昵称到搜索框:className/android.widget.EditText】失败，总共花费 " + sw.toSplitString() + " 秒....");
                        sw.split();
                        if (i == 30) {
                            throw new Exception("点击坐标【输入昵称到搜索框:text/搜索】与【输入昵称到搜索框:className/android.widget.EditText】均失败,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因，总共花费 " + sw.toSplitString() + " 秒....");
                        } else {
                            Thread.sleep(5000);         //此处会创建索引，会比较费时间才能打开
                            sw.split();
                            logger.info("第【" + i + "】次点击坐标【输入昵称到搜索框:text/搜索】与【输入昵称到搜索框:className/android.widget.EditText】均失败，因为微信正在建立索引，总共花费 " + sw.toSplitString() + " 秒....");
                            continue;
                        }
                    }
                }
            }

            //4.点击坐标【昵称对应的微信好友】
            try {
                String str_0_of_9 = chatFriendNickName;
//                int firstEmojiIndex = EmojiUtil.getFirstEmojiIndex(chatFriendNickName);
//                if (firstEmojiIndex >= 0) {
//                    str_0_of_9 = chatFriendNickName.substring(0, firstEmojiIndex);        //截取emoji之前的字符串
//                }
                List<WebElement> targetGroupElementList = Lists.newArrayList();
                if (str_0_of_9.length() > 9) {                      //截取emoji字符串之后，长度还是超过9个字符，则截取前9个字符.
                    //启用模糊匹配
                    str_0_of_9 = str_0_of_9.substring(0, 9);
                    targetGroupElementList = driver.findElementsByAndroidUIAutomator("new UiSelector().textContains(\"" + str_0_of_9 + "\")");
                } else {
                    targetGroupElementList = driver.findElementsByAndroidUIAutomator("new UiSelector().text(\"" + chatFriendNickName + "\")");
                }
                for (WebElement targetGroupElement : targetGroupElementList) {
                    if ("android.widget.TextView".equals(targetGroupElement.getAttribute("class"))) {
                        targetGroupElement.click();
                        break;
                    }
                }
                sw.split();
                logger.info("点击坐标【昵称对应的微信好友】成功，总共花费 " + sw.toSplitString() + " 秒....");
                Thread.sleep(1000);
            } catch (Exception e) {
                sw.split();
                throw new Exception("长按坐标【昵称对应的微信好友】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因，总共花费 " + sw.toSplitString() + " 秒....");
            }

            //判断是否为-公众号，查看坐标【设置】
            try {
                WebElement publicNumber_WebElement = driver.findElementByAndroidUIAutomator("new UiSelector().description(\"" + "设置" + "\")");
                logger.info("【检测当前页面聊天好友信息】成功，【公众号】--->>>【" + chatFriendNickName + "】，总共花费 " + sw.toSplitString() + " 秒....");
                continue;
            } catch (Exception e) {

            }
            //判断是否为-微信群，查看坐标【群成员总数：聊天信息(】
            String groupTotalNumStr = "-1";
            try {
                Pattern pattern = Pattern.compile("(?<=\\()(.+?)(?=\\))");
                Matcher matcher = pattern.matcher(chatFriendNickName);
                while (matcher.find()) {
                    groupTotalNumStr = matcher.group();
                }
                Integer groupTotalNum = Integer.parseInt(groupTotalNumStr);
                if (groupTotalNum > 0) {
                    logger.info("【检测当前页面聊天好友信息】成功，【微信群】--->>>【" + chatFriendNickName + "】为，总共花费 " + sw.toSplitString() + " 秒....");
                    continue;
                }
            } catch (Exception e) {

            }
            //判断到最后，则是微信好友
            logger.info("【检测当前页面聊天好友信息】成功，【微信好友】--->>>【" + chatFriendNickName + "】为，总共花费 " + sw.toSplitString() + " 秒....");

            driver.pressKeyCode(AndroidKeyCode.BACK);                   //返回【搜索结果界面】
            Thread.sleep(2000);

            driver.pressKeyCode(AndroidKeyCode.BACK);                   //返回【当前页面聊天好友信息】
            Thread.sleep(2000);
        }

        sw.split();
        logger.info("设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】 同意好友请求【" + theAddNum + "】个发送成功，总共花费 " + sw.toSplitString() + " 秒....");
    }

    public static void main(String[] args) {
        try {
            StopWatch sw = new StopWatch();
            sw.start();
            Map<String, Object> paramMap = Maps.newHashMap();
            new RealMachineDevices().addGroupMembersAsFriends(paramMap, sw);
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.oilStationMap.utils.wxAdAutomation.addGroupMembersAsFriends;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.oilStationMap.utils.EmojiUtil;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import org.apache.commons.lang.time.StopWatch;
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
 * 真机设备 根据微信群昵称添加群成员为好友 策略
 * 默认 华为 Mate 8
 */
public class RealMachineDevices implements AddGroupMembersAsFriends {

    public static final Logger logger = LoggerFactory.getLogger(RealMachineDevices.class);

    /**
     * 根据微信群昵称添加群成员为好友
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
            //不用处理
        }
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
        //坐标【昵称对应的微信好友/群】
        String nickName =
                paramMap.get("nickName") != null ?
                        paramMap.get("nickName").toString() :
                        "铜仁推广商务群";
        nickName = EmojiUtil.emojiRecovery(nickName);
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
            this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
            sw.split();
            throw new Exception("配置连接android驱动出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的环境是否正常运行等原因，总共花费 " + sw.toSplitString() + " 秒....");
        }
        for (int i = 1; i <= 30; i++) {     //每间隔5秒点击一次，持续90秒
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
                driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + searchInputLocaltion + "\")").sendKeys(nickName);
                sw.split();
                logger.info("点击坐标【输入昵称到搜索框:text/搜索】成功，总共花费 " + sw.toSplitString() + " 秒....");
                Thread.sleep(1000);
                break;
            } catch (Exception e) {
                sw.split();
                logger.info("点击坐标【输入昵称到搜索框:text/搜索】失败，总共花费 " + sw.toSplitString() + " 秒....");
                try {
                    driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.EditText\")").sendKeys(nickName);
                    sw.split();
                    logger.info("点击坐标【输入昵称到搜索框:className/android.widget.EditText】成功，总共花费 " + sw.toSplitString() + " 秒....");
                    Thread.sleep(1000);
                    break;
                } catch (Exception e1) {
                    sw.split();
                    logger.info("点击坐标【输入昵称到搜索框:className/android.widget.EditText】失败，总共花费 " + sw.toSplitString() + " 秒....");
                    this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
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
        //4.点击坐标【昵称对应的微信好友/群】
        try {
            String str_0_of_9 = nickName;
            int firstEmojiIndex = EmojiUtil.getFirstEmojiIndex(nickName);
            if (firstEmojiIndex >= 0) {
                str_0_of_9 = nickName.substring(0, firstEmojiIndex);        //截取emoji之前的字符串
            }
            if (str_0_of_9.length() > 9) {                      //截取emoji字符串之后，长度还是超过9个字符，则截取前9个字符.
                str_0_of_9 = str_0_of_9.substring(0, 9);
            }        //启用模糊匹配
            List<WebElement> targetGroupElementList = driver.findElementsByAndroidUIAutomator("new UiSelector().textContains(\"" + str_0_of_9 + "\")");
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
            this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
            sw.split();
            throw new Exception("长按坐标【昵称对应的微信好友群】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因，总共花费 " + sw.toSplitString() + " 秒....");
        }
        //5.点击坐标【右上角的三个点：聊天信息】
        try {
            driver.findElementByAndroidUIAutomator("new UiSelector().description(\"" + threePointLocaltion + "\")").click();
            sw.split();
            logger.info("点击坐标【右上角的三个点】成功，总共花费 " + sw.toSplitString() + " 秒....");
            Thread.sleep(1000);
        } catch (Exception e) {
            this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
            sw.split();
            throw new Exception("长按坐标【右上角的三个点】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因，总共花费 " + sw.toSplitString() + " 秒....");
        }
        //6.查看坐标【群成员总数：聊天信息(】
        Integer groupTotalNum = 100;
        try {
            WebElement groupTotalNumWebElement = driver.findElementByAndroidUIAutomator("new UiSelector().textStartsWith(\"" + groupTotalNumLocation + "\")");
            String text = groupTotalNumWebElement.getAttribute("text");
            String groupTotalNumStr = ((text.split("\\("))[1].split("\\)"))[0];
            groupTotalNum = Integer.parseInt(groupTotalNumStr);
            sw.split();
            logger.info("【查看当前群的成员数量】成功，总共花费 " + sw.toSplitString() + " 秒....");
            Thread.sleep(1000);
        } catch (Exception e) {
            this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
            sw.split();
            throw new Exception("长按坐标【右上角的三个点】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因，总共花费 " + sw.toSplitString() + " 秒....");
        }
        Integer theAddNum = 1;         //添加过的好友数量
        if (groupTotalNum >= 40) {     //当群成员超过40人事，才会出现【查看全部群成员】
            //7.点击坐标【上滑同时检测坐标查看全部群成员】并点击
            int i = 1;
            while (i <= 10) {
                try {
                    Thread.sleep(1000);
                    logger.info("第【" + i + "】次，scroll下滑中寻找坐标【查看全部群成员】...");
                    driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().className(\"android.widget.ListView\").scrollable(true)).scrollForward()");
                } catch (Exception e) {
                    logger.info("scroll下滑中寻找坐标【查看全部群成员】...");
                }
                try {
                    Thread.sleep(1000);
                    driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + checkAllGroupMembers + "\")").click();
                    Thread.sleep(1000);
                    sw.split();
                    logger.info("点击坐标【查看全部群成员】成功，总共花费 " + sw.toSplitString() + " 秒....");
                    break;
                } catch (Exception e) {
                    this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
                    sw.split();
                    logger.info("当前为群成员界面，正在往上滑动，寻找坐标【查看全部群成员】");
                }
                i++;
            }
            //8.下滑，回到群成员的顶部
            try {
                driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).flingToBeginning(5)");
                logger.info("scroll!");
            } catch (Exception e) {
                logger.info("【上滑】已滑到群成员的顶部，总共花费 " + sw.toSplitString() + " 秒....");
            }
            sw.split();
            logger.info("点击坐标【下滑】已滑到群成员的顶部成功，总共花费 " + sw.toSplitString() + " 秒....");
            //9.遍历群成员，并将其保存 groupMembersMap，如果groupMembersMap中有数据，则在循环遍历一次，就当查缺补漏
            if (groupMembersMap.size() != groupTotalNum) {
                int cyclesNumber = 1;       //循环下拉的次数, 默认超过30次
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
                                if (!groupMembersMap.containsKey(groupMemberNickName)) {

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
                                logger.info("当前不是群成员头像坐标，总共花费 " + sw.toSplitString() + " 秒....");
                            }
                        }
                        if (cyclesNumber >= 30) {           //当循环下拉的次数超过30次时，则强制终止循环，主要是群成员中存在昵称相同的人，导致groupMembersMap无法添加进去，导致数量达不到真实的群成员数量
                            //停止循环
                            logger.info("已滑到群成员的底部...");
                            break;
                        } else {
                            if (groupMembersMap.size() >= groupTotalNum) {        //当群成员数量全部统计完成时就跳出循环
                                //停止循环
                                logger.info("已滑到群成员的底部...");
                                break;
                            } else {                        //下滑，继续循环
                                try {
                                    logger.info("第【" + cyclesNumber + "】次，scroll上滑中,查看更过群成员，直到groupMembersMap的大小达到" + groupTotalNum + "，当前groupMembersMap的大小为：" + groupMembersMap.size() + "...");
                                    driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollForward()");
                                } catch (Exception e) {
                                    sw.split();
                                    logger.info("scroll上滑中,查看更过群成员，总共花费 " + sw.toSplitString() + " 秒....");
                                }
                                cyclesNumber++;
                            }
                        }
                    }
                }
                for (String key : groupMembersMap.keySet()) {
                    System.out.println(groupMembersMap.get(key).get("groupMemberIndex") + " ---->>> " + key + " ---->>> " + JSON.toJSONString(groupMembersMap.get(key)));
                }
                sw.split();
                logger.info("点击坐标【上滑同时检测坐标查看全部群成员】成功，总共花费 " + sw.toSplitString() + " 秒....");
                Thread.sleep(1000);
            }
            //10.循环点击群成员
            Integer addFriendNum = 1;
            for (String key : groupMembersMap.keySet()) {
                Map<String, String> groupMember = groupMembersMap.get(key);
                String isAddFlag = groupMember.get("isAddFlag");
                String groupMemberNickName = groupMember.get("groupMemberNickName");
//                if (!groupMemberNickName.startsWith("刚刚好")) {
//                    continue;
//                }
                if ("false".equals(isAddFlag)) {
                    try {
                        theAddNum++;
                        try {
                            driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + "搜索" + "\")").sendKeys(groupMemberNickName);
                            sw.split();
                            logger.info("点击坐标【搜索:text/搜索：" + groupMemberNickName + "】成功，总共花费 " + sw.toSplitString() + " 秒....");
                        } catch (Exception e) {
                            sw.split();
                            logger.info("点击坐标【搜索:text/搜索：" + groupMemberNickName + "】失败，总共花费 " + sw.toSplitString() + " 秒....");
                            try {
                                driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.EditText\")").sendKeys(groupMemberNickName);
                                sw.split();
                                logger.info("点击坐标【搜索:className/android.widget.EditText：" + groupMemberNickName + "】成功，总共花费 " + sw.toSplitString() + " 秒....");
                            } catch (Exception e1) {
                                sw.split();
                                logger.info("点击坐标【搜索:className/android.widget.EditText：" + groupMemberNickName + "】失败，总共花费 " + sw.toSplitString() + " 秒....");
                                throw new Exception("点击坐标【搜索:text/搜索】与【搜索:className/android.widget.EditText】均失败");
                            }
                        }
                        Thread.sleep(1500);
                        List<WebElement> linearWebElementList = Lists.newArrayList();
                        try {
                            WebElement gridWebElement = driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.GridView\")");
                            if (gridWebElement != null) {
                                sw.split();
                                logger.info("检测坐标【android.widget.GridView】成功，获取昵称对应的群成员列表信息，总共花费 " + sw.toSplitString() + " 秒....");
                                Thread.sleep(1000);
                                try {
                                    linearWebElementList = gridWebElement.findElements(By.className("android.widget.TextView"));       //获取昵称对应的群成员列表信息
                                    if (linearWebElementList != null) {
                                        sw.split();
                                        logger.info("检测坐标【android.widget.TextView】成功，获取昵称对应的群成员列表信息，共【" + linearWebElementList.size() + "】个，总共花费 " + sw.toSplitString() + " 秒....");
                                        Thread.sleep(1000);
                                    } else {
                                        sw.split();
                                        logger.info("检测坐标【android.widget.TextView】成功，获取昵称对应的群成员列表信息，共【" + linearWebElementList.size() + "】个，总共花费 " + sw.toSplitString() + " 秒....");
                                    }
                                } catch (Exception e) {
                                    sw.split();
                                    logger.info("检测坐标【android.widget.TextView】失败，获取昵称对应的群成员列表信息，总共花费 " + sw.toSplitString() + " 秒....");
                                }
                            }
                        } catch (Exception e) {
                            sw.split();
                            logger.info("检测坐标【android.widget.GridView】失败，获取昵称对应的群成员列表信息，总共花费 " + sw.toSplitString() + " 秒....");
                        }
                        if (linearWebElementList != null && linearWebElementList.size() > 0) {
                            for (WebElement webElement : linearWebElementList) {            //准备点击群成员头像，开始点击
                                try {
                                    //11.点击【单个群成员】
                                    String groupMemberNickName_Temp = webElement.getAttribute("text");
                                    if (!groupMemberNickName_Temp.equals(groupMemberNickName)) {      //防止一个短昵称对应多个群成员
                                        sw.split();
                                        logger.info("未发现昵称对应的群成员【" + groupMemberNickName + "】，当前昵称【" + groupMemberNickName_Temp + "】，总共花费 " + sw.toSplitString() + " 秒....");
                                        continue;
                                    } else {
                                        sw.split();
                                        logger.info("找到昵称对应的群成员【" + groupMemberNickName + "】准备添加为好友，总共花费 " + sw.toSplitString() + " 秒....");
                                    }
                                    webElement.click();
                                    sw.split();
                                    logger.info("准备开始_点击群成员【" + groupMemberNickName + "】准备添加为好友，总共花费 " + sw.toSplitString() + " 秒....");
                                    Thread.sleep(4000);
                                    //12.检测坐标【发消息】
                                    try {
                                        WebElement sendMessageBtn_Element = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + sendMessageBtnLocaltion + "\")");
                                        if (sendMessageBtn_Element != null) {
                                            sw.split();
                                            logger.info("检测坐标【发消息】成功，您与【" + groupMemberNickName + "】已是好友，则直接下一个群成员坐标，总共花费 " + sw.toSplitString() + " 秒....");
                                            Thread.sleep(1000);
                                            driver.pressKeyCode(AndroidKeyCode.BACK);                   //返回【群成员界面】
                                            Thread.sleep(4000);
                                            groupMember.put("isAddFlag", "true");
                                            continue;
                                        }
                                    } catch (Exception e) {
                                        sw.split();
                                        logger.info("检测坐标【发消息】时异常，可能是没有找到【发消息】按钮，总共花费 " + sw.toSplitString() + " 秒....");
                                    }
                                    //13.点击坐标【添加到通讯录】
                                    try {
                                        WebElement aadToContactBook_Element = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + aadToContactBookLocaltion + "\")");
                                        aadToContactBook_Element.click();
                                        sw.split();
                                        logger.info("点击坐标【添加到通讯录】成功，总共花费 " + sw.toSplitString() + " 秒....");
                                        Thread.sleep(6000);
                                    } catch (Exception e) {
                                        sw.split();
                                        logger.info("点击坐标【添加到通讯录】时异常，可能是没有找到【添加到通讯录】按钮，总共花费 " + sw.toSplitString() + " 秒....");
                                    }

                                    //13.1检测坐标【发消息】,点击坐标【添加到通讯录】直接被添加为好友，则检测坐标【发消息】         @TODO 可进行循环检测
                                    try {
                                        WebElement sendMessageBtn_Element = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + sendMessageBtnLocaltion + "\")");
                                        if (sendMessageBtn_Element != null) {
                                            sw.split();
                                            logger.info("点击坐标【添加到通讯录】后，检测坐标【发消息】成功，在点击坐标【添加到通讯录】直接被对方【" + groupMemberNickName + "】通过为好友（即通知发送成功），则直接下一个群成员坐标，总共花费 " + sw.toSplitString() + " 秒....");
                                            Thread.sleep(1000);
                                            driver.pressKeyCode(AndroidKeyCode.BACK);                   //返回【群成员界面】
                                            Thread.sleep(4000);
                                            groupMember.put("isAddFlag", "true");
                                            addFriendNum++;
                                            continue;
                                        }
                                    } catch (Exception e) {
                                        sw.split();
                                        logger.info("点击坐标【添加到通讯录】后，检测坐标【发消息】时异常，当前用户没有在点击坐标【添加到通讯录】直接添加为好友，总共花费 " + sw.toSplitString() + " 秒....");
                                    }

                                    //13.2.显示内容【由于对方的隐私设置，你无法通过群聊将其添加至通讯录。】，注：如果这个坐标找不到则使用【确定】这个坐标 privacyContentLocaltion
                                    try {
                                        WebElement privacyContent_Element = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + privacyContentLocaltion + "\")");
                                        if (privacyContent_Element != null) {
                                            sw.split();
                                            logger.info("点击坐标【添加到通讯录】后，显示内容【由于对方的隐私设置，你无法通过群聊将其添加至通讯录】成功，直接下一个群成员坐标，总共花费 " + sw.toSplitString() + " 秒....");
                                            Thread.sleep(1000);
                                            driver.pressKeyCode(AndroidKeyCode.BACK);                   //返回【弹窗】
                                            Thread.sleep(4000);
                                            driver.pressKeyCode(AndroidKeyCode.BACK);                   //返回【群成员界面】
                                            Thread.sleep(4000);
                                            groupMember.put("isAddFlag", "true");
                                            continue;
                                        }
                                    } catch (Exception e) {
                                        try {
                                            WebElement privacyContent_Element = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + "确定" + "\")");
                                            if (privacyContent_Element != null) {
                                                sw.split();
                                                logger.info("点击坐标【添加到通讯录】后，显示内容【由于对方的隐私设置，你无法通过群聊将其添加至通讯录】成功，直接下一个群成员坐标，总共花费 " + sw.toSplitString() + " 秒....");
                                                Thread.sleep(1000);
                                                driver.pressKeyCode(AndroidKeyCode.BACK);                   //返回【弹窗】
                                                Thread.sleep(4000);
                                                driver.pressKeyCode(AndroidKeyCode.BACK);                   //返回【群成员界面】
                                                Thread.sleep(4000);
                                                groupMember.put("isAddFlag", "true");
                                                continue;
                                            }
                                        } catch (Exception e1) {
                                            sw.split();
                                            logger.info("点击坐标【添加到通讯录】后，【未】显示内容【由于对方的隐私设置，你无法通过群聊将其添加至通讯录】，可能是没有找到【由于对方的隐私设置，你无法通过群聊将其添加至通讯录】，总共花费 " + sw.toSplitString() + " 秒....");
                                        }
                                    }

                                    //14.点击坐标【发送】
                                    try {
                                        WebElement sendBtn_Element = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + sendBtnLocaltion + "\")");
                                        sendBtn_Element.click();
                                        sw.split();
                                        logger.info("点击坐标【发送】成功，总共花费 " + sw.toSplitString() + " 秒....");
                                        Thread.sleep(5000);
                                    } catch (Exception e) {
                                        sw.split();
                                        logger.info("点击坐标【发送】时异常，可能出现了【对方帐号异常，无法添加朋友。】或者【由于对方的隐私设置，你无法通过群聊将其添加至通讯录】，总共花费 " + sw.toSplitString() + " 秒....");
                                    }

                                    //15.检测坐标【添加到通讯录】
                                    try {
                                        WebElement aadToContactBook_Element = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + aadToContactBookLocaltion + "\")");
                                        groupMember.put("isAddFlag", "true");
                                        addFriendNum++;
                                        sw.split();
                                        logger.info("点击坐标【发送】后，检测坐标【添加到通讯录】成功，当前请求好友【" + groupMemberNickName + "】通知发送成功，总共花费 " + sw.toSplitString() + " 秒....");
                                    } catch (Exception e) {
                                        sw.split();
                                        logger.info("点击坐标【发送】后，检测坐标【添加到通讯录】时异常，可能是当前用户【" + groupMemberNickName + "】在发送阶段才显示【对方账号异常，无法添加朋友。】，总共花费 " + sw.toSplitString() + " 秒....");
                                        groupMember.put("isAddFlag", "true");
                                        driver.pressKeyCode(AndroidKeyCode.BACK);                   //返回【群成员界面】
                                        Thread.sleep(4000);
                                        logger.info("返回【群成员简介】成功....");
                                    }
                                    Thread.sleep(1000);
                                    driver.pressKeyCode(AndroidKeyCode.BACK);                   //返回【群成员界面】
                                    Thread.sleep(4000);
                                    sw.split();
                                    logger.info("返回【群成员界面】成功，总共花费 " + " 秒....");
                                } catch (Exception e) {
                                    logger.info("点击群成员【" + groupMemberNickName + "】准备添加为好友时异常，有可能一个昵称对应多个群成员，在循环遍历过程中WebElement超时了，无法定位点击，总共花费 " + sw.toSplitString() + " 秒....，e ：", e);
                                }
                                break;
                            }
                            //昵称在当前群中匹配太多群成员，可能命中的群成员在下拉框下面，导致坐标未被识别，即【当前昵称在此群中对应的群成员太多无法快速定位】
                            if(!"true".equals(groupMember.get("isAddFlag"))){
                                groupMember.put("isAddFlag", "true");           //根据群昵称找不到群成员的，则默认为已添加过
                                sw.split();
                                logger.info("未发现群成员用户【" + groupMemberNickName + "】，当前昵称在此群中对应的群成员太多无法快速定位，直接下一个，总共花费 " + sw.toSplitString() + " 秒....");
                            }
                        } else {
                            groupMember.put("isAddFlag", "true");           //根据群昵称找不到群成员的，则默认为已添加过
                            sw.split();
                            logger.info("未发现群成员用户【" + groupMemberNickName + "】，直接下一个，总共花费 " + sw.toSplitString() + " 秒....");
                        }
                    } catch (Exception e) {
                        sw.split();
                        logger.info("在搜索框输入【" + groupMemberNickName + "】查找群成员时异常，总共花费 " + sw.toSplitString() + " 秒....，e ：", e);
                    } finally {
                        try {
                            Thread.sleep(2000);
                            try {
                                driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + groupMemberNickName + "\")").clear();
                                sw.split();
                                logger.info("点击坐标【搜索:text/" + groupMemberNickName + "】清空【群成员搜索框】成功，总共花费 " + sw.toSplitString() + " 秒....");
                            } catch (Exception e) {
                                sw.split();
                                logger.info("点击坐标【搜索:text/" + groupMemberNickName + "】清空【群成员搜索框】失败，总共花费 " + sw.toSplitString() + " 秒....");
                                try {
                                    driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.EditText\")").clear();
                                    sw.split();
                                    logger.info("点击坐标【搜索:className/android.widget.EditText】清空【群成员搜索框】成功，总共花费 " + sw.toSplitString() + " 秒....");
                                } catch (Exception e1) {
                                    sw.split();
                                    logger.info("点击坐标【搜索:className/android.widget.EditText】清空【群成员搜索框】失败，总共花费 " + sw.toSplitString() + " 秒....");
                                    throw new Exception("点击坐标【搜索:text/" + groupMemberNickName + "】与【搜索:className/android.widget.EditText】清空【群成员搜索框】均失败");
                                }
                            }
                        } catch (Exception e) {
                            sw.split();
                            logger.info("在搜索框输入【" + groupMemberNickName + "】清空时，准备进入下一个群成员时异常，" + sw.toSplitString() + " 秒....，总共花费 e ：", e);
                            driver.pressKeyCode(AndroidKeyCode.BACK);                   //返回【群成员界面】
                            sw.split();
                            logger.info("返回【群成员界面】成功，总共花费 " + sw.toSplitString() + " 秒....");
                            try {
                                Thread.sleep(1000);
                                driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + checkAllGroupMembers + "\")").click();
                                Thread.sleep(1000);
                                sw.split();
                                logger.info("点击坐标【查看全部群成员】成功，总共花费 " + sw.toSplitString() + " 秒....");
                                break;
                            } catch (Exception e1) {
                                this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
                                sw.split();
                                logger.info("当前为群成员界面，正在往上滑动，寻找坐标【查看全部群成员】，总共花费 " + sw.toSplitString() + " 秒....");
                            }
                        }
                        if (addFriendNum > addFrirndTotalNum) {
                            break;
                        }
                    }
                }
            }
            System.out.println(JSON.toJSON(groupMembersMap));
        } else {
            sw.split();
            logger.info("群成员未超过40人，则不添加当前群的成员为好友，总共花费 " + sw.toSplitString() + " 秒....");
        }
        paramMap.put("groupMembersMapStr", JSON.toJSONString(groupMembersMap));
        paramMap.put("addGroupMembersFlag", "true");        //当前设备已经添加过好友的标志位
        //16.退出驱动
        this.quitDriver(driver, deviceNameDesc, deviceName);
        sw.split();
        logger.info("设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】 添加好友【" + theAddNum + "】个发送成功，总共花费 " + sw.toSplitString() + " 秒....");
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
//            Thread.sleep(1000);
//            if (driver != null) {
//                driver.quit();
//            }
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
//            Thread.sleep(1000);
//            if (driver != null) {
//                driver.quit();
//            }
//            try {
//                //关闭 appium 相关进程
//                CommandUtil.run("/opt/android_sdk/platform-tools/adb -s " + deviceName + " shell am force-stop io.appium.settings");
//                CommandUtil.run("/opt/android_sdk/platform-tools/adb -s " + deviceName + " shell am force-stop io.appium.uiautomator2.server");
//                CommandUtil.run("/opt/android_sdk/platform-tools/adb -s " + deviceName + " shell am force-stop io.appium.uiautomator2.test");
//                //重启android设备
//                Thread.sleep(2000);
//                CommandUtil.run("/opt/android_sdk/platform-tools/adb -s " + deviceName + " reboot");
//                logger.info("重启成功，设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】");
//            } catch (Exception e1) {
//                logger.info("重启失败，设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.info("退出driver异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的连接等原因");
//            try {
//                //重启android设备
//                Thread.sleep(2000);
//                CommandUtil.run("/opt/android_sdk/platform-tools/adb -s " + deviceName + " reboot");
//                logger.info("重启成功，设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】");
//            } catch (Exception e1) {
//                logger.info("重启失败，设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】");
//            }
//        }
    }

    public static void main(String[] args) {
        try {
//            StopWatch sw = new StopWatch();
//            sw.start();
//            Map<String, Object> paramMap = Maps.newHashMap();
//            paramMap.put("nickName", "油站科技-内部交流群");
//            paramMap.put("nickName", "铜仁推广商务群");
//            paramMap.put("nickName", "求职信息发布群");
//            paramMap.put("nickName", "铜仁市本地生活便利群_1");
//            paramMap.put("action", "addGroupMembersAsFriends");
//            paramMap.put("deviceName", "5LM0216122009385");
//            paramMap.put("deviceNameDesc", "华为 Mate 8 _ 6");
//            new RealMachineDevices().addGroupMembersAsFriends(paramMap, sw);
//            Thread.sleep(5000);

            String str = "\"【坐车】️\uD83D\uDE97 人找车\\\\n\",\n" +
                    "\"【出发】✈️ 贵阳 \\\\n\",\n" +
                    "\"【到达】\uD83C\uDFC1 石阡\\\\n\",\n" +
                    "\"【时间】\uD83D\uDD51 11月06日\\\\n\",\n" +
                    "\"【电话】\uD83D\uDCF1 13096869668（点击联系）\\\\n\",\n" +
                    "\"【备注】\uD83D\uDCDD 司机厚道，车内干净舒适，车辆舒适，无异味即可，安全第一，诚信第一，鸽子勿扰，顺带大小物件.\\\\n\",\n" +
                    "\"【详情如下】↓↓↓↓↓↓↓↓↓↓↓↓\\\\n\"";
            System.out.println(EmojiUtil.emojiConvert(str));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

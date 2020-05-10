package com.oilStationMap.utils.wxAdAutomation.addGroupMembersAsFriends;

import com.google.common.collect.Maps;
import com.oilStationMap.utils.CommandUtil;
import com.oilStationMap.utils.appiumUtil.ScrollUtil;
import com.oilStationMap.utils.appiumUtil.SwipeUtil;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.lang.time.StopWatch;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.time.Duration;
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
                        "内部交流群";
        //坐标:搜索框
        String searchLocaltionStr =
                paramMap.get("searchLocaltion") != null ?
                        paramMap.get("searchLocaltion").toString() :
                        "com.tencent.mm:id/r_";
        //坐标:搜索输入框
        String searchInputLocaltion =
                paramMap.get("searchInputLocaltion") != null ?
                        paramMap.get("searchInputLocaltion").toString() :
                        "搜索";
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
            desiredCapabilities.setCapability("uiautomator2ServerInstallTimeout", 10000);               //等待uiAutomator2服务安装的超时时间，单位毫秒
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
            e.printStackTrace();
            this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
            throw new Exception("配置连接android驱动出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的环境是否正常运行等原因，总共花费 " + sw.toSplitString() + " 秒....");
        }
        //2.点击坐标【搜索框】
        try {
            driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"" + searchLocaltionStr + "\")").click();
            sw.split();
            logger.info("点击坐标【搜索框】成功，总共花费 " + sw.toSplitString() + " 秒....");
            Thread.sleep(1000);
        } catch (Exception e) {
            sw.split();
            e.printStackTrace();
            this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
            throw new Exception("长按坐标【搜索】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因，总共花费 " + sw.toSplitString() + " 秒....");
        }
        //3.点击坐标【搜索输入框】
        try {
            driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + searchInputLocaltion + "\")").sendKeys(nickName);
            sw.split();
            logger.info("点击坐标【输入昵称到搜索框】成功，总共花费 " + sw.toSplitString() + " 秒....");
            Thread.sleep(1000);
        } catch (Exception e) {
            sw.split();
            e.printStackTrace();
            this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
            throw new Exception("长按坐标【输入昵称到搜索框】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因，总共花费 " + sw.toSplitString() + " 秒....");
        }
        //4.点击坐标【昵称对应的微信好友/群】
        try {
            List<WebElement> targetGroupElementList = driver.findElementsByAndroidUIAutomator("new UiSelector().text(\"" + nickName + "\")");
            for (WebElement targetGroupElement : targetGroupElementList) {
                if ("android.widget.TextView".equals(targetGroupElement.getAttribute("class"))) {
                    targetGroupElement.click();
                }
            }
            sw.split();
            logger.info("点击坐标【昵称对应的微信好友群】成功，总共花费 " + sw.toSplitString() + " 秒....");
            Thread.sleep(1000);
        } catch (Exception e) {
            sw.split();
            e.printStackTrace();
            this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
            throw new Exception("长按坐标【昵称对应的微信好友群】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因，总共花费 " + sw.toSplitString() + " 秒....");
        }
        //5.点击坐标【右上角的三个点】
        try {
            driver.findElementByAndroidUIAutomator("new UiSelector().description(\"" + "聊天信息" + "\")").click();
            sw.split();
            logger.info("点击坐标【右上角的三个点】成功，总共花费 " + sw.toSplitString() + " 秒....");
            Thread.sleep(1000);
        } catch (Exception e) {
            sw.split();
            e.printStackTrace();
            this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
            throw new Exception("长按坐标【右上角的三个点】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因，总共花费 " + sw.toSplitString() + " 秒....");
        }
        //6.查看当前群的成员数量
        Integer groupNum = 100;
        try {
            WebElement groupNumWebElement = driver.findElementByAndroidUIAutomator("new UiSelector().textStartsWith(\"" + "聊天信息(" + "\")");
            String text = groupNumWebElement.getAttribute("text");
            String groupNumStr = ((text.split("\\("))[1].split("\\)"))[0];
            groupNum = Integer.parseInt(groupNumStr);
            sw.split();
            logger.info("【查看当前群的成员数量】成功，总共花费 " + sw.toSplitString() + " 秒....");
            Thread.sleep(1000);
        } catch (Exception e) {
            sw.split();
            e.printStackTrace();
            this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
            throw new Exception("长按坐标【右上角的三个点】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因，总共花费 " + sw.toSplitString() + " 秒....");
        }
        if(groupNum >= 40){     //当群成员超过40人事，才会出现【查看全部群成员】
            //7.点击坐标【上滑同时检测坐标查看全部群成员】并点击
            try {
                int x0 = 0; int y0 = 202;
                int x1 = 0; int y1 = 492;
                WebElement listWebElement = driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"" + "android:id/list" + "\")");
                List<WebElement> linearWebElement = listWebElement.findElements(By.className("android.widget.LinearLayout"));
                if(linearWebElement != null && linearWebElement.size() > 0){
                    String bounds = linearWebElement.get(0).getAttribute("bounds");
                    String[] boundsArr = bounds.split("\\]\\[");
                    String[] x0y0Arr = boundsArr[0].split(",");
                    String x0Str = x0y0Arr[0].replace("[","");
                    x0 = Integer.parseInt(x0Str);
                    y0 = Integer.parseInt(x0y0Arr[1]);
                    String[] x1y1Arr = boundsArr[1].split(",");
                    String y1Str = x1y1Arr[1].replace("]","");
                    x1 = Integer.parseInt(x1y1Arr[0]);
                    x1 = x0;
                    y1 = Integer.parseInt(y1Str);
                    System.out.println("bounds = " + bounds);
                    System.out.println("x0 = " + x0 + " , y0 = " + y0 + " , x1 = " + x1 + " , y1 = " + y1);
                } else {
                    x0 = 0; y0 = 202;
                    x1 = 0; y1 = 492;
                }
                int i = 0;
                while(i <= 10){
                    new TouchAction(driver).longPress(PointOption.point(x1, y1))
                            .moveTo(PointOption.point(x0, y0)).release().perform();
                    Thread.sleep(1000);
                    try {
                        driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + "查看全部群成员" + "\")").click();
                        break;
                    } catch (Exception e) {
                        logger.info("当前为群简介页面，正在往上滑动，寻找坐标【查看全部群成员】");
                    }
                    i++;
                }
                sw.split();
                logger.info("点击坐标【上滑同时检测坐标查看全部群成员】成功，总共花费 " + sw.toSplitString() + " 秒....");
                Thread.sleep(1000);
            } catch (Exception e) {
                sw.split();
                e.printStackTrace();
                this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
                throw new Exception("长按坐标【上滑同时检测坐标查看全部群成员】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因，总共花费 " + sw.toSplitString() + " 秒....");
            }
            //8.下滑，回到群成员的顶部
            try{
                driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).flingToBeginning(5)");
                logger.info("scroll!");
            } catch (Exception e) {
                logger.info("【下滑】已滑到群成员的顶部...");
            }
            sw.split();
            logger.info("点击坐标【下滑】已滑到群成员的顶部成功，总共花费 " + sw.toSplitString() + " 秒....");
            //8.根据每个好友头像的坐标index进行判断该添加第几个好友进行添加
            Integer startNum = 40;
            try {
                WebElement gridWebElement = driver.findElementByAndroidUIAutomator("new UiSelector().className(\"" + "android.widget.GridView" + "\")");
                List<WebElement> linearWebElementList = gridWebElement.findElements(By.className("android.widget.LinearLayout"));
                if(linearWebElementList != null && linearWebElementList.size() > 0){
                    Integer index = 0;
                    String indexStr = "0";
//                    //判断最后一个元素是否在startNum范围内，如在在之内则下滑，反之亦然
//                    WebElement maxNumWebElement = linearWebElementList.get(linearWebElementList.size()-1);
//                    String indexStr = maxNumWebElement.getAttribute("index")!=null?maxNumWebElement.getAttribute("index").toString():"0";
//                    Integer index = Integer.parseInt(indexStr);
//                    if(index <= startNum){
//                        //下滑，继续循环
//                        try{
//                            driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollForward(5)");
//                            logger.info("scroll!");
//                        } catch (Exception e) {
//                            logger.info("下滑】已滑到群成员的顶部...");
//                        }
//
//
////                        continue;
//                    } else {
                        index = 0;
                        indexStr = "0";
                        for(WebElement webElement : linearWebElementList){
                            try {
//                                indexStr = webElement.getAttribute("index")!=null?webElement.getAttribute("index").toString():"0";
//                                index = Integer.parseInt(indexStr);
//                                if(index >= startNum){      //开始点击群成员头像
//
//                                } else {
//                                    //下滑，继续循环
//                                }
                                //点击头像进入群成员详情


                                System.out.println("text = " + webElement.findElement(By.className("android.widget.TextView")).getAttribute("text"));
//                                System.out.println("index = " + webElement.getAttribute("index"));
                                System.out.println("class = " + webElement.getAttribute("class"));
                                System.out.println();
                                System.out.println();
                            } catch (Exception e) {
                                logger.info("当前不是群成员头像坐标....");
                            }
                        }
//                    }
                }
                sw.split();
                logger.info("点击坐标【上滑同时检测坐标查看全部群成员】成功，总共花费 " + sw.toSplitString() + " 秒....");
                Thread.sleep(1000);
            } catch (Exception e) {
                sw.split();
                e.printStackTrace();
                this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
                throw new Exception("长按坐标【上滑同时检测坐标查看全部群成员】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因，总共花费 " + sw.toSplitString() + " 秒....");
            }
        }




//        //9.沉睡5秒，等待加载用户信息，主要是为了显示坐标【添加到通讯录】
//        Thread.sleep(5000);
//        //10.点击坐标【添加到通讯录】
//        WebElement canemerElement = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + "添加到通讯录" + "\")");
//            //11.直接添加为好友，如果当前坐标存在坐标【发消息】，注：对方默认已添加为好友
//            WebElement canemerElement = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + "发消息" + "\")");
//            //12.点击坐标【左上角的返回箭头】
//            WebElement canemerElement = driver.findElementByAndroidUIAutomator("new UiSelector().description(\"" + "返回" + "\")");
//            //13.循环8步骤的坐标
//
//            //11.显示内容【由于对方的隐私设置，你无法通过群聊将其添加至通讯录】
//            WebElement canemerElement = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + "由于对方的隐私设置，你无法通过群聊将其添加至通讯录。" + "\")");
//            //12.点击坐标【确定】
//            WebElement canemerElement = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + "确定" + "\")");
//            //13.点击坐标【左上角的返回箭头】
//            WebElement canemerElement = driver.findElementByAndroidUIAutomator("new UiSelector().description(\"" + "返回" + "\")");
//            //14.循环8步骤的坐标
//
//            //11.显示账号异常
//            //14.3.点击坐标【左上角的返回箭头】
//            WebElement canemerElement = driver.findElementByAndroidUIAutomator("new UiSelector().description(\"" + "返回" + "\")");
//            //14.4.点击坐标【左上角的返回箭头】
//            WebElement canemerElement = driver.findElementByAndroidUIAutomator("new UiSelector().description(\"" + "返回" + "\")");
//            //14.5.循环8步骤的坐标
//
//            //11.沉睡5秒，等待加载用户信息，注：需要发起申请
//            Thread.sleep(5000);
//            //12.点击坐标【好友申请内容】输入申请信息，比如：来自通讯录
//            //获取第一个坐标的输入框
//            List<WebElement> canemerElement = driver.findElementByAndroidUIAutomator("new UiSelector().className(\"" + "android.widget.EditText" + "\")");
//            //13.点击坐标【发送】
//            WebElement canemerElement = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + "发送" + "\")");
//                //14.1.如果返回的页面存在坐标【添加到通讯录】则发送申请成功
//                WebElement canemerElement = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + "添加到通讯录" + "\")");
//                //14.2.点击坐标【左上角的返回箭头】
//                WebElement canemerElement = driver.findElementByAndroidUIAutomator("new UiSelector().description(\"" + "返回" + "\")");
//                //14.3.循环8步骤的坐标
//
//                //14.1.显示账号异常
//                //14.2.点击确定
//                //14.3.点击坐标【左上角的返回箭头】
//                WebElement canemerElement = driver.findElementByAndroidUIAutomator("new UiSelector().description(\"" + "返回" + "\")");
//                //14.4.点击坐标【左上角的返回箭头】
//                WebElement canemerElement = driver.findElementByAndroidUIAutomator("new UiSelector().description(\"" + "返回" + "\")");
//                //14.5.循环8步骤的坐标





        //5.退出驱动
//        this.quitDriver(driver, deviceNameDesc, deviceName);
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
            paramMap.put("nickName", "内部交流群");
            paramMap.put("nickName", "孟溪镇预防接种交流群");
            paramMap.put("action", "addGroupMembersAsFriends");
            new RealMachineDevices().addGroupMembersAsFriends(paramMap, sw);
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

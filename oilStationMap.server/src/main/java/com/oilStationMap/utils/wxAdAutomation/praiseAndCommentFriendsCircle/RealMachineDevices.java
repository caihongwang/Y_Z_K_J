package com.oilStationMap.utils.wxAdAutomation.praiseAndCommentFriendsCircle;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * 真机设备 点赞和评论朋友圈 策略
 * 默认 华为 Mate 8
 */
public class RealMachineDevices implements PraiseAndCommentFriendsCircle {

    public static final Logger logger = LoggerFactory.getLogger(RealMachineDevices.class);

    /**
     * 点赞和评论朋友圈
     *
     * @param paramMap
     * @throws Exception
     */
    @Override
    public boolean praiseAndCommentFriendsCircle(Map<String, Object> paramMap) throws Exception {
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
        //昵称对象
        String nickName =
                paramMap.get("nickName") != null ?
                        paramMap.get("nickName").toString() :
                        "广告";
        //评论内容
        String commentContent =
                paramMap.get("commentContent") != null ?
                        paramMap.get("commentContent").toString() :
                        "好高级啊，羡慕....";
        //对所有好友点赞或评论时，滑动朋友圈的次数
        String allSwipeNumStr =
                paramMap.get("allSwipeNum") != null ?
                        paramMap.get("allSwipeNum").toString() :
                        "20";
        int allSwipeNum = 20;
        try {
            allSwipeNum = Integer.parseInt(allSwipeNumStr);
        } catch (Exception e) {
            allSwipeNum = 20;
        }
        //操作:纯文字朋友圈和图片文字朋友圈
        String action =
                paramMap.get("action") != null ?
                        paramMap.get("action").toString() :
                        "praiseAndCommentFriendsCircle";
        //坐标:发现
        String findBtnLocaltion =
                paramMap.get("findBtnLocaltion") != null ?
                        paramMap.get("findBtnLocaltion").toString() :
                        "发现";
        //坐标:朋友圈
        String friendCircleBtnLocation =
                paramMap.get("friendCircleBtnLocation") != null ?
                        paramMap.get("friendCircleBtnLocation").toString() :
                        "朋友圈";
        //坐标:评论
        String commentToPointLocation =
                paramMap.get("commentToPointLocation") != null ?
                        paramMap.get("commentToPointLocation").toString() :
                        "评论";
        //坐标:照片或视频
        String picOrVedioLocation =
                paramMap.get("picOrVedioLocation") != null ?
                        paramMap.get("picOrVedioLocation").toString() :
                        "照片或视频";
        //坐标:取消
        String cameraCancelLocation =
                paramMap.get("cameraCancelLocation") != null ?
                        paramMap.get("cameraCancelLocation").toString() :
                        "取消";
        //坐标:赞
        String praiseLocation =
                paramMap.get("praiseLocation") != null ?
                        paramMap.get("praiseLocation").toString() :
                        "赞";
        //坐标:取消
        String cancelLocation =
                paramMap.get("cancelLocation") != null ?
                        paramMap.get("cancelLocation").toString() :
                        "取消";
        //坐标:评论
        String commentLocation =
                paramMap.get("commentLocation") != null ?
                        paramMap.get("commentLocation").toString() :
                        "评论";
        //坐标:android.widget.EditText
        String commentInputLocation =
                paramMap.get("commentInputLocation") != null ?
                        paramMap.get("commentInputLocation").toString() :
                        "android.widget.EditText";
        //坐标:发送
        String sendLocation =
                paramMap.get("sendLocation") != null ?
                        paramMap.get("sendLocation").toString() :
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
            desiredCapabilities.setCapability("newCommandTimeout", 15);                                 //在下一个命令执行之前的等待最大时长,单位为秒
            desiredCapabilities.setCapability("deviceReadyTimeout", 30);                                //等待设备就绪的时间,单位为秒
            desiredCapabilities.setCapability("uiautomator2ServerLaunchTimeout", 10000);                //等待uiAutomator2服务启动的超时时间，单位毫秒
            desiredCapabilities.setCapability("uiautomator2ServerInstallTimeout", 20000);               //等待uiAutomator2服务安装的超时时间，单位毫秒
            desiredCapabilities.setCapability("androidDeviceReadyTimeout", 30);                         //等待设备在启动应用后超时时间，单位秒
            desiredCapabilities.setCapability("autoAcceptAlerts", true);                                //默认选择接受弹窗的条款，有些app启动的时候，会有一些权限的弹窗
            desiredCapabilities.setCapability("waitForSelectorTimeout", 10000);
            URL remoteUrl = new URL("http://localhost:" + appiumPort + "/wd/hub");                            //连接本地的appium
            driver = new AndroidDriver(remoteUrl, desiredCapabilities);
            logger.info("【点赞和评论朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】连接Appium【" + appiumPort + "】成功....");
            Thread.sleep(10000);                                                                     //加载安卓页面10秒,保证xml树完全加载
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("【点赞和评论朋友圈】配置连接android驱动出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】Appium端口号【" + appiumPort + "】的环境是否正常运行等原因....");
        }
        //2.点击坐标【发现】
        try {
            driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + findBtnLocaltion + "\")").click();
            logger.info("【点赞和评论朋友圈】点击坐标【发现】成功....");
            Thread.sleep(1000);
        } catch (Exception e) {
            //2.1 点击坐标【发现】【xpath定位】
            try {
                driver.findElementByXPath("//com.tencent.mm.ui.mogic.WxViewPager/../android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.RelativeLayout[3]").click();
                logger.info("【点赞和评论朋友圈】点击坐标【发现】【xpath定位】成功....");
                Thread.sleep(1000);
            } catch (Exception e1) {
                e.printStackTrace();
                throw new Exception("【点赞和评论朋友圈】点击坐标【发现】与【发现】【xpath定位】均出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
            }
        }
        //3.点击坐标【朋友圈】
        try {
            driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + friendCircleBtnLocation + "\")").click();
            logger.info("【点赞和评论朋友圈】点击坐标【朋友圈】成功....");
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("【点赞和评论朋友圈】点击坐标【朋友圈】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
        }
        if ("所有".equals(nickName)) {
//            try {
//                driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollToEnd(10)");
//                logger.info("【点赞和评论朋友圈】屏幕先向上滚动10次，成功....");
//            } catch (Exception e) {
//                logger.info("scroll上滑中，10次....");
//            }
            for (int i = 0; i < allSwipeNum; i++) {
                logger.info("【点赞和评论朋友圈】第【" + i + "】次上滑朋友圈...");
                List<WebElement> commentToPointElementList = Lists.newLinkedList();
                try {
                    //获取当前屏幕所有坐标【评论两个点】
                    commentToPointElementList = driver.findElementsByAndroidUIAutomator("new UiSelector().description(\"" + commentToPointLocation + "\")");
                    logger.info("【点赞和评论朋友圈】获取当前屏幕所有坐标【评论两个点】成功....");
                } catch (Exception e) {
                    logger.info("【点赞和评论朋友圈】获取当前屏幕所有坐标【评论两个点】失败....");
                    continue;
                }
                //循环操作所有的坐标【评论两个点】
                for (int j = 0; j < commentToPointElementList.size(); j++) {
                    if (j == 0) {     //第一个评论不操作，容易误触坐标【返回】和坐标【相机】
                        continue;
                    }
                    WebElement commentToPointElement = commentToPointElementList.get(j);
//                    WebElement commentToPointElement = driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollForward().scrollIntoView(new UiSelector().description(\"" + commentToPointLocation + "\"))");
                    //点击坐标【评论两个点】，随后出现【赞】与【评论】
                    try {
                        commentToPointElement.click();
                        logger.info("【点赞和评论朋友圈】点击坐标【评论两个点】成功....");
                    } catch (Exception e) {
                        logger.info("【点赞和评论朋友圈】点击坐标【评论两个点】失败....");
                        continue;
                    } finally {
                        boolean picOrVedio_2_friendCircle_Flag = false;
                        //在屏幕的滑动过程中坐标【评论两个点】正好滚动到【相机】后面，从而误触点击【相机】
                        try {
                            driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + picOrVedioLocation + "\")");
                            logger.info("【点赞和评论朋友圈】检测坐标【照片或视频】成功，原因：在屏幕的滑动或者输入法弹出导致的滑动过程中坐标【评论两个点】正好滚动到【相机】后面，从而误触点击，继续下一个【评论两个点】....");
                            driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + cameraCancelLocation + "\")").click();
                            logger.info("【点赞和评论朋友圈】点击坐标【取消】成功....");
                            picOrVedio_2_friendCircle_Flag = true;
                        } catch (Exception e) {

                        }
                        //在屏幕的滑动过程中坐标【评论两个点】正好滚动到【相机】后面，从而误触点击【返回】退回到【发现】页面
                        try {
                            driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + findBtnLocaltion + "\")");
                            driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + "直播和附近" + "\")");
                            logger.info("【点赞和评论朋友圈】检测坐标【发现】与【直播和附近】成功，原因：在屏幕的滑动或者输入法弹出导致的滑动过程中坐标【评论两个点】正好滚动到【相机】后面，从而误触点击，继续下一个【评论两个点】....");
                            driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + friendCircleBtnLocation + "\")").click();
                            logger.info("【点赞和评论朋友圈】点击坐标【朋友圈】成功....");
                            picOrVedio_2_friendCircle_Flag = true;
                        } catch (Exception e) {

                        }
                        if (picOrVedio_2_friendCircle_Flag) {
                            continue;
                        }
                    }
                    //点击坐标【赞】
                    try {
                        driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + praiseLocation + "\")").click();
                        logger.info("【点赞和评论朋友圈】点击坐标【赞】成功....");
                        Thread.sleep(1500);
                    } catch (Exception e) {
                        try {
                            driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + cancelLocation + "\")");
                            logger.info("【点赞和评论朋友圈】检测坐标【赞】的【取消】成功，即已经点过赞了，继续下一步评论....");
                            Thread.sleep(1500);
                        } catch (Exception e1) {
                            logger.info("【点赞和评论朋友圈】点击坐标【赞】的【取消】失败，继续滚动再寻找....");
                            continue;
                        }
                    }
                    //点击坐标【评论两个点】，随后出现【赞】与【评论】
                    try {
                        commentToPointElement.click();
                        logger.info("【点赞和评论朋友圈】点击坐标【评论两个点】成功....");
                    } catch (Exception e) {
                        logger.info("【点赞和评论朋友圈】点击坐标【评论两个点】失败....");
                        continue;
                    } finally {
                        boolean picOrVedio_2_friendCircle_Flag = false;
                        //在屏幕的滑动过程中坐标【评论两个点】正好滚动到【相机】后面，从而误触点击【相机】
                        try {
                            driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + picOrVedioLocation + "\")");
                            logger.info("【点赞和评论朋友圈】检测坐标【照片或视频】成功，原因：在屏幕的滑动或者输入法弹出导致的滑动过程中坐标【评论两个点】正好滚动到【相机】后面，从而误触点击，继续下一个【评论两个点】....");
                            driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + cameraCancelLocation + "\")").click();
                            logger.info("【点赞和评论朋友圈】点击坐标【取消】成功....");
                            picOrVedio_2_friendCircle_Flag = true;
                        } catch (Exception e) {

                        }
                        //在屏幕的滑动过程中坐标【评论两个点】正好滚动到【相机】后面，从而误触点击【返回】退回到【发现】页面
                        try {
                            driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + findBtnLocaltion + "\")");
                            driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + "直播和附近" + "\")");
                            logger.info("【点赞和评论朋友圈】检测坐标【发现】与【直播和附近】成功，原因：在屏幕的滑动或者输入法弹出导致的滑动过程中坐标【评论两个点】正好滚动到【相机】后面，从而误触点击，继续下一个【评论两个点】....");
                            driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + friendCircleBtnLocation + "\")").click();
                            logger.info("【点赞和评论朋友圈】点击坐标【朋友圈】成功....");
                            picOrVedio_2_friendCircle_Flag = true;
                        } catch (Exception e) {

                        }
                        if (picOrVedio_2_friendCircle_Flag) {
                            continue;
                        }
                    }
                    //点击坐标【评论】
                    try {
                        driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + commentLocation + "\")").click();
                        logger.info("【点赞和评论朋友圈】点击坐标【评论】成功....");
                        Thread.sleep(1500);
                    } catch (Exception e) {
                        logger.info("【点赞和评论朋友圈】点击坐标【评论】失败，继续滚动再寻找....");
                        continue;
                    }
                    //点坐坐标【评论输入框】，并输入评论内容
                    try {
                        driver.findElementByAndroidUIAutomator("new UiSelector().className(\"" + commentInputLocation + "\")").sendKeys(commentContent);
                        logger.info("【点赞和评论朋友圈】点击坐标【评论输入框】成功....");
                        Thread.sleep(1500);
                    } catch (Exception e) {
                        logger.info("【点赞和评论朋友圈】点击坐标【评论】失败，继续滚动再寻找....");
                        continue;
                    }
                    //点击坐标【发送】
                    try {
                        driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + sendLocation + "\")").click();
                        logger.info("【点赞和评论朋友圈】点击坐标【发送】成功....");
                        Thread.sleep(1500);
                    } catch (Exception e) {
                        logger.info("【点赞和评论朋友圈】点击坐标【发送】失败，继续滚动再寻找....");
                        continue;
                    } finally {
//                        try {
//                            //获取当前屏幕所有坐标【评论两个点】
//                            commentToPointElementList.clear();
//                            commentToPointElementList = driver.findElementsByAndroidUIAutomator("new UiSelector().description(\"" + commentToPointLocation + "\")");
//                            logger.info("【点赞和评论朋友圈】获取当前屏幕所有坐标【评论两个点】成功....");
//                        } catch (Exception e) {
//                            logger.info("【点赞和评论朋友圈】获取当前屏幕所有坐标【评论两个点】失败....");
//                            continue;
//                        }
                    }
                    System.out.println(">>>>>>>>>>>>>>>>>下一个【赞】与【评论】<<<<<<<<<<<<<<<<<<");
                }
                //屏幕滚动到下一屏
                try {
                    driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollForward()");
                } catch (Exception e) {
                    logger.info("【点赞和评论朋友圈】scroll上滑中，检测当前朋友圈页面【评论两个点】....");
                }
            }
        } else {
            for (int i = 0; i < 2; i++) {
                //上滑至坐标【广告/指定昵称】
                try {
                    WebElement ad_WebElement = driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollForward().scrollIntoView(new UiSelector().text(\"" + nickName + "\"))");
                    logger.info("【点赞和评论朋友圈】上滑至坐标【" + nickName + "】成功....");
                    Thread.sleep(1500);
                } catch (Exception e) {
                    logger.info("【点赞和评论朋友圈】上滑至坐标【" + nickName + "】失败，继续滚动再寻找....");
                }
                //点击坐标【评论两个点】，xpath定位，随后出现【赞】与【评论】
                try {
                    if ("广告".equals(nickName)) {
                        driver.findElementByXPath("//android.widget.TextView[@text=\"" + nickName + "\"]/../../../android.widget.FrameLayout/android.widget.ImageView[@content-desc='评论']").click();
                    } else {
                        driver.findElementByXPath("//android.widget.TextView[@text=\"" + nickName + "\"]/../../android.widget.FrameLayout/android.widget.ImageView[@content-desc='评论']").click();
                    }
                    logger.info("【点赞和评论朋友圈】点击坐标【评论两个点】成功....");
                    Thread.sleep(1500);
                } catch (Exception e) {
                    logger.info("【点赞和评论朋友圈】点击坐标【评论两个点】失败，继续滚动再寻找....");
                    continue;
                }
                //点击坐标【赞】
                try {
                    driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + praiseLocation + "\")").click();
                    logger.info("【点赞和评论朋友圈】点击坐标【赞】成功....");
                    Thread.sleep(1500);
                } catch (Exception e) {
                    try {
                        driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + cancelLocation + "\")");
                        logger.info("【点赞和评论朋友圈】检测坐标【赞】的【取消】成功，即已经点过赞了，继续下一步评论....");
                        Thread.sleep(1500);
                    } catch (Exception e1) {
                        logger.info("【点赞和评论朋友圈】点击坐标【赞】的【取消】失败，继续滚动再寻找....");
                        continue;
                    }
                }
                //点击坐标【评论两个点】，xpath定位，随后出现【赞】与【评论】
                try {
                    if ("广告".equals(nickName)) {
                        driver.findElementByXPath("//android.widget.TextView[@text=\"" + nickName + "\"]/../../../android.widget.FrameLayout/android.widget.ImageView[@content-desc='评论']").click();
                    } else {
                        driver.findElementByXPath("//android.widget.TextView[@text=\"" + nickName + "\"]/../../android.widget.FrameLayout/android.widget.ImageView[@content-desc='评论']").click();
                    }
                    logger.info("【点赞和评论朋友圈】点击坐标【评论两个点】成功....");
                    Thread.sleep(1500);
                } catch (Exception e) {
                    logger.info("【点赞和评论朋友圈】点击坐标【评论两个点】失败，继续滚动再寻找....");
                    continue;
                }
                //点击坐标【评论】
                try {
                    driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + commentLocation + "\")").click();
                    logger.info("【点赞和评论朋友圈】点击坐标【评论】成功....");
                    Thread.sleep(1500);
                } catch (Exception e) {
                    logger.info("【点赞和评论朋友圈】点击坐标【评论】失败，继续滚动再寻找....");
                    continue;
                }
                //点坐坐标【评论输入框】，并输入评论内容
                try {
                    driver.findElementByAndroidUIAutomator("new UiSelector().className(\"" + commentInputLocation + "\")").sendKeys(commentContent);
                    logger.info("【点赞和评论朋友圈】点击坐标【评论输入框】成功....");
                    Thread.sleep(1500);
                } catch (Exception e) {
                    logger.info("【点赞和评论朋友圈】点击坐标【评论】失败，继续滚动再寻找....");
                    continue;
                }
                //点击坐标【发送】
                try {
                    driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + sendLocation + "\")").click();
                    logger.info("【点赞和评论朋友圈】点击坐标【发送】成功....");
                    Thread.sleep(1500);
                } catch (Exception e) {
                    logger.info("【点赞和评论朋友圈】点击坐标【发送】失败，继续滚动再寻找....");
                    continue;
                }
            }
        }
        logger.info("【点赞和评论朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】 发送成功....");
        return true;
    }

    public static void main(String[] args) {
        try {
            Map<String, Object> paramMap = Maps.newHashMap();
//            paramMap.put("nickName", "汽车原装皮套批发");
//            paramMap.put("nickName", "广告");
            paramMap.put("nickName", "所有");
            paramMap.put("allSwipeNum", "20");
            paramMap.put("commentContent", "看着好高级啊，真棒...");
            new RealMachineDevices().praiseAndCommentFriendsCircle(paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}



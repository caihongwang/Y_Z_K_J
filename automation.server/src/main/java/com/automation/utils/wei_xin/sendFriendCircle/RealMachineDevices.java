package com.automation.utils.wei_xin.sendFriendCircle;

import com.google.common.collect.Maps;
import com.automation.utils.CommandUtil;
import com.automation.utils.EmojiUtil;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * 真机设备 发布朋友圈 策略
 * 默认 华为 Mate 8
 */
public class RealMachineDevices implements SendFriendCircle {

    public static final Logger logger = LoggerFactory.getLogger(RealMachineDevices.class);

    /**
     * 发送朋友圈
     *
     * @param paramMap
     * @throws Exception
     */
    @Override
    public boolean sendFriendCircle(Map<String, Object> paramMap) throws Exception {
        //0.获取参数
        //手机的图片地址
        String phoneLocalPath =
                paramMap.get("phoneLocalPath") != null ?
                        paramMap.get("phoneLocalPath").toString() :
                        "/storage/emulated/0/tencent/MicroMsg/WeiXin/";
        //设备编码
        String deviceName =
                paramMap.get("deviceName") != null ?
                        paramMap.get("deviceName").toString() :
                        "9f4eda95";
        //设备描述
        String deviceNameDesc =
                paramMap.get("deviceNameDesc") != null ?
                        paramMap.get("deviceNameDesc").toString() :
                        "小米Max3_08";
        //appium端口号
        String appiumPort =
                paramMap.get("appiumPort") != null ?
                        paramMap.get("appiumPort").toString() :
                        "4723";
        //操作:纯文字朋友圈和图片文字朋友圈
        String action =
                paramMap.get("action") != null ?
                        paramMap.get("action").toString() :
                        "textMessageFriendCircle";
        //朋友圈文本内容
        String textMessage =
                paramMap.get("textMessage") != null ?
                        paramMap.get("textMessage").toString() :
                        "选择有效的推广方式更为重要![闪电][闪电]早上第一件事干什么？刷微信；上班忙里偷闲干什么？刷微信；中午吃饭你还在干什么？刷微信；晚上回家干什么？刷微信；睡觉前最一件事干什么？还是刷微信。现在是微信时代，还在担心人脉不多知名度低？交给我们一切就是这么简单[拳头][拥抱][拥抱]";
        textMessage = EmojiUtil.emojiRecovery(textMessage);
        //图片文件路径，总路径+微信昵称
        String imgDirPath =
                paramMap.get("imgDirPath") != null ?
                        paramMap.get("imgDirPath").toString() :
                        "/opt/nextcloud/铜仁市碧江区智惠加油站科技服务工作室/微信朋友圈广告业务";
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
        //坐标:相机
        String cameraLocaltion =
                paramMap.get("cameraLocaltion") != null ?
                        paramMap.get("cameraLocaltion").toString() :
                        "拍照分享";
        //坐标:输入分享文本框
        String textInputLocaltion =
                paramMap.get("textInputLocaltion") != null ?
                        paramMap.get("textInputLocaltion").toString() :
                        "这一刻的想法...";
        //坐标:发表
        String publishBtnLocaltion =
                paramMap.get("publishBtnLocaltion") != null ?
                        paramMap.get("publishBtnLocaltion").toString() :
                        "发表";
        //坐标：从相册选择
        String selectFromPhotosBtnLocaltion =
                paramMap.get("selectFromPhotosBtnLocaltion") != null ?
                        paramMap.get("selectFromPhotosBtnLocaltion").toString() :
                        "从相册选择";
        //坐标：单选框
        String singlePhotoLocaltion =
                paramMap.get("singlePhotoLocaltion") != null ?
                        paramMap.get("singlePhotoLocaltion").toString() :
                        "android.widget.CheckBox";
        //坐标：完成
        String completeBtnLocaltion =
                paramMap.get("completeBtnLocaltion") != null ?
                        paramMap.get("completeBtnLocaltion").toString() :
                        "完成";
        Integer imageNum = 0;
        if (action.equals("imgMessageFriendCircle")) {
            imgDirPath = imgDirPath + "/" + paramMap.get("nickName");
            File imgDir = new File(imgDirPath);
            File[] imgFiles = imgDir.listFiles();
            if ("今日油价".equals(imgDir.getName())) {
                imageNum = 1;
            } else {
                Integer tempNum = 0;
                for (int i = 0; i < imgFiles.length; i++) {
                    File imgFile = imgFiles[i];
                    if (imgFile.getName().startsWith(".")) {          //过滤部分操作系统的隐藏文件
                        tempNum++;
                        continue;
                    }
                }
                imageNum = imgFiles.length - tempNum;
            }
        }

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
            logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】连接Appium【" + appiumPort + "】成功....");
            Thread.sleep(10000);                                                                     //加载安卓页面10秒,保证xml树完全加载
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】配置连接android驱动出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】Appium端口号【" + appiumPort + "】的环境是否正常运行等原因....");
        }
        //2.点击坐标【发现】   启动【微信】在老旧设备会花费很长时间，在此循环5此点击【发现】坐标.
        int findNum = 0;       //循环下拉的次数
        int maxFindNum = 5;       //默认超过30次
        boolean isFindBtnLocaltionFlag = false;
        while (true) {
            try {
                driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + findBtnLocaltion + "\")").click();
                logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + findNum + "】次点击坐标【发现】成功....");
                isFindBtnLocaltionFlag = true;
                Thread.sleep(1000);
            } catch (Exception e) {
                //2.1 点击坐标【发现】【xpath定位】
                try {
                    driver.findElementByXPath("//com.tencent.mm.ui.mogic.WxViewPager/../android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.RelativeLayout[3]").click();
                    logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + findNum + "】次点击坐标【发现】【xpath定位】成功....");
                    isFindBtnLocaltionFlag = true;
                    Thread.sleep(1000);
                } catch (Exception e1) {
//                    e.printStackTrace();
//                    throw new Exception("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【发现】与【发现】【xpath定位】均出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
                }
            } finally {
                findNum++;
                if (isFindBtnLocaltionFlag) {
                    break;
                }
                Thread.sleep(5000);
                logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】第【" + findNum + "】次点击坐标【发现】失败....");
                if (findNum > maxFindNum) {
                    throw new Exception("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【发现】与【发现】【xpath定位】均出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
                }
            }
        }
        //3.点击坐标【朋友圈】
        try {
            driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + friendCircleBtnLocation + "\")").click();
            logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【朋友圈】成功....");
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【朋友圈】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
        }
        //4.具体操作
        if (action.equals("textMessageFriendCircle")) {             //文字信息朋友圈
            //5.1.点击坐标【相机】
            try {
                WebElement canemerElement = driver.findElementByAndroidUIAutomator("new UiSelector().description(\"" + cameraLocaltion + "\")");
                Duration duration = Duration.ofMillis(2000);
                new TouchAction(driver).press(canemerElement).waitAction(WaitOptions.waitOptions(duration)).release().perform();
                logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【相机】成功....");
                Thread.sleep(1500);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【相机】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
            }
            //5.2【我知道了】
            try {
                WebElement knowElement = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + "我知道了" + "\")");
                if (knowElement != null) {
                    knowElement.click();
                    logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测坐标【我知道了】成功....");
                    Thread.sleep(1500);
                }
                logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测坐标【我知道了】成功....");
            } catch (Exception e) {
                logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测坐标【我知道了】已经点击过了....");
            }
            //5.3.输入分享文本框
            try {
                driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + textInputLocaltion + "\")").sendKeys(textMessage);
                logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【输入文字】成功....");
                Thread.sleep(2000);
            } catch (Exception e) {
                try{
                    driver.findElementByAndroidUIAutomator("new UiSelector().className(\"" + "android.widget.EditText" + "\")").clear();
                    logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【输入文字】【清空】成功....");
                    driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + textInputLocaltion + "\")").sendKeys(textMessage);
                    logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【输入文字】成功....");
                } catch (Exception e1) {
                    e1.printStackTrace();
                    throw new Exception("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】输入文字出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
                }
            }
            //5.4.点击坐标【发表】
            try {
                driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + publishBtnLocaltion + "\")").click();
                logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【发表】成功....");
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【发表】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
            }
        } else if (action.equals("imgMessageFriendCircle")) {        //图片信息朋友圈
            //5.1.点击坐标【相机】
            try {
                driver.findElementByAndroidUIAutomator("new UiSelector().description(\"" + cameraLocaltion + "\")").click();
                logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【相机】成功....");
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【相机】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
            }
            //检测是否直接进入了上一次的朋友圈草稿
            boolean isLastDraft = false;        //是否为上一次未发成功的朋友圈超高
            try{
                driver.findElementByAndroidUIAutomator("new UiSelector().className(\"" + "android.widget.EditText" + "\")");
                logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测坐标【android.widget.EditText】成功,当前【是】上一次的朋友圈草稿....");
                isLastDraft = true;
            } catch (Exception e) {
                logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测坐标【android.widget.EditText】失败，当前【不是】上一次的朋友圈草稿....");
                isLastDraft = false;
            }
            if(!isLastDraft){       //当前【不是】上一次的朋友圈草稿,进入相册，点击图片
                //5.2.点击坐标【从相册选择】
                try {
                    driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + selectFromPhotosBtnLocaltion + "\")").click();
                    logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【从相册选择】成功....");
                    Thread.sleep(5000);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【从相册选择】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
                }
                //5.3.点击坐标【我知道了】
                try {
                    WebElement knowElement = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + "我知道了" + "\")");
                    if (knowElement != null) {
                        knowElement.click();
                        logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测坐标【我知道了】成功....");
                        Thread.sleep(1500);
                    }
                    logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测坐标【我知道了】成功....");
                } catch (Exception e) {
                    logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】检测坐标【我知道了】已经点击过了....");
                }
                //5.4.点击坐标【从相册的左上角开始计数，数字代表第几个图片，勾选】,此处存在耗费超长时间的应还
                try {
                    List<WebElement> photoElementList = driver.findElementsByAndroidUIAutomator("new UiSelector().className(\"" + singlePhotoLocaltion + "\")");
                    if (photoElementList != null && photoElementList.size() > 0) {
                        for (int i = 0; i < photoElementList.size(); i++) {
                            if (i < imageNum) {
                                WebElement photoElement = photoElementList.get(i);
                                photoElement.click();
                                logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标选择第" + i + "张图片....");
                            }
                        }
                    } else {
                        for (int j = 1; j <= 100; j++) {
                            String refreshCommandStr = "";
                            for (int i = 1; i <= imageNum; i++) {
                                try {
                                    refreshCommandStr = "/opt/android_sdk/platform-tools/adb -s " + deviceName + " shell am broadcast -a android.intent.action.MEDIA_SCANNER_SCAN_FILE -d file://" + phoneLocalPath + i + ".jpg";
                                    CommandUtil.run(refreshCommandStr);
                                } catch (Exception e) {
                                    logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【选择图片】失败，第【" + j + "】次更新【jpg】图片失败，即将重启..... , refreshCommandStr = " + refreshCommandStr + " , e : ", e);
                                }
                                try {
                                    refreshCommandStr = "/opt/android_sdk/platform-tools/adb -s " + deviceName + " shell am broadcast -a android.intent.action.MEDIA_SCANNER_SCAN_FILE -d file://" + phoneLocalPath + i + ".jpeg";
                                } catch (Exception e) {
                                    logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【选择图片】失败，第【" + j + "】次更新【jpeg】图片失败，即将重启..... , refreshCommandStr = " + refreshCommandStr + " , e : ", e);
                                }
                                logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【选择图片】失败，第【" + j + "】次更新图片成功，即将重启..... ");
                            }
                        }
                        //此处可以沉睡2秒等待处理更新图片通知
                        Thread.sleep(2000);
                        logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【选择图片】失败，图片还没有更新到相册里面来，已发送消息通知更新，即将重启.....");
                        logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【选择图片】失败，图片还没有更新到相册里面来，已发送消息通知更新，即将重启.....");
                        logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【选择图片】失败，图片还没有更新到相册里面来，已发送消息通知更新，即将重启.....");
                    }
                    logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【选择图片】成功....");
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【选择图片】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
                }
                //5.5.点击坐标【完成】
                try {
                    driver.findElementByAndroidUIAutomator("new UiSelector().textContains(\"" + completeBtnLocaltion + "\")").click();
                    logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【完成】成功....");
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【完成】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
                }
            }
            //5.6.点击【输入分享文本框】
            try {
                driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + textInputLocaltion + "\")").sendKeys(textMessage);
                logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【输入文字】成功....");
                Thread.sleep(2000);
            } catch (Exception e) {
                try{
                    driver.findElementByAndroidUIAutomator("new UiSelector().className(\"" + "android.widget.EditText" + "\")").clear();
                    logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【输入文字】【清空】成功....");
                    driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + textInputLocaltion + "\")").sendKeys(textMessage);
                    logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【输入文字】成功....");
                } catch (Exception e1) {
                    e1.printStackTrace();
                    throw new Exception("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】输入文字出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
                }
            }
            //5.7.点击坐标【发表】
            try {
                driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + publishBtnLocaltion + "\")").click();
                logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【发表】成功....");
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】点击坐标【输入文字】出现异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因....");
            }
        }
        logger.info("【发送朋友圈】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】操作【" + action + "】 发送成功....");
        return true;
    }

    public static void main(String[] args) {
        try {
            Map<String, Object> paramMap = Maps.newHashMap();
//            paramMap.put("deviceName", "5LM0216122009385");
//            paramMap.put("deviceNameDesc", "华为 Mate 8 _ 6");
//            paramMap.put("action", "imgMessageFriendCircle");
            paramMap.put("action", "textMessageFriendCircle");
            new RealMachineDevices().sendFriendCircle(paramMap);
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



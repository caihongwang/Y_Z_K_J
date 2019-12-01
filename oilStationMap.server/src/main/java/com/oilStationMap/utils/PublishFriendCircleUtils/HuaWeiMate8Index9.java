package com.oilStationMap.utils.PublishFriendCircleUtils;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.time.Duration;
import java.util.Map;

/**
 * 华为 Mate 8 _ 9 发布朋友圈 策略
 */
public class HuaWeiMate8Index9 implements FriendCircleStraetge{

    public static final Logger logger = LoggerFactory.getLogger(HuaWeiMate8Index9.class);

    /**
     * 发送朋友圈
     * @param paramMap
     * @throws Exception
     */
    @Override
    public void sendFriendCircle(Map<String, Object> paramMap) throws Exception {
        //0.获取参数
        String action = paramMap.get("action")!=null?paramMap.get("action").toString():"textMessageFriendCircle";                       //操作:纯文字朋友圈和图片文字朋友圈
        String content = paramMap.get("content")!=null?paramMap.get("content").toString():"/玫瑰我们做的是广告，广告的目的是广而告之。 /微笑央视同样不保证效果，广告推广的意义就在于提高产品的知名度和覆盖面。/愉快推广面越广，覆盖人群越多，才越容易被接受。正规公司，全国统一价。 /勾引谈的是价值，不是价格。正品和高仿，您更愿意选择哪个？ /闪电不值得的花一分钱也是多， /闪电值得的一百万也值得。 /闪电 认准品牌， /闪电认准实力。/强 /强 /强 ";                     //朋友圈文本
        String photoNumStr = paramMap.get("photoNum")!=null?paramMap.get("photoNum").toString():"1";                     //朋友圈文本
        String deviceName = "QVM0216304003850";               //设备编码
        String deviceNameDesc = "华为 Mate 8 _ 9";       //设备描述
        //1.配置连接android驱动
        AndroidDriver driver = null;
        try{
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability("platformName", "Android");           //Android设备
            desiredCapabilities.setCapability("deviceName", deviceName);                   //设备
            desiredCapabilities.setCapability("udid", deviceName);                         //设备唯一标识
            desiredCapabilities.setCapability("appPackage", "com.tencent.mm");      //打开 微信
            desiredCapabilities.setCapability("appActivity", "ui.LauncherUI");      //首个 页面
            desiredCapabilities.setCapability("noReset", true);                     //不用重新安装APK
            desiredCapabilities.setCapability("sessionOverride", true);             //每次启动时覆盖session，否则第二次后运行会报错不能新建session
            desiredCapabilities.setCapability("automationName", "UiAutomator2");

            desiredCapabilities.setCapability("newCommandTimeout", 30);

            URL remoteUrl = new URL("http://localhost:"+4723+"/wd/hub");                                 //连接本地的appium
            driver = new AndroidDriver(remoteUrl, desiredCapabilities);
            logger.info("设备描述["+deviceNameDesc+"]设备编码[" + deviceName + "]启动链接成功....");
            Thread.sleep(10000);                                                                     //加载安卓页面10秒,保证xml树完全加载
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("配置连接android驱动出现异常,请检查设备描述["+deviceNameDesc+"]设备编码[" + deviceName + "]的环境是否正常运行等原因");
        }
        //2.根据设备编码和操作指令,获取对应的操作坐标,此坐标根据手机而异,当app更新或者升级等原因均会导致坐标紊乱.
        //Dao操作数据库,并整理坐标
        //Dao操作数据库,并整理坐标
        //Dao操作数据库,并整理坐标
        //Dao操作数据库,并整理坐标
        //Dao操作数据库,并整理坐标
        //Dao操作数据库,并整理坐标
        String findBtnLocaltion = "//android.widget.RelativeLayout[3]";        //坐标:发现
        String friendCircleBtnLocation = "//android.widget.LinearLayout[@resource-id='com.tencent.mm:id/aoi'][1]"; //坐标:朋友圈
        String cameraLocaltion = "//android.widget.ImageButton[@content-desc=\"拍照分享\"]";            //坐标:相机
        String textInputLocaltion = "//android.widget.EditText[@resource-id='com.tencent.mm:id/d41']";  //坐标:文本输入框
        String publishOrCompleteBtnLocaltion = "com.tencent.mm:id/ln";                   //坐标:发表/完成

        String selectFromPhotosBtnLocaltion = "//android.widget.LinearLayout[2]/android.widget.RelativeLayout/android.widget.TextView";     //坐标：从相册中选择
        String photoBtnPreLocation = "//android.widget.FrameLayout[2]/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.GridView/android.widget.RelativeLayout[";    //坐标前缀：相片前缀
        String photoBtnSufLocation = "]/android.widget.CheckBox";                                                                           ////坐标后缀：相片后缀
        Integer photoNum = Integer.parseInt(photoNumStr);

        //3.点击坐标[发现]
        try{
            driver.findElementByXPath(findBtnLocaltion).click();
            logger.info("点击坐标[发现]成功....");
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("点击坐标[发现]出现异常,请检查设备描述["+deviceNameDesc+"]设备编码[" + deviceName + "]的应用是否更新导致坐标变化等原因");
        }
        //4.点击坐标[朋友圈]
        try{
            driver.findElementByXPath(friendCircleBtnLocation).click();
            logger.info("点击坐标[朋友圈]成功....");
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("点击坐标[朋友圈]出现异常,请检查设备描述["+deviceNameDesc+"]设备编码[" + deviceName + "]的应用是否更新导致坐标变化等原因");
        }
        //5.具体操作
        if (action.equals("textMessageFriendCircle")) {             //文字信息朋友圈
            //5.1.长按坐标[相机]
            try{
                Duration duration = Duration.ofMillis(2000);
                new TouchAction(driver).press(driver.findElementByXPath(cameraLocaltion)).waitAction(WaitOptions.waitOptions(duration)).release().perform();
                logger.info("点击坐标[相机]成功....");
                Thread.sleep(1500);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("长按坐标[相机]出现异常,请检查设备描述["+deviceNameDesc+"]设备编码[" + deviceName + "]的应用是否更新导致坐标变化等原因");
            }
            //5.2.输入文本
            try{
                driver.findElementByXPath(textInputLocaltion).sendKeys(content);
                logger.info("点击坐标[输入文字]成功....");
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("输入文字出现异常,请检查设备描述["+deviceNameDesc+"]设备编码[" + deviceName + "]的应用是否更新导致坐标变化等原因");
            }
            //5.3.点击坐标[发表]
            try{
//                driver.findElementById(publishOrCompleteBtnLocaltion).click();
                logger.info("点击坐标[发表]成功....");
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("点击坐标[发表]出现异常,请检查设备描述["+deviceNameDesc+"]设备编码[" + deviceName + "]的应用是否更新导致坐标变化等原因");
            }
        } else if (action.equals("imgMessageFriendCircle")) {        //图片信息朋友圈
            //5.1.点击坐标[相机]
            try {
                driver.findElementByXPath(cameraLocaltion).click();
                logger.info("点击坐标[相机]成功....");
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("长按坐标[相机]出现异常,请检查设备描述["+deviceNameDesc+"]设备编码[" + deviceName + "]的应用是否更新导致坐标变化等原因");
            }
            //5.2.点击坐标[从相册选择]
            try {
                driver.findElementByXPath(selectFromPhotosBtnLocaltion).click();
                logger.info("点击坐标[从相册选择]成功....");
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("长按坐标[从相册选择]出现异常,请检查设备描述["+deviceNameDesc+"]设备编码[" + deviceName + "]的应用是否更新导致坐标变化等原因");
            }
            //5.3.点击坐标[从相册的左上角开始计数，数字代表第几个图片，勾选]
            for (int i = 1; i <= photoNum; i++) {
                try {
                    driver.findElementByXPath( photoBtnPreLocation+ i + photoBtnSufLocation).click();
                    Thread.sleep(1000);
                } catch (Exception e) {
                    photoNum++;
                    continue;
                }
            }
            logger.info("点击坐标[选择图片]成功....");
            //5.4.点击坐标[完成]
            try {
                driver.findElementById(publishOrCompleteBtnLocaltion).click();
                logger.info("点击坐标[完成]成功....");
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("长按坐标[完成]出现异常,请检查设备描述["+deviceNameDesc+"]设备编码[" + deviceName + "]的应用是否更新导致坐标变化等原因");
            }
            //5.5.点击[输入文字]
            try {
                driver.findElementByXPath(textInputLocaltion).sendKeys(content);
                logger.info("点击坐标[输入文字]成功....");
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("长按坐标[输入文字]出现异常,请检查设备描述["+deviceNameDesc+"]设备编码[" + deviceName + "]的应用是否更新导致坐标变化等原因");
            }
            //5.6.点击坐标[发布]
            try {
                driver.findElementById(publishOrCompleteBtnLocaltion).click();
                logger.info("点击坐标[发表]成功....");
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("长按坐标[输入文字]出现异常,请检查设备描述["+deviceNameDesc+"]设备编码[" + deviceName + "]的应用是否更新导致坐标变化等原因");
            }
        }
        try {
            Thread.sleep(5000);
            if(driver!=null){
                driver.quit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("退出driver异常,请检查设备描述["+deviceNameDesc+"]设备编码[" + deviceName + "]的连接等原因");
        }
        System.out.println( "设备描述["+deviceNameDesc+"]设备编码[" + deviceName + "]操作[" + action + "] 发送成功!!!");
    }
}

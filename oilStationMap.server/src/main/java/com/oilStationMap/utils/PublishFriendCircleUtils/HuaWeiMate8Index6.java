package com.oilStationMap.utils.PublishFriendCircleUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.oilStationMap.service.WX_DicService;
import com.oilStationMap.service.impl.WX_DicServiceImpl;
import com.oilStationMap.utils.ApplicationContextUtils;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 华为 Mate 8 _ 6 发布朋友圈 策略
 */
public class HuaWeiMate8Index6 implements FriendCircleStraetge{

    public static final Logger logger = LoggerFactory.getLogger(HuaWeiMate8Index6.class);

    /**
     * 发送朋友圈
     * @param paramMap
     * @throws Exception
     */
    @Override
    public void sendFriendCircle(Map<String, Object> paramMap) throws Exception {
        //0.获取参数
        String deviceName = "5LM0216122009385";               //设备编码
        String deviceNameDesc = "华为 Mate 8 _ 6";       //设备描述
        String action = paramMap.get("action")!=null?paramMap.get("action").toString():"textMessageFriendCircle";                  //操作:纯文字朋友圈和图片文字朋友圈
        String findBtnLocaltion = paramMap.get("findBtnLocaltion")!=null?paramMap.get("findBtnLocaltion").toString():"//android.widget.RelativeLayout[3]";        //坐标:发现
        String friendCircleBtnLocation = paramMap.get("friendCircleBtnLocation")!=null?paramMap.get("friendCircleBtnLocation").toString():"//android.widget.LinearLayout[@resource-id='com.tencent.mm:id/aoi'][1]"; //坐标:朋友圈
        String cameraLocaltion = paramMap.get("cameraLocaltion")!=null?paramMap.get("cameraLocaltion").toString():"//android.widget.ImageButton[@content-desc=\"拍照分享\"]";            //坐标:相机
        String textInputLocaltion = paramMap.get("textInputLocaltion")!=null?paramMap.get("textInputLocaltion").toString():"//android.widget.EditText[@resource-id='com.tencent.mm:id/d41']";  //坐标:文本输入框
        String publishOrCompleteBtnLocaltion = paramMap.get("publishOrCompleteBtnLocaltion")!=null?paramMap.get("publishOrCompleteBtnLocaltion").toString():"com.tencent.mm:id/ln";                   //坐标:发表/完成
        String textMessageUrl = paramMap.get("textMessageUrl")!=null?paramMap.get("textMessageUrl").toString():"http://192.168.43.181/owncloud/index.php/s/6Y0lVeKWCarVgCF/download?path=%2FAAA%2FtextMessage&files=textMessage.txt";      //朋友圈文本内容

        String selectFromPhotosBtnLocaltion = paramMap.get("selectFromPhotosBtnLocaltion")!=null?paramMap.get("selectFromPhotosBtnLocaltion").toString():"//android.widget.LinearLayout[2]/android.widget.RelativeLayout/android.widget.TextView";     //坐标：从相册中选择
        String photoBtnPreLocation = paramMap.get("photoBtnPreLocation")!=null?paramMap.get("photoBtnPreLocation").toString():"//android.widget.FrameLayout[2]/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.GridView/android.widget.RelativeLayout[";    //坐标前缀：相片前缀
        String photoBtnSufLocation = paramMap.get("photoBtnSufLocation")!=null?paramMap.get("photoBtnSufLocation").toString():"]/android.widget.CheckBox";                                                                           ////坐标后缀：相片后缀
        String phoneLocalPath = paramMap.get("phoneLocalPath")!=null?paramMap.get("phoneLocalPath").toString():"/storage/emulated/0/tencent/MicroMsg/WeiXin/";         //手机本地的微信图片路径
        String imgListStr = paramMap.get("imgList")!=null?paramMap.get("imgList").toString():"";
        List<String> imgList = Lists.newArrayList();
        Integer imageNum = 0;
        if(!"".equals(imgListStr)){
            imgList = JSONObject.parseObject(imgListStr, List.class);
            imageNum = imgList.size();
        } else {
            imgList = Lists.newArrayList();
            imageNum = 1;
        }
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
            URL remoteUrl = new URL("http://localhost:"+4723+"/wd/hub");                                 //连接本地的appium
            driver = new AndroidDriver(remoteUrl, desiredCapabilities);
            logger.info("设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】启动链接成功....");
            Thread.sleep(10000);                                                                     //加载安卓页面10秒,保证xml树完全加载
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("配置连接android驱动出现异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的环境是否正常运行等原因");
        }
        //2.将图片保存到【手机本地的微信图片路径】
        if(imgList != null && imgList.size() > 0){
            for(String imgPath : imgList){
                try {
                    //从本地获取
//                        File imgFile = new File(imgPath);
//                        FileInputStream imgInputFile = new FileInputStream(imgFile);
//                        byte【】 buffer = new byte【(int)imgFile.length()】;
//                        imgInputFile.read(buffer);
//                        imgInputFile.close();
//                        byte【】 imgData = new BASE64Encoder().encode(buffer).getBytes();
//                        String imgName = "/storage/emulated/0/tencent/MicroMsg/WeiXin/"+imgFile.getName();
//                        driver.pushFile(imgName, imgData);
//                        logger.info("远程图片保存到手机成功，imgPath = " + imgPath);
                    //从Url获取
                    URL imgUrl = new URL(imgPath);
                    URLConnection con = imgUrl.openConnection();
                    InputStream imgInputStream = con.getInputStream();
                    ByteArrayOutputStream imgOutStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = imgInputStream.read(buffer)) != -1) {
                        imgOutStream.write(buffer, 0, len);
                    }
                    byte[] imgData = new BASE64Encoder().encode(imgOutStream.toByteArray()).getBytes();
                    imgInputStream.close();
                    imgOutStream.close();
                    Date currentDate = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
                    String imgName = phoneLocalPath + formatter.format(currentDate) + ".jpg";
                    driver.pushFile(imgName, imgData);
                    logger.info("将图片保存到【手机本地的微信图片路径】成功，imgPath = " + imgPath);
                } catch (Exception e) {
                    logger.info("将图片保存到【手机本地的微信图片路径】失败，imgPath = " + imgPath);
                    continue;
                }
            }
        }
        logger.info("将图片保存到【手机本地的微信图片路径】成功....");
        //3.获取发送的朋友圈文字内容
        String textMessage = "";
        try {
            URL url = new URL(textMessageUrl);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(3*1000);
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            InputStream inputStream = conn.getInputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while((len = inputStream.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            bos.close();
            textMessage = new String(bos.toByteArray(),"utf-8");
            logger.info("【朋友圈文字内容】---->>> " + textMessage);
        } catch (Exception e) {
            logger.info("获取发送的朋友圈文字内容失败，textMessageUrl = " + textMessageUrl);
        }
        textMessage = (textMessage!=null||!"".equals(textMessage))?textMessage:"/玫瑰我们做的是广告，广告的目的是广而告之。 /微笑央视同样不保证效果，广告推广的意义就在于提高产品的知名度和覆盖面。/愉快推广面越广，覆盖人群越多，才越容易被接受。正规公司，全国统一价。 /勾引谈的是价值，不是价格。正品和高仿，您更愿意选择哪个？ /闪电不值得的花一分钱也是多， /闪电值得的一百万也值得。 /闪电 认准品牌， /闪电认准实力。/强 /强 /强 ";                     //朋友圈文本
        logger.info("获取发送【朋友圈文字内容】成功....");

        //4.点击坐标【发现】
        try{
            driver.findElementByXPath(findBtnLocaltion).click();
            logger.info("点击坐标【发现】成功....");
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("点击坐标【发现】出现异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因");
        }
        //5.点击坐标【朋友圈】
        try{
            driver.findElementByXPath(friendCircleBtnLocation).click();
            logger.info("点击坐标【朋友圈】成功....");
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("点击坐标【朋友圈】出现异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因");
        }
        //6.具体操作
        if (action.equals("textMessageFriendCircle")) {             //文字信息朋友圈
            //6.1.长按坐标【相机】
            try{
                Duration duration = Duration.ofMillis(2000);
                new TouchAction(driver).press(driver.findElementByXPath(cameraLocaltion)).waitAction(WaitOptions.waitOptions(duration)).release().perform();
                logger.info("点击坐标【相机】成功....");
                Thread.sleep(1500);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("长按坐标【相机】出现异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因");
            }
            //6.2.输入文本
            try{
                driver.findElementByXPath(textInputLocaltion).sendKeys(textMessage);
                logger.info("点击坐标【输入文字】成功....");
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("输入文字出现异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因");
            }
            //6.3.点击坐标【发表】
            try{
                driver.findElementById(publishOrCompleteBtnLocaltion).click();
                logger.info("点击坐标【发表】成功....");
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("点击坐标【发表】出现异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因");
            }
        } else if (action.equals("imgMessageFriendCircle")) {        //图片信息朋友圈
            //6.3.点击坐标【相机】
            try {
                driver.findElementByXPath(cameraLocaltion).click();
                logger.info("点击坐标【相机】成功....");
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("长按坐标【相机】出现异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因");
            }
            //6.4.点击坐标【从相册选择】
            try {
                driver.findElementByXPath(selectFromPhotosBtnLocaltion).click();
                logger.info("点击坐标【从相册选择】成功....");
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("长按坐标【从相册选择】出现异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因");
            }
            //6.5.点击坐标【从相册的左上角开始计数，数字代表第几个图片，勾选】
            for (int i = 1; i <= imageNum; i++) {
                try {
                    driver.findElementByXPath( photoBtnPreLocation+ i + photoBtnSufLocation).click();
                    Thread.sleep(1000);
                } catch (Exception e) {
                    imageNum++;
                    continue;
                }
            }
            logger.info("点击坐标【选择图片】成功....");
            //6.6.点击坐标【完成】
            try {
                driver.findElementById(publishOrCompleteBtnLocaltion).click();
                logger.info("点击坐标【完成】成功....");
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("长按坐标【完成】出现异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因");
            }
            //6.7.点击【输入文字】
            try {
                driver.findElementByXPath(textInputLocaltion).sendKeys(textMessage);
                logger.info("点击坐标【输入文字】成功....");
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("长按坐标【输入文字】出现异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因");
            }
            //6.8.点击坐标【发布】
            try {
                driver.findElementById(publishOrCompleteBtnLocaltion).click();
                logger.info("点击坐标【发表】成功....");
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("长按坐标【输入文字】出现异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因");
            }
        }
        try {
            Thread.sleep(5000);
            if(driver!=null){
                driver.quit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("退出driver异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的连接等原因");
        }
        System.out.println( "设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】操作【" + action + "】 发送成功!!!");
    }
}

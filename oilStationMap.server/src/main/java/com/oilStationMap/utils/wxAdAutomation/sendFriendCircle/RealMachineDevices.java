package com.oilStationMap.utils.wxAdAutomation.sendFriendCircle;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.oilStationMap.utils.CommandUtil;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 真机设备 发布朋友圈 策略
 * 默认 华为 Mate 8
 */
public class RealMachineDevices implements FriendCircleStraetge{

    public static final Logger logger = LoggerFactory.getLogger(RealMachineDevices.class);

    /**
     * 发送朋友圈
     * @param paramMap
     * @throws Exception
     */
    @Override
    public void sendFriendCircle(Map<String, Object> paramMap) throws Exception {
        //0.获取参数
        //设备编码
        String deviceName =
                paramMap.get("deviceName")!=null?
                        paramMap.get("deviceName").toString():
                                "5LM0216122009385";
        //设备描述
        String deviceNameDesc =
                paramMap.get("deviceNameDesc")!=null?
                        paramMap.get("deviceNameDesc").toString():
                                "华为 Mate 8 _ 6";
        //操作:纯文字朋友圈和图片文字朋友圈
        String action =
                paramMap.get("action")!=null?
                        paramMap.get("action").toString():
                                "textMessageFriendCircle";
        //坐标:发现
        String findBtnLocaltionStr =
                paramMap.get("findBtnLocaltion")!=null?
                        paramMap.get("findBtnLocaltion").toString():
                                "{\n" +
                                "        \"findBtnLocaltion_x1\":540,\n" +
                                "        \"findBtnLocaltion_y1\":1661,\n" +
                                "        \"findBtnLocaltion_x2\":810,\n" +
                                "        \"findBtnLocaltion_y2\":1812\n" +
                                "    }";
        Map<String, Integer> findBtnLocaltion = JSONObject.parseObject(findBtnLocaltionStr, Map.class);
        //坐标:朋友圈
        String friendCircleBtnLocationStr =
                paramMap.get("friendCircleBtnLocation")!=null?
                        paramMap.get("friendCircleBtnLocation").toString():
                                "{\n" +
                                "        \"friendCircleBtnLocation_x1\":0,\n" +
                                "        \"friendCircleBtnLocation_y1\":202,\n" +
                                "        \"friendCircleBtnLocation_x2\":1080,\n" +
                                "        \"friendCircleBtnLocation_y2\":354\n" +
                                "    }";
        Map<String, Integer> friendCircleBtnLocation = JSONObject.parseObject(friendCircleBtnLocationStr, Map.class);
        //坐标:相机
        String cameraLocaltionStr =
                paramMap.get("cameraLocaltion")!=null?
                        paramMap.get("cameraLocaltion").toString():
                                "{\n" +
                                "        \"cameraLocaltion_x1\":929,\n" +
                                "        \"cameraLocaltion_y1\":72,\n" +
                                "        \"cameraLocaltion_x2\":1080,\n" +
                                "        \"cameraLocaltion_y2\":202\n" +
                                "    }";
        Map<String, Integer> cameraLocaltion = JSONObject.parseObject(cameraLocaltionStr, Map.class);
        //朋友圈文本内容
//        String textMessageUrl =
//                paramMap.get("textMessageUrl")!=null?
//                        paramMap.get("textMessageUrl").toString():
//                                "http://192.168.43.181/owncloud/index.php/s/6Y0lVeKWCarVgCF/download?path=%2FAAA%2FtextMessage&files=textMessage.txt";
        String textMessage =
                paramMap.get("textMessage")!=null?
                        paramMap.get("textMessage").toString():
                                "选择有效的推广方式更为重要![闪电][闪电]早上第一件事干什么？刷微信；上班忙里偷闲干什么？刷微信；中午吃饭你还在干什么？刷微信；晚上回家干什么？刷微信；睡觉前最一件事干什么？还是刷微信。现在是微信时代，还在担心人脉不多知名度低？交给我们一切就是这么简单[拳头][拥抱][拥抱]";
        //坐标:文本输入框
        String textInputLocaltion =
                paramMap.get("textInputLocaltion")!=null?
                        paramMap.get("textInputLocaltion").toString():
                                "//android.widget.EditText[@resource-id='com.tencent.mm:id/d41']";
        //坐标:发表/完成
        String publishOrCompleteBtnLocaltion =
                paramMap.get("publishOrCompleteBtnLocaltion")!=null?
                        paramMap.get("publishOrCompleteBtnLocaltion").toString():
                                "com.tencent.mm:id/ln";
        //坐标：从相册中选择
        String selectFromPhotosBtnLocaltionStr =
                paramMap.get("selectFromPhotosBtnLocaltion")!=null?
                        paramMap.get("selectFromPhotosBtnLocaltion").toString():
                                "{\n" +
                                "        \"selectFromPhotosBtnLocaltion_x1\":119,\n" +
                                "        \"selectFromPhotosBtnLocaltion_y1\":942,\n" +
                                "        \"selectFromPhotosBtnLocaltion_x2\":961,\n" +
                                "        \"selectFromPhotosBtnLocaltion_y2\":1092\n" +
                                "    }";
        Map<String, Integer> selectFromPhotosBtnLocaltion = JSONObject.parseObject(selectFromPhotosBtnLocaltionStr, Map.class);
        //坐标前缀：相片前缀
        String photoBtnPreLocation =
                paramMap.get("photoBtnPreLocation")!=null?
                        paramMap.get("photoBtnPreLocation").toString():
                                "//android.widget.FrameLayout[2]/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.GridView/android.widget.RelativeLayout[";
        //坐标后缀：相片后缀
        String photoBtnSufLocation =
                paramMap.get("photoBtnSufLocation")!=null?
                        paramMap.get("photoBtnSufLocation").toString():
                                "]/android.widget.CheckBox";
        //手机本地的微信图片路径
        String phoneLocalPath =
                paramMap.get("phoneLocalPath")!=null?
                        paramMap.get("phoneLocalPath").toString():
                                "/storage/emulated/0/tencent/MicroMsg/WeiXin/";
        String imgListStr =
                paramMap.get("imgList")!=null?
                        paramMap.get("imgList").toString():
                                "[\n" +
                                "        \"http://192.168.43.181/owncloud/index.php/s/6Y0lVeKWCarVgCF/download?path=%2F带图片For朋友圈%2F默认&files=171575470401_.pic_hd.jpg\",\n" +
                                "        \"http://192.168.43.181/owncloud/index.php/s/6Y0lVeKWCarVgCF/download?path=%2F带图片For朋友圈%2F默认&files=181575470402_.pic_hd.jpg\",\n" +
                                "        \"http://192.168.43.181/owncloud/index.php/s/6Y0lVeKWCarVgCF/download?path=%2F带图片For朋友圈%2F默认&files=191575470403_.pic_hd.jpg\"\n" +
                                "    ]";
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
            desiredCapabilities.setCapability("deviceName", deviceName);                  //设备
            desiredCapabilities.setCapability("udid", deviceName);                        //设备唯一标识
            desiredCapabilities.setCapability("appPackage", "com.tencent.mm");      //打开 微信
            desiredCapabilities.setCapability("appActivity", "ui.LauncherUI");      //首个 页面
            desiredCapabilities.setCapability("noReset", true);                     //不用重新安装APK
            desiredCapabilities.setCapability("sessionOverride", true);             //每次启动时覆盖session，否则第二次后运行会报错不能新建session
            desiredCapabilities.setCapability("automationName", "UiAutomator2");
            desiredCapabilities.setCapability("newCommandTimeout", "60");           //在下一个命令执行之前的等待最大时长,单位为秒
            desiredCapabilities.setCapability("autoAcceptAlerts", true);            //默认选择接受弹窗的条款，有些app启动的时候，会有一些权限的弹窗
            URL remoteUrl = new URL("http://localhost:"+4723+"/wd/hub");                           //连接本地的appium
            long startTime = System.currentTimeMillis();
            driver = new AndroidDriver(remoteUrl, desiredCapabilities);
            long endTime = System.currentTimeMillis();
            logger.info("设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】连接Appium成功，总共花费 "+((endTime-startTime)/1000)+" 秒....");
            Thread.sleep(10000);                                                                     //加载安卓页面10秒,保证xml树完全加载
        } catch (Exception e) {
            e.printStackTrace();
            this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
            throw new Exception("配置连接android驱动出现异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的环境是否正常运行等原因");
        }
        //2.将图片保存到【手机本地的微信图片路径】
        if(imgList != null && imgList.size() > 0){
            for(String imgPath : imgList){
                try {
                    /**
                        //从本地获取
                        File imgFile = new File(imgPath);
                        FileInputStream imgInputFile = new FileInputStream(imgFile);
                        byte【】 buffer = new byte【(int)imgFile.length()】;
                        imgInputFile.read(buffer);
                        imgInputFile.close();
                        byte【】 imgData = new BASE64Encoder().encode(buffer).getBytes();
                        String imgName = "/storage/emulated/0/tencent/MicroMsg/WeiXin/"+imgFile.getName();
                        driver.pushFile(imgName, imgData);
                        logger.info("远程图片保存到手机成功，imgPath = " + imgPath);
                    **/
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
                    Thread.sleep(1000);
                } catch (Exception e) {
                    logger.info("将图片保存到【手机本地的微信图片路径】失败，imgPath = " + imgPath);
                    continue;
                }
            }
        }
        logger.info("将图片保存到【手机本地的微信图片路径】成功....");
        /** //3.获取发送的朋友圈文字内容
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
        logger.info("获取发送【朋友圈文字内容】成功...."); **/
        //4.点击坐标【发现】
        try{
            Integer findBtnLocaltion_x1 = findBtnLocaltion.get("findBtnLocaltion_x1")!=null?findBtnLocaltion.get("findBtnLocaltion_x1"):540;
            Integer findBtnLocaltion_y1 = findBtnLocaltion.get("findBtnLocaltion_y1")!=null?findBtnLocaltion.get("findBtnLocaltion_y1"):1661;
            Integer findBtnLocaltion_x2 = findBtnLocaltion.get("findBtnLocaltion_x2")!=null?findBtnLocaltion.get("findBtnLocaltion_x2"):810;
            Integer findBtnLocaltion_y2 = findBtnLocaltion.get("findBtnLocaltion_y2")!=null?findBtnLocaltion.get("findBtnLocaltion_y2"):1812;
            Integer findBtnLocaltion_x = (int)(Math.random()*(findBtnLocaltion_x2 - findBtnLocaltion_x1) + findBtnLocaltion_x1);
            Integer findBtnLocaltion_y = (int)(Math.random()*(findBtnLocaltion_y2 - findBtnLocaltion_y1) + findBtnLocaltion_y1);
            Duration duration = Duration.ofMillis(500);
            new TouchAction(driver).press(findBtnLocaltion_x, findBtnLocaltion_y).waitAction(WaitOptions.waitOptions(duration)).release().perform();
            logger.info("点击坐标【发现】,x = " + findBtnLocaltion_x + " , y = " + findBtnLocaltion_y + "成功....");
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
            this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
            throw new Exception("点击坐标【发现】出现异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因");
        }
        //5.点击坐标【朋友圈】
        try{
            Integer friendCircleBtnLocation_x1 = friendCircleBtnLocation.get("friendCircleBtnLocation_x1")!=null?friendCircleBtnLocation.get("friendCircleBtnLocation_x1"):0;
            Integer friendCircleBtnLocation_y1 = friendCircleBtnLocation.get("friendCircleBtnLocation_y1")!=null?friendCircleBtnLocation.get("friendCircleBtnLocation_y1"):202;
            Integer friendCircleBtnLocation_x2 = friendCircleBtnLocation.get("friendCircleBtnLocation_x2")!=null?friendCircleBtnLocation.get("friendCircleBtnLocation_x2"):1080;
            Integer friendCircleBtnLocation_y2 = friendCircleBtnLocation.get("friendCircleBtnLocation_y2")!=null?friendCircleBtnLocation.get("friendCircleBtnLocation_y2"):354;
            Integer friendCircleBtnLocation_x = (int)(Math.random()*(friendCircleBtnLocation_x2 - friendCircleBtnLocation_x1) + friendCircleBtnLocation_x1);
            Integer friendCircleBtnLocation_y = (int)(Math.random()*(friendCircleBtnLocation_y2 - friendCircleBtnLocation_y1) + friendCircleBtnLocation_y1);
            Duration duration = Duration.ofMillis(500);
            new TouchAction(driver).press(friendCircleBtnLocation_x, friendCircleBtnLocation_y).waitAction(WaitOptions.waitOptions(duration)).release().perform();
            logger.info("点击坐标【朋友圈】,x = " + friendCircleBtnLocation_x + " , y = " + friendCircleBtnLocation_y + "成功....");
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
            this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
            throw new Exception("点击坐标【朋友圈】出现异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因");
        }
        //6.具体操作
        if (action.equals("textMessageFriendCircle")) {             //文字信息朋友圈
            //6.1.长按坐标【相机】
            try{
                Integer cameraLocaltion_x1 = cameraLocaltion.get("cameraLocaltion_x1")!=null?cameraLocaltion.get("cameraLocaltion_x1"):929;
                Integer cameraLocaltion_y1 = cameraLocaltion.get("cameraLocaltion_y1")!=null?cameraLocaltion.get("cameraLocaltion_y1"):72;
                Integer cameraLocaltion_x2 = cameraLocaltion.get("cameraLocaltion_x2")!=null?cameraLocaltion.get("cameraLocaltion_x2"):1080;
                Integer cameraLocaltion_y2 = cameraLocaltion.get("cameraLocaltion_y2")!=null?cameraLocaltion.get("cameraLocaltion_y2"):202;
                Integer cameraLocaltion_x = (int)(Math.random()*(cameraLocaltion_x2 - cameraLocaltion_x1) + cameraLocaltion_x1);
                Integer cameraLocaltion_y = (int)(Math.random()*(cameraLocaltion_y2 - cameraLocaltion_y1) + cameraLocaltion_y1);
                Duration duration = Duration.ofMillis(2000);
                new TouchAction(driver).press(cameraLocaltion_x, cameraLocaltion_y).waitAction(WaitOptions.waitOptions(duration)).release().perform();
                logger.info("点击坐标【相机】,x = " + cameraLocaltion_x + " , y = " + cameraLocaltion_y + "成功....");
                Thread.sleep(1500);
            } catch (Exception e) {
                e.printStackTrace();
                this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
                throw new Exception("长按坐标【相机】出现异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因");
            }
            //6.2.输入文本
            try{
                driver.findElementByXPath(textInputLocaltion).sendKeys(textMessage);
                logger.info("点击坐标【输入文字】成功....");
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
                this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
                throw new Exception("输入文字出现异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因");
            }
            //6.3.点击坐标【发表】
            try{
                driver.findElementById(publishOrCompleteBtnLocaltion).click();
                logger.info("点击坐标【发表】成功....");
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
                this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
                throw new Exception("点击坐标【发表】出现异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因");
            }
        } else if (action.equals("imgMessageFriendCircle")) {        //图片信息朋友圈
            //6.3.点击坐标【相机】
            try {
                Integer cameraLocaltion_x1 = cameraLocaltion.get("cameraLocaltion_x1")!=null?cameraLocaltion.get("cameraLocaltion_x1"):540;
                Integer cameraLocaltion_y1 = cameraLocaltion.get("cameraLocaltion_y1")!=null?cameraLocaltion.get("cameraLocaltion_y1"):1661;
                Integer cameraLocaltion_x2 = cameraLocaltion.get("cameraLocaltion_x2")!=null?cameraLocaltion.get("cameraLocaltion_x2"):810;
                Integer cameraLocaltion_y2 = cameraLocaltion.get("cameraLocaltion_y2")!=null?cameraLocaltion.get("cameraLocaltion_y2"):1812;
                Integer cameraLocaltion_x = (int)(Math.random()*(cameraLocaltion_x2 - cameraLocaltion_x1) + cameraLocaltion_x1);
                Integer cameraLocaltion_y = (int)(Math.random()*(cameraLocaltion_y2 - cameraLocaltion_y1) + cameraLocaltion_y1);
                Duration duration = Duration.ofMillis(100);
                new TouchAction(driver).press(cameraLocaltion_x, cameraLocaltion_y).release().perform();
                logger.info("点击坐标【相机】,x = " + cameraLocaltion_x + " , y = " + cameraLocaltion_y + "成功....");
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
                this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
                throw new Exception("长按坐标【相机】出现异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因");
            }
            //6.4.点击坐标【从相册选择】
            try {
                Integer selectFromPhotosBtnLocaltion_x1 = selectFromPhotosBtnLocaltion.get("selectFromPhotosBtnLocaltion_x1")!=null?selectFromPhotosBtnLocaltion.get("selectFromPhotosBtnLocaltion_x1"):119;
                Integer selectFromPhotosBtnLocaltion_y1 = selectFromPhotosBtnLocaltion.get("selectFromPhotosBtnLocaltion_y1")!=null?selectFromPhotosBtnLocaltion.get("selectFromPhotosBtnLocaltion_y1"):942;
                Integer selectFromPhotosBtnLocaltion_x2 = selectFromPhotosBtnLocaltion.get("selectFromPhotosBtnLocaltion_x2")!=null?selectFromPhotosBtnLocaltion.get("selectFromPhotosBtnLocaltion_x2"):961;
                Integer selectFromPhotosBtnLocaltion_y2 = selectFromPhotosBtnLocaltion.get("selectFromPhotosBtnLocaltion_y2")!=null?selectFromPhotosBtnLocaltion.get("selectFromPhotosBtnLocaltion_y2"):1092;
                Integer selectFromPhotosBtnLocaltion_x = (int)(Math.random()*(selectFromPhotosBtnLocaltion_x2 - selectFromPhotosBtnLocaltion_x1) + selectFromPhotosBtnLocaltion_x1);
                Integer selectFromPhotosBtnLocaltion_y = (int)(Math.random()*(selectFromPhotosBtnLocaltion_y2 - selectFromPhotosBtnLocaltion_y1) + selectFromPhotosBtnLocaltion_y1);
                Duration duration = Duration.ofMillis(500);
                new TouchAction(driver).press(selectFromPhotosBtnLocaltion_x, selectFromPhotosBtnLocaltion_y).waitAction(WaitOptions.waitOptions(duration)).release().perform();
                logger.info("点击坐标【从相册选择】,x = " + selectFromPhotosBtnLocaltion_x + " , y = " + selectFromPhotosBtnLocaltion_y + "成功....");
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
                this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
                throw new Exception("长按坐标【从相册选择】出现异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因");
            }
            //6.5.点击坐标【从相册的左上角开始计数，数字代表第几个图片，勾选】
            for (int i = 1; i <= imageNum; i++) {
                try {
                    driver.findElementByXPath( photoBtnPreLocation+ i + photoBtnSufLocation).click();
                    Thread.sleep(1000);
                } catch (Exception e) {
                    if(imageNum <= 100){
                        imageNum++;
                    } else {
                        break;
                    }
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
                this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
                throw new Exception("长按坐标【完成】出现异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因");
            }
            //6.7.点击【输入文字】
            try {
                driver.findElementByXPath(textInputLocaltion).sendKeys(textMessage);
                logger.info("点击坐标【输入文字】成功....");
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
                this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
                throw new Exception("长按坐标【输入文字】出现异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因");
            }
            //6.8.点击坐标【发布】
            try {
                driver.findElementById(publishOrCompleteBtnLocaltion).click();
                logger.info("点击坐标【发表】成功....");
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
                this.quitDriverAndReboot(driver, deviceNameDesc, deviceName);
                throw new Exception("长按坐标【输入文字】出现异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的应用是否更新导致坐标变化等原因");
            }
        }
        //7.退出驱动
        this.quitDriver(driver, deviceNameDesc, deviceName);
        logger.info( "设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】操作【" + action + "】 发送成功!!!");
    }

    /**
     * 退出驱动并重启手机
     * @param driver
     * @param deviceNameDesc
     * @param deviceName
     */
    public void quitDriver(AndroidDriver driver, String deviceNameDesc, String deviceName) {
        try {
            Thread.sleep(3000);
            if (driver != null) {
                driver.quit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("退出driver异常,请检查设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】的连接等原因");
        }
    }

    /**
     * 退出驱动并重启手机
     * @param driver
     * @param deviceNameDesc
     * @param deviceName
     */
    public void quitDriverAndReboot(AndroidDriver driver, String deviceNameDesc, String deviceName){
        try {
            Thread.sleep(3000);
            if(driver!=null){
                driver.quit();
            }
            try{
                //重启android设备
                Thread.sleep(2000);
                CommandUtil.run("/Users/caihongwang/我的文件/android-sdk/platform-tools/adb -s " + deviceName + " reboot");
                logger.info("重启成功，设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】");
            } catch (Exception e1) {
                logger.info("重启失败，设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("退出driver异常,请检查设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】的连接等原因");
            try{
                //重启android设备
                Thread.sleep(2000);
                CommandUtil.run("/Users/caihongwang/我的文件/android-sdk/platform-tools/adb -s " + deviceName + " reboot");
                logger.info("重启成功，设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】");
            } catch (Exception e1) {
                logger.info("重启失败，设备描述【"+deviceNameDesc+"】设备编码【" + deviceName + "】");
            }
        }
    }

    public static void main(String[] args) {
        try{
            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("action", "imgMessageFriendCircle");
            new RealMachineDevices().sendFriendCircle(paramMap);
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

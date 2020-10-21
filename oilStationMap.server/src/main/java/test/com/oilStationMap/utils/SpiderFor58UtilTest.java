package com.oilStationMap.utils;

import com.google.common.collect.Maps;
import com.oilStationMap.MySuperTest;
import com.oilStationMap.utils.wxAdAutomation.sendFriendCircle.SendFriendCircleUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by caihongwang on 2019/6/12.
 */
public class SpiderFor58UtilTest extends MySuperTest {

    private static final Logger logger = LoggerFactory.getLogger(SpiderFor58UtilTest.class);

    @Test
    public void Test() {
//        for(int pageNum = 1; pageNum <= 30; pageNum++){
//            String ershoufang58Url = "https://tr.58.com/trbj/ershoufang/pn"+pageNum+"/?PGTID=0d30000c-03e6-587c-c89d-e153307aa116&ClickID=1";
//            logger.info("ershoufang58Url = " + ershoufang58Url);
//            this.browse1(ershoufang58Url);
////            Thread.sleep(5000);
//        }

//        SpiderFor58Util.getContactFromWeb();
        Map<String, Object> paramMap = Maps.newHashMap();
        SendFriendCircleUtils.sendFriendCircle(paramMap);
    }


    public void browse1(String url) {
        String osName = System.getProperty("os.name", "");// 获取操作系统的名字
        try {
            if (osName.startsWith("Windows")) {             // windows
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else if (osName.startsWith("Mac OS X")) {     // Mac
                Class fileMgr = Class.forName("com.apple.eio.FileManager");
                Method openURL = fileMgr.getDeclaredMethod("openURL", String.class);
                openURL.invoke(null, url);
            } else {                                        // Unix or Linux
                String[] browsers = {"firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape"};
                String browser = null;
                for (int count = 0; count < browsers.length && browser == null; count++) { // 执行代码，在brower有值后跳出，
                    // 这里是如果进程创建成功了，==0是表示正常结束。
                    if (Runtime.getRuntime().exec(new String[]{"which", browsers[count]}).waitFor() == 0) {
                        browser = browsers[count];
                    }
                }

                if (browser == null) {
                    throw new RuntimeException("未找到任何可用的浏览器");
                } else {// 这个值在上面已经成功的得到了一个进程。
                    Runtime.getRuntime().exec(new String[]{browser, url});
                }
            }
        } catch (Exception e) {
            logger.error("打开 二手房列表页 失败....");
        }
    }

}
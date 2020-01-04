package com.oilStationMap.utils.wxAdAutomation.appiumUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.functions.ExpectedCondition;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 说明：查找元素、判断元素是否存在
 */

public class ElementJudgeMethodUtil {

    public static final Logger logger = LoggerFactory.getLogger(ElementJudgeMethodUtil.class);

    /**
     * 判断元素是否存在
     * 参数： androiddriver By的对象
     * return 布尔值
     **/

    public static boolean isElementPresent(AndroidDriver driver, By by) {
        try {
            if (driver == null) {
                logger.info("driver 对象为空...请检查代码....");
            }
            driver.findElement(by);
            logger.info("在当前页面找到元素：" + by.toString());
            return true;
        } catch (NoSuchElementException e) {
            //e.printStackTrace();
            logger.error("在当前页面找不到该元素：" + by.toString());
            return false;
        }
    }

    /**
     * 等待元素出现 10s超时，找不到返回null 自定义方法等待
     * 参数： androiddriver By的对象 ，等待时间
     * 找到返回true
     **/
    public static WebElement waitForElementPresent(AndroidDriver driver, By by, int waitSec) {
        WebElement webElement = null;
        for (int sec = 0; ; sec++) {
            try {
                if (sec >= waitSec) {
                    try {
                        throw new NoSuchElementException("超过" + waitSec + "s元素未找到：" + by.toString());
                    } catch (NoSuchElementException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                if (isElementPresent(driver, by)) {
                    webElement = driver.findElement(by);
                    break;
                }
                logger.info("继续尝试查找：" + by.toString());
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return webElement;
    }

    /**
     * 查找元素 显性等待
     * 参数：driver 对象,等待时间,by对象
     * return  webelment对象
     **/
    public static WebElement WebElementWait(AndroidDriver driver, int waittime, final By by) {
        WebDriverWait wait = new WebDriverWait(driver, waittime);
        WebElement element = wait.until(new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver d) {
                return d.findElement(by);
            }
        });
        return element;
    }
}

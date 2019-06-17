package com.oilStationMap.utils;

import com.oilStationMap.MySuperTest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by caihongwang on 2019/6/15.
 */
public class SpiderForFangTianXiaUtilTest extends MySuperTest {

    @Test
    public void Test(){
        String url = "https://tongren.esf.fang.com/chushou/3_153414337.htm?channel=1,2&psid=2_58_60";
        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36")
                    .timeout(5000).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(doc.html());
    }

}
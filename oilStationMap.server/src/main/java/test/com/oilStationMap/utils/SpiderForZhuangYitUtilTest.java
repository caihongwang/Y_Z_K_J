package com.oilStationMap.utils;

import com.oilStationMap.MySuperTest;
import org.junit.Test;

/**
 * Created by caihongwang on 2019/6/12.
 */
public class SpiderForZhuangYitUtilTest extends MySuperTest {



    @Test
    public void Test() {

        //发起装修预定
        String baseUrl = "http://tr.zhuangyi.com";
        SpiderForZhuangYitUtil.subscribeRenovation(baseUrl);

//        //装修地区链接
//        String baseUrl = "http://tr.zhuangyi.com";
//        SpiderForZhuangYitUtil.getRenovationCompanyList(baseUrl);

//        //装修公司链接
//        String renovationCompanyUrl = "http://www.375438.zhuangyi.com";
//        String zbSd = "529";
//        String zbCommunity = "蓝月清水湾";
//        String zbBuiltArea = "135";
//        String zbName = "蔡先生";
//        String zbTel = "18685679555";
//        SpiderForZhuangYitUtil.getSimpleRenovationCompany(renovationCompanyUrl,
//                zbSd, zbCommunity, zbBuiltArea, zbName, zbTel);
    }
}
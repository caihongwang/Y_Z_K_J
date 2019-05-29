package com.oilStationMap.utils;

import com.oilStationMap.MySuperTest;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by caihongwang on 2019/5/29.
 */
public class WX_PublicNumberUtilTest extends MySuperTest {

    @Test
    public void Test(){
        String appId = "wx07cf52be1444e4b7";
        String secret = "d6de12032cfe660253b96d5f2868a06c";
        Map<String, Object> resultMap = WX_PublicNumberUtil.createActivityId(appId, secret);
        System.out.println("======resultMap=====");
        System.out.println(resultMap);
        //{errcode=0, expiration_time=1559198011, activity_id=1010_v5qwvE6H1CljIsOBYVGuI5V9oRC8yadHVSsyK5hGxHAfxWwTGBzvWvpWDwc~, errmsg=ok}
    }

}
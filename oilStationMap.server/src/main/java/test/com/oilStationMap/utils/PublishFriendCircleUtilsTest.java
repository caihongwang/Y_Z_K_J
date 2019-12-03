package test.com.oilStationMap.utils;

import com.oilStationMap.MySuperTest;
import com.oilStationMap.utils.PublishFriendCircleUtils.PublishFriendCircleUtils;
import com.oilStationMap.utils.SpiderFor58Util;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Created by caihongwang on 2019/6/12.
 */
public class PublishFriendCircleUtilsTest extends MySuperTest {

    private static final Logger logger = LoggerFactory.getLogger(PublishFriendCircleUtilsTest.class);

    @Test
    public void Test() {

        PublishFriendCircleUtils.sendFriendCircle();
    }

}
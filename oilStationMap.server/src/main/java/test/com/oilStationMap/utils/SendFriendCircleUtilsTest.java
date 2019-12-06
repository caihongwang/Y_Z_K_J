package test.com.oilStationMap.utils;

import com.google.common.collect.Maps;
import com.oilStationMap.MySuperTest;
import com.oilStationMap.utils.wxAdAutomation.sendFriendCircle.SendFriendCircleUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by caihongwang on 2019/6/12.
 */
public class SendFriendCircleUtilsTest extends MySuperTest {

    private static final Logger logger = LoggerFactory.getLogger(SendFriendCircleUtilsTest.class);

    @Test
    public void Test() {

        Map<String, Object> paramMap = Maps.newHashMap();
        SendFriendCircleUtils.sendFriendCircle(paramMap);
    }

}
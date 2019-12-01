package com.oilStationMap.utils.PublishFriendCircleUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.List;
import java.util.Map;

public interface FriendCircleStraetge {

    /**
     * 发送朋友圈
     * @param paramMap
     * @throws Exception
     */
    public void sendFriendCircle(Map<String, Object> paramMap) throws Exception;
}

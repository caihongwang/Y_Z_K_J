package com.oilStationMap.utils.wxAdAutomation.sendFriendCircle;

import org.apache.commons.lang.time.StopWatch;

import java.util.Map;

public interface SendFriendCircle {

    /**
     * 发送朋友圈
     * @param paramMap
     * @throws Exception
     */
    public boolean sendFriendCircle(Map<String, Object> paramMap) throws Exception;
}

package com.oilStationMap.utils.wxAdAutomation.sendFriendCircle;

import org.apache.commons.lang.time.StopWatch;

import java.util.Map;

public interface SendFriendCircle {

    /**
     * 发送朋友圈
     * @param paramMap
     * @throws Exception
     */
    public void sendFriendCircle(Map<String, Object> paramMap, StopWatch sw) throws Exception;
}

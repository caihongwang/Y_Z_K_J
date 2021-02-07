package com.oilStationMap.utils.wxAdAutomation.agreeToFriendRequest;

import org.apache.commons.lang.time.StopWatch;

import java.util.Map;

public interface AgreeToFriendRequest {

    /**
     * 同意好友请求
     * @param paramMap
     * @throws Exception
     */
    public boolean agreeToFriendRequest(Map<String, Object> paramMap) throws Exception;
}

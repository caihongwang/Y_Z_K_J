package com.oilStationMap.utils.wxAdAutomation.chatByNickName;

import org.apache.commons.lang.time.StopWatch;

import java.util.Map;

public interface ChatByNickName {

    /**
     * 根据微信昵称进行聊天
     * @param paramMap
     * @throws Exception
     */
    public void chatByNickName(Map<String, Object> paramMap, StopWatch sw) throws Exception;
}

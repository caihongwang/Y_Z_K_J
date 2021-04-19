package com.oilStationMap.utils.wxAdAutomation.chatByNickName;

import java.util.Map;

public interface ChatByNickName {

    /**
     * 根据微信昵称进行聊天
     * @param paramMap
     * @throws Exception
     */
    public boolean chatByNickName(Map<String, Object> paramMap) throws Exception;
}

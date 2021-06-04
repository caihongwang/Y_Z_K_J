package com.automation.utils.wei_xin.relayTheWxMessage;

import java.util.Map;

public interface RelayTheWxMessage {

    /**
     * 转发微信消息
     * @param paramMap
     * @throws Exception
     */
    public boolean relayTheWxMessage(Map<String, Object> paramMap) throws Exception;
}

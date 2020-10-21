package com.oilStationMap.service;

import java.util.Map;

/**
 * 微信公众平台账户 service
 */
public interface WX_AccountService {

    /**
     * 根据微信小程序的appId获取相关账号信息,默认油价地图的微信小程序账号
     * @param accountId 微信公众号或者小程序的原始ID
     */
    public Map<String, Object> getWxAccount(String accountId);
}

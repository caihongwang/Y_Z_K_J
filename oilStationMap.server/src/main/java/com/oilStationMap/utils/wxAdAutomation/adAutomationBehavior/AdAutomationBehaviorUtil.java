package com.oilStationMap.utils.wxAdAutomation.adAutomationBehavior;

import com.google.common.collect.Maps;
import com.oilStationMap.utils.ApplicationContextUtils;
import redis.clients.jedis.JedisPool;

import java.util.LinkedHashMap;

/**
 * 广告自动化-在各个自动化行为过程中的行为数量统计
 * 根据这些统计数量来确定对当前设备的 重复执行次数.
 */
public class AdAutomationBehaviorUtil {
    public static JedisPool jedisPool = (JedisPool) ApplicationContextUtils.getBeanByClass(JedisPool.class);

    /**
     * 将设备当天的用户行为数量进行统计存入redis
     * @param deviceName
     * @param currentDay
     * @param action
     * @param behavior
     */
    public void setAdAutomationBehavior(String deviceName, String currentDay, String action, String behavior){
//        LinkedHashMap<String, LinkedHashMap<String, Object>> deviceNameMap = Maps.newLinkedHashMap();
//        LinkedHashMap<String, LinkedHashMap<String, Object>> behaviorMap = Maps.newLinkedHashMap();

    }

}

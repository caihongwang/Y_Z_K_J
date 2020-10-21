package com.oilStationMap.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.service.WX_AccountService;
import com.oilStationMap.service.WX_DicService;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * 微信公众平台账户 service
 */
@Service
public class WX_AccountServiceImpl implements WX_AccountService {

    private static final Logger logger = LoggerFactory.getLogger(WX_AccountServiceImpl.class);

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private WX_DicService wxDicService;

    /**
     * 根据微信小程序的appId获取相关账号信息,默认油价地图的微信小程序账号
     * @param accountId 微信公众号或者小程序的原始ID
     */
    @Override
    public Map<String, Object> getWxAccount(String accountId){
        Map<String, Object> accountMap = Maps.newHashMap();
        if(accountId == null || "".equals(accountId)){
            accountId = "gh_417c90af3488";      //默认油价地图的微信小程序账号
        }
        try (Jedis jedis = jedisPool.getResource()) {
            String accountMapStr = jedis.get(accountId);
            if(accountMapStr != null && !"".equals(accountMapStr)){     //先从redis中获取账号相关信息
                accountMap = JSONObject.parseObject(accountMapStr, Map.class);
            } else {
                Map<String, Object> dicParamMap = Maps.newHashMap();
                dicParamMap.put("dicCode", accountId);
                dicParamMap.put("dicType", "customMessageAccount");
                ResultDTO resultDTO = wxDicService.getSimpleDicByCondition(dicParamMap);
                if(resultDTO.getResultList() != null
                        && resultDTO.getResultList().size() > 0){
                    jedis.set(accountId,
                            JSONObject.toJSONString(resultDTO.getResultList().get(0)));
                    accountMap.putAll(resultDTO.getResultList().get(0));
                }
            }
        }
        return accountMap;
    }
}

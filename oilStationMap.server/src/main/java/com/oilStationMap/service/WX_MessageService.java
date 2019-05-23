package com.oilStationMap.service;

import com.oilStationMap.dto.ResultMapDTO;

import java.util.Map;

/**
 * 消息管理
 */
public interface WX_MessageService {

  /**
   * 根据OpenID列表群发【订阅号不可用，服务号认证后可用】
   */
  ResultMapDTO redActivityMessageSend(Map<String, Object> paramMap) throws Exception;

  /**
   * 根据OpenID列表群发【订阅号不可用，服务号认证后可用】
   */
  ResultMapDTO dailyMessageSend(Map<String, Object> paramMap) throws Exception;

}

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
  public ResultMapDTO redActivityMessageSend(Map<String, Object> paramMap) throws Exception;

  /**
   * 根据OpenID列表群发【订阅号不可用，服务号认证后可用】
   */
  public ResultMapDTO dailyMessageSend(Map<String, Object> paramMap) throws Exception;

  /**
   * 根据OpenID列表群发【抽奖】福利
   * @param paramMap
   */
  public ResultMapDTO dailyLuckDrawMessageSend(Map<String, Object> paramMap) throws Exception;


  /**
   * 根据OpenID向 管理员 发【更新油价】
   * @param paramMap
   */
  public ResultMapDTO dailyUpdateOilPriceMessageSend(Map<String, Object> paramMap) throws Exception;


  /**
   * 根据OpenID向 管理员 发【加盟】消息
   * @param paramMap
   */
  public ResultMapDTO dailyLeagueMessageSend(Map<String, Object> paramMap) throws Exception;


  /**
   * 根据OpenID向 管理员 发【更新或者添加加油站】消息
   * @param paramMap
   */
  public ResultMapDTO dailyUpdateOrAddOilStationMessageSend(Map<String, Object> paramMap) throws Exception;


  /**
   * 根据OpenID向 管理员 发【恶意篡改加油站油价】消息
   * @param paramMap
   */
  public ResultMapDTO dailyIllegalUpdateOilPriceMessageSend(Map<String, Object> paramMap) throws Exception;

  /**
   * 根据OpenID向 管理员 发【恶意篡改管理员用户信息】消息
   * @param paramMap
   */
  public ResultMapDTO dailyIllegalUpdateUserInfoMessageSend(Map<String, Object> paramMap) throws Exception;

  /**
   * 根据OpenID向 管理员 发【服务类通知 info级别的】
   * @param paramMap
   */
  public ResultMapDTO dailyServiceProgressMessageSend(Map<String, Object> paramMap) throws Exception;

  /**
   * 根据OpenID列表群发【车主福利for车用尿素】福利
   * @param paramMap
   */
  public ResultMapDTO dailyCarUreaMessageSend(Map<String, Object> paramMap) throws Exception;

  /**
   * 根据粉象生活Json获取【粉象生活Excel】福利
   * @param paramMap
   */
  public ResultMapDTO dailyGetFenXiangShengHuoProduct(Map<String, Object> paramMap) throws Exception;

  /**
   * 根据OpenID向 管理员 发【微信广告自动化过程中的异常设备】
   * @param paramMap
   */
  public ResultMapDTO exceptionDevicesMessageSend(Map<String, Object> paramMap) throws Exception;

  /**
   * 根据OpenID向 管理员 发【小寨家里公网IP地址发生变化】
   * @param paramMap
   */
  public ResultMapDTO exceptionDomainMessageSend(Map<String, Object> paramMap) throws Exception;
}

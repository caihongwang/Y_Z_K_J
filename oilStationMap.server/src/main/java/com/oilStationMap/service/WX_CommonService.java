package com.oilStationMap.service;

import com.oilStationMap.dto.ResultMapDTO;

import java.util.Map;

public interface WX_CommonService {

    /**
     * 根据appId和secret想对应的公众号进行发送文本消息
     * 发送公众号的文本消息
     * @param paramMap
     * @return
     */
    public ResultMapDTO sendTextMessageForWxPublicNumber(Map<String, Object> paramMap);

    /**
     * 在微信公众号内发送卡片类的小程序
     * @param paramMap
     * @return
     */
    public ResultMapDTO sendCustomCardMessageWxPublicNumber(Map<String, Object> paramMap);

    /**
     * 发送公众号的模板消息
     *
     * @param paramMap
     */
    public ResultMapDTO sendTemplateMessageForWxPublicNumber(Map<String, Object> paramMap);

    /**
     * 发送小程序名片的模板消息
     *
     * @param paramMap
     */
    public ResultMapDTO sendTemplateMessageForMiniProgram(Map<String, Object> paramMap);

    /**
     * 获取openId和sessionKey
     */
    public ResultMapDTO getOpenIdAndSessionKeyForWX(Map<String, Object> paramMap);

    /**
     * 获取Signature和JsapiTicket和NonceStr
     */
    public ResultMapDTO getSignatureAndJsapiTicketAndNonceStrForWxPublicNumber(Map<String, Object> paramMap);

    /**
     * 向微信服务器发送请求，获取响应的二维码,同事将获取过来的二维码网络流转换为base64数据
     */
    public Map<String, Object> getTwoDimensionForWX(String accessToken, String page, String scene);

    /**
     * 接受小程序端发送过来的消息，同时对特定的消息进行回复小程序的固定客服消息
     *
     * @param paramMap
     * @return
     */
    public ResultMapDTO receviceAndSendCustomMessage(Map<String, Object> paramMap);

}

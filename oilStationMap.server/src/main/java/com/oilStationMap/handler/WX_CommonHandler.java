package com.oilStationMap.handler;

import com.alibaba.fastjson.JSONObject;
import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.service.WX_CommonService;
import com.oilStationMap.utils.MapUtil;
import com.google.common.collect.Maps;
import org.apache.commons.codec.binary.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Map;

/**
 * 公共Handler
 */
@Component
public class WX_CommonHandler {

    private static final Logger logger = LoggerFactory.getLogger(WX_CommonHandler.class);

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private WX_CommonService wxCommonService;

    /**
     * 在获取微信手机号时获取解密后手机号
     * @param paramMap
     * @return
     */
    public ResultMapDTO getDecryptPhone(Map<String, String> paramMap) {
        logger.info("在hanlder中在获取微信手机号时获取解密后手机号-getDecryptPhone,请求-paramMap:" + paramMap);
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        String encData = paramMap.get("encData") != null ? paramMap.get("encData") : "";
        String iv = paramMap.get("iv") != null ? paramMap.get("iv") : "";
        String sessionKey = paramMap.get("sessionKey") != null ? paramMap.get("sessionKey") : "";
        try (Jedis jedis = jedisPool.getResource()) {
            String wxSessionKey = jedis.get(sessionKey);
            byte[] encrypData = Base64.decodeBase64(encData.getBytes());
            byte[] ivData = Base64.decodeBase64(iv.getBytes());
            byte[] wxSessionKeyData = Base64.decodeBase64(wxSessionKey.getBytes());
            String wxResultData = decrypt(wxSessionKeyData, ivData, encrypData);
            Map<String, String> jsonResultMap = JSONObject.parseObject(wxResultData, Map.class);
            String userPhone = jsonResultMap.get("phoneNumber");
            Map<String, String> resultMap = Maps.newHashMap();
            resultMap.put("userPhone", userPhone);
            resultMapDTO.setResultMap(resultMap);
            resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
            resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        } catch (Exception e) {
            resultMapDTO.setCode(OilStationMapCode.DECRYPT_IS_ERROR.getNo());
            resultMapDTO.setMessage(OilStationMapCode.DECRYPT_IS_ERROR.getMessage());
            logger.error("在hanlder中在获取微信手机号时获取解密后手机号-getDecryptPhone is error, paramMap : " + paramMap + ", e : " + e);
        }
        logger.info("在hanlder中在获取微信手机号时获取解密后手机号-getDecryptPhone,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

    public static String decrypt(byte[] key, byte[] iv, byte[] encData) throws Exception {
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        //解析解密后的字符串
        return new String(cipher.doFinal(encData), "UTF-8");
    }

    /**
     * 发送公众号的模板消息
     * @param paramMap
     * @return
     */
    public ResultMapDTO sendTemplateMessageForWxPublicNumber(Map<String, String> paramMap) {
        logger.info("在hanlder中发送公众号的模板消息-sendTemplateMessageForWxPublicNumber,请求-paramMap:" + paramMap);
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultMapDTO = wxCommonService.sendTemplateMessageForWxPublicNumber(objectParamMap);
            } catch (Exception e) {
                resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                logger.error("在hanlder中发送公众号的模板消息-sendTemplateMessageForWxPublicNumber is error, paramMap : " + paramMap + ", e : " + e);
            }
        } else {
            resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在hanlder中发送公众号的模板消息-sendTemplateMessageForWxPublicNumber,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 在微信公众号内发送卡片类的小程序
     * @param paramMap
     * @return
     */
    public ResultMapDTO sendCustomCardMessageWxPublicNumber(Map<String, String> paramMap) {
        logger.info("在hanlder中在微信公众号内发送卡片类的小程序-sendCustomCardMessageWxPublicNumber,请求-paramMap:" + paramMap);
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultMapDTO = wxCommonService.sendCustomCardMessageWxPublicNumber(objectParamMap);
            } catch (Exception e) {
                resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                logger.error("在hanlder中在微信公众号内发送卡片类的小程序-sendCustomCardMessageWxPublicNumbersendCustomCardMessageWxPublicNumber is error, paramMap : " + paramMap + ", e : " + e);
            }
        } else {
            resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在hanlder中在微信公众号内发送卡片类的小程序-sendCustomCardMessageWxPublicNumber,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 发送小程序名片的模板消息
     * @param paramMap
     * @return
     */
    public ResultMapDTO sendTemplateMessageForMiniProgram(Map<String, String> paramMap) {
        logger.info("在hanlder中发送小程序名片的模板消息-sendTemplateMessageForMiniProgram,请求-paramMap:" + paramMap);
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultMapDTO = wxCommonService.sendTemplateMessageForMiniProgram(objectParamMap);
            } catch (Exception e) {
                resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                logger.error("在hanlder中发送小程序名片的模板消息-sendTemplateMessageForMiniProgram is error, paramMap : " + paramMap + ", e : " + e);
            }
        } else {
            resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在hanlder中发送小程序名片的模板消息-sendTemplateMessageForMiniProgram,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 获取小程序的openId和sessionKey
     * @param paramMap
     * @return
     */
    public ResultMapDTO getOpenIdAndSessionKeyForWX(Map<String, String> paramMap) {
        logger.info("在hanlder中获取openId和sessionKey-getOpenIdAndSessionKeyForWX,请求-paramMap:" + paramMap);
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultMapDTO = wxCommonService.getOpenIdAndSessionKeyForWX(objectParamMap);
            } catch (Exception e) {
                Map<String, String> resultMap = Maps.newHashMap();
                resultMapDTO.setResultListTotal(0);
                resultMapDTO.setResultMap(resultMap);
                resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                logger.error("在hanlder中获取openId和sessionKey-getOpenIdAndSessionKeyForWX is error, paramMap : " + paramMap + ", e : " + e);
            }
        } else {
            resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在hanlder中获取openId和sessionKey-getOpenIdAndSessionKeyForWX,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }


    public ResultMapDTO getSignatureAndJsapiTicketAndNonceStrForWxPublicNumber(Map<String, String> paramMap) {
        logger.info("在hanlder中获取SignatureAndJsapiTicketAndNonceStr-getSignatureAndJsapiTicketAndNonceStrForWxPublicNumber,请求-paramMap:" + paramMap);
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultMapDTO = wxCommonService.getSignatureAndJsapiTicketAndNonceStrForWxPublicNumber(objectParamMap);
            } catch (Exception e) {
                Map<String, String> resultMap = Maps.newHashMap();
                resultMapDTO.setResultListTotal(0);
                resultMapDTO.setResultMap(resultMap);
                resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                logger.error("在hanlder中获取SignatureAndJsapiTicketAndNonceStr-getSignatureAndJsapiTicketAndNonceStrForWxPublicNumber is error, paramMap : " + paramMap + ", e : " + e);
            }
        } else {
            resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在hanlder中获取SignatureAndJsapiTicketAndNonceStr-getSignatureAndJsapiTicketAndNonceStrForWxPublicNumber,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 接受小程序端发送过来的消息，同时对特定的消息进行回复小程序的固定客服消息
     * @param paramMap
     * @return
     */
    public ResultMapDTO receviceAndSendCustomMessage(Map<String, String> paramMap) {
        logger.info("在hanlder中接受小程序端发送过来的消息，同时对特定的消息进行回复小程序的固定客服消息-receviceAndSendCustomMessage,请求-paramMap:" + paramMap);
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultMapDTO = wxCommonService.receviceAndSendCustomMessage(objectParamMap);
            } catch (Exception e) {
                resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                logger.error("在hanlder中接受小程序端发送过来的消息，同时对特定的消息进行回复小程序的固定客服消息-receviceAndSendCustomMessage is error, paramMap : " + paramMap + ", e : " + e);
            }
        } else {
            resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在hanlder中接受小程序端发送过来的消息，同时对特定的消息进行回复小程序的固定客服消息-receviceAndSendCustomMessage,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 接受小程序端发送过来的消息，同时对特定的消息进行回复小程序的固定客服消息
     * @param paramMap
     * @return
     */
    public ResultMapDTO receviceAndSendCustomMessage_For_XSXJ(Map<String, String> paramMap) {
        logger.info("在hanlder中接受【像素星舰】小程序端发送过来的消息，同时对特定的消息进行回复小程序的固定客服消息-receviceAndSendCustomMessage,请求-paramMap:" + paramMap);
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultMapDTO = wxCommonService.receviceAndSendCustomMessage_For_XSXJ(objectParamMap);
            } catch (Exception e) {
                resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                logger.error("在hanlder中接受【像素星舰】小程序端发送过来的消息，同时对特定的消息进行回复小程序的固定客服消息-receviceAndSendCustomMessage is error, paramMap : " + paramMap + ", e : " + e);
            }
        } else {
            resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在hanlder中接受【像素星舰】小程序端发送过来的消息，同时对特定的消息进行回复小程序的固定客服消息-receviceAndSendCustomMessage,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }
}

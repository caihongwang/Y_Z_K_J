package com.oilStationMap.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.service.WX_AccountService;
import com.oilStationMap.service.WX_OrderService;
import com.oilStationMap.utils.wxpay.WXPay;
import com.oilStationMap.utils.wxpay.WXPayConfigImpl;
import com.oilStationMap.utils.wxpay.WXPayConstants;
import com.oilStationMap.utils.wxpay.WXPayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单service
 */
@Service
public class WX_OrderServiceImpl implements WX_OrderService {

    private static final Logger logger = LoggerFactory.getLogger(WX_OrderServiceImpl.class);

    @Autowired
    private WX_AccountService wxAccountService;

    /**
     * 使用统一订单的方式进行下订单，发起微信支付
     *
     * @param paramMap
     * @return
     * 
     */
    @Override
    public ResultMapDTO requestWxPayUnifiedOrder(Map<String, Object> paramMap) throws Exception {
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, String> resultMap = new HashMap<String, String>();
        String payMoney = paramMap.get("payMoney") != null ? paramMap.get("payMoney").toString() : "";
        WXPayConfigImpl config = WXPayConfigImpl.getInstance();
        WXPay wxpay = new WXPay(config);
        String nonce_str = WXPayUtil.generateUUID();        //生成的随机字符串
        String body = "小程序内发起支付";                     //商品名称
        String out_trade_no = WXPayUtil.generateUUID();     //统一订单编号
        String spbillCreateIp = paramMap.get("spbillCreateIp") != null ? paramMap.get("spbillCreateIp").toString() : "";      //获取发起支付的IP地址
        String openId = paramMap.get("openId") != null ? paramMap.get("openId").toString() : "";
        if (!"".equals(openId) && !"".equals(spbillCreateIp)) {
            if ("".equals(payMoney)) {
                resultMapDTO.setCode(OilStationMapCode.ORDER_PAY_MONEY_IS_NOT_NULL.getNo());
                resultMapDTO.setMessage(OilStationMapCode.ORDER_PAY_MONEY_IS_NOT_NULL.getMessage());
            } else {
                String accountId = paramMap.get("accountId")!=null?paramMap.get("accountId").toString():"";
                Map<String, Object> accountMap = wxAccountService.getWxAccount(accountId);
                String appid = accountMap.get("customMessageAccountAppId").toString();
                String secret = accountMap.get("customMessageAccountSecret").toString();
                String accountName = accountMap.get("customMessageAccountName").toString();

                body = accountName + "-" + body;    //商品描述
                float payMoneyFloat = Float.parseFloat(payMoney != "" ? payMoney : "10");
                String total_fee = ((int) (payMoneyFloat * 100)) + "";                           //支付金额，单位：分，这边需要转成字符串类型，否则后面的签名会失败，默认付款1元
                System.out.println("================================================");
                System.out.println("================================================");
                System.out.println("================================================");
                System.out.println("支付费用 payMoney = " + payMoney);
                System.out.println("支付费用 total_fee = " + total_fee);
                System.out.println("================================================");
                System.out.println("================================================");
                System.out.println("================================================");
                try {
                    Map<String, String> packageParams = new HashMap<String, String>();
                    packageParams.put("appid", appid);
                    packageParams.put("mch_id", OilStationMapCode.WX_PAY_MCH_ID);
                    packageParams.put("nonce_str", nonce_str);
                    packageParams.put("body", body);
                    packageParams.put("out_trade_no", out_trade_no);//商户订单号
                    packageParams.put("total_fee", total_fee);//支付金额，这边需要转成字符串类型，否则后面的签名会失败
                    packageParams.put("spbill_create_ip", spbillCreateIp);
                    packageParams.put("notify_url", spbillCreateIp + OilStationMapCode.WX_PAY_NOTIFY_URL);
                    packageParams.put("trade_type", OilStationMapCode.WX_PAY_TRADE_TYPE);
                    packageParams.put("openid", openId);
                    packageParams.put("sign_type", WXPayConstants.MD5);
                    Map<String, String> unifiedOrderResponseMap = wxpay.unifiedOrder(packageParams);            //向微信客户端发送统一订单请求
                    System.out.println("统一订单返回请求：" + JSONObject.toJSONString(unifiedOrderResponseMap));
                    String return_code = (String) unifiedOrderResponseMap.get("return_code");           //返回状态码
                    if (return_code == "SUCCESS" || return_code.equals(return_code)) {
                        String prepay_id = (String) unifiedOrderResponseMap.get("prepay_id");//返回的预付单信息
                        resultMap.put("nonceStr", nonce_str);
                        resultMap.put("package", "prepay_id=" + prepay_id);
                        Long timeStamp = System.currentTimeMillis() / 1000;
                        resultMap.put("timeStamp", timeStamp + "");//这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误
                        //获取unifiedOrderResponseMap需要的支付验证签名
                        Map<String, String> paramMap_temp = new HashMap<String, String>();
                        paramMap_temp.put("appId", appid);
                        paramMap_temp.put("timeStamp", timeStamp + "");
                        paramMap_temp.put("nonceStr", nonce_str);
                        paramMap_temp.put("package", "prepay_id=" + prepay_id);
                        paramMap_temp.put("signType", "MD5");
                        //再次签名，这个签名用于小程序端调用wx.requesetPayment方法
                        String paySign = WXPayUtil.generateSignature(paramMap_temp, config.getKey(), WXPayConstants.SignType.MD5);
                        resultMap.put("paySign", paySign);
                        resultMap.put("appid", appid);
                        /** 此处添加自己的更新订单信息 **/
                        /** 此处添加自己的更新订单信息 **/
                        /** 此处添加自己的更新订单信息 **/
                        /** 此处添加自己的更新订单信息 **/
                        /** 此处添加自己的更新订单信息 **/
                        /** 此处添加自己的更新订单信息 **/
                        /** 此处添加自己的更新订单信息 **/
                        /** 此处添加自己的更新订单信息 **/
                        /** 此处添加自己的更新订单信息 **/
                        /** 此处添加自己的更新订单信息 **/
                        /** 此处添加自己的更新订单信息 **/
                        /** 此处添加自己的更新订单信息 **/
                        /** 此处添加自己的业务逻辑代码 **/
                        /** 此处添加自己的业务逻辑代码 **/
                        /** 此处添加自己的业务逻辑代码 **/
                        /** 此处添加自己的业务逻辑代码 **/
                        /** 此处添加自己的业务逻辑代码 **/
                        resultMapDTO.setResultMap(resultMap);
                        resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                        resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
                    } else {

                        resultMapDTO.setCode(OilStationMapCode.ORDER_RESPONSE_UNIFIEDORDER_IS_ERROR.getNo());
                        resultMapDTO.setMessage(OilStationMapCode.ORDER_RESPONSE_UNIFIEDORDER_IS_ERROR.getMessage());
                    }
                } catch (Exception e) {
                    logger.info("在service中使用统一订单的方式进行下订单，发起微信支付-wxPayUnifiedOrder is error, e : " + e);

                    resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
                    resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
                }
            }
        } else {

            resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在service中使用统一订单的方式进行下订单，发起微信支付-wxPayUnifiedOrder,结果-result:" + resultMapDTO);
        return resultMapDTO;
    }

}

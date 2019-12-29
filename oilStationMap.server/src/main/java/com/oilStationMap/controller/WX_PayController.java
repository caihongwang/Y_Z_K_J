package com.oilStationMap.controller;

import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.handler.WX_PayHandler;
import com.oilStationMap.utils.HttpUtil;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
@RequestMapping(value = "/wxPay", produces = "application/json;charset=utf-8")
public class WX_PayController {

    private static final Logger logger = LoggerFactory.getLogger(WX_PayController.class);

    @Autowired
    private WX_PayHandler wx_PayHandler;

    /**
     * 获取微信鉴权链接
     * @param request
     * @return
     */
    @RequestMapping("/toOauthUrlForPaymentPage")
    public String toOauthUrlForPaymentPage(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中获取oauth的url-toOauthUrlForPaymentPage,请求-paramMap:" + paramMap);
        String toOauthUrl = "https://www.91caihongwang.com/oilStationMap/wxPay/getPaymentPage";
        try {
            ResultMapDTO resultMapDTO = wx_PayHandler.toOauthUrlForPaymentPage(paramMap);
            Map<String, String> dataMap = resultMapDTO.getResultMap();
            toOauthUrl = dataMap.get("toOauthUrl");
            resultMap.put("recordsFiltered", resultMapDTO.getResultListTotal());
            resultMap.put("data", dataMap);
            resultMap.put("code", resultMapDTO.getCode());
            resultMap.put("message", resultMapDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中获取oauth的url-toOauthUrlForPaymentPage is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中获取oauth的url-toOauthUrlForPaymentPage,响应-response:" + resultMap);
        logger.info("在controller中获取oauth的url-toOauthUrlForPaymentPage,重定向的链接toOauthUrl = " + toOauthUrl);
        //从定向到微信服务器，然后从微信服务器跳转到getOauth
        return "redirect:" + toOauthUrl;//重定向;
    }

    /**
     * 获取支付页面
     * @param request
     * @return
     */
    @RequestMapping("/getPaymentPage")
    public String getPaymentPage(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中获取oauth-getPaymentPage,请求-paramMap:" + paramMap);
        String paymentUrl = "https://www.91caihongwang.com/oilStationMap/resourceOfOilStationMap/webapp/payment.html";
        try {
            ResultMapDTO resultMapDTO = wx_PayHandler.getPaymentPage(paramMap);
            Map<String, String> dataMap = resultMapDTO.getResultMap();
            paymentUrl = dataMap.get("paymentUrl");
            resultMap.put("recordsFiltered", resultMapDTO.getResultListTotal());
            resultMap.put("data", resultMapDTO.getResultMap());
            resultMap.put("code", resultMapDTO.getCode());
            resultMap.put("message", resultMapDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中获取oauth-getPaymentPage is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        //整合参数
        logger.info("在controller中获取oauth-getPaymentPage,重定向的付款链接，整合之前 paymentUrl = " + paymentUrl);
        try{
            Map<String, String> paramMap_temp = Maps.newHashMap();
            String lon = paramMap.get("lon")!=null?paramMap.get("lon").toString():"";
            String lat = paramMap.get("lat")!=null?paramMap.get("lat").toString():"";
            String oilStationName = paramMap.get("oilStationName")!=null?paramMap.get("oilStationName").toString():"";
            String oilStationWxPaymentCodeImgUrl = paramMap.get("oilStationWxPaymentCodeImgUrl")!=null?paramMap.get("oilStationWxPaymentCodeImgUrl").toString():"";
            if(!"".equals(lon)){
                paramMap_temp.put("lon", lon);
            }
            if(!"".equals(lat)){
                paramMap_temp.put("lat", lat);
            }
            if(!"".equals(oilStationName)){
                logger.info("油站编码之前： oilStationName = " + oilStationName);
                oilStationName = URLEncoder.encode(oilStationName);
                logger.info("油站编码之后： oilStationName = " + oilStationName);
                paramMap_temp.put("oilStationName", oilStationName);
            }
            if(!"".equals(oilStationWxPaymentCodeImgUrl)){
                paramMap_temp.put("oilStationWxPaymentCodeImgUrl", oilStationWxPaymentCodeImgUrl);
            }
            if(paymentUrl.endsWith("?")){
                paymentUrl = paymentUrl.substring(paymentUrl.length() - 1);
            }
            if (paramMap_temp != null && !paramMap_temp.isEmpty()) {
                paymentUrl = paymentUrl + "&";
                Map.Entry entry;
                for (Iterator i$ = paramMap_temp.entrySet().iterator(); i$.hasNext(); paymentUrl = paymentUrl + (String) entry.getKey() + "=" + URLDecoder.decode((String) entry.getValue(), "UTF-8") + "&") {
                    entry = (Map.Entry) i$.next();
                }
                paymentUrl = paymentUrl.substring(paymentUrl.length() - 1);
            } else {
                paymentUrl = paymentUrl;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("在controller中获取oauth-getPaymentPage,重定向的付款链接，整合之后 paymentUrl = " + paymentUrl);
        logger.info("在controller中获取oauth-getPaymentPage,响应-response:" + resultMap);
        return "redirect:" + paymentUrl;//重定向;
    }

    /**
     * 统一订单支付请求
     * @param request
     * @return
     */
    @RequestMapping("/unifiedOrderPay")
    @ResponseBody
    public Map<String, Object> unifiedOrderPay(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中统一订单支付请求-unifiedOrderPay,请求-paramMap:" + paramMap);
        try {
            ResultMapDTO resultMapDTO = wx_PayHandler.unifiedOrderPay(paramMap);
            resultMap.put("recordsFiltered", resultMapDTO.getResultListTotal());
            resultMap.put("data", resultMapDTO.getResultMap());
            resultMap.put("code", resultMapDTO.getCode());
            resultMap.put("message", resultMapDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中统一订单支付请求-unifiedOrderPay is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中统一订单支付请求-unifiedOrderPay,响应-response:" + resultMap);
        return resultMap;
    }
}

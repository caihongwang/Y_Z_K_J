package com.oilStationMap.controller;

import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.handler.WX_SpiderHandler;
import com.oilStationMap.utils.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/wxSpider", produces = "application/json;charset=utf-8")
public class WX_SpiderController {

    private static final Logger logger = LoggerFactory.getLogger(WX_SpiderController.class);

    @Autowired
    private WX_SpiderHandler wxSpiderHandler;

    @RequestMapping("/getContactFrom58ErShouFang")
    @ResponseBody
    public Map<String, Object> getContactFrom58ErShouFang(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中爬取58二手房爬取手机号并整合入库-getContactFrom58ErShouFang,请求-paramMap:" + paramMap);
        try {
            ResultDTO resultDTO = wxSpiderHandler.getContactFrom58ErShouFang(paramMap);
            resultMap.put("code", resultDTO.getCode());
            resultMap.put("message", resultDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中爬取58二手房爬取手机号并整合入库-getContactFrom58ErShouFang is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("code", OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中爬取58二手房爬取手机号并整合入库-getContactFrom58ErShouFang,响应-response:" + resultMap);
        return resultMap;
    }

}

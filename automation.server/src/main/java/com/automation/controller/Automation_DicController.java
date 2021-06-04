package com.automation.controller;

import com.automation.code.Automation_Code;
import com.automation.dto.BoolDTO;
import com.automation.dto.ResultDTO;
import com.automation.dto.ResultMapDTO;
import com.automation.service.Automation_DicService;
import com.automation.utils.HttpUtil;
import com.automation.utils.MapUtil;
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
@RequestMapping(value = "/dic", produces = "application/json;charset=utf-8")
public class Automation_DicController {

    private static final Logger logger = LoggerFactory.getLogger(Automation_DicController.class);

    @Autowired
    private Automation_DicService automation_DicService;

    @RequestMapping("/addDic")
    @ResponseBody
    public Map<String, Object> addDic(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中添加字典-addDic,请求-paramMap:" + paramMap);
        try {
            Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
            BoolDTO boolDTO = automation_DicService.addDic(objectParamMap);
            resultMap.put("success", true);
            resultMap.put("code", boolDTO.getCode());
            resultMap.put("message", boolDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中添加字典-addDic is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", Automation_Code.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", Automation_Code.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中添加字典-addDic,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/deleteDic")
    @ResponseBody
    public Map<String, Object> deleteDic(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中删除字典-deleteDic,请求-paramMap:" + paramMap);
        try {
            Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
            BoolDTO boolDTO = automation_DicService.deleteDic(objectParamMap);
            resultMap.put("success", true);
            resultMap.put("code", boolDTO.getCode());
            resultMap.put("message", boolDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中删除字典-deleteDic is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", Automation_Code.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", Automation_Code.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中删除字典-deleteDic,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/updateDic")
    @ResponseBody
    public Map<String, Object> updateDic(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中修改字典-updateDic,请求-paramMap:" + paramMap);
        try {
            Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
            BoolDTO boolDTO = automation_DicService.updateDic(objectParamMap);
            resultMap.put("success", true);
            resultMap.put("code", boolDTO.getCode());
            resultMap.put("message", boolDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中修改字典-updateDic is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", Automation_Code.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", Automation_Code.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中修改字典-updateDic,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/getSimpleDicByCondition")
    @ResponseBody
    public Map<String, Object> getSimpleDicByCondition(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中获取单一的字典-getSimpleDicByCondition,请求-paramMap:" + paramMap);
        try {
            Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
            ResultDTO resultDTO = automation_DicService.getSimpleDicByCondition(objectParamMap);
            resultMap.put("recordsFiltered", resultDTO.getResultListTotal());
            resultMap.put("data", resultDTO.getResultList());
            resultMap.put("code", resultDTO.getCode());
            resultMap.put("message", resultDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中修改字典-getSimpleDicByCondition is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", Automation_Code.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", Automation_Code.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中修改字典-getSimpleDicByCondition,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/getMoreDicByCondition")
    @ResponseBody
    public Map<String, Object> getMoreDicByCondition(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在controller中获取多个的字典-getMoreDicByCondition,请求-paramMap:" + paramMap);
        try {
            Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
            ResultMapDTO resultMapDTO = automation_DicService.getMoreDicByCondition(objectParamMap);
            resultMap.put("recordsFiltered", resultMapDTO.getResultListTotal());
            resultMap.put("data", resultMapDTO.getResultMap());
            resultMap.put("code", resultMapDTO.getCode());
            resultMap.put("message", resultMapDTO.getMessage());
        } catch (Exception e) {
            logger.error("在controller中获取多个的字典-getMoreDicByCondition is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", Automation_Code.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", Automation_Code.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在controller中获取多个的字典-getMoreDicByCondition,响应-response:" + resultMap);
        return resultMap;
    }



    @RequestMapping("/addDicForAdmin")
    @ResponseBody
    public Map<String, Object> addDicForAdmin(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在【controller】添加字典For管理中心-addDicForAdmin,请求-paramMap:" + paramMap);
        try {
            paramMap.remove("id");
            Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
            BoolDTO boolDTO = automation_DicService.addDic(objectParamMap);
            resultMap.put("success", true);
            resultMap.put("code", boolDTO.getCode());
            resultMap.put("message", boolDTO.getMessage());
        } catch (Exception e) {
            logger.error("在【controller】添加字典For管理中心-addDicForAdmin is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", Automation_Code.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", Automation_Code.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在【controller】添加字典For管理中心-addDicForAdmin,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/deleteDicForAdmin")
    @ResponseBody
    public Map<String, Object> deleteDicForAdmin(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在【controller】删除字典For管理中心-deleteDicForAdmin,请求-paramMap:" + paramMap);
        try {
            Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
            BoolDTO boolDTO = automation_DicService.deleteDic(objectParamMap);
            resultMap.put("success", true);
            resultMap.put("code", boolDTO.getCode());
            resultMap.put("message", boolDTO.getMessage());
        } catch (Exception e) {
            logger.error("在【controller】删除字典For管理中心-deleteDicForAdmin is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", Automation_Code.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", Automation_Code.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在【controller】删除字典For管理中心-deleteDicForAdmin,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/updateDicForAdmin")
    @ResponseBody
    public Map<String, Object> updateDicForAdmin(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在【controller】修改字典For管理中心-updateDicForAdmin,请求-paramMap:" + paramMap);
        try {
            Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
            BoolDTO boolDTO = automation_DicService.updateDic(objectParamMap);
            resultMap.put("success", true);
            resultMap.put("code", boolDTO.getCode());
            resultMap.put("message", boolDTO.getMessage());
        } catch (Exception e) {
            logger.error("在【controller】修改字典For管理中心-updateDicForAdmin is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", Automation_Code.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", Automation_Code.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在【controller】修改字典For管理中心-updateDicForAdmin,响应-response:" + resultMap);
        return resultMap;
    }

    @RequestMapping("/getDicListByConditionForAdmin")
    @ResponseBody
    public Map<String, Object> getDicListByConditionForAdmin(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取请求参数能够获取到并解析
        paramMap = HttpUtil.getRequestParams(request);
        logger.info("在【controller】获取单一的字典列表For管理中心-getDicListByConditionForAdmin,请求-paramMap:" + paramMap);
        try {
            Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
            ResultDTO resultDTO = automation_DicService.getDicListByConditionForAdmin(objectParamMap);
            resultMap.put("recordsFiltered", resultDTO.getResultListTotal());
            resultMap.put("recordsTotal", resultDTO.getResultListTotal());
            resultMap.put("data", resultDTO.getResultList());
            resultMap.put("code", resultDTO.getCode());
            resultMap.put("message", resultDTO.getMessage());
        } catch (Exception e) {
            logger.error("在【controller】获取单一的字典列表For管理中心-getDicListByConditionForAdmin is error, paramMap : " + paramMap + ", e : " + e);
            resultMap.put("success", false);
            resultMap.put("code", Automation_Code.SERVER_INNER_ERROR.getNo());
            resultMap.put("message", Automation_Code.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在【controller】获取单一的字典列表For管理中心-getDicListByConditionForAdmin,响应-response:" + resultMap);
        return resultMap;
    }

}

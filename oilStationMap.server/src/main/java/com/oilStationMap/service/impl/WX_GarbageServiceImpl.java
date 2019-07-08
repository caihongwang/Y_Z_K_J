package com.oilStationMap.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dao.WX_GarbageDao;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.service.WX_DicService;
import com.oilStationMap.service.WX_GarbageService;
import com.oilStationMap.utils.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description 垃圾Service
 * @author caihongwang
 */
@Service
public class WX_GarbageServiceImpl implements WX_GarbageService {

    private static final Logger logger = LoggerFactory.getLogger(WX_GarbageServiceImpl.class);

    @Autowired
    private WX_DicService wxDicService;

    @Autowired
    private WX_GarbageDao wxGarbageDao;

    /**
     * 获取垃圾类型列表
     * @param paramMap
     * @return
     */
    @Override
    public ResultDTO getGarbageList(Map<String, Object> paramMap) {
        logger.info("【service】获取垃圾类型列表-getGarbageTypeList,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        ResultDTO resultDTO = new ResultDTO();
        List<Map<String, Object>> garbageTypeList = Lists.newArrayList();
        //默认显示 湿垃圾
        String garbageTypeCode = paramMap.get("garbageTypeCode")!=null?paramMap.get("garbageTypeCode").toString():"wetGarbage";
        Integer start = Integer.parseInt(paramMap.get("start")!=null?paramMap.get("start").toString():"0");
        Integer end = Integer.parseInt(paramMap.get("end")!=null?paramMap.get("end").toString():"20");
        //1.获取垃圾类型
        String dicType = "garbageType";     //垃圾类型
        paramMap.put("dicType", dicType);
        resultDTO = wxDicService.getSimpleDicByCondition(paramMap);
        List<Map<String, String>> garbageTypeStrList = resultDTO.getResultList();
        //2.根据垃圾类型获取垃圾列表
        if(garbageTypeStrList != null && garbageTypeStrList.size() > 0){
            for(Map<String, String> garbageTypeStrMap : garbageTypeStrList){
                Map<String, Object> garbageTypeMap = Maps.newHashMap();
                garbageTypeMap.putAll(garbageTypeStrMap);
                String garbageTypeCodeTemp = garbageTypeStrMap.get("garbageTypeCode").toString();
                if(garbageTypeCode.equals(garbageTypeCodeTemp)){
                    paramMap.put("dicType", "garbage");
                    paramMap.put("dicRemark", garbageTypeCode);
                    resultDTO = wxDicService.getSimpleDicByCondition(paramMap);
                    List<Map<String, String>> garbageList = resultDTO.getResultList();
                    garbageList = garbageList.subList(start, end);
                    garbageTypeMap.put("garbageList", JSONObject.toJSONString(garbageList));
                }
                garbageTypeList.add(garbageTypeMap);
            }
        }
        resultDTO.setResultList(MapUtil.getStringMapList(garbageTypeList));
        logger.info("【service】获取垃圾类型列表-getGarbageTypeList,响应-resultDTO = {}", JSONObject.toJSONString(resultDTO));
        return resultDTO;
    }

    /**
     * 获取单一的垃圾信息
     * @param paramMap
     * @return
     */
    @Override
    public ResultDTO getSimpleGarbageByCondition(Map<String, Object> paramMap) {
        logger.info("【service】获取单一的垃圾-getSimpleGarbageByCondition,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        ResultDTO resultDTO = new ResultDTO();
        String garbageName = paramMap.get("garbageName")!=null?paramMap.get("garbageName").toString():"";
        if(!"".equals(garbageName)){
            paramMap.put("dicType", "garbage");
            paramMap.put("dicName", garbageName);
            resultDTO = wxDicService.getSimpleDicByCondition(paramMap);
            if(resultDTO.getResultList() == null || resultDTO.getResultList().size() < 0){
                //将未知的垃圾进行入库,方便后续进行辨别.
                wxGarbageDao.addGarbage(paramMap);
            } else {
                List<Map<String, String>> garbageList = resultDTO.getResultList();
                String garbageTypeCode = garbageList.get(0).get("garbageTypeCode");
                //获取垃圾类型
                paramMap.put("dicType", "garbageType");
                paramMap.put("dicCode", garbageTypeCode);
                resultDTO = wxDicService.getSimpleDicByCondition(paramMap);
                List<Map<String, String>> garbageTypeStrList = resultDTO.getResultList();
                Map<String, String> garbageTypeMap = Maps.newHashMap();
                garbageTypeMap.put("garbageList", JSONObject.toJSONString(garbageList));
                garbageTypeStrList.add(garbageTypeMap);
                resultDTO.setResultList(garbageTypeStrList);
            }
        } else {
            resultDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("【service】获取单一的垃圾-getSimpleGarbageByCondition,响应-resultDTO = {}", JSONObject.toJSONString(resultDTO));
        return resultDTO;
    }
}

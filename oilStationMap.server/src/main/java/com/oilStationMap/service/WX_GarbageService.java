package com.oilStationMap.service;

import com.oilStationMap.dto.ResultDTO;

import java.util.Map;

/**
 * @Description 垃圾Service
 * @author caihongwang
 */
public interface WX_GarbageService {

    /**
     * 获取垃圾列表
     */
    ResultDTO getGarbageList(Map<String, Object> paramMap);

    /**
     * 根据条件查询垃圾信息
     */
    ResultDTO getSimpleGarbageByCondition(Map<String, Object> paramMap);

}

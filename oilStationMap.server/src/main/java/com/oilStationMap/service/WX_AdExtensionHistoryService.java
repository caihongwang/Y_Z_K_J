package com.oilStationMap.service;

import com.oilStationMap.dto.BoolDTO;
import com.oilStationMap.dto.ResultDTO;

import java.util.Map;

/**
 * @Description 广告推广历史Service
 * @author caihongwang
 */
public interface WX_AdExtensionHistoryService {

    /**
     * 根据条件查询广告推广历史信息
     */
    ResultDTO getSimpleAdExtensionHistoryByCondition(Map<String, Object> paramMap);

    /**
     * 添加或者修改广告推广历史信息
     */
    BoolDTO addAdExtensionHistory(Map<String, Object> paramMap);

    /**
     * 修改广告推广历史信息
     */
    BoolDTO updateAdExtensionHistory(Map<String, Object> paramMap);

    /**
     * 删除广告推广历史信息
     */
    BoolDTO deleteAdExtensionHistory(Map<String, Object> paramMap);
}

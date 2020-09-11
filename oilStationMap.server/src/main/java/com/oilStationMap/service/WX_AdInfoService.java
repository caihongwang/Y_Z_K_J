package com.oilStationMap.service;

import com.oilStationMap.dto.BoolDTO;
import com.oilStationMap.dto.ResultDTO;

import java.util.Map;

public interface WX_AdInfoService {

    /**
     * 根据条件查询广告信息
     */
    ResultDTO getSimpleAdInfoByCondition(Map<String, Object> paramMap);

    /**
     * 添加或者修改广告信息
     */
    BoolDTO addAdInfo(Map<String, Object> paramMap);

    /**
     * 修改广告信息
     */
    BoolDTO updateAdInfo(Map<String, Object> paramMap);

    /**
     * 删除广告信息
     */
    BoolDTO deleteAdInfo(Map<String, Object> paramMap);
}

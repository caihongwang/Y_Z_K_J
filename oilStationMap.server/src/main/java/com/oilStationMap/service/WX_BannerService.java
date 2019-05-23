package com.oilStationMap.service;

import com.oilStationMap.dto.ResultDTO;

import java.util.Map;

public interface WX_BannerService {

    /**
     * 获取正在活跃的Banner信息
     */
    public ResultDTO getActivityBanner(Map<String, Object> paramMap);

}

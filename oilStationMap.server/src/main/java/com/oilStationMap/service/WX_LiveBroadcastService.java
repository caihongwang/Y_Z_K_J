package com.oilStationMap.service;

import com.oilStationMap.dto.ResultMapDTO;

import java.util.Map;

/**
 * 直播管理
 */
public interface WX_LiveBroadcastService {

    /**
     * 获取直播房间列表
     * @param paramMap
     * @return
     */
    public ResultMapDTO getLiveInfoList(Map<String, Object> paramMap);
}

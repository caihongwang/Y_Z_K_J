package com.oilStationMap.service;

import com.oilStationMap.dto.BoolDTO;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.dto.ResultMapDTO;

import java.util.Map;

public interface WX_RedPacketHistoryService {

    /**
     * 获取已领取红包总额
     */
    ResultMapDTO getAllRedPacketMoneyTotal(Map<String, Object> paramMap);

    /**
     * 红包领取记录
     */
    ResultDTO getRedPacketHistoryList(Map<String, Object> paramMap);

    /**
     * 根据条件查询红包领取记录
     */
    ResultDTO getSimpleRedPacketHistoryByCondition(Map<String, Object> paramMap);

    /**
     * 添加或者修改红包领取记录
     */
    BoolDTO addRedPacketHistory(Map<String, Object> paramMap);

    /**
     * 修改红包领取记录
     */
    BoolDTO updateRedPacketHistory(Map<String, Object> paramMap);

    /**
     * 删除红包领取记录
     */
    BoolDTO deleteRedPacketHistory(Map<String, Object> paramMap);
}

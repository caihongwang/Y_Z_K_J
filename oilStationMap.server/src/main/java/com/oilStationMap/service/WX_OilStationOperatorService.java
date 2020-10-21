package com.oilStationMap.service;

import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.dto.ResultMapDTO;

import java.util.Map;

public interface WX_OilStationOperatorService {

    /**
     * 根据条件查询对加油站的操作信息
     */
    ResultDTO getSimpleOilStationOperatorByCondition(Map<String, Object> paramMap);

    /**
     * 根据条件查询对加油站操作的用户总金额
     */
    ResultDTO getOilStationOperatorByCondition(Map<String, Object> paramMap);

    /**
     * 添加或者修改对加油站的操作信息
     */
    ResultMapDTO addOilStationOperator(Map<String, Object> paramMap);

    /**
     * 修改对加油站的操作信息
     */
    ResultMapDTO updateOilStationOperator(Map<String, Object> paramMap);

    /**
     * 删除对加油站的操作信息
     */
    ResultMapDTO deleteOilStationOperator(Map<String, Object> paramMap);

    /**
     * 领取或者提现加油站操作红包
     */
    ResultMapDTO cashOilStationOperatorRedPacket(Map<String, Object> paramMap);
}

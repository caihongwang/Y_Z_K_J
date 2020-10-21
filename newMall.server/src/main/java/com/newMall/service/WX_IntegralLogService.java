package com.newMall.service;

import com.newMall.dto.BoolDTO;
import com.newMall.dto.ResultDTO;

import java.util.Map;

/**
 * @Description 积分日志Service
 * @author caihongwang
 */
public interface WX_IntegralLogService {

    /**
     * 根据条件查询积分日志信息
     */
    ResultDTO getSimpleIntegralLogByCondition(Map<String, Object> paramMap);

    /**
     * 添加或者修改积分日志信息
     */
    BoolDTO addIntegralLog(Map<String, Object> paramMap);

    /**
     * 修改积分日志信息
     */
    BoolDTO updateIntegralLog(Map<String, Object> paramMap);

    /**
     * 删除积分日志信息
     */
    BoolDTO deleteIntegralLog(Map<String, Object> paramMap);
}

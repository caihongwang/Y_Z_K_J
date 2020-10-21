package com.oilStationMap.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface XXL_JobInfoDao {

    /**
     * 根据条件查询任务信息
     */
    List<Map<String, Object>> getSimpleJobInfoByCondition(Map<String, Object> paramMap);

    /**
     * 根据条件查询任务信息总数
     */
    Integer getSimpleJobInfoTotalByCondition(Map<String, Object> paramMap);
}

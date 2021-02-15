package com.oilStationMap.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Description 广告推广历史Dao
 * @author caihongwang
 */
@Mapper
@Transactional
public interface WX_AdExtensionHistoryDao {

    /**
     * 根据条件查询广告推广历史信息
     */
    List<Map<String, Object>> getSimpleAdExtensionHistoryByCondition(Map<String, Object> paramMap);

    /**
     * 根据条件查询广告推广历史信息总数
     */
    Integer getSimpleAdExtensionHistoryTotalByCondition(Map<String, Object> paramMap);

    /**
     * 添加或者修改广告推广历史信息
     */
    Integer addAdExtensionHistory(Map<String, Object> paramMap);

    /**
     * 修改广告推广历史信息
     */
    Integer updateAdExtensionHistory(Map<String, Object> paramMap);

    /**
     * 删除广告推广历史信息
     */
    Integer deleteAdExtensionHistory(Map<String, Object> paramMap);
}

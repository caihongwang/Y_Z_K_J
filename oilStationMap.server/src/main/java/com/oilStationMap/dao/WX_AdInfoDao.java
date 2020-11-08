package com.oilStationMap.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface WX_AdInfoDao {

    /**
     * 根据条件查询广告信息
     */
    List<Map<String, Object>> getSimpleAdInfoByConditionForAdmin(Map<String, Object> paramMap);

    /**
     * 根据条件查询广告信息总数
     */
    Integer getSimpleAdInfoTotalByConditionForAdmin(Map<String, Object> paramMap);

    /**
     * 根据条件查询广告信息
     */
    List<Map<String, Object>> getSimpleAdInfoByCondition(Map<String, Object> paramMap);

    /**
     * 根据条件查询广告信息总数
     */
    Integer getSimpleAdInfoTotalByCondition(Map<String, Object> paramMap);

    /**
     * 添加或者修改广告信息
     */
    Integer addAdInfo(Map<String, Object> paramMap);

    /**
     * 修改广告信息
     */
    Integer updateAdInfo(Map<String, Object> paramMap);

    /**
     * 删除广告信息
     */
    Integer deleteAdInfo(Map<String, Object> paramMap);
}

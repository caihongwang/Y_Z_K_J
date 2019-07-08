package com.oilStationMap.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Description 垃圾Dao
 * @author caihongwang
 */
@Mapper
public interface WX_GarbageDao {

    /**
     * 根据条件查询垃圾信息
     */
    List<Map<String, Object>> getSimpGarbageByCondition(Map<String, Object> paramMap);

    /**
     * 根据条件查询垃圾信息总数
     */
    Integer getSimpGarbageTotalByCondition(Map<String, Object> paramMap);

    /**
     * 添加或者修改垃圾信息
     */
    Integer addGarbage(Map<String, Object> paramMap);

    /**
     * 修改垃圾信息
     */
    Integer updateGarbage(Map<String, Object> paramMap);

    /**
     * 删除垃圾信息
     */
    Integer deleteGarbage(Map<String, Object> paramMap);
}

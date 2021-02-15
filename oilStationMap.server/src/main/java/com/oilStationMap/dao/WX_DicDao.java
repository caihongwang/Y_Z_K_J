package com.oilStationMap.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Mapper
@Transactional
public interface WX_DicDao {

    /**
     * 根据条件查询字典信息
     */
    List<Map<String, Object>> getLatelyDicByCondition(Map<String, Object> paramMap);

    /**
     * 根据条件查询字典信息总数
     */
    Integer getLatelyDicTotalByCondition(Map<String, Object> paramMap);

    /**
     * 根据条件查询字典信息For管理中心
     */
    List<Map<String, Object>> getDicListByConditionForAdmin(Map<String, Object> paramMap);

    /**
     * 根据条件查询字典信息总数For管理中心
     */
    Integer getDicListTotalByConditionForAdmin(Map<String, Object> paramMap);

    /**
     * 根据条件查询字典信息
     */
    List<Map<String, Object>> getSimpleDicByCondition(Map<String, Object> paramMap);

    /**
     * 根据条件查询字典信息总数
     */
    Integer getSimpleDicTotalByCondition(Map<String, Object> paramMap);

    /**
     * 添加或者修改字典信息
     */
    Integer addDic(Map<String, Object> paramMap);

    /**
     * 修改字典信息
     */
    Integer updateDic(Map<String, Object> paramMap);

    /**
     * 删除字典信息
     */
    Integer deleteDic(Map<String, Object> paramMap);
}

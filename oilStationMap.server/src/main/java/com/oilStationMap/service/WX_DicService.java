package com.oilStationMap.service;

import com.oilStationMap.dto.BoolDTO;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.dto.ResultMapDTO;

import java.util.Map;

public interface WX_DicService {

    /**
     * 获取单一的字典列表For管理中心
     */
    ResultDTO getDicListByConditionForAdmin(Map<String, Object> paramMap);

    /**
     * 根据条件查询字典信息
     */
    ResultDTO getSimpleDicByCondition(Map<String, Object> paramMap);

    /**
     * 根据条件查询字典信息(支持同时查询多个字典)
     */
    ResultMapDTO getMoreDicByCondition(Map<String, Object> paramMap);

    /**
     * 获取最近的单一的字典信息
     */
    ResultDTO getLatelyDicByCondition(Map<String, Object> paramMap);

    /**
     * 添加或者修改字典信息
     */
    BoolDTO addDic(Map<String, Object> paramMap);

    /**
     * 修改字典信息
     */
    BoolDTO updateDic(Map<String, Object> paramMap);

    /**
     * 删除字典信息
     */
    BoolDTO deleteDic(Map<String, Object> paramMap);
}

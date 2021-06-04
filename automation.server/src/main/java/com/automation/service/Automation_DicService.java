package com.automation.service;

import com.automation.dto.BoolDTO;
import com.automation.dto.ResultDTO;
import com.automation.dto.ResultMapDTO;

import java.util.Map;

public interface Automation_DicService {

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

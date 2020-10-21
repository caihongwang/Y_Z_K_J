package com.oilStationMap.service;

import com.oilStationMap.dto.BoolDTO;
import com.oilStationMap.dto.ResultDTO;

import java.util.Map;

public interface WX_CommentsService {

    /**
     * 根据条件查询意见信息
     */
    ResultDTO getSimpleCommentsByCondition(Map<String, Object> paramMap);

    /**
     * 添加或者修改意见信息
     */
    BoolDTO addComments(Map<String, Object> paramMap);

    /**
     * 修改意见信息
     */
    BoolDTO updateComments(Map<String, Object> paramMap);

    /**
     * 删除意见信息
     */
    BoolDTO deleteComments(Map<String, Object> paramMap);
}

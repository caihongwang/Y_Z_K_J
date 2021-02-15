package com.oilStationMap.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Mapper
@Transactional
public interface WX_UserDao {



    /**
     * 根据条件查询用户详细信息
     */
    List<Map<String, Object>> getUserByCondition(Map<String, Object> paramMap);

    /**
     * 根据条件查询用户详细信息总数
     */
    Integer getUserTotalByCondition(Map<String, Object> paramMap);

    /**
     * 更新用户的推荐人用户uid
     */
    Integer updateUserOfRecommendUid(Map<String, Object> paramMap);

    /**
     * 根据条件查询用户信息
     */
    List<Map<String, Object>> getSimpleUserByCondition(Map<String, Object> paramMap);

    /**
     * 根据条件查询用户信息总数
     */
    Integer getSimpleUserTotalByCondition(Map<String, Object> paramMap);

    /**
     * 添加或者修改用户信息
     */
    Integer addUser(Map<String, Object> paramMap);

    /**
     * 修改用户信息
     */
    Integer updateUser(Map<String, Object> paramMap);

    /**
     * 删除用户信息
     */
    Integer deleteUser(Map<String, Object> paramMap);
}

package com.newMall.service;

import java.util.Map;

/**
 * @Description 用户Service
 * @author caihongwang
 */
public interface WX_UserService {

    /**
     * 设置用户余额是否自动提现
     *
     * @param paramMap
     * @return
     */
    com.newMall.dto.BoolDTO checkUserAutoCashBalance(Map<String, Object> paramMap);

    /**
     * 获取用户的基本信息
     *
     * @param paramMap
     * @return
     */
    com.newMall.dto.ResultMapDTO getUserBaseInfo(Map<String, Object> paramMap);

    /**
     * 添加用户
     *
     * @param paramMap
     * @return
     */
    com.newMall.dto.ResultMapDTO login(Map<String, Object> paramMap);

    /**
     * 设置用户的session
     */
    public com.newMall.dto.BoolDTO setSession(Map<String, Object> paramMap);

    /**
     * 检测用户会话是否过期
     *
     * @param paramMap
     * @return
     */
    com.newMall.dto.BoolDTO checkSession(Map<String, Object> paramMap);

    /**
     * 删除用户
     *
     * @param paramMap
     * @return
     */
    com.newMall.dto.BoolDTO deleteUser(Map<String, Object> paramMap);

    /**
     * 更新用户
     *
     * @param paramMap
     * @return
     */
    com.newMall.dto.BoolDTO updateUser(Map<String, Object> paramMap);

    /**
     * 获取单一的用户信息
     *
     * @param paramMap
     * @return
     */
    com.newMall.dto.ResultDTO getSimpleUserByCondition(Map<String, Object> paramMap);

    /**
     * 根据手机号校验验证码是否正确
     */
    com.newMall.dto.MessageDTO getCheckVerificationCode(Map<String, Object> paramMap);
}

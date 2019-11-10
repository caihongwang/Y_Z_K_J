package com.oilStationMap.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 联系人 DAO
 */
@Mapper
public interface WX_ContactDao {

    /**
     * 根据联系人名称标识查询最大的ID
     */
    Map<String, Object> getMaxIdByName(Map<String, Object> paramMap);

    /**
     * 根据手机号查询联系人是否存在
     */
    Integer checkContactByPhone(Map<String, Object> paramMap);

    /**
     * 获取所有的联系人
     */
    List<Map<String, Object>> getAllContactList(Map<String, Object> paramMap);

    /**
     * 添加或者修改联系人
     */
    Integer addContact(Map<String, Object> paramMap);
}

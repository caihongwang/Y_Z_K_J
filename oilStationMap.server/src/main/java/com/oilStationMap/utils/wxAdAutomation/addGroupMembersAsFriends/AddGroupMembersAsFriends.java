package com.oilStationMap.utils.wxAdAutomation.addGroupMembersAsFriends;

import java.util.Map;

public interface AddGroupMembersAsFriends {

    /**
     * 根据微信群昵称添加群成员为好友工具
     * @param paramMap
     * @throws Exception
     */
    public boolean addGroupMembersAsFriends(Map<String, Object> paramMap) throws Exception;
}

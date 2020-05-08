package com.oilStationMap.utils.wxAdAutomation.addGroupMembersAsFriends;

import org.apache.commons.lang.time.StopWatch;

import java.util.Map;

public interface AddGroupMembersAsFriends {

    /**
     * 根据微信群昵称添加群成员为好友工具
     * @param paramMap
     * @throws Exception
     */
    public void addGroupMembersAsFriends(Map<String, Object> paramMap, StopWatch sw) throws Exception;
}

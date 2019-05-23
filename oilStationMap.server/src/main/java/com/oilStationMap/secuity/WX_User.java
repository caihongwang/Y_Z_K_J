package com.oilStationMap.secuity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import java.util.Collection;
import java.util.Map;

public class WX_User extends User {

    private static final long serialVersionUID = 4125096758372084309L;

    public WX_User(String username, String password,
                   String uid, String openId, String nickName,
                   Map<String, String> userInfoMap, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        setUid(uid);
        setOpenId(openId);
        setNickName(nickName);
        setUserInfoMap(userInfoMap);
    }

    private String uid;

    private String nickName;

    private String openId;

    private Map<String, String> userInfoMap;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Map<String, String> getUserInfoMap() {
        return userInfoMap;
    }

    public void setUserInfoMap(Map<String, String> userInfoMap) {
        this.userInfoMap = userInfoMap;
    }
}

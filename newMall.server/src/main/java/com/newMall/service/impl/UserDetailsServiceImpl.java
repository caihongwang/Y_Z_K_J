package com.newMall.service.impl;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.newMall.dao.WX_UserDao;
import com.newMall.dto.ResultMapDTO;
import com.newMall.secuity.WX_User;
import com.newMall.service.WX_UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author bootdo
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(com.newMall.service.impl.UserDetailsServiceImpl.class);

    @Autowired
    private WX_UserDao wxUserDao;

    @Autowired
    private WX_UserService wxUserService;

    @Override
    public UserDetails loadUserByUsername(String code) throws UsernameNotFoundException {
        //获取 authenticationUser
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticationUser = (User)authentication.getPrincipal();

        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("code", code);
        paramMap.put("accountId", authenticationUser.getUsername());
        resultMapDTO = wxUserService.login(paramMap);
        //登陆后的用户信息
        Map<String, String> userInfoMap = resultMapDTO.getResultMap();
        //用户uid
        String uid = userInfoMap.get("uid");
        //查询用户基本信息
        Map<String,Object> userParam = Maps.newHashMap();
        userParam.put("id", uid);
        List<Map<String, Object>> userList = wxUserDao.getSimpleUserByCondition(userParam);
        Map<String, Object> userMap = userList.get(0);
        //用户昵称
        String nickName = userMap.get("nickName")!=null?userMap.get("nickName").toString():"默认用户";
        //用户头像
        String avatarUrl = userMap.get("avatarUrl")!=null?userMap.get("avatarUrl").toString():"https://www.91caihongwang.com/resourceOfNewMall/user/defaultavatar.png";
        //微信openId
        String openId = userMap.get("openId").toString();
        //密码
        String password = new BCryptPasswordEncoder().encode(code);
        //权限
        Set<String> perms = Sets.newHashSet();
        perms.add(userMap.get("grayStatus").toString());
        Set<GrantedAuthority> authorities = perms.stream().filter(Objects::nonNull).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        logger.info("spring UserDetailsService 中获取的用户uid="+uid+",openId="+openId);
        return new WX_User(nickName, password, uid, openId, nickName, avatarUrl, userInfoMap, authorities);
    }
}

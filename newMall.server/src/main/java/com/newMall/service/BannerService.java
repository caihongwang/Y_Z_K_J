package com.newMall.service;

import com.newMall.dto.ResultDTO;

import java.util.Map;

public interface BannerService {

    /**
     * 获取正在活跃的Banner信息
     */
    public ResultDTO getActivityBanner(Map<String, Object> paramMap);

}

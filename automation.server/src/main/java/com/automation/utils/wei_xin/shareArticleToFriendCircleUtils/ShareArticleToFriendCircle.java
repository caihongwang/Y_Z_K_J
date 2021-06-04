package com.automation.utils.wei_xin.shareArticleToFriendCircleUtils;

import java.util.Map;

public interface ShareArticleToFriendCircle {

    /**
     * 前置条件：将微信文章群发到【油站科技-内部交流群】里面
     * 分享微信文章到微信朋友圈
     * @param paramMap
     * @throws Exception
     */
    public boolean shareArticleToFriendCircle(Map<String, Object> paramMap) throws Exception;
}

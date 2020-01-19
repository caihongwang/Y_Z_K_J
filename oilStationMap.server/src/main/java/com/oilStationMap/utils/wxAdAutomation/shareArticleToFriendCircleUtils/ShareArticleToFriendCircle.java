package com.oilStationMap.utils.wxAdAutomation.shareArticleToFriendCircleUtils;

import org.apache.commons.lang.time.StopWatch;

import java.util.Map;

public interface ShareArticleToFriendCircle {

    /**
     * 前置条件：将微信文章群发到【内部交流群】里面
     * 分享微信文章到微信朋友圈
     * @param paramMap
     * @throws Exception
     */
    public void shareArticleToFriendCircle(Map<String, Object> paramMap, StopWatch sw) throws Exception;
}

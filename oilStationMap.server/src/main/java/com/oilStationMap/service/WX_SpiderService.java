package com.oilStationMap.service;

import com.oilStationMap.dto.BoolDTO;
import com.oilStationMap.dto.MessageDTO;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.dto.ResultMapDTO;

import java.util.Map;

/**
 * 爬虫 service
 */
public interface WX_SpiderService {

    /**
     * 从58二手房爬取手机号并整合入库
     * @param paramMap
     * @return
     */
    ResultMapDTO getContactFrom58ErShouFang(Map<String, Object> paramMap);
}

package com.oilStationMap.service.impl;

import com.google.common.collect.Maps;
import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.service.WX_DicService;
import com.oilStationMap.service.WX_LiveBroadcastService;
import com.oilStationMap.utils.MapUtil;
import com.oilStationMap.utils.WX_PublicNumberUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description 加盟Service
 * @author caihongwang
 */
@Service
public class WX_LiveBroadcastServiceImpl implements WX_LiveBroadcastService {

    private static final Logger logger = LoggerFactory.getLogger(WX_LiveBroadcastServiceImpl.class);

    @Autowired
    private WX_DicService wxDicService;

    /**
     * 获取直播房间列表
     * @param paramMap
     * @return
     */
    @Override
    public ResultMapDTO getLiveInfoList(Map<String, Object> paramMap) {
        String accountId = paramMap.get("accountId")!=null?paramMap.get("accountId").toString():"gh_417c90af3488";  //默认油价地图的 原始ID
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> resultMap = Maps.newHashMap();
        Map<String, Object> customMessageAccountParamMap = Maps.newHashMap();
        customMessageAccountParamMap.put("dicType", "customMessageAccount");
        customMessageAccountParamMap.put("dicCode", accountId);
        ResultDTO customMessageAccountResultDTO = wxDicService.getSimpleDicByCondition(customMessageAccountParamMap);
        if(customMessageAccountResultDTO != null && customMessageAccountResultDTO.getResultList() != null
                && customMessageAccountResultDTO.getResultList().size() > 0){
            Map<String, String> customMessageAccountMap = customMessageAccountResultDTO.getResultList().get(0);
            String customMessageAccountName = customMessageAccountMap.get("customMessageAccountName") != null ? customMessageAccountMap.get("customMessageAccountName").toString() : "";
            String customMessageAccountAppId = customMessageAccountMap.get("customMessageAccountAppId") != null ? customMessageAccountMap.get("customMessageAccountAppId").toString() : "";
            String customMessageAccountSecret = customMessageAccountMap.get("customMessageAccountSecret") != null ? customMessageAccountMap.get("customMessageAccountSecret").toString() : "";
            resultMap = WX_PublicNumberUtil.getAccessToken(customMessageAccountAppId, customMessageAccountSecret);
            Map<String, Object> liveInfoListMap = WX_PublicNumberUtil.getLiveInfoList(customMessageAccountAppId, customMessageAccountSecret);
            List<Map<String, Object>> roomInfoList = (List<Map<String, Object>>)liveInfoListMap.get("room_info");
            resultMap.put("roomInfoList", roomInfoList);
            resultMapDTO.setResultMap(MapUtil.getStringMap(resultMap));
            resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
            resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        }else{
            resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        return resultMapDTO;
    }
}

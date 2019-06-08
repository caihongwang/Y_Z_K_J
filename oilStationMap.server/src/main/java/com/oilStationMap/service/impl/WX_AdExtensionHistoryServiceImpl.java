package com.oilStationMap.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dao.WX_AdExtensionHistoryDao;
import com.oilStationMap.dto.BoolDTO;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.service.WX_CommonService;
import com.oilStationMap.service.WX_DicService;
import com.oilStationMap.service.WX_AdExtensionHistoryService;
import com.oilStationMap.utils.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description 广告推广历史Service
 * @author caihongwang
 */
@Service
public class WX_AdExtensionHistoryServiceImpl implements WX_AdExtensionHistoryService {

    private static final Logger logger = LoggerFactory.getLogger(WX_AdExtensionHistoryServiceImpl.class);

    @Autowired
    private WX_AdExtensionHistoryDao wxAdExtensionHistoryDao;

    @Autowired
    private WX_CommonService wxCommonService;

    /**
     * 添加广告推广历史
     * @param paramMap
     * @return
     */
    @Override
    public BoolDTO addAdExtensionHistory(Map<String, Object> paramMap) {
        logger.info("【service】添加广告推广历史-addAdExtensionHistory,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        Integer addNum = 0;
        BoolDTO boolDTO = new BoolDTO();
        String uid = paramMap.get("uid") != null ? paramMap.get("uid").toString() : "";
        String mediaAppId = paramMap.get("mediaAppId") != null ? paramMap.get("mediaAppId").toString() : "";
        String adAppId = paramMap.get("adAppId") != null ? paramMap.get("adAppId").toString() : "";
        String adExtensionRandomNum = paramMap.get("adExtensionRandomNum") != null ? paramMap.get("adExtensionRandomNum").toString() : "";
        String createTime = paramMap.get("createTime") != null ? paramMap.get("createTime").toString() : "";
        if (!"".equals(uid) && !"".equals(mediaAppId)
                && !"".equals(adAppId) && !"".equals(adExtensionRandomNum)
                    && !"".equals(createTime)) {
            //查看当前操作在当天是否存在过.如果存在则不记录也就意味着不计算CPC,不存在则记录
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Map<String, Object> paramMapTemp = Maps.newHashMap();
            paramMapTemp.put("uid", uid);
            paramMapTemp.put("adAppId", adAppId);
            paramMapTemp.put("createTime", formatter.format(new Date()));
            List<Map<String, Object>> exist_oilStationOperatorList =
                    wxAdExtensionHistoryDao.getSimpleAdExtensionHistoryByCondition(paramMapTemp);
            if(exist_oilStationOperatorList != null && exist_oilStationOperatorList.size() > 0){
                paramMap.put("status", "1");        //不计费的cpc状态
            } else {
                paramMap.put("status", "0");        //要计费的cpc状态
            }
            addNum = wxAdExtensionHistoryDao.addAdExtensionHistory(paramMap);
            if (addNum != null && addNum > 0) {
                boolDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                boolDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
            } else {
                boolDTO.setCode(OilStationMapCode.NO_DATA_CHANGE.getNo());
                boolDTO.setMessage(OilStationMapCode.NO_DATA_CHANGE.getMessage());

                //获取当前时间
                Date currentDate = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                paramMap.clear();//清空参数，重新准备参数
                Map<String, Object> dataMap = Maps.newHashMap();
                //标题
                Map<String, Object> firstMap = Maps.newHashMap();
                firstMap.put("value", "监测到广告推广历史存档服务出现问题");
                firstMap.put("color", "#0017F5");
                dataMap.put("keyword1", firstMap);
                //错误描述
                Map<String, Object> keyword1Map = Maps.newHashMap();
                keyword1Map.put("value", "媒体appId: "+mediaAppId+" 在广告推广历史存档时异常...");
                keyword1Map.put("color", "#0017F5");
                dataMap.put("first", keyword1Map);
                //错误详情
                Map<String, Object> keyword2Map = Maps.newHashMap();
                keyword2Map.put("value", "媒体appId: "+mediaAppId+" ,在推广广告主appId: " + adAppId+" 进行广告推广历史存档时异常，");
                keyword2Map.put("color", "#0017F5");
                dataMap.put("keyword2", keyword2Map);
                //时间
                Map<String, Object> keyword3Map = Maps.newHashMap();
                keyword3Map.put("value", sdf.format(currentDate));
                keyword3Map.put("color", "#0017F5");
                dataMap.put("keyword3", keyword3Map);
                //备注
                Map<String, Object> remarkMap = Maps.newHashMap();
                remarkMap.put("value", "媒体appId: "+mediaAppId+" ,在推广广告主appId: " + adAppId+" 进行广告推广历史存档时异常，");
                remarkMap.put("color", "#0017F5");
                dataMap.put("remark", remarkMap);
                //整合
                paramMap.put("data", JSONObject.toJSONString(dataMap));
                //发送
                paramMap.put("openId", "oJcI1wt-ibRdgri1y8qKYCRQaq8g");
                paramMap.put("template_id", "TdKDrcNW934K0r1rtlDKCUI0XCQ5xb4GGb8ieHb0zug"); //服务器报错提醒
                wxCommonService.sendTemplateMessageForWxPublicNumber(paramMap);
            }
        } else {
            boolDTO.setCode(OilStationMapCode.LEAGUE_UID_OR_PHONE_OR_NAME_OR_LEAGUETYPECODE_IS_NOT_NULL.getNo());
            boolDTO.setMessage(OilStationMapCode.LEAGUE_UID_OR_PHONE_OR_NAME_OR_LEAGUETYPECODE_IS_NOT_NULL.getMessage());
        }
        logger.info("【service】添加广告推广历史-addAdExtensionHistory,响应-boolDTO = {}", JSONObject.toJSONString(boolDTO));
        return boolDTO;
    }

    /**
     * 删除广告推广历史
     * @param paramMap
     * @return
     */
    @Override
    public BoolDTO deleteAdExtensionHistory(Map<String, Object> paramMap) {
        logger.info("【service】删除广告推广历史-deleteAdExtensionHistory,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        Integer deleteNum = 0;
        BoolDTO boolDTO = new BoolDTO();
        String id = paramMap.get("id") != null ? paramMap.get("id").toString() : "";
        if (!"".equals(id)) {
            deleteNum = wxAdExtensionHistoryDao.deleteAdExtensionHistory(paramMap);
            if (deleteNum != null && deleteNum > 0) {
                boolDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                boolDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
            } else {
                boolDTO.setCode(OilStationMapCode.NO_DATA_CHANGE.getNo());
                boolDTO.setMessage(OilStationMapCode.NO_DATA_CHANGE.getMessage());
            }
        } else {
            boolDTO.setCode(OilStationMapCode.LEAGUE_ID_IS_NOT_NULL.getNo());
            boolDTO.setMessage(OilStationMapCode.LEAGUE_ID_IS_NOT_NULL.getMessage());
        }
        logger.info("【service】删除广告推广历史-deleteAdExtensionHistory,响应-boolDTO = {}", JSONObject.toJSONString(boolDTO));
        return boolDTO;
    }

    /**
     * 修改广告推广历史
     * @param paramMap
     * @return
     */
    @Override
    public BoolDTO updateAdExtensionHistory(Map<String, Object> paramMap) {
        logger.info("【service】修改广告推广历史-updateAdExtensionHistory,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        Integer updateNum = 0;
        BoolDTO boolDTO = new BoolDTO();
        String id = paramMap.get("id") != null ? paramMap.get("id").toString() : "";
        if (!"".equals(id)) {
            updateNum = wxAdExtensionHistoryDao.updateAdExtensionHistory(paramMap);
            if (updateNum != null && updateNum > 0) {
                boolDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                boolDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
            } else {
                boolDTO.setCode(OilStationMapCode.NO_DATA_CHANGE.getNo());
                boolDTO.setMessage(OilStationMapCode.NO_DATA_CHANGE.getMessage());
            }
        } else {
            boolDTO.setCode(OilStationMapCode.LEAGUE_ID_IS_NOT_NULL.getNo());
            boolDTO.setMessage(OilStationMapCode.LEAGUE_ID_IS_NOT_NULL.getMessage());
        }
        logger.info("【service】修改广告推广历史-updateAdExtensionHistory,响应-boolDTO = {}", JSONObject.toJSONString(boolDTO));
        return boolDTO;
    }

    /**
     * 获取单一的广告推广历史信息
     * @param paramMap
     * @return
     */
    @Override
    public ResultDTO getSimpleAdExtensionHistoryByCondition(Map<String, Object> paramMap) {
        logger.info("【service】获取单一的广告推广历史-getSimpleAdExtensionHistoryByCondition,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        ResultDTO resultDTO = new ResultDTO();
        List<Map<String, String>> leagueStrList = Lists.newArrayList();
        Boolean isQueryListFlag = paramMap.get("isQueryListFlag") != null ? Boolean.parseBoolean(paramMap.get("isQueryListFlag").toString()) : false;
        List<Map<String, Object>> leagueList = wxAdExtensionHistoryDao.getSimpleAdExtensionHistoryByCondition(paramMap);
        if ((leagueList != null && leagueList.size() > 0)) {
            if(isQueryListFlag){
                leagueList = Lists.newArrayList();
            }
            leagueStrList = MapUtil.getStringMapList(leagueList);
            Integer total = wxAdExtensionHistoryDao.getSimpleAdExtensionHistoryTotalByCondition(paramMap);
            resultDTO.setResultListTotal(total);
            resultDTO.setResultList(leagueStrList);
            resultDTO.setCode(OilStationMapCode.SUCCESS.getNo());
            resultDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        } else {
            List<Map<String, String>> resultList = Lists.newArrayList();
            resultDTO.setResultListTotal(0);
            resultDTO.setResultList(resultList);
            resultDTO.setCode(OilStationMapCode.LEAGUE_LIST_IS_NULL.getNo());
            resultDTO.setMessage(OilStationMapCode.LEAGUE_LIST_IS_NULL.getMessage());
        }
        logger.info("【service】获取单一的广告推广历史-getSimpleAdExtensionHistoryByCondition,响应-resultDTO = {}", JSONObject.toJSONString(resultDTO));
        return resultDTO;
    }
}

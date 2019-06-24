package com.oilStationMap.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.BoolDTO;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.service.WX_CommonService;
import com.oilStationMap.service.WX_DicService;
import com.oilStationMap.service.WX_LeagueService;
import com.oilStationMap.service.WX_MessageService;
import com.oilStationMap.utils.MapUtil;
import com.oilStationMap.dao.WX_LeagueDao;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description 加盟Service
 * @author caihongwang
 */
@Service
public class WX_LeagusServiceImpl implements WX_LeagueService {

    private static final Logger logger = LoggerFactory.getLogger(WX_LeagusServiceImpl.class);

    @Autowired
    private WX_LeagueDao wxLeagueDao;

    @Autowired
    private WX_DicService wxDicService;

    @Autowired
    private WX_MessageService wxMessageService;

    /**
     * 获取加盟类型列表
     * @param paramMap
     * @return
     */
    @Override
    public ResultDTO getLeagueTypeList(Map<String, Object> paramMap) {
        logger.info("【service】获取加盟类型列表-getLeagueTypeList,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        ResultDTO resultDTO = new ResultDTO();
        String dicType = paramMap.get("dicType") != null ? paramMap.get("dicType").toString() : "leagueType";
        if(!"".equals(dicType)){
            paramMap.put("dicType", dicType);
            resultDTO = wxDicService.getSimpleDicByCondition(paramMap);
        } else {
            resultDTO.setCode(OilStationMapCode.LEAGUE_TYPE_IS_NULL.getNo());
            resultDTO.setMessage(OilStationMapCode.LEAGUE_TYPE_IS_NULL.getMessage());
        }
        logger.info("【service】获取加盟类型列表-getLeagueTypeList,响应-resultDTO = {}", JSONObject.toJSONString(resultDTO));
        return resultDTO;
    }

    /**
     * 添加加盟
     * @param paramMap
     * @return
     */
    @Override
    public BoolDTO addLeague(Map<String, Object> paramMap) {
        logger.info("【service】添加加盟-addLeague,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        Integer addNum = 0;
        BoolDTO boolDTO = new BoolDTO();
        String uid = paramMap.get("uid") != null ? paramMap.get("uid").toString() : "";
        String phone = paramMap.get("phone") != null ? paramMap.get("phone").toString() : "";
        String name = paramMap.get("name") != null ? paramMap.get("name").toString() : "";
        String remark = paramMap.get("remark") != null ? paramMap.get("remark").toString() : "";
        String leagueTypeCode = paramMap.get("leagueTypeCode") != null ? paramMap.get("leagueTypeCode").toString() : "";
        if (!"".equals(uid) && !"".equals(phone)
                && !"".equals(name) && !"".equals(leagueTypeCode)) {
            addNum = wxLeagueDao.addLeague(paramMap);
            if (addNum != null && addNum > 0) {
                new Thread(){
                    public void run(){
                        try{
                            wxMessageService.dailyLeagueMessageSend(paramMap);
                        } catch (Exception e) {
                            logger.info("发送加盟消息是异常，e ：", e);
                        }
                    }
                }.start();
                boolDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                boolDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
            } else {
                boolDTO.setCode(OilStationMapCode.NO_DATA_CHANGE.getNo());
                boolDTO.setMessage(OilStationMapCode.NO_DATA_CHANGE.getMessage());
            }
        } else {
            boolDTO.setCode(OilStationMapCode.LEAGUE_UID_OR_PHONE_OR_NAME_OR_LEAGUETYPECODE_IS_NOT_NULL.getNo());
            boolDTO.setMessage(OilStationMapCode.LEAGUE_UID_OR_PHONE_OR_NAME_OR_LEAGUETYPECODE_IS_NOT_NULL.getMessage());
        }
        logger.info("【service】添加加盟-addLeague,响应-boolDTO = {}", JSONObject.toJSONString(boolDTO));
        return boolDTO;
    }

    /**
     * 删除加盟
     * @param paramMap
     * @return
     */
    @Override
    public BoolDTO deleteLeague(Map<String, Object> paramMap) {
        logger.info("【service】删除加盟-deleteLeague,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        Integer deleteNum = 0;
        BoolDTO boolDTO = new BoolDTO();
        String id = paramMap.get("id") != null ? paramMap.get("id").toString() : "";
        if (!"".equals(id)) {
            deleteNum = wxLeagueDao.deleteLeague(paramMap);
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
        logger.info("【service】删除加盟-deleteLeague,响应-boolDTO = {}", JSONObject.toJSONString(boolDTO));
        return boolDTO;
    }

    /**
     * 修改加盟
     * @param paramMap
     * @return
     */
    @Override
    public BoolDTO updateLeague(Map<String, Object> paramMap) {
        logger.info("【service】修改加盟-updateLeague,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        Integer updateNum = 0;
        BoolDTO boolDTO = new BoolDTO();
        String id = paramMap.get("id") != null ? paramMap.get("id").toString() : "";
        if (!"".equals(id)) {
            updateNum = wxLeagueDao.updateLeague(paramMap);
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
        logger.info("【service】修改加盟-updateLeague,响应-boolDTO = {}", JSONObject.toJSONString(boolDTO));
        return boolDTO;
    }

    /**
     * 获取单一的加盟信息
     * @param paramMap
     * @return
     */
    @Override
    public ResultDTO getSimpleLeagueByCondition(Map<String, Object> paramMap) {
        logger.info("【service】获取单一的加盟-getSimpleLeagueByCondition,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        ResultDTO resultDTO = new ResultDTO();
        List<Map<String, String>> leagueStrList = Lists.newArrayList();
        Boolean isQueryListFlag = paramMap.get("isQueryListFlag") != null ? Boolean.parseBoolean(paramMap.get("isQueryListFlag").toString()) : false;
        List<Map<String, Object>> leagueList = wxLeagueDao.getSimpleLeagueByCondition(paramMap);
        if ((leagueList != null && leagueList.size() > 0)) {
            if(isQueryListFlag){
                leagueList = Lists.newArrayList();
            }
            leagueStrList = MapUtil.getStringMapList(leagueList);
            Integer total = wxLeagueDao.getSimpleLeagueTotalByCondition(paramMap);
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
        logger.info("【service】获取单一的加盟-getSimpleLeagueByCondition,响应-resultDTO = {}", JSONObject.toJSONString(resultDTO));
        return resultDTO;
    }
}

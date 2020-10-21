package com.oilStationMap.service.impl;

import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.BoolDTO;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.service.WX_RedPacketHistoryService;
import com.oilStationMap.utils.MapUtil;
import com.oilStationMap.dao.WX_RedPacketHistoryDao;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 红包领取记录service
 */
@Service
public class WX_RedPacketHistoryServiceImpl implements WX_RedPacketHistoryService {

    private static final Logger logger = LoggerFactory.getLogger(WX_RedPacketHistoryServiceImpl.class);

    @Autowired
    private WX_RedPacketHistoryDao wxRedPacketHistoryDao;

    /**
     * 获取已提现红包总额
     *
     * @param paramMap
     * @return
     */
    @Override
    public ResultMapDTO getAllRedPacketMoneyTotal(Map<String, Object> paramMap) {
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        String phone = paramMap.get("phone") != null ? paramMap.get("phone").toString() : "";
        String uid = paramMap.get("uid") != null ? paramMap.get("uid").toString() : "";
        if (!"".equals(phone) || !"".equals(uid)) {
            Map<String, Object> redPacketMoneyTotalMap = Maps.newHashMap();
            Double allRedPacketMoneyTotal = wxRedPacketHistoryDao.getAllRedPacketMoneyTotal(paramMap);
            redPacketMoneyTotalMap.put("allRedPacketMoneyTotal", allRedPacketMoneyTotal);
            if (redPacketMoneyTotalMap != null && redPacketMoneyTotalMap.size() > 0) {
                resultMapDTO.setResultMap(MapUtil.getStringMap(redPacketMoneyTotalMap));

                resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
            } else {

                resultMapDTO.setCode(OilStationMapCode.WX_RED_PACKET__HISTORY_IS_NULL.getNo());
                resultMapDTO.setMessage(OilStationMapCode.WX_RED_PACKET__HISTORY_IS_NULL.getMessage());
            }
        } else {

            resultMapDTO.setCode(OilStationMapCode.WX_RED_PACKET__UID_OR_MONEY_IS_NOT_NULL.getNo());
            resultMapDTO.setMessage(OilStationMapCode.WX_RED_PACKET__UID_OR_MONEY_IS_NOT_NULL.getMessage());
        }
        logger.info("在service中获取已领取红包总额-getAllRedPacketMoneyTotal,结果-result:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 获取红包领取记录信息
     *
     * @param paramMap
     * @return
     */
    @Override
    public ResultDTO getRedPacketHistoryList(Map<String, Object> paramMap) {
        ResultDTO resultDTO = new ResultDTO();
        List<Map<String, String>> redPacketDrawCashHistoryStrList = Lists.newArrayList();
        String phone = paramMap.get("phone") != null ? paramMap.get("phone").toString() : "";
        String uid = paramMap.get("uid") != null ? paramMap.get("uid").toString() : "";
        if (!"".equals(phone) || !"".equals(uid)) {
            List<Map<String, Object>> redPacketDrawCashHistoryList = wxRedPacketHistoryDao.getRedPacketHistoryList(paramMap);
            if (redPacketDrawCashHistoryList != null && redPacketDrawCashHistoryList.size() > 0) {
                redPacketDrawCashHistoryStrList = MapUtil.getStringMapList(redPacketDrawCashHistoryList);
                Integer total = wxRedPacketHistoryDao.getRedPacketHistoryTotal(paramMap);
                Double allRedPacketMoneyTotal = wxRedPacketHistoryDao.getAllRedPacketMoneyTotal(paramMap);
                resultDTO.setAllRedPacketMoneyTotal(allRedPacketMoneyTotal!=null?allRedPacketMoneyTotal.toString():"0");
                resultDTO.setResultListTotal(total!=null?total:0);
                resultDTO.setResultList(redPacketDrawCashHistoryStrList);

                resultDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                resultDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
            } else {
                List<Map<String, String>> resultList = Lists.newArrayList();
                resultDTO.setResultListTotal(0);
                resultDTO.setResultList(resultList);

                resultDTO.setCode(OilStationMapCode.WX_RED_PACKET__HISTORY_IS_NULL.getNo());
                resultDTO.setMessage(OilStationMapCode.WX_RED_PACKET__HISTORY_IS_NULL.getMessage());
            }
        } else {

            resultDTO.setCode(OilStationMapCode.WX_RED_PACKET__UID_OR_MONEY_IS_NOT_NULL.getNo());
            resultDTO.setMessage(OilStationMapCode.WX_RED_PACKET__UID_OR_MONEY_IS_NOT_NULL.getMessage());
        }
        logger.info("在service中获取单一的红包提现记录信息-getRedPacketHistoryList,结果-result:" + resultDTO);
        return resultDTO;
    }

    /**
     * 添加红包领取记录
     *
     * @param paramMap
     * @return
     */
    @Override
    public BoolDTO addRedPacketHistory(Map<String, Object> paramMap) {
        Integer addNum = 0;
        BoolDTO boolDTO = new BoolDTO();
        String uid = paramMap.get("uid") != null ? paramMap.get("uid").toString() : "";
        String redPacketMoney = paramMap.get("redPacketMoney") != null ? paramMap.get("redPacketMoney").toString() : "";
        if (!"".equals(uid) && !"".equals(redPacketMoney)) {
            addNum = wxRedPacketHistoryDao.addRedPacketHistory(paramMap);
            if (addNum != null && addNum > 0) {

                boolDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                boolDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
            } else {

                boolDTO.setCode(OilStationMapCode.NO_DATA_CHANGE.getNo());
                boolDTO.setMessage(OilStationMapCode.NO_DATA_CHANGE.getMessage());
            }
        } else {

            boolDTO.setCode(OilStationMapCode.WX_RED_PACKET__UID_OR_MONEY_IS_NOT_NULL.getNo());
            boolDTO.setMessage(OilStationMapCode.WX_RED_PACKET__UID_OR_MONEY_IS_NOT_NULL.getMessage());
        }
        logger.info("在service中添加红包领取记录-addRedPacketHistory,结果-result:" + boolDTO);
        return boolDTO;
    }

    /**
     * 删除红包领取记录
     *
     * @param paramMap
     * @return
     */
    @Override
    public BoolDTO deleteRedPacketHistory(Map<String, Object> paramMap) {
        Integer deleteNum = 0;
        BoolDTO boolDTO = new BoolDTO();
        String id = paramMap.get("id") != null ? paramMap.get("id").toString() : "";
        String uid = paramMap.get("uid") != null ? paramMap.get("uid").toString() : "";
        if (!"".equals(id) || !"".equals(uid)) {
            deleteNum = wxRedPacketHistoryDao.deleteRedPacketHistory(paramMap);
            if (deleteNum != null && deleteNum > 0) {

                boolDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                boolDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
            } else {

                boolDTO.setCode(OilStationMapCode.NO_DATA_CHANGE.getNo());
                boolDTO.setMessage(OilStationMapCode.NO_DATA_CHANGE.getMessage());
            }
        } else {

            boolDTO.setCode(OilStationMapCode.WX_RED_PACKET__UID_OR_MONEY_IS_NOT_NULL.getNo());
            boolDTO.setMessage(OilStationMapCode.WX_RED_PACKET__UID_OR_MONEY_IS_NOT_NULL.getMessage());
        }
        logger.info("在service中删除红包领取记录-deleteRedPacketHistory,结果-result:" + boolDTO);
        return boolDTO;
    }

    /**
     * 更新红包领取记录
     *
     * @param paramMap
     * @return
     */
    @Override
    public BoolDTO updateRedPacketHistory(Map<String, Object> paramMap) {
        Integer updateNum = 0;
        BoolDTO boolDTO = new BoolDTO();
        String id = paramMap.get("id") != null ? paramMap.get("id").toString() : "";
        String uid = paramMap.get("uid") != null ? paramMap.get("uid").toString() : "";
        if (!"".equals(id) || !"".equals(uid)) {
            updateNum = wxRedPacketHistoryDao.updateRedPacketHistory(paramMap);
            if (updateNum != null && updateNum > 0) {

                boolDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                boolDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
            } else {

                boolDTO.setCode(OilStationMapCode.NO_DATA_CHANGE.getNo());
                boolDTO.setMessage(OilStationMapCode.NO_DATA_CHANGE.getMessage());
            }
        } else {

            boolDTO.setCode(OilStationMapCode.WX_RED_PACKET__UID_OR_MONEY_IS_NOT_NULL.getNo());
            boolDTO.setMessage(OilStationMapCode.WX_RED_PACKET__UID_OR_MONEY_IS_NOT_NULL.getMessage());
        }
        logger.info("在service中更新红包领取记录-updateRedPacketHistory,结果-result:" + boolDTO);
        return boolDTO;
    }

    /**
     * 获取单一的红包领取记录信息
     *
     * @param paramMap
     * @return
     */
    @Override
    public ResultDTO getSimpleRedPacketHistoryByCondition(Map<String, Object> paramMap) {
        ResultDTO resultDTO = new ResultDTO();
        List<Map<String, String>> redPacketHistoryStrList = Lists.newArrayList();
        String id = paramMap.get("id") != null ? paramMap.get("id").toString() : "";
        String uid = paramMap.get("uid") != null ? paramMap.get("uid").toString() : "";
        if (!"".equals(uid) || !"".equals(uid)) {
            List<Map<String, Object>> redPacketHistoryList = wxRedPacketHistoryDao.getSimpleRedPacketHistoryByCondition(paramMap);
            if (redPacketHistoryList != null && redPacketHistoryList.size() > 0) {
                redPacketHistoryStrList = MapUtil.getStringMapList(redPacketHistoryList);
                Integer total = wxRedPacketHistoryDao.getSimpleRedPacketHistoryTotalByCondition(paramMap);
                resultDTO.setResultListTotal(total);
                resultDTO.setResultList(redPacketHistoryStrList);

                resultDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                resultDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
            } else {
                List<Map<String, String>> resultList = Lists.newArrayList();
                resultDTO.setResultListTotal(0);
                resultDTO.setResultList(resultList);

                resultDTO.setCode(OilStationMapCode.WX_RED_PACKET__HISTORY_IS_NULL.getNo());
                resultDTO.setMessage(OilStationMapCode.WX_RED_PACKET__HISTORY_IS_NULL.getMessage());
            }
        } else {

            resultDTO.setCode(OilStationMapCode.WX_RED_PACKET__UID_OR_MONEY_IS_NOT_NULL.getNo());
            resultDTO.setMessage(OilStationMapCode.WX_RED_PACKET__UID_OR_MONEY_IS_NOT_NULL.getMessage());
        }
        logger.info("在service中获取单一的红包领取记录信息-getSimpleRedPacketHistoryByCondition,结果-result:" + resultDTO);
        return resultDTO;
    }

}

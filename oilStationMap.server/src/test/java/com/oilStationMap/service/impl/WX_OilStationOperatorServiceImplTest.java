package com.oilStationMap.service.impl;

import com.oilStationMap.MySuperTest;
import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.service.WX_DicService;
import com.oilStationMap.service.WX_RedPacketHistoryService;
import com.oilStationMap.service.WX_RedPacketService;
import com.oilStationMap.utils.MapUtil;
import com.oilStationMap.dao.WX_OilStationOperatorDao;
import com.oilStationMap.dao.WX_RedPacketHistoryDao;
import com.oilStationMap.dao.WX_UserDao;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by caihongwang on 2019/4/4.
 */
public class WX_OilStationOperatorServiceImplTest extends MySuperTest {

    private static final Logger logger = LoggerFactory.getLogger(WX_RedPacketHistoryServiceImpl.class);

    @Autowired
    private WX_RedPacketHistoryDao wxRedPacketHistoryDao;

    @Autowired
    private WX_UserDao wxUserDao;

    @Autowired
    private WX_DicService wxDicService;

    @Autowired
    private WX_OilStationOperatorDao wxOilStationOperatorDao;

    @Autowired
    private WX_RedPacketService wxRedPacketService;

    @Autowired
    private WX_RedPacketHistoryService wxRedPacketHistoryService;


    @Test
    public void TEST() throws Exception {
//        Map<String, Object> paramMap = Maps.newHashMap();
//        paramMap.put("uid", 1);
//        paramMap.put("oilStationCode", "61210");
//        paramMap.put("operator", "updateOilStation");
//        this.addOilStationOperator(paramMap);

//        Map<String, Object> paramMap = Maps.newHashMap();
//        paramMap.put("uid", 1);
//        this.getRedPacketHistoryList(paramMap);
    }

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

    public ResultMapDTO addOilStationOperator(Map<String, Object> paramMap) {
        Integer addNum = 0;
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        String uid = paramMap.get("uid") != null ? paramMap.get("uid").toString() : "";
        String oilStationCode = paramMap.get("oilStationCode") != null ? paramMap.get("oilStationCode").toString() : "";
        String operator = paramMap.get("operator") != null ? paramMap.get("operator").toString() : "";
        if (!"".equals(uid) && !"".equals(oilStationCode) && !"".equals(operator)) {
            //1.根据operator获取红包金额redPacketTotal
            String redPacketTotal = "0";
            Map<String, Object> dicParamMap = Maps.newHashMap();
            dicParamMap.put("dicCode", operator);
            dicParamMap.put("dicType", "oilStationOperator");
            ResultDTO dicResultDto = wxDicService.getSimpleDicByCondition(dicParamMap);
            if (dicResultDto.getResultList() != null
                    && dicResultDto.getResultList().size() > 0) {
                Object redPacketTotalObj = dicResultDto.getResultList().get(0).get("redPacketTotal");
                redPacketTotal = redPacketTotalObj!=null?redPacketTotalObj.toString():"0";
            } else {
                redPacketTotal = "0";
            }
            paramMap.put("status", 0);//状态，-1表示审核拒绝, 0表示待审核, 1表示审核通过且待处理, 2表示已处理
            paramMap.put("redPacketTotal", redPacketTotal);
            //2.保存操作
            //2.1查看当前操作在当天是否存在过.如果存在则不记录也就意味着不发红包,不存在则记录
            if(!"recommendUser".equals(operator)){
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Map<String, Object> paramMapTemp = Maps.newHashMap();
                paramMapTemp.put("uid", uid);
                paramMapTemp.put("operator", operator);
//            paramMapTemp.put("oilStationCode", oilStationCode);
                paramMapTemp.put("createTime", formatter.format(new Date()));
                List<Map<String, Object>> exist_oilStationOperatorList = wxOilStationOperatorDao.getSimpleOilStationOperatorByCondition(paramMapTemp);
                if(exist_oilStationOperatorList != null && exist_oilStationOperatorList.size() > 0){
                    paramMap.put("status", "-1");       //已拒绝
                } else {
                    paramMap.put("status", "0");        //待审核
                }
            } else {
                paramMap.put("status", "0");        //待审核
            }
            //2.2记录加油站操作
            addNum = wxOilStationOperatorDao.addOilStationOperator(paramMap);
            if (addNum != null && addNum > 0) {

                resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
            } else {

                resultMapDTO.setCode(OilStationMapCode.NO_DATA_CHANGE.getNo());
                resultMapDTO.setMessage(OilStationMapCode.NO_DATA_CHANGE.getMessage());
            }
        } else {

            resultMapDTO.setCode(OilStationMapCode.OIL_STATION_OPERATOR_UID_OILSTATIONCODE_OPERATOR_IS_NOT_NULL.getNo());
            resultMapDTO.setMessage(OilStationMapCode.OIL_STATION_OPERATOR_UID_OILSTATIONCODE_OPERATOR_IS_NOT_NULL.getMessage());
        }
        logger.info("在service中添加对加油站的操作-addOilStationOperator,结果-result:" + resultMapDTO);
        return resultMapDTO;
    }

}

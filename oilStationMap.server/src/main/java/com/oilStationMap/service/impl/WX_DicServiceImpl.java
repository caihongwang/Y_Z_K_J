package com.oilStationMap.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.BoolDTO;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.service.WX_DicService;
import com.oilStationMap.utils.MapUtil;
import com.oilStationMap.dao.WX_DicDao;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 字典service
 */
@Service
public class WX_DicServiceImpl implements WX_DicService {

    private static final Logger logger = LoggerFactory.getLogger(WX_DicServiceImpl.class);

    @Autowired
    private WX_DicDao wxDicDao;

    /**
     * 添加字典
     *
     * @param paramMap
     * @return
     */
    @Override
    public BoolDTO addDic(Map<String, Object> paramMap) {
        Integer addNum = 0;
        BoolDTO boolDTO = new BoolDTO();
        String dicType = paramMap.get("dicType") != null ? paramMap.get("dicType").toString() : "";
        String dicCode = paramMap.get("dicCode") != null ? paramMap.get("dicCode").toString() : "";
        String dicName = paramMap.get("dicName") != null ? paramMap.get("dicName").toString() : "";
        if (!"".equals(dicType) && !"".equals(dicCode) && !"".equals(dicName)) {
            Map<String, Object> paramMap_temp = Maps.newHashMap();
            paramMap_temp.put("dicCode", dicCode);
            paramMap_temp.put("dicStatus", "0");
            Integer total = wxDicDao.getSimpleDicTotalByCondition(paramMap_temp);
            if (total != null && total <= 0) {
                addNum = wxDicDao.addDic(paramMap);
                if (addNum != null && addNum > 0) {
                    boolDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                    boolDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
                } else {
                    boolDTO.setCode(OilStationMapCode.NO_DATA_CHANGE.getNo());
                    boolDTO.setMessage(OilStationMapCode.NO_DATA_CHANGE.getMessage());
                }
            } else {

                boolDTO.setCode(OilStationMapCode.DIC_EXIST.getNo());
                boolDTO.setMessage(OilStationMapCode.DIC_EXIST.getMessage());
            }
        } else {

            boolDTO.setCode(OilStationMapCode.DIC_TYPE_OR_CODE_OR_NAME_IS_NOT_NULL.getNo());
            boolDTO.setMessage(OilStationMapCode.DIC_TYPE_OR_CODE_OR_NAME_IS_NOT_NULL.getMessage());
        }
        logger.info("在service中添加字典-addDic,结果-result:" + boolDTO);
        return boolDTO;
    }

    /**
     * 删除字典
     *
     * @param paramMap
     * @return
     */
    @Override
    public BoolDTO deleteDic(Map<String, Object> paramMap) {
        Integer deleteNum = 0;
        BoolDTO boolDTO = new BoolDTO();
        String id = paramMap.get("id") != null ? paramMap.get("id").toString() : "";
        String dicCode = paramMap.get("dicCode") != null ? paramMap.get("dicCode").toString() : "";
        if (!"".equals(id) || !"".equals(dicCode)) {
            deleteNum = wxDicDao.deleteDic(paramMap);
            if (deleteNum != null && deleteNum > 0) {

                boolDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                boolDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
            } else {

                boolDTO.setCode(OilStationMapCode.NO_DATA_CHANGE.getNo());
                boolDTO.setMessage(OilStationMapCode.NO_DATA_CHANGE.getMessage());
            }
        } else {

            boolDTO.setCode(OilStationMapCode.DIC_ID_OR_CODE_IS_NOT_NULL.getNo());
            boolDTO.setMessage(OilStationMapCode.DIC_ID_OR_CODE_IS_NOT_NULL.getMessage());
        }
        logger.info("在service中删除字典-deleteDic,结果-result:" + boolDTO);
        return boolDTO;
    }

    /**
     * 更新字典
     *
     * @param paramMap
     * @return
     */
    @Override
    public BoolDTO updateDic(Map<String, Object> paramMap) {
        Integer updateNum = 0;
        BoolDTO boolDTO = new BoolDTO();
        String id = paramMap.get("id") != null ? paramMap.get("id").toString() : "";
        String dicCode = paramMap.get("dicCode") != null ? paramMap.get("dicCode").toString() : "";
        if (!"".equals(id) || !"".equals(dicCode)) {
            updateNum = wxDicDao.updateDic(paramMap);
            if (updateNum != null && updateNum > 0) {

                boolDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                boolDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
            } else {

                boolDTO.setCode(OilStationMapCode.NO_DATA_CHANGE.getNo());
                boolDTO.setMessage(OilStationMapCode.NO_DATA_CHANGE.getMessage());
            }
        } else {

            boolDTO.setCode(OilStationMapCode.DIC_ID_OR_CODE_IS_NOT_NULL.getNo());
            boolDTO.setMessage(OilStationMapCode.DIC_ID_OR_CODE_IS_NOT_NULL.getMessage());
        }
        logger.info("在service中更新字典-updateDic,结果-result:" + boolDTO);
        return boolDTO;
    }

    /**
     * 获取最近的单一的字典信息
     * @param paramMap
     * @return
     */
    @Override
    public ResultDTO getLatelyDicByCondition(Map<String, Object> paramMap) {
        ResultDTO resultDTO = new ResultDTO();
        List<Map<String, String>> dicStrList = Lists.newArrayList();
        String dicType = paramMap.get("dicType") != null ? paramMap.get("dicType").toString() : "";
        String dicCode = paramMap.get("dicCode") != null ? paramMap.get("dicCode").toString() : "";
        if (!"".equals(dicType) || !"".equals(dicCode)) {
            List<Map<String, Object>> dicList = wxDicDao.getLatelyDicByCondition(paramMap);
            if (dicList != null && dicList.size() > 0) {
                for (Map<String, Object> dicMap : dicList) {
                    String dicRemark = dicMap.get("dicRemark") != null ? dicMap.get("dicRemark").toString() : "";
                    if (!"".equals(dicRemark)) {
                        Map<String, Object> dicRemarkMap = JSONObject.parseObject(dicRemark, Map.class);
                        dicMap.remove("dicRemark");
                        dicMap.putAll(dicRemarkMap);
                    }
                }
                dicStrList = MapUtil.getStringMapList(dicList);
                Integer total = wxDicDao.getLatelyDicTotalByCondition(paramMap);
                resultDTO.setResultListTotal(total);
                resultDTO.setResultList(dicStrList);
                resultDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                resultDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
            } else {
                List<Map<String, String>> resultList = Lists.newArrayList();
                resultDTO.setResultListTotal(0);
                resultDTO.setResultList(resultList);
                resultDTO.setCode(OilStationMapCode.DIC_LIST_IS_NULL.getNo());
                resultDTO.setMessage(OilStationMapCode.DIC_LIST_IS_NULL.getMessage());
            }
        } else {
            resultDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在service中获取单一的字典信息-getSimpleDicByCondition,结果-result:" + resultDTO);
        return resultDTO;
    }

    /**
     * 获取单一的字典信息
     *
     * @param paramMap
     * @return
     */
    @Override
    public ResultDTO getSimpleDicByCondition(Map<String, Object> paramMap) {
        ResultDTO resultDTO = new ResultDTO();
        List<Map<String, String>> dicStrList = Lists.newArrayList();
        String dicType = paramMap.get("dicType") != null ? paramMap.get("dicType").toString() : "";
        String dicCode = paramMap.get("dicCode") != null ? paramMap.get("dicCode").toString() : "";
        if (!"".equals(dicType) || !"".equals(dicCode)) {
            List<Map<String, Object>> dicList = wxDicDao.getSimpleDicByCondition(paramMap);
            if (dicList != null && dicList.size() > 0) {
                for (Map<String, Object> dicMap : dicList) {
                    String dicRemark = dicMap.get("dicRemark") != null ? dicMap.get("dicRemark").toString() : "";
                    if (!"".equals(dicRemark)) {
                        Map<String, Object> dicRemarkMap = JSONObject.parseObject(dicRemark, Map.class);
                        dicMap.remove("dicRemark");
                        dicMap.putAll(dicRemarkMap);
                    }
                }
                dicStrList = MapUtil.getStringMapList(dicList);
                Integer total = wxDicDao.getSimpleDicTotalByCondition(paramMap);
                resultDTO.setResultListTotal(total);
                resultDTO.setResultList(dicStrList);
                resultDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                resultDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
            } else {
                List<Map<String, String>> resultList = Lists.newArrayList();
                resultDTO.setResultListTotal(0);
                resultDTO.setResultList(resultList);
                resultDTO.setCode(OilStationMapCode.DIC_LIST_IS_NULL.getNo());
                resultDTO.setMessage(OilStationMapCode.DIC_LIST_IS_NULL.getMessage());
            }
        } else {
            resultDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在service中获取单一的字典信息-getSimpleDicByCondition,结果-result:" + resultDTO);
        return resultDTO;
    }

    /**
     * 获取单一的字典信息(支持同时查询多个字典)
     * dicTypes可以传送多个参数，使用英文逗号(,)分隔
     *
     * @param paramMap
     * @return
     */
    @Override
    public ResultMapDTO getMoreDicByCondition(Map<String, Object> paramMap) {
        Integer total = 0;
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, String> resultMap = Maps.newHashMap();
        List<Map<String, String>> dicStrList = Lists.newArrayList();
        String dicTypeStr = paramMap.get("dicTypes") != null ? paramMap.get("dicTypes").toString() : "";
        if (!"".equals(dicTypeStr)) {
            if (dicTypeStr.contains(",")) {          //同事查询多个字典
                String[] dicTypeArr = dicTypeStr.split(",");
                for (String dicType : dicTypeArr) {
                    paramMap.put("dicType", dicType);
                    List<Map<String, Object>> dicList = wxDicDao.getSimpleDicByCondition(paramMap);
                    if (dicList != null && dicList.size() > 0) {
                        for (Map<String, Object> dicMap : dicList) {
                            String dicRemark = dicMap.get("dicRemark") != null ? dicMap.get("dicRemark").toString() : "";
                            if (!"".equals(dicRemark)) {
                                Map<String, Object> dicRemarkMap = JSONObject.parseObject(dicRemark, Map.class);
                                dicMap.remove("dicRemark");
                                dicMap.putAll(dicRemarkMap);
                            }
                        }
                        resultMap.put(dicType, JSONObject.toJSONString(dicList));
                        total += wxDicDao.getSimpleDicTotalByCondition(paramMap);
                    } else {
                        dicList = Lists.newArrayList();
                        resultMap.put("dicType", JSONObject.toJSONString(dicList));
                    }

                }
                resultMapDTO.setResultListTotal(total);
                resultMapDTO.setResultMap(resultMap);

                resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
            }
        } else {

            resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在service中获取多个的字典-getMoreDicByCondition,结果-result:" + resultMapDTO);
        return resultMapDTO;
    }


    /**
     * 获取单一的字典列表For管理中心
     *
     * @param paramMap
     * @return
     */
    @Override
    public ResultDTO getDicListByConditionForAdmin(Map<String, Object> paramMap) {
        ResultDTO resultDTO = new ResultDTO();
        List<Map<String, String>> dicStrList = Lists.newArrayList();
        List<Map<String, Object>> dicList = wxDicDao.getSimpleDicByCondition(paramMap);
        if (dicList != null && dicList.size() > 0) {
//            for (Map<String, Object> dicMap : dicList) {
//                String dicRemark = dicMap.get("dicRemark") != null ? dicMap.get("dicRemark").toString() : "";
//                if (!"".equals(dicRemark)) {
//                    Map<String, Object> dicRemarkMap = JSONObject.parseObject(dicRemark, Map.class);
//                    dicMap.remove("dicRemark");
//                    dicMap.putAll(dicRemarkMap);
//                }
//            }
            dicStrList = MapUtil.getStringMapList(dicList);
            Integer total = wxDicDao.getSimpleDicTotalByCondition(paramMap);
            resultDTO.setResultListTotal(total);
            resultDTO.setResultList(dicStrList);
            resultDTO.setCode(OilStationMapCode.SUCCESS.getNo());
            resultDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        } else {
            List<Map<String, String>> resultList = Lists.newArrayList();
            resultDTO.setResultListTotal(0);
            resultDTO.setResultList(resultList);
            resultDTO.setCode(OilStationMapCode.DIC_LIST_IS_NULL.getNo());
            resultDTO.setMessage(OilStationMapCode.DIC_LIST_IS_NULL.getMessage());
        }
        logger.info("【service】获取单一的字典列表For管理中心-getDicListByConditionForAdmin,结果-result:" + resultDTO);
        return resultDTO;
    }

}

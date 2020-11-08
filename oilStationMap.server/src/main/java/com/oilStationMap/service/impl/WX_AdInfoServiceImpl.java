package com.oilStationMap.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dao.WX_AdInfoDao;
import com.oilStationMap.dto.BoolDTO;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.service.WX_AdInfoService;
import com.oilStationMap.utils.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 广告service
 */
@Service
public class WX_AdInfoServiceImpl implements WX_AdInfoService {

    private static final Logger logger = LoggerFactory.getLogger(WX_AdInfoServiceImpl.class);

    @Autowired
    private WX_AdInfoDao WXAdInfoDao;

    /**
     * 添加广告
     *
     * @param paramMap
     * @return
     */
    @Override
    public BoolDTO addAdInfo(Map<String, Object> paramMap) {
        Integer addNum = 0;
        BoolDTO boolDTO = new BoolDTO();
        String adTitle = paramMap.get("adTitle") != null ? paramMap.get("adTitle").toString() : "";
        String adImgUrl = paramMap.get("adImgUrl") != null ? paramMap.get("adImgUrl").toString() : "";
        String adContent = paramMap.get("adContent") != null ? paramMap.get("adContent").toString() : "";
        String adRemark = paramMap.get("adRemark") != null ? paramMap.get("adRemark").toString() : "";
        if (!"".equals(adTitle) && !"".equals(adImgUrl) && !"".equals(adContent)) {
//            Map<String, Object> paramMap_temp = Maps.newHashMap();
//            paramMap_temp.put("adTitle", adTitle);
//            paramMap_temp.put("status", "0");
//            Integer total = WXAdInfoDao.getSimpleAdInfoTotalByCondition(paramMap_temp);
//            if (total != null && total <= 0) {
                addNum = WXAdInfoDao.addAdInfo(paramMap);
                if (addNum != null && addNum > 0) {
                    boolDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                    boolDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
                } else {
                    boolDTO.setCode(OilStationMapCode.NO_DATA_CHANGE.getNo());
                    boolDTO.setMessage(OilStationMapCode.NO_DATA_CHANGE.getMessage());
                }
//            } else {
//                boolDTO.setCode(OilStationMapCode.AD_INFO_EXIST.getNo());
//                boolDTO.setMessage(OilStationMapCode.AD_INFO_EXIST.getMessage());
//            }
        } else {
            boolDTO.setCode(OilStationMapCode.AD_TITLE_OR_IMGURL_OR_CONTENT_IS_NOT_NULL.getNo());
            boolDTO.setMessage(OilStationMapCode.AD_TITLE_OR_IMGURL_OR_CONTENT_IS_NOT_NULL.getMessage());
        }
        logger.info("在service中添加广告-addAdInfo,结果-result:" + boolDTO);
        return boolDTO;
    }

    /**
     * 删除广告
     *
     * @param paramMap
     * @return
     */
    @Override
    public BoolDTO deleteAdInfo(Map<String, Object> paramMap) {
        Integer deleteNum = 0;
        BoolDTO boolDTO = new BoolDTO();
        String id = paramMap.get("id") != null ? paramMap.get("id").toString() : "";
        if (!"".equals(id)) {
            deleteNum = WXAdInfoDao.deleteAdInfo(paramMap);
            if (deleteNum != null && deleteNum > 0) {
                boolDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                boolDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
            } else {
                boolDTO.setCode(OilStationMapCode.NO_DATA_CHANGE.getNo());
                boolDTO.setMessage(OilStationMapCode.NO_DATA_CHANGE.getMessage());
            }
        } else {
            boolDTO.setCode(OilStationMapCode.AD_ID_IS_NOT_NULL.getNo());
            boolDTO.setMessage(OilStationMapCode.AD_ID_IS_NOT_NULL.getMessage());
        }
        logger.info("在service中删除广告-deleteAdInfo,结果-result:" + boolDTO);
        return boolDTO;
    }

    /**
     * 更新广告
     *
     * @param paramMap
     * @return
     */
    @Override
    public BoolDTO updateAdInfo(Map<String, Object> paramMap) {
        Integer updateNum = 0;
        BoolDTO boolDTO = new BoolDTO();
        String id = paramMap.get("id") != null ? paramMap.get("id").toString() : "";
        if (!"".equals(id)) {
            updateNum = WXAdInfoDao.updateAdInfo(paramMap);
            if (updateNum != null && updateNum > 0) {
                boolDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                boolDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
            } else {
                boolDTO.setCode(OilStationMapCode.NO_DATA_CHANGE.getNo());
                boolDTO.setMessage(OilStationMapCode.NO_DATA_CHANGE.getMessage());
            }
        } else {
            boolDTO.setCode(OilStationMapCode.AD_ID_IS_NOT_NULL.getNo());
            boolDTO.setMessage(OilStationMapCode.AD_ID_IS_NOT_NULL.getMessage());
        }
        logger.info("在service中更新广告-updateAdInfo,结果-result:" + boolDTO);
        return boolDTO;
    }

    /**
     * 获取单一的广告信息
     *
     * @param paramMap
     * @return
     */
    @Override
    public ResultDTO getSimpleAdInfoByCondition(Map<String, Object> paramMap) {
        ResultDTO resultDTO = new ResultDTO();
        List<Map<String, String>> dicStrList = Lists.newArrayList();
        List<Map<String, Object>> dicList = WXAdInfoDao.getSimpleAdInfoByCondition(paramMap);
        if (dicList != null && dicList.size() > 0) {
            for (int i = 1; i < dicList.size(); i++) {
                dicList.remove(i);
            }
            dicStrList = MapUtil.getStringMapList(dicList);
            Integer total = 1;
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
        logger.info("在service中获取单一的广告信息-getSimpleAdInfoByCondition,结果-result:" + resultDTO);
        return resultDTO;
    }

    /**
     * 获取单一的广告信息
     *
     * @param paramMap
     * @return
     */
    @Override
    public ResultDTO getSimpleAdInfoByConditionForAdmin(Map<String, Object> paramMap) {
        ResultDTO resultDTO = new ResultDTO();
        List<Map<String, String>> dicStrList = Lists.newArrayList();
        List<Map<String, Object>> dicList = WXAdInfoDao.getSimpleAdInfoByConditionForAdmin(paramMap);
        if (dicList != null && dicList.size() > 0) {
            dicStrList = MapUtil.getStringMapList(dicList);
            Integer total = WXAdInfoDao.getSimpleAdInfoTotalByConditionForAdmin(paramMap);
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
        logger.info("在service中获取单一的广告信息-getSimpleAdInfoByCondition,结果-result:" + resultDTO);
        return resultDTO;
    }

}

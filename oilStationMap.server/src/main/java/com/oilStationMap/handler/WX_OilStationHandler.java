package com.oilStationMap.handler;

import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.BoolDTO;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.service.WX_OilStationService;
import com.oilStationMap.utils.MapUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 加油站Handler
 */
@Component
public class WX_OilStationHandler {

    private static final Logger logger = LoggerFactory.getLogger(WX_OilStationHandler.class);

    @Autowired
    private WX_OilStationService wxOilStationService;


    /**
     * 从腾讯地图中获取或者添加加油站
     * @param paramMap
     * @return
     */
    public BoolDTO addOrUpdateOilStationAllCountry(Map<String, String> paramMap) {
        logger.info("在【hanlder】中从腾讯和百度地图中获取或者添加加油站-addOrUpdateOilStationAllCountry,请求-paramMap:" + paramMap);
        BoolDTO boolDTO = new BoolDTO();
        new Thread(){

            public void run(){
                Map<String, Object> objectParamMap = Maps.newHashMap();
                try {
                    wxOilStationService.addOrUpdateOilStationByTencetMap(objectParamMap);
//                    wxOilStationService.addOrUpdateOilStationByBaiduMap(objectParamMap);
                } catch (Exception e) {
                    boolDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                    boolDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                    logger.error("在【hanlder】中从腾讯和百度地图中获取或者添加加油站-addOrUpdateOilStationAllCountry is error, paramMap : " + paramMap + ", e : " + e);
                }
            }
        }.start();
        boolDTO.setCode(OilStationMapCode.SUCCESS.getNo());
        boolDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        logger.info("在【hanlder】中从腾讯地图中获取或者添加加油站-addOrUpdateOilStationByTencetMap,响应-response:" + boolDTO);
        return boolDTO;
    }

    /**
     * 更新加油站的招聘信息
     * @param paramMap
     * @return
     */
    public BoolDTO updateOilStationHireInfo(Map<String, String> paramMap) {
        logger.info("在【hanlder】中更新加油站的招聘信息-updateOilStationHireInfo,请求-paramMap:" + paramMap);
        BoolDTO boolDTO = new BoolDTO();
        new Thread(){

            public void run(){
                Map<String, Object> objectParamMap = Maps.newHashMap();
                try {
                    Integer size = 1000;
                    Integer start = 0;
                    objectParamMap.put("size", size);
                    objectParamMap.put("start", start);
                    wxOilStationService.updateOilStationHireInfo(objectParamMap);
                } catch (Exception e) {
                    boolDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                    boolDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                    logger.error("在【hanlder】中更新加油站的招聘信息-updateOilStationHireInfo is error, paramMap : " + paramMap + ", e : " + e);
                }
            }
        }.start();
        boolDTO.setCode(OilStationMapCode.SUCCESS.getNo());
        boolDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        logger.info("在【hanlder】中更新加油站的招聘信息-updateOilStationHireInfo,响应-response:" + boolDTO);
        return boolDTO;
    }

    /**
     * 定时更新全国油价
     * @param paramMap
     * @return
     */
    public BoolDTO getOilPriceFromOilUsdCnyCom(Map<String, String> paramMap) {
        logger.info("在【hanlder】中定时更新全国油价-getOilPriceFromOilUsdCnyCom,请求-paramMap:" + paramMap);
        BoolDTO boolDTO = new BoolDTO();
        new Thread(){

            public void run(){
                Map<String, Object> objectParamMap = Maps.newHashMap();
                try {
                    wxOilStationService.getOilPriceFromOilUsdCnyCom(objectParamMap);
                } catch (Exception e) {
                    boolDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                    boolDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                    logger.error("在【hanlder】中定时更新全国油价-getOilPriceFromOilUsdCnyCom is error, paramMap : " + paramMap + ", e : " + e);
                }
            }
        }.start();
        boolDTO.setCode(OilStationMapCode.SUCCESS.getNo());
        boolDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        logger.info("在【hanlder】中定时更新全国油价-getOilPriceFromOilUsdCnyCom,响应-response:" + boolDTO);
        return boolDTO;
    }


    public BoolDTO addOrUpdateOilStation(Map<String, String> paramMap) {
        logger.info("在hanlder中添加或者更新加油站-addOrUpdateOilStation,请求-paramMap:" + paramMap);
        BoolDTO boolDTO = new BoolDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                boolDTO = wxOilStationService.addOrUpdateOilStation(objectParamMap);
            } catch (Exception e) {
                boolDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                boolDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                logger.error("在hanlder中添加或者更新加油站-addOrUpdateOilStation is addOilStation, paramMap : " + paramMap + ", e : " + e);
            }
        } else {
            boolDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            boolDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在hanlder中添加或者更新加油站-addOrUpdateOilStation,响应-response:" + boolDTO);
        return boolDTO;
    }


    public ResultDTO getOilStationList(Map<String, String> paramMap) {
        logger.info("在hanlder中获取加油站列表-getOilStationList,请求-paramMap:" + paramMap);
        ResultDTO resultDTO = new ResultDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultDTO = wxOilStationService.getOilStationList(objectParamMap);
            } catch (Exception e) {
                List<Map<String, String>> resultList = Lists.newArrayList();
                resultDTO.setResultListTotal(0);
                resultDTO.setResultList(resultList);
                resultDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                logger.error("在hanlder中获取加油站列表-getOilStationList is error, paramMap : " + paramMap + ", e : " + e);
            }
        } else {
            resultDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在hanlder中获取加油站列表-getOilStationList,响应-response:" + resultDTO);
        return resultDTO;
    }


    public ResultMapDTO getOneOilStationByCondition(Map<String, String> paramMap) {
        logger.info("在hanlder中获取单一加油站信息-getSimpleOilStationByCondition,请求-paramMap:" + paramMap);
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultMapDTO = wxOilStationService.getOneOilStationByCondition(objectParamMap);
            } catch (Exception e) {
                Map<String, String> resultMap = Maps.newHashMap();
                resultMapDTO.setResultListTotal(0);
                resultMapDTO.setResultMap(resultMap);
                resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                logger.error("在hanlder中获取单一加油站信息-getSimpleOilStationByCondition is error, paramMap : " + paramMap + ", e : " + e);
                e.printStackTrace();
            }
        } else {
            resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在hanlder中获取单一加油站信息-getSimpleOilStationByCondition,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }


    public ResultDTO getOilStationByLonLat(Map<String, String> paramMap) {
        logger.info("在hanlder中根据经纬度地址获取所处的加油站-getOilStationByLonLat,请求-paramMap:" + paramMap);
        ResultDTO resultDTO = new ResultDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultDTO = wxOilStationService.getOilStationByLonLat(objectParamMap);
            } catch (Exception e) {
                List<Map<String, String>> resultList = Lists.newArrayList();
                resultDTO.setResultListTotal(0);
                resultDTO.setResultList(resultList);
                resultDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                logger.error("在hanlder中根据经纬度地址获取所处的加油站-getOilStationByLonLat is error, paramMap : " + paramMap + ", e : " + e);
            }
        } else {

            resultDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在hanlder中根据经纬度地址获取所处的加油站-getOilStationByLonLat,响应-response:" + resultDTO);
        return resultDTO;
    }

    public ResultDTO getOilStationListForAdmin(Map<String, String> paramMap) {
        logger.info("在【Handle】中获取加油站列表For管理中心-getOilStationListForAdmin,请求-paramMap:" + paramMap);
        ResultDTO resultDTO = new ResultDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                resultDTO = wxOilStationService.getOilStationListForAdmin(objectParamMap);
            } catch (Exception e) {
                List<Map<String, String>> resultList = Lists.newArrayList();
                resultDTO.setResultListTotal(0);
                resultDTO.setResultList(resultList);
                resultDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                logger.error("在【Handle】中获取加油站列表For管理中心-getOilStationListForAdmin is error, paramMap : " + paramMap + ", e : " + e);
            }
        } else {
            resultDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在【Handle】中获取加油站列表For管理中心-getOilStationListForAdmin,响应-response:" + resultDTO);
        return resultDTO;
    }

    public BoolDTO updateOilStationForAdmin(Map<String, String> paramMap) {
        logger.info("在【hanlder】更新加油站For管理中心-updateOilStationForAdmin,请求-paramMap:" + paramMap);
        BoolDTO boolDTO = new BoolDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        if (paramMap.size() > 0) {
            try {
                boolDTO = wxOilStationService.updateOilStationForAdmin(objectParamMap);
            } catch (Exception e) {
                boolDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                boolDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                logger.error("在【hanlder】更新加油站For管理中心-updateOilStationForAdmin is addOilStation, paramMap : " + paramMap + ", e : " + e);
            }
        } else {
            boolDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            boolDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在【hanlder】更新加油站For管理中心-updateOilStationForAdmin,响应-response:" + boolDTO);
        return boolDTO;
    }
}

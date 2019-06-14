package com.oilStationMap.service;

import com.oilStationMap.dto.BoolDTO;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.dto.ResultMapDTO;

import java.util.Map;

public interface WX_OilStationService {

    /**
     * 从百度地图中获取或者添加加油站
     *
     * @param paramMap
     * @return
     */
    BoolDTO addOrUpdateOilStationByBaiduMap(Map<String, Object> paramMap);

    /**
     * 从腾讯地图中获取或者添加加油站
     *
     * @param paramMap
     * @return
     */
    BoolDTO addOrUpdateOilStationByTencetMap(Map<String, Object> paramMap);

    /**
     * 定时更新全国油价
     *
     * @param paramMap
     * @return
     */
    BoolDTO getOilPriceFromOilUsdCnyCom(Map<String, Object> paramMap);

    /**
     * 添加或者更新加油站
     *
     * @param paramMap
     * @return
     */
    BoolDTO addOrUpdateOilStation(Map<String, Object> paramMap);

    /**
     * 获取加油站列表
     *
     * @param paramMap
     * @return
     */
    ResultDTO getOilStationList(Map<String, Object> paramMap);

    /**
     * 根据条件查询加油站信息
     */
    ResultDTO getSimpleOilStationByCondition(Map<String, Object> paramMap);

    /**
     * 获取民营加油站信息
     */
    ResultDTO getPrivateOilStationByCondition(Map<String, Object> paramMap);

    /**
     * 获取一个加油站信息
     */
    ResultMapDTO getOneOilStationByCondition(Map<String, Object> paramMap);

    /**
     * 根据经纬度地址获取所处的加油站
     *
     * @param paramMap
     * @return
     */
    ResultDTO getOilStationByLonLat(Map<String, Object> paramMap);

    /**
     * 添加或者修改加油站信息
     */
    BoolDTO addOilStation(Map<String, Object> paramMap);

    /**
     * 修改加油站信息
     */
    BoolDTO updateOilStation(Map<String, Object> paramMap);

    /**
     * 删除加油站信息
     */
    BoolDTO deleteOilStation(Map<String, Object> paramMap);

    /**
     * 通过excel的方式导入加油站数据
     */
    public ResultMapDTO batchImportOilStationByExcel(Map<String, Object> paramMap) throws Exception;

    /**
     * 创建加油站招聘信息
     */
    public String createOilStationHireInfoUrl(String oilStationName, String oilStationAddress);
}

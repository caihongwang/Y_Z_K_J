package com.oilStationMap.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.BoolDTO;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.service.*;
import com.oilStationMap.utils.*;
import com.oilStationMap.vo.OilStationVO;
import com.oilStationMap.dao.WX_OilStationDao;
import com.oilStationMap.dao.WX_OilStationOperatorDao;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.oilStationMap.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 加油站service
 */
@Service
public class WX_OilStationServiceImpl implements WX_OilStationService {

    private static final Logger logger = LoggerFactory.getLogger(WX_OilStationServiceImpl.class);

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private WX_OilStationDao wxOilStationDao;

    @Autowired
    private WX_CommonService wxCommonService;

    @Autowired
    private WX_MessageService wxMessageService;

    @Autowired
    private WX_OilStationOperatorService wxOilStationOperatorService;

    @Autowired
    private WX_DicService wxDicService;

    @Autowired
    private HttpsUtil httpsUtil;

    @Value("${oilStationMap.hirePath}")
    private String oilStationMapHirePath;

    /**
     * 从百度地图中获取或者添加加油站
     * @param paramMap
     * @return
     */
    @Override
    public BoolDTO addOrUpdateOilStationByBaiduMap(Map<String, Object> paramMap) {
        Map<String, Object> dicMap = Maps.newHashMap();
        dicMap.put("dicType", "region");
        ResultDTO cityResultDTO = wxDicService.getSimpleDicByCondition(dicMap);
        if (cityResultDTO.getResultList() != null
                && cityResultDTO.getResultList().size() > 0) {
            List<Map<String, String>> regionList = cityResultDTO.getResultList();
            for (int j = 0; j < regionList.size(); j++) {
                Map<String, String> regionMap = regionList.get(j);
                String regionName = regionMap.get("regionName");
                String query = "加油站";
                String tag = "加油站";
                String output = "json";
                Integer page_size = 20;
                Integer page_num = 0;
                List<Map<String, Object>> oilStationList =
                        LonLatUtil.getDetailAddressByCityAndKeyWordFromBaiduMap(
                                regionName, query, tag, page_size, page_num, output);
                if (oilStationList.size() > 0) {
                    //对加油站进行入库
                    for (int i = 0; i < oilStationList.size(); i++) {
                        //获取当前时间
                        Date currentDate = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                        Map<String, Object> oilStationMap = oilStationList.get(i);
                        //准备参数插入
                        String oilStationName = oilStationMap.get("title").toString();
                        paramMap.clear();
                        paramMap.put("uid", "1");
                        paramMap.put("oilStationName", oilStationName);
                        paramMap.put("oilStationAreaSpell", PingYingUtil.getPingYin(oilStationMap.get("city") != null ? oilStationMap.get("city").toString() : ""));
                        paramMap.put("oilStationAreaName",
                                (oilStationMap.get("province") != null ? oilStationMap.get("province").toString() : "") +
                                        (oilStationMap.get("city") != null ? oilStationMap.get("city").toString() : "") +
                                        (oilStationMap.get("district") != null ? oilStationMap.get("district").toString() : "")
                        );
                        paramMap.put("oilStationAdress", oilStationMap.get("address"));
                        String oilStationBrandName = "民营";
                        oilStationBrandName = getOilStationBrankName(oilStationName);
                        paramMap.put("oilStationBrandName", oilStationBrandName);
                        paramMap.put("oilStationType", oilStationMap.get("category"));
                        paramMap.put("oilStationDiscount", "折扣店");
                        paramMap.put("oilStationExhaust", "0#,92#,95#,98#");
                        paramMap.put("oilStationPosition",
                                (oilStationMap.get("lng") != null ? oilStationMap.get("lng").toString() : "") +
                                    "," +
                                        (oilStationMap.get("lat") != null ? oilStationMap.get("lat").toString() : "")
                        );
                        paramMap.put("oilStationLon", oilStationMap.get("lng") != null ? oilStationMap.get("lng").toString() : "");
                        paramMap.put("oilStationLat", oilStationMap.get("lat") != null ? oilStationMap.get("lat").toString() : "");
                        paramMap.put("oilStationPayType", "微信，支付宝，银联，现金，赊账等");
                        paramMap.put("oilStationPrice", "[{\"oilModelLabel\":\"0\",\"oilNameLabel\":\"柴油\",\"oilPriceLabel\":\"7.43\"},{\"oilModelLabel\":\"92\",\"oilNameLabel\":\"汽油\",\"oilPriceLabel\":\"7.65\"},{\"oilModelLabel\":\"95\",\"oilNameLabel\":\"汽油\",\"oilPriceLabel\":\"8.28\"}]");
                        paramMap.put("oilStationDistance", "待定");
                        paramMap.put("isManualModify", "0");
                        paramMap.put("oilStationOwnerUid", "baiduMap");
                        paramMap.put("oilStationCategory", getOilStationCategory(oilStationName));
                        paramMap.put("oilStationHireUrl", this.createOilStationHireInfoUrl(oilStationName, oilStationMap.get("address").toString()));
                        paramMap.put("oilStationHireTitle", oilStationName+"--招聘");
                        try {
                            Date startDate = new Date();
                            this.addOrUpdateOilStation(paramMap);
                            Date endDate = new Date();
                            long interval = (endDate.getTime() - startDate.getTime())/1000;
                            logger.info("添加或者更新加油站-相差【"+interval+"】秒");
                        } catch (Exception e) {
                            logger.error("=============添加或者更新加油站失败============");
                            logger.error("=============添加或者更新加油站失败============");
                            logger.error("=============添加或者更新加油站失败============");
                            logger.error("=============添加或者更新加油站失败============");
                            logger.error("=============添加或者更新加油站失败============");
                            logger.error("添加或者更新加油站失败， param = " + JSONObject.toJSONString(paramMap));
                            logger.error("=============添加或者更新加油站失败============");
                            logger.error("=============添加或者更新加油站失败============");
                            logger.error("=============添加或者更新加油站失败============");
                            logger.error("=============添加或者更新加油站失败============");
                            logger.error("=============添加或者更新加油站失败============");

                            paramMap.clear();//清空参数，重新准备参数
                            Map<String, Object> dataMap = Maps.newHashMap();
                            //标题
                            Map<String, Object> firstMap = Maps.newHashMap();
                            firstMap.put("value", "监测到油站信息操作服务出现问题");
                            firstMap.put("color", "#0017F5");
                            dataMap.put("keyword1", firstMap);
                            //错误描述
                            Map<String, Object> keyword1Map = Maps.newHashMap();
                            keyword1Map.put("value", oilStationMap.get("title").toString()+" 添加或者更新异常");
                            keyword1Map.put("color", "#0017F5");
                            dataMap.put("first", keyword1Map);
                            //错误详情
                            Map<String, Object> keyword2Map = Maps.newHashMap();
                            keyword2Map.put("value", "从[百度地图]获取爬取加油站信息入库失败");
                            keyword2Map.put("color", "#0017F5");
                            dataMap.put("keyword2", keyword2Map);
                            //时间
                            Map<String, Object> keyword3Map = Maps.newHashMap();
                            keyword3Map.put("value", sdf.format(currentDate));
                            keyword3Map.put("color", "#0017F5");
                            dataMap.put("keyword3", keyword3Map);
                            //备注
                            Map<String, Object> remarkMap = Maps.newHashMap();
                            remarkMap.put("value", e.getMessage());
                            remarkMap.put("color", "#0017F5");
                            dataMap.put("remark", remarkMap);
                            //整合
                            paramMap.put("data", JSONObject.toJSONString(dataMap));
                            new Thread(){
                                public void run(){
                                    try{
                                        wxMessageService.dailyUpdateOrAddOilStationMessageSend(paramMap);
                                    } catch (Exception e1){
                                        logger.info("服务器异常，发送消息通知管理员异常，e : ", e1);
                                    }
                                }
                            }.start();
                        }
                    }
                    String oilStationNum = oilStationList.size() + "";
                    //更新字典表中city的加油站数量
                    Map<String, String> dicRemarkMap = Maps.newHashMap();
                    dicRemarkMap.put("parentId", regionMap.get("parentId"));
                    dicRemarkMap.put("regionId", regionMap.get("regionId"));
                    dicRemarkMap.put("regionCode", regionMap.get("regionCode"));
                    dicRemarkMap.put("regionName", regionMap.get("regionName"));
                    dicRemarkMap.put("oilStationNum", oilStationNum);
                    dicMap.clear();
                    dicMap.put("dicRemark", JSONObject.toJSONString(dicRemarkMap));
                    dicMap.put("dicStatus", "1");
                    dicMap.put("id", regionMap.get("id"));
                    wxDicService.updateDic(dicMap);
                } else {
                    logger.error("获取当前地区【" + regionName + "】暂时没有加油站，反正我不信...");
                }
            }
        }
        //更新油价
        LonLatUtil.getOilPriceFromOilUsdCnyCom(paramMap);
        BoolDTO boolDTO = new BoolDTO();
        boolDTO.setCode(OilStationMapCode.SUCCESS.getNo());
        boolDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        return boolDTO;
    }

    /**
     * 从腾讯地图中获取或者添加加油站
     * @param paramMap
     * @return
     */
    @Override
    public BoolDTO addOrUpdateOilStationByTencetMap(Map<String, Object> paramMap) {
        Map<String, Object> dicMap = Maps.newHashMap();
        dicMap.put("dicType", "city");
//        dicMap.put("dicType", "region");
        ResultDTO cityResultDTO = wxDicService.getSimpleDicByCondition(dicMap);
        if (cityResultDTO.getResultList() != null
                && cityResultDTO.getResultList().size() > 0) {
            List<Map<String, String>> cityList = cityResultDTO.getResultList();
            for (int j = 0; j < cityList.size(); j++) {
                Map<String, String> cityMap = cityList.get(j);
                String cityName = cityMap.get("cityName");
//                String cityName = cityMap.get("regionName");
                String keyWord = "加油站";
                Integer pageSize = 20;
                Integer pageIndex = 1;
                String orderby = "_distance";
                List<Map<String, Object>> oilStationList = LonLatUtil.getDetailAddressByCityAndKeyWord(cityName, keyWord, pageSize, pageIndex, orderby);
                if (oilStationList.size() > 0) {
                    //对加油站进行入库
                    for (int i = 0; i < oilStationList.size(); i++) {
                        //获取当前时间
                        Date currentDate = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                        Map<String, Object> oilStationMap = oilStationList.get(i);
                        //准备参数插入
                        String oilStationName = oilStationMap.get("title").toString();
                        paramMap.clear();
                        paramMap.put("uid", "1");
                        paramMap.put("oilStationName", oilStationName);
                        paramMap.put("oilStationAreaSpell", PingYingUtil.getPingYin(oilStationMap.get("city") != null ? oilStationMap.get("city").toString() : ""));
                        paramMap.put("oilStationAreaName",
                                (oilStationMap.get("province") != null ? oilStationMap.get("province").toString() : "") +
                                        (oilStationMap.get("city") != null ? oilStationMap.get("city").toString() : "") +
                                        (oilStationMap.get("district") != null ? oilStationMap.get("district").toString() : "")
                        );
                        paramMap.put("oilStationAdress", oilStationMap.get("address"));
                        String oilStationBrandName = "民营";
                        oilStationBrandName = getOilStationBrankName(oilStationName);
                        paramMap.put("oilStationBrandName", oilStationBrandName);
                        paramMap.put("oilStationType", oilStationMap.get("category"));
                        paramMap.put("oilStationDiscount", "折扣店");
                        paramMap.put("oilStationExhaust", "0#,92#,95#,98#");
                        paramMap.put("oilStationPosition",
                                (oilStationMap.get("lng") != null ? oilStationMap.get("lng").toString() : "") +
                                        (oilStationMap.get("lat") != null ? oilStationMap.get("lat").toString() : "")
                        );
                        paramMap.put("oilStationLon", oilStationMap.get("lng") != null ? oilStationMap.get("lng").toString() : "");
                        paramMap.put("oilStationLat", oilStationMap.get("lat") != null ? oilStationMap.get("lat").toString() : "");
                        paramMap.put("oilStationPayType", "微信，支付宝，银联，现金，赊账等");
                        paramMap.put("oilStationPrice", "[{\"oilModelLabel\":\"0\",\"oilNameLabel\":\"柴油\",\"oilPriceLabel\":\"7.43\"},{\"oilModelLabel\":\"92\",\"oilNameLabel\":\"汽油\",\"oilPriceLabel\":\"7.65\"},{\"oilModelLabel\":\"95\",\"oilNameLabel\":\"汽油\",\"oilPriceLabel\":\"8.28\"}]");
                        paramMap.put("oilStationDistance", "待定");
                        paramMap.put("isManualModify", "0");
                        paramMap.put("oilStationOwnerUid", "tencentMap");
                        paramMap.put("oilStationCategory", getOilStationCategory(oilStationName));
                        paramMap.put("oilStationHireUrl", this.createOilStationHireInfoUrl(oilStationName, oilStationMap.get("address").toString()));
                        paramMap.put("oilStationHireTitle", oilStationName+"--招聘");
                        try {
                            Date startDate = new Date();
                            //this.addOrUpdateOilStation(paramMap);
                            Date endDate = new Date();
                            long interval = (endDate.getTime() - startDate.getTime())/1000;
                            logger.info("添加或者更新加油站-相差【"+interval+"】秒");
                        } catch (Exception e) {
                            logger.error("=============添加或者更新加油站失败============");
                            logger.error("=============添加或者更新加油站失败============");
                            logger.error("=============添加或者更新加油站失败============");
                            logger.error("=============添加或者更新加油站失败============");
                            logger.error("=============添加或者更新加油站失败============");
                            logger.error("添加或者更新加油站失败， param = " + JSONObject.toJSONString(paramMap));
                            logger.error("=============添加或者更新加油站失败============");
                            logger.error("=============添加或者更新加油站失败============");
                            logger.error("=============添加或者更新加油站失败============");
                            logger.error("=============添加或者更新加油站失败============");
                            logger.error("=============添加或者更新加油站失败============");

                            paramMap.clear();//清空参数，重新准备参数
                            Map<String, Object> dataMap = Maps.newHashMap();
                            //标题
                            Map<String, Object> firstMap = Maps.newHashMap();
                            firstMap.put("value", "监测到油站信息操作服务出现问题");
                            firstMap.put("color", "#0017F5");
                            dataMap.put("keyword1", firstMap);
                            //错误描述
                            Map<String, Object> keyword1Map = Maps.newHashMap();
                            keyword1Map.put("value", oilStationMap.get("title").toString()+" 添加或者更新异常");
                            keyword1Map.put("color", "#0017F5");
                            dataMap.put("first", keyword1Map);
                            //错误详情
                            Map<String, Object> keyword2Map = Maps.newHashMap();
                            keyword2Map.put("value", "从[腾讯地图]获取爬取加油站信息入库失败");
                            keyword2Map.put("color", "#0017F5");
                            dataMap.put("keyword2", keyword2Map);
                            //时间
                            Map<String, Object> keyword3Map = Maps.newHashMap();
                            keyword3Map.put("value", sdf.format(currentDate));
                            keyword3Map.put("color", "#0017F5");
                            dataMap.put("keyword3", keyword3Map);
                            //备注
                            Map<String, Object> remarkMap = Maps.newHashMap();
                            remarkMap.put("value", e.getMessage());
                            remarkMap.put("color", "#0017F5");
                            dataMap.put("remark", remarkMap);
                            //整合
                            paramMap.put("data", JSONObject.toJSONString(dataMap));
                            new Thread(){
                                public void run(){
                                    try{
                                        wxMessageService.dailyUpdateOrAddOilStationMessageSend(paramMap);
                                    } catch (Exception e1){
                                        logger.info("服务器异常，发送消息通知管理员异常，e : ", e1);
                                    }
                                }
                            }.start();
                        }
                    }
                    String oilStationNum = oilStationList.size() + "";
                    //更新字典表中city的加油站数量
                    Map<String, String> dicRemarkMap = Maps.newHashMap();
                    dicRemarkMap.put("provinceId", cityMap.get("provinceId"));
                    dicRemarkMap.put("cityName", cityMap.get("cityName"));
                    dicRemarkMap.put("cityCode", cityMap.get("cityCode"));
                    dicRemarkMap.put("oilStationNum", oilStationNum);
                    dicMap.clear();
                    dicMap.put("dicRemark", JSONObject.toJSONString(dicRemarkMap));
                    dicMap.put("dicStatus", "1");
                    dicMap.put("id", cityMap.get("id"));
                    wxDicService.updateDic(dicMap);
//                    //更新字典表中city的加油站数量
//                    Map<String, String> dicRemarkMap = Maps.newHashMap();
//                    dicRemarkMap.put("cityId", cityMap.get("cityId"));
//                    dicRemarkMap.put("regionCode", cityMap.get("regionCode"));
//                    dicRemarkMap.put("regionName", cityMap.get("regionName"));
//                    dicRemarkMap.put("oilStationNum", oilStationNum);
//                    dicMap.clear();
//                    dicMap.put("dicRemark", JSONObject.toJSONString(dicRemarkMap));
//                    dicMap.put("dicStatus", "1");
//                    dicMap.put("id", cityMap.get("id"));
//                    wxDicService.updateDic(dicMap);
                } else {
                    logger.error("获取当前城市【" + cityName + "】暂时没有加油站，反正我不信...");
                }
            }
        }
        //更新油价
        LonLatUtil.getOilPriceFromOilUsdCnyCom(paramMap);
        BoolDTO boolDTO = new BoolDTO();
        boolDTO.setCode(OilStationMapCode.SUCCESS.getNo());
        boolDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        return boolDTO;
    }

    /**
     * 更新加油站的招聘信息
     * @param paramMap
     * @return
     */
    @Override
    public BoolDTO updateOilStationHireInfo(Map<String, Object> paramMap) {
        Integer start = (Integer)paramMap.get("start");
        Integer size = (Integer)paramMap.get("size");
        paramMap.clear();
        paramMap.put("start", start);
        paramMap.put("size", size);
        List<Map<String, Object>> oilStationList = wxOilStationDao.getSimpleOilStationByCondition(paramMap);
        if(oilStationList != null && oilStationList.size() > 0){
            for(int i = 0; i < oilStationList.size(); i++) {
                Map<String, Object> oilStationMap = oilStationList.get(i);
                String oilStationName = oilStationMap.get("oilStationName").toString();
                String oilStationAdress = oilStationMap.get("oilStationAdress").toString();
                String id = oilStationMap.get("id").toString();
                paramMap.clear();
                paramMap.put("oilStationHireUrl", this.createOilStationHireInfoUrl(oilStationName, oilStationAdress));
                paramMap.put("oilStationHireTitle", oilStationName+"--招聘");
                paramMap.put("id", id);
                Integer updateNum = wxOilStationDao.updateOilStation(paramMap);
                if(updateNum != null && updateNum > 0){
                    logger.info("第"+i+"座【"+oilStationName+"】更新招聘信息成功...");
                } else {
                    logger.info("第"+i+"座【"+oilStationName+"】更新招聘信息失败...");
                }
            }
            start = start + 1000;
            paramMap.put("size", size);
            paramMap.put("start", start);
            updateOilStationHireInfo(paramMap);
        } else {
            logger.info("更新招聘信息结束...");
        }
        BoolDTO boolDTO = new BoolDTO();
        boolDTO.setCode(OilStationMapCode.SUCCESS.getNo());
        boolDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        return boolDTO;
    }

    /**
     * 根据加油站名称来获取加油站的类型
     * @param oilStationName
     * @return
     */
    public String getOilStationBrankName(String oilStationName){
        String oilStationBrandName = "民营";
        if(oilStationName.indexOf("中石化")!=-1
                || oilStationName.indexOf("中国石化")!=-1){
            if(oilStationName.indexOf("壳牌")!=-1
                || oilStationName.indexOf("BP")!=-1
                    || oilStationName.indexOf("埃索")!=-1
                        || oilStationName.indexOf("埃克森")!=-1){
                oilStationBrandName = "中国石化;合资;";
            } else {
                oilStationBrandName = "中国石化;";
            }
        } else if(oilStationName.indexOf("中石油")!=-1
                || oilStationName.indexOf("中国石油")!=-1){
            if(oilStationName.indexOf("壳牌")!=-1
                    || oilStationName.indexOf("BP")!=-1
                    || oilStationName.indexOf("埃索")!=-1
                    || oilStationName.indexOf("埃克森")!=-1){
                oilStationBrandName = "中国石油;合资;";
            } else {
                oilStationBrandName = "中国石油;";
            }
        } else if(oilStationName.indexOf("中海油")!=-1
                || oilStationName.indexOf("中国海油")!=-1){
            if(oilStationName.indexOf("壳牌")!=-1
                    || oilStationName.indexOf("BP")!=-1
                    || oilStationName.indexOf("埃索")!=-1
                    || oilStationName.indexOf("埃克森")!=-1){
                oilStationBrandName = "中国海油;合资;";
            } else {
                oilStationBrandName = "中国海油;";
            }
        } else{
            oilStationBrandName = "民营";
        }
        return oilStationBrandName;
    }

    /**
     * 根据加油站名称来获取加油站的类型
     * @param oilStationName
     * @return
     */
    public String getOilStationCategory(String oilStationName){
        String oilStationCategory = "民营";
        if(oilStationName.indexOf("中石化")!=-1
                || oilStationName.indexOf("中国石化")!=-1 ||
                oilStationName.indexOf("中石油")!=-1
                    || oilStationName.indexOf("中国石油")!=-1 ||
                    oilStationName.indexOf("中海油")!=-1
                        || oilStationName.indexOf("中国海油")!=-1){
            oilStationCategory = "国营";
        } else if(oilStationName.indexOf("壳牌")!=-1
                    || oilStationName.indexOf("BP")!=-1
                        || oilStationName.indexOf("埃索")!=-1
                            || oilStationName.indexOf("埃克森")!=-1){
            oilStationCategory = "合资";
        } else{
            oilStationCategory = "民营";
        }
        return oilStationCategory;
    }

    /**
     * 定时更新全国油价
     *
     * @param paramMap
     * @return
     */
    @Override
    public BoolDTO getOilPriceFromOilUsdCnyCom(Map<String, Object> paramMap) {
        BoolDTO boolDTO = new BoolDTO();
        LonLatUtil.getOilPriceFromOilUsdCnyCom(paramMap);
        boolDTO.setCode(OilStationMapCode.SUCCESS.getNo());
        boolDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        return boolDTO;
    }

    /**
     * 获取加油站列表
     *
     * @param paramMap
     * @return
     */
    @Override
    public ResultDTO getOilStationList(Map<String, Object> paramMap) {
        ResultDTO resultDTO = new ResultDTO();
        List<Map<String, String>> oilStationStrList = Lists.newArrayList();
        String uid = paramMap.get("uid") != null ? paramMap.get("uid").toString() : "";
        Double lon = Double.parseDouble(paramMap.get("lon") != null ? paramMap.get("lon").toString() : "0");
        Double lat = Double.parseDouble(paramMap.get("lat") != null ? paramMap.get("lat").toString() : "0");
        Double page = Double.parseDouble(paramMap.get("page") != null ? paramMap.get("page").toString() : "1");           //默认一页查询
        Double r = Double.parseDouble(paramMap.get("r") != null ? paramMap.get("r").toString() : "10000");                //默认10公里范围
        if (!"".equals(lon.toString()) && !"".equals(lat.toString()) &&
                !"".equals(page.toString()) && !"".equals(r.toString())) {

            //纠正坐标，防止被刷
            if(lon < 0) {
                lon = 0 - lon;
            }
            if(lat < 0) {
                lat = 0 - lat;
            }

            //限定 运营人员 可以访问redis中的某个经纬度的加油站
            try (Jedis jedis = jedisPool.getResource()) {
                String currentLon = jedis.get(OilStationMapCode.CURRENT_LON_UID + uid);
                String currentLat = jedis.get(OilStationMapCode.CURRENT_LAT_UID + uid);
                logger.info("uid = " + uid + " , currentLon = " + currentLon + " , currentLat = " + currentLat);
                if(currentLon != null && !"".equals(currentLon)
                        && currentLat != null && !"".equals(currentLat)){
                    lon = Double.parseDouble(currentLon);
                    lat = Double.parseDouble(currentLat);
                }
            }

            //1.根据经纬度获取当前所在的城市名称
            String city = LonLatUtil.getAddressByLonLat(lon, lat, "city");
            //1.根据城市名称获取整座城市的加油站
            Map<String, Object> paramMap_temp = Maps.newHashMap();
            paramMap_temp.put("oilStationAreaName", city);
            List<Map<String, Object>> oilStationList_city = wxOilStationDao.getSimpleOilStationByCondition(paramMap_temp);
            if(oilStationList_city != null && oilStationList_city.size() > 0){
                List<Map<String, String>> oilStationStrList_city = Lists.newArrayList();
                oilStationStrList_city = MapUtil.getStringMapList(oilStationList_city);
                //通过循环遍历计算当前经纬度坐标与民营加油站的经纬度坐标之间的距离
                for (Map<String, String> oilStationMap : oilStationStrList_city) {
                    Double endLat = Double.parseDouble(oilStationMap.get("oilStationLat").toString());
                    Double endLon = Double.parseDouble(oilStationMap.get("oilStationLon").toString());
                    Double distance = LonLatUtil.getDistance(lat, lon, endLat, endLon);
                    BigDecimal bg = new BigDecimal(distance);
                    distance = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    oilStationMap.put("oilStationDistance", distance.toString());
                    //清除是baiduMap和tencetMap的oilStationOwnerUid
                    String oilStationOwnerUid = oilStationMap.get("oilStationOwnerUid").toString();
                    Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
                    //oilStationOwnerUid是数字，且与用户的uid相等时才可以修改
                    if(!pattern.matcher(oilStationOwnerUid).matches()){
                        oilStationMap.put("oilStationOwnerUid", "");
                    }
                }
                oilStationStrList.addAll(oilStationStrList_city);
                //对oilStationList的距离进行排序
                Collections.sort(oilStationStrList, new Comparator<Map<String, String>>() {
                    public int compare(Map<String, String> o1, Map<String, String> o2) {
                        Double oilStationDistance_1 = Double.parseDouble(o1.get("oilStationDistance").toString());
                        Double oilStationDistance_2 = Double.parseDouble(o2.get("oilStationDistance").toString());
                        return oilStationDistance_1.compareTo(oilStationDistance_2);
                    }
                });
                //默认在地图上显示自己周围附近的50座加油站
                Integer startIndex = paramMap.get("startIndex")!=null?Integer.parseInt(paramMap.get("startIndex").toString()):0;
                Integer endIndex = paramMap.get("endIndex")!=null?Integer.parseInt(paramMap.get("endIndex").toString()):50;
                oilStationStrList = oilStationStrList.subList(startIndex, endIndex);
                //查找去#92汽油价格最低的加油站
                Map<String, String> minMap = Collections.min(oilStationStrList, new Comparator<Map<String, String>>() {
                    public int compare(Map<String, String> o1, Map<String, String> o2) {
                        Double oilPrice1 = 0.0;
                        String oilPriceLabel1 = "0.0";
                        String oilStationPriceStr1 = o1.get("oilStationPrice")!=null?o1.get("oilStationPrice").toString():"";
                        JSONArray oilStationPrice_JSONArray1 = JSONArray.parseArray(oilStationPriceStr1);
                        if(oilStationPrice_JSONArray1.size()>0){
                            for(int i=0;i<oilStationPrice_JSONArray1.size();i++){
                                JSONObject oilStationPrice_JSONObject1 = oilStationPrice_JSONArray1.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                                String oilModelLabel = oilStationPrice_JSONObject1.get("oilModelLabel")!=null?oilStationPrice_JSONObject1.get("oilModelLabel").toString():"";
                                if(!"".equals(oilModelLabel) && "92".equals(oilModelLabel)){
                                    oilPriceLabel1 = oilStationPrice_JSONObject1.get("oilPriceLabel")!=null?oilStationPrice_JSONObject1.get("oilPriceLabel").toString():"0.0";
                                }
                            }
                        }
                        oilPrice1 = Double.parseDouble(oilPriceLabel1);

                        Double oilPrice2 = 0.0;
                        String oilPriceLabel2 = "0.0";
                        String oilStationPriceStr2 = o2.get("oilStationPrice")!=null?o2.get("oilStationPrice").toString():"";
                        JSONArray oilStationPrice_JSONArray2 = JSONArray.parseArray(oilStationPriceStr2);
                        if(oilStationPrice_JSONArray2.size()>0){
                            for(int i=0;i<oilStationPrice_JSONArray2.size();i++){
                                JSONObject oilStationPrice_JSONObject2 = oilStationPrice_JSONArray2.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                                String oilModelLabel = oilStationPrice_JSONObject2.get("oilModelLabel")!=null?oilStationPrice_JSONObject2.get("oilModelLabel").toString():"";
                                if(!"".equals(oilModelLabel) && "92".equals(oilModelLabel)){
                                    oilPriceLabel2 = oilStationPrice_JSONObject2.get("oilPriceLabel")!=null?oilStationPrice_JSONObject2.get("oilPriceLabel").toString():"0.0";
                                }
                            }
                        }
                        oilPrice2 = Double.parseDouble(oilPriceLabel2);

                        if(oilPrice1 > oilPrice2){
                            return 0;
                        } else {
                            return -1;
                        }
                    }
                });
                Integer index = oilStationStrList.indexOf(minMap);
                if(index != null && index >= 0){
                    oilStationStrList.get(index).put("lowestOilPrice", "true");
                }

                //移除不必要展示的数据，减少服务端与小程序端的网络压力
                for(Map<String, String> oilStationStrMap : oilStationStrList){
                    oilStationStrMap.remove("updateTime");
                    oilStationStrMap.remove("createTime");
                    oilStationStrMap.remove("isManualModify");
                    oilStationStrMap.remove("oilStationExhaust");
                    oilStationStrMap.remove("oilStationBrandName");
                    oilStationStrMap.remove("oilStationAreaName");
                    oilStationStrMap.remove("oilStationDiscount");
                    oilStationStrMap.remove("oilStationType");
                    oilStationStrMap.remove("oilStationPayType");
                    oilStationStrMap.remove("oilStationAreaSpell");
                    oilStationStrMap.remove("oilStationPosition");
                    //对招人网址进行检测
                    String oilStationHireUrl = oilStationStrMap.get("oilStationHireUrl");
                    if(oilStationHireUrl != null && !"".equals(oilStationHireUrl)){
                        String oilStationHireUrlFilePath = oilStationHireUrl.replace("https://www.91caihongwang.com/", "/opt/");
                        File oilStationHireUrlFile = new File(oilStationHireUrlFilePath);
                        File parentDir = oilStationHireUrlFile.getParentFile();
                        if(!parentDir.isDirectory()){            //加油站名称的英文编码的文件夹
                            parentDir.mkdir();
                        }
                        //获取加油站转义文件夹名称
                        String oilStationName_En = "";
                        String oilStationNameTemp = oilStationStrMap.get("oilStationName");
                        oilStationNameTemp = oilStationNameTemp.replace("/", "");
                        char[] tempArr = oilStationNameTemp.toCharArray();
                        for(int i = 0; i < tempArr.length; i++){
                            char temp = tempArr[i];
                            if(i == 0){
                                oilStationName_En = oilStationName_En + PingYingUtil.getPingYin(temp+"");
                            } else {
                                oilStationName_En = oilStationName_En + "_" + PingYingUtil.getPingYin(temp+"");
                            }
                        }
                        if(!oilStationHireUrlFile.exists() || oilStationHireUrlFile.length() <= 0 || !oilStationName_En.equals(parentDir.getName())){
                            oilStationHireUrl = this.createOilStationHireInfoUrl(oilStationStrMap.get("oilStationName"), oilStationStrMap.get("address"));
                            oilStationStrMap.put("oilStationHireUrl", oilStationHireUrl);
                            //重新创建招聘文件
                            Map<String, Object> paramMapTemp = Maps.newHashMap();
                            paramMapTemp.put("oilStationHireUrl", oilStationHireUrl);
                            paramMapTemp.put("oilStationHireTitle", oilStationStrMap.get("oilStationName")+"--招聘");
                            paramMapTemp.put("id", oilStationStrMap.get("id"));
                            wxOilStationDao.updateOilStation(paramMapTemp);
                        }
                    } else {
                        oilStationHireUrl = this.createOilStationHireInfoUrl(oilStationStrMap.get("oilStationName"), oilStationStrMap.get("address"));
                        oilStationStrMap.put("oilStationHireUrl", oilStationHireUrl);
                        //重新创建招聘文件
                        Map<String, Object> paramMapTemp = Maps.newHashMap();
                        paramMapTemp.put("oilStationHireUrl", oilStationHireUrl);
                        paramMapTemp.put("oilStationHireTitle", oilStationStrMap.get("oilStationName")+"--招聘");
                        paramMapTemp.put("id", oilStationStrMap.get("id"));
                        wxOilStationDao.updateOilStation(paramMapTemp);
                    }
                }

                resultDTO.setResultList(oilStationStrList);
                resultDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                resultDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
            } else {
                resultDTO.setResultList(oilStationStrList);

                resultDTO.setCode(OilStationMapCode.OIL_QUERY_IS_NULL.getNo());
                resultDTO.setMessage(OilStationMapCode.OIL_QUERY_IS_NULL.getMessage());
            }
        } else {
            resultDTO.setResultList(oilStationStrList);
            resultDTO.setCode(OilStationMapCode.OIL_STATION_PARAM_IS_NOT_NULL.getNo());
            resultDTO.setMessage(OilStationMapCode.OIL_STATION_PARAM_IS_NOT_NULL.getMessage());
        }
        logger.info("在service中获取加油站列表-getOilStationList,响应-response:" + resultDTO);
        return resultDTO;
    }

    /**
     * 获取民营加油站信息
     *
     * @param paramMap
     * @return
     */
    @Override
    public ResultDTO getPrivateOilStationByCondition(Map<String, Object> paramMap) {
        ResultDTO resultDTO = new ResultDTO();
        Map<String, String> resultMap = Maps.newHashMap();
        List<Map<String, String>> oilStationStrList = Lists.newArrayList();
        String oilStationName = paramMap.get("oilStationName") != null ? paramMap.get("oilStationName").toString() : "";
        Float lon = paramMap.get("lon") != null ? Float.parseFloat(paramMap.get("lon").toString()) : 0;
        Float lat = paramMap.get("lat") != null ? Float.parseFloat(paramMap.get("lat").toString()) : 0;
        Float dis = paramMap.get("dis") != null ? Float.parseFloat(paramMap.get("dis").toString()) : 1;
        String oilStationType = paramMap.get("oilStationType") != null ? paramMap.get("oilStationType").toString() : "民营";
        if (!"".equals(oilStationName) ||
                (!"".equals(lon) && !"".equals(lat))) {
            //1.现在数据库中查找是否存在金纬度范围之内的加油站
            //先计算查询点的经纬度范围
            double r = LonLatUtil.EARTH_RADIUS;//地球半径千米
            double dlng = 2 * Math.asin(Math.sin(dis / (2 * r)) / Math.cos(lat * Math.PI / 180));
            dlng = dlng * 180 / Math.PI;//角度转为弧度
            double dlat = dis / r;
            dlat = dlat * 180 / Math.PI;
            double minLat = lat - dlat;
            double maxLat = lat + dlat;
            double minLon = lon - dlng;
            double maxLon = lon + dlng;
            paramMap.put("minLon", minLon);
            paramMap.put("maxLon", maxLon);
            paramMap.put("minLat", minLat);
            paramMap.put("maxLat", maxLat);
            paramMap.put("oilStationType", oilStationType);
            List<Map<String, Object>> oilStationList = wxOilStationDao.getSimpleOilStationByCondition(paramMap);
            if (oilStationList != null && oilStationList.size() > 0) {
                Integer total = wxOilStationDao.getSimpleOilStationTotalByCondition(paramMap);
                resultDTO.setResultListTotal(total);
                oilStationStrList.addAll(MapUtil.getStringMapList(oilStationList));
                resultDTO.setResultList(oilStationStrList);

                resultDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                resultDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
            } else {
                //使用递归，务必查找到最近的加油站进行展示
                System.out.println("第 " + dis + " 次递归查询【民营加油站】...");
                if (dis == 10) {
                    resultDTO.setResultList(oilStationStrList);

                    resultDTO.setCode(OilStationMapCode.OIL_QUERY_IS_NULL.getNo());
                    resultDTO.setMessage(OilStationMapCode.OIL_QUERY_IS_NULL.getMessage());
                    return resultDTO;
                }
                dis++;
                paramMap.put("dis", dis);
                ResultDTO resultDTO_temp = getPrivateOilStationByCondition(paramMap);
                resultDTO.setResultList(oilStationStrList);
                resultDTO.getResultList().addAll(resultDTO_temp.getResultList());
            }
        } else {

            resultDTO.setResultList(oilStationStrList);
            resultDTO.setCode(OilStationMapCode.OIL_STATION_PARAM_IS_NOT_NULL.getNo());
            resultDTO.setMessage(OilStationMapCode.OIL_STATION_PARAM_IS_NOT_NULL.getMessage());
        }
        logger.info("在service中获取单一的加油站信息-getPrivateOilStationByCondition,结果-result:" + resultDTO);
        return resultDTO;
    }

    /**
     * 获取一个加油站信息
     * @param paramMap
     * @return
     */
    @Override
    public ResultMapDTO getOneOilStationByCondition(Map<String, Object> paramMap) {
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, String> resultMap = Maps.newHashMap();
        List<Map<String, String>> oilStationStrList = Lists.newArrayList();
        Integer total = 0;
        String uid = paramMap.get("uid") != null ? paramMap.get("uid").toString() : "";
        String oilStationName = paramMap.get("oilStationName") != null ? paramMap.get("oilStationName").toString() : "";
        Double lon = Double.parseDouble(paramMap.get("lon") != null ? paramMap.get("lon").toString() : "0");
        Double lat = Double.parseDouble(paramMap.get("lat") != null ? paramMap.get("lat").toString() : "0");
        Double dis = Double.parseDouble(paramMap.get("dis") != null ? paramMap.get("dis").toString() : "1");
        if (!"".equals(oilStationName) ||
                (!"".equals(lon) && !"".equals(lat))) {

            //纠正坐标，防止被刷
            if(lon < 0) {
                lon = 0 - lon;
            }
            if(lat < 0) {
                lat = 0 - lat;
            }

            //限定 运营人员 可以访问redis中的某个经纬度的加油站
            try (Jedis jedis = jedisPool.getResource()) {
                String currentLon = jedis.get(OilStationMapCode.CURRENT_LON_UID + uid);
                String currentLat = jedis.get(OilStationMapCode.CURRENT_LAT_UID + uid);
                logger.info("uid = " + uid + " , currentLon = " + currentLon + " , currentLat = " + currentLat);
                if(currentLon != null && !"".equals(currentLon)
                        && currentLat != null && !"".equals(currentLat)){
                    lon = Double.parseDouble(currentLon);
                    lat = Double.parseDouble(currentLat);
                }
            }

            int num = 1;
            List<Map<String, Object>> oilStationList = Lists.newArrayList();
            while (true){
                //计算查询点的经纬度范围
                double r = LonLatUtil.EARTH_RADIUS;//地球半径千米
                double dlng = 2 * Math.asin(Math.sin(dis / (2 * r)) / Math.cos(lat * Math.PI / 180));
                dlng = dlng * 180 / Math.PI;//角度转为弧度
                double dlat = dis / r;
                dlat = dlat * 180 / Math.PI;
                double minLat = lat - dlat;
                double maxLat = lat + dlat;
                double minLon = lon - dlng;
                double maxLon = lon + dlng;
                paramMap.put("minLat", minLat);
                paramMap.put("maxLat", maxLat);
                paramMap.put("minLon", minLon);
                paramMap.put("maxLon", maxLon);
                oilStationList = wxOilStationDao.getSimpleOilStationByCondition(paramMap);
                if(oilStationList != null && oilStationList.size() > 0){
                    total = oilStationList.size();
                    break;
                } else {
                    logger.info("第"+num+"次获取单个加油站，paramMap = " + JSONObject.toJSONString(paramMap) +
                            " ，oilStationList = " + JSONObject.toJSONString(oilStationList));
                    num++;
                    dis++;
                }
            }

            //通过循环遍历计算当前经纬度坐标与民营加油站的经纬度坐标之间的距离
            for (Map<String, Object> oilStationMap : oilStationList) {
                Double endLat = Double.parseDouble(oilStationMap.get("oilStationLat").toString());
                Double endLon = Double.parseDouble(oilStationMap.get("oilStationLon").toString());
                Double distance = LonLatUtil.getDistance(lat, lon, endLat, endLon);
                BigDecimal bg = new BigDecimal(distance);
                distance = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                oilStationMap.put("oilStationDistance", distance.toString());
                //清除是baiduMap和tencetMap的oilStationOwnerUid
                String oilStationOwnerUid = oilStationMap.get("oilStationOwnerUid").toString();
                Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
                //oilStationOwnerUid是数字，且与用户的uid相等时才可以修改
                if(!pattern.matcher(oilStationOwnerUid).matches()){
                    oilStationMap.put("oilStationOwnerUid", "");
                }
            }
            oilStationStrList = MapUtil.getStringMapList(oilStationList);
            //对oilStationList的距离进行排序
            Collections.sort(oilStationStrList, new Comparator<Map<String, String>>() {
                public int compare(Map<String, String> o1, Map<String, String> o2) {
                    Double oilStationDistance_1 = Double.parseDouble(o1.get("oilStationDistance").toString());
                    Double oilStationDistance_2 = Double.parseDouble(o2.get("oilStationDistance").toString());
                    return oilStationDistance_1.compareTo(oilStationDistance_2);
                }
            });

            logger.info("获取单个加油站列表，已按距离排序好，oilStationStrList : " + JSONObject.toJSONString(oilStationStrList));

            //移除不必要展示的数据，减少服务端与小程序端的网络压力
            for(Map<String, String> oilStationStrMap : oilStationStrList){
                oilStationStrMap.remove("updateTime");
                oilStationStrMap.remove("createTime");
                oilStationStrMap.remove("isManualModify");
                oilStationStrMap.remove("oilStationExhaust");
                oilStationStrMap.remove("oilStationBrandName");
                oilStationStrMap.remove("oilStationAreaName");
                oilStationStrMap.remove("oilStationDiscount");
                oilStationStrMap.remove("oilStationType");
                oilStationStrMap.remove("oilStationPayType");
                oilStationStrMap.remove("oilStationAreaSpell");
                oilStationStrMap.remove("oilStationPosition");
                //对招人网址进行检测
                String oilStationHireUrl = oilStationStrMap.get("oilStationHireUrl");
                if(oilStationHireUrl != null && !"".equals(oilStationHireUrl)){
                    String oilStationHireUrlFilePath = oilStationHireUrl.replace("https://www.91caihongwang.com/", "/opt/");
                    File oilStationHireUrlFile = new File(oilStationHireUrlFilePath);
                    File parentDir = oilStationHireUrlFile.getParentFile();
                    if(!parentDir.isDirectory()){            //加油站名称的英文编码的文件夹
                        parentDir.mkdir();
                    }
                    //获取加油站转义文件夹名称
                    String oilStationName_En = "";
                    String oilStationNameTemp = oilStationStrMap.get("oilStationName");
                    oilStationNameTemp = oilStationNameTemp.replace("/", "");
                    char[] tempArr = oilStationNameTemp.toCharArray();
                    for(int i = 0; i < tempArr.length; i++){
                        char temp = tempArr[i];
                        if(i == 0){
                            oilStationName_En = oilStationName_En + PingYingUtil.getPingYin(temp+"");
                        } else {
                            oilStationName_En = oilStationName_En + "_" + PingYingUtil.getPingYin(temp+"");
                        }
                    }
                    if(!oilStationHireUrlFile.exists() || oilStationHireUrlFile.length() <= 0 || !oilStationName_En.equals(parentDir.getName())){
                        oilStationHireUrl = this.createOilStationHireInfoUrl(oilStationStrMap.get("oilStationName"), oilStationStrMap.get("address"));
                        oilStationStrMap.put("oilStationHireUrl", oilStationHireUrl);
                        //重新创建招聘文件
                        Map<String, Object> paramMapTemp = Maps.newHashMap();
                        paramMapTemp.put("oilStationHireUrl", oilStationHireUrl);
                        paramMapTemp.put("oilStationHireTitle", oilStationStrMap.get("oilStationName")+"--招聘");
                        paramMapTemp.put("id", oilStationStrMap.get("id"));
                        wxOilStationDao.updateOilStation(paramMapTemp);
                    }
                } else {
                    oilStationHireUrl = this.createOilStationHireInfoUrl(oilStationStrMap.get("oilStationName"), oilStationStrMap.get("address"));
                    oilStationStrMap.put("oilStationHireUrl", oilStationHireUrl);
                    //重新创建招聘文件
                    Map<String, Object> paramMapTemp = Maps.newHashMap();
                    paramMapTemp.put("oilStationHireUrl", oilStationHireUrl);
                    paramMapTemp.put("oilStationHireTitle", oilStationStrMap.get("oilStationName")+"--招聘");
                    paramMapTemp.put("id", oilStationStrMap.get("id"));
                    wxOilStationDao.updateOilStation(paramMapTemp);
                }
            }
            //list 删除除第一个之外，后续所有的元素，避免所有元素传输暂用网络带宽
            if(oilStationStrList.size() >= 2) {
                for (int i = (oilStationStrList.size()-1); i > 0; i--) {
                    oilStationStrList.remove(i);
                }
            }

            resultMapDTO.setResultListTotal(total);
            resultMap.put("oilStationName", oilStationStrList.get(0).get("oilStationName").toString());
            resultMap.put("oilStationList", JSONObject.toJSONString(oilStationStrList));
            resultMapDTO.setResultMap(resultMap);
            resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
            resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        } else {
            resultMapDTO.setCode(OilStationMapCode.OIL_STATION_PARAM_IS_NOT_NULL.getNo());
            resultMapDTO.setMessage(OilStationMapCode.OIL_STATION_PARAM_IS_NOT_NULL.getMessage());
        }
        logger.info("在service中获取单一的加油站信息-getSimpleOilStationByCondition,结果-result:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 根据经纬度地址获取所处的加油站
     *
     * @param paramMap
     * @return
     */
    @Override
    public ResultDTO getOilStationByLonLat(Map<String, Object> paramMap) {
        ResultDTO resultDTO = new ResultDTO();
        Map<String, String> resultMap = Maps.newHashMap();
        List<Map<String, String>> oilStationStrList = Lists.newArrayList();
        Float lon = paramMap.get("lon") != null ? Float.parseFloat(paramMap.get("lon").toString()) : 0;
        Float lat = paramMap.get("lat") != null ? Float.parseFloat(paramMap.get("lat").toString()) : 0;
        Float dis = paramMap.get("dis") != null ? Float.parseFloat(paramMap.get("dis").toString()) : 3;
        if (!"".equals(lon) && !"".equals(lat) && !"".equals(dis)) {
            //1.现在数据库中查找是否存在金纬度范围之内的加油站
            //先计算查询点的经纬度范围
            double r = LonLatUtil.EARTH_RADIUS;//地球半径千米
            double dlng = 2 * Math.asin(Math.sin(dis / (2 * r)) / Math.cos(lat * Math.PI / 180));
            dlng = dlng * 180 / Math.PI;//角度转为弧度
            double dlat = dis / r;
            dlat = dlat * 180 / Math.PI;
            double minLat = lat - dlat;
            double maxLat = lat + dlat;
            double minLon = lon - dlng;
            double maxLon = lon + dlng;
            paramMap.put("minLon", minLon);
            paramMap.put("maxLon", maxLon);
            paramMap.put("minLat", minLat);
            paramMap.put("maxLat", maxLat);
            List<Map<String, Object>> oilStationList = wxOilStationDao.getSimpleOilStationByCondition(paramMap);
            if (oilStationList != null && oilStationList.size() > 0) {
                oilStationStrList.addAll(MapUtil.getStringMapList(oilStationList));
                //2.通过循环遍历计算当前经纬度坐标与民营加油站的经纬度坐标之间的距离
                for (Map<String, String> oilStationMap : oilStationStrList) {
                    Double endLat = Double.parseDouble(oilStationMap.get("oilStationLat").toString());
                    Double endLon = Double.parseDouble(oilStationMap.get("oilStationLon").toString());
                    Double distance = LonLatUtil.getDistance(lat, lon, endLat, endLon);
                    oilStationMap.put("oilStationDistance", distance.toString());
                }
                //3.对oilStationPriceList进行排序
                Collections.sort(oilStationStrList, new Comparator<Map<String, String>>() {
                    public int compare(Map<String, String> o1, Map<String, String> o2) {
                        Double oilStationDistance_1 = Double.parseDouble(o1.get("oilStationDistance").toString());
                        Double oilStationDistance_2 = Double.parseDouble(o2.get("oilStationDistance").toString());
                        return oilStationDistance_1.compareTo(oilStationDistance_2);
                    }
                });
                resultDTO.setResultList(oilStationStrList);
                resultDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                resultDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
            } else {
                resultDTO.setResultList(oilStationStrList);

                resultDTO.setCode(OilStationMapCode.OIL_QUERY_IS_NULL.getNo());
                resultDTO.setMessage(OilStationMapCode.OIL_QUERY_IS_NULL.getMessage());
                return resultDTO;
            }
        } else {

            resultDTO.setResultList(oilStationStrList);
            resultDTO.setCode(OilStationMapCode.OIL_STATION_PARAM_IS_NOT_NULL.getNo());
            resultDTO.setMessage(OilStationMapCode.OIL_STATION_PARAM_IS_NOT_NULL.getMessage());
        }
        logger.info("在service中根据经纬度地址获取所处的加油站-getOilStationByLonLat,结果-result:" + resultDTO);
        return resultDTO;
    }

    /**
     * 添加或者更新加油站
     *      术语解释:
     *          添加油站：添加不存在的并且有油价信息的加油站
     *          纠正油价：修改当前的加油站油价
     *      不管用户是【添加油站】还是【纠正油价】，第二天均有红包奖励
     * @param paramMap
     * @return
     */
    @Override
    public BoolDTO addOrUpdateOilStation(Map<String, Object> paramMap) {
        Integer addNum = 0;
        Integer updateNum = 0;
        BoolDTO boolDTO = new BoolDTO();
        String uid = paramMap.get("uid") != null ? paramMap.get("uid").toString() : "";
        String oilStationCode = paramMap.get("oilStationCode") != null ? paramMap.get("oilStationCode").toString() : "";
        String oilStationName = paramMap.get("oilStationName") != null ? paramMap.get("oilStationName").toString() : "";
        String oilStationAdress = paramMap.get("oilStationAdress") != null ? paramMap.get("oilStationAdress").toString() : "";
        String oilStationLon = paramMap.get("oilStationLon") != null ? paramMap.get("oilStationLon").toString() : "";
        String oilStationLat = paramMap.get("oilStationLat") != null ? paramMap.get("oilStationLat").toString() : "";
        String oilStationPrice = paramMap.get("oilStationPrice") != null ? paramMap.get("oilStationPrice").toString() : "";
        String oilStationType = paramMap.get("oilStationType") != null ? paramMap.get("oilStationType").toString() : "民营";
        paramMap.put("oilStationType", oilStationType);
        paramMap.put("isManualModify", 1);

        //如果数据添加或更改来源是百度地图或者腾讯地图，则不需要校验用户是否是加油站业主
        String oilStationOwnerUid = paramMap.get("oilStationOwnerUid") != null ? paramMap.get("oilStationOwnerUid").toString() : "";

        if (!"".equals(oilStationName) && !"".equals(oilStationAdress) &&
                !"".equals(oilStationLat) && !"".equals(oilStationLon) &&
                    !"".equals(oilStationPrice)
        ) {
            String oilStationPosition = oilStationLon + "," + oilStationLat;
            paramMap.put("oilStationPosition", oilStationPosition);
            paramMap.put("operator", "addOilStation");
            Map<String, Object> paramMap_temp = Maps.newHashMap();
            if (!"".equals(oilStationCode)) {              //存在oilStationCode，则更新
                //1.检测oilStationCode是否有效
                paramMap_temp.put("oilStationCode", oilStationCode);
                Integer total = wxOilStationDao.getSimpleOilStationTotalByCondition(paramMap_temp);
                if (total == null || total <= 0) {
                    //1.1 检测oilStationCode无效
                    paramMap_temp.clear();      //清空参数，重新整理参数
                    Long oilStationCode_l = 0L;
                    Map<String, Object> maxOilStationCodeMap = wxOilStationDao.getMaxOilStationCode(paramMap_temp);
                    if (maxOilStationCodeMap.size() > 0) {
                        oilStationCode_l = Long.parseLong(maxOilStationCodeMap.get("oilStationCode").toString());
                        oilStationCode_l++;
                        paramMap.put("oilStationCode", oilStationCode_l);
                    }
                    //添加油站
                    String city = LonLatUtil.getAddressByLonLat(Double.parseDouble(oilStationLon),
                            Double.parseDouble(oilStationLat), "city");
                    String nation_province_city_district = LonLatUtil.getAddressByLonLat(Double.parseDouble(oilStationLon),
                            Double.parseDouble(oilStationLat), "nation_province_city_district");
                    paramMap.put("oilStationName", oilStationName);
                    paramMap.put("oilStationAreaSpell", PingYingUtil.getPingYin(city));
                    paramMap.put("oilStationAreaName",nation_province_city_district);
                    paramMap.put("oilStationAdress", oilStationAdress);
                    String oilStationBrandName = "民营";
                    oilStationBrandName = getOilStationBrankName(oilStationName);
                    paramMap.put("oilStationBrandName", oilStationBrandName);
                    paramMap.put("oilStationType", "汽车:加油站;");
                    paramMap.put("oilStationDiscount", "折扣店");
                    paramMap.put("oilStationExhaust", "0#,92#,95#,98#");
                    paramMap.put("oilStationPosition", oilStationLon+","+oilStationLat);
                    paramMap.put("oilStationLon", oilStationLon);
                    paramMap.put("oilStationLat", oilStationLat);
                    paramMap.put("oilStationPayType", "微信，支付宝，银联，现金，赊账等");
                    paramMap.put("oilStationPrice", oilStationPrice);
                    paramMap.put("oilStationDistance", "待定");
                    paramMap.put("isManualModify", "0");
                    paramMap.put("oilStationHireUrl", this.createOilStationHireInfoUrl(oilStationName, oilStationAdress));
                    paramMap.put("oilStationHireTitle", oilStationName+"--招聘");
                    logger.info("开始新增 加油站 数据， paramMap = " + JSONObject.toJSONString(paramMap));
                    addNum = wxOilStationDao.addOilStation(paramMap);
                    if (addNum != null && addNum > 0) {
                        boolDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                        boolDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
                        //新增【添加油站】的操作，便于后期发送红包的参考
                        Map<String, Object> oilStationOperator_paramMap = Maps.newHashMap();
                        oilStationOperator_paramMap.put("status", "0");
                        oilStationOperator_paramMap.put("uid", paramMap.get("uid"));
                        oilStationOperator_paramMap.put("operator", "addOilStation");
                        oilStationOperator_paramMap.put("oilStationCode", oilStationCode_l);
                        wxOilStationOperatorService.addOilStationOperator(oilStationOperator_paramMap);
                    } else {
                        boolDTO.setCode(OilStationMapCode.NO_DATA_CHANGE.getNo());
                        boolDTO.setMessage(OilStationMapCode.NO_DATA_CHANGE.getMessage());
                    }
                } else {
                    //1.2 检测oilStationCode有效,如果是百度地图则不更新坐标
                    if("baiduMap".equals(oilStationOwnerUid)){      //百度地图的数据不更新坐标
                        paramMap.remove("oilStationPosition");
                        paramMap.remove("oilStationLon");
                        paramMap.remove("oilStationLat");
                    }
                    if(!"".equals(oilStationOwnerUid)){
                        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
                        //oilStationOwnerUid是数字，且与用户的uid相等时才可以修改
                        if(pattern.matcher(oilStationOwnerUid).matches()
                                && !oilStationOwnerUid.equals(uid)){
                            logger.info("用户(uid="+uid+")不是当前加油站业主(oilStationOwnerUid="+oilStationOwnerUid+"),操作无效.");
                            //向 管理员汇报 有人恶意修改油价
                            paramMap.clear();//清空参数，重新准备参数
                            paramMap.put("uid", uid);
                            paramMap.put("oilStationCode", oilStationCode);
                            paramMap.put("oilStationName", oilStationName);
                            new Thread(){
                                public void run(){
                                    try{
                                        wxMessageService.dailyIllegalUpdateOilPriceMessageSend(paramMap);
                                    } catch (Exception e) {
                                        logger.info("向管理员发送有人恶意串改加油站油价信息失败，e : ", e);
                                    }
                                }
                            }.start();
                            boolDTO.setCode(OilStationMapCode.IS_NOT_OIL_STATION_OWNER_UID.getNo());
                            boolDTO.setMessage(OilStationMapCode.IS_NOT_OIL_STATION_OWNER_UID.getMessage().replace("AAA", oilStationName));
                            return boolDTO;
                        }
                    }
                    logger.info("开始更新 加油站 数据， paramMap = " + JSONObject.toJSONString(paramMap));
                    updateNum = wxOilStationDao.updateOilStation(paramMap);
                    if (updateNum != null && updateNum > 0) {
                        boolDTO.setCode(OilStationMapCode.OIL_STATION_EXIST_AND_UPDATE.getNo());
                        boolDTO.setMessage(OilStationMapCode.OIL_STATION_EXIST_AND_UPDATE.getMessage());
                        //新增【添加油站】的操作，便于后期发送红包的参考
                        Map<String, Object> oilStationOperator_paramMap = Maps.newHashMap();
                        oilStationOperator_paramMap.put("status", "0");//状态，-1表示审核拒绝, 0表示待审核, 1表示审核通过且待处理, 2表示已处理
                        oilStationOperator_paramMap.put("uid", paramMap.get("uid"));
                        oilStationOperator_paramMap.put("operator", "updateOilStation");
                        oilStationOperator_paramMap.put("oilStationCode", oilStationCode);
                        wxOilStationOperatorService.addOilStationOperator(oilStationOperator_paramMap);
                    } else {
                        boolDTO.setCode(OilStationMapCode.NO_DATA_CHANGE.getNo());
                        boolDTO.setMessage(OilStationMapCode.NO_DATA_CHANGE.getMessage());
                    }
                }
            } else {
                //2.通过加油站名称判断加油站是否存在
                //主要来自于百度地图，腾讯地图的数据，检测 当前加油站名称 是否存在
                paramMap_temp.clear();      //清空参数，重新整理参数
                paramMap_temp.put("oilStationName", oilStationName);
                Integer total = wxOilStationDao.getSimpleOilStationTotalByCondition(paramMap_temp);
                if(total != null && total > 0){
                    //2.1加油站存在
                    List<Map<String, Object>> existOilStationList = wxOilStationDao.getSimpleOilStationByCondition(paramMap_temp);
                    if(existOilStationList != null && existOilStationList.size() > 0){
                        Map<String, Object> existOilStation = existOilStationList.get(0);
                        paramMap.put("oilStationCode", existOilStation.get("oilStationCode"));
                        logger.info("开始更新 加油站 数据， paramMap = " + JSONObject.toJSONString(paramMap));
                        if("baiduMap".equals(oilStationOwnerUid)){      //百度地图的数据不更新坐标
                            paramMap.remove("oilStationPosition");
                            paramMap.remove("oilStationLon");
                            paramMap.remove("oilStationLat");
                        }
                        if(!"".equals(oilStationOwnerUid)){
                            Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
                            //oilStationOwnerUid是数字，且与用户的uid相等时才可以修改
                            if(pattern.matcher(oilStationOwnerUid).matches()
                                    && oilStationOwnerUid.equals(uid)){
                                logger.info("对不起，您(uid="+uid+")不是当前加油站业主(oilStationOwnerUid="+oilStationOwnerUid+"),您的操作无效.");
                                //向 管理员汇报 有人恶意修改油价
                                paramMap.clear();//清空参数，重新准备参数
                                paramMap.put("uid", uid);
                                paramMap.put("oilStationCode", oilStationCode);
                                paramMap.put("oilStationName", oilStationName);
                                new Thread(){
                                    public void run(){
                                        try{
                                            wxMessageService.dailyIllegalUpdateOilPriceMessageSend(paramMap);
                                        } catch (Exception e) {
                                            logger.info("向管理员发送有人恶意串改加油站油价信息失败，e : ", e);
                                        }
                                    }
                                }.start();
                                boolDTO.setCode(OilStationMapCode.IS_NOT_OIL_STATION_OWNER_UID.getNo());
                                boolDTO.setMessage(OilStationMapCode.IS_NOT_OIL_STATION_OWNER_UID.getMessage());
                                return boolDTO;
                            }
                        }
                        updateNum = wxOilStationDao.updateOilStation(paramMap);
                        if (updateNum != null && updateNum > 0) {
                            boolDTO.setCode(OilStationMapCode.OIL_STATION_EXIST_AND_UPDATE.getNo());
                            boolDTO.setMessage(OilStationMapCode.OIL_STATION_EXIST_AND_UPDATE.getMessage());
                            //新增【添加油站】的s操作，便于后期发送红包的参考
                            Map<String, Object> oilStationOperator_paramMap = Maps.newHashMap();
                            oilStationOperator_paramMap.put("status", "0");//状态，-1表示审核拒绝, 0表示待审核, 1表示审核通过且待处理, 2表示已处理
                            oilStationOperator_paramMap.put("uid", paramMap.get("uid"));
                            oilStationOperator_paramMap.put("operator", "updateOilStation");
                            oilStationOperator_paramMap.put("oilStationCode", existOilStation.get("oilStationCode"));
                            wxOilStationOperatorService.addOilStationOperator(oilStationOperator_paramMap);
                        } else {
                            boolDTO.setCode(OilStationMapCode.NO_DATA_CHANGE.getNo());
                            boolDTO.setMessage(OilStationMapCode.NO_DATA_CHANGE.getMessage());
                        }
                    }
                } else {
                    Long oilStationCode_l = 1L;
                    Map<String, Object> maxOilStationCodeMap = wxOilStationDao.getMaxOilStationCode(paramMap_temp);
                    if (maxOilStationCodeMap != null && maxOilStationCodeMap.size() > 0) {
                        oilStationCode_l = Long.parseLong(maxOilStationCodeMap.get("oilStationCode").toString());
                        oilStationCode_l++;
                        paramMap.put("oilStationCode", oilStationCode_l);
                    } else {
                        paramMap.put("oilStationCode", oilStationCode_l);
                    }
                    paramMap.put("oilStationName", oilStationName);
                    paramMap.put("oilStationAdress", oilStationAdress);
                    String oilStationBrandName = "民营";
                    oilStationBrandName = getOilStationBrankName(oilStationName);
                    paramMap.put("oilStationBrandName", oilStationBrandName);
                    paramMap.put("oilStationType", "汽车:加油站;");
                    paramMap.put("oilStationDiscount", "折扣店");
                    paramMap.put("oilStationExhaust", "0#,92#,95#,98#");
                    paramMap.put("oilStationPosition", oilStationLon+","+oilStationLat);
                    paramMap.put("oilStationLon", oilStationLon);
                    paramMap.put("oilStationLat", oilStationLat);
                    paramMap.put("oilStationPayType", "微信，支付宝，银联，现金，赊账等");
                    paramMap.put("oilStationPrice", oilStationPrice);
                    paramMap.put("oilStationDistance", "待定");
                    paramMap.put("isManualModify", "0");
                    paramMap.put("oilStationHireUrl", this.createOilStationHireInfoUrl(oilStationName, oilStationAdress));
                    paramMap.put("oilStationHireTitle", oilStationName+"--招聘");
                    logger.info("开始新增 加油站 数据， paramMap = " + JSONObject.toJSONString(paramMap));
                    addNum = wxOilStationDao.addOilStation(paramMap);
                    if (addNum != null && addNum > 0) {
                        boolDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                        boolDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
                        //新增【添加油站】的操作，便于后期发送红包的参考
                        Map<String, Object> oilStationOperator_paramMap = Maps.newHashMap();
                        oilStationOperator_paramMap.put("status", "0");
                        oilStationOperator_paramMap.put("uid", paramMap.get("uid"));
                        oilStationOperator_paramMap.put("operator", "addOilStation");
                        oilStationOperator_paramMap.put("oilStationCode", oilStationCode_l);
                        wxOilStationOperatorService.addOilStationOperator(oilStationOperator_paramMap);
                    } else {
                        boolDTO.setCode(OilStationMapCode.NO_DATA_CHANGE.getNo());
                        boolDTO.setMessage(OilStationMapCode.NO_DATA_CHANGE.getMessage());
                    }
                }
            }
        } else {
            boolDTO.setCode(OilStationMapCode.OIL_STATION_PARAM_IS_NOT_NULL.getNo());
            boolDTO.setMessage(OilStationMapCode.OIL_STATION_PARAM_IS_NOT_NULL.getMessage());
        }
        logger.info("在service中添加加油站-addOilStation,结果-result:" + boolDTO);
        return boolDTO;
    }

    /**
     * 添加加油站
     *
     * @param paramMap
     * @return
     */
    @Override
    public BoolDTO addOilStation(Map<String, Object> paramMap) {
        Integer addNum = 0;
        Integer updateNum = 0;
        BoolDTO boolDTO = new BoolDTO();
        String oilStationCode = paramMap.get("oilStationCode") != null ? paramMap.get("oilStationCode").toString() : "";
        String oilStationName = paramMap.get("oilStationName") != null ? paramMap.get("oilStationName").toString() : "";
        String oilStationAreaSpell = paramMap.get("oilStationAreaSpell") != null ? paramMap.get("oilStationAreaSpell").toString() : "";
        String oilStationAreaName = paramMap.get("oilStationAreaName") != null ? paramMap.get("oilStationAreaName").toString() : "";
        String oilStationAdress = paramMap.get("oilStationAdress") != null ? paramMap.get("oilStationAdress").toString() : "";
        String oilStationBrandName = paramMap.get("oilStationBrandName") != null ? paramMap.get("oilStationBrandName").toString() : "";
        String oilStationType = paramMap.get("oilStationType") != null ? paramMap.get("oilStationType").toString() : "";
        String oilStationDiscount = paramMap.get("oilStationDiscount") != null ? paramMap.get("oilStationDiscount").toString() : "";
        String oilStationExhaust = paramMap.get("oilStationExhaust") != null ? paramMap.get("oilStationExhaust").toString() : "";
        String oilStationPosition = paramMap.get("oilStationPosition") != null ? paramMap.get("oilStationPosition").toString() : "";
        String oilStationLon = paramMap.get("oilStationLon") != null ? paramMap.get("oilStationLon").toString() : "";
        String oilStationLat = paramMap.get("oilStationLat") != null ? paramMap.get("oilStationLat").toString() : "";
        String oilStationPayType = paramMap.get("oilStationPayType") != null ? paramMap.get("oilStationPayType").toString() : "";
        String oilStationPrice = paramMap.get("oilStationPrice") != null ? paramMap.get("oilStationPrice").toString() : "";
        String oilStationDistance = paramMap.get("oilStationDistance") != null ? paramMap.get("oilStationDistance").toString() : "";
        if (
            !"".equals(oilStationCode) && !"".equals(oilStationName) && !"".equals(oilStationAreaSpell) &&
                    !"".equals(oilStationAreaName) && !"".equals(oilStationAdress) && !"".equals(oilStationBrandName) &&
                    !"".equals(oilStationType) && !"".equals(oilStationDiscount) && !"".equals(oilStationExhaust) &&
                    !"".equals(oilStationPosition) && !"".equals(oilStationLon) && !"".equals(oilStationLat) &&
                    !"".equals(oilStationPayType) && !"".equals(oilStationPrice) && !"".equals(oilStationDistance)
        ) {
            Map<String, Object> paramMap_temp = Maps.newHashMap();
            paramMap_temp.put("oilStationCode", oilStationCode);
            paramMap_temp.put("oilStationType", oilStationType);
            Integer total = wxOilStationDao.getSimpleOilStationTotalByCondition(paramMap_temp);
            if (total != null && total <= 0) {
                addNum = wxOilStationDao.addOilStation(paramMap);
                if (addNum != null && addNum > 0) {

                    boolDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                    boolDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
                } else {

                    boolDTO.setCode(OilStationMapCode.NO_DATA_CHANGE.getNo());
                    boolDTO.setMessage(OilStationMapCode.NO_DATA_CHANGE.getMessage());
                }
            } else {
                updateNum = wxOilStationDao.updateOilStation(paramMap);
                if (updateNum != null && updateNum > 0) {

                    boolDTO.setCode(OilStationMapCode.OIL_STATION_EXIST_AND_UPDATE.getNo());
                    boolDTO.setMessage(OilStationMapCode.OIL_STATION_EXIST_AND_UPDATE.getMessage());
                } else {

                    boolDTO.setCode(OilStationMapCode.NO_DATA_CHANGE.getNo());
                    boolDTO.setMessage(OilStationMapCode.NO_DATA_CHANGE.getMessage());
                }


            }
        } else {

            boolDTO.setCode(OilStationMapCode.OIL_STATION_PARAM_IS_NOT_NULL.getNo());
            boolDTO.setMessage(OilStationMapCode.OIL_STATION_PARAM_IS_NOT_NULL.getMessage());
        }
        logger.info("在service中添加加油站-addOilStation,结果-result:" + boolDTO);
        return boolDTO;
    }

    /**
     * 删除加油站
     *
     * @param paramMap
     * @return
     */
    @Override
    public BoolDTO deleteOilStation(Map<String, Object> paramMap) {
        Integer deleteNum = 0;
        BoolDTO boolDTO = new BoolDTO();
        String id = paramMap.get("id") != null ? paramMap.get("id").toString() : "";
        String oilStationCode = paramMap.get("oilStationCode") != null ? paramMap.get("oilStationCode").toString() : "";
        if (!"".equals(id) || !"".equals(oilStationCode)) {
            deleteNum = wxOilStationDao.deleteOilStation(paramMap);
            if (deleteNum != null && deleteNum > 0) {

                boolDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                boolDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
            } else {

                boolDTO.setCode(OilStationMapCode.NO_DATA_CHANGE.getNo());
                boolDTO.setMessage(OilStationMapCode.NO_DATA_CHANGE.getMessage());
            }
        } else {

            boolDTO.setCode(OilStationMapCode.OIL_STATION_PARAM_IS_NOT_NULL.getNo());
            boolDTO.setMessage(OilStationMapCode.OIL_STATION_PARAM_IS_NOT_NULL.getMessage());
        }
        logger.info("在service中删除加油站-deleteOilStation,结果-result:" + boolDTO);
        return boolDTO;
    }

    /**
     * 更新加油站
     *
     * @param paramMap
     * @return
     */
    @Override
    public BoolDTO updateOilStation(Map<String, Object> paramMap) {
        Integer updateNum = 0;
        BoolDTO boolDTO = new BoolDTO();
        String id = paramMap.get("id") != null ? paramMap.get("id").toString() : "";
        String oilStationCode = paramMap.get("oilStationCode") != null ? paramMap.get("oilStationCode").toString() : "";
        if (!"".equals(id) || !"".equals(oilStationCode)) {
            updateNum = wxOilStationDao.updateOilStation(paramMap);
            if (updateNum != null && updateNum > 0) {

                boolDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                boolDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
            } else {

                boolDTO.setCode(OilStationMapCode.NO_DATA_CHANGE.getNo());
                boolDTO.setMessage(OilStationMapCode.NO_DATA_CHANGE.getMessage());
            }
        } else {

            boolDTO.setCode(OilStationMapCode.OIL_STATION_PARAM_IS_NOT_NULL.getNo());
            boolDTO.setMessage(OilStationMapCode.OIL_STATION_PARAM_IS_NOT_NULL.getMessage());
        }
        logger.info("在service中更新加油站-updateOilStation,结果-result:" + boolDTO);
        return boolDTO;
    }

    /**
     * 获取单一的加油站信息
     *
     * @param paramMap
     * @return
     */
    @Override
    public ResultDTO getSimpleOilStationByCondition(Map<String, Object> paramMap) {
        ResultDTO resultDTO = new ResultDTO();
        Map<String, String> resultMap = Maps.newHashMap();
        List<Map<String, String>> oilStationStrList = Lists.newArrayList();
        String oilStationName = paramMap.get("oilStationName") != null ? paramMap.get("oilStationName").toString() : "";
        Float lon = paramMap.get("lon") != null ? Float.parseFloat(paramMap.get("lon").toString()) : 0;
        Float lat = paramMap.get("lat") != null ? Float.parseFloat(paramMap.get("lat").toString()) : 0;
        Float dis = paramMap.get("dis") != null ? Float.parseFloat(paramMap.get("dis").toString()) : 1;
        if (!"".equals(oilStationName) ||
                (!"".equals(lon) && !"".equals(lat))) {
            //1.现在数据库中查找是否存在金纬度范围之内的加油站
            //先计算查询点的经纬度范围
            double r = LonLatUtil.EARTH_RADIUS;//地球半径千米
            double dlng = 2 * Math.asin(Math.sin(dis / (2 * r)) / Math.cos(lat * Math.PI / 180));
            dlng = dlng * 180 / Math.PI;//角度转为弧度
            double dlat = dis / r;
            dlat = dlat * 180 / Math.PI;
            double minLat = lat - dlat;
            double maxLat = lat + dlat;
            double minLon = lon - dlng;
            double maxLon = lon + dlng;
            paramMap.put("minLon", minLon);
            paramMap.put("maxLon", maxLon);
            paramMap.put("minLat", minLat);
            paramMap.put("maxLat", maxLat);
            List<Map<String, Object>> oilStationList = wxOilStationDao.getSimpleOilStationByCondition(paramMap);
            if (oilStationList != null && oilStationList.size() > 0) {
                Integer total = wxOilStationDao.getSimpleOilStationTotalByCondition(paramMap);
                resultDTO.setResultListTotal(total);
                oilStationStrList.addAll(MapUtil.getStringMapList(oilStationList));
                resultDTO.setResultList(oilStationStrList);

                resultDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                resultDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
            } else {
                //使用递归，务必查找到最近的加油站进行展示
                System.out.println("第 " + dis + " 次递归查询【数据库中加油站】...");
                if (dis == 10) {
                    resultDTO.setResultList(oilStationStrList);

                    resultDTO.setCode(OilStationMapCode.OIL_QUERY_IS_NULL.getNo());
                    resultDTO.setMessage(OilStationMapCode.OIL_QUERY_IS_NULL.getMessage());
                    return resultDTO;
                }
                dis++;
                paramMap.put("dis", dis);
                ResultDTO resultDTO_temp = getSimpleOilStationByCondition(paramMap);
                resultDTO.setResultList(oilStationStrList);
                resultDTO.getResultList().addAll(resultDTO_temp.getResultList());
            }
        } else {

            resultDTO.setResultList(oilStationStrList);
            resultDTO.setCode(OilStationMapCode.OIL_STATION_PARAM_IS_NOT_NULL.getNo());
            resultDTO.setMessage(OilStationMapCode.OIL_STATION_PARAM_IS_NOT_NULL.getMessage());
        }
        logger.info("在service中获取单一的加油站信息-getSimpleOilStationByCondition,结果-result:" + resultDTO);
        return resultDTO;
    }

    /**
     * 通过excel的方式导入加油站数据
     */
    @Override
    public ResultMapDTO batchImportOilStationByExcel(Map<String, Object> paramMap) throws Exception {
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Integer addNum = 0;
        Integer updateNum = 0;
        String destFilePath = paramMap.get("destFilePath") != null ? paramMap.get("destFilePath").toString() : "";
        if ("".equals(destFilePath)) {
            destFilePath = "/Users/caihongwang/Downloads/加油站名单.xlsx";
        }
        if (!"".equals(destFilePath)) {
            ImportExcelUtil<OilStationVO> importExcelUtil = new ImportExcelUtil<>();
            importExcelUtil.read(destFilePath, OilStationVO.class, 0);
            List<OilStationVO> oilStationVOList = importExcelUtil.getData();
            System.out.println("========================开始导入数据===========================");
            if (oilStationVOList != null && oilStationVOList.size() > 0) {
                for (int i = 0; i < oilStationVOList.size(); i++) {
                    paramMap.clear();
                    OilStationVO oilStationVO = oilStationVOList.get(i);
                    if (oilStationVO.getOilStationName() == null || "".equals(oilStationVO.getOilStationName())
                            || oilStationVO.getOilStationAddress() == null || "".equals(oilStationVO.getOilStationAddress())
                            || oilStationVO.getFireControl() == null || "".equals(oilStationVO.getFireControl())) {
                        System.out.println(oilStationVO);
                    }
                    String oilStationCode = 10000000 + i + 1 + "";
                    String oilStationName = oilStationVO.getOilStationName();
                    if (!oilStationName.contains("中")) {
                        String queryAddressUrl = OilStationMapCode.TENCENT_KEY_WORD_SEARCH +
                                "?key=" + OilStationMapCode.TENCENT_KEY + "&region=" + "贵州" + "&keyword=" + oilStationName;
                        String addressDataJson = httpsUtil.get(queryAddressUrl, MapUtil.getStringMap(paramMap));
                        logger.info("在service中获取加油站列表-getAddressByLonLat, 从腾讯地图获取地址的结果 : " + addressDataJson);
                        JSONObject addressData_JSONObject = JSONObject.parseObject(addressDataJson);
                        String statusStr = addressData_JSONObject.getString("status");
                        if ("0".equals(statusStr)) {
                            JSONArray data_JSONArray = addressData_JSONObject.getJSONArray("data");
                            if (data_JSONArray.size() > 0) {
                                JSONObject data_JSONObject = data_JSONArray.getJSONObject(0);        //全路径 地址
                                String addressStr = data_JSONObject.getString("address");        //全路径 地址
                                String adcodeStr = data_JSONObject.getString("adcode");        // 邮编
                                String categoryStr = data_JSONObject.getString("category");        // 描述
                                JSONObject location_JSONObject = data_JSONObject.getJSONObject("location");        // 经纬度
                                String lonStr = location_JSONObject.getString("lng");        // 经度
                                String latStr = location_JSONObject.getString("lat");        // 纬度
                                String provinceStr = data_JSONObject.getString("province");        // 省份
                                String cityStr = data_JSONObject.getString("city");        //  城市
                                String districtStr = data_JSONObject.getString("district");        //  区域

                                String oilStationAreaSpell = adcodeStr;
                                String oilStationAreaName = provinceStr + " " + cityStr + " " + districtStr;
                                String oilStationAdress = addressStr;
                                String oilStationBrandName = "民营店";
                                String oilStationType = "民营";
                                String oilStationDiscount = "打折加油站";
                                String oilStationPosition = lonStr + "," + latStr;
                                String oilStationLon = lonStr;
                                String oilStationLat = latStr;
                                String oilStationPayType = categoryStr;
                                String oilStationPrice = "[{\"oilPriceLabel\":\"6.89\",\"oilModelLabel\":\"0\",\"oilNameLabel\":\"柴油\"},{\"oilPriceLabel\":\"7.29\",\"oilModelLabel\":\"92\",\"oilNameLabel\":\"汽油\"},{\"oilPriceLabel\":\"7.89\",\"oilModelLabel\":\"95\",\"oilNameLabel\":\"汽油\"}]";
                                String oilStationDistance = "3537";
                                String oilStationWxPaymentCodeImgUrl = "";

                                paramMap.put("oilStationCode", oilStationCode);
                                paramMap.put("oilStationName", oilStationName);
                                paramMap.put("oilStationAreaSpell", oilStationAreaSpell);
                                paramMap.put("oilStationAreaName", oilStationAreaName);
                                paramMap.put("oilStationAdress", oilStationAdress);
                                paramMap.put("oilStationBrandName", oilStationBrandName);
                                paramMap.put("oilStationType", oilStationType);
                                paramMap.put("oilStationDiscount", oilStationDiscount);
                                paramMap.put("oilStationPosition", oilStationPosition);
                                paramMap.put("oilStationLon", oilStationLon);
                                paramMap.put("oilStationLat", oilStationLat);
                                paramMap.put("oilStationPayType", oilStationPayType);
                                paramMap.put("oilStationPrice", oilStationPrice);
                                paramMap.put("oilStationDistance", oilStationDistance);
                                paramMap.put("oilStationWxPaymentCodeImgUrl", oilStationWxPaymentCodeImgUrl);

                                Map<String, Object> paramMap_temp = Maps.newHashMap();
                                paramMap_temp.put("oilStationName", oilStationName);
                                List<Map<String, Object>> oilStationList = wxOilStationDao.getSimpleOilStationByCondition(paramMap_temp);
                                if (oilStationList != null && oilStationList.size() > 0) {
                                    String id = oilStationList.get(0).get("id").toString();
                                    paramMap.put("id", id);
                                    updateNum = updateNum + wxOilStationDao.updateOilStation(paramMap);
                                } else {
                                    addNum = addNum + wxOilStationDao.addOilStation(paramMap);
                                }
                            } else {
                                System.out.println("未出现在腾讯地图上的加油站：  oilStationName = " + oilStationName);
                            }
                            System.out.println("开始沉睡 5 秒.");
                            Thread.sleep(5000);
                        }
                    } else {
                        System.out.println("三【中】加油站：  oilStationName = " + oilStationName);
                    }
                }
            }
            Map<String, String> resultMap = Maps.newHashMap();
            resultMap.put("addNum", addNum != null ? addNum.toString() : "0");
            resultMap.put("updateNum", updateNum != null ? updateNum.toString() : "0");
            resultMapDTO.setResultMap(resultMap);

            resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
            resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
            System.out.println("======================================================");
            System.out.println(resultMapDTO);
        } else {
            resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        return resultMapDTO;
    }

    /**
     * 创建加油站招聘信息
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Override
    public String createOilStationHireInfoUrl(String oilStationName, String oilStationAddress){
        String oilStationHireInfoUrl = "https://www.91caihongwang.com/resourceOfOilStationMap/webapp/hire/zhong_guo_shi_hua_song_tao_da_lu_tian_ba_jia_you_zhan/index.html";
        String baseUrl = "https://www.91caihongwang.com/resourceOfOilStationMap/webapp/hire/";
        if("中国石化松桃大路田坝加油站".equals(oilStationName)){
            return "https://www.91caihongwang.com/resourceOfOilStationMap/webapp/hire/zhong_guo_shi_hua_song_tao_da_lu_tian_ba_jia_you_zhan/index.html";
        }
        if(oilStationName == null || "".equals(oilStationName)){
            oilStationName = "中国石化松桃大路田坝加油站";
        }
        if(oilStationAddress == null || "".equals(oilStationAddress)){
            oilStationAddress = "贵州省铜仁市松桃苗族自治县304省道东50米";
        }
        String dirName = "";
        try {
            //1.根据加油站转义文件夹名称
            oilStationName = oilStationName.replace("/", "");
            char[] tempArr = oilStationName.toCharArray();
            for(int i = 0; i < tempArr.length; i++){
                char temp = tempArr[i];
                if(i == 0){
                    dirName = dirName + PingYingUtil.getPingYin(temp+"");
                } else {
                    dirName = dirName + "_" + PingYingUtil.getPingYin(temp+"");
                }
            }
            String sourcePath = oilStationMapHirePath + "zhong_guo_shi_hua_song_tao_da_lu_tian_ba_jia_you_zhan";
            String newPath = oilStationMapHirePath + dirName;
            //2.复制文件夹及其文件内容
            FileUtil.copyDirAndFile(sourcePath, newPath);
            String hireHtmlPath = newPath + "/index.html";
            File hireHtmlFile = new File(hireHtmlPath);
            if(!hireHtmlFile.exists() || hireHtmlFile.length() <= 0){
                logger.info("创建 加油站："+oilStationName+" 的招聘链接失败...");
                return "";
            }
            //3.替换文件中字符串
            FileUtil.replaceStrInFile(
                    newPath+"/index.html",
                    "贵州省铜仁市松桃苗族自治县304省道东50米-中国石化松桃大路田坝加油站",
                    oilStationAddress+"-"+oilStationName
            );
            FileUtil.replaceStrInFile(
                    newPath+"/index.html",
                    "中国石化松桃大路田坝加油站-招聘",
                    oilStationName+"-招聘"
            );
            //4.拼接url
            oilStationHireInfoUrl = baseUrl + dirName + "/index.html";
        } catch (Exception e) {
            logger.info("创建 加油站："+oilStationName+" 的招聘链接失败..., e : ");
            e.printStackTrace();
        }
        return oilStationHireInfoUrl;
    }
}









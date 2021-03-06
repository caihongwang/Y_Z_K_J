package com.oilStationMap.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.oilStationMap.MySuperTest;
import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.service.WX_SourceMaterialService;
import com.oilStationMap.utils.MapUtil;
import com.oilStationMap.utils.MediaArticlesUtil;
import com.oilStationMap.utils.MediaTypeUtil;
import com.oilStationMap.utils.WX_PublicNumberUtil;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by caihongwang on 2018/11/20.
 */
public class WX_SourceMaterialServiceImplTest extends MySuperTest {

    private static final Logger logger = LoggerFactory.getLogger(WX_SourceMaterialServiceImpl.class);

    @Autowired
    private WX_SourceMaterialService wxSourceMaterialService;

    @Test
    public void TEST(){

//        上传用于群发的视频素材
//        Map<String, Object> paramMap = Maps.newHashMap();
//        paramMap.put("description", "我一直在这里，看着时光流过.");
//        paramMap.put("title", "我在这里");
//        paramMap.put("mediaId", "21__bUIN1KaB5m5Pk9DEENAdSJzju0JOLAcMaI9ewe6BEUskhm6AIsSA1RaTWmoH");
//        uploadVideo(paramMap);

//        新增临时素材
//        Map<String, Object> paramMap = Maps.newHashMap();
//        paramMap.put("filePath", "/Users/caihongwang/Desktop/A_A.jpeg");
//        paramMap.put("mediaType", MediaTypeUtil.IMAGE);
//        uploadMedia(paramMap);

//        获取临时素材
//        Map<String, Object> paramMap = Maps.newHashMap();
//        paramMap.put("mediaId", "21__bUIN1KaB5m5Pk9DEENAdSJzju0JOLAcMaI9ewe6BEUskhm6AIsSA1RaTWmoH");
//        getMedia(paramMap);

//        新增其他类型永久素材
//        Map<String, Object> paramMap = Maps.newHashMap();
//        paramMap.put("filePath", "/Users/caihongwang/Desktop/油价地图/田坝加油站.jpeg");
//        paramMap.put("mediaType", MediaTypeUtil.IMAGE);
//        addMaterial(paramMap);


//        新增永久素材
//        Map<String, Object> paramMap = Maps.newHashMap();
//        List<MediaArticlesUtil> mediaArticlesUtilList = Lists.newArrayList();
//        MediaArticlesUtil mediaArticlesUtil = new MediaArticlesUtil();
//        mediaArticlesUtil.setTitle("B_B截屏-标题");
//        mediaArticlesUtil.setThumb_media_id("nmOaXf9mW-kNJNTS6gdUSt9g7G1IbwT8bAN8JVYyg-o");
//        mediaArticlesUtil.setShow_cover_pic(true);
//        mediaArticlesUtil.setContent("B_B截屏-内容");
//        mediaArticlesUtil.setContent_source_url("www.baidu.com");
//        mediaArticlesUtilList.add(mediaArticlesUtil);
//        paramMap.put("mediaArticlesUtilList", mediaArticlesUtilList);
//        addNews(paramMap);
//        返回{"media_id":"nmOaXf9mW-kNJNTS6gdUSoPZt_F6KZEY2pcTK-uaf60"}
        
//        获取永久素材
//        Map<String, Object> paramMap = Maps.newHashMap();
//        paramMap.put("mediaId", "nmOaXf9mW-kNJNTS6gdUSlV2LEmv_9H1BwQ50QfMi9E");
//        paramMap.put("filePath", "/Users/caihongwang/Desktop/B_B.jpeg");
//        getMaterial(paramMap);

//        删除永久素材
//        Map<String, Object> paramMap = Maps.newHashMap();
//        paramMap.put("mediaId", "nmOaXf9mW-kNJNTS6gdUSlV2LEmv_9H1BwQ50QfMi9E");
//        delMaterial(paramMap);

//        修改永久图文素材
//        Map<String, Object> paramMap = Maps.newHashMap();
//        MediaArticlesUtil mediaArticlesUtil = new MediaArticlesUtil();
//        mediaArticlesUtil.setTitle("B_B截屏-标题");
//        mediaArticlesUtil.setThumb_media_id("nmOaXf9mW-kNJNTS6gdUSoPZt_F6KZEY2pcTK-uaf60");
//        mediaArticlesUtil.setShow_cover_pic(true);
//        mediaArticlesUtil.setContent("B_B截屏-内容");
//        mediaArticlesUtil.setContent_source_url("www.baidu.com");
//        paramMap.put("mediaArticlesUtil", mediaArticlesUtil);
//        paramMap.put("mediaId", "nmOaXf9mW-kNJNTS6gdUSlfkuu2X7SAnk5qPmtDZoA4");
//        paramMap.put("index", "0");
//        updateNews(paramMap);

//        获取素材总数
//        Map<String, Object> paramMap = Maps.newHashMap();
//        getMaterialCount(paramMap);
//        返回{"voice_count":"0","video_count":"0","image_count":"139","news_count":"8"}

//        获取素材列表
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("offset", "0");
        paramMap.put("count", "20");
        paramMap.put("mediaType", MediaTypeUtil.IMAGE);
        paramMap.put("mediaType", "NEWS");
        wxSourceMaterialService.batchGetMaterial(paramMap);
    }

    /**
     * 新增临时素材
     * 图片（image）: 2M，支持PNG\JPEG\JPG\GIF格式
     * 语音（voice）：2M，播放长度不超过60s，支持AMR\MP3格式
     * 视频（video）：10MB，支持MP4格式
     * 缩略图（thumb）：64KB，支持JPG格式
     * @param paramMap
     * @return
     */
    public ResultMapDTO uploadMedia(Map<String, Object> paramMap) {
        logger.info("在service中新增临时素材-uploadMedia,请求-paramMap:" + JSONObject.toJSONString(paramMap));
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> resultMap = Maps.newHashMap();
        String filePath = paramMap.get("filePath")!=null?paramMap.get("filePath").toString():"";
        Object mediaTypeObj = paramMap.get("mediaType")!=null?paramMap.get("mediaType"):null;
        if(!"".equals(filePath) && mediaTypeObj != null){
            MediaTypeUtil mediaType = (MediaTypeUtil)mediaTypeObj;
            File file = new File(filePath);
            resultMap = WX_PublicNumberUtil.uploadMedia(mediaType, file);
            if (resultMap != null && resultMap.size() > 0) {
                resultMapDTO.setResultMap(MapUtil.getStringMap(resultMap));
                resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
            } else {
                resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
            }
        } else {
            resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在service中新增临时素材-uploadMedia,响应-response:" + JSONObject.toJSONString(resultMapDTO));
        return resultMapDTO;
    }

    /**
     * 获取临时素材
     * 注意: 视频文件不支持https下载，调用该接口需http协议。
     * @param paramMap
     * @return
     */
    public ResultMapDTO getMedia(Map<String, Object> paramMap) {
        logger.info("在service中获取临时素材-getMedia,请求-paramMap:" + JSONObject.toJSONString(paramMap));
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> resultMap = Maps.newHashMap();
        String mediaId = paramMap.get("mediaId")!=null?paramMap.get("mediaId").toString():"";
        if(!"".equals(mediaId)){
            resultMap = WX_PublicNumberUtil.getMedia(mediaId);
            if (resultMap != null && resultMap.size() > 0) {
                resultMapDTO.setResultMap(MapUtil.getStringMap(resultMap));
                resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
            } else {
                resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
            }
        } else {
            resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在service中获取临时素材-getMedia,响应-response:" + JSONObject.toJSONString(resultMapDTO));
        return resultMapDTO;
    }

    /**
     * 新增其他类型永久素材
     * @param paramMap
     * @return
     */
    public ResultMapDTO addMaterial(Map<String, Object> paramMap) {
        logger.info("在service中新增其他类型永久素材-addMaterial,请求-paramMap:" + JSONObject.toJSONString(paramMap));
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> resultMap = Maps.newHashMap();
        String filePath = paramMap.get("filePath")!=null?paramMap.get("filePath").toString():"";
        Object mediaTypeObj = paramMap.get("mediaType")!=null?paramMap.get("mediaType"):null;
        if(!"".equals(filePath) && mediaTypeObj != null){
            MediaTypeUtil mediaType = (MediaTypeUtil)mediaTypeObj;
            File file = new File(filePath);
            resultMap = WX_PublicNumberUtil.addMaterial(file, mediaType);
            if (resultMap != null && resultMap.size() > 0) {
                resultMapDTO.setResultMap(MapUtil.getStringMap(resultMap));
                resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
            } else {
                resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
            }
        } else {
            resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在service中新增其他类型永久素材-addMaterial,响应-response:" + JSONObject.toJSONString(resultMapDTO));
        return resultMapDTO;
    }

    /**
     * 新增永久素材
     * 图片（image）: 2M，支持PNG\JPEG\JPG\GIF格式
     * 语音（voice）：2M，播放长度不超过60s，支持AMR\MP3格式
     * 视频（video）：10MB，支持MP4格式
     * 缩略图（thumb）：64KB，支持JPG格式
     * @param paramMap
     * @return
     */
    public ResultMapDTO addNews(Map<String, Object> paramMap) {
        logger.info("在service中新增永久素材-addNews,请求-paramMap:" + JSONObject.toJSONString(paramMap));
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> resultMap = Maps.newHashMap();
        Object mediaArticlesUtilListObj = paramMap.get("mediaArticlesUtilList")!=null?paramMap.get("mediaArticlesUtilList"):null;
        if(mediaArticlesUtilListObj != null){
            List<MediaArticlesUtil> mediaArticlesUtilList = (List<MediaArticlesUtil>)mediaArticlesUtilListObj;
            resultMap = WX_PublicNumberUtil.addNews(mediaArticlesUtilList);
            if (resultMap != null && resultMap.size() > 0) {
                resultMapDTO.setResultMap(MapUtil.getStringMap(resultMap));
                resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
            } else {
                resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
            }
        } else {
            resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在service中新增永久素材-addNews,响应-response:" + JSONObject.toJSONString(resultMapDTO));
        return resultMapDTO;
    }

    /**
     * 获取永久素材
     * 根据mediaId下载素材，并保存在filePath路径之下
     * @param paramMap
     * @return
     */
    public ResultMapDTO getMaterial(Map<String, Object> paramMap) {
        logger.info("在service中获取永久素材-getMaterial,请求-paramMap:" + JSONObject.toJSONString(paramMap));
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> resultMap = Maps.newHashMap();
        String mediaId = paramMap.get("mediaId")!=null?paramMap.get("mediaId").toString():"";
        String filePath = paramMap.get("filePath")!=null?paramMap.get("filePath").toString():"";
        if(!"".equals(mediaId) && !"".equals(filePath)){
            resultMap = WX_PublicNumberUtil.getMaterial(mediaId, filePath);
            if (resultMap != null && resultMap.size() > 0) {
                resultMapDTO.setResultMap(MapUtil.getStringMap(resultMap));
                resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
            } else {
                resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
            }
        } else {
            resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在service中获取永久素材-getMaterial,响应-response:" + JSONObject.toJSONString(resultMapDTO));
        return resultMapDTO;
    }

    /**
     * 删除永久素材
     * @param paramMap
     * @return
     */
    public ResultMapDTO delMaterial(Map<String, Object> paramMap) {
        logger.info("在service中删除永久素材-delMaterial,请求-paramMap:" + JSONObject.toJSONString(paramMap));
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> resultMap = Maps.newHashMap();
        String mediaId = paramMap.get("mediaId")!=null?paramMap.get("mediaId").toString():"";
        if(!"".equals(mediaId)){
            resultMap = WX_PublicNumberUtil.delMaterial(mediaId);
            if (resultMap != null && resultMap.size() > 0) {
                resultMapDTO.setResultMap(MapUtil.getStringMap(resultMap));
                resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
            } else {
                resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
            }
        } else {
            resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在service中删除永久素材-delMaterial,响应-response:" + JSONObject.toJSONString(resultMapDTO));
        return resultMapDTO;
    }


    /**
     * 修改永久图文素材
     * @param paramMap
     * @return
     */
    public ResultMapDTO updateNews(Map<String, Object> paramMap) {
        logger.info("在service中修改永久图文素材-updateNews,请求-paramMap:" + JSONObject.toJSONString(paramMap));
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> resultMap = Maps.newHashMap();
        String mediaId = paramMap.get("mediaId")!=null?paramMap.get("mediaId").toString():"";
        String index = paramMap.get("index")!=null?paramMap.get("index").toString():"0";
        Object mediaArticlesUtilObj = paramMap.get("mediaArticlesUtil")!=null?paramMap.get("mediaArticlesUtil"):null;
        if(!"".equals(mediaId) && !"".equals(mediaId) && mediaArticlesUtilObj != null){
            MediaArticlesUtil mediaArticlesUtil = (MediaArticlesUtil)mediaArticlesUtilObj;
            resultMap = WX_PublicNumberUtil.updateNews(mediaId, Integer.parseInt(index), mediaArticlesUtil);
            if (resultMap != null && resultMap.size() > 0) {
                resultMapDTO.setResultMap(MapUtil.getStringMap(resultMap));
                resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
            } else {
                resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
            }
        } else {
            resultMapDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultMapDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在service中修改永久图文素材-updateNews,响应-response:" + JSONObject.toJSONString(resultMapDTO));
        return resultMapDTO;
    }

    /**
     * 获取素材总数
     * @param paramMap
     * @return
     */
    public ResultMapDTO getMaterialCount(Map<String, Object> paramMap) {
        logger.info("在service中获取素材总数-getMaterialCount,请求-paramMap:" + JSONObject.toJSONString(paramMap));
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap = WX_PublicNumberUtil.getMaterialCount();
        if (resultMap != null && resultMap.size() > 0) {
            resultMapDTO.setResultMap(MapUtil.getStringMap(resultMap));
            resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
            resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        } else {
            resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在service中获取素材总数-getMaterialCount,响应-response:" + JSONObject.toJSONString(resultMapDTO));
        return resultMapDTO;
    }


}

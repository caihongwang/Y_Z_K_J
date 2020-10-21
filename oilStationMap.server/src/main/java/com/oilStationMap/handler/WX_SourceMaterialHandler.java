package com.oilStationMap.handler;

import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.service.WX_CommonService;
import com.oilStationMap.service.WX_SourceMaterialService;
import com.oilStationMap.utils.MapUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 红包Handler
 */
@Component
public class WX_SourceMaterialHandler {

    private static final Logger logger = LoggerFactory.getLogger(WX_SourceMaterialHandler.class);

    @Autowired
    private WX_CommonService wxCommonService;

    @Autowired
    private WX_SourceMaterialService wxSourceMaterialService;

    /**
     * 获取红包二维码
     * @param paramMap
     * @return
     */
    public ResultMapDTO batchGetMaterial(Map<String, String> paramMap) {
        logger.info("在hanlder中获取素材列表-batchGetMaterial,请求-paramMap:" + paramMap);
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        try {
            resultMapDTO = wxSourceMaterialService.batchGetMaterial(objectParamMap);
        } catch (Exception e) {
            resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
            logger.error("在hanlder中获取素材列表-batchGetMaterial is error, paramMap : " + paramMap + ", e : " + e);
        }
        logger.info("在hanlder中获取素材列表-batchGetMaterial,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

}

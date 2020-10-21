package com.newMall.handler;

import com.newMall.code.NewMallCode;
import com.newMall.dto.ResultMapDTO;
import com.newMall.service.CommonService;
import com.newMall.service.WX_SourceMaterialService;
import com.newMall.utils.MapUtil;
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

    private static final Logger logger = LoggerFactory.getLogger(com.newMall.handler.WX_SourceMaterialHandler.class);

    @Autowired
    private CommonService commonService;

    @Autowired
    private WX_SourceMaterialService wx_SourceMaterialService;

    /**
     * 获取红包二维码
     *
     * @param tid
     * @param paramMap
     * @return
     * @throws
     */
    public ResultMapDTO batchGetMaterial(int tid, Map<String, String> paramMap) {
        logger.info("在hanlder中获取素材列表-batchGetMaterial,请求-paramMap:" + paramMap);
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> objectParamMap = MapUtil.getObjectMap(paramMap);
        try {
            resultMapDTO = wx_SourceMaterialService.batchGetMaterial(objectParamMap);
        } catch (Exception e) {
            resultMapDTO.setCode(NewMallCode.SERVER_INNER_ERROR.getNo());
            resultMapDTO.setMessage(NewMallCode.SERVER_INNER_ERROR.getMessage());
            logger.error("在hanlder中获取素材列表-batchGetMaterial is error, paramMap : " + paramMap + ", e : " + e);
        }
        logger.info("在hanlder中获取素材列表-batchGetMaterial,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }

}

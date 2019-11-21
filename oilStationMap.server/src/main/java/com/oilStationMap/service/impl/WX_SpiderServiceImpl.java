package com.oilStationMap.service.impl;

import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.service.*;
import com.oilStationMap.utils.SpiderFor58Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.Map;

/**
 * 爬虫service
 */
@Service
public class WX_SpiderServiceImpl implements WX_SpiderService {

    private static final Logger logger = LoggerFactory.getLogger(WX_SpiderServiceImpl.class);

    /**
     * 从网络：５８同城、美团等网络进行爬取房产人员、美食店铺等联系方式
     * @param paramMap
     * @return
     */
    @Override
    public ResultMapDTO getContactFromWeb(Map<String, Object> paramMap) {
        SpiderFor58Util.getContactFromWeb();
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
        resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        logger.info("在service中从网络：５８同城、美团等网络进行爬取房产人员、美食店铺等联系方式-getContactFromWeb,结果-result:" + resultMapDTO);
        return resultMapDTO;
    }
}

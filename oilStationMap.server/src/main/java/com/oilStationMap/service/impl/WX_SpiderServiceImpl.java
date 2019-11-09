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
     * 从58二手房爬取手机号并整合入库
     * @param paramMap
     * @return
     */
    @Override
    public ResultMapDTO getContactFrom58ErShouFang(Map<String, Object> paramMap) {
        SpiderFor58Util.getContactFrom58ErShouFang();
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
        resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        logger.info("在service中爬取58二手房爬取手机号并整合入库-getContactFrom58ErShouFang,结果-result:" + resultMapDTO);
        return resultMapDTO;
    }
}

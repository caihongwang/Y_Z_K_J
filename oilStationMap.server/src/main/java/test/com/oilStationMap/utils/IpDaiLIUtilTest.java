package com.oilStationMap.utils;

import com.oilStationMap.MySuperTest;
import com.oilStationMap.dao.WX_OilStationDao;
import com.oilStationMap.service.WX_OilStationService;
import com.oilStationMap.utils.IpDaiLiUtil;
import com.oilStationMap.utils.LonLatUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Map;

/**
 * Created by caihongwang on 2018/10/12.
 */
public class IpDaiLIUtilTest extends MySuperTest {

    @Test
    public void Test() {
        List<Map<String, String>> ipList = IpDaiLiUtil.getDaiLiIpList();
        for(Map ipMap : ipList){
            System.out.println("ip ---->>>>" + ipMap.toString());
        }
    }


}

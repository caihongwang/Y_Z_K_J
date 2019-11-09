package com.oilStationMap.utils;

import com.google.common.collect.Maps;
import com.newMall.utils.SpriderForFenXiangShengHuoUtil;
import com.oilStationMap.MySuperTest;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class SpriderForFenXiangShengHuoUtilTest  extends MySuperTest{

    @Test
    public void Test(){
        Map<String, Object> paramMap = Maps.newHashMap();
        SpriderForFenXiangShengHuoUtil.getFenXiangShengHuoProduct(paramMap);
    }

}
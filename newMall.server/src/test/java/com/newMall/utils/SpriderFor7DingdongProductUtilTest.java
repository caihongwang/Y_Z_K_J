package com.newMall.utils;

import com.newMall.NewMallSuperTest;
import com.google.common.collect.Maps;
import org.junit.Test;
import java.util.Map;

/**
 * Created by caihongwang on 2019/5/23.
 */
public class SpriderFor7DingdongProductUtilTest extends NewMallSuperTest {

    @Test
    public void TEST(){
        Map<String, Object> paramMap = Maps.newHashMap();
        //获取所有企叮咚的商品
        SpriderFor7DingdongProductUtil.get7DingdongProduct(paramMap);
    }
}
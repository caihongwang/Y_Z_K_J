package com.newMall.utils;

import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by caihongwang on 2018/1/24.
 */
public class NumberUtil {

    /**
     * 获取小数点后两位的double数字
     * @param doubleNumber
     * @return
     */
    public static Double getPointTowNumber(Double doubleNumber) {
        BigDecimal bg = new BigDecimal(doubleNumber);
        doubleNumber = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return doubleNumber;
    }

    /**
     * java从字符串中提取数字
     * str:传递过来的字符串
     */
    public static String getNUm(String str){
        str = str.trim();
        String numStr = "";
        for(String temp : str.replaceAll("[^0-9]", ",").split(",")){
            if(temp.length()>0){
                numStr = numStr + temp;
            }
        }
        return numStr;
    }

}

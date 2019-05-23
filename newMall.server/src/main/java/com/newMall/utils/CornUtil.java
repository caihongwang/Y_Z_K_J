package com.newMall.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * corn表达式工具
 * caihongwang
 */
public class CornUtil {

    /**
     * 将时间按照正则进行格式化
     * @param date
     * @param dateFormat
     * @return
     */
    public static String formatDateByPattern(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    /**
     * 将时间格式化为corn表达式
     * @param date
     * @return
     */
    public static String getCron(Date date) {
        String dateFormat = "ss mm HH dd MM ? yyyy";
        return formatDateByPattern(date, dateFormat);
    }
}

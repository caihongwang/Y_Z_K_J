package com.automation.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    /**
     * @param nowTime   当前时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     * @author sunran   判断当前时间在时间区间类
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * @param nowTime   当前时间
     * @param startTime 开始时间
     * @return
     * @author sunran   判断当前时间在开始时间之前
     */
    public static boolean isBeforeDate(Date nowTime, Date startTime) {
        if (nowTime.getTime() == startTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        if (date.before(begin)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 根据 startDate 获取指定前几个小时或者后几个小时的时间
     *
     * @param startDateStr
     * @param hours 正数则获取过去几个小时的时间，负数则获取未来几个小时的时间
     * @return
     */
    public static String getPostponeTimesOradvanceTimes(String startDateStr, int hours) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");//设置日期格式
        Date startDate = sdf.parse(startDateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - hours);
        String endTime = sdf.format(calendar.getTime());
        return endTime;
    }

}

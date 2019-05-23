package com.oilStationMap.service;

import java.text.DateFormat;
import java.util.Date;

/**
 * caihongwang
 */
public abstract class BaseConvert {
    public String getDateString(Date date) {
        return getDateFormat().format(date);
    }

    public abstract DateFormat getDateFormat();
}

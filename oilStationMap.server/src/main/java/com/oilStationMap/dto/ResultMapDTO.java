package com.oilStationMap.dto;/**
 * Created by caihongwang on 2019/5/14.
 */

import java.util.Map;

/**
 * @ClassName ResultMapDTO
 * @Description TODO
 * @Author caihongwang
 * @Date 2019/5/14 9:00 PM
 * @Version 1.0.0
 **/
public class ResultMapDTO {

    public int code; // required
    public String message; // required
    public int resultListTotal; // required
    public Map<String,String> resultMap; // required

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResultListTotal() {
        return resultListTotal;
    }

    public void setResultListTotal(int resultListTotal) {
        this.resultListTotal = resultListTotal;
    }

    public Map<String, String> getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map<String, String> resultMap) {
        this.resultMap = resultMap;
    }
}

package com.automation.dto;/**
 * Created by caihongwang on 2019/5/14.
 */

import java.util.List;
import java.util.Map;

/**
 * @ClassName ResultDTO
 * @Description TODO
 * @Author caihongwang
 * @Date 2019/5/14 8:53 PM
 * @Version 1.0.0
 **/
public class ResultDTO {
    public int code; // required
    public String message; // required
    public int resultListTotal; // required
    public List<Map<String,String>> resultList; // required
    public String allRedPacketMoneyTotal; // required

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

    public List<Map<String, String>> getResultList() {
        return resultList;
    }

    public void setResultList(List<Map<String, String>> resultList) {
        this.resultList = resultList;
    }

    public String getAllRedPacketMoneyTotal() {
        return allRedPacketMoneyTotal;
    }

    public void setAllRedPacketMoneyTotal(String allRedPacketMoneyTotal) {
        this.allRedPacketMoneyTotal = allRedPacketMoneyTotal;
    }
}

package com.oilStationMap.dto;

/**
 * @ClassName MessageDTO
 * @Description TODO
 * @Author caihongwang
 * @Date 2019/5/15 2:26 PM
 * @Version 1.0.0
 **/
public class MessageDTO {

    public int code; // required
    public String message; // required

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
}

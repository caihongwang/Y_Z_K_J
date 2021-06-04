package com.automation.dto;/**
 * Created by caihongwang on 2019/5/14.
 */

/**
 * @ClassName BoolDTO
 * @Description TODO
 * @Author caihongwang
 * @Date 2019/5/14 8:55 PM
 * @Version 1.0.0
 **/
public class BoolDTO {

    public int code; // required
    public String message; // required
    public boolean value; // required

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

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}

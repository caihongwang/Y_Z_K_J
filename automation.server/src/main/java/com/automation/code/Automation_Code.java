package com.automation.code;


/**
 * 给前端的状态码
 *
 * @author caihongwang
 */
public class Automation_Code {

    private int no;
    private String message;

    public Automation_Code() {

    }

    public Automation_Code(int no, String message) {
        this.no = no;
        this.message = message;
    }

    public int getNo() {
        return no;
    }

    public String getMessage() {
        return message;
    }

    //system
    public static Automation_Code SUCCESS = new Automation_Code(0, "成功");
    public static Automation_Code SERVER_INNER_ERROR = new Automation_Code(10001, "服务异常,请稍后重试.");
    public static Automation_Code NO_DATA_CHANGE = new Automation_Code(10009, "没有数据发生更改.");

    //字典
    public static Automation_Code DIC_EXIST = new Automation_Code(30001, "字典已经存在，请修改。");
    public static Automation_Code DIC_TYPE_OR_CODE_OR_NAME_IS_NOT_NULL = new Automation_Code(30002, "字典的类型或者编码或者名称不能为空");
    public static Automation_Code DIC_ID_OR_CODE_IS_NOT_NULL = new Automation_Code(30003, "字典的ID或者编码不能为空");
    public static Automation_Code DIC_LIST_IS_NULL = new Automation_Code(0, "当前字典没有数据.");

    //公共模板
    public static Automation_Code PARAM_IS_NULL = new Automation_Code(60001, "必填参数不允许为空.");
}
package com.darkason.common.enums;

/**
 * @auth admin
 * @date
 * @Description
 */
public enum StatusEnum {

    SUCCESS(200, "成功"),

    FAIL(-1, "失败"),

    EXCEPTION(500, "未知异常，请联系管理员"),

    PARAM_EXCEPTION(300, "参数异常"),

    LOGIN_EXCEPTION(501, "登录失败"),
    ;

    private Integer code;
    private String msg;

    StatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }}

package com.darkason.common.response;

import com.darkason.common.enums.StatusEnum;

/**
 * @auth admin
 * @date
 * @Description 后端统一响应结果集
 */
public class Result<T> {

    // 响应状态码
    private Integer code;

    private String msg;

    // 响应数据
    private T data;

    public Result() {
    }

    public Result(StatusEnum statusEnum) {
        this.code = statusEnum.getCode();
        this.msg = statusEnum.getMsg();
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(StatusEnum statusEnum, T data) {
        this.code = statusEnum.getCode();
        this.msg = statusEnum.getMsg();
        this.data = data;
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
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
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

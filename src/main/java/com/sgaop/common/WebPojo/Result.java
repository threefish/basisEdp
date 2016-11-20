package com.sgaop.common.WebPojo;

/**
 * 作者:黄川
 * 邮件:306955302@qq.com
 * 时间：2016-11-20 14:17
 */
public class Result<T> {

    public static <T> Result sucess(T data, String msg) {
        return new Result(true, data, msg);
    }

    public static <T> Result sucess(T data) {
        return new Result(true, data, null);
    }

    public static Result error(String msg) {
        return new Result(false, null, msg);
    }

    private boolean ok = false;

    private T data;

    private String msg;

    public Result(boolean ok, T data, String msg) {
        this.ok = ok;
        this.data = data;
        this.msg = msg;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

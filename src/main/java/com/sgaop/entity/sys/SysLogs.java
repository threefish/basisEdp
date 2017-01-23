package com.sgaop.entity.sys;

import com.sgaop.basis.annotation.Colum;
import com.sgaop.basis.annotation.ID;
import com.sgaop.basis.annotation.Table;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2017/1/23 0023
 * To change this template use File | Settings | File Templates.
 */
@Table("sys_logs")
public class SysLogs {

    /**
     * 主键自增
     */
    @ID
    private int id;

    /**
     * 模块
     */
    @Colum
    private String module;
    /**
     * 标签
     */
    @Colum
    private String tag;

    @Colum
    private String src;

    @Colum
    private String method;

    /**
     * 状态 /暂定 1 为成功  0 和-1为失败  9为异常 其他为2345678保留
     */
    @Colum
    private int status;

    /**
     * 用户
     */
    @Colum
    private String account;

    @Colum
    private String ip;
    /**
     * 访问时间
     */
    @Colum("access_time")
    private Date accessTime;

    /**
     * 返回内容
     */
    @Colum("msg")
    private String msg;

    /**
     * 返回内容
     */
    @Colum("result_json")
    private String resultJson;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(Date accessTime) {
        this.accessTime = accessTime;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResultJson() {
        return resultJson;
    }

    public void setResultJson(String resultJson) {
        this.resultJson = resultJson;
    }
}

package com.sgaop.entity.sys;

import com.sgaop.basis.annotation.Colum;
import com.sgaop.basis.annotation.Table;

import java.io.Serializable;
import java.util.Date;

@Table("sys_apm_alarm")
public class APMAlarm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Colum("alarmType")
    private String type;

    @Colum("alarmTime")
    private Date alarmTime = new Date();

    @Colum("msg")
    private String msg;

    @Colum("ip")
    private String ip;

    @Colum("title")
    private String title;

    @Colum("device")
    private String device;

    @Colum("alarmUsage")
    private double usage;

    @Colum("point")
    private double alarm;

    public double getAlarm() {
        return alarm;
    }

    public Date getAlarmTime() {
        return alarmTime;
    }

    public String getDevice() {
        return device;
    }

    public String getIp() {
        return ip;
    }

    public String getMsg() {
        return msg;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public double getUsage() {
        return usage;
    }

    public void setAlarm(double alarm) {
        this.alarm = alarm;
    }

    public void setAlarmTime(Date alarmTime) {
        this.alarmTime = alarmTime;
    }


    public void setDevice(String device) {
        this.device = device;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUsage(double usage) {
        this.usage = usage;
    }

}
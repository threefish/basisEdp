package com.sgaop.entity.sys;

import com.sgaop.basis.annotation.*;

import java.util.Date;

@Table("apm_alarm")
public class APMAlarm {

	public static enum Type {
        SLOW, MEM, DISK, CPU, NETWORK;
	}

	@Colum("code")
	private String code = "";

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
	private int alarm;

	public int getAlarm() {
		return alarm;
	}

	public Date getAlarmTime() {
		return alarmTime;
	}

	public String getCode() {
		return code;
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

	public void setAlarm(int alarm) {
		this.alarm = alarm;
	}

	public void setAlarmTime(Date alarmTime) {
		this.alarmTime = alarmTime;
	}

	public void setCode(String code) {
		this.code = code;
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

	public void setType(Type type) {
		this.type = type.toString();
	}

	public void setUsage(double usage) {
		this.usage = usage;
	}

}
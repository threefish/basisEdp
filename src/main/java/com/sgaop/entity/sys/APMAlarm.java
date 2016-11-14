package com.sgaop.entity.sys;

import com.sgaop.basis.annotation.*;

import java.util.Date;

@Table("apm_alarm")
//@Comment("性能告警表")
public class APMAlarm {

	public static enum Type {
        SLOW, MEM, DISK, CPU, NETWORK;
	}

	@Colum("alarm_code")
//	@Comment("报警信息编号")
	private String code = "";

	@Colum("alarm_type")
//	@Comment("报警类型")
	private Type type;

	@Colum("alarm_time")
//	@Comment("报警时间")
	private Date alarmTime = new Date();

	@Colum("alarm_msg")
//	@Comment("报警消息")
	private String msg;

	@Colum("alarm_ip")
//	@Comment("报警 ip")
	private String ip;

	@Colum("alarm_title")
//	@Comment("报警标题")
	private String title;

	@Colum("alarm_device")
//	@Comment("报警设备")
	private String device;

	@Colum("alarm_usage")
//	@Comment(" 设备使用情况")
	private double usage;

	@Colum("alarm_alarm_point")
//	@Comment("设备告警点")
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

	public Type getType() {
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
		this.type = type;
	}

	public void setUsage(double usage) {
		this.usage = usage;
	}

}
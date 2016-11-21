package com.sgaop.entity.sys;

import com.sgaop.basis.annotation.Colum;
import com.sgaop.basis.annotation.Table;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/11/21 0021
 * To change this template use File | Settings | File Templates.
 */
@Table("sys_alarm_option")
public class AlarmOption {

    @Colum("alarmType")
    private String alarmType;

    @Colum("percent")
    private double percent;

    @Colum("listenerTypes")
    private String listenerTypes;

    @Colum("listeners")
    private String listeners;

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public String getListenerTypes() {
        return listenerTypes;
    }

    public void setListenerTypes(String listenerTypes) {
        this.listenerTypes = listenerTypes;
    }

    public String getListeners() {
        return listeners;
    }

    public void setListeners(String listeners) {
        this.listeners = listeners;
    }
}

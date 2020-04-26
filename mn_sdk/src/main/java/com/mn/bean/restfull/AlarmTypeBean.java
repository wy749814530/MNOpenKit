package com.mn.bean.restfull;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/11/2 0002.
 */

public class AlarmTypeBean implements Serializable {

    /**
     * alarmType : 3
     * subAlarmType : 4
     */

    private int alarmType;
    private String subAlarmType;

    public int getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(int alarmType) {
        this.alarmType = alarmType;
    }

    public String getSubAlarmType() {
        return subAlarmType;
    }

    public void setSubAlarmType(String subAlarmType) {
        this.subAlarmType = subAlarmType;
    }
}

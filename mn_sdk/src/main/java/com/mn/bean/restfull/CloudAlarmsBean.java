package com.mn.bean.restfull;

import java.util.List;

/**
 * Created by Administrator on 2019/1/17 0017.
 */

public class CloudAlarmsBean extends BaseBean {

    private static final long serialVersionUID = -3732691486413905591L;


    private List<AlarmsBean> alarms;

    public List<AlarmsBean> getAlarms() {
        return alarms;
    }

    public void setAlarms(List<AlarmsBean> alarms) {
        this.alarms = alarms;
    }
}

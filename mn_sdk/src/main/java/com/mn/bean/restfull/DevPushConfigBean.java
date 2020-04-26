package com.mn.bean.restfull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WIN on 2018/5/8.
 */

public class DevPushConfigBean extends BaseBean {

    private static final long serialVersionUID = -5306166009618487110L;
    /**
     * pushconfig : {"pushenable":1,"sleepenable":1,"level":1,"sleep_time_range": [{"bsleeptime": "00:00:00","esleeptime": "23:59:59"}],"face_push":1,"stranger_push":0,"push_list":[{"alarmType":3,"subAlarmType":[3]}]}
     */

    private PushconfigBean pushconfig;

    public PushconfigBean getPushconfig() {
        return pushconfig;
    }

    public void setPushconfig(PushconfigBean pushconfig) {
        this.pushconfig = pushconfig;
    }
}

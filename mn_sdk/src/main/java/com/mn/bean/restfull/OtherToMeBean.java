package com.mn.bean.restfull;

import java.util.List;

/**
 * Created by hjz on 2017/11/9.
 */

public class OtherToMeBean extends BaseBean {
    private List<DevicesBean> devices;

    public List<DevicesBean> getDevices() {
        return devices;
    }


    public void setDevices(List<DevicesBean> devices) {
        this.devices = devices;
    }

}

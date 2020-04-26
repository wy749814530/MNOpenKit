package com.mn.bean.restfull;

import java.util.List;

/**
 * Created by hjz on 2018/12/17.
 */

public class MeToOtherBean extends BaseBean {
    private static final long serialVersionUID = 634188493473870207L;
    private List<DevicesBean> myDevices;

    public List<DevicesBean> getMyDevices() {
        return myDevices;
    }

    public void setMyDevices(List<DevicesBean> myDevices) {
        this.myDevices = myDevices;
    }
}

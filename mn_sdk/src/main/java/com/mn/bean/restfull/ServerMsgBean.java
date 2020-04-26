package com.mn.bean.restfull;

/**
 * Created by Administrator on 2019/7/19 0019.
 */

public class ServerMsgBean {

    /**
     * userId : 187346601650425860
     * deviceSn :
     * actionType : 7
     * data : {"authority":7}
     */

    private long userId;
    private String deviceSn;
    private int actionType;
    private DataBean data;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn;
    }

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * authority : 7
         */

        private int authority;

        public int getAuthority() {
            return authority;
        }

        public void setAuthority(int authority) {
            this.authority = authority;
        }
    }
}

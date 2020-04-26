package com.mn.bean.setting;

/**
 * Created by Administrator on 2019/3/27 0027.
 */

public class AlarmTimeRecordBean {


    /**
     * channel : 0
     * params : {"AllDayRecord":true}
     * result : true
     * code : 408
     * msg : Record Plan Unsupport
     */

    private int channel;
    private ParamsBean params;
    private boolean result;
    private int code;
    private String msg;

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class ParamsBean {
        /**
         * AllDayRecord : true
         */

        private boolean AllDayRecord;

        public boolean isAllDayRecord() {
            return AllDayRecord;
        }

        public void setAllDayRecord(boolean AllDayRecord) {
            this.AllDayRecord = AllDayRecord;
        }
    }
}

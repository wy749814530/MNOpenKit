package com.mn.bean.setting;

/**
 * Created by Administrator on 2019/3/25 0025.
 */

public class AlarmCloudRecordBean {

    /**
     * channel : 0
     * result : true
     * params : {"MNAlarmCloudRecord":true,"RecordTime":10}
     *
     */

    private int channel;
    private boolean result;
    private ParamsBean params;

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public static class ParamsBean {
        /**
         * MNAlarmCloudRecord : true
         * RecordTime : 10
         */

        private boolean MNAlarmCloudRecord;
        private int RecordTime;

        public boolean isMNAlarmCloudRecord() {
            return MNAlarmCloudRecord;
        }

        public void setMNAlarmCloudRecord(boolean MNAlarmCloudRecord) {
            this.MNAlarmCloudRecord = MNAlarmCloudRecord;
        }

        public int getRecordTime() {
            return RecordTime;
        }

        public void setRecordTime(int RecordTime) {
            this.RecordTime = RecordTime;
        }
    }
}

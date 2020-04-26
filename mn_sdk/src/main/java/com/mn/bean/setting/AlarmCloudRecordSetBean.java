package com.mn.bean.setting;

/**
 * Created by Administrator on 2019/3/27 0027.
 */

public class AlarmCloudRecordSetBean {

    /**
     * channel : 0
     * MNAlarmCloudRecord : true
     * RecordTime : 10
     */

    private int channel;
    private boolean MNAlarmCloudRecord;
    private int RecordTime;

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

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

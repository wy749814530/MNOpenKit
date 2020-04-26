package com.mn.bean.setting;

import java.util.List;

/**
 * Created by Administrator on 2019/3/27 0027.
 */

public class AlarmRecordBase {
    /**
     * AlarmRecord : false
     * TimingRecord : false
     * VideoDetectRecord : true
     * TimeSection : ["1 01:00:00-21:59:59","0 00:00:00-00:00:00","1 00:00:00-22:59:59","0 00:00:00-00:00:00","1 00:00:00-22:59:59","0 00:00:00-00:00:00","1 00:00:00-22:59:59"]
     */

    private boolean AlarmRecord;
    private boolean TimingRecord;
    private boolean VideoDetectRecord;
    private List<String> TimeSection;

    public boolean isAlarmRecord() {
        return AlarmRecord;
    }

    public void setAlarmRecord(boolean AlarmRecord) {
        this.AlarmRecord = AlarmRecord;
    }

    public boolean isTimingRecord() {
        return TimingRecord;
    }

    public void setTimingRecord(boolean TimingRecord) {
        this.TimingRecord = TimingRecord;
    }

    public boolean isVideoDetectRecord() {
        return VideoDetectRecord;
    }

    public void setVideoDetectRecord(boolean VideoDetectRecord) {
        this.VideoDetectRecord = VideoDetectRecord;
    }

    public List<String> getTimeSection() {
        return TimeSection;
    }

    public void setTimeSection(List<String> TimeSection) {
        this.TimeSection = TimeSection;
    }
}

package com.mn.bean.setting;

/**
 * Created by Administrator on 2019/3/26 0026.
 */

public class SetAlarmRecordBean {

    /**
     * channel : 0
     * AllDayRecord : true
     */

    private int channel;
    private boolean AllDayRecord;

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public boolean isAllDayRecord() {
        return AllDayRecord;
    }

    public void setAllDayRecord(boolean AllDayRecord) {
        this.AllDayRecord = AllDayRecord;
    }
}

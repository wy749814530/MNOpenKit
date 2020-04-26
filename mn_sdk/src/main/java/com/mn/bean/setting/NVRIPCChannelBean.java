package com.mn.bean.setting;

/**
 * Created by Administrator on 2019/4/1 0001.
 */

public class NVRIPCChannelBean {

    /**
     * channel : 0
     * Name : ctt name1
     * IsSynToFront : true
     */

    private int channel;
    private String Name;
    private boolean IsSynToFront;

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public boolean isIsSynToFront() {
        return IsSynToFront;
    }

    public void setIsSynToFront(boolean IsSynToFront) {
        this.IsSynToFront = IsSynToFront;
    }
}

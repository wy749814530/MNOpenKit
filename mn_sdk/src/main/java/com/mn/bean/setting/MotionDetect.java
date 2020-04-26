package com.mn.bean.setting;

/**
 * Created by Administrator on 2019/4/1 0001.
 */

public class MotionDetect {
    /**
     * MotionDetect : true
     * Sensitivity : 5
     * channel : 0
     */

    private boolean MotionDetect;
    private int Sensitivity;
    private int channel;

    public boolean isMotionDetect() {
        return MotionDetect;
    }

    public void setMotionDetect(boolean MotionDetect) {
        this.MotionDetect = MotionDetect;
    }

    public int getSensitivity() {
        return Sensitivity;
    }

    public void setSensitivity(int Sensitivity) {
        this.Sensitivity = Sensitivity;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }
}

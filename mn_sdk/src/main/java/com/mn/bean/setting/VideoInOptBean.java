package com.mn.bean.setting;

/**
 * Created by Administrator on 2019/3/26 0026.
 */

public class VideoInOptBean {

    /**
     * channel : 0
     * Mirror : false
     * Flip : false
     * DayNightColor : 1
     */

    private int channel;
    private boolean Mirror;
    private boolean Flip;
    private int DayNightColor;

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public boolean isMirror() {
        return Mirror;
    }

    public void setMirror(boolean Mirror) {
        this.Mirror = Mirror;
    }

    public boolean isFlip() {
        return Flip;
    }

    public void setFlip(boolean Flip) {
        this.Flip = Flip;
    }

    public int getDayNightColor() {
        return DayNightColor;
    }

    public void setDayNightColor(int DayNightColor) {
        this.DayNightColor = DayNightColor;
    }
}

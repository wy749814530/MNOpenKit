package com.mn.bean.setting;

/**
 * Created by Administrator on 2019/4/1 0001.
 */

public class FaceBean {
    /**
     * FaceDetection : false
     * channel : 0
     */

    private boolean FaceDetection;

    private int channel;

    public boolean isFaceDetection() {
        return FaceDetection;
    }

    public void setFaceDetection(boolean FaceDetection) {
        this.FaceDetection = FaceDetection;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }
}

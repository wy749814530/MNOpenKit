package com.mn.bean.restfull;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/6/5 0005.
 */

public class Record24Bean implements Serializable {
    private static final long serialVersionUID = -47560778970302005L;
    /**
     * id : 5cf762b54d048a027fc114c3
     * deviceId : 192774745500946700
     * channelNo : 0
     * startTime : 2019-06-05 14:33:54
     * endTime : 2019-06-05 14:35:32
     * videoUrl : http://s3.cn-north-1.amazonaws.com.cn/bullyun-test-stage/007/4248422550/1559716434920.mp4
     */

    private String id;
    private String deviceId;
    private int channelNo;
    private String startTime;
    private String endTime;
    private String videoUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(int channelNo) {
        this.channelNo = channelNo;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}

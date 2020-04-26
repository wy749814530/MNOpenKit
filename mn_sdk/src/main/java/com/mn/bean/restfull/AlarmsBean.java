package com.mn.bean.restfull;

import java.io.Serializable;

/**
 * Created by WIN on 2017/11/13.
 */

public class AlarmsBean implements Serializable {
    private static final long serialVersionUID = -4759871073812002605L;
    /**
     * alarmId : 5a0947e04d048a7cba3b6473
     * deviceSn : MDAhAQEAbDAwMDA4OTM2MjAzMQAA
     * channelNo : 0
     * alarmTime : 1510557665000
     * alarmType : 1
     * imageUrl : https://manniu-cn-north-1.s3.cn-north-1.amazonaws.com.cn/003/958759451/1510557665284.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20171113T084717Z&X-Amz-SignedHeaders=host&X-Amz-Expires=86400&X-Amz-Credential=AKIAOVQUXF4JJYZ63LCA%2F20171113%2Fcn-north-1%2Fs3%2Faws4_request&X-Amz-Signature=8e7dd4ded10462b00d078e98705a077de56d6abe34036baa60fe4d4966a4f764
     * videoUrl : http://s3.cn-north-1.amazonaws.com.cn/manniu-cn-north-1/003/958759451/1510557676893.mp4
     * vStartTime : 1510557665000
     * vEndTime : 1510557686000
     * devName : 蛮牛测试
     * alarmInfo : find a person
     * personName : 蓝慧东
     * confidence : 92.79723
     */

    private String alarmId;
    private String deviceSn;
    private int channelNo;
    private long alarmTime;
    private int alarmType;
    private String imageUrl;
    private String videoUrl;
    private long vStartTime;
    private long vEndTime;
    private String devName;
    private String alarmInfo;
    private String personName;
    private double confidence;
    private String showTime;   // 自己添加的数据
    public int subAlarmType;//新加的字段
    private int status;
    private String recordUrl;
    private String vStartLocalTime;
    private String vEndLocalTime;
    public String devImagePath;

    public String getDevImagePath() {
        return devImagePath;
    }

    public void setDevImagePath(String devImagePath) {
        this.devImagePath = devImagePath;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public String getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }

    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn;
    }

    public int getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(int channelNo) {
        this.channelNo = channelNo;
    }

    public long getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(long alarmTime) {
        this.alarmTime = alarmTime;
    }

    public int getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(int alarmType) {
        this.alarmType = alarmType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public long getVStartTime() {
        return vStartTime;
    }

    public void setVStartTime(long vStartTime) {
        this.vStartTime = vStartTime;
    }

    public long getVEndTime() {
        return vEndTime;
    }

    public void setVEndTime(long vEndTime) {
        this.vEndTime = vEndTime;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String dev_name) {
        this.devName = dev_name;
    }

    public String getAlarmInfo() {
        return alarmInfo;
    }

    public void setAlarmInfo(String alarmInfo) {
        this.alarmInfo = alarmInfo;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public int getSubAlarmType() {
        return subAlarmType;
    }

    public void setSubAlarmType(int subAlarmType) {
        this.subAlarmType = subAlarmType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setRecordUrl(String recordUrl) {
        this.recordUrl = recordUrl;
    }

    public String getRecordUrl() {
        return recordUrl;
    }

    public String getvStartLocalTime() {
        return vStartLocalTime;
    }

    public void setvStartLocalTime(String vStartLocalTime) {
        this.vStartLocalTime = vStartLocalTime;
    }

    public String getvEndLocalTime() {
        return vEndLocalTime;
    }

    public void setvEndLocalTime(String vEndLocalTime) {
        this.vEndLocalTime = vEndLocalTime;
    }
}
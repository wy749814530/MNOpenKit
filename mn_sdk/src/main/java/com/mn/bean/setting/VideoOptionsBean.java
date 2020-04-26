package com.mn.bean.setting;

/**
 * Created by Administrator on 2019/3/25 0025.
 */

public class VideoOptionsBean {

    /**
     * channel : 0
     * code : 402
     * msg : Device Not Exist Or Unsupport
     * params : {"DayNightColor":1,"Flip":false,"Mirror":false}
     * result : true
     */

    private int channel;
    private int code;
    private String msg;
    private VideoInOptBean params;
    private boolean result;

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public VideoInOptBean getParams() {
        return params;
    }

    public void setParams(VideoInOptBean params) {
        this.params = params;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }


}

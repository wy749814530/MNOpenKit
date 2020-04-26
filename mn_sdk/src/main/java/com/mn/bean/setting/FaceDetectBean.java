package com.mn.bean.setting;

/**
 * Created by Administrator on 2019/3/25 0025.
 */

public class FaceDetectBean {

    /**
     * result : true
     * params : {"FaceDetection":true}
     * code : 402
     * msg : Device Not Exist Or Unsupport
     * channel : 1
     */

    private boolean result;
    private FaceBean params;
    private int code;
    private String msg;
    private int channel;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public FaceBean getParams() {
        return params;
    }

    public void setParams(FaceBean params) {
        this.params = params;
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

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }
}

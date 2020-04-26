package com.mn.bean.setting;

/**
 * Created by Administrator on 2019/3/25 0025.
 */

public class BaseResult {
    /**
     * result : false
     * code : 407
     * msg : TF Card Not Exist
     * channel : 0
     */

    private boolean result;
    private int code;
    private String msg;
    private int channel;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
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

package com.mn.bean.restfull;

import java.io.Serializable;

/**
 * Created by hjz on 2018/5/5.
 */

public class DevOnlineBean implements Serializable {

    /**
     * msg : OK
     * code : 2000
     * state : 2
     */

    private String msg;
    private int code;
    private int state;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}

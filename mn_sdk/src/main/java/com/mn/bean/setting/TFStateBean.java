package com.mn.bean.setting;

/**
 * Created by Administrator on 2019/9/26 0026.
 */

public class TFStateBean {

    /**
     * params : {"Name":"/dev/block/mmcblk0","State":"Normal"}
     * result : true
     * code : 407
     * msg : TF Card Not Exist
     */

    private ParamsBean params;
    private boolean result;
    private int code;
    private String msg;

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

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

    public static class ParamsBean {
        /**
         * Name : /dev/block/mmcblk0
         * State : Normal
         */

        private String Name;
        private String State;

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getState() {
            return State;
        }

        public void setState(String State) {
            this.State = State;
        }
    }
}

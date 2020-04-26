package com.mn.bean.setting;

/**
 * Created by Administrator on 2019/3/25 0025.
 */

public class MotionDetectBean {

    /**
     * channel : 0
     * params : {"MotionDetect":true,"Sensitivity":99}
     * result : true
     * code : 402
     * msg : Device Not Exist Or Unsupport
     */

    private int channel;
    private ParamsBean params;
    private boolean result;
    private int code;
    private String msg;

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

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
         * MotionDetect : true
         * Sensitivity : 99
         */

        private boolean MotionDetect;
        private int Sensitivity;

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
    }
}

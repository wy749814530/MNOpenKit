package com.mn.bean.setting;

/**
 * Created by jz on 2019/6/13.
 */

public class LocationMobileBean {

    /**
     * result : true
     * params : {"MotionTrack":true}
     */

    private boolean result;
    private ParamsBean params;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public static class ParamsBean {
        /**
         * MotionTrack : true
         */

        private boolean MotionTrack;

        public boolean isMotionTrack() {
            return MotionTrack;
        }

        public void setMotionTrack(boolean MotionTrack) {
            this.MotionTrack = MotionTrack;
        }
    }
}

package com.mn.bean.setting;

/**
 * Created by hjz on 2019/8/15.
 */

public class AlarmAsossiatedBean {

    /**
     * result : true
     * params : {"LightType":0,"AudioEnable":false}
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
         * LightType : 0
         * AudioEnable : false
         */

        private int LightType;
        private boolean AudioEnable;

        public int getLightSeconds() {
            return LightSeconds;
        }

        public void setLightSeconds(int lightSeconds) {
            LightSeconds = lightSeconds;
        }

        private int LightSeconds;

        public int getLightType() {
            return LightType;
        }

        public void setLightType(int LightType) {
            this.LightType = LightType;
        }

        public boolean isAudioEnable() {
            return AudioEnable;
        }

        public void setAudioEnable(boolean AudioEnable) {
            this.AudioEnable = AudioEnable;
        }
    }
}

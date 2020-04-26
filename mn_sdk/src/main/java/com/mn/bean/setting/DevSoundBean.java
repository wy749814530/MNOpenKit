package com.mn.bean.setting;

/**
 * Created by Administrator on 2019/3/25 0025.
 */

public class DevSoundBean {

    /**
     * result : true
     * params : {"SilentMode":false,"VoiceEnable":false}
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
         * SilentMode : false
         * VoiceEnable : false
         */

        private boolean SilentMode;
        private boolean VoiceEnable;

        public boolean isSilentMode() {
            return SilentMode;
        }

        public void setSilentMode(boolean SilentMode) {
            this.SilentMode = SilentMode;
        }

        public boolean isVoiceEnable() {
            return VoiceEnable;
        }

        public void setVoiceEnable(boolean VoiceEnable) {
            this.VoiceEnable = VoiceEnable;
        }
    }
}

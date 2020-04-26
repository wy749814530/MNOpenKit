package com.mn.bean.setting;

/**
 * Created by hjz on 2019/9/26.
 */

public class BLCConfigBean {


    /**
     * result : true
     * params : {"LightEnable":false,"LightSensitive":50}
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
         * LightEnable : false
         * LightSensitive : 50
         */

        private boolean LightEnable;
        private int LightSensitive;

        public boolean isLightEnable() {
            return LightEnable;
        }

        public void setLightEnable(boolean LightEnable) {
            this.LightEnable = LightEnable;
        }

        public int getLightSensitive() {
            return LightSensitive;
        }

        public void setLightSensitive(int LightSensitive) {
            this.LightSensitive = LightSensitive;
        }
    }
}

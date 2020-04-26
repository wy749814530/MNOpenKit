package com.mn.bean.setting;

/**
 * Created by Administrator on 2019/3/25 0025.
 */

public class NetLightCallBean {

    /**
     * result : true
     * params : {"NetLight":true}
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
         * NetLight : true
         */

        private boolean NetLight;

        public boolean isNetLight() {
            return NetLight;
        }

        public void setNetLight(boolean NetLight) {
            this.NetLight = NetLight;
        }
    }
}

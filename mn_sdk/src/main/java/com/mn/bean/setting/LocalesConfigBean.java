package com.mn.bean.setting;

/**
 * Created by Administrator on 2019/3/25 0025.
 */

public class LocalesConfigBean {

    /**
     * result : true
     * params : {"DSTEnable":false}
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
         * DSTEnable : false
         */

        private boolean DSTEnable;

        public boolean isDSTEnable() {
            return DSTEnable;
        }

        public void setDSTEnable(boolean DSTEnable) {
            this.DSTEnable = DSTEnable;
        }
    }
}

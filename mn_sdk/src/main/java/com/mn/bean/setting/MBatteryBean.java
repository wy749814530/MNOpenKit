package com.mn.bean.setting;

import java.util.List;

/**
 * Created by WIN on 2018/5/16.
 */

public class MBatteryBean {


    /**
     * result : true
     * params : [{"Charging":true,"Percent":10}]
     * error : Params Is Error
     */

    private boolean result;
    private List<ParamsBean> params;
    private String error;
    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public List<ParamsBean> getParams() {
        return params;
    }

    public void setParams(List<ParamsBean> params) {
        this.params = params;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public static class ParamsBean {
        /**
         * Charging : true
         * Percent : 10
         */

        private boolean Charging;
        private int Percent;

        public boolean isCharging() {
            return Charging;
        }

        public void setCharging(boolean Charging) {
            this.Charging = Charging;
        }

        public int getPercent() {
            return Percent;
        }

        public void setPercent(int Percent) {
            this.Percent = Percent;
        }
    }
}

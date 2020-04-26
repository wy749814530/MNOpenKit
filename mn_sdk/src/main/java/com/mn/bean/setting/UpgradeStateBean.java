package com.mn.bean.setting;

/**
 * Created by Administrator on 2019/3/27 0027.
 */

public class UpgradeStateBean {

    /**
     * result : true
     * params : {"State":"Preparing","Progress":0}
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
         * State : Preparing
         * Progress : 0
         */

        private String State;
        private int Progress;

        public String getState() {
            return State;
        }

        public void setState(String State) {
            this.State = State;
        }

        public int getProgress() {
            return Progress;
        }

        public void setProgress(int Progress) {
            this.Progress = Progress;
        }
    }
}

package com.mn.bean.setting;

import java.util.List;

/**
 * Created by Administrator on 2019/4/1 0001.
 */

public class NVRIPCResultBean {

    /**
     * result : true
     * error : time out
     * params : [{"channel":0,"result":false},{"channel":1,"result":false}]
     */

    private boolean result;
    private String error;
    private List<ParamsBean> params;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<ParamsBean> getParams() {
        return params;
    }

    public void setParams(List<ParamsBean> params) {
        this.params = params;
    }

    public static class ParamsBean {
        /**
         * channel : 0
         * result : false
         */

        private int channel;
        private boolean result;

        public int getChannel() {
            return channel;
        }

        public void setChannel(int channel) {
            this.channel = channel;
        }

        public boolean isResult() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }
    }
}

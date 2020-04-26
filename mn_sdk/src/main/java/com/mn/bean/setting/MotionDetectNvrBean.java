package com.mn.bean.setting;

import java.util.List;

/**
 * Created by Administrator on 2019/4/1 0001.
 */

public class MotionDetectNvrBean {

    /**
     * result : true
     * params : [{"channel":0,"params":{"MotionDetect":true,"Sensitivity":5},"result":true},{"channel":1,"params":{"MotionDetect":true,"Sensitivity":5},"result":true}]
     */

    private boolean result;
    private List<MotionDetectBean> params;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public List<MotionDetectBean> getParams() {
        return params;
    }

    public void setParams(List<MotionDetectBean> params) {
        this.params = params;
    }
}

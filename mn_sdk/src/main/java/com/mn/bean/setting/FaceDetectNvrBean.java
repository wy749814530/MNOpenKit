package com.mn.bean.setting;

import java.util.List;

/**
 * Created by Administrator on 2019/4/1 0001.
 */

public class FaceDetectNvrBean {

    /**
     * {"result":true,"params":[
     * {"channel":0,"params":{"FaceDetection":true},"result":true},
     * {"result":false,"code":402,"msg":"Device Not Exist Or Unsupport","channel":1},
     * {"result":false,"code":402,"msg":"Device Not Exist Or Unsupport","channel":2},
     * {"result":false,"code":402,"msg":"Device Not Exist Or Unsupport","channel":3}]}
     */

    private boolean result;
    private String error;
    private List<FaceDetectBean> params;

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

    public List<FaceDetectBean> getParams() {
        return params;
    }

    public void setParams(List<FaceDetectBean> params) {
        this.params = params;
    }
}

package com.mn.bean.setting;

import java.util.List;

/**
 * Created by Administrator on 2019/3/26 0026.
 */

public class VideoOptionsNvrBean {

    /**
     * {"result":true,"params":[
     * {"channel":0,"params":{"DayNightColor":1,"Flip":false,"Mirror":false},"result":true},
     * {"channel":1,"params":{"DayNightColor":1,"Flip":false,"Mirror":false},"result":true},
     * {"result":false,"code":402,"msg":"Device Not Exist Or Unsupport","channel":2},
     * {"result":false,"code":402,"msg":"Device Not Exist Or Unsupport","channel":3}]}
     */

    private boolean result;
    private List<VideoOptionsBean> params;
    private int code;

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public List<VideoOptionsBean> getParams() {
        return params;
    }

    public void setParams(List<VideoOptionsBean> params) {
        this.params = params;
    }
}

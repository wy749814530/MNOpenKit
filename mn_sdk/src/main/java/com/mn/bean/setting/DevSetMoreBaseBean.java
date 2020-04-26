package com.mn.bean.setting;

import java.util.List;

/**
 * Created by Administrator on 2019/3/26 0026.
 */

public class DevSetMoreBaseBean {


    /**
     * params : [{"result":true,"channel":0},{"result":false,"code":402,"msg":"Device Not Exist Or Unsupport","channel":1},{"result":false,"code":402,"msg":"Device Not Exist Or Unsupport","channel":2},{"result":false,"code":402,"msg":"Device Not Exist Or Unsupport","channel":3}]
     * result : true
     */
    private int code;
    private String msg;
    private boolean result;
    private List<BaseResult> params;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public List<BaseResult> getParams() {
        return params;
    }

    public void setParams(List<BaseResult> params) {
        this.params = params;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}

package com.mn.bean.setting;

import java.util.List;

/**
 * Created by Administrator on 2019/3/25 0025.
 */

public class AlarmTimeRecordNvrBean {
    /*
    *{"result":true,"params":[
    * {"channel":0,"params":{"AllDayRecord":true},"result":true},
    * {"result":false,"code":408,"msg":"Record Plan Unsupport","channel":1},
    * {"result":false,"code":408,"msg":"Record Plan Unsupport","channel":2},
    * {"result":false,"code":408,"msg":"Record Plan Unsupport","channel":3}]}
   */
    private boolean result;
    private List<AlarmTimeRecordBean> params;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public List<AlarmTimeRecordBean> getParams() {
        return params;
    }

    public void setParams(List<AlarmTimeRecordBean> params) {
        this.params = params;
    }
}

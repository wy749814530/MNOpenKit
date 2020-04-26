package com.mn.bean.setting;

/**
 * Created by Administrator on 2019/3/25 0025.
 */

public class LanguageBean {

    /**
     * result : true
     * code :403
     * params : {"Language":"SimpChinese"}
     */

    private boolean result;
    private ParamsBean params;
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


    public ParamsBean getParams() {
        return params;
    }


    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public static class ParamsBean {
        /**
         * Language : SimpChinese
         */

        private String Language;

        public String getLanguage() {
            return Language;
        }

        public void setLanguage(String Language) {
            this.Language = Language;
        }
    }
}

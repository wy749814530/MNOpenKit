package com.mn.bean.setting;

import java.util.List;

/**
 * Created by Administrator on 2019/3/26 0026.
 */

public class TFStateConfigBean {

    /**
     * params : [{"Name":"/dev/block/mmcblk0","State":"Normal","TotalSpace":7811891200,"FreeSpace":7505707008}]
     * result : true
     * code : 407
     * msg : TF Card Not Exist
     */

    private boolean result;
    private int code;
    private String msg;
    private List<ParamsBean> params;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ParamsBean> getParams() {
        return params;
    }

    public void setParams(List<ParamsBean> params) {
        this.params = params;
    }

    public static class ParamsBean {
        /**
         * Name : /dev/block/mmcblk0
         * State : Normal
         * TotalSpace : 7811891200
         * FreeSpace : 7505707008
         */

        private String Name;
        private String State;
        private long TotalSpace;
        private long FreeSpace;

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getState() {
            return State;
        }

        public void setState(String State) {
            this.State = State;
        }

        public long getTotalSpace() {
            return TotalSpace;
        }

        public void setTotalSpace(long TotalSpace) {
            this.TotalSpace = TotalSpace;
        }

        public long getFreeSpace() {
            return FreeSpace;
        }

        public void setFreeSpace(long FreeSpace) {
            this.FreeSpace = FreeSpace;
        }
    }
}

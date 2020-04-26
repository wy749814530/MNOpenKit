package com.mn.bean.setting;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/4/1 0001.
 */

public class NVRIPCInfoBean implements Serializable {

    private static final long serialVersionUID = 5385185397687214512L;


    /**
     * result : true
     * params : [{"channel":0,"params":{"Name":"ctt name1","OnLine":true,"IsSynToFront":false,"LinkQuality":100,"Mac":"e0:61:b2:20:11:33"},"result":true},{"channel":1,"params":{"Name":"ctt name2","OnLine":true,"IsSynToFront":false,"LinkQuality":100,"Mac":"e0:61:b2:29:36:b5"},"result":true},{"result":false,"code":402,"msg":"Device Not Exist Or Unsupport","channel":2},{"result":false,"code":402,"msg":"Device Not Exist Or Unsupport","channel":3}]
     */

    private boolean result;
    private List<ChannelBean> params;
    /**
     * code : 414
     * msg : Time Out
     */

    private int code;
    private String msg;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public List<ChannelBean> getParams() {
        return params;
    }

    public void setParams(List<ChannelBean> params) {
        this.params = params;
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

    public static class ChannelBean implements Serializable {
        private static final long serialVersionUID = -8984999167986553279L;
        /**
         * channel : 0
         * params : {"Name":"ctt name1","OnLine":true,"IsSynToFront":false,"LinkQuality":100,"Mac":"e0:61:b2:20:11:33"}
         * result : true
         * code : 402
         * msg : Device Not Exist Or Unsupport
         */

        private int channel;
        private DataBean params;
        private boolean result;
        private int code;
        private String msg;

        public int getChannel() {
            return channel;
        }

        public void setChannel(int channel) {
            this.channel = channel;
        }

        public DataBean getParams() {
            return params;
        }

        public void setParams(DataBean params) {
            this.params = params;
        }

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

        public static class DataBean implements Serializable {
            private static final long serialVersionUID = -4653130777490466502L;
            /**
             * Name : ctt name1
             * OnLine : true
             * IsSynToFront : false
             * LinkQuality : 100
             * Mac : e0:61:b2:20:11:33
             */

            private String Name;
            private boolean OnLine;
            private boolean IsSynToFront;
            private int LinkQuality;
            private String Mac;

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public boolean isOnLine() {
                return OnLine;
            }

            public void setOnLine(boolean OnLine) {
                this.OnLine = OnLine;
            }

            public boolean isIsSynToFront() {
                return IsSynToFront;
            }

            public void setIsSynToFront(boolean IsSynToFront) {
                this.IsSynToFront = IsSynToFront;
            }

            public int getLinkQuality() {
                return LinkQuality;
            }

            public void setLinkQuality(int LinkQuality) {
                this.LinkQuality = LinkQuality;
            }

            public String getMac() {
                return Mac;
            }

            public void setMac(String Mac) {
                this.Mac = Mac;
            }
        }
    }
}

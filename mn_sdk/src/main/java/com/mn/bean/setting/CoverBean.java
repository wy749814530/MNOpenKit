package com.mn.bean.setting;

import java.util.List;

/**
 * Created by hjz on 2017/11/27.
 */

public class CoverBean {

    /**
     * deviceSn : MDAhAQEAbGUwNjFiMjIzOWNmZAAA
     * imgUrl : http://s3.cn-north-1.amazonaws.com.cn/manniu-cn-north-1/003/1527048548/1511750665803.jpg
     */
    /**
     * result : false
     * code : 414
     * msg : Time Out
     */
    private String deviceSn;
    private String imgUrl;
    private List<ImgUrlsBean> imgUrls;

    private boolean result;
    private int code;
    private String msg;

    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<ImgUrlsBean> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List<ImgUrlsBean> imgUrls) {
        this.imgUrls = imgUrls;
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


    public static class ImgUrlsBean {
        /**
         * channel : 0
         * url : http://s3.cn-north-1.amazonaws.com.cn/cloud-alarm-data-beijing/007/2345116456/1556603203621.jpg
         */

        private int channel;
        private String url;

        public int getChannel() {
            return channel;
        }

        public void setChannel(int channel) {
            this.channel = channel;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}

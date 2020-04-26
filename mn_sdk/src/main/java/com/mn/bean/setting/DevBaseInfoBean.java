package com.mn.bean.setting;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jz on 2018/5/31.
 */

/**
 * result : true
 * params : {"deviceSn":"MDAhAQEAbGUwNjFiMjRjMDAxMAAA",
 * "ipAddr":"192.168.1.108","linkMode":1,
 * "sdCard":true,"version":"2019-01-15","wifiId":"TP-link-8c50","wifiSignal":100}
 */
public class DevBaseInfoBean implements Serializable {
    private static final long serialVersionUID = 4478727208919604828L;

    private boolean result;
    private ParamsBean params;
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

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
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

    public static class ParamsBean implements Serializable {

        private static final long serialVersionUID = 8001883425203263813L;
        /**
         * deviceSn : MDAhAQEAbGUwNjFiMjRjMDAxMAAA
         * ipAddr : 192.168.1.108
         * linkMode : 1     2代表网线， 3，4代表 g
         * sdCard : true
         * version : 2019-01-15
         * wifiId : TP-link-8c50
         * wifiSignal : 100  信号强度
         * signalQuality:20 信号质量
         */

        private String deviceSn;
        private String ipAddr;
        private int linkMode;
        private boolean sdCard;
        private String version;
        private String wifiId;
        private int wifiSignal;

        private String gmoduleVer;
        private int signalQuality;

        public int getSignalQuality() {
            return signalQuality;
        }

        public void setSignalQuality(int signalQuality) {
            this.signalQuality = signalQuality;
        }

        /**
         * cardNumber : 1
         * sdCardName : ["mmcblock0"]
         */
        public String getGmoduleVer() {
            return gmoduleVer;
        }

        public void setGmoduleVer(String gmoduleVer) {
            this.gmoduleVer = gmoduleVer;
        }

        private int cardNumber;
        private List<String> sdCardName;

        public String getDeviceSn() {
            return deviceSn;
        }

        public void setDeviceSn(String deviceSn) {
            this.deviceSn = deviceSn;
        }

        public String getIpAddr() {
            return ipAddr;
        }

        public void setIpAddr(String ipAddr) {
            this.ipAddr = ipAddr;
        }

        public int getLinkMode() {
            return linkMode;
        }

        public void setLinkMode(int linkMode) {
            this.linkMode = linkMode;
        }

        public boolean isSdCard() {
            return sdCard;
        }

        public void setSdCard(boolean sdCard) {
            this.sdCard = sdCard;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getWifiId() {
            return wifiId;
        }

        public void setWifiId(String wifiId) {
            this.wifiId = wifiId;
        }

        public int getWifiSignal() {
            return wifiSignal;
        }

        public void setWifiSignal(int wifiSignal) {
            this.wifiSignal = wifiSignal;
        }

        public int getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(int cardNumber) {
            this.cardNumber = cardNumber;
        }

        public List<String> getSdCardName() {
            return sdCardName;
        }

        public void setSdCardName(List<String> sdCardName) {
            this.sdCardName = sdCardName;
        }
    }
}
package com.mn.bean.restfull;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/9/10 0010.
 */

public class FirmwareVerBean extends BaseBean {

    private static final long serialVersionUID = 3183338748522508555L;
    /**
     * currentVersion : 2018-06-08
     * firmwares : [{"url":"http://firmware-beijing.s3.cn-north-1.amazonaws.com.cn/50951efbacbc411b8312d89fbf88a4a6/General_CM_[ZK313E_1S_W_8188]_Chn_P_V2.115.0.0.R.180608.bin","size":7545363,"md5":"5a1cf99364df1c821421b24080d964df","version":"2018-06-08","desc":"1、修改APP端升级时下载进度显示方式\r\n2、修改升级保证在进度条达到100时才开始语音播报\r\n3、修改升级成功后由APP发起重启命令后才重启\r\n4、修改不同连接过程状态灯显示\r\n5、修改时区设置时间，加大延时为20ms，确保时间已经更新\r\n6、更新蛮牛SDK，优化音频引起的不能发送视频的问题\r\n7、更新patch：MSC3XX_SDK_V_1805031100-I3_sz1109--1805250952-I3_sz1109，解决抓图通道出错问题","min_version":"2017-01-01","max_version":"2018-06-07"},{"url":"http://firmware-beijing.s3.cn-north-1.amazonaws.com.cn/96f17a20f3944790b292c1e76c1b74c6/General_CM_[ZK313E_1S_W_8188]_Chn_P_V3.115.0.0.R.180606.bin","size":7542722,"md5":"e7ca1f6a70df4842c8333d81d590bd24","version":"2018-06-06","desc":"1.修改了升级下载进度条显示方式","min_version":"2017-01-01","max_version":"2018-06-05"}]
     */

    private String currentVersion;
    private List<FirmwaresBean> firmwares;

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    public List<FirmwaresBean> getFirmwares() {
        return firmwares;
    }

    public void setFirmwares(List<FirmwaresBean> firmwares) {
        this.firmwares = firmwares;
    }

    public static class FirmwaresBean implements Serializable {
        private static final long serialVersionUID = -7992126183720880950L;
        /**
         * url : http://firmware-beijing.s3.cn-north-1.amazonaws.com.cn/50951efbacbc411b8312d89fbf88a4a6/General_CM_[ZK313E_1S_W_8188]_Chn_P_V2.115.0.0.R.180608.bin
         * size : 7545363
         * md5 : 5a1cf99364df1c821421b24080d964df
         * version : 2018-06-08
         * desc : 1、修改APP端升级时下载进度显示方式
         * 2、修改升级保证在进度条达到100时才开始语音播报
         * 3、修改升级成功后由APP发起重启命令后才重启
         * 4、修改不同连接过程状态灯显示
         * 5、修改时区设置时间，加大延时为20ms，确保时间已经更新
         * 6、更新蛮牛SDK，优化音频引起的不能发送视频的问题
         * 7、更新patch：MSC3XX_SDK_V_1805031100-I3_sz1109--1805250952-I3_sz1109，解决抓图通道出错问题
         * min_version : 2017-01-01
         * max_version : 2018-06-07
         */

        private String url;
        private int size;
        private String md5;
        private String version;
        private String desc;
        private String min_version;
        private String max_version;
        private int force_upgrade;
        private int upgrade_prompt;

        public int getForce_upgrade() {
            return force_upgrade;
        }

        public void setForce_upgrade(int force_upgrade) {
            this.force_upgrade = force_upgrade;
        }

        public int getUpgrade_prompt() {
            return upgrade_prompt;
        }

        public void setUpgrade_prompt(int upgrade_prompt) {
            this.upgrade_prompt = upgrade_prompt;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getMin_version() {
            return min_version;
        }

        public void setMin_version(String min_version) {
            this.min_version = min_version;
        }

        public String getMax_version() {
            return max_version;
        }

        public void setMax_version(String max_version) {
            this.max_version = max_version;
        }
    }
}

package com.mn.bean.restfull;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;

import MNSDK.MNJni;
import MNSDK.MNOpenSDK;

/**
 * Created by Administrator on 2019/4/12 0012.
 */

public class DevicesBean implements Serializable {

    private static final long serialVersionUID = 681264427475593161L;
    private int ctrl_access;
    private long valid_term;
    private int max_count;
    private int online;
    private String facelib;
    private String dev_name;
    private int logo_type;
    private long add_time;
    private long manufacturer_id;
    private long first_online_time;
    private int alarm_storage_days;
    private int video_storage_days;
    private int authority;
    private String id;
    private String sn;
    private String ip;
    private int port;
    private int state;
    private int type;
    private String ver;
    private String model;
    private String vn;
    private String logo;
    private int channels;
    private long remain_time = -1;
    private double longitude;
    private double latitude;
    private MNJni.MNServerInfo mnServerInfo;
    private String signOutTime;
    private float cloudStoreDays;
    private int storage_received = -1;
    /**
     * iotPoints : [{"id":"10000","type":1,"name":"PM2.5","value":"12.3","dataType":2,"readWrite":1,"upper":400,"lower":0,"validBit":0,"unit":"μg/m³"},
     * {"id":"10020","type":2,"name":"温度","value":"24.3","dataType":2,"readWrite":1,"upper":100,"lower":-50,"validBit":0,"unit":"℃"},
     * {"id":"10040","type":3,"name":"湿度","value":"22.3","dataType":2,"readWrite":1,"upper":100,"lower":0,"validBit":0,"unit":"%RH"},
     * {"id":"10060","type":4,"name":"甲醛","value":"24.3","dataType":2,"readWrite":1,"upper":1,"lower":0,"validBit":0,"unit":"mg/m³"}]
     * alarm_storage_days : 7
     * video_storage_days : 1
     * last_offline_time : 1555038958000
     */

    private String last_offline_time;
    private List<IotPointsBean> iot_points;
    private List<ChannelImagesBean> channel_images;
    /**
     * support_ability : {"h24recordAbility":1,"alarmAbility":[{"alarmType":8,"subAlarmType":[0]},{"alarmType":2,"subAlarmType":[10]},{"alarmType":3,"subAlarmType":[1]}]}
     * storage_type : 2
     */

    private SupportAbilityBean support_ability;
    private int storage_type;
    private int wifiSignal;
    private int signalModel;
    private List<BatteryBean> battery;

    public int getWifiSignal() {
        return wifiSignal;
    }

    public void setWifiSignal(int wifiSignal) {
        this.wifiSignal = wifiSignal;
    }

    public int getSignalModel() {
        return signalModel;
    }

    public void setSignalModel(int signalModel) {
        this.signalModel = signalModel;
    }

    public MNJni.MNServerInfo getMnServerInfo() {
        return mnServerInfo;
    }

    public void setMnServerInfo(MNJni.MNServerInfo mnServerInfo) {
        this.mnServerInfo = mnServerInfo;
    }

    public List<IotPointsBean> getIot_points() {
        return iot_points;
    }

    public void setIot_points(List<IotPointsBean> iot_points) {
        this.iot_points = iot_points;
    }

    public List<ChannelImagesBean> getChannel_images() {
        return channel_images;
    }

    public void setChannel_images(List<ChannelImagesBean> channel_images) {
        this.channel_images = channel_images;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getCtrl_access() {
        return ctrl_access;
    }

    public void setCtrl_access(int ctrl_access) {
        this.ctrl_access = ctrl_access;
    }

    public long getValid_term() {
        return valid_term;
    }

    public void setValid_term(long valid_term) {
        this.valid_term = valid_term;
    }

    public int getMax_count() {
        return max_count;
    }

    public void setMax_count(int max_count) {
        this.max_count = max_count;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getAuthority() {
        return authority;
    }

    public void setAuthority(int authority) {
        this.authority = authority;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setRemain_time(long remain_time) {
        this.remain_time = remain_time;
    }

    public long getRemain_time() {
        return remain_time;
    }

    private String from;
    private List<UsersBean> users;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVn() {
        return vn;
    }

    public void setVn(String vn) {
        this.vn = vn;
    }

    public String getLogo() {
        if (TextUtils.isEmpty(logo)) {
            return null;
        } else {
            if (logo.contains("?app_key=") && logo.contains("&app_secret=") && logo.contains("&access_token=")) {
                return logo;
            }
            return logo + "?app_key=" + MNOpenSDK.getAppKey() + "&app_secret=" + MNOpenSDK.getAppSecret() + "&access_token=" + MNOpenSDK.getAccessToken();
        }
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getChannels() {
        return channels;
    }

    public void setChannels(int channels) {
        this.channels = channels;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public String getFacelib() {
        return facelib;
    }

    public void setFacelib(String facelib) {
        this.facelib = facelib;
    }

    public String getDev_name() {
        return dev_name;
    }

    public void setDev_name(String dev_name) {
        this.dev_name = dev_name;
    }

    public int getLogo_type() {
        return logo_type;
    }

    public void setLogo_type(int logo_type) {
        this.logo_type = logo_type;
    }

    public long getAdd_time() {
        return add_time;
    }

    public void setAdd_time(long add_time) {
        this.add_time = add_time;
    }

    public long getManufacturer_id() {
        return manufacturer_id;
    }

    public void setManufacturer_id(long manufacturer_id) {
        this.manufacturer_id = manufacturer_id;
    }

    public long getFirst_online_time() {
        return first_online_time;
    }

    public void setFirst_online_time(long first_online_time) {
        this.first_online_time = first_online_time;
    }

    public int getAlarm_storage_days() {
        return alarm_storage_days;
    }

    public void setAlarm_storage_days(int alarm_storage_days) {
        this.alarm_storage_days = alarm_storage_days;
    }

    public int getVideo_storage_days() {
        return video_storage_days;
    }

    public void setVideo_storage_days(int video_storage_days) {
        this.video_storage_days = video_storage_days;
    }

    public void setSignOutTime(String signOutTime) {
        this.signOutTime = signOutTime;
    }

    public void setCloudStoreDays(float cloudStoreDays) {
        this.cloudStoreDays = cloudStoreDays;
    }

    public float getCloudStoreDays() {
        return cloudStoreDays;
    }

    public String getSignOutTime() {
        return signOutTime;
    }

    public List<UsersBean> getUsers() {
        return users;
    }

    public void setUsers(List<UsersBean> users) {
        this.users = users;
    }

    public String getLast_offline_time() {
        return last_offline_time;
    }

    public void setLast_offline_time(String last_offline_time) {
        this.last_offline_time = last_offline_time;
    }

    public SupportAbilityBean getSupport_ability() {
        return support_ability;
    }

    public void setSupport_ability(SupportAbilityBean support_ability) {
        this.support_ability = support_ability;
    }

    public int getStorage_type() {
        return storage_type;
    }

    public void setStorage_type(int storage_type) {
        this.storage_type = storage_type;
    }

    public int getStorage_received() {
        return storage_received;
    }

    public void setStorage_received(int storage_received) {
        this.storage_received = storage_received;
    }

    public List<BatteryBean> getBattery() {
        return battery;
    }

    public void setBattery(List<BatteryBean> battery) {
        this.battery = battery;
    }

    public static class UsersBean implements Serializable {
        private static final long serialVersionUID = 3904234524620020409L;
        /**
         * id : 189870869743407104
         * phone : 13291820925
         * avatar : http://rest.mny9.com:80/api/v1/users/avatar/01AEDEBFF7C96382F4E23FE48F989D69/u_189870869743407104.jpeg
         * nickname : 很懒，没设置昵称
         * state : 1
         * authority : 15
         * watch_time : 1534467729000
         * watch_times : 43
         */

        private String id;
        private String phone;
        private String avatar;
        private String nickname;
        private int state;
        private int authority;
        private String watch_time;
        private int watch_times;
        private int remain_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getRemain_time() {
            return remain_time;
        }

        public void setRemain_time(int remain_time) {
            this.remain_time = remain_time;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getAuthority() {
            return authority;
        }

        public void setAuthority(int authority) {
            this.authority = authority;
        }

        public String getWatch_time() {
            return watch_time;
        }

        public void setWatch_time(String watch_time) {
            this.watch_time = watch_time;
        }

        public int getWatch_times() {
            return watch_times;
        }

        public void setWatch_times(int watch_times) {
            this.watch_times = watch_times;
        }
    }

    public static class IotPointsBean implements Serializable {
        private static final long serialVersionUID = 7683307466109791081L;
        /**
         * id : 10000
         * type : 1     1 PM2.5， 2 温度， 3 湿度， 4 甲醛， 5 可燃气体
         * name : PM2.5
         * value : 12.3   ""
         * dataType : 2  data_type ""
         * readWrite : 1  read_write
         * upper : 400
         * lower : 0
         * validBit : 0
         * unit : μg/m³
         * pointId   point_id  ""
         * multiple
         */

        private String id;
        private int type;
        private String name;
        private String value;
        private int data_type;
        private int read_write;
        private int upper;
        private int lower;
        private int validBit;
        private String unit;
        private int multiple;
        private String point_id;

        public String getPoint_id() {
            return point_id;
        }

        public void setPoint_id(String point_id) {
            this.point_id = point_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getData_type() {
            return data_type;
        }

        public void setData_type(int data_type) {
            this.data_type = data_type;
        }

        public int getRead_write() {
            return read_write;
        }

        public void setRead_write(int read_write) {
            this.read_write = read_write;
        }

        public int getUpper() {
            return upper;
        }

        public void setUpper(int upper) {
            this.upper = upper;
        }

        public int getLower() {
            return lower;
        }

        public void setLower(int lower) {
            this.lower = lower;
        }

        public int getValidBit() {
            return validBit;
        }

        public void setValidBit(int validBit) {
            this.validBit = validBit;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public int getMultiple() {
            return multiple;
        }

        public void setMultiple(int multiple) {
            this.multiple = multiple;
        }
    }

    public static class ChannelImagesBean implements Serializable {
        private static final long serialVersionUID = 7956160329226489642L;


        /**
         * channel_no : 1
         * image_url :
         */

        private int channel_no;
        private String image_url;

        public int getChannel_no() {
            return channel_no;
        }

        public void setChannel_no(int channel_no) {
            this.channel_no = channel_no;
        }

        public String getImage_url() {
            if (image_url == null) {
                return null;
            } else {
                if (image_url.contains("?app_key=") && image_url.contains("&app_secret=") && image_url.contains("&access_token=")) {
                    return image_url;
                }
                return image_url + "?app_key=" + MNOpenSDK.getAppKey() + "&app_secret=" + MNOpenSDK.getAppSecret() + "&access_token=" + MNOpenSDK.getAccessToken() + "&channel_no=" + channel_no;
            }
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }
    }

    public static class SupportAbilityBean implements Serializable {

        private static final long serialVersionUID = -298299203577609815L;
        private int h24recordAbility;
        private int timingCaptureAbility;
        private List<AlarmAbilityBean> alarmAbility; //报警能力
        private int batteryAbility;
        private OtherAbilityBean otherAbility;
        //todo 服务器上线之后开放，现在服务器上是int变量属性
        private PtzAbilityBean ptzAbility; // 云台能力
        private CloudStorageAbilityBean cloudStorageAbility;
        private FourgAbilityBean fourgAbility;


        /**
         * ptzAbility : {"direction":1,"track":1}
         * cloudStorageAbility : {"eventStorage":1,"h24recordStorage":1}
         * distributionNetworkAbility : [{"configNetType":1},{"configNetType":2},{"configNetType":3},{"configNetType":4},{"configNetType":5}]
         * connectNetworkAbility : [{"connectNetType":1},{"connectNetType":2},{"connectNetType":3}]
         * fourgAbility : {"fourgEnable":1}
         * alarmAbility : [{"alarmType":8,"subAlarmType":[0]},{"alarmType":2,"subAlarmType":[10]},{"alarmType":3,"subAlarmType":[1]}]
         * h24recordAbility : 1
         */

        public int getH24recordAbility() {
            return h24recordAbility;
        }

        public void setH24recordAbility(int h24recordAbility) {
            this.h24recordAbility = h24recordAbility;
        }

        public void setTimingCaptureAbility(int timingCaptureAbility) {
            this.timingCaptureAbility = timingCaptureAbility;
        }

        public int getTimingCaptureAbility() {
            return timingCaptureAbility;
        }

        public List<AlarmAbilityBean> getAlarmAbility() {
            return alarmAbility;
        }

        public void setAlarmAbility(List<AlarmAbilityBean> alarmAbility) {
            this.alarmAbility = alarmAbility;
        }

        public PtzAbilityBean getPtzAbility() {
            return ptzAbility;
        }

        public void setPtzAbility(PtzAbilityBean ptzAbility) {
            this.ptzAbility = ptzAbility;
        }

        public CloudStorageAbilityBean getCloudStorageAbility() {
            return cloudStorageAbility;
        }

        public void setCloudStorageAbility(CloudStorageAbilityBean cloudStorageAbility) {
            this.cloudStorageAbility = cloudStorageAbility;
        }

        public FourgAbilityBean getFourgAbility() {
            return fourgAbility;
        }

        public void setFourgAbility(FourgAbilityBean fourgAbility) {
            this.fourgAbility = fourgAbility;
        }

        public void setBatteryAbility(int batteryAbility) {
            this.batteryAbility = batteryAbility;
        }

        public int getBatteryAbility() {
            return batteryAbility;
        }

        public void setOtherAbility(OtherAbilityBean otherAbility) {
            this.otherAbility = otherAbility;
        }

        public OtherAbilityBean getOtherAbility() {
            return otherAbility;
        }

        /*     public List<DistributionNetworkAbilityBean> getDistributionNetworkAbility() {
            return distributionNetworkAbility;
        }

        public void setDistributionNetworkAbility(List<DistributionNetworkAbilityBean> distributionNetworkAbility) {
            this.distributionNetworkAbility = distributionNetworkAbility;
        }

        public List<ConnectNetworkAbilityBean> getConnectNetworkAbility() {
            return connectNetworkAbility;
        }

        public void setConnectNetworkAbility(List<ConnectNetworkAbilityBean> connectNetworkAbility) {
            this.connectNetworkAbility = connectNetworkAbility;
        }*/

        public static class AlarmAbilityBean implements Serializable {
            private static final long serialVersionUID = 8549746970510628280L;
            /**
             * alarmType : 8
             * subAlarmType : [0]
             */

            private int alarmType;
            private List<Integer> subAlarmType;

            public int getAlarmType() {
                return alarmType;
            }

            public void setAlarmType(int alarmType) {
                this.alarmType = alarmType;
            }

            public List<Integer> getSubAlarmType() {
                return subAlarmType;
            }

            public void setSubAlarmType(List<Integer> subAlarmType) {
                this.subAlarmType = subAlarmType;
            }
        }

        public static class OtherAbilityBean implements Serializable {

            private static final long serialVersionUID = -4423487672867479294L;
            private int lowPower;

            public void setLowPower(int lowPower) {
                this.lowPower = lowPower;
            }

            public int getLowPower() {
                return lowPower;
            }
        }

        public static class PtzAbilityBean implements Serializable {
            private static final long serialVersionUID = -71553500445170084L;
            /**
             * direction : 1
             * track : 1
             */

            private int direction;
            private int track;

            public int getDirection() {
                return direction;
            }

            public void setDirection(int direction) {
                this.direction = direction;
            }

            public int getTrack() {
                return track;
            }

            public void setTrack(int track) {
                this.track = track;
            }
        }

        public static class CloudStorageAbilityBean implements Serializable {
            private static final long serialVersionUID = -7151647876499718999L;
            /**
             * eventStorage : 1
             * h24recordStorage : 1
             */

            private int eventStorage;
            private int h24recordStorage;

            public int getEventStorage() {
                return eventStorage;
            }

            public void setEventStorage(int eventStorage) {
                this.eventStorage = eventStorage;
            }

            public int getH24recordStorage() {
                return h24recordStorage;
            }

            public void setH24recordStorage(int h24recordStorage) {
                this.h24recordStorage = h24recordStorage;
            }
        }

        public static class FourgAbilityBean implements Serializable {
            private static final long serialVersionUID = -5776892572925917018L;
            /**
             * fourgEnable : 1
             */

            private int fourgEnable;

            public int getFourgEnable() {
                return fourgEnable;
            }

            public void setFourgEnable(int fourgEnable) {
                this.fourgEnable = fourgEnable;
            }
        }

        public static class DistributionNetworkAbilityBean implements Serializable {
            private static final long serialVersionUID = 8790887884519602653L;
            /**
             * configNetType : 1
             */

            private int configNetType;

            public int getConfigNetType() {
                return configNetType;
            }

            public void setConfigNetType(int configNetType) {
                this.configNetType = configNetType;
            }
        }

        public static class ConnectNetworkAbilityBean implements Serializable {
            private static final long serialVersionUID = 7619350375164775327L;
            /**
             * connectNetType : 1
             */

            private int connectNetType;

            public int getConnectNetType() {
                return connectNetType;
            }

            public void setConnectNetType(int connectNetType) {
                this.connectNetType = connectNetType;
            }
        }
    }

    public static class BatteryBean implements Serializable {
        private static final long serialVersionUID = -7218396774220060639L;
        /**
         * percent : 80
         * charging : true
         */

        private int percent;
        private boolean charging;

        public int getPercent() {
            return percent;
        }

        public void setPercent(int percent) {
            this.percent = percent;
        }

        public boolean isCharging() {
            return charging;
        }

        public void setCharging(boolean charging) {
            this.charging = charging;
        }
    }
}

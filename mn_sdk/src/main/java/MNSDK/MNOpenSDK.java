package MNSDK;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.mn.okhttp3.OkHttpUtils;
import com.mn.okhttp3.https.HttpsUtils;
import com.mn.okhttp3.log.LoggerInterceptor;
import com.mn.tools.AuthorityManager;
import com.mn.tools.BaseSetting;
import com.mn.bean.setting.AlarmAsossiatedBean;
import com.mn.bean.setting.AlarmCloudRecordBean;
import com.mn.bean.setting.AlarmCloudRecordSetBean;
import com.mn.bean.setting.AlarmTimeRecordBean;
import com.mn.bean.setting.AlarmTimeRecordNvrBean;
import com.mn.bean.setting.AudioOutputBean;
import com.mn.bean.setting.AudioOutputNvrBean;
import com.mn.bean.setting.AudioOutputSetBean;
import com.mn.bean.setting.BaseResult;
import com.mn.bean.setting.CoverBean;
import com.mn.bean.setting.DevSetMoreBaseBean;
import com.mn.bean.setting.DevSoundBean;
import com.mn.bean.setting.DevStandardBean;
import com.mn.bean.setting.FaceBean;
import com.mn.bean.setting.FaceDetectBean;
import com.mn.bean.setting.FaceDetectNvrBean;
import com.mn.bean.setting.LocationMobileBean;
import com.mn.bean.setting.MBatteryBean;
import com.mn.bean.setting.LanguageBean;
import com.mn.bean.setting.BLCConfigBean;
import com.mn.bean.setting.LocalesConfigBean;
import com.mn.bean.setting.MotionDetect;
import com.mn.bean.setting.MotionDetectBean;
import com.mn.bean.setting.MotionDetectNvrBean;
import com.mn.bean.setting.NVRIPCInfoBean;
import com.mn.bean.setting.NetLightCallBean;
import com.mn.bean.setting.SetAlarmRecordBean;
import com.mn.bean.setting.TFStateBean;
import com.mn.bean.setting.TFStateConfigBean;
import com.mn.bean.setting.TimeZoneBean;
import com.mn.bean.setting.UpgradeStateBean;
import com.mn.bean.setting.VideoInOptBean;
import com.mn.bean.setting.VideoOptionsBean;
import com.mn.bean.setting.VideoOptionsNvrBean;
import com.mn.bean.setting.DevBaseInfoBean;
import com.mn.tools.LocalDataUtils;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import MNSDK.inface.MNOpenSDKInterface;
import okhttp3.OkHttpClient;

import static MNSDK.MNJni.MNDeviceLinkStatusCode.MNDEVICE_LINK_STATUS_CODE_t.MNP2P_SESSION_STATUS_FAILED;
import static MNSDK.MNJni.MNDeviceLinkStatusCode.MNDEVICE_LINK_STATUS_CODE_t.MNP2P_SESSION_STATUS_UNEXIST;

/**
 * Created by Administrator on 2020/1/2 0002.
 */

public class MNOpenSDK extends BaseSetting {
    public static String TAG = MNOpenSDK.class.getSimpleName();
    public static String errMsg = "appkey ,appSecret or domain is not set, please see the MNOpenSDK.initWithKeyAndSecret(Context,String,String) method";
    public static Context mContext;


    private MNOpenSDK() {
    }

    /**
     * 获取SDK版本信息 （get the SDK Version）
     *
     * @return 返回版本信息（Version infomation）
     */
    public static String getSDKVersion() {
        return MNJni.GetVersion();
    }

    /**
     * 初始化SDK（Initialize the SDK）
     *
     * @param context   Context
     * @param appKey    应用分配的appKey
     * @param appSecret 应用分配的appSecret
     */
    public static void initWithKeyAndSecret(Context context, String appKey, String appSecret) {
        mContext = context;

        try {
            SharedPreferences preferences = MNOpenSDK.mContext.getSharedPreferences(TAG, MNOpenSDK.mContext.MODE_PRIVATE);
            SharedPreferences.Editor edit = preferences.edit();
            edit.putString("mn_appKey", appKey);
            edit.putString("mn_appSecret", appSecret);
            edit.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        MNJni.Init();

        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30000L, TimeUnit.MILLISECONDS)
                .readTimeout(30000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor("TAG"))
                .hostnameVerifier((hostname, session) -> true)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    /**
     * 获取AppKey （Get AppSecret）
     *
     * @return AppSecret
     */
    public static String getAppKey() {
        String tem = "";
        if (MNOpenSDK.mContext == null) {
            Log.e("BaseHelper", errMsg);
            return "";
        }
        try {
            SharedPreferences preferences = MNOpenSDK.mContext.getSharedPreferences(TAG, MNOpenSDK.mContext.MODE_PRIVATE);
            tem = preferences.getString("mn_appKey", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tem;
    }

    /**
     * 获取AppSecret（ Get AppSecret）
     *
     * @return AppSecret
     */
    public static String getAppSecret() {
        String tem = "";
        if (MNOpenSDK.mContext == null) {
            Log.e("BaseHelper", errMsg);
            return "";
        }
        try {
            SharedPreferences preferences = MNOpenSDK.mContext.getSharedPreferences(TAG, MNOpenSDK.mContext.MODE_PRIVATE);
            tem = preferences.getString("mn_appSecret", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tem;
    }

    /**
     * 设置域名（Modify the Domain for test）
     */
    public static void setMNKitDomain(Context context, String domain) {
        mContext = context;
        try {
            SharedPreferences preferences = MNOpenSDK.mContext.getSharedPreferences(TAG, MNOpenSDK.mContext.MODE_PRIVATE);
            SharedPreferences.Editor edit = preferences.edit();
            edit.putString("mn_domain", domain);
            edit.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置是否提前建立P2p连接关系，设备会自动切换到APP所在域名（Set whether to establish the connection between the APP and the device in advance, the device will automatically switch to the domain name where the APP is located）
     *
     * @param state 是否提前建立与设备的连接关系(Whether to establish a connection relationship with the device in advance)
     */
    public static void setP2pPreLinkState(Context context, boolean state) {
        mContext = context;
        if (MNOpenSDK.mContext == null) {
            Log.e("BaseHelper", errMsg);
            return;
        }
        try {
            SharedPreferences preferences = MNOpenSDK.mContext.getSharedPreferences(TAG, MNOpenSDK.mContext.MODE_PRIVATE);
            SharedPreferences.Editor edit = preferences.edit();
            edit.putBoolean("mn_P2pPreLink", state);
            edit.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前是否提前链接P2p的状态;
     *
     * @return boolean
     */
    public static boolean getP2pPreLinkState() {
        if (MNOpenSDK.mContext == null) {
            Log.e("BaseHelper", errMsg);
            return false;
        }
        try {
            SharedPreferences preferences = MNOpenSDK.mContext.getSharedPreferences(TAG, MNOpenSDK.mContext.MODE_PRIVATE);
            return preferences.getBoolean("mn_P2pPreLink", false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取域名（Get domain name）
     *
     * @return 域名（https://restcn.bullyun.com）
     */
    public static String getDomain() {
        String tem = "";
        if (MNOpenSDK.mContext == null) {
            Log.e("BaseHelper", errMsg);
            return "";
        }
        try {
            SharedPreferences preferences = MNOpenSDK.mContext.getSharedPreferences(TAG, MNOpenSDK.mContext.MODE_PRIVATE);
            tem = preferences.getString("mn_domain", "https://restcn.bullyun.com");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tem;
    }

    /**
     * 获取AccessToken（Get AccessToken）
     *
     * @return accessToken
     */
    public static String getAccessToken() {
        String tem = "";
        if (MNOpenSDK.mContext == null) {
            Log.e("BaseHelper", errMsg);
            return "";
        }
        try {
            SharedPreferences preferences = MNOpenSDK.mContext.getSharedPreferences(TAG, MNOpenSDK.mContext.MODE_PRIVATE);
            tem = preferences.getString("mn_AccessToken", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tem;
    }

    /**
     * 注销SDK （Cancellation the SDK）
     */
    public static void uninitSDK() {
        threadPool.execute(() -> {
            LocalDataUtils.setUseId("");
            LocalDataUtils.setUserName("");
            LocalDataUtils.setPassword("");
            MNJni.UnInit();
        });
    }

    /**
     * 退出SDK（Logout SDK）
     */
    public static void logout() {
        threadPool.execute(() -> {
            for (String sn : linkedSns) {
                MNJni.DestroyLink(sn);
            }
            MNJni.Logout();
        });
    }

    /**
     * 与设备建立连接，建立连接成功之后才能打开视频，所以提前建立连接，可以加快打开视频的速度
     * (Establish a connection with the device, and then open the video after the connection is established, so establishing a connection in advance can speed up the opening of the video.)
     *
     * @param sn         Device sn
     * @param isShareDev 是否是分享设备，true:分享设备不会同步切换设备所在域名。false：自己账号下的设备，会同步切换设备域名(Whether it is a sharing device, true: the sharing device will not switch the domain name of the device. false: Devices under your account will switch device domain names simultaneously)
     * @return
     */
    public static void linkToDevice(String sn, boolean isShareDev) {
        threadPool.execute(() -> {
            if (MNJni.GetDeviceLinkStatus(sn) == MNP2P_SESSION_STATUS_UNEXIST.ordinal()) {
                MNJni.LinkToDevice(sn, isShareDev); //链接⾄设备
            } else if (MNJni.GetDeviceLinkStatus(sn) == MNP2P_SESSION_STATUS_FAILED.ordinal()) {
                MNJni.DestroyLink(sn);
                MNJni.LinkToDevice(sn, isShareDev); //链接⾄设备
            }

            if (!linkedSns.contains(sn)) {
                linkedSns.add(sn);
            }
        });
    }

    /**
     * 获取设备本地卡录像信息(Get device local card recording information)
     *
     * @param sn           Device sn
     * @param channelId    设备通道号(Device channel number)
     * @param pszStartTime 查询起始时间（Query start time） 2019-12-11 00:00:00
     * @param pszEndTime   查询结束时间 （Query end time）2019-12-11 23:59:59
     * @param callBack     Callback method
     */
    public static void getDeviceLocalVideos(String sn, int channelId, String pszStartTime, String pszEndTime, MNOpenSDKInterface.DeviceLocalVideosCallBack callBack) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                if (!AuthorityManager.isHadLocalVideoAuthority(sn)) {
                    String No_Permission = "{\"found\":0,\"code\":5005,\"msg\":\"Restricted permission\"}";
                    callBack.onDeviceLocalVideos(No_Permission);
                    return;
                }
                String data = MNJni.QueryMicroSDCardAlarms(sn, channelId, 0, 0, pszStartTime, pszEndTime);
                if (callBack != null) {
                    callBack.onDeviceLocalVideos(data);
                }
            }
        });
    }

    /**
     * 获取设备基本信息（新协议多了TF卡名称数组和数量）(Obtain basic device information (the new protocol adds an array and number of TF card names))
     *
     * @param sn       Device sn
     * @param callback Callback method
     */
    public static void getDeviceBaseInfo(String sn, MNOpenSDKInterface.GetDeviceBaseInfoCallBack callback) {
        threadPool.execute(() -> {
            try {
                String optionsResult = MNJni.GetDeviceBaseInfo(sn, 12);
                if (!TextUtils.isEmpty(optionsResult)) {
                    if (callback != null && mainHandler != null) {
                        DevBaseInfoBean mDevBaseInfoBean = new Gson().fromJson(optionsResult, DevBaseInfoBean.class);
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onGetDeviceBaseInfo(mDevBaseInfoBean);
                            }
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 获取电池设备的电量(Get battery power)
     *
     * @param sn       Device sn
     * @param callback Callback method
     */
    public static void getPowerState(String sn, MNOpenSDKInterface.GetPowerStateCallBack callback) {
        threadPool.execute(() -> {
            try {
                String optionsResult = MNJni.GetPowerState(sn, SDK_TIMEOUT);
                if (!TextUtils.isEmpty(optionsResult)) {
                    MBatteryBean finalOptionsBean = new Gson().fromJson(optionsResult, MBatteryBean.class);
                    if (callback != null && mainHandler != null) {
                        mainHandler.post(() -> {
                            callback.onGetPowerState(finalOptionsBean);
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 获取设备语言信息(Get device language information)
     *
     * @param sn       Device sn
     * @param callback Callback method
     */
    public static void getLanguageConfig(String sn, MNOpenSDKInterface.LanguageConfigCallBack callback) {
        threadPool.execute(() -> {
            try {
                String getConfig = "{\"method\":\"getConfig\"}";
                String optionsResult = MNJni.RequestLanguageConfig(sn, getConfig, SDK_TIMEOUT);
                if (!TextUtils.isEmpty(optionsResult)) {
                    if (callback != null && mainHandler != null) {
                        LanguageBean optionsBean = new Gson().fromJson(optionsResult.trim(), LanguageBean.class);
                        mainHandler.post(() -> {
                            callback.onLanguageConfig(optionsBean);
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 为设备设置语言（当设备支持语言切换时有效）(Set the language for the device (effective when the device supports language switching))
     *
     * @param sn       Device sn
     * @param language 语言(language)
     * @param callback Callback method
     */
    public static void setLanguageConfig(String sn, String language, MNOpenSDKInterface.SetLanguageConfigCallBack callback) {
        threadPool.execute(() -> {
            try {
                if (!AuthorityManager.isHadDeviceConfigAuthority(sn)) {
                    if (callback != null && mainHandler != null) {
                        BaseResult baseResult = new BaseResult();
                        baseResult.setResult(false);
                        baseResult.setCode(5005);
                        baseResult.setMsg("Restricted permission");
                        mainHandler.post(()->{
                            callback.onSetLanguageConfig(baseResult);
                        });
                    }
                    return;
                }

                BaseResult optionsBean = null;
                String getConfig = "{\"method\":\"setConfig\",\"params\":{\"Language\":\"" + language + "\"}}";
                String optionsResult = MNJni.RequestLanguageConfig(sn, getConfig, SDK_TIMEOUT);
                if (!TextUtils.isEmpty(optionsResult)) {
                    optionsBean = new Gson().fromJson(optionsResult.trim(), BaseResult.class);
                }
                BaseResult finalOptionsBean = optionsBean;
                if (callback != null && mainHandler != null) {
                    mainHandler.post(() -> {
                        callback.onSetLanguageConfig(finalOptionsBean);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 获取设备视频制式(Get device video format)
     *
     * @param sn       Device sn
     * @param callback Callback method
     */
    public static void getVideoStandard(String sn, MNOpenSDKInterface.VideoStandardConfigCallBack callback) {
        threadPool.execute(() -> {
            try {
                DevStandardBean optionsBean = null;
                String optionsResult = MNJni.GetVideoStandard(sn, SDK_TIMEOUT);
                if (!TextUtils.isEmpty(optionsResult)) {
                    optionsBean = new Gson().fromJson(optionsResult.trim(), DevStandardBean.class);
                }
                DevStandardBean finalOptionsBean = optionsBean;
                if (callback != null && mainHandler != null) {
                    mainHandler.post(() -> {
                        callback.onVideoStandardConfig(finalOptionsBean);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 获取设备音量配置(Get device volume configuration)
     *
     * @param sn       Device sn
     * @param callback Callback method
     */
    public static void getAudioOutputVolume(String sn, MNOpenSDKInterface.GetAudioOutputCallBack callback) {
        threadPool.execute(() -> {
            try {
                AudioOutputBean audioOptions = null;
                String getConfig = "{\"method\":\"getConfig\"}";
                String optionsResult = MNJni.RequestAudioOutputVolume(sn, getConfig, SDK_TIMEOUT);
                if (!TextUtils.isEmpty(optionsResult)) {
                    audioOptions = new Gson().fromJson(optionsResult, AudioOutputBean.class);
                }
                AudioOutputBean finalAudioOptions = audioOptions;
                if (callback != null && mainHandler != null) {
                    mainHandler.post(() -> {
                        callback.onGetAudioOutput(finalAudioOptions);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 设置设备音量配置(Set device volume configuration)
     *
     * @param sn       Device sn
     * @param aovBean  AudioOutputSetBean channel:通道号，单通道设备默认写0，多通道设备默认从0开始(Channel number, single-channel device writes 0 by default, multi-channel devices start from 0 by default)；AudioOutputVolume 音频输出值(Audio output value)（0--100）
     * @param callback Callback method
     */
    public static void setAudioOutputVolume(String sn, AudioOutputSetBean aovBean, MNOpenSDKInterface.SetAudioOutputCallBack callback) {
        threadPool.execute(() -> {
            try {
                if (!AuthorityManager.isHadDeviceConfigAuthority(sn)) {
                    if (callback != null && mainHandler != null) {
                        BaseResult baseResult = new BaseResult();
                        baseResult.setResult(false);
                        baseResult.setCode(5005);
                        baseResult.setMsg("Restricted permission");
                        mainHandler.post(()->{
                            callback.onSetAudioOutput(baseResult);
                        });
                    }
                    return;
                }

                BaseResult audioOptions = null;
                String setConfig = "{\"method\":\"setConfig\",\"params\":" + new Gson().toJson(aovBean) + "}";
                String optionsResult = MNJni.RequestAudioOutputVolume(sn, setConfig, SDK_TIMEOUT);
                if (!TextUtils.isEmpty(optionsResult)) {
                    audioOptions = new Gson().fromJson(optionsResult, BaseResult.class);
                }
                BaseResult finalAudioOptions = audioOptions;
                if (callback != null && mainHandler != null) {
                    mainHandler.post(() -> {
                        callback.onSetAudioOutput(finalAudioOptions);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 获取NVR设备音量配置(Get NVR device volume configuration)
     *
     * @param sn       Device sn
     * @param channels 通道号
     * @param callback Callback method
     */
    public static void getNvrAudioOutputVolume(String sn, int[] channels, MNOpenSDKInterface.GetNvrAudioOutputCallBack callback) {
        threadPool.execute(() -> {
            try {
                AudioOutputNvrBean audioOptions = null;
                String getConfig = "{\"method\":\"getConfig\",\"channel\":" + arrayInt2String(channels) + "}";
                String videoOptionsResult = MNJni.RequestAudioOutputVolume(sn, getConfig, SDK_TIMEOUT);
                if (!TextUtils.isEmpty(videoOptionsResult)) {
                    audioOptions = new Gson().fromJson(videoOptionsResult, AudioOutputNvrBean.class);
                }
                AudioOutputNvrBean finalAudioOptions = audioOptions;
                if (callback != null && mainHandler != null) {
                    mainHandler.post(() -> {
                        callback.onGetNvrAudioOutput(finalAudioOptions);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 设置NVR设备音量配置(Set NVR device volume configuration)
     *
     * @param sn       Device sn
     * @param aovBeans 音量配置信息(Volume configuration information)
     * @param callback Callback method
     */
    public static void setNvrAudioOutputVolume(String sn, ArrayList<AudioOutputSetBean> aovBeans, MNOpenSDKInterface.SetNvrAudioOutputCallBack callback) {
        threadPool.execute(() -> {
            try {
                if (!AuthorityManager.isHadDeviceConfigAuthority(sn)) {
                    if (callback != null && mainHandler != null) {
                        DevSetMoreBaseBean baseResult = new DevSetMoreBaseBean();
                        baseResult.setResult(false);
                        baseResult.setCode(5005);
                        baseResult.setMsg("Restricted permission");
                        mainHandler.post(()->{
                            callback.onSetNvrAudioOutput(baseResult);
                        });
                    }
                    return;
                }

                DevSetMoreBaseBean audioOptions = null;
                String setConfig = "{\"method\":\"setConfig\",\"params\":" + new Gson().toJson(aovBeans) + "}";
                String videoOptionsResult = MNJni.RequestAudioOutputVolume(sn, setConfig, SDK_TIMEOUT);
                if (!TextUtils.isEmpty(videoOptionsResult)) {
                    audioOptions = new Gson().fromJson(videoOptionsResult, DevSetMoreBaseBean.class);
                }
                DevSetMoreBaseBean finalAudioOptions = audioOptions;
                if (callback != null && mainHandler != null) {
                    mainHandler.post(() -> {
                        callback.onSetNvrAudioOutput(finalAudioOptions);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    /**
     * 获取设备静音和离线语音配置(Get device mute and offline voice configuration)
     *
     * @param sn       Device sn
     * @param callback Callback method
     */
    public static void getSoundModeConfig(String sn, MNOpenSDKInterface.GetSoundModeConfigCallBack callback) {
        threadPool.execute(() -> {
            try {
                DevSoundBean soundOptions = null;
                String getConfig = "{\"method\":\"getConfig\"}";
                String optionsResult = MNJni.RequestSound(sn, getConfig, SDK_TIMEOUT);
                if (!TextUtils.isEmpty(optionsResult)) {
                    soundOptions = new Gson().fromJson(optionsResult.trim(), DevSoundBean.class);
                }
                DevSoundBean finalSoundOptions = soundOptions;
                if (callback != null && mainHandler != null) {
                    mainHandler.post(() -> {
                        callback.onGetSoundModeConfig(finalSoundOptions);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 设置设备静音和离线语音配置（Set device mute and offline voice configuration）
     *
     * @param sn          Device sn
     * @param silentMode  是否开启语言提示(Whether to enable the language prompt)
     * @param voiceEnable 是否关闭离线语音提示(hether to turn off offline voice prompts)
     * @param callback    Callback method
     */
    public static void setSoundModeConfig(String sn, boolean silentMode, boolean voiceEnable, MNOpenSDKInterface.SetSoundModeConfigCallBack callback) {
        threadPool.execute(() -> {
            try {
                if (!AuthorityManager.isHadDeviceConfigAuthority(sn)) {
                    if (callback != null && mainHandler != null) {
                        BaseResult baseResult = new BaseResult();
                        baseResult.setResult(false);
                        baseResult.setCode(5005);
                        baseResult.setMsg("Restricted permission");
                        mainHandler.post(()->{
                            callback.onSetSoundModeConfig(baseResult);
                        });
                    }
                    return;
                }

                BaseResult soundOptions = null;
                String setConfig = "{\"method\":\"setConfig\",\"params\":{\"SilentMode\":" + silentMode + ",\"VoiceEnable\":" + voiceEnable + "}}";
                String optionsResult = MNJni.RequestSound(sn, setConfig, SDK_TIMEOUT);
                if (!TextUtils.isEmpty(optionsResult)) {
                    soundOptions = new Gson().fromJson(optionsResult.trim(), BaseResult.class);
                }
                BaseResult finalSoundOptions = soundOptions;
                if (callback != null && mainHandler != null) {
                    mainHandler.post(() -> {
                        callback.onSetSoundModeConfig(finalSoundOptions);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    /**
     * 获取设备时区配置（Get device time zone configuration）
     *
     * @param sn       Device sn
     * @param callback Callback method
     */
    public static void getTimeZoneConfig(String sn, MNOpenSDKInterface.GetTimeZoneConfigCallBack callback) {
        threadPool.execute(() -> {
            try {
                TimeZoneBean optionsBean = null;
                String getConfig = "{\"method\":\"getConfig\"}";
                String optionsResult = MNJni.RequestNTPConfig(sn, getConfig, SDK_TIMEOUT);
                if (!TextUtils.isEmpty(optionsResult)) {
                    optionsBean = new Gson().fromJson(optionsResult.trim(), TimeZoneBean.class);
                }
                TimeZoneBean finalOptionsBean = optionsBean;
                if (callback != null && mainHandler != null) {
                    mainHandler.post(() -> {
                        callback.onGetTimeZoneConfig(finalOptionsBean);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 设置设备时区配置（Set device time zone configuration）
     *
     * @param sn       Device sn
     * @param timeZone 时区对照id（Time zone control id）
     * @param callback Callback method
     */
    public static void setTimeZoneConfig(String sn, int timeZone, MNOpenSDKInterface.SetTimeZoneConfigCallBack callback) {
        threadPool.execute(() -> {
            try {
                if (!AuthorityManager.isHadDeviceConfigAuthority(sn)) {
                    if (callback != null && mainHandler != null) {
                        BaseResult baseResult = new BaseResult();
                        baseResult.setResult(false);
                        baseResult.setCode(5005);
                        baseResult.setMsg("Restricted permission");
                        mainHandler.post(()->{
                            callback.onSetTimeZoneConfig(baseResult);
                        });
                    }
                    return;
                }

                BaseResult optionsBean = null;
                String getConfig = "{\"method\":\"setConfig\",\"params\":{\"TimeZone\":" + timeZone + "}}";
                String optionsResult = MNJni.RequestNTPConfig(sn, getConfig, SDK_TIMEOUT);
                if (!TextUtils.isEmpty(optionsResult)) {
                    optionsBean = new Gson().fromJson(optionsResult.trim(), BaseResult.class);
                }
                BaseResult finalOptionsBean = optionsBean;
                if (callback != null && mainHandler != null) {
                    mainHandler.post(() -> {
                        callback.onSetTimeZoneConfig(finalOptionsBean);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 获取设备夏令时配置（Get device daylight saving time configuration）
     *
     * @param sn       Device sn
     * @param callback Callback method
     */
    public static void getSummerTimeConfig(String sn, MNOpenSDKInterface.GetSummerTimeConfigCallBack callback) {
        threadPool.execute(() -> {
            try {
                LocalesConfigBean optionsBean = null;
                String getConfig = "{\"method\":\"getConfig\"}";
                String optionsResult = MNJni.RequestLocalesConfig(sn, getConfig, SDK_TIMEOUT);
                if (!TextUtils.isEmpty(optionsResult)) {
                    optionsBean = new Gson().fromJson(optionsResult.trim(), LocalesConfigBean.class);
                }
                LocalesConfigBean finalOptionsBean = optionsBean;
                if (callback != null && mainHandler != null) {
                    mainHandler.post(() -> {
                        callback.onGetSummerTimeConfig(finalOptionsBean);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 设置设备夏令时配置（Set device daylight saving time configuration）
     *
     * @param sn        Device sn
     * @param DSTEnable 是否开启夏令时（Whether to enable daylight saving time）
     * @param callback  Callback method
     */
    public static void setSummerTimeConfig(String sn, boolean DSTEnable, MNOpenSDKInterface.SetSummerTimeConfigCallBack callback) {
        threadPool.execute(() -> {
            try {
                if (!AuthorityManager.isHadDeviceConfigAuthority(sn)) {
                    if (callback != null && mainHandler != null) {
                        BaseResult baseResult = new BaseResult();
                        baseResult.setResult(false);
                        baseResult.setCode(5005);
                        baseResult.setMsg("Restricted permission");
                        mainHandler.post(()->{
                            callback.onSetSummerTimeConfig(baseResult);
                        });
                    }
                    return;
                }

                BaseResult optionsBean = null;
                String getConfig = "{\"method\":\"setConfig\",\"params\":{\"DSTEnable\":" + DSTEnable + "}}";
                String optionsResult = MNJni.RequestLocalesConfig(sn, getConfig, SDK_TIMEOUT);
                if (!TextUtils.isEmpty(optionsResult)) {
                    optionsBean = new Gson().fromJson(optionsResult.trim(), BaseResult.class);
                }
                BaseResult finalOptionsBean = optionsBean;
                if (callback != null && mainHandler != null) {
                    mainHandler.post(() -> {
                        callback.onSetSummerTimeConfig(finalOptionsBean);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 获取设备呼吸灯配置（Get device breathing light configuration）
     *
     * @param sn       Device sn
     * @param callback Callback method
     */
    public static void getNetLightConfig(String sn, MNOpenSDKInterface.GetNetLightCallBack callback) {
        threadPool.execute(() -> {
            try {
                NetLightCallBean mNetLightCallBean = null;
                String getConfig = "{\"method\":\"getConfig\"}";
                String optionsResult = MNJni.RequestNetLight(sn, getConfig, SDK_TIMEOUT);
                if (!TextUtils.isEmpty(optionsResult)) {
                    mNetLightCallBean = new Gson().fromJson(optionsResult, NetLightCallBean.class);
                }
                if (callback != null && mainHandler != null) {
                    NetLightCallBean finalMNetLightCallBean = mNetLightCallBean;
                    mainHandler.post(() -> {
                        callback.onGetNetLight(finalMNetLightCallBean);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 设置设备呼吸灯配置（Set device breathing light configuration）
     *
     * @param sn       Device sn
     * @param netLight 是否开启呼吸灯（Whether to turn on the breathing light）
     * @param callback Callback method
     */
    public static void setNetLightConfig(String sn, boolean netLight, MNOpenSDKInterface.SetNetLightCallBack callback) {
        threadPool.execute(() -> {
            try {
                if (!AuthorityManager.isHadDeviceConfigAuthority(sn)) {
                    if (callback != null && mainHandler != null) {
                        BaseResult baseResult = new BaseResult();
                        baseResult.setResult(false);
                        baseResult.setCode(5005);
                        baseResult.setMsg("Restricted permission");
                        mainHandler.post(()->{
                            callback.onSetNetLight(baseResult);
                        });
                    }
                    return;
                }

                String getConfig = "{\"method\":\"setConfig\",\"params\":{\"NetLight\" :" + netLight + "}}";
                String optionsResult = MNJni.RequestNetLight(sn, getConfig, SDK_TIMEOUT);
                if (!TextUtils.isEmpty(optionsResult)) {
                    if (callback != null && mainHandler != null) {
                        BaseResult options = new Gson().fromJson(optionsResult, BaseResult.class);
                        mainHandler.post(() -> {
                            callback.onSetNetLight(options);
                        });
                    }
                } else {
                    if (callback != null && mainHandler != null) {
                        mainHandler.post(() -> {
                            callback.onSetNetLight(null);
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 获取背光补偿配置(Get gun camera backlight compensation configuration)
     *
     * @param sn       Device sn
     * @param callback Callback method
     */
    public static void getBLCConfig(String sn, MNOpenSDKInterface.GetBLCConfigCallBack callback) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String getConfig = "{\"method\":\"getConfig\"}";
                    String optionsResult = MNJni.RequestLightCompensationConfig(sn, getConfig, 10);
                    if (!TextUtils.isEmpty(optionsResult)) {
                        if (callback != null && mainHandler != null) {
                            BLCConfigBean options = new Gson().fromJson(optionsResult, BLCConfigBean.class);
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onGetBLCConfig(options);
                                }
                            });
                        }
                    } else {
                        if (callback != null && mainHandler != null) {
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onGetBLCConfig(null);
                                }
                            });
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 设置背光补偿开启与关闭状态(Setting backlight compensation on and off)
     *
     * @param sn             Device sn
     * @param LightEnable    是否开启背光补偿（Whether to enable backlight compensation）
     * @param LightSensitive 光敏值（Photosensitivity）
     * @param callback       Callback method
     */
    public static void setBLCConfig(String sn, boolean LightEnable, int LightSensitive, MNOpenSDKInterface.SetBLCConfigCallBack callback) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (!AuthorityManager.isHadDeviceConfigAuthority(sn)) {
                        if (callback != null && mainHandler != null) {
                            BaseResult baseResult = new BaseResult();
                            baseResult.setResult(false);
                            baseResult.setCode(5005);
                            baseResult.setMsg("Restricted permission");
                            mainHandler.post(()->{
                                callback.onSetBLCConfig(baseResult);
                            });
                        }
                        return;
                    }

                    String serConfig = "{\"method\":\"setConfig\",\"params\":{\"LightEnable\":" + LightEnable + ",\"LightSensitive\":" + LightSensitive + "}}";
                    String optionsResult = MNJni.RequestLightCompensationConfig(sn, serConfig, 10);
                    if (!TextUtils.isEmpty(optionsResult)) {
                        if (callback != null && mainHandler != null) {
                            BaseResult lightCompensationBean = new Gson().fromJson(optionsResult, BaseResult.class);
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onSetBLCConfig(lightCompensationBean);
                                }
                            });
                        }
                    } else {
                        if (callback != null && mainHandler != null) {
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onSetBLCConfig(null);
                                }
                            });
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 获取设备红外灯、镜像、翻转配置(Get device infrared light, mirror, flip configuration)
     *
     * @param sn       Device sn
     * @param callback Callback method
     */
    public static void getVideoInOptions(String sn, MNOpenSDKInterface.GetVideoInOptionsCallBack callback) {
        threadPool.execute(() -> {
            try {
                String getConfig = "{\"method\":\"getConfig\"}";
                String optionsResult = MNJni.RequestVideoInOptions(sn, getConfig, SDK_TIMEOUT);
                if (!TextUtils.isEmpty(optionsResult)) {
                    if (callback != null && mainHandler != null) {
                        VideoOptionsBean options = new Gson().fromJson(optionsResult.trim(), VideoOptionsBean.class);
                        mainHandler.post(() -> {
                            callback.onGetVideoInOptions(options);
                        });
                    }
                } else {
                    if (callback != null && mainHandler != null) {
                        mainHandler.post(() -> {
                            callback.onGetVideoInOptions(null);
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    /**
     * 设置设备红外灯、镜像、翻转配置(Set device infrared light, mirror, flip configuration)
     *
     * @param sn        Device sn
     * @param inOptBean 配置信息(Configuration information)
     * @param callback  Callback method
     */
    public static void setVideoInOptions(String sn, VideoInOptBean inOptBean, MNOpenSDKInterface.SetVideoInOptionsCallBack callback) {
        threadPool.execute(() -> {
            try {
                if (!AuthorityManager.isHadDeviceConfigAuthority(sn)) {
                    if (callback != null && mainHandler != null) {
                        BaseResult baseResult = new BaseResult();
                        baseResult.setResult(false);
                        baseResult.setCode(5005);
                        baseResult.setMsg("Restricted permission");
                        mainHandler.post(()->{
                            callback.onSetVideoInOptions(baseResult);
                        });
                    }
                    return;
                }


                String setConfig = "{\"method\":\"setConfig\",\"params\":" + new Gson().toJson(inOptBean) + "}";
                String optionsResult = MNJni.RequestVideoInOptions(sn, setConfig, SDK_TIMEOUT);
                if (!TextUtils.isEmpty(optionsResult)) {
                    if (callback != null && mainHandler != null) {
                        BaseResult optionsBean = new Gson().fromJson(optionsResult.trim(), BaseResult.class);
                        mainHandler.post(() -> {
                            callback.onSetVideoInOptions(optionsBean);
                        });
                    }
                } else {
                    if (callback != null && mainHandler != null) {
                        mainHandler.post(() -> {
                            callback.onSetVideoInOptions(null);
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    /**
     * 获取NVR对应通道设备红外灯、镜像、翻转配置(Get NVR corresponding channel device infrared light, mirror, flip configuration)
     *
     * @param sn       Device sn
     * @param channels 通道号(Channel number)
     * @param callback Callback method
     */
    public static void getNvrVideoInOptions(String sn, int[] channels, MNOpenSDKInterface.GetNvrVideoInOptionsCallBack callback) {
        threadPool.execute(() -> {
            try {
                String getConfig = "{\"method\":\"getConfig\",\"channel\":" + arrayInt2String(channels) + "}";
                String videoOptionsResult = MNJni.RequestVideoInOptions(sn, getConfig, SDK_TIMEOUT);
                if (!TextUtils.isEmpty(videoOptionsResult)) {
                    if (callback != null && mainHandler != null) {
                        VideoOptionsNvrBean optionsBean = new Gson().fromJson(videoOptionsResult.trim(), VideoOptionsNvrBean.class);
                        mainHandler.post(() -> {
                            callback.onGetNvrVideoInOptions(optionsBean);
                        });
                    }
                } else {
                    if (callback != null && mainHandler != null) {
                        mainHandler.post(() -> {
                            callback.onGetNvrVideoInOptions(null);
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 设置NVR对应通道设备设备红外灯、镜像、翻转配置(Set NVR corresponding channel device equipment infrared light, mirror, flip configuration)
     *
     * @param sn         Device sn
     * @param inOptBeans 配置信息(Configuration information)
     * @param callback   Callback method
     */
    public static void setNvrVideoInOptions(String sn, ArrayList<VideoInOptBean> inOptBeans, MNOpenSDKInterface.SetNvrVideoInOptionsCallBack callback) {
        threadPool.execute(() -> {
            try {
                if (!AuthorityManager.isHadDeviceConfigAuthority(sn)) {
                    if (callback != null && mainHandler != null) {
                        DevSetMoreBaseBean baseResult = new DevSetMoreBaseBean();
                        baseResult.setResult(false);
                        baseResult.setCode(5005);
                        baseResult.setMsg("Restricted permission");
                        mainHandler.post(()->{
                            callback.onSetNvrVideoInOptions(baseResult);
                        });
                    }
                    return;
                }

                String setConfig = "{\"method\":\"setConfig\",\"params\":" + new Gson().toJson(inOptBeans) + "}";
                String videoOptionsResult = MNJni.RequestVideoInOptions(sn, setConfig, SDK_TIMEOUT);
                if (!TextUtils.isEmpty(videoOptionsResult)) {
                    if (callback != null && mainHandler != null) {
                        DevSetMoreBaseBean finalVideoOptions = new Gson().fromJson(videoOptionsResult.trim(), DevSetMoreBaseBean.class);
                        mainHandler.post(() -> {
                            callback.onSetNvrVideoInOptions(finalVideoOptions);
                        });
                    }
                } else {
                    if (callback != null && mainHandler != null) {
                        mainHandler.post(() -> {
                            callback.onSetNvrVideoInOptions(null);
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 获取声光模式设置(Get sound and light mode settings)
     *
     * @param sn       Device sn
     * @param callback Callback method
     */
    public static void getAlarmAsossiatedConfig(String sn, MNOpenSDKInterface.GetAlarmAsossiatedConfigCallBack callback) {
        try {
            threadPool.execute(() -> {
                String getConfig = "{\"method\":\"getConfig\"}";
                String optionsResult = MNJni.RequestAlarmAsossiatedConfig(sn, getConfig, 10);
                if (!TextUtils.isEmpty(optionsResult)) {
                    if (mainHandler != null && callback != null) {
                        AlarmAsossiatedBean alartBean = new Gson().fromJson(optionsResult, AlarmAsossiatedBean.class);
                        mainHandler.post(() -> {
                            callback.onGetAlarmAsossiatedConfig(alartBean);
                        });
                    }
                } else {
                    if (mainHandler != null && callback != null) {
                        mainHandler.post(() -> {
                            callback.onGetAlarmAsossiatedConfig(null);
                        });
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置声光模式(Set acousto-optic mode)
     *
     * @param sn          Device sn
     * @param LightType   白光灯是否开启(Whether the white light is on)
     * @param audioEnable 警示音是否开启(Whether the warning sound is on)
     * @param callback    Callback method
     */
    public static void setAlarmAsossiatedConfig(String sn, int LightType, boolean audioEnable, MNOpenSDKInterface.SetAlarmAsossiatedConfigCallBack callback) {
        try {
            if (!AuthorityManager.isHadDeviceConfigAuthority(sn)) {
                if (callback != null && mainHandler != null) {
                    BaseResult baseResult = new BaseResult();
                    baseResult.setResult(false);
                    baseResult.setCode(5005);
                    baseResult.setMsg("Restricted permission");
                    mainHandler.post(()->{
                        callback.onSetAlarmAsossiatedConfig(baseResult);
                    });
                }
                return;
            }

            threadPool.execute(() -> {
                String getConfig = "{\"method\":\"setConfig\",\"params\":{\"LightType\":" + LightType + ",\"LightSeconds\":60" + ",\"AudioEnable\":" + audioEnable + "}}";
                String optionsResult = MNJni.RequestAlarmAsossiatedConfig(sn, getConfig, 10);
                if (!TextUtils.isEmpty(optionsResult)) {
                    if (mainHandler != null && callback != null) {
                        BaseResult alartBean = new Gson().fromJson(optionsResult, BaseResult.class);
                        mainHandler.post(() -> {
                            callback.onSetAlarmAsossiatedConfig(alartBean);
                        });
                    }
                } else {
                    if (mainHandler != null && callback != null) {
                        mainHandler.post(() -> {
                            callback.onSetAlarmAsossiatedConfig(null);
                        });
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取TF卡名称、是否需要格式化、总容量、剩余容量(Get TF card name, whether formatting is required, total capacity, remaining capacity)
     *
     * @param sn       Device sn
     * @param callback Callback method
     */
    public static void getTFStateConfig(String sn, MNOpenSDKInterface.GetTFStorageCallBack callback) {
        threadPool.execute(() -> {
            try {
                String optionsResult = MNJni.GetTFState(sn, 15);
                if (!TextUtils.isEmpty(optionsResult)) {
                    if (callback != null && mainHandler != null) {
                        TFStateConfigBean options = new Gson().fromJson(optionsResult.trim(), TFStateConfigBean.class);
                        mainHandler.post(() -> {
                            callback.onGetTFStorage(options);
                        });
                    }
                } else {
                    if (callback != null && mainHandler != null) {
                        mainHandler.post(() -> {
                            callback.onGetTFStorage(null);
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 获取TF卡名称、是否需要格式化(Get TF card name, whether it needs to be formatted)
     *
     * @param sn       Device sn
     * @param callback Callback method
     */
    public static void getTFSimpleState(String sn, MNOpenSDKInterface.GetTFSimpleStateCallBack callback) {
        threadPool.execute(() -> {
            try {
                String optionsResult = MNJni.GetTFSimpleState(sn, 15);
                if (!TextUtils.isEmpty(optionsResult)) {
                    if (callback != null && mainHandler != null) {
                        TFStateBean options = new Gson().fromJson(optionsResult.trim(), TFStateBean.class);
                        mainHandler.post(() -> {
                            callback.onGetTFSimpleState(options);
                        });
                    }
                } else {
                    if (callback != null && mainHandler != null) {
                        mainHandler.post(() -> {
                            callback.onGetTFSimpleState(null);
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    /**
     * 设备TF卡格式化(Device TF card format)
     *
     * @param sn       Device sn
     * @param tfName   要格式化的卡名称(Card name to format)
     * @param callback Callback method
     */
    public static void setTFStorageFormatting(String sn, String tfName, MNOpenSDKInterface.TFStorageFormatCallBack callback) {
        threadPool.execute(() -> {
            try {
                if (!AuthorityManager.isHadDeviceConfigAuthority(sn)) {
                    if (callback != null && mainHandler != null) {
                        BaseResult baseResult = new BaseResult();
                        baseResult.setResult(false);
                        baseResult.setCode(5005);
                        baseResult.setMsg("Restricted permission");
                        mainHandler.post(()->{
                            callback.onTFStorageFormat(baseResult);
                        });
                    }
                    return;
                }

                BaseResult optionBean = null;
                String pszCommand = "{\"Name\":\"" + tfName + " \",\"Part\" : " + 0 + "}";
                String optionsResult = MNJni.SetTFStorageFormatting(sn, pszCommand, 60);
                if (!TextUtils.isEmpty(optionsResult)) {
                    optionBean = new Gson().fromJson(optionsResult.trim(), BaseResult.class);
                }
                BaseResult finalOptionsBean = optionBean;
                if (callback != null && mainHandler != null) {
                    mainHandler.post(() -> {
                        callback.onTFStorageFormat(finalOptionsBean);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    /**
     * 获取设备TF卡存储类型和时间配置(Get device TF card storage type and time configuration)
     *
     * @param sn       Device sn
     * @param callback Callback method
     */
    public static void getAlarmRecord(String sn, MNOpenSDKInterface.GetAlarmRecordCallBack callback) {
        threadPool.execute(() -> {
            try {
                String getConfig = "{\"method\":\"getConfig\"}";
                String optionsResult = MNJni.RequestAlarmRecord(sn, getConfig, SDK_TIMEOUT);
                if (!TextUtils.isEmpty(optionsResult)) {
                    if (callback != null && mainHandler != null) {
                        AlarmTimeRecordBean options = new Gson().fromJson(optionsResult.trim(), AlarmTimeRecordBean.class);
                        mainHandler.post(() -> {
                            callback.onGetAlarmRecord(options);
                        });
                    }
                } else {
                    if (callback != null && mainHandler != null) {
                        mainHandler.post(() -> {
                            callback.onGetAlarmRecord(null);
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        });
    }

    /**
     * 设置设备TF卡存储类型和时间配置(Set device TF card storage type and time configuration)
     *
     * @param sn             Device sn
     * @param isAllDayRecord 是否开启全时录像(Whether to enable full-time recording)
     * @param callback       Callback method
     */
    public static void setAlarmRecord(String sn, boolean isAllDayRecord, MNOpenSDKInterface.SetAlarmRecordCallBack callback) {
        threadPool.execute(() -> {
            try {
                if (!AuthorityManager.isHadDeviceConfigAuthority(sn)) {
                    if (callback != null && mainHandler != null) {
                        BaseResult baseResult = new BaseResult();
                        baseResult.setResult(false);
                        baseResult.setCode(5005);
                        baseResult.setMsg("Restricted permission");
                        mainHandler.post(()->{
                            callback.onSetAlarmRecord(baseResult);
                        });
                    }
                    return;
                }

                String getConfig = "{\"method\":\"setConfig\",\"params\":{\"AllDayRecord\":" + isAllDayRecord + "}}";
                String optionsResult = MNJni.RequestAlarmRecord(sn, getConfig, 10);
                if (!TextUtils.isEmpty(optionsResult)) {
                    if (callback != null && mainHandler != null) {
                        BaseResult options = new Gson().fromJson(optionsResult.trim(), BaseResult.class);
                        mainHandler.post(() -> {
                            callback.onSetAlarmRecord(options);
                        });
                    }
                } else {
                    if (callback != null && mainHandler != null) {
                        mainHandler.post(() -> {
                            callback.onSetAlarmRecord(null);
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    /**
     * 获取设备TF卡存储类型和时间配置(Get device TF card storage type and time configuration)
     *
     * @param sn       Device sn
     * @param channels 通道号(Channel number)
     * @param callback Callback method
     */
    public static void getNvrAlarmRecord(String sn, int[] channels, MNOpenSDKInterface.GetNvrAlarmRecordCallBack callback) {
        threadPool.execute(() -> {
            try {
                String getConfig = "{\"method\":\"getConfig\",\"channel\":" + arrayInt2String(channels) + "}";
                String videoOptionsResult = MNJni.RequestAlarmRecord(sn, getConfig, SDK_TIMEOUT);
                if (!TextUtils.isEmpty(videoOptionsResult)) {
                    if (callback != null && mainHandler != null) {
                        AlarmTimeRecordNvrBean options = new Gson().fromJson(videoOptionsResult.trim(), AlarmTimeRecordNvrBean.class);
                        mainHandler.post(() -> {
                            callback.onGetNvrAlarmRecord(options);
                        });
                    }
                } else {
                    if (callback != null && mainHandler != null) {
                        mainHandler.post(() -> {
                            callback.onGetNvrAlarmRecord(null);
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 设置设备TF卡存储类型和时间配置(Set device TF card storage type and time configuration)
     *
     * @param sn           Device sn
     * @param alarmRecords 设置对应通道是否开启全时录像的配置(Configure whether the corresponding channel is enabled for full-time recording)
     * @param callback     Callback method
     */
    public static void setNvrAlarmRecord(String sn, ArrayList<SetAlarmRecordBean> alarmRecords, MNOpenSDKInterface.SetNvrAlarmRecordCallBack callback) {
        threadPool.execute(() -> {
            try {
                if (!AuthorityManager.isHadDeviceConfigAuthority(sn)) {
                    if (callback != null && mainHandler != null) {
                        DevSetMoreBaseBean baseResult = new DevSetMoreBaseBean();
                        baseResult.setResult(false);
                        baseResult.setCode(5005);
                        baseResult.setMsg("Restricted permission");
                        mainHandler.post(()->{
                            callback.onSetNvrAlarmRecord(baseResult);
                        });
                    }
                    return;
                }

                String getConfig = "{\"method\":\"setConfig\",\"params\":" + new Gson().toJson(alarmRecords) + "}";
                String videoOptionsResult = MNJni.RequestAlarmRecord(sn, getConfig, SDK_TIMEOUT);
                if (!TextUtils.isEmpty(videoOptionsResult)) {
                    DevSetMoreBaseBean options = new Gson().fromJson(videoOptionsResult.trim(), DevSetMoreBaseBean.class);
                    if (callback != null && mainHandler != null) {
                        mainHandler.post(() -> {
                            callback.onSetNvrAlarmRecord(options);
                        });
                    }
                } else {
                    if (callback != null && mainHandler != null) {
                        mainHandler.post(() -> {
                            callback.onSetNvrAlarmRecord(null);
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    /**
     * 获取设备动检使能和灵敏度配置(Get device dynamic detection enable and sensitivity configuration)
     *
     * @param sn       Device sn
     * @param callback Callback method
     */
    public static void getMotionDetectConfig(String sn, MNOpenSDKInterface.GetMotionDetectCallBack callback) {
        threadPool.execute(() -> {
            try {
                MotionDetectBean optionsBean = null;
                String getConfig = "{\"method\":\"getConfig\"}";
                String optionsResult = MNJni.RequestMotionDetect(sn, getConfig, SDK_TIMEOUT);
                if (!TextUtils.isEmpty(optionsResult)) {
                    optionsBean = new Gson().fromJson(optionsResult.trim(), MotionDetectBean.class);
                }
                MotionDetectBean finalOptionsBean = optionsBean;
                if (callback != null && mainHandler != null) {
                    mainHandler.post(() -> {
                        callback.onGetMotionDetect(finalOptionsBean);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 设置设备动检使能和灵敏度配置(Set device motion detection enable and sensitivity configuration)
     *
     * @param sn           Device sn
     * @param sensitivity  移动侦测灵敏度(Motion detection sensitivity)
     * @param motionDetect 移动侦测开关(Motion detection switch)
     * @param callback     Callback method
     */
    public static void setMotionDetectConfig(String sn, int sensitivity, boolean motionDetect, MNOpenSDKInterface.SetMotionDetectCallBack callback) {
        threadPool.execute(() -> {
            try {
                if (!AuthorityManager.isHadDeviceConfigAuthority(sn)) {
                    if (callback != null && mainHandler != null) {
                        BaseResult baseResult = new BaseResult();
                        baseResult.setResult(false);
                        baseResult.setCode(5005);
                        baseResult.setMsg("Restricted permission");
                        mainHandler.post(()->{
                            callback.onSetMotionDetect(baseResult);
                        });
                    }
                    return;
                }

                BaseResult optionsBean = null;
                String getConfig = "{\"method\":\"setConfig\",\"params\":{\"Sensitivity\" :" + sensitivity + ",\"MotionDetect\":" + motionDetect + "}}";
                String optionsResult = MNJni.RequestMotionDetect(sn, getConfig, SDK_TIMEOUT);
                if (!TextUtils.isEmpty(optionsResult)) {
                    optionsBean = new Gson().fromJson(optionsResult.trim(), BaseResult.class);
                }
                BaseResult finalOptionsBean = optionsBean;
                if (callback != null && mainHandler != null) {
                    mainHandler.post(() -> {
                        callback.onSetMotionDetect(finalOptionsBean);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    /**
     * 获取NVR设备动检使能和灵敏度配置(Get NVR device motion detection enable and sensitivity configuration)
     *
     * @param sn       Device sn
     * @param channels 设备通道号 从0开始(Device channel number starting from 0)
     * @param callback Callback method
     */
    public static void getNVRMotionDetectConfig(String sn, int[] channels, MNOpenSDKInterface.GetNVRMotionDetectCallBack callback) {
        threadPool.execute(() -> {
            try {
                MotionDetectNvrBean optionsBean = null;
                String getConfig = "{\"method\":\"getConfig\",\"channel\":" + arrayInt2String(channels) + "}";
                String optionsResult = MNJni.RequestMotionDetect(sn, getConfig, SDK_TIMEOUT);
                if (!TextUtils.isEmpty(optionsResult)) {
                    optionsBean = new Gson().fromJson(optionsResult.trim(), MotionDetectNvrBean.class);
                }
                MotionDetectNvrBean finalOptionsBean = optionsBean;
                if (callback != null && mainHandler != null) {
                    mainHandler.post(() -> {
                        callback.onGetNVRMotionDetect(finalOptionsBean);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 设置NVR设备动检使能和灵敏度配置(Set NVR device motion detection enable and sensitivity configuration)
     *
     * @param sn            Device sn
     * @param motionDetects 对应通道设置信息(Corresponding channel setting information)
     * @param callback      Callback method
     */
    public static void setNVRMotionDetectConfig(String sn, ArrayList<MotionDetect> motionDetects, MNOpenSDKInterface.SetNVRMotionDetectCallBack callback) {
        threadPool.execute(() -> {
            try {
                if (!AuthorityManager.isHadDeviceConfigAuthority(sn)) {
                    if (callback != null && mainHandler != null) {
                        DevSetMoreBaseBean baseResult = new DevSetMoreBaseBean();
                        baseResult.setResult(false);
                        baseResult.setCode(5005);
                        baseResult.setMsg("Restricted permission");
                        mainHandler.post(()->{
                            callback.onSetNVRMotionDetect(baseResult);
                        });
                    }
                    return;
                }

                DevSetMoreBaseBean optionsBean = null;
                String getConfig = "{\"method\":\"setConfig\",\"params\":" + new Gson().toJson(motionDetects) + "}";
                String optionsResult = MNJni.RequestMotionDetect(sn, getConfig, SDK_TIMEOUT);
                if (!TextUtils.isEmpty(optionsResult)) {
                    optionsBean = new Gson().fromJson(optionsResult.trim(), DevSetMoreBaseBean.class);
                }
                DevSetMoreBaseBean finalOptionsBean = optionsBean;
                if (callback != null && mainHandler != null) {
                    mainHandler.post(() -> {
                        callback.onSetNVRMotionDetect(finalOptionsBean);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    /**
     * 获取设备人脸识别使能配置(Get device face recognition enabled configuration)
     *
     * @param sn       Device sn
     * @param callback Callback method
     */
    public static void getFaceDetectConfig(String sn, MNOpenSDKInterface.GetFaceDetectCallBack callback) {
        threadPool.execute(() -> {
            try {
                FaceDetectBean optionsBean = null;
                String getConfig = "{\"method\":\"getConfig\"}";
                String optionsResult = MNJni.RequestFaceDetect(sn, getConfig, SDK_TIMEOUT);
                if (!TextUtils.isEmpty(optionsResult)) {
                    optionsBean = new Gson().fromJson(optionsResult, FaceDetectBean.class);
                }
                FaceDetectBean finalOptionsBean = optionsBean;
                if (callback != null && mainHandler != null) {
                    mainHandler.post(() -> {
                        callback.onGetFaceDetect(finalOptionsBean);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 设置设备人脸识别使能配置(Set device face recognition enable configuration)
     *
     * @param sn            Device sn
     * @param faceDetection 是否开启人脸识别（Whether to enable face recognition）
     * @param callback      Callback method
     */
    public static void setFaceDetectConfig(String sn, boolean faceDetection, MNOpenSDKInterface.SetFaceDetectCallBack callback) {
        threadPool.execute(() -> {
            try {
                if (!AuthorityManager.isHadDeviceConfigAuthority(sn)) {
                    if (callback != null && mainHandler != null) {
                        BaseResult baseResult = new BaseResult();
                        baseResult.setResult(false);
                        baseResult.setCode(5005);
                        baseResult.setMsg("Restricted permission");
                        mainHandler.post(()->{
                            callback.onSetFaceDetect(baseResult);
                        });
                    }
                    return;
                }


                BaseResult optionsBean = null;
                String getConfig = "{\"method\":\"setConfig\",\"params\":{\"FaceDetection\":" + faceDetection + "}}";
                String optionsResult = MNJni.RequestFaceDetect(sn, getConfig, SDK_TIMEOUT);
                if (!TextUtils.isEmpty(optionsResult)) {
                    optionsBean = new Gson().fromJson(optionsResult, BaseResult.class);
                }
                BaseResult finalOptionsBean = optionsBean;
                if (callback != null && mainHandler != null) {
                    mainHandler.post(() -> {
                        callback.onSetFaceDetect(finalOptionsBean);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    /**
     * 获取NVR对应设备人脸识别配置（Get the face recognition configuration of the NVR corresponding device）
     *
     * @param sn       Device sn
     * @param channels 设备通道号（Device channel number）
     * @param callback Callback method
     */
    public static void getNvrFaceDetectConfig(String sn, int[] channels, MNOpenSDKInterface.GetNvrFaceDetectCallBack callback) {
        threadPool.execute(() -> {
            try {
                FaceDetectNvrBean optionsBean = null;
                String getConfig = "{\"method\":\"getConfig\",\"channel\":" + arrayInt2String(channels) + "}";
                String optionsResult = MNJni.RequestFaceDetect(sn, getConfig, SDK_TIMEOUT);
                if (!TextUtils.isEmpty(optionsResult)) {
                    optionsBean = new Gson().fromJson(optionsResult, FaceDetectNvrBean.class);
                }
                FaceDetectNvrBean finalOptionsBean = optionsBean;
                if (callback != null && mainHandler != null) {
                    mainHandler.post(() -> {
                        callback.onGetNvrFaceDetect(finalOptionsBean);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 设置NVR设备对应通道人脸识别状态（Set the face recognition status of the corresponding channel of the NVR device）
     *
     * @param sn        Device sn
     * @param faceBeans 对应通道的人脸识别开关配置（Face recognition switch configuration for the corresponding channel）
     * @param callback  Callback method
     */
    public static void setNvrFaceDetectConfig(String sn, ArrayList<FaceBean> faceBeans, MNOpenSDKInterface.SetNvrFaceDetectCallBack callback) {
        threadPool.execute(() -> {
            try {
                if (!AuthorityManager.isHadDeviceConfigAuthority(sn)) {
                    if (callback != null && mainHandler != null) {
                        DevSetMoreBaseBean baseResult = new DevSetMoreBaseBean();
                        baseResult.setResult(false);
                        baseResult.setCode(5005);
                        baseResult.setMsg("Restricted permission");
                        mainHandler.post(()->{
                            callback.onSetNvrFaceDetect(baseResult);
                        });
                    }
                    return;
                }

                DevSetMoreBaseBean optionsBean = null;
                String getConfig = "{\"method\":\"setConfig\",\"params\":" + new Gson().toJson(faceBeans) + "}";
                String optionsResult = MNJni.RequestFaceDetect(sn, getConfig, SDK_TIMEOUT);
                if (!TextUtils.isEmpty(optionsResult)) {
                    optionsBean = new Gson().fromJson(optionsResult.trim(), DevSetMoreBaseBean.class);
                }
                DevSetMoreBaseBean finalOptionsBean = optionsBean;
                if (callback != null && mainHandler != null) {
                    mainHandler.post(() -> {
                        callback.onSetNvrFaceDetect(finalOptionsBean);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    /**
     * 获取P2摇头机设备移动追踪开关（Get P2 shaker device movement tracking switch）
     *
     * @param sn       Device sn
     * @param callback Callback method
     */
    public static void getMotionTrackConfig(String sn, MNOpenSDKInterface.GetMotionTrackConfigCallBack callback) {
        threadPool.execute(() -> {
            try {
                LocationMobileBean optionsBean = null;
                String getConfig = "{\"method\":\"getConfig\"}";
                String optionsResult = MNJni.RequestMotionTrackConfig(sn, getConfig, 10);
                if (!TextUtils.isEmpty(optionsResult)) {
                    optionsBean = new Gson().fromJson(optionsResult, LocationMobileBean.class);
                }
                LocationMobileBean finalOptionsBean = optionsBean;
                if (callback != null && mainHandler != null) {
                    mainHandler.post(() -> {
                        callback.onGetMotionTrackConfig(finalOptionsBean);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 设置P2摇头机设备移动追踪开关（Set the P2 shaker device movement tracking switch）
     *
     * @param sn          Device sn
     * @param motionTrack 是否开启移动追踪（Whether to enable mobile tracking）
     * @param callback    Callback method
     */
    public static void setMotionTrackConfig(String sn, boolean motionTrack, MNOpenSDKInterface.SetMotionTrackConfigCallBack callback) {
        threadPool.execute(() -> {
            try {
                if (!AuthorityManager.isHadDeviceConfigAuthority(sn)) {
                    if (callback != null && mainHandler != null) {
                        BaseResult baseResult = new BaseResult();
                        baseResult.setResult(false);
                        baseResult.setCode(5005);
                        baseResult.setMsg("Restricted permission");
                        mainHandler.post(()->{
                            callback.onSetMotionTrackConfig(baseResult);
                        });
                    }
                    return;
                }

                BaseResult optionsBean = null;
                String getConfig = "{\"method\":\"setConfig\",\"params\":{\"MotionTrack\":" + motionTrack + "}}";

                String optionsResult = MNJni.RequestMotionTrackConfig(sn, getConfig, 10);
                if (!TextUtils.isEmpty(optionsResult)) {
                    optionsBean = new Gson().fromJson(optionsResult, BaseResult.class);
                }
                BaseResult finalOptionsBean = optionsBean;
                if (callback != null && mainHandler != null) {
                    mainHandler.post(() -> {
                        callback.onSetMotionTrackConfig(finalOptionsBean);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 获取设备视频是否上传云端和视频长度配置（Get whether device video is uploaded to the cloud and video length configuration）
     *
     * @param sn       Device sn
     * @param callback Callback method
     */
    public static void getAlarmCloudRecordConfig(String sn, MNOpenSDKInterface.GetAlarmCloudRecordConfigCallBack callback) {
        threadPool.execute(() -> {
            try {
                AlarmCloudRecordBean optionsBean = null;
                String getConfig = "{\"method\":\"getConfig\"}";

                String optionsResult = MNJni.RequestMNAlarmCloudRecord(sn, getConfig, 10);
                if (!TextUtils.isEmpty(optionsResult)) {
                    optionsBean = new Gson().fromJson(optionsResult, AlarmCloudRecordBean.class);
                }
                AlarmCloudRecordBean finalOptionsBean = optionsBean;
                if (callback != null && mainHandler != null) {
                    mainHandler.post(() -> {
                        callback.onGetAlarmCloudRecordConfig(finalOptionsBean);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 设置设备视频是否上传云端和视频长度配置（Set whether device video is uploaded to the cloud and video length configuration）
     *
     * @param sn         Device sn
     * @param nvrSetBean 配置信息（Configuration information）
     * @param callback   Callback method
     */
    public static void setAlarmCloudRecordConfig(String sn, AlarmCloudRecordSetBean nvrSetBean, MNOpenSDKInterface.SetAlarmCloudRecordConfigCallBack callback) {
        threadPool.execute(() -> {
            try {
                if (!AuthorityManager.isHadDeviceConfigAuthority(sn)) {
                    if (callback != null && mainHandler != null) {
                        BaseResult baseResult = new BaseResult();
                        baseResult.setResult(false);
                        baseResult.setCode(5005);
                        baseResult.setMsg("Restricted permission");
                        mainHandler.post(()->{
                            callback.onSetAlarmCloudRecordConfig(baseResult);
                        });
                    }
                    return;
                }

                BaseResult optionsBean = null;
                String getConfig = "{\"method\":\"setConfig\",\"params\":" + new Gson().toJson(nvrSetBean) + "}";

                String optionsResult = MNJni.RequestMNAlarmCloudRecord(sn, getConfig, 10);
                if (!TextUtils.isEmpty(optionsResult)) {
                    optionsBean = new Gson().fromJson(optionsResult, BaseResult.class);
                }
                BaseResult finalOptionsBean = optionsBean;
                if (callback != null && mainHandler != null) {
                    mainHandler.post(() -> {
                        callback.onSetAlarmCloudRecordConfig(finalOptionsBean);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 截取设置当前画面（Capture the current picture）
     *
     * @param sn       Device sn
     * @param callback Callback method
     */
    public static void getCapturePicture(String sn, MNOpenSDKInterface.GetCapturePictureCallBack callback) {
        threadPool.execute(() -> {
            try {
                CoverBean optionsBean = null;
                String optionsResult = MNJni.SdkCapturePicture(sn);
                if (!TextUtils.isEmpty(optionsResult) && !"null".equals(optionsResult)) {
                    optionsBean = new Gson().fromJson(optionsResult, CoverBean.class);
                }
                CoverBean finalOptionsBean = optionsBean;
                if (callback != null && mainHandler != null) {
                    mainHandler.post(() -> {
                        callback.onGetCapturePicture(finalOptionsBean);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 获取NVR设备信息(Get NVR Device Information)
     *
     * @param sn       Device sn
     * @param callback Callback method
     */
    public static void getNVRIPCInfo(String sn, MNOpenSDKInterface.GetNVRIPCInfoCallBack callback) {
        threadPool.execute(() -> {
            try {
                NVRIPCInfoBean option = null;
                String videoOptionsResult = MNJni.GetNVRIPCInfo(sn, 10);
                if (!TextUtils.isEmpty(videoOptionsResult)) {
                    option = new Gson().fromJson(videoOptionsResult.trim(), NVRIPCInfoBean.class);
                }
                NVRIPCInfoBean finalOptions = option;
                if (callback != null && mainHandler != null) {
                    mainHandler.post(() -> {
                        callback.onGetNVRIPCInfo(finalOptions);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 低功耗电池设备保活（Low power battery equipment keep alive）
     *
     * @param sn Device sn
     */
    public static void setKeepAlive(String sn) {
        threadPool.execute(() -> {
            MNJni.SetKeepAlive(sn, 2);
        });
    }

    /**
     * 设备获取设备固件升级进度（Device gets device firmware upgrade progress）
     *
     * @param sn Device sn
     */
    public static void getUpgradeState(String sn, MNOpenSDKInterface.GetUpgradeStateCallBack callback) {
        threadPool.execute(() -> {
            try {
                UpgradeStateBean optionsBean = null;
                String optionsResult = MNJni.GetUpgradeState(sn, 10);
                if (!TextUtils.isEmpty(optionsResult)) {
                    optionsBean = new Gson().fromJson(optionsResult, UpgradeStateBean.class);
                }
                UpgradeStateBean finalOptionsBean = optionsBean;
                if (callback != null && mainHandler != null) {
                    mainHandler.post(() -> {
                        callback.onGetUpgradeState(sn, finalOptionsBean);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 设备固件升级（Device firmware upgrade）
     *
     * @param sn        Device sn
     * @param pPkgUrl
     * @param lPkgSize
     * @param pszPkgMD5
     */
    public static BaseResult upgradeFirmware(String sn, String pPkgUrl, long lPkgSize, String pszPkgMD5) {
        if (!AuthorityManager.isHadDeviceConfigAuthority(sn)) {
            BaseResult baseResult = new BaseResult();
            baseResult.setResult(false);
            baseResult.setCode(5005);
            baseResult.setMsg("Restricted permission");
            return baseResult;
        }

        threadPool.execute(() -> {
            MNJni.FirmwareUpgradeRequest(sn, pPkgUrl, lPkgSize, pszPkgMD5);
        });

        BaseResult baseResult = new BaseResult();
        baseResult.setResult(true);
        baseResult.setCode(0);
        baseResult.setMsg("");
        return baseResult;
    }

    /**
     * 重启设备（reboot device）
     *
     * @param sn Device sn
     */
    public static BaseResult rebootDevice(String sn) {
        if (!AuthorityManager.isHadDeviceConfigAuthority(sn)) {
            BaseResult baseResult = new BaseResult();
            baseResult.setResult(false);
            baseResult.setCode(5005);
            baseResult.setMsg("Restricted permission");
            return baseResult;
        }
        threadPool.execute(() -> {
            MNJni.SetDeviceReboot(sn);
        });
        BaseResult baseResult = new BaseResult();
        baseResult.setResult(true);
        baseResult.setCode(0);
        baseResult.setMsg("");
        return baseResult;
    }

    /**
     * 重置设备wifi（Reset device wifi）
     *
     * @param sn Device sn
     */
    public static BaseResult restoreWLan(String sn) {
        if (!AuthorityManager.isHadDeviceConfigAuthority(sn)) {
            BaseResult baseResult = new BaseResult();
            baseResult.setResult(false);
            baseResult.setCode(5005);
            baseResult.setMsg("Restricted permission");
            return baseResult;
        }
        threadPool.execute(() -> {
            MNJni.SetDeviceRestoreWLan(sn);
        });
        BaseResult baseResult = new BaseResult();
        baseResult.setResult(true);
        baseResult.setCode(0);
        baseResult.setMsg("");
        return baseResult;
    }

    /**
     * 向传感器发送wifi信息（Send wifi information to the sensor）
     *
     * @param ssid     wifi名称（wifi name）
     * @param pwd      wifi密码（wifi password）
     * @param pwdMode  加密方式 例如：WPA2-AES （Encryption method Example: WPA2-AES）
     * @param userID   用户ID（User ID）
     * @param areaCode 区域码，无此字段则认为空（Area code, without this field it is considered empty）
     * @param domain   域名附加段，例如"in"，则对应域名（Additional domain name segment, such as "in", corresponding to the domain name）iotin.bullyun.com ，videoin.bullyun.com
     * @return 0发送成功，-1创建套接字失败，-2连接服务器失败，-3发送wifi信息失败,-4参数有问题（0 sent successfully, -1 failed to create a socket, -2 failed to connect to the server, -3 failed to send wifi information, -4 has a problem with the parameter）
     */
    public static int SendWifiConfig(String ssid, String pwd, String pwdMode, String userID, String areaCode, String domain) {
        return MNJni.SendWifiConfig(ssid, pwd, pwdMode, userID, areaCode, domain);
    }

    /**
     * APP收到传感器绑定的请求后需要调用此接口（APP needs to call this interface after receiving the sensor binding request）
     *
     * @param sn       Device sn
     * @param response JSON格式的响应,{"bindUser":{"name":"zhangsan"}}
     * @param code     0 绑定成功，-1 绑定失败（0 binding succeeded, -1 failed to bind）
     * @return 0 发送成功，-1 发送失败（0 sent successfully, -1 sent failed）
     */
    public static int ResponseBindDevice(String sn, String response, int code) {
        return MNJni.ResponseBindDevice(sn, response, code);
    }
}

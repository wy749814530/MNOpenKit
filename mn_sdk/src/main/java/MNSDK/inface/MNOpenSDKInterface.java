package MNSDK.inface;

import com.mn.bean.setting.AlarmAsossiatedBean;
import com.mn.bean.setting.AlarmCloudRecordBean;
import com.mn.bean.setting.AlarmTimeRecordBean;
import com.mn.bean.setting.AlarmTimeRecordNvrBean;
import com.mn.bean.setting.AudioOutputBean;
import com.mn.bean.setting.AudioOutputNvrBean;
import com.mn.bean.setting.BaseResult;
import com.mn.bean.setting.CoverBean;
import com.mn.bean.setting.DevSetMoreBaseBean;
import com.mn.bean.setting.DevSoundBean;
import com.mn.bean.setting.DevStandardBean;
import com.mn.bean.setting.FaceDetectBean;
import com.mn.bean.setting.FaceDetectNvrBean;
import com.mn.bean.setting.LocationMobileBean;
import com.mn.bean.setting.MBatteryBean;
import com.mn.bean.setting.LanguageBean;
import com.mn.bean.setting.BLCConfigBean;
import com.mn.bean.setting.LocalesConfigBean;
import com.mn.bean.setting.MotionDetectBean;
import com.mn.bean.setting.MotionDetectNvrBean;
import com.mn.bean.setting.NVRIPCInfoBean;
import com.mn.bean.setting.NetLightCallBean;
import com.mn.bean.setting.TFStateBean;
import com.mn.bean.setting.TFStateConfigBean;
import com.mn.bean.setting.TimeZoneBean;
import com.mn.bean.setting.UpgradeStateBean;
import com.mn.bean.setting.VideoOptionsBean;
import com.mn.bean.setting.VideoOptionsNvrBean;
import com.mn.bean.setting.DevBaseInfoBean;

/**
 * Created by Administrator on 2020/1/3 0003.
 */

public class MNOpenSDKInterface {

    public interface DeviceLocalVideosCallBack {
        void onDeviceLocalVideos(String data);
    }

    public interface LanguageConfigCallBack {
        void onLanguageConfig(LanguageBean data);
    }

    public interface SetLanguageConfigCallBack {
        void onSetLanguageConfig(BaseResult data);
    }

    public interface VideoStandardConfigCallBack {
        void onVideoStandardConfig(DevStandardBean data);
    }

    public interface GetAudioOutputCallBack {
        void onGetAudioOutput(AudioOutputBean bean);
    }

    public interface SetAudioOutputCallBack {
        void onSetAudioOutput(BaseResult bean);
    }

    public interface GetNvrAudioOutputCallBack {
        void onGetNvrAudioOutput(AudioOutputNvrBean bean);
    }

    public interface SetNvrAudioOutputCallBack {
        void onSetNvrAudioOutput(DevSetMoreBaseBean finalAudioOptions);
    }

    public interface GetSoundModeConfigCallBack {
        void onGetSoundModeConfig(DevSoundBean bean);
    }

    public interface SetSoundModeConfigCallBack {
        void onSetSoundModeConfig(BaseResult bean);
    }

    public interface GetTimeZoneConfigCallBack {
        void onGetTimeZoneConfig(TimeZoneBean bean);
    }

    public interface SetTimeZoneConfigCallBack {
        void onSetTimeZoneConfig(BaseResult bean);
    }

    public interface GetSummerTimeConfigCallBack {
        void onGetSummerTimeConfig(LocalesConfigBean bean);
    }

    public interface SetSummerTimeConfigCallBack {
        void onSetSummerTimeConfig(BaseResult bean);
    }

    public interface GetBLCConfigCallBack {
        void onGetBLCConfig(BLCConfigBean bean);
    }

    public interface SetBLCConfigCallBack {
        void onSetBLCConfig(BaseResult bean);
    }

    public interface GetVideoInOptionsCallBack {
        void onGetVideoInOptions(VideoOptionsBean bean);
    }

    public interface SetVideoInOptionsCallBack {
        void onSetVideoInOptions(BaseResult bean);
    }


    public interface GetNvrVideoInOptionsCallBack {
        void onGetNvrVideoInOptions(VideoOptionsNvrBean bean);
    }


    public interface SetNvrVideoInOptionsCallBack {
        void onSetNvrVideoInOptions(DevSetMoreBaseBean bean);
    }

    public interface GetAlarmAsossiatedConfigCallBack {
        void onGetAlarmAsossiatedConfig(AlarmAsossiatedBean bean);
    }

    public interface SetAlarmAsossiatedConfigCallBack {
        void onSetAlarmAsossiatedConfig(BaseResult bean);
    }


    public interface GetTFStorageCallBack {
        void onGetTFStorage(TFStateConfigBean bean);
    }

    public interface GetTFSimpleStateCallBack {
        void onGetTFSimpleState(TFStateBean bean);
    }

    public interface TFStorageFormatCallBack {
        void onTFStorageFormat(BaseResult bean);
    }

    public interface GetAlarmRecordCallBack {
        void onGetAlarmRecord(AlarmTimeRecordBean bean);
    }

    public interface SetAlarmRecordCallBack {
        void onSetAlarmRecord(BaseResult bean);
    }


    public interface GetNvrAlarmRecordCallBack {
        void onGetNvrAlarmRecord(AlarmTimeRecordNvrBean bean);
    }

    public interface SetNvrAlarmRecordCallBack {
        void onSetNvrAlarmRecord(DevSetMoreBaseBean bean);
    }

    public interface GetNetLightCallBack {
        void onGetNetLight(NetLightCallBean bean);
    }

    public interface SetNetLightCallBack {
        void onSetNetLight(BaseResult bean);
    }

    public interface GetDeviceBaseInfoCallBack {
        void onGetDeviceBaseInfo(DevBaseInfoBean bean);
    }

    public interface GetPowerStateCallBack {
        void onGetPowerState(MBatteryBean bean);
    }

    public interface GetMotionDetectCallBack {
        void onGetMotionDetect(MotionDetectBean bean);
    }

    public interface SetMotionDetectCallBack {
        void onSetMotionDetect(BaseResult bean);
    }

    public interface GetNVRMotionDetectCallBack {
        void onGetNVRMotionDetect(MotionDetectNvrBean bean);
    }

    public interface SetNVRMotionDetectCallBack {
        void onSetNVRMotionDetect(DevSetMoreBaseBean bean);
    }

    public interface GetFaceDetectCallBack {
        void onGetFaceDetect(FaceDetectBean bean);
    }

    public interface SetFaceDetectCallBack {
        void onSetFaceDetect(BaseResult bean);
    }

    public interface GetNvrFaceDetectCallBack {
        void onGetNvrFaceDetect(FaceDetectNvrBean bean);
    }

    public interface SetNvrFaceDetectCallBack {
        void onSetNvrFaceDetect(DevSetMoreBaseBean bean);
    }

    public interface GetMotionTrackConfigCallBack {
        void onGetMotionTrackConfig(LocationMobileBean optionsBean);
    }

    public interface SetMotionTrackConfigCallBack {
        void onSetMotionTrackConfig(BaseResult optionsBean);
    }

    public interface GetAlarmCloudRecordConfigCallBack {
        void onGetAlarmCloudRecordConfig(AlarmCloudRecordBean bean);
    }

    public interface SetAlarmCloudRecordConfigCallBack {
        void onSetAlarmCloudRecordConfig(BaseResult bean);
    }

    public interface GetCapturePictureCallBack {
        void onGetCapturePicture(CoverBean bean);
    }


    public interface GetNVRIPCInfoCallBack {
        void onGetNVRIPCInfo(NVRIPCInfoBean bean);
    }


    public interface GetUpgradeStateCallBack {
        void onGetUpgradeState(String sn, UpgradeStateBean bean);
    }
}

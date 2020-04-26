package MNSDK;

import com.google.gson.Gson;
import com.mn.MNApplication;
import com.mn.bean.restfull.ServerMsgBean;
import com.mn.tools.AuthorityManager;

public class MNJni {
    static {
        try {
            System.loadLibrary("mnsdk");
            System.loadLibrary("voiceRecog");
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    // 自定论错误码 从-100开始
    public static final int Error = -1;

    public static class MNTaskType {
        public enum MNAV_TASK_TYPE_t {
            MTT_P2P_REALPLAY,
            MTT_P2P_SD_PLAYBACK,
            MTT_P2P_VOICE_TALK,
            MTT_CLOUD_ALARM,
            MTT_P2P_SD_CARD_QUERY,
            MTT_P2P_DEVICE_SERVER_INFO,
        }

        public MNTaskType(int val) {
            this.nValue = val;
        }

        public int getValue() {
            return this.nValue;
        }

        private int nValue;
    }

    public static class MNCloudTaskType {
        public enum MN_CLOUD_TASK_TYPE {
            MCTT_PLAY,
            MCTT_DOWNLOAD
        }

        public MNCloudTaskType(int val) {
            this.nValue = val;
        }

        public int getValue() {
            return this.nValue;
        }

        private int nValue;
    }

    public static class MNOutputDataType {
        public enum MN_OUTPUT_DATA_TYPE {
            MODT_H264,
            MODT_FFMPEG_YUV420,
            MODT_IHW_H265_YUV420,
            MODT_ANDROID_HARDWARE,
            MODT_PCM,
        }

        public MNOutputDataType(int val) {
            this.nValue = val;
        }

        public int getValue() {
            return this.nValue;
        }

        private int nValue;
    }

    public static class MNTaskStatusCode {
        public enum MNTASK_STATUS_CODE_t {
            MNTS_UNINITIALIZED,         // 未配置
            MNTS_READY,                 // 就绪
            MNTS_CONNECTING,            // 正在连接(等待P2P建立 || curl开始下载数据)
            MNTS_RUNNING,               // 正在接收数据
            MNTS_CANCELING,             // 正在取消
            MNTS_STOPPED,               // 已经停止
            MNTS_DESTROYED,             // 已销毁
            MNTS_CONNECT_FAILED,        // 链接失败
            MNTS_DEVICE_OFFLINE,        // 设备不在线
            MNTS_PAUSED,                // 任务暂停
            MNTS_FORBID,                // 没有权限
        }
    }

    public static class MNDeviceLinkStatusCode {
        public enum MNDEVICE_LINK_STATUS_CODE_t {
            MNP2P_SESSION_STATUS_UNEXIST,       //链接不存在
            MNP2P_SESSION_STATUS_LINKING,       //正在链接
            MNP2P_SESSION_STATUS_LINKED,        //链接成功
            MNP2P_SESSION_STATUS_FAILED,        //链接断开
        }
    }

    public static class MNSessionCtrlType {
        public enum MNSESSION_CTRL_TYPE_t {
            MNSCT_YT,                   // 摇头机会话
            MNSCT_QUERY_SD_CARD,        //SD卡录像时间查询
            MNSCT_PLAYBACK_PLAY,        //NVR设备，回放
            MNSCT_REAL_PLAY,            //NVR设备，对应通道是否创建成功，或者是否有IPC
            MNSCT_VOICE_TALK,           //NVR设备，对讲是否成功
            MNSCT_FACE_GROUP_ADD,       //增加人脸库
            MNSCT_FACE_GROUP_MODIFY,    //修改人脸库
            MNSCT_FACE_GROUP_DELETE,    //删除人脸库
            MNSCT_FACE_GROUP_GET,       //获取人脸库
            MNSCT_FACE_PERSON_ADD,      //增加人脸库人员信息
            MNSCT_FACE_PERSON_MODIFY,   //修改人脸库人员信息
            MNSCT_FACE_PERSON_DELETE,   //删除人脸库人员信息
            MNSCT_FACE_PERSON_GET,      //获取指定人员信息
            MNSCT_FACE_PERSON_GET_GROUP,      //获取人脸库指定组所有人员信息
            MNSCT_FACE_PERSON_DELETE_PIC,   //删除人脸库人脸图片
            MNSCT_FACE_PERSON_SEARCH,   //人员考勤信息查询
        }

        public MNSessionCtrlType(int val) {
            this.nValue = val;
        }

        public int getValue() {
            return this.nValue;
        }

        private int nValue;
    }

    public static class MNPicType {
        public enum MNPIC_TYPE_t {
            MNPT_DOWNLOAD,      //下载图片
            MNPT_UPLOAD,        //上传图片
        }
    }

    public static class MNPlayRateType {
        public enum MN_PLAY_RATE_TYPE {
            MPRT_NORMAL, //1
            MPRT_SLOW, // 0.5
            MPRT_FAST // 2
        }

        public MNPlayRateType(int val) {
            this.nValue = val;
        }

        public int getValue() {
            return this.nValue;
        }

        private int nValue;
    }

    public static class MNServerInfo {
        public String EtsDomain;
        public String IdmDomain;
        public String EtsIP;
        public String IdmIP;
        public String Region;//获取设备信息时有用
        public String tsIP;
        public int tsPort;
        public int EtsPort;
        public int IdmPort;
        public int LocalPort;   //针对每个设备连接的手机本地端口
        public int P2pType; //0代表转发，1代表p2p，-1代表未链接
    }

    public static class MN24CloudRecordInfo {
        public String Mp4Url;
        public String StartTime;
        public String EndTime;
        public int FileLength;

        public MN24CloudRecordInfo(String Mp4Url, String StartTime, String EndTime) {
            this.Mp4Url = Mp4Url;
            this.StartTime = StartTime;
            this.EndTime = EndTime;
        }

        public String getMp4Url() {
            return this.Mp4Url;
        }

        public String getStartTime() {
            return this.StartTime;
        }

        public String getEndTime() {
            return this.EndTime;
        }
    }

    public static String StatusNames[] = {"MNTS_UNINITIALIZED", "MNTS_READY", "MNTS_CONNECTING", "MNTS_RUNNING", "MNTS_CANCELING", "MNTS_STOPPED", "MNTS_DESTROYED", "MNTS_CONNECT_FAILED", "MNTS_DEVICE_OFFLINE", "MNTS_PAUSED"};

    public static native String GetServerIP(String domainName);

    public static native void Init();

    public static native void UnInit();

    public static native String GetVersion();

    public static native int GetEtsConnectionStatus();

    public static native int GetP2pConnectionStatus();

    /**
     * @param uuid:   用户uuid
     * @param token:  用户token
     * @param domain: 根据区域确定域名，如果是中国，则传"cn.bullyun.com",如果是美国，为"us.bullyunus.com",如果是印度，为"in.bullyun.com"
     * @param region: 代表区域的字符串，将用于负载均衡
     * @return
     */
    public static native int Login(String uuid, String token, String domain, String region);

    public static native int Logout();

    public static native int SendRedirectDomainMSG(String deviceSn);

    public static native int LinkToDevice(String uuid, boolean bShareDevice);

    public static native int GetDeviceLinkStatus(String uuid);

    public static native void PauseLinkToDevice(String uuid);

    public static native void ResumeLinkToDevice(String uuid);

    public static native void DestroyLink(String uuid);

    public static native MNServerInfo GetDeviceServerInfo(String uuid, MNServerInfo deviceSrverInfo);//在LinkToDevice成功后用

    public static native MNServerInfo GetAppServerInfo(MNServerInfo appServerInfo);

    public static native String QueryMicroSDCardAlarms(String uuid, int nChannelId, int type, int nStream, String pszStartTime, String pszEndTime);//new

    public static native int RequestWithMsgTunnel(String uuid, String pszCommand);//new

    public static native int FirmwareUpgradeRequest(String uuid, String pPkgUrl, long lPkgSize, String pszPkgMD5);//new

    public static native long PrepareTask(MNTaskType type, long userdata);

    public static native int GetTaskType(long lTaskContext);

    public static native String GetTaskDeviceSn(long lTaskContext);

    public static native int GetTaskStatus(long lTaskContext);

    public static native void ConfigRealPlayTask(long lTaskContext, String uuid, int channel, int stream, int deviceType, boolean bFourGEnable, MNOutputDataType eOutDataType);

    public static native void ConfigPlayBackTask(long lTaskContext, String uuid, String startTime, String stopTime, int index, int channel, int stream, MNOutputDataType eOutDataType);

    public static native void ConfigPlayBackAutoTask(long lTaskContext, String uuid, String recordTimeJson, String startTime, String stopTime, int videoType, int channel, int stream, MNOutputDataType eOutDataType);

    public static native void ConfigCloudAlarmTask(long lTaskContext, String url, int nStartPos, long lFileLength, MNOutputDataType eOutDataType, MNCloudTaskType eCloudTaskType);

    public static native void Config24HCloudRecordTask(long lTaskContext, String mp4Url, String idxUrl, String startTime, String stopTime, long lFileLength, MNOutputDataType eOutDataType, MNCloudTaskType eCloudTaskType);

    public static native void Config24HCloudRecordAutoTask(long lTaskContext, MN24CloudRecordInfo[] infos, String startTime, String stopTime, MNOutputDataType eOutDataType, MNCloudTaskType eCloudTaskType);

    /**
     * typedef enum
     * {
     * AUDIO_FORMAT_PCM8 = 1,
     * AUDIO_FORMAT_G711a = 2,
     * AUDIO_FORMAT_AMR = 3,
     * AUDIO_FORMAT_G711u = 4,
     * AUDIO_FORMAT_G726 = 5,
     * AUDIO_FORMAT_AAC = 8,
     * }C_GW_AUDIO_FORMAT;
     * <p>
     * <p>
     * typedef enum AudioEncPackType
     * {
     * audioEncPackTypeZLAV = 0,	///< ZLAV��ʽ���
     * audioEncPackTypePS,			///< PS����ʽ���
     * audioEncPackTypeRAW,		///< �������������
     * audioEncPackTypeDHAV,		///< DHAV��ʽ���
     * }AudioEncPackType;
     *
     * @param lTaskContext
     * @param nFormat
     * @param nPackType
     */
    public static native void ConfigVoiceTalkTask(long lTaskContext, String uuid, int nFormat, int nPackType);

    public static native int StartTask(long lTaskContext);

    public static native int PauseTask(long lTaskContext);

    public static native int ResumeTask(long lTaskContext);

    public static native int DestroyTask(long lTaskContext);

    public static native int ChangePlayBackAutoPlayTime(long lTaskContext, String startTime, String stopTime, int videoType);//改变卡回放播放起始时间和结束时间

    public static native int Change24CloudRecordAutoPlayTime(long lTaskContext, String startTime, String stopTime);//改变24小时云存任务的播放起始时间和结束时间

    public static native int StartVoiceTalk(long lTaskContext);

    public static native int StopVoiceTalk(long lTaskContext);

    public static native int SwitchStreamMode(long lTaskContext, int channel, int nNewStream);

    public static native int SwitchPlayRate(long lTaskContext, MNPlayRateType eRateType);

    public static native int RequestYTControl(long lTaskContext, String pszCommand);

    public static native int SendData(long lTaskContext, byte[] buf, int nLen, int nType);

    public static native int SendJsonPackage(int encrypt, byte[] buf, int len);

    public static native int StartRecordVideo(String videoPath, String audioPath, long lTaskContext);

    public static native int FinishRecordVideo(String mp4Path, long lTaskContext);

    //public static native long DownloadPlayBackVideo(String sid, String startTime, String stopTime, int index, int channel, int stream, String mp4Path);

    public static native long DownloadPlayBackVideoAuto(String sid, String recordTimeJson, String startTime, String stopTime, int videoType, int channel, int stream, String mp4Path);

    public static native int DownloadAlarmPicData(String uuid, int channel, String url, String index);//必须先链接上设备，成功返回0，失败返回-1,index用于对应在回调中获取的数据

    public static native int DownloadAttendancePicData(String uuid, int channel, String url, String index);//必须先链接上设备，成功返回0，失败返回-1,index用于对应在回调中获取的数据

    public static native int SendPicData(String uuid, int channel, byte[] picData, int picLen, String index);//必须先链接上设备，成功返回0，失败返回-1,index用于对应在回调中获取的数据

    /**
     * 请求人脸考勤相关配置
     *
     * @param uuid 设备序列号
     * @param type 命令类型
     * @param data json格式命令,如果是删除人员图片则是{"imageID":"12344"}的格式
     * @return 0 发送成功，-1 发送失败
     */
    public static native int SendFaceAttentionConfig(String uuid, MNSessionCtrlType type, String data);

    public static native int YUV420P2RGB565(int nWidth, int nHeight, byte[] yuv420p, byte[] rgb565);

    public static native int YUV420P2BMP(int nWidth, int nHeight, byte[] yuv420p, byte[] bmpData);

    public static native int JPEG2MP4(int frameRate, String folderPath, String mp4Path, String id);//0代表成功，-1代表失败

    public static native String SdkCapturePicture(String uuid);

    public static native int SdkSetLogPath(String pszPath);

    public static native int SdkWriteLogData(String tag, String uuid, String data);

    public static native String SdkReadLogData();

    //设备配置相关接口

    /**
     * 向传感器发送wifi信息
     *
     * @param ssid     wifi名称
     * @param pwd      wifi密码
     * @param pwdMode  加密方式 例如：WPA2-AES
     * @param userID   用户ID
     * @param areaCode 区域码，无此字段则认为空
     * @param domain   域名附加段，例如"in"，则对应域名iotin.bullyun.com videoin.bullyun.com
     * @return 0发送成功，-1创建套接字失败，-2连接服务器失败，-3发送wifi信息失败,-4参数有问题
     */
    public static native int SendWifiConfig(String ssid, String pwd, String pwdMode, String userID, String areaCode, String domain);

    /**
     * APP收到传感器绑定的请求后需要调用此接口
     *
     * @param uuid     设备序列号
     * @param response JSON格式的响应,{"bindUser":{"name":"zhangsan"}}
     * @param code     0 绑定成功，-1 绑定失败
     * @return 0 发送成功，-1 发送失败
     */
    public static native int ResponseBindDevice(String uuid, String response, int code);

    /**
     * 设置设备使用新协议还是老协议
     *
     * @param uuid       设备序列号
     * @param bOldDevice false:新协议；true:老协议
     * @return
     */
    public static native int SetDeviceVersionType(String uuid, boolean bOldDevice);

    /**
     * 设备红外灯、镜像、翻转配置
     *
     * @param uuid       设备序列号
     * @param pszCommand 命令
     * @param nBlockSec  阻塞时长（单位s)
     * @return Json格式的结果
     */
    public static native String RequestVideoInOptions(String uuid, String pszCommand, int nBlockSec);

    /**
     * 设备音量配置
     *
     * @param uuid       设备序列号
     * @param pszCommand 命令
     * @param nBlockSec  阻塞时长（单位s)
     * @return Json格式的结果
     */
    public static native String RequestAudioOutputVolume(String uuid, String pszCommand, int nBlockSec);

    /**
     * 设备静音和离线语音配置
     *
     * @param uuid       设备序列号
     * @param pszCommand 命令
     * @param nBlockSec  阻塞时长（单位s)
     * @return Json格式的结果
     */
    public static native String RequestSound(String uuid, String pszCommand, int nBlockSec);

    /**
     * 设备视频是否上传云端和视频长度配置
     *
     * @param uuid       设备序列号
     * @param pszCommand 命令
     * @param nBlockSec  阻塞时长（单位s)
     * @return Json格式的结果
     */
    public static native String RequestMNAlarmCloudRecord(String uuid, String pszCommand, int nBlockSec);

    /**
     * 设备动检使能和灵敏度配置
     *
     * @param uuid       设备序列号
     * @param pszCommand 命令
     * @param nBlockSec  阻塞时长（单位s)
     * @return Json格式的结果
     */
    public static native String RequestMotionDetect(String uuid, String pszCommand, int nBlockSec);

    /**
     * 设备人脸识别使能配置
     *
     * @param uuid       设备序列号
     * @param pszCommand 命令
     * @param nBlockSec  阻塞时长（单位s)
     * @return Json格式的结果
     */
    public static native String RequestFaceDetect(String uuid, String pszCommand, int nBlockSec);

    /**
     * 设备呼吸灯配置
     *
     * @param uuid       设备序列号
     * @param pszCommand 命令
     * @param nBlockSec  阻塞时长（单位s)
     * @return Json格式的结果
     */
    public static native String RequestNetLight(String uuid, String pszCommand, int nBlockSec);

    /**
     * 设备夏令时配置
     *
     * @param uuid       设备序列号
     * @param pszCommand 命令
     * @param nBlockSec  阻塞时长（单位s)
     * @return Json格式的结果
     */
    public static native String RequestLocalesConfig(String uuid, String pszCommand, int nBlockSec);

    /**
     * 设备语言配置
     *
     * @param uuid       设备序列号
     * @param pszCommand 命令
     * @param nBlockSec  阻塞时长（单位s)
     * @return Json格式的结果
     */
    public static native String RequestLanguageConfig(String uuid, String pszCommand, int nBlockSec);

    /**
     * 设备时区配置
     *
     * @param uuid       设备序列号
     * @param pszCommand 命令
     * @param nBlockSec  阻塞时长（单位s)
     * @return Json格式的结果
     */
    public static native String RequestNTPConfig(String uuid, String pszCommand, int nBlockSec);

    /**
     * 设备TF卡存储类型和时间配置
     *
     * @param uuid       设备序列号
     * @param pszCommand 命令
     * @param nBlockSec  阻塞时长（单位s)
     * @return Json格式的结果
     */
    public static native String RequestAlarmRecord(String uuid, String pszCommand, int nBlockSec);

    /**
     * 读写传感器数据
     *
     * @param uuid       设备序列号
     * @param pszCommand 命令
     * @param nBlockSec  阻塞时长（单位s)
     * @return Json格式的结果
     */
    public static native String RequestSensorDataConfig(String uuid, String pszCommand, int nBlockSec);

    /**
     * 摇头机设备移动追踪的获取和设置
     *
     * @param uuid       设备序列号
     * @param pszCommand 命令
     * @param nBlockSec  阻塞时长（单位s)
     * @return Json格式的结果
     */
    public static native String RequestMotionTrackConfig(String uuid, String pszCommand, int nBlockSec);

    /**
     * 枪机声光模式设置
     *
     * @param uuid       设备序列号
     * @param pszCommand 命令 LightType: 0 红外夜视;1 星光全彩;2 双光警戒;
     *                   {"method":"getConfig"}
     *                   {"method":"setConfig","params":{"LightType":0,"LightSeconds":10,"AudioEnable":true}};
     * @param nBlockSec  阻塞时长（单位s)
     * @return Json格式的结果
     */
    public static native String RequestAlarmAsossiatedConfig(String uuid, String pszCommand, int nBlockSec);

    /**
     * 枪机背光补偿设置
     *
     * @param uuid       设备序列号
     * @param pszCommand 命令
     *                   {"method":"getConfig"}
     *                   {"method":"setConfig","params":{"LightEnable":true,"LightSensitive":10}};
     * @param nBlockSec  阻塞时长（单位s)
     * @return Json格式的结果
     */
    public static native String RequestLightCompensationConfig(String uuid, String pszCommand, int nBlockSec);

    /**
     * NVR前端设备名字/在线状态/连接质量/Mac地址的获取，通道名的设置
     *
     * @param uuid       设备序列号
     * @param pszCommand 命令
     * @param nBlockSec  阻塞时长（单位s)
     * @return Json格式的结果
     */
    public static native String RequestNVRIPCInfo(String uuid, String pszCommand, int nBlockSec);

    /**
     * 人形检测框设置
     *
     * @param uuid       设备序列号
     * @param pszCommand 命令
     *                   {"method":"getConfig"}
     *                   {"method":"setConfig","params":{"HumenShapeBox":true}};
     * @param nBlockSec  阻塞时长（单位s)
     * @return Json格式的结果
     */
    public static native String RequestHumenShapeBox(String uuid, String pszCommand, int nBlockSec);

    /**
     * 遮挡报警设置
     *
     * @param uuid       设备序列号
     * @param pszCommand 命令
     *                   {"method":"getConfig"}
     *                   {"method":"setConfig","params":{"BlineDetect":true,"Sensitive":50}};
     *                   低功耗设备的Sensitive是1-6；530设备的Sensitive是1-100；
     * @param nBlockSec  阻塞时长（单位s)
     * @return Json格式的结果
     */
    public static native String RequestBlineDetect(String uuid, String pszCommand, int nBlockSec);

    /**
     * 低功耗设备工作模式设置
     *
     * @param uuid       设备序列号
     * @param pszCommand 命令
     *                   {"method":"getConfig"}
     *                   {"method":"setConfig","params":{"Mode":0}};
     *                   Mode:0 LowMode低功耗模式; 1 AutoMode自动模式;2 RtcMode自动唤醒
     * @param nBlockSec  阻塞时长（单位s)
     * @return Json格式的结果
     */
    public static native String RequestLowPowerWorkMode(String uuid, String pszCommand, int nBlockSec);

    /**
     * 低功耗设备唤醒报警上报
     *
     * @param uuid       设备序列号
     * @param pszCommand 命令
     *                   {"method":"getConfig"}
     *                   {"method":"setConfig","params":{"MNWakeAlarm":false}};
     * @param nBlockSec  阻塞时长（单位s)
     * @return Json格式的结果
     */
    public static native String RequestWakeAlarmConfig(String uuid, String pszCommand, int nBlockSec);

    /**
     * 外部报警
     *
     * @param uuid       设备序列号
     * @param pszCommand 命令
     *                   {"method":"getConfig"}
     *                   {"method":"setConfig","params":{"ExternAlarm":false}};
     * @param nBlockSec  阻塞时长（单位s)
     * @return Json格式的结果
     */
    public static native String RequestExternAlarmConfig(String uuid, String pszCommand, int nBlockSec);

    /**
     * PIR报警
     *
     * @param uuid       设备序列号
     * @param pszCommand 命令
     *                   {"method":"getConfig"}
     *                   {"method":"setConfig","params":{"PIRAlarm":false}};
     * @param nBlockSec  阻塞时长（单位s)
     * @return Json格式的结果
     */
    public static native String RequestPIRAlarmConfig(String uuid, String pszCommand, int nBlockSec);

    /**
     * 设备TF卡格式化(建议阻塞时间为20s)
     *
     * @param uuid       设备序列号
     * @param pszCommand 命令
     * @param nBlockSec  阻塞时长（单位s)
     * @return Json格式的结果
     */
    public static native String SetTFStorageFormatting(String uuid, String pszCommand, int nBlockSec);

    /**
     * 门铃设备保活
     *
     * @param uuid      设备序列号
     * @param nBlockSec 阻塞时长（单位s)
     * @return Json格式的结果
     */
    public static native String SetKeepAlive(String uuid, int nBlockSec);

    /**
     * 重启设备
     *
     * @param uuid 设备序列号
     * @return 0:发送成功 -1:发送失败
     */
    public static native int SetDeviceReboot(String uuid);

    /**
     * 重置设备wifi
     *
     * @param uuid 设备序列号
     * @return 0:发送成功 -1:发送失败
     */
    public static native int SetDeviceRestoreWLan(String uuid);

    /**
     * 获取TF卡名称、是否需要格式化
     *
     * @param uuid      设备序列号
     * @param nBlockSec 阻塞时长（单位s)
     * @return Json格式的结果
     */
    public static native String GetTFSimpleState(String uuid, int nBlockSec);

    /**
     * 获取TF卡名称、是否需要格式化、总容量、剩余容量
     *
     * @param uuid      设备序列号
     * @param nBlockSec 阻塞时长（单位s)
     * @return Json格式的结果
     */
    public static native String GetTFState(String uuid, int nBlockSec);

    /**
     * 获取设备基本信息（新协议多了TF卡名称数组和数量）
     *
     * @param uuid      设备序列号
     * @param nBlockSec 阻塞时长（单位s)
     * @return Json格式的结果
     */
    public static native String GetDeviceBaseInfo(String uuid, int nBlockSec);

    /**
     * 获取设备升级状态和进度
     *
     * @param uuid      设备序列号
     * @param nBlockSec 阻塞时长（单位s)
     * @return Json格式的结果
     */
    public static native String GetUpgradeState(String uuid, int nBlockSec);

    /**
     * 获取设备制式
     *
     * @param uuid      设备序列号
     * @param nBlockSec 阻塞时长（单位s)
     * @return Json格式的结果
     */
    public static native String GetVideoStandard(String uuid, int nBlockSec);

    /**
     * 获取门铃设备的电量
     *
     * @param uuid      设备序列号
     * @param nBlockSec 阻塞时长（单位s)
     * @return Json格式的结果
     */
    public static native String GetPowerState(String uuid, int nBlockSec);

    /**
     * 获取NVR前端设备名字和在线状态(在线状态请用GetNVRRemoteDeviceInfo)
     *
     * @param uuid      设备序列号
     * @param nBlockSec 阻塞时长（单位s)
     * @return Json格式的结果
     */
    public static native String GetNVRIPCInfo(String uuid, int nBlockSec);

    /**
     * 获取NVR通道是否配置过设备以及设备在线状态(在线状态请用GetNVRRemoteDeviceInfo)
     *
     * @param uuid      设备序列号
     * @param nBlockSec 阻塞时长（单位s)
     * @return Json格式的结果
     */
    public static native String GetNVRRemoteDeviceInfo(String uuid, int nBlockSec);

    /**
     * APP与IDM服务器的连接状态
     *
     * @param status 0 下线；1 上线
     */
    public static void OnIDMServerLoginStatus(int status) {
        MNIdmServerProcessor.getInstance().OnIDMServerLoginStatus(status);
    }

    /**
     * 视频数据回调函数
     *
     * @param lTaskContext          任务句柄
     * @param nChannelId            通道号
     * @param userdata              用户数据
     * @param dataType              回调类型，暂时只支持MODT_H264/MODT_FFMPEG_YUV420/MODT_IHW_H265_YUV420
     * @param data
     * @param nDataLen              数据总长度
     * @param y                     Y分量数据
     * @param u                     U分量数据
     * @param v                     V分量数据
     * @param nWidth                宽
     * @param nHeight               高
     * @param nYStride              Y跨度
     * @param nUStride              U跨度
     * @param nVStride              V跨度
     * @param nFps                  帧率
     * @param nSliceType            帧类型
     * @param nYear                 当前一帧显示年
     * @param nMonth                当前一帧显示月
     * @param nDay                  当前一帧显示日
     * @param nHour                 当前一帧显示小时
     * @param nMinute               当前一帧显示分钟
     * @param nSecond               当前一帧显示秒
     * @param lNetworkFlowPerSecond 网络流量
     * @param lTotalFlow            总流量（单位为B）
     */
    public static void OnVideoDataByte(long lTaskContext, int nChannelId, long userdata, int dataType, byte[] data, int nDataLen, byte[] y, byte[] u, byte[] v, int nWidth, int nHeight, int nYStride, int nUStride, int nVStride, int nFps, int nSliceType, int nYear, int nMonth, int nDay, int nHour, int nMinute, int nSecond, long lNetworkFlowPerSecond, long lTotalFlow) {
        MNVideoProcessor.getInstance().OnVideoDataByte(lTaskContext, nChannelId, userdata, dataType, data, nDataLen, y, u, v, nWidth, nHeight, nYStride, nUStride, nVStride, nFps, nSliceType, nYear, nMonth, nDay, nHour, nMinute, nSecond, lNetworkFlowPerSecond, lTotalFlow);
    }

    /**
     * 音频数据回调函数
     *
     * @param lTaskContext 任务句柄
     * @param nChannelId   通道号
     * @param userdata     用户数据
     * @param InData       音频数据
     * @param nDataLen     音频数据长度
     * @param nEncodeType  音频类型（暂时只为PCM）
     */
    public static void OnAudioDataByte(long lTaskContext, int nChannelId, long userdata, byte[] InData, int nDataLen, int nEncodeType) {
        MNVideoProcessor.getInstance().OnAudioDataByte(lTaskContext, nChannelId, userdata, InData, nDataLen, nEncodeType);
    }

    /**
     * 透传结果回调函数
     *
     * @param uuid   设备序列号
     * @param data   json格式的透传结果
     * @param length 数据长度
     */
    public static void OnEtsTunnel(String uuid, String data, int length) {
//        LogUtil.i(TAG, "data = " + data);
        MNEtsTtunelProcessor.getInstance().OnEtsTunnel(uuid, data, length);
    }

    /**
     * 任务状态回调函数
     *
     * @param lTaskContext 任务context[由PrepareTask函数生成]
     * @param userdata     自定义数据
     * @param nTaskStatus  MNTS_UNINITIALIZED = 0,     // 未配置			调用PrepareTask函数时的初始状态
     *                     MNTS_READY,                 // 就绪				调用Config*Task后的状态
     *                     MNTS_CONNECTING,            // 正在连接			调用StartTask后的状态
     *                     MNTS_RUNNING,               // 正在接收数据		开始传输音视频时的状态
     *                     MNTS_CANCELING,             // 正在取消			正在取消时状态
     *                     MNTS_STOPPED,               // 已经停止			停止时状态
     *                     MNTS_DESTROYED,				// 任务销毁			任务被销毁时
     * @param fProgress    当前进度[云报警时才有效]
     */
    public static void OnTaskStatus(long lTaskContext, long userdata, int nTaskStatus, float fProgress) {
        MNVideoProcessor.getInstance().OnTaskStatus(lTaskContext, userdata, nTaskStatus, fProgress);
    }

    /**
     * 绑定设备的回调函数
     *
     * @param pszJson json格式的绑定信息数据
     * @param nlen    数据长度
     */
    public static void OnRequestToBindDevice(String pszJson, int nlen) {
        MNBindDevProcessor.getInstance().OnRequestToBindDevice(pszJson, nlen);
    }

    /**
     * 门铃呼叫回调
     *
     * @param data   json格式的呼叫数据
     * @param length 数据长度
     */
    public static void OnCallOut(String data, int length) {
        //上綫新的
     /*        "alarmTime":	0,
                "alarmId":	"5acac8294d048a739d340027",
                "online":	1,
                "type":	2, 上线
                "deviceSn":	"MDAhAQEAbGQwNmY0YTVjOGUyMAAA"*/
        //呼叫新的
        /*       "alarmTime":	"1525772985000",
                "alarmId":	"5af172be4d048a285f1e839f",
                "type":	1, 呼叫
                "deviceSn":	"MDAhAQEAbGQwNmY0YTVjOGUyMAAA"*/
        if (data.contains("online")) {//设备上线
            MNRingProcessor.getInstance().OnDevOnline(data, length);
        } else { //门铃呼叫推送
            MNRingProcessor.getInstance().OnRingCall(data, length);
        }
    }

    /**
     * 与设备会话控制的回调函数
     *
     * @param lTaskContext     任务句柄
     * @param nChannelId       通道号
     * @param eSessionCtrlType MNSCT_YT,                   // 摇头机会话
     *                         MNSCT_QUERY_SD_CARD,        //SD卡录像时间查询
     * @param data             json格式的会话结果
     * @param nlen             数据长度
     */
    public static void OnSessionCtrl(long lTaskContext, int nChannelId, int eSessionCtrlType, String data, int nlen) {
        if (eSessionCtrlType == MNSessionCtrlType.MNSESSION_CTRL_TYPE_t.MNSCT_QUERY_SD_CARD.ordinal()) {
            // MNLocalRecordProcessor.getInstance().OnLocalRecord(lTaskContext, nChannelId, eSessionCtrlType, data, nlen);
        } else if (eSessionCtrlType >= MNSessionCtrlType.MNSESSION_CTRL_TYPE_t.MNSCT_FACE_GROUP_ADD.ordinal() && eSessionCtrlType <= MNSessionCtrlType.MNSESSION_CTRL_TYPE_t.MNSCT_FACE_PERSON_SEARCH.ordinal()) {
            MNSessionCtrlProcessor.getInstance().OnSessionCtrl(lTaskContext, nChannelId, eSessionCtrlType, data, nlen);
        } else {
            MNVideoProcessor.getInstance().OnSessionCtrl(lTaskContext, nChannelId, eSessionCtrlType, data, nlen);
        }
    }

    /**
     * 4G设备报警图片的回调
     *
     * @param pDestSID 设备序列号
     * @param picData  图片数据
     * @param type     MNPT_DOWNLOAD,      //下载图片
     *                 MNPT_UPLOAD,        //上传图片
     * @param uLen     图片长度
     * @param index    序列号（和请求时的index对应）
     */
    public static void OnPicData(String pDestSID, int nChannelId, int type, byte[] picData, int uLen, String index) {
        MNDownloadProcessor.getInstance().OnPicDownloadData(pDestSID, nChannelId, picData, uLen, index);
    }

    /**
     * 服务端透传消息
     *
     * @param data   json格式的数据
     * @param length 数据长度
     */
    public static void OnServerMSG(String data, int length) {
        //{"userId":187346601650425860, "actionType":7 }
        /**
         * 1：设备分享
         * 2：取消设备分享或者设备分享过期
         * 3：添加设备
         * 4：解绑设备
         * 5：设备上线
         * 6：设备下线
         * 7：用户密码修改
         * 8.4g低功耗休眠（暂时4G设备）
         */
        if (data != null) {
            ServerMsgBean msgBean = new Gson().fromJson(data, ServerMsgBean.class);
            if (msgBean != null) {
                if (msgBean.getActionType() == 10) {
                    AuthorityManager.setDevAuthority(msgBean.getDeviceSn(), msgBean.getData().getAuthority());
                }
                MNApplication.getApplication().OnServerMSG(msgBean);
            }
        }
    }

    /**
     * JPEG2MP4这个阻塞接口的进度
     *
     * @param id
     * @param progress
     */
    public static void OnJPG2MP4Progress(String id, float progress) {
        //MNJPG2MP4Processor.getInstance().OnJPG2MP4Progress(id, progress);
    }
}

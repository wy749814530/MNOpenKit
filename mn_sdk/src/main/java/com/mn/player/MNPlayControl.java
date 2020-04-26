package com.mn.player;

import android.app.Activity;
import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.opengl.GLSurfaceView;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.mn.bean.restfull.AlarmsBean;
import com.mn.bean.restfull.AuthenticationBean;
import com.mn.bean.restfull.DevicesBean;
import com.mn.bean.restfull.FavoriteDelteBean;
import com.mn.bean.restfull.FavoritePointSaveBean;
import com.mn.bean.restfull.Record24Bean;
import com.mn.bean.restfull.UpdateDeviceCoverBean;
import com.mn.okhttp3.JsonGenericsSerializator;
import com.mn.okhttp3.OkHttpUtils;
import com.mn.okhttp3.callback.GenericsCallback;
import com.mn.player.audio.AudioRecorder;
import com.mn.player.audio.AudioRunable;
import com.mn.player.audio.OnAudioObserver;
import com.mn.player.audio.OnVolumeListener;
import com.mn.player.bean.PtzBaseBean;
import com.mn.player.opengl.GLFrameRenderer;
import com.mn.player.ptz.PTZControl;
import com.mn.player.utils.BitmapUtils;
import com.mn.player.utils.FileSizeUtil;
import com.mn.player.utils.Mp4RecordManager;
import com.mn.player.video.OnVideoObserver;
import com.mn.player.video.VideoBean;
import com.mn.player.video.VideoRunable;
import com.mn.sdk.R;
import com.mn.tools.AbilityTools;
import com.mn.tools.AuthorityManager;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import MNSDK.MNJni;
import MNSDK.MNKit;
import MNSDK.MNOpenSDK;
import MNSDK.MNVideoProcessor;
import MNSDK.inface.IMNVideoFace;
import MNSDK.inface.MNKitInterface;
import okhttp3.Call;

import static MNSDK.MNJni.MNSessionCtrlType.MNSESSION_CTRL_TYPE_t.MNSCT_REAL_PLAY;
import static MNSDK.MNJni.MNSessionCtrlType.MNSESSION_CTRL_TYPE_t.MNSCT_YT;
import static MNSDK.MNJni.MNTaskStatusCode.MNTASK_STATUS_CODE_t.MNTS_CANCELING;
import static MNSDK.MNJni.MNTaskStatusCode.MNTASK_STATUS_CODE_t.MNTS_CONNECTING;
import static MNSDK.MNJni.MNTaskStatusCode.MNTASK_STATUS_CODE_t.MNTS_CONNECT_FAILED;
import static MNSDK.MNJni.MNTaskStatusCode.MNTASK_STATUS_CODE_t.MNTS_DESTROYED;
import static MNSDK.MNJni.MNTaskStatusCode.MNTASK_STATUS_CODE_t.MNTS_DEVICE_OFFLINE;
import static MNSDK.MNJni.MNTaskStatusCode.MNTASK_STATUS_CODE_t.MNTS_READY;
import static MNSDK.MNJni.MNTaskStatusCode.MNTASK_STATUS_CODE_t.MNTS_RUNNING;
import static MNSDK.MNJni.MNTaskStatusCode.MNTASK_STATUS_CODE_t.MNTS_STOPPED;
import static MNSDK.MNJni.MNTaskStatusCode.MNTASK_STATUS_CODE_t.MNTS_UNINITIALIZED;
import static MNSDK.MNJni.MNTaskType.MNAV_TASK_TYPE_t.MTT_CLOUD_ALARM;
import static MNSDK.MNKit.jsonType;

/**
 * Created by Administrator on 2018/8/30 0030.
 */

public class MNPlayControl extends FrameLayout implements MNMediaInterface, OnAudioObserver, View.OnTouchListener, OnVideoObserver, OnVolumeListener, IMNVideoFace {
    private final String TAG = MNPlayControl.class.getSimpleName();
    private MNPlayControlLinstener mnPlayControlLinstener;
    private FrameLayout mainFrameLayout;
    private GLSurfaceView mGlsfView;
    private GLFrameRenderer renderer;
    public int LIVE_STATE = 0;   // -1:临界状态处于进入任务状态 0：正处于直播状态，1：正处于云端录像，2:正处于卡录像 3, 速览模式

    private Context mContext;
    private LinearLayout.LayoutParams mainParams;
    private Lock mVideoTaskLock = new ReentrantLock();
    private AudioRecorder _talkRecorder = null;      // 音频采集
    private AudioTrack _audioPlayer;
    private AudioRunable mAudioRunable;
    private VideoRunable mVideoRunable;
    private long lVideoTaskContext = 0;             // 云报警 直播 回放
    private long lVoiceTalkTaskContext = 0;         // 对讲context
    private long lCloudVideoDownTaskContext = 0;   // 云报警视频下载
    private long tfDownloadTaskContext = 0;
    private DevicesBean mDevicesBean;
    private boolean isWatchTimeed = false;
    private int defaultWidth = 0, defaultHeight = 0;
    private MainHandler myHandler = new MainHandler(this);
    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    // 直播
    private int btnTryCount = 0;
    private boolean mSupportPtz = false; // 是否支持云台控制
    private int mChinalId = 0;
    private int mStream = 0;
    private String mSn = null;
    private int mType = 1;
    private boolean mIs4GDev = false;
    private boolean mAllowChangeDomain = false;
    MNControlAction.MNCodeStream currentStream = MNControlAction.MNCodeStream.VIDEO_HD;

    public MNPlayControl(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public MNPlayControl(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    private void initView(Context context) {
        View.inflate(context, R.layout.manniu_play_control, this);
        mainFrameLayout = findViewById(R.id.manniu_framlayout);
        mContext = context;
        mGlsfView = findViewById(R.id.glsf_view); // 渲染视图

        renderer = new GLFrameRenderer();
        mGlsfView.setEGLContextClientVersion(2);
        mGlsfView.setRenderer(renderer);
        mGlsfView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);  // RENDERMODE_WHEN_DIRTY方式，这样不会让CPU一直处于高速运转状态

        mainParams = (LinearLayout.LayoutParams) mainFrameLayout.getLayoutParams();
        defaultWidth = mainParams.width;
        defaultHeight = mainParams.height;

        mGlsfView.setOnTouchListener(this);
        initLinstener();
    }

    private void watchTime() {
        if (mDevicesBean == null) {
            return;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("device_id", mDevicesBean.getId());
        String strJson = jsonObject.toJSONString();
        OkHttpUtils.postString().mediaType(jsonType)
                .url(MNOpenSDK.getDomain() + "/api/v3/user/device_share/watch_time")
                .addHeader("app_key", MNOpenSDK.getAppKey())
                .addHeader("app_secret", MNOpenSDK.getAppSecret())
                .addHeader("access_token", MNOpenSDK.getAccessToken())
                .content(strJson)
                .build()
                .execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                    }
                });
    }

    public void setRender() {
        renderer.mAngleX = 0;
        renderer.mAngleY = 0;
        renderer.xAngle = 0;
        renderer.yAngle = 0;
    }

    @Override
    public void setDeviceInfo(DevicesBean devicesBean) {
        mDevicesBean = devicesBean;
        mSn = devicesBean.getSn();

        mType = devicesBean.getType();
        mIs4GDev = AbilityTools.isFourGEnable(devicesBean);
        mAllowChangeDomain = devicesBean.getAuthority() != 0;
        MNOpenSDK.linkToDevice(mSn, mAllowChangeDomain);
    }

    @Override
    public void startLive(MNControlAction.MNCodeStream stream, int channelId) {
        btnTryCount = 0;
        mChinalId = channelId;
        cachedThreadPool.execute(() -> {
            //开启任务
            cleanTasks();
            mVideoTaskLock.lock();
            currentStream = stream;
            lVideoTaskContext = LiveTaskManager.startLiveTask(mSn, mChinalId, stream.ordinal(), mType, mIs4GDev, lVideoTaskContext);
            if (!mVideoRunable.isRunning()) {
                mVideoRunable.startRun();
            }
            lVoiceTalkTaskContext = LiveTaskManager.startSpeakerTask(mSn, mChinalId, lVoiceTalkTaskContext);
            mVideoTaskLock.unlock();
            Log.i(TAG, "-- startLive --");
        });
        if (!isWatchTimeed) {
            isWatchTimeed = true;
            watchTime();
        }
    }

    @Override
    public void tryStartLive() {
        if (mSn == null || "".equals(mSn)) {
            return;
        }
        btnTryCount++;
        if (btnTryCount >= 3) {
            int i = MNJni.GetP2pConnectionStatus();
            return;
        }
        cachedThreadPool.execute(() -> {
            MNJni.DestroyLink(mSn);
            MNJni.LinkToDevice(mSn, mAllowChangeDomain);//链接⾄设备
            myHandler.post(() -> {
                startLive(currentStream, mChinalId);
            });
        });
    }

    @Override
    public void setSurfaceSize(int width, int height) {
        mainParams.height = width;
        mainParams.width = height;
        mainFrameLayout.setLayoutParams(mainParams);
    }


    private void initLinstener() {
        MNVideoProcessor.getInstance().register(this);

        mAudioRunable = new AudioRunable(this);
        mVideoRunable = new VideoRunable(this);

        _talkRecorder = new AudioRecorder();
        _talkRecorder.setOnVolumeListener(MNPlayControl.this);
    }

    public void setPlayControlLinstener(MNPlayControlLinstener linstener) {
        mnPlayControlLinstener = linstener;
    }

    private void cleanTasks() {
        mVideoRunable.stopRun();
        mAudioRunable.stopRun();
        isDownLoadCloudVideo = false;
        isDownloadTfVideo = false;

        if (lVideoTaskContext != 0 && mVideoTaskLock.tryLock()) {
            try {
                MNJni.DestroyTask(lVideoTaskContext);
                lVideoTaskContext = 0;
            } finally {
                mVideoTaskLock.unlock();
            }
        }

        if (lVoiceTalkTaskContext != 0) {
            MNJni.DestroyTask(lVoiceTalkTaskContext);
            lVoiceTalkTaskContext = 0;
        }


        if (tfDownloadTaskContext != 0) {
            MNJni.DestroyTask(tfDownloadTaskContext);
            tfDownloadTaskContext = 0;

        }

        if (lCloudVideoDownTaskContext != 0) {
            MNJni.DestroyTask(lCloudVideoDownTaskContext);
            lCloudVideoDownTaskContext = 0;
        }
    }

    @Override
    public void startAudio() {
        int nBufSize = AudioTrack.getMinBufferSize(8000, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
        if (_audioPlayer == null) {
            _audioPlayer = new AudioTrack(AudioManager.STREAM_MUSIC, 8000, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, nBufSize, AudioTrack.MODE_STREAM);
        }
        if (_audioPlayer.getPlayState() != AudioTrack.PLAYSTATE_PLAYING) {
            Log.i(TAG, "开启音频");
            if (!mAudioRunable.isRunning()) {
                mAudioRunable.startRun();
            }
            _audioPlayer.play();
        } else {
            Log.i(TAG, "音频已经开启了");
        }
        if (mnPlayControlLinstener != null) {
            mnPlayControlLinstener.onAudioSwitchChanged(true);
        }
    }

    @Override
    public void stopAudio() {
        mAudioRunable.stopRun();
        if (_audioPlayer != null) {
            if (_audioPlayer.getPlayState() == AudioTrack.PLAYSTATE_PLAYING) {//新修改，增加判断状态
                _audioPlayer.stop();
                _audioPlayer.release();
                _audioPlayer = null;
            } else {
                _audioPlayer.release();
                _audioPlayer = null;
            }
        }
        if (mnPlayControlLinstener != null) {
            mnPlayControlLinstener.onAudioSwitchChanged(false);
        }
    }

    @Override
    public boolean startRecord(String filePath) {
        return Mp4RecordManager.getInstance().startRecord(filePath, lVideoTaskContext);
    }

    @Override
    public String stopRecord() {
        return Mp4RecordManager.getInstance().stopRecord(lVideoTaskContext);
    }


    @Override
    public void startTalk() {
        cancelYTZTimerTask();
        stopAudio();
        if (MNJni.GetTaskType(lVoiceTalkTaskContext) == MNJni.MNTaskType.MNAV_TASK_TYPE_t.MTT_P2P_VOICE_TALK.ordinal() || 0 == lVoiceTalkTaskContext) {
            MNJni.StartVoiceTalk(lVoiceTalkTaskContext);
            if (_talkRecorder != null) {
                cachedThreadPool.execute(() -> _talkRecorder.Start(lVoiceTalkTaskContext));
            }
        }

        if (mnPlayControlLinstener != null) {
            mnPlayControlLinstener.onHoldTalkSwitchChanged(true);
        }
    }


    public void talkPause() {
        MNJni.StopVoiceTalk(lVoiceTalkTaskContext);
        if (_talkRecorder != null) {
            _talkRecorder.Stop();
        }
        if (mnPlayControlLinstener != null) {
            mnPlayControlLinstener.onHoldTalkSwitchChanged(false);
        }
    }

    @Override
    public void stopTalk() {
        MNJni.StopVoiceTalk(lVoiceTalkTaskContext);
        if (_talkRecorder != null) {
            _talkRecorder.Stop();
        }
        if (mnPlayControlLinstener != null) {
            mnPlayControlLinstener.onHoldTalkSwitchChanged(false);
        }
    }

    @Override
    public boolean screenShot(String fileName) {
        File file = screenshotByPath(fileName);
        if (file != null) {
            return true;
        }
        file = screenshotByPath(fileName);
        if (file != null) {
            return true;
        }

        file = screenshotByPath(fileName);
        if (file != null) {
            return true;
        }
        return false;
    }


    @Override
    public boolean setCodeStream(MNControlAction.MNCodeStream stream) {
        if (MNJni.GetTaskType(lVideoTaskContext) == MNJni.MNTaskType.MNAV_TASK_TYPE_t.MTT_P2P_REALPLAY.ordinal()) {
            if (stream == MNControlAction.MNCodeStream.VIDEO_HD) {
                Log.i(TAG, "高清");
                mStream = 0;
                MNJni.SwitchStreamMode(lVideoTaskContext, 0, 0);
                currentStream = MNControlAction.MNCodeStream.VIDEO_HD;
                return true;
            } else {
                Log.i(TAG, "流畅");
                mStream = 1;
                MNJni.SwitchStreamMode(lVideoTaskContext, 0, 1);
                currentStream = MNControlAction.MNCodeStream.VIDEO_FUL;
                return true;
            }
        }
        return false;
    }

    @Override
    public void releasePlayer() {
        stopPlayer();
        MNVideoProcessor.getInstance().unregister(this);
    }

    @Override
    public void stopPlayer() {
        Log.i(TAG, "== stat stopPlayer ==");
        cleanTasks();
        if (_audioPlayer != null) {
            try {
                if (_audioPlayer.getPlayState() == AudioTrack.PLAYSTATE_PLAYING) {//新修改，增加判断状态
                    _audioPlayer.stop();
                    _audioPlayer.release();
                    _audioPlayer = null;
                } else {
                    if (_audioPlayer != null) {
                        _audioPlayer.release();
                        _audioPlayer = null;
                    }
                }
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
        if (_talkRecorder != null) {
            _talkRecorder.Stop();
        }
        mn_TaskStatus = MNTS_UNINITIALIZED.ordinal();
        Log.i(TAG, "== end releaseTask ==");
    }

    // 设备暂停
    @Override
    public void pausePlayer() {
        Log.i(TAG, "== mnts_Paused ==");
        if (lVideoTaskContext != 0) {
            MNJni.PauseTask(lVideoTaskContext);
        }
        mVideoRunable.stopRun();
        mAudioRunable.stopRun();
    }

    @Override
    public void resumePlayer() {
        Log.i(TAG, "== mnts_Resume ==");
        cachedThreadPool.execute(() -> {
            MNJni.ResumeTask(lVideoTaskContext);
        });

        if (!mVideoRunable.isRunning()) {
            mVideoRunable.startRun();
        }
        if (!mAudioRunable.isRunning()) {
            mAudioRunable.startRun();
        }
    }

    //播放
    @Override
    public void playTfCardVideo(String recordJson, String startTime, String endTime) {
        Log.i(TAG, "== playTfCardVideo ==" + String.format("startTime = %s , endTime = %s", startTime, endTime));
        isDownLoadCloudVideo = false;
        isDownloadTfVideo = false;

        try {
            cachedThreadPool.execute(() -> {
                MNOpenSDK.linkToDevice(mSn, mAllowChangeDomain);
                try {
                    if (lVideoTaskContext != 0) {
                        cleanTasks();
                    }
                    boolean bGetLock = mVideoTaskLock.tryLock(100, TimeUnit.MILLISECONDS);
                    if (bGetLock) {
                        MNJni.MNTaskType taskType = new MNJni.MNTaskType(MNJni.MNTaskType.MNAV_TASK_TYPE_t.MTT_P2P_SD_PLAYBACK.ordinal());
                        MNJni.MNOutputDataType dataType = new MNJni.MNOutputDataType(MNJni.MNOutputDataType.MN_OUTPUT_DATA_TYPE.MODT_FFMPEG_YUV420.ordinal());
                        lVideoTaskContext = MNJni.PrepareTask(taskType, 0);
                        // 2017-11-29 00:55:19
                        // 2017-11-29 00:56:44
                        if (recordJson == null) {
                            mVideoTaskLock.unlock();
                            return;
                        }
                        MNJni.ConfigPlayBackAutoTask(lVideoTaskContext, mSn, recordJson, startTime, endTime, -1, mChinalId, mStream, dataType);
                        int startTask = MNJni.StartTask(lVideoTaskContext);
                        mVideoRunable.startRun();
                        if (startTask == 0) {
                            LIVE_STATE = 2;// 卡录像
                        }
                        mVideoTaskLock.unlock();
                    } else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void playTfCardVideoChange(String recordJson, String startTime, String endTime) {
        isDownLoadCloudVideo = false;
        isDownloadTfVideo = false;
        mVideoRunable.stopRun();
        try {
            cachedThreadPool.execute(() -> {
                try {
                    boolean bGetLock = mVideoTaskLock.tryLock(100, TimeUnit.MILLISECONDS);
                    if (bGetLock) {
                        int changeResult = MNJni.ChangePlayBackAutoPlayTime(lVideoTaskContext, startTime, endTime, -1);
                        if (changeResult != 0) {
                            mVideoTaskLock.unlock();
                            myHandler.post(() -> {
                                playTfCardVideo(recordJson, startTime, endTime);
                            });
                            return;
                        }
                        int startTask = MNJni.StartTask(lVideoTaskContext);
                        mVideoRunable.startRun();
                        if (startTask == 0) {
                            LIVE_STATE = 2;// 卡录像
                        }
                        mVideoTaskLock.unlock();
                    } else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            mVideoRunable.stopRun();
        }
    }

    private String tfDownloadMp4Path = null;
    private boolean isDownloadTfVideo = false;

    @Override
    public void downloadTfCardVideo(String recordJson, String startTime, String stopTime, String downloadPath) {
        if (tfDownloadTaskContext != 0) {
            if (mnPlayControlLinstener != null) {
                mnPlayControlLinstener.onDownloadTaskStatus(false, 0, 0, null);
            }
            return;
        }
        if (isDownloadTfVideo) {
            if (mnPlayControlLinstener != null) {
                mnPlayControlLinstener.onDownloadTaskStatus(false, 0, 0, null);
            }
            return;
        }
        if (!TextUtils.isEmpty(recordJson) && !TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(stopTime) && !TextUtils.isEmpty(downloadPath)) {
            try {
                cachedThreadPool.execute(() -> {
                    cleanTasks();
                    tfDownloadMp4Path = downloadPath;
                    isDownloadTfVideo = true;
                    tfDownloadTaskContext = MNJni.DownloadPlayBackVideoAuto(mSn, recordJson, startTime, stopTime, -1, 0, mStream, tfDownloadMp4Path);
                    if (mnPlayControlLinstener != null) {
                        mnPlayControlLinstener.onDownloadTaskStatus(true, 0, 0, null);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                tfDownloadMp4Path = null;
                isDownloadTfVideo = false;
                if (mnPlayControlLinstener != null) {
                    mnPlayControlLinstener.onDownloadTaskStatus(false, 2, 0, null);
                }
            }
        } else {
            tfDownloadMp4Path = null;
            isDownloadTfVideo = false;
            if (mnPlayControlLinstener != null) {
                mnPlayControlLinstener.onDownloadTaskStatus(false, 2, 0, null);
            }
        }
    }

    /**
     * 下载云端报警视频
     */
    boolean isDownLoadCloudVideo = false;

    @Override
    public void downCloudAlarmVideo(String savePath, AlarmsBean alarmsBean) {//下载下载
        if (isDownLoadCloudVideo) {
            // 已经有一个下载任务
            return;
        }
        isDownLoadCloudVideo = true;
        if (alarmsBean != null && !TextUtils.isEmpty(alarmsBean.getVideoUrl())) {
            // 普通事件报警视频
            MNKit.authenticationUrl(alarmsBean.getVideoUrl(), new MNKitInterface.AuthenticationUrlCallBack() {
                @Override
                public void onAuthenticationUrlSuc(AuthenticationBean url) {
                    if (url.getCode() == 2000 && url.getUrls() != null && url.getUrls().size() > 0) {
                        AuthenticationBean.UrlsBean urlsBean = url.getUrls().get(0);
                        // 授权成功，开始播放云视频（Authorization successful, start playing cloud video）
                        MNJni.MNTaskType taskType = new MNJni.MNTaskType(MNJni.MNTaskType.MNAV_TASK_TYPE_t.MTT_CLOUD_ALARM.ordinal());
                        MNJni.MNOutputDataType dataType = new MNJni.MNOutputDataType(MNJni.MNOutputDataType.MN_OUTPUT_DATA_TYPE.MODT_FFMPEG_YUV420.ordinal());
                        MNJni.MNCloudTaskType cloudTaskType = new MNJni.MNCloudTaskType(MNJni.MNCloudTaskType.MN_CLOUD_TASK_TYPE.MCTT_DOWNLOAD.ordinal());
                        lCloudVideoDownTaskContext = MNJni.PrepareTask(taskType, 0);
                        MNJni.ConfigCloudAlarmTask(lCloudVideoDownTaskContext, urlsBean.getPresignedurl(), 0, urlsBean.getFile_size(), dataType, cloudTaskType);
                        if (Mp4RecordManager.getInstance().startRecord(savePath, lCloudVideoDownTaskContext)) {
                            // 创建下载任务成功
                            MNJni.StartTask(lCloudVideoDownTaskContext);
                            if (mnPlayControlLinstener != null) {
                                mnPlayControlLinstener.onDownloadTaskStatus(true, 0, 0, null);
                            }
                        } else {
                            // 创建下载任务失败
                            Mp4RecordManager.getInstance().stopRecord(lCloudVideoDownTaskContext);
                            if (0 == MNJni.DestroyTask(lCloudVideoDownTaskContext)) {
                                lCloudVideoDownTaskContext = 0;
                                if (mnPlayControlLinstener != null) {
                                    mnPlayControlLinstener.onDownloadTaskStatus(false, 2, 0, null);
                                }
                            }
                            isDownLoadCloudVideo = false;
                        }
                    } else {
                        Mp4RecordManager.getInstance().stopRecord(lCloudVideoDownTaskContext);
                        if (0 == MNJni.DestroyTask(lCloudVideoDownTaskContext)) {
                            lCloudVideoDownTaskContext = 0;
                            if (mnPlayControlLinstener != null) {
                                mnPlayControlLinstener.onDownloadTaskStatus(false, 2, 0, null);
                            }
                        }
                        isDownLoadCloudVideo = false;
                    }
                }

                @Override
                public void onAuthenticationUrlFailed(String msg) {
                    if (mnPlayControlLinstener != null) {
                        mnPlayControlLinstener.onDownloadTaskStatus(false, 2, 0, null);
                    }
                    isDownLoadCloudVideo = false;
                }
            });
        } else if (alarmsBean != null && !TextUtils.isEmpty(alarmsBean.getRecordUrl())) {
            String alarmJson = new Gson().toJson(alarmsBean);
            Log.i(TAG, "下载24小时云录像：" + alarmJson);
            try {
                // 24小时云录像下载
                MNJni.MNTaskType taskType = new MNJni.MNTaskType(MNJni.MNTaskType.MNAV_TASK_TYPE_t.MTT_CLOUD_ALARM.ordinal());
                MNJni.MNOutputDataType dataType = new MNJni.MNOutputDataType(MNJni.MNOutputDataType.MN_OUTPUT_DATA_TYPE.MODT_FFMPEG_YUV420.ordinal());
                MNJni.MNCloudTaskType cloudTaskType = new MNJni.MNCloudTaskType(MNJni.MNCloudTaskType.MN_CLOUD_TASK_TYPE.MCTT_DOWNLOAD.ordinal());
                lCloudVideoDownTaskContext = MNJni.PrepareTask(taskType, 0);
                MNJni.MN24CloudRecordInfo recordInfo = new MNJni.MN24CloudRecordInfo(alarmsBean.getRecordUrl(), alarmsBean.getvStartLocalTime(), alarmsBean.getvEndLocalTime());
                MNJni.MN24CloudRecordInfo[] recordInfos = {recordInfo};
                MNJni.Config24HCloudRecordAutoTask(lCloudVideoDownTaskContext, recordInfos, alarmsBean.getvStartLocalTime(), alarmsBean.getvEndLocalTime(), dataType, cloudTaskType);

                if (Mp4RecordManager.getInstance().startRecord(savePath, lCloudVideoDownTaskContext)) {
                    // 创建下载任务成功
                    MNJni.StartTask(lCloudVideoDownTaskContext);
                    if (mnPlayControlLinstener != null) {
                        mnPlayControlLinstener.onDownloadTaskStatus(true, 0, 0, null);
                    }
                } else {
                    // 创建下载任务失败
                    Mp4RecordManager.getInstance().stopRecord(lCloudVideoDownTaskContext);
                    if (0 == MNJni.DestroyTask(lCloudVideoDownTaskContext)) {
                        lCloudVideoDownTaskContext = 0;
                        myHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (mnPlayControlLinstener != null)
                                    mnPlayControlLinstener.onDownloadTaskStatus(false, 2, 0, null);
                            }
                        });
                    }
                    isDownLoadCloudVideo = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isShaking = false;// 是否正在摇头

    @Override
    public void ptzControl(MNControlAction.MNDirection direction, int channelId) {
        if (direction != MNControlAction.MNDirection.DIRECTION_CENTER && mDevicesBean != null && mDevicesBean.getFrom() != null && !AuthorityManager.isHadPTZControlAuthority(mDevicesBean.getSn())) {
            if (mnPlayControlLinstener != null) {
                mnPlayControlLinstener.onAuthorityChanged(AuthorityManager.getDevAuthority(mDevicesBean.getSn()));
            }
            return;
        }
        cachedThreadPool.execute(() -> {
            if (direction == MNControlAction.MNDirection.DIRECTION_UP) {
                isShaking = true;
                PTZControl.PTZUp(lVideoTaskContext, 0);
            } else if (direction == MNControlAction.MNDirection.DIRECTION_LEFT) {
                isShaking = true;
                PTZControl.PTZleft(lVideoTaskContext, 0);
            } else if (direction == MNControlAction.MNDirection.DIRECTION_RIGHT) {
                isShaking = true;
                PTZControl.PTZright(lVideoTaskContext, 0);
            } else if (direction == MNControlAction.MNDirection.DIRECTION_DOWN) {
                isShaking = true;
                PTZControl.PTZdown(lVideoTaskContext, 0);
            } else if (direction == MNControlAction.MNDirection.DIRECTION_CENTER) {
                isShaking = false;
                PTZControl.PTZstop(lVideoTaskContext, 0);
            } else if (direction == MNControlAction.MNDirection.DIRECTION_DOWN_RIGHT) {
                //右下
                isShaking = true;
                PTZControl.PTZright_down(lVideoTaskContext, 0);
            } else if (direction == MNControlAction.MNDirection.DIRECTION_UP_RIGHT) {
                //右上
                isShaking = true;
                PTZControl.PTZright_up(lVideoTaskContext, 0);
            } else if (direction == MNControlAction.MNDirection.DIRECTION_UP_LEFT) {
                //左上
                isShaking = true;
                PTZControl.PTZleft_up(lVideoTaskContext, 0);
            } else if (direction == MNControlAction.MNDirection.DIRECTION_DOWN_LEFT) {
                //左下
                isShaking = true;
                PTZControl.PTZleft_down(lVideoTaskContext, 0);
            }
        });
    }

    //操作云台只过滤声音
    public void stopPTZAudio() {
        mAudioRunable.stopRun();
        if (_audioPlayer != null) {
            if (_audioPlayer.getPlayState() == AudioTrack.PLAYSTATE_PLAYING) {//新修改，增加判断状态
                _audioPlayer.stop();
                _audioPlayer.release();
                _audioPlayer = null;
            } else {
                _audioPlayer.release();
                _audioPlayer = null;
            }
        }
    }

    @Override
    public void supportPTZControl(boolean support) {
        mSupportPtz = support;
    }

    @Override
    public void playCloudVideo(AlarmsBean alarmsBean) {
        MNKit.authenticationUrl(alarmsBean.getVideoUrl(), new MNKitInterface.AuthenticationUrlCallBack() {
            @Override
            public void onAuthenticationUrlSuc(AuthenticationBean url) {
                if (url.getCode() == 2000 && url.getUrls() != null && url.getUrls().size() > 0) {
                    AuthenticationBean.UrlsBean urlsBean = url.getUrls().get(0);
                    // 授权成功，开始播放云视频（Authorization successful, start playing cloud video）
                    playCloudVideo(urlsBean.getPresignedurl(), urlsBean.getFile_size());
                } else {
                    if (mnPlayControlLinstener != null) {
                        mnPlayControlLinstener.OnTaskStatus(MTT_CLOUD_ALARM.ordinal(), MNTS_CONNECT_FAILED.ordinal(), 0);
                    }
                }
            }

            @Override
            public void onAuthenticationUrlFailed(String msg) {
                if (mnPlayControlLinstener != null) {
                    mnPlayControlLinstener.OnTaskStatus(MTT_CLOUD_ALARM.ordinal(), MNTS_CONNECT_FAILED.ordinal(), 0);
                }
            }
        });
    }

    private void playCloudVideo(String presignedUrl, long fileSize) {
        String lCloudVideoUrl = presignedUrl.replace("https", "http");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    boolean bGetLock = mVideoTaskLock.tryLock(100, TimeUnit.MILLISECONDS);
                    if (bGetLock) {
                        stopPlayer();
                        if (0 == lVideoTaskContext) {
                            MNJni.MNTaskType taskType = new MNJni.MNTaskType(MNJni.MNTaskType.MNAV_TASK_TYPE_t.MTT_CLOUD_ALARM.ordinal());
                            MNJni.MNOutputDataType dataType = new MNJni.MNOutputDataType(MNJni.MNOutputDataType.MN_OUTPUT_DATA_TYPE.MODT_FFMPEG_YUV420.ordinal());
                            MNJni.MNCloudTaskType cloudTaskType = new MNJni.MNCloudTaskType(MNJni.MNCloudTaskType.MN_CLOUD_TASK_TYPE.MCTT_PLAY.ordinal());
                            lVideoTaskContext = MNJni.PrepareTask(taskType, 0);
                            MNJni.ConfigCloudAlarmTask(lVideoTaskContext, lCloudVideoUrl, 0, fileSize, dataType, cloudTaskType);
                            MNJni.StartTask(lVideoTaskContext);
                            if (!mVideoRunable.isRunning()) {
                                mVideoRunable.startRun();
                            }
                            myHandler.post(() -> {
                                if (mnPlayControlLinstener != null) {
                                    mnPlayControlLinstener.OnTaskStatus(MNJni.MNTaskType.MNAV_TASK_TYPE_t.MTT_CLOUD_ALARM.ordinal(), MNTS_CONNECTING.ordinal(), 0);
                                }
                            });
                        }
                        mVideoTaskLock.unlock();
                    } else {
                        myHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (mnPlayControlLinstener != null) {
                                    mnPlayControlLinstener.OnTaskStatus(MNJni.MNTaskType.MNAV_TASK_TYPE_t.MTT_CLOUD_ALARM.ordinal(), MNTS_UNINITIALIZED.ordinal(), 0);
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void config24HCloudRecordAutoTask(List<Record24Bean> record24Beans, String vStartLoaclTime, String vEndLoaclTime) {
        try {
            cachedThreadPool.execute(() -> {
                if (lVideoTaskContext != 0) {
                    cleanTasks();
                }
                try {
                    boolean bGetLock = mVideoTaskLock.tryLock(100, TimeUnit.MILLISECONDS);
                    if (bGetLock) {
                        if (0 == lVideoTaskContext) {
                            MNJni.MNTaskType taskType = new MNJni.MNTaskType(MNJni.MNTaskType.MNAV_TASK_TYPE_t.MTT_CLOUD_ALARM.ordinal());
                            MNJni.MNOutputDataType dataType = new MNJni.MNOutputDataType(MNJni.MNOutputDataType.MN_OUTPUT_DATA_TYPE.MODT_FFMPEG_YUV420.ordinal());
                            MNJni.MNCloudTaskType cloudTaskType = new MNJni.MNCloudTaskType(MNJni.MNCloudTaskType.MN_CLOUD_TASK_TYPE.MCTT_PLAY.ordinal());
                            lVideoTaskContext = MNJni.PrepareTask(taskType, 0);

                            MNJni.MN24CloudRecordInfo[] recordInfos = new MNJni.MN24CloudRecordInfo[record24Beans.size()];
                            for (int i = 0; i < record24Beans.size(); i++) {
                                Record24Bean record24Bean = record24Beans.get(i);
                                MNJni.MN24CloudRecordInfo recordInfo = new MNJni.MN24CloudRecordInfo(record24Bean.getVideoUrl(), record24Bean.getStartTime(), record24Bean.getEndTime());
                                recordInfos[i] = recordInfo;
                            }
                            MNJni.Config24HCloudRecordAutoTask(lVideoTaskContext, recordInfos, vStartLoaclTime, vEndLoaclTime, dataType, cloudTaskType);
                            MNJni.StartTask(lVideoTaskContext);
                            if (!mVideoRunable.isRunning()) {
                                mVideoRunable.startRun();
                            }
                        }
                        mVideoTaskLock.unlock();
                    } else {
                        Log.e(TAG, "config24HCloudRecordAutoTask 获取安全锁失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void change24CloudRecordAutoPlayTime(List<Record24Bean> record24Beans, String vStartLoaclTime, String vEndLoaclTime) {
        try {
            cachedThreadPool.execute(() -> {
                if (lVideoTaskContext != 0) {
                    mVideoRunable.stopRun();
                    int result = MNJni.Change24CloudRecordAutoPlayTime(lVideoTaskContext, vStartLoaclTime, vEndLoaclTime);
                    if (result != 0) {
                        config24HCloudRecordAutoTask(record24Beans, vStartLoaclTime, vEndLoaclTime);
                    } else {
                        myHandler.postDelayed(() -> {
                            mVideoRunable.startRun();
                        }, 18);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void OnVideoDataByte(long lTaskContext, int nChannelId, long userdata, int dataType, byte[] data, int nDataLen, byte[] y, byte[] u, byte[] v, int nWidth, int nHeight, int nYStride, int nUStride, int nVStride, int nFps, int nSliceType, int nYear, int nMonth, int nDay, int nHour, int nMinute, int nSecond, long lNetworkFlowPerSecond, long lTotalFlow) {
        if (lTaskContext != lVideoTaskContext || mVideoRunable == null) {
            return;
        }
        mVideoRunable.writeVideo(lTaskContext, nChannelId, userdata, dataType, data, nDataLen, ByteBuffer.wrap(y), ByteBuffer.wrap(u), ByteBuffer.wrap(v),
                nWidth, nHeight, nYStride, nUStride, nVStride, nFps, nSliceType,
                nYear, nMonth, nDay, nHour, nMinute, nSecond, lNetworkFlowPerSecond, lTotalFlow);
    }

    @Override
    public void onRunableVideoData(VideoBean videoBean) {
        if (renderer == null || videoBean == null) return;
        try {
            renderer.update(videoBean.getnWidth(), videoBean.getnHeight());//可能会产出内存溢出问题以及空指针
            renderer.update(videoBean.getY(), videoBean.getU(), videoBean.getV());
            mGlsfView.requestRender();
            myHandler.post(() -> {
                if (mnPlayControlLinstener != null) {
                    mnPlayControlLinstener.runSpeed(videoBean.getnYear(), videoBean.getnMonth(), videoBean.getnDay(), videoBean.getnHour(), videoBean.getnMinute(), videoBean.getnSecond(), videoBean.getlNetworkFlowPerSecond());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnAudioDataByte(long lTaskContext, int nChannelId, long userdata, byte[] InData, int nDataLen, int nEncodeType) {
        if (lTaskContext != lVideoTaskContext || mAudioRunable == null) {
            return;
        }
        mAudioRunable.writeAudio(lTaskContext, nChannelId, userdata, InData, nDataLen, nEncodeType);
    }

    @Override
    public void onRunableAudioData(long lTaskContext, int nChannelId, long userdata, byte[] InData, int nDataLen, int nEncodeType) {
        if (ByteBuffer.wrap(InData).remaining() > 0) {
            if (_audioPlayer != null && _audioPlayer.getPlayState() == AudioTrack.PLAYSTATE_PLAYING) {
                _audioPlayer.write(InData, 0, InData.length);
            }
        }
    }

    private int mn_TaskStatus = 0;

    public int getTaskStatus() {
        return mn_TaskStatus;
    }

    public long getVideoTaskContext() {
        return lVideoTaskContext;
    }

    private boolean printPlaiing = true;

    @Override
    public void OnTaskStatus(long lTaskContext, long userdata, int nTaskStatus, float fProgress) {
        myHandler.post(new Runnable() {
            @Override
            public void run() {
                // 此时切换到在主线程
                if (mnPlayControlLinstener != null) {
                    mnPlayControlLinstener.OnTaskStatus(MNJni.GetTaskType(lTaskContext), nTaskStatus, fProgress);
                }

                if (lTaskContext == lVideoTaskContext) {
                    mn_TaskStatus = nTaskStatus;
                } else if (lTaskContext == lCloudVideoDownTaskContext) {
                    // 云端报警视频下载

                    if (nTaskStatus == MNTS_READY.ordinal() || nTaskStatus == MNTS_RUNNING.ordinal()) {
                        // 下载中
                        if (mnPlayControlLinstener != null) {
                            mnPlayControlLinstener.onDownloadTaskStatus(true, 1, fProgress, null);
                        }
                    } else if (nTaskStatus == MNTS_DEVICE_OFFLINE.ordinal() || nTaskStatus == MNTS_CONNECT_FAILED.ordinal()) {
                        // 下载失败
                        isDownLoadCloudVideo = false;
                        if (nTaskStatus == MNTS_CONNECT_FAILED.ordinal()) {
                            MNJni.DestroyTask(lCloudVideoDownTaskContext);
                            lCloudVideoDownTaskContext = 0;
                        }
                        if (mnPlayControlLinstener != null) {
                            mnPlayControlLinstener.onDownloadTaskStatus(true, 2, fProgress, null);
                        }
                    } else if (nTaskStatus == MNTS_STOPPED.ordinal()) {
                        // 下载成功
                        isDownLoadCloudVideo = false;
                        String fileName = Mp4RecordManager.getInstance().stopRecord(lCloudVideoDownTaskContext);
                        if (fileName != null) {
                            if (mnPlayControlLinstener != null) {
                                mnPlayControlLinstener.onDownloadTaskStatus(true, 3, fProgress, fileName);
                            }
                        } else {
                            if (mnPlayControlLinstener != null) {
                                mnPlayControlLinstener.onDownloadTaskStatus(true, 2, fProgress, null);
                            }
                        }
                        MNJni.DestroyTask(lCloudVideoDownTaskContext);
                        lCloudVideoDownTaskContext = 0;
                    } else if (nTaskStatus == MNTS_DESTROYED.ordinal() || nTaskStatus == MNTS_CANCELING.ordinal()) {
                        // 取消下载
                        if (!isDownLoadCloudVideo) {
                            // 下载成功后DestroyTask()或者链接失败后DestroyTask()，不在回调取消
                            return;
                        }
                        isDownLoadCloudVideo = false;
                        if (mnPlayControlLinstener != null) {
                            mnPlayControlLinstener.onDownloadTaskStatus(true, 4, fProgress, null);
                        }
                    }
                } else if (lTaskContext == tfDownloadTaskContext) {
                    // TODO 下载卡录像
                    if (nTaskStatus == MNTS_READY.ordinal() || nTaskStatus == MNTS_RUNNING.ordinal()) {
                        // 正在下载
                        if (mnPlayControlLinstener != null) {
                            mnPlayControlLinstener.onDownloadTaskStatus(true, 1, fProgress, null);
                        }
                    } else if (nTaskStatus == MNTS_DEVICE_OFFLINE.ordinal() || nTaskStatus == MNTS_CONNECT_FAILED.ordinal()) {
                        // 下载失败
                        isDownloadTfVideo = false;
                        MNJni.DestroyTask(tfDownloadTaskContext);
                        tfDownloadTaskContext = 0;
                        if (mnPlayControlLinstener != null) {
                            mnPlayControlLinstener.onDownloadTaskStatus(true, 2, fProgress, null);
                        }
                    } else if (nTaskStatus == MNTS_STOPPED.ordinal()) {
                        // 下载成功
                        isDownloadTfVideo = false;
                        if (mnPlayControlLinstener != null) {
                            mnPlayControlLinstener.onDownloadTaskStatus(true, 3, fProgress, tfDownloadMp4Path);
                        }
                        MNJni.DestroyTask(tfDownloadTaskContext);
                        tfDownloadTaskContext = 0;
                        tfDownloadMp4Path = null;
                    } else if (nTaskStatus == MNTS_DESTROYED.ordinal() || nTaskStatus == MNTS_CANCELING.ordinal()) {
                        // 取消下载
                        if (!isDownloadTfVideo) {
                            return;
                        }
                        isDownloadTfVideo = false;
                        tfDownloadMp4Path = null;
                        if (mnPlayControlLinstener != null) {
                            mnPlayControlLinstener.onDownloadTaskStatus(true, 4, fProgress, null);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void OnSessionCtrl(long lTaskContext, int nChannelId, int eSessionCtrlType, String data, int length) {
        Log.i(TAG, "-- OnSessionCtrl -- data = " + data);
        if (mnPlayControlLinstener == null) return;
        String mSn = MNJni.GetTaskDeviceSn(lTaskContext);
        if (lVideoTaskContext != lTaskContext) return;
        if (eSessionCtrlType != MNSCT_YT.ordinal()) {
            return;
        }
        try {
            if (eSessionCtrlType == MNSCT_REAL_PLAY.ordinal() && !TextUtils.isEmpty(data)) {
                myHandler.post(() -> {
                    if (mnPlayControlLinstener != null) {
                        mnPlayControlLinstener.onNvrSessionView(data);
                    }
                });
                return;
            }

            PtzBaseBean ptzBean = new Gson().fromJson(data, PtzBaseBean.class);
            if (ptzBean == null) return;
            myHandler.post(() -> {
                if (!ptzBean.isResult()) {
                    return;
                }
                if (ptzBean.getValue() == 0 || ptzBean.getValue() == 2 || ptzBean.getValue() == 4 || ptzBean.getValue() == 6 || ptzBean.getValue() == 25 || ptzBean.getValue() == 93 || ptzBean.getValue() == 90 || ptzBean.getValue() == 92 || ptzBean.getValue() == 91) {
                    // 到边界了
                    if (mnPlayControlLinstener != null) {
                        mnPlayControlLinstener.onToborder();
                    }
                } else if (ptzBean.getValue() == 30 || ptzBean.getValue() == 32 || ptzBean.getValue() == 34 || ptzBean.getValue() == 36 || ptzBean.getValue() == 38 || ptzBean.getValue() == 40) {
                    // 设置摇头机预置点
                    String pointId = "0";
                    if (ptzBean.getValue() == 30) {
                        pointId = "0";
                    } else if (ptzBean.getValue() == 32) {
                        pointId = "1";
                    } else if (ptzBean.getValue() == 34) {
                        pointId = "2";
                    } else if (ptzBean.getValue() == 36) {
                        pointId = "3";
                    } else if (ptzBean.getValue() == 38) {
                        pointId = "4";
                    } else if (ptzBean.getValue() == 40) {
                        pointId = "5";
                    }
                    String finalPointId = pointId;
                    File file = getFavoritesImage();
                    if (file == null) {
                        if (mnPlayControlLinstener != null) {
                            mnPlayControlLinstener.onSetSessionCtrl(false, finalPointId);
                        }
                    } else {
                        MNKit.saveFavoritePoint(mPointName, pointId, mDevicesBean.getId(), file, new MNKitInterface.SaveFavoritePointsCallBack() {
                            @Override
                            public void onSaveFavoritePointsSuc(FavoritePointSaveBean bean) {
                                if (mnPlayControlLinstener != null) {
                                    mnPlayControlLinstener.onSetSessionCtrl(ptzBean.isResult(), finalPointId);
                                }
                            }

                            @Override
                            public void onSaveFavoritePointsFailed(String msg) {
                                if (mnPlayControlLinstener != null) {
                                    mnPlayControlLinstener.onSetSessionCtrl(false, finalPointId);
                                }
                            }
                        });
                    }
                } else if (ptzBean.getValue() == 70 || ptzBean.getValue() == 71 || ptzBean.getValue() == 72 || ptzBean.getValue() == 73 || ptzBean.getValue() == 74 || ptzBean.getValue() == 75) {
                    // 删除摇头机预置点
                    if (mnPlayControlLinstener != null) {
                        mnPlayControlLinstener.onDelSessionCtrl(ptzBean.isResult(), String.valueOf(ptzBean.getValue() - 70));
                    }
                } else if (ptzBean.getValue() == 31 || ptzBean.getValue() == 33 || ptzBean.getValue() == 35 || ptzBean.getValue() == 37 || ptzBean.getValue() == 39 || ptzBean.getValue() == 41) {
                    // 控制摇头机摇动到预置点
                    String pointId = "0";
                    if (ptzBean.getValue() == 31) {
                        pointId = "0";
                    } else if (ptzBean.getValue() == 33) {
                        pointId = "1";
                    } else if (ptzBean.getValue() == 35) {
                        pointId = "2";
                    } else if (ptzBean.getValue() == 37) {
                        pointId = "3";
                    } else if (ptzBean.getValue() == 39) {
                        pointId = "4";
                    } else if (ptzBean.getValue() == 41) {
                        pointId = "5";
                    }
                    if (mnPlayControlLinstener != null) {
                        mnPlayControlLinstener.onGotoSessionCtrl(ptzBean.isResult(), pointId);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String getDiskCachePath(Context context) {
        try {
            String cachePath = "";
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
                cachePath = context.getExternalCacheDir().getPath() + "/favorites/";
            } else {
                cachePath = context.getCacheDir().getPath() + "/favorites/";
            }
            if (TextUtils.isEmpty(cachePath)) {
                return "";
            }
            File file = new File(cachePath);
            if (file != null && file.isDirectory()) {
                File[] flies = file.listFiles();
                for (File lfile : flies) {
                    lfile.delete();
                }
            }
            if (file != null && !file.exists()) {
                file.mkdirs();
            }
            return cachePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private File getFavoritesImage() {
        String fileName = getDiskCachePath(getContext()) + System.currentTimeMillis() + ".bmp";
        File file = screenshotByPath(fileName);
        if (file != null) {
            return file;
        }
        file = screenshotByPath(fileName);
        if (file != null) {
            return file;
        }

        file = screenshotByPath(fileName);
        if (file != null) {
            return file;
        }

        return null;
    }

    private File screenshotByPath(String fileName) {
        int[] nWidth = new int[1];
        int[] nHeight = new int[1];
        try {
            ByteBuffer yuv = renderer.getYUVByteBuffer(nWidth, nHeight);
            if (yuv == null) {
                return null;
            }
            ByteBuffer rgb565 = ByteBuffer.allocate(nWidth[0] * nHeight[0] * 3 + 54);
            MNJni.YUV420P2BMP(nWidth[0], nHeight[0], yuv.array(), rgb565.array());
            BitmapUtils.getFileByBytes(rgb565.array(), fileName);
            BitmapUtils.saveBitmaptoFile(BitmapUtils.getSmallBitmap(fileName, 480, 800), fileName);
            File file = new File(fileName);

            if (file.isFile() && file.exists()) {
                long filesiz = FileSizeUtil.getFileSize(file);
                double sizekb = FileSizeUtil.FormetFileSize(filesiz, FileSizeUtil.SIZETYPE_KB);
                Log.i(TAG, "fileName : file.size() : " + filesiz + " , " + sizekb);
                if ((sizekb <= 5) || sizekb == 6.93 || sizekb == 12.23 || sizekb == 12.53 || sizekb == 32.49 || sizekb == 14.68 || sizekb == 5.69 || sizekb == 5.55 || sizekb == 12 || sizekb == 12.2 || sizekb == 20.48) {
                    return null;
                } else {
                    return file;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private int mode = 0;// 触控点的个数
    float oldDist = 0;
    /**
     * 放缩比例/移动比例
     */
    public static float change = 0.1f;
    static final int NONE = 0;
    static final int DRAG = 1;       //拖动中
    static final int ZOOM = 2;     //缩放中
    float base = 1.5f;//缩放基数
    float tempX;//滑动到最左边：缩小时直接到原始值
    private float mPreviousY;//拖动中---记录触控笔位置
    private float mPreviousX;
    private float mDownX;//初始状态  向上-缩放    坐标点信息
    private float mDownY;
    private float mCurrentPosX;//初始状态  向上-缩放    坐标点信息
    private float mCurrentPosY;
    private int flag = 0;//初始状态  标识
    private float mDownX2;
    private float MaxZomm = 4;//缩放最大值


    public float caluDist(MotionEvent event) {
        float dx = event.getX(0) - event.getX(1);
        float dy = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    int _nClickedCount = 0;
    View _lastClieckView = null;

    public void onTouchClick(View v) {
        if (v.equals(_lastClieckView)) {
            //主要控制双击事件 单个视频可以注释
            _nClickedCount++;
        } else {
            _nClickedCount = 1;
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                selected(_nClickedCount);
                _nClickedCount = 0;
                _lastClieckView = null;
            }, 300);

        }
        _lastClieckView = v;
    }

    int _clickFlag = 0;

    public int deviceType = 0;//判断是否是180度摄像机 -1:180 0:IPC

    public void selected(int nClickedCount) {
        //处理双击事件
        if (nClickedCount > 1 && deviceType == 0) {
            //跳转到指点的位置
            if (renderer._is180Open != 0 && _clickFlag == 0) {
                _clickFlag = 1;
                renderer._is180Open = 0;
                renderer.changeMatrix();
                MaxZomm = 2.5f;
            } else if (renderer._is180Open != 1) {
                renderer._is180Open = 1;
                renderer.changeMatrix();
                MaxZomm = 4;
                _clickFlag = 0;
            }
        }
//        if (nClickedCount == 1) {//单击
//            if (mPlayType != 0) return;
//            if (VIDEO_MODEL == MNControlAction.MN_VIDEO_MODEL_LIVE && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {// 直播 并且是横屏
//
//            }
//        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN://按下
                mode = 1;
                mDownX = event.getX();
                mDownY = event.getY();
                if (event.getPointerCount() == 2) {
                    mode = ZOOM;
                } else if (event.getPointerCount() == 1) {
                    mode = DRAG;
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN://非第一个处抹点按下（）
                if (caluDist(event) > 10f) {
                    mode = ZOOM;
                    oldDist = caluDist(event);
                }
                break;
            case MotionEvent.ACTION_POINTER_UP://非第一个触摸点抬起
                mode = NONE;
                break;
            case MotionEvent.ACTION_UP://抬起
                if (mSupportPtz) {
                    startYTZTimerTask();
                    isTouch = false;
                    if (ptzFlag != 0) {
                        ptzControl(MNControlAction.MNDirection.DIRECTION_CENTER, mChinalId);
                    }
                    ptzFlag = 0;
                }
                if (mode == DRAG) {
                    mDownX2 = event.getX();
                    if (Math.abs(mDownX - mDownX2) < 6) {
                        onTouchClick(v);
                    }
                    if (flag == 1) {
                        renderer.xAngle = 0;
                        renderer.yAngle = 0;
                        renderer.scaleX = renderer.scaleInitX;
                        renderer.scaleY = renderer.scaleInitY;
                        flag = 0;
                    }
                    //180设备特殊处理
                    if (deviceType == -1 && renderer._is180Open == 0) {
                        if (renderer.scaleX > 4f) {
                            base = 85f;
                        } else if (renderer.scaleX > 3) {
                            base = 80f;
                        } else if (renderer.scaleX > 2) {
                            base = 70f;
                        } else if (renderer.scaleX > 1.5) {
                            base = 60f;
                        } else {
                            base = 40f;
                        }
                        if (renderer.mAngleX > base) {//已左移到最左边
                            renderer.mAngleX = base;
                            if (renderer.mAngleY < -base) {//左上
                                if (base >= 70f) base = 40f;
                                renderer.mAngleY = -base;
                            }
                            if (renderer.mAngleY > base) {//右上
                                if (base >= 70f) base = 40f;
                                renderer.mAngleY = base;
                            }
                        } else if (renderer.mAngleX < -base) {//最右边
                            renderer.mAngleX = -base;
                            if (renderer.mAngleY > base) {//右上
                                if (base >= 70f) base = 40f;
                                renderer.mAngleY = base;
                            }
                            if (renderer.mAngleY < -base) {
                                if (base >= 70f) base = 40f;
                                renderer.mAngleY = -base;
                            }
                        } else if (renderer.mAngleY > base) {//最上面
                            renderer.mAngleY = base;
                        } else if (renderer.mAngleY < -base) {//最下面
                            if (base >= 70f) base = 40f;
                            renderer.mAngleY = -base;
                        }
                    } else {
                        //IPC设备
                        if (renderer.scaleX > 4f) {
                            base = 3.5f;
                        } else if (renderer.scaleX > 3f) {
                            base = 2.5f;
                        } else if (renderer.scaleX > 2f) {
                            base = 1.7f;
                        } else if (renderer.scaleX > 1.5f) {
                            base = 1.2f;
                        } else {
                            base = 0.7f;
                        }
                        if (renderer.xAngle > base) {//已左移到最左边
                            tempX = renderer.xAngle;
                            renderer.xAngle = base;
                            if (renderer.yAngle < -base)//左上
                                renderer.yAngle = -base;
                            if (renderer.yAngle > base)//右上
                                renderer.yAngle = base;
                        } else if (renderer.xAngle < -base) {//最右边
                            tempX = renderer.xAngle;
                            renderer.xAngle = -base;
                            if (renderer.yAngle > base)//右上
                                renderer.yAngle = base;
                            if (renderer.yAngle < -base)
                                renderer.yAngle = -base;
                        } else if (renderer.yAngle > base) {//已左移到最下边
                            renderer.yAngle = base;
                        } else if (renderer.yAngle < -base) {//最上边
                            renderer.yAngle = -base;
                        }
                    }

                }
                break;
            case MotionEvent.ACTION_MOVE://移动
                if (mode == ZOOM) {//双指
                    float newDist = caluDist(event);
                    float gapLenght = newDist - oldDist;
                    if (gapLenght == 0) {
                        break;
                    } else if (Math.abs(gapLenght) > 15f) {
                        if (gapLenght > 0) { //放大ing
                            if (renderer.scaleX > MaxZomm) {

                            } else {
                                renderer.scaleX += change;
                                renderer.scaleY += change;
                                MaxZomm = 2.5f;
                                if (deviceType == -1) {
                                    if (renderer._is180Open != 0) {
                                        renderer._is180Open = 0;
                                        renderer.changeMatrix();
                                        MaxZomm = 3;
                                    }
                                    renderer.wheelEvent(gapLenght);
                                }
                            }
                        } else {  //缩小ing
                            if (renderer.scaleX <= renderer.scaleInitX) {
                                //优化修改：180缩放到最小时 平滑缩放
                                renderer.xAngle = 0;
                                renderer.yAngle = 0;
                                if (deviceType == -1 && renderer._is180Open != 1) {
                                    renderer.scaleX -= change;
                                    renderer.scaleY -= change;
                                    if (renderer.scaleX < 1) {
                                        renderer._is180Open = 1;
                                        renderer.changeMatrix();
                                        MaxZomm = 4;
                                        _clickFlag = 0;
                                    }
                                } else {
                                    renderer.scaleX = renderer.scaleInitX;
                                    renderer.scaleY = renderer.scaleInitY;
                                }
                                break;
                            } else {
                                if ((tempX > base || tempX < -base) && (base == 3 || base == 1.5)) {
                                    renderer.xAngle = 0;
                                    renderer.yAngle = 0;
                                    renderer.scaleX = renderer.scaleInitX;
                                    renderer.scaleY = renderer.scaleInitY;
                                    tempX = 0;
                                    break;
                                }
                                ///缩小
                                renderer.narrow(0.1f);//缩放保持不出现黑边
                                if (deviceType == -1) renderer.wheelEvent(gapLenght);
                            }
                        }
                        oldDist = newDist;
                    }
                } else if (mode == DRAG) {//单指
                    //  LogUtil.i("scaleX","renderer.scaleX==="+renderer.scaleX+"====y:"+y+",,,mPreviousY:====="+mPreviousY);
                    if (renderer.scaleX > 1) {//拖动平移
                        float dy = y - mPreviousY;// 计算触控笔Y位移
                        float dx = x - mPreviousX;// 计算触控笔X位移
                        renderer.drag(dx, dy);//放大不出现黑边，不超过视频大小
                        flag = 0;
                        //  LogUtil.i("scaleX","dx:=="+dx+",,,dy==="+dy);
                    } else if (renderer.scaleX <= 1) {
                        if (flag == 0) {
                            flag = 1;
                        }
                        if (mSupportPtz) {//如果是小黄人，这里做云台操作并显示上下左右的图标
                            goYTZ(event);
                        }
                    }
                }
                /*if (_stream == 0) */
                mGlsfView.requestRender();
                break;
        }
        mPreviousY = y;// 记录触控笔位置
        mPreviousX = x;// 记录触控笔位置
        return true;
    }

    int ptzFlag = 0;//防止多次触发打的标记
    public boolean isTouch = false;

    private boolean mLastAudioIsRun = false;

    private void goYTZ(MotionEvent event) {
        if (mDevicesBean != null && mDevicesBean.getFrom() != null && !AuthorityManager.isHadPTZControlAuthority(mDevicesBean.getSn())) {
            if (mnPlayControlLinstener != null) {
                mnPlayControlLinstener.onAuthorityChanged(AuthorityManager.getDevAuthority(mDevicesBean.getSn()));
            }
            return;
        }
        mCurrentPosX = event.getX();
        mCurrentPosY = event.getY();

        if (!isTouch) {
            isTouch = true;//小黄人在触摸
            Log.i("PTZControl", "开始触摸");
            mLastAudioIsRun = mAudioRunable.isRunning();
            if (mLastAudioIsRun) {
                cancelYTZTimerTask();
                stopPTZAudio();
            }
        }
        if (mCurrentPosX - mDownX > 10 && Math.abs(mCurrentPosY - mDownY) < (mCurrentPosX - mDownX)) {
            //向右
            if (ptzFlag != 1) {
                Log.i("PTZControl", "向右");
                ptzFlag = 1;
                ptzControl(MNControlAction.MNDirection.DIRECTION_RIGHT, mChinalId);
            }
        } else if (mCurrentPosX - mDownX < -10 && Math.abs(mCurrentPosY - mDownY) < Math.abs(mCurrentPosX - mDownX)) {
            //向左
            if (ptzFlag != 2) {
                Log.i("PTZControl", "向左");
                ptzFlag = 2;
                ptzControl(MNControlAction.MNDirection.DIRECTION_LEFT, mChinalId);
            }
        } else if (mCurrentPosY - mDownY > 10 && Math.abs(mCurrentPosX - mDownX) < (mCurrentPosY - mDownY)) {
            if (ptzFlag != 3) {
                Log.i("PTZControl", "向下");
                ptzFlag = 3;
                ptzControl(MNControlAction.MNDirection.DIRECTION_DOWN, mChinalId);
            }
        } else if (mCurrentPosY - mDownY < -10 && Math.abs(mCurrentPosX - mDownX) < Math.abs(mCurrentPosY - mDownY)) {
            if (ptzFlag != 4) {
                Log.i("PTZControl", "向上");
                ptzFlag = 4;
                ptzControl(MNControlAction.MNDirection.DIRECTION_UP, mChinalId);
            }
        }
    }

    Timer m_timeSr;
    TimerTask m_timeTasSk;

    //操作云台对声音的处理
    public void startYTZTimerTask() {
        try {
            cancelYTZTimerTask();
            if (m_timeSr == null) {
                m_timeSr = new Timer();
            }
            if (m_timeTasSk == null) {
                m_timeTasSk = new TimerTask() {
                    @Override
                    public void run() {
                        myHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (mLastAudioIsRun) {
                                    startAudio();
                                }
                            }
                        });
                    }
                };
            }
            m_timeSr.schedule(m_timeTasSk, 600);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelYTZTimerTask() {
        try {
            if (m_timeSr != null) {
                m_timeSr.cancel();
                m_timeSr = null;
            }
            if (m_timeTasSk != null) {
                m_timeTasSk.cancel();
                m_timeTasSk = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onVolumeBack(int volume) {
        ((Activity) mContext).runOnUiThread(() -> {
            if (mnPlayControlLinstener != null) {
                mnPlayControlLinstener.onAudioVolume(volume);
            }
        });
    }


    private class MainHandler extends Handler {

        public MainHandler(MNPlayControl playControl) {

        }
    }

    /**
     * 角度复位
     */
    @Override
    public void ptzResetAngle() {
        if (mDevicesBean != null && mDevicesBean.getFrom() != null && !AuthorityManager.isHadPTZControlAuthority(mDevicesBean.getSn())) {
            if (mnPlayControlLinstener != null) {
                mnPlayControlLinstener.onAuthorityChanged(AuthorityManager.getDevAuthority(mDevicesBean.getSn()));
            }
            return;
        }
        cachedThreadPool.execute(() -> {
            PTZControl.PTZCenter(lVideoTaskContext, 0);
        });
    }

    private String mPointName;

    /**
     * 设置预置点
     *
     * @param pointName
     * @param pointIndex
     */
    @Override
    public void saveFavoritePoint(String pointName, String pointIndex) {
        if (mDevicesBean != null && mDevicesBean.getFrom() != null && !AuthorityManager.isHadPTZControlAuthority(mDevicesBean.getSn())) {
            if (mnPlayControlLinstener != null) {
                mnPlayControlLinstener.onAuthorityChanged(AuthorityManager.getDevAuthority(mDevicesBean.getSn()));
            }
            return;
        }
        mPointName = pointName;
        PTZControl.PTZSetPrePosition(lVideoTaskContext, 0, pointIndex);
    }

    /**
     * 删除摇头机预置点
     *
     * @param pointId
     * @param pointIndex
     */
    @Override
    public void delFavoritePoint(String pointId, String pointIndex) {
        if (mDevicesBean != null && mDevicesBean.getFrom() != null && !AuthorityManager.isHadPTZControlAuthority(mDevicesBean.getSn())) {
            if (mnPlayControlLinstener != null) {
                mnPlayControlLinstener.onAuthorityChanged(AuthorityManager.getDevAuthority(mDevicesBean.getSn()));
            }
            return;
        }
        ArrayList<String> ids = new ArrayList<>();
        ids.add(pointId);
        MNKit.delteFavoritePoints(ids, new MNKitInterface.DelteFavoritePointsCallBack() {
            @Override
            public void onDelteFavoritePointsSuc(FavoriteDelteBean bean) {
                if (mnPlayControlLinstener != null) {
                    mnPlayControlLinstener.onDelSessionCtrl(true, pointIndex);
                }
            }

            @Override
            public void onDelteFavoritePointsFailed(String o) {
                if (mnPlayControlLinstener != null) {
                    mnPlayControlLinstener.onDelSessionCtrl(false, pointIndex);
                }
            }
        });
    }

    /**
     * 前往收藏点
     *
     * @param pointIndex
     */
    @Override
    public void gotoFavoritePoint(String pointIndex) {
        if (mDevicesBean != null && mDevicesBean.getFrom() != null && !AuthorityManager.isHadPTZControlAuthority(mDevicesBean.getSn())) {
            if (mnPlayControlLinstener != null) {
                mnPlayControlLinstener.onAuthorityChanged(AuthorityManager.getDevAuthority(mDevicesBean.getSn()));
            }
            return;
        }
        PTZControl.PTZGotoPrePosition(lVideoTaskContext, 0, pointIndex);
    }
}

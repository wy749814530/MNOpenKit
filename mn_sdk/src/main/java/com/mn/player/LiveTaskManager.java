package com.mn.player;

import MNSDK.MNJni;

/**
 * Created by Administrator on 2018/8/31 0031.
 */

public class LiveTaskManager {

    /**
     * 开启直播
     *
     * @param sn
     * @param channelId
     * @param lVideoTaskContext
     * @return
     */
    public static long startLiveTask(String sn, int channelId, int stream, int devtype, boolean is4GDev, long lVideoTaskContext) {
        if (MNJni.GetTaskType(lVideoTaskContext) == MNJni.MNTaskType.MNAV_TASK_TYPE_t.MTT_P2P_REALPLAY.ordinal() || 0 == lVideoTaskContext) {
            MNJni.MNTaskType taskType = new MNJni.MNTaskType(MNJni.MNTaskType.MNAV_TASK_TYPE_t.MTT_P2P_REALPLAY.ordinal());
            MNJni.MNOutputDataType dataType = new MNJni.MNOutputDataType(MNJni.MNOutputDataType.MN_OUTPUT_DATA_TYPE.MODT_FFMPEG_YUV420.ordinal());
            lVideoTaskContext = MNJni.PrepareTask(taskType, 0);
            MNJni.ConfigRealPlayTask(lVideoTaskContext, sn, channelId, stream, devtype, is4GDev, dataType);
            MNJni.StartTask(lVideoTaskContext);
        }
        return lVideoTaskContext;
    }

    /**
     * 开启云回放
     * @param videoUrl
     * @param fileSize
     * @return
     */
    public static long startCloudVideoTask(String videoUrl, int fileSize) {
        MNJni.MNTaskType taskType = new MNJni.MNTaskType(MNJni.MNTaskType.MNAV_TASK_TYPE_t.MTT_CLOUD_ALARM.ordinal());
        MNJni.MNOutputDataType dataType = new MNJni.MNOutputDataType(MNJni.MNOutputDataType.MN_OUTPUT_DATA_TYPE.MODT_FFMPEG_YUV420.ordinal());
        MNJni.MNCloudTaskType cloudTaskType = new MNJni.MNCloudTaskType(MNJni.MNCloudTaskType.MN_CLOUD_TASK_TYPE.MCTT_PLAY.ordinal());
        long lVideoTaskContext = MNJni.PrepareTask(taskType, 0);
        MNJni.ConfigCloudAlarmTask(lVideoTaskContext, videoUrl, 0, fileSize, dataType, cloudTaskType);
        MNJni.StartTask(lVideoTaskContext);
        return lVideoTaskContext;
    }

    /**
     * 开启扬声器
     *
     * @param mDevSn
     * @param mDevChannelId
     * @param lVoiceTalkTaskContext
     * @return
     */
    public static long startSpeakerTask(String mDevSn, int mDevChannelId, long lVoiceTalkTaskContext) {
        if (MNJni.GetTaskType(lVoiceTalkTaskContext) == MNJni.MNTaskType.MNAV_TASK_TYPE_t.MTT_P2P_VOICE_TALK.ordinal() || 0 == lVoiceTalkTaskContext) {
            MNJni.DestroyTask(lVoiceTalkTaskContext);
            MNJni.MNTaskType taskType1 = new MNJni.MNTaskType(MNJni.MNTaskType.MNAV_TASK_TYPE_t.MTT_P2P_VOICE_TALK.ordinal());
            lVoiceTalkTaskContext = MNJni.PrepareTask(taskType1, 0);
            MNJni.ConfigVoiceTalkTask(lVoiceTalkTaskContext, mDevSn, 2, 0);
            MNJni.StartTask(lVoiceTalkTaskContext);
        }
        return lVoiceTalkTaskContext;
    }
}


package com.mn.player.utils;


import android.text.TextUtils;

import java.io.File;

import MNSDK.MNJni;

/**
 * 视频录像类
 */
public class Mp4RecordManager {
    private static final String TAG = Mp4RecordManager.class.getSimpleName();
    private String mH264FilePath = null;
    private String mMp4FilePath = null;
    private boolean mIsRecording = false;

    private static Mp4RecordManager instance = new Mp4RecordManager();

    private Mp4RecordManager() {
    }

    public static Mp4RecordManager getInstance() {
        return instance;
    }

    /**
     * 开启直播录像或者卡回放录像
     *
     * @param filePath     文件存储路径
     * @param lTaskContext 播放任务id
     * @return
     */
    public boolean startRecord(String filePath, long lTaskContext) {
        if (0 == lTaskContext || TextUtils.isEmpty(filePath)) {
            mIsRecording = false;
            return false;
        }

        mH264FilePath = FileUtil.modifyFileSuffix(filePath, "h264");
        mMp4FilePath = filePath;
        int nRetCode = MNJni.StartRecordVideo(mH264FilePath, null, lTaskContext);
        mIsRecording = (0 == nRetCode);
        return mIsRecording;
    }

    /**
     * 关闭录像
     *
     * @param lTaskContext 播放任务id
     * @return
     */
    public String stopRecord(long lTaskContext) {
        mIsRecording = false;
        if (0 == lTaskContext) return null;
        int nRetCode = MNJni.FinishRecordVideo(mMp4FilePath, lTaskContext);
        if (0 == nRetCode) {
            deleteFile(mH264FilePath);
            filterVedio(mH264FilePath);
            return mMp4FilePath;
        }
        return null;
    }

    /**
     * 是否正在录像
     *
     * @return
     */
    public boolean isRecording() {
        return mIsRecording;
    }

    private void filterVedio(String videoPath) {
        File file = new File(videoPath);
        if (file.exists()) {
            long fsize = file.length();
            if (fsize < 102400) {
                deleteFile(videoPath);
                return;
            }
        }
    }

    private void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file != null && file.exists()) {
            file.delete();
        }
    }
}

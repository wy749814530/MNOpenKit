package com.mn.player;

/**
 * Created by Administrator on 2018/8/30 0030.
 */

public interface MNPlayControlLinstener {
    /**
     * 视频状态回调（Video status callback）
     *
     * @param videoType  播放视频类型（Play video type）
     * @param taskStatus 任务状态（Task status）
     * @param fProgress  进度（如果是下载任务）（Progress (if it is a download task)）
     */
    void OnTaskStatus(int videoType, int taskStatus, float fProgress);

    /**
     * @param taskStatus 任务创建是否成功（Whether the task was created successfully）
     * @param downStatus 下载状态: 1：下载中，2：下载失败 ，3：下载成功，4：取消下载（Download status: 1: downloading, 2: download failed, 3: download successful, 4: cancel download）
     * @param progress   下载进度(Download progress)
     * @param fileName   文件路径(file path)
     */
    void onDownloadTaskStatus(boolean taskStatus, int downStatus, float progress, String fileName);

    /**
     * 摇头机收藏预置点回调(Shaking machine collection presets callback)
     *
     * @param result  设置结果(Setting results)
     * @param pointId 预置点(Preset)
     */
    void onSetSessionCtrl(boolean result, String pointId);

    /**
     * 摇头机删除收藏点回调（Shaking machine delete favorites callback）
     *
     * @param result  设置结果(Setting results)
     * @param pointId 预置点(Preset)
     */
    void onDelSessionCtrl(boolean result, String pointId);

    /**
     * 前往收藏点（Go to favorites）
     *
     * @param result  设置结果(Setting results)
     * @param pointId 预置点(Preset)
     */
    void onGotoSessionCtrl(boolean result, String pointId);

    /**
     * 视频帧率与网速回调（Video frame rate and internet speed callback）
     *
     * @param nYear                 当前播放视频中解析到的时间:年
     * @param nMonth                当前播放视频中解析到的时间:月
     * @param nDay                  当前播放视频中解析到的时间:日
     * @param nHour                 当前播放视频中解析到的时间:时
     * @param nMinute               当前播放视频中解析到的时间:分
     * @param nSecond               当前播放视频中解析到的时间:秒
     * @param lNetworkFlowPerSecond 网速
     */
    void runSpeed(int nYear, int nMonth, int nDay, int nHour, int nMinute, int nSecond, long lNetworkFlowPerSecond);

    /**
     * 音频开关状态变化（Audio switch status change）
     *
     * @param isOpen
     */
    void onAudioSwitchChanged(boolean isOpen); // 音频开关变化

    /**
     * 语音对讲状态变化(Voice intercom status changes)
     *
     * @param isOpen
     */
    void onHoldTalkSwitchChanged(boolean isOpen); // 单攻对讲变化

    /**
     * 摇头机转动到达边界回调(Shaking machine turns to reach the boundary callback)
     */
    void onToborder();

    /**
     * NVR 设备会有这个回调(NVR devices will have this callback)
     *
     * @param data
     */
    void onNvrSessionView(String data);

    /**
     * 输入音量大小
     *
     * @param volume
     */
    void onAudioVolume(int volume);

    /**
     * 权限变更
     *
     * @param authority
     */
    void onAuthorityChanged(int authority);
}

package com.mn.player;

import com.mn.bean.restfull.AlarmsBean;
import com.mn.bean.restfull.DevicesBean;
import com.mn.bean.restfull.Record24Bean;

import java.util.List;

/**
 * 自定义播放器
 */

public interface MNMediaInterface {

    /**
     * 设置播放器初始数据(Set player initial data)
     *
     * @param deviceInfo 设备信息(Device Information)
     */
    void setDeviceInfo(DevicesBean deviceInfo);

    /**
     * 开启直播(Start live)
     *
     * @param stream    码流（Code stream）
     * @param channelId 通道号（Channel number）
     */
    void startLive(MNControlAction.MNCodeStream stream, int channelId);

    /**
     * 打开视频失败，尝试重新打开视频（Failed to open video, try to reopen video）
     */
    void tryStartLive();

    /**
     * 销毁播放器（Destroy player）
     */
    void releasePlayer();

    /**
     * 关闭播放任务（Close playback task）
     */
    void stopPlayer();

    /**
     * 暂停播放任务（Pause task）
     */
    void pausePlayer();

    /**
     * 恢复播放任务（Resume playback task）
     */
    void resumePlayer();

    /**
     * 开启音频（Turn on audio）
     */
    void startAudio();

    /**
     * 关闭音频（关闭音频）
     */
    void stopAudio();

    /**
     * 开始录像（Start recording）
     *
     * @param filePath 录制文件存放地址（Recording file storage address）
     * @return boool 开始录制成功或者失败
     */
    boolean startRecord(String filePath);

    /**
     * 结束录像(End recording)
     *
     * @return 录制成功返回文件录制文件路径（Recording successfully returns the file recording file path）
     */
    String stopRecord();

    /**
     * 开启对讲(Start intercom)
     */
    void startTalk();

    /**
     * 结束单项对讲(End single talk)
     */
    void stopTalk();

    /**
     * 截图(Screenshot)
     *
     * @param fileName 文件存储位置(File storage location)
     * @return
     */
    boolean screenShot(String fileName);

    /**
     * 设置码流(Set the code stream)
     *
     * @param stream // 码流
     * @return
     */
    boolean setCodeStream(MNControlAction.MNCodeStream stream);

    /**
     * 设置值直播窗口大小（Set value live window size）
     *
     * @param width
     * @param height
     */
    void setSurfaceSize(int width, int height);

    /**
     * 播放云视频（Play cloud video）
     *
     * @param alarmsBean 云视频授权路径（Cloud video authorization path）
     */
    void playCloudVideo(AlarmsBean alarmsBean);

    /**
     * 配置自动播放24小时云录像(Configure automatic playback of 24-hour cloud recording)
     *
     * @param record24Beans   24小时报警数组（24-hour alarm array）
     * @param vStartLoaclTime 开始播放时间（Start time）
     * @param vEndLoaclTime   结束播放时间（End play time）
     */
    void config24HCloudRecordAutoTask(List<Record24Bean> record24Beans, String vStartLoaclTime, String vEndLoaclTime);

    /**
     * 切换播放时间段（24小时云录像）（Switch playback time period (24-hour cloud video)）
     *
     * @param record24Beans   24小时报警数组（24-hour alarm array）
     * @param vStartLoaclTime 开始播放时间（Start time）
     * @param vEndLoaclTime   结束播放时间（End play time）
     */
    void change24CloudRecordAutoPlayTime(List<Record24Bean> record24Beans, String vStartLoaclTime, String vEndLoaclTime);

    /**
     * 下载云视频(Download Cloud Video)
     *
     * @param savePath   录像保存路径(Video save path)
     * @param alarmsBean 文件名称(file name)
     */
    void downCloudAlarmVideo(String savePath, AlarmsBean alarmsBean);

    /**
     * 播放卡录像(Play card video)
     *
     * @param recordJson 卡录像数据(Card recording data)
     * @param startTime  开始播放时间(Start play time)
     * @param endTime    结束播放时间(End play time)
     */
    void playTfCardVideo(String recordJson, String startTime, String endTime);

    /**
     * 变化播放卡录像时间(Change playback card recording time)
     *
     * @param recordJson 卡录像数据(Card recording data)
     * @param startTime  开始播放时间(Start play time)
     * @param endTime    结束播放时间(End play time)
     */
    void playTfCardVideoChange(String recordJson, String startTime, String endTime);

    /**
     * 下载卡录像视频(Download card video)
     *
     * @param recordJson   卡录像数据(Card recording data)
     * @param startTime    下载开始时间(Download start time)yyyy-MM-dd HH:mm:ss
     * @param stopTime     下载结束时间(Download end time) yyyy-MM-dd HH:mm:ss
     * @param downloadPath 视频保存地址 (Video save address)
     */
    void downloadTfCardVideo(String recordJson, String startTime, String stopTime, String downloadPath);

    /**
     * 云台控制(PTZ control)
     *
     * @param direction 方向
     * @param channelId 通道号 0
     */
    void ptzControl(MNControlAction.MNDirection direction, int channelId);

    /**
     * 设置设备是否支持云台控制(Set whether the device supports PTZ control)
     *
     * @param support 是否支持云台控制
     */
    void supportPTZControl(boolean support);

    /**
     * 摇头机返回原点(Shaking machine returns to the origin)
     */
    void ptzResetAngle();

    /**
     * 保存摇头机预置点(Save the shaking machine preset)
     *
     * @param pointName  收藏名称（Favorite name）
     * @param pointIndex (Favorites index：{0,1,2,3,4,5})
     */
    void saveFavoritePoint(String pointName, String pointIndex);

    /**
     * 前往指定收藏点（Go to designated collection point）
     *
     * @param pointIndex (Favorites index：{0,1,2,3,4,5})
     */
    void gotoFavoritePoint(String pointIndex);

    /**
     * 删除摇头机预置点（Delete the preset point of the shaker）
     *
     * @param pointId    收藏点id （Favorites id）
     * @param pointIndex (Favorites index：{0,1,2,3,4,5})
     */
    void delFavoritePoint(String pointId, String pointIndex);
}

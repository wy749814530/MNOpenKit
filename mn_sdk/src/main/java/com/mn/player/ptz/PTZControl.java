package com.mn.player.ptz;

import MNSDK.MNJni;

public class PTZControl {

    public static void PTZUp(long mContext, int channel) {
        String liPtzUp = "{\"PTZ_CMD\":\"CMD_PTZ_UP\",\"value\":0,\"onestep\":0,\"channel\":" + channel + "}";
        MNJni.RequestYTControl(mContext, liPtzUp);
    }

    public static void PTZdown(long mContext, int channel) {
        String liPtzDown = "{\"PTZ_CMD\":\"CMD_PTZ_DOWN\",\"value\":2,\"onestep\":0,\"channel\":" + channel + "}";
        MNJni.RequestYTControl(mContext, liPtzDown);
    }

    public static void PTZleft(long mContext, int channel) {
        String liPtzLef = "{\"PTZ_CMD\":\"CMD_PTZ_LEFT\",\"value\":4,\"onestep\":0,\"channel\":" + channel + "}";
        MNJni.RequestYTControl(mContext, liPtzLef);
    }

    public static void PTZright(long mContext, int channel) {
        String liPtzRight = "{\"PTZ_CMD\":\"CMD_PTZ_RIGHT\",\"value\":6,\"onestep\":0,\"channel\":" + channel + "}";
        MNJni.RequestYTControl(mContext, liPtzRight);
    }

    public static void PTZstop(long mContext, int channel) {
        String liPtzStop = "{\"PTZ_CMD\":\"CMD_PTZ_STOP\",\"value\":100,\"channel\":" + channel + "}";
        MNJni.RequestYTControl(mContext, liPtzStop);
    }

    public static void PTZCenter(long mContext, int channel) {
        String liPtzCenter = "{\"PTZ_CMD\":\"CMD_PTZ_CENTER\",\"value\":25,\"channel\":" + channel + "}";
        MNJni.RequestYTControl(mContext, liPtzCenter);
    }

    // 设置预置点
    public static void PTZSetPrePosition(long mContext, int channel, String pointIndex) {
        String liPtzCenter = "{\"PTZ_CMD\":\"CMD_PTZ_PREFAB_BIT_SET" + pointIndex + "\", \"value\":" + (30 + 2 * Integer.valueOf(pointIndex)) + ",\"channel\":" + channel + "}";
        MNJni.RequestYTControl(mContext, liPtzCenter);
    }

    // 回到指定预置点
    public static void PTZGotoPrePosition(long mContext, int channel, String pointIndex) {
        String req = "{\"PTZ_CMD\":\"CMD_PTZ_PREFAB_BIT_RUN" + pointIndex + "\",\"value\":" + (31 + 2 * Integer.valueOf(pointIndex)) + ",\"channel\":" + channel + "}";
        MNJni.RequestYTControl(mContext, req);
    }

    // 删除指定预置点
    public static void PTZDelPrePosition(long mContext, int channel, String pointIndex) {
        String req = "{\"PTZ_CMD\":\" CMD_PTZ_PREFAB_BIT_DEL" + pointIndex + "\",\"value\":" + (70 + Integer.valueOf(pointIndex)) + ",\"channel\":" + channel + "}";
        MNJni.RequestYTControl(mContext, req);
    }

    // 获取已有预置点
    public static void getPTZPrePosition(long mContext, int channel) {
        String req = "{\"PTZ_CMD\":\"CMD_PTZ_PREFAB_GET\",\"value\":124,\"channel\":" + channel + "}";
        MNJni.RequestYTControl(mContext, req);
    }

    //右上
    public static void PTZright_up(long mContext, int channel) {
        String liPtzRight = "{\"PTZ_CMD\":\"CMD_PTZ_RIGHT_UP\",\"value\":91,\"onestep\":0,\"channel\":" + channel + "}";
        MNJni.RequestYTControl(mContext, liPtzRight);
    }

    //右下
    public static void PTZright_down(long mContext, int channel) {
        String liPtzRight = "{\"PTZ_CMD\":\"CMD_PTZ_RIGHT_DOWN\",\"value\":93,\"onestep\":0,\"channel\":" + channel + "}";
        MNJni.RequestYTControl(mContext, liPtzRight);
    }

    //左上
    public static void PTZleft_up(long mContext, int channel) {
        String liPtzRight = "{\"PTZ_CMD\":\"CMD_PTZ_LEFT_UP\",\"value\":90,\"onestep\":0,\"channel\":" + channel + "}";
        MNJni.RequestYTControl(mContext, liPtzRight);
    }

    //左下
    public static void PTZleft_down(long mContext, int channel) {
        String liPtzRight = "{\"PTZ_CMD\":\"CMD_PTZ_LEFT_DOWN\",\"value\":92,\"onestep\":0,\"channel\":" + channel + "}";
        MNJni.RequestYTControl(mContext, liPtzRight);
    }
}

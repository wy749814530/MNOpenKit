package com.mn.player;

/**
 * Created by Administrator on 2018/8/30 0030.
 */

public class MNControlAction {

    /**
     * 摇头机遥感方向
     */
    public enum MNDirection {
        DIRECTION_LEFT, // 左
        DIRECTION_RIGHT, // 右
        DIRECTION_UP, // 上
        DIRECTION_DOWN, // 下
        DIRECTION_UP_LEFT, // 左上
        DIRECTION_UP_RIGHT, // 右上
        DIRECTION_DOWN_LEFT, // 左下
        DIRECTION_DOWN_RIGHT, // 右下
        DIRECTION_CENTER // 停止
    }

    /**
     * 视频码流
     */
    public enum MNCodeStream {
        VIDEO_HD,     // 高清
        VIDEO_FUL,     // 流畅
    }
}

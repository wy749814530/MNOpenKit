package com.mn;

/**
 * Created by Administrator on 2020/3/10 0010.
 */

public class MnKitAlarmType {

    /**
     * 全部报警
     */
    public static int AllAlarm_Detection = 1 << 0;

    /**
     * 动检报警
     */
    public static int Motion_Detection = 1 << 1;
    /**
     * 人形报警
     */
    public static int Humanoid_Detection = 1 << 2;
    /**
     * 遮挡报警
     */
    public static int Occlusion_Detection = 1 << 3;
    /**
     * 人脸识别报警
     */
    public static int Face_Detection = 1 << 4;

    /**
     * 哭声报警
     */
    public static int Cry_Detection = 1 << 5;
    /**
     * 箱体报警
     */
    public static int Box_Detection = 1 << 6;
    /**
     * 门铃来电提醒报警
     */
    public static int CallReminder_Detection = 1 << 7;
    /**
     * PIR 报警
     */
    public static int Infrared_detection = 1 << 8;
    /**
     * 外部IO报警
     */
    public static int IO_detection = 1 << 9;

}

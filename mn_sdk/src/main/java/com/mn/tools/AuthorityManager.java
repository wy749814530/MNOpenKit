package com.mn.tools;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2018/10/10 0010.
 */

public class AuthorityManager {
    /*
    1、视频直播 	00001  0
    2、云端报警 	00010  1
    3、本地录像 	00100  2
    4、设备配置 	01000  3
    5、云台控制权限 10000  4
   */

    public static int Live_Video_Authority = 1 << 0;   //视频直播权限
    public static int Cloud_Alarm_Authority = 1 << 1;  //云端报警权限
    public static int Local_Video_Authority = 1 << 2;  //本地录像权限
    public static int Device_Config_Authority = 1 << 3;//设备配置权限
    public static int PTZ_Control_Authority = 1 << 4;  //云台控制权限

    private static ConcurrentHashMap<String, Integer> devAuthority = new ConcurrentHashMap<>();

    public static void setDevAuthority(String devSn, int authority) {
        devAuthority.put(devSn, authority);
    }

    public static int getDevAuthority(String devSn) {
        if (devAuthority.containsKey(devSn)) {
            int authority = devAuthority.get(devSn);
            return authority;
        }
        return 0;
    }

    /**
     * 设置直播权限
     *
     * @param authority
     * @param open
     * @return
     */
    public static int setLiveVideoAuthority(int authority, boolean open) {
        if (open) {
            authority = authority | Live_Video_Authority;
        } else {
            authority = authority & (~Live_Video_Authority);
        }
        return authority;
    }

    /**
     * 云端报警权限
     *
     * @param authority
     * @param open
     * @return
     */
    public static int setCloudAlarmAuthority(int authority, boolean open) {
        if (open) {
            authority = authority | Cloud_Alarm_Authority;
        } else {
            authority = authority & (~Cloud_Alarm_Authority);
        }
        return authority;
    }

    /**
     * 本地录像权限
     *
     * @param authority
     * @param open
     * @return
     */
    public static int setLocalVideoAuthority(int authority, boolean open) {
        if (open) {
            authority = authority | Local_Video_Authority;
        } else {
            authority = authority & (~Local_Video_Authority);
        }
        return authority;
    }

    /**
     * 设备配置权限
     *
     * @param authority
     * @param open
     * @return
     */
    public static int setDeviceConfigAuthority(int authority, boolean open) {
        if (open) {
            authority = authority | Device_Config_Authority;
        } else {
            authority = authority & (~Device_Config_Authority);
        }
        return authority;
    }

    /**
     * 云台控制权限
     *
     * @param authority
     * @param open
     * @return
     */
    public static int setPTZControlAuthority(int authority, boolean open) {
        if (open) {
            authority = authority | PTZ_Control_Authority;
        } else {
            authority = authority & (~PTZ_Control_Authority);
        }
        return authority;
    }

    public static boolean hadLiveVideoAuthority(int authority) {
        return (authority & Live_Video_Authority) == Live_Video_Authority;
    }

    public static boolean hadCloudAlarmAuthority(int authority) {
        return (authority & Cloud_Alarm_Authority) == Cloud_Alarm_Authority;
    }

    public static boolean hadLocalVideoAuthority(int authority) {
        return (authority & Local_Video_Authority) == Local_Video_Authority;
    }

    public static boolean hadDeviceConfigAuthority(int authority) {
        return (authority & Device_Config_Authority) == Device_Config_Authority;
    }

    public static boolean hadPTZControlAuthority(int authority) {
        return (authority & PTZ_Control_Authority) == PTZ_Control_Authority;
    }

    ////////////////////////////////////////////////////////////////
    // 一下用于做设置判断
    public static boolean isHadCloudAlarmAuthority(String devSn) {
        if (devAuthority.containsKey(devSn)) {
            int authority = devAuthority.get(devSn);
            if (authority == 0) {
                return false;
            }
            return (authority & Cloud_Alarm_Authority) == Cloud_Alarm_Authority;
        }
        return true;
    }

    public static boolean isHadLocalVideoAuthority(String devSn) {
        if (devAuthority.containsKey(devSn)) {
            int authority = devAuthority.get(devSn);
            if (authority == 0) {
                return false;
            }
            return (authority & Local_Video_Authority) == Local_Video_Authority;
        }
        return true;
    }

    public static boolean isHadDeviceConfigAuthority(String devSn) {
        if (devAuthority.containsKey(devSn)) {
            int authority = devAuthority.get(devSn);
            if (authority == 0) {
                return false;
            }
            return (authority & Device_Config_Authority) == Device_Config_Authority;
        }
        return true;
    }

    public static boolean isHadPTZControlAuthority(String devSn) {
        if (devAuthority.containsKey(devSn)) {
            int authority = devAuthority.get(devSn);
            if (authority == 0) {
                return false;
            }
            return (authority & PTZ_Control_Authority) == PTZ_Control_Authority;
        }
        return true;
    }
}


package com.mn.tools;

import com.mn.bean.restfull.DevicesBean;

/**
 * Created by Administrator on 2019/10/29 0029.
 */

public class AbilityTools {

    /**
     * 是否是4G设备
     *
     * @param device
     * @return
     */
    public static boolean isFourGEnable(DevicesBean device) {
        if (device != null && device.getSupport_ability() != null && device.getSupport_ability().getFourgAbility() != null) {
            if (device.getSupport_ability().getFourgAbility().getFourgEnable() == 1) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 是否是4G设备(剔除低功耗)
     * 支持5分钟暂停
     *
     * @param device
     * @return
     */
    public static boolean isFourGEnableSupportFivePouse(DevicesBean device) {
        if (device != null && device.getSupport_ability() != null && device.getSupport_ability().getFourgAbility() != null) {
            if (device.getSupport_ability().getFourgAbility().getFourgEnable() == 1 && device.getType() != 17) {//17暂时没安排做低功耗，暂时过滤
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 是否是电池设备
     *
     * @param device
     * @return
     */
    public static boolean isBatteryDev(DevicesBean device) {
        if (device != null && (device.getType() == 2 || device.getType() == 17)) {
            return true;
        }
        return false;
    }

    /**
     * 是否支持8方向云台
     *
     * @param device
     * @return
     */
    public static boolean isSupport8directions(DevicesBean device) {
        if (device != null && device.getType() == 13) {
            return true;
        }
        return false;
    }

    /**
     * 是否支持云台控制
     *
     * @param device
     * @return
     */
    public static boolean isSupportPTZControl(DevicesBean device) {
        if (device != null && device.getSupport_ability() != null && device.getSupport_ability().getPtzAbility() != null && device.getSupport_ability().getPtzAbility().getDirection() == 1) {
            // 支持云台控制的
            if (device.getAuthority() == 0 || AuthorityManager.hadPTZControlAuthority(device.getAuthority())) {
                // 自己的和分享的
                return true;
            }
        }
        if (device != null && (device.getType() == 7 || device.getType() == 10 || device.getType() == 13)) {
            if (device.getAuthority() == 0 || AuthorityManager.hadPTZControlAuthority(device.getAuthority())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否支持24小时云录像
     *
     * @param mDevice
     * @return
     */
    public static boolean isSupport24HourCloudRecording(DevicesBean mDevice) {
        if (mDevice != null && mDevice.getSupport_ability() != null && mDevice.getSupport_ability().getH24recordAbility() == 1) {
            return true;
        }
        return false;
    }


    /**
     * 是否支持时光机
     *
     * @param mDevice
     * @return
     */
    public static boolean isSupportTimeMachine(DevicesBean mDevice) {
        if (mDevice != null && mDevice.getSupport_ability() != null && mDevice.getSupport_ability().getTimingCaptureAbility() == 1) {
            return true;
        }
        return false;
    }

    /**
     * 是否支持遮挡报警
     *
     * @param mDevice
     * @return
     */
    public static boolean isSupportBlineDetect(DevicesBean mDevice) {
        if (mDevice != null && mDevice.getSupport_ability() != null && mDevice.getSupport_ability().getAlarmAbility() != null) {
            for (DevicesBean.SupportAbilityBean.AlarmAbilityBean alarmAbilityBean : mDevice.getSupport_ability().getAlarmAbility()) {
                if (alarmAbilityBean.getAlarmType() == 13 && alarmAbilityBean.getSubAlarmType().contains(1)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 是否是低功耗设备
     *
     * @param mDevice
     * @return
     */
    public static boolean isLowPowerDev(DevicesBean mDevice) {
        if (mDevice != null && mDevice.getSupport_ability() != null && mDevice.getSupport_ability().getOtherAbility() != null) {
            if (mDevice.getSupport_ability().getOtherAbility().getLowPower() == 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否支持通用外部IO报警
     *
     * @param mDevice
     * @return
     */
    public static boolean isSupportIoAlarm(DevicesBean mDevice) {
        if (mDevice != null && mDevice.getSupport_ability() != null && mDevice.getSupport_ability().getAlarmAbility() != null) {
            for (DevicesBean.SupportAbilityBean.AlarmAbilityBean alarmAbilityBean : mDevice.getSupport_ability().getAlarmAbility()) {
                if (alarmAbilityBean.getAlarmType() == 1 && alarmAbilityBean.getSubAlarmType().contains(1)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 是否支持PIR红外侦测报警
     *
     * @param mDevice
     * @return
     */
    public static boolean isSupportPIRAlarm(DevicesBean mDevice) {
        if (mDevice != null && mDevice.getSupport_ability() != null && mDevice.getSupport_ability().getAlarmAbility() != null) {
            for (DevicesBean.SupportAbilityBean.AlarmAbilityBean alarmAbilityBean : mDevice.getSupport_ability().getAlarmAbility()) {
                if (alarmAbilityBean.getAlarmType() == 14 && alarmAbilityBean.getSubAlarmType().contains(1)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 是否支持警戒音
     *
     * @param device
     * @return
     */
    public static boolean isSupportAlertTone(DevicesBean device) {
        if (device != null && (device.getType() == 15 || device.getType() == 16)) {

        }
        return false;
    }

    /**
     * 是否支持背光补偿
     *
     * @param device
     * @return
     */
    public static boolean isSupportBLC(DevicesBean device) {
        if (device != null && (device.getType() == 10 || device.getType() == 16)) {

        }
        return false;
    }

    /**
     * 是否支持人形框
     *
     * @param device
     * @return
     */
    public static boolean isSupportHumanFrame(DevicesBean device) {
        if (device != null && (device.getType() == 16)) {

        }
        return false;
    }

    /**
     * 是否支持灯光模式
     *
     * @param device
     * @return
     */
    public static boolean isSupportLightingMode(DevicesBean device) {
        if (device != null && (device.getType() == 16)) {

        }
        return false;
    }

    /**
     * 是否支持呼吸灯
     *
     * @param device
     * @return
     */
    public static boolean isSupportBreathingLight(DevicesBean device) {
        if (device != null && (device.getType() == 1 || device.getType() == 2)) {

        }
        return false;
    }
}

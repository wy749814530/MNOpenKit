package com.mn.bean.restfull;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2020/3/11 0011.
 */

public class PushconfigBean implements Serializable {
    private static final long serialVersionUID = -7700314240172379718L;
    /**
     * pushenable : 1
     * sleepenable : 1
     * level : 1
     * sleep_time_range : [{"bsleeptime": "00:00:00","esleeptime": "23:59:59"}]
     * face_push : 1
     * stranger_push : 0
     * push_list : [{"alarmType":3,"subAlarmType":[3]}]
     */

    private int pushenable;
    private int sleepenable;
    private int level;
    private int face_push;
    private int stranger_push;
    private int alarmTypeOptions;
    private List<PushListBean> push_list;
    private List<SleepTimeRangeBean> sleep_time_range;

    public int getPushenable() {
        return pushenable;
    }

    public void setPushenable(int pushenable) {
        this.pushenable = pushenable;
    }

    public int getSleepenable() {
        return sleepenable;
    }

    public void setSleepenable(int sleepenable) {
        this.sleepenable = sleepenable;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getFace_push() {
        return face_push;
    }

    public void setFace_push(int face_push) {
        this.face_push = face_push;
    }

    public int getStranger_push() {
        return stranger_push;
    }

    public void setStranger_push(int stranger_push) {
        this.stranger_push = stranger_push;
    }

    public List<PushListBean> getPush_list() {
        return push_list;
    }

    public void setPush_list(List<PushListBean> push_list) {
        this.push_list = push_list;
    }

    public List<SleepTimeRangeBean> getSleep_time_range() {
        return sleep_time_range;
    }

    public void setSleep_time_range(List<SleepTimeRangeBean> sleep_time_range) {
        this.sleep_time_range = sleep_time_range;
    }

    public static class PushListBean implements Serializable {
        private static final long serialVersionUID = -1658557704646054010L;
        /**
         * alarmType : 3
         * subAlarmType : [3]
         */

        private int alarmType;
        private List<Integer> subAlarmType;

        public int getAlarmType() {
            return alarmType;
        }

        public void setAlarmType(int alarmType) {
            this.alarmType = alarmType;
        }

        public List<Integer> getSubAlarmType() {
            return subAlarmType;
        }

        public void setSubAlarmType(List<Integer> subAlarmType) {
            this.subAlarmType = subAlarmType;
        }
    }

    public void setAlarmTypeOptions(int alarmTypeOptions) {
        this.alarmTypeOptions = alarmTypeOptions;
    }

    public int getAlarmTypeOptions() {
        return alarmTypeOptions;
    }

    public static class SleepTimeRangeBean implements Serializable {
        private static final long serialVersionUID = 7226042026196423303L;
        /**
         * bsleeptime : 00:00:00
         * esleeptime : 23:59:59
         */

        private String bsleeptime;
        private String esleeptime;

        public String getBsleeptime() {
            return bsleeptime;
        }

        public void setBsleeptime(String bsleeptime) {
            this.bsleeptime = bsleeptime;
        }

        public String getEsleeptime() {
            return esleeptime;
        }

        public void setEsleeptime(String esleeptime) {
            this.esleeptime = esleeptime;
        }
    }
}

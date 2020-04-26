package com.mn.bean.restfull;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/7/9 0009.
 */

public class SharedHistoryBean extends BaseBean {

    private static final long serialVersionUID = -3079421289433770813L;

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable{
        private static final long serialVersionUID = 2400575159684291496L;
        /**
         * user_name : 小明
         * share_time : UTC 毫秒数
         */

        private String user_name;
        private long share_time;
        private long start_time;
        private long end_time;

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public long getShare_time() {
            return share_time;
        }

        public void setShare_time(long share_time) {
            this.share_time = share_time;
        }

        public void setEnd_time(long end_time) {
            this.end_time = end_time;
        }

        public void setStart_time(long start_time) {
            this.start_time = start_time;
        }

        public long getEnd_time() {
            return end_time;
        }

        public long getStart_time() {
            return start_time;
        }
    }
}

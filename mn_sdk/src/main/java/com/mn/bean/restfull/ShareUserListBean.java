package com.mn.bean.restfull;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;

import MNSDK.MNOpenSDK;

/**
 * Created by WIN on 2018/4/12.
 */

public class ShareUserListBean extends BaseBean {
    private static final long serialVersionUID = -1697696020290443755L;
    private int limit;
    private List<ShareUserBean> users;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setShareUserBeen(List<ShareUserBean> shareUserBeen) {
        this.users = shareUserBeen;
    }

    public List<ShareUserBean> getShareUserBeen() {
        return users;
    }

    public static class ShareUserBean implements Serializable {
        private static final long serialVersionUID = -7492339512004553627L;
        private String username;
        private String email;
        private String phone;
        private String avatar;
        private String nickname;
        private String realname;
        private String enableAdd;
        private int state;
        private String id;
        private long watch_time;
        private int watch_times;
        private int authority;
        private String device_id;
        private long remain_time = -1;

        public void setAuthority(int authority) {
            this.authority = authority;
        }

        public int getAuthority() {
            return authority;
        }

        public void setWatch_time(long watch_time) {
            this.watch_time = watch_time;
        }

        public void setWatch_times(int watch_times) {
            this.watch_times = watch_times;
        }

        public int getWatch_times() {
            return watch_times;
        }

        public long getWatch_time() {
            return watch_time;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAvatar() {
            if (TextUtils.isEmpty(avatar)) {
                return null;
            } else {
                if (avatar.contains("?app_key=") && avatar.contains("&app_secret=") && avatar.contains("&access_token=")) {
                    return avatar;
                }
                return avatar + "?app_key=" + MNOpenSDK.getAppKey() + "&app_secret=" + MNOpenSDK.getAppSecret() + "&access_token=" + MNOpenSDK.getAccessToken();
            }
        }

        public String getEmail() {
            return email;
        }

        public String getNickname() {
            return nickname;
        }

        public String getPhone() {
            return phone;
        }

        public String getRealname() {
            return realname;
        }

        public String getUsername() {
            return username;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getState() {
            return state;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setDevice_id(String device_id) {
            this.device_id = device_id;
        }

        public String getDevice_id() {
            return device_id;
        }

        public void setEnableAdd(String enableAdd) {
            this.enableAdd = enableAdd;
        }

        public String getEnableAdd() {
            return enableAdd;
        }

        public void setRemain_time(long remain_time) {
            this.remain_time = remain_time;
        }

        public long getRemain_time() {
            return remain_time;
        }
    }

}

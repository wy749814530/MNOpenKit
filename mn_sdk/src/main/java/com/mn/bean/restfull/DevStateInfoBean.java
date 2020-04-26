package com.mn.bean.restfull;

import java.io.Serializable;

/**
 * Created by hjz on 2018/11/14.
 */

public class DevStateInfoBean implements Serializable {

    /**
     * code : 2000
     * msg : OK
     * online : 0
     * type : 1
     * bind_state : 1
     */

    private int code;
    private String msg;
    private int online;
    private int type;
    private int bind_state;
    /**
     * bind_user : {"phone":"158****1975","email":"110******@qq.com"}
     */
// {"bind_state":2,"bind_user":{"email":"867******@qq.com"},"code":2000,"msg":"OK","online":1,"type":10}
    private BindUserBean bind_user;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getBind_state() {
        return bind_state;
    }

    public void setBind_state(int bind_state) {
        this.bind_state = bind_state;
    }

    public BindUserBean getBind_user() {
        return bind_user;
    }

    public void setBind_user(BindUserBean bind_user) {
        this.bind_user = bind_user;
    }

    public static class BindUserBean implements Serializable {
        /**
         * phone : 158****1975
         * email : 110******@qq.com
         */

        private String phone;
        private String email;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}

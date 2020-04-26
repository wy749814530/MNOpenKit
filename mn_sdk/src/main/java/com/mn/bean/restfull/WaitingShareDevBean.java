package com.mn.bean.restfull;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/7/18 0018.
 */

public class WaitingShareDevBean extends BaseBean {
    private static final long serialVersionUID = 8475615255969628722L;


    private List<ShareDevicesBean> share_devices;

    public List<ShareDevicesBean> getShare_devices() {
        return share_devices;
    }

    public void setShare_devices(List<ShareDevicesBean> share_devices) {
        this.share_devices = share_devices;
    }

    public static class ShareDevicesBean implements Serializable {
        private static final long serialVersionUID = -831328722547299020L;
        /**
         * sn : MDAhAQEAbGUwNjFiMjRjMDIwMwAA
         * device_user : {"email":"749814530@qq.com"}
         */

        private String sn;
        private DeviceUserBean device_user;

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public DeviceUserBean getDevice_user() {
            return device_user;
        }

        public void setDevice_user(DeviceUserBean device_user) {
            this.device_user = device_user;
        }

        public static class DeviceUserBean implements Serializable {
            private static final long serialVersionUID = 6316559506204080464L;
            /**
             * email : 749814530@qq.com
             * "phone":"13291820925"
             */

            private String email;
            private String phone;

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }
        }
    }
}

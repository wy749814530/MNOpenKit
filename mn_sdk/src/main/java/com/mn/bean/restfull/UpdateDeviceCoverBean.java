package com.mn.bean.restfull;

/**
 * Created by Administrator on 2020/1/3 0003.
 */

public class UpdateDeviceCoverBean extends BaseBean {
    private static final long serialVersionUID = 8115510995826103158L;


    /**
     * url : http://restte.bullyun.com:80/api/v1/devices/MDAhAQEAbGUwNjFiMjQ0ZmQxNgAA/logo/052F83FC3557D382DA4A88CE84D3A26C/d_3518513938390097921.bmp
     */

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}


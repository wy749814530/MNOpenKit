package com.mn.bean.restfull;

import android.text.TextUtils;

import MNSDK.MNOpenSDK;

/**
 * Created by Administrator on 2018/9/21 0021.
 */

public class FavoritePointBean {
    /**
     * id : 289436388355477504
     * name : point0
     * desc : sdasd
     * position_id : 0
     * device_id : 227614837641842688
     * image_url : http://rest.mny9.com/api/v3/pre_position/image/8F66EAB5E923AF73699BE05A8BDCE378/p_289436388355477504.bmp
     */

    private String id;
    private String name;
    private String desc;
    private String position_id;
    private String device_id;
    private String image_url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPostion_id() {
        return position_id;
    }

    public void setPostion_id(String postion_id) {
        this.position_id = postion_id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getImage_url() {
        if (TextUtils.isEmpty(image_url)) {
            return null;
        } else {
            if (image_url.contains("?app_key=") && image_url.contains("&app_secret=") && image_url.contains("&access_token=")) {
                return image_url;
            }
            return image_url + "?app_key=" + MNOpenSDK.getAppKey() + "&app_secret=" + MNOpenSDK.getAppSecret() + "&access_token=" + MNOpenSDK.getAccessToken();
        }
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}

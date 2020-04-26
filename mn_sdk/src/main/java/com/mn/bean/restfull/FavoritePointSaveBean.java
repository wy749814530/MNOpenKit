package com.mn.bean.restfull;

/**
 * Created by Administrator on 2018/9/21 0021.
 */

public class FavoritePointSaveBean extends BaseBean {

    /**
     * data : {"id":"289436388355477504","name":"point0","desc":"sdasd","postion_id":"0","device_id":"227614837641842688","image_url":"http://rest.mny9.com/api/v3/pre_position/image/8F66EAB5E923AF73699BE05A8BDCE378/p_289436388355477504.bmp"}
     */

    private FavoritePointBean data;

    public FavoritePointBean getData() {
        return data;
    }

    public void setData(FavoritePointBean data) {
        this.data = data;
    }

}

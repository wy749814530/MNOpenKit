package com.mn.bean.restfull;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jz on 2018/8/29.
 */

public class DevListSortBean implements Serializable {
    private static final long serialVersionUID = 3904234524620020419L;
    private final static String CACHE_FILE = DevListSortBean.class.getSimpleName();
    /**
     * code : 2000
     * msg : OK
     * myDevices : [{"id":"192774745500946607","sn":"MDAhAQEAbGUwNjFiMjRjMDIwMwAA","state":0,"type":1,"ver":"2018-06-08","model":"CM-MCA200","vn":"ABCDEF","logo":"http://rest.mny9.com:10210/api/v1/devices/MDAhAQEAbGUwNjFiMjRjMDIwMwAA/logo/F49188983D286928B3697FBB69A8656F/d_192774745500946607.jpg","channels":1,"longitude":117.051,"latitude":30.5092,"online":0,"facelib":"200727628502536192","users":[{"id":"189870869743407104","phone":"13291820925","avatar":"http://rest.mny9.com:80/api/v1/users/avatar/01AEDEBFF7C96382F4E23FE48F989D69/u_189870869743407104.jpeg","nickname":"很懒，没设置昵称","state":1,"authority":15,"watch_time":"1534467729000","watch_times":43}],"dev_name":"天柱家","logo_type":0,"add_time":1514445228000,"manufacturer_id":154717866845798400,"first_online_time":1514984056000,"alarm_storage_days":"7","video_storage_days":"3"},{"id":"192774745500947047","sn":"MDAhAQEAbGUwNjFiMjRjMDNiYgAA","state":0,"type":1,"ver":"2018-06-08","model":"CM-MCA200","vn":"ABCDEF","logo":"http://rest.mny9.com:10210/api/v1/devices/MDAhAQEAbGUwNjFiMjRjMDNiYgAA/logo/B48F88FB748C24786E380F95204A285E/d_192774745500947047.jpeg","channels":1,"longitude":120.161,"latitude":30.2936,"online":0,"facelib":"200727628502536192","users":[{"id":"189870869743407104","phone":"13291820925","avatar":"http://rest.mny9.com:80/api/v1/users/avatar/01AEDEBFF7C96382F4E23FE48F989D69/u_189870869743407104.jpeg","nickname":"很懒，没设置昵称","state":1,"authority":15,"watch_time":"1532484008000","watch_times":28},{"id":"261553944759635968","phone":"18719288027","state":1,"authority":7,"watch_time":"1531217889000","watch_times":23}],"dev_name":"办公室","logo_type":1,"add_time":1514445228000,"manufacturer_id":154717866845798400,"first_online_time":1517452352000,"alarm_storage_days":"3","video_storage_days":"3"},{"id":"227614837641842688","sn":"84E0F4200E8F02FA","state":0,"type":3,"ver":"20180613","model":"Uface-2","vn":"ABCDEF","channels":1,"longitude":120.161,"latitude":30.2936,"online":0,"facelib":"200727628502536192","dev_name":"人脸考勤","logo_type":0,"add_time":1522776953000,"manufacturer_id":154717866845798400,"first_online_time":1522782040000,"alarm_storage_days":"3","video_storage_days":"3"},{"id":"236255158906916864","sn":"MDAhAQEAbDAwMTI1MjMzNDRhMgAA","state":0,"type":2,"ver":"2018-06-06","model":"CM-MHA100","vn":"ABCDEF","logo":"http://rest.mny9.com:10210/api/v1/devices/MDAhAQEAbDAwMTI1MjMzNDRhMgAA/logo/4BFCD367CA2117AE5DC25C318B886638/d_236255158906916864.jpg","channels":1,"longitude":120.161,"latitude":30.2936,"online":0,"facelib":"200727628502536192","users":[{"id":"189870869743407104","phone":"13291820925","avatar":"http://rest.mny9.com:80/api/v1/users/avatar/01AEDEBFF7C96382F4E23FE48F989D69/u_189870869743407104.jpeg","nickname":"很懒，没设置昵称","state":1,"authority":15,"watch_time":"1531278461000","watch_times":730},{"id":"261553944759635968","phone":"18719288027","state":1,"authority":7,"watch_time":"1531217914000","watch_times":23}],"dev_name":"Doorbell Camera","logo_type":0,"add_time":1524836966000,"manufacturer_id":154717866845798400,"first_online_time":1524929516000,"alarm_storage_days":"3","video_storage_days":"3"}]
     * shareDevices : [{"id":"189821750970683408","sn":"MDAhAQEAbGUwNjFiMjRjMDAxMAAA","state":1,"type":1,"ver":"2018-06-28","model":"CM-MCA200","vn":"ABCDEF","logo":"http://rest.mny9.com:10210/api/v1/devices/MDAhAQEAbGUwNjFiMjRjMDAxMAAA/logo/A1E81689FDF1B91C273163A52BC90E31/d_189821750970683408.jpeg","channels":1,"longitude":120.161,"latitude":30.2936,"online":1,"facelib":"276756925968814080","authority":1,"from":"15757834577","dev_name":"人脸识别摄像机","logo_type":1,"add_time":1513741179000,"manufacturer_id":154717866845798400,"first_online_time":1517467772000,"alarm_storage_days":"30","video_storage_days":"3"}]
     */

    private int code;
    private String msg;
    private List<DevicesBean> devices;


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

    public List<DevicesBean> getDevices() {
        return devices;
    }

    public void setDevices(List<DevicesBean> devices) {
        this.devices = devices;
    }

}

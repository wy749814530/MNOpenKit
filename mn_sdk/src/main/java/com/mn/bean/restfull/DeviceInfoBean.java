package com.mn.bean.restfull;

public class DeviceInfoBean extends BaseBean {
    private static final long serialVersionUID = 3904234524620020419L;


    /**
     * device : {"id":"308388555011395866","sn":"MDAhAQEAbGUwNjFiMjRmODZjZAAA","state":0,"type":7,"ver":"2019-01-15","model":"CM-MYT200","vn":"ABCDEF","logo":"http://restte.bullyun.com:/api/v1/devices/MDAhAQEAbGUwNjFiMjRmODZjZAAA/logo/F1E17FCFFB28E7BCB166404C2DD31189/d_3083885550113958660","channels":1,"longitude":120.162,"latitude":30.294,"online":0,"facelib":"195725983062429696","authority":23,"from":"749814530@qq.com","dev_name":"wyu p1","logo_type":0,"add_time":1542009708000,"manufacturer_id":154717866845798400,"first_online_time":1574668642000,"alarm_storage_days":"0","video_storage_days":"0","support_ability":{"alarmAbility":[{"alarmType":8,"subAlarmType":[0]},{"alarmType":3,"subAlarmType":[1]}],"ptzAbility":{"direction":1},"cloudStorageAbility":{"eventStorage":1},"distributionNetworkAbility":[2],"connectNetworkAbility":[2]}}
     */

    private DevicesBean device;

    public DevicesBean getDevice() {
        return device;
    }

    public void setDevice(DevicesBean device) {
        this.device = device;
    }
}

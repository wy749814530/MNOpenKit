package com.mn.bean.restfull;

/**
 * Created by WIN on 2018/6/6.
 */

public class LoginBean extends BaseBean {
    /**
     * msg:OK
     * access_token : u_2c205a17405e44e591e1a963de371496
     * code : 2000
     * user_id : 174947673885904896
     */

    private String access_token;
    private String user_id;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}

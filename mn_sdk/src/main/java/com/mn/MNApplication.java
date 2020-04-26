package com.mn;

import android.app.Application;

import com.mn.bean.restfull.ServerMsgBean;

/**
 * Created by Administrator on 2020/3/9 0009.
 */

public abstract class MNApplication extends Application {
    private static MNApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    public static MNApplication getApplication() {
        return mApplication;
    }

    public abstract void OnServerMSG(ServerMsgBean data);
}

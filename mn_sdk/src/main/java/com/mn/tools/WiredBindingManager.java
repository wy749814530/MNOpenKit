package com.mn.tools;

import com.mn.bean.restfull.DevStateInfoBean;
import com.mn.bean.setting.LanguageBean;

import MNSDK.MNJni;
import MNSDK.MNKit;
import MNSDK.MNOpenSDK;
import MNSDK.inface.MNKitInterface;
import MNSDK.inface.MNOpenSDKInterface;

/**
 * 有线绑定管理类（Wired binding management class）
 */
public class WiredBindingManager {
    private WiredBindingCallBack mCallBack;
    private int mTimeout = 60 * 1000;
    private final static int Domain_Cookie = 1000;
    private final static int DevOnline_Cookie = 2000;
    private String mDevSn = "";
    private MTimerTask mDomainTask, mDevOnlineTask;
    private MOnTimerListener mOnTimerListener = new MOnTimerListener();
    private MLanguageConfigCallBack mLanguageCallBack = new MLanguageConfigCallBack();

    private WiredBindingManager() {
    }

    private static class Factory {
        private static WiredBindingManager INSTANCE = new WiredBindingManager();
    }

    /**
     * 获取有线绑定管理类（Get the wired binding management class）
     *
     * @return
     */
    public static WiredBindingManager getIntance() {
        return Factory.INSTANCE;
    }

    private class MOnTimerListener implements MTimerTask.OnTimerListener {

        @Override
        public void OnTimerRun(int cookie) {
            if (cookie == Domain_Cookie) {
                MNJni.SendRedirectDomainMSG(mDevSn);
            } else if (cookie == DevOnline_Cookie) {
                MNOpenSDK.getLanguageConfig(mDevSn, mLanguageCallBack);
            }
        }

        @Override
        public void OnTimerFinished(int cookie) {
            if (cookie == DevOnline_Cookie) {
                if (mCallBack != null) {
                    mCallBack.onWiredBindingFailed();
                }
            }
        }
    }

    private class MLanguageConfigCallBack implements MNOpenSDKInterface.LanguageConfigCallBack {

        @Override
        public void onLanguageConfig(LanguageBean bean) {
            if (bean.isResult() && bean.getParams() != null) {
                try {
                    if (mDomainTask != null) {
                        mDomainTask.stopPostDelay();
                        mDomainTask.setOnTimerListener(null);
                        mDomainTask = null;
                    }
                    if (mDevOnlineTask != null) {
                        mDevOnlineTask.stopPostDelay();
                        mDevOnlineTask.setOnTimerListener(null);
                        mDevOnlineTask = null;
                    }

                    if (mCallBack != null) {
                        mCallBack.onWiredBindingSuc(mDevSn);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 开始有线网络配置（Start wired network configuration）
     *
     * @param devSn    设备SN
     * @param callBack 回调方法
     */
    public void startWiredBinding(String devSn, WiredBindingCallBack callBack) {
        mDevSn = devSn;
        mCallBack = callBack;
        MNKit.getDeviceStateInfoWithSn(devSn, new MNKitInterface.DeviceStateInfoCallBack() {
            @Override
            public void onGetDeviceStateFailed(String message) {
                if (mCallBack != null) {
                    mCallBack.onWiredBindingFailed();
                }
            }

            @Override
            public void onGetDeviceStateSuc(DevStateInfoBean response) {
                if (response != null && response.getCode() == 2000) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int type = response.getType();
                            if (type == 10 || type == 16) {
                                MNJni.SetDeviceVersionType(mDevSn, false);
                            } else {
                                MNJni.SetDeviceVersionType(mDevSn, true);
                            }

                            MNJni.SendRedirectDomainMSG(mDevSn);
                            mDomainTask = new MTimerTask(mOnTimerListener, Domain_Cookie);
                            mDomainTask.postDelayed(mTimeout, 4 * 1000);

                            MNOpenSDK.getLanguageConfig(mDevSn, mLanguageCallBack);
                            mDevOnlineTask = new MTimerTask(mOnTimerListener, DevOnline_Cookie);
                            mDevOnlineTask.postDelayed(mTimeout, 4 * 1000);
                        }
                    }).start();
                }
            }
        });
    }

    /**
     * 停止配网（Stop distribution）
     */
    public void destory() {
        mCallBack = null;
        if (mDomainTask != null) {
            mDomainTask.stopPostDelay();
        }
        if (mDevOnlineTask != null) {
            mDevOnlineTask.stopPostDelay();
        }
    }

    /**
     * 有线绑定设备网络配置回调（Wired bonding device network configuration callback）
     */
    public interface WiredBindingCallBack {
        void onWiredBindingSuc(String sn);

        void onWiredBindingFailed();
    }
}

package MNSDK;

import java.util.ArrayList;

import MNSDK.inface.MNSessionCtrlFace;

/**
 * 人脸考勤设备相关监听
 */
public class MNSessionCtrlProcessor {
    private ArrayList<MNSessionCtrlFace> observers;

    private MNSessionCtrlProcessor() {
        observers = new ArrayList<>();
    }

    private static class Factory {
        private static MNSessionCtrlProcessor etsProcessor = new MNSessionCtrlProcessor();
    }

    public static MNSessionCtrlProcessor getInstance() {
        return MNSessionCtrlProcessor.Factory.etsProcessor;
    }

    /**
     * 注册人脸考勤设备相关监听
     * @param o
     */
    public void register(MNSessionCtrlFace o) {
        synchronized (observers) {
            if (!observers.contains(o)) {
                observers.add(o);
            }
        }
    }
    /**
     * 注销人脸考勤设备相关监听
     * @param o
     */
    public void unregister(MNSessionCtrlFace o) {
        synchronized (observers) {
            if (observers.contains(o)) {
                observers.remove(o);
            }
        }
    }
    public void OnSessionCtrl(long lTaskContext, int nChannelId, int eSessionCtrlType, String data, int nlen) {
        synchronized (observers) {
            for (int i = 0; i < observers.size(); i++) {
                MNSessionCtrlFace obj = observers.get(i);
                obj.OnSessionCtrl(lTaskContext, nChannelId, eSessionCtrlType, data, nlen);
            }
        }
    }
}

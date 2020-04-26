package MNSDK;

import java.util.ArrayList;

import MNSDK.inface.IMNBindDeviceFace;

/**
 * 绑定设备监听
 */
public class MNBindDevProcessor {
    private ArrayList<IMNBindDeviceFace> observers;

    private MNBindDevProcessor() {
        observers = new ArrayList<>();
    }

    private static class Factory {
        private static MNBindDevProcessor etsProcessor = new MNBindDevProcessor();
    }

    public static MNBindDevProcessor getInstance() {
        return Factory.etsProcessor;
    }

    /**
     * 注册绑定设备监听
     *
     * @param o  监听类
     */
    public void register(IMNBindDeviceFace o) {
        synchronized (observers) {
            if (!observers.contains(o)) {
                observers.add(o);
            }
        }
    }

    /**
     * 注销绑定设备监听
     *
     * @param o 监听类
     */
    public void unregister(IMNBindDeviceFace o) {
        synchronized (observers) {
            if (observers.contains(o)) {
                observers.remove(o);
            }
        }
    }

    public void OnRequestToBindDevice(String pszJson, int nlen) {
        synchronized (observers) {
            for (int i = 0; i < observers.size(); i++) {
                IMNBindDeviceFace obj = observers.get(i);
                obj.OnRequestToBindDevice(pszJson, nlen);
            }
        }
    }
}

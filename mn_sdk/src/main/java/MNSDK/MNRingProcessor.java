package MNSDK;

import java.util.ArrayList;

import MNSDK.inface.IMNRingFace;

/**
 * 门铃呼叫监听
 */
public class MNRingProcessor {
    private ArrayList<IMNRingFace> observers;

    private MNRingProcessor() {
        observers = new ArrayList<>();
    }

    private static class Factory {
        private static MNRingProcessor etsProcessor = new MNRingProcessor();
    }

    public static MNRingProcessor getInstance() {
        return Factory.etsProcessor;
    }

    /**
     * 注册门铃呼叫监听
     * @param o
     */
    public void register(IMNRingFace o) {
        synchronized (observers) {
            if (!observers.contains(o)) {
                observers.add(o);
            }
        }
    }
    /**
     * 注销门铃呼叫监听
     * @param o
     */
    public void unregister(IMNRingFace o) {
        synchronized (observers) {
            if (observers.contains(o)) {
                observers.remove(o);
            }
        }
    }

    public void OnDevOnline(String data, int length) {
        synchronized (observers) {
            for (int i = 0; i < observers.size(); i++) {
                IMNRingFace obj = observers.get(i);
                obj.OnDevOnline(data, length);
            }
        }
    }

    public void OnRingCall(String data, int length) {
        synchronized (observers) {
            for (int i = 0; i < observers.size(); i++) {
                IMNRingFace obj = observers.get(i);
                obj.OnRingCall(data, length);
            }
        }
    }
}

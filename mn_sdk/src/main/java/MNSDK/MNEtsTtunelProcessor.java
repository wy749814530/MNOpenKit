package MNSDK;

import java.util.ArrayList;

import MNSDK.inface.IMNEtsTunnelFace;


/**
 * 透传消息监听
 */
public class MNEtsTtunelProcessor {
    private ArrayList<IMNEtsTunnelFace> observers;

    private MNEtsTtunelProcessor() {
        observers = new ArrayList<>();
    }

    private static class Factory {
        private static MNEtsTtunelProcessor etsProcessor = new MNEtsTtunelProcessor();
    }

    public static MNEtsTtunelProcessor getInstance() {
        return Factory.etsProcessor;
    }

    /**
     * 注册透传消息监听
     * @param o
     */
    public void register(IMNEtsTunnelFace o) {
        synchronized (observers) {
            if (!observers.contains(o)) {
                observers.add(o);
            }
        }
    }

    /**
     * 注销透传消息监听
     * @param o
     */
    public void unregister(IMNEtsTunnelFace o) {
        synchronized (observers) {
            if (observers.contains(o)) {
                observers.remove(o);
            }
        }
    }

    public void OnEtsTunnel(String uuid, String data, int length) {
        synchronized (observers) {
            for (int i = 0; i < observers.size(); i++) {
                IMNEtsTunnelFace obj = observers.get(i);
                obj.OnEtsTunnel(uuid, data, length);
            }
        }
    }
}

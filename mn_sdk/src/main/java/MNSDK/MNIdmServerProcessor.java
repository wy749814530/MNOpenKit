package MNSDK;

import java.util.ArrayList;

import MNSDK.inface.MNIdmServerFace;


/**
 * Created by Administrator on 2018/11/6 0006.
 */

public class MNIdmServerProcessor {
    private ArrayList<MNIdmServerFace> observers;

    private MNIdmServerProcessor() {
        observers = new ArrayList<>();
    }

    private static class Factory {
        private static MNIdmServerProcessor etsProcessor = new MNIdmServerProcessor();
    }

    public static MNIdmServerProcessor getInstance() {
        return Factory.etsProcessor;
    }

    /**
     * 注册IDM服务上下线监听
     *
     * @param o
     */
    public void register(MNIdmServerFace o) {
        synchronized (observers) {
            if (!observers.contains(o)) {
                observers.add(o);
            }
        }
    }

    /**
     * 注销DM服务上下线监听
     *
     * @param o
     */
    public void unregister(MNIdmServerFace o) {
        synchronized (observers) {
            if (observers.contains(o)) {
                observers.remove(o);
            }
        }
    }

    public void OnIDMServerLoginStatus(int status) {
        synchronized (observers) {
            for (int i = 0; i < observers.size(); i++) {
                MNIdmServerFace obj = observers.get(i);
                obj.OnIDMServerLoginStatus(status);
            }
        }
    }
}

package MNSDK;

import java.util.ArrayList;

import MNSDK.inface.IMNDownloadFace;


/**
 * 从设备SD卡中下载数据监听
 */
public class MNDownloadProcessor {
    private ArrayList<IMNDownloadFace> observers;

    private MNDownloadProcessor() {
        observers = new ArrayList<>();
    }

    private static class Factory {
        private static MNDownloadProcessor etsProcessor = new MNDownloadProcessor();
    }

    public static MNDownloadProcessor getInstance() {
        return Factory.etsProcessor;
    }

    /**
     * 注册下载监听
     *
     * @param o
     */
    public void register(IMNDownloadFace o) {
        synchronized (observers) {
            if (!observers.contains(o)) {
                observers.add(o);
            }
        }
    }
    /**
     * 注销下载监听
     *
     * @param o
     */
    public void unregister(IMNDownloadFace o) {
        synchronized (observers) {
            if (observers.contains(o)) {
                observers.remove(o);
            }
        }
    }

    public void OnPicDownloadData(String pDestSID, int nChannelId, byte[] picData, int uLen, String id) {
        synchronized (observers) {
            for (int i = 0; i < observers.size(); i++) {
                IMNDownloadFace obj = observers.get(i);
                obj.OnPicDownloadData(pDestSID, nChannelId, picData, uLen, id);
            }
        }
    }
}

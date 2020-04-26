package MNSDK;

import java.util.ArrayList;

import MNSDK.inface.IMNVideoFace;

/**
 * 直播，回放先关监听
 */
public class MNVideoProcessor {
    private ArrayList<IMNVideoFace> observers;

    private MNVideoProcessor() {
        observers = new ArrayList<>();
    }

    private static class Factory {
        private static MNVideoProcessor mnVProcessor = new MNVideoProcessor();
    }

    public static MNVideoProcessor getInstance() {
        return Factory.mnVProcessor;
    }

    /**
     * 注册直播，回放先关监听
     * @param o
     */
    public void register(IMNVideoFace o) {
        synchronized (observers) {
            if (!observers.contains(o)) {
                observers.add(o);
            }
        }
    }
    /**
     * 注销直播，回放先关监听
     * @param o
     */
    public void unregister(IMNVideoFace o) {
        synchronized (observers) {
            if (observers.contains(o)) {
                observers.remove(o);
            }
        }
    }

    public void OnVideoDataByte(long lTaskContext, int nChannelId, long userdata, int dataType, byte[] data, int nDataLen, byte[] y, byte[] u, byte[] v, int nWidth, int nHeight, int nYStride, int nUStride, int nVStride, int nFps, int nSliceType, int nYear, int nMonth, int nDay, int nHour, int nMinute, int nSecond, long lNetworkFlowPerSecond, long lTotalFlow) {
        synchronized (observers) {
            for (int i = 0; i < observers.size(); i++) {
                IMNVideoFace obj = observers.get(i);
                obj.OnVideoDataByte(lTaskContext, nChannelId, userdata, dataType, data, nDataLen, y, u, v, nWidth, nHeight, nYStride, nUStride, nVStride, nFps, nSliceType, nYear, nMonth, nDay, nHour, nMinute, nSecond, lNetworkFlowPerSecond, lTotalFlow);
            }
        }
    }

    public void OnAudioDataByte(long lTaskContext, int nChannelId, long userdata, byte[] InData, int nDataLen, int nEncodeType) {
        synchronized (observers) {
            for (int i = 0; i < observers.size(); i++) {
                IMNVideoFace obj = observers.get(i);
                obj.OnAudioDataByte(lTaskContext, nChannelId, userdata, InData, nDataLen, nEncodeType);            }
        }
    }

    public void OnTaskStatus(long lTaskContext, long userdata, int nTaskStatus, float fProgress) {
        synchronized (observers) {
            for (int i = 0; i < observers.size(); i++) {
                IMNVideoFace obj = observers.get(i);
                obj.OnTaskStatus(lTaskContext, userdata, nTaskStatus, fProgress);
            }
        }
    }

    public void OnSessionCtrl(long lTaskContext, int nChannelId, int eSessionCtrlType, String data, int nlen) {
        synchronized (observers) {
            for (int i = 0; i < observers.size(); i++) {
                IMNVideoFace obj = observers.get(i);
                obj.OnSessionCtrl(lTaskContext, nChannelId, eSessionCtrlType, data, nlen);
            }
        }
    }
}

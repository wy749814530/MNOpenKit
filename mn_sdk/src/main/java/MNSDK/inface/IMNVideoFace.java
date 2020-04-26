package MNSDK.inface;

/**
 * Created by Administrator on 2018/11/9 0009.
 */

public interface IMNVideoFace {
    public void OnVideoDataByte(long lTaskContext, int nChannelId, long userdata, int dataType, byte[] data, int nDataLen, byte[] y, byte[] u, byte[] v, int nWidth, int nHeight, int nYStride, int nUStride, int nVStride, int nFps, int nSliceType, int nYear, int nMonth, int nDay, int nHour, int nMinute, int nSecond, long lNetworkFlowPerSecond, long lTotalFlow);
    public void OnAudioDataByte(long lTaskContext, int nChannelId, long userdata, byte[] InData, int nDataLen, int nEncodeType);
    public void OnTaskStatus(long lTaskContext, long userdata, int eTaskStatus, float fProgress);
    public void OnSessionCtrl(long lTaskContext, int nChannelId, int eSessionCtrlType, String data, int length);
}

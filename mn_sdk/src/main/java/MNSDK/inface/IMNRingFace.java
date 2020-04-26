package MNSDK.inface;

/**
 * Created by Administrator on 2018/11/9 0009.
 */

public interface IMNRingFace {
    public void OnDevOnline(String data, int length);
    public void OnRingCall(String data, int length);
}

package MNSDK.inface;

/**
 * Created by Administrator on 2019/8/7 0007.
 */

public interface MNSessionCtrlFace {
    void OnSessionCtrl(long lTaskContext, int nChannelId, int eSessionCtrlType, String data, int nlen);
}

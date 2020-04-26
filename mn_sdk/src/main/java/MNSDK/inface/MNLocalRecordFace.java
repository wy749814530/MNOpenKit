package MNSDK.inface;

/**
 * Created by Administrator on 2019/8/22 0022.
 */

public interface MNLocalRecordFace {
    void OnLocalRecord(long lTaskContext, int nChannelId, int eSessionCtrlType, String pszJson, int nlen);
}

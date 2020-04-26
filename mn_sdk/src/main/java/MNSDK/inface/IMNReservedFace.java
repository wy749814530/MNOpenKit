package MNSDK.inface;

/**
 * Created by Administrator on 2018/11/9 0009.
 */

public interface IMNReservedFace {
    public void OnStatus(long lTaskContext, int nType, int nStatus, String destSID, long userdata);
    public void OnCommand(long lTaskContext, int nChannelID, int nCmd, int nValue, String jsonData, int nLen);
    public void OnTunnel(String uuid, String data, int length);
}

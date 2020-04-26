package MNSDK.inface;

/**
 * Created by Administrator on 2018/11/9 0009.
 */

public interface IMNDownloadFace {
    public void OnPicDownloadData(String pDestSID, int nChannelId, byte[] picData, int uLen, String index);
}

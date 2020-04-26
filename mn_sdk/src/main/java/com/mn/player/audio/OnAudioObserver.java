package com.mn.player.audio;


/**
 * Created by WIN on 2018/5/18.
 */

public interface OnAudioObserver{
    void onRunableAudioData(long lTaskContext, int nChannelId, long userdata, byte[] InData, int nDataLen, int nEncodeType);
}

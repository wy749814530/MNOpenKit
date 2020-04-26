package com.mn.player.audio;

/**
 * Created by WIN on 2018/5/21.
 */

public class AudioBean {
    private long _lTaskContext;
    private int _nChannelId;
    private long _userdata;
    private byte[] _InData;
    private int _nDataLen;
    private int _nEncodeType;

    public void set_InData(byte[] _InData) {
        this._InData = _InData;
    }

    public void set_lTaskContext(long _lTaskContext) {
        this._lTaskContext = _lTaskContext;
    }

    public void set_nChannelId(int _nChannelId) {
        this._nChannelId = _nChannelId;
    }

    public void set_nDataLen(int _nDataLen) {
        this._nDataLen = _nDataLen;
    }

    public void set_nEncodeType(int _nEncodeType) {
        this._nEncodeType = _nEncodeType;
    }

    public void set_userdata(long _userdata) {
        this._userdata = _userdata;
    }

    public byte[] get_InData() {
        return _InData;
    }

    public int get_nChannelId() {
        return _nChannelId;
    }

    public int get_nDataLen() {
        return _nDataLen;
    }

    public int get_nEncodeType() {
        return _nEncodeType;
    }

    public long get_lTaskContext() {
        return _lTaskContext;
    }

    public long get_userdata() {
        return _userdata;
    }
}


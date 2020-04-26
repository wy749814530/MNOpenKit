package com.mn.player.video;

import java.nio.ByteBuffer;

/**
 * Created by WIN on 2018/5/21.
 */

public class VideoBean {
    private long lTaskContext;
    private int nChannelId;
    private long userdata;
    private int dataType;
    private byte[] data;
    private int nDataLen;
    private ByteBuffer y;
    private ByteBuffer u;
    private ByteBuffer v;
    private int nWidth;
    private int nHeight;
    private int nYStride;
    private int nUStride;
    private int nVStride;
    private int nFps;
    private int nSliceType;
    private int nYear;
    private int nMonth;
    private int nDay;
    private int nHour;
    private int nMinute;
    private int nSecond;
    private long lNetworkFlowPerSecond;
    private long lTotalFlow;

    public VideoBean(long lTaskContext, int nChannelId, long userdata, int dataType, byte[] data, int nDataLen, ByteBuffer y, ByteBuffer u, ByteBuffer v,
                     int nWidth, int nHeight, int nYStride, int nUStride, int nVStride, int nFps, int nSliceType, int nYear, int nMonth,
                     int nDay, int nHour, int nMinute, int nSecond, long lNetworkFlowPerSecond, long lTotalFlow){
        this.lTaskContext = lTaskContext;
        this.nChannelId = nChannelId;
        this.userdata = userdata;
        this.dataType = dataType;
        this.data = data;
        this.nDataLen = nDataLen;
        this.y = y;
        this.u = u;
        this.v = v;
        this.nWidth = nWidth;
        this.nHeight = nHeight;
        this.nYStride = nYStride;
        this.nUStride = nUStride;
        this.nVStride = nVStride;
        this.nFps = nFps;
        this.nSliceType = nSliceType;
        this.nYear = nYear;
        this.nMonth = nMonth;
        this.nDay = nDay;
        this.nHour = nHour;
        this.nMinute = nMinute;
        this.nSecond = nSecond;
        this.lNetworkFlowPerSecond = lNetworkFlowPerSecond;
        this.lTotalFlow = lTotalFlow;
    }

    public byte[] getData() {
        return data;
    }

    public ByteBuffer getU() {
        return u;
    }

    public ByteBuffer getV() {
        return v;
    }

    public ByteBuffer getY() {
        return y;
    }

    public int getDataType() {
        return dataType;
    }

    public int getnChannelId() {
        return nChannelId;
    }

    public int getnDataLen() {
        return nDataLen;
    }

    public int getnDay() {
        return nDay;
    }

    public int getnFps() {
        return nFps;
    }

    public int getnHeight() {
        return nHeight;
    }

    public int getnHour() {
        return nHour;
    }

    public int getnMinute() {
        return nMinute;
    }

    public int getnMonth() {
        return nMonth;
    }

    public int getnSecond() {
        return nSecond;
    }

    public int getnSliceType() {
        return nSliceType;
    }

    public int getnUStride() {
        return nUStride;
    }

    public int getnVStride() {
        return nVStride;
    }

    public int getnWidth() {
        return nWidth;
    }

    public int getnYear() {
        return nYear;
    }

    public int getnYStride() {
        return nYStride;
    }

    public long getlNetworkFlowPerSecond() {
        return lNetworkFlowPerSecond;
    }

    public long getlTaskContext() {
        return lTaskContext;
    }

    public long getUserdata() {
        return userdata;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public void setlTaskContext(long lTaskContext) {
        this.lTaskContext = lTaskContext;
    }

    public void setlNetworkFlowPerSecond(long lNetworkFlowPerSecond) {
        this.lNetworkFlowPerSecond = lNetworkFlowPerSecond;
    }

    public void setnChannelId(int nChannelId) {
        this.nChannelId = nChannelId;
    }

    public void setnDataLen(int nDataLen) {
        this.nDataLen = nDataLen;
    }

    public void setnDay(int nDay) {
        this.nDay = nDay;
    }

    public void setnFps(int nFps) {
        this.nFps = nFps;
    }

    public void setnHeight(int nHeight) {
        this.nHeight = nHeight;
    }

    public void setnHour(int nHour) {
        this.nHour = nHour;
    }

    public void setnMinute(int nMinute) {
        this.nMinute = nMinute;
    }

    public void setnMonth(int nMonth) {
        this.nMonth = nMonth;
    }

    public void setnSecond(int nSecond) {
        this.nSecond = nSecond;
    }

    public void setnSliceType(int nSliceType) {
        this.nSliceType = nSliceType;
    }

    public void setnUStride(int nUStride) {
        this.nUStride = nUStride;
    }

    public void setnVStride(int nVStride) {
        this.nVStride = nVStride;
    }

    public void setnWidth(int nWidth) {
        this.nWidth = nWidth;
    }

    public void setnYear(int nYear) {
        this.nYear = nYear;
    }

    public void setnYStride(int nYStride) {
        this.nYStride = nYStride;
    }

    public void setU(ByteBuffer u) {
        this.u = u;
    }

    public void setUserdata(long userdata) {
        this.userdata = userdata;
    }

    public void setV(ByteBuffer v) {
        this.v = v;
    }

    public void setY(ByteBuffer y) {
        this.y = y;
    }

    public void setlTotalFlow(long lTotalFlow) {
        this.lTotalFlow = lTotalFlow;
    }

    public long getlTotalFlow() {
        return lTotalFlow;
    }
}


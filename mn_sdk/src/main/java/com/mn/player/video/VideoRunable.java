package com.mn.player.video;


import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.Deque;

import static java.lang.Thread.sleep;

/**
 * Created by WIN on 2018/5/18.
 */

public class VideoRunable implements Runnable {
    private OnVideoObserver _runableVideo;
    private volatile Deque<VideoBean> VDeque = new ArrayDeque<>();
    private Thread thread = null;
    public void writeVideo(long lTaskContext, int nChannelId, long userdata, int dataType, byte[] data, int nDataLen, ByteBuffer y, ByteBuffer u, ByteBuffer v,
                           int nWidth, int nHeight, int nYStride, int nUStride, int nVStride, int nFps, int nSliceType,
                           int nYear, int nMonth, int nDay, int nHour, int nMinute, int nSecond, long lNetworkFlowPerSecond, long lTotalFlow) {
        if (thread!=null && VDeque.size() < 6) {
            VideoBean videoBean = new VideoBean(lTaskContext, nChannelId, userdata, dataType, data, nDataLen, y, u, v,
                    nWidth, nHeight, nYStride, nUStride, nVStride, nFps, nSliceType,
                    nYear, nMonth, nDay, nHour, nMinute, nSecond, lNetworkFlowPerSecond, lTotalFlow);

            VDeque.addLast(videoBean);
        }
    }
    public boolean isRunning(){
        return thread!=null;
    }
    public VideoRunable(OnVideoObserver runableVideo) {
        this._runableVideo = runableVideo;
    }

    public void clearAll() {
        VDeque.clear();
    }

    public synchronized void startRun() {
        if (thread == null) {
            clearAll();
            thread = new Thread(this);
            thread.start();
        }
    }

    public synchronized void stopRun() {
        if (thread != null) {
            thread.interrupt();
            thread = null;
        }
        clearAll();
    }

    @Override
    public void run() {
        while (thread != null && !thread.isInterrupted()){
            if (!VDeque.isEmpty() && VDeque.size() > 0) {
                VideoBean mVideoBean = VDeque.pollFirst();
                if (mVideoBean != null) {
                    _runableVideo.onRunableVideoData(mVideoBean);
                }
                mVideoBean = null;
            }

            try {
                sleep(10);
            } catch (InterruptedException e) {
                break; // 当调用线程的interrupt()方法时，系统会抛出一个InterruptedException异常，代码中通过捕获异常，然后break跳出循环状态，使线程正常结束。
            }
        }
    }
}

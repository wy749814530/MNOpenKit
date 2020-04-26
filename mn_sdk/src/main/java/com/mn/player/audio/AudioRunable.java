package com.mn.player.audio;

import java.util.ArrayDeque;
import java.util.Deque;

import static java.lang.Thread.sleep;

/**
 * Created by WIN on 2018/5/18.
 */

public class AudioRunable implements Runnable {
    private OnAudioObserver _runableAudio;
    private volatile Deque<AudioBean> deque = new ArrayDeque<>();
    private Thread thread = null;

    public void writeAudio(long lTaskContext, int nChannelId, long userdata, byte[] InData, int nDataLen, int nEncodeType) {
        if (thread != null) {
            AudioBean audioBean = new AudioBean();
            audioBean.set_lTaskContext(lTaskContext);
            audioBean.set_nChannelId(nChannelId);
            audioBean.set_userdata(userdata);
            audioBean.set_InData(InData);
            audioBean.set_nDataLen(nDataLen);
            audioBean.set_nEncodeType(nEncodeType);
            deque.addLast(audioBean);
        }
    }

    public AudioRunable(OnAudioObserver runableAudio) {
        this._runableAudio = runableAudio;
    }

    public void clearAll() {
        deque.clear();
    }


    public boolean isRunning(){
        return thread!=null;
    }

    public void startRun() {
        synchronized (this) {
            if (thread == null) {
                clearAll();
                thread = new Thread(this);
                thread.start();
            }
        }

    }

    public void stopRun() {
        synchronized (this) {
            if (thread != null) {
                thread.interrupt();
                thread = null;
            }
            clearAll();
        }

    }

    @Override
    public void run() {
        while (thread != null && !thread.isInterrupted()) {
            if (deque.size() > 10) {
                while (deque.size() > 5) {
                    deque.pollFirst();
                }
            }
            if (!deque.isEmpty() && deque.size() > 0) {
                AudioBean mAudioBean = deque.pollFirst();
                if (mAudioBean != null) {
                    _runableAudio.onRunableAudioData(mAudioBean.get_lTaskContext(), mAudioBean.get_nChannelId(), mAudioBean.get_userdata(), mAudioBean.get_InData(), mAudioBean.get_nDataLen(), mAudioBean.get_nEncodeType());
                }
            }

            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
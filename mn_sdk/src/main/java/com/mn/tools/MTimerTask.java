package com.mn.tools;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by WIN on 2017/12/1.
 */

public class MTimerTask {

    private static final Handler handler = new Handler(Looper.getMainLooper());
    private OnTimerListener mListener;
    private boolean isStopAction = false;
    private long mRunNum = 0;
    private long mMaxNum = 0;          // 最大允许次数：0表示无限循环
    private long mDelayMillis = 100;  // 每次循环的最大延时
    private int mCookie = 0;

    public MTimerTask() {

    }

    public MTimerTask(OnTimerListener listener, int cookie) {
        this.mListener = listener;
        this.mCookie = cookie;
    }

    private Runnable mPullTasl = new Runnable() {
        @Override
        public void run() {
            if (isStopAction) {
                return;
            }
            if (mMaxNum == 0) {
                mListener.OnTimerRun(mCookie);
                handler.postDelayed(mPullTasl, mDelayMillis);
                return;
            } else if (mMaxNum < 0) {
                stopPostDelay();
                mListener.OnTimerFinished(mCookie);
                return;
            }

            if (mRunNum <= 0) {
                stopPostDelay();
                mListener.OnTimerFinished(mCookie);
            } else if (mRunNum > 0) {
                mRunNum--;
                mListener.OnTimerRun(mCookie);
                handler.postDelayed(mPullTasl, mDelayMillis);
            }
        }
    };

    /**
     * @param delayMillis 延时多长时间
     * @param runNum      执行次数， 0 表示无限循环
     */
    public void postDelayed(long delayMillis, long runNum) {
        this.mDelayMillis = delayMillis;
        this.mRunNum = runNum;
        this.mMaxNum = runNum;
        isStopAction = false;
        handler.postDelayed(mPullTasl, delayMillis);
    }

    /**
     * 停止循环延时任务。当主动调用stopPostDelay() 之后，将不会在回调OnTimerFinished()方法，只有自然执行到最后时才会回调。
     */
    public void stopPostDelay() {
        isStopAction = true;
        this.mRunNum = -1;
        this.mMaxNum = -1;
        handler.removeCallbacks(mPullTasl);
    }

    public void setOnTimerListener(OnTimerListener onTimerListener) {
        mListener = onTimerListener;
    }

    public interface OnTimerListener {
        void OnTimerRun(int cookie);

        void OnTimerFinished(int cookie);
    }
}

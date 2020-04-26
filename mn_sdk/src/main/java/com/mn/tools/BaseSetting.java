package com.mn.tools;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2019/12/13 0013.
 */

public abstract class BaseSetting {
    protected static final ExecutorService threadPool = Executors.newCachedThreadPool();
    protected static final Handler mainHandler = new Handler(Looper.getMainLooper());
    protected static ArrayList<String> linkedSns = new ArrayList<>();

    protected static final int SDK_TIMEOUT = 15;

    public static String arrayInt2String(int[] channels) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < channels.length; i++) {
            if (i == (channels.length - 1)) {
                sb.append(channels[i]);
            } else {
                sb.append(channels[i] + ",");
            }
        }
        return "[" + sb.toString() + "]";
    }

    public static String arrayString2String(String[] SafetyMeasure) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < SafetyMeasure.length; i++) {
            if (i == (SafetyMeasure.length - 1)) {
                sb.append(SafetyMeasure[i]);
            } else {
                sb.append(SafetyMeasure[i] + ",");
            }
        }
        return "[" + sb.toString() + "]";
    }
}

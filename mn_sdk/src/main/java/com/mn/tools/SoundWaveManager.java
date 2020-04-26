package com.mn.tools;

import android.util.Log;

import com.mn.tools.LocalDataUtils;

import MNSDK.MNOpenSDK;
import voice.encoder.DataEncoder;
import voice.encoder.VoicePlayer;
import voice.encoder.VoicePlayerListener;

/**
 * Created by Administrator on 2020/1/6 0006.
 */

/**
 * SoundWaveManager 声波管理类(Sonic Management)
 */
public class SoundWaveManager {
    private VoicePlayer player;
    private int[] CODE_FREQUENCY = new int[23];

    private SoundWaveManager() {
        player = new VoicePlayer();

        player.setPlayerType(VoicePlayer.PT_SoundPlayer);
        for (int i = 0; i < 23; i++) {
            CODE_FREQUENCY[i] = 4000 + i * 150;
        }
        player.setFreqs(CODE_FREQUENCY);
    }

    private static class Factory {
        private static SoundWaveManager INSTANCE = new SoundWaveManager();
    }

    /**
     * 获取声波管理类(Get Sonic Management Class)
     *
     * @return
     */
    public static SoundWaveManager getIntance() {
        return Factory.INSTANCE;
    }

    /**
     * 设置声波监听器(Setting up a sonic monitor)
     *
     * @param listener
     */
    public void setListener(VoicePlayerListener listener) {
        player.setListener(listener);
    }

    /**
     * 发送声波(Send sound waves)
     *
     * @param ssid     WIFI -- ssid
     * @param password WIFI -- password
     */
    public void sendMsg(String ssid, String password) {
        String host = MNOpenSDK.getDomain();
        String domain_area = "CN";
        String domain = "cn";
        if (host.contains("cn")) {
            //中国
            domain = "cn";
            domain_area = "CN";
        } else if (host.contains("us")) {
            //美国
            domain = "us";
            domain_area = "US";
        } else if (host.contains("in")) {
            //印度
            domain = "in";
            domain_area = "IN";
        } else if (host.contains("te")) {
            domain = "te";
            domain_area = "TE";
        } else if (host.contains("DV")) {
            domain = "dv";
            domain_area = "DV";
        }
        char[] chars = domain_area.toCharArray();//区域Ask11
        char[] chars1 = domain.toCharArray();//域名Ask11

        int dd1 = chars[0];
        int dd2 = chars[1];
        int yy1 = chars1[0];
        int yy2 = chars1[1];

        int d1 = dd1 - 65;
        int d2 = dd2 - 65;
        int y1 = yy1 - 65;
        int y2 = yy2 - 65;

        String ddyyString = LocalDataUtils.getUseId() + covertString(d1, d2, y1, y2);
        String user_domain = DataEncoder.encodeManniuString(ddyyString);
        String wifi_info = DataEncoder.encodeSSIDWiFi(ssid, password);

        new Thread(() -> {
            try {
                // 发送声波
                player.play(wifi_info, 1, 1000);
                player.play(user_domain, 1, 2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 停止发送声波（Stop sending sound waves）
     */
    public void destroy() {
        new Thread(() -> {
            if (player != null) {
                player.stop();
            }
        }).start();
    }

    private String covertString(int a, int b, int c, int d) {
        String covertStr = "";
        if (a < 10) {
            covertStr = covertStr + "0" + a;
        } else {
            covertStr = covertStr + a;
        }

        if (b < 10) {
            covertStr = covertStr + "0" + b;
        } else {
            covertStr = covertStr + b;
        }

        if (c < 10) {
            covertStr = covertStr + "0" + c;
        } else {
            covertStr = covertStr + c;
        }

        if (d < 10) {
            covertStr = covertStr + "0" + d;
        } else {
            covertStr = covertStr + d;
        }

        return covertStr;
    }
}

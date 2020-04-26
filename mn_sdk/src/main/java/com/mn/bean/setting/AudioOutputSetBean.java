package com.mn.bean.setting;

/**
 * Created by Administrator on 2019/3/27 0027.
 */

public class AudioOutputSetBean {

    /**
     * channel : 0
     * AudioOutputVolume : 20
     */

    private int channel;
    private int AudioOutputVolume;

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public int getAudioOutputVolume() {
        return AudioOutputVolume;
    }

    public void setAudioOutputVolume(int AudioOutputVolume) {
        this.AudioOutputVolume = AudioOutputVolume;
    }
}

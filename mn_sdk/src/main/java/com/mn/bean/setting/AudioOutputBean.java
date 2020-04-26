package com.mn.bean.setting;

/**
 * Created by Administrator on 2019/3/25 0025.
 */

public class AudioOutputBean {

    /**
     * channel : 0
     * result : true
     * params : {"AudioOutputVolume":50}
     */

    private int channel;
    private boolean result;
    private ParamsBean params;

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public static class ParamsBean {
        /**
         * AudioOutputVolume : 50
         */

        private int AudioOutputVolume;

        public int getAudioOutputVolume() {
            return AudioOutputVolume;
        }

        public void setAudioOutputVolume(int AudioOutputVolume) {
            this.AudioOutputVolume = AudioOutputVolume;
        }
    }
}

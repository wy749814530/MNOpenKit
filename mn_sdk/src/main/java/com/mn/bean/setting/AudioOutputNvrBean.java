package com.mn.bean.setting;

import java.util.List;

/**
 * Created by Administrator on 2019/3/27 0027.
 */

public class AudioOutputNvrBean {

    /**
     * result : true
     * params : [{"channel":0,"params":{"AudioOutputVolume":40},"result":true},{"channel":1,"params":{"AudioOutputVolume":40},"result":true}]
     */

    private boolean result;
    private List<ParamsBean> params;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public List<ParamsBean> getParams() {
        return params;
    }

    public void setParams(List<ParamsBean> params) {
        this.params = params;
    }

    public static class ParamsBean {
        /**
         * channel : 0
         * params : {"AudioOutputVolume":40}
         * result : true
         */

        private int channel;
        private ParamsBase params;
        private boolean result;

        public int getChannel() {
            return channel;
        }

        public void setChannel(int channel) {
            this.channel = channel;
        }

        public ParamsBase getParams() {
            return params;
        }

        public void setParams(ParamsBase params) {
            this.params = params;
        }

        public boolean isResult() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }

        public static class ParamsBase {
            /**
             * AudioOutputVolume : 40
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
}

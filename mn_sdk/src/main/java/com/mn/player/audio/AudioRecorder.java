package com.mn.player.audio;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder.AudioSource;
import android.media.audiofx.AcousticEchoCanceler;
import MNSDK.MNJni;

// 音频采集线程
public class AudioRecorder implements Runnable {
    private volatile boolean isRecording = false;
    public static int frequency = 8000;//音频采集率
    public static int channelConfiguration = AudioFormat.CHANNEL_OUT_MONO;  //单声道输出 2 CHANNEL_CONFIGURATION_MONO
    public static int audionEncoding = AudioFormat.ENCODING_PCM_16BIT;  //2
    AudioRecord _recorder;              // 音频录制
    byte[] _tempBuffer;                 // 临时Buffer
    public int volume = 0;              // 获取音量值，只是针对录音音量
    private long lTaskContext = 0;
    private OnVolumeListener mVolumeListener;

    private AcousticEchoCanceler m_canceler = null;

    public void setOnVolumeListener(OnVolumeListener volumeListener) {
        this.mVolumeListener = volumeListener;
    }

    public static boolean isDeviceSupport() {
        return AcousticEchoCanceler.isAvailable();
    }


    public boolean initAEC(int audioSession) {
        if (m_canceler != null) {
            return false;
        }
        m_canceler = AcousticEchoCanceler.create(audioSession);
        m_canceler.setEnabled(true);
        return m_canceler.getEnabled();
    }

    public boolean setAECEnabled(boolean enable) {
        if (null == m_canceler) {
            return false;
        }
        m_canceler.setEnabled(enable);
        return m_canceler.getEnabled();
    }

    public boolean release() {
        if (null == m_canceler) {
            return false;
        }
        m_canceler.setEnabled(false);
        m_canceler.release();
        return true;
    }

    public void intAudioRecorder() {
        try {
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
            int bufferSize = AudioRecord.getMinBufferSize(frequency, AudioFormat.CHANNEL_IN_MONO, audionEncoding);

            if (bufferSize > 0) {
                // bufferSize--魅蓝：2048 联想：640  华为：640 vivo:640 牛眼采集时需要_tempBuffer = new byte[bufferSize];
                _tempBuffer = new byte[640];
                _recorder = new AudioRecord(AudioSource.MIC, frequency, AudioFormat.CHANNEL_IN_MONO, audionEncoding, bufferSize);
                if (isDeviceSupport()) {
                    if (initAEC(_recorder.getAudioSessionId())) {
                        setAECEnabled(true);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getD() {
        int audioSessionId = _recorder.getAudioSessionId();
        return audioSessionId;
    }

    public AudioRecord get_recorder() {
        return _recorder;
    }

    public int Start(long _lTaskContext) {
        this.lTaskContext = _lTaskContext;

        try {
            synchronized (this) {
                if (isRecording) {
                    return 0;
                }
                intAudioRecorder();
                if (_recorder != null) {
                    isRecording = true;
                    _recorder.startRecording();
                    _thread = new Thread(this);
                    _thread.start();
                }
            }
        } catch (Exception e) {
        }
        return 0;
    }

    Thread _thread = null;

    public void Stop() {
        synchronized (this) {
            try {
                isRecording = false;
                _thread = null;
                if (_recorder != null) {
                    _recorder.stop();
                    _recorder.release();
                }
                _tempBuffer = null;
                _recorder = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void run() {
        int bufferRead = 0;
        int bufferSize = _tempBuffer.length;
        while (this.isRecording) {
            try {
                bufferRead = _recorder.read(_tempBuffer, 0, bufferSize);
            } catch (Exception e) {
            }

            if (bufferRead > 0) {
                if (this.lTaskContext != 0 && _tempBuffer != null) {
                    int ret = MNJni.SendData(lTaskContext, _tempBuffer, _tempBuffer.length, 3);
                    long v = 0;
                    // 将 buffer 内容取出，进行平方和运算
                    if (_tempBuffer != null) {
                        for (int i = 0; i < _tempBuffer.length; i++) {
                            v += _tempBuffer[i] * _tempBuffer[i];
                        }
                        // 平方和除以数据总长度，得到音量大小。
                        double mean = v / (double) bufferSize;

                        volume = (int) (10 * Math.log10(mean));

                        if (mVolumeListener != null) {
                            mVolumeListener.onVolumeBack(volume);
                        }
                    }
                }
            }

            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

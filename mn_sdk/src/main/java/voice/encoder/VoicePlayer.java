package voice.encoder;

/**
 * Created by Administrator on 2015/1/2.
 */
public class VoicePlayer {
    private VoicePlayerListener listener;
    //	public enum AudioPlayerType{VP_WavPlayer, VP_SoundPlayer};
    public static final int PT_SoundPlayer = 1;
    public static final int PT_WavPlayer = 2;
    private final int defaultSampleRate = 44100;

    public VoicePlayer() {
        init(defaultSampleRate);
    }

    public VoicePlayer(int _sampleRate) {
        init(_sampleRate);
    }

    private native void init(int _sampleRate);

    public void play(final String _text) {
        play(_text, 1, 0);
    }

    public void play(String[] _texts, int _playCount, int _muteInterval) {
        for (int playIdx = 0; playIdx < _playCount; playIdx++) {
            for (int i = 0; i < _texts.length; i++) {
                play(_texts[i], 1, ((i < _texts.length - 1) ? 800 : _muteInterval));
            }
        }
    }

    public void setListener(VoicePlayerListener _listener) {
        listener = _listener;
    }

    public native void play(final String _text, int _playCount, int _muteInterval);//1

    public native void setFreqs(int[] _freqs);//1

    public native void stop();//1

    public native void setPlayerType(int _type);//1

    public void onPlayStart() {
//        LogUtil.i("ppppp", "声波开始");
        if (listener != null) listener.onPlayStart(this);
    }

    public void onPlayEnd() {
//        LogUtil.i("ppppp", "声波结束");
        if (listener != null) listener.onPlayEnd(this);
    }
//	public void onPlayStep(int _totalTime, int _currTime)
//	{
//		if(listener != null)listener.onPlayStep(this, _totalTime, _currTime);
//	}
}

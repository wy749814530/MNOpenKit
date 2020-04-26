package voice.encoder;

public interface VoicePlayerListener {
    public void onPlayStart(VoicePlayer _player);

    //	public void onPlayStep(VoicePlayer _player, int _totalTime, int _currTime);
    public void onPlayEnd(VoicePlayer _player);
}

package voice.encoder;

public interface StringEncoder {
    public byte[] string2Bytes(String _s);

    public String bytes2String(byte[] _bytes, int _off, int _len);
}

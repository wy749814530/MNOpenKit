package voice.encoder;

import java.io.UnsupportedEncodingException;

public class DefaultStringEncoder implements StringEncoder {

    @Override
    public byte[] string2Bytes(String _s) {
        byte[] bytes = null;
        try {
            bytes = _s.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        if (bytes == null) {
            bytes = _s.getBytes();
        }
        return bytes;
    }

    @Override
    public String bytes2String(byte[] _bytes, int _off, int _len) {
        String s = null;
        try {
            s = new String(_bytes, _off, _len, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        if (s == null) {
            s = new String(_bytes, _off, _len);
        }
        return s;
    }
}

package voice.encoder;


/**
 * Created by Administrator on 14-3-16.
 */
public class DataEncoder {
    public static final char[] hexChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static StringEncoder stringEncoder = new DefaultStringEncoder();
    private static int MAX_TRANS_BLOCK_SIZE = 59;

    public static String decode(String s) {
        s = s.substring(0, s.length() - 1);
        long l = Long.parseLong(s, 16);
        s = String.valueOf(l);
        if (l > 1000000000L && l < 10000000000L) {
            long start = l / 1000000000L;
            if (start == 3 || start == 4 || start == 5 || start == 8) {
                return "1" + s;
            }
        }
        return s;
    }

    public static String encode(long _number) {
        String s = Long.toHexString(_number);
        while (s.length() < 9) {
            s = "0" + s;
        }
        return s + checkSum(s);
    }

    private static int hexIdx(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        } else if (c >= 'a' && c <= 'f') {
            return c - 'a';
        }
        return 0;
    }

    static byte bitSet(byte _desBits, int _start, int _end, byte _sourceBits) {
        byte x = (byte) ((_start > 0 ? (0xff >> _start) : 0xff) & (_end < 8 ? (0xff << (8 - _end)) : 0xff));//变成类似00111110
        return (byte) ((_desBits & ~x) | ((_sourceBits << (8 - _end)) & x));
    }

    static byte bitGet(byte _sourceBits, int _start, int _end) {
        return (byte) ((_end < 8 ? (_sourceBits >> (8 - _end)) : _sourceBits) & (0xff >> (8 - (_end - _start))));
    }

    static void bitsSet(byte[] _bits, int _bitsStart, int _bitsEnd, byte _c) {
        int byteStart, byteEnd;
        if ((byteStart = _bitsStart / 8) == (byteEnd = _bitsEnd / 8)) {
            _bits[byteStart] = bitSet(_bits[byteStart], _bitsStart % 8, _bitsEnd % 8, _c);
        } else {
            assert (byteEnd == byteStart + 1);
            int startPos = _bitsStart % 8;
            int endPos = _bitsEnd % 8;
            byte startSource = (byte) (_c >> endPos);
            byte endSource = (byte) (_c & (0xff >> (8 - endPos)));
            _bits[byteStart] = bitSet(_bits[byteStart], startPos, 8, startSource);
            _bits[byteEnd] = bitSet(_bits[byteEnd], 0, endPos, endSource);
        }
    }

    static byte bitsGet(byte[] _bits, int _bitsStart, int _bitsEnd) {
        int byteStart, byteEnd;
        if ((byteStart = _bitsStart / 8) == (byteEnd = _bitsEnd / 8)) {
            return bitGet(_bits[byteStart], _bitsStart % 8, _bitsEnd % 8);
        } else {
            assert (byteEnd == byteStart + 1);
            int startPos = _bitsStart % 8;
            int endPos = _bitsEnd % 8;
            byte startSource = bitGet(_bits[byteStart], startPos, 8);
            byte endSource = bitGet(_bits[byteEnd], 0, endPos);
            return (byte) (startSource << endPos | endSource);
        }
    }

    //16进制字符个数
    static int digit2HexCount(byte[] _chars, int _charsOff, int _charsLen) {
        return _charsLen;
    }

    static int digit2Hex(byte[] _chars, int _charsOff, int _charsLen, byte[] _result, int _resultOff) {
        for (int i = 0; i < _charsLen; i++) {
            _result[i + _resultOff] = _chars[i + _charsOff];
        }
        return _charsLen;
    }

    //16进制字符个数
    static int lower2HexCount(byte[] _chars, int _charsOff, int _charsLen) {
        int bitsCount = _charsLen * 5;
        return (bitsCount % 4 > 0) ? (bitsCount / 4 + 1) : (bitsCount / 4);
    }

    static void charsToHex(byte[] _chars, int _charsOff, int _hexCount, byte[] _result, int _resultOff) {
        for (int i = 0; i < _hexCount; i++) {
            if (i % 2 == 0) {
                _result[i + _resultOff] = (byte) hexChars[(_chars[_charsOff + i / 2] >> 4) & 0x0f];
            } else {
                _result[i + _resultOff] = (byte) hexChars[_chars[_charsOff + i / 2] & 0x0f];
            }
        }
    }

    static int lower2Hex(byte[] _chars, int _charsOff, int _charsLen, byte[] _result, int _resultOff) {
        int hexCount = lower2HexCount(_chars, _charsOff, _charsLen);
        byte[] tempChars = new byte[_charsLen];
        for (int i = 0; i < _charsLen; i++) {
            bitsSet(tempChars, i * 5, (i + 1) * 5, (byte) (_chars[i + _charsOff] - (byte) 'a'));
        }
        charsToHex(tempChars, 0, hexCount, _result, _resultOff);
        tempChars = null;
        return hexCount;
    }

    //16进制字符个数
    static int upper2HexCount(byte[] _chars, int _charsOff, int _charsLen) {
        return lower2HexCount(_chars, _charsOff, _charsLen);
    }

    static int upper2Hex(byte[] _chars, int _charsOff, int _charsLen, byte[] _result, int _resultOff) {
        int hexCount = upper2HexCount(_chars, _charsOff, _charsLen);
        byte[] tempChars = new byte[_charsLen];
        for (int i = 0; i < _charsLen; i++) {
            bitsSet(tempChars, i * 5, (i + 1) * 5, (byte) (_chars[i + _charsOff] - 'A'));
        }
        charsToHex(tempChars, 0, hexCount, _result, _resultOff);
        tempChars = null;
        return hexCount;
    }

    //16进制字符个数
    static int char64ToHexCount(byte[] _chars, int _charsOff, int _charsLen) {
        int bitsCount = _charsLen * 6;
        return (bitsCount % 4 > 0) ? (bitsCount / 4 + 1) : (bitsCount / 4);
    }

    static int char64ToHex(byte[] _chars, int _charsOff, int _charsLen, byte[] _result, int _resultOff) {
        int hexCount = char64ToHexCount(_chars, _charsOff, _charsLen);
        byte[] tempChars = new byte[_charsLen];
        for (int i = 0; i < _charsLen; i++) {
            bitsSet(tempChars, i * 6, (i + 1) * 6, (byte) Util.char64ToInt(_chars[i + _charsOff]));
        }
        charsToHex(tempChars, 0, hexCount, _result, _resultOff);
        tempChars = null;
        return hexCount;
    }

    //16进制字符个数
    static int char256ToHexCount(byte[] _chars, int _charsOff, int _charsLen) {
        return _charsLen * 8 / 4;
    }

    static int char256ToHex(byte[] _chars, int _charsOff, int _charsLen, byte[] _result, int _resultOff) {
        int hexCount = char256ToHexCount(_chars, _charsOff, _charsLen);
        charsToHex(_chars, _charsOff, hexCount, _result, _resultOff);
        return hexCount;
    }

    //16进制字符个数
    static int type2HexCount(int _type, int _aPartLen) {
        if (_type == 1 || _type == 2 || _type == 4) {
            return 2;
        } else {
            return 1;
        }
    }

    static int type2Hex(int _type, int _aPartLen, byte[] _result, int _resultOff) {
        _result[0 + _resultOff] = (byte) (_type << 1);//因为下面还可能修改，最后才转成十六进制字符
        int hexCount = type2HexCount(_type, _aPartLen);
        if (hexCount > 1) {
            _aPartLen = _aPartLen - 1;
            _result[0 + _resultOff] = (byte) (_result[0 + _resultOff] | ((_aPartLen & 0x10) >> 4));
            _result[1 + _resultOff] = (byte) hexChars[_aPartLen & 0x0f];
        }
        _result[0 + _resultOff] = (byte) hexChars[_result[0 + _resultOff]];//转成十六进制字符
        return hexCount;
    }

    private static int MAX_APART_LEN = 32;

    static int encodeType(byte[] _data, int _dataLen, int[] _APartLen) {
        boolean type0 = true, type1 = false, type2 = false, type3 = true,
                type4 = false, type5 = true, type6 = true, type7 = true;
        boolean isDigit_, isLowerChar_, isUpperChar_, is64Char_;
        int apart1Len = 0, apart2Len = 0, apart4Len = 0;
        for (int i = 0; i < _dataLen; i++) {
            isDigit_ = Util.isDigit(_data[i]);
            isLowerChar_ = Util.isLowerChar(_data[i]);
            isUpperChar_ = Util.isUpperChar(_data[i]);
            is64Char_ = Util.is64Char(_data[i]);
            if (type0 && !isDigit_) {
                type0 = false;
                if (i > 0) {
                    type4 = is64Char_;
                    apart4Len = i;
                }
            }
            if (type4 && !is64Char_) type4 = false;
            if (type5 && !isLowerChar_) {
                type5 = false;
                if (i > 0) {
                    type1 = isDigit_;
                    apart1Len = i;
                }
            }
            if (type1 && !isDigit_) type1 = false;
            if (type7 && !isUpperChar_) type7 = false;
            if (type6 && !is64Char_) type6 = false;
            if (type6) {
                if (i > 0) {
                    if (!type2 && isDigit_) apart2Len = i;
                    type2 = isDigit_;
                }
            } else {
                type2 = false;
            }
        }
        if (type0) return 0;
        if (type5) return 5;
        if (type7) return 7;
        if (type1 && apart1Len <= MAX_APART_LEN) {
            _APartLen[0] = apart1Len;
            return 1;
        }
        if (type2 && apart2Len <= MAX_APART_LEN) {
            _APartLen[0] = apart2Len;
            return 2;
        }
        if (type4) {
            if (apart4Len >= MAX_APART_LEN) apart4Len = MAX_APART_LEN;
            _APartLen[0] = apart4Len;
            return 4;
        }
        if (type6) return 6;
        return 3;//永远都支持
    }

    public static int encodeData(byte[] _data, byte[] _result, int _resultOff) {
        int[] aPartLen = {0};
        int type = encodeType(_data, _data.length, aPartLen);
        int len = type2Hex(type, aPartLen[0], _result, _resultOff);
        if (type == 1) {
            len += lower2Hex(_data, 0, aPartLen[0], _result, len + _resultOff);
            len += digit2Hex(_data, aPartLen[0], _data.length - aPartLen[0], _result, len + _resultOff);
        } else if (type == 2) {
            len += char64ToHex(_data, 0, aPartLen[0], _result, len + _resultOff);
            len += digit2Hex(_data, aPartLen[0], _data.length - aPartLen[0], _result, len + _resultOff);
        } else if (type == 4) {
            len += digit2Hex(_data, 0, aPartLen[0], _result, len + _resultOff);
            len += char64ToHex(_data, aPartLen[0], _data.length - aPartLen[0], _result, len + _resultOff);
        } else if (type == 0) {
            len += digit2Hex(_data, 0, _data.length, _result, len + _resultOff);
        } else if (type == 3) {
            len += char256ToHex(_data, 0, _data.length, _result, len + _resultOff);
        } else if (type == 5) {
            len += lower2Hex(_data, 0, _data.length, _result, len + _resultOff);
        } else if (type == 6) {
            len += char64ToHex(_data, 0, _data.length, _result, len + _resultOff);
        } else if (type == 7) {
            len += upper2Hex(_data, 0, _data.length, _result, len + _resultOff);
        }
        return len;
    }

    public static void setStringEncoder(StringEncoder stringEncoder) {
        DataEncoder.stringEncoder = stringEncoder;
    }

    static byte[] String2bytes(String _s) {
        return stringEncoder.string2Bytes(_s);
    }

    public static String encodeString(String _s) {
        byte[] s = String2bytes(_s);
        byte[] result = new byte[(s.length) * 2 + 7];
        int resultLen = 1;
        result[0] = (byte) hexChars[(3 << 1)];
        resultLen += encodeData(s, result, resultLen);
        return new String(result, 0, resultLen);
    }

    public static String encodeManniuString(String _s) {
        byte[] s = String2bytes(_s);
        byte[] result = new byte[(s.length) * 2 + 7];
        int resultLen = 1;
        result[0] = (byte) hexChars[(4 << 1)];
        resultLen += encodeData(s, result, resultLen);
        return new String(result, 0, resultLen);
    }

    public static String[] encodeByBlock(byte[] _hexs, int _hexLen) {
        int blockCount, playIdx;
        byte block[] = new byte[MAX_TRANS_BLOCK_SIZE + 2];
        //分块
        blockCount = (((_hexLen % MAX_TRANS_BLOCK_SIZE) > 0) ? ((_hexLen / MAX_TRANS_BLOCK_SIZE) + 1) : (_hexLen / MAX_TRANS_BLOCK_SIZE));
        if (blockCount > 4) throw new RuntimeException("too much blocks");//最大支持4块
        String result[] = new String[blockCount];
        for (int i = 0; i < blockCount; i++) {
            block[0] = (byte) hexChars[7 << 1];//1个类型
            block[1] = (byte) hexChars[((blockCount - 1) << 2) | (i & 0x3)];//总块数和第几块
            int blockDataLen = ((i < blockCount - 1) ? MAX_TRANS_BLOCK_SIZE : (_hexLen % MAX_TRANS_BLOCK_SIZE));
            System.arraycopy(_hexs, i * MAX_TRANS_BLOCK_SIZE, block, 2, blockDataLen);//(block+2, _hexs+i*MAX_TRANS_BLOCK_SIZE, ((i<blockCount-1)?MAX_TRANS_BLOCK_SIZE:(_hexCount%MAX_TRANS_BLOCK_SIZE)));
            result[i] = new String(block, 0, blockDataLen + 2);
        }
        return result;
    }

    public static String encodeMacWiFi(byte[] _mac, String _pwd) {
        byte[] mac = _mac;
        byte[] pwd = _pwd.getBytes();
        assert (mac.length <= MAX_APART_LEN && pwd.length <= MAX_APART_LEN);
        byte[] result = new byte[(mac.length + pwd.length) * 2 + 7];
        int encodeLen = mac.length - 1;//0-31表示1-32
        result[0] = (byte) hexChars[(0 << 1) | ((encodeLen & 0x10) >> 4)];
        result[1] = (byte) hexChars[encodeLen & 0x0f];
        int resultLen = 2;
        resultLen += encodeData(mac, result, resultLen);
        resultLen += encodeData(pwd, result, resultLen);
        return new String(result, 0, resultLen);
    }

    public static String encodeSSIDWiFi(String _ssid, String _pwd) {
        byte[] ssid = String2bytes(_ssid);
        byte[] pwd = String2bytes(_pwd);
        assert (ssid.length <= MAX_APART_LEN && pwd.length <= MAX_APART_LEN);
        byte[] result = new byte[(ssid.length + pwd.length) * 2 + 7];//可能最长的字符串长度，每一个部分可能两个字节头，应用层协议两个字节头
        int encodeLen = ssid.length - 1;//0-31表示1-32
        result[0] = (byte) hexChars[(1 << 1) | ((encodeLen & 0x10) >> 4)];
        result[1] = (byte) hexChars[encodeLen & 0x0f];
        int resultLen = 2;
        resultLen += encodeData(ssid, result, resultLen);
        resultLen += encodeData(pwd, result, resultLen);
        return new String(result, 0, resultLen);
    }

    public static String[] encodeZSSSIDWiFi(String _ssid, String _pwd, String _phone) {
        byte[] ssid = String2bytes(_ssid);
        byte[] pwd = String2bytes(_pwd);
        byte[] phone = String2bytes(_phone);
        assert (ssid.length <= MAX_APART_LEN && pwd.length <= MAX_APART_LEN);
        byte[] result = new byte[(ssid.length + pwd.length) * 2 + 7 + 1 + 11];//可能最长的字符串长度，每一个部分可能两个字节头，应用层协议两个字节头
        int encodeLen = ssid.length - 1;//0-31表示1-32
        result[0] = (byte) hexChars[(1 << 1) | ((encodeLen & 0x10) >> 4)];
        result[1] = (byte) hexChars[encodeLen & 0x0f];
        int resultLen = 2;
        resultLen += encodeData(phone, result, resultLen);
        resultLen += encodeData(ssid, result, resultLen);
        resultLen += encodeData(pwd, result, resultLen);
        return encodeByBlock(result, resultLen);
    }

    public static String encodePhone(String _imei, String _phoneName) {
        byte[] phoneName = String2bytes(_phoneName);
        byte[] imei = String2bytes(_imei);
        assert (phoneName.length <= MAX_APART_LEN);
        byte[] result = new byte[(imei.length + phoneName.length) * 2 + 7];//可能最长的字符串长度，每一个部分可能两个字节头，应用层协议两个字节头
        int encodeLen = imei.length - 1;//0-31表示1-32
        result[0] = (byte) hexChars[(2 << 1) | ((encodeLen & 0x10) >> 4)];
        result[1] = (byte) hexChars[encodeLen & 0x0f];
        int resultLen = 2;
        resultLen += encodeData(imei, result, resultLen);
        resultLen += encodeData(phoneName, result, resultLen);
        return new String(result, 0, resultLen);
    }

    private static char checkSum(String _s) {
        int checksum = 0;
        for (int i = 0, num = _s.length(); i < num; i++)
            checksum = (checksum ^ hexIdx(_s.charAt(i)));
        checksum = ((-checksum) & 0xF);
        return hexChars[checksum % 16];
    }

    public static boolean isCheckSum(String s) {
        return s.charAt(s.length() - 1) == checkSum(s.substring(0, s.length() - 1));
    }
}

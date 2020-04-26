package voice.encoder;

/**
 * Created by Administrator on 15-1-10.
 */
public class Util {

    public static byte int2Char64(int _hex) {
        if (_hex >= 0 && _hex < 26) {
            return (byte) ('A' + _hex);
        } else if (_hex >= 26 && _hex < 52) {
            return (byte) ('a' + _hex - 26);
        } else if (_hex >= 52 && _hex < 62) {
            return (byte) ('0' + _hex - 52);
        } else if (_hex == 62) {
            return '_';
        } else if (_hex == 63) {
            return '-';
        }
        return -1;
    }

    public static int hexChar2Int(byte _c) {
        if (_c >= '0' && _c <= '9') {
            return _c - '0';
        } else if (_c >= 'a' && _c <= 'z') {
            return _c - 'a' + 10;
        } else if (_c >= 'A' && _c <= 'Z') {
            return _c - 'A' + 10;
        }
        return -1;
    }

    public static int char64ToInt(byte _c) {
        if (_c >= 'A' && _c <= 'Z') {
            return _c - 'A';
        } else if (_c >= 'a' && _c <= 'z') {
            return _c - 'a' + 26;
        } else if (_c >= '0' && _c <= '9') {
            return _c - '0' + 52;
        } else if (_c == '_') {
            return 62;
        } else if (_c == '-') {
            return 63;
        }
        return -1;
    }

    public static boolean isDigit(byte _c) {
        return _c >= '0' && _c <= '9';
    }

    public static boolean isLowerChar(byte _c) {
        return _c >= 'a' && _c <= 'z';
    }

    public static boolean isUpperChar(byte _c) {
        return _c >= 'A' && _c <= 'Z';
    }

    public static boolean is64Char(byte _c) {
        return ((_c >= 'a' && _c <= 'z') || (_c >= 'A' && _c <= 'Z')
                || (_c >= '0' && _c <= '9') || _c == '_' || _c == '-');
    }
}

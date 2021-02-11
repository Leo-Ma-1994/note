package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.util.ASMUtils;
import com.alibaba.fastjson.util.Base64;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public final class JSONScanner extends JSONLexerBase {
    protected static final char[] typeFieldName = ("\"" + JSON.DEFAULT_TYPE_KEY + "\":\"").toCharArray();
    public final int ISO8601_LEN_0;
    public final int ISO8601_LEN_1;
    public final int ISO8601_LEN_2;
    private final String text;

    public JSONScanner(String input) {
        this(input, JSON.DEFAULT_PARSER_FEATURE);
    }

    public JSONScanner(String input, int features) {
        this.ISO8601_LEN_0 = "0000-00-00".length();
        this.ISO8601_LEN_1 = "0000-00-00T00:00:00".length();
        this.ISO8601_LEN_2 = "0000-00-00T00:00:00.000".length();
        this.features = features;
        this.text = input;
        this.bp = -1;
        next();
        if (this.ch == 65279) {
            next();
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final char charAt(int index) {
        if (index >= this.text.length()) {
            return 26;
        }
        return this.text.charAt(index);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public final char next() {
        int i = this.bp + 1;
        this.bp = i;
        char charAt = charAt(i);
        this.ch = charAt;
        return charAt;
    }

    public JSONScanner(char[] input, int inputLength) {
        this(input, inputLength, JSON.DEFAULT_PARSER_FEATURE);
    }

    public JSONScanner(char[] input, int inputLength, int features) {
        this(new String(input, 0, inputLength), features);
    }

    /* access modifiers changed from: protected */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final void copyTo(int offset, int count, char[] dest) {
        this.text.getChars(offset, offset + count, dest, 0);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final int scanType(String type) {
        this.matchStat = 0;
        if (!charArrayCompare(this.text, this.bp, typeFieldName)) {
            return -2;
        }
        int bpLocal = this.bp + typeFieldName.length;
        int typeLength = type.length();
        for (int i = 0; i < typeLength; i++) {
            if (type.charAt(i) != charAt(bpLocal + i)) {
                return -1;
            }
        }
        int bpLocal2 = bpLocal + typeLength;
        if (charAt(bpLocal2) != '\"') {
            return -1;
        }
        int bpLocal3 = bpLocal2 + 1;
        this.ch = charAt(bpLocal3);
        if (this.ch == ',') {
            int bpLocal4 = bpLocal3 + 1;
            this.ch = charAt(bpLocal4);
            this.bp = bpLocal4;
            this.token = 16;
            return 3;
        }
        if (this.ch == '}') {
            bpLocal3++;
            this.ch = charAt(bpLocal3);
            if (this.ch == ',') {
                this.token = 16;
                bpLocal3++;
                this.ch = charAt(bpLocal3);
            } else if (this.ch == ']') {
                this.token = 15;
                bpLocal3++;
                this.ch = charAt(bpLocal3);
            } else if (this.ch == '}') {
                this.token = 13;
                bpLocal3++;
                this.ch = charAt(bpLocal3);
            } else if (this.ch != 26) {
                return -1;
            } else {
                this.token = 20;
            }
            this.matchStat = 4;
        }
        this.bp = bpLocal3;
        return this.matchStat;
    }

    static final boolean charArrayCompare(String src, int offset, char[] dest) {
        int destLen = dest.length;
        if (destLen + offset > src.length()) {
            return false;
        }
        for (int i = 0; i < destLen; i++) {
            if (dest[i] != src.charAt(offset + i)) {
                return false;
            }
        }
        return true;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final boolean charArrayCompare(char[] chars) {
        return charArrayCompare(this.text, this.bp, chars);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final int indexOf(char ch, int startIndex) {
        return this.text.indexOf(ch, startIndex);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final String addSymbol(int offset, int len, int hash, SymbolTable symbolTable) {
        return symbolTable.addSymbol(this.text, offset, len, hash);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public byte[] bytesValue() {
        return Base64.decodeFast(this.text, this.np + 1, this.sp);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public final String stringVal() {
        if (!this.hasSpecial) {
            return subString(this.np + 1, this.sp);
        }
        return new String(this.sbuf, 0, this.sp);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final String subString(int offset, int count) {
        if (!ASMUtils.isAndroid()) {
            return this.text.substring(offset, offset + count);
        }
        char[] chars = new char[count];
        for (int i = offset; i < offset + count; i++) {
            chars[i - offset] = this.text.charAt(i);
        }
        return new String(chars);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public final String numberString() {
        char chLocal = charAt((this.np + this.sp) - 1);
        int sp = this.sp;
        if (chLocal == 'L' || chLocal == 'S' || chLocal == 'B' || chLocal == 'F' || chLocal == 'D') {
            sp--;
        }
        return subString(this.np, sp);
    }

    public boolean scanISO8601DateIfMatch() {
        return scanISO8601DateIfMatch(true);
    }

    public boolean scanISO8601DateIfMatch(boolean strict) {
        int millis;
        int seconds;
        int minute;
        int hour;
        char S2;
        int rest = this.text.length() - this.bp;
        if (!strict && rest > 13) {
            char c0 = charAt(this.bp);
            char c1 = charAt(this.bp + 1);
            char c2 = charAt(this.bp + 2);
            char c3 = charAt(this.bp + 3);
            char c4 = charAt(this.bp + 4);
            char c5 = charAt(this.bp + 5);
            char c_r0 = charAt((this.bp + rest) - 1);
            char c_r1 = charAt((this.bp + rest) - 2);
            if (c0 == '/' && c1 == 'D' && c2 == 'a' && c3 == 't' && c4 == 'e' && c5 == '(' && c_r0 == '/' && c_r1 == ')') {
                int plusIndex = -1;
                for (int i = 6; i < rest; i++) {
                    char c = charAt(this.bp + i);
                    if (c != '+') {
                        if (c < '0' || c > '9') {
                            break;
                        }
                    } else {
                        plusIndex = i;
                    }
                }
                if (plusIndex == -1) {
                    return false;
                }
                int offset = this.bp + 6;
                long millis2 = Long.parseLong(subString(offset, plusIndex - offset));
                this.calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
                this.calendar.setTimeInMillis(millis2);
                this.token = 5;
                return true;
            }
        }
        if (rest == 8 || rest == 14 || rest == 17) {
            if (strict) {
                return false;
            }
            char y0 = charAt(this.bp);
            char y1 = charAt(this.bp + 1);
            char y2 = charAt(this.bp + 2);
            char y3 = charAt(this.bp + 3);
            char M0 = charAt(this.bp + 4);
            char M1 = charAt(this.bp + 5);
            char d0 = charAt(this.bp + 6);
            char d1 = charAt(this.bp + 7);
            if (!checkDate(y0, y1, y2, y3, M0, M1, d0, d1)) {
                return false;
            }
            setCalendar(y0, y1, y2, y3, M0, M1, d0, d1);
            if (rest != 8) {
                char h0 = charAt(this.bp + 8);
                char h1 = charAt(this.bp + 9);
                char m0 = charAt(this.bp + 10);
                char m1 = charAt(this.bp + 11);
                char s0 = charAt(this.bp + 12);
                char s1 = charAt(this.bp + 13);
                if (!checkTime(h0, h1, m0, m1, s0, s1)) {
                    return false;
                }
                if (rest == 17) {
                    char S0 = charAt(this.bp + 14);
                    char S1 = charAt(this.bp + 15);
                    char S22 = charAt(this.bp + 16);
                    if (S0 < '0' || S0 > '9' || S1 < '0' || S1 > '9' || S22 < '0' || S22 > '9') {
                        return false;
                    }
                    millis = (digits[S0] * 100) + (digits[S1] * 10) + digits[S22];
                } else {
                    millis = 0;
                }
                hour = (digits[h0] * 10) + digits[h1];
                minute = (digits[m0] * 10) + digits[m1];
                seconds = (digits[s0] * 10) + digits[s1];
            } else {
                hour = 0;
                minute = 0;
                seconds = 0;
                millis = 0;
            }
            this.calendar.set(11, hour);
            this.calendar.set(12, minute);
            this.calendar.set(13, seconds);
            this.calendar.set(14, millis);
            this.token = 5;
            return true;
        } else if (rest < this.ISO8601_LEN_0 || charAt(this.bp + 4) != '-' || charAt(this.bp + 7) != '-') {
            return false;
        } else {
            char y02 = charAt(this.bp);
            char y12 = charAt(this.bp + 1);
            char y22 = charAt(this.bp + 2);
            char y32 = charAt(this.bp + 3);
            char M02 = charAt(this.bp + 5);
            char M12 = charAt(this.bp + 6);
            char d02 = charAt(this.bp + 8);
            char d12 = charAt(this.bp + 9);
            if (!checkDate(y02, y12, y22, y32, M02, M12, d02, d12)) {
                return false;
            }
            setCalendar(y02, y12, y22, y32, M02, M12, d02, d12);
            char t = charAt(this.bp + 10);
            if (t == 'T' || (t == ' ' && !strict)) {
                if (!(rest >= this.ISO8601_LEN_1 && charAt(this.bp + 13) == ':' && charAt(this.bp + 16) == ':')) {
                    return false;
                }
                char h02 = charAt(this.bp + 11);
                char h12 = charAt(this.bp + 12);
                char m02 = charAt(this.bp + 14);
                char m12 = charAt(this.bp + 15);
                char s02 = charAt(this.bp + 17);
                char s12 = charAt(this.bp + 18);
                if (!checkTime(h02, h12, m02, m12, s02, s12)) {
                    return false;
                }
                int hour2 = (digits[h02] * 10) + digits[h12];
                int minute2 = (digits[m02] * 10) + digits[m12];
                int seconds2 = (digits[s02] * 10) + digits[s12];
                this.calendar.set(11, hour2);
                this.calendar.set(12, minute2);
                this.calendar.set(13, seconds2);
                if (charAt(this.bp + 19) != '.') {
                    this.calendar.set(14, 0);
                    int i2 = this.bp + 19;
                    this.bp = i2;
                    this.ch = charAt(i2);
                    this.token = 5;
                    return true;
                } else if (rest < this.ISO8601_LEN_2) {
                    return false;
                } else {
                    char S02 = charAt(this.bp + 20);
                    if (S02 < '0') {
                        return false;
                    }
                    if (S02 > '9') {
                        return false;
                    }
                    int millis3 = digits[S02];
                    int millisLen = 1;
                    char S12 = charAt(this.bp + 21);
                    if (S12 >= '0' && S12 <= '9') {
                        millis3 = (millis3 * 10) + digits[S12];
                        millisLen = 2;
                    }
                    if (millisLen == 2 && (S2 = charAt(this.bp + 22)) >= '0' && S2 <= '9') {
                        millis3 = (millis3 * 10) + digits[S2];
                        millisLen = 3;
                    }
                    this.calendar.set(14, millis3);
                    int timzeZoneLength = 0;
                    char timeZoneFlag = charAt(this.bp + 20 + millisLen);
                    if (timeZoneFlag == '+' || timeZoneFlag == '-') {
                        char t0 = charAt(this.bp + 20 + millisLen + 1);
                        if (t0 < '0') {
                            return false;
                        }
                        if (t0 > '1') {
                            return false;
                        }
                        char t1 = charAt(this.bp + 20 + millisLen + 2);
                        if (t1 < '0') {
                            return false;
                        }
                        if (t1 > '9') {
                            return false;
                        }
                        char t2 = charAt(this.bp + 20 + millisLen + 3);
                        if (t2 == ':') {
                            if (!(charAt(this.bp + 20 + millisLen + 4) == '0' && charAt(this.bp + 20 + millisLen + 5) == '0')) {
                                return false;
                            }
                            timzeZoneLength = 6;
                        } else if (t2 != '0') {
                            timzeZoneLength = 3;
                        } else if (charAt(this.bp + 20 + millisLen + 4) != '0') {
                            return false;
                        } else {
                            timzeZoneLength = 5;
                        }
                        int timeZoneOffset = ((digits[t0] * 10) + digits[t1]) * 3600 * 1000;
                        if (timeZoneFlag == '-') {
                            timeZoneOffset = -timeZoneOffset;
                        }
                        if (this.calendar.getTimeZone().getRawOffset() != timeZoneOffset) {
                            String[] timeZoneIDs = TimeZone.getAvailableIDs(timeZoneOffset);
                            if (timeZoneIDs.length > 0) {
                                this.calendar.setTimeZone(TimeZone.getTimeZone(timeZoneIDs[0]));
                            }
                        }
                    }
                    char end = charAt(this.bp + millisLen + 20 + timzeZoneLength);
                    if (!(end == 26 || end == '\"')) {
                        return false;
                    }
                    int i3 = this.bp + millisLen + 20 + timzeZoneLength;
                    this.bp = i3;
                    this.ch = charAt(i3);
                    this.token = 5;
                    return true;
                }
            } else if (t != '\"' && t != 26) {
                return false;
            } else {
                this.calendar.set(11, 0);
                this.calendar.set(12, 0);
                this.calendar.set(13, 0);
                this.calendar.set(14, 0);
                int i4 = this.bp + 10;
                this.bp = i4;
                this.ch = charAt(i4);
                this.token = 5;
                return true;
            }
        }
    }

    private boolean checkTime(char h0, char h1, char m0, char m1, char s0, char s1) {
        if (h0 == '0') {
            if (h1 < '0' || h1 > '9') {
                return false;
            }
        } else if (h0 == '1') {
            if (h1 < '0' || h1 > '9') {
                return false;
            }
        } else if (h0 != '2' || h1 < '0' || h1 > '4') {
            return false;
        }
        if (m0 < '0' || m0 > '5') {
            if (!(m0 == '6' && m1 == '0')) {
                return false;
            }
        } else if (m1 < '0' || m1 > '9') {
            return false;
        }
        if (s0 < '0' || s0 > '5') {
            if (s0 == '6' && s1 == '0') {
                return true;
            }
            return false;
        } else if (s1 < '0' || s1 > '9') {
            return false;
        } else {
            return true;
        }
    }

    private void setCalendar(char y0, char y1, char y2, char y3, char M0, char M1, char d0, char d1) {
        this.calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        int year = (digits[y0] * 1000) + (digits[y1] * 100) + (digits[y2] * 10) + digits[y3];
        int day = (digits[d0] * 10) + digits[d1];
        this.calendar.set(1, year);
        this.calendar.set(2, ((digits[M0] * 10) + digits[M1]) - 1);
        this.calendar.set(5, day);
    }

    static boolean checkDate(char y0, char y1, char y2, char y3, char M0, char M1, int d0, int d1) {
        if ((y0 != '1' && y0 != '2') || y1 < '0' || y1 > '9' || y2 < '0' || y2 > '9' || y3 < '0' || y3 > '9') {
            return false;
        }
        if (M0 == '0') {
            if (M1 < '1' || M1 > '9') {
                return false;
            }
        } else if (M0 != '1') {
            return false;
        } else {
            if (!(M1 == '0' || M1 == '1' || M1 == '2')) {
                return false;
            }
        }
        if (d0 == 48) {
            if (d1 < 49 || d1 > 57) {
                return false;
            }
            return true;
        } else if (d0 == 49 || d0 == 50) {
            if (d1 < 48 || d1 > 57) {
                return false;
            }
            return true;
        } else if (d0 != 51) {
            return false;
        } else {
            if (d1 == 48 || d1 == 49) {
                return true;
            }
            return false;
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public boolean isEOF() {
        if (this.bp != this.text.length()) {
            return this.ch == 26 && this.bp + 1 == this.text.length();
        }
        return true;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public int scanFieldInt(char[] fieldName) {
        int index;
        char ch;
        this.matchStat = 0;
        int startPos = this.bp;
        char startChar = this.ch;
        if (!charArrayCompare(this.text, this.bp, fieldName)) {
            this.matchStat = -2;
            return 0;
        }
        int index2 = this.bp + fieldName.length;
        int index3 = index2 + 1;
        char ch2 = charAt(index2);
        if (ch2 < '0' || ch2 > '9') {
            this.matchStat = -1;
            return 0;
        }
        int value = digits[ch2];
        while (true) {
            index = index3 + 1;
            ch = charAt(index3);
            if (ch < '0' || ch > '9') {
                break;
            }
            value = (value * 10) + digits[ch];
            index3 = index;
        }
        if (ch == '.') {
            this.matchStat = -1;
            return 0;
        }
        this.bp = index - 1;
        if (value < 0) {
            this.matchStat = -1;
            return 0;
        } else if (ch == ',') {
            int i = this.bp + 1;
            this.bp = i;
            this.ch = charAt(i);
            this.matchStat = 3;
            this.token = 16;
            return value;
        } else {
            if (ch == '}') {
                int i2 = this.bp + 1;
                this.bp = i2;
                char ch3 = charAt(i2);
                if (ch3 == ',') {
                    this.token = 16;
                    int i3 = this.bp + 1;
                    this.bp = i3;
                    this.ch = charAt(i3);
                } else if (ch3 == ']') {
                    this.token = 15;
                    int i4 = this.bp + 1;
                    this.bp = i4;
                    this.ch = charAt(i4);
                } else if (ch3 == '}') {
                    this.token = 13;
                    int i5 = this.bp + 1;
                    this.bp = i5;
                    this.ch = charAt(i5);
                } else if (ch3 == 26) {
                    this.token = 20;
                } else {
                    this.bp = startPos;
                    this.ch = startChar;
                    this.matchStat = -1;
                    return 0;
                }
                this.matchStat = 4;
            }
            return value;
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public String scanFieldString(char[] fieldName) {
        this.matchStat = 0;
        int startPos = this.bp;
        char startChar = this.ch;
        if (!charArrayCompare(this.text, this.bp, fieldName)) {
            this.matchStat = -2;
            return stringDefaultValue();
        }
        int index = this.bp + fieldName.length;
        int index2 = index + 1;
        if (charAt(index) != '\"') {
            this.matchStat = -1;
            return stringDefaultValue();
        }
        boolean hasSpecial = false;
        int endIndex = this.text.indexOf(34, index2);
        if (endIndex != -1) {
            String stringVal = subString(index2, endIndex - index2);
            int i = 0;
            while (true) {
                if (i >= stringVal.length()) {
                    break;
                } else if (stringVal.charAt(i) == '\\') {
                    hasSpecial = true;
                    break;
                } else {
                    i++;
                }
            }
            if (hasSpecial) {
                this.matchStat = -1;
                return stringDefaultValue();
            }
            this.bp = endIndex + 1;
            char ch = charAt(this.bp);
            this.ch = ch;
            if (ch == ',') {
                int i2 = this.bp + 1;
                this.bp = i2;
                this.ch = charAt(i2);
                this.matchStat = 3;
                return stringVal;
            } else if (ch == '}') {
                int i3 = this.bp + 1;
                this.bp = i3;
                char ch2 = charAt(i3);
                if (ch2 == ',') {
                    this.token = 16;
                    int i4 = this.bp + 1;
                    this.bp = i4;
                    this.ch = charAt(i4);
                } else if (ch2 == ']') {
                    this.token = 15;
                    int i5 = this.bp + 1;
                    this.bp = i5;
                    this.ch = charAt(i5);
                } else if (ch2 == '}') {
                    this.token = 13;
                    int i6 = this.bp + 1;
                    this.bp = i6;
                    this.ch = charAt(i6);
                } else if (ch2 == 26) {
                    this.token = 20;
                } else {
                    this.bp = startPos;
                    this.ch = startChar;
                    this.matchStat = -1;
                    return stringDefaultValue();
                }
                this.matchStat = 4;
                return stringVal;
            } else {
                this.matchStat = -1;
                return stringDefaultValue();
            }
        } else {
            throw new JSONException("unclosed str");
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public String scanFieldSymbol(char[] fieldName, SymbolTable symbolTable) {
        this.matchStat = 0;
        if (!charArrayCompare(this.text, this.bp, fieldName)) {
            this.matchStat = -2;
            return null;
        }
        int index = this.bp + fieldName.length;
        int index2 = index + 1;
        if (charAt(index) != '\"') {
            this.matchStat = -1;
            return null;
        }
        int hash = 0;
        while (true) {
            int index3 = index2 + 1;
            char ch = charAt(index2);
            if (ch == '\"') {
                this.bp = index3;
                char ch2 = charAt(this.bp);
                this.ch = ch2;
                String strVal = symbolTable.addSymbol(this.text, index2, (index3 - index2) - 1, hash);
                if (ch2 == ',') {
                    int i = this.bp + 1;
                    this.bp = i;
                    this.ch = charAt(i);
                    this.matchStat = 3;
                    return strVal;
                } else if (ch2 == '}') {
                    int i2 = this.bp + 1;
                    this.bp = i2;
                    char ch3 = charAt(i2);
                    if (ch3 == ',') {
                        this.token = 16;
                        int i3 = this.bp + 1;
                        this.bp = i3;
                        this.ch = charAt(i3);
                    } else if (ch3 == ']') {
                        this.token = 15;
                        int i4 = this.bp + 1;
                        this.bp = i4;
                        this.ch = charAt(i4);
                    } else if (ch3 == '}') {
                        this.token = 13;
                        int i5 = this.bp + 1;
                        this.bp = i5;
                        this.ch = charAt(i5);
                    } else if (ch3 == 26) {
                        this.token = 20;
                    } else {
                        this.matchStat = -1;
                        return null;
                    }
                    this.matchStat = 4;
                    return strVal;
                } else {
                    this.matchStat = -1;
                    return null;
                }
            } else {
                hash = (hash * 31) + ch;
                if (ch == '\\') {
                    this.matchStat = -1;
                    return null;
                }
                index2 = index3;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x005d, code lost:
        r0.add(subString(r4, (r7 - r4) - 1));
        r4 = r7 + 1;
        r2 = charAt(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0071, code lost:
        if (r2 != ',') goto L_0x007b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0073, code lost:
        r2 = charAt(r4);
        r4 = r4 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x007d, code lost:
        if (r2 != ']') goto L_0x00ed;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x007f, code lost:
        r2 = charAt(r4);
        r10.bp = r4 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0088, code lost:
        if (r2 != ',') goto L_0x0096;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x008a, code lost:
        r10.ch = charAt(r10.bp);
        r10.matchStat = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0095, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0098, code lost:
        if (r2 != '}') goto L_0x00ea;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x009a, code lost:
        r2 = charAt(r10.bp);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00a0, code lost:
        if (r2 != ',') goto L_0x00b3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00a2, code lost:
        r10.token = 16;
        r1 = r10.bp + 1;
        r10.bp = r1;
        r10.ch = charAt(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00b3, code lost:
        if (r2 != ']') goto L_0x00c6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00b5, code lost:
        r10.token = 15;
        r1 = r10.bp + 1;
        r10.bp = r1;
        r10.ch = charAt(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00c6, code lost:
        if (r2 != '}') goto L_0x00d9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00c8, code lost:
        r10.token = 13;
        r1 = r10.bp + 1;
        r10.bp = r1;
        r10.ch = charAt(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00db, code lost:
        if (r2 != 26) goto L_0x00e7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00dd, code lost:
        r10.token = 20;
        r10.ch = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00e3, code lost:
        r10.matchStat = 4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00e6, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00e7, code lost:
        r10.matchStat = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00e9, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00ea, code lost:
        r10.matchStat = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00ec, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00ed, code lost:
        r10.matchStat = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00ef, code lost:
        return null;
     */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.Collection<java.lang.String> scanFieldStringArray(char[] r11, java.lang.Class<?> r12) {
        /*
        // Method dump skipped, instructions count: 261
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONScanner.scanFieldStringArray(char[], java.lang.Class):java.util.Collection");
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public long scanFieldLong(char[] fieldName) {
        int index;
        char ch;
        this.matchStat = 0;
        int startPos = this.bp;
        char startChar = this.ch;
        if (!charArrayCompare(this.text, this.bp, fieldName)) {
            this.matchStat = -2;
            return 0;
        }
        int index2 = this.bp + fieldName.length;
        int index3 = index2 + 1;
        char ch2 = charAt(index2);
        if (ch2 >= '0') {
            char c = '9';
            if (ch2 <= '9') {
                long value = (long) digits[ch2];
                while (true) {
                    index = index3 + 1;
                    ch = charAt(index3);
                    if (ch < '0' || ch > c) {
                        break;
                    }
                    value = (10 * value) + ((long) digits[ch]);
                    index3 = index;
                    c = '9';
                }
                if (ch == '.') {
                    this.matchStat = -1;
                    return 0;
                }
                this.bp = index - 1;
                if (value < 0) {
                    this.bp = startPos;
                    this.ch = startChar;
                    this.matchStat = -1;
                    return 0;
                } else if (ch == ',') {
                    int i = this.bp + 1;
                    this.bp = i;
                    this.ch = charAt(i);
                    this.matchStat = 3;
                    this.token = 16;
                    return value;
                } else if (ch == '}') {
                    int i2 = this.bp + 1;
                    this.bp = i2;
                    char ch3 = charAt(i2);
                    if (ch3 == ',') {
                        this.token = 16;
                        int i3 = this.bp + 1;
                        this.bp = i3;
                        this.ch = charAt(i3);
                    } else if (ch3 == ']') {
                        this.token = 15;
                        int i4 = this.bp + 1;
                        this.bp = i4;
                        this.ch = charAt(i4);
                    } else if (ch3 == '}') {
                        this.token = 13;
                        int i5 = this.bp + 1;
                        this.bp = i5;
                        this.ch = charAt(i5);
                    } else if (ch3 == 26) {
                        this.token = 20;
                    } else {
                        this.bp = startPos;
                        this.ch = startChar;
                        this.matchStat = -1;
                        return 0;
                    }
                    this.matchStat = 4;
                    return value;
                } else {
                    this.matchStat = -1;
                    return 0;
                }
            }
        }
        this.bp = startPos;
        this.ch = startChar;
        this.matchStat = -1;
        return 0;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public boolean scanFieldBoolean(char[] fieldName) {
        boolean value;
        char ch;
        this.matchStat = 0;
        if (!charArrayCompare(this.text, this.bp, fieldName)) {
            this.matchStat = -2;
            return false;
        }
        int index = this.bp + fieldName.length;
        int index2 = index + 1;
        char ch2 = charAt(index);
        if (ch2 == 't') {
            int index3 = index2 + 1;
            if (charAt(index2) != 114) {
                this.matchStat = -1;
                return false;
            }
            int index4 = index3 + 1;
            if (charAt(index3) != 117) {
                this.matchStat = -1;
                return false;
            }
            int index5 = index4 + 1;
            if (charAt(index4) != 101) {
                this.matchStat = -1;
                return false;
            }
            this.bp = index5;
            ch = charAt(this.bp);
            value = true;
        } else if (ch2 == 'f') {
            int index6 = index2 + 1;
            if (charAt(index2) != 97) {
                this.matchStat = -1;
                return false;
            }
            int index7 = index6 + 1;
            if (charAt(index6) != 108) {
                this.matchStat = -1;
                return false;
            }
            int index8 = index7 + 1;
            if (charAt(index7) != 115) {
                this.matchStat = -1;
                return false;
            }
            int index9 = index8 + 1;
            if (charAt(index8) != 101) {
                this.matchStat = -1;
                return false;
            }
            this.bp = index9;
            ch = charAt(this.bp);
            value = false;
        } else {
            this.matchStat = -1;
            return false;
        }
        if (ch == ',') {
            int i = this.bp + 1;
            this.bp = i;
            this.ch = charAt(i);
            this.matchStat = 3;
            this.token = 16;
        } else if (ch == '}') {
            int i2 = this.bp + 1;
            this.bp = i2;
            char ch3 = charAt(i2);
            if (ch3 == ',') {
                this.token = 16;
                int i3 = this.bp + 1;
                this.bp = i3;
                this.ch = charAt(i3);
            } else if (ch3 == ']') {
                this.token = 15;
                int i4 = this.bp + 1;
                this.bp = i4;
                this.ch = charAt(i4);
            } else if (ch3 == '}') {
                this.token = 13;
                int i5 = this.bp + 1;
                this.bp = i5;
                this.ch = charAt(i5);
            } else if (ch3 == 26) {
                this.token = 20;
            } else {
                this.matchStat = -1;
                return false;
            }
            this.matchStat = 4;
        } else {
            this.matchStat = -1;
            return false;
        }
        return value;
    }

    /* access modifiers changed from: protected */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final void arrayCopy(int srcPos, char[] dest, int destPos, int length) {
        this.text.getChars(srcPos, srcPos + length, dest, destPos);
    }
}

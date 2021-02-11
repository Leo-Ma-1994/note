package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.util.IOUtils;
import com.goodocom.bttek.BuildConfig;
import java.io.Closeable;
import java.lang.ref.SoftReference;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public abstract class JSONLexerBase implements JSONLexer, Closeable {
    private static final Map<String, Integer> DEFAULT_KEYWORDS;
    protected static final int INT_MULTMIN_RADIX_TEN = -214748364;
    protected static final int INT_N_MULTMAX_RADIX_TEN = -214748364;
    protected static final long MULTMIN_RADIX_TEN = -922337203685477580L;
    protected static final long N_MULTMAX_RADIX_TEN = -922337203685477580L;
    private static final ThreadLocal<SoftReference<char[]>> SBUF_REF_LOCAL = new ThreadLocal<>();
    protected static final int[] digits = new int[103];
    protected static final char[] typeFieldName = ("\"" + JSON.DEFAULT_TYPE_KEY + "\":\"").toCharArray();
    protected int bp;
    protected Calendar calendar = null;
    protected char ch;
    protected int eofPos;
    protected int features = JSON.DEFAULT_PARSER_FEATURE;
    protected boolean hasSpecial;
    protected Map<String, Integer> keywods = DEFAULT_KEYWORDS;
    public int matchStat = 0;
    protected int np;
    protected int pos;
    protected char[] sbuf;
    protected int sp;
    protected int token;

    public abstract String addSymbol(int i, int i2, int i3, SymbolTable symbolTable);

    /* access modifiers changed from: protected */
    public abstract void arrayCopy(int i, char[] cArr, int i2, int i3);

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public abstract byte[] bytesValue();

    /* access modifiers changed from: protected */
    public abstract boolean charArrayCompare(char[] cArr);

    public abstract char charAt(int i);

    /* access modifiers changed from: protected */
    public abstract void copyTo(int i, int i2, char[] cArr);

    public abstract int indexOf(char c, int i);

    public abstract boolean isEOF();

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public abstract char next();

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public abstract String numberString();

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public abstract String stringVal();

    public abstract String subString(int i, int i2);

    static {
        Map<String, Integer> map = new HashMap<>();
        map.put("null", 8);
        map.put("new", 9);
        map.put("true", 6);
        map.put("false", 7);
        map.put("undefined", 23);
        DEFAULT_KEYWORDS = map;
        for (int i = 48; i <= 57; i++) {
            digits[i] = i - 48;
        }
        for (int i2 = 97; i2 <= 102; i2++) {
            digits[i2] = (i2 - 97) + 10;
        }
        for (int i3 = 65; i3 <= 70; i3++) {
            digits[i3] = (i3 - 65) + 10;
        }
    }

    /* access modifiers changed from: protected */
    public void lexError(String key, Object... args) {
        this.token = 1;
    }

    public JSONLexerBase() {
        SoftReference<char[]> sbufRef = SBUF_REF_LOCAL.get();
        if (sbufRef != null) {
            this.sbuf = sbufRef.get();
            SBUF_REF_LOCAL.set(null);
        }
        if (this.sbuf == null) {
            this.sbuf = new char[64];
        }
    }

    public final int matchStat() {
        return this.matchStat;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void nextToken() {
        this.sp = 0;
        while (true) {
            this.pos = this.bp;
            char c = this.ch;
            if (c == '\"') {
                scanString();
                return;
            } else if (c == ',') {
                next();
                this.token = 16;
                return;
            } else if (c < '0' || c > '9') {
                char c2 = this.ch;
                if (c2 == '-') {
                    scanNumber();
                    return;
                }
                if (!(c2 == '\f' || c2 == '\r' || c2 == ' ')) {
                    if (c2 == ':') {
                        next();
                        this.token = 17;
                        return;
                    } else if (c2 == 'N') {
                        scanNULL();
                        return;
                    } else if (c2 == '[') {
                        next();
                        this.token = 14;
                        return;
                    } else if (c2 == ']') {
                        next();
                        this.token = 15;
                        return;
                    } else if (c2 == 'f') {
                        scanFalse();
                        return;
                    } else if (c2 == 'n') {
                        scanNullOrNew();
                        return;
                    } else if (c2 == '{') {
                        next();
                        this.token = 12;
                        return;
                    } else if (c2 == '}') {
                        next();
                        this.token = 13;
                        return;
                    } else if (c2 == 'S') {
                        scanSet();
                        return;
                    } else if (c2 == 'T') {
                        scanTreeSet();
                        return;
                    } else if (c2 == 't') {
                        scanTrue();
                        return;
                    } else if (c2 != 'u') {
                        switch (c2) {
                            default:
                                switch (c2) {
                                    case '\'':
                                        if (isEnabled(Feature.AllowSingleQuotes)) {
                                            scanStringSingleQuote();
                                            return;
                                        }
                                        throw new JSONException("Feature.AllowSingleQuotes is false");
                                    case '(':
                                        next();
                                        this.token = 10;
                                        return;
                                    case ')':
                                        next();
                                        this.token = 11;
                                        return;
                                    default:
                                        if (!isEOF()) {
                                            lexError("illegal.char", String.valueOf((int) this.ch));
                                            next();
                                            return;
                                        } else if (this.token != 20) {
                                            this.token = 20;
                                            int i = this.eofPos;
                                            this.bp = i;
                                            this.pos = i;
                                            return;
                                        } else {
                                            throw new JSONException("EOF error");
                                        }
                                }
                            case '\b':
                            case '\t':
                            case '\n':
                                break;
                        }
                    } else {
                        scanUndefined();
                        return;
                    }
                }
                next();
            } else {
                scanNumber();
                return;
            }
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void nextToken(int expect) {
        this.sp = 0;
        while (true) {
            if (expect == 2) {
                char c = this.ch;
                if (c < '0' || c > '9') {
                    char c2 = this.ch;
                    if (c2 == '\"') {
                        this.pos = this.bp;
                        scanString();
                        return;
                    } else if (c2 == '[') {
                        this.token = 14;
                        next();
                        return;
                    } else if (c2 == '{') {
                        this.token = 12;
                        next();
                        return;
                    }
                } else {
                    this.pos = this.bp;
                    scanNumber();
                    return;
                }
            } else if (expect == 4) {
                char c3 = this.ch;
                if (c3 == '\"') {
                    this.pos = this.bp;
                    scanString();
                    return;
                } else if (c3 < '0' || c3 > '9') {
                    char c4 = this.ch;
                    if (c4 == '[') {
                        this.token = 14;
                        next();
                        return;
                    } else if (c4 == '{') {
                        this.token = 12;
                        next();
                        return;
                    }
                } else {
                    this.pos = this.bp;
                    scanNumber();
                    return;
                }
            } else if (expect == 12) {
                char c5 = this.ch;
                if (c5 == '{') {
                    this.token = 12;
                    next();
                    return;
                } else if (c5 == '[') {
                    this.token = 14;
                    next();
                    return;
                }
            } else if (expect != 18) {
                if (expect != 20) {
                    switch (expect) {
                        case 14:
                            char c6 = this.ch;
                            if (c6 == '[') {
                                this.token = 14;
                                next();
                                return;
                            } else if (c6 == '{') {
                                this.token = 12;
                                next();
                                return;
                            }
                            break;
                        case 15:
                            if (this.ch == ']') {
                                this.token = 15;
                                next();
                                return;
                            }
                            break;
                        case 16:
                            char c7 = this.ch;
                            if (c7 == ',') {
                                this.token = 16;
                                next();
                                return;
                            } else if (c7 == '}') {
                                this.token = 13;
                                next();
                                return;
                            } else if (c7 == ']') {
                                this.token = 15;
                                next();
                                return;
                            } else if (c7 == 26) {
                                this.token = 20;
                                return;
                            }
                            break;
                    }
                }
                if (this.ch == 26) {
                    this.token = 20;
                    return;
                }
            } else {
                nextIdent();
                return;
            }
            char c8 = this.ch;
            if (c8 == ' ' || c8 == '\n' || c8 == '\r' || c8 == '\t' || c8 == '\f' || c8 == '\b') {
                next();
            } else {
                nextToken();
                return;
            }
        }
    }

    public final void nextIdent() {
        while (isWhitespace(this.ch)) {
            next();
        }
        char c = this.ch;
        if (c == '_' || Character.isLetter(c)) {
            scanIdent();
        } else {
            nextToken();
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void nextTokenWithColon() {
        nextTokenWithChar(':');
    }

    public final void nextTokenWithComma() {
        nextTokenWithChar(':');
    }

    public final void nextTokenWithChar(char expect) {
        this.sp = 0;
        while (true) {
            char c = this.ch;
            if (c == expect) {
                next();
                nextToken();
                return;
            } else if (c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == '\f' || c == '\b') {
                next();
            } else {
                throw new JSONException("not match " + expect + " - " + this.ch);
            }
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final int token() {
        return this.token;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final String tokenName() {
        return JSONToken.name(this.token);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final int pos() {
        return this.pos;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final int getBufferPosition() {
        return this.bp;
    }

    public final String stringDefaultValue() {
        if (isEnabled(Feature.InitStringFieldAsEmpty)) {
            return BuildConfig.FLAVOR;
        }
        return null;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final Number integerValue() throws NumberFormatException {
        long limit;
        int i;
        long result = 0;
        boolean negative = false;
        if (this.np == -1) {
            this.np = 0;
        }
        int i2 = this.np;
        int max = this.np + this.sp;
        char type = ' ';
        char charAt = charAt(max - 1);
        if (charAt == 'B') {
            max--;
            type = 'B';
        } else if (charAt == 'L') {
            max--;
            type = 'L';
        } else if (charAt == 'S') {
            max--;
            type = 'S';
        }
        if (charAt(this.np) == '-') {
            negative = true;
            limit = Long.MIN_VALUE;
            i2++;
        } else {
            limit = -9223372036854775807L;
        }
        if (i2 < max) {
            i = i2 + 1;
            result = (long) (-digits[charAt(i2)]);
        } else {
            i = i2;
        }
        while (i < max) {
            int i3 = i + 1;
            int digit = digits[charAt(i)];
            if (result < -922337203685477580L) {
                return new BigInteger(numberString());
            }
            long result2 = result * 10;
            if (result2 < ((long) digit) + limit) {
                return new BigInteger(numberString());
            }
            result = result2 - ((long) digit);
            i = i3;
        }
        if (!negative) {
            long result3 = -result;
            if (result3 > 2147483647L || type == 'L') {
                return Long.valueOf(result3);
            }
            if (type == 'S') {
                return Short.valueOf((short) ((int) result3));
            }
            if (type == 'B') {
                return Byte.valueOf((byte) ((int) result3));
            }
            return Integer.valueOf((int) result3);
        } else if (i <= this.np + 1) {
            throw new NumberFormatException(numberString());
        } else if (result < -2147483648L || type == 'L') {
            return Long.valueOf(result);
        } else {
            if (type == 'S') {
                return Short.valueOf((short) ((int) result));
            }
            if (type == 'B') {
                return Byte.valueOf((byte) ((int) result));
            }
            return Integer.valueOf((int) result);
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void nextTokenWithColon(int expect) {
        nextTokenWithChar(':');
    }

    public final void nextTokenWithComma(int expect) {
        nextTokenWithChar(',');
    }

    public final void nextTokenWithChar(char seperator, int expect) {
        this.sp = 0;
        while (true) {
            char c = this.ch;
            if (c == seperator) {
                next();
                while (true) {
                    if (expect == 2) {
                        char c2 = this.ch;
                        if (c2 >= '0' && c2 <= '9') {
                            this.pos = this.bp;
                            scanNumber();
                            return;
                        } else if (this.ch == '\"') {
                            this.pos = this.bp;
                            scanString();
                            return;
                        }
                    } else if (expect == 4) {
                        char c3 = this.ch;
                        if (c3 == '\"') {
                            this.pos = this.bp;
                            scanString();
                            return;
                        } else if (c3 >= '0' && c3 <= '9') {
                            this.pos = this.bp;
                            scanNumber();
                            return;
                        }
                    } else if (expect == 12) {
                        char c4 = this.ch;
                        if (c4 == '{') {
                            this.token = 12;
                            next();
                            return;
                        } else if (c4 == '[') {
                            this.token = 14;
                            next();
                            return;
                        }
                    } else if (expect == 14) {
                        char c5 = this.ch;
                        if (c5 == '[') {
                            this.token = 14;
                            next();
                            return;
                        } else if (c5 == '{') {
                            this.token = 12;
                            next();
                            return;
                        }
                    }
                    if (isWhitespace(this.ch)) {
                        next();
                    } else {
                        nextToken();
                        return;
                    }
                }
            } else if (isWhitespace(c)) {
                next();
            } else {
                throw new JSONException("not match " + expect + " - " + this.ch);
            }
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public float floatValue() {
        return Float.parseFloat(numberString());
    }

    public double doubleValue() {
        return Double.parseDouble(numberString());
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public void config(Feature feature, boolean state) {
        this.features = Feature.config(this.features, feature, state);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final boolean isEnabled(Feature feature) {
        return Feature.isEnabled(this.features, feature);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final char getCurrent() {
        return this.ch;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final String scanSymbol(SymbolTable symbolTable) {
        skipWhitespace();
        char c = this.ch;
        if (c == '\"') {
            return scanSymbol(symbolTable, '\"');
        }
        if (c == '\'') {
            if (isEnabled(Feature.AllowSingleQuotes)) {
                return scanSymbol(symbolTable, '\'');
            }
            throw new JSONException("syntax error");
        } else if (c == '}') {
            next();
            this.token = 13;
            return null;
        } else if (c == ',') {
            next();
            this.token = 16;
            return null;
        } else if (c == 26) {
            this.token = 20;
            return null;
        } else if (isEnabled(Feature.AllowUnQuotedFieldNames)) {
            return scanSymbolUnQuoted(symbolTable);
        } else {
            throw new JSONException("syntax error");
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final String scanSymbol(SymbolTable symbolTable, char quote) {
        int i;
        String value;
        int offset;
        int newCapcity;
        SymbolTable symbolTable2 = symbolTable;
        int hash = 0;
        this.np = this.bp;
        this.sp = 0;
        boolean hasSpecial2 = false;
        int i2 = 0;
        char x_char = 0;
        int x_val = 0;
        int hash2 = 0;
        char c1 = 0;
        char c2 = 0;
        char c3 = 0;
        char c4 = 0;
        int hash3 = 0;
        while (true) {
            char chLocal = next();
            if (chLocal == quote) {
                this.token = 4;
                if (!hasSpecial2) {
                    int i3 = this.np;
                    if (i3 == -1) {
                        offset = 0;
                    } else {
                        offset = i3 + 1;
                    }
                    value = addSymbol(offset, this.sp, hash, symbolTable2);
                    i = 0;
                } else {
                    i = 0;
                    value = symbolTable2.addSymbol(this.sbuf, 0, this.sp, hash);
                }
                this.sp = i;
                next();
                return value;
            } else if (chLocal == 26) {
                throw new JSONException("unclosed.str");
            } else if (chLocal == '\\') {
                if (!hasSpecial2) {
                    int i4 = this.sp;
                    char[] cArr = this.sbuf;
                    if (i4 >= cArr.length) {
                        int newCapcity2 = cArr.length * 2;
                        if (i4 > newCapcity2) {
                            newCapcity2 = this.sp;
                        }
                        char[] newsbuf = new char[newCapcity2];
                        char[] cArr2 = this.sbuf;
                        newCapcity = 0;
                        System.arraycopy(cArr2, 0, newsbuf, 0, cArr2.length);
                        this.sbuf = newsbuf;
                        i2 = newCapcity2;
                    } else {
                        newCapcity = 0;
                    }
                    arrayCopy(this.np + 1, this.sbuf, newCapcity, this.sp);
                    hasSpecial2 = true;
                }
                char chLocal2 = next();
                if (chLocal2 == '\"') {
                    putChar('\"');
                    hash = (hash * 31) + 34;
                } else if (chLocal2 != '\'') {
                    if (chLocal2 != 'F') {
                        if (chLocal2 == '\\') {
                            putChar('\\');
                            hash = (hash * 31) + 92;
                        } else if (chLocal2 == 'b') {
                            putChar('\b');
                            hash = (hash * 31) + 8;
                        } else if (chLocal2 != 'f') {
                            if (chLocal2 == 'n') {
                                putChar('\n');
                                hash = (hash * 31) + 10;
                            } else if (chLocal2 == 'r') {
                                putChar('\r');
                                hash = (hash * 31) + 13;
                            } else if (chLocal2 != 'x') {
                                switch (chLocal2) {
                                    case '/':
                                        putChar('/');
                                        hash = (hash * 31) + 47;
                                        break;
                                    case '0':
                                        putChar(0);
                                        hash = (hash * 31) + chLocal2;
                                        break;
                                    case '1':
                                        putChar(1);
                                        hash = (hash * 31) + chLocal2;
                                        break;
                                    case '2':
                                        putChar(2);
                                        hash = (hash * 31) + chLocal2;
                                        break;
                                    case '3':
                                        putChar(3);
                                        hash = (hash * 31) + chLocal2;
                                        break;
                                    case '4':
                                        putChar(4);
                                        hash = (hash * 31) + chLocal2;
                                        break;
                                    case '5':
                                        putChar(5);
                                        hash = (hash * 31) + chLocal2;
                                        break;
                                    case '6':
                                        putChar(6);
                                        hash = (hash * 31) + chLocal2;
                                        break;
                                    case '7':
                                        putChar(7);
                                        hash = (hash * 31) + chLocal2;
                                        break;
                                    default:
                                        switch (chLocal2) {
                                            case 't':
                                                putChar('\t');
                                                hash = (hash * 31) + 9;
                                                break;
                                            case 'u':
                                                c1 = next();
                                                c2 = next();
                                                c3 = next();
                                                c4 = next();
                                                int val = Integer.parseInt(new String(new char[]{c1, c2, c3, c4}), 16);
                                                putChar((char) val);
                                                hash = (hash * 31) + val;
                                                hash3 = val;
                                                break;
                                            case 'v':
                                                putChar(11);
                                                hash = (hash * 31) + 11;
                                                break;
                                            default:
                                                this.ch = chLocal2;
                                                throw new JSONException("unclosed.str.lit");
                                        }
                                }
                            } else {
                                char x1 = next();
                                this.ch = x1;
                                char x2 = next();
                                this.ch = x2;
                                int[] iArr = digits;
                                x_val = (iArr[x1] * 16) + iArr[x2];
                                char x_char2 = (char) x_val;
                                putChar(x_char2);
                                hash = (hash * 31) + x_char2;
                                hash2 = x_char2;
                                x_char = x2;
                                i2 = x1;
                            }
                        }
                    }
                    putChar('\f');
                    hash = (hash * 31) + 12;
                } else {
                    putChar('\'');
                    hash = (hash * 31) + 39;
                }
                symbolTable2 = symbolTable;
            } else {
                hash = (hash * 31) + chLocal;
                if (!hasSpecial2) {
                    this.sp++;
                } else {
                    int i5 = this.sp;
                    char[] cArr3 = this.sbuf;
                    if (i5 == cArr3.length) {
                        putChar(chLocal);
                    } else {
                        this.sp = i5 + 1;
                        cArr3[i5] = chLocal;
                    }
                }
                symbolTable2 = symbolTable;
            }
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void resetStringPosition() {
        this.sp = 0;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final String scanSymbolUnQuoted(SymbolTable symbolTable) {
        boolean[] firstIdentifierFlags = IOUtils.firstIdentifierFlags;
        char first = this.ch;
        if (this.ch >= firstIdentifierFlags.length || firstIdentifierFlags[first]) {
            boolean[] identifierFlags = IOUtils.identifierFlags;
            int hash = first;
            this.np = this.bp;
            this.sp = 1;
            while (true) {
                char chLocal = next();
                if (chLocal < identifierFlags.length && !identifierFlags[chLocal]) {
                    break;
                }
                hash = (hash * 31) + chLocal;
                this.sp++;
            }
            this.ch = charAt(this.bp);
            this.token = 18;
            if (this.sp == 4 && hash == 3392903 && charAt(this.np) == 'n' && charAt(this.np + 1) == 'u' && charAt(this.np + 2) == 'l' && charAt(this.np + 3) == 'l') {
                return null;
            }
            return addSymbol(this.np, this.sp, hash, symbolTable);
        }
        throw new JSONException("illegal identifier : " + this.ch);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void scanString() {
        this.np = this.bp;
        this.hasSpecial = false;
        char x2 = 0;
        char u4 = 0;
        int val = 0;
        while (true) {
            char ch2 = next();
            if (ch2 == '\"') {
                this.token = 4;
                this.ch = next();
                return;
            } else if (ch2 == 26) {
                throw new JSONException("unclosed string : " + ch2);
            } else if (ch2 == '\\') {
                if (!this.hasSpecial) {
                    this.hasSpecial = true;
                    int i = this.sp;
                    char[] cArr = this.sbuf;
                    if (i >= cArr.length) {
                        int newCapcity = cArr.length * 2;
                        if (i > newCapcity) {
                            newCapcity = this.sp;
                        }
                        char[] newsbuf = new char[newCapcity];
                        char[] cArr2 = this.sbuf;
                        System.arraycopy(cArr2, 0, newsbuf, 0, cArr2.length);
                        this.sbuf = newsbuf;
                        x2 = newCapcity;
                    }
                    copyTo(this.np + 1, this.sp, this.sbuf);
                }
                char ch3 = next();
                if (ch3 == '\"') {
                    putChar('\"');
                } else if (ch3 != '\'') {
                    if (ch3 != 'F') {
                        if (ch3 == '\\') {
                            putChar('\\');
                        } else if (ch3 == 'b') {
                            putChar('\b');
                        } else if (ch3 != 'f') {
                            if (ch3 == 'n') {
                                putChar('\n');
                            } else if (ch3 == 'r') {
                                putChar('\r');
                            } else if (ch3 != 'x') {
                                switch (ch3) {
                                    case '/':
                                        putChar('/');
                                        break;
                                    case '0':
                                        putChar(0);
                                        break;
                                    case '1':
                                        putChar(1);
                                        break;
                                    case '2':
                                        putChar(2);
                                        break;
                                    case '3':
                                        putChar(3);
                                        break;
                                    case '4':
                                        putChar(4);
                                        break;
                                    case '5':
                                        putChar(5);
                                        break;
                                    case '6':
                                        putChar(6);
                                        break;
                                    case '7':
                                        putChar(7);
                                        break;
                                    default:
                                        switch (ch3) {
                                            case 't':
                                                putChar('\t');
                                                break;
                                            case 'u':
                                                putChar((char) Integer.parseInt(new String(new char[]{next(), next(), next(), next()}), 16));
                                                x2 = x2;
                                                u4 = u4;
                                                val = val;
                                                break;
                                            case 'v':
                                                putChar(11);
                                                break;
                                            default:
                                                this.ch = ch3;
                                                throw new JSONException("unclosed string : " + ch3);
                                        }
                                }
                            } else {
                                char x1 = next();
                                char x22 = next();
                                int[] iArr = digits;
                                val = (iArr[x1] * 16) + iArr[x22];
                                putChar((char) val);
                                x2 = x1;
                                u4 = x22;
                            }
                        }
                    }
                    putChar('\f');
                } else {
                    putChar('\'');
                }
            } else if (!this.hasSpecial) {
                this.sp++;
            } else {
                int i2 = this.sp;
                char[] cArr3 = this.sbuf;
                if (i2 == cArr3.length) {
                    putChar(ch2);
                } else {
                    this.sp = i2 + 1;
                    cArr3[i2] = ch2;
                }
            }
        }
    }

    public Calendar getCalendar() {
        return this.calendar;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final int intValue() {
        int limit;
        int i;
        if (this.np == -1) {
            this.np = 0;
        }
        int result = 0;
        boolean negative = false;
        int i2 = this.np;
        int i3 = this.np;
        int max = this.sp + i3;
        if (charAt(i3) == '-') {
            negative = true;
            limit = Integer.MIN_VALUE;
            i2++;
        } else {
            limit = -2147483647;
        }
        if (i2 < max) {
            result = -digits[charAt(i2)];
            i2++;
        }
        while (true) {
            if (i2 >= max) {
                break;
            }
            i = i2 + 1;
            char chLocal = charAt(i2);
            if (chLocal == 'L' || chLocal == 'S' || chLocal == 'B') {
                break;
            }
            int digit = digits[chLocal];
            if (result >= -214748364) {
                int result2 = result * 10;
                if (result2 >= limit + digit) {
                    result = result2 - digit;
                    i2 = i;
                } else {
                    throw new NumberFormatException(numberString());
                }
            } else {
                throw new NumberFormatException(numberString());
            }
        }
        i2 = i;
        if (!negative) {
            return -result;
        }
        if (i2 > this.np + 1) {
            return result;
        }
        throw new NumberFormatException(numberString());
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        char[] cArr = this.sbuf;
        if (cArr.length <= 8192) {
            SBUF_REF_LOCAL.set(new SoftReference<>(cArr));
        }
        this.sbuf = null;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final boolean isRef() {
        if (this.sp == 4 && charAt(this.np + 1) == '$' && charAt(this.np + 2) == 'r' && charAt(this.np + 3) == 'e' && charAt(this.np + 4) == 'f') {
            return true;
        }
        return false;
    }

    public int scanType(String type) {
        this.matchStat = 0;
        if (!charArrayCompare(typeFieldName)) {
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
        char c = this.ch;
        if (c == ',') {
            int bpLocal4 = bpLocal3 + 1;
            this.ch = charAt(bpLocal4);
            this.bp = bpLocal4;
            this.token = 16;
            return 3;
        }
        if (c == '}') {
            bpLocal3++;
            this.ch = charAt(bpLocal3);
            char c2 = this.ch;
            if (c2 == ',') {
                this.token = 16;
                bpLocal3++;
                this.ch = charAt(bpLocal3);
            } else if (c2 == ']') {
                this.token = 15;
                bpLocal3++;
                this.ch = charAt(bpLocal3);
            } else if (c2 == '}') {
                this.token = 13;
                bpLocal3++;
                this.ch = charAt(bpLocal3);
            } else if (c2 != 26) {
                return -1;
            } else {
                this.token = 20;
            }
            this.matchStat = 4;
        }
        this.bp = bpLocal3;
        return this.matchStat;
    }

    public final boolean matchField(char[] fieldName) {
        if (!charArrayCompare(fieldName)) {
            return false;
        }
        this.bp += fieldName.length;
        this.ch = charAt(this.bp);
        char c = this.ch;
        if (c == '{') {
            next();
            this.token = 12;
            return true;
        } else if (c == '[') {
            next();
            this.token = 14;
            return true;
        } else {
            nextToken();
            return true;
        }
    }

    public String scanFieldString(char[] fieldName) {
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return stringDefaultValue();
        }
        int offset = fieldName.length;
        int offset2 = offset + 1;
        if (charAt(this.bp + offset) != '\"') {
            this.matchStat = -1;
            return stringDefaultValue();
        }
        boolean hasSpecial2 = false;
        int endIndex = indexOf('\"', this.bp + fieldName.length + 1);
        if (endIndex != -1) {
            int startIndex2 = this.bp + fieldName.length + 1;
            String stringVal = subString(startIndex2, endIndex - startIndex2);
            int i = this.bp + fieldName.length + 1;
            while (true) {
                if (i >= endIndex) {
                    break;
                } else if (charAt(i) == '\\') {
                    hasSpecial2 = true;
                    break;
                } else {
                    i++;
                }
            }
            if (hasSpecial2) {
                this.matchStat = -1;
                return stringDefaultValue();
            }
            int i2 = this.bp;
            int offset3 = offset2 + (endIndex - ((fieldName.length + i2) + 1)) + 1;
            int offset4 = offset3 + 1;
            char chLocal = charAt(i2 + offset3);
            if (chLocal == ',') {
                this.bp += offset4 - 1;
                next();
                this.matchStat = 3;
                return stringVal;
            } else if (chLocal == '}') {
                int offset5 = offset4 + 1;
                char chLocal2 = charAt(this.bp + offset4);
                if (chLocal2 == ',') {
                    this.token = 16;
                    this.bp += offset5 - 1;
                    next();
                } else if (chLocal2 == ']') {
                    this.token = 15;
                    this.bp += offset5 - 1;
                    next();
                } else if (chLocal2 == '}') {
                    this.token = 13;
                    this.bp += offset5 - 1;
                    next();
                } else if (chLocal2 == 26) {
                    this.token = 20;
                    this.bp += offset5 - 1;
                    this.ch = 26;
                } else {
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

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public String scanString(char expectNextChar) {
        this.matchStat = 0;
        int offset = 0 + 1;
        char chLocal = charAt(this.bp + 0);
        if (chLocal == 'n') {
            if (charAt(this.bp + offset) == 'u' && charAt(this.bp + offset + 1) == 'l' && charAt(this.bp + offset + 2) == 'l') {
                int offset2 = offset + 3;
                int offset3 = offset2 + 1;
                if (charAt(this.bp + offset2) == expectNextChar) {
                    this.bp += offset3 - 1;
                    next();
                    this.matchStat = 3;
                    return null;
                }
                this.matchStat = -1;
                return null;
            }
            this.matchStat = -1;
            return null;
        } else if (chLocal != '\"') {
            this.matchStat = -1;
            return stringDefaultValue();
        } else {
            boolean hasSpecial2 = false;
            int startIndex = this.bp + 1;
            int endIndex = indexOf('\"', startIndex);
            if (endIndex != -1) {
                String stringVal = subString(this.bp + 1, endIndex - startIndex);
                int i = this.bp + 1;
                while (true) {
                    if (i >= endIndex) {
                        break;
                    } else if (charAt(i) == '\\') {
                        hasSpecial2 = true;
                        break;
                    } else {
                        i++;
                    }
                }
                if (hasSpecial2) {
                    this.matchStat = -1;
                    return stringDefaultValue();
                }
                int i2 = this.bp;
                int offset4 = offset + (endIndex - (i2 + 1)) + 1;
                int offset5 = offset4 + 1;
                if (charAt(i2 + offset4) == expectNextChar) {
                    this.bp += offset5 - 1;
                    next();
                    this.matchStat = 3;
                    return stringVal;
                }
                this.matchStat = -1;
                return stringVal;
            }
            throw new JSONException("unclosed str");
        }
    }

    public String scanFieldSymbol(char[] fieldName, SymbolTable symbolTable) {
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return null;
        }
        int offset = fieldName.length;
        int offset2 = offset + 1;
        if (charAt(this.bp + offset) != '\"') {
            this.matchStat = -1;
            return null;
        }
        int hash = 0;
        while (true) {
            int offset3 = offset2 + 1;
            char chLocal = charAt(this.bp + offset2);
            if (chLocal == '\"') {
                int i = this.bp;
                int start = fieldName.length + i + 1;
                String strVal = addSymbol(start, ((i + offset3) - start) - 1, hash, symbolTable);
                int offset4 = offset3 + 1;
                char chLocal2 = charAt(this.bp + offset3);
                if (chLocal2 == ',') {
                    this.bp += offset4 - 1;
                    next();
                    this.matchStat = 3;
                    return strVal;
                } else if (chLocal2 == '}') {
                    int offset5 = offset4 + 1;
                    char chLocal3 = charAt(this.bp + offset4);
                    if (chLocal3 == ',') {
                        this.token = 16;
                        this.bp += offset5 - 1;
                        next();
                    } else if (chLocal3 == ']') {
                        this.token = 15;
                        this.bp += offset5 - 1;
                        next();
                    } else if (chLocal3 == '}') {
                        this.token = 13;
                        this.bp += offset5 - 1;
                        next();
                    } else if (chLocal3 == 26) {
                        this.token = 20;
                        this.bp += offset5 - 1;
                        this.ch = 26;
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
                hash = (hash * 31) + chLocal;
                if (chLocal == '\\') {
                    this.matchStat = -1;
                    return null;
                }
                offset2 = offset3;
            }
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public Enum<?> scanEnum(Class<?> enumClass, SymbolTable symbolTable, char serperator) {
        String name = scanSymbolWithSeperator(symbolTable, serperator);
        if (name == null) {
            return null;
        }
        return Enum.valueOf(enumClass, name);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public String scanSymbolWithSeperator(SymbolTable symbolTable, char serperator) {
        this.matchStat = 0;
        int offset = 0 + 1;
        char chLocal = charAt(this.bp + 0);
        if (chLocal == 'n') {
            if (charAt(this.bp + offset) == 'u' && charAt(this.bp + offset + 1) == 'l' && charAt(this.bp + offset + 2) == 'l') {
                int offset2 = offset + 3;
                int offset3 = offset2 + 1;
                if (charAt(this.bp + offset2) == serperator) {
                    this.bp += offset3 - 1;
                    next();
                    this.matchStat = 3;
                    return null;
                }
                this.matchStat = -1;
                return null;
            }
            this.matchStat = -1;
            return null;
        } else if (chLocal != '\"') {
            this.matchStat = -1;
            return null;
        } else {
            int hash = 0;
            while (true) {
                int offset4 = offset + 1;
                char chLocal2 = charAt(this.bp + offset);
                if (chLocal2 == '\"') {
                    int i = this.bp;
                    int start = i + 0 + 1;
                    String strVal = addSymbol(start, ((i + offset4) - start) - 1, hash, symbolTable);
                    int offset5 = offset4 + 1;
                    if (charAt(this.bp + offset4) == serperator) {
                        this.bp += offset5 - 1;
                        next();
                        this.matchStat = 3;
                        return strVal;
                    }
                    this.matchStat = -1;
                    return strVal;
                }
                hash = (hash * 31) + chLocal2;
                if (chLocal2 == '\\') {
                    this.matchStat = -1;
                    return null;
                }
                offset = offset4;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x005f, code lost:
        r3 = r11.bp;
        r6 = r3 + r6;
        r0.add(subString(r6, ((r3 + r8) - r6) - 1));
        r10 = r8 + 1;
        r2 = charAt(r11.bp + r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x007a, code lost:
        if (r2 != ',') goto L_0x0086;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x007c, code lost:
        r6 = r10 + 1;
        r2 = charAt(r11.bp + r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0088, code lost:
        if (r2 != ']') goto L_0x00ff;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x008a, code lost:
        r9 = r10 + 1;
        r2 = charAt(r11.bp + r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0094, code lost:
        if (r2 != ',') goto L_0x00a4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0096, code lost:
        r11.bp += r9 - 1;
        next();
        r11.matchStat = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00a3, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00a6, code lost:
        if (r2 != '}') goto L_0x00fc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00a8, code lost:
        r8 = r9 + 1;
        r2 = charAt(r11.bp + r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00b1, code lost:
        if (r2 != ',') goto L_0x00c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00b3, code lost:
        r11.token = 16;
        r11.bp += r8 - 1;
        next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00c2, code lost:
        if (r2 != ']') goto L_0x00d3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00c4, code lost:
        r11.token = 15;
        r11.bp += r8 - 1;
        next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00d3, code lost:
        if (r2 != '}') goto L_0x00e4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00d5, code lost:
        r11.token = 13;
        r11.bp += r8 - 1;
        next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00e6, code lost:
        if (r2 != 26) goto L_0x00f9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00e8, code lost:
        r11.bp += r8 - 1;
        r11.token = 20;
        r11.ch = 26;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00f5, code lost:
        r11.matchStat = 4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00f8, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00f9, code lost:
        r11.matchStat = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00fb, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00fc, code lost:
        r11.matchStat = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00fe, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00ff, code lost:
        r11.matchStat = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0101, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.Collection<java.lang.String> scanFieldStringArray(char[] r12, java.lang.Class<?> r13) {
        /*
        // Method dump skipped, instructions count: 279
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexerBase.scanFieldStringArray(char[], java.lang.Class):java.util.Collection");
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public Collection<String> scanStringArray(Class<?> type, char seperator) {
        Collection<String> list;
        int offset;
        char chLocal;
        this.matchStat = 0;
        if (type.isAssignableFrom(HashSet.class)) {
            list = new HashSet<>();
        } else if (type.isAssignableFrom(ArrayList.class)) {
            list = new ArrayList<>();
        } else {
            try {
                list = (Collection) type.newInstance();
            } catch (Exception e) {
                throw new JSONException(e.getMessage(), e);
            }
        }
        int offset2 = 0 + 1;
        char chLocal2 = charAt(this.bp + 0);
        char c = 'u';
        if (chLocal2 == 'n') {
            if (charAt(this.bp + offset2) == 'u' && charAt(this.bp + offset2 + 1) == 'l' && charAt(this.bp + offset2 + 2) == 'l') {
                int offset3 = offset2 + 3;
                int offset4 = offset3 + 1;
                if (charAt(this.bp + offset3) == seperator) {
                    this.bp += offset4 - 1;
                    next();
                    this.matchStat = 3;
                    return null;
                }
                this.matchStat = -1;
                return null;
            }
            this.matchStat = -1;
            return null;
        } else if (chLocal2 != '[') {
            this.matchStat = -1;
            return null;
        } else {
            int offset5 = offset2 + 1;
            char chLocal3 = charAt(this.bp + offset2);
            while (true) {
                if (chLocal3 == 'n' && charAt(this.bp + offset5) == c && charAt(this.bp + offset5 + 1) == 'l' && charAt(this.bp + offset5 + 2) == 'l') {
                    int offset6 = offset5 + 3;
                    chLocal = charAt(this.bp + offset6);
                    offset = offset6 + 1;
                } else if (chLocal3 != '\"') {
                    this.matchStat = -1;
                    return null;
                } else {
                    while (true) {
                        int offset7 = offset5 + 1;
                        char chLocal4 = charAt(this.bp + offset5);
                        if (chLocal4 == '\"') {
                            int i = this.bp;
                            int start = i + offset5;
                            list.add(subString(start, ((i + offset7) - start) - 1));
                            offset = offset7 + 1;
                            chLocal = charAt(this.bp + offset7);
                            break;
                        } else if (chLocal4 == '\\') {
                            this.matchStat = -1;
                            return null;
                        } else {
                            offset5 = offset7;
                        }
                    }
                }
                if (chLocal == ',') {
                    offset5 = offset + 1;
                    chLocal3 = charAt(this.bp + offset);
                    c = 'u';
                } else if (chLocal == ']') {
                    int offset8 = offset + 1;
                    if (charAt(this.bp + offset) == seperator) {
                        this.bp += offset8 - 1;
                        next();
                        this.matchStat = 3;
                        return list;
                    }
                    this.matchStat = -1;
                    return list;
                } else {
                    this.matchStat = -1;
                    return null;
                }
            }
        }
    }

    public int scanFieldInt(char[] fieldName) {
        int offset;
        char chLocal;
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return 0;
        }
        int offset2 = fieldName.length;
        int offset3 = offset2 + 1;
        char chLocal2 = charAt(this.bp + offset2);
        if (chLocal2 < '0' || chLocal2 > '9') {
            this.matchStat = -1;
            return 0;
        }
        int value = digits[chLocal2];
        while (true) {
            offset = offset3 + 1;
            chLocal = charAt(this.bp + offset3);
            if (chLocal < '0' || chLocal > '9') {
                break;
            }
            value = (value * 10) + digits[chLocal];
            offset3 = offset;
        }
        if (chLocal == '.') {
            this.matchStat = -1;
            return 0;
        } else if (value < 0) {
            this.matchStat = -1;
            return 0;
        } else if (chLocal == ',') {
            this.bp += offset - 1;
            next();
            this.matchStat = 3;
            this.token = 16;
            return value;
        } else if (chLocal == '}') {
            int offset4 = offset + 1;
            char chLocal3 = charAt(this.bp + offset);
            if (chLocal3 == ',') {
                this.token = 16;
                this.bp += offset4 - 1;
                next();
            } else if (chLocal3 == ']') {
                this.token = 15;
                this.bp += offset4 - 1;
                next();
            } else if (chLocal3 == '}') {
                this.token = 13;
                this.bp += offset4 - 1;
                next();
            } else if (chLocal3 == 26) {
                this.token = 20;
                this.bp += offset4 - 1;
                this.ch = 26;
            } else {
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

    public boolean scanBoolean(char expectNext) {
        int offset;
        this.matchStat = 0;
        int offset2 = 0 + 1;
        char chLocal = charAt(this.bp + 0);
        boolean value = false;
        if (chLocal == 't') {
            if (charAt(this.bp + offset2) == 'r' && charAt(this.bp + offset2 + 1) == 'u' && charAt(this.bp + offset2 + 2) == 'e') {
                int offset3 = offset2 + 3;
                offset = offset3 + 1;
                chLocal = charAt(this.bp + offset3);
                value = true;
            } else {
                this.matchStat = -1;
                return false;
            }
        } else if (chLocal != 'f') {
            offset = offset2;
        } else if (charAt(this.bp + offset2) == 'a' && charAt(this.bp + offset2 + 1) == 'l' && charAt(this.bp + offset2 + 2) == 's' && charAt(this.bp + offset2 + 3) == 'e') {
            int offset4 = offset2 + 4;
            offset = offset4 + 1;
            chLocal = charAt(this.bp + offset4);
            value = false;
        } else {
            this.matchStat = -1;
            return false;
        }
        if (chLocal == expectNext) {
            this.bp += offset - 1;
            next();
            this.matchStat = 3;
            return value;
        }
        this.matchStat = -1;
        return value;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public int scanInt(char expectNext) {
        int offset;
        char chLocal;
        this.matchStat = 0;
        int offset2 = 0 + 1;
        char chLocal2 = charAt(this.bp + 0);
        if (chLocal2 < '0' || chLocal2 > '9') {
            this.matchStat = -1;
            return 0;
        }
        int value = digits[chLocal2];
        while (true) {
            offset = offset2 + 1;
            chLocal = charAt(this.bp + offset2);
            if (chLocal < '0' || chLocal > '9') {
                break;
            }
            value = (value * 10) + digits[chLocal];
            offset2 = offset;
        }
        if (chLocal == '.') {
            this.matchStat = -1;
            return 0;
        } else if (value < 0) {
            this.matchStat = -1;
            return 0;
        } else if (chLocal == expectNext) {
            this.bp += offset - 1;
            next();
            this.matchStat = 3;
            this.token = 16;
            return value;
        } else {
            this.matchStat = -1;
            return value;
        }
    }

    public boolean scanFieldBoolean(char[] fieldName) {
        int offset;
        boolean value;
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return false;
        }
        int offset2 = fieldName.length;
        int offset3 = offset2 + 1;
        char chLocal = charAt(this.bp + offset2);
        if (chLocal == 't') {
            int offset4 = offset3 + 1;
            if (charAt(this.bp + offset3) != 'r') {
                this.matchStat = -1;
                return false;
            }
            int offset5 = offset4 + 1;
            if (charAt(this.bp + offset4) != 'u') {
                this.matchStat = -1;
                return false;
            }
            int offset6 = offset5 + 1;
            if (charAt(this.bp + offset5) != 'e') {
                this.matchStat = -1;
                return false;
            }
            value = true;
            offset = offset6;
        } else if (chLocal == 'f') {
            int offset7 = offset3 + 1;
            if (charAt(this.bp + offset3) != 'a') {
                this.matchStat = -1;
                return false;
            }
            int offset8 = offset7 + 1;
            if (charAt(this.bp + offset7) != 'l') {
                this.matchStat = -1;
                return false;
            }
            int offset9 = offset8 + 1;
            if (charAt(this.bp + offset8) != 's') {
                this.matchStat = -1;
                return false;
            }
            offset = offset9 + 1;
            if (charAt(this.bp + offset9) != 'e') {
                this.matchStat = -1;
                return false;
            }
            value = false;
        } else {
            this.matchStat = -1;
            return false;
        }
        int offset10 = offset + 1;
        char chLocal2 = charAt(this.bp + offset);
        if (chLocal2 == ',') {
            this.bp += offset10 - 1;
            next();
            this.matchStat = 3;
            this.token = 16;
            return value;
        } else if (chLocal2 == '}') {
            int offset11 = offset10 + 1;
            char chLocal3 = charAt(this.bp + offset10);
            if (chLocal3 == ',') {
                this.token = 16;
                this.bp += offset11 - 1;
                next();
            } else if (chLocal3 == ']') {
                this.token = 15;
                this.bp += offset11 - 1;
                next();
            } else if (chLocal3 == '}') {
                this.token = 13;
                this.bp += offset11 - 1;
                next();
            } else if (chLocal3 == 26) {
                this.token = 20;
                this.bp += offset11 - 1;
                this.ch = 26;
            } else {
                this.matchStat = -1;
                return false;
            }
            this.matchStat = 4;
            return value;
        } else {
            this.matchStat = -1;
            return false;
        }
    }

    public long scanFieldLong(char[] fieldName) {
        int offset;
        char chLocal;
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return 0;
        }
        int offset2 = fieldName.length;
        int offset3 = offset2 + 1;
        char chLocal2 = charAt(this.bp + offset2);
        char c = '0';
        if (chLocal2 < '0' || chLocal2 > '9') {
            this.matchStat = -1;
            return 0;
        }
        long value = (long) digits[chLocal2];
        while (true) {
            offset = offset3 + 1;
            chLocal = charAt(this.bp + offset3);
            if (chLocal < c || chLocal > '9') {
                break;
            }
            value = (10 * value) + ((long) digits[chLocal]);
            offset3 = offset;
            c = '0';
        }
        if (chLocal == '.') {
            this.matchStat = -1;
            return 0;
        } else if (value < 0) {
            this.matchStat = -1;
            return 0;
        } else if (chLocal == ',') {
            this.bp += offset - 1;
            next();
            this.matchStat = 3;
            this.token = 16;
            return value;
        } else if (chLocal == '}') {
            int offset4 = offset + 1;
            char chLocal3 = charAt(this.bp + offset);
            if (chLocal3 == ',') {
                this.token = 16;
                this.bp += offset4 - 1;
                next();
            } else if (chLocal3 == ']') {
                this.token = 15;
                this.bp += offset4 - 1;
                next();
            } else if (chLocal3 == '}') {
                this.token = 13;
                this.bp += offset4 - 1;
                next();
            } else if (chLocal3 == 26) {
                this.token = 20;
                this.bp += offset4 - 1;
                this.ch = 26;
            } else {
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

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public long scanLong(char expectNextChar) {
        int offset;
        char chLocal;
        this.matchStat = 0;
        int offset2 = 0 + 1;
        char chLocal2 = charAt(this.bp + 0);
        if (chLocal2 < '0' || chLocal2 > '9') {
            this.matchStat = -1;
            return 0;
        }
        long value = (long) digits[chLocal2];
        while (true) {
            offset = offset2 + 1;
            chLocal = charAt(this.bp + offset2);
            if (chLocal < '0' || chLocal > '9') {
                break;
            }
            value = (10 * value) + ((long) digits[chLocal]);
            offset2 = offset;
        }
        if (chLocal == '.') {
            this.matchStat = -1;
            return 0;
        } else if (value < 0) {
            this.matchStat = -1;
            return 0;
        } else if (chLocal == expectNextChar) {
            this.bp += offset - 1;
            next();
            this.matchStat = 3;
            this.token = 16;
            return value;
        } else {
            this.matchStat = -1;
            return value;
        }
    }

    /* JADX INFO: Multiple debug info for r3v7 float: [D('count' int), D('value' float)] */
    public final float scanFieldFloat(char[] fieldName) {
        int offset;
        char chLocal;
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return 0.0f;
        }
        int offset2 = fieldName.length;
        int offset3 = offset2 + 1;
        char chLocal2 = charAt(this.bp + offset2);
        if (chLocal2 < '0' || chLocal2 > '9') {
            this.matchStat = -1;
            return 0.0f;
        }
        while (true) {
            offset = offset3 + 1;
            chLocal = charAt(this.bp + offset3);
            if (chLocal < '0' || chLocal > '9') {
                break;
            }
            offset3 = offset;
        }
        if (chLocal == '.') {
            int offset4 = offset + 1;
            char chLocal3 = charAt(this.bp + offset);
            if (chLocal3 >= '0' && chLocal3 <= '9') {
                while (true) {
                    offset = offset4 + 1;
                    chLocal = charAt(this.bp + offset4);
                    if (chLocal < '0' || chLocal > '9') {
                        break;
                    }
                    offset4 = offset;
                }
            } else {
                this.matchStat = -1;
                return 0.0f;
            }
        }
        int i = this.bp;
        int start = fieldName.length + i;
        float value = Float.parseFloat(subString(start, ((i + offset) - start) - 1));
        if (chLocal == ',') {
            this.bp += offset - 1;
            next();
            this.matchStat = 3;
            this.token = 16;
            return value;
        } else if (chLocal == '}') {
            int offset5 = offset + 1;
            char chLocal4 = charAt(this.bp + offset);
            if (chLocal4 == ',') {
                this.token = 16;
                this.bp += offset5 - 1;
                next();
            } else if (chLocal4 == ']') {
                this.token = 15;
                this.bp += offset5 - 1;
                next();
            } else if (chLocal4 == '}') {
                this.token = 13;
                this.bp += offset5 - 1;
                next();
            } else if (chLocal4 == 26) {
                this.bp += offset5 - 1;
                this.token = 20;
                this.ch = 26;
            } else {
                this.matchStat = -1;
                return 0.0f;
            }
            this.matchStat = 4;
            return value;
        } else {
            this.matchStat = -1;
            return 0.0f;
        }
    }

    /* JADX INFO: Multiple debug info for r1v4 float: [D('start' int), D('value' float)] */
    public final float scanFloat(char seperator) {
        int offset;
        char chLocal;
        int offset2;
        this.matchStat = 0;
        int offset3 = 0 + 1;
        char chLocal2 = charAt(this.bp + 0);
        if (chLocal2 < '0' || chLocal2 > '9') {
            this.matchStat = -1;
            return 0.0f;
        }
        while (true) {
            offset = offset3 + 1;
            chLocal = charAt(this.bp + offset3);
            if (chLocal < '0' || chLocal > '9') {
                break;
            }
            offset3 = offset;
        }
        if (chLocal == '.') {
            int offset4 = offset + 1;
            char chLocal3 = charAt(this.bp + offset);
            if (chLocal3 >= '0' && chLocal3 <= '9') {
                while (true) {
                    offset2 = offset4 + 1;
                    chLocal = charAt(this.bp + offset4);
                    if (chLocal < '0' || chLocal > '9') {
                        break;
                    }
                    offset4 = offset2;
                }
            } else {
                this.matchStat = -1;
                return 0.0f;
            }
        } else {
            offset2 = offset;
        }
        int start = this.bp;
        float value = Float.parseFloat(subString(start, ((this.bp + offset2) - start) - 1));
        if (chLocal == seperator) {
            this.bp += offset2 - 1;
            next();
            this.matchStat = 3;
            this.token = 16;
            return value;
        }
        this.matchStat = -1;
        return value;
    }

    /* JADX INFO: Multiple debug info for r4v8 double: [D('count' int), D('value' double)] */
    public final double scanFieldDouble(char[] fieldName) {
        int offset;
        char chLocal;
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return 0.0d;
        }
        int offset2 = fieldName.length;
        int offset3 = offset2 + 1;
        char chLocal2 = charAt(this.bp + offset2);
        if (chLocal2 < '0' || chLocal2 > '9') {
            this.matchStat = -1;
            return 0.0d;
        }
        while (true) {
            offset = offset3 + 1;
            chLocal = charAt(this.bp + offset3);
            if (chLocal < '0' || chLocal > '9') {
                break;
            }
            offset3 = offset;
        }
        if (chLocal == '.') {
            int offset4 = offset + 1;
            char chLocal3 = charAt(this.bp + offset);
            if (chLocal3 >= '0' && chLocal3 <= '9') {
                while (true) {
                    offset = offset4 + 1;
                    chLocal = charAt(this.bp + offset4);
                    if (chLocal < '0' || chLocal > '9') {
                        break;
                    }
                    offset4 = offset;
                }
            } else {
                this.matchStat = -1;
                return 0.0d;
            }
        }
        if (chLocal == 'e' || chLocal == 'E') {
            int offset5 = offset + 1;
            chLocal = charAt(this.bp + offset);
            if (chLocal == '+' || chLocal == '-') {
                offset = offset5 + 1;
                chLocal = charAt(this.bp + offset5);
            } else {
                offset = offset5;
            }
            while (chLocal >= '0' && chLocal <= '9') {
                chLocal = charAt(this.bp + offset);
                offset++;
            }
        }
        int i = this.bp;
        int start = fieldName.length + i;
        double value = Double.parseDouble(subString(start, ((i + offset) - start) - 1));
        if (chLocal == ',') {
            this.bp += offset - 1;
            next();
            this.matchStat = 3;
            this.token = 16;
            return value;
        } else if (chLocal == '}') {
            int offset6 = offset + 1;
            char chLocal4 = charAt(this.bp + offset);
            if (chLocal4 == ',') {
                this.token = 16;
                this.bp += offset6 - 1;
                next();
            } else if (chLocal4 == ']') {
                this.token = 15;
                this.bp += offset6 - 1;
                next();
            } else if (chLocal4 == '}') {
                this.token = 13;
                this.bp += offset6 - 1;
                next();
            } else if (chLocal4 == 26) {
                this.token = 20;
                this.bp += offset6 - 1;
                this.ch = 26;
            } else {
                this.matchStat = -1;
                return 0.0d;
            }
            this.matchStat = 4;
            return value;
        } else {
            this.matchStat = -1;
            return 0.0d;
        }
    }

    /* JADX INFO: Multiple debug info for r4v4 double: [D('count' int), D('value' double)] */
    public final double scanFieldDouble(char seperator) {
        int offset;
        char chLocal;
        int offset2;
        this.matchStat = 0;
        int offset3 = 0 + 1;
        char chLocal2 = charAt(this.bp + 0);
        if (chLocal2 < '0' || chLocal2 > '9') {
            this.matchStat = -1;
            return 0.0d;
        }
        while (true) {
            offset = offset3 + 1;
            chLocal = charAt(this.bp + offset3);
            if (chLocal < '0' || chLocal > '9') {
                break;
            }
            offset3 = offset;
        }
        if (chLocal == '.') {
            int offset4 = offset + 1;
            char chLocal3 = charAt(this.bp + offset);
            if (chLocal3 >= '0' && chLocal3 <= '9') {
                while (true) {
                    offset2 = offset4 + 1;
                    chLocal = charAt(this.bp + offset4);
                    if (chLocal < '0' || chLocal > '9') {
                        break;
                    }
                    offset4 = offset2;
                }
            } else {
                this.matchStat = -1;
                return 0.0d;
            }
        } else {
            offset2 = offset;
        }
        if (chLocal == 'e' || chLocal == 'E') {
            int offset5 = offset2 + 1;
            chLocal = charAt(this.bp + offset2);
            if (chLocal == '+' || chLocal == '-') {
                offset2 = offset5 + 1;
                chLocal = charAt(this.bp + offset5);
            } else {
                offset2 = offset5;
            }
            while (chLocal >= '0' && chLocal <= '9') {
                chLocal = charAt(this.bp + offset2);
                offset2++;
            }
        }
        int start = this.bp;
        double value = Double.parseDouble(subString(start, ((this.bp + offset2) - start) - 1));
        if (chLocal == seperator) {
            this.bp += offset2 - 1;
            next();
            this.matchStat = 3;
            this.token = 16;
            return value;
        }
        this.matchStat = -1;
        return value;
    }

    public final void scanTrue() {
        if (this.ch == 't') {
            next();
            if (this.ch == 'r') {
                next();
                if (this.ch == 'u') {
                    next();
                    if (this.ch == 'e') {
                        next();
                        char c = this.ch;
                        if (c == ' ' || c == ',' || c == '}' || c == ']' || c == '\n' || c == '\r' || c == '\t' || c == 26 || c == '\f' || c == '\b' || c == ':') {
                            this.token = 6;
                            return;
                        }
                        throw new JSONException("scan true error");
                    }
                    throw new JSONException("error parse true");
                }
                throw new JSONException("error parse true");
            }
            throw new JSONException("error parse true");
        }
        throw new JSONException("error parse true");
    }

    public final void scanTreeSet() {
        if (this.ch == 'T') {
            next();
            if (this.ch == 'r') {
                next();
                if (this.ch == 'e') {
                    next();
                    if (this.ch == 'e') {
                        next();
                        if (this.ch == 'S') {
                            next();
                            if (this.ch == 'e') {
                                next();
                                if (this.ch == 't') {
                                    next();
                                    char c = this.ch;
                                    if (c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == '\f' || c == '\b' || c == '[' || c == '(') {
                                        this.token = 22;
                                        return;
                                    }
                                    throw new JSONException("scan set error");
                                }
                                throw new JSONException("error parse true");
                            }
                            throw new JSONException("error parse true");
                        }
                        throw new JSONException("error parse true");
                    }
                    throw new JSONException("error parse true");
                }
                throw new JSONException("error parse true");
            }
            throw new JSONException("error parse true");
        }
        throw new JSONException("error parse true");
    }

    public final void scanNullOrNew() {
        if (this.ch == 'n') {
            next();
            char c = this.ch;
            if (c == 'u') {
                next();
                if (this.ch == 'l') {
                    next();
                    if (this.ch == 'l') {
                        next();
                        char c2 = this.ch;
                        if (c2 == ' ' || c2 == ',' || c2 == '}' || c2 == ']' || c2 == '\n' || c2 == '\r' || c2 == '\t' || c2 == 26 || c2 == '\f' || c2 == '\b') {
                            this.token = 8;
                            return;
                        }
                        throw new JSONException("scan true error");
                    }
                    throw new JSONException("error parse l");
                }
                throw new JSONException("error parse l");
            } else if (c == 'e') {
                next();
                if (this.ch == 'w') {
                    next();
                    char c3 = this.ch;
                    if (c3 == ' ' || c3 == ',' || c3 == '}' || c3 == ']' || c3 == '\n' || c3 == '\r' || c3 == '\t' || c3 == 26 || c3 == '\f' || c3 == '\b') {
                        this.token = 9;
                        return;
                    }
                    throw new JSONException("scan true error");
                }
                throw new JSONException("error parse w");
            } else {
                throw new JSONException("error parse e");
            }
        } else {
            throw new JSONException("error parse null or new");
        }
    }

    public final void scanNULL() {
        if (this.ch == 'N') {
            next();
            if (this.ch == 'U') {
                next();
                if (this.ch == 'L') {
                    next();
                    if (this.ch == 'L') {
                        next();
                        char c = this.ch;
                        if (c == ' ' || c == ',' || c == '}' || c == ']' || c == '\n' || c == '\r' || c == '\t' || c == 26 || c == '\f' || c == '\b') {
                            this.token = 8;
                            return;
                        }
                        throw new JSONException("scan NULL error");
                    }
                    throw new JSONException("error parse NULL");
                }
                throw new JSONException("error parse U");
            }
            return;
        }
        throw new JSONException("error parse NULL");
    }

    public final void scanUndefined() {
        if (this.ch == 'u') {
            next();
            if (this.ch == 'n') {
                next();
                if (this.ch == 'd') {
                    next();
                    if (this.ch == 'e') {
                        next();
                        if (this.ch == 'f') {
                            next();
                            if (this.ch == 'i') {
                                next();
                                if (this.ch == 'n') {
                                    next();
                                    if (this.ch == 'e') {
                                        next();
                                        if (this.ch == 'd') {
                                            next();
                                            char c = this.ch;
                                            if (c == ' ' || c == ',' || c == '}' || c == ']' || c == '\n' || c == '\r' || c == '\t' || c == 26 || c == '\f' || c == '\b') {
                                                this.token = 23;
                                                return;
                                            }
                                            throw new JSONException("scan false error");
                                        }
                                        throw new JSONException("error parse false");
                                    }
                                    throw new JSONException("error parse false");
                                }
                                throw new JSONException("error parse false");
                            }
                            throw new JSONException("error parse false");
                        }
                        throw new JSONException("error parse false");
                    }
                    throw new JSONException("error parse false");
                }
                throw new JSONException("error parse false");
            }
            throw new JSONException("error parse false");
        }
        throw new JSONException("error parse false");
    }

    public final void scanFalse() {
        if (this.ch == 'f') {
            next();
            if (this.ch == 'a') {
                next();
                if (this.ch == 'l') {
                    next();
                    if (this.ch == 's') {
                        next();
                        if (this.ch == 'e') {
                            next();
                            char c = this.ch;
                            if (c == ' ' || c == ',' || c == '}' || c == ']' || c == '\n' || c == '\r' || c == '\t' || c == 26 || c == '\f' || c == '\b' || c == ':') {
                                this.token = 7;
                                return;
                            }
                            throw new JSONException("scan false error");
                        }
                        throw new JSONException("error parse false");
                    }
                    throw new JSONException("error parse false");
                }
                throw new JSONException("error parse false");
            }
            throw new JSONException("error parse false");
        }
        throw new JSONException("error parse false");
    }

    public final void scanIdent() {
        this.np = this.bp - 1;
        this.hasSpecial = false;
        do {
            this.sp++;
            next();
        } while (Character.isLetterOrDigit(this.ch));
        Integer tok = this.keywods.get(stringVal());
        if (tok != null) {
            this.token = tok.intValue();
        } else {
            this.token = 18;
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final boolean isBlankInput() {
        int i = 0;
        while (true) {
            char chLocal = charAt(i);
            if (chLocal == 26) {
                return true;
            }
            if (!isWhitespace(chLocal)) {
                return false;
            }
            i++;
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void skipWhitespace() {
        while (this.ch < IOUtils.whitespaceFlags.length && IOUtils.whitespaceFlags[this.ch]) {
            next();
        }
    }

    private final void scanStringSingleQuote() {
        this.np = this.bp;
        this.hasSpecial = false;
        char c2 = 0;
        char c3 = 0;
        int val = 0;
        while (true) {
            char chLocal = next();
            if (chLocal == '\'') {
                this.token = 4;
                next();
                return;
            } else if (chLocal == 26) {
                throw new JSONException("unclosed single-quote string");
            } else if (chLocal == '\\') {
                if (!this.hasSpecial) {
                    this.hasSpecial = true;
                    int i = this.sp;
                    char[] cArr = this.sbuf;
                    if (i > cArr.length) {
                        char[] newsbuf = new char[(i * 2)];
                        System.arraycopy(cArr, 0, newsbuf, 0, cArr.length);
                        this.sbuf = newsbuf;
                    }
                    copyTo(this.np + 1, this.sp, this.sbuf);
                }
                char chLocal2 = next();
                if (chLocal2 == '\"') {
                    putChar('\"');
                } else if (chLocal2 != '\'') {
                    if (chLocal2 != 'F') {
                        if (chLocal2 == '\\') {
                            putChar('\\');
                        } else if (chLocal2 == 'b') {
                            putChar('\b');
                        } else if (chLocal2 != 'f') {
                            if (chLocal2 == 'n') {
                                putChar('\n');
                            } else if (chLocal2 == 'r') {
                                putChar('\r');
                            } else if (chLocal2 != 'x') {
                                switch (chLocal2) {
                                    case '/':
                                        putChar('/');
                                        continue;
                                    case '0':
                                        putChar(0);
                                        continue;
                                    case '1':
                                        putChar(1);
                                        continue;
                                    case '2':
                                        putChar(2);
                                        continue;
                                    case '3':
                                        putChar(3);
                                        continue;
                                    case '4':
                                        putChar(4);
                                        continue;
                                    case '5':
                                        putChar(5);
                                        continue;
                                    case '6':
                                        putChar(6);
                                        continue;
                                    case '7':
                                        putChar(7);
                                        continue;
                                    default:
                                        switch (chLocal2) {
                                            case 't':
                                                putChar('\t');
                                                continue;
                                            case 'u':
                                                putChar((char) Integer.parseInt(new String(new char[]{next(), next(), next(), next()}), 16));
                                                c2 = c2;
                                                val = val;
                                                c3 = c3;
                                                continue;
                                            case 'v':
                                                putChar(11);
                                                continue;
                                                continue;
                                            default:
                                                this.ch = chLocal2;
                                                throw new JSONException("unclosed single-quote string");
                                        }
                                }
                            } else {
                                char x1 = next();
                                char x2 = next();
                                int[] iArr = digits;
                                val = (iArr[x1] * 16) + iArr[x2];
                                putChar((char) val);
                                c2 = x1;
                                c3 = x2;
                            }
                        }
                    }
                    putChar('\f');
                } else {
                    putChar('\'');
                }
            } else if (!this.hasSpecial) {
                this.sp++;
            } else {
                int i2 = this.sp;
                char[] cArr2 = this.sbuf;
                if (i2 == cArr2.length) {
                    putChar(chLocal);
                } else {
                    this.sp = i2 + 1;
                    cArr2[i2] = chLocal;
                }
            }
        }
    }

    public final void scanSet() {
        if (this.ch == 'S') {
            next();
            if (this.ch == 'e') {
                next();
                if (this.ch == 't') {
                    next();
                    char c = this.ch;
                    if (c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == '\f' || c == '\b' || c == '[' || c == '(') {
                        this.token = 21;
                        return;
                    }
                    throw new JSONException("scan set error");
                }
                throw new JSONException("error parse true");
            }
            throw new JSONException("error parse true");
        }
        throw new JSONException("error parse true");
    }

    /* access modifiers changed from: protected */
    public final void putChar(char ch2) {
        int i = this.sp;
        char[] cArr = this.sbuf;
        if (i == cArr.length) {
            char[] newsbuf = new char[(cArr.length * 2)];
            System.arraycopy(cArr, 0, newsbuf, 0, cArr.length);
            this.sbuf = newsbuf;
        }
        char[] newsbuf2 = this.sbuf;
        int i2 = this.sp;
        this.sp = i2 + 1;
        newsbuf2[i2] = ch2;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void scanNumber() {
        this.np = this.bp;
        if (this.ch == '-') {
            this.sp++;
            next();
        }
        while (true) {
            char c = this.ch;
            if (c < '0' || c > '9') {
                break;
            }
            this.sp++;
            next();
        }
        boolean isDouble = false;
        if (this.ch == '.') {
            this.sp++;
            next();
            isDouble = true;
            while (true) {
                char c2 = this.ch;
                if (c2 < '0' || c2 > '9') {
                    break;
                }
                this.sp++;
                next();
            }
        }
        char c3 = this.ch;
        if (c3 == 'L') {
            this.sp++;
            next();
        } else if (c3 == 'S') {
            this.sp++;
            next();
        } else if (c3 == 'B') {
            this.sp++;
            next();
        } else if (c3 == 'F') {
            this.sp++;
            next();
            isDouble = true;
        } else if (c3 == 'D') {
            this.sp++;
            next();
            isDouble = true;
        } else if (c3 == 'e' || c3 == 'E') {
            this.sp++;
            next();
            char c4 = this.ch;
            if (c4 == '+' || c4 == '-') {
                this.sp++;
                next();
            }
            while (true) {
                char c5 = this.ch;
                if (c5 < '0' || c5 > '9') {
                    break;
                }
                this.sp++;
                next();
            }
            char c6 = this.ch;
            if (c6 == 'D' || c6 == 'F') {
                this.sp++;
                next();
            }
            isDouble = true;
        }
        if (isDouble) {
            this.token = 3;
        } else {
            this.token = 2;
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final long longValue() throws NumberFormatException {
        long limit;
        int i;
        long result = 0;
        boolean negative = false;
        int i2 = this.np;
        int i3 = this.np;
        int max = this.sp + i3;
        if (charAt(i3) == '-') {
            negative = true;
            limit = Long.MIN_VALUE;
            i2++;
        } else {
            limit = -9223372036854775807L;
        }
        if (i2 < max) {
            result = (long) (-digits[charAt(i2)]);
            i2++;
        }
        while (true) {
            if (i2 >= max) {
                break;
            }
            i = i2 + 1;
            char chLocal = charAt(i2);
            if (chLocal == 'L' || chLocal == 'S' || chLocal == 'B') {
                break;
            }
            int digit = digits[chLocal];
            if (result >= -922337203685477580L) {
                long result2 = result * 10;
                if (result2 >= ((long) digit) + limit) {
                    result = result2 - ((long) digit);
                    i2 = i;
                } else {
                    throw new NumberFormatException(numberString());
                }
            } else {
                throw new NumberFormatException(numberString());
            }
        }
        i2 = i;
        if (!negative) {
            return -result;
        }
        if (i2 > this.np + 1) {
            return result;
        }
        throw new NumberFormatException(numberString());
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final Number decimalValue(boolean decimal) {
        char chLocal = charAt((this.np + this.sp) - 1);
        if (chLocal == 'F') {
            return Float.valueOf(Float.parseFloat(numberString()));
        }
        if (chLocal == 'D') {
            return Double.valueOf(Double.parseDouble(numberString()));
        }
        if (decimal) {
            return decimalValue();
        }
        return Double.valueOf(doubleValue());
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final BigDecimal decimalValue() {
        return new BigDecimal(numberString());
    }

    public static final boolean isWhitespace(char ch2) {
        return ch2 == ' ' || ch2 == '\n' || ch2 == '\r' || ch2 == '\t' || ch2 == '\f' || ch2 == '\b';
    }
}

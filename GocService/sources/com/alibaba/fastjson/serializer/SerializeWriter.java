package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.util.Base64;
import com.alibaba.fastjson.util.IOUtils;
import com.goodocom.bttek.BuildConfig;
import com.goodocom.bttek.bt.res.NfDef;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.lang.ref.SoftReference;
import java.math.BigDecimal;
import java.nio.charset.Charset;

public final class SerializeWriter extends Writer {
    private static final ThreadLocal<SoftReference<char[]>> bufLocal = new ThreadLocal<>();
    protected char[] buf;
    protected int count;
    private int features;
    private final Writer writer;

    public SerializeWriter() {
        this((Writer) null);
    }

    public SerializeWriter(Writer writer2) {
        this.writer = writer2;
        this.features = JSON.DEFAULT_GENERATE_FEATURE;
        SoftReference<char[]> ref = bufLocal.get();
        if (ref != null) {
            this.buf = ref.get();
            bufLocal.set(null);
        }
        if (this.buf == null) {
            this.buf = new char[1024];
        }
    }

    public SerializeWriter(SerializerFeature... features2) {
        this((Writer) null, features2);
    }

    public SerializeWriter(Writer writer2, SerializerFeature... features2) {
        this.writer = writer2;
        SoftReference<char[]> ref = bufLocal.get();
        if (ref != null) {
            this.buf = ref.get();
            bufLocal.set(null);
        }
        if (this.buf == null) {
            this.buf = new char[1024];
        }
        int featuresValue = 0;
        for (SerializerFeature feature : features2) {
            featuresValue |= feature.getMask();
        }
        this.features = featuresValue;
    }

    public int getBufferLength() {
        return this.buf.length;
    }

    public SerializeWriter(int initialSize) {
        this((Writer) null, initialSize);
    }

    public SerializeWriter(Writer writer2, int initialSize) {
        this.writer = writer2;
        if (initialSize > 0) {
            this.buf = new char[initialSize];
            return;
        }
        throw new IllegalArgumentException("Negative initial size: " + initialSize);
    }

    public void config(SerializerFeature feature, boolean state) {
        if (state) {
            this.features |= feature.getMask();
        } else {
            this.features &= ~feature.getMask();
        }
    }

    public boolean isEnabled(SerializerFeature feature) {
        return SerializerFeature.isEnabled(this.features, feature);
    }

    @Override // java.io.Writer
    public void write(int c) {
        int newcount = this.count + 1;
        if (newcount > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(newcount);
            } else {
                flush();
                newcount = 1;
            }
        }
        this.buf[this.count] = (char) c;
        this.count = newcount;
    }

    public void write(char c) {
        int newcount = this.count + 1;
        if (newcount > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(newcount);
            } else {
                flush();
                newcount = 1;
            }
        }
        this.buf[this.count] = c;
        this.count = newcount;
    }

    @Override // java.io.Writer
    public void write(char[] c, int off, int len) {
        if (off < 0 || off > c.length || len < 0 || off + len > c.length || off + len < 0) {
            throw new IndexOutOfBoundsException();
        } else if (len != 0) {
            int newcount = this.count + len;
            if (newcount > this.buf.length) {
                if (this.writer == null) {
                    expandCapacity(newcount);
                } else {
                    do {
                        char[] cArr = this.buf;
                        int length = cArr.length;
                        int i = this.count;
                        int rest = length - i;
                        System.arraycopy(c, off, cArr, i, rest);
                        this.count = this.buf.length;
                        flush();
                        len -= rest;
                        off += rest;
                    } while (len > this.buf.length);
                    newcount = len;
                }
            }
            System.arraycopy(c, off, this.buf, this.count, len);
            this.count = newcount;
        }
    }

    public void expandCapacity(int minimumCapacity) {
        int newCapacity = ((this.buf.length * 3) / 2) + 1;
        if (newCapacity < minimumCapacity) {
            newCapacity = minimumCapacity;
        }
        char[] newValue = new char[newCapacity];
        System.arraycopy(this.buf, 0, newValue, 0, this.count);
        this.buf = newValue;
    }

    @Override // java.io.Writer
    public void write(String str, int off, int len) {
        int newcount = this.count + len;
        if (newcount > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(newcount);
            } else {
                do {
                    char[] cArr = this.buf;
                    int length = cArr.length;
                    int i = this.count;
                    int rest = length - i;
                    str.getChars(off, off + rest, cArr, i);
                    this.count = this.buf.length;
                    flush();
                    len -= rest;
                    off += rest;
                } while (len > this.buf.length);
                newcount = len;
            }
        }
        str.getChars(off, off + len, this.buf, this.count);
        this.count = newcount;
    }

    public void writeTo(Writer out) throws IOException {
        if (this.writer == null) {
            out.write(this.buf, 0, this.count);
            return;
        }
        throw new UnsupportedOperationException("writer not null");
    }

    public void writeTo(OutputStream out, String charsetName) throws IOException {
        writeTo(out, Charset.forName(charsetName));
    }

    public void writeTo(OutputStream out, Charset charset) throws IOException {
        if (this.writer == null) {
            out.write(new String(this.buf, 0, this.count).getBytes(charset));
            return;
        }
        throw new UnsupportedOperationException("writer not null");
    }

    @Override // java.io.Writer, java.lang.Appendable
    public SerializeWriter append(CharSequence csq) {
        String s = csq == null ? "null" : csq.toString();
        write(s, 0, s.length());
        return this;
    }

    @Override // java.io.Writer, java.lang.Appendable
    public SerializeWriter append(CharSequence csq, int start, int end) {
        String s = (csq == null ? "null" : csq).subSequence(start, end).toString();
        write(s, 0, s.length());
        return this;
    }

    @Override // java.io.Writer, java.lang.Appendable
    public SerializeWriter append(char c) {
        write(c);
        return this;
    }

    public void reset() {
        this.count = 0;
    }

    public char[] toCharArray() {
        if (this.writer == null) {
            int i = this.count;
            char[] newValue = new char[i];
            System.arraycopy(this.buf, 0, newValue, 0, i);
            return newValue;
        }
        throw new UnsupportedOperationException("writer not null");
    }

    public byte[] toBytes(String charsetName) {
        if (this.writer == null) {
            if (charsetName == null) {
                charsetName = "UTF-8";
            }
            return new SerialWriterStringEncoder(Charset.forName(charsetName)).encode(this.buf, 0, this.count);
        }
        throw new UnsupportedOperationException("writer not null");
    }

    public int size() {
        return this.count;
    }

    @Override // java.lang.Object
    public String toString() {
        return new String(this.buf, 0, this.count);
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        if (this.writer != null && this.count > 0) {
            flush();
        }
        char[] cArr = this.buf;
        if (cArr.length <= 8192) {
            bufLocal.set(new SoftReference<>(cArr));
        }
        this.buf = null;
    }

    @Override // java.io.Writer
    public void write(String text) {
        if (text == null) {
            writeNull();
        } else {
            write(text, 0, text.length());
        }
    }

    public void writeInt(int i) {
        if (i == Integer.MIN_VALUE) {
            write("-2147483648");
            return;
        }
        int size = i < 0 ? IOUtils.stringSize(-i) + 1 : IOUtils.stringSize(i);
        int newcount = this.count + size;
        if (newcount > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(newcount);
            } else {
                char[] chars = new char[size];
                IOUtils.getChars(i, size, chars);
                write(chars, 0, chars.length);
                return;
            }
        }
        IOUtils.getChars(i, newcount, this.buf);
        this.count = newcount;
    }

    /* JADX INFO: Multiple debug info for r5v0 char[]: [D('emptyString' java.lang.String), D('CA' char[])] */
    public void writeByteArray(byte[] bytes) {
        int bytesLen = bytes.length;
        boolean singleQuote = isEnabled(SerializerFeature.UseSingleQuotes);
        char quote = singleQuote ? '\'' : '\"';
        if (bytesLen == 0) {
            write(singleQuote ? "''" : "\"\"");
            return;
        }
        char[] CA = Base64.CA;
        int eLen = (bytesLen / 3) * 3;
        int offset = this.count;
        int newcount = this.count + ((((bytesLen - 1) / 3) + 1) << 2) + 2;
        if (newcount > this.buf.length) {
            if (this.writer != null) {
                write(quote);
                int i = 0;
                while (i < eLen) {
                    int s = i + 1;
                    int s2 = s + 1;
                    int i2 = ((bytes[i] & NfDef.AVRCP_PLAYING_STATUS_ID_ERROR) << 16) | ((bytes[s] & NfDef.AVRCP_PLAYING_STATUS_ID_ERROR) << 8) | (bytes[s2] & NfDef.AVRCP_PLAYING_STATUS_ID_ERROR);
                    write(CA[(i2 >>> 18) & 63]);
                    write(CA[(i2 >>> 12) & 63]);
                    write(CA[(i2 >>> 6) & 63]);
                    write(CA[i2 & 63]);
                    i = s2 + 1;
                }
                int left = bytesLen - eLen;
                if (left > 0) {
                    int i3 = (left == 2 ? (bytes[bytesLen - 1] & NfDef.AVRCP_PLAYING_STATUS_ID_ERROR) << 2 : 0) | ((bytes[eLen] & NfDef.AVRCP_PLAYING_STATUS_ID_ERROR) << 10);
                    write(CA[i3 >> 12]);
                    write(CA[(i3 >>> 6) & 63]);
                    write(left == 2 ? CA[i3 & 63] : '=');
                    write('=');
                }
                write(quote);
                return;
            }
            expandCapacity(newcount);
        }
        this.count = newcount;
        int offset2 = offset + 1;
        this.buf[offset] = quote;
        int i4 = 0;
        int d = offset2;
        while (i4 < eLen) {
            int s3 = i4 + 1;
            int s4 = s3 + 1;
            int i5 = ((bytes[i4] & NfDef.AVRCP_PLAYING_STATUS_ID_ERROR) << 16) | ((bytes[s3] & NfDef.AVRCP_PLAYING_STATUS_ID_ERROR) << 8);
            int s5 = s4 + 1;
            int i6 = i5 | (bytes[s4] & NfDef.AVRCP_PLAYING_STATUS_ID_ERROR);
            char[] cArr = this.buf;
            int d2 = d + 1;
            cArr[d] = CA[(i6 >>> 18) & 63];
            int d3 = d2 + 1;
            cArr[d2] = CA[(i6 >>> 12) & 63];
            int d4 = d3 + 1;
            cArr[d3] = CA[(i6 >>> 6) & 63];
            d = d4 + 1;
            cArr[d4] = CA[i6 & 63];
            i4 = s5;
        }
        int left2 = bytesLen - eLen;
        if (left2 > 0) {
            int i7 = ((bytes[eLen] & NfDef.AVRCP_PLAYING_STATUS_ID_ERROR) << 10) | (left2 == 2 ? (bytes[bytesLen - 1] & NfDef.AVRCP_PLAYING_STATUS_ID_ERROR) << 2 : 0);
            char[] cArr2 = this.buf;
            cArr2[newcount - 5] = CA[i7 >> 12];
            cArr2[newcount - 4] = CA[(i7 >>> 6) & 63];
            cArr2[newcount - 3] = left2 == 2 ? CA[i7 & 63] : '=';
            this.buf[newcount - 2] = '=';
        }
        this.buf[newcount - 1] = quote;
    }

    public void writeFloatAndChar(float value, char c) {
        String text = Float.toString(value);
        if (text.endsWith(".0")) {
            text = text.substring(0, text.length() - 2);
        }
        write(text);
        write(c);
    }

    public void writeDoubleAndChar(double value, char c) {
        String text = Double.toString(value);
        if (text.endsWith(".0")) {
            text = text.substring(0, text.length() - 2);
        }
        write(text);
        write(c);
    }

    public void writeBooleanAndChar(boolean value, char c) {
        if (value) {
            if (c == ',') {
                write("true,");
            } else if (c == ']') {
                write("true]");
            } else {
                write("true");
                write(c);
            }
        } else if (c == ',') {
            write("false,");
        } else if (c == ']') {
            write("false]");
        } else {
            write("false");
            write(c);
        }
    }

    public void writeCharacterAndChar(char value, char c) {
        writeString(Character.toString(value));
        write(c);
    }

    public void writeEnum(Enum<?> value, char c) {
        if (value == null) {
            writeNull();
            write(',');
        } else if (!isEnabled(SerializerFeature.WriteEnumUsingToString)) {
            writeIntAndChar(value.ordinal(), c);
        } else if (isEnabled(SerializerFeature.UseSingleQuotes)) {
            write('\'');
            write(value.name());
            write('\'');
            write(c);
        } else {
            write('\"');
            write(value.name());
            write('\"');
            write(c);
        }
    }

    public void writeIntAndChar(int i, char c) {
        if (i == Integer.MIN_VALUE) {
            write("-2147483648");
            write(c);
            return;
        }
        int newcount0 = this.count + (i < 0 ? IOUtils.stringSize(-i) + 1 : IOUtils.stringSize(i));
        int newcount1 = newcount0 + 1;
        if (newcount1 > this.buf.length) {
            if (this.writer != null) {
                writeInt(i);
                write(c);
                return;
            }
            expandCapacity(newcount1);
        }
        IOUtils.getChars(i, newcount0, this.buf);
        this.buf[newcount0] = c;
        this.count = newcount1;
    }

    public void writeLongAndChar(long i, char c) throws IOException {
        if (i == Long.MIN_VALUE) {
            write("-9223372036854775808");
            write(c);
            return;
        }
        int newcount0 = this.count + (i < 0 ? IOUtils.stringSize(-i) + 1 : IOUtils.stringSize(i));
        int newcount1 = newcount0 + 1;
        if (newcount1 > this.buf.length) {
            if (this.writer != null) {
                writeLong(i);
                write(c);
                return;
            }
            expandCapacity(newcount1);
        }
        IOUtils.getChars(i, newcount0, this.buf);
        this.buf[newcount0] = c;
        this.count = newcount1;
    }

    public void writeLong(long i) {
        if (i == Long.MIN_VALUE) {
            write("-9223372036854775808");
            return;
        }
        int size = i < 0 ? IOUtils.stringSize(-i) + 1 : IOUtils.stringSize(i);
        int newcount = this.count + size;
        if (newcount > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(newcount);
            } else {
                char[] chars = new char[size];
                IOUtils.getChars(i, size, chars);
                write(chars, 0, chars.length);
                return;
            }
        }
        IOUtils.getChars(i, newcount, this.buf);
        this.count = newcount;
    }

    public void writeNull() {
        write("null");
    }

    private void writeStringWithDoubleQuote(String text, char seperator) {
        writeStringWithDoubleQuote(text, seperator, true);
    }

    /* JADX INFO: Multiple debug info for r3v5 int: [D('len' int), D('srcPos' int)] */
    private void writeStringWithDoubleQuote(String text, char seperator, boolean checkSpecial) {
        if (text == null) {
            writeNull();
            if (seperator != 0) {
                write(seperator);
                return;
            }
            return;
        }
        int len = text.length();
        int newcount = this.count + len + 2;
        if (seperator != 0) {
            newcount++;
        }
        char c = '/';
        char c2 = '\f';
        char c3 = '\b';
        if (newcount > this.buf.length) {
            if (this.writer != null) {
                write('\"');
                for (int i = 0; i < text.length(); i++) {
                    char ch = text.charAt(i);
                    if (isEnabled(SerializerFeature.BrowserCompatible)) {
                        if (ch == '\b' || ch == '\f' || ch == '\n' || ch == '\r' || ch == '\t' || ch == '\"' || ch == '/' || ch == '\\') {
                            write('\\');
                            write(IOUtils.replaceChars[ch]);
                        } else {
                            if (ch < ' ') {
                                write('\\');
                                write('u');
                                write('0');
                                write('0');
                                write(IOUtils.ASCII_CHARS[ch * 2]);
                                write(IOUtils.ASCII_CHARS[(ch * 2) + 1]);
                            } else if (ch >= 127) {
                                write('\\');
                                write('u');
                                write(IOUtils.DIGITS[(ch >>> '\f') & 15]);
                                write(IOUtils.DIGITS[(ch >>> '\b') & 15]);
                                write(IOUtils.DIGITS[(ch >>> 4) & 15]);
                                write(IOUtils.DIGITS[ch & 15]);
                            }
                        }
                    } else if ((ch < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[ch] != 0) || (ch == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                        write('\\');
                        write(IOUtils.replaceChars[ch]);
                    }
                    write(ch);
                }
                write('\"');
                if (seperator != 0) {
                    write(seperator);
                    return;
                }
                return;
            }
            expandCapacity(newcount);
        }
        int i2 = this.count;
        int start = i2 + 1;
        int end = start + len;
        char[] cArr = this.buf;
        cArr[i2] = '\"';
        text.getChars(0, len, cArr, start);
        this.count = newcount;
        if (isEnabled(SerializerFeature.BrowserCompatible)) {
            int lastSpecialIndex = -1;
            for (int i3 = start; i3 < end; i3++) {
                char ch2 = this.buf[i3];
                if (ch2 == '\"' || ch2 == '/' || ch2 == '\\') {
                    lastSpecialIndex = i3;
                    newcount++;
                } else if (ch2 == '\b' || ch2 == '\f' || ch2 == '\n' || ch2 == '\r' || ch2 == '\t') {
                    lastSpecialIndex = i3;
                    newcount++;
                } else if (ch2 < ' ') {
                    lastSpecialIndex = i3;
                    newcount += 5;
                } else if (ch2 >= 127) {
                    lastSpecialIndex = i3;
                    newcount += 5;
                }
            }
            if (newcount > this.buf.length) {
                expandCapacity(newcount);
            }
            this.count = newcount;
            int i4 = lastSpecialIndex;
            while (i4 >= start) {
                char[] cArr2 = this.buf;
                char ch3 = cArr2[i4];
                if (ch3 == c3 || ch3 == c2 || ch3 == '\n' || ch3 == '\r' || ch3 == '\t') {
                    char[] cArr3 = this.buf;
                    System.arraycopy(cArr3, i4 + 1, cArr3, i4 + 2, (end - i4) - 1);
                    char[] cArr4 = this.buf;
                    cArr4[i4] = '\\';
                    cArr4[i4 + 1] = IOUtils.replaceChars[ch3];
                    end++;
                } else if (ch3 == '\"' || ch3 == c || ch3 == '\\') {
                    char[] cArr5 = this.buf;
                    System.arraycopy(cArr5, i4 + 1, cArr5, i4 + 2, (end - i4) - 1);
                    char[] cArr6 = this.buf;
                    cArr6[i4] = '\\';
                    cArr6[i4 + 1] = ch3;
                    end++;
                } else if (ch3 < ' ') {
                    System.arraycopy(cArr2, i4 + 1, cArr2, i4 + 6, (end - i4) - 1);
                    char[] cArr7 = this.buf;
                    cArr7[i4] = '\\';
                    cArr7[i4 + 1] = 'u';
                    cArr7[i4 + 2] = '0';
                    cArr7[i4 + 3] = '0';
                    cArr7[i4 + 4] = IOUtils.ASCII_CHARS[ch3 * 2];
                    this.buf[i4 + 5] = IOUtils.ASCII_CHARS[(ch3 * 2) + 1];
                    end += 5;
                } else if (ch3 >= 127) {
                    System.arraycopy(cArr2, i4 + 1, cArr2, i4 + 6, (end - i4) - 1);
                    char[] cArr8 = this.buf;
                    cArr8[i4] = '\\';
                    cArr8[i4 + 1] = 'u';
                    cArr8[i4 + 2] = IOUtils.DIGITS[(ch3 >>> '\f') & 15];
                    this.buf[i4 + 3] = IOUtils.DIGITS[(ch3 >>> '\b') & 15];
                    this.buf[i4 + 4] = IOUtils.DIGITS[(ch3 >>> 4) & 15];
                    this.buf[i4 + 5] = IOUtils.DIGITS[ch3 & 15];
                    end += 5;
                }
                i4--;
                c = '/';
                c2 = '\f';
                c3 = '\b';
            }
            if (seperator != 0) {
                char[] cArr9 = this.buf;
                int i5 = this.count;
                cArr9[i5 - 2] = '\"';
                cArr9[i5 - 1] = seperator;
                return;
            }
            this.buf[this.count - 1] = '\"';
            return;
        }
        int specialCount = 0;
        int lastSpecialIndex2 = -1;
        int firstSpecialIndex = -1;
        char lastSpecial = 0;
        if (checkSpecial) {
            for (int i6 = start; i6 < end; i6++) {
                char ch4 = this.buf[i6];
                if (ch4 == 8232) {
                    specialCount++;
                    lastSpecialIndex2 = i6;
                    lastSpecial = ch4;
                    newcount += 4;
                    if (firstSpecialIndex == -1) {
                        firstSpecialIndex = i6;
                    }
                } else if (ch4 >= ']') {
                    if (ch4 >= 127 && ch4 <= 160) {
                        if (firstSpecialIndex == -1) {
                            firstSpecialIndex = i6;
                        }
                        specialCount++;
                        lastSpecialIndex2 = i6;
                        lastSpecial = ch4;
                        newcount += 4;
                    }
                } else if (isSpecial(ch4, this.features)) {
                    specialCount++;
                    lastSpecialIndex2 = i6;
                    lastSpecial = ch4;
                    if (ch4 < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[ch4] == 4) {
                        newcount += 4;
                    }
                    if (firstSpecialIndex == -1) {
                        firstSpecialIndex = i6;
                    }
                }
            }
            if (specialCount > 0) {
                int newcount2 = newcount + specialCount;
                if (newcount2 > this.buf.length) {
                    expandCapacity(newcount2);
                }
                this.count = newcount2;
                if (specialCount == 1) {
                    if (lastSpecial == 8232) {
                        char[] cArr10 = this.buf;
                        System.arraycopy(cArr10, lastSpecialIndex2 + 1, cArr10, lastSpecialIndex2 + 6, (end - lastSpecialIndex2) - 1);
                        char[] cArr11 = this.buf;
                        cArr11[lastSpecialIndex2] = '\\';
                        int lastSpecialIndex3 = lastSpecialIndex2 + 1;
                        cArr11[lastSpecialIndex3] = 'u';
                        int lastSpecialIndex4 = lastSpecialIndex3 + 1;
                        cArr11[lastSpecialIndex4] = '2';
                        int lastSpecialIndex5 = lastSpecialIndex4 + 1;
                        cArr11[lastSpecialIndex5] = '0';
                        int lastSpecialIndex6 = lastSpecialIndex5 + 1;
                        cArr11[lastSpecialIndex6] = '2';
                        cArr11[lastSpecialIndex6 + 1] = '8';
                    } else if (lastSpecial >= IOUtils.specicalFlags_doubleQuotes.length || IOUtils.specicalFlags_doubleQuotes[lastSpecial] != 4) {
                        char[] cArr12 = this.buf;
                        System.arraycopy(cArr12, lastSpecialIndex2 + 1, cArr12, lastSpecialIndex2 + 2, (end - lastSpecialIndex2) - 1);
                        char[] cArr13 = this.buf;
                        cArr13[lastSpecialIndex2] = '\\';
                        cArr13[lastSpecialIndex2 + 1] = IOUtils.replaceChars[lastSpecial];
                    } else {
                        char[] cArr14 = this.buf;
                        System.arraycopy(cArr14, lastSpecialIndex2 + 1, cArr14, lastSpecialIndex2 + 6, (end - lastSpecialIndex2) - 1);
                        char[] cArr15 = this.buf;
                        int bufIndex = lastSpecialIndex2 + 1;
                        cArr15[lastSpecialIndex2] = '\\';
                        int bufIndex2 = bufIndex + 1;
                        cArr15[bufIndex] = 'u';
                        int bufIndex3 = bufIndex2 + 1;
                        cArr15[bufIndex2] = IOUtils.DIGITS[(lastSpecial >>> '\f') & 15];
                        int bufIndex4 = bufIndex3 + 1;
                        this.buf[bufIndex3] = IOUtils.DIGITS[(lastSpecial >>> '\b') & 15];
                        int bufIndex5 = bufIndex4 + 1;
                        this.buf[bufIndex4] = IOUtils.DIGITS[(lastSpecial >>> 4) & 15];
                        int i7 = bufIndex5 + 1;
                        this.buf[bufIndex5] = IOUtils.DIGITS[lastSpecial & 15];
                    }
                } else if (specialCount > 1) {
                    int bufIndex6 = firstSpecialIndex;
                    for (int i8 = firstSpecialIndex - start; i8 < text.length(); i8++) {
                        char ch5 = text.charAt(i8);
                        if ((ch5 < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[ch5] != 0) || (ch5 == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                            int bufIndex7 = bufIndex6 + 1;
                            this.buf[bufIndex6] = '\\';
                            if (IOUtils.specicalFlags_doubleQuotes[ch5] == 4) {
                                char[] cArr16 = this.buf;
                                int bufIndex8 = bufIndex7 + 1;
                                cArr16[bufIndex7] = 'u';
                                int bufIndex9 = bufIndex8 + 1;
                                cArr16[bufIndex8] = IOUtils.DIGITS[(ch5 >>> '\f') & 15];
                                int bufIndex10 = bufIndex9 + 1;
                                this.buf[bufIndex9] = IOUtils.DIGITS[(ch5 >>> '\b') & 15];
                                int bufIndex11 = bufIndex10 + 1;
                                this.buf[bufIndex10] = IOUtils.DIGITS[(ch5 >>> 4) & 15];
                                this.buf[bufIndex11] = IOUtils.DIGITS[ch5 & 15];
                                end += 5;
                                bufIndex6 = bufIndex11 + 1;
                            } else {
                                this.buf[bufIndex7] = IOUtils.replaceChars[ch5];
                                end++;
                                bufIndex6 = bufIndex7 + 1;
                            }
                        } else if (ch5 == 8232) {
                            char[] cArr17 = this.buf;
                            int bufIndex12 = bufIndex6 + 1;
                            cArr17[bufIndex6] = '\\';
                            int bufIndex13 = bufIndex12 + 1;
                            cArr17[bufIndex12] = 'u';
                            int bufIndex14 = bufIndex13 + 1;
                            cArr17[bufIndex13] = IOUtils.DIGITS[(ch5 >>> '\f') & 15];
                            int bufIndex15 = bufIndex14 + 1;
                            this.buf[bufIndex14] = IOUtils.DIGITS[(ch5 >>> '\b') & 15];
                            int bufIndex16 = bufIndex15 + 1;
                            this.buf[bufIndex15] = IOUtils.DIGITS[(ch5 >>> 4) & 15];
                            this.buf[bufIndex16] = IOUtils.DIGITS[ch5 & 15];
                            end += 5;
                            bufIndex6 = bufIndex16 + 1;
                        } else {
                            this.buf[bufIndex6] = ch5;
                            bufIndex6++;
                        }
                    }
                }
            }
        }
        if (seperator != 0) {
            char[] cArr18 = this.buf;
            int i9 = this.count;
            cArr18[i9 - 2] = '\"';
            cArr18[i9 - 1] = seperator;
            return;
        }
        this.buf[this.count - 1] = '\"';
    }

    public void writeFieldNull(char seperator, String name) {
        write(seperator);
        writeFieldName(name);
        writeNull();
    }

    public void writeFieldEmptyList(char seperator, String key) {
        write(seperator);
        writeFieldName(key);
        write("[]");
    }

    public void writeFieldNullString(char seperator, String name) {
        write(seperator);
        writeFieldName(name);
        if (isEnabled(SerializerFeature.WriteNullStringAsEmpty)) {
            writeString(BuildConfig.FLAVOR);
        } else {
            writeNull();
        }
    }

    public void writeFieldNullBoolean(char seperator, String name) {
        write(seperator);
        writeFieldName(name);
        if (isEnabled(SerializerFeature.WriteNullBooleanAsFalse)) {
            write("false");
        } else {
            writeNull();
        }
    }

    public void writeFieldNullList(char seperator, String name) {
        write(seperator);
        writeFieldName(name);
        if (isEnabled(SerializerFeature.WriteNullListAsEmpty)) {
            write("[]");
        } else {
            writeNull();
        }
    }

    public void writeFieldNullNumber(char seperator, String name) {
        write(seperator);
        writeFieldName(name);
        if (isEnabled(SerializerFeature.WriteNullNumberAsZero)) {
            write('0');
        } else {
            writeNull();
        }
    }

    public void writeFieldValue(char seperator, String name, char value) {
        write(seperator);
        writeFieldName(name);
        if (value == 0) {
            writeString("\u0000");
        } else {
            writeString(Character.toString(value));
        }
    }

    public void writeFieldValue(char seperator, String name, boolean value) {
        char keySeperator = isEnabled(SerializerFeature.UseSingleQuotes) ? '\'' : '\"';
        int intSize = value ? 4 : 5;
        int nameLen = name.length();
        int newcount = this.count + nameLen + 4 + intSize;
        if (newcount > this.buf.length) {
            if (this.writer != null) {
                write(seperator);
                writeString(name);
                write(':');
                write(value);
                return;
            }
            expandCapacity(newcount);
        }
        int start = this.count;
        this.count = newcount;
        char[] cArr = this.buf;
        cArr[start] = seperator;
        int nameEnd = start + nameLen + 1;
        cArr[start + 1] = keySeperator;
        name.getChars(0, nameLen, cArr, start + 2);
        this.buf[nameEnd + 1] = keySeperator;
        if (value) {
            System.arraycopy(":true".toCharArray(), 0, this.buf, nameEnd + 2, 5);
        } else {
            System.arraycopy(":false".toCharArray(), 0, this.buf, nameEnd + 2, 6);
        }
    }

    public void write(boolean value) {
        if (value) {
            write("true");
        } else {
            write("false");
        }
    }

    public void writeFieldValue(char seperator, String name, int value) {
        if (value == Integer.MIN_VALUE || !isEnabled(SerializerFeature.QuoteFieldNames)) {
            writeFieldValue1(seperator, name, value);
            return;
        }
        char keySeperator = isEnabled(SerializerFeature.UseSingleQuotes) ? '\'' : '\"';
        int intSize = value < 0 ? IOUtils.stringSize(-value) + 1 : IOUtils.stringSize(value);
        int nameLen = name.length();
        int newcount = this.count + nameLen + 4 + intSize;
        if (newcount > this.buf.length) {
            if (this.writer != null) {
                writeFieldValue1(seperator, name, value);
                return;
            }
            expandCapacity(newcount);
        }
        int start = this.count;
        this.count = newcount;
        char[] cArr = this.buf;
        cArr[start] = seperator;
        int nameEnd = start + nameLen + 1;
        cArr[start + 1] = keySeperator;
        name.getChars(0, nameLen, cArr, start + 2);
        char[] cArr2 = this.buf;
        cArr2[nameEnd + 1] = keySeperator;
        cArr2[nameEnd + 2] = ':';
        IOUtils.getChars(value, this.count, cArr2);
    }

    public void writeFieldValue1(char seperator, String name, int value) {
        write(seperator);
        writeFieldName(name);
        writeInt(value);
    }

    public void writeFieldValue(char seperator, String name, long value) {
        if (value == Long.MIN_VALUE || !isEnabled(SerializerFeature.QuoteFieldNames)) {
            writeFieldValue1(seperator, name, value);
            return;
        }
        char keySeperator = isEnabled(SerializerFeature.UseSingleQuotes) ? '\'' : '\"';
        int intSize = value < 0 ? IOUtils.stringSize(-value) + 1 : IOUtils.stringSize(value);
        int nameLen = name.length();
        int newcount = this.count + nameLen + 4 + intSize;
        if (newcount > this.buf.length) {
            if (this.writer != null) {
                write(seperator);
                writeFieldName(name);
                writeLong(value);
                return;
            }
            expandCapacity(newcount);
        }
        int start = this.count;
        this.count = newcount;
        char[] cArr = this.buf;
        cArr[start] = seperator;
        int nameEnd = start + nameLen + 1;
        cArr[start + 1] = keySeperator;
        name.getChars(0, nameLen, cArr, start + 2);
        char[] cArr2 = this.buf;
        cArr2[nameEnd + 1] = keySeperator;
        cArr2[nameEnd + 2] = ':';
        IOUtils.getChars(value, this.count, cArr2);
    }

    public void writeFieldValue1(char seperator, String name, long value) {
        write(seperator);
        writeFieldName(name);
        writeLong(value);
    }

    public void writeFieldValue(char seperator, String name, float value) {
        write(seperator);
        writeFieldName(name);
        if (value == 0.0f) {
            write('0');
        } else if (Float.isNaN(value)) {
            writeNull();
        } else if (Float.isInfinite(value)) {
            writeNull();
        } else {
            String text = Float.toString(value);
            if (text.endsWith(".0")) {
                text = text.substring(0, text.length() - 2);
            }
            write(text);
        }
    }

    public void writeFieldValue(char seperator, String name, double value) {
        write(seperator);
        writeFieldName(name);
        if (value == 0.0d) {
            write('0');
        } else if (Double.isNaN(value)) {
            writeNull();
        } else if (Double.isInfinite(value)) {
            writeNull();
        } else {
            String text = Double.toString(value);
            if (text.endsWith(".0")) {
                text = text.substring(0, text.length() - 2);
            }
            write(text);
        }
    }

    public void writeFieldValue(char seperator, String name, String value) {
        if (!isEnabled(SerializerFeature.QuoteFieldNames)) {
            write(seperator);
            writeFieldName(name);
            if (value == null) {
                writeNull();
            } else {
                writeString(value);
            }
        } else if (isEnabled(SerializerFeature.UseSingleQuotes)) {
            write(seperator);
            writeFieldName(name);
            if (value == null) {
                writeNull();
            } else {
                writeString(value);
            }
        } else if (isEnabled(SerializerFeature.BrowserCompatible)) {
            write(seperator);
            writeStringWithDoubleQuote(name, ':');
            writeStringWithDoubleQuote(value, 0);
        } else {
            writeFieldValueStringWithDoubleQuote(seperator, name, value, true);
        }
    }

    /* JADX INFO: Multiple debug info for r4v9 int: [D('nameLen' int), D('LengthOfCopy' int)] */
    private void writeFieldValueStringWithDoubleQuote(char seperator, String name, String value, boolean checkSpecial) {
        int valueLen;
        int newcount;
        int nameLen = name.length();
        int newcount2 = this.count;
        if (value == null) {
            valueLen = 4;
            newcount = newcount2 + nameLen + 8;
        } else {
            valueLen = value.length();
            newcount = newcount2 + nameLen + valueLen + 6;
        }
        if (newcount > this.buf.length) {
            if (this.writer != null) {
                write(seperator);
                writeStringWithDoubleQuote(name, ':', checkSpecial);
                writeStringWithDoubleQuote(value, 0, checkSpecial);
                return;
            }
            expandCapacity(newcount);
        }
        char[] cArr = this.buf;
        int i = this.count;
        cArr[i] = seperator;
        int nameStart = i + 2;
        int nameEnd = nameStart + nameLen;
        cArr[i + 1] = '\"';
        name.getChars(0, nameLen, cArr, nameStart);
        this.count = newcount;
        char[] cArr2 = this.buf;
        cArr2[nameEnd] = '\"';
        int index = nameEnd + 1;
        int index2 = index + 1;
        cArr2[index] = ':';
        if (value == null) {
            int index3 = index2 + 1;
            cArr2[index2] = 'n';
            int index4 = index3 + 1;
            cArr2[index3] = 'u';
            int index5 = index4 + 1;
            cArr2[index4] = 'l';
            int i2 = index5 + 1;
            cArr2[index5] = 'l';
            return;
        }
        int index6 = index2 + 1;
        cArr2[index2] = '\"';
        int valueEnd = index6 + valueLen;
        value.getChars(0, valueLen, cArr2, index6);
        if (checkSpecial && !isEnabled(SerializerFeature.DisableCheckSpecialChar)) {
            int specialCount = 0;
            int lastSpecialIndex = -1;
            int firstSpecialIndex = -1;
            char lastSpecial = 0;
            int newcount3 = newcount;
            for (int i3 = index6; i3 < valueEnd; i3++) {
                char ch = this.buf[i3];
                if (ch == 8232) {
                    specialCount++;
                    newcount3 += 4;
                    if (firstSpecialIndex == -1) {
                        firstSpecialIndex = i3;
                        lastSpecial = ch;
                        lastSpecialIndex = i3;
                    } else {
                        lastSpecial = ch;
                        lastSpecialIndex = i3;
                    }
                } else if (ch >= ']') {
                    if (ch >= 127 && ch <= 160) {
                        if (firstSpecialIndex == -1) {
                            firstSpecialIndex = i3;
                        }
                        specialCount++;
                        newcount3 += 4;
                        lastSpecial = ch;
                        lastSpecialIndex = i3;
                    }
                } else if (isSpecial(ch, this.features)) {
                    specialCount++;
                    if (ch < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[ch] == 4) {
                        newcount3 += 4;
                    }
                    if (firstSpecialIndex == -1) {
                        firstSpecialIndex = i3;
                        lastSpecial = ch;
                        lastSpecialIndex = i3;
                    } else {
                        lastSpecial = ch;
                        lastSpecialIndex = i3;
                    }
                }
            }
            if (specialCount > 0) {
                int newcount4 = newcount3 + specialCount;
                if (newcount4 > this.buf.length) {
                    expandCapacity(newcount4);
                }
                this.count = newcount4;
                if (specialCount == 1) {
                    if (lastSpecial == 8232) {
                        char[] cArr3 = this.buf;
                        System.arraycopy(cArr3, lastSpecialIndex + 1, cArr3, lastSpecialIndex + 6, (valueEnd - lastSpecialIndex) - 1);
                        char[] cArr4 = this.buf;
                        cArr4[lastSpecialIndex] = '\\';
                        int lastSpecialIndex2 = lastSpecialIndex + 1;
                        cArr4[lastSpecialIndex2] = 'u';
                        int lastSpecialIndex3 = lastSpecialIndex2 + 1;
                        cArr4[lastSpecialIndex3] = '2';
                        int lastSpecialIndex4 = lastSpecialIndex3 + 1;
                        cArr4[lastSpecialIndex4] = '0';
                        int lastSpecialIndex5 = lastSpecialIndex4 + 1;
                        cArr4[lastSpecialIndex5] = '2';
                        cArr4[lastSpecialIndex5 + 1] = '8';
                    } else if (lastSpecial >= IOUtils.specicalFlags_doubleQuotes.length || IOUtils.specicalFlags_doubleQuotes[lastSpecial] != 4) {
                        char[] cArr5 = this.buf;
                        System.arraycopy(cArr5, lastSpecialIndex + 1, cArr5, lastSpecialIndex + 2, (valueEnd - lastSpecialIndex) - 1);
                        char[] cArr6 = this.buf;
                        cArr6[lastSpecialIndex] = '\\';
                        cArr6[lastSpecialIndex + 1] = IOUtils.replaceChars[lastSpecial];
                    } else {
                        char[] cArr7 = this.buf;
                        System.arraycopy(cArr7, lastSpecialIndex + 1, cArr7, lastSpecialIndex + 6, (valueEnd - lastSpecialIndex) - 1);
                        char[] cArr8 = this.buf;
                        int bufIndex = lastSpecialIndex + 1;
                        cArr8[lastSpecialIndex] = '\\';
                        int bufIndex2 = bufIndex + 1;
                        cArr8[bufIndex] = 'u';
                        int bufIndex3 = bufIndex2 + 1;
                        cArr8[bufIndex2] = IOUtils.DIGITS[(lastSpecial >>> '\f') & 15];
                        int bufIndex4 = bufIndex3 + 1;
                        this.buf[bufIndex3] = IOUtils.DIGITS[(lastSpecial >>> '\b') & 15];
                        int bufIndex5 = bufIndex4 + 1;
                        this.buf[bufIndex4] = IOUtils.DIGITS[(lastSpecial >>> 4) & 15];
                        int i4 = bufIndex5 + 1;
                        this.buf[bufIndex5] = IOUtils.DIGITS[lastSpecial & 15];
                    }
                } else if (specialCount > 1) {
                    int bufIndex6 = firstSpecialIndex;
                    for (int i5 = firstSpecialIndex - index6; i5 < value.length(); i5++) {
                        char ch2 = value.charAt(i5);
                        if (ch2 >= IOUtils.specicalFlags_doubleQuotes.length || IOUtils.specicalFlags_doubleQuotes[ch2] == 0) {
                            if (ch2 == '/') {
                                if (!isEnabled(SerializerFeature.WriteSlashAsSpecial)) {
                                }
                            }
                            if (ch2 == 8232) {
                                char[] cArr9 = this.buf;
                                int bufIndex7 = bufIndex6 + 1;
                                cArr9[bufIndex6] = '\\';
                                int bufIndex8 = bufIndex7 + 1;
                                cArr9[bufIndex7] = 'u';
                                int bufIndex9 = bufIndex8 + 1;
                                cArr9[bufIndex8] = IOUtils.DIGITS[(ch2 >>> '\f') & 15];
                                int bufIndex10 = bufIndex9 + 1;
                                this.buf[bufIndex9] = IOUtils.DIGITS[(ch2 >>> '\b') & 15];
                                int bufIndex11 = bufIndex10 + 1;
                                this.buf[bufIndex10] = IOUtils.DIGITS[(ch2 >>> 4) & 15];
                                this.buf[bufIndex11] = IOUtils.DIGITS[ch2 & 15];
                                valueEnd += 5;
                                bufIndex6 = bufIndex11 + 1;
                            } else {
                                this.buf[bufIndex6] = ch2;
                                bufIndex6++;
                            }
                        }
                        int bufIndex12 = bufIndex6 + 1;
                        this.buf[bufIndex6] = '\\';
                        if (IOUtils.specicalFlags_doubleQuotes[ch2] == 4) {
                            char[] cArr10 = this.buf;
                            int bufIndex13 = bufIndex12 + 1;
                            cArr10[bufIndex12] = 'u';
                            int bufIndex14 = bufIndex13 + 1;
                            cArr10[bufIndex13] = IOUtils.DIGITS[(ch2 >>> '\f') & 15];
                            int bufIndex15 = bufIndex14 + 1;
                            this.buf[bufIndex14] = IOUtils.DIGITS[(ch2 >>> '\b') & 15];
                            int bufIndex16 = bufIndex15 + 1;
                            this.buf[bufIndex15] = IOUtils.DIGITS[(ch2 >>> 4) & 15];
                            this.buf[bufIndex16] = IOUtils.DIGITS[ch2 & 15];
                            valueEnd += 5;
                            bufIndex6 = bufIndex16 + 1;
                        } else {
                            this.buf[bufIndex12] = IOUtils.replaceChars[ch2];
                            valueEnd++;
                            bufIndex6 = bufIndex12 + 1;
                        }
                    }
                }
            }
        }
        this.buf[this.count - 1] = '\"';
    }

    static final boolean isSpecial(char ch, int features2) {
        if (ch == ' ') {
            return false;
        }
        if (ch == '/' && SerializerFeature.isEnabled(features2, SerializerFeature.WriteSlashAsSpecial)) {
            return true;
        }
        if (ch > '#' && ch != '\\') {
            return false;
        }
        if (ch <= 31 || ch == '\\' || ch == '\"') {
            return true;
        }
        return false;
    }

    public void writeFieldValue(char seperator, String name, Enum<?> value) {
        if (value == null) {
            write(seperator);
            writeFieldName(name);
            writeNull();
        } else if (!isEnabled(SerializerFeature.WriteEnumUsingToString)) {
            writeFieldValue(seperator, name, value.ordinal());
        } else if (isEnabled(SerializerFeature.UseSingleQuotes)) {
            writeFieldValue(seperator, name, value.name());
        } else {
            writeFieldValueStringWithDoubleQuote(seperator, name, value.name(), false);
        }
    }

    public void writeFieldValue(char seperator, String name, BigDecimal value) {
        write(seperator);
        writeFieldName(name);
        if (value == null) {
            writeNull();
        } else {
            write(value.toString());
        }
    }

    public void writeString(String text, char seperator) {
        if (isEnabled(SerializerFeature.UseSingleQuotes)) {
            writeStringWithSingleQuote(text);
            write(seperator);
            return;
        }
        writeStringWithDoubleQuote(text, seperator);
    }

    public void writeString(String text) {
        if (isEnabled(SerializerFeature.UseSingleQuotes)) {
            writeStringWithSingleQuote(text);
        } else {
            writeStringWithDoubleQuote(text, 0);
        }
    }

    private void writeStringWithSingleQuote(String text) {
        if (text == null) {
            int newcount = this.count + 4;
            if (newcount > this.buf.length) {
                expandCapacity(newcount);
            }
            "null".getChars(0, 4, this.buf, this.count);
            this.count = newcount;
            return;
        }
        int len = text.length();
        int newcount2 = this.count + len + 2;
        char c = '\r';
        char c2 = '\\';
        if (newcount2 > this.buf.length) {
            if (this.writer != null) {
                write('\'');
                for (int i = 0; i < text.length(); i++) {
                    char ch = text.charAt(i);
                    if (ch <= '\r' || ch == '\\' || ch == '\'' || (ch == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                        write('\\');
                        write(IOUtils.replaceChars[ch]);
                    } else {
                        write(ch);
                    }
                }
                write('\'');
                return;
            }
            expandCapacity(newcount2);
        }
        int i2 = this.count;
        int start = i2 + 1;
        int end = start + len;
        char[] cArr = this.buf;
        cArr[i2] = '\'';
        text.getChars(0, len, cArr, start);
        this.count = newcount2;
        int specialCount = 0;
        int lastSpecialIndex = -1;
        char lastSpecial = 0;
        for (int i3 = start; i3 < end; i3++) {
            char ch2 = this.buf[i3];
            if (ch2 <= '\r' || ch2 == '\\' || ch2 == '\'' || (ch2 == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                specialCount++;
                lastSpecialIndex = i3;
                lastSpecial = ch2;
            }
        }
        int newcount3 = newcount2 + specialCount;
        if (newcount3 > this.buf.length) {
            expandCapacity(newcount3);
        }
        this.count = newcount3;
        if (specialCount == 1) {
            char[] cArr2 = this.buf;
            System.arraycopy(cArr2, lastSpecialIndex + 1, cArr2, lastSpecialIndex + 2, (end - lastSpecialIndex) - 1);
            char[] cArr3 = this.buf;
            cArr3[lastSpecialIndex] = '\\';
            cArr3[lastSpecialIndex + 1] = IOUtils.replaceChars[lastSpecial];
        } else if (specialCount > 1) {
            char[] cArr4 = this.buf;
            System.arraycopy(cArr4, lastSpecialIndex + 1, cArr4, lastSpecialIndex + 2, (end - lastSpecialIndex) - 1);
            char[] cArr5 = this.buf;
            cArr5[lastSpecialIndex] = '\\';
            int lastSpecialIndex2 = lastSpecialIndex + 1;
            cArr5[lastSpecialIndex2] = IOUtils.replaceChars[lastSpecial];
            int end2 = end + 1;
            int i4 = lastSpecialIndex2 - 2;
            while (i4 >= start) {
                char ch3 = this.buf[i4];
                if (ch3 > c && ch3 != c2 && ch3 != '\'') {
                    if (ch3 == '/') {
                        if (!isEnabled(SerializerFeature.WriteSlashAsSpecial)) {
                        }
                    }
                    i4--;
                    c = '\r';
                }
                char[] cArr6 = this.buf;
                System.arraycopy(cArr6, i4 + 1, cArr6, i4 + 2, (end2 - i4) - 1);
                char[] cArr7 = this.buf;
                c2 = '\\';
                cArr7[i4] = '\\';
                cArr7[i4 + 1] = IOUtils.replaceChars[ch3];
                end2++;
                i4--;
                c = '\r';
            }
        }
        this.buf[this.count - 1] = '\'';
    }

    public void writeFieldName(String key) {
        writeFieldName(key, false);
    }

    public void writeFieldName(String key, boolean checkSpecial) {
        if (key == null) {
            write("null:");
        } else if (isEnabled(SerializerFeature.UseSingleQuotes)) {
            if (isEnabled(SerializerFeature.QuoteFieldNames)) {
                writeStringWithSingleQuote(key);
                write(':');
                return;
            }
            writeKeyWithSingleQuoteIfHasSpecial(key);
        } else if (isEnabled(SerializerFeature.QuoteFieldNames)) {
            writeStringWithDoubleQuote(key, ':', checkSpecial);
        } else {
            writeKeyWithDoubleQuoteIfHasSpecial(key);
        }
    }

    /* JADX INFO: Multiple debug info for r6v2 int: [D('newCount' int), D('start' int)] */
    private void writeKeyWithDoubleQuoteIfHasSpecial(String text) {
        byte[] specicalFlags_doubleQuotes = IOUtils.specicalFlags_doubleQuotes;
        int len = text.length();
        int newcount = this.count + len + 1;
        if (newcount > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(newcount);
            } else if (len == 0) {
                write('\"');
                write('\"');
                write(':');
                return;
            } else {
                boolean hasSpecial = false;
                int i = 0;
                while (true) {
                    if (i >= len) {
                        break;
                    }
                    char ch = text.charAt(i);
                    if (ch < specicalFlags_doubleQuotes.length && specicalFlags_doubleQuotes[ch] != 0) {
                        hasSpecial = true;
                        break;
                    }
                    i++;
                }
                if (hasSpecial) {
                    write('\"');
                }
                for (int i2 = 0; i2 < len; i2++) {
                    char ch2 = text.charAt(i2);
                    if (ch2 >= specicalFlags_doubleQuotes.length || specicalFlags_doubleQuotes[ch2] == 0) {
                        write(ch2);
                    } else {
                        write('\\');
                        write(IOUtils.replaceChars[ch2]);
                    }
                }
                if (hasSpecial) {
                    write('\"');
                }
                write(':');
                return;
            }
        }
        if (len == 0) {
            int i3 = this.count;
            if (i3 + 3 > this.buf.length) {
                expandCapacity(i3 + 3);
            }
            char[] cArr = this.buf;
            int i4 = this.count;
            this.count = i4 + 1;
            cArr[i4] = '\"';
            int i5 = this.count;
            this.count = i5 + 1;
            cArr[i5] = '\"';
            int i6 = this.count;
            this.count = i6 + 1;
            cArr[i6] = ':';
            return;
        }
        int start = this.count;
        int end = start + len;
        text.getChars(0, len, this.buf, start);
        this.count = newcount;
        boolean hasSpecial2 = false;
        int i7 = start;
        while (i7 < end) {
            char[] cArr2 = this.buf;
            char ch3 = cArr2[i7];
            if (ch3 < specicalFlags_doubleQuotes.length && specicalFlags_doubleQuotes[ch3] != 0) {
                if (!hasSpecial2) {
                    newcount += 3;
                    if (newcount > cArr2.length) {
                        expandCapacity(newcount);
                    }
                    this.count = newcount;
                    char[] cArr3 = this.buf;
                    System.arraycopy(cArr3, i7 + 1, cArr3, i7 + 3, (end - i7) - 1);
                    char[] cArr4 = this.buf;
                    System.arraycopy(cArr4, 0, cArr4, 1, i7);
                    char[] cArr5 = this.buf;
                    cArr5[start] = '\"';
                    int i8 = i7 + 1;
                    cArr5[i8] = '\\';
                    i7 = i8 + 1;
                    cArr5[i7] = IOUtils.replaceChars[ch3];
                    end += 2;
                    this.buf[this.count - 2] = '\"';
                    hasSpecial2 = true;
                } else {
                    newcount++;
                    if (newcount > cArr2.length) {
                        expandCapacity(newcount);
                    }
                    this.count = newcount;
                    char[] cArr6 = this.buf;
                    System.arraycopy(cArr6, i7 + 1, cArr6, i7 + 2, end - i7);
                    char[] cArr7 = this.buf;
                    cArr7[i7] = '\\';
                    i7++;
                    cArr7[i7] = IOUtils.replaceChars[ch3];
                    end++;
                }
            }
            i7++;
        }
        this.buf[this.count - 1] = ':';
    }

    /* JADX INFO: Multiple debug info for r6v2 int: [D('newCount' int), D('start' int)] */
    private void writeKeyWithSingleQuoteIfHasSpecial(String text) {
        byte[] specicalFlags_singleQuotes = IOUtils.specicalFlags_singleQuotes;
        int len = text.length();
        int newcount = this.count + len + 1;
        if (newcount > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(newcount);
            } else if (len == 0) {
                write('\'');
                write('\'');
                write(':');
                return;
            } else {
                boolean hasSpecial = false;
                int i = 0;
                while (true) {
                    if (i >= len) {
                        break;
                    }
                    char ch = text.charAt(i);
                    if (ch < specicalFlags_singleQuotes.length && specicalFlags_singleQuotes[ch] != 0) {
                        hasSpecial = true;
                        break;
                    }
                    i++;
                }
                if (hasSpecial) {
                    write('\'');
                }
                for (int i2 = 0; i2 < len; i2++) {
                    char ch2 = text.charAt(i2);
                    if (ch2 >= specicalFlags_singleQuotes.length || specicalFlags_singleQuotes[ch2] == 0) {
                        write(ch2);
                    } else {
                        write('\\');
                        write(IOUtils.replaceChars[ch2]);
                    }
                }
                if (hasSpecial) {
                    write('\'');
                }
                write(':');
                return;
            }
        }
        if (len == 0) {
            int i3 = this.count;
            if (i3 + 3 > this.buf.length) {
                expandCapacity(i3 + 3);
            }
            char[] cArr = this.buf;
            int i4 = this.count;
            this.count = i4 + 1;
            cArr[i4] = '\'';
            int i5 = this.count;
            this.count = i5 + 1;
            cArr[i5] = '\'';
            int i6 = this.count;
            this.count = i6 + 1;
            cArr[i6] = ':';
            return;
        }
        int start = this.count;
        int end = start + len;
        text.getChars(0, len, this.buf, start);
        this.count = newcount;
        boolean hasSpecial2 = false;
        int i7 = start;
        while (i7 < end) {
            char[] cArr2 = this.buf;
            char ch3 = cArr2[i7];
            if (ch3 < specicalFlags_singleQuotes.length && specicalFlags_singleQuotes[ch3] != 0) {
                if (!hasSpecial2) {
                    newcount += 3;
                    if (newcount > cArr2.length) {
                        expandCapacity(newcount);
                    }
                    this.count = newcount;
                    char[] cArr3 = this.buf;
                    System.arraycopy(cArr3, i7 + 1, cArr3, i7 + 3, (end - i7) - 1);
                    char[] cArr4 = this.buf;
                    System.arraycopy(cArr4, 0, cArr4, 1, i7);
                    char[] cArr5 = this.buf;
                    cArr5[start] = '\'';
                    int i8 = i7 + 1;
                    cArr5[i8] = '\\';
                    i7 = i8 + 1;
                    cArr5[i7] = IOUtils.replaceChars[ch3];
                    end += 2;
                    this.buf[this.count - 2] = '\'';
                    hasSpecial2 = true;
                } else {
                    newcount++;
                    if (newcount > cArr2.length) {
                        expandCapacity(newcount);
                    }
                    this.count = newcount;
                    char[] cArr6 = this.buf;
                    System.arraycopy(cArr6, i7 + 1, cArr6, i7 + 2, end - i7);
                    char[] cArr7 = this.buf;
                    cArr7[i7] = '\\';
                    i7++;
                    cArr7[i7] = IOUtils.replaceChars[ch3];
                    end++;
                }
            }
            i7++;
        }
        this.buf[newcount - 1] = ':';
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() {
        Writer writer2 = this.writer;
        if (writer2 != null) {
            try {
                writer2.write(this.buf, 0, this.count);
                this.writer.flush();
                this.count = 0;
            } catch (IOException e) {
                throw new JSONException(e.getMessage(), e);
            }
        }
    }
}

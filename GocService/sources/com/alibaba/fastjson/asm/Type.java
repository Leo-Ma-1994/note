package com.alibaba.fastjson.asm;

public class Type {
    public static final int ARRAY = 9;
    public static final int BOOLEAN = 1;
    public static final Type BOOLEAN_TYPE = new Type(1, null, 1509950721, 1);
    public static final int BYTE = 3;
    public static final Type BYTE_TYPE = new Type(3, null, 1107297537, 1);
    public static final int CHAR = 2;
    public static final Type CHAR_TYPE = new Type(2, null, 1124075009, 1);
    public static final int DOUBLE = 8;
    public static final Type DOUBLE_TYPE = new Type(8, null, 1141048066, 1);
    public static final int FLOAT = 6;
    public static final Type FLOAT_TYPE = new Type(6, null, 1174536705, 1);
    public static final int INT = 5;
    public static final Type INT_TYPE = new Type(5, null, 1224736769, 1);
    public static final int LONG = 7;
    public static final Type LONG_TYPE = new Type(7, null, 1241579778, 1);
    public static final int OBJECT = 10;
    public static final int SHORT = 4;
    public static final Type SHORT_TYPE = new Type(4, null, 1392510721, 1);
    public static final int VOID = 0;
    public static final Type VOID_TYPE = new Type(0, null, 1443168256, 1);
    private final char[] buf;
    private final int len;
    private final int off;
    private final int sort;

    private Type(int sort2, char[] buf2, int off2, int len2) {
        this.sort = sort2;
        this.buf = buf2;
        this.off = off2;
        this.len = len2;
    }

    public static Type getType(String typeDescriptor) {
        return getType(typeDescriptor.toCharArray(), 0);
    }

    public static int getArgumentsAndReturnSizes(String desc) {
        int c;
        int c2;
        int n = 1;
        int c3 = 1;
        while (true) {
            c = c3 + 1;
            char car = desc.charAt(c3);
            if (car == ')') {
                break;
            } else if (car == 'L') {
                while (true) {
                    c2 = c + 1;
                    if (desc.charAt(c) == 59) {
                        break;
                    }
                    c = c2;
                }
                n++;
                c3 = c2;
            } else if (car == 'D' || car == 'J') {
                n += 2;
                c3 = c;
            } else {
                n++;
                c3 = c;
            }
        }
        char car2 = desc.charAt(c);
        return (n << 2) | (car2 == 'V' ? 0 : (car2 == 'D' || car2 == 'J') ? 2 : 1);
    }

    private static Type getType(char[] buf2, int off2) {
        char c = buf2[off2];
        if (c == 'F') {
            return FLOAT_TYPE;
        }
        if (c == 'S') {
            return SHORT_TYPE;
        }
        if (c == 'V') {
            return VOID_TYPE;
        }
        if (c == 'I') {
            return INT_TYPE;
        }
        if (c == 'J') {
            return LONG_TYPE;
        }
        if (c == 'Z') {
            return BOOLEAN_TYPE;
        }
        if (c != '[') {
            switch (c) {
                case 'B':
                    return BYTE_TYPE;
                case 'C':
                    return CHAR_TYPE;
                case 'D':
                    return DOUBLE_TYPE;
                default:
                    int len2 = 1;
                    while (buf2[off2 + len2] != ';') {
                        len2++;
                    }
                    return new Type(10, buf2, off2 + 1, len2 - 1);
            }
        } else {
            int len3 = 1;
            while (buf2[off2 + len3] == '[') {
                len3++;
            }
            if (buf2[off2 + len3] == 'L') {
                while (true) {
                    len3++;
                    if (buf2[off2 + len3] == ';') {
                        break;
                    }
                }
            }
            return new Type(9, buf2, off2, len3 + 1);
        }
    }

    public int getSort() {
        return this.sort;
    }

    public String getInternalName() {
        return new String(this.buf, this.off, this.len);
    }

    /* access modifiers changed from: package-private */
    public String getDescriptor() {
        return new String(this.buf, this.off, this.len);
    }
}

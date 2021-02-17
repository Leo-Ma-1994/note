package com.alibaba.fastjson.util;

import com.alibaba.fastjson.asm.Opcodes;
import com.goodocom.bttek.bt.res.NfDef;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;

public class UTF8Decoder extends CharsetDecoder {
    private static final Charset charset = Charset.forName("UTF-8");

    public UTF8Decoder() {
        super(charset, 1.0f, 1.0f);
    }

    private static boolean isNotContinuation(int b) {
        return (b & Opcodes.CHECKCAST) != 128;
    }

    private static final boolean isMalformed2(int b1, int b2) {
        return (b1 & 30) == 0 || (b2 & Opcodes.CHECKCAST) != 128;
    }

    private static boolean isMalformed3(int b1, int b2, int b3) {
        return ((b1 != -32 || (b2 & 224) != 128) && (b2 & Opcodes.CHECKCAST) == 128 && (b3 & Opcodes.CHECKCAST) == 128) ? false : true;
    }

    private static final boolean isMalformed4(int b2, int b3, int b4) {
        return ((b2 & Opcodes.CHECKCAST) == 128 && (b3 & Opcodes.CHECKCAST) == 128 && (b4 & Opcodes.CHECKCAST) == 128) ? false : true;
    }

    private static CoderResult lookupN(ByteBuffer src, int n) {
        for (int i = 1; i < n; i++) {
            if (isNotContinuation(src.get())) {
                return CoderResult.malformedForLength(i);
            }
        }
        return CoderResult.malformedForLength(n);
    }

    public static CoderResult malformedN(ByteBuffer src, int nb) {
        int i = 1;
        if (nb == 1) {
            int b1 = src.get();
            if ((b1 >> 2) == -2) {
                if (src.remaining() < 4) {
                    return CoderResult.UNDERFLOW;
                }
                return lookupN(src, 5);
            } else if ((b1 >> 1) != -2) {
                return CoderResult.malformedForLength(1);
            } else {
                if (src.remaining() < 5) {
                    return CoderResult.UNDERFLOW;
                }
                return lookupN(src, 6);
            }
        } else if (nb == 2) {
            return CoderResult.malformedForLength(1);
        } else {
            if (nb == 3) {
                int b12 = src.get();
                int b2 = src.get();
                if (!(b12 == -32 && (b2 & 224) == 128) && !isNotContinuation(b2)) {
                    i = 2;
                }
                return CoderResult.malformedForLength(i);
            } else if (nb == 4) {
                int b13 = src.get() & NfDef.AVRCP_PLAYING_STATUS_ID_ERROR;
                int b22 = src.get() & NfDef.AVRCP_PLAYING_STATUS_ID_ERROR;
                if (b13 > 244 || ((b13 == 240 && (b22 < 144 || b22 > 191)) || ((b13 == 244 && (b22 & 240) != 128) || isNotContinuation(b22)))) {
                    return CoderResult.malformedForLength(1);
                }
                if (isNotContinuation(src.get())) {
                    return CoderResult.malformedForLength(2);
                }
                return CoderResult.malformedForLength(3);
            } else {
                throw new IllegalStateException();
            }
        }
    }

    private static CoderResult malformed(ByteBuffer src, int sp, CharBuffer dst, int dp, int nb) {
        src.position(sp - src.arrayOffset());
        CoderResult cr = malformedN(src, nb);
        updatePositions(src, sp, dst, dp);
        return cr;
    }

    private static CoderResult xflow(Buffer src, int sp, int sl, Buffer dst, int dp, int nb) {
        updatePositions(src, sp, dst, dp);
        return (nb == 0 || sl - sp < nb) ? CoderResult.UNDERFLOW : CoderResult.OVERFLOW;
    }

    private CoderResult decodeArrayLoop(ByteBuffer src, CharBuffer dst) {
        byte[] srcArray = src.array();
        int srcPosition = src.arrayOffset() + src.position();
        int srcLength = src.arrayOffset() + src.limit();
        char[] destArray = dst.array();
        int destPosition = dst.arrayOffset() + dst.position();
        int destLength = dst.arrayOffset() + dst.limit();
        int destLengthASCII = destPosition + Math.min(srcLength - srcPosition, destLength - destPosition);
        while (destPosition < destLengthASCII && srcArray[srcPosition] >= 0) {
            destArray[destPosition] = (char) srcArray[srcPosition];
            destPosition++;
            srcPosition++;
        }
        int srcPosition2 = srcPosition;
        int destPosition2 = destPosition;
        while (srcPosition2 < srcLength) {
            byte b = srcArray[srcPosition2];
            if (b >= 0) {
                if (destPosition2 >= destLength) {
                    return xflow(src, srcPosition2, srcLength, dst, destPosition2, 1);
                }
                destArray[destPosition2] = (char) b;
                srcPosition2++;
                destPosition2++;
            } else if ((b >> 5) == -2) {
                if (srcLength - srcPosition2 < 2 || destPosition2 >= destLength) {
                    return xflow(src, srcPosition2, srcLength, dst, destPosition2, 2);
                }
                byte b2 = srcArray[srcPosition2 + 1];
                if (isMalformed2(b, b2)) {
                    return malformed(src, srcPosition2, dst, destPosition2, 2);
                }
                destArray[destPosition2] = (char) (((b << 6) ^ b2) ^ 3968);
                srcPosition2 += 2;
                destPosition2++;
            } else if ((b >> 4) == -2) {
                if (srcLength - srcPosition2 < 3 || destPosition2 >= destLength) {
                    return xflow(src, srcPosition2, srcLength, dst, destPosition2, 3);
                }
                byte b3 = srcArray[srcPosition2 + 1];
                byte b4 = srcArray[srcPosition2 + 2];
                if (isMalformed3(b, b3, b4)) {
                    return malformed(src, srcPosition2, dst, destPosition2, 3);
                }
                destArray[destPosition2] = (char) ((((b << 12) ^ (b3 << 6)) ^ b4) ^ 8064);
                srcPosition2 += 3;
                destPosition2++;
            } else if ((b >> 3) != -2) {
                return malformed(src, srcPosition2, dst, destPosition2, 1);
            } else {
                if (srcLength - srcPosition2 < 4 || destLength - destPosition2 < 2) {
                    return xflow(src, srcPosition2, srcLength, dst, destPosition2, 4);
                }
                byte b5 = srcArray[srcPosition2 + 1];
                byte b6 = srcArray[srcPosition2 + 2];
                byte b7 = srcArray[srcPosition2 + 3];
                int uc = ((b & 7) << 18) | ((b5 & 63) << 12) | ((b6 & 63) << 6) | (b7 & 63);
                if (isMalformed4(b5, b6, b7) || !Surrogate.neededFor(uc)) {
                    return malformed(src, srcPosition2, dst, destPosition2, 4);
                }
                int destPosition3 = destPosition2 + 1;
                destArray[destPosition2] = Surrogate.high(uc);
                destArray[destPosition3] = Surrogate.low(uc);
                srcPosition2 += 4;
                destPosition2 = destPosition3 + 1;
            }
        }
        return xflow(src, srcPosition2, srcLength, dst, destPosition2, 0);
    }

    /* access modifiers changed from: protected */
    @Override // java.nio.charset.CharsetDecoder
    public CoderResult decodeLoop(ByteBuffer src, CharBuffer dst) {
        return decodeArrayLoop(src, dst);
    }

    static final void updatePositions(Buffer src, int sp, Buffer dst, int dp) {
        src.position(sp);
        dst.position(dp);
    }

    /* access modifiers changed from: private */
    public static class Surrogate {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        public static final int UCS4_MAX = 1114111;
        public static final int UCS4_MIN = 65536;

        private Surrogate() {
        }

        public static boolean neededFor(int uc) {
            return uc >= 65536 && uc <= 1114111;
        }

        public static char high(int uc) {
            return (char) (55296 | (((uc - 65536) >> 10) & 1023));
        }

        public static char low(int uc) {
            return (char) (56320 | ((uc - 65536) & 1023));
        }
    }
}

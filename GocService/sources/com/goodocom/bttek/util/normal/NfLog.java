package com.goodocom.bttek.util.normal;

import android.util.Log;

public final class NfLog {
    private static boolean D = true;
    private static boolean E = true;
    private static boolean I = true;
    private static boolean V = true;
    private static boolean W = true;

    public static void setVerbose(boolean enable) {
        V = enable;
    }

    public static void setDebug(boolean enable) {
        D = enable;
    }

    public static void setInfo(boolean enable) {
        I = enable;
    }

    public static void setWarn(boolean enable) {
        W = enable;
    }

    public static void setError(boolean enable) {
        E = enable;
    }

    public static int v(String tag, String msg) {
        if (V) {
            return Log.v(tag, msg);
        }
        return -1;
    }

    public static int v(String tag, String msg, Throwable tr) {
        if (V) {
            return Log.v(tag, msg, tr);
        }
        return -1;
    }

    public static int d(String tag, String msg) {
        if (D) {
            return Log.d(tag, msg);
        }
        return -1;
    }

    public static int d(boolean isDBG, String tag, String msg) {
        if (isDBG && D) {
            return Log.d(tag, msg);
        }
        return -1;
    }

    public static int d(String tag, String msg, Throwable tr) {
        if (D) {
            return Log.d(tag, msg, tr);
        }
        return -1;
    }

    public static int i(String tag, String msg) {
        if (I) {
            return Log.i(tag, msg);
        }
        return -1;
    }

    public static int i(String tag, String msg, Throwable tr) {
        if (I) {
            return Log.i(tag, msg, tr);
        }
        return -1;
    }

    public static int w(String tag, String msg) {
        if (W) {
            return Log.w(tag, msg);
        }
        return -1;
    }

    public static int w(String tag, String msg, Throwable tr) {
        if (W) {
            return Log.w(tag, msg);
        }
        return -1;
    }

    public static int w(String tag, Throwable tr) {
        if (W) {
            return Log.w(tag, tr);
        }
        return -1;
    }

    public static int e(String tag, String msg) {
        if (E) {
            return Log.e(tag, msg);
        }
        return -1;
    }

    public static int e(String tag, String msg, Throwable tr) {
        if (E) {
            return Log.e(tag, msg, tr);
        }
        return -1;
    }
}

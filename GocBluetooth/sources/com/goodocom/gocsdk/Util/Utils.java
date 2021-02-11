package com.goodocom.gocsdk.Util;

import java.util.List;

public class Utils {
    public static final String ACTION_AUDIO_STATE_CHANGED = "android.bluetooth.headsetclient.profile.action.AUDIO_STATE_CHANGED";
    public static final String ACTION_CALL_CHANGED = "android.bluetooth.headsetclient.profile.action.AG_CALL_CHANGED";
    public static final String ACTION_CONNECTION_STATE_CHANGED = "android.bluetooth.headsetclient.profile.action.CONNECTION_STATE_CHANGED";
    public static final String EXTRA_CALL = "android.bluetooth.headsetclient.extra.CALL";
    public static final String EXTRA_NUMBER = "android.bluetooth.headsetclient.extra.NUMBER";

    public static boolean isListEmpty(List list) {
        if (list == null || list.size() == 0) {
            return true;
        }
        return false;
    }

    public static String generateTime(long time) {
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        return hours > 0 ? String.format("%02d:%02d:%02d", Integer.valueOf(hours), Integer.valueOf(minutes), Integer.valueOf(seconds)) : String.format("%02d:%02d", Integer.valueOf(minutes), Integer.valueOf(seconds));
    }

    public static byte[] getBytesFromAddress(String address) {
        int j = 0;
        byte[] output = new byte[16];
        int i = 0;
        while (i < address.length()) {
            if (address.charAt(i) != ':') {
                output[j] = (byte) Integer.parseInt(address.substring(i, i + 2), 16);
                j++;
                i++;
            }
            i++;
        }
        return output;
    }
}

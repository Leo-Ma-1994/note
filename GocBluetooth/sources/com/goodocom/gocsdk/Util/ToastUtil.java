package com.goodocom.gocsdk.Util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    public static void showMsg(Context context, String msg) {
        Toast.makeText(context, msg, 0).show();
    }
}

package com.goodocom.gocsdk.Util;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;

public class PermissionUtils {
    private String[] PERMISSIONS_STORAGE = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
    private final int REQUEST_EXTERNAL_STORAGE = 1;

    public void verifyStoragePermissions(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            ActivityCompat.requestPermissions(activity, this.PERMISSIONS_STORAGE, 1);
        }
    }
}

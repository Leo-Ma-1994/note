package com.goodocom.gocsdkserver;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import com.goodocom.bttek.R;
import com.goodocom.bttek.bt.aidl.GocCommandHfp;
import com.goodocom.bttek.bt.res.NfDef;

public class MainActivity extends Activity {
    private ServiceConnection connection = new ServiceConnection() {
        /* class com.goodocom.gocsdkserver.MainActivity.AnonymousClass1 */

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MainActivity.this.hfp = GocCommandHfp.Stub.asInterface(iBinder);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            MainActivity.this.hfp = null;
        }
    };
    private GocCommandHfp hfp = null;

    /* access modifiers changed from: protected */
    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(NfDef.CLASS_SERVICE_HFP);
        intent.setPackage(getPackageName());
        bindService(intent, this.connection, 1);
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT < 23) {
            return;
        }
        if (checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != -1) {
            requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 100);
        } else {
            Log.e("gocPermission", "权限已申请");
        }
    }

    /* access modifiers changed from: protected */
    @Override // android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        unbindService(this.connection);
    }
}

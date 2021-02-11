package com.goodocom.gocsdk.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import com.goodocom.bttek.bt.aidl.GocHfpClientCall;
import com.goodocom.bttek.bt.base.jar.GocJar;
import com.goodocom.bttek.bt.base.listener.GocBluetoothPhoneChangeListener;
import com.goodocom.gocsdk.BuildConfig;
import com.goodocom.gocsdk.db.GocDatabase;

public class HfpCallBaseActivity extends Activity {
    public static final String TAG = HfpCallBaseActivity.class.getSimpleName();
    public Handler mHanlder = new Handler();
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        /* class com.goodocom.gocsdk.activity.HfpCallBaseActivity.AnonymousClass2 */

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            intent.getAction();
        }
    };

    /* access modifiers changed from: protected */
    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.huaqin.pbap.complete");
        registerReceiver(this.receiver, intentFilter);
        registCallPhone();
    }

    public String numberForName(String number) {
        return "unknow";
    }

    public void registCallPhone() {
        GocJar.registerBluetoothPhoneChangeListener(new GocBluetoothPhoneChangeListener() {
            /* class com.goodocom.gocsdk.activity.HfpCallBaseActivity.AnonymousClass1 */

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothPhoneChangeListener
            public void onHfpCallChanged(String s, GocHfpClientCall gocHfpClientCall) {
                String str = HfpCallBaseActivity.TAG;
                Log.e(str, "s : " + s + "   gocHfpClientCall " + gocHfpClientCall.getNumber() + "   gocHfpClientCall  " + gocHfpClientCall.getState() + "  gocHfpClientCall  " + gocHfpClientCall.isMultiParty() + "   gocHfpClientCall   " + gocHfpClientCall.isOutgoing());
                if (!gocHfpClientCall.isOutgoing()) {
                    Log.e(HfpCallBaseActivity.TAG, "outgonging--------false------------------");
                    if (gocHfpClientCall.getState() == 4) {
                        Log.e(HfpCallBaseActivity.TAG, "----------------------CALL_STATE_INCOMING---------------------");
                        ComponentName cn = new ComponentName(BuildConfig.APPLICATION_ID, "com.goodocom.gocsdk.activity.IncomingActivity");
                        Intent intent = new Intent();
                        intent.putExtra(GocDatabase.COL_NUMBER, gocHfpClientCall.getNumber());
                        intent.setComponent(cn);
                        HfpCallBaseActivity.this.startActivity(intent);
                    }
                } else if (gocHfpClientCall.getState() == 2) {
                    Log.e(HfpCallBaseActivity.TAG, "----------------------CALL_STATE_DIALING---------------------");
                    ComponentName cn2 = new ComponentName(BuildConfig.APPLICATION_ID, "com.goodocom.gocsdk.activity.CallActivity");
                    Intent intent2 = new Intent();
                    intent2.putExtra(GocDatabase.COL_NUMBER, gocHfpClientCall.getNumber());
                    intent2.setComponent(cn2);
                    HfpCallBaseActivity.this.startActivity(intent2);
                }
                Log.e(HfpCallBaseActivity.TAG, "----------------------start  onCallStateChanged---------------------");
                HfpCallBaseActivity.this.onCallStateChanged(s, gocHfpClientCall);
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothPhoneChangeListener
            public void onHfpCallingTimeChanged(String s) {
                String str = HfpCallBaseActivity.TAG;
                Log.e(str, "onHfpCallingTimeChanged : " + s);
                HfpCallBaseActivity.this.onHfpCallingTime(s);
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothPhoneChangeListener
            public void onPbapStateChanged(int i) {
            }
        });
    }

    public void onCallStateChanged(String s, GocHfpClientCall gocHfpClientCall) {
    }

    public void onHfpCallingTime(String s) {
    }

    public static String getContactName(String number, ContentResolver resolver) {
        Exception ex;
        Exception e;
        if (TextUtils.isEmpty(number)) {
            return null;
        }
        String[] projection = {"_id", "display_name"};
        Cursor cursor = null;
        try {
            try {
                cursor = resolver.query(Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number)), projection, null, null, null);
            } catch (Exception e2) {
                ex = e2;
                ex.printStackTrace();
                try {
                    try {
                        cursor = resolver.query(Uri.withAppendedPath(Contacts.Phones.CONTENT_FILTER_URL, Uri.encode(number)), projection, null, null, null);
                    } catch (Exception e3) {
                        e = e3;
                        e.printStackTrace();
                        String ret = null;
                        ret = cursor.getString(1);
                        cursor.close();
                        return ret;
                    }
                } catch (Exception e4) {
                    e = e4;
                    e.printStackTrace();
                    String ret = null;
                    ret = cursor.getString(1);
                    cursor.close();
                    return ret;
                }
                String ret = null;
                ret = cursor.getString(1);
                cursor.close();
                return ret;
            }
        } catch (Exception e5) {
            ex = e5;
            ex.printStackTrace();
            cursor = resolver.query(Uri.withAppendedPath(Contacts.Phones.CONTENT_FILTER_URL, Uri.encode(number)), projection, null, null, null);
            String ret = null;
            ret = cursor.getString(1);
            cursor.close();
            return ret;
        }
        String ret = null;
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            ret = cursor.getString(1);
        }
        cursor.close();
        return ret;
    }

    /* access modifiers changed from: protected */
    @Override // android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.receiver);
    }
}

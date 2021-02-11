package com.goodocom.bttek.bt.demo.ui;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.goodocom.bttek.R;
import com.goodocom.bttek.bt.aidl.UiCommand;
import com.goodocom.bttek.bt.demo.service.BtService;

public class MainMenuActivity extends Activity {
    private static boolean D = true;
    private static String TAG = "NfDemo_MainMenu";
    private Button button_A2dpPage;
    private Button button_Back;
    private Button button_GattPage;
    private Button button_HfpPage;
    private Button button_HidPage;
    private Button button_MapPage;
    private Button button_OppPage;
    private Button button_PbapPage;
    private Button button_SettingPage;
    private Button button_SppPage;
    private UiCommand mCommand;
    private ServiceConnection mConnection = new ServiceConnection() {
        /* class com.goodocom.bttek.bt.demo.ui.MainMenuActivity.AnonymousClass1 */

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.e(MainMenuActivity.TAG, "ready  onServiceConnected");
            MainMenuActivity.this.mCommand = UiCommand.Stub.asInterface(service);
            if (MainMenuActivity.this.mCommand == null) {
                Log.e(MainMenuActivity.TAG, "mCommand is null!!");
                Toast.makeText(MainMenuActivity.this.getApplicationContext(), "UiService is null!", 0).show();
                MainMenuActivity.this.finish();
            }
            new Thread(new Runnable() {
                /* class com.goodocom.bttek.bt.demo.ui.MainMenuActivity.AnonymousClass1.AnonymousClass1 */

                @Override // java.lang.Runnable
                public void run() {
                    MainMenuActivity.this.runOnUiThread(new Runnable() {
                        /* class com.goodocom.bttek.bt.demo.ui.MainMenuActivity.AnonymousClass1.AnonymousClass1.AnonymousClass1 */

                        @Override // java.lang.Runnable
                        public void run() {
                            while (!MainMenuActivity.this.mCommand.isBluetoothServiceReady()) {
                                try {
                                    try {
                                        Thread.sleep(50);
                                        Log.v(MainMenuActivity.TAG, "Piggy Check waiting for isBluetoothServiceReady");
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                } catch (RemoteException e2) {
                                    e2.printStackTrace();
                                    return;
                                }
                            }
                            String nf_version = MainMenuActivity.this.mCommand.getNfServiceVersionName();
                            String service_version = MainMenuActivity.this.mCommand.getUiServiceVersionName();
                            TextView textView = MainMenuActivity.this.text_nf_version;
                            textView.setText("nFore BT version: " + nf_version);
                            TextView textView2 = MainMenuActivity.this.text_service_version;
                            textView2.setText("Demo Service version: " + service_version);
                            TextView textView3 = MainMenuActivity.this.text_ui_version;
                            textView3.setText("Demo UI Version: " + MainMenuActivity.this.mVersionName);
                        }
                    });
                }
            }).start();
            Log.e(MainMenuActivity.TAG, "end  onServiceConnected");
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName className) {
            Log.e(MainMenuActivity.TAG, "onServiceDisconnected");
            MainMenuActivity.this.mCommand = null;
        }
    };
    private String mVersionName;
    private TextView text_nf_version;
    private TextView text_service_version;
    private TextView text_ui_version;

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (D) {
            Log.e(TAG, "+++ ON CREATE +++");
        }
        setContentView(R.layout.main_menu);
        initButton();
        Intent intent = new Intent(this, BtService.class);
        startService(intent);
        bindService(intent, this.mConnection, 1);
        PackageInfo mPackageInfo = null;
        try {
            mPackageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Exception when getting package information !!!");
        }
        if (mPackageInfo != null) {
            this.mVersionName = mPackageInfo.versionName;
            TAG = "NfDemo_MainMenu_" + this.mVersionName;
        } else {
            Log.e(TAG, "In onCreate() : mPackageInfo is null");
        }
        if (getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
            Log.d(TAG, "Piggy Check system support BLE");
        } else {
            Log.d(TAG, "Piggy Check system not support BLE");
        }
    }

    @Override // android.app.Activity
    public void onStart() {
        super.onStart();
        if (D) {
            Log.e(TAG, "++ ON START ++");
        }
    }

    @Override // android.app.Activity
    public synchronized void onResume() {
        super.onResume();
        if (D) {
            Log.e(TAG, "++ ON Resume ++");
        }
    }

    @Override // android.app.Activity
    public synchronized void onPause() {
        super.onPause();
        if (D) {
            Log.e(TAG, "- ON PAUSE -");
        }
    }

    @Override // android.app.Activity
    public void onStop() {
        super.onStop();
        if (D) {
            Log.e(TAG, "-- ON STOP --");
        }
    }

    @Override // android.app.Activity
    public void onDestroy() {
        if (D) {
            Log.e(TAG, "--- ON DESTROY ---");
        }
        unbindService(this.mConnection);
        super.onDestroy();
    }

    public void initButton() {
        this.text_nf_version = (TextView) findViewById(R.id.text_nf_version);
        this.text_service_version = (TextView) findViewById(R.id.text_service_version);
        this.text_ui_version = (TextView) findViewById(R.id.text_ui_version);
        this.button_Back = (Button) findViewById(R.id.button_back);
        this.button_OppPage = (Button) findViewById(R.id.button_opp);
        this.button_HfpPage = (Button) findViewById(R.id.button_hfp);
        this.button_A2dpPage = (Button) findViewById(R.id.button_a2dp);
        this.button_PbapPage = (Button) findViewById(R.id.button_pbap);
        this.button_HidPage = (Button) findViewById(R.id.button_hid);
        this.button_SettingPage = (Button) findViewById(R.id.button_setting);
        this.button_SppPage = (Button) findViewById(R.id.button_spp);
        this.button_MapPage = (Button) findViewById(R.id.button_map);
        this.button_GattPage = (Button) findViewById(R.id.button_gatt);
        this.button_OppPage.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.MainMenuActivity.AnonymousClass2 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(MainMenuActivity.TAG, "OppPage onClicked");
                Intent newAct = new Intent();
                newAct.setClass(MainMenuActivity.this, OppPageActivity.class);
                MainMenuActivity.this.startActivity(newAct);
            }
        });
        this.button_HfpPage.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.MainMenuActivity.AnonymousClass3 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(MainMenuActivity.TAG, "HfpPage onClicked");
                Intent newAct = new Intent();
                newAct.setClass(MainMenuActivity.this, HfpPageActivity.class);
                MainMenuActivity.this.startActivity(newAct);
            }
        });
        this.button_A2dpPage.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.MainMenuActivity.AnonymousClass4 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(MainMenuActivity.TAG, "A2dpPage onClicked");
                Intent newAct = new Intent();
                newAct.setClass(MainMenuActivity.this, A2dpPageActivity.class);
                MainMenuActivity.this.startActivity(newAct);
            }
        });
        this.button_PbapPage.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.MainMenuActivity.AnonymousClass5 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(MainMenuActivity.TAG, "PbapPage onClicked");
                Intent newAct = new Intent();
                newAct.setClass(MainMenuActivity.this, PbapPageActivity.class);
                MainMenuActivity.this.startActivity(newAct);
            }
        });
        this.button_SppPage.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.MainMenuActivity.AnonymousClass6 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(MainMenuActivity.TAG, "SppPage onClicked");
                Intent newAct = new Intent();
                newAct.setClass(MainMenuActivity.this, SppPageActivity.class);
                MainMenuActivity.this.startActivity(newAct);
            }
        });
        this.button_MapPage.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.MainMenuActivity.AnonymousClass7 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(MainMenuActivity.TAG, "MapPage onClicked");
                Intent newAct = new Intent();
                newAct.setClass(MainMenuActivity.this, MapPageActivity.class);
                MainMenuActivity.this.startActivity(newAct);
            }
        });
        this.button_HidPage.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.MainMenuActivity.AnonymousClass8 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(MainMenuActivity.TAG, "HidPage onClicked");
                Intent newAct = new Intent();
                newAct.setClass(MainMenuActivity.this, HidPageActivity.class);
                MainMenuActivity.this.startActivity(newAct);
            }
        });
        this.button_GattPage.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.MainMenuActivity.AnonymousClass9 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(MainMenuActivity.TAG, "GattPage onClicked");
                Intent newAct = new Intent();
                newAct.setClass(MainMenuActivity.this, GattServerPageActivity.class);
                MainMenuActivity.this.startActivity(newAct);
            }
        });
        this.button_SettingPage.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.MainMenuActivity.AnonymousClass10 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(MainMenuActivity.TAG, "SettingPage onClicked");
                Intent newAct = new Intent();
                newAct.setClass(MainMenuActivity.this, SettingPageActivity.class);
                MainMenuActivity.this.startActivity(newAct);
            }
        });
        this.button_Back.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.MainMenuActivity.AnonymousClass11 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(MainMenuActivity.TAG, "BackButton onClicked");
                MainMenuActivity.this.finish();
            }
        });
    }
}

package com.goodocom.bttek.bt.demo.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.goodocom.bttek.R;
import com.goodocom.bttek.bt.aidl.UiCallbackOpp;
import com.goodocom.bttek.bt.aidl.UiCommand;
import com.goodocom.bttek.bt.demo.service.BtService;

public class OppDownloadProgressActivity extends Activity {
    private static String TAG = "NfDemo_OppDownloadProgressPage";
    private Button cancel_download;
    private UiCommand mCommand;
    private ServiceConnection mConnection = new ServiceConnection() {
        /* class com.goodocom.bttek.bt.demo.ui.OppDownloadProgressActivity.AnonymousClass5 */

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.e(OppDownloadProgressActivity.TAG, "ready  onServiceConnected");
            OppDownloadProgressActivity.this.mCommand = UiCommand.Stub.asInterface(service);
            if (OppDownloadProgressActivity.this.mCommand == null) {
                Log.e(OppDownloadProgressActivity.TAG, "mCommand is null!!");
                Toast.makeText(OppDownloadProgressActivity.this.getApplicationContext(), "UiService is null!", 0).show();
                OppDownloadProgressActivity.this.finish();
            }
            try {
                OppDownloadProgressActivity.this.mCommand.registerOppCallback(OppDownloadProgressActivity.this.mOppCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.e(OppDownloadProgressActivity.TAG, "end  onServiceConnected");
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName className) {
            Log.e(OppDownloadProgressActivity.TAG, "onServiceDisconnected");
        }
    };
    private UiCallbackOpp mOppCallback = new UiCallbackOpp.Stub() {
        /* class com.goodocom.bttek.bt.demo.ui.OppDownloadProgressActivity.AnonymousClass6 */

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackOpp
        public void onOppServiceReady() throws RemoteException {
            Log.i(OppDownloadProgressActivity.TAG, "onOppServiceReady");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackOpp
        public void onOppStateChanged(String address, int preState, int currentState, int reason) throws RemoteException {
            if (currentState == 110) {
                OppPageActivity.mOppDownloadProgress = 100;
                OppDownloadProgressActivity.this.finish();
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackOpp
        public void onOppReceiveFileInfo(String fileName, int fileSize, String deviceName, String savePath) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackOpp
        public void onOppReceiveProgress(int receivedOffset) throws RemoteException {
        }
    };
    Handler myHandle = new Handler() {
        /* class com.goodocom.bttek.bt.demo.ui.OppDownloadProgressActivity.AnonymousClass4 */

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            OppDownloadProgressActivity.this.progressBar_opp.setProgress(OppPageActivity.mOppDownloadProgress);
        }
    };
    private ProgressBar progressBar_opp;

    /* access modifiers changed from: protected */
    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Download Progress");
        setContentView(R.layout.activity_opp_download_progress);
        Intent intent = new Intent(this, BtService.class);
        startService(intent);
        bindService(intent, this.mConnection, 1);
        initView();
    }

    @Override // android.app.Activity
    public void onStart() {
        super.onStart();
        Log.e(TAG, "++ ON START ++");
    }

    @Override // android.app.Activity
    public synchronized void onResume() {
        super.onResume();
        Log.e(TAG, "++ ON Resume ++");
    }

    @Override // android.app.Activity
    public synchronized void onPause() {
        super.onPause();
        Log.e(TAG, "- ON PAUSE -");
    }

    @Override // android.app.Activity
    public void onStop() {
        super.onStop();
        Log.e(TAG, "-- ON STOP --");
    }

    @Override // android.app.Activity
    public void onDestroy() {
        Log.e(TAG, "--- ON DESTROY ---");
        super.onDestroy();
    }

    private void showDownloadSuccessDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Download File Success");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.OppDownloadProgressActivity.AnonymousClass1 */

            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int whichButton) {
                OppDownloadProgressActivity.this.startActivity(new Intent(OppDownloadProgressActivity.this.getApplicationContext(), OppPageActivity.class));
                OppDownloadProgressActivity.this.finish();
            }
        });
        alert.show();
    }

    public void initView() {
        this.progressBar_opp = (ProgressBar) findViewById(R.id.progressBar_opp);
        this.cancel_download = (Button) findViewById(R.id.cancel_download);
        this.progressBar_opp.setProgress(OppPageActivity.mOppDownloadProgress);
        new Thread(new Runnable() {
            /* class com.goodocom.bttek.bt.demo.ui.OppDownloadProgressActivity.AnonymousClass2 */

            @Override // java.lang.Runnable
            public void run() {
                while (OppPageActivity.mOppDownloadProgress < 100) {
                    try {
                        OppDownloadProgressActivity.this.myHandle.sendMessage(OppDownloadProgressActivity.this.myHandle.obtainMessage());
                        Thread.sleep(10);
                        String str = OppDownloadProgressActivity.TAG;
                        Log.e(str, "----- percentage = " + OppPageActivity.mOppDownloadProgress + " -----");
                    } catch (Throwable th) {
                    }
                }
                OppPageActivity.mOppDownloadProgress = 0;
                OppDownloadProgressActivity.this.finish();
            }
        }).start();
        this.cancel_download.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.OppDownloadProgressActivity.AnonymousClass3 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(OppDownloadProgressActivity.TAG, "cancel_download on click");
                try {
                    OppPageActivity.mCommand.reqOppInterruptReceiveFile();
                    Log.v(OppDownloadProgressActivity.TAG, "reqOppInterruptReceiveFile.click");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                OppPageActivity.mOppDownloadProgress = 100;
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
                OppPageActivity.mOppDownloadProgress = 0;
                OppDownloadProgressActivity.this.finish();
            }
        });
    }
}

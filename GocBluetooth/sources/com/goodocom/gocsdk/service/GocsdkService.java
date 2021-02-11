package com.goodocom.gocsdk.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.util.Log;
import com.goodocom.gocsdk.Commands;
import com.goodocom.gocsdk.IGocsdkCallback;
import com.goodocom.gocsdk.SerialPort;
import com.goodocom.gocsdk.vcard.VCardBuilder;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class GocsdkService extends Service {
    private static final String ACTION_CLOSE_BT = "android.intent.action.BT_OFF";
    private static final String ACTION_OPEN_BT = "android.intent.action.BT_ON";
    public static final int MSG_SERIAL_RECEIVED = 2;
    public static final int MSG_START_SERIAL = 1;
    private static final int RESTART_DELAY = 2000;
    public static final String TAG = "GocsdkService";
    private static Handler hand = null;
    private RemoteCallbackList<IGocsdkCallback> callbacks;
    private Handler handler = new Handler(Looper.getMainLooper()) {
        /* class com.goodocom.gocsdk.service.GocsdkService.AnonymousClass2 */

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                Log.d("app", "serialThread start!");
                GocsdkService gocsdkService = GocsdkService.this;
                gocsdkService.serialThread = new SerialThread();
                GocsdkService.this.serialThread.start();
            } else if (msg.what == 2) {
                GocsdkService.this.parser.onBytes((byte[]) msg.obj);
            }
        }
    };
    private CommandParser parser;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        /* class com.goodocom.gocsdk.service.GocsdkService.AnonymousClass1 */

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (!GocsdkService.ACTION_CLOSE_BT.equals(intent.getAction())) {
                GocsdkService.ACTION_OPEN_BT.equals(intent.getAction());
            }
        }
    };
    private volatile boolean running = true;
    private SerialThread serialThread = null;
    private final boolean use_socket = false;

    @Override // android.app.Service
    public void onCreate() {
        Log.d("app", "Service onCreate");
        this.callbacks = new RemoteCallbackList<>();
        this.parser = new CommandParser(this.callbacks, this);
        hand = this.handler;
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_OPEN_BT);
        filter.addAction(ACTION_CLOSE_BT);
        registerReceiver(this.receiver, filter);
        super.onCreate();
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("app", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override // android.app.Service
    public void onDestroy() {
        this.running = false;
        this.callbacks.kill();
        Log.d("app", "Service onDestroy");
        unregisterReceiver(this.receiver);
        super.onDestroy();
    }

    public static Handler getHandler() {
        return hand;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        Log.d("app", "onBind");
        return new GocsdkServiceImp(this);
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        Log.d("app", "onUnbind");
        return super.onUnbind(intent);
    }

    /* access modifiers changed from: package-private */
    public class SerialThread extends Thread {
        private byte[] buffer = new byte[1024];
        private InputStream inputStream;
        private OutputStream outputStream = null;

        public void write(byte[] buf) {
            OutputStream outputStream2 = this.outputStream;
            if (outputStream2 != null) {
                try {
                    outputStream2.write(buf);
                } catch (IOException e) {
                }
            }
        }

        public SerialThread() {
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            SerialPort serial = null;
            try {
                Log.d("app", "use serial!");
                SerialPort serial2 = new SerialPort(new File("/dev/goc_serial"), 115200, 0);
                Log.d("app", "serial not is null!");
                this.inputStream = serial2.getInputStream();
                this.outputStream = serial2.getOutputStream();
                while (GocsdkService.this.running) {
                    int n = this.inputStream.read(this.buffer);
                    if (n < 0) {
                        serial2.close();
                        throw new IOException("n==-1");
                    }
                    byte[] data = new byte[n];
                    System.arraycopy(this.buffer, 0, data, 0, n);
                    GocsdkService.this.handler.sendMessage(GocsdkService.this.handler.obtainMessage(2, data));
                }
                try {
                    serial2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e2) {
                if (0 != 0) {
                    try {
                        serial.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                GocsdkService.this.handler.sendEmptyMessageDelayed(1, 2000);
            }
        }
    }

    public void write(String str) {
        if (this.serialThread != null) {
            Log.d("app", "write:" + str);
            SerialThread serialThread2 = this.serialThread;
            serialThread2.write((Commands.COMMAND_HEAD + str + VCardBuilder.VCARD_END_OF_LINE).getBytes());
        }
    }

    public void registerCallback(IGocsdkCallback callback) {
        Log.d(TAG, "registerCallback");
        this.callbacks.register(callback);
        Log.d(TAG, "callback count:" + this.callbacks.getRegisteredCallbackCount());
    }

    public void unregisterCallback(IGocsdkCallback callback) {
        this.callbacks.unregister(callback);
    }
}

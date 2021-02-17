package com.goodocom.gocsdk.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.audiofx.AcousticEchoCanceler;
import android.media.audiofx.NoiseSuppressor;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import com.goodocom.gocsdk.Config;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ScoService extends Service {
    private static ScoService INSTANCE = null;
    private static final int MSG_CLOSE = 2;
    private static final int MSG_INIT = 4;
    private static final int MSG_OPEN = 1;
    private static final int MSG_START_SCO = 3;
    private static final int RESTART_DELAY = 2000;
    private AudioRecord audioRecord;
    private AudioTrack audioTrack;
    private AcousticEchoCanceler canceler;
    private Handler handler = new Handler(Looper.getMainLooper()) {
        /* class com.goodocom.gocsdk.service.ScoService.AnonymousClass1 */

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                ScoService.this.audioRecord.startRecording();
                ScoService.this.audioTrack.play();
                ScoService.this.sco_running = true;
            } else if (msg.what == 2) {
                ScoService.this.sco_running = false;
                ScoService.this.audioRecord.stop();
                ScoService.this.audioTrack.pause();
                ScoService.this.audioTrack.flush();
            } else if (msg.what == 3) {
                ScoService scoService = ScoService.this;
                scoService.scoThread = new ScoThread();
                ScoService.this.scoThread.start();
                ScoService scoService2 = ScoService.this;
                scoService2.recordThread = new RecordThread();
                ScoService.this.recordThread.start();
            } else if (msg.what == 4 && !ScoService.this.init()) {
                ScoService.this.running = false;
                ScoService.this.stopSelf();
            }
        }
    };
    private NoiseSuppressor ns;
    private volatile OutputStream outputStream = null;
    private RecordThread recordThread = null;
    private boolean running = true;
    private ScoThread scoThread = null;
    private volatile boolean sco_running = false;

    @Override // android.app.Service
    public void onCreate() {
        INSTANCE = this;
        this.handler.sendEmptyMessage(4);
        this.handler.sendEmptyMessage(3);
        super.onCreate();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean init() {
        this.audioRecord = new AudioRecord(7, 8000, 16, 2, AudioRecord.getMinBufferSize(8000, 16, 2));
        AudioRecord audioRecord2 = this.audioRecord;
        if (audioRecord2 == null || audioRecord2.getState() == 0) {
            return false;
        }
        if (AcousticEchoCanceler.isAvailable()) {
            this.canceler = AcousticEchoCanceler.create(this.audioRecord.getAudioSessionId());
            AcousticEchoCanceler acousticEchoCanceler = this.canceler;
            if (acousticEchoCanceler != null) {
                acousticEchoCanceler.setEnabled(true);
            }
        }
        if (NoiseSuppressor.isAvailable()) {
            this.ns = NoiseSuppressor.create(this.audioRecord.getAudioSessionId());
            NoiseSuppressor noiseSuppressor = this.ns;
            if (noiseSuppressor != null) {
                noiseSuppressor.setEnabled(true);
            }
        }
        this.audioTrack = new AudioTrack(0, 8000, 4, 2, AudioTrack.getMinBufferSize(8000, 4, 2), 1, this.audioRecord.getAudioSessionId());
        return true;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void startSco() {
        ScoService scoService = INSTANCE;
        if (scoService != null) {
            scoService.handler.sendEmptyMessage(1);
        }
    }

    public static void stopSco() {
        ScoService scoService = INSTANCE;
        if (scoService != null) {
            scoService.handler.sendEmptyMessage(2);
        }
    }

    /* access modifiers changed from: private */
    public class RecordThread extends Thread {
        private RecordThread() {
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            Process.setThreadPriority(-19);
            byte[] buffer = new byte[1024];
            FileOutputStream out = null;
            try {
                out = new FileOutputStream("/mnt/sdcard/sco.data", false);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            while (ScoService.this.running) {
                if (!ScoService.this.sco_running) {
                    try {
                        Thread.sleep(10, 0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else if (ScoService.this.outputStream == null) {
                    try {
                        Thread.sleep(10, 0);
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                } else {
                    int len = ScoService.this.audioRecord.read(buffer, 0, 1024);
                    if (len > 0) {
                        try {
                            ScoService.this.outputStream.write(buffer, 0, len);
                            if (out != null) {
                                out.write(buffer, 0, len);
                            }
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public class ScoThread extends Thread {
        private LocalSocketAddress address = new LocalSocketAddress(Config.SCO_SOCKET_NAME, LocalSocketAddress.Namespace.RESERVED);
        private byte[] buffer = new byte[4096];
        private LocalSocket client = new LocalSocket();
        private InputStream inputStream;

        public ScoThread() {
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                this.client.connect(this.address);
                this.inputStream = this.client.getInputStream();
                ScoService.this.outputStream = this.client.getOutputStream();
                while (ScoService.this.running) {
                    int n = this.inputStream.read(this.buffer);
                    if (n < 0) {
                        throw new IOException("n==-1");
                    } else if (ScoService.this.audioTrack != null) {
                        ScoService.this.audioTrack.write(this.buffer, 0, n);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    this.client.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                ScoService.this.handler.sendEmptyMessageDelayed(3, 2000);
            }
        }
    }
}

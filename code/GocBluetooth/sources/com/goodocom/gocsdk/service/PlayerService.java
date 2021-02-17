package com.goodocom.gocsdk.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.goodocom.gocsdk.Config;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PlayerService extends Service implements MediaPlayer.OnCompletionListener {
    private static final int MSG_CONTROL_RECEIVED = 3;
    private static final int MSG_START_CONTROL = 1;
    private static final int MSG_START_DATA = 2;
    private static final int RESTART_DELAY = 2000;
    private static final String TAG = "goc";
    private volatile AudioTrack audioTrack = null;
    private volatile int channels = -1;
    private StringBuilder controlBuilder = new StringBuilder();
    private ControlThread controlThread = null;
    private DataThread dataThread = null;
    private Handler handler = new Handler(Looper.getMainLooper()) {
        /* class com.goodocom.gocsdk.service.PlayerService.AnonymousClass1 */

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                PlayerService playerService = PlayerService.this;
                playerService.controlThread = new ControlThread();
                PlayerService.this.controlThread.start();
                if (PlayerService.this.audioTrack != null) {
                    PlayerService.this.audioTrack.pause();
                }
            } else if (msg.what == 2) {
                PlayerService playerService2 = PlayerService.this;
                playerService2.dataThread = new DataThread();
                PlayerService.this.dataThread.start();
            } else if (msg.what == 3) {
                for (byte b : (byte[]) msg.obj) {
                    PlayerService.this.onControlByte(b);
                }
            }
        }
    };
    private AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        /* class com.goodocom.gocsdk.service.PlayerService.AnonymousClass2 */

        @Override // android.media.AudioManager.OnAudioFocusChangeListener
        public void onAudioFocusChange(int focusChange) {
            if (PlayerService.this.audioTrack != null) {
                if (focusChange == -3) {
                    PlayerService.this.audioTrack.setStereoVolume(0.5f, 0.5f);
                } else if (focusChange == -2) {
                    PlayerService.this.audioTrack.setStereoVolume(0.0f, 0.0f);
                } else if (focusChange != -1 && focusChange == 1) {
                    PlayerService.this.audioTrack.setStereoVolume(1.0f, 1.0f);
                }
            }
        }
    };
    private AudioManager mAudioManager = null;
    private volatile MediaPlayer ringPlayer = null;
    private boolean ringing = false;
    private volatile boolean running = true;
    private volatile int sampleBits = -1;
    private volatile int sampleRate = -1;

    private void openAudioTrack(int rate, int ch, int bits) {
        if (rate == this.sampleRate && ch == this.channels && bits == this.sampleBits && this.audioTrack != null) {
            this.audioTrack.play();
            return;
        }
        this.sampleRate = rate;
        this.channels = ch;
        this.sampleBits = bits;
        int minBufSize = AudioTrack.getMinBufferSize(this.sampleRate, this.channels == 2 ? 12 : 4, this.sampleBits == 16 ? 2 : 3) * 4;
        if (this.audioTrack != null) {
            this.audioTrack.stop();
            this.audioTrack = null;
        }
        this.audioTrack = new AudioTrack(3, this.sampleRate, this.channels == 2 ? 12 : 4, this.sampleBits == 16 ? 2 : 3, minBufSize, 1);
        int realRate = this.sampleRate;
        this.audioTrack.setPlaybackRate(realRate);
        Log.d(TAG, "real play rate " + realRate);
        this.audioTrack.play();
    }

    private void onControlCommand(String cmd) {
        Log.d(TAG, cmd);
        if (cmd.startsWith("open")) {
            if (cmd.length() < 28) {
                Log.e(TAG, "get error open:" + cmd);
                return;
            }
            int rate = Integer.valueOf(cmd.substring(4, 12), 16).intValue();
            int ch = Integer.valueOf(cmd.substring(12, 20), 16).intValue();
            int bits = Integer.valueOf(cmd.substring(20, 28), 16).intValue();
            Log.d(TAG, "open rate:" + rate + " channels:" + ch + " bits:" + bits);
            openAudioTrack(rate, ch, bits);
            this.mAudioManager.requestAudioFocus(this.mAudioFocusChangeListener, 3, 1);
        } else if (cmd.startsWith("stop")) {
            Log.d(TAG, "stop");
            if (this.audioTrack != null) {
                this.audioTrack.pause();
                this.audioTrack.flush();
            }
            this.mAudioManager.abandonAudioFocus(this.mAudioFocusChangeListener);
        } else if (cmd.startsWith("ring start")) {
            this.ringing = true;
            ringStart();
        } else if (cmd.startsWith("ring stop")) {
            this.ringing = false;
            ringStop();
        } else if (cmd.startsWith("mute")) {
            Log.d(TAG, "PlayerService mute");
            if (this.audioTrack != null) {
                this.audioTrack.setStereoVolume(0.0f, 0.0f);
            }
        } else if (cmd.startsWith("unmute")) {
            Log.d(TAG, "PlayerService unmute");
            if (this.audioTrack != null) {
                this.audioTrack.setStereoVolume(1.0f, 1.0f);
            }
        } else if (cmd.startsWith("vol half")) {
            Log.d(TAG, "PlayerService vol half");
            if (this.audioTrack != null) {
                this.audioTrack.setStereoVolume(0.5f, 0.5f);
            }
        } else if (cmd.startsWith("vol normal")) {
            Log.d(TAG, "PlayerService vol normal");
            if (this.audioTrack != null) {
                this.audioTrack.setStereoVolume(1.0f, 1.0f);
            }
        } else if (cmd.startsWith("vol 0")) {
            Log.d(TAG, "PlayerService vol 0");
            if (this.audioTrack != null) {
                this.audioTrack.setStereoVolume(0.1f, 0.1f);
            }
        } else if (cmd.startsWith("vol 1")) {
            Log.d(TAG, "PlayerService vol 1");
            if (this.audioTrack != null) {
                this.audioTrack.setStereoVolume(0.2f, 0.2f);
            }
        } else if (cmd.startsWith("vol 2")) {
            Log.d(TAG, "PlayerService vol 2");
            if (this.audioTrack != null) {
                this.audioTrack.setStereoVolume(0.3f, 0.3f);
            }
        } else if (cmd.startsWith("vol 3")) {
            Log.d(TAG, "PlayerService vol 3");
            if (this.audioTrack != null) {
                this.audioTrack.setStereoVolume(0.4f, 0.4f);
            }
        } else if (cmd.startsWith("vol 4")) {
            Log.d(TAG, "PlayerService vol 4");
            if (this.audioTrack != null) {
                this.audioTrack.setStereoVolume(0.5f, 0.5f);
            }
        } else if (cmd.startsWith("vol 5")) {
            Log.d(TAG, "PlayerService vol 5");
            if (this.audioTrack != null) {
                this.audioTrack.setStereoVolume(0.6f, 0.6f);
            }
        } else if (cmd.startsWith("vol 6")) {
            Log.d(TAG, "PlayerService vol 6");
            if (this.audioTrack != null) {
                this.audioTrack.setStereoVolume(0.7f, 0.7f);
            }
        } else if (cmd.startsWith("vol 7")) {
            Log.d(TAG, "PlayerService vol 7");
            if (this.audioTrack != null) {
                this.audioTrack.setStereoVolume(0.8f, 0.8f);
            }
        } else if (cmd.startsWith("vol 8")) {
            Log.d(TAG, "PlayerService vol 8");
            if (this.audioTrack != null) {
                this.audioTrack.setStereoVolume(0.9f, 0.9f);
            }
        } else if (cmd.startsWith("vol 9")) {
            Log.d(TAG, "PlayerService vol 9");
            if (this.audioTrack != null) {
                this.audioTrack.setStereoVolume(1.0f, 1.0f);
            }
        } else if (cmd.startsWith("sco open")) {
            ScoService.startSco();
        } else if (cmd.startsWith("sco close")) {
            ScoService.stopSco();
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onControlByte(byte b) {
        if (b != 10) {
            this.controlBuilder.append((char) b);
            return;
        }
        onControlCommand(this.controlBuilder.toString());
        StringBuilder sb = this.controlBuilder;
        sb.delete(0, sb.length());
    }

    private void ringStart() {
        String path = null;
        try {
            String[] strArr = Config.RING_PATH;
            for (String p : strArr) {
                if (new File(p).exists()) {
                    path = p;
                }
            }
            if (path == null) {
                Log.e(TAG, "cannot find ring file");
                return;
            }
            this.ringPlayer = new MediaPlayer();
            try {
                this.ringPlayer.setDataSource(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.ringPlayer.prepareAsync();
            this.ringPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                /* class com.goodocom.gocsdk.service.PlayerService.AnonymousClass3 */

                @Override // android.media.MediaPlayer.OnPreparedListener
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
            this.ringPlayer.setOnCompletionListener(this);
            Log.d(TAG, "playing ring ");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void ringStop() {
        if (this.ringPlayer == null || !this.ringPlayer.isPlaying()) {
            Log.e(TAG, "ringPlayer is null!");
        } else {
            this.ringPlayer.stop();
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        Log.d(TAG, "onCreate");
        this.handler.sendEmptyMessage(1);
        this.handler.sendEmptyMessage(2);
        this.mAudioManager = (AudioManager) getSystemService("audio");
        super.onCreate();
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onDestroy() {
        this.ringPlayer.release();
        super.onDestroy();
    }

    /* access modifiers changed from: package-private */
    public class ControlThread extends Thread {
        private LocalSocketAddress address = new LocalSocketAddress(Config.CONTROL_SOCKET_NAME, LocalSocketAddress.Namespace.RESERVED);
        private byte[] buffer = new byte[102400];
        private LocalSocket client = new LocalSocket();
        private InputStream inputStream;

        public ControlThread() {
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                this.client.connect(this.address);
                this.inputStream = this.client.getInputStream();
                while (PlayerService.this.running) {
                    int n = this.inputStream.read(this.buffer);
                    if (n >= 0) {
                        byte[] data = new byte[n];
                        System.arraycopy(this.buffer, 0, data, 0, n);
                        PlayerService.this.handler.sendMessage(PlayerService.this.handler.obtainMessage(3, data));
                    } else {
                        throw new IOException("n==-1");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    this.client.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                PlayerService.this.handler.sendEmptyMessageDelayed(1, 2000);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public class DataThread extends Thread {
        private LocalSocketAddress address = new LocalSocketAddress(Config.DATA_SOCKET_NAME, LocalSocketAddress.Namespace.RESERVED);
        private byte[] buffer = new byte[4096];
        private LocalSocket client = new LocalSocket();
        private InputStream inputStream;

        public DataThread() {
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                this.client.connect(this.address);
                this.inputStream = this.client.getInputStream();
                while (PlayerService.this.running) {
                    int n = this.inputStream.read(this.buffer);
                    if (n < 0) {
                        throw new IOException("n==-1");
                    } else if (PlayerService.this.audioTrack != null) {
                        PlayerService.this.audioTrack.write(this.buffer, 0, n);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    this.client.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                PlayerService.this.handler.sendEmptyMessageDelayed(2, 2000);
            }
        }
    }

    @Override // android.media.MediaPlayer.OnCompletionListener
    public void onCompletion(MediaPlayer mp) {
        if (this.ringing) {
            ringStart();
        }
    }

    public static void appendMethodB(String fileName, byte[] content) {
        try {
            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File(fileName), true)));
            out.write(content);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

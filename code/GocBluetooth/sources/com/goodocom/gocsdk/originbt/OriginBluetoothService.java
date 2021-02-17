package com.goodocom.gocsdk.originbt;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaMetadata;
import android.media.session.PlaybackState;
import android.os.Binder;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import com.goodocom.bttek.bt.base.jar.GocJar;
import com.goodocom.gocsdk.GocAppData;
import com.goodocom.gocsdk.GocApplication;
import com.goodocom.gocsdk.Util.Utils;
import com.goodocom.gocsdk.fragment.FragmentMusic;
import com.goodocom.gocsdk.manager.BluetoothConnectManager;
import com.goodocom.gocsdk.service.GocsdkCallbackImp;

public class OriginBluetoothService extends Service {
    public static final String ACTION_TRACK_EVENT = "android.bluetooth.avrcp-controller.profile.action.TRACK_EVENT";
    public static final String EXTRA_METADATA = "android.bluetooth.avrcp-controller.profile.extra.METADATA";
    public static final String EXTRA_PLAYBACK = "android.bluetooth.avrcp-controller.profile.extra.PLAYBACK";
    public static final String TAG = OriginBluetoothService.class.getSimpleName();
    private A2dpMusicInfoListener mA2dpMusicInfoListener;
    private GocAppData mGocAppData;
    private MusicPlayListener mMusicPlayListener;
    private OriginBinder mOriginBinder;
    public OriginBluetoothConnectStateListener mOriginBluetoothConnectStateListener;
    public PbapDownloadFinishListener mPbapDownloadFinishListener;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        /* class com.goodocom.gocsdk.originbt.OriginBluetoothService.AnonymousClass1 */

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.e(GocsdkCallbackImp.TAG, "mReceiver: " + action);
            if ("com.huaqin.pbap.complete".equals(action)) {
                String str = OriginBluetoothService.TAG;
                Log.e(str, "PBAP_CONTACTS_DOWNLOAD_FINISH : mPbapDownloadFinishListener " + OriginBluetoothService.this.mPbapDownloadFinishListener);
                if (OriginBluetoothService.this.mPbapDownloadFinishListener != null) {
                    OriginBluetoothService.this.mPbapDownloadFinishListener.onContactsLoadFinish();
                    return;
                }
                return;
            }
            if ("android.bluetooth.device.action.BOND_STATE_CHANGED".equals(action)) {
                Log.e(GocsdkCallbackImp.TAG, "BluetoothDevice.ACTION_BOND_STATE_CHANGED>>>>");
            } else if ("com.huaqin.pbap.complete".equals(action)) {
                if (OriginBluetoothService.this.mPbapDownloadFinishListener != null) {
                    OriginBluetoothService.this.mPbapDownloadFinishListener.onCalllogLoadFinish();
                    return;
                }
                return;
            }
            BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
            Log.e(GocsdkCallbackImp.TAG, "serive device: " + device + "   mOriginBluetoothConnectStateListener:  " + OriginBluetoothService.this.mOriginBluetoothConnectStateListener);
            if ("android.bluetooth.device.action.ACL_CONNECTED".equals(action)) {
                if (OriginBluetoothService.this.mOriginBluetoothConnectStateListener != null) {
                    OriginBluetoothService.this.mOriginBluetoothConnectStateListener.OriginConnected(device, false);
                }
                Log.e(GocsdkCallbackImp.TAG, "serive device: ACTION_ACL_CONNECTED:" + device);
            } else if ("android.bluetooth.device.action.ACL_DISCONNECTED".equals(action)) {
                if (OriginBluetoothService.this.mOriginBluetoothConnectStateListener != null) {
                    OriginBluetoothService.this.mOriginBluetoothConnectStateListener.OriginDisconnected(device, false);
                }
                GocAppData.getInstance().mIsloadFinished = false;
                Log.e(GocsdkCallbackImp.TAG, "serive device: ACTION_ACL_DISCONNECTED:" + device);
            } else if (Utils.ACTION_CONNECTION_STATE_CHANGED.equals(action)) {
                int state = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0);
                Log.e(GocsdkCallbackImp.TAG, "BluetoothHeadsetClient  = " + state + "  address  " + device.getAddress());
                if (state == 2) {
                    GocAppData.getInstance().mCurrentBluetoothDevice = device;
                    GocJar.setBtMainDevices(device.getAddress());
                    if (OriginBluetoothService.this.mOriginBluetoothConnectStateListener != null) {
                        OriginBluetoothService.this.mOriginBluetoothConnectStateListener.OriginConnected(device, true);
                    }
                } else if (state == 0) {
                    if (OriginBluetoothService.this.mOriginBluetoothConnectStateListener != null) {
                        OriginBluetoothService.this.mOriginBluetoothConnectStateListener.OriginDisconnected(device, true);
                    }
                    GocAppData.getInstance().mIsloadFinished = false;
                } else if (state == 1 && OriginBluetoothService.this.mOriginBluetoothConnectStateListener != null) {
                    OriginBluetoothService.this.mOriginBluetoothConnectStateListener.OriginPaired(device);
                }
                Log.e(GocsdkCallbackImp.TAG, "BluetoothHeadsetClient.ACTION_CONNECTION_STATE_CHANGED>>>>>>>>>." + state);
            } else if ("android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED".equals(action)) {
                int value = intent.getIntExtra("android.bluetooth.adapter.extra.CONNECTION_STATE", 0);
                Log.e(GocsdkCallbackImp.TAG, "device: value>>>>>>>>serive>>>>>>>>>:" + value + "   device: " + device);
                if (value == 2) {
                    if (OriginBluetoothService.this.mOriginBluetoothConnectStateListener != null) {
                        OriginBluetoothService.this.mOriginBluetoothConnectStateListener.OriginConnected(device, false);
                    }
                } else if (value == 0) {
                    if (OriginBluetoothService.this.mOriginBluetoothConnectStateListener != null) {
                        OriginBluetoothService.this.mOriginBluetoothConnectStateListener.OriginDisconnected(device, false);
                    }
                    GocAppData.getInstance().mIsloadFinished = false;
                } else if (value == 1 && OriginBluetoothService.this.mOriginBluetoothConnectStateListener != null) {
                    OriginBluetoothService.this.mOriginBluetoothConnectStateListener.OriginPaired(device);
                }
            } else if (action.equals(FragmentMusic.PAUSE)) {
                String str2 = OriginBluetoothService.TAG;
                Log.e(str2, "mMusicPlayListener PAUSE: " + OriginBluetoothService.this.mMusicPlayListener);
                if (OriginBluetoothService.this.mMusicPlayListener != null) {
                    OriginBluetoothService.this.mMusicPlayListener.onMusicPlayChange(0);
                }
            } else if (action.equals(FragmentMusic.PLAY)) {
                String str3 = OriginBluetoothService.TAG;
                Log.e(str3, "mMusicPlayListener PLAY: " + OriginBluetoothService.this.mMusicPlayListener);
                if (OriginBluetoothService.this.mMusicPlayListener != null) {
                    OriginBluetoothService.this.mMusicPlayListener.onMusicPlayChange(1);
                }
            } else if (action.equals(OriginBluetoothService.ACTION_TRACK_EVENT)) {
                MediaMetadata mediaMetadata = (MediaMetadata) intent.getParcelableExtra(OriginBluetoothService.EXTRA_METADATA);
                PlaybackState playbackState = (PlaybackState) intent.getParcelableExtra(OriginBluetoothService.EXTRA_PLAYBACK);
                if (!(mediaMetadata == null || OriginBluetoothService.this.mA2dpMusicInfoListener == null)) {
                    OriginBluetoothService.this.mA2dpMusicInfoListener.onMediaMetadataUpdate(mediaMetadata);
                }
                if (!(playbackState == null || OriginBluetoothService.this.mA2dpMusicInfoListener == null)) {
                    OriginBluetoothService.this.mA2dpMusicInfoListener.onPlaybackStateUpdate(playbackState);
                }
                Log.e("apps", "ACTION_TRACK_EVENT: " + playbackState + "   mediaMetadata " + mediaMetadata);
            }
        }
    };

    public interface A2dpMusicInfoListener {
        void onMediaMetadataUpdate(MediaMetadata mediaMetadata);

        void onPlaybackStateUpdate(PlaybackState playbackState);
    }

    public interface MusicPlayListener {
        void onMusicPlayChange(int i);
    }

    public interface OriginBluetoothConnectStateListener {
        void OriginConnected(BluetoothDevice bluetoothDevice, boolean z);

        void OriginDisconnected(BluetoothDevice bluetoothDevice, boolean z);

        void OriginPaired(BluetoothDevice bluetoothDevice);
    }

    public interface PbapDownloadFinishListener {
        void onCalllogLoadFinish();

        void onContactsLoadFinish();
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        Log.e(GocsdkCallbackImp.TAG, "service >>onCreate");
        this.mGocAppData = GocAppData.getInstance();
        this.mOriginBinder = new OriginBinder();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.device.action.ACL_CONNECTED");
        intentFilter.addAction("android.bluetooth.device.action.ACL_DISCONNECTED");
        intentFilter.addAction("android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED");
        intentFilter.addAction("com.huaqin.pbap.complete");
        intentFilter.addAction("com.huaqin.pbap.complete");
        intentFilter.addAction("android.bluetooth.device.action.BOND_STATE_CHANGED");
        intentFilter.addAction(Utils.ACTION_CONNECTION_STATE_CHANGED);
        intentFilter.addAction(ACTION_TRACK_EVENT);
        intentFilter.addAction(FragmentMusic.PAUSE);
        intentFilter.addAction(FragmentMusic.PLAY);
        GocAppData.getInstance().mIsloadFinished = false;
        registerReceiver(this.mReceiver, intentFilter);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        Log.e(GocsdkCallbackImp.TAG, "service >>onBind");
        return this.mOriginBinder;
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        Log.e(GocsdkCallbackImp.TAG, "service >>onDestroy");
        GocAppData.getInstance().mIsloadFinished = false;
        if (!TextUtils.isEmpty(this.mGocAppData.mCurrentBluetoothDevice.getAddress())) {
            BluetoothConnectManager.getInstance(GocApplication.INSTANCE).disconnectPbap(this.mGocAppData.mCurrentBluetoothDevice);
            BluetoothConnectManager.getInstance(GocApplication.INSTANCE).disconnectHfpProfile(this.mGocAppData.mCurrentBluetoothDevice);
            BluetoothConnectManager.getInstance(GocApplication.INSTANCE).disconnectA2dpProfile(this.mGocAppData.mCurrentBluetoothDevice);
        }
        if (!TextUtils.isEmpty(this.mGocAppData.mOtherBluetoothDevice.getAddress())) {
            BluetoothConnectManager.getInstance(GocApplication.INSTANCE).disconnectPbap(this.mGocAppData.mOtherBluetoothDevice);
            BluetoothConnectManager.getInstance(GocApplication.INSTANCE).disconnectHfpProfile(this.mGocAppData.mOtherBluetoothDevice);
            BluetoothConnectManager.getInstance(GocApplication.INSTANCE).disconnectA2dpProfile(this.mGocAppData.mOtherBluetoothDevice);
        }
    }

    public class OriginBinder extends Binder {
        public OriginBinder() {
        }

        public OriginBluetoothService getOriginService() {
            return OriginBluetoothService.this;
        }
    }

    public void setA2dpMusicInfoListener(A2dpMusicInfoListener listener) {
        this.mA2dpMusicInfoListener = listener;
    }

    public void setMusicPlayListener(MusicPlayListener listener) {
        this.mMusicPlayListener = listener;
    }

    public void setOriginBluetoothConnectStateListener(OriginBluetoothConnectStateListener listener) {
        this.mOriginBluetoothConnectStateListener = listener;
    }

    public void setPbapDownloadFinishListener(PbapDownloadFinishListener listener) {
        this.mPbapDownloadFinishListener = listener;
    }
}

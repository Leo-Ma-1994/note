package com.goodocom.gocsdkserver;

import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;
import com.goodocom.bttek.BuildConfig;
import com.goodocom.bttek.bt.aidl.GocCallbackAvrcp;
import com.goodocom.bttek.bt.aidl.GocCommandAvrcp;
import com.goodocom.bttek.bt.bean.DeviceInfo;
import com.goodocom.bttek.bt.res.NfDef;
import java.util.List;

public class CommandAvrcpImp extends GocCommandAvrcp.Stub {
    public static final int AVRCP_CONNECTED = 2;
    public static final int AVRCP_DISCONNECTED = 1;
    public static final int AVRCP_EVENT_ID_ADDRESSED_PLAYER_CHANGED = 11;
    public static final int AVRCP_EVENT_ID_AVAILABLE_PLAYERS_CHANGED = 10;
    public static final int AVRCP_EVENT_ID_BATT_STATUS_CHANGED = 6;
    public static final int AVRCP_EVENT_ID_NOW_PLAYING_CONTENT_CHANGED = 9;
    public static final int AVRCP_EVENT_ID_PLAYBACK_POS_CHANGED = 5;
    public static final int AVRCP_EVENT_ID_PLAYBACK_STATUS_CHANGED = 1;
    public static final int AVRCP_EVENT_ID_PLAYER_APPLICATION_SETTING_CHANGED = 8;
    public static final int AVRCP_EVENT_ID_SYSTEM_STATUS_CHANGED = 7;
    public static final int AVRCP_EVENT_ID_TRACK_CHANGED = 2;
    public static final int AVRCP_EVENT_ID_TRACK_REACHED_END = 3;
    public static final int AVRCP_EVENT_ID_TRACK_REACHED_START = 4;
    public static final int AVRCP_EVENT_ID_UIDS_CHANGED = 12;
    public static final int AVRCP_EVENT_ID_VOLUME_CHANGED = 13;
    public static final int AVRCP_LIST_LOOP = 4;
    public static final int AVRCP_META_ATTRIBUTE_ID_ALBUM = 3;
    public static final int AVRCP_META_ATTRIBUTE_ID_ARTIST = 2;
    public static final int AVRCP_META_ATTRIBUTE_ID_GENRE = 6;
    public static final int AVRCP_META_ATTRIBUTE_ID_NUMBER_OF_MEDIA = 4;
    public static final int AVRCP_META_ATTRIBUTE_ID_SONG_LENGTH = 7;
    public static final int AVRCP_META_ATTRIBUTE_ID_TITLE = 1;
    public static final int AVRCP_META_ATTRIBUTE_ID_TOTAL_NUMBER_OF_MEDIA = 5;
    public static final int AVRCP_NON_SUPPORTED = 0;
    public static final int AVRCP_NORMAL_MODE = 2;
    public static final int AVRCP_RANDOM_PLAY = 1;
    public static final int AVRCP_SINGLE_CYCLE = 3;
    public static final int STATE_BROWSING = 145;
    public static final int STATE_CONNECTED = 140;
    public static final int STATE_CONNECTING = 120;
    public static final int STATE_NOT_INITIALIZED = 100;
    public static final int STATE_READY = 110;
    public static final String TAG = "GoodocomAvrcpImp";
    public static boolean currentPlaying = false;
    private boolean a2dpForceStartMusic = false;
    private int avrcp_volume = 100;
    private RemoteCallbackList<GocCallbackAvrcp> callbacks;
    private Object callbacksLock = new Object();
    public String current_song_album = BuildConfig.FLAVOR;
    public String current_song_artist = BuildConfig.FLAVOR;
    public int current_song_duration = 0;
    public String current_song_music = BuildConfig.FLAVOR;
    public int current_song_pos = 0;
    public int current_song_total = 0;
    public long lastLength = 0;
    public long lastPosition = 0;
    public int mPlayMode;
    private GocsdkService service;

    public void onInitSucceed() {
        currentPlaying = false;
    }

    private int status2State(int status) {
        if (status == 1 || status != 2) {
            return 110;
        }
        return 140;
    }

    private int status2pre(int status) {
        if (status == 1) {
            return 140;
        }
        if (status != 2) {
            return 110;
        }
        return 120;
    }

    public CommandAvrcpImp(GocsdkService service2) {
        this.service = service2;
        this.callbacks = new RemoteCallbackList<>();
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean isAvrcpServiceReady() throws RemoteException {
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean registerAvrcpCallback(GocCallbackAvrcp cb) throws RemoteException {
        cb.onAvrcpServiceReady();
        List<String> addrList = this.service.bluetooth.getProfileConnectedAddrByProile("AVRCP");
        for (int i = 0; i < addrList.size(); i++) {
            String addr = addrList.get(i);
            DeviceInfo _dev = this.service.bluetooth.getDeviceByAddr(addr);
            if (_dev == null) {
                Log.d(TAG, "registerAvrcpCallback dev is null");
            } else if (addr.length() > 0) {
                cb.onAvrcpStateChanged(addr, 110, status2State(_dev.avrcp_state));
            }
        }
        return this.callbacks.register(cb);
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean unregisterAvrcpCallback(GocCallbackAvrcp cb) throws RemoteException {
        return this.callbacks.unregister(cb);
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public int getAvrcpConnectionState() throws RemoteException {
        DeviceInfo _dev = this.service.bluetooth.getDeviceByAddr(this.service.bluetooth.cmdMainAddr);
        if (_dev == null) {
            Log.d(TAG, "getAvrcpConnectionState dev is null");
            return 110;
        }
        Log.e(TAG, "getAvrcpConnectionState currentStatus:" + _dev.avrcp_state);
        return status2State(_dev.avrcp_state);
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean isAvrcpConnected() throws RemoteException {
        DeviceInfo _dev = this.service.bluetooth.getDeviceByAddr(this.service.bluetooth.cmdMainAddr);
        if (_dev == null) {
            Log.d(TAG, "isAvrcpConnected dev is null");
            return false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("isAvrcpConnected:");
        sb.append(_dev.avrcp_state >= 2);
        Log.e(TAG, sb.toString());
        if (_dev.avrcp_state >= 2) {
            return true;
        }
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public String getAvrcpConnectedAddress() throws RemoteException {
        Log.e(TAG, "getAvrcpConnectedAddress");
        List<String> addrList = this.service.bluetooth.getProfileConnectedAddrByProile("AVRCP");
        if (addrList.isEmpty()) {
            return NfDef.DEFAULT_ADDRESS;
        }
        if (addrList.size() > 0) {
            return addrList.get(0);
        }
        return null;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean reqAvrcpConnect(String address) throws RemoteException {
        Log.e(TAG, "reqAvrcpConnect address:" + address);
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean reqAvrcpDisconnect(String address) throws RemoteException {
        Log.e(TAG, "reqAvrcpDisconnect address:" + address);
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean isAvrcp13Supported(String address) throws RemoteException {
        Log.e(TAG, "isAvrcp13Supported address:" + address);
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean isAvrcp14Supported(String address) throws RemoteException {
        Log.e(TAG, "isAvrcp14Supported address:" + address);
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean reqAvrcpPlay() throws RemoteException {
        Log.e(TAG, "reqAvrcpPlay");
        this.service.getCommand().musicUnmute();
        this.service.getCommand().musicPlay();
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean reqAvrcpStop() throws RemoteException {
        Log.e(TAG, "reqAvrcpStop");
        this.service.getCommand().musicMute();
        this.service.getCommand().musicStop();
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean reqAvrcpPause() throws RemoteException {
        Log.e(TAG, "reqAvrcpPause");
        this.service.getCommand().musicMute();
        this.service.getCommand().musicPause();
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean reqAvrcpForward() throws RemoteException {
        Log.e(TAG, "reqAvrcpForward");
        this.service.getCommand().musicNext();
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean reqAvrcpBackward() throws RemoteException {
        Log.e(TAG, "reqAvrcpBackward");
        this.service.getCommand().musicPrevious();
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean reqAvrcpVolumeUp() throws RemoteException {
        Log.e(TAG, "reqAvrcpVolumeUp");
        int i = this.avrcp_volume;
        if (i < 100) {
            this.avrcp_volume = i + 10;
        }
        this.service.getCommand().setA2dpVolume(this.avrcp_volume);
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean reqAvrcpVolumeDown() throws RemoteException {
        Log.e(TAG, "reqAvrcpVolumeDown");
        int i = this.avrcp_volume;
        if (i > 0) {
            this.avrcp_volume = i - 10;
        }
        this.service.getCommand().setA2dpVolume(this.avrcp_volume);
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean reqAvrcpStartFastForward() throws RemoteException {
        Log.d(TAG, "reqAvrcpStartFastForward");
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean reqAvrcpStopFastForward() throws RemoteException {
        Log.d(TAG, "reqAvrcpStopFastForward");
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean reqAvrcpStartRewind() throws RemoteException {
        Log.d(TAG, "reqAvrcpStartRewind");
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean reqAvrcpStopRewind() throws RemoteException {
        Log.d(TAG, "reqAvrcpStopRewind");
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean reqAvrcp13GetCapabilitiesSupportEvent() throws RemoteException {
        Log.d(TAG, "reqAvrcp13GetCapabilitiesSupportEvent");
        GocsdkService.getInstance().getHandler().post(new Runnable() {
            /* class com.goodocom.gocsdkserver.CommandAvrcpImp.AnonymousClass1 */

            @Override // java.lang.Runnable
            public void run() {
                synchronized (CommandAvrcpImp.this.callbacksLock) {
                    int n = CommandAvrcpImp.this.callbacks.beginBroadcast();
                    for (int i = 0; i < n; i++) {
                        try {
                            ((GocCallbackAvrcp) CommandAvrcpImp.this.callbacks.getBroadcastItem(i)).retAvrcp13CapabilitiesSupportEvent(new byte[]{1, 2, 5});
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    CommandAvrcpImp.this.callbacks.finishBroadcast();
                }
            }
        });
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean reqAvrcp13GetPlayerSettingAttributesList() throws RemoteException {
        Log.d(TAG, "reqAvrcp13GetPlayerSettingAttributesList");
        GocsdkService.getInstance().getHandler().post(new Runnable() {
            /* class com.goodocom.gocsdkserver.CommandAvrcpImp.AnonymousClass2 */

            @Override // java.lang.Runnable
            public void run() {
                synchronized (CommandAvrcpImp.this.callbacksLock) {
                    int n = CommandAvrcpImp.this.callbacks.beginBroadcast();
                    for (int i = 0; i < n; i++) {
                        try {
                            ((GocCallbackAvrcp) CommandAvrcpImp.this.callbacks.getBroadcastItem(i)).retAvrcp13PlayerSettingAttributesList(new byte[]{2, 3});
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    CommandAvrcpImp.this.callbacks.finishBroadcast();
                }
            }
        });
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean reqAvrcp13GetPlayerSettingValuesList(byte attributeId) throws RemoteException {
        Log.d(TAG, "reqAvrcp13GetPlayerSettingValuesList attributeId:" + ((int) attributeId));
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean reqAvrcp13GetPlayerSettingCurrentValues() throws RemoteException {
        Log.d(TAG, "reqAvrcp13GetPlayerSettingCurrentValues");
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean reqAvrcp13SetPlayerSettingValue(byte attributeId, byte valueId) throws RemoteException {
        Log.d(TAG, "reqAvrcp13SetPlayerSettingValue attributeId:" + ((int) attributeId) + " valueId:" + ((int) valueId));
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean reqAvrcp13GetElementAttributesPlaying() throws RemoteException {
        Log.d(TAG, "reqAvrcp13GetElementAttributesPlaying");
        GocsdkService.getInstance().getHandler().post(new Runnable() {
            /* class com.goodocom.gocsdkserver.CommandAvrcpImp.AnonymousClass3 */

            @Override // java.lang.Runnable
            public void run() {
                synchronized (CommandAvrcpImp.this.callbacksLock) {
                    int n = CommandAvrcpImp.this.callbacks.beginBroadcast();
                    for (int i = 0; i < n; i++) {
                        try {
                            ((GocCallbackAvrcp) CommandAvrcpImp.this.callbacks.getBroadcastItem(i)).retAvrcp13ElementAttributesPlaying(new int[]{1, 2, 3, 4, 5, 7}, new String[]{CommandAvrcpImp.this.current_song_music, CommandAvrcpImp.this.current_song_artist, CommandAvrcpImp.this.current_song_album, String.valueOf(CommandAvrcpImp.this.current_song_pos), String.valueOf(CommandAvrcpImp.this.current_song_total), String.valueOf(CommandAvrcpImp.this.current_song_duration)});
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    CommandAvrcpImp.this.callbacks.finishBroadcast();
                }
            }
        });
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean reqAvrcp13GetPlayStatus() throws RemoteException {
        Log.e(TAG, "reqAvrcp13GetPlayStatus");
        this.service.getCommand().inqueryA2dpStatus();
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean reqAvrcpRegisterEventWatcher(byte eventId, long interval) throws RemoteException {
        Log.d(TAG, "reqAvrcpRegisterEventWatcher eventId:" + ((int) eventId) + " interval:" + interval);
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean reqAvrcpUnregisterEventWatcher(byte eventId) throws RemoteException {
        Log.d(TAG, "reqAvrcpUnregisterEventWatcher eventId:" + ((int) eventId));
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean reqAvrcp13NextGroup() throws RemoteException {
        Log.d(TAG, "reqAvrcp13NextGroup");
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean reqAvrcp13PreviousGroup() throws RemoteException {
        Log.d(TAG, "reqAvrcp13PreviousGroup");
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean isAvrcp14BrowsingChannelEstablished() throws RemoteException {
        Log.d(TAG, "isAvrcp14BrowsingChannelEstablished");
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean reqAvrcp14SetAddressedPlayer(int playerId) throws RemoteException {
        Log.d(TAG, "reqAvrcp14SetAddressedPlayer playerId:" + playerId);
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean reqAvrcp14SetBrowsedPlayer(int playerId) throws RemoteException {
        Log.d(TAG, "reqAvrcp14SetBrowsedPlayer playerId:" + playerId);
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean reqAvrcp14GetFolderItems(byte scopeId) throws RemoteException {
        Log.d(TAG, "reqAvrcp14GetFolderItems scopeId:" + ((int) scopeId));
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean reqAvrcp14ChangePath(int uidCounter, long uid, byte direction) throws RemoteException {
        Log.d(TAG, "reqAvrcp14ChangePath uidCounter:" + uidCounter + " uid:" + uid + "direction:" + ((int) direction));
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean reqAvrcp14GetItemAttributes(byte scopeId, int uidCounter, long uid) throws RemoteException {
        Log.d(TAG, "reqAvrcp14GetItemAttributes scopeId:" + ((int) scopeId) + " uidCounter:" + uidCounter + " uid:" + uid);
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean reqAvrcp14PlaySelectedItem(byte scopeId, int uidCounter, long uid) throws RemoteException {
        Log.d(TAG, "reqAvrcp14PlaySelectedItem scopeId:" + ((int) scopeId) + " uidCounter:" + uidCounter + " uid:" + uid);
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean reqAvrcp14Search(String text) throws RemoteException {
        Log.d(TAG, "reqAvrcp14Search text:" + text);
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean reqAvrcp14AddToNowPlaying(byte scopeId, int uidCounter, long uid) throws RemoteException {
        Log.d(TAG, "reqAvrcp14AddToNowPlaying scopeId:" + ((int) scopeId) + " uidCounter:" + uidCounter + " uid" + uid);
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean reqAvrcp14SetAbsoluteVolume(byte volume) throws RemoteException {
        Log.d(TAG, "reqAvrcp14SetAbsoluteVolume volume:" + ((int) volume));
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean reqAvrcpQueryVersion(String address) throws RemoteException {
        Log.d(TAG, "reqAvrcpQueryVersion address:" + address);
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public int getPlayMode() throws RemoteException {
        Log.d(TAG, "getPlayMode : ");
        return 0;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
    public boolean setPlayMode(int mode) throws RemoteException {
        Log.d(TAG, "setPlayMode : " + mode);
        return false;
    }

    public void startBtmusicForce(int force) {
        Log.e("forcestart", "startBtmusicForce>>>> " + force);
        if (force == 1) {
            this.a2dpForceStartMusic = true;
        } else {
            this.a2dpForceStartMusic = false;
        }
    }

    public void onMusicPlaying() {
        Log.e("forcemusic", "onMusicPlaying a2dpForceStartMusic: " + this.a2dpForceStartMusic + "  currentPlaying : " + currentPlaying);
        if (this.a2dpForceStartMusic) {
            this.service.mBtCommandReceiver.applyA2dpAudioFocus();
        }
        Log.e(TAG, "onMusicPlaying:" + currentPlaying);
        currentPlaying = true;
        synchronized (this.callbacksLock) {
            int n = this.callbacks.beginBroadcast();
            for (int i = 0; i < n; i++) {
                try {
                    this.callbacks.getBroadcastItem(i).onAvrcp13EventPlaybackStatusChanged((byte) 1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.callbacks.finishBroadcast();
        }
        currentPlaying = true;
    }

    public void onMusicStopped() {
        Log.e(TAG, "onMusicStopped a2dpForceStartMusic: " + this.a2dpForceStartMusic + "   " + currentPlaying);
        boolean z = this.a2dpForceStartMusic;
        StringBuilder sb = new StringBuilder();
        sb.append("onMusicStopped:");
        sb.append(currentPlaying);
        Log.e(TAG, sb.toString());
        synchronized (this.callbacksLock) {
            int n = this.callbacks.beginBroadcast();
            for (int i = 0; i < n; i++) {
                try {
                    this.callbacks.getBroadcastItem(i).onAvrcp13EventPlaybackStatusChanged((byte) 2);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.callbacks.finishBroadcast();
        }
        currentPlaying = false;
    }

    public void onAvrcpStatus(int status) {
        DeviceInfo _dev = this.service.bluetooth.getDeviceByAddr(this.service.bluetooth.rspAddr);
        if (_dev == null) {
            Log.d(TAG, "onAvrcpStatus dev is null");
            return;
        }
        for (String addr : this.service.bluetooth.DevicesList.keySet()) {
            DeviceInfo info = this.service.bluetooth.DevicesList.get(addr);
            if (info != null) {
                if (info.avrcp_state > 1 && !info.addr.equals(this.service.bluetooth.rspAddr)) {
                    return;
                }
            } else {
                return;
            }
        }
        int oldstate = _dev.avrcp_state;
        Log.e(TAG, "onAvrcpStatus addr:" + this.service.bluetooth.rspAddr + " old:" + oldstate + " new:" + status);
        if (oldstate != status) {
            _dev.avrcp_state = status;
            this.service.bluetooth.updateDeviceInfo(this.service.bluetooth.rspAddr, _dev);
            if (status == 2) {
                this.service.bluetooth.setDeviceListProfileConnected(this.service.bluetooth.rspAddr, "AVRCP", true);
            } else if (status == 1) {
                this.service.bluetooth.setDeviceListProfileConnected(this.service.bluetooth.rspAddr, "AVRCP", false);
            }
            synchronized (this.callbacksLock) {
                int n = this.callbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        this.callbacks.getBroadcastItem(i).onAvrcpStateChanged(this.service.bluetooth.rspAddr, status2pre(status), status2State(status));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                this.callbacks.finishBroadcast();
            }
        }
    }

    public void onAvStatus(int status) {
    }

    public void onMusicInfo(String music, String artist, String album, int duration, int pos, int total) {
        this.current_song_music = music;
        this.current_song_artist = artist;
        this.current_song_album = album;
        this.current_song_pos = pos;
        this.current_song_total = total;
        this.current_song_duration = duration;
        Log.e(TAG, "onMusicInfo music:" + music + " artist:" + artist + " album:" + album + " pos:" + pos + " total:" + total + " duration:" + duration);
        synchronized (this.callbacksLock) {
            int n = this.callbacks.beginBroadcast();
            for (int i = 0; i < n; i++) {
                try {
                    this.callbacks.getBroadcastItem(i).retAvrcp13ElementAttributesPlaying(new int[]{1, 2, 3, 4, 5, 7}, new String[]{music, artist, album, String.valueOf(pos), String.valueOf(total), String.valueOf(duration)});
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.callbacks.finishBroadcast();
        }
    }

    public void onMusicPos(long pos, long total) {
        Log.e("musicpos", "T:" + total + ",P:" + pos + "    " + this.service.playStete);
        this.lastPosition = pos;
        this.lastLength = total;
        synchronized (this.callbacksLock) {
            int n = this.callbacks.beginBroadcast();
            for (int i = 0; i < n; i++) {
                try {
                    this.callbacks.getBroadcastItem(i).retAvrcp13PlayStatus(total, pos, this.service.playStete == 1 ? (byte) 1 : 0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.callbacks.finishBroadcast();
        }
    }

    public void avrcpPlayModeChanged(int mode) {
        Log.d(TAG, "avrcpPlayModeChanged " + mode);
        this.mPlayMode = mode;
        synchronized (this.callbacksLock) {
            int n = this.callbacks.beginBroadcast();
            for (int i = 0; i < n; i++) {
                try {
                    this.callbacks.getBroadcastItem(i).retAvrcpPlayModeChanged(this.service.bluetooth.rspAddr, mode);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.callbacks.finishBroadcast();
        }
    }
}

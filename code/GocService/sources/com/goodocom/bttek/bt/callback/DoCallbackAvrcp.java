package com.goodocom.bttek.bt.callback;

import android.os.Bundle;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.goodocom.bttek.bt.aidl.UiCallbackAvrcp;

public final class DoCallbackAvrcp extends DoCallback<UiCallbackAvrcp> {
    private final int onAvrcp13EventBatteryStatusChanged = 14;
    private final int onAvrcp13EventPlaybackPosChanged = 13;
    private final int onAvrcp13EventPlaybackStatusChanged = 9;
    private final int onAvrcp13EventPlayerSettingChanged = 16;
    private final int onAvrcp13EventSystemStatusChanged = 15;
    private final int onAvrcp13EventTrackChanged = 10;
    private final int onAvrcp13EventTrackReachedEnd = 11;
    private final int onAvrcp13EventTrackReachedStart = 12;
    private final int onAvrcp13RegisterEventWatcherFail = 37;
    private final int onAvrcp13RegisterEventWatcherSuccess = 36;
    private final int onAvrcp14EventAddressedPlayerChanged = 21;
    private final int onAvrcp14EventAvailablePlayerChanged = 20;
    private final int onAvrcp14EventNowPlayingContentChanged = 19;
    private final int onAvrcp14EventUidsChanged = 22;
    private final int onAvrcp14EventVolumeChanged = 23;
    private final int onAvrcpErrorResponse = 34;
    private final int onAvrcpServiceReady = 0;
    private final int onAvrcpStateChanged = 1;
    private final int retAvrcp13CapabilitiesSupportEvent = 2;
    private final int retAvrcp13ElementAttributesPlaying = 7;
    private final int retAvrcp13PlayStatus = 8;
    private final int retAvrcp13PlayerSettingAttributesList = 3;
    private final int retAvrcp13PlayerSettingCurrentValues = 5;
    private final int retAvrcp13PlayerSettingValuesList = 4;
    private final int retAvrcp13SetPlayerSettingValueSuccess = 6;
    private final int retAvrcp14AddToNowPlayingSuccess = 32;
    private final int retAvrcp14ChangePathSuccess = 28;
    private final int retAvrcp14FolderItems = 26;
    private final int retAvrcp14ItemAttributes = 29;
    private final int retAvrcp14MediaItems = 27;
    private final int retAvrcp14PlaySelectedItemSuccess = 30;
    private final int retAvrcp14SearchResult = 31;
    private final int retAvrcp14SetAbsoluteVolumeSuccess = 33;
    private final int retAvrcp14SetAddressedPlayerSuccess = 24;
    private final int retAvrcp14SetBrowsedPlayerSuccess = 25;
    private final int retAvrcpPlayModeChanged = 38;
    private final int retAvrcpUpdateSongStatus = 35;

    /* access modifiers changed from: protected */
    @Override // com.goodocom.bttek.bt.callback.DoCallback
    public String getLogTag() {
        return "DoCallbackAvrcp";
    }

    public void onAvrcpServiceReady() {
        Log.v(this.TAG, "onAvrcpServiceReady()");
        enqueueMessage(Message.obtain(this.mHandler, 0));
    }

    public synchronized void onAvrcpStateChanged(String address, int prevState, int newState) {
        String str = this.TAG;
        Log.v(str, "onAvrcpStateChanged() " + address + " state: " + prevState + " -> " + newState);
        Message m = getMessage(1);
        m.obj = address;
        m.arg1 = prevState;
        m.arg2 = newState;
        enqueueMessage(m);
    }

    public synchronized void retAvrcp13CapabilitiesSupportEvent(byte[] eventIds) {
        Log.v(this.TAG, "retAvrcp13CapabilitiesSupportEvent() ");
        Message m = getMessage(2);
        Bundle b = new Bundle();
        b.putByteArray("eventIds", eventIds);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void retAvrcp13PlayerSettingAttributesList(byte[] attributeIds) {
        Log.v(this.TAG, "retAvrcp13PlayerSettingAttributesList() ");
        Message m = getMessage(3);
        Bundle b = new Bundle();
        b.putByteArray("attributeIds", attributeIds);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void retAvrcp13PlayerSettingValuesList(byte attributeId, byte[] valueIds) {
        Log.v(this.TAG, "retAvrcp13PlayerSettingValuesList() ");
        Message m = getMessage(4);
        Bundle b = new Bundle();
        b.putByte("attributeId", attributeId);
        b.putByteArray("valueIds", valueIds);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void retAvrcp13PlayerSettingCurrentValues(byte[] attributeIds, byte[] valueIds) {
        Log.v(this.TAG, "retAvrcp13PlayerSettingCurrentValues() ");
        Message m = getMessage(5);
        Bundle b = new Bundle();
        b.putByteArray("attributeIds", attributeIds);
        b.putByteArray("valueIds", valueIds);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void retAvrcp13SetPlayerSettingValueSuccess() {
        Log.v(this.TAG, "retAvrcp13SetPlayerSettingValueSuccess() ");
        enqueueMessage(getMessage(6));
    }

    public synchronized void retAvrcp13ElementAttributesPlaying(int[] metadataAtrributeIds, String[] texts) {
        Log.v(this.TAG, "retAvrcp13ElementAttributesPlaying() ");
        Message m = getMessage(7);
        Bundle b = new Bundle();
        b.putIntArray("metadataAtrributeIds", metadataAtrributeIds);
        b.putStringArray("texts", texts);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void retAvrcp13PlayStatus(long songLen, long songPos, byte statusId) {
        Log.v(this.TAG, "retAvrcp13PlayStatus() ");
        Message m = getMessage(8);
        Bundle b = new Bundle();
        b.putLong("songLen", songLen);
        b.putLong("songPos", songPos);
        b.putByte("statusId", statusId);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void onAvrcp13EventPlaybackStatusChanged(byte statusId) {
        Log.v(this.TAG, "onAvrcp13EventPlaybackStatusChanged() ");
        Message m = getMessage(9);
        Bundle b = new Bundle();
        b.putByte("statusId", statusId);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void onAvrcp13EventTrackChanged(long elementId) {
        Log.v(this.TAG, "onAvrcp13EventTrackChanged() ");
        Message m = getMessage(10);
        Bundle b = new Bundle();
        b.putLong("elementId", elementId);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void onAvrcp13EventTrackReachedEnd() {
        Log.v(this.TAG, "onAvrcp13EventTrackReachedEnd() ");
        enqueueMessage(getMessage(11));
    }

    public synchronized void onAvrcp13EventTrackReachedStart() {
        Log.v(this.TAG, "onAvrcp13EventTrackReachedStart() ");
        enqueueMessage(getMessage(12));
    }

    public synchronized void onAvrcp13EventPlaybackPosChanged(long songPos) {
        Log.v(this.TAG, "onAvrcp13EventPlaybackPosChanged() ");
        Message m = getMessage(13);
        Bundle b = new Bundle();
        b.putLong("songPos", songPos);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void onAvrcp13EventBatteryStatusChanged(byte statusId) {
        Log.v(this.TAG, "onAvrcp13EventBatteryStatusChanged() ");
        Message m = getMessage(14);
        Bundle b = new Bundle();
        b.putByte("statusId", statusId);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void onAvrcp13EventSystemStatusChanged(long statusId) {
        Log.v(this.TAG, "onAvrcp13EventSystemStatusChanged() ");
        Message m = getMessage(15);
        Bundle b = new Bundle();
        b.putLong("statusId", statusId);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void onAvrcp13EventPlayerSettingChanged(byte[] attributeIds, byte[] valueIds) {
        Log.v(this.TAG, "onAvrcp13EventPlayerSettingChanged() ");
        Message m = getMessage(16);
        Bundle b = new Bundle();
        b.putByteArray("attributeIds", attributeIds);
        b.putByteArray("valueIds", valueIds);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void onAvrcp14EventNowPlayingContentChanged() {
        Log.v(this.TAG, "onAvrcp14EventNowPlayingContentChanged() ");
        enqueueMessage(getMessage(19));
    }

    public synchronized void onAvrcp14EventAvailablePlayerChanged() {
        Log.v(this.TAG, "onAvrcp14EventAvailablePlayerChanged() ");
        enqueueMessage(getMessage(20));
    }

    public synchronized void onAvrcp14EventAddressedPlayerChanged(int playerId, int uidCounter) {
        Log.v(this.TAG, "onAvrcp14EventAddressedPlayerChanged() ");
        Message m = getMessage(21);
        Bundle b = new Bundle();
        b.putInt("playerId", playerId);
        b.putInt("uidCounter", uidCounter);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void onAvrcp14EventUidsChanged(int uidCounter) {
        Log.v(this.TAG, "onAvrcp14EventUidsChanged() ");
        Message m = getMessage(22);
        Bundle b = new Bundle();
        b.putInt("uidCounter", uidCounter);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void onAvrcp14EventVolumeChanged(byte volume) {
        Log.v(this.TAG, "onAvrcp14EventVolumeChanged() ");
        Message m = getMessage(23);
        Bundle b = new Bundle();
        b.putByte("volume", volume);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void retAvrcp14SetAddressedPlayerSuccess() {
        Log.v(this.TAG, "retAvrcp14SetAddressedPlayerSuccess() ");
        enqueueMessage(getMessage(24));
    }

    public synchronized void retAvrcp14SetBrowsedPlayerSuccess(String[] path, int uidCounter, long itemCount) {
        Log.v(this.TAG, "retAvrcp14SetBrowsedPlayerSuccess() ");
        Message m = getMessage(25);
        Bundle b = new Bundle();
        b.putStringArray("path", path);
        b.putInt("uidCounter", uidCounter);
        b.putLong("itemCount", itemCount);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void retAvrcp14FolderItems(int uidCounter, long itemCount) {
        Log.v(this.TAG, "retAvrcp14FolderItems() ");
        Message m = getMessage(26);
        Bundle b = new Bundle();
        b.putInt("uidCounter", uidCounter);
        b.putLong("itemCount", itemCount);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void retAvrcp14MediaItems(int uidCounter, long itemCount) {
        Log.v(this.TAG, "retAvrcp14MediaItems() ");
        Message m = getMessage(27);
        Bundle b = new Bundle();
        b.putInt("uidCounter", uidCounter);
        b.putLong("itemCount", itemCount);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void retAvrcp14ChangePathSuccess(long itemCount) {
        Log.v(this.TAG, "retAvrcp14ChangePathSuccess() ");
        Message m = getMessage(28);
        Bundle b = new Bundle();
        b.putLong("itemCount", itemCount);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void retAvrcp14ItemAttributes(int[] metadataAtrributeIds, String[] texts) {
        Log.v(this.TAG, "retAvrcp14ItemAttributes() ");
        Message m = getMessage(29);
        Bundle b = new Bundle();
        b.putIntArray("metadataAtrributeIds", metadataAtrributeIds);
        b.putStringArray("texts", texts);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void retAvrcp14PlaySelectedItemSuccess() {
        Log.v(this.TAG, "retAvrcp14PlaySelectedItemSuccess() ");
        enqueueMessage(getMessage(30));
    }

    public synchronized void retAvrcp14SearchResult(int uidCounter, long itemCount) {
        Log.v(this.TAG, "retAvrcp14SearchResult() ");
        Message m = getMessage(31);
        Bundle b = new Bundle();
        b.putInt("uidCounter", uidCounter);
        b.putLong("itemCount", itemCount);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void retAvrcp14AddToNowPlayingSuccess() {
        Log.v(this.TAG, "retAvrcp14AddToNowPlayingSuccess() ");
        enqueueMessage(getMessage(32));
    }

    public synchronized void retAvrcp14SetAbsoluteVolumeSuccess(byte volume) {
        Log.v(this.TAG, "retAvrcp14SetAbsoluteVolumeSuccess() ");
        Message m = getMessage(33);
        Bundle b = new Bundle();
        b.putByte("volume", volume);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void onAvrcpErrorResponse(int opId, int reason, byte eventId) {
        Log.v(this.TAG, "onAvrcpErrorResponse() ");
        Message m = getMessage(34);
        Bundle b = new Bundle();
        b.putInt("opId", opId);
        b.putInt("reason", reason);
        b.putByte("eventId", eventId);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void retAvrcpPlayModeChanged(String address, int mode) {
        String str = this.TAG;
        Log.v(str, "retAvrcpUpdateSongStatus " + address + " , " + mode);
        Message m = getMessage(38);
        m.obj = address;
        m.arg1 = mode;
        enqueueMessage(m);
    }

    public synchronized void retAvrcpUpdateSongStatus(String artist, String album, String title) {
        Log.v(this.TAG, "retAvrcpUpdateSongStatus() ");
        Message m = getMessage(35);
        Bundle b = new Bundle();
        b.putString("artist", artist);
        b.putString("album", album);
        b.putString("title", title);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void onAvrcp13RegisterEventWatcherSuccess(byte eventId) {
        Log.v(this.TAG, "onAvrcp13RegisterEventWatcherSuccess() ");
        Message m = getMessage(36);
        Bundle b = new Bundle();
        b.putByte("eventId", eventId);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void onAvrcp13RegisterEventWatcherFail(byte eventId) {
        Log.v(this.TAG, "onAvrcp13RegisterEventWatcherFail() ");
        Message m = getMessage(37);
        Bundle b = new Bundle();
        b.putByte("eventId", eventId);
        m.setData(b);
        enqueueMessage(m);
    }

    /* access modifiers changed from: protected */
    @Override // com.goodocom.bttek.bt.callback.DoCallback
    public void dequeueMessage(Message msg) {
        Bundle b = msg.getData();
        String address = (String) msg.obj;
        int prevState = msg.arg1;
        int newState = msg.arg2;
        switch (msg.what) {
            case 0:
                callbackOnAvrcpServiceReady();
                return;
            case 1:
                callbackOnAvrcpStateChanged(address, prevState, newState);
                return;
            case 2:
                callbackRetAvrcp13CapabilitiesSupportEvent(b.getByteArray("eventIds"));
                return;
            case 3:
                callbackRetAvrcp13PlayerSettingAttributesList(b.getByteArray("attributeIds"));
                return;
            case 4:
                callbackRetAvrcp13PlayerSettingValuesList(b.getByte("attributeId"), b.getByteArray("valueIds"));
                return;
            case 5:
                callbackRetAvrcp13PlayerSettingCurrentValues(b.getByteArray("attributeIds"), b.getByteArray("valueIds"));
                return;
            case 6:
                callbackRetAvrcp13SetPlayerSettingValueSuccess();
                return;
            case 7:
                callbackRetAvrcp13ElementAttributesPlaying(b.getIntArray("metadataAtrributeIds"), b.getStringArray("texts"));
                return;
            case 8:
                callbackRetAvrcp13PlayStatus(b.getLong("songLen"), b.getLong("songPos"), b.getByte("statusId"));
                return;
            case 9:
                callbackOnAvrcp13EventPlaybackStatusChanged(b.getByte("statusId"));
                return;
            case 10:
                callbackOnAvrcp13EventTrackChanged(b.getLong("elementId"));
                return;
            case 11:
                callbackOnAvrcp13EventTrackReachedEnd();
                return;
            case 12:
                callbackOnAvrcp13EventTrackReachedStart();
                return;
            case 13:
                callbackOnAvrcp13EventPlaybackPosChanged(b.getLong("songPos"));
                return;
            case 14:
                callbackOnAvrcp13EventBatteryStatusChanged(b.getByte("statusId"));
                return;
            case 15:
                callbackOnAvrcp13EventSystemStatusChanged(b.getByte("statusId"));
                return;
            case 16:
                callbackOnAvrcp13EventPlayerSettingChanged(b.getByteArray("attributeIds"), b.getByteArray("valueIds"));
                return;
            case 17:
            case 18:
            default:
                String str = this.TAG;
                Log.e(str, "unhandle Message : " + msg.what);
                return;
            case 19:
                callbackOnAvrcp14EventNowPlayingContentChanged();
                return;
            case 20:
                callbackOnAvrcp14EventAvailablePlayerChanged();
                return;
            case 21:
                callbackOnAvrcp14EventAddressedPlayerChanged(b.getInt("playerId"), b.getInt("uidCounter"));
                return;
            case 22:
                callbackOnAvrcp14EventUidsChanged(b.getInt("uidCounter"));
                return;
            case 23:
                callbackOnAvrcp14EventVolumeChanged(b.getByte("volume"));
                return;
            case 24:
                callbackRetAvrcp14SetAddressedPlayerSuccess();
                return;
            case 25:
                callbackRetAvrcp14SetBrowsedPlayerSuccess(b.getStringArray("path"), b.getInt("uidCounter"), b.getLong("itemCount"));
                return;
            case 26:
                callbackRetAvrcp14FolderItems(b.getInt("uidCounter"), b.getLong("itemCount"));
                return;
            case 27:
                callbackRetAvrcp14MediaItems(b.getInt("uidCounter"), b.getLong("itemCount"));
                return;
            case 28:
                callbackRetAvrcp14ChangePathSuccess(b.getLong("itemCount"));
                return;
            case 29:
                callbackRetAvrcp14ItemAttributes(b.getIntArray("metadataAtrributeIds"), b.getStringArray("texts"));
                return;
            case 30:
                callbackRetAvrcp14PlaySelectedItemSuccess();
                return;
            case 31:
                callbackRetAvrcp14SearchResult(b.getInt("uidCounter"), b.getLong("itemCount"));
                return;
            case 32:
                callbackRetAvrcp14AddToNowPlayingSuccess();
                return;
            case 33:
                callbackRetAvrcp14SetAbsoluteVolumeSuccess(b.getByte("volume"));
                return;
            case 34:
                callbackOnAvrcpErrorResponse(b.getInt("opId"), b.getInt("reason"), b.getByte("eventId"));
                return;
            case 35:
                callbackRetAvrcpUpdateSongStatus(b.getString("artist"), b.getString("album"), b.getString("title"));
                return;
            case 36:
                callbackOnAvrcp13RegisterEventWatcherSuccess(b.getByte("eventId"));
                return;
            case 37:
                callbackOnAvrcp13RegisterEventWatcherFail(b.getByte("eventId"));
                return;
            case 38:
                callbackRetAvrcpPlayModeChanged(address, prevState);
                return;
        }
    }

    private synchronized void callbackOnAvrcpServiceReady() {
        Throwable th;
        Log.v(this.TAG, "callbackOnAvrcpServiceReady()");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onAvrcpServiceReady beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackAvrcp) this.mRemoteCallbacks.getBroadcastItem(i)).onAvrcpServiceReady();
                    } catch (RemoteException e) {
                        String str = this.TAG;
                        Log.e(str, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "onAvrcpServiceReady CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnAvrcpStateChanged(String address, int prevState, int newState) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackOnAvrcpStateChanged() " + address + " state: " + prevState + " -> " + newState);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onAvrcpStateChanged beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackAvrcp) this.mRemoteCallbacks.getBroadcastItem(i)).onAvrcpStateChanged(address, prevState, newState);
                    } catch (RemoteException e) {
                        String str2 = this.TAG;
                        Log.e(str2, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "onAvrcpStateChanged CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackRetAvrcp13CapabilitiesSupportEvent(byte[] eventIds) {
        Throwable th;
        Log.d(this.TAG, "callbackRetAvrcp13CapabilitiesSupportEvent() ");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "retAvrcp13CapabilitiesSupportEvent beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackAvrcp) this.mRemoteCallbacks.getBroadcastItem(i)).retAvrcp13CapabilitiesSupportEvent(eventIds);
                    } catch (RemoteException e) {
                        String str = this.TAG;
                        Log.e(str, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "retAvrcp13CapabilitiesSupportEvent CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackRetAvrcp13PlayerSettingAttributesList(byte[] attributeIds) {
        Throwable th;
        Log.d(this.TAG, "callbackRetAvrcp13PlayerSettingAttributesList() ");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "retAvrcp13PlayerSettingAttributesList beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackAvrcp) this.mRemoteCallbacks.getBroadcastItem(i)).retAvrcp13PlayerSettingAttributesList(attributeIds);
                    } catch (RemoteException e) {
                        String str = this.TAG;
                        Log.e(str, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "retAvrcp13PlayerSettingAttributesList CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackRetAvrcp13PlayerSettingValuesList(byte attributeId, byte[] valueIds) {
        Throwable th;
        Log.d(this.TAG, "callbackRetAvrcp13PlayerSettingValuesList() ");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "retAvrcp13PlayerSettingValuesList beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackAvrcp) this.mRemoteCallbacks.getBroadcastItem(i)).retAvrcp13PlayerSettingValuesList(attributeId, valueIds);
                    } catch (RemoteException e) {
                        String str = this.TAG;
                        Log.e(str, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "retAvrcp13PlayerSettingValuesList CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackRetAvrcp13PlayerSettingCurrentValues(byte[] attributeIds, byte[] valueIds) {
        Throwable th;
        Log.d(this.TAG, "callbackRetAvrcp13PlayerSettingCurrentValues() ");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "retAvrcp13PlayerSettingCurrentValues beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackAvrcp) this.mRemoteCallbacks.getBroadcastItem(i)).retAvrcp13PlayerSettingCurrentValues(attributeIds, valueIds);
                    } catch (RemoteException e) {
                        String str = this.TAG;
                        Log.e(str, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "retAvrcp13PlayerSettingCurrentValues CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackRetAvrcp13SetPlayerSettingValueSuccess() {
        Throwable th;
        Log.d(this.TAG, "callbackRetAvrcp13SetPlayerSettingValueSuccess() ");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "retAvrcp13SetPlayerSettingValueSuccess beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackAvrcp) this.mRemoteCallbacks.getBroadcastItem(i)).retAvrcp13SetPlayerSettingValueSuccess();
                    } catch (RemoteException e) {
                        String str = this.TAG;
                        Log.e(str, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "retAvrcp13SetPlayerSettingValueSuccess CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackRetAvrcp13ElementAttributesPlaying(int[] metadataAtrributeIds, String[] texts) {
        Throwable th;
        Log.d(this.TAG, "callbackRetAvrcp13ElementAttributesPlaying() ");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "retAvrcp13ElementAttributesPlaying beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackAvrcp) this.mRemoteCallbacks.getBroadcastItem(i)).retAvrcp13ElementAttributesPlaying(metadataAtrributeIds, texts);
                    } catch (RemoteException e) {
                        String str = this.TAG;
                        Log.e(str, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "retAvrcp13ElementAttributesPlaying CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackRetAvrcp13PlayStatus(long songLen, long songPos, byte statusId) {
        Throwable th;
        Log.d(this.TAG, "callbackRetAvrcp13PlayStatus() ");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "retAvrcp13PlayStatus beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackAvrcp) this.mRemoteCallbacks.getBroadcastItem(i)).retAvrcp13PlayStatus(songLen, songPos, statusId);
                    } catch (RemoteException e) {
                        String str = this.TAG;
                        Log.e(str, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "retAvrcp13PlayStatus CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnAvrcp13EventPlaybackStatusChanged(byte statusId) {
        Throwable th;
        Log.d(this.TAG, "callbackOnAvrcp13EventPlaybackStatusChanged() ");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onAvrcp13EventPlaybackStatusChanged beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackAvrcp) this.mRemoteCallbacks.getBroadcastItem(i)).onAvrcp13EventPlaybackStatusChanged(statusId);
                    } catch (RemoteException e) {
                        String str = this.TAG;
                        Log.e(str, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "onAvrcp13EventPlaybackStatusChanged CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnAvrcp13EventTrackChanged(long elementId) {
        Throwable th;
        Log.d(this.TAG, "callbackOnAvrcp13EventTrackChanged() ");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onAvrcp13EventTrackChanged beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackAvrcp) this.mRemoteCallbacks.getBroadcastItem(i)).onAvrcp13EventTrackChanged(elementId);
                    } catch (RemoteException e) {
                        String str = this.TAG;
                        Log.e(str, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "onAvrcp13EventTrackChanged CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnAvrcp13EventTrackReachedEnd() {
        Throwable th;
        Log.d(this.TAG, "callbackOnAvrcp13EventTrackReachedEnd() ");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onAvrcp13EventTrackReachedEnd beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackAvrcp) this.mRemoteCallbacks.getBroadcastItem(i)).onAvrcp13EventTrackReachedEnd();
                    } catch (RemoteException e) {
                        String str = this.TAG;
                        Log.e(str, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "onAvrcp13EventTrackReachedEnd CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnAvrcp13EventTrackReachedStart() {
        Throwable th;
        Log.d(this.TAG, "callbackOnAvrcp13EventTrackReachedStart() ");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onAvrcp13EventTrackReachedStart beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackAvrcp) this.mRemoteCallbacks.getBroadcastItem(i)).onAvrcp13EventTrackReachedStart();
                    } catch (RemoteException e) {
                        String str = this.TAG;
                        Log.e(str, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "onAvrcp13EventTrackReachedStart CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnAvrcp13EventPlaybackPosChanged(long songPos) {
        Throwable th;
        Log.d(this.TAG, "callbackOnAvrcp13EventPlaybackPosChanged() ");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onAvrcp13EventPlaybackPosChanged beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackAvrcp) this.mRemoteCallbacks.getBroadcastItem(i)).onAvrcp13EventPlaybackPosChanged(songPos);
                    } catch (RemoteException e) {
                        String str = this.TAG;
                        Log.e(str, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "onAvrcp13EventPlaybackPosChanged CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnAvrcp13EventBatteryStatusChanged(byte statusId) {
        Throwable th;
        Log.d(this.TAG, "callbackOnAvrcp13EventBatteryStatusChanged() ");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onAvrcp13EventBatteryStatusChanged beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackAvrcp) this.mRemoteCallbacks.getBroadcastItem(i)).onAvrcp13EventBatteryStatusChanged(statusId);
                    } catch (RemoteException e) {
                        String str = this.TAG;
                        Log.e(str, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "onAvrcp13EventBatteryStatusChanged CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnAvrcp13EventSystemStatusChanged(byte statusId) {
        Throwable th;
        Log.d(this.TAG, "callbackOnAvrcp13EventSystemStatusChanged() ");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onAvrcp13EventSystemStatusChanged beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackAvrcp) this.mRemoteCallbacks.getBroadcastItem(i)).onAvrcp13EventSystemStatusChanged(statusId);
                    } catch (RemoteException e) {
                        String str = this.TAG;
                        Log.e(str, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "onAvrcp13EventSystemStatusChanged CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnAvrcp13EventPlayerSettingChanged(byte[] attributeIds, byte[] valueIds) {
        Throwable th;
        Log.d(this.TAG, "callbackOnAvrcp13EventPlayerSettingChanged() ");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onAvrcp13EventPlayerSettingChanged beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackAvrcp) this.mRemoteCallbacks.getBroadcastItem(i)).onAvrcp13EventPlayerSettingChanged(attributeIds, valueIds);
                    } catch (RemoteException e) {
                        String str = this.TAG;
                        Log.e(str, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "onAvrcp13EventPlayerSettingChanged CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnAvrcp14EventNowPlayingContentChanged() {
        Throwable th;
        Log.d(this.TAG, "callbackOnAvrcp14EventNowPlayingContentChanged() ");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onAvrcp14EventNowPlayingContentChanged beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackAvrcp) this.mRemoteCallbacks.getBroadcastItem(i)).onAvrcp14EventNowPlayingContentChanged();
                    } catch (RemoteException e) {
                        String str = this.TAG;
                        Log.e(str, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "onAvrcp14EventNowPlayingContentChanged CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnAvrcp14EventAvailablePlayerChanged() {
        Throwable th;
        Log.d(this.TAG, "callbackOnAvrcp14EventAvailablePlayerChanged() ");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onAvrcp14EventAvailablePlayerChanged beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackAvrcp) this.mRemoteCallbacks.getBroadcastItem(i)).onAvrcp14EventAvailablePlayerChanged();
                    } catch (RemoteException e) {
                        String str = this.TAG;
                        Log.e(str, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "onAvrcp14EventAvailablePlayerChanged CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnAvrcp14EventAddressedPlayerChanged(int playerId, int uidCounter) {
        Throwable th;
        Log.d(this.TAG, "callbackOnAvrcp14EventAddressedPlayerChanged() ");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onAvrcp14EventAddressedPlayerChanged beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackAvrcp) this.mRemoteCallbacks.getBroadcastItem(i)).onAvrcp14EventAddressedPlayerChanged(playerId, uidCounter);
                    } catch (RemoteException e) {
                        String str = this.TAG;
                        Log.e(str, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "onAvrcp14EventAddressedPlayerChanged CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnAvrcp14EventUidsChanged(int uidCounter) {
        Throwable th;
        Log.d(this.TAG, "callbackOnAvrcp14EventUidsChanged() ");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onAvrcp14EventUidsChanged beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackAvrcp) this.mRemoteCallbacks.getBroadcastItem(i)).onAvrcp14EventUidsChanged(uidCounter);
                    } catch (RemoteException e) {
                        String str = this.TAG;
                        Log.e(str, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "onAvrcp14EventUidsChanged CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnAvrcp14EventVolumeChanged(byte volume) {
        Throwable th;
        Log.d(this.TAG, "callbackOnAvrcp14EventVolumeChanged() ");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onAvrcp14EventVolumeChanged beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackAvrcp) this.mRemoteCallbacks.getBroadcastItem(i)).onAvrcp14EventVolumeChanged(volume);
                    } catch (RemoteException e) {
                        String str = this.TAG;
                        Log.e(str, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "onAvrcp14EventVolumeChanged CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackRetAvrcp14SetAddressedPlayerSuccess() {
        Throwable th;
        Log.d(this.TAG, "retAvrcp14SetAddressedPlayerSuccess() ");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "retAvrcp14SetAddressedPlayerSuccess beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackAvrcp) this.mRemoteCallbacks.getBroadcastItem(i)).retAvrcp14SetAddressedPlayerSuccess();
                    } catch (RemoteException e) {
                        String str = this.TAG;
                        Log.e(str, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "retAvrcp14SetAddressedPlayerSuccess CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackRetAvrcp14SetBrowsedPlayerSuccess(String[] path, int uidCounter, long itemCount) {
        Throwable th;
        Log.d(this.TAG, "callbackRetAvrcp14SetBrowsedPlayerSuccess() ");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "retAvrcp14SetBrowsedPlayerSuccess beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackAvrcp) this.mRemoteCallbacks.getBroadcastItem(i)).retAvrcp14SetBrowsedPlayerSuccess(path, uidCounter, itemCount);
                    } catch (RemoteException e) {
                        String str = this.TAG;
                        Log.e(str, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "retAvrcp14SetBrowsedPlayerSuccess CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackRetAvrcp14FolderItems(int uidCounter, long itemCount) {
        Throwable th;
        Log.d(this.TAG, "callbackRetAvrcp14FolderItems() ");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "retAvrcp14FolderItems beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackAvrcp) this.mRemoteCallbacks.getBroadcastItem(i)).retAvrcp14FolderItems(uidCounter, itemCount);
                    } catch (RemoteException e) {
                        String str = this.TAG;
                        Log.e(str, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "retAvrcp14FolderItems CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackRetAvrcp14MediaItems(int uidCounter, long itemCount) {
        Throwable th;
        Log.d(this.TAG, "callbackRetAvrcp14MediaItems() ");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "retAvrcp14MediaItems beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackAvrcp) this.mRemoteCallbacks.getBroadcastItem(i)).retAvrcp14MediaItems(uidCounter, itemCount);
                    } catch (RemoteException e) {
                        String str = this.TAG;
                        Log.e(str, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "retAvrcp14MediaItems CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackRetAvrcp14ChangePathSuccess(long itemCount) {
        Throwable th;
        Log.d(this.TAG, "callbackRetAvrcp14ChangePathSuccess() ");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "retAvrcp14ChangePathSuccess beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackAvrcp) this.mRemoteCallbacks.getBroadcastItem(i)).retAvrcp14ChangePathSuccess(itemCount);
                    } catch (RemoteException e) {
                        String str = this.TAG;
                        Log.e(str, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "retAvrcp14ChangePathSuccess CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackRetAvrcp14ItemAttributes(int[] metadataAtrributeIds, String[] texts) {
        Throwable th;
        Log.d(this.TAG, "callbackRetAvrcp14ItemAttributes() ");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "retAvrcp14ItemAttributes beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackAvrcp) this.mRemoteCallbacks.getBroadcastItem(i)).retAvrcp14ItemAttributes(metadataAtrributeIds, texts);
                    } catch (RemoteException e) {
                        String str = this.TAG;
                        Log.e(str, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "retAvrcp14ItemAttributes CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackRetAvrcp14PlaySelectedItemSuccess() {
        Throwable th;
        Log.d(this.TAG, "callbackRetAvrcp14PlaySelectedItemSuccess() ");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "retAvrcp14PlaySelectedItemSuccess beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackAvrcp) this.mRemoteCallbacks.getBroadcastItem(i)).retAvrcp14PlaySelectedItemSuccess();
                    } catch (RemoteException e) {
                        String str = this.TAG;
                        Log.e(str, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "retAvrcp14PlaySelectedItemSuccess CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackRetAvrcp14SearchResult(int uidCounter, long itemCount) {
        Throwable th;
        Log.d(this.TAG, "callbackRetAvrcp14SearchResult() ");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "retAvrcp14SearchResult beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackAvrcp) this.mRemoteCallbacks.getBroadcastItem(i)).retAvrcp14SearchResult(uidCounter, itemCount);
                    } catch (RemoteException e) {
                        String str = this.TAG;
                        Log.e(str, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "retAvrcp14SearchResult CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackRetAvrcp14AddToNowPlayingSuccess() {
        Throwable th;
        Log.d(this.TAG, "callbackRetAvrcp14AddToNowPlayingSuccess() ");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "retAvrcp14AddToNowPlayingSuccess beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackAvrcp) this.mRemoteCallbacks.getBroadcastItem(i)).retAvrcp14AddToNowPlayingSuccess();
                    } catch (RemoteException e) {
                        String str = this.TAG;
                        Log.e(str, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "retAvrcp14AddToNowPlayingSuccess CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackRetAvrcp14SetAbsoluteVolumeSuccess(byte volume) {
        Throwable th;
        Log.d(this.TAG, "callbackRetAvrcp14SetAbsoluteVolumeSuccess() ");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "retAvrcp14SetAbsoluteVolumeSuccess beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackAvrcp) this.mRemoteCallbacks.getBroadcastItem(i)).retAvrcp14SetAbsoluteVolumeSuccess(volume);
                    } catch (RemoteException e) {
                        String str = this.TAG;
                        Log.e(str, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "retAvrcp14SetAbsoluteVolumeSuccess CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnAvrcpErrorResponse(int opId, int reason, byte eventId) {
        Throwable th;
        Log.d(this.TAG, "callbackOnAvrcpErrorResponse() ");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onAvrcpErrorResponse beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackAvrcp) this.mRemoteCallbacks.getBroadcastItem(i)).onAvrcpErrorResponse(opId, reason, eventId);
                    } catch (RemoteException e) {
                        String str = this.TAG;
                        Log.e(str, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "onAvrcpErrorResponse CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackRetAvrcpUpdateSongStatus(String artist, String album, String title) {
        Throwable th;
        Log.d(this.TAG, "callbackRetAvrcpUpdateSongStatus() ");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "retAvrcpUpdateSongStatus beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackAvrcp) this.mRemoteCallbacks.getBroadcastItem(i)).retAvrcpUpdateSongStatus(artist, album, title);
                    } catch (RemoteException e) {
                        String str = this.TAG;
                        Log.e(str, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "retAvrcpUpdateSongStatus CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnAvrcp13RegisterEventWatcherSuccess(byte eventId) {
        Throwable th;
        Log.v(this.TAG, "callbackOnAvrcp13RegisterEventWatcherSuccess()");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onAvrcp13RegisterEventWatcherSuccess beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackAvrcp) this.mRemoteCallbacks.getBroadcastItem(i)).onAvrcp13RegisterEventWatcherSuccess(eventId);
                    } catch (RemoteException e) {
                        String str = this.TAG;
                        Log.e(str, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "onAvrcp13RegisterEventWatcherSuccess CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnAvrcp13RegisterEventWatcherFail(byte eventId) {
        Throwable th;
        Log.v(this.TAG, "callbackOnAvrcp13RegisterEventWatcherFail()");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onAvrcp13RegisterEventWatcherFail beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackAvrcp) this.mRemoteCallbacks.getBroadcastItem(i)).onAvrcp13RegisterEventWatcherFail(eventId);
                    } catch (RemoteException e) {
                        String str = this.TAG;
                        Log.e(str, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "onAvrcp13RegisterEventWatcherFail CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0032, code lost:
        r1 = th;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void callbackRetAvrcpPlayModeChanged(java.lang.String r5, int r6) {
        /*
            r4 = this;
            monitor-enter(r4)
            java.lang.String r0 = r4.TAG     // Catch:{ all -> 0x0034 }
            java.lang.String r1 = "callbackRetAvrcpPlayModeChanged"
            android.util.Log.d(r0, r1)     // Catch:{ all -> 0x0034 }
            android.os.RemoteCallbackList r0 = r4.mRemoteCallbacks     // Catch:{ all -> 0x0034 }
            monitor-enter(r0)     // Catch:{ all -> 0x0034 }
            android.os.RemoteCallbackList r1 = r4.mRemoteCallbacks     // Catch:{ all -> 0x002f }
            int r1 = r1.beginBroadcast()     // Catch:{ all -> 0x002f }
            r2 = 0
        L_0x0012:
            if (r2 >= r1) goto L_0x0027
            android.os.RemoteCallbackList r3 = r4.mRemoteCallbacks     // Catch:{ RemoteException -> 0x0020 }
            android.os.IInterface r3 = r3.getBroadcastItem(r2)     // Catch:{ RemoteException -> 0x0020 }
            com.goodocom.bttek.bt.aidl.UiCallbackAvrcp r3 = (com.goodocom.bttek.bt.aidl.UiCallbackAvrcp) r3     // Catch:{ RemoteException -> 0x0020 }
            r3.retAvrcpPlayModeChanged(r5, r6)     // Catch:{ RemoteException -> 0x0020 }
            goto L_0x0024
        L_0x0020:
            r3 = move-exception
            r3.printStackTrace()
        L_0x0024:
            int r2 = r2 + 1
            goto L_0x0012
        L_0x0027:
            android.os.RemoteCallbackList r2 = r4.mRemoteCallbacks
            r2.finishBroadcast()
            monitor-exit(r0)
            monitor-exit(r4)
            return
        L_0x002f:
            r1 = move-exception
        L_0x0030:
            monitor-exit(r0)     // Catch:{ all -> 0x0032 }
            throw r1
        L_0x0032:
            r1 = move-exception
            goto L_0x0030
        L_0x0034:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.goodocom.bttek.bt.callback.DoCallbackAvrcp.callbackRetAvrcpPlayModeChanged(java.lang.String, int):void");
    }
}

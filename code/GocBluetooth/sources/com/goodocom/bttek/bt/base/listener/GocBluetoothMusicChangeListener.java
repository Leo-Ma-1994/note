package com.goodocom.bttek.bt.base.listener;

public interface GocBluetoothMusicChangeListener {
    void onAvrcp13EventBatteryStatusChanged(byte b);

    void onAvrcp13EventPlaybackPosChanged(long j);

    void onAvrcp13EventPlaybackStatusChanged(byte b);

    void onAvrcp13EventPlayerSettingChanged(byte[] bArr, byte[] bArr2);

    void onAvrcp13EventSystemStatusChanged(byte b);

    void onAvrcp13EventTrackChanged(long j);

    void onAvrcp13EventTrackReachedEnd();

    void onAvrcp13EventTrackReachedStart();

    void onAvrcp13RegisterEventWatcherFail(byte b);

    void onAvrcp13RegisterEventWatcherSuccess(byte b);

    void onAvrcp14EventAddressedPlayerChanged(int i, int i2);

    void onAvrcp14EventAvailablePlayerChanged();

    void onAvrcp14EventNowPlayingContentChanged();

    void onAvrcp14EventUidsChanged(int i);

    void onAvrcp14EventVolumeChanged(byte b);

    void onAvrcpErrorResponse(int i, int i2, byte b);

    void retAvrcp13CapabilitiesSupportEvent(byte[] bArr);

    void retAvrcp13ElementAttributesPlaying(int[] iArr, String[] strArr);

    void retAvrcp13PlayStatus(long j, long j2, byte b);

    void retAvrcp13PlayerSettingAttributesList(byte[] bArr);

    void retAvrcp13PlayerSettingCurrentValues(byte[] bArr, byte[] bArr2);

    void retAvrcp13PlayerSettingValuesList(byte b, byte[] bArr);

    void retAvrcp13SetPlayerSettingValueSuccess();

    void retAvrcp14AddToNowPlayingSuccess();

    void retAvrcp14ChangePathSuccess(long j);

    void retAvrcp14FolderItems(int i, long j);

    void retAvrcp14ItemAttributes(int[] iArr, String[] strArr);

    void retAvrcp14MediaItems(int i, long j);

    void retAvrcp14PlaySelectedItemSuccess();

    void retAvrcp14SearchResult(int i, long j);

    void retAvrcp14SetAbsoluteVolumeSuccess(byte b);

    void retAvrcp14SetAddressedPlayerSuccess();

    void retAvrcp14SetBrowsedPlayerSuccess(String[] strArr, int i, long j);

    void retAvrcpPlayModeChanged(String str, int i);

    void retAvrcpUpdateSongStatus(String str, String str2, String str3);
}

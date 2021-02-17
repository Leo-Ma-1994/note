package com.goodocom.bttek.bt.base.listener;

public interface GocBluetoothMapChangeListener {
    void onMapDownloadNotify(String str, int i, int i2, int i3);

    void onMapMemoryAvailableEvent(String str, int i, boolean z);

    void onMapMessageDeletedEvent(String str, String str2, int i, int i2);

    void onMapMessageDeliverStatusEvent(String str, String str2, boolean z);

    void onMapMessageSendingStatusEvent(String str, String str2, boolean z);

    void onMapMessageShiftedEvent(String str, String str2, int i, int i2, int i3);

    void onMapNewMessageReceivedEvent(String str, String str2, String str3, String str4);

    void onMapServiceReady();

    void onMapStateChanged(String str, int i, int i2, int i3);

    void retMapChangeReadStatusCompleted(String str, String str2, int i);

    void retMapCleanDatabaseCompleted(boolean z);

    void retMapDatabaseAvailable();

    void retMapDeleteDatabaseByAddressCompleted(String str, boolean z);

    void retMapDeleteMessageCompleted(String str, String str2, int i);

    void retMapDownloadedMessage(String str, String str2, String str3, String str4, String str5, String str6, int i, int i2, boolean z, String str7, String str8);

    void retMapSendMessageCompleted(String str, String str2, int i);
}

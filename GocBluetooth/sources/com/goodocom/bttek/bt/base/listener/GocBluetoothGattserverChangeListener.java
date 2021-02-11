package com.goodocom.bttek.bt.base.listener;

import android.os.ParcelUuid;

public interface GocBluetoothGattserverChangeListener {
    void onGattServerCharacteristicReadRequest(String str, int i, int i2, boolean z, int i3, ParcelUuid parcelUuid, ParcelUuid parcelUuid2);

    void onGattServerCharacteristicWriteRequest(String str, int i, int i2, boolean z, boolean z2, int i3, ParcelUuid parcelUuid, ParcelUuid parcelUuid2, byte[] bArr);

    void onGattServerDescriptorReadRequest(String str, int i, int i2, boolean z, int i3, ParcelUuid parcelUuid, ParcelUuid parcelUuid2, ParcelUuid parcelUuid3);

    void onGattServerDescriptorWriteRequest(String str, int i, int i2, boolean z, boolean z2, int i3, ParcelUuid parcelUuid, ParcelUuid parcelUuid2, ParcelUuid parcelUuid3, byte[] bArr);

    void onGattServerExecuteWrite(String str, int i, boolean z);

    void onGattServerServiceAdded(int i, int i2, ParcelUuid parcelUuid);

    void onGattServerServiceDeleted(int i, int i2, ParcelUuid parcelUuid);

    void onGattServerStateChanged(String str, int i);

    void onGattServiceReady();
}

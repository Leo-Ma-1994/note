package com.goodocom.bttek.bt.bean;

import android.os.Parcel;
import android.os.Parcelable;

public final class BluetoothDevice implements Parcelable {
    public static final Parcelable.Creator<BluetoothDevice> CREATOR = new Parcelable.Creator<BluetoothDevice>() {
        /* class com.goodocom.bttek.bt.bean.BluetoothDevice.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public BluetoothDevice createFromParcel(Parcel in) {
            return new BluetoothDevice(in.readString());
        }

        @Override // android.os.Parcelable.Creator
        public BluetoothDevice[] newArray(int size) {
            return new BluetoothDevice[size];
        }
    };
    public int category;
    public boolean isA2dpConnected;
    public boolean isBtConnectd;
    public boolean isHfpConnected;
    public boolean isMaster;
    public boolean isPaired;
    private String mAddress;
    public byte[] mDevAdr = new byte[6];
    public int mDevID = 0;
    private String mRemoteDevName;

    public BluetoothDevice(String address) {
        this.mAddress = address;
    }

    public String getAddress() {
        return this.mAddress;
    }

    public void setAddress(String mAddress2) {
        this.mAddress = mAddress2;
    }

    public String getRemoteDevName() {
        return this.mRemoteDevName;
    }

    public void setRemoteDevName(String mRemoteDevName2) {
        this.mRemoteDevName = mRemoteDevName2;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mAddress);
    }
}

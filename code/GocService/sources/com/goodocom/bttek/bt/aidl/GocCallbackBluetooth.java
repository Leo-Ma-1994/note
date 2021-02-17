package com.goodocom.bttek.bt.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface GocCallbackBluetooth extends IInterface {
    void onAdapterDiscoverableModeChanged(int i, int i2) throws RemoteException;

    void onAdapterDiscoveryFinished() throws RemoteException;

    void onAdapterDiscoveryStarted() throws RemoteException;

    void onAdapterStateChanged(int i, int i2) throws RemoteException;

    void onAutoAnwer(int i) throws RemoteException;

    void onAutoConnect(int i) throws RemoteException;

    void onBluetoothServiceReady() throws RemoteException;

    void onBtAutoConnectStateChanged(String str, int i, int i2) throws RemoteException;

    void onBtRoleModeChanged(int i) throws RemoteException;

    void onConnectedDevice(String str, String str2) throws RemoteException;

    void onDeviceAclDisconnected(String str) throws RemoteException;

    void onDeviceBondStateChanged(String str, String str2, int i, int i2) throws RemoteException;

    void onDeviceFound(String str, String str2, byte b) throws RemoteException;

    void onDeviceOutOfRange(String str) throws RemoteException;

    void onDeviceUuidsUpdated(String str, String str2, int i) throws RemoteException;

    void onLocalAdapterNameChanged(String str) throws RemoteException;

    void onMainDevicesChanged(String str, String str2) throws RemoteException;

    void retPairedDevices(int i, String[] strArr, String[] strArr2, int[] iArr, byte[] bArr) throws RemoteException;

    public static class Default implements GocCallbackBluetooth {
        @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
        public void onBluetoothServiceReady() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
        public void onAdapterStateChanged(int prevState, int newState) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
        public void onAdapterDiscoverableModeChanged(int prevState, int newState) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
        public void onAdapterDiscoveryStarted() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
        public void onAdapterDiscoveryFinished() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
        public void retPairedDevices(int elements, String[] address, String[] name, int[] supportProfile, byte[] category) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
        public void onDeviceFound(String address, String name, byte category) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
        public void onDeviceBondStateChanged(String address, String name, int prevState, int newState) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
        public void onDeviceUuidsUpdated(String address, String name, int supportProfile) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
        public void onLocalAdapterNameChanged(String name) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
        public void onDeviceOutOfRange(String address) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
        public void onDeviceAclDisconnected(String address) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
        public void onBtRoleModeChanged(int mode) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
        public void onBtAutoConnectStateChanged(String address, int prevState, int newState) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
        public void onMainDevicesChanged(String address, String name) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
        public void onAutoConnect(int state) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
        public void onAutoAnwer(int state) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
        public void onConnectedDevice(String mainDevice, String subDevice) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements GocCallbackBluetooth {
        private static final String DESCRIPTOR = "com.goodocom.bttek.bt.aidl.GocCallbackBluetooth";
        static final int TRANSACTION_onAdapterDiscoverableModeChanged = 3;
        static final int TRANSACTION_onAdapterDiscoveryFinished = 5;
        static final int TRANSACTION_onAdapterDiscoveryStarted = 4;
        static final int TRANSACTION_onAdapterStateChanged = 2;
        static final int TRANSACTION_onAutoAnwer = 17;
        static final int TRANSACTION_onAutoConnect = 16;
        static final int TRANSACTION_onBluetoothServiceReady = 1;
        static final int TRANSACTION_onBtAutoConnectStateChanged = 14;
        static final int TRANSACTION_onBtRoleModeChanged = 13;
        static final int TRANSACTION_onConnectedDevice = 18;
        static final int TRANSACTION_onDeviceAclDisconnected = 12;
        static final int TRANSACTION_onDeviceBondStateChanged = 8;
        static final int TRANSACTION_onDeviceFound = 7;
        static final int TRANSACTION_onDeviceOutOfRange = 11;
        static final int TRANSACTION_onDeviceUuidsUpdated = 9;
        static final int TRANSACTION_onLocalAdapterNameChanged = 10;
        static final int TRANSACTION_onMainDevicesChanged = 15;
        static final int TRANSACTION_retPairedDevices = 6;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static GocCallbackBluetooth asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof GocCallbackBluetooth)) {
                return new Proxy(obj);
            }
            return (GocCallbackBluetooth) iin;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code != 1598968902) {
                switch (code) {
                    case 1:
                        data.enforceInterface(DESCRIPTOR);
                        onBluetoothServiceReady();
                        reply.writeNoException();
                        return true;
                    case 2:
                        data.enforceInterface(DESCRIPTOR);
                        onAdapterStateChanged(data.readInt(), data.readInt());
                        reply.writeNoException();
                        return true;
                    case 3:
                        data.enforceInterface(DESCRIPTOR);
                        onAdapterDiscoverableModeChanged(data.readInt(), data.readInt());
                        reply.writeNoException();
                        return true;
                    case 4:
                        data.enforceInterface(DESCRIPTOR);
                        onAdapterDiscoveryStarted();
                        reply.writeNoException();
                        return true;
                    case 5:
                        data.enforceInterface(DESCRIPTOR);
                        onAdapterDiscoveryFinished();
                        reply.writeNoException();
                        return true;
                    case 6:
                        data.enforceInterface(DESCRIPTOR);
                        retPairedDevices(data.readInt(), data.createStringArray(), data.createStringArray(), data.createIntArray(), data.createByteArray());
                        reply.writeNoException();
                        return true;
                    case 7:
                        data.enforceInterface(DESCRIPTOR);
                        onDeviceFound(data.readString(), data.readString(), data.readByte());
                        reply.writeNoException();
                        return true;
                    case 8:
                        data.enforceInterface(DESCRIPTOR);
                        onDeviceBondStateChanged(data.readString(), data.readString(), data.readInt(), data.readInt());
                        reply.writeNoException();
                        return true;
                    case 9:
                        data.enforceInterface(DESCRIPTOR);
                        onDeviceUuidsUpdated(data.readString(), data.readString(), data.readInt());
                        reply.writeNoException();
                        return true;
                    case 10:
                        data.enforceInterface(DESCRIPTOR);
                        onLocalAdapterNameChanged(data.readString());
                        reply.writeNoException();
                        return true;
                    case 11:
                        data.enforceInterface(DESCRIPTOR);
                        onDeviceOutOfRange(data.readString());
                        reply.writeNoException();
                        return true;
                    case 12:
                        data.enforceInterface(DESCRIPTOR);
                        onDeviceAclDisconnected(data.readString());
                        reply.writeNoException();
                        return true;
                    case 13:
                        data.enforceInterface(DESCRIPTOR);
                        onBtRoleModeChanged(data.readInt());
                        reply.writeNoException();
                        return true;
                    case 14:
                        data.enforceInterface(DESCRIPTOR);
                        onBtAutoConnectStateChanged(data.readString(), data.readInt(), data.readInt());
                        reply.writeNoException();
                        return true;
                    case 15:
                        data.enforceInterface(DESCRIPTOR);
                        onMainDevicesChanged(data.readString(), data.readString());
                        reply.writeNoException();
                        return true;
                    case 16:
                        data.enforceInterface(DESCRIPTOR);
                        onAutoConnect(data.readInt());
                        reply.writeNoException();
                        return true;
                    case 17:
                        data.enforceInterface(DESCRIPTOR);
                        onAutoAnwer(data.readInt());
                        reply.writeNoException();
                        return true;
                    case 18:
                        data.enforceInterface(DESCRIPTOR);
                        onConnectedDevice(data.readString(), data.readString());
                        reply.writeNoException();
                        return true;
                    default:
                        return super.onTransact(code, data, reply, flags);
                }
            } else {
                reply.writeString(DESCRIPTOR);
                return true;
            }
        }

        /* access modifiers changed from: private */
        public static class Proxy implements GocCallbackBluetooth {
            public static GocCallbackBluetooth sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
            public void onBluetoothServiceReady() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onBluetoothServiceReady();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
            public void onAdapterStateChanged(int prevState, int newState) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(prevState);
                    _data.writeInt(newState);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onAdapterStateChanged(prevState, newState);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
            public void onAdapterDiscoverableModeChanged(int prevState, int newState) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(prevState);
                    _data.writeInt(newState);
                    if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onAdapterDiscoverableModeChanged(prevState, newState);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
            public void onAdapterDiscoveryStarted() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onAdapterDiscoveryStarted();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
            public void onAdapterDiscoveryFinished() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onAdapterDiscoveryFinished();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
            public void retPairedDevices(int elements, String[] address, String[] name, int[] supportProfile, byte[] category) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(elements);
                    _data.writeStringArray(address);
                    _data.writeStringArray(name);
                    _data.writeIntArray(supportProfile);
                    _data.writeByteArray(category);
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().retPairedDevices(elements, address, name, supportProfile, category);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
            public void onDeviceFound(String address, String name, byte category) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeString(name);
                    _data.writeByte(category);
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onDeviceFound(address, name, category);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
            public void onDeviceBondStateChanged(String address, String name, int prevState, int newState) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeString(name);
                    _data.writeInt(prevState);
                    _data.writeInt(newState);
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onDeviceBondStateChanged(address, name, prevState, newState);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
            public void onDeviceUuidsUpdated(String address, String name, int supportProfile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeString(name);
                    _data.writeInt(supportProfile);
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onDeviceUuidsUpdated(address, name, supportProfile);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
            public void onLocalAdapterNameChanged(String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onLocalAdapterNameChanged(name);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
            public void onDeviceOutOfRange(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onDeviceOutOfRange(address);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
            public void onDeviceAclDisconnected(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    if (this.mRemote.transact(12, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onDeviceAclDisconnected(address);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
            public void onBtRoleModeChanged(int mode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mode);
                    if (this.mRemote.transact(13, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onBtRoleModeChanged(mode);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
            public void onBtAutoConnectStateChanged(String address, int prevState, int newState) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(prevState);
                    _data.writeInt(newState);
                    if (this.mRemote.transact(14, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onBtAutoConnectStateChanged(address, prevState, newState);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
            public void onMainDevicesChanged(String address, String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeString(name);
                    if (this.mRemote.transact(15, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onMainDevicesChanged(address, name);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
            public void onAutoConnect(int state) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(state);
                    if (this.mRemote.transact(16, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onAutoConnect(state);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
            public void onAutoAnwer(int state) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(state);
                    if (this.mRemote.transact(17, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onAutoAnwer(state);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
            public void onConnectedDevice(String mainDevice, String subDevice) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(mainDevice);
                    _data.writeString(subDevice);
                    if (this.mRemote.transact(18, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onConnectedDevice(mainDevice, subDevice);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(GocCallbackBluetooth impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static GocCallbackBluetooth getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}

package com.goodocom.bttek.bt.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.goodocom.bttek.bt.aidl.GocCallbackMap;

public interface GocCommandMap extends IInterface {
    int getMapCurrentState(String str) throws RemoteException;

    int getMapRegisterState(String str) throws RemoteException;

    boolean isMapNotificationRegistered(String str) throws RemoteException;

    boolean isMapServiceReady() throws RemoteException;

    boolean registerMapCallback(GocCallbackMap gocCallbackMap) throws RemoteException;

    boolean reqMapChangeReadStatus(String str, int i, String str2, boolean z) throws RemoteException;

    void reqMapCleanDatabase() throws RemoteException;

    void reqMapDatabaseAvailable() throws RemoteException;

    void reqMapDeleteDatabaseByAddress(String str) throws RemoteException;

    boolean reqMapDeleteMessage(String str, int i, String str2) throws RemoteException;

    boolean reqMapDownloadInterrupt(String str) throws RemoteException;

    boolean reqMapDownloadMessage(String str, int i, boolean z, int i2, int i3, int i4, String str2, String str3, String str4, String str5, int i5, int i6) throws RemoteException;

    boolean reqMapDownloadSingleMessage(String str, int i, String str2, int i2) throws RemoteException;

    boolean reqMapRegisterNotification(String str, boolean z) throws RemoteException;

    boolean reqMapSendMessage(String str, String str2, String str3) throws RemoteException;

    void reqMapUnregisterNotification(String str) throws RemoteException;

    boolean setMapDownloadNotify(int i) throws RemoteException;

    boolean unregisterMapCallback(GocCallbackMap gocCallbackMap) throws RemoteException;

    public static class Default implements GocCommandMap {
        @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
        public boolean isMapServiceReady() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
        public boolean registerMapCallback(GocCallbackMap cb) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
        public boolean unregisterMapCallback(GocCallbackMap cb) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
        public boolean reqMapDownloadSingleMessage(String address, int folder, String handle, int storage) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
        public boolean reqMapDownloadMessage(String address, int folder, boolean isContentDownload, int count, int startPos, int storage, String periodBegin, String periodEnd, String sender, String recipient, int readStatus, int typeFilter) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
        public boolean reqMapRegisterNotification(String address, boolean downloadNewMessage) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
        public void reqMapUnregisterNotification(String address) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
        public boolean isMapNotificationRegistered(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
        public boolean reqMapDownloadInterrupt(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
        public void reqMapDatabaseAvailable() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
        public void reqMapDeleteDatabaseByAddress(String address) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
        public void reqMapCleanDatabase() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
        public int getMapCurrentState(String address) throws RemoteException {
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
        public int getMapRegisterState(String address) throws RemoteException {
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
        public boolean reqMapSendMessage(String address, String message, String target) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
        public boolean reqMapDeleteMessage(String address, int folder, String handle) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
        public boolean reqMapChangeReadStatus(String address, int folder, String handle, boolean isReadStatus) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
        public boolean setMapDownloadNotify(int frequency) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements GocCommandMap {
        private static final String DESCRIPTOR = "com.goodocom.bttek.bt.aidl.GocCommandMap";
        static final int TRANSACTION_getMapCurrentState = 13;
        static final int TRANSACTION_getMapRegisterState = 14;
        static final int TRANSACTION_isMapNotificationRegistered = 8;
        static final int TRANSACTION_isMapServiceReady = 1;
        static final int TRANSACTION_registerMapCallback = 2;
        static final int TRANSACTION_reqMapChangeReadStatus = 17;
        static final int TRANSACTION_reqMapCleanDatabase = 12;
        static final int TRANSACTION_reqMapDatabaseAvailable = 10;
        static final int TRANSACTION_reqMapDeleteDatabaseByAddress = 11;
        static final int TRANSACTION_reqMapDeleteMessage = 16;
        static final int TRANSACTION_reqMapDownloadInterrupt = 9;
        static final int TRANSACTION_reqMapDownloadMessage = 5;
        static final int TRANSACTION_reqMapDownloadSingleMessage = 4;
        static final int TRANSACTION_reqMapRegisterNotification = 6;
        static final int TRANSACTION_reqMapSendMessage = 15;
        static final int TRANSACTION_reqMapUnregisterNotification = 7;
        static final int TRANSACTION_setMapDownloadNotify = 18;
        static final int TRANSACTION_unregisterMapCallback = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static GocCommandMap asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof GocCommandMap)) {
                return new Proxy(obj);
            }
            return (GocCommandMap) iin;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code != 1598968902) {
                boolean _arg3 = false;
                switch (code) {
                    case 1:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isMapServiceReady = isMapServiceReady();
                        reply.writeNoException();
                        reply.writeInt(isMapServiceReady ? 1 : 0);
                        return true;
                    case 2:
                        data.enforceInterface(DESCRIPTOR);
                        boolean registerMapCallback = registerMapCallback(GocCallbackMap.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        reply.writeInt(registerMapCallback ? 1 : 0);
                        return true;
                    case 3:
                        data.enforceInterface(DESCRIPTOR);
                        boolean unregisterMapCallback = unregisterMapCallback(GocCallbackMap.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        reply.writeInt(unregisterMapCallback ? 1 : 0);
                        return true;
                    case 4:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqMapDownloadSingleMessage = reqMapDownloadSingleMessage(data.readString(), data.readInt(), data.readString(), data.readInt());
                        reply.writeNoException();
                        reply.writeInt(reqMapDownloadSingleMessage ? 1 : 0);
                        return true;
                    case 5:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqMapDownloadMessage = reqMapDownloadMessage(data.readString(), data.readInt(), data.readInt() != 0, data.readInt(), data.readInt(), data.readInt(), data.readString(), data.readString(), data.readString(), data.readString(), data.readInt(), data.readInt());
                        reply.writeNoException();
                        reply.writeInt(reqMapDownloadMessage ? 1 : 0);
                        return true;
                    case 6:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg0 = data.readString();
                        if (data.readInt() != 0) {
                            _arg3 = true;
                        }
                        boolean reqMapRegisterNotification = reqMapRegisterNotification(_arg0, _arg3);
                        reply.writeNoException();
                        reply.writeInt(reqMapRegisterNotification ? 1 : 0);
                        return true;
                    case 7:
                        data.enforceInterface(DESCRIPTOR);
                        reqMapUnregisterNotification(data.readString());
                        reply.writeNoException();
                        return true;
                    case 8:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isMapNotificationRegistered = isMapNotificationRegistered(data.readString());
                        reply.writeNoException();
                        reply.writeInt(isMapNotificationRegistered ? 1 : 0);
                        return true;
                    case 9:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqMapDownloadInterrupt = reqMapDownloadInterrupt(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqMapDownloadInterrupt ? 1 : 0);
                        return true;
                    case 10:
                        data.enforceInterface(DESCRIPTOR);
                        reqMapDatabaseAvailable();
                        reply.writeNoException();
                        return true;
                    case 11:
                        data.enforceInterface(DESCRIPTOR);
                        reqMapDeleteDatabaseByAddress(data.readString());
                        reply.writeNoException();
                        return true;
                    case 12:
                        data.enforceInterface(DESCRIPTOR);
                        reqMapCleanDatabase();
                        reply.writeNoException();
                        return true;
                    case 13:
                        data.enforceInterface(DESCRIPTOR);
                        int _result = getMapCurrentState(data.readString());
                        reply.writeNoException();
                        reply.writeInt(_result);
                        return true;
                    case 14:
                        data.enforceInterface(DESCRIPTOR);
                        int _result2 = getMapRegisterState(data.readString());
                        reply.writeNoException();
                        reply.writeInt(_result2);
                        return true;
                    case 15:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqMapSendMessage = reqMapSendMessage(data.readString(), data.readString(), data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqMapSendMessage ? 1 : 0);
                        return true;
                    case 16:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqMapDeleteMessage = reqMapDeleteMessage(data.readString(), data.readInt(), data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqMapDeleteMessage ? 1 : 0);
                        return true;
                    case 17:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg02 = data.readString();
                        int _arg1 = data.readInt();
                        String _arg2 = data.readString();
                        if (data.readInt() != 0) {
                            _arg3 = true;
                        }
                        boolean reqMapChangeReadStatus = reqMapChangeReadStatus(_arg02, _arg1, _arg2, _arg3);
                        reply.writeNoException();
                        reply.writeInt(reqMapChangeReadStatus ? 1 : 0);
                        return true;
                    case 18:
                        data.enforceInterface(DESCRIPTOR);
                        boolean mapDownloadNotify = setMapDownloadNotify(data.readInt());
                        reply.writeNoException();
                        reply.writeInt(mapDownloadNotify ? 1 : 0);
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
        public static class Proxy implements GocCommandMap {
            public static GocCommandMap sDefaultImpl;
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
            public boolean isMapServiceReady() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isMapServiceReady();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
            public boolean registerMapCallback(GocCallbackMap cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    boolean _result = false;
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().registerMapCallback(cb);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
            public boolean unregisterMapCallback(GocCallbackMap cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    boolean _result = false;
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().unregisterMapCallback(cb);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
            public boolean reqMapDownloadSingleMessage(String address, int folder, String handle, int storage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(folder);
                    _data.writeString(handle);
                    _data.writeInt(storage);
                    boolean _result = false;
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqMapDownloadSingleMessage(address, folder, handle, storage);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
            public boolean reqMapDownloadMessage(String address, int folder, boolean isContentDownload, int count, int startPos, int storage, String periodBegin, String periodEnd, String sender, String recipient, int readStatus, int typeFilter) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(folder);
                    boolean _result = false;
                    _data.writeInt(isContentDownload ? 1 : 0);
                    _data.writeInt(count);
                    _data.writeInt(startPos);
                    _data.writeInt(storage);
                    _data.writeString(periodBegin);
                    _data.writeString(periodEnd);
                    _data.writeString(sender);
                    _data.writeString(recipient);
                    _data.writeInt(readStatus);
                    _data.writeInt(typeFilter);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqMapDownloadMessage(address, folder, isContentDownload, count, startPos, storage, periodBegin, periodEnd, sender, recipient, readStatus, typeFilter);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
            public boolean reqMapRegisterNotification(String address, boolean downloadNewMessage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = true;
                    _data.writeInt(downloadNewMessage ? 1 : 0);
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqMapRegisterNotification(address, downloadNewMessage);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
            public void reqMapUnregisterNotification(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reqMapUnregisterNotification(address);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
            public boolean isMapNotificationRegistered(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isMapNotificationRegistered(address);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
            public boolean reqMapDownloadInterrupt(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqMapDownloadInterrupt(address);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
            public void reqMapDatabaseAvailable() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reqMapDatabaseAvailable();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
            public void reqMapDeleteDatabaseByAddress(String address) throws RemoteException {
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
                    Stub.getDefaultImpl().reqMapDeleteDatabaseByAddress(address);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
            public void reqMapCleanDatabase() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(12, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reqMapCleanDatabase();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
            public int getMapCurrentState(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMapCurrentState(address);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
            public int getMapRegisterState(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    if (!this.mRemote.transact(14, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMapRegisterState(address);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
            public boolean reqMapSendMessage(String address, String message, String target) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeString(message);
                    _data.writeString(target);
                    boolean _result = false;
                    if (!this.mRemote.transact(15, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqMapSendMessage(address, message, target);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
            public boolean reqMapDeleteMessage(String address, int folder, String handle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(folder);
                    _data.writeString(handle);
                    boolean _result = false;
                    if (!this.mRemote.transact(16, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqMapDeleteMessage(address, folder, handle);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
            public boolean reqMapChangeReadStatus(String address, int folder, String handle, boolean isReadStatus) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(folder);
                    _data.writeString(handle);
                    boolean _result = true;
                    _data.writeInt(isReadStatus ? 1 : 0);
                    if (!this.mRemote.transact(17, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqMapChangeReadStatus(address, folder, handle, isReadStatus);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
            public boolean setMapDownloadNotify(int frequency) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(frequency);
                    boolean _result = false;
                    if (!this.mRemote.transact(18, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setMapDownloadNotify(frequency);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(GocCommandMap impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static GocCommandMap getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}

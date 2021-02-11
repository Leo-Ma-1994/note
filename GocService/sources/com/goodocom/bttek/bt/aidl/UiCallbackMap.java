package com.goodocom.bttek.bt.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface UiCallbackMap extends IInterface {
    void onMapDownloadNotify(String str, int i, int i2, int i3) throws RemoteException;

    void onMapMemoryAvailableEvent(String str, int i, boolean z) throws RemoteException;

    void onMapMessageDeletedEvent(String str, String str2, int i, int i2) throws RemoteException;

    void onMapMessageDeliverStatusEvent(String str, String str2, boolean z) throws RemoteException;

    void onMapMessageSendingStatusEvent(String str, String str2, boolean z) throws RemoteException;

    void onMapMessageShiftedEvent(String str, String str2, int i, int i2, int i3) throws RemoteException;

    void onMapNewMessageReceivedEvent(String str, String str2, String str3, String str4) throws RemoteException;

    void onMapServiceReady() throws RemoteException;

    void onMapStateChanged(String str, int i, int i2, int i3) throws RemoteException;

    void retMapChangeReadStatusCompleted(String str, String str2, int i) throws RemoteException;

    void retMapCleanDatabaseCompleted(boolean z) throws RemoteException;

    void retMapDatabaseAvailable() throws RemoteException;

    void retMapDeleteDatabaseByAddressCompleted(String str, boolean z) throws RemoteException;

    void retMapDeleteMessageCompleted(String str, String str2, int i) throws RemoteException;

    void retMapDownloadedMessage(String str, String str2, String str3, String str4, String str5, String str6, int i, int i2, boolean z, String str7, String str8) throws RemoteException;

    void retMapSendMessageCompleted(String str, String str2, int i) throws RemoteException;

    public static class Default implements UiCallbackMap {
        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void onMapServiceReady() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void onMapStateChanged(String address, int prevState, int newState, int reason) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void retMapDownloadedMessage(String address, String handle, String senderName, String senderNumber, String recipientNumber, String date, int type, int folder, boolean isReadStatus, String subject, String message) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void onMapNewMessageReceivedEvent(String address, String handle, String sender, String message) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void onMapDownloadNotify(String address, int folder, int totalMessages, int currentMessages) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void retMapDatabaseAvailable() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void retMapDeleteDatabaseByAddressCompleted(String address, boolean isSuccess) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void retMapCleanDatabaseCompleted(boolean isSuccess) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void retMapSendMessageCompleted(String address, String target, int state) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void retMapDeleteMessageCompleted(String address, String handle, int reason) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void retMapChangeReadStatusCompleted(String address, String handle, int reason) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void onMapMemoryAvailableEvent(String address, int structure, boolean available) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void onMapMessageSendingStatusEvent(String address, String handle, boolean isSuccess) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void onMapMessageDeliverStatusEvent(String address, String handle, boolean isSuccess) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void onMapMessageShiftedEvent(String address, String handle, int type, int newFolder, int oldFolder) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void onMapMessageDeletedEvent(String address, String handle, int type, int folder) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements UiCallbackMap {
        private static final String DESCRIPTOR = "com.goodocom.bttek.bt.aidl.UiCallbackMap";
        static final int TRANSACTION_onMapDownloadNotify = 5;
        static final int TRANSACTION_onMapMemoryAvailableEvent = 12;
        static final int TRANSACTION_onMapMessageDeletedEvent = 16;
        static final int TRANSACTION_onMapMessageDeliverStatusEvent = 14;
        static final int TRANSACTION_onMapMessageSendingStatusEvent = 13;
        static final int TRANSACTION_onMapMessageShiftedEvent = 15;
        static final int TRANSACTION_onMapNewMessageReceivedEvent = 4;
        static final int TRANSACTION_onMapServiceReady = 1;
        static final int TRANSACTION_onMapStateChanged = 2;
        static final int TRANSACTION_retMapChangeReadStatusCompleted = 11;
        static final int TRANSACTION_retMapCleanDatabaseCompleted = 8;
        static final int TRANSACTION_retMapDatabaseAvailable = 6;
        static final int TRANSACTION_retMapDeleteDatabaseByAddressCompleted = 7;
        static final int TRANSACTION_retMapDeleteMessageCompleted = 10;
        static final int TRANSACTION_retMapDownloadedMessage = 3;
        static final int TRANSACTION_retMapSendMessageCompleted = 9;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static UiCallbackMap asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof UiCallbackMap)) {
                return new Proxy(obj);
            }
            return (UiCallbackMap) iin;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code != 1598968902) {
                boolean _arg2 = false;
                switch (code) {
                    case 1:
                        data.enforceInterface(DESCRIPTOR);
                        onMapServiceReady();
                        reply.writeNoException();
                        return true;
                    case 2:
                        data.enforceInterface(DESCRIPTOR);
                        onMapStateChanged(data.readString(), data.readInt(), data.readInt(), data.readInt());
                        reply.writeNoException();
                        return true;
                    case 3:
                        data.enforceInterface(DESCRIPTOR);
                        retMapDownloadedMessage(data.readString(), data.readString(), data.readString(), data.readString(), data.readString(), data.readString(), data.readInt(), data.readInt(), data.readInt() != 0, data.readString(), data.readString());
                        reply.writeNoException();
                        return true;
                    case 4:
                        data.enforceInterface(DESCRIPTOR);
                        onMapNewMessageReceivedEvent(data.readString(), data.readString(), data.readString(), data.readString());
                        reply.writeNoException();
                        return true;
                    case 5:
                        data.enforceInterface(DESCRIPTOR);
                        onMapDownloadNotify(data.readString(), data.readInt(), data.readInt(), data.readInt());
                        reply.writeNoException();
                        return true;
                    case 6:
                        data.enforceInterface(DESCRIPTOR);
                        retMapDatabaseAvailable();
                        reply.writeNoException();
                        return true;
                    case 7:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg0 = data.readString();
                        if (data.readInt() != 0) {
                            _arg2 = true;
                        }
                        retMapDeleteDatabaseByAddressCompleted(_arg0, _arg2);
                        reply.writeNoException();
                        return true;
                    case 8:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg2 = true;
                        }
                        retMapCleanDatabaseCompleted(_arg2);
                        reply.writeNoException();
                        return true;
                    case 9:
                        data.enforceInterface(DESCRIPTOR);
                        retMapSendMessageCompleted(data.readString(), data.readString(), data.readInt());
                        reply.writeNoException();
                        return true;
                    case 10:
                        data.enforceInterface(DESCRIPTOR);
                        retMapDeleteMessageCompleted(data.readString(), data.readString(), data.readInt());
                        reply.writeNoException();
                        return true;
                    case 11:
                        data.enforceInterface(DESCRIPTOR);
                        retMapChangeReadStatusCompleted(data.readString(), data.readString(), data.readInt());
                        reply.writeNoException();
                        return true;
                    case 12:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg02 = data.readString();
                        int _arg1 = data.readInt();
                        if (data.readInt() != 0) {
                            _arg2 = true;
                        }
                        onMapMemoryAvailableEvent(_arg02, _arg1, _arg2);
                        reply.writeNoException();
                        return true;
                    case 13:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg03 = data.readString();
                        String _arg12 = data.readString();
                        if (data.readInt() != 0) {
                            _arg2 = true;
                        }
                        onMapMessageSendingStatusEvent(_arg03, _arg12, _arg2);
                        reply.writeNoException();
                        return true;
                    case 14:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg04 = data.readString();
                        String _arg13 = data.readString();
                        if (data.readInt() != 0) {
                            _arg2 = true;
                        }
                        onMapMessageDeliverStatusEvent(_arg04, _arg13, _arg2);
                        reply.writeNoException();
                        return true;
                    case 15:
                        data.enforceInterface(DESCRIPTOR);
                        onMapMessageShiftedEvent(data.readString(), data.readString(), data.readInt(), data.readInt(), data.readInt());
                        reply.writeNoException();
                        return true;
                    case 16:
                        data.enforceInterface(DESCRIPTOR);
                        onMapMessageDeletedEvent(data.readString(), data.readString(), data.readInt(), data.readInt());
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
        public static class Proxy implements UiCallbackMap {
            public static UiCallbackMap sDefaultImpl;
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

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
            public void onMapServiceReady() throws RemoteException {
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
                    Stub.getDefaultImpl().onMapServiceReady();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
            public void onMapStateChanged(String address, int prevState, int newState, int reason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(prevState);
                    _data.writeInt(newState);
                    _data.writeInt(reason);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onMapStateChanged(address, prevState, newState, reason);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
            public void retMapDownloadedMessage(String address, String handle, String senderName, String senderNumber, String recipientNumber, String date, int type, int folder, boolean isReadStatus, String subject, String message) throws RemoteException {
                Throwable th;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(address);
                        _data.writeString(handle);
                        _data.writeString(senderName);
                        _data.writeString(senderNumber);
                        _data.writeString(recipientNumber);
                        _data.writeString(date);
                        _data.writeInt(type);
                        _data.writeInt(folder);
                        _data.writeInt(isReadStatus ? 1 : 0);
                        _data.writeString(subject);
                        _data.writeString(message);
                        if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                            _reply.readException();
                            _reply.recycle();
                            _data.recycle();
                            return;
                        }
                        Stub.getDefaultImpl().retMapDownloadedMessage(address, handle, senderName, senderNumber, recipientNumber, date, type, folder, isReadStatus, subject, message);
                        _reply.recycle();
                        _data.recycle();
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
            public void onMapNewMessageReceivedEvent(String address, String handle, String sender, String message) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeString(handle);
                    _data.writeString(sender);
                    _data.writeString(message);
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onMapNewMessageReceivedEvent(address, handle, sender, message);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
            public void onMapDownloadNotify(String address, int folder, int totalMessages, int currentMessages) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(folder);
                    _data.writeInt(totalMessages);
                    _data.writeInt(currentMessages);
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onMapDownloadNotify(address, folder, totalMessages, currentMessages);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
            public void retMapDatabaseAvailable() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().retMapDatabaseAvailable();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
            public void retMapDeleteDatabaseByAddressCompleted(String address, boolean isSuccess) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(isSuccess ? 1 : 0);
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().retMapDeleteDatabaseByAddressCompleted(address, isSuccess);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
            public void retMapCleanDatabaseCompleted(boolean isSuccess) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(isSuccess ? 1 : 0);
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().retMapCleanDatabaseCompleted(isSuccess);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
            public void retMapSendMessageCompleted(String address, String target, int state) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeString(target);
                    _data.writeInt(state);
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().retMapSendMessageCompleted(address, target, state);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
            public void retMapDeleteMessageCompleted(String address, String handle, int reason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeString(handle);
                    _data.writeInt(reason);
                    if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().retMapDeleteMessageCompleted(address, handle, reason);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
            public void retMapChangeReadStatusCompleted(String address, String handle, int reason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeString(handle);
                    _data.writeInt(reason);
                    if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().retMapChangeReadStatusCompleted(address, handle, reason);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
            public void onMapMemoryAvailableEvent(String address, int structure, boolean available) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(structure);
                    _data.writeInt(available ? 1 : 0);
                    if (this.mRemote.transact(12, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onMapMemoryAvailableEvent(address, structure, available);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
            public void onMapMessageSendingStatusEvent(String address, String handle, boolean isSuccess) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeString(handle);
                    _data.writeInt(isSuccess ? 1 : 0);
                    if (this.mRemote.transact(13, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onMapMessageSendingStatusEvent(address, handle, isSuccess);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
            public void onMapMessageDeliverStatusEvent(String address, String handle, boolean isSuccess) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeString(handle);
                    _data.writeInt(isSuccess ? 1 : 0);
                    if (this.mRemote.transact(14, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onMapMessageDeliverStatusEvent(address, handle, isSuccess);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
            public void onMapMessageShiftedEvent(String address, String handle, int type, int newFolder, int oldFolder) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeString(handle);
                    _data.writeInt(type);
                    _data.writeInt(newFolder);
                    _data.writeInt(oldFolder);
                    if (this.mRemote.transact(15, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onMapMessageShiftedEvent(address, handle, type, newFolder, oldFolder);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
            public void onMapMessageDeletedEvent(String address, String handle, int type, int folder) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeString(handle);
                    _data.writeInt(type);
                    _data.writeInt(folder);
                    if (this.mRemote.transact(16, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onMapMessageDeletedEvent(address, handle, type, folder);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(UiCallbackMap impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static UiCallbackMap getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}

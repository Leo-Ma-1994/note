package com.goodocom.bttek.bt.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.RemoteException;
import com.goodocom.bttek.bt.aidl.GocCallbackGattServer;
import java.util.List;

public interface GocCommandGattServer extends IInterface {
    List<ParcelUuid> getGattAddedGattCharacteristicUuidList(ParcelUuid parcelUuid) throws RemoteException;

    List<ParcelUuid> getGattAddedGattDescriptorUuidList(ParcelUuid parcelUuid, ParcelUuid parcelUuid2) throws RemoteException;

    List<ParcelUuid> getGattAddedGattServiceUuidList() throws RemoteException;

    int getGattServerConnectionState() throws RemoteException;

    boolean isGattServiceReady() throws RemoteException;

    boolean registerGattServerCallback(GocCallbackGattServer gocCallbackGattServer) throws RemoteException;

    boolean reqGattServerAddCharacteristic(ParcelUuid parcelUuid, int i, int i2) throws RemoteException;

    boolean reqGattServerAddDescriptor(ParcelUuid parcelUuid, int i) throws RemoteException;

    boolean reqGattServerBeginServiceDeclaration(int i, ParcelUuid parcelUuid) throws RemoteException;

    boolean reqGattServerClearServices() throws RemoteException;

    boolean reqGattServerDisconnect(String str) throws RemoteException;

    boolean reqGattServerEndServiceDeclaration() throws RemoteException;

    boolean reqGattServerListen(boolean z) throws RemoteException;

    boolean reqGattServerRemoveService(int i, ParcelUuid parcelUuid) throws RemoteException;

    boolean reqGattServerSendNotification(String str, int i, ParcelUuid parcelUuid, ParcelUuid parcelUuid2, boolean z, byte[] bArr) throws RemoteException;

    boolean reqGattServerSendResponse(String str, int i, int i2, int i3, byte[] bArr) throws RemoteException;

    boolean unregisterGattServerCallback(GocCallbackGattServer gocCallbackGattServer) throws RemoteException;

    public static class Default implements GocCommandGattServer {
        @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
        public boolean isGattServiceReady() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
        public boolean registerGattServerCallback(GocCallbackGattServer cb) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
        public boolean unregisterGattServerCallback(GocCallbackGattServer cb) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
        public int getGattServerConnectionState() throws RemoteException {
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
        public boolean reqGattServerDisconnect(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
        public boolean reqGattServerBeginServiceDeclaration(int srvcType, ParcelUuid srvcUuid) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
        public boolean reqGattServerAddCharacteristic(ParcelUuid charUuid, int properties, int permissions) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
        public boolean reqGattServerAddDescriptor(ParcelUuid descUuid, int permissions) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
        public boolean reqGattServerEndServiceDeclaration() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
        public boolean reqGattServerRemoveService(int srvcType, ParcelUuid srvcUuid) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
        public boolean reqGattServerClearServices() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
        public boolean reqGattServerListen(boolean listen) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
        public boolean reqGattServerSendResponse(String address, int requestId, int status, int offset, byte[] value) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
        public boolean reqGattServerSendNotification(String address, int srvcType, ParcelUuid srvcUuid, ParcelUuid charUuid, boolean confirm, byte[] value) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
        public List<ParcelUuid> getGattAddedGattServiceUuidList() throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
        public List<ParcelUuid> getGattAddedGattCharacteristicUuidList(ParcelUuid srvcUuid) throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
        public List<ParcelUuid> getGattAddedGattDescriptorUuidList(ParcelUuid srvcUuid, ParcelUuid charUuid) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements GocCommandGattServer {
        private static final String DESCRIPTOR = "com.goodocom.bttek.bt.aidl.GocCommandGattServer";
        static final int TRANSACTION_getGattAddedGattCharacteristicUuidList = 16;
        static final int TRANSACTION_getGattAddedGattDescriptorUuidList = 17;
        static final int TRANSACTION_getGattAddedGattServiceUuidList = 15;
        static final int TRANSACTION_getGattServerConnectionState = 4;
        static final int TRANSACTION_isGattServiceReady = 1;
        static final int TRANSACTION_registerGattServerCallback = 2;
        static final int TRANSACTION_reqGattServerAddCharacteristic = 7;
        static final int TRANSACTION_reqGattServerAddDescriptor = 8;
        static final int TRANSACTION_reqGattServerBeginServiceDeclaration = 6;
        static final int TRANSACTION_reqGattServerClearServices = 11;
        static final int TRANSACTION_reqGattServerDisconnect = 5;
        static final int TRANSACTION_reqGattServerEndServiceDeclaration = 9;
        static final int TRANSACTION_reqGattServerListen = 12;
        static final int TRANSACTION_reqGattServerRemoveService = 10;
        static final int TRANSACTION_reqGattServerSendNotification = 14;
        static final int TRANSACTION_reqGattServerSendResponse = 13;
        static final int TRANSACTION_unregisterGattServerCallback = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static GocCommandGattServer asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof GocCommandGattServer)) {
                return new Proxy(obj);
            }
            return (GocCommandGattServer) iin;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            ParcelUuid _arg1;
            ParcelUuid _arg0;
            ParcelUuid _arg02;
            ParcelUuid _arg12;
            ParcelUuid _arg2;
            ParcelUuid _arg3;
            ParcelUuid _arg03;
            ParcelUuid _arg04;
            ParcelUuid _arg13;
            if (code != 1598968902) {
                boolean _arg05 = false;
                switch (code) {
                    case 1:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isGattServiceReady = isGattServiceReady();
                        reply.writeNoException();
                        reply.writeInt(isGattServiceReady ? 1 : 0);
                        return true;
                    case 2:
                        data.enforceInterface(DESCRIPTOR);
                        boolean registerGattServerCallback = registerGattServerCallback(GocCallbackGattServer.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        reply.writeInt(registerGattServerCallback ? 1 : 0);
                        return true;
                    case 3:
                        data.enforceInterface(DESCRIPTOR);
                        boolean unregisterGattServerCallback = unregisterGattServerCallback(GocCallbackGattServer.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        reply.writeInt(unregisterGattServerCallback ? 1 : 0);
                        return true;
                    case 4:
                        data.enforceInterface(DESCRIPTOR);
                        int _result = getGattServerConnectionState();
                        reply.writeNoException();
                        reply.writeInt(_result);
                        return true;
                    case 5:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqGattServerDisconnect = reqGattServerDisconnect(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqGattServerDisconnect ? 1 : 0);
                        return true;
                    case 6:
                        data.enforceInterface(DESCRIPTOR);
                        int _arg06 = data.readInt();
                        if (data.readInt() != 0) {
                            _arg1 = (ParcelUuid) ParcelUuid.CREATOR.createFromParcel(data);
                        } else {
                            _arg1 = null;
                        }
                        boolean reqGattServerBeginServiceDeclaration = reqGattServerBeginServiceDeclaration(_arg06, _arg1);
                        reply.writeNoException();
                        reply.writeInt(reqGattServerBeginServiceDeclaration ? 1 : 0);
                        return true;
                    case 7:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg0 = (ParcelUuid) ParcelUuid.CREATOR.createFromParcel(data);
                        } else {
                            _arg0 = null;
                        }
                        boolean reqGattServerAddCharacteristic = reqGattServerAddCharacteristic(_arg0, data.readInt(), data.readInt());
                        reply.writeNoException();
                        reply.writeInt(reqGattServerAddCharacteristic ? 1 : 0);
                        return true;
                    case 8:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg02 = (ParcelUuid) ParcelUuid.CREATOR.createFromParcel(data);
                        } else {
                            _arg02 = null;
                        }
                        boolean reqGattServerAddDescriptor = reqGattServerAddDescriptor(_arg02, data.readInt());
                        reply.writeNoException();
                        reply.writeInt(reqGattServerAddDescriptor ? 1 : 0);
                        return true;
                    case 9:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqGattServerEndServiceDeclaration = reqGattServerEndServiceDeclaration();
                        reply.writeNoException();
                        reply.writeInt(reqGattServerEndServiceDeclaration ? 1 : 0);
                        return true;
                    case 10:
                        data.enforceInterface(DESCRIPTOR);
                        int _arg07 = data.readInt();
                        if (data.readInt() != 0) {
                            _arg12 = (ParcelUuid) ParcelUuid.CREATOR.createFromParcel(data);
                        } else {
                            _arg12 = null;
                        }
                        boolean reqGattServerRemoveService = reqGattServerRemoveService(_arg07, _arg12);
                        reply.writeNoException();
                        reply.writeInt(reqGattServerRemoveService ? 1 : 0);
                        return true;
                    case 11:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqGattServerClearServices = reqGattServerClearServices();
                        reply.writeNoException();
                        reply.writeInt(reqGattServerClearServices ? 1 : 0);
                        return true;
                    case 12:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg05 = true;
                        }
                        boolean reqGattServerListen = reqGattServerListen(_arg05);
                        reply.writeNoException();
                        reply.writeInt(reqGattServerListen ? 1 : 0);
                        return true;
                    case 13:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqGattServerSendResponse = reqGattServerSendResponse(data.readString(), data.readInt(), data.readInt(), data.readInt(), data.createByteArray());
                        reply.writeNoException();
                        reply.writeInt(reqGattServerSendResponse ? 1 : 0);
                        return true;
                    case 14:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg08 = data.readString();
                        int _arg14 = data.readInt();
                        if (data.readInt() != 0) {
                            _arg2 = (ParcelUuid) ParcelUuid.CREATOR.createFromParcel(data);
                        } else {
                            _arg2 = null;
                        }
                        if (data.readInt() != 0) {
                            _arg3 = (ParcelUuid) ParcelUuid.CREATOR.createFromParcel(data);
                        } else {
                            _arg3 = null;
                        }
                        boolean reqGattServerSendNotification = reqGattServerSendNotification(_arg08, _arg14, _arg2, _arg3, data.readInt() != 0, data.createByteArray());
                        reply.writeNoException();
                        reply.writeInt(reqGattServerSendNotification ? 1 : 0);
                        return true;
                    case 15:
                        data.enforceInterface(DESCRIPTOR);
                        List<ParcelUuid> _result2 = getGattAddedGattServiceUuidList();
                        reply.writeNoException();
                        reply.writeTypedList(_result2);
                        return true;
                    case 16:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg03 = (ParcelUuid) ParcelUuid.CREATOR.createFromParcel(data);
                        } else {
                            _arg03 = null;
                        }
                        List<ParcelUuid> _result3 = getGattAddedGattCharacteristicUuidList(_arg03);
                        reply.writeNoException();
                        reply.writeTypedList(_result3);
                        return true;
                    case 17:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg04 = (ParcelUuid) ParcelUuid.CREATOR.createFromParcel(data);
                        } else {
                            _arg04 = null;
                        }
                        if (data.readInt() != 0) {
                            _arg13 = (ParcelUuid) ParcelUuid.CREATOR.createFromParcel(data);
                        } else {
                            _arg13 = null;
                        }
                        List<ParcelUuid> _result4 = getGattAddedGattDescriptorUuidList(_arg04, _arg13);
                        reply.writeNoException();
                        reply.writeTypedList(_result4);
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
        public static class Proxy implements GocCommandGattServer {
            public static GocCommandGattServer sDefaultImpl;
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
            public boolean isGattServiceReady() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isGattServiceReady();
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
            public boolean registerGattServerCallback(GocCallbackGattServer cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    boolean _result = false;
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().registerGattServerCallback(cb);
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
            public boolean unregisterGattServerCallback(GocCallbackGattServer cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    boolean _result = false;
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().unregisterGattServerCallback(cb);
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
            public int getGattServerConnectionState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getGattServerConnectionState();
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
            public boolean reqGattServerDisconnect(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqGattServerDisconnect(address);
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
            public boolean reqGattServerBeginServiceDeclaration(int srvcType, ParcelUuid srvcUuid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(srvcType);
                    boolean _result = true;
                    if (srvcUuid != null) {
                        _data.writeInt(1);
                        srvcUuid.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqGattServerBeginServiceDeclaration(srvcType, srvcUuid);
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
            public boolean reqGattServerAddCharacteristic(ParcelUuid charUuid, int properties, int permissions) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (charUuid != null) {
                        _data.writeInt(1);
                        charUuid.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(properties);
                    _data.writeInt(permissions);
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqGattServerAddCharacteristic(charUuid, properties, permissions);
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
            public boolean reqGattServerAddDescriptor(ParcelUuid descUuid, int permissions) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (descUuid != null) {
                        _data.writeInt(1);
                        descUuid.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(permissions);
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqGattServerAddDescriptor(descUuid, permissions);
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
            public boolean reqGattServerEndServiceDeclaration() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqGattServerEndServiceDeclaration();
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
            public boolean reqGattServerRemoveService(int srvcType, ParcelUuid srvcUuid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(srvcType);
                    boolean _result = true;
                    if (srvcUuid != null) {
                        _data.writeInt(1);
                        srvcUuid.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqGattServerRemoveService(srvcType, srvcUuid);
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
            public boolean reqGattServerClearServices() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqGattServerClearServices();
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
            public boolean reqGattServerListen(boolean listen) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    _data.writeInt(listen ? 1 : 0);
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqGattServerListen(listen);
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
            public boolean reqGattServerSendResponse(String address, int requestId, int status, int offset, byte[] value) throws RemoteException {
                Throwable th;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(address);
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(requestId);
                        try {
                            _data.writeInt(status);
                            try {
                                _data.writeInt(offset);
                            } catch (Throwable th3) {
                                th = th3;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeByteArray(value);
                        try {
                            boolean _result = false;
                            if (this.mRemote.transact(13, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                if (_reply.readInt() != 0) {
                                    _result = true;
                                }
                                _reply.recycle();
                                _data.recycle();
                                return _result;
                            }
                            boolean reqGattServerSendResponse = Stub.getDefaultImpl().reqGattServerSendResponse(address, requestId, status, offset, value);
                            _reply.recycle();
                            _data.recycle();
                            return reqGattServerSendResponse;
                        } catch (Throwable th6) {
                            th = th6;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th7) {
                        th = th7;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th8) {
                    th = th8;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
            public boolean reqGattServerSendNotification(String address, int srvcType, ParcelUuid srvcUuid, ParcelUuid charUuid, boolean confirm, byte[] value) throws RemoteException {
                Throwable th;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(address);
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(srvcType);
                        boolean _result = true;
                        if (srvcUuid != null) {
                            _data.writeInt(1);
                            srvcUuid.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        if (charUuid != null) {
                            _data.writeInt(1);
                            charUuid.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        _data.writeInt(confirm ? 1 : 0);
                        try {
                            _data.writeByteArray(value);
                        } catch (Throwable th3) {
                            th = th3;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            if (this.mRemote.transact(14, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                if (_reply.readInt() == 0) {
                                    _result = false;
                                }
                                _reply.recycle();
                                _data.recycle();
                                return _result;
                            }
                            boolean reqGattServerSendNotification = Stub.getDefaultImpl().reqGattServerSendNotification(address, srvcType, srvcUuid, charUuid, confirm, value);
                            _reply.recycle();
                            _data.recycle();
                            return reqGattServerSendNotification;
                        } catch (Throwable th4) {
                            th = th4;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
            public List<ParcelUuid> getGattAddedGattServiceUuidList() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(15, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getGattAddedGattServiceUuidList();
                    }
                    _reply.readException();
                    List<ParcelUuid> _result = _reply.createTypedArrayList(ParcelUuid.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
            public List<ParcelUuid> getGattAddedGattCharacteristicUuidList(ParcelUuid srvcUuid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (srvcUuid != null) {
                        _data.writeInt(1);
                        srvcUuid.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(16, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getGattAddedGattCharacteristicUuidList(srvcUuid);
                    }
                    _reply.readException();
                    List<ParcelUuid> _result = _reply.createTypedArrayList(ParcelUuid.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
            public List<ParcelUuid> getGattAddedGattDescriptorUuidList(ParcelUuid srvcUuid, ParcelUuid charUuid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (srvcUuid != null) {
                        _data.writeInt(1);
                        srvcUuid.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (charUuid != null) {
                        _data.writeInt(1);
                        charUuid.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(17, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getGattAddedGattDescriptorUuidList(srvcUuid, charUuid);
                    }
                    _reply.readException();
                    List<ParcelUuid> _result = _reply.createTypedArrayList(ParcelUuid.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(GocCommandGattServer impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static GocCommandGattServer getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}

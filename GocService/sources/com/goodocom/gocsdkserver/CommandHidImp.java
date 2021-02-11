package com.goodocom.gocsdkserver;

import android.os.RemoteException;
import com.goodocom.bttek.bt.aidl.GocCallbackHid;
import com.goodocom.bttek.bt.aidl.GocCommandHid;

public class CommandHidImp extends GocCommandHid.Stub {
    private GocsdkService service;

    public CommandHidImp(GocsdkService service2) {
        this.service = service2;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHid
    public boolean isHidServiceReady() throws RemoteException {
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHid
    public boolean registerHidCallback(GocCallbackHid cb) throws RemoteException {
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHid
    public boolean unregisterHidCallback(GocCallbackHid cb) throws RemoteException {
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHid
    public boolean reqHidConnect(String address) throws RemoteException {
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHid
    public boolean reqHidDisconnect(String address) throws RemoteException {
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHid
    public boolean reqSendHidMouseCommand(int button, int offset_x, int offset_y, int wheel) throws RemoteException {
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHid
    public boolean reqSendHidVirtualKeyCommand(int key_1, int key_2) throws RemoteException {
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHid
    public int getHidConnectionState() throws RemoteException {
        return 0;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHid
    public boolean isHidConnected() throws RemoteException {
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHid
    public String getHidConnectedAddress() throws RemoteException {
        return null;
    }
}

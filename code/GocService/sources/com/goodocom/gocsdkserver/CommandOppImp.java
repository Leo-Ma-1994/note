package com.goodocom.gocsdkserver;

import android.os.RemoteException;
import com.goodocom.bttek.bt.aidl.GocCallbackOpp;
import com.goodocom.bttek.bt.aidl.GocCommandOpp;

public class CommandOppImp extends GocCommandOpp.Stub {
    private GocsdkService service;

    public CommandOppImp(GocsdkService service2) {
        this.service = service2;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandOpp
    public boolean isOppServiceReady() throws RemoteException {
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandOpp
    public boolean registerOppCallback(GocCallbackOpp cb) throws RemoteException {
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandOpp
    public boolean unregisterOppCallback(GocCallbackOpp cb) throws RemoteException {
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandOpp
    public boolean setOppFilePath(String path) throws RemoteException {
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandOpp
    public String getOppFilePath() throws RemoteException {
        return null;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandOpp
    public boolean reqOppAcceptReceiveFile(boolean accept) throws RemoteException {
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandOpp
    public boolean reqOppInterruptReceiveFile() throws RemoteException {
        return false;
    }
}

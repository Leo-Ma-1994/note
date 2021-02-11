package com.goodocom.gocsdk;

import android.bluetooth.BluetoothDevice;
import com.goodocom.gocsdk.domain.BlueToothConnectInfo;
import com.goodocom.gocsdk.domain.BlueToothPairedInfo;
import com.goodocom.gocsdk.domain.CallLogInfo;
import com.goodocom.gocsdk.domain.PhoneBookInfo;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GocAppData {
    private static final GocAppData INSTANCE = new GocAppData();
    public static final boolean isGoc = false;
    public static int sCurrentState = 0;
    public Set<String> mAddrCod = new HashSet();
    public List<CallLogInfo> mComingCalllog = new ArrayList();
    public List<BlueToothConnectInfo> mConnectList = new ArrayList(2);
    public Set mConnectedMap = new HashSet();
    public List<PhoneBookInfo> mContacts = new ArrayList();
    public BluetoothDevice mCurrentBluetoothDevice;
    public boolean mIsloadFinished = false;
    public List<CallLogInfo> mMissCalllog = new ArrayList();
    public BluetoothDevice mOtherBluetoothDevice;
    public List<CallLogInfo> mOutCalllog = new ArrayList();
    public List<BlueToothPairedInfo> mParedList = new ArrayList();
    public List<BluetoothDevice> mParedList1 = new ArrayList();

    private GocAppData() {
    }

    public static GocAppData getInstance() {
        return INSTANCE;
    }
}

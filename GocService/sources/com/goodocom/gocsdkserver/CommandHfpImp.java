package com.goodocom.gocsdkserver;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.goodocom.bttek.BuildConfig;
import com.goodocom.bttek.bt.GocApplication;
import com.goodocom.bttek.bt.aidl.GocCallbackHfp;
import com.goodocom.bttek.bt.aidl.GocCommandHfp;
import com.goodocom.bttek.bt.aidl.GocHfpClientCall;
import com.goodocom.bttek.bt.bean.BluetoothDevice;
import com.goodocom.bttek.bt.bean.DeviceInfo;
import com.goodocom.bttek.bt.res.NfDef;
import com.goodocom.gocDataBase.GocPbapHelper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class CommandHfpImp extends GocCommandHfp.Stub {
    public static final int HF_CALLING = 4;
    public static final int HF_CALLING_TIME = 1;
    public static final int HF_CONNECTED = 3;
    public static final int HF_CONNECTING = 2;
    public static final int HF_DISCONNECTED = 1;
    public static final int HF_GET_CALLLOG = 4;
    public static final int HF_HELD = 8;
    public static final int HF_INCOMING = 5;
    public static final int HF_MSG_DELAY_REQUST_CALLLOG_HANGUP = 5;
    public static final int HF_SECOND_HANGUP = 3;
    public static final int HF_SECOND_INCOMING = 2;
    public static final int HF_TALKING = 6;
    public static final int HF_WAITING = 7;
    public static final int STATE_CONNECTED = 140;
    public static final int STATE_CONNECTING = 120;
    public static final int STATE_NOT_INITIALIZED = 100;
    public static final int STATE_READY = 110;
    private static final String TAG = "GoodocomHfpImp";
    private int PhoneId = 1;
    private AutoAnswerTask autoAnswerTask = null;
    private long autoAnswerTime = 5000;
    private Timer autoAnswertimer = null;
    private RemoteCallbackList<GocCallbackHfp> callbacks;
    private Object callbacksLock = new Object();
    ArrayList<GocHfpClientCall> calllist = new ArrayList<>();
    private int connectIndexTemp = 0;
    public boolean currentMute = false;
    private String currentNumber = BuildConfig.FLAVOR;
    public DeviceInfo currentTalkingDevice = null;
    private String currentTalkingNumber = BuildConfig.FLAVOR;
    private Handler handler = new Handler() {
        /* class com.goodocom.gocsdkserver.CommandHfpImp.AnonymousClass1 */

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                Log.d(CommandHfpImp.TAG, "stopTimerTalking --------------end");
                CommandHfpImp.this.stopTimerTalking();
            } else if (msg.what == 2) {
                int index = msg.arg1;
                String number = (String) msg.obj;
                Log.d(CommandHfpImp.TAG, "revice handler index : " + index + " , number : " + number);
                GocHfpClientCall call = new GocHfpClientCall(new BluetoothDevice(CommandHfpImp.this.service.bluetooth.rspAddr), index, 5, number, index == 2, CommandHfpImp.this.outgoing);
                synchronized (CommandHfpImp.this.callbacksLock) {
                    int n = CommandHfpImp.this.callbacks.beginBroadcast();
                    for (int i = 0; i < n; i++) {
                        try {
                            ((GocCallbackHfp) CommandHfpImp.this.callbacks.getBroadcastItem(i)).onHfpCallChanged(CommandHfpImp.this.service.bluetooth.rspAddr, call);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    CommandHfpImp.this.callbacks.finishBroadcast();
                }
            } else if (msg.what == 3) {
                String number2 = (String) msg.obj;
                int index2 = msg.arg1;
                if (index2 <= 0) {
                    index2 = 2;
                }
                boolean isMultiParty = index2 == 2;
                Log.d(CommandHfpImp.TAG, "revice hangup handler index : " + index2 + " , number : " + number2 + " , multiParty : " + isMultiParty + " , " + CommandHfpImp.this.outgoing);
                GocHfpClientCall call2 = new GocHfpClientCall(new BluetoothDevice(CommandHfpImp.this.service.bluetooth.rspAddr), index2, 7, number2, isMultiParty, CommandHfpImp.this.outgoing);
                synchronized (CommandHfpImp.this.callbacksLock) {
                    int n2 = CommandHfpImp.this.callbacks.beginBroadcast();
                    for (int i2 = 0; i2 < n2; i2++) {
                        try {
                            ((GocCallbackHfp) CommandHfpImp.this.callbacks.getBroadcastItem(i2)).onHfpCallChanged(CommandHfpImp.this.service.bluetooth.rspAddr, call2);
                        } catch (RemoteException e2) {
                            e2.printStackTrace();
                        }
                    }
                    CommandHfpImp.this.callbacks.finishBroadcast();
                }
            } else if (4 == msg.what) {
                CommandHfpImp.this.service.pbap.downloadAllCallLog();
            } else if (5 == msg.what) {
                Log.e(CommandHfpImp.TAG, "pbap.calllogDownopt: " + CommandHfpImp.this.service.pbap.calllogDownopt);
                String currentAddress = (String) msg.obj;
                int count = msg.arg1;
                Log.e(CommandHfpImp.TAG, "service.pbap.phonebookDowning: " + CommandHfpImp.this.service.pbap.phonebookDowning + "   count : " + count);
                if (CommandHfpImp.this.service.pbap.phonebookDowning) {
                    Message message = obtainMessage();
                    message.what = 5;
                    message.obj = currentAddress;
                    message.arg1 = 1;
                    CommandHfpImp.this.handler.sendMessageDelayed(message, 2000);
                    return;
                }
                CommandHfpImp.this.syncCalllogByHangup(currentAddress, count);
            }
        }
    };
    private boolean hangupCurrentAcceptWait = false;
    public boolean isAnswerType = true;
    public boolean isApplyHfpAudioFocus = false;
    private boolean isAutoAnswer = false;
    private boolean isBtmain = false;
    public boolean isMicFocus = false;
    private String mSourceBeforePhone = BuildConfig.FLAVOR;
    private String onCallName = BuildConfig.FLAVOR;
    private String onCallNumber = BuildConfig.FLAVOR;
    private long onCallTalkStartTime = 0;
    private boolean onCallTalkTimer = false;
    private String operator = BuildConfig.FLAVOR;
    private boolean outgoing = true;
    public int requestMicMode = 1;
    private boolean scoConnected = false;
    private GocsdkService service;
    private CallTimerTask talkTimeTask;
    private boolean talkingFlag = false;
    private String threePartyName = BuildConfig.FLAVOR;
    private String threePartyNumber = BuildConfig.FLAVOR;
    private long threePartyStartTime = 0;
    private boolean threePartyTimer = false;
    private Timer timer = null;

    public CommandHfpImp(GocsdkService service2) {
        this.service = service2;
        this.callbacks = new RemoteCallbackList<>();
    }

    public boolean requestHfpAudioFocus() {
        Log.i("audio", "request------------------------mApplyHfpAudioFocus-------------------------------");
        if (!this.isApplyHfpAudioFocus) {
            return false;
        }
        Log.e("audio", "mApplyHfpAudioFocus return");
        return false;
    }

    public void releaseHfpAudioFocus() {
        Log.i("audio", "release-------hfp--------releaseAudioFocus-------" + this.isApplyHfpAudioFocus);
        if (this.isApplyHfpAudioFocus) {
            this.mSourceBeforePhone = BuildConfig.FLAVOR;
        }
    }

    public void requestMicResource() {
        Log.e("audio", "----requestMicResource()isMicFocus----" + this.isMicFocus + "    ");
        if (this.isMicFocus) {
            Log.e("audio", "requestMicResource return");
        } else {
            this.isMicFocus = true;
        }
    }

    public void releaseMicResource() {
        Log.e("audio", "----releaseMicResource()+++----");
    }

    private int status2State(int status) {
        switch (status) {
            case 1:
                return 110;
            case 2:
                return 120;
            case 3:
                return 140;
            case 4:
                return 140;
            case 5:
                return 140;
            case 6:
                return 140;
            default:
                return 110;
        }
    }

    private int status2CallState(int status) {
        switch (status) {
            case 1:
                return 7;
            case 2:
                return 7;
            case 3:
                return 7;
            case 4:
                return 2;
            case 5:
                return 4;
            case 6:
                return 0;
            case 7:
                return 5;
            case 8:
                return 1;
            default:
                return 7;
        }
    }

    private DeviceInfo getHfConnectDev(boolean isMain) {
        DeviceInfo _devinfo = null;
        if (!isMain) {
            List<String> addrList = this.service.bluetooth.getProfileConnectedAddrByProile("HFP");
            if (addrList != null) {
                int i = 0;
                while (true) {
                    if (i >= addrList.size()) {
                        break;
                    }
                    String addr = addrList.get(i);
                    if (!addr.equals(this.service.bluetooth.cmdMainAddr)) {
                        _devinfo = this.service.bluetooth.getDeviceByAddr(addr);
                        if (_devinfo == null) {
                            Log.e(TAG, "getconnectdev " + isMain + " dev is null");
                            return null;
                        }
                    } else {
                        i++;
                    }
                }
            } else {
                return null;
            }
        } else {
            _devinfo = this.service.bluetooth.getDeviceByAddr(this.service.bluetooth.cmdMainAddr);
            if (_devinfo == null) {
                Log.e(TAG, "getHfConnectDev " + isMain + " dev is null");
                return null;
            }
        }
        return _devinfo;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
    public boolean isHfpServiceReady() throws RemoteException {
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
    public boolean registerHfpCallback(GocCallbackHfp cb) throws RemoteException {
        Log.d(TAG, "registerBtCallback:");
        cb.onHfpServiceReady();
        List<String> addrList = this.service.bluetooth.getProfileConnectedAddrByProile("HFP");
        for (int i = 0; i < addrList.size(); i++) {
            String addr = addrList.get(i);
            DeviceInfo _dev = this.service.bluetooth.getDeviceByAddr(addr);
            if (_dev == null) {
                Log.d(TAG, "registerBtCallback dev is null");
            } else {
                if (i == 0 && this.service.bluetooth.cmdMainAddr == null) {
                    this.service.bluetooth.setBtMainDevices(addr);
                    Log.d(TAG, "registerBtCallback set cmdMainAddr:" + this.service.bluetooth.cmdMainAddr);
                }
                int state = _dev.hf_state;
                cb.onHfpStateChanged(addr, 110, status2State(state));
                Log.d(TAG, "registerBtCallback find dev:" + addr + " state:" + state);
                if (_dev.isScoConnected) {
                    cb.onHfpAudioStateChanged(addr, 110, 140);
                }
                if (state > 3) {
                    cb.onHfpCallChanged(addr, new GocHfpClientCall(new BluetoothDevice(this.service.bluetooth.rspAddr), 1, status2CallState(state), BuildConfig.FLAVOR, false, true));
                }
            }
        }
        return this.callbacks.register(cb);
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
    public boolean unregisterHfpCallback(GocCallbackHfp cb) throws RemoteException {
        return this.callbacks.unregister(cb);
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
    public int getHfpConnectionState() throws RemoteException {
        DeviceInfo _dev = this.service.bluetooth.getDeviceByAddr(this.service.bluetooth.cmdMainAddr);
        if (_dev == null) {
            Log.d(TAG, "getHfpConnectionState dev is null");
            return 110;
        }
        Log.e(TAG, "getHfpConnectionState:" + _dev.hf_state);
        return status2State(_dev.hf_state);
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
    public boolean isHfpConnected(String address) throws RemoteException {
        Log.e("isconnect", ">>>>>>>address " + address);
        if (TextUtils.isEmpty(address)) {
            DeviceInfo[] infos = this.service.bluetooth.loopFindConnectDevices();
            if (infos[0] != null && infos[1] != null) {
                Log.e("isconnect", ">>>>>>>isHfpConnected ");
                if (this.service.bluetooth.rspAddr.equals(infos[1].addr)) {
                    return infos[1].is_connected_by_profile(1);
                }
                return infos[0].is_connected_by_profile(1);
            } else if (infos[0] == null || infos[1] != null) {
                Log.e(TAG, ">>>>>>>else " + address);
            } else {
                boolean connect = infos[0].is_connected_by_profile(1);
                Log.e(TAG, ">>>>>>> " + connect);
                return connect;
            }
        }
        DeviceInfo _dev = this.service.bluetooth.getDeviceByAddr(address);
        if (_dev == null) {
            Log.d(TAG, "isHfpConnected dev is null");
            return false;
        }
        Log.e(TAG, "address:" + address + " isHfpConnected:" + _dev.hf_state);
        if (_dev.hf_state > 2) {
            return true;
        }
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
    public String getHfpConnectedAddress() throws RemoteException {
        List<String> addrList = this.service.bluetooth.getProfileConnectedAddrByProile("HFP");
        if (addrList == null || addrList.size() == 0) {
            return NfDef.DEFAULT_ADDRESS;
        }
        String addr = addrList.get(0);
        Log.e(TAG, "getHfpConnectedAddress:" + addr);
        return addr;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
    public int getHfpAudioConnectionState() throws RemoteException {
        List<String> addrList = this.service.bluetooth.getProfileConnectedAddrByProile("HFP");
        for (int i = 0; i < addrList.size(); i++) {
            DeviceInfo _dev = this.service.bluetooth.getDeviceByAddr(addrList.get(i));
            if (_dev == null) {
                Log.d(TAG, "getHfpAudioConnectionState dev is null");
            } else if (_dev.isScoConnected) {
                Log.d(TAG, "getHfpAudioConnectionState " + _dev.addr);
                return 140;
            }
        }
        Log.d(TAG, "getHfpAudioConnectionState : 110");
        return 110;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
    public boolean reqHfpConnect(String address) throws RemoteException {
        Log.e(TAG, "reqHfpConnect");
        this.service.getCommand().connectDevice(address);
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
    public boolean reqHfpDisconnect(String address) throws RemoteException {
        Log.e(TAG, "reqHfpDisconnect");
        this.service.getCommand().disconnectHFP(address);
        return true;
    }

    public void currentHfpRemoteSignalStrength(int val) {
        Log.e(TAG, "--currentHfpRemoteSignalStrength:" + val);
        this.service.bluetooth.updateDeviceState(this.service.bluetooth.rspAddr, "SIGNAL", val);
        if (this.service.bluetooth.getDeviceByAddr(this.service.bluetooth.rspAddr) != null) {
        }
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
    public int getHfpRemoteSignalStrength() throws RemoteException {
        int _currentSignal = this.service.bluetooth.getDeviceState(this.service.bluetooth.cmdMainAddr, "SIGNAL");
        Log.e(TAG, "getHfpRemoteSignalStrength:" + _currentSignal);
        return _currentSignal;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
    public List<GocHfpClientCall> getHfpCallList() throws RemoteException {
        DeviceInfo[] deviceInfos = this.service.bluetooth.loopFindConnectDevices();
        if (deviceInfos[0] == null || deviceInfos[1] == null) {
            if (deviceInfos[0] == null || deviceInfos[1] != null || deviceInfos[0].calllist.isEmpty()) {
                return null;
            }
            this.isBtmain = true;
            return deviceInfos[0].calllist;
        } else if (deviceInfos[0].calllist.isEmpty() || deviceInfos[1].calllist.isEmpty()) {
            if (!deviceInfos[0].calllist.isEmpty()) {
                this.isBtmain = true;
                return deviceInfos[0].calllist;
            } else if (deviceInfos[1].calllist.isEmpty()) {
                return null;
            } else {
                this.isBtmain = false;
                return deviceInfos[1].calllist;
            }
        } else if (this.isBtmain) {
            return deviceInfos[0].calllist;
        } else {
            return deviceInfos[1].calllist;
        }
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
    public List<GocHfpClientCall> getHfpCallList2() throws RemoteException {
        DeviceInfo[] deviceInfos = this.service.bluetooth.loopFindConnectDevices();
        if (deviceInfos[0] == null || deviceInfos[1] == null || deviceInfos[1].calllist.isEmpty() || deviceInfos[1].calllist.isEmpty()) {
            return null;
        }
        if (this.isBtmain) {
            return deviceInfos[1].calllist;
        }
        return deviceInfos[0].calllist;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
    public boolean isHfpRemoteOnRoaming() throws RemoteException {
        Log.e(TAG, "isHfpRemoteOnRoaming");
        return false;
    }

    public void currentHfpRemoteBatteryIndicator(int val) {
        Log.e(TAG, "currentHfpRemoteBatteryIndicator:" + val);
        this.service.bluetooth.updateDeviceState(this.service.bluetooth.rspAddr, "BATTERY", val);
        if (this.service.bluetooth.getDeviceByAddr(this.service.bluetooth.rspAddr) != null) {
        }
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
    public int getHfpRemoteBatteryIndicator() throws RemoteException {
        int battery = this.service.bluetooth.getDeviceState(this.service.bluetooth.cmdMainAddr, "BATTERY");
        Log.e(TAG, "--getHfpRemoteBatteryIndicator:" + battery);
        return battery;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
    public boolean isHfpRemoteTelecomServiceOn() throws RemoteException {
        Log.e(TAG, "isHfpRemoteTelecomServiceOn");
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
    public boolean isHfpRemoteVoiceDialOn() throws RemoteException {
        Log.e(TAG, "isHfpRemoteVoiceDialOn");
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
    public boolean reqHfpDialCall(String number) throws RemoteException {
        Log.d(TAG, "reqHfpDialCall " + number);
        this.service.getCommand().setBtMainDevice(this.service.bluetooth.cmdMainAddr);
        this.service.getCommand().phoneDail(number);
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
    public boolean reqHfpReDial() throws RemoteException {
        this.service.getCommand().phoneReDial();
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
    public boolean reqHfpMemoryDial(String index) throws RemoteException {
        Log.e(TAG, "reqHfpMemoryDial");
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
    public boolean reqHfpAnswerCall(int flag) throws RemoteException {
        Log.e(TAG, "reqHfpAnswerCall:" + flag + " , " + this.isAnswerType);
        this.service.getCommand().phoneAnswer();
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
    public boolean reqHfpRejectIncomingCall(String address) throws RemoteException {
        Log.e(TAG, "reqHfpRejectIncomingCall:" + address + " , currentDevice : " + this.service.currentCallAddress);
        if (address == null) {
            address = this.service.currentCallAddress;
        }
        this.service.getCommand().phoneRejecthold(address);
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
    public boolean reqHfpTerminateCurrentCall(String address) throws RemoteException {
        Log.e(TAG, "reqHfpTerminateCurrentCall:" + address);
        this.service.getCommand().phoneHangUp(address);
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
    public boolean reqHfpSendDtmf(String address, String number) throws RemoteException {
        this.service.getCommand().phoneTransmitDTMFCode(address, number.charAt(0));
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
    public boolean reqHfpAudioTransferToCarkit(String address) throws RemoteException {
        Log.e(TAG, "reqHfpAudioTransferToCarkit");
        this.service.getCommand().phoneTransfer(address);
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
    public boolean reqHfpAudioTransferToPhone(String address) throws RemoteException {
        Log.e(TAG, "reqHfpAudioTransferToPhone:" + address);
        this.service.getCommand().phoneTransferBack(address);
        return true;
    }

    public void currentHfpRemoteOperatorStrength(String val) {
        Log.e(TAG, "--currentHfpRemoteOperatorStrength:" + val);
        DeviceInfo deviceInfo = this.service.bluetooth.getDeviceByAddr(this.service.bluetooth.rspAddr);
        if (deviceInfo != null) {
            deviceInfo.operator = val;
        }
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
    public String getHfpRemoteNetworkOperator() throws RemoteException {
        Log.e(TAG, "getHfpRemoteNetworkOperator : " + this.operator);
        DeviceInfo deviceInfo = this.service.bluetooth.getDeviceByAddr(this.service.bluetooth.rspAddr);
        if (deviceInfo == null) {
            return BuildConfig.FLAVOR;
        }
        return deviceInfo.operator;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
    public String getHfpRemoteSubscriberNumber() throws RemoteException {
        DeviceInfo _devinfo = this.service.bluetooth.getDeviceByAddr(this.service.bluetooth.cmdMainAddr);
        if (_devinfo == null) {
            Log.e(TAG, "getHfpRemoteSubscriberNumber dev is null");
            return null;
        }
        Log.e(TAG, "getHfpRemoteSubscriberNumber:" + _devinfo.phoneSimNumber);
        return _devinfo.phoneSimNumber;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
    public boolean reqHfpVoiceDial(boolean enable) throws RemoteException {
        Log.e(TAG, "reqHfpVoiceDial");
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
    public void pauseHfpRender() throws RemoteException {
        Log.e(TAG, "gpauseHfpRender");
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
    public void startHfpRender() throws RemoteException {
        Log.e(TAG, "startHfpRender");
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
    public boolean isHfpMicMute() throws RemoteException {
        Log.e(TAG, "isHfpMicMute:" + this.currentMute);
        this.service.bluetooth.getDeviceByAddr(this.service.bluetooth.rspAddr);
        return this.currentMute;
    }

    public void onInBandRingtoneSupport(int _support) {
        Log.e(TAG, "onInBandRingtoneSupport:" + _support);
        DeviceInfo _devinfo = this.service.bluetooth.getDeviceByAddr(this.service.bluetooth.rspAddr);
        if (_devinfo == null) {
            Log.e(TAG, "onInBandRingtoneSupport dev is null");
            return;
        }
        if (_support == 1) {
            _devinfo.RingBandSupport = true;
        } else {
            _devinfo.RingBandSupport = false;
        }
        this.service.bluetooth.updateDeviceInfo(this.service.bluetooth.rspAddr, _devinfo);
    }

    public void onPhoneSIMNum(String number) {
        Log.e(TAG, "onPhoneSIMNum:" + number);
        DeviceInfo _devinfo = this.service.bluetooth.getDeviceByAddr(this.service.bluetooth.rspAddr);
        if (_devinfo == null) {
            Log.e(TAG, "onPhoneSIMNum dev is null");
            return;
        }
        _devinfo.phoneSimNumber = number;
        this.service.bluetooth.updateDeviceInfo(this.service.bluetooth.rspAddr, _devinfo);
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
    public void muteHfpMic(boolean mute) throws RemoteException {
        Log.e(TAG, "muteHfpMic:" + mute);
        this.currentMute = mute;
        if (mute) {
            this.service.getCommand().muteOpenAndClose(1);
        } else {
            this.service.getCommand().muteOpenAndClose(0);
        }
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
    public boolean getBtAutoAnswerState() {
        Log.d(TAG, "getBtAutoAnswerState : " + this.isAutoAnswer);
        return this.isAutoAnswer;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
    public void setBtAutoAnswerEnable(boolean isBtAutoAnswerEnable, int time) {
        Log.d(TAG, "setBtAutoAnswerEnable isBtAutoAnswerEnable:" + isBtAutoAnswerEnable + " time:" + time);
        this.isAutoAnswer = isBtAutoAnswerEnable;
        if (this.isAutoAnswer) {
            this.autoAnswerTime = (long) time;
            this.service.getCommand().setAutoAnswer(time / 1000);
            return;
        }
        this.service.getCommand().cancelAutoAnswer();
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
    public boolean getBtAnswerType() {
        Log.d(TAG, "getBtAnswerType : " + this.isAnswerType);
        return this.isAnswerType;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
    public void setBtAnswerTypeEnable(boolean isBtAnswerTypeEnable) {
        Log.d(TAG, "setBtAnswerTypeEnable : " + isBtAnswerTypeEnable);
        this.isAnswerType = isBtAnswerTypeEnable;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
    public boolean getThreePartyCallEnable() throws RemoteException {
        Log.d(TAG, "getThreePartyCallEnable");
        return this.service.bluetooth.getDualCallEnable();
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
    public void setThreePartyCallEnable(boolean enable) throws RemoteException {
        Log.d(TAG, "setThreePartyCallEnable : " + enable);
        this.service.bluetooth.setDualCallEnable(enable);
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
    public boolean isHfpInBandRingtoneSupport() throws RemoteException {
        Log.e(TAG, "isHfpInBandRingtoneSupport:");
        DeviceInfo _devinfo = this.service.bluetooth.getDeviceByAddr(this.service.bluetooth.cmdMainAddr);
        if (_devinfo != null) {
            return _devinfo.RingBandSupport;
        }
        Log.e(TAG, "isHfpInBandRingtoneSupport dev is null");
        return false;
    }

    public void onHfpConnecting(String address) {
        DeviceInfo _devinfo = this.service.bluetooth.getDeviceByAddr(address);
        if (_devinfo == null) {
            Log.e(TAG, "onHfpConnecting dev is null");
            return;
        }
        Log.e(TAG, "onHfpConnecting:" + _devinfo.hf_state);
        if (_devinfo.hf_state < 2) {
            int pre_currentStatus = _devinfo.hf_state;
            _devinfo.hf_state = 2;
            this.service.bluetooth.updateDeviceInfo(address, _devinfo);
            synchronized (this.callbacksLock) {
                int n = this.callbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        this.callbacks.getBroadcastItem(i).onHfpStateChanged(address, status2State(pre_currentStatus), 120);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                this.callbacks.finishBroadcast();
            }
        }
    }

    public void onHfpConnected(String address) {
        this.service.adayoAutoConnect.setAutoConnectIndexZero();
        DeviceInfo _devinfo = this.service.bluetooth.getDeviceByAddr(this.service.bluetooth.rspAddr);
        if (_devinfo == null) {
            Log.e(TAG, "onHfpConnected dev is null");
            this.service.getCommand().disconnectHFP(address);
            return;
        }
        DeviceInfo[] deviceInfos = this.service.bluetooth.loopFindConnectDevices();
        if (deviceInfos[0] == null || deviceInfos[1] == null || deviceInfos[0] == _devinfo || deviceInfos[1] == _devinfo) {
            this.connectIndexTemp++;
            _devinfo.connectIndex = this.connectIndexTemp;
            Log.e(TAG, "onHfpConnected:" + _devinfo.hf_state + ",  connectIndex : " + _devinfo.connectIndex);
            this.service.bluetooth.setDeviceListProfileConnected(this.service.bluetooth.rspAddr, "HFP", true);
            if (this.service.bluetooth.cmdMainAddr == null) {
                this.service.bluetooth.setBtMainDevices(address);
                Log.d(TAG, "IND_HFP_CONNECTED set cmdMainAddr:" + this.service.bluetooth.cmdMainAddr);
            } else if (this.service.bluetooth.cmdMainAddr.equals(address)) {
                Message message = this.handler.obtainMessage();
                message.obj = this.service.bluetooth.cmdMainAddr;
                message.what = 4;
                this.handler.sendMessageDelayed(message, 2500);
            }
            _devinfo.sort = System.currentTimeMillis();
            if (_devinfo.hf_state < 3) {
                _devinfo.phoneSimNumber = BuildConfig.FLAVOR;
                _devinfo.hf_state = 3;
                this.service.bluetooth.updateDeviceInfo(this.service.bluetooth.rspAddr, _devinfo);
                synchronized (this.callbacksLock) {
                    int n = this.callbacks.beginBroadcast();
                    for (int i = 0; i < n; i++) {
                        try {
                            this.callbacks.getBroadcastItem(i).onHfpStateChanged(address, 120, 140);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    this.callbacks.finishBroadcast();
                }
                this.service.bluetooth.sendConnectA2dpMessage(address);
                return;
            }
            return;
        }
        Log.d(TAG, "not allow three device connect hfp");
        this.service.getCommand().disconnectHFP(address);
    }

    public void saveLocalClearData(String address) {
        Log.e(TAG, "downContactSaveLocal: " + this.service.pbap.downContactSaveLocal + "   service.pbap.downCalllogSaveLocal " + this.service.pbap.downCalllogSaveLocal + "   address:  " + address);
        CommandPbapImp commandPbapImp = this.service.pbap;
        if (CommandPbapImp.mdb == null) {
            CommandPbapImp commandPbapImp2 = this.service.pbap;
            CommandPbapImp.mdb = GocPbapHelper.getInstance().getWritableDatabase();
        }
        if (!this.service.pbap.downContactSaveLocal) {
            this.service.pbap.deleteContactsByAddress(address);
        }
        if (!this.service.pbap.downCalllogSaveLocal) {
            GocPbapHelper instance = GocPbapHelper.getInstance();
            CommandPbapImp commandPbapImp3 = this.service.pbap;
            instance.deleteAllCallHistoryInfo(CommandPbapImp.mdb, address);
        }
    }

    public void onHfpClearConnectedDeviceInfo(String address) {
        synchronized (this.callbacksLock) {
            int n = this.callbacks.beginBroadcast();
            for (int i = 0; i < n; i++) {
                try {
                    this.callbacks.getBroadcastItem(i).onHfpStateChanged(address, 140, 110);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.callbacks.finishBroadcast();
        }
    }

    public void onHfpDisconnected(String address) {
        this.currentNumber = BuildConfig.FLAVOR;
        DeviceInfo _devinfo = this.service.bluetooth.getDeviceByAddr(address);
        if (_devinfo == null) {
            Log.e(TAG, "onHfpDisconnected dev is null:" + address + " main:" + this.service.bluetooth.cmdMainAddr);
            if (this.service.bluetooth.cmdMainAddr != null && this.service.bluetooth.rspAddr.equals(this.service.bluetooth.cmdMainAddr)) {
                this.service.bluetooth.setBtMainDevices(null);
                Log.d(TAG, "----->onHfpDisconnected set cmdMainAddr addr:" + this.service.bluetooth.cmdMainAddr);
                this.service.bluetooth.loopFindSetMainDev();
                return;
            }
            return;
        }
        Log.e(TAG, "onHfpDisconnected : " + address + ",  " + _devinfo.hf_state);
        _devinfo.phoneSimNumber = BuildConfig.FLAVOR;
        Log.e(TAG, "onHfpDisconnected:" + _devinfo.hf_state + " btopen:" + this.service.bluetooth.btenalbe_state);
        this.service.bluetooth.setDeviceListProfileConnected(address, "HFP", false);
        if (_devinfo.hf_state >= 2) {
            int pr_currentStatus = _devinfo.hf_state;
            _devinfo.hf_state = 1;
            _devinfo.sort = 0;
            this.service.bluetooth.updateDeviceInfo(address, _devinfo);
            DeviceInfo[] infos = this.service.bluetooth.loopFindConnectDevices();
            if (infos[0] != null && infos[0].addr.equals(address)) {
                this.service.bluetooth.setBtMainDevices(null);
                this.service.bluetooth.loopFindSetMainDev();
            }
            removeCallListByAddr(address);
            synchronized (this.callbacksLock) {
                int n = this.callbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        this.callbacks.getBroadcastItem(i).onHfpStateChanged(address, status2State(pr_currentStatus), 110);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                this.callbacks.finishBroadcast();
            }
        }
        this.service.getCommand().getPairList();
        if (!TextUtils.isEmpty(address)) {
            saveLocalClearData(address);
        }
    }

    public void addCallList(String number, int index, int status) {
        DeviceInfo _devinfo = this.service.bluetooth.getDeviceByAddr(this.service.bluetooth.rspAddr);
        if (_devinfo == null) {
            Log.e(TAG, "addCallList dev is null");
            return;
        }
        int listcount = _devinfo.calllist.size();
        Log.e(TAG, "addCallList index:" + index + " number:" + number + " status:" + status2CallState(status) + " , listcount : " + listcount);
        for (int i = 0; i < listcount; i++) {
            if (index == _devinfo.calllist.get(i).getId()) {
                Log.e(TAG, "update call list");
                _devinfo.calllist.get(i).setNumber(number);
                _devinfo.calllist.get(i).setState(status2CallState(status));
                return;
            }
        }
        if (_devinfo.hf_state > 3) {
            GocHfpClientCall call = new GocHfpClientCall(new BluetoothDevice(this.service.bluetooth.rspAddr), index, status2CallState(status), number, index == 2, this.outgoing);
            _devinfo.calllist.add(call);
            Log.d(TAG, "addr : " + this.service.bluetooth.rspAddr + " , " + call + " , " + call.getDevice());
            this.service.bluetooth.updateDeviceInfo(this.service.bluetooth.rspAddr, _devinfo);
        }
    }

    public void removeCallList(String number, int index, int status) {
        DeviceInfo _devinfo = this.service.bluetooth.getDeviceByAddr(this.service.bluetooth.rspAddr);
        if (_devinfo == null) {
            Log.e(TAG, "addCallList dev is null");
            return;
        }
        int listcount = _devinfo.calllist.size();
        Log.e(TAG, "removeCallList index:" + index + " number:" + number + " status:" + status2CallState(status));
        for (int i = 0; i < listcount; i++) {
            Log.d(TAG, "removeCallList>>>>1 i : " + i + " , ID : " + _devinfo.calllist.get(i).getId());
            if (index == _devinfo.calllist.get(i).getId()) {
                Log.d(TAG, "removeCallList-----2 i : " + i + " , ID : " + _devinfo.calllist.get(i).getId());
                _devinfo.calllist.remove(i);
                this.service.bluetooth.updateDeviceInfo(this.service.bluetooth.rspAddr, _devinfo);
                return;
            }
        }
    }

    public void removeCallListByAddr(String addr) {
        DeviceInfo _devinfo = this.service.bluetooth.getDeviceByAddr(addr);
        if (_devinfo == null) {
            Log.e(TAG, "addCallList dev is null:" + addr);
            return;
        }
        int listcount = _devinfo.calllist.size();
        Log.e(TAG, "removeCallListByAddr listcount:" + listcount);
        if (listcount > 0) {
            _devinfo.calllist.clear();
            this.service.bluetooth.updateDeviceInfo(addr, _devinfo);
        }
    }

    public void onCallSucceedByIndex(String number, int index) {
        Log.e(TAG, "onCallSucceedByIndex:" + number + " Index:" + index);
        activeCallMcu(GocApplication.INSTANCE);
        if (number.length() > 0) {
            this.currentNumber = number;
        }
        addCallList(number, index, 4);
        BluetoothDevice bluetoothDevice = new BluetoothDevice(this.service.bluetooth.rspAddr);
        Log.d(TAG, "onCallSucceedByIndex " + bluetoothDevice.getAddress());
        GocHfpClientCall call = new GocHfpClientCall(bluetoothDevice, index, 2, number, index == 2, true);
        synchronized (this.callbacksLock) {
            int n = this.callbacks.beginBroadcast();
            for (int i = 0; i < n; i++) {
                try {
                    this.callbacks.getBroadcastItem(i).onHfpCallChanged(this.service.bluetooth.rspAddr, call);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.callbacks.finishBroadcast();
        }
    }

    public void onCallSucceed(String number) {
        Log.e(TAG, "onCallSucceed:" + number);
        activeCallMcu(GocApplication.INSTANCE);
        if (number.length() > 0) {
            this.currentNumber = number;
            addCallList(number, 1, 4);
        }
        GocHfpClientCall call = new GocHfpClientCall(new BluetoothDevice(this.service.bluetooth.rspAddr), 1, 2, this.currentNumber, false, true);
        synchronized (this.callbacksLock) {
            int n = this.callbacks.beginBroadcast();
            for (int i = 0; i < n; i++) {
                try {
                    this.callbacks.getBroadcastItem(i).onHfpCallChanged(this.service.bluetooth.rspAddr, call);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.callbacks.finishBroadcast();
        }
    }

    public void onSecondIncoming(String number) {
        Log.e(TAG, "onSecondIncoming:" + number);
        if (number.length() > 0) {
            addCallList(number, 2, 7);
            GocHfpClientCall call = new GocHfpClientCall(new BluetoothDevice(this.service.bluetooth.rspAddr), 2, 5, number, false, false);
            synchronized (this.callbacksLock) {
                int n = this.callbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        this.callbacks.getBroadcastItem(i).onHfpCallChanged(this.service.bluetooth.rspAddr, call);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                this.callbacks.finishBroadcast();
            }
        }
    }

    public void onIncomingByIndex(String number, int index) {
        activeCallMcu(GocApplication.INSTANCE);
        Log.e(TAG, "onIncomingByIndex  Index:" + index);
        if (number.length() > 0) {
            this.currentNumber = number;
        }
        addCallList(number, index, 5);
        GocHfpClientCall call = new GocHfpClientCall(new BluetoothDevice(this.service.bluetooth.rspAddr), index, 4, this.currentNumber, index == 2, false);
        synchronized (this.callbacksLock) {
            int n = this.callbacks.beginBroadcast();
            for (int i = 0; i < n; i++) {
                try {
                    this.callbacks.getBroadcastItem(i).onHfpCallChanged(this.service.bluetooth.rspAddr, call);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.callbacks.finishBroadcast();
        }
    }

    public void onIncoming(String number) {
        Log.e(TAG, "onIncoming:" + number + ",  address : " + this.service.bluetooth.rspAddr);
        if (number.length() > 0) {
            this.currentNumber = number;
        }
        addCallList(number, 1, 5);
        GocHfpClientCall call = new GocHfpClientCall(new BluetoothDevice(this.service.bluetooth.rspAddr), 1, 4, this.currentNumber, false, false);
        synchronized (this.callbacksLock) {
            int n = this.callbacks.beginBroadcast();
            for (int i = 0; i < n; i++) {
                try {
                    this.callbacks.getBroadcastItem(i).onHfpCallChanged(this.service.bluetooth.rspAddr, call);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.callbacks.finishBroadcast();
        }
    }

    public void sendCurrentWaitingByIndexMessage(String number, int index) {
        Log.d(TAG, "sendCurrentWaitingByIndexMessage");
        Message msg = new Message();
        msg.what = 2;
        msg.obj = number;
        msg.arg1 = index;
        this.handler.sendMessageDelayed(msg, 300);
    }

    public void onCurrentWaitingByIndex(String number, int index) {
        Log.e(TAG, "onCurrentHoldByIndex:" + number + " Index:" + index);
        this.outgoing = false;
        DeviceInfo _devinfo = this.service.bluetooth.getDeviceByAddr(this.service.bluetooth.rspAddr);
        if (_devinfo == null) {
            Log.e(TAG, "onCurrentWaitingByIndex dev is null");
        } else if (_devinfo.hf_state > 3) {
            addCallList(number, index, 7);
            sendCurrentWaitingByIndexMessage(number, index);
        }
    }

    public void onCurrentHoldByIndex(String number, int index) {
        DeviceInfo _devinfo = this.service.bluetooth.getDeviceByAddr(this.service.bluetooth.rspAddr);
        if (_devinfo == null) {
            Log.e(TAG, "onCurrentHoldByIndex dev is null");
            return;
        }
        Log.e(TAG, "onCurrentHoldByIndex:" + _devinfo.hf_state + " Index:" + index);
        if (_devinfo.hf_state > 3) {
            addCallList(number, index, 8);
            GocHfpClientCall call = new GocHfpClientCall(new BluetoothDevice(this.service.bluetooth.rspAddr), index, 1, number, index == 2, this.outgoing);
            synchronized (this.callbacksLock) {
                int n = this.callbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        this.callbacks.getBroadcastItem(i).onHfpCallChanged(this.service.bluetooth.rspAddr, call);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                this.callbacks.finishBroadcast();
            }
        }
    }

    public void onCurrentHold(String number) {
        DeviceInfo _devinfo = this.service.bluetooth.getDeviceByAddr(this.service.bluetooth.rspAddr);
        if (_devinfo == null) {
            Log.e(TAG, "onCurrentHold dev is null");
            return;
        }
        Log.e(TAG, "onCurrentHold:" + _devinfo.hf_state);
        if (_devinfo.hf_state > 3) {
            addCallList(number, 2, 8);
            GocHfpClientCall call = new GocHfpClientCall(new BluetoothDevice(this.service.bluetooth.rspAddr), 1, 1, number, false, this.outgoing);
            synchronized (this.callbacksLock) {
                int n = this.callbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        this.callbacks.getBroadcastItem(i).onHfpCallChanged(this.service.bluetooth.rspAddr, call);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                this.callbacks.finishBroadcast();
            }
        }
    }

    public void onCurrentHangUp(String number) {
        DeviceInfo _devinfo = this.service.bluetooth.getDeviceByAddr(this.service.bluetooth.rspAddr);
        if (_devinfo == null) {
            Log.e(TAG, "onCurrentHangUp dev is null");
            return;
        }
        Log.e(TAG, "onCurrentHangUp:" + _devinfo.hf_state);
        if (_devinfo.hf_state > 3) {
            removeCallListByAddr(this.service.bluetooth.rspAddr);
            GocHfpClientCall call = new GocHfpClientCall(new BluetoothDevice(this.service.bluetooth.rspAddr), 1, 7, number, false, this.outgoing);
            synchronized (this.callbacksLock) {
                int n = this.callbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        this.callbacks.getBroadcastItem(i).onHfpCallChanged(this.service.bluetooth.rspAddr, call);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                this.callbacks.finishBroadcast();
            }
        }
        if (_devinfo.calllist.size() == 0) {
            this.currentMute = false;
            this.service.getCommand().muteOpenAndClose(0);
        }
    }

    public void onSecondHangUp(String number) {
        DeviceInfo _devinfo = this.service.bluetooth.getDeviceByAddr(this.service.bluetooth.rspAddr);
        if (_devinfo == null) {
            Log.e(TAG, "onSecondHangUp dev is null");
            return;
        }
        Log.e(TAG, "onSecondHangUp:" + _devinfo.hf_state);
        if (_devinfo.hf_state > 3) {
            removeCallList(number, 2, 3);
            GocHfpClientCall call = new GocHfpClientCall(new BluetoothDevice(this.service.bluetooth.rspAddr), 2, 7, number, true, this.outgoing);
            synchronized (this.callbacksLock) {
                int n = this.callbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        this.callbacks.getBroadcastItem(i).onHfpCallChanged(this.service.bluetooth.rspAddr, call);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                this.callbacks.finishBroadcast();
            }
        }
        if (_devinfo.calllist.size() == 0) {
            this.currentMute = false;
            this.service.getCommand().muteOpenAndClose(0);
        }
        Log.e(TAG, "IND_SECOND_HANG_UP");
    }

    public void onSecondHangUpByIndex(String number, int index) {
        this.hangupCurrentAcceptWait = false;
        DeviceInfo _devinfo = this.service.bluetooth.getDeviceByAddr(this.service.bluetooth.rspAddr);
        if (_devinfo == null) {
            Log.e(TAG, "onSecondHangUpByIndex dev is null");
            return;
        }
        Log.e(TAG, "onSecondHangUpByIndex:" + _devinfo.hf_state);
        if (_devinfo.hf_state > 3) {
            if (index >= 2) {
                this.threePartyTimer = false;
                this.threePartyStartTime = 0;
                this.threePartyNumber = BuildConfig.FLAVOR;
            } else if (index == 1) {
                this.onCallTalkTimer = false;
                this.onCallTalkStartTime = 0;
                this.onCallNumber = BuildConfig.FLAVOR;
            }
            this.currentTalkingNumber = BuildConfig.FLAVOR;
            removeCallList(number, index, 3);
            GocHfpClientCall call = new GocHfpClientCall(new BluetoothDevice(this.service.bluetooth.rspAddr), index, 7, number, index >= 2, false);
            synchronized (this.callbacksLock) {
                int n = this.callbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        this.callbacks.getBroadcastItem(i).onHfpCallChanged(this.service.bluetooth.rspAddr, call);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                this.callbacks.finishBroadcast();
            }
        }
        if (_devinfo.calllist.size() == 0) {
            this.currentMute = false;
            this.service.getCommand().muteOpenAndClose(0);
        }
        this.currentNumber = BuildConfig.FLAVOR;
    }

    public void onHangUpByIndex(String number, int index) {
        Log.d(TAG, "onHangUpByIndex : " + this.calllist);
        DeviceInfo _devinfo = this.service.bluetooth.getDeviceByAddr(this.service.bluetooth.rspAddr);
        if (_devinfo == null) {
            Log.e(TAG, "onHangUpByIndex dev is null");
            return;
        }
        Log.e(TAG, "onHangUpByIndex hf_state:" + _devinfo.hf_state + " index:" + index);
        if (_devinfo.hf_state >= 3) {
            removeCallList(number, index, 3);
            _devinfo.hf_state = 3;
            this.service.bluetooth.updateDeviceInfo(this.service.bluetooth.rspAddr, _devinfo);
            GocHfpClientCall call = new GocHfpClientCall(new BluetoothDevice(this.service.bluetooth.rspAddr), index, 7, number, false, this.outgoing);
            synchronized (this.callbacksLock) {
                int n = this.callbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        this.callbacks.getBroadcastItem(i).onHfpCallChanged(this.service.bluetooth.rspAddr, call);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                this.callbacks.finishBroadcast();
            }
            stopTimerTalking();
        }
        if (_devinfo.calllist.size() == 0) {
            this.currentMute = false;
            this.service.getCommand().muteOpenAndClose(0);
        }
        this.currentNumber = BuildConfig.FLAVOR;
        terminatCallMcu();
    }

    public void sendDelayRequestCalllog(int time) {
        String currentAddress = this.service.bluetooth.cmdMainAddr;
        Log.e(TAG, "handler.hasMessages(HF_MSG_DELAY_REQUST_CALLLOG_HANGUP):: " + this.handler.hasMessages(5));
        if (this.handler.hasMessages(5)) {
            this.handler.removeMessages(5);
            Message message = this.handler.obtainMessage();
            message.what = 5;
            message.obj = currentAddress;
            message.arg1 = 2;
            this.handler.sendMessageDelayed(message, (long) time);
            return;
        }
        Message message2 = this.handler.obtainMessage();
        message2.what = 5;
        message2.obj = currentAddress;
        message2.arg1 = 1;
        this.handler.sendMessageDelayed(message2, (long) time);
    }

    public void releaseAudioInfo(boolean releaseHfpAudio) {
        Log.e("audio", ">>>releaseHfpAudioFocus>>>");
        if (releaseHfpAudio) {
            releaseHfpAudioFocus();
        }
        DeviceInfo[] DeviceInfos = this.service.bluetooth.loopFindConnectDevices();
        Log.e("audio", "DeviceInfos" + DeviceInfos[0]);
        Log.e("audio", "DeviceInfos1111 " + DeviceInfos[1]);
        if (DeviceInfos[0] != null && DeviceInfos[1] != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("!DeviceInfos[0].isScoConnected: ");
            sb.append(!DeviceInfos[0].isScoConnected);
            sb.append("    DeviceInfos[0].hf_state: ");
            sb.append(DeviceInfos[0].hf_state);
            sb.append("   !DeviceInfos[1].isScoConnected    ");
            sb.append(!DeviceInfos[1].isScoConnected);
            sb.append("  DeviceInfos[1].hf_state  : ");
            sb.append(DeviceInfos[1].hf_state);
            Log.e("audio", sb.toString());
            if (!DeviceInfos[0].isScoConnected && DeviceInfos[0].hf_state < 4 && !DeviceInfos[1].isScoConnected && DeviceInfos[1].hf_state < 4) {
                Log.e("audio", "releaseMicResource ");
                releaseMicResource();
                Log.e("mic", "   releaseMicResource2");
            }
        } else if (DeviceInfos[0] != null && DeviceInfos[1] == null) {
            Log.e("audio", " !DeviceInfos[0].isScoConnected ： " + (true ^ DeviceInfos[0].isScoConnected) + "   DeviceInfos[0].hf_state : " + DeviceInfos[0].hf_state);
            Log.e("mic", "   releaseMicResource3");
            if (!DeviceInfos[0].isScoConnected && DeviceInfos[0].hf_state < 4) {
                Log.e("audio", "releaseMicResource1 ");
                releaseMicResource();
                Log.e("mic", "   releaseMicResource4");
            }
        } else if (DeviceInfos[1] != null && DeviceInfos[0] == null) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("!DeviceInfos[1].isScoConnected :   ");
            sb2.append(!DeviceInfos[1].isScoConnected);
            sb2.append("  DeviceInfos[1].hf_state   ");
            sb2.append(DeviceInfos[1].hf_state);
            Log.e("audio", sb2.toString());
            Log.e("mic", "   releaseMicResource5");
            if (!DeviceInfos[1].isScoConnected && DeviceInfos[1].hf_state < 4) {
                Log.e("audio", "releaseMicResource2 ");
                Log.e("mic", "   releaseMicResource6");
                releaseMicResource();
            }
        }
    }

    public void onHangUp() {
        DeviceInfo _devinfo = this.service.bluetooth.getDeviceByAddr(this.service.bluetooth.rspAddr);
        if (_devinfo == null) {
            Log.e(TAG, "onHangUp dev is null");
            return;
        }
        Log.e(TAG, "onHangUp:" + _devinfo.hf_state);
        if (_devinfo.hf_state > 3) {
            removeCallListByAddr(this.service.bluetooth.rspAddr);
            _devinfo.hf_state = 3;
            _devinfo.hf_state = 3;
            this.service.bluetooth.updateDeviceInfo(this.service.bluetooth.rspAddr, _devinfo);
            GocHfpClientCall call = new GocHfpClientCall(new BluetoothDevice(this.service.bluetooth.rspAddr), this.PhoneId, 7, this.currentNumber, false, this.outgoing);
            synchronized (this.callbacksLock) {
                int n = this.callbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        this.callbacks.getBroadcastItem(i).onHfpCallChanged(this.service.bluetooth.rspAddr, call);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                this.callbacks.finishBroadcast();
            }
        }
        this.currentNumber = BuildConfig.FLAVOR;
        if (_devinfo.calllist.size() == 0) {
            this.currentMute = false;
            this.service.getCommand().muteOpenAndClose(0);
        }
    }

    public void onSecondTalking(String number) {
        if (number.length() > 0) {
            this.currentNumber = number;
        }
        this.PhoneId = 2;
        addCallList(number, 2, 6);
        GocHfpClientCall call = new GocHfpClientCall(new BluetoothDevice(this.service.bluetooth.rspAddr), this.PhoneId, 0, number, false, this.outgoing);
        synchronized (this.callbacksLock) {
            int n = this.callbacks.beginBroadcast();
            for (int i = 0; i < n; i++) {
                try {
                    this.callbacks.getBroadcastItem(i).onHfpCallChanged(this.service.bluetooth.rspAddr, call);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.callbacks.finishBroadcast();
        }
    }

    public void onTalkingByIndex(String number, int index) {
        if (this.hangupCurrentAcceptWait) {
            Log.d(TAG, "onTalkingByIndex return");
            return;
        }
        if (number.equals(BuildConfig.FLAVOR)) {
            number = "N/A";
        }
        if (!this.currentTalkingNumber.equals(number)) {
            Log.d(TAG, "startTimerTalking ------------- " + this.currentTalkingNumber + ", " + number);
            Log.d(TAG, "onCallNumber : " + this.onCallNumber + " , threePartyNumber : " + this.threePartyNumber);
            if (index != 1 || ((!this.onCallNumber.equals(BuildConfig.FLAVOR) && !this.onCallNumber.equals(number)) || this.threePartyNumber.equals(number))) {
                this.threePartyNumber = number;
            } else {
                this.onCallNumber = number;
            }
            this.currentTalkingNumber = number;
            startTimerTalking(index);
            this.talkingFlag = true;
        }
        if (number.length() > 0) {
            this.currentNumber = number;
        }
        Log.e(TAG, "onTalkingByIndex:" + number + " index:" + index + "address : " + this.service.bluetooth.rspAddr);
        addCallList(number, index, 6);
        GocHfpClientCall call = new GocHfpClientCall(new BluetoothDevice(this.service.bluetooth.rspAddr), index, 0, number, index == 2, this.outgoing);
        synchronized (this.callbacksLock) {
            int n = this.callbacks.beginBroadcast();
            for (int i = 0; i < n; i++) {
                try {
                    this.callbacks.getBroadcastItem(i).onHfpCallChanged(this.service.bluetooth.rspAddr, call);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.callbacks.finishBroadcast();
        }
    }

    public void syncCalllogByHangup(String currentAddr, int count) {
        Log.d(TAG, ".........syncCalllogByHangup  rsp : " + this.service.bluetooth.rspAddr + " , main : " + this.service.bluetooth.cmdMainAddr + "    current : " + currentAddr + "    count " + count);
        if (this.service.bluetooth.isBtMainDevices(currentAddr)) {
            this.service.getCommand().setBtMainDevice(currentAddr);
            this.service.getCommand().calllogByCallUptate(count);
        }
    }

    /* access modifiers changed from: private */
    public class CallTimerTask extends TimerTask {
        private CallTimerTask() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            long talkTime = 0;
            DeviceInfo info = CommandHfpImp.this.service.bluetooth.getDeviceByAddr(CommandHfpImp.this.service.bluetooth.rspAddr);
            if (info == null) {
                Log.d(CommandHfpImp.TAG, "call timer task info is null");
                return;
            }
            if (CommandHfpImp.this.currentTalkingNumber.equals(CommandHfpImp.this.onCallNumber)) {
                talkTime = CommandHfpImp.this.onCallTalkStartTime;
                if (CommandHfpImp.this.onCallName.equals(BuildConfig.FLAVOR)) {
                    CommandPbapImp commandPbapImp = CommandHfpImp.this.service.pbap;
                    if (CommandPbapImp.mdb == null) {
                        CommandPbapImp commandPbapImp2 = CommandHfpImp.this.service.pbap;
                        CommandPbapImp.mdb = GocPbapHelper.getInstance().getWritableDatabase();
                    }
                    CommandHfpImp commandHfpImp = CommandHfpImp.this;
                    GocPbapHelper instance = GocPbapHelper.getInstance();
                    CommandPbapImp commandPbapImp3 = CommandHfpImp.this.service.pbap;
                    commandHfpImp.onCallName = instance.queryNameByNum(CommandPbapImp.mdb, info.addr, CommandHfpImp.this.currentTalkingNumber);
                }
                String callName = CommandHfpImp.this.onCallName;
            } else if (CommandHfpImp.this.currentTalkingNumber.equals(CommandHfpImp.this.threePartyNumber)) {
                talkTime = CommandHfpImp.this.threePartyStartTime;
                if (CommandHfpImp.this.threePartyName.equals(BuildConfig.FLAVOR)) {
                    CommandPbapImp commandPbapImp4 = CommandHfpImp.this.service.pbap;
                    if (CommandPbapImp.mdb == null) {
                        CommandPbapImp commandPbapImp5 = CommandHfpImp.this.service.pbap;
                        CommandPbapImp.mdb = GocPbapHelper.getInstance().getWritableDatabase();
                    }
                    CommandHfpImp commandHfpImp2 = CommandHfpImp.this;
                    GocPbapHelper instance2 = GocPbapHelper.getInstance();
                    CommandPbapImp commandPbapImp6 = CommandHfpImp.this.service.pbap;
                    commandHfpImp2.threePartyName = instance2.queryNameByNum(CommandPbapImp.mdb, info.addr, CommandHfpImp.this.currentTalkingNumber);
                }
                String callName2 = CommandHfpImp.this.threePartyName;
            }
            Date date = new Date(talkTime);
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            format.setTimeZone(TimeZone.getTimeZone("GMT+0"));
            String time = format.format(date);
            CommandBluetoothImp commandBluetoothImp = CommandHfpImp.this.service.bluetooth;
            if (!CommandBluetoothImp.currentEnable) {
                CommandHfpImp.this.stopTimerTalking();
                return;
            }
            synchronized (CommandHfpImp.this.callbacksLock) {
                int n = CommandHfpImp.this.callbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((GocCallbackHfp) CommandHfpImp.this.callbacks.getBroadcastItem(i)).onHfpCallingTimeChanged(time);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                CommandHfpImp.this.callbacks.finishBroadcast();
                if (CommandHfpImp.this.onCallTalkTimer) {
                    CommandHfpImp.this.onCallTalkStartTime += 1000;
                    Log.d(CommandHfpImp.TAG, "---------->> onCallTalkStartTime : " + CommandHfpImp.this.onCallTalkStartTime);
                }
                if (CommandHfpImp.this.threePartyTimer) {
                    CommandHfpImp.this.threePartyStartTime += 1000;
                    Log.d(CommandHfpImp.TAG, "---------->> threePartyStartTime :" + CommandHfpImp.this.threePartyStartTime);
                }
            }
        }
    }

    private void startTimerTalking(int index) {
        if (this.timer == null) {
            Log.d(TAG, "startTimerTalking : " + this.service.bluetooth.rspAddr);
            this.timer = new Timer(true);
            this.talkTimeTask = new CallTimerTask();
            this.timer.schedule(this.talkTimeTask, 0, 1000);
        }
        Log.d(TAG, "onCallNumber : " + this.onCallNumber + " , " + this.onCallTalkStartTime + " , " + this.onCallTalkTimer);
        if (index == 1 && !this.onCallTalkTimer) {
            this.onCallTalkTimer = true;
            this.onCallTalkStartTime = 0;
        }
        Log.d(TAG, "threePartyNumber : " + this.threePartyNumber + " , threePartyStartTime : " + this.threePartyStartTime + " , " + this.threePartyTimer);
        if (index == 2 && !this.threePartyTimer) {
            this.threePartyTimer = true;
            this.threePartyStartTime = 0;
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void stopTimerTalking() {
        Log.d(TAG, "stopTimerTalking start");
        if (this.timer != null) {
            Log.d(TAG, "stopTimerTalking end");
            this.timer.cancel();
            this.timer.purge();
            this.timer = null;
            this.onCallTalkTimer = false;
            this.threePartyTimer = false;
            this.onCallTalkStartTime = 0;
            this.threePartyStartTime = 0;
            this.onCallNumber = BuildConfig.FLAVOR;
            this.threePartyNumber = BuildConfig.FLAVOR;
            this.currentTalkingNumber = BuildConfig.FLAVOR;
            this.threePartyName = BuildConfig.FLAVOR;
            this.onCallName = BuildConfig.FLAVOR;
            this.currentTalkingDevice = null;
        }
    }

    public void onTalking(String number) {
        if (number.length() > 0) {
            this.currentNumber = number;
        }
        this.PhoneId = 1;
        addCallList(number, 1, 6);
        GocHfpClientCall call = new GocHfpClientCall(new BluetoothDevice(this.service.bluetooth.rspAddr), this.PhoneId, 0, this.currentNumber, false, this.outgoing);
        synchronized (this.callbacksLock) {
            int n = this.callbacks.beginBroadcast();
            for (int i = 0; i < n; i++) {
                try {
                    this.callbacks.getBroadcastItem(i).onHfpCallChanged(this.service.bluetooth.rspAddr, call);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.callbacks.finishBroadcast();
        }
    }

    public void onRingStart() {
    }

    public void onRingStop() {
    }

    public void onHfpLocal() {
        Intent intent = new Intent();
        intent.setAction("bluetooth.action.CALL_END");
        GocApplication.INSTANCE.sendBroadcast(intent);
        DeviceInfo _devinfo = this.service.bluetooth.getDeviceByAddr(this.service.bluetooth.rspAddr);
        Log.e(TAG, "onHfpLocal_devinfo>>>> " + _devinfo);
        if (_devinfo == null) {
            Log.e(TAG, "onHfpLocal dev is null");
            return;
        }
        boolean _scoConnected = _devinfo.isScoConnected;
        Log.e(TAG, "_scoConnected>>>>>>>> " + _scoConnected);
        if (_scoConnected) {
            _devinfo.isScoConnected = false;
            this.service.bluetooth.updateDeviceInfo(this.service.bluetooth.rspAddr, _devinfo);
            synchronized (this.callbacksLock) {
                int n = this.callbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        Log.d(TAG, "onHfpLocal : " + this.service.bluetooth.rspAddr);
                        this.callbacks.getBroadcastItem(i).onHfpAudioStateChanged(this.service.bluetooth.rspAddr, 140, 110);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                this.callbacks.finishBroadcast();
            }
        }
        releaseAudioInfo(false);
    }

    public void onHfpRemote() {
        Intent intent = new Intent();
        intent.setAction("bluetooth.action.CALL");
        GocApplication.INSTANCE.sendBroadcast(intent);
        DeviceInfo _devinfo = this.service.bluetooth.getDeviceByAddr(this.service.bluetooth.rspAddr);
        Log.e(TAG, "onHfpRemote   _devinfo>>>> " + _devinfo);
        if (_devinfo == null) {
            Log.e(TAG, "onHfpRemote dev is null");
            return;
        }
        boolean _scoConnected = _devinfo.isScoConnected;
        Log.e(TAG, "_scoConnected>>>>>>>> " + _scoConnected);
        if (!_scoConnected) {
            _devinfo.isScoConnected = true;
            this.service.bluetooth.updateDeviceInfo(this.service.bluetooth.rspAddr, _devinfo);
            synchronized (this.callbacksLock) {
                int n = this.callbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        Log.d(TAG, "onHfpRemote : " + this.service.bluetooth.rspAddr);
                        this.callbacks.getBroadcastItem(i).onHfpAudioStateChanged(this.service.bluetooth.rspAddr, 110, 140);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                this.callbacks.finishBroadcast();
            }
        }
    }

    public void onVoiceConnected() {
        this.scoConnected = true;
    }

    public void onVoiceDisconnected() {
        this.scoConnected = false;
    }

    public void onAutoAccept(int autoAccept) {
        if (autoAccept > 0) {
            this.isAutoAnswer = true;
        } else {
            this.isAutoAnswer = false;
        }
        this.service.bluetooth.onAutoAnwer(autoAccept);
    }

    public void onHfpStatus(int status) {
        DeviceInfo _devinfo = this.service.bluetooth.getDeviceByAddr(this.service.bluetooth.rspAddr);
        if (_devinfo == null) {
            Log.e(TAG, "onHfpStatus dev is null");
            return;
        }
        _devinfo.hf_state = status;
        this.service.bluetooth.updateDeviceInfo(this.service.bluetooth.rspAddr, _devinfo);
        if (status == 5) {
            this.outgoing = false;
        } else if (status == 4) {
            this.outgoing = true;
        }
    }

    public void onOutGoingOrTalkingNumber(String number) {
        DeviceInfo _devinfo = this.service.bluetooth.getDeviceByAddr(this.service.bluetooth.rspAddr);
        if (_devinfo == null) {
            Log.e(TAG, "onOutGoingOrTalkingNumber dev is null");
            return;
        }
        int state = _devinfo.hf_state;
        if (!this.currentNumber.equals(number)) {
            this.currentNumber = number;
            if (state > 3) {
                Log.e(TAG, "onHfpCallChanged");
                GocHfpClientCall call = new GocHfpClientCall(new BluetoothDevice(this.service.bluetooth.rspAddr), 1, status2CallState(state), number, false, this.outgoing);
                synchronized (this.callbacksLock) {
                    int n = this.callbacks.beginBroadcast();
                    for (int i = 0; i < n; i++) {
                        try {
                            this.callbacks.getBroadcastItem(i).onHfpCallChanged(this.service.bluetooth.rspAddr, call);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    this.callbacks.finishBroadcast();
                }
            }
        }
    }

    public void hfpSwitchUi() {
    }

    private class AutoAnswerTask extends TimerTask {
        private AutoAnswerTask() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            CommandHfpImp.this.service.getCommand().phoneAnswer();
        }
    }

    public void onAnswerType(int type) {
        if (type == 1) {
            this.isAnswerType = true;
        } else {
            this.isAnswerType = false;
        }
    }

    public void onAutoAnswer(int answer) {
        if (answer > 0) {
            this.isAutoAnswer = true;
        } else {
            this.isAutoAnswer = false;
        }
    }

    public static void activeCallMcu(Context mContext) {
        try {
            mContext.sendBroadcast(new Intent("HIDE_SOFTKEYBOARD"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "进入通话通知mcu");
    }

    public static void terminatCallMcu() {
    }
}

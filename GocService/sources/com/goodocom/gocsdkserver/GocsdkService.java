package com.goodocom.gocsdkserver;

import android.content.IntentFilter;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import androidx.vectordrawable.graphics.drawable.PathInterpolatorCompat;
import com.goodocom.bttek.BuildConfig;
import com.goodocom.bttek.bt.GocApplication;
import com.goodocom.bttek.bt.bean.DeviceInfo;
import com.goodocom.bttek.bt.bean.GocMessages;
import com.goodocom.bttek.bt.res.NfDef;
import com.goodocom.gocDataBase.GocPbapHelper;
import com.goodocom.gocsdk.Commands;
import com.goodocom.gocsdk.SerialPort;
import com.goodocom.sharedata.AdayoAutoConnect;
import com.goodocom.sharedata.AdayoShareInfoImple;
import com.goodocom.sharedata.AdayoSystemManager;
import com.goodocom.sharedata.BtCommandReceiver;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class GocsdkService {
    private static GocsdkService INSTANCE = null;
    public static final int MSG_SERIAL_RECEIVED = 2;
    public static final int MSG_SHAREDATA_AVRCP = 22;
    public static final int MSG_START_SERIAL = 1;
    private static final int RESTART_DELAY = 2000;
    private static final String TAG = "GoodocomService";
    private static Object lock = new Object();
    private static final boolean newCallRspType = true;
    public volatile CommandA2dpImp a2dp;
    public volatile AdayoAutoConnect adayoAutoConnect;
    public volatile AdayoShareInfoImple adayoShareInfoImple;
    public volatile AdayoSystemManager adayoSystemManager;
    public volatile CommandAvrcpImp avrcp;
    public volatile CommandBluetoothImp bluetooth;
    public volatile boolean bserviceready;
    public String callNumber;
    public int callState;
    private GocsdkCommandSender command;
    private int count;
    public String currentCallAddress;
    public volatile String currentConnectedAddr;
    public volatile String currentConnectedName;
    public volatile CommandGattServerImp gattServer;
    private Handler handler;
    public volatile CommandHfpImp hfp;
    public volatile CommandHidImp hid;
    public volatile String lastConnectedAddr;
    public volatile String localAddress;
    public volatile String localName;
    public volatile String localPin;
    public BtCommandReceiver mBtCommandReceiver;
    public volatile CommandMapImp map;
    public volatile CommandOppImp opp;
    public volatile CommandPbapImp pbap;
    public int playStete;
    private volatile boolean running;
    private byte[] serialBuffer;
    private SerialThread serialThread;
    public volatile CommandSppImp spp;
    private boolean use_socket;

    public static GocsdkService getInstance() {
        GocsdkService gocsdkService = INSTANCE;
        if (gocsdkService != null) {
            return gocsdkService;
        }
        synchronized (lock) {
            if (INSTANCE != null) {
                return INSTANCE;
            }
            INSTANCE = new GocsdkService();
            return INSTANCE;
        }
    }

    private GocsdkService() {
        this.a2dp = null;
        this.avrcp = null;
        this.bluetooth = null;
        this.gattServer = null;
        this.hfp = null;
        this.hid = null;
        this.map = null;
        this.opp = null;
        this.pbap = null;
        this.spp = null;
        this.adayoShareInfoImple = null;
        this.adayoSystemManager = null;
        this.adayoAutoConnect = null;
        this.serialBuffer = new byte[1024];
        this.count = 0;
        this.currentConnectedAddr = NfDef.DEFAULT_ADDRESS;
        this.lastConnectedAddr = NfDef.DEFAULT_ADDRESS;
        this.currentConnectedName = BuildConfig.FLAVOR;
        this.localAddress = BuildConfig.FLAVOR;
        this.localName = BuildConfig.FLAVOR;
        this.localPin = BuildConfig.FLAVOR;
        this.bserviceready = false;
        this.playStete = 0;
        this.callState = 7;
        this.callNumber = BuildConfig.FLAVOR;
        this.use_socket = false;
        this.running = newCallRspType;
        this.handler = new Handler(Looper.getMainLooper()) {
            /* class com.goodocom.gocsdkserver.GocsdkService.AnonymousClass1 */

            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    Log.e(GocsdkService.TAG, "serialThread start!");
                    GocsdkService gocsdkService = GocsdkService.this;
                    gocsdkService.serialThread = new SerialThread();
                    GocsdkService.this.serialThread.start();
                } else if (msg.what == 2) {
                    GocsdkService.this.onBytes((byte[]) msg.obj);
                }
            }
        };
        this.a2dp = new CommandA2dpImp(this);
        this.avrcp = new CommandAvrcpImp(this);
        this.bluetooth = new CommandBluetoothImp(this);
        this.gattServer = new CommandGattServerImp(this);
        this.hfp = new CommandHfpImp(this);
        this.hid = new CommandHidImp(this);
        this.map = new CommandMapImp(this);
        this.opp = new CommandOppImp(this);
        this.pbap = new CommandPbapImp(this);
        this.spp = new CommandSppImp(this);
        this.adayoShareInfoImple = new AdayoShareInfoImple(this);
        this.adayoSystemManager = new AdayoSystemManager(this);
        this.adayoAutoConnect = new AdayoAutoConnect(this);
        this.mBtCommandReceiver = new BtCommandReceiver();
        this.mBtCommandReceiver.setGocsdkService(this);
        this.mBtCommandReceiver.setINfCommandA2dp(this.a2dp);
        this.mBtCommandReceiver.setINfCommandAvrcp(this.avrcp);
        this.mBtCommandReceiver.setINfCommandBluetooth(this.bluetooth);
        this.mBtCommandReceiver.setINfCommandHfp(this.hfp);
        IntentFilter filter = new IntentFilter();
        filter.addAction(BtCommandReceiver.MSG_SYS_STD_BT_MUSIC_PAUSE);
        filter.addAction(BtCommandReceiver.MSG_SYS_STD_BT_MUSIC_START);
        filter.addAction(BtCommandReceiver.MSG_SYS_STD_EASYCONN);
        filter.addAction(BtCommandReceiver.MSG_SYS_STD_EASYCONN_CHECKSTATUS);
        filter.addAction(BtCommandReceiver.MSG_SYS_STD_EASYCONN_GWMEC);
        filter.addAction(BtCommandReceiver.MSG_SYS_STD_SCREEN_PAUSE);
        filter.addAction(BtCommandReceiver.MSG_SYS_STD_SCREEN_START);
        filter.addAction(BtCommandReceiver.ONKEYUP);
        filter.addAction(BtCommandReceiver.ONKEYDOWN);
        filter.addAction(BtCommandReceiver.ONKEYLONGPRESS);
        GocApplication.INSTANCE.registerReceiver(this.mBtCommandReceiver, filter);
        this.running = newCallRspType;
        this.command = new GocsdkCommandSender(this);
        Log.d(TAG, "onCreate tid:" + Thread.currentThread().getId());
        this.serialThread = new SerialThread();
        this.serialThread.setPriority(10);
        this.serialThread.start();
        if (this.adayoSystemManager != null) {
            this.adayoSystemManager.notifyAdayoSystemServiceStartFinished();
        }
    }

    public static void destroy() {
        if (INSTANCE != null) {
            synchronized (lock) {
                if (INSTANCE != null) {
                    getInstance().running = false;
                    INSTANCE = null;
                }
            }
        }
    }

    public GocsdkCommandSender getCommand() {
        return this.command;
    }

    public Handler getHandler() {
        return this.handler;
    }

    public static final String gocAddr2Droid(String gocAddr) {
        String gocAddr2 = gocAddr.trim();
        if (gocAddr2.length() == 0) {
            return BuildConfig.FLAVOR;
        }
        if (gocAddr2.length() != 12) {
            Log.e(TAG, "gocAddr2Droid error:" + gocAddr2);
            return BuildConfig.FLAVOR;
        }
        return gocAddr2.substring(0, 2) + ":" + gocAddr2.substring(2, 4) + ":" + gocAddr2.substring(4, 6) + ":" + gocAddr2.substring(6, 8) + ":" + gocAddr2.substring(8, 10) + ":" + gocAddr2.substring(10, 12);
    }

    public static final String droidAddr2Goc(String droidAddr) {
        if (droidAddr == null) {
            return BuildConfig.FLAVOR;
        }
        return droidAddr.replaceAll(":", BuildConfig.FLAVOR);
    }

    private void onSerialCommand(String cmd) {
        String name;
        Log.e("serial", "REV:" + cmd);
        if (cmd.startsWith(Commands.IND_CMD_ADDR)) {
            if (cmd.length() >= 14) {
                String cmdaddr = gocAddr2Droid(cmd.substring(2));
                Log.e(TAG, "IND_CMD_ADDR:" + cmdaddr);
                this.bluetooth.onCmdAddress(cmdaddr);
            }
        } else if (cmd.startsWith(Commands.IND_CONNECT_MODE)) {
            if (cmd.length() == 3) {
                String _connect_mode = cmd.substring(2);
                Log.e(TAG, "IND_CONNECT_MODE:" + _connect_mode);
                this.bluetooth.onConnectMode(_connect_mode);
            }
        } else if (cmd.startsWith(Commands.IND_DUAL_CALL_MODE)) {
            if (cmd.length() == 3) {
                String _call_mode = cmd.substring(2);
                Log.e(TAG, "IND_DUAL_CALL_MODE:" + _call_mode);
                this.bluetooth.onCallMode(_call_mode);
            }
        } else if (cmd.startsWith(Commands.IND_HFP_CONNECTED)) {
            this.bluetooth.disconnectException = false;
            if (cmd.length() >= 14) {
                this.currentConnectedAddr = gocAddr2Droid(cmd.substring(2));
                this.lastConnectedAddr = this.currentConnectedAddr;
                Log.e(TAG, "IND_HFP_CONNECTED:" + this.currentConnectedAddr);
                this.hfp.onHfpConnected(gocAddr2Droid(cmd.substring(2)));
            } else {
                this.hfp.onHfpConnected(this.bluetooth.rspAddr);
            }
            this.pbap.onPhoneBookReady();
        } else if (cmd.startsWith(Commands.IND_CONNECTING)) {
            if (cmd.length() >= 14) {
                this.currentConnectedAddr = gocAddr2Droid(cmd.substring(2));
                Log.e(TAG, "IND_CONNECTING:" + this.currentConnectedAddr);
                this.hfp.onHfpConnecting(gocAddr2Droid(cmd.substring(2)));
                return;
            }
            this.hfp.onHfpConnecting(this.bluetooth.rspAddr);
        } else if (cmd.startsWith(Commands.IND_HFP_DISCONNECTED)) {
            if (cmd.length() < 4) {
                this.bluetooth.clearDeviceConnectInfo();
            } else {
                this.hfp.onHfpDisconnected(gocAddr2Droid(cmd.substring(2, 14)));
            }
        } else if (cmd.startsWith(Commands.IND_HFP_DISCONNECTED_RESON)) {
            Log.e(TAG, "IND_HFP_DISCONNECTED_RESON:" + cmd.substring(2));
            if (cmd.length() == 4) {
                this.bluetooth.onBtDisconnectedReason(Integer.parseInt(cmd.substring(2, 4)));
            }
        } else if (cmd.startsWith(Commands.IND_HFP_BANDRING)) {
            if (cmd.length() == 3) {
                this.hfp.onInBandRingtoneSupport(Integer.parseInt(cmd.substring(2, 3)));
            }
        } else if (cmd.startsWith(Commands.IND_LOCAL_PHONE_NUMBER)) {
            if (cmd.length() > 2) {
                this.hfp.onPhoneSIMNum(cmd.substring(2));
            }
        } else if (cmd.startsWith(Commands.IND_CLCC)) {
            Log.e(TAG, "CL:" + cmd + " type:" + newCallRspType);
            this.currentCallAddress = this.bluetooth.rspAddr;
            this.hfp.requestHfpAudioFocus();
            if (this.hfp.requestMicMode == 1) {
                this.hfp.requestMicResource();
            }
            this.hfp.hfpSwitchUi();
            int index = Integer.parseInt(cmd.substring(2, 3));
            int status = Integer.parseInt(cmd.substring(3, 4));
            String number = cmd.substring(4);
            this.callState = status;
            this.callNumber = number;
            DeviceInfo _devinfo = this.bluetooth.getDeviceByAddr(this.currentCallAddress);
            switch (status) {
                case 0:
                    this.hfp.onTalkingByIndex(number, index);
                    break;
                case 1:
                    this.hfp.onCurrentHoldByIndex(number, index);
                    break;
                case 2:
                case 3:
                    this.hfp.onCallSucceedByIndex(number, index);
                    break;
                case 4:
                    this.hfp.onIncomingByIndex(number, index);
                    break;
                case 5:
                    this.hfp.onCurrentWaitingByIndex(number, index);
                    break;
                case 6:
                    this.hfp.onCurrentHoldByIndex(number, index);
                    break;
            }
            CommandPbapImp commandPbapImp = this.pbap;
            if (CommandPbapImp.mdb == null) {
                CommandPbapImp commandPbapImp2 = this.pbap;
                CommandPbapImp.mdb = GocPbapHelper.getInstance().getWritableDatabase();
            }
            GocPbapHelper instance = GocPbapHelper.getInstance();
            CommandPbapImp commandPbapImp3 = this.pbap;
            String callName = instance.queryNameByNum(CommandPbapImp.mdb, this.currentCallAddress, this.callNumber);
            Log.e(TAG, "IND_CLCC : " + callName + " , num : " + this.callNumber + " , " + _devinfo);
            if (_devinfo == null) {
                this.adayoShareInfoImple.mShareBtPhoneInfo(this.callState, "暂无", this.callNumber, callName, null, newCallRspType, this.hfp.currentMute ^ newCallRspType);
            } else {
                this.adayoShareInfoImple.mShareBtPhoneInfo(this.callState, "暂无", this.callNumber, callName, null, _devinfo.isScoConnected, this.hfp.currentMute ^ newCallRspType);
            }
            this.adayoAutoConnect.autoConnectCancel();
        } else if (!cmd.startsWith(Commands.IND_SECOND_INCOMING)) {
            if (cmd.startsWith(Commands.IND_CALL_SUCCEED)) {
                if (cmd.length() == 2) {
                    this.hfp.onCallSucceed(BuildConfig.FLAVOR);
                }
            } else if (!cmd.startsWith(Commands.IND_INCOMING)) {
                if (cmd.startsWith(Commands.IND_HANG_UP)) {
                    Log.e("audio", ">>>newCallRspType>>>true");
                    DeviceInfo _devinfo2 = this.bluetooth.getDeviceByAddr(this.bluetooth.rspAddr);
                    if (cmd.length() <= 2) {
                        this.hfp.onHangUpByIndex(BuildConfig.FLAVOR, 1);
                        this.hfp.onHangUpByIndex(BuildConfig.FLAVOR, 2);
                        CommandPbapImp commandPbapImp4 = this.pbap;
                        if (CommandPbapImp.mdb == null) {
                            CommandPbapImp commandPbapImp5 = this.pbap;
                            CommandPbapImp.mdb = GocPbapHelper.getInstance().getWritableDatabase();
                        }
                        Log.e("audio", ">>>newCallRspType>>>true");
                        GocPbapHelper instance2 = GocPbapHelper.getInstance();
                        CommandPbapImp commandPbapImp6 = this.pbap;
                        String callName2 = instance2.queryNameByNum(CommandPbapImp.mdb, this.bluetooth.rspAddr, this.callNumber);
                        this.callState = 7;
                        this.adayoShareInfoImple.mShareBtPhoneInfo(this.callState, "暂无", this.callNumber, callName2, null, false, this.hfp.currentMute ^ newCallRspType);
                    } else {
                        Log.e("audio", ">>>cmd.contains>>>" + cmd);
                        if (cmd.contains(",")) {
                            String[] split = cmd.split(",");
                            int splitcount = split.length;
                            Log.e(TAG, "splitcount:" + splitcount);
                            if (splitcount == 2) {
                                this.hfp.onHangUpByIndex(split[0].substring(3), Integer.parseInt(split[0].substring(2, 3)));
                                this.hfp.onHangUpByIndex(split[1].substring(1), Integer.parseInt(split[1].substring(0, 1)));
                            } else if (split.length == 3) {
                                this.hfp.onHangUpByIndex(split[0].substring(3), Integer.parseInt(split[0].substring(2, 3)));
                                this.hfp.onHangUpByIndex(split[1].substring(1), Integer.parseInt(split[1].substring(0, 1)));
                                this.hfp.onHangUpByIndex(split[2].substring(1), Integer.parseInt(split[2].substring(0, 1)));
                            }
                        } else {
                            this.hfp.onHangUpByIndex(cmd.substring(3), Integer.parseInt(cmd.substring(2, 3)));
                            Log.e("audio", ">>>cmd.contains>>>" + cmd);
                            CommandPbapImp commandPbapImp7 = this.pbap;
                            if (CommandPbapImp.mdb == null) {
                                CommandPbapImp commandPbapImp8 = this.pbap;
                                CommandPbapImp.mdb = GocPbapHelper.getInstance().getWritableDatabase();
                            }
                            GocPbapHelper instance3 = GocPbapHelper.getInstance();
                            CommandPbapImp commandPbapImp9 = this.pbap;
                            String callName3 = instance3.queryNameByNum(CommandPbapImp.mdb, this.bluetooth.rspAddr, cmd.substring(3));
                            Log.d(TAG, "audio : " + callName3);
                            DeviceInfo[] infos = this.bluetooth.loopFindConnectDevices();
                            if (infos[0] == null || infos[1] == null) {
                                this.callState = 7;
                            } else if (_devinfo2 == infos[0]) {
                                int i = infos[1].hf_state;
                                CommandHfpImp commandHfpImp = this.hfp;
                                if (i <= 3) {
                                    this.callState = 7;
                                    this.adayoShareInfoImple.mShareBtPhoneInfo(this.callState, "暂无", cmd.substring(3), callName3, null, _devinfo2.isScoConnected, this.hfp.currentMute ^ newCallRspType);
                                }
                            } else {
                                int i2 = infos[0].hf_state;
                                CommandHfpImp commandHfpImp2 = this.hfp;
                                if (i2 <= 3) {
                                    this.callState = 7;
                                    this.adayoShareInfoImple.mShareBtPhoneInfo(this.callState, "暂无", cmd.substring(3), callName3, null, _devinfo2.isScoConnected, this.hfp.currentMute ^ newCallRspType);
                                }
                            }
                        }
                    }
                    this.hfp.sendDelayRequestCalllog(PathInterpolatorCompat.MAX_NUM_POINTS);
                    DeviceInfo[] infos2 = this.bluetooth.loopFindConnectDevices();
                    if (infos2[0] == null || infos2[1] == null) {
                        this.hfp.releaseAudioInfo(newCallRspType);
                        return;
                    }
                    int i3 = infos2[0].hf_state;
                    CommandHfpImp commandHfpImp3 = this.hfp;
                    if (i3 <= 3) {
                        int i4 = infos2[1].hf_state;
                        CommandHfpImp commandHfpImp4 = this.hfp;
                        if (i4 <= 3) {
                            this.hfp.releaseAudioInfo(newCallRspType);
                        }
                    }
                } else if (!cmd.startsWith(Commands.IND_TALKING) && !cmd.startsWith(Commands.IND_SECOND_HOLD) && !cmd.startsWith(Commands.IND_SECOND_CALLING)) {
                    if (cmd.startsWith(Commands.IND_SECOND_HANG_UP)) {
                        int index2 = Integer.parseInt(cmd.substring(2, 3));
                        if (cmd.length() <= 3) {
                            this.hfp.onSecondHangUpByIndex(BuildConfig.FLAVOR, index2);
                        } else {
                            this.hfp.onSecondHangUpByIndex(cmd.substring(3), index2);
                        }
                        this.hfp.sendDelayRequestCalllog(PathInterpolatorCompat.MAX_NUM_POINTS);
                    } else if (!cmd.startsWith(Commands.IND_SECOND_TALKING)) {
                        if (cmd.startsWith(Commands.IND_SET_RING_PATH)) {
                            this.bluetooth.onBellPath(cmd.substring(2));
                        } else if (cmd.startsWith(Commands.IND_RING_START)) {
                            this.hfp.onRingStart();
                        } else if (cmd.startsWith(Commands.IND_RING_STOP)) {
                            this.hfp.onRingStop();
                        } else if (cmd.startsWith(Commands.IND_HF_LOCAL)) {
                            this.hfp.onHfpLocal();
                            DeviceInfo info = this.bluetooth.getDeviceByAddr(this.bluetooth.rspAddr);
                            if (info != null) {
                                int i5 = info.hf_state;
                                CommandHfpImp commandHfpImp5 = this.hfp;
                                if (i5 <= 3) {
                                    this.hfp.releaseHfpAudioFocus();
                                }
                            }
                            if (this.callState >= 0) {
                                this.adayoShareInfoImple.mShareBtPhoneInfo(this.callState, "暂无", this.callNumber, null, null, false, this.hfp.currentMute ^ newCallRspType);
                            }
                        } else if (cmd.startsWith(Commands.IND_HF_REMOTE)) {
                            this.hfp.requestHfpAudioFocus();
                            this.hfp.onHfpRemote();
                            if (this.callState >= 0) {
                                this.adayoShareInfoImple.mShareBtPhoneInfo(this.callState, "暂无", this.callNumber, null, null, newCallRspType, this.hfp.currentMute ^ newCallRspType);
                            }
                        } else if (cmd.startsWith(Commands.IND_IN_PAIR_MODE)) {
                            this.bluetooth.onInPairMode();
                        } else if (cmd.startsWith(Commands.IND_EXIT_PAIR_MODE)) {
                            this.bluetooth.onExitPairMode();
                        } else if (cmd.startsWith(Commands.IND_INIT_SUCCEED)) {
                            Log.e(TAG, "into is");
                            if (cmd.length() == 3) {
                                int initState = Integer.parseInt(cmd.substring(2, 3));
                                Log.e(TAG, "BT state init:" + initState);
                                if (initState != 1) {
                                    this.a2dp.onInitSucceed();
                                    this.pbap.onInitSucceed();
                                    this.avrcp.onInitSucceed();
                                }
                                if (initState == 0 || initState == 2) {
                                    this.bluetooth.onInitSucceed();
                                }
                                this.bluetooth.onBTenable(initState);
                                return;
                            }
                            Log.e(TAG, "out is length err");
                        } else if (cmd.startsWith(Commands.IND_PAIR_STATE)) {
                            String str_addr = gocAddr2Droid(cmd.substring(3, 15));
                            int pairstate = Integer.parseInt(cmd.substring(2, 3));
                            Log.e(TAG, "pair resault:" + pairstate + ",addr:" + str_addr);
                            this.bluetooth.onDevicePairStaus(str_addr, BuildConfig.FLAVOR, pairstate);
                            if (pairstate == 1 && this.bluetooth.isLocalReqPair) {
                                this.bluetooth.isLocalReqPair = false;
                            } else if (pairstate == 2) {
                                Log.e("delete", "str_addr:: " + str_addr);
                                this.pbap.deleteContactsByAddress(str_addr);
                                this.pbap.deleteAllCalllogByAddress(str_addr);
                            }
                        } else if (cmd.startsWith(Commands.IND_MUSIC_PLAYING)) {
                            Log.e(TAG, "callback Commands playing:" + cmd);
                            this.avrcp.onMusicPlaying();
                            this.a2dp.onA2dpPlaying();
                            this.playStete = 1;
                        } else if (cmd.startsWith(Commands.IND_MUSIC_STOPPED)) {
                            Log.e(TAG, "callback Commands stoped:" + cmd);
                            this.playStete = 2;
                            DeviceInfo info2 = this.bluetooth.getDeviceByAddr(this.bluetooth.rspAddr);
                            if (info2 != null) {
                                int i6 = info2.a2dp_state;
                                CommandA2dpImp commandA2dpImp = this.a2dp;
                                if (i6 < 2) {
                                    Log.d(TAG, "A2dp is not connected return");
                                    return;
                                }
                                this.avrcp.onMusicStopped();
                                this.a2dp.onA2dpStop();
                                this.playStete = 2;
                                this.adayoShareInfoImple.mShareBtMusicInfo(22, this.avrcp.current_song_music, this.avrcp.current_song_artist, this.avrcp.current_song_album, this.avrcp.lastPosition, this.avrcp.lastLength, "暂无", this.avrcp.mPlayMode, this.playStete);
                            }
                        } else if (cmd.startsWith(Commands.IND_SIGNAL_BATTERY_VAL)) {
                            this.hfp.currentHfpRemoteSignalStrength(Integer.parseInt(cmd.substring(2, 4)));
                            this.hfp.currentHfpRemoteBatteryIndicator(Integer.parseInt(cmd.substring(4, 6)));
                        } else if (cmd.startsWith(Commands.IND_VOICE_CONNECTED)) {
                            this.hfp.onVoiceConnected();
                        } else if (cmd.startsWith(Commands.IND_VOICE_DISCONNECTED)) {
                            this.hfp.onVoiceDisconnected();
                        } else if (cmd.startsWith(Commands.IND_AUTO_CONNECT_ACCEPT)) {
                            if (cmd.length() < 4) {
                                Log.e(TAG, cmd + "=====error command");
                                return;
                            }
                            this.bluetooth.onAutoConnect(Integer.parseInt(cmd.substring(2, 3)));
                            this.hfp.onAutoAccept(Integer.parseInt(cmd.substring(3, 4)));
                        } else if (cmd.startsWith(Commands.IND_CURRENT_ADDR)) {
                            if (cmd.length() < 3) {
                                Log.e(TAG, cmd + "==== error command");
                                return;
                            }
                            this.currentConnectedAddr = gocAddr2Droid(cmd.substring(2));
                            this.lastConnectedAddr = this.currentConnectedAddr;
                            Log.e(TAG, "currentConnectedAddr:" + this.currentConnectedAddr);
                        } else if (cmd.startsWith(Commands.IND_CURRENT_NAME)) {
                            if (cmd.length() < 3) {
                                Log.e(TAG, cmd + "==== error command");
                                return;
                            }
                            this.currentConnectedName = cmd.substring(2);
                        } else if (cmd.startsWith(Commands.IND_AV_STATUS)) {
                            if (cmd.length() < 3) {
                                Log.e(TAG, cmd + "=====error");
                                return;
                            }
                            this.avrcp.onAvStatus(Integer.parseInt(cmd.substring(2, 3)));
                            this.a2dp.onAvStatus(Integer.parseInt(cmd.substring(2, 3)));
                        } else if (cmd.startsWith(Commands.IND_AVRCP_STATUS)) {
                            if (cmd.length() < 3) {
                                Log.e(TAG, cmd + "=====error");
                                return;
                            }
                            this.avrcp.onAvrcpStatus(Integer.parseInt(cmd.substring(2, 3)));
                        } else if (cmd.startsWith(Commands.IND_HFP_STATUS)) {
                            if (cmd.length() < 3) {
                                Log.e(TAG, cmd + " ==== error");
                                return;
                            }
                            int status2 = Integer.parseInt(cmd.substring(2, 3));
                            this.hfp.onHfpStatus(status2);
                            this.bluetooth.onHfpStatus(status2);
                        } else if (cmd.startsWith(Commands.IND_VERSION_DATE)) {
                            if (cmd.length() < 3) {
                                Log.e(TAG, cmd + "====error");
                                return;
                            }
                            this.bluetooth.onVersionDate(cmd.substring(2));
                        } else if (cmd.startsWith(Commands.IND_CURRENT_DEVICE_NAME)) {
                            if (cmd.length() < 3) {
                                Log.e(TAG, cmd + "====error");
                                return;
                            }
                            this.localName = cmd.substring(2);
                            this.bluetooth.onLocalName(this.localName);
                        } else if (cmd.startsWith(Commands.IND_CURRENT_PIN_CODE)) {
                            if (cmd.length() < 3) {
                                Log.e(TAG, cmd + "====error");
                                return;
                            }
                            this.localPin = cmd.substring(2);
                            this.bluetooth.onLocalPin(this.localPin);
                        } else if (cmd.startsWith(Commands.IND_A2DP_CONNECTED)) {
                            if (cmd.length() > 2) {
                                if (cmd.length() >= 14) {
                                    this.currentConnectedAddr = gocAddr2Droid(cmd.substring(2, 14));
                                    this.lastConnectedAddr = this.currentConnectedAddr;
                                    Log.e(TAG, "IND_A2DP_CONNECTED:" + this.currentConnectedAddr);
                                }
                                this.a2dp.onA2dpConnected(gocAddr2Droid(cmd.substring(2, 14)));
                                return;
                            }
                            this.a2dp.onA2dpConnected(this.bluetooth.rspAddr);
                        } else if (cmd.startsWith(Commands.IND_A2DP_DISCONNECTED)) {
                            if (cmd.length() < 3) {
                                this.a2dp.onA2dpDisconnected(this.bluetooth.rspAddr);
                            } else {
                                this.a2dp.onA2dpDisconnected(gocAddr2Droid(cmd.substring(2, 14)));
                            }
                            this.avrcp.onMusicStopped();
                            if (cmd.length() > 2) {
                                this.adayoShareInfoImple.sendShareBtConnState(newCallRspType, gocAddr2Droid(cmd.substring(2, 14)));
                            } else {
                                this.adayoShareInfoImple.sendShareBtConnState(newCallRspType, this.bluetooth.rspAddr);
                            }
                        } else if (cmd.startsWith(Commands.IND_CURRENT_AND_PAIR_LIST)) {
                            if (cmd.length() < 15) {
                                Log.e(TAG, cmd + "====error");
                            } else if (cmd.length() == 15) {
                                String str_addr2 = gocAddr2Droid(cmd.substring(3, 15));
                                int index3 = Integer.parseInt(cmd.substring(2, 3));
                                Log.e(TAG, "send callback:" + str_addr2);
                                this.bluetooth.onCurrentAndPairList(index3, BuildConfig.FLAVOR, str_addr2, cmd.substring(15, 21), 0);
                            } else {
                                String str_index = cmd.substring(2, 3);
                                String str_addr3 = cmd.substring(3, 15);
                                String str_cod = cmd.substring(15, 21);
                                int int_uuid_cnt = Integer.parseInt(cmd.substring(21, 23));
                                String str_connect_profile = cmd.substring((int_uuid_cnt * 4) + 23, (int_uuid_cnt * 4) + 31);
                                String str_name = cmd.substring((int_uuid_cnt * 4) + 31);
                                int int_connect_profile = Integer.parseInt(str_connect_profile, 16);
                                Log.e(TAG, "send callback:" + str_addr3 + "," + str_name + " int_connect_profile:" + int_connect_profile);
                                this.bluetooth.onCurrentAndPairList(Integer.parseInt(str_index), str_name, gocAddr2Droid(str_addr3), str_cod, int_connect_profile);
                            }
                        } else if (cmd.startsWith(Commands.IND_SET_PHONE_BOOK)) {
                            Log.e("stop", "IND_SET_PHONE_BOOK");
                            this.pbap.onPhoneBookDowning(Integer.parseInt(cmd.substring(2)));
                        } else if (cmd.startsWith(Commands.IND_PHONE_BOOK_VCARD_START)) {
                            this.pbap.onPhoneBookVcardStart(Integer.parseInt(cmd.substring(2)));
                        } else if (cmd.startsWith(Commands.IND_PHONE_BOOK_VCARD_END)) {
                            if (cmd.length() < 3) {
                                this.pbap.onPhoneBookVcardEnd(null);
                            } else {
                                this.pbap.onPhoneBookVcardEnd(cmd.substring(2));
                            }
                        } else if (cmd.startsWith(Commands.IND_CURRENT_AND_PAIR_LIST_OVER)) {
                            if (cmd.length() == 3 && Integer.parseInt(cmd.substring(2, 3)) == 0) {
                                Log.e(TAG, "pairlist is empty");
                                this.bluetooth.onCurrentAndPairList(-1, BuildConfig.FLAVOR, BuildConfig.FLAVOR, "0x00", 0);
                            }
                        } else if (cmd.startsWith(Commands.IND_PHONE_BOOK)) {
                            if (cmd.length() < 6) {
                                Log.e(TAG, cmd + "====error");
                                return;
                            }
                            String number2 = BuildConfig.FLAVOR;
                            String str_type = cmd.substring(2, 3);
                            int nameLen = Integer.parseInt(cmd.substring(3, 5));
                            int numLen = Integer.parseInt(cmd.substring(5, 7));
                            byte[] bytes = cmd.getBytes();
                            Log.e("len", "nameLen : " + nameLen + "  numLen: " + numLen);
                            if (nameLen > 0) {
                                byte[] buffer = new byte[nameLen];
                                System.arraycopy(bytes, 7, buffer, 0, nameLen);
                                name = new String(buffer);
                            } else {
                                name = BuildConfig.FLAVOR;
                            }
                            if (numLen <= 0) {
                                number2 = BuildConfig.FLAVOR;
                            } else if (nameLen + 7 + numLen == bytes.length) {
                                byte[] buffer2 = new byte[numLen];
                                System.arraycopy(bytes, nameLen + 7, buffer2, 0, numLen);
                                number2 = new String(buffer2);
                            } else {
                                Log.e("goc", "PhoneBook bytes length is err!");
                            }
                            Log.e("len", "str_type : " + str_type + "  name: " + name + "   number: " + number2);
                            this.pbap.onPhoneBook(Integer.parseInt(str_type), name, number2);
                        } else if (cmd.startsWith(Commands.IND_PHONE_BOOK_DONE)) {
                            if (cmd.length() < 3) {
                                this.pbap.onDownOver(0);
                                return;
                            }
                            this.pbap.onDownOver(Integer.parseInt(cmd.substring(2)));
                        } else if (cmd.startsWith(Commands.IND_PHONEBOOK_SIZE)) {
                            if (cmd.length() < 3) {
                                Log.d(TAG, "cmd error");
                            } else {
                                this.pbap.onPhonebookSize(Integer.parseInt(cmd.substring(2)));
                            }
                        } else if (cmd.startsWith(Commands.IND_SIM_DONE)) {
                            this.pbap.onSimDone();
                        } else if (cmd.startsWith(Commands.IND_CALLLOG_DONE)) {
                            if (cmd.length() < 3) {
                                this.pbap.onCalllogDone(0);
                                return;
                            }
                            this.pbap.onCalllogDone(Integer.parseInt(cmd.substring(2)));
                        } else if (cmd.startsWith(Commands.IND_CALLLOG)) {
                            if (cmd.length() < 4) {
                                Log.e(TAG, cmd + "====error");
                                return;
                            }
                            String[] split2 = cmd.substring(4).split("\\[FF\\]");
                            int splitCnt = split2.length;
                            if (splitCnt != 3) {
                                Log.e(TAG, "split cnt:" + splitCnt);
                                Log.e(TAG, "split[0]:" + split2[0]);
                                Log.e(TAG, "split[1]:" + split2[1]);
                            }
                            if (splitCnt == 3) {
                                this.pbap.onCalllog(Integer.parseInt(cmd.substring(2, 3)), Integer.parseInt(cmd.substring(3, 4)), split2[0], split2[1], split2[2]);
                            }
                            if (splitCnt == 2) {
                                this.pbap.onCalllog(Integer.parseInt(cmd.substring(2, 3)), Integer.parseInt(cmd.substring(3, 4)), split2[0], split2[1], BuildConfig.FLAVOR);
                            }
                        } else if (!cmd.startsWith(Commands.IND_PHOOKBOOK_PHOTO)) {
                            if (cmd.startsWith(Commands.IND_MESSAGE_INFO)) {
                                if (cmd.length() < 3) {
                                    Log.e(TAG, "MESSAGE_ERROR");
                                } else {
                                    parseMapMessage(cmd);
                                }
                            } else if (cmd.startsWith(Commands.IND_CONNECT_MAP_MESSAGE)) {
                                this.map.onMapServiceReady();
                                if (cmd.length() < 3) {
                                    Log.d(TAG, "cmd err");
                                } else {
                                    this.map.onMapConnected(gocAddr2Droid(cmd.substring(2)));
                                }
                            } else if (cmd.startsWith(Commands.IND_DISCOVERY)) {
                                if (cmd.length() < 15) {
                                    Log.e(TAG, cmd + "===error");
                                } else if (cmd.length() == 15) {
                                    this.bluetooth.onDiscovery(cmd.substring(2, 3), BuildConfig.FLAVOR, gocAddr2Droid(cmd.substring(3)));
                                } else {
                                    String str_addr4 = cmd.substring(2, 14);
                                    String str_cod2 = cmd.substring(14, 20);
                                    cmd.substring(20, 22);
                                    int int_servicelist_cnt = Integer.parseInt(cmd.substring(22, 24));
                                    if (int_servicelist_cnt > 0) {
                                        cmd.substring(24, (int_servicelist_cnt * 4) + 24);
                                    }
                                    String str_name2 = cmd.substring((int_servicelist_cnt * 4) + 24);
                                    Log.e(TAG, cmd + "type:" + str_cod2 + " addr:" + str_addr4 + " name:" + str_name2);
                                    this.bluetooth.onDiscovery(str_cod2, str_name2, gocAddr2Droid(str_addr4));
                                }
                            } else if (cmd.startsWith(Commands.IND_DISCOVERY_DONE)) {
                                this.bluetooth.onDiscoveryDone();
                            } else if (cmd.startsWith(Commands.IND_LOCAL_ADDRESS)) {
                                if (cmd.length() != 14) {
                                    Log.e(TAG, "ind local addr err");
                                } else {
                                    this.localAddress = gocAddr2Droid(cmd.substring(2));
                                }
                            } else if (!cmd.startsWith(Commands.IND_OUTGOING_TALKING_NUMBER)) {
                                if (cmd.startsWith(Commands.IND_MUSIC_INFO)) {
                                    if (cmd.length() <= 2) {
                                        Log.e(TAG, cmd + "===error");
                                        return;
                                    }
                                    String[] arr = cmd.substring(2).split("\\[FF\\]");
                                    Log.e("play", "arr>>>> " + arr.length);
                                    if (arr.length == 5) {
                                        this.avrcp.onMusicInfo(arr[0], arr[1], "none", Integer.parseInt(arr[2]), Integer.parseInt(arr[3]), Integer.parseInt(arr[4]));
                                    } else if (arr.length == 6) {
                                        this.avrcp.onMusicInfo(arr[0], arr[1], arr[2], Integer.parseInt(arr[3]), Integer.parseInt(arr[4]), Integer.parseInt(arr[5]));
                                    } else {
                                        Log.e(TAG, cmd + "===error");
                                    }
                                } else if (cmd.startsWith(Commands.IND_MUSIC_POS)) {
                                    if (cmd.length() != 18) {
                                        Log.e(TAG, cmd + "====error");
                                        return;
                                    }
                                    String strpos = cmd.substring(2, 10);
                                    String strlen = cmd.substring(10, 18);
                                    long pos = Long.parseLong(strpos, 16);
                                    long total = Long.parseLong(strlen, 16);
                                    Log.e("musicpos", "pos:: " + pos + "  total  " + total + "   strpos " + strpos + "  strlen " + strlen);
                                    this.avrcp.onMusicPos(pos, total);
                                } else if (cmd.startsWith(Commands.IND_PROFILE_ENABLED)) {
                                    if (cmd.length() < 12) {
                                        Log.e(TAG, cmd + "====error");
                                        return;
                                    }
                                    boolean[] enabled = new boolean[10];
                                    for (int ii = 0; ii < 10; ii++) {
                                        if (cmd.charAt(ii + 2) == '0') {
                                            enabled[ii] = false;
                                        } else {
                                            enabled[ii] = newCallRspType;
                                        }
                                    }
                                    Log.e(TAG, "IND_PROFILE_ENABLED:" + enabled.toString());
                                } else if (cmd.startsWith(Commands.IND_MESSAGE_LIST)) {
                                    String text = cmd.substring(2);
                                    if (text.length() == 0) {
                                        Log.e("goc", "cmd error:param==0" + cmd);
                                        return;
                                    }
                                    String[] arr2 = text.split("\\[FF\\]", -1);
                                    if (arr2.length != 6) {
                                        Log.e("goc", "cmd error:arr.length=" + arr2.length + ";" + cmd);
                                        return;
                                    }
                                    this.map.onMessageInfo(arr2[0], arr2[1], arr2[2], arr2[3], arr2[4], arr2[5]);
                                } else if (cmd.startsWith(Commands.IND_MESSAGE_TEXT)) {
                                    this.map.onMessageContent(cmd.substring(2));
                                } else if (cmd.startsWith(Commands.IND_DEFAULT_SETING)) {
                                    if (cmd.length() < 3) {
                                        Log.d(TAG, "cmd error");
                                        return;
                                    }
                                    this.hfp.onAnswerType(Integer.parseInt(cmd.substring(2, 3)));
                                    this.hfp.onAutoAnswer(Integer.parseInt(cmd.substring(3, 4)));
                                    this.adayoAutoConnect.onAutoConnectMode(Integer.parseInt(cmd.substring(4, 5)));
                                    this.bluetooth.onAutoConnect(Integer.parseInt(cmd.substring(5, 6)));
                                    this.adayoAutoConnect.onAutoConnectTime((long) Integer.parseInt(cmd.substring(6, 9), 16));
                                    this.pbap.onAutoDownCalllog(Integer.parseInt(cmd.substring(9, 10)));
                                    this.pbap.onAutoDownContact(Integer.parseInt(cmd.substring(10, 11)));
                                    this.bluetooth.onDiscoverable(Integer.parseInt(cmd.substring(12, 13)));
                                    this.bluetooth.startBtEnable(Integer.parseInt(cmd.substring(13, 14)));
                                    this.pbap.onCalllogDownopt(Integer.parseInt(cmd.substring(15, 16)));
                                    this.pbap.onCalllogSaveLocal(Integer.parseInt(cmd.substring(16, 17)));
                                    this.pbap.onContactSaveLocal(Integer.parseInt(cmd.substring(17, 18)));
                                    this.pbap.onMaxDownCalllogSize(Integer.parseInt(cmd.substring(19, 23), 16));
                                    this.pbap.onMaxDownContactSize(Integer.parseInt(cmd.substring(23, 27), 16));
                                    this.bluetooth.onMaxPairList(Integer.parseInt(cmd.substring(27, 28)));
                                    this.avrcp.startBtmusicForce(Integer.parseInt(cmd.substring(28, 29)));
                                    this.bluetooth.onConnectMode(cmd.substring(29, 30));
                                    this.bluetooth.onCallMode(cmd.substring(31, 32));
                                    this.bluetooth.onPlayPhoneBell(Integer.parseInt(cmd.substring(33, 34)));
                                    this.adayoAutoConnect.onAutoConnectNumber(Integer.parseInt(cmd.substring(34, 36), 16));
                                } else if (cmd.startsWith(Commands.IND_OPERATOR)) {
                                    if (cmd.length() < 3) {
                                        Log.d(TAG, "cmd error : " + cmd);
                                        return;
                                    }
                                    this.hfp.currentHfpRemoteOperatorStrength(cmd.substring(2));
                                } else if (cmd.startsWith(Commands.IND_GET_MESSAGE_LIST_START)) {
                                    if (cmd.length() < 3) {
                                        Log.d(TAG, "cmd err");
                                    } else {
                                        this.map.onMapDownloadStart(gocAddr2Droid(cmd.substring(2)));
                                    }
                                } else if (cmd.startsWith(Commands.IND_GET_MESSAGE_LIST_DONE)) {
                                    if (cmd.length() < 3) {
                                        Log.d(TAG, "cmd err");
                                    } else {
                                        this.map.onMapDownloadEnd(gocAddr2Droid(cmd.substring(2)));
                                    }
                                } else if (cmd.startsWith(Commands.IND_MESSAGE_DISCONNECTED)) {
                                    if (cmd.length() < 3) {
                                        Log.d(TAG, "cmd err");
                                    } else {
                                        this.map.onMapDisconnected(gocAddr2Droid(cmd.substring(2)));
                                    }
                                } else if (cmd.startsWith(Commands.IND_RECEIVE_NEW_MESSAGE)) {
                                    if (cmd.length() < 3) {
                                        Log.d(TAG, "cmd err");
                                    } else if (cmd.contains("[FF]")) {
                                        String[] split3 = cmd.split("\\[FF\\]");
                                        Log.e("mess", "split length is " + split3.length);
                                        if (split3.length == 4) {
                                            this.map.onMapReceiveNewMessage(split3[0].substring(2), split3[1], split3[2], split3[3]);
                                        }
                                    }
                                } else if (cmd.startsWith(Commands.IND_MAP_SEND_MESSAGE_SUCCESS)) {
                                    if (cmd.length() < 3) {
                                        Log.d(TAG, "cmd err");
                                        return;
                                    }
                                    String addr = gocAddr2Droid(cmd.substring(2, 14));
                                    String handle = BuildConfig.FLAVOR;
                                    String status3 = BuildConfig.FLAVOR;
                                    if (cmd.contains("[FF]")) {
                                        String[] split4 = cmd.split("\\[FF\\]");
                                        Log.e("mess", "split: " + split4.length);
                                        if (split4.length == 2) {
                                            handle = split4[0].substring(14);
                                            status3 = split4[1];
                                        }
                                    }
                                    this.map.onMapMessageSendSuccess(addr, handle, status3);
                                } else if (cmd.startsWith(Commands.IND_MAP_DELETE_MESSAGE_SUCCESS)) {
                                    if (cmd.length() < 3) {
                                        Log.e(TAG, "cmd err");
                                        return;
                                    }
                                    this.map.onMapMessageDeleteSuccess(gocAddr2Droid(cmd.substring(2, 14)), BuildConfig.FLAVOR, BuildConfig.FLAVOR);
                                } else if (!cmd.startsWith(Commands.IND_OK)) {
                                    cmd.startsWith(Commands.IND_ERROR);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void parseMapMessage(String cmd) {
        if (cmd.contains("[FF]")) {
            String[] split = cmd.split("\\[FF\\]");
            Log.e("mess", "split: " + split.length);
            if (split.length == 3) {
                String id = split[0];
                String shortMess = split[1];
                String time = split[2];
                GocMessages messages = new GocMessages();
                messages.messageId = id;
                messages.messageTime = time;
                messages.shortMessage = shortMess;
                this.map.onGocMessage(messages);
            } else if (split.length == 10) {
                this.map.onMapMessageInfo(split[0].substring(2), split[1], split[2], split[3], split[4], split[5], 0, Integer.parseInt(split[7]), split[8], split[9]);
            }
        }
    }

    private void onByte(byte b) {
        if (10 != b) {
            if (this.count >= 1000) {
                this.count = 0;
            }
            if (13 == b) {
                int i = this.count;
                if (i > 0) {
                    byte[] buf = new byte[i];
                    System.arraycopy(this.serialBuffer, 0, buf, 0, i);
                    onSerialCommand(new String(buf));
                    this.count = 0;
                }
            } else if ((b & NfDef.AVRCP_PLAYING_STATUS_ID_ERROR) == 255) {
                byte[] bArr = this.serialBuffer;
                int i2 = this.count;
                this.count = i2 + 1;
                bArr[i2] = 91;
                int i3 = this.count;
                this.count = i3 + 1;
                bArr[i3] = 70;
                int i4 = this.count;
                this.count = i4 + 1;
                bArr[i4] = 70;
                int i5 = this.count;
                this.count = i5 + 1;
                bArr[i5] = 93;
            } else {
                byte[] bArr2 = this.serialBuffer;
                int i6 = this.count;
                this.count = i6 + 1;
                bArr2[i6] = b;
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onBytes(byte[] data) {
        for (byte b : data) {
            onByte(b);
        }
    }

    /* access modifiers changed from: private */
    public class SerialThread extends Thread {
        private byte[] buffer = new byte[2048];
        private InputStream inputStream;
        private OutputStream outputStream = null;

        public void write(byte[] buf) {
            OutputStream outputStream2 = this.outputStream;
            if (outputStream2 != null) {
                try {
                    outputStream2.write(buf);
                } catch (IOException e) {
                }
            }
        }

        public SerialThread() {
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            LocalSocket client = null;
            SerialPort serial = null;
            try {
                if (GocsdkService.this.use_socket) {
                    Log.e("app", "use socket!");
                    client = new LocalSocket();
                    client.connect(new LocalSocketAddress("goc_serial", LocalSocketAddress.Namespace.RESERVED));
                    this.inputStream = client.getInputStream();
                    this.outputStream = client.getOutputStream();
                } else {
                    Log.e("goc", "use serial!");
                    serial = new SerialPort(new File("/dev/goc_serial"), 921600, 0);
                    Log.e("goc", "serial is not null!");
                    this.inputStream = serial.getInputStream();
                    this.outputStream = serial.getOutputStream();
                }
                if (this.outputStream != null) {
                    Log.e("goc", "+ snd init at cmd");
                    write((Commands.COMMAND_HEAD + Commands.INQUIRY_VERSION_DATE + "\r\n").getBytes());
                    Log.e("goc", "- snd init at cmd");
                }
                while (GocsdkService.this.running) {
                    int n = this.inputStream.read(this.buffer);
                    if (n < 0) {
                        if (GocsdkService.this.use_socket) {
                            if (client != null) {
                                client.close();
                            }
                        } else if (serial != null) {
                            serial.close();
                        }
                        throw new IOException("n==-1");
                    }
                    byte[] data = new byte[n];
                    System.arraycopy(this.buffer, 0, data, 0, n);
                    GocsdkService.this.handler.sendMessage(GocsdkService.this.handler.obtainMessage(2, data));
                }
                try {
                    if (GocsdkService.this.use_socket) {
                        if (client != null) {
                            client.close();
                        }
                    } else if (serial != null) {
                        serial.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e2) {
                try {
                    if (GocsdkService.this.use_socket) {
                        if (0 != 0) {
                            client.close();
                        }
                    } else if (0 != 0) {
                        serial.close();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                GocsdkService.this.handler.sendEmptyMessageDelayed(1, 2000);
            }
        }
    }

    public void write(String str) {
        if (this.serialThread == null) {
            Log.e(TAG, "write " + str + "error,serial not connected");
            return;
        }
        Log.e("serial", "--------------------write------------------:" + str);
        SerialThread serialThread2 = this.serialThread;
        serialThread2.write((Commands.COMMAND_HEAD + str + "\r\n").getBytes());
    }

    /* access modifiers changed from: package-private */
    public int decode_cod(int cod) {
        int majorDeviceClass = (cod & 7936) >> 8;
        int i = (cod & 252) >> 2;
        if (majorDeviceClass == 31) {
            return 9;
        }
        switch (majorDeviceClass) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 6;
            case 7:
                return 7;
            case 8:
                return 8;
            default:
                return 0;
        }
    }
}

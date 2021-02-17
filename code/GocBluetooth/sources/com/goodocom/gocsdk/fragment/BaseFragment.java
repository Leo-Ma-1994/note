package com.goodocom.gocsdk.fragment;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothA2dpSink;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadsetClient;
import android.bluetooth.BluetoothProfile;
import android.media.session.MediaSessionManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import com.goodocom.bttek.bt.base.jar.GocJar;
import com.goodocom.bttek.bt.base.listener.GocBluetoothSettingChangeListener;
import com.goodocom.gocsdk.GocAppData;
import com.goodocom.gocsdk.domain.BlueToothInfo;
import com.goodocom.gocsdk.domain.BlueToothPairedInfo;
import com.goodocom.gocsdk.domain.CallLogInfo;
import com.goodocom.gocsdk.domain.PhoneBookInfo;
import com.goodocom.gocsdk.manager.BluetoothConnectManager;

public abstract class BaseFragment extends Fragment implements BluetoothProfile.ServiceListener {
    public static final int MSG_AUTO_STATUS = 34;
    public static final int MSG_CALLLOG = 1;
    public static final int MSG_CALLLOG_DONE = 2;
    public static final int MSG_CONNECT_ADDRESS = 14;
    public static final int MSG_CONNECT_FAILE = 16;
    public static final int MSG_CONNECT_SUCCESS = 15;
    public static final int MSG_CURRENT_DEVICE_ADDRESS = 21;
    public static final int MSG_CURRENT_STATUS = 17;
    public static final int MSG_DEVICE_CONNECT = 22;
    public static final int MSG_DEVICE_CONNECTED = 3;
    public static final int MSG_DEVICE_DISCONNECTED = 4;
    public static final int MSG_DEVICE_NAME = 27;
    public static final int MSG_HFP_STATUS = 18;
    public static final int MSG_MUSIC_COVER = 6;
    public static final int MSG_MUSIC_LIST = 5;
    public static final int MSG_MUSIC_PLAYED_FAIL = 12;
    public static final int MSG_MUSIC_PLAYED_SUCCESS = 11;
    public static final int MSG_MUSIC_REQUEST_FAIL = 10;
    public static final int MSG_MUSIC_REQUEST_SUCCESS = 9;
    public static final int MSG_MUSIC_SETTING_FAIL = 8;
    public static final int MSG_MUSIC_SETTING_SUCCESS = 7;
    public static final int MSG_ORIGIN_BLUETOOTH_CONNECT = 36;
    public static final int MSG_ORIGIN_BLUETOOTH_PAIRED = 35;
    public static final int MSG_ORIGIN_PHONE_BOOK_DONE = 24;
    public static final int MSG_PAIRED_DEVICE = 13;
    public static final int MSG_PHONE_BOOK = 19;
    public static final int MSG_PHONE_BOOK_DONE = 20;
    public static final int MSG_PIN_CODE = 28;
    public static final int MSG_SEARCHE_DEVICE = 25;
    public static final int MSG_SEARCHE_DEVICE_DONE = 26;
    private static final String TAG = BaseFragment.class.getSimpleName();
    public static Handler hanlder = null;
    private static BluetoothPairListListener mBluetoothPairListListener;
    public Handler mBaseHander = new Handler(Looper.getMainLooper()) {
        /* class com.goodocom.gocsdk.fragment.BaseFragment.AnonymousClass1 */

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int i = msg.what;
            if (i == 1) {
                BaseFragment.this.mBluetoothCalllogListener.onCalllogStart();
                CallLogInfo info = (CallLogInfo) msg.obj;
                int i2 = info.type;
                if (i2 == 4) {
                    BaseFragment.this.mBluetoothCalllogListener.onOutCalllog(info);
                } else if (i2 == 5) {
                    BaseFragment.this.mBluetoothCalllogListener.onMissCalllog(info);
                } else if (i2 == 6) {
                    BaseFragment.this.mBluetoothCalllogListener.onComingCalllog(info);
                }
            } else if (i == 2) {
                BaseFragment.this.mBluetoothCalllogListener.onCalllogdown();
            } else if (i != 5 && i != 6 && i != 35) {
                if (i != 36) {
                    switch (i) {
                        case 13:
                            if (BaseFragment.mBluetoothPairListListener != null) {
                                BaseFragment.mBluetoothPairListListener.gocPaired((BlueToothPairedInfo) msg.obj);
                                return;
                            }
                            return;
                        case 14:
                            if (BaseFragment.mBluetoothPairListListener != null) {
                                BaseFragment.mBluetoothPairListListener.connectedAddress((String) msg.obj);
                                return;
                            }
                            return;
                        case 15:
                            if (BaseFragment.mBluetoothPairListListener != null) {
                                BaseFragment.mBluetoothPairListListener.connectedSuccess();
                                return;
                            }
                            return;
                        case 16:
                            if (BaseFragment.mBluetoothPairListListener != null) {
                                BaseFragment.mBluetoothPairListListener.connectedFail();
                                return;
                            }
                            return;
                        case 17:
                        case 21:
                        case 22:
                            return;
                        case 18:
                            if (BaseFragment.mBluetoothPairListListener != null) {
                                BaseFragment.mBluetoothPairListListener.hfpConnectState(((Integer) msg.obj).intValue());
                                return;
                            }
                            return;
                        case 19:
                            BaseFragment.this.mPhoneBookListListener.onGocPhoneBook((PhoneBookInfo) msg.obj);
                            return;
                        case 20:
                            BaseFragment.this.mPhoneBookListListener.onPhoneDown();
                            return;
                        default:
                            switch (i) {
                                case 24:
                                    BaseFragment.this.mPhoneBookListListener.onOriginPhoneBook();
                                    return;
                                case 25:
                                    BaseFragment.this.mSearchBluetoothListener.onGocSearch((BlueToothInfo) msg.obj);
                                    return;
                                case 26:
                                    BaseFragment.this.mSearchBluetoothListener.onSearchEnd();
                                    return;
                                case 27:
                                    if (BaseFragment.this.mBluetoothSettingsInfoListener != null) {
                                        BaseFragment.this.mBluetoothSettingsInfoListener.onName(msg);
                                    }
                                    if (BaseFragment.this.mSearchBluetoothListener != null) {
                                        BaseFragment.this.mSearchBluetoothListener.onName(msg);
                                        return;
                                    }
                                    return;
                                default:
                                    return;
                            }
                    }
                } else {
                    int data = ((Integer) msg.obj).intValue();
                    if (BaseFragment.mBluetoothPairListListener != null) {
                        BaseFragment.mBluetoothPairListListener.originConnect(data);
                    }
                }
            }
        }
    };
    public BluetoothCalllogListener mBluetoothCalllogListener;
    public BluetoothMusicListener mBluetoothMusicListener;
    public BluetoothSettingsInfoListener mBluetoothSettingsInfoListener;
    private MediaSessionManager mMediaSessionManager;
    public PhoneBookListListener mPhoneBookListListener;
    public SearchBluetoothListener mSearchBluetoothListener;
    private boolean onA2dpConnected = false;
    private boolean onHfpConnected = false;

    public interface BluetoothCalllogListener {
        void onCalllogStart();

        void onCalllogdown();

        void onComingCalllog(CallLogInfo callLogInfo);

        void onMissCalllog(CallLogInfo callLogInfo);

        void onOutCalllog(CallLogInfo callLogInfo);
    }

    public interface BluetoothMusicListener {
    }

    public interface BluetoothPairListListener {
        void connect(int i);

        void connectedAddress(String str);

        void connectedFail();

        void connectedSuccess();

        void currentConnectState(int i);

        void disconnect();

        void gocPaired(BlueToothPairedInfo blueToothPairedInfo);

        void hfpConnectState(int i);

        void originConnect(int i);

        void paired();
    }

    public interface BluetoothSettingsInfoListener {
        void onAutoConnectStateChange(String str);

        void onName(Message message);

        void onPincode(Message message);
    }

    public interface PhoneBookListListener {
        void onGocPhoneBook(PhoneBookInfo phoneBookInfo);

        void onOriginPhoneBook();

        void onPhoneDown();
    }

    public interface SearchBluetoothListener {
        void onGocSearch(BlueToothInfo blueToothInfo);

        void onName(Message message);

        void onPin(Message message);

        void onSearch(BluetoothDevice bluetoothDevice);

        void onSearchEnd();

        void onSearchStart();
    }

    public void onConnected(BluetoothDevice device) {
        Log.e("connect", "Base>>>>>>>>>>onConnected>>");
        if (device != null) {
            GocAppData.getInstance().mCurrentBluetoothDevice = device;
            GocAppData.getInstance().mConnectedMap.add(device.getAddress());
        }
    }

    public void onDisconnected() {
        Log.e("connect", "Base>>>>>>>>>>onDisconnected>");
        GocAppData.getInstance().mCurrentBluetoothDevice = null;
    }

    public void setBluetoothCalllogListener(BluetoothCalllogListener listener) {
        this.mBluetoothCalllogListener = listener;
    }

    public void setSearchBluetoothListener(SearchBluetoothListener listener) {
        this.mSearchBluetoothListener = listener;
    }

    public void setBluetoothMusicListener(BluetoothMusicListener listener) {
        this.mBluetoothMusicListener = listener;
    }

    public void setBluetoothSettingsInfoListener(BluetoothSettingsInfoListener listener) {
        this.mBluetoothSettingsInfoListener = listener;
    }

    public void setBluetoothPairListListener(BluetoothPairListListener listListener) {
        mBluetoothPairListListener = listListener;
    }

    public void setPhoneBookListListener(PhoneBookListListener listListener) {
        this.mPhoneBookListListener = listListener;
    }

    @Override // android.bluetooth.BluetoothProfile.ServiceListener
    public void onServiceConnected(int profile, BluetoothProfile proxy) {
        Log.e("connect", "onServiceConnected>>>>>>> " + proxy + "  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>profile :" + profile + "     proxy.getConnectedDevices().size()" + proxy.getConnectedDevices().size());
        if (proxy instanceof BluetoothA2dpSink) {
            if (proxy.getConnectedDevices().size() > 0) {
                for (int i = 0; i < proxy.getConnectedDevices().size(); i++) {
                    GocAppData.getInstance().mConnectedMap.add(proxy.getConnectedDevices().get(i).getAddress());
                }
                int uuid_size = proxy.getConnectedDevices().get(0).getUuids().length;
                for (int i2 = 0; i2 < uuid_size; i2++) {
                    if ("110b".equals(proxy.getConnectedDevices().get(0).getUuids()[i2].getUuid().toString())) {
                        GocAppData.getInstance().mOtherBluetoothDevice = proxy.getConnectedDevices().get(0);
                    }
                }
                onConnected(GocAppData.getInstance().mCurrentBluetoothDevice);
                this.onA2dpConnected = true;
                return;
            }
            this.onA2dpConnected = false;
        } else if (proxy instanceof BluetoothHeadsetClient) {
            if (proxy.getConnectedDevices().size() > 0) {
                for (int i3 = 0; i3 < proxy.getConnectedDevices().size(); i3++) {
                    GocAppData.getInstance().mConnectedMap.add(proxy.getConnectedDevices().get(i3).getAddress());
                }
                GocAppData.getInstance().mCurrentBluetoothDevice = proxy.getConnectedDevices().get(0);
                onConnected(GocAppData.getInstance().mCurrentBluetoothDevice);
                this.onHfpConnected = true;
                return;
            }
            this.onHfpConnected = false;
        } else if (proxy instanceof BluetoothA2dp) {
            if (proxy.getConnectedDevices().size() > 0) {
                GocAppData.getInstance().mOtherBluetoothDevice = proxy.getConnectedDevices().get(0);
                String str = TAG;
                Log.e(str, "BluetoothA2dp connect   GocAppData.getInstance().mOtherBluetoothDevice " + GocAppData.getInstance().mOtherBluetoothDevice.getAddress());
                for (int i4 = 0; i4 < proxy.getConnectedDevices().size(); i4++) {
                    GocAppData.getInstance().mConnectedMap.add(proxy.getConnectedDevices().get(i4).getAddress());
                }
                onConnected(GocAppData.getInstance().mCurrentBluetoothDevice);
                this.onA2dpConnected = true;
                return;
            }
            this.onA2dpConnected = false;
        }
    }

    @Override // android.bluetooth.BluetoothProfile.ServiceListener
    public void onServiceDisconnected(int profile) {
        Log.e("connect", "onServiceDisconnected : " + profile);
        onDisconnected();
    }

    @Override // android.support.v4.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hanlder = this.mBaseHander;
        if (getActivity() != null) {
            BluetoothConnectManager.getInstance(getActivity()).initHeadsetClient();
        }
        registBluetoothSettings();
        try {
            GocJar.requestBtPairedDevices(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroy() {
        super.onDestroy();
    }

    public void loadContacts() {
        Log.e("load", "base loadContacts>>>>>>>>>>>>>>.");
    }

    public void loadCalllog() {
        Log.e("load", "base loadCalllog>>>>>>>>>>>>>>.");
    }

    @Override // android.support.v4.app.Fragment
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        String str = TAG;
        Log.e(str, "onHiddenChanged: SUPER " + hidden);
    }

    public void registBluetoothSettings() {
        GocJar.registerBluetoothSettingChangeListener(new GocBluetoothSettingChangeListener() {
            /* class com.goodocom.gocsdk.fragment.BaseFragment.AnonymousClass2 */

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothSettingChangeListener
            public void onAdapterStateChanged(int i, int i1) {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothSettingChangeListener
            public void onEnableChanged(boolean b) {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothSettingChangeListener
            public void onConnectedChanged(String s, int i) {
                String str = BaseFragment.TAG;
                Log.e(str, "s : " + s + "  i  " + i);
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothSettingChangeListener
            public void onHfpStateChanged(String s, int i) {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothSettingChangeListener
            public void onHfpAudioStateChanged(String s, int i, int i1) {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothSettingChangeListener
            public void onAvrcpStateChanged(String s, int i) {
                String str = BaseFragment.TAG;
                Log.e(str, "onAvrcpStateChanged : " + s + "   i" + i);
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothSettingChangeListener
            public void onA2dpStateChanged(String s, int i) {
                String str = BaseFragment.TAG;
                Log.e(str, "onA2dpStateChanged : " + s + "   i" + i);
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothSettingChangeListener
            public void onAdapterDiscoveryStarted() {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothSettingChangeListener
            public void onAdapterDiscoveryFinished() {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothSettingChangeListener
            public void retPairedDevices(int i, String[] strings, String[] strings1, int[] ints, byte[] cats) {
                Log.e("pairlist", "i : " + i + "   strings " + strings.length);
                for (int k = 0; k < strings.length; k++) {
                    Log.e("pairlist", "pairlist : " + strings[k] + "   ints " + ints[k] + " cats  " + ((int) cats[k]));
                    if (cats[k] == 4) {
                        Log.e("pairlist", "pairlist: " + strings[k]);
                        GocAppData.getInstance().mAddrCod.add(strings[k]);
                    }
                }
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothSettingChangeListener
            public void onDeviceFound(String s, String s1) {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothSettingChangeListener
            public void onDeviceBondStateChanged(String s, String s1, int i) {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothSettingChangeListener
            public void onLocalAdapterNameChanged(String s) {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothSettingChangeListener
            public void onPairStateChanged(String s, String s1, int i, int i1) {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothSettingChangeListener
            public void onMainDevicesChanged(String s, String s1) {
                String str = BaseFragment.TAG;
                Log.e(str, "onMainDevicesChanged : " + s + "   s1" + s1);
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothSettingChangeListener
            public void onAutoConnect(int i) {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothSettingChangeListener
            public void onAutoAnwer(int i) {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothSettingChangeListener
            public void onConnectDevice(String s, String s1) {
                String str = BaseFragment.TAG;
                Log.e(str, "onMainDevicesChanged : " + s + "   s1" + s1);
            }
        });
    }
}

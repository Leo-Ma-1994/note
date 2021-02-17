package com.goodocom.gocsdk.manager;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothA2dpSink;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAvrcp;
import android.bluetooth.BluetoothAvrcpController;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadsetClient;
import android.bluetooth.BluetoothPbapClient;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;
import com.goodocom.gocsdk.GocAppData;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

public class BluetoothConnectManager {
    private static BluetoothConnectManager mInstance;
    private BluetoothAdapter adapter;
    boolean connected = true;
    boolean connecting = false;
    private Context ctx;
    private BluetoothA2dp mBluetoothA2dp;
    private BluetoothA2dpSink mBluetoothA2dpSink;
    private BluetoothAvrcp mBluetoothAvrcp;
    private BluetoothAvrcpController mBluetoothAvrcpController;
    BluetoothDevice mBluetoothDevice;
    public BluetoothHeadsetClient mBluetoothHeadsetClient;
    private BluetoothPbapClient mBluetoothPbapClient;
    BluetoothSocket mBluetoothSocket;
    String macAddress = "";
    public int scoet = 0;

    public static BluetoothConnectManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new BluetoothConnectManager(context);
        }
        return mInstance;
    }

    private BluetoothConnectManager(Context ctx2) {
        this.ctx = ctx2;
        this.adapter = BluetoothAdapter.getDefaultAdapter();
    }

    public void connect(BluetoothDevice device, final String uuids) throws IOException {
        this.mBluetoothDevice = device;
        if (this.adapter == null) {
            this.adapter = BluetoothAdapter.getDefaultAdapter();
        }
        if (device.getBondState() == 10) {
            device.createBond();
        }
        if (this.adapter.isDiscovering()) {
            this.adapter.cancelDiscovery();
        }
        GocThreadPoolFactory.getInstance().executeRequest(new Runnable() {
            /* class com.goodocom.gocsdk.manager.BluetoothConnectManager.AnonymousClass1 */

            @Override // java.lang.Runnable
            public void run() {
                BluetoothConnectManager.this.connectDevice(uuids);
            }
        });
    }

    public synchronized void cancel() {
        boolean z = false;
        try {
            this.mBluetoothSocket.close();
            this.mBluetoothSocket = null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.connecting = z;
        }
        this.mBluetoothDevice = null;
    }

    /* access modifiers changed from: protected */
    public synchronized void connectDevice(String deviceId) {
        if (this.mBluetoothDevice != null) {
            Log.e("connect", "uuid>>>>" + deviceId);
            UUID uuid = UUID.fromString(deviceId);
            try {
                this.mBluetoothSocket = this.mBluetoothDevice.createRfcommSocketToServiceRecord(uuid);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("connect>>>>> ");
                sb.append(this.mBluetoothSocket);
                sb.append("   ");
                sb.append(uuid);
                sb.append("    ");
                sb.append(!this.mBluetoothSocket.isConnected());
                Log.e("connect", sb.toString());
                if (!this.mBluetoothSocket.isConnected()) {
                    this.mBluetoothSocket.connect();
                    this.connected = true;
                }
                Log.e("connect", "finally");
            } catch (Exception e2) {
                e2.printStackTrace();
                cancel();
                Log.e("connect", "finally");
            } catch (Throwable th) {
                Log.e("connect", "finally");
                this.connecting = false;
                throw th;
            }
            this.connecting = false;
        }
    }

    public void disconnectHfpProfile(final BluetoothDevice info) {
        Log.e("connect", "disconnectHfpProfile: " + info);
        Log.e("connect", "mBluetoothHeadsetClient : " + this.mBluetoothHeadsetClient);
        BluetoothHeadsetClient bluetoothHeadsetClient = this.mBluetoothHeadsetClient;
        if (bluetoothHeadsetClient != null) {
            bluetoothHeadsetClient.disconnect(info);
        } else {
            BluetoothAdapter.getDefaultAdapter().getProfileProxy(this.ctx, new BluetoothProfile.ServiceListener() {
                /* class com.goodocom.gocsdk.manager.BluetoothConnectManager.AnonymousClass2 */

                @Override // android.bluetooth.BluetoothProfile.ServiceListener
                public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
                    BluetoothConnectManager.this.mBluetoothHeadsetClient = (BluetoothHeadsetClient) bluetoothProfile;
                    Log.e("connect", "onServiceConnected>>>>>>>>i " + i + "     bluetoothProfile: " + bluetoothProfile);
                    BluetoothConnectManager.this.mBluetoothHeadsetClient.disconnect(info);
                }

                @Override // android.bluetooth.BluetoothProfile.ServiceListener
                public void onServiceDisconnected(int i) {
                }
            }, 16);
        }
    }

    public void disconnectA2dpSource(final BluetoothDevice device) {
        if (this.mBluetoothA2dp != null) {
            try {
                BluetoothA2dp.class.getMethod("disconnect", BluetoothDevice.class).invoke(this.mBluetoothA2dp, device);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e2) {
                e2.printStackTrace();
            } catch (NoSuchMethodException e3) {
                e3.printStackTrace();
            }
        } else {
            BluetoothAdapter.getDefaultAdapter().getProfileProxy(this.ctx, new BluetoothProfile.ServiceListener() {
                /* class com.goodocom.gocsdk.manager.BluetoothConnectManager.AnonymousClass3 */

                @Override // android.bluetooth.BluetoothProfile.ServiceListener
                public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
                    BluetoothConnectManager.this.mBluetoothA2dp = (BluetoothA2dp) bluetoothProfile;
                    Log.e("connect", "onServiceConnected>>>>>>>>i " + i + "     bluetoothProfile: " + bluetoothProfile);
                    try {
                        BluetoothA2dp.class.getMethod("disconnect", BluetoothDevice.class).invoke(BluetoothConnectManager.this.mBluetoothA2dp, device);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e2) {
                        e2.printStackTrace();
                    } catch (NoSuchMethodException e3) {
                        e3.printStackTrace();
                    }
                }

                @Override // android.bluetooth.BluetoothProfile.ServiceListener
                public void onServiceDisconnected(int i) {
                }
            }, 2);
        }
    }

    public void disconnectA2dpProfile(final BluetoothDevice info) {
        Log.e("connect", "disconnectA2dpProfile: " + info + "   mBluetoothA2dpSink : " + this.mBluetoothA2dpSink);
        BluetoothA2dpSink bluetoothA2dpSink = this.mBluetoothA2dpSink;
        if (bluetoothA2dpSink != null) {
            bluetoothA2dpSink.disconnect(info);
        } else {
            BluetoothAdapter.getDefaultAdapter().getProfileProxy(this.ctx, new BluetoothProfile.ServiceListener() {
                /* class com.goodocom.gocsdk.manager.BluetoothConnectManager.AnonymousClass4 */

                @Override // android.bluetooth.BluetoothProfile.ServiceListener
                public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
                    BluetoothConnectManager.this.mBluetoothA2dpSink = (BluetoothA2dpSink) bluetoothProfile;
                    Log.e("connect", "onServiceConnected>>>>>>>>i " + i + "     bluetoothProfile: " + bluetoothProfile);
                    BluetoothConnectManager.this.mBluetoothA2dpSink.disconnect(info);
                }

                @Override // android.bluetooth.BluetoothProfile.ServiceListener
                public void onServiceDisconnected(int i) {
                }
            }, 11);
        }
    }

    public void connect(BluetoothDevice info) {
        Log.e("connect", " info             >>s   " + info.getType());
        int len_uid = info.getUuids().length;
        boolean issourceToConnect = false;
        for (int i = 0; i < len_uid; i++) {
            Log.e("connect", "uuid " + info.getUuids()[i].getUuid().toString());
        }
        Log.e("connect", "info.getAddress(): " + info.getAddress() + "GocAppData.getInstance().mAddrs " + GocAppData.getInstance().mAddrCod.size());
        if (GocAppData.getInstance().mAddrCod.contains(info.getAddress())) {
            issourceToConnect = true;
        }
        Log.e("connect", "issourceToConnect == " + issourceToConnect);
        if (issourceToConnect) {
            connectA2dpSource(info);
            Log.e("connect", "to connectA2dpSource    ");
            return;
        }
        connectHfp(info);
        Log.e("connect", "to connectA2dp    ");
        connectA2dp(info);
        Log.e("connect", "mBluetoothHeadsetClient : " + this.mBluetoothHeadsetClient);
    }

    public void connectA2dpSource(final BluetoothDevice device) {
        Log.e("connect", "connectA2dpSource :: " + device.getAddress());
        if (this.mBluetoothA2dp != null) {
            GocThreadPoolFactory.getInstance().executeRequest(new Runnable() {
                /* class com.goodocom.gocsdk.manager.BluetoothConnectManager.AnonymousClass5 */

                @Override // java.lang.Runnable
                public void run() {
                    try {
                        BluetoothA2dp.class.getMethod("connect", BluetoothDevice.class).invoke(BluetoothConnectManager.this.mBluetoothA2dp, device);
                        Log.e("connect", "BluetoothA2dp -----connect");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e2) {
                        e2.printStackTrace();
                    } catch (NoSuchMethodException e3) {
                        e3.printStackTrace();
                    }
                }
            });
        } else {
            BluetoothAdapter.getDefaultAdapter().getProfileProxy(this.ctx, new BluetoothProfile.ServiceListener() {
                /* class com.goodocom.gocsdk.manager.BluetoothConnectManager.AnonymousClass6 */

                @Override // android.bluetooth.BluetoothProfile.ServiceListener
                public void onServiceConnected(final int i, final BluetoothProfile bluetoothProfile) {
                    if (bluetoothProfile instanceof BluetoothA2dp) {
                        BluetoothConnectManager.this.mBluetoothA2dp = (BluetoothA2dp) bluetoothProfile;
                        GocThreadPoolFactory.getInstance().executeRequest(new Runnable() {
                            /* class com.goodocom.gocsdk.manager.BluetoothConnectManager.AnonymousClass6.AnonymousClass1 */

                            @Override // java.lang.Runnable
                            public void run() {
                                try {
                                    BluetoothA2dp.class.getMethod("connect", BluetoothDevice.class).invoke(BluetoothConnectManager.this.mBluetoothA2dp, device);
                                    Log.e("connect", "BluetoothA2dp -----connect");
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e2) {
                                    e2.printStackTrace();
                                } catch (NoSuchMethodException e3) {
                                    e3.printStackTrace();
                                }
                                Log.e("connect", "a2dpsource onServiceConnected>>>>>>>>i " + i + "     bluetoothProfile: " + bluetoothProfile + "   connectSuccess: ");
                            }
                        });
                    }
                }

                @Override // android.bluetooth.BluetoothProfile.ServiceListener
                public void onServiceDisconnected(int i) {
                }
            }, 2);
        }
    }

    public void connectHfp(final BluetoothDevice device) {
        Log.e("connect ", "toconnect" + device.getAddress());
        if (this.mBluetoothHeadsetClient != null) {
            Log.e("connect ", "mBluetoothHeadsetClient != null");
            GocThreadPoolFactory.getInstance().executeRequest(new Runnable() {
                /* class com.goodocom.gocsdk.manager.BluetoothConnectManager.AnonymousClass7 */

                @Override // java.lang.Runnable
                public void run() {
                    Log.e("connect ", "mBluetoothHeadsetClient != null to connect hfp");
                    boolean connectSuccess = BluetoothConnectManager.this.mBluetoothHeadsetClient.connect(device);
                    Log.e("connect", "connectSuccess" + connectSuccess);
                }
            });
            return;
        }
        Log.e("connect ", "mBluetoothHeadsetClient to init ");
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(this.ctx, new BluetoothProfile.ServiceListener() {
            /* class com.goodocom.gocsdk.manager.BluetoothConnectManager.AnonymousClass8 */

            @Override // android.bluetooth.BluetoothProfile.ServiceListener
            public void onServiceConnected(final int i, final BluetoothProfile bluetoothProfile) {
                Log.e("connect ", "bluetoothProfile  " + bluetoothProfile.toString());
                if (bluetoothProfile instanceof BluetoothHeadsetClient) {
                    BluetoothConnectManager.this.mBluetoothHeadsetClient = (BluetoothHeadsetClient) bluetoothProfile;
                    GocThreadPoolFactory.getInstance().executeRequest(new Runnable() {
                        /* class com.goodocom.gocsdk.manager.BluetoothConnectManager.AnonymousClass8.AnonymousClass1 */

                        @Override // java.lang.Runnable
                        public void run() {
                            boolean connectSuccess = BluetoothConnectManager.this.mBluetoothHeadsetClient.connect(device);
                            Log.e("connect", "onServiceConnected>>>>>>>>i " + i + "     bluetoothProfile: " + bluetoothProfile + "   connectSuccess: " + connectSuccess);
                            StringBuilder sb = new StringBuilder();
                            sb.append("mBluetoothA2dpSink : ");
                            sb.append(BluetoothConnectManager.this.mBluetoothHeadsetClient);
                            Log.e("connect", sb.toString());
                        }
                    });
                    return;
                }
                Log.e("connect ", "bluetoothProfile  " + bluetoothProfile.toString());
            }

            @Override // android.bluetooth.BluetoothProfile.ServiceListener
            public void onServiceDisconnected(int i) {
                Log.e("connect ", "onServiceDisconnected ");
            }
        }, 16);
    }

    public void initHeadsetClient() {
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(this.ctx, new BluetoothProfile.ServiceListener() {
            /* class com.goodocom.gocsdk.manager.BluetoothConnectManager.AnonymousClass9 */

            @Override // android.bluetooth.BluetoothProfile.ServiceListener
            public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
                Log.e("connect ", "bluetoothProfile  " + bluetoothProfile.toString());
                if (bluetoothProfile instanceof BluetoothHeadsetClient) {
                    BluetoothConnectManager.this.mBluetoothHeadsetClient = (BluetoothHeadsetClient) bluetoothProfile;
                }
            }

            @Override // android.bluetooth.BluetoothProfile.ServiceListener
            public void onServiceDisconnected(int i) {
                Log.e("connect ", "onServiceDisconnected ");
            }
        }, 16);
    }

    public void connectA2dp(final BluetoothDevice device) {
        Log.e("connect ", "to connect mBluetoothA2dpSink  " + device.getAddress());
        if (this.mBluetoothA2dpSink == null) {
            Log.e("connect ", "mBluetoothA2dpSink to init   ");
            BluetoothAdapter.getDefaultAdapter().getProfileProxy(this.ctx, new BluetoothProfile.ServiceListener() {
                /* class com.goodocom.gocsdk.manager.BluetoothConnectManager.AnonymousClass10 */

                @Override // android.bluetooth.BluetoothProfile.ServiceListener
                public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
                    if (bluetoothProfile instanceof BluetoothA2dpSink) {
                        BluetoothConnectManager.this.mBluetoothA2dpSink = (BluetoothA2dpSink) bluetoothProfile;
                        GocThreadPoolFactory.getInstance().executeRequest(new Runnable() {
                            /* class com.goodocom.gocsdk.manager.BluetoothConnectManager.AnonymousClass10.AnonymousClass1 */

                            @Override // java.lang.Runnable
                            public void run() {
                                BluetoothConnectManager.this.mBluetoothA2dpSink.setPriority(device, 100);
                                BluetoothConnectManager.this.mBluetoothA2dpSink.connect(device);
                            }
                        });
                        return;
                    }
                    Log.e("connect ", " mBluetoothA2dpSink bluetoothProfile    " + bluetoothProfile.toString());
                }

                @Override // android.bluetooth.BluetoothProfile.ServiceListener
                public void onServiceDisconnected(int i) {
                }
            }, 11);
            return;
        }
        GocThreadPoolFactory.getInstance().executeRequest(new Runnable() {
            /* class com.goodocom.gocsdk.manager.BluetoothConnectManager.AnonymousClass11 */

            @Override // java.lang.Runnable
            public void run() {
                Log.e("connect", "to connect mBluetoothA2dpSink ");
                BluetoothConnectManager.this.mBluetoothA2dpSink.setPriority(device, 100);
                boolean connectSuccess = BluetoothConnectManager.this.mBluetoothA2dpSink.connect(device);
                Log.e("connect", "connectSuccess >>>>>>>>>>>>>>>>" + connectSuccess + "   device " + device + "  mBluetoothA2dpSink " + BluetoothConnectManager.this.mBluetoothA2dpSink);
            }
        });
    }

    public BluetoothAvrcpController getAvrcpControl(final BluetoothDevice device) {
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(this.ctx, new BluetoothProfile.ServiceListener() {
            /* class com.goodocom.gocsdk.manager.BluetoothConnectManager.AnonymousClass12 */

            @Override // android.bluetooth.BluetoothProfile.ServiceListener
            public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
                BluetoothConnectManager.this.mBluetoothAvrcpController = (BluetoothAvrcpController) bluetoothProfile;
                int avrcp = BluetoothConnectManager.this.mBluetoothAvrcpController.getConnectionState(device);
                Log.e("avrcp", "mBluetoothAvrcpController: " + BluetoothConnectManager.this.mBluetoothAvrcpController + "    " + avrcp);
            }

            @Override // android.bluetooth.BluetoothProfile.ServiceListener
            public void onServiceDisconnected(int i) {
            }
        }, 12);
        return this.mBluetoothAvrcpController;
    }

    public void connectPbapToLoadContacts(final BluetoothDevice info) {
        Log.e("pbap", "mBluetoothPbapClient : " + this.mBluetoothPbapClient);
        if (this.mBluetoothPbapClient == null) {
            BluetoothAdapter.getDefaultAdapter().getProfileProxy(this.ctx, new BluetoothProfile.ServiceListener() {
                /* class com.goodocom.gocsdk.manager.BluetoothConnectManager.AnonymousClass13 */

                @Override // android.bluetooth.BluetoothProfile.ServiceListener
                public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
                    Log.e("pbap", "onServiceConnected>>>>>>>>>>" + bluetoothProfile.getConnectedDevices().size());
                    BluetoothConnectManager.this.mBluetoothPbapClient = (BluetoothPbapClient) bluetoothProfile;
                    GocThreadPoolFactory.getInstance().executeRequest(new Runnable() {
                        /* class com.goodocom.gocsdk.manager.BluetoothConnectManager.AnonymousClass13.AnonymousClass1 */

                        @Override // java.lang.Runnable
                        public void run() {
                            BluetoothConnectManager.this.mBluetoothPbapClient.setPriority(info, 100);
                            boolean connectSuccess = BluetoothConnectManager.this.mBluetoothPbapClient.connect(info);
                            Log.e("pbap", "----------connectSuccess-----" + connectSuccess + "  mBluetoothPbapClient  " + BluetoothConnectManager.this.mBluetoothPbapClient);
                        }
                    });
                }

                @Override // android.bluetooth.BluetoothProfile.ServiceListener
                public void onServiceDisconnected(int i) {
                    Log.e("pbap", "onServiceDisconnected : " + i);
                }
            }, 17);
        } else {
            GocThreadPoolFactory.getInstance().executeRequest(new Runnable() {
                /* class com.goodocom.gocsdk.manager.BluetoothConnectManager.AnonymousClass14 */

                @Override // java.lang.Runnable
                public void run() {
                    BluetoothConnectManager.this.mBluetoothPbapClient.setPriority(info, 100);
                    boolean connectSuccess = BluetoothConnectManager.this.mBluetoothPbapClient.connect(info);
                    Log.e("pbap", "connectSuccess : " + connectSuccess);
                }
            });
        }
    }

    public void disconnectPbap(final BluetoothDevice info) {
        BluetoothPbapClient bluetoothPbapClient = this.mBluetoothPbapClient;
        if (bluetoothPbapClient != null) {
            bluetoothPbapClient.disconnect(info);
        } else {
            BluetoothAdapter.getDefaultAdapter().getProfileProxy(this.ctx, new BluetoothProfile.ServiceListener() {
                /* class com.goodocom.gocsdk.manager.BluetoothConnectManager.AnonymousClass15 */

                @Override // android.bluetooth.BluetoothProfile.ServiceListener
                public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
                    Log.e("pbap", "onServiceConnected>>>>>>>>>>" + bluetoothProfile.getConnectedDevices().size());
                    BluetoothConnectManager.this.mBluetoothPbapClient = (BluetoothPbapClient) bluetoothProfile;
                    BluetoothConnectManager.this.mBluetoothPbapClient.disconnect(info);
                }

                @Override // android.bluetooth.BluetoothProfile.ServiceListener
                public void onServiceDisconnected(int i) {
                }
            }, 17);
        }
    }

    public boolean isA2dpPlaying() {
        Log.e("avrcp", "mBluetoothA2dpSink : " + this.mBluetoothA2dpSink + "    mBluetoothDevice: " + GocAppData.getInstance().mCurrentBluetoothDevice);
        BluetoothA2dpSink bluetoothA2dpSink = this.mBluetoothA2dpSink;
        if (bluetoothA2dpSink == null) {
            return false;
        }
        return bluetoothA2dpSink.isA2dpPlaying(GocAppData.getInstance().mCurrentBluetoothDevice);
    }

    public void connectAvrcp(BluetoothDevice info) {
        if (info != null) {
            BluetoothAdapter.getDefaultAdapter().getProfileProxy(this.ctx, new BluetoothProfile.ServiceListener() {
                /* class com.goodocom.gocsdk.manager.BluetoothConnectManager.AnonymousClass16 */

                @Override // android.bluetooth.BluetoothProfile.ServiceListener
                public void onServiceConnected(int profile, BluetoothProfile proxy) {
                }

                @Override // android.bluetooth.BluetoothProfile.ServiceListener
                public void onServiceDisconnected(int profile) {
                }
            }, 12);
        }
    }

    private boolean connectA2dpAndHeadSet(Class btClass, BluetoothProfile bluetoothProfile, BluetoothDevice device) {
        setPriority(device, 100);
        try {
            Method connectMethod = btClass.getMethod("connect", BluetoothDevice.class);
            connectMethod.setAccessible(true);
            connectMethod.invoke(bluetoothProfile, device);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean disConnectA2dpAndHeadSet(Class btClass, BluetoothProfile bluetoothProfile, BluetoothDevice device) {
        setPriority(device, 0);
        try {
            Method connectMethod = btClass.getMethod("disconnect", BluetoothDevice.class);
            connectMethod.setAccessible(true);
            connectMethod.invoke(bluetoothProfile, device);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean connectPbapClient(Class btClass, BluetoothPbapClient bluetoothProfile, BluetoothDevice device) {
        setPriority(device, 0);
        try {
            Method connectMethod = btClass.getMethod("connect", BluetoothDevice.class);
            connectMethod.setAccessible(true);
            connectMethod.invoke(bluetoothProfile, device);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void setPriority(BluetoothDevice device, int priority) {
        if (this.mBluetoothA2dp != null) {
            try {
                Method connectMethod = BluetoothA2dp.class.getMethod("setPriority", BluetoothDevice.class, Integer.TYPE);
                connectMethod.setAccessible(true);
                connectMethod.invoke(this.mBluetoothA2dp, device, Integer.valueOf(priority));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

package com.goodocom.gocsdk.manager;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.goodocom.gocsdk.GocApplication;
import java.lang.reflect.Method;

public class BluetoothManager {
    private static final String BLUE_TOOTH_PROFILE_FIELD = "HEADSET_CLIENT";
    private static final String CONNECT = "connect";
    private static final String DIAL_CLASS_NAME = "android.bluetooth.BluetoothHeadsetClient";
    private static final String DIAL_METHOD_NAME = "dial";
    private static final String HOLD_CALL = "holdCall";
    private static final String REJECT_CALL = "rejectCall";
    private static final String TEEMINATE_CALL = "terminateCall";
    private static BluetoothManager instance;
    private Context mContext;
    public OnHfpConnectListener mOnHfpConnectListener;
    private OnCallResultListener onCallResultListener;

    public interface OnCallResultListener {
        void onBluetoothIsClosed();

        void onDeviceIsEmpty();

        void onError(String str);

        void onPhoneIsInValid();
    }

    public interface OnHfpConnectListener {
        void onFail();

        void onSuccess();
    }

    public boolean getBluetoothConnectState() {
        int hfpstate = BluetoothAdapter.getDefaultAdapter().getProfileConnectionState(16);
        int a2dpstate = BluetoothAdapter.getDefaultAdapter().getProfileConnectionState(11);
        Log.e(CONNECT, "getBluetoothConnectDevice: hfpstate " + hfpstate + "   " + a2dpstate);
        if (hfpstate == 2 || a2dpstate == 2) {
            return true;
        }
        return false;
    }

    public void setOnHfpConnectListener(OnHfpConnectListener listener, Context context) {
        this.mOnHfpConnectListener = listener;
        this.mContext = context;
    }

    public static BluetoothManager getInstance() {
        if (instance == null) {
            instance = new BluetoothManager();
        }
        return instance;
    }

    public void getConnectStatus(BluetoothProfile.ServiceListener serviceListener) {
        try {
            BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
            if (adapter.isEnabled() || this.onCallResultListener == null) {
                int bluetooth_Profile_HEADSET_CLIENT = ((Integer) BluetoothProfile.class.getField(BLUE_TOOTH_PROFILE_FIELD).get(null)).intValue();
                Log.e(DIAL_METHOD_NAME, "bluetooth_Profile_HEADSET_CLIENT : " + bluetooth_Profile_HEADSET_CLIENT);
                int isConnected = adapter.getProfileConnectionState(bluetooth_Profile_HEADSET_CLIENT);
                Log.e(DIAL_METHOD_NAME, "isConnected  " + isConnected + "  bluetooth_Profile_HEADSET_CLIENT  " + bluetooth_Profile_HEADSET_CLIENT);
                if (isConnected != 0 || this.onCallResultListener == null) {
                    Log.e(DIAL_METHOD_NAME, "isConnected  " + isConnected);
                    adapter.getProfileProxy(GocApplication.INSTANCE, serviceListener, bluetooth_Profile_HEADSET_CLIENT);
                    return;
                }
                this.onCallResultListener.onDeviceIsEmpty();
                return;
            }
            this.onCallResultListener.onBluetoothIsClosed();
        } catch (Exception e) {
            Log.e(DIAL_METHOD_NAME, "e.getMessage() : " + e.getMessage());
            OnCallResultListener onCallResultListener2 = this.onCallResultListener;
            if (onCallResultListener2 != null) {
                onCallResultListener2.onError(e.getMessage());
            }
        }
    }

    private void dial(BluetoothProfile proxy, BluetoothDevice bluetoothDevice, String number) {
        try {
            Log.e(DIAL_METHOD_NAME, "proxy :" + proxy + "   bluetoothDevice  " + bluetoothDevice + "  number " + number);
            if (proxy != null && bluetoothDevice != null) {
                if (!TextUtils.isEmpty(number)) {
                    Method methodMain = Class.forName(DIAL_CLASS_NAME).getMethod(DIAL_METHOD_NAME, BluetoothDevice.class, String.class);
                    if (methodMain != null) {
                        methodMain.invoke(proxy, bluetoothDevice, number);
                    }
                }
            }
        } catch (Exception e) {
            Log.e(DIAL_METHOD_NAME, "e.getMessage()                 : " + e.getMessage());
            OnCallResultListener onCallResultListener2 = this.onCallResultListener;
            if (onCallResultListener2 != null) {
                onCallResultListener2.onError(e.getMessage());
            }
        }
    }

    public void connenctHfp(BluetoothDevice device) {
        try {
            Class BluetoothHeadsetClient = Class.forName(DIAL_CLASS_NAME);
            Method methodMain = BluetoothHeadsetClient.getMethod(CONNECT, BluetoothDevice.class);
            for (int i = 0; i < BluetoothHeadsetClient.getMethods().length; i++) {
                Log.e(CONNECT, "methodMain: " + BluetoothHeadsetClient.getMethods()[i]);
            }
            if (methodMain != null) {
                methodMain.invoke(device, new Object[0]);
                this.mOnHfpConnectListener.onSuccess();
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.mOnHfpConnectListener.onFail();
        }
    }

    private void terminateCall(BluetoothProfile proxy, BluetoothDevice bluetoothDevice) {
        if (proxy != null && bluetoothDevice != null) {
            try {
                Method methodMain = Class.forName(DIAL_CLASS_NAME).getMethod(TEEMINATE_CALL, BluetoothDevice.class, String.class);
                if (methodMain != null) {
                    methodMain.invoke(proxy, bluetoothDevice);
                }
            } catch (Exception e) {
                Log.e(DIAL_METHOD_NAME, "e.getMessage() :   sss" + e.getMessage());
                OnCallResultListener onCallResultListener2 = this.onCallResultListener;
                if (onCallResultListener2 != null) {
                    onCallResultListener2.onError(e.getMessage());
                }
            }
        }
    }
}

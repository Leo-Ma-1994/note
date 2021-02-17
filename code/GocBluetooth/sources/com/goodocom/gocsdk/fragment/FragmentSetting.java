package com.goodocom.gocsdk.fragment;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.goodocom.gocsdk.R;
import com.goodocom.gocsdk.activity.MainActivity;
import com.goodocom.gocsdk.fragment.BaseFragment;
import com.goodocom.gocsdk.view.MyEditText;

public class FragmentSetting extends BaseFragment implements View.OnClickListener, BaseFragment.BluetoothSettingsInfoListener {
    private MainActivity activity;
    private ImageView auto_answer_switch;
    private ImageView auto_connect_switch;
    private MyEditText et_device_name;
    private MyEditText et_pin_code;
    private ImageView mbluetooth_image_switch;
    private TextView mbluetooth_switch;
    private TextView tv_imei;

    @Override // com.goodocom.gocsdk.fragment.BaseFragment
    public void onConnected(BluetoothDevice device) {
        Log.e("connect", "FragmentSettings: onConnected");
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment
    public void onDisconnected() {
        Log.e("connect", "FragmentSettings: onDisconnected");
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment, android.support.v4.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = (MainActivity) getActivity();
    }

    @Override // android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(this.activity, R.layout.fragmentsettings, null);
        initView(view);
        return view;
    }

    private void initData() {
        BluetoothAdapter.getDefaultAdapter();
    }

    private View initView(View view) {
        this.mbluetooth_switch = (TextView) view.findViewById(R.id.bluetooth_switch);
        this.mbluetooth_image_switch = (ImageView) view.findViewById(R.id.bluetooth_image_switch);
        this.et_device_name = (MyEditText) view.findViewById(R.id.et_device_name);
        this.et_pin_code = (MyEditText) view.findViewById(R.id.et_pin_code);
        this.auto_connect_switch = (ImageView) view.findViewById(R.id.auto_connect_switch);
        this.auto_answer_switch = (ImageView) view.findViewById(R.id.auto_answer_switch);
        this.tv_imei = (TextView) view.findViewById(R.id.tv_imei);
        setImei();
        if (BluetoothAdapter.getDefaultAdapter().isEnabled()) {
            this.mbluetooth_image_switch.setImageResource(R.drawable.ico_4157_kai);
        } else {
            this.mbluetooth_image_switch.setImageResource(R.drawable.ico_4158_guan);
        }
        this.mbluetooth_image_switch.setOnClickListener(this);
        this.auto_answer_switch.setOnClickListener(this);
        this.auto_connect_switch.setOnClickListener(this);
        if (!TextUtils.isEmpty(MainActivity.mLocalName)) {
            this.et_device_name.setText(MainActivity.mLocalName);
        } else {
            this.et_device_name.setText("hello");
        }
        if (!TextUtils.isEmpty(MainActivity.mPinCode)) {
            this.et_pin_code.setText(MainActivity.mPinCode);
        } else {
            this.et_pin_code.setText("0000");
        }
        setBluetoothName();
        this.et_device_name.setOnFinishComposingListener(new MyEditText.onFinishComposingListener() {
            /* class com.goodocom.gocsdk.fragment.FragmentSetting.AnonymousClass1 */

            @Override // com.goodocom.gocsdk.view.MyEditText.onFinishComposingListener
            public void finishComposing() {
                String deviceName = FragmentSetting.this.et_device_name.getText().toString();
                if (FragmentSetting.this.mBaseHander != null) {
                    Message msg = Message.obtain();
                    msg.what = 27;
                    msg.obj = deviceName;
                    FragmentSetting.this.mBaseHander.sendMessage(msg);
                }
            }
        });
        this.et_pin_code.setOnFinishComposingListener(new MyEditText.onFinishComposingListener() {
            /* class com.goodocom.gocsdk.fragment.FragmentSetting.AnonymousClass2 */

            @Override // com.goodocom.gocsdk.view.MyEditText.onFinishComposingListener
            public void finishComposing() {
                String pinCode = FragmentSetting.this.et_pin_code.getText().toString();
                if (FragmentSetting.this.mBaseHander != null) {
                    Message msg = Message.obtain();
                    msg.what = 28;
                    msg.obj = pinCode;
                    FragmentSetting.this.mBaseHander.sendMessage(msg);
                }
            }
        });
        return view;
    }

    private void setImei() {
        String imei;
        try {
            TelephonyManager telephonyManager = (TelephonyManager) this.activity.getSystemService("phone");
            if (telephonyManager != null && (imei = telephonyManager.getDeviceId()) != null) {
                this.tv_imei.setText(imei);
            }
        } catch (SecurityException e) {
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.auto_answer_switch /* 2131165230 */:
                isAnswerSwitch();
                return;
            case R.id.auto_connect_switch /* 2131165231 */:
                isConnectSwitch();
                return;
            default:
                return;
        }
    }

    private void swithBluetooth() {
        Log.e("blue", "blue>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + BluetoothAdapter.getDefaultAdapter().isEnabled());
        if (BluetoothAdapter.getDefaultAdapter().isEnabled()) {
            Log.e("blue", "blue>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>disable()");
            BluetoothAdapter.getDefaultAdapter().disable();
            return;
        }
        Log.e("blue", "blue>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>enable()");
        BluetoothAdapter.getDefaultAdapter().enable();
    }

    private void isAnswerSwitch() {
    }

    private void isConnectSwitch() {
    }

    public void setBluetoothName() {
        String name = BluetoothAdapter.getDefaultAdapter().getName();
        if (!TextUtils.isEmpty(name)) {
            this.et_device_name.setText(name);
        }
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment.BluetoothSettingsInfoListener
    public void onName(Message message) {
        this.et_device_name.setText(BluetoothAdapter.getDefaultAdapter().getName());
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment.BluetoothSettingsInfoListener
    public void onPincode(Message message) {
        this.et_pin_code.setText((String) message.obj);
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment.BluetoothSettingsInfoListener
    public void onAutoConnectStateChange(String state) {
    }
}

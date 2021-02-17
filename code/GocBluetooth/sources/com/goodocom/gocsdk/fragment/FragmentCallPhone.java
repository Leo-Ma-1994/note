package com.goodocom.gocsdk.fragment;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.goodocom.bttek.bt.base.jar.GocJar;
import com.goodocom.gocsdk.R;
import com.goodocom.gocsdk.activity.MainActivity;
import com.goodocom.gocsdk.manager.BluetoothManager;

public class FragmentCallPhone extends BaseFragment implements View.OnClickListener, BluetoothManager.OnCallResultListener {
    public static final String TAG = FragmentCallPhone.class.getSimpleName();
    private MainActivity activity;
    private ImageView iv_callout;
    private ImageView iv_delete;
    private ImageView iv_eight;
    private ImageView iv_five;
    private ImageView iv_four;
    private ImageView iv_jinghao;
    private ImageView iv_nine;
    private ImageView iv_one;
    private ImageView iv_seven;
    private ImageView iv_six;
    private ImageView iv_three;
    private ImageView iv_two;
    private ImageView iv_xinghao;
    private ImageView iv_zero;
    private StringBuffer sb;
    private TextView tv_phonenumber;

    @Override // com.goodocom.gocsdk.fragment.BaseFragment
    public void onConnected(BluetoothDevice device) {
        Log.e("connect", "FragmentCallPhone: onConnected");
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment
    public void onDisconnected() {
        Log.e("connect", "FragmentCallPhone: onDisconnected");
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment, android.support.v4.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = (MainActivity) getActivity();
        this.sb = new StringBuffer();
    }

    @Override // android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(this.activity, R.layout.fragmentcallphone, null);
        this.iv_one = (ImageView) view.findViewById(R.id.iv_one);
        this.iv_two = (ImageView) view.findViewById(R.id.iv_two);
        this.iv_three = (ImageView) view.findViewById(R.id.iv_three);
        this.iv_four = (ImageView) view.findViewById(R.id.iv_four);
        this.iv_five = (ImageView) view.findViewById(R.id.iv_five);
        this.iv_six = (ImageView) view.findViewById(R.id.iv_six);
        this.iv_seven = (ImageView) view.findViewById(R.id.iv_seven);
        this.iv_eight = (ImageView) view.findViewById(R.id.iv_eight);
        this.iv_nine = (ImageView) view.findViewById(R.id.iv_nine);
        this.iv_xinghao = (ImageView) view.findViewById(R.id.iv_xinghao);
        this.iv_zero = (ImageView) view.findViewById(R.id.iv_zero);
        this.iv_jinghao = (ImageView) view.findViewById(R.id.iv_jinghao);
        this.iv_callout = (ImageView) view.findViewById(R.id.iv_callout);
        this.iv_delete = (ImageView) view.findViewById(R.id.iv_delete);
        this.tv_phonenumber = (TextView) view.findViewById(R.id.tv_phonenumber);
        this.iv_one.setOnClickListener(this);
        this.iv_two.setOnClickListener(this);
        this.iv_three.setOnClickListener(this);
        this.iv_four.setOnClickListener(this);
        this.iv_five.setOnClickListener(this);
        this.iv_six.setOnClickListener(this);
        this.iv_seven.setOnClickListener(this);
        this.iv_eight.setOnClickListener(this);
        this.iv_nine.setOnClickListener(this);
        this.iv_xinghao.setOnClickListener(this);
        this.iv_zero.setOnClickListener(this);
        this.iv_jinghao.setOnClickListener(this);
        this.iv_callout.setOnClickListener(this);
        this.iv_delete.setOnClickListener(this);
        return view;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_callout /* 2131165353 */:
                placeCall(this.tv_phonenumber.getText().toString().trim());
                break;
            case R.id.iv_delete /* 2131165356 */:
                if (this.sb.length() > 0) {
                    StringBuffer stringBuffer = this.sb;
                    stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                    break;
                }
                break;
            case R.id.iv_eight /* 2131165357 */:
                this.sb.append("8");
                break;
            case R.id.iv_five /* 2131165358 */:
                this.sb.append("5");
                break;
            case R.id.iv_four /* 2131165360 */:
                this.sb.append("4");
                break;
            case R.id.iv_jinghao /* 2131165364 */:
                this.sb.append("#");
                break;
            case R.id.iv_nine /* 2131165368 */:
                this.sb.append("9");
                break;
            case R.id.iv_one /* 2131165370 */:
                this.sb.append("1");
                break;
            case R.id.iv_seven /* 2131165383 */:
                this.sb.append("7");
                break;
            case R.id.iv_six /* 2131165384 */:
                this.sb.append("6");
                break;
            case R.id.iv_three /* 2131165385 */:
                this.sb.append("3");
                break;
            case R.id.iv_two /* 2131165386 */:
                this.sb.append("2");
                break;
            case R.id.iv_xinghao /* 2131165389 */:
                this.sb.append("*");
                break;
            case R.id.iv_zero /* 2131165390 */:
                this.sb.append("0");
                break;
        }
        this.tv_phonenumber.setText(this.sb.toString());
    }

    private void placeCall(String number) {
        Log.e(NotificationCompat.CATEGORY_CALL, "startCall");
        try {
            GocJar.requestHfpDialCall(number);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Message message = MainActivity.getHandler().obtainMessage();
        message.obj = number;
        message.what = 5;
        MainActivity.getHandler().sendMessage(message);
    }

    @Override // com.goodocom.gocsdk.manager.BluetoothManager.OnCallResultListener
    public void onBluetoothIsClosed() {
        Toast.makeText(this.activity, "bluetooth is close", 0).show();
    }

    @Override // com.goodocom.gocsdk.manager.BluetoothManager.OnCallResultListener
    public void onDeviceIsEmpty() {
        Toast.makeText(this.activity, "please connect device", 0).show();
    }

    @Override // com.goodocom.gocsdk.manager.BluetoothManager.OnCallResultListener
    public void onPhoneIsInValid() {
    }

    @Override // com.goodocom.gocsdk.manager.BluetoothManager.OnCallResultListener
    public void onError(String errorMsg) {
        Toast.makeText(this.activity, errorMsg, 0).show();
    }
}

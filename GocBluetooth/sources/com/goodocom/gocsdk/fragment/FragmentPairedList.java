package com.goodocom.gocsdk.fragment;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.goodocom.bttek.bt.base.jar.GocJar;
import com.goodocom.bttek.bt.base.listener.GocBluetoothOtherFunChangeListener;
import com.goodocom.bttek.bt.base.listener.GocBluetoothServiceConnectedListener;
import com.goodocom.gocsdk.GocAppData;
import com.goodocom.gocsdk.GocApplication;
import com.goodocom.gocsdk.R;
import com.goodocom.gocsdk.activity.MainActivity;
import com.goodocom.gocsdk.manager.BluetoothManager;
import com.goodocom.gocsdk.service.GocsdkCallbackImp;
import com.goodocom.gocsdk.view.TransparentDialog;
import java.lang.reflect.Method;

public class FragmentPairedList extends BaseFragment implements BluetoothManager.OnHfpConnectListener {
    private static final String TAG = FragmentPairedList.class.getSimpleName();
    public static boolean inited = false;
    private MainActivity activity;
    private TransparentDialog dialog;
    private ListView lv_paired_list;
    private DeviceAdapterGoc mDeviceAdapterGoc;
    private GocAppData mGocAppData;

    @Override // android.support.v4.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(GocsdkCallbackImp.TAG, "onAttach");
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment, android.support.v4.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mGocAppData = GocAppData.getInstance();
        this.activity = (MainActivity) getActivity();
        inited = true;
        Log.e(GocsdkCallbackImp.TAG, "onCreate");
        registBluetooth();
    }

    @Override // android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(GocsdkCallbackImp.TAG, "onCreateView");
        View view = View.inflate(this.activity, R.layout.fragmentbluetoothlist, null);
        this.lv_paired_list = (ListView) view.findViewById(R.id.lv_paired_list);
        this.lv_paired_list.setSelector(R.drawable.contact_list_item_selector);
        this.mDeviceAdapterGoc = new DeviceAdapterGoc();
        this.lv_paired_list.setAdapter((ListAdapter) this.mDeviceAdapterGoc);
        BluetoothManager.getInstance().setOnHfpConnectListener(this, this.activity);
        initData();
        this.lv_paired_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /* class com.goodocom.gocsdk.fragment.FragmentPairedList.AnonymousClass1 */

            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                BluetoothDevice bluetoothDevice = FragmentPairedList.this.mDeviceAdapterGoc.getItem(position);
                if (GocAppData.getInstance().mConnectedMap.contains(bluetoothDevice.getAddress())) {
                    Log.e("connect", "to disconnect remote device");
                    FragmentPairedList.this.createDisContentBtDialog(bluetoothDevice);
                    return;
                }
                Log.e("connect", "to connect remote device");
                FragmentPairedList.this.createContentBtDialog(bluetoothDevice);
            }
        });
        boolean hfpinit = BluetoothAdapter.getDefaultAdapter().getProfileProxy(GocApplication.INSTANCE, this, 16);
        boolean a2dpinit = BluetoothAdapter.getDefaultAdapter().getProfileProxy(GocApplication.INSTANCE, this, 11);
        boolean pbapinit = BluetoothAdapter.getDefaultAdapter().getProfileProxy(GocApplication.INSTANCE, this, 17);
        boolean avrcpinit = BluetoothAdapter.getDefaultAdapter().getProfileProxy(GocApplication.INSTANCE, this, 12);
        Log.e("connect", "a2dpinit " + a2dpinit);
        Log.e("connect", "hfpinit " + hfpinit);
        Log.e("connect", "pbapinit " + pbapinit);
        Log.e("connect", "avrcpinit " + avrcpinit);
        return view;
    }

    @Override // android.support.v4.app.Fragment
    public void onResume() {
        super.onResume();
        Log.e(GocsdkCallbackImp.TAG, "onResume");
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment, android.support.v4.app.Fragment
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e(GocsdkCallbackImp.TAG, "onHiddenChanged  " + hidden);
        if (!hidden) {
            BluetoothAdapter.getDefaultAdapter().getProfileProxy(GocApplication.INSTANCE, this, 16);
            BluetoothAdapter.getDefaultAdapter().getProfileProxy(GocApplication.INSTANCE, this, 11);
        }
    }

    public void PairedChange() {
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(GocApplication.INSTANCE, this, 16);
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(GocApplication.INSTANCE, this, 11);
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(GocApplication.INSTANCE, this, 17);
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        inited = false;
        Log.e(GocsdkCallbackImp.TAG, "onDestroyView");
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment, android.support.v4.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        Log.e(GocsdkCallbackImp.TAG, "onDestroy");
    }

    /* access modifiers changed from: protected */
    public void showPopupWindow() {
        this.dialog = new TransparentDialog(this.activity, R.style.transparentdialog);
        this.dialog.setCanceledOnTouchOutside(false);
        this.dialog.show();
        hanlder.postDelayed(new Runnable() {
            /* class com.goodocom.gocsdk.fragment.FragmentPairedList.AnonymousClass2 */

            @Override // java.lang.Runnable
            public void run() {
                if (FragmentPairedList.this.dialog.isShowing()) {
                    FragmentPairedList.this.dialog.dismiss();
                    FragmentPairedList.this.dialog.cancel();
                    Toast.makeText(FragmentPairedList.this.activity, "connect timeout ", 0).show();
                }
            }
        }, 13000);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void createContentBtDialog(final BluetoothDevice info) {
        MainActivity.currentDeviceName = info.getName();
        AlertDialog.Builder builder = new AlertDialog.Builder(this.activity);
        builder.setMessage("确定要连接该设备吗?" + info.getName() + ":" + info.getAddress());
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            /* class com.goodocom.gocsdk.fragment.FragmentPairedList.AnonymousClass3 */

            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.dismiss();
                FragmentPairedList.this.showPopupWindow();
                FragmentPairedList.this.activity.mBluetoothConnectManager.connect(info);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            /* class com.goodocom.gocsdk.fragment.FragmentPairedList.AnonymousClass4 */

            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void createDisContentBtDialog(final BluetoothDevice info) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.activity);
        builder.setMessage("确定要断开该设备吗?" + info.getName() + ":" + info.getAddress());
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            /* class com.goodocom.gocsdk.fragment.FragmentPairedList.AnonymousClass5 */

            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.dismiss();
                FragmentPairedList.this.activity.mBluetoothConnectManager.disconnectHfpProfile(info);
                FragmentPairedList.this.activity.mBluetoothConnectManager.disconnectA2dpProfile(info);
                FragmentPairedList.this.activity.mBluetoothConnectManager.disconnectPbap(info);
                FragmentPairedList.this.activity.mBluetoothConnectManager.disconnectA2dpSource(info);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            /* class com.goodocom.gocsdk.fragment.FragmentPairedList.AnonymousClass6 */

            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void initData() {
        this.mGocAppData.mParedList1.clear();
        for (BluetoothDevice devices : BluetoothAdapter.getDefaultAdapter().getBondedDevices()) {
            this.mGocAppData.mParedList1.add(devices);
        }
        String str = TAG;
        Log.e(str, "mGocAppData.mParedList1： " + this.mGocAppData.mParedList1.size());
        this.mBaseHander.post(new Runnable() {
            /* class com.goodocom.gocsdk.fragment.FragmentPairedList.AnonymousClass7 */

            @Override // java.lang.Runnable
            public void run() {
                FragmentPairedList.this.mDeviceAdapterGoc.notifyDataSetChanged();
            }
        });
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment
    public void onConnected(BluetoothDevice device) {
        connected();
        Log.e("connect", "FragmentPairList: onConnected");
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment
    public void onDisconnected() {
        initData();
        Log.e("connect", "FragmentPairList: onDisconnected");
        this.lv_paired_list.invalidate();
    }

    public void onPaired() {
        initData();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void unpairDevice(BluetoothDevice device) {
        try {
            Method m = device.getClass().getMethod("removeBond", null);
            m.setAccessible(true);
            m.invoke(device, null);
        } catch (Exception e) {
            Log.e("ble", e.toString());
        }
    }

    @Override // com.goodocom.gocsdk.manager.BluetoothManager.OnHfpConnectListener
    public void onSuccess() {
        Toast.makeText(this.activity, "conneted success", 0).show();
    }

    @Override // com.goodocom.gocsdk.manager.BluetoothManager.OnHfpConnectListener
    public void onFail() {
        Toast.makeText(this.activity, "conneted fail", 0).show();
    }

    /* access modifiers changed from: private */
    public class DeviceAdapterGoc extends BaseAdapter {
        private DeviceAdapterGoc() {
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return FragmentPairedList.this.mGocAppData.mParedList1.size();
        }

        @Override // android.widget.Adapter
        public BluetoothDevice getItem(int position) {
            return FragmentPairedList.this.mGocAppData.mParedList1.get(position);
        }

        @Override // android.widget.Adapter
        public long getItemId(int position) {
            return (long) position;
        }

        @Override // android.widget.Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            String name;
            if (convertView == null) {
                convertView = View.inflate(FragmentPairedList.this.activity, R.layout.btpair_list_item_layout, null);
                holder = new ViewHolder();
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_name = (TextView) convertView.findViewById(R.id.bt_name);
            holder.iv_remove = (ImageView) convertView.findViewById(R.id.iv_remove);
            holder.tv_isconnect = (TextView) convertView.findViewById(R.id.tv_isconnect);
            holder.tv_address = (TextView) convertView.findViewById(R.id.bt_address_goc);
            final BluetoothDevice blueToothInfo = FragmentPairedList.this.mGocAppData.mParedList1.get(position);
            if (TextUtils.isEmpty(blueToothInfo.getName())) {
                name = "该设备无名称";
            } else {
                name = blueToothInfo.getName();
            }
            Log.e(GocsdkCallbackImp.TAG, "blueToothInfo.address: " + blueToothInfo.getAddress() + "    blueToothInfo.isConnected:" + GocAppData.getInstance().mConnectedMap.size());
            if (TextUtils.isEmpty(blueToothInfo.getAddress()) || !GocAppData.getInstance().mConnectedMap.contains(blueToothInfo.getAddress())) {
                holder.tv_isconnect.setText("连接断开");
            } else {
                holder.tv_isconnect.setText("已经连接");
            }
            holder.tv_address.setText(blueToothInfo.getAddress());
            holder.tv_name.setText(name);
            holder.iv_remove.setOnClickListener(new View.OnClickListener() {
                /* class com.goodocom.gocsdk.fragment.FragmentPairedList.DeviceAdapterGoc.AnonymousClass1 */

                @Override // android.view.View.OnClickListener
                public void onClick(View v) {
                    FragmentPairedList.this.unpairDevice(blueToothInfo);
                    for (int i = 0; i < FragmentPairedList.this.mGocAppData.mParedList1.size(); i++) {
                        if (FragmentPairedList.this.mGocAppData.mParedList1.get(i).getAddress().equals(blueToothInfo.getAddress())) {
                            FragmentPairedList.this.mGocAppData.mParedList1.remove(blueToothInfo);
                            try {
                                GocJar.requestBtPairedDevices(0);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    DeviceAdapterGoc.this.notifyDataSetChanged();
                }
            });
            return convertView;
        }
    }

    private static class ViewHolder {
        public ImageView iv_remove;
        public TextView tv_address;
        public TextView tv_isconnect;
        public TextView tv_name;

        private ViewHolder() {
        }
    }

    public static Handler getHandler() {
        return hanlder;
    }

    public void connected() {
        TransparentDialog transparentDialog = this.dialog;
        if (transparentDialog != null) {
            transparentDialog.dismiss();
        }
        initData();
        this.mDeviceAdapterGoc.notifyDataSetChanged();
    }

    public void registBluetooth() {
        GocJar.registerBluetoothServiceConnectedListener(new GocBluetoothServiceConnectedListener() {
            /* class com.goodocom.gocsdk.fragment.FragmentPairedList.AnonymousClass8 */

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothServiceConnectedListener
            public void onServiceConnectedChanged(boolean b) {
                String str = FragmentPairedList.TAG;
                Log.e(str, "onServiceConnectedChanged : " + b);
            }
        });
    }

    public void registOther() {
        GocJar.registerBluetoothOtherFunChangeListener(new GocBluetoothOtherFunChangeListener() {
            /* class com.goodocom.gocsdk.fragment.FragmentPairedList.AnonymousClass9 */

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothOtherFunChangeListener
            public void onHfpRemoteBatteryIndicator(String s, int i, int i1, int i2) {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothOtherFunChangeListener
            public void onHfpRemoteSignalStrength(String s, int i, int i1, int i2) {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothOtherFunChangeListener
            public void onHfpMissedCall(String s, int i) {
            }
        });
    }
}

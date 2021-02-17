package com.goodocom.gocsdk.fragment;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.goodocom.gocsdk.GocAppData;
import com.goodocom.gocsdk.GocApplication;
import com.goodocom.gocsdk.R;
import com.goodocom.gocsdk.activity.MainActivity;
import com.goodocom.gocsdk.domain.BlueToothInfo;
import com.goodocom.gocsdk.fragment.BaseFragment;
import com.goodocom.gocsdk.manager.BtReceiver;
import com.goodocom.gocsdk.service.GocsdkCallbackImp;
import com.goodocom.gocsdk.view.TransparentDialog;
import java.util.ArrayList;
import java.util.List;

public class FragmentSearch extends BaseFragment implements View.OnClickListener, BtReceiver.Listener, BaseFragment.SearchBluetoothListener {
    private MainActivity activity;
    private MyAdapter adapter;
    private Button btn_device_name;
    private Button btn_pin_code;
    private List<BlueToothInfo> bts = new ArrayList();
    private TransparentDialog dialog;
    private boolean isSearch = false;
    private ImageView iv_anim;
    private ImageView iv_search_bt;
    private LinearLayout ll_bt_left;
    private LinearLayout ll_connected;
    private ListView lv_device_list;
    private BtReceiver mBtReceiver;
    private GocAppData mGocAppData;
    private MyAdapterGoc myAdapterGoc;
    private List<BluetoothDevice> searchBts = new ArrayList();
    private TextView tv_content_name;
    private TextView tv_content_name2;

    @Override // android.support.v4.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment
    public void onConnected(BluetoothDevice device) {
        Log.e("connect", "FragmentSearch: onConnected");
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment
    public void onDisconnected() {
        Log.e("connect", "FragmentSearch: onDisconnected");
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment, android.support.v4.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mGocAppData = GocAppData.getInstance();
        this.activity = (MainActivity) getActivity();
        this.mBtReceiver = new BtReceiver(GocApplication.INSTANCE, this, this);
        setSearchBluetoothListener(this);
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment, android.support.v4.app.Fragment
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(this.activity, R.layout.fragmentbluetoothinfo, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        this.ll_bt_left = (LinearLayout) view.findViewById(R.id.ll_bt_left);
        this.iv_search_bt = (ImageView) view.findViewById(R.id.iv_search_bt);
        this.lv_device_list = (ListView) view.findViewById(R.id.lv_device_list);
        this.lv_device_list.setSelector(R.drawable.contact_list_item_selector);
        this.btn_device_name = (Button) view.findViewById(R.id.btn_device_name);
        this.btn_pin_code = (Button) view.findViewById(R.id.btn_pin_code);
        this.iv_anim = (ImageView) view.findViewById(R.id.iv_anim);
        this.ll_connected = (LinearLayout) view.findViewById(R.id.ll_connected);
        this.tv_content_name = (TextView) view.findViewById(R.id.tv_content_name);
        this.tv_content_name2 = (TextView) view.findViewById(R.id.tv_content_name2);
        if (GocsdkCallbackImp.hfpStatus >= 3) {
            showConnected();
        } else {
            showSearch();
        }
        if (!TextUtils.isEmpty(MainActivity.mLocalName)) {
            this.btn_device_name.setText(MainActivity.mLocalName);
        } else {
            this.btn_device_name.setText("hello");
        }
        if (!TextUtils.isEmpty(MainActivity.mPinCode)) {
            this.btn_pin_code.setText(MainActivity.mPinCode);
        } else {
            this.btn_pin_code.setText("0000");
        }
        this.iv_search_bt.setOnClickListener(this);
        this.btn_device_name.setText(BluetoothAdapter.getDefaultAdapter().getName());
        this.myAdapterGoc = new MyAdapterGoc();
        this.lv_device_list.setAdapter((ListAdapter) this.myAdapterGoc);
        this.lv_device_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /* class com.goodocom.gocsdk.fragment.FragmentSearch.AnonymousClass1 */

            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) FragmentSearch.this.searchBts.get(position);
                Log.e("connect", "bluetoothDevice : " + bluetoothDevice.getBondState());
                if (bluetoothDevice.getBondState() == 10) {
                    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (bluetoothAdapter.isDiscovering()) {
                        bluetoothAdapter.cancelDiscovery();
                        Toast.makeText(FragmentSearch.this.activity, "搜索完成", 0).show();
                        FragmentSearch.this.iv_anim.setVisibility(8);
                        FragmentSearch.this.iv_anim.clearAnimation();
                        FragmentSearch.this.isSearch = false;
                    }
                    bluetoothDevice.createBond();
                }
            }
        });
    }

    private void connectDevice(int position) {
    }

    private void showSearch() {
        this.ll_connected.setVisibility(4);
        this.ll_bt_left.setVisibility(0);
        this.tv_content_name.setText("");
    }

    private void showConnected() {
        this.ll_connected.setVisibility(0);
        this.ll_bt_left.setVisibility(0);
        this.mBaseHander.postDelayed(new Runnable() {
            /* class com.goodocom.gocsdk.fragment.FragmentSearch.AnonymousClass2 */

            @Override // java.lang.Runnable
            public void run() {
                if (FragmentSearch.this.mGocAppData.mConnectList.size() == 1) {
                    FragmentSearch.this.tv_content_name.setText(FragmentSearch.this.mGocAppData.mConnectList.get(0).address);
                    FragmentSearch.this.tv_content_name2.setText("");
                } else if (FragmentSearch.this.mGocAppData.mConnectList.size() == 2) {
                    FragmentSearch.this.tv_content_name.setText(FragmentSearch.this.mGocAppData.mConnectList.get(0).address);
                    FragmentSearch.this.tv_content_name2.setText(FragmentSearch.this.mGocAppData.mConnectList.get(1).address);
                }
            }
        }, 200);
    }

    /* access modifiers changed from: protected */
    public void showPopupWindow() {
        this.dialog = new TransparentDialog(this.activity, R.style.transparentdialog);
        this.dialog.setCanceledOnTouchOutside(false);
        this.dialog.show();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        if (v.getId() == R.id.iv_search_bt) {
            Log.e("search", "isSearch : " + this.isSearch);
            if (this.isSearch) {
                this.iv_anim.setVisibility(8);
                this.iv_anim.clearAnimation();
                BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (bluetoothAdapter.isDiscovering()) {
                    bluetoothAdapter.cancelDiscovery();
                    return;
                }
                return;
            }
            this.iv_anim.setVisibility(0);
            RotateAnimation ra = new RotateAnimation(0.0f, 359.0f, 1, 0.5f, 1, 0.5f);
            ra.setRepeatCount(-1);
            ra.setRepeatMode(1);
            ra.setDuration(1000);
            ra.setInterpolator(new LinearInterpolator());
            this.iv_anim.startAnimation(ra);
            this.searchBts.clear();
            this.myAdapterGoc.notifyDataSetChanged();
            BluetoothAdapter bluetoothAdapter2 = BluetoothAdapter.getDefaultAdapter();
            if (!bluetoothAdapter2.isDiscovering()) {
                bluetoothAdapter2.startDiscovery();
            }
        }
    }

    @Override // com.goodocom.gocsdk.manager.BtReceiver.Listener
    public void foundDev(BluetoothDevice dev) {
        Log.e("search", ">>>>>>>>>>>>>>>>>> " + dev);
        if (dev.getBondState() == 10) {
            this.searchBts.add(dev);
            this.myAdapterGoc.notifyDataSetChanged();
            this.isSearch = true;
        }
    }

    @Override // com.goodocom.gocsdk.manager.BtReceiver.Listener
    public void finishFound() {
        Toast.makeText(this.activity, "搜索完成", 0).show();
        this.iv_anim.setVisibility(8);
        this.iv_anim.clearAnimation();
        this.isSearch = false;
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment.SearchBluetoothListener
    public void onGocSearch(BlueToothInfo info) {
        this.bts.add(info);
        this.adapter.notifyDataSetChanged();
        this.isSearch = true;
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment.SearchBluetoothListener
    public void onSearch(BluetoothDevice device) {
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment.SearchBluetoothListener
    public void onSearchStart() {
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment.SearchBluetoothListener
    public void onSearchEnd() {
        Toast.makeText(this.activity, "搜索完成", 0).show();
        this.iv_anim.setVisibility(8);
        this.iv_anim.clearAnimation();
        this.isSearch = false;
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment.SearchBluetoothListener
    public void onName(Message message) {
        this.btn_device_name.setText(BluetoothAdapter.getDefaultAdapter().getName());
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment.SearchBluetoothListener
    public void onPin(Message message) {
        this.btn_pin_code.setText((String) message.obj);
    }

    private class MyAdapter extends BaseAdapter {
        private MyAdapter() {
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return FragmentSearch.this.bts.size();
        }

        @Override // android.widget.Adapter
        public BlueToothInfo getItem(int position) {
            return (BlueToothInfo) FragmentSearch.this.bts.get(position);
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
                convertView = View.inflate(FragmentSearch.this.activity, R.layout.bluetooth_listview_item_layout, null);
                holder = new ViewHolder();
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
            BlueToothInfo blueToothInfo = (BlueToothInfo) FragmentSearch.this.bts.get(position);
            if (TextUtils.isEmpty(blueToothInfo.name)) {
                name = "该设备无名称";
            } else {
                name = blueToothInfo.name;
            }
            holder.tv_name.setText(name);
            holder.tv_address.setText(blueToothInfo.address);
            return convertView;
        }
    }

    /* access modifiers changed from: private */
    public class MyAdapterGoc extends BaseAdapter {
        private MyAdapterGoc() {
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return FragmentSearch.this.searchBts.size();
        }

        @Override // android.widget.Adapter
        public BluetoothDevice getItem(int position) {
            return (BluetoothDevice) FragmentSearch.this.searchBts.get(position);
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
                convertView = View.inflate(FragmentSearch.this.activity, R.layout.bluetooth_listview_item_layout, null);
                holder = new ViewHolder();
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
            BluetoothDevice blueToothInfo = (BluetoothDevice) FragmentSearch.this.searchBts.get(position);
            if (TextUtils.isEmpty(blueToothInfo.getName())) {
                name = "该设备无名称";
            } else {
                name = blueToothInfo.getName();
            }
            holder.tv_name.setText(name);
            holder.tv_address.setText(blueToothInfo.getAddress());
            return convertView;
        }
    }

    private static class ViewHolder {
        public TextView tv_address;
        public TextView tv_name;

        private ViewHolder() {
        }
    }
}

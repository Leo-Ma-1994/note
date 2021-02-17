package com.goodocom.gocsdk.fragment;

import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CallLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.goodocom.gocsdk.GocAppData;
import com.goodocom.gocsdk.R;
import com.goodocom.gocsdk.activity.MainActivity;
import com.goodocom.gocsdk.adapter.CalllogAdapter;
import com.goodocom.gocsdk.db.GocDatabase;
import com.goodocom.gocsdk.domain.CallLogInfo;
import com.goodocom.gocsdk.fragment.BaseFragment;
import com.goodocom.gocsdk.manager.BluetoothManager;
import com.goodocom.gocsdk.service.GocsdkCallbackImp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FragmentCallog extends BaseFragment implements View.OnClickListener, BaseFragment.BluetoothCalllogListener {
    private static final int CALLLOG_IN = 3;
    private static final int CALLLOG_MISS = 2;
    private static final int CALLLOG_OUT = 1;
    private static final String TAG = FragmentCallog.class.getSimpleName();
    private MainActivity activity;
    private String[] callLogString = {"拨出", "打进", "未接"};
    private FrameLayout fl_content;
    private ImageView ib_call_in;
    private ImageView ib_call_missed;
    private ImageView ib_call_out;
    private ImageView image_animation;
    private LinearLayout ll_title;
    private CalllogDownloadListener mCalllogDownloadListener;
    private ListView mIncommingListView;
    private ListView mMissListView;
    private ListView mOutListView;
    private CalllogAdapter mSimpleAdapterIn;
    private CalllogAdapter mSimpleAdapterMiss;
    private CalllogAdapter mSimpleAdapterOut;
    private Map<String, String> map = null;
    private RelativeLayout rl_downloading;
    private TextView tv_device_disconnected;

    public interface CalllogDownloadListener {
        void onCallogDownload(int i);
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment, android.support.v4.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = (MainActivity) getActivity();
        setBluetoothCalllogListener(this);
    }

    public static Handler getHandler() {
        return hanlder;
    }

    @Override // android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentcalllog, (ViewGroup) null);
        initView(view);
        initEvents();
        Log.d("app", "GocsdkCallbackImp.hfpStatus=" + GocsdkCallbackImp.hfpStatus);
        BluetoothManager.getInstance().getConnectStatus(this);
        return view;
    }

    private void initEvents() {
        this.ib_call_in.setOnClickListener(this);
        this.ib_call_out.setOnClickListener(this);
        this.ib_call_missed.setOnClickListener(this);
    }

    public void showDisconnect() {
        this.ll_title.setVisibility(8);
        this.tv_device_disconnected.setVisibility(0);
        Log.e(TAG, "showDisconnect");
    }

    public void showConnect() {
        this.ll_title.setVisibility(0);
        this.tv_device_disconnected.setVisibility(8);
        String str = TAG;
        Log.e(str, "showConnect   GocAppData.getInstance().mComingCalllog.size() " + GocAppData.getInstance().mComingCalllog.size());
        showIncommingData();
    }

    private void initView(View view) {
        this.ll_title = (LinearLayout) view.findViewById(R.id.ll_title);
        this.tv_device_disconnected = (TextView) view.findViewById(R.id.tv_device_disconnect);
        this.ib_call_in = (ImageView) view.findViewById(R.id.ib_call_in);
        this.ib_call_out = (ImageView) view.findViewById(R.id.ib_call_out);
        this.ib_call_missed = (ImageView) view.findViewById(R.id.ib_call_missed);
        this.mIncommingListView = (ListView) view.findViewById(R.id.incoming_listview);
        this.mOutListView = (ListView) view.findViewById(R.id.out_listview);
        this.mMissListView = (ListView) view.findViewById(R.id.miss_listview);
        this.rl_downloading = (RelativeLayout) view.findViewById(R.id.rl_downloading);
        this.image_animation = (ImageView) view.findViewById(R.id.image_animation);
        this.mSimpleAdapterIn = new CalllogAdapter(GocAppData.getInstance().mComingCalllog, this.activity);
        this.mIncommingListView.setAdapter((ListAdapter) this.mSimpleAdapterIn);
        this.mSimpleAdapterOut = new CalllogAdapter(GocAppData.getInstance().mOutCalllog, this.activity);
        this.mOutListView.setAdapter((ListAdapter) this.mSimpleAdapterOut);
        this.mSimpleAdapterMiss = new CalllogAdapter(GocAppData.getInstance().mMissCalllog, this.activity);
        this.mMissListView.setAdapter((ListAdapter) this.mSimpleAdapterMiss);
        InitData();
    }

    private void startAnima() {
        TranslateAnimation animation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, 0.8f);
        animation.setDuration(1000);
        animation.setFillAfter(false);
        animation.setRepeatCount(-1);
        animation.setRepeatMode(1);
        this.image_animation.startAnimation(animation);
    }

    private void InitData() {
        LoadIncomingData();
    }

    public List<CallLogInfo> getCalllog(String otype) {
        Cursor cursor = getActivity().getContentResolver().query(CallLog.Calls.CONTENT_URI, null, "type=?", new String[]{otype}, null);
        List<CallLogInfo> callLogInfos = new ArrayList<>();
        while (cursor.moveToNext()) {
            String type = cursor.getString(cursor.getColumnIndex(GocDatabase.COL_TYPE));
            String number = cursor.getString(cursor.getColumnIndex(GocDatabase.COL_NUMBER));
            CallLogInfo callLogInfo = new CallLogInfo();
            Log.e("calllog", "callLogInfo: " + type + "   number : " + number);
            callLogInfo.number = number;
            if (otype.equals("1")) {
                callLogInfo.type = 4;
            } else if (otype.equals("2")) {
                callLogInfo.type = 5;
            } else if (otype.equals("3")) {
                callLogInfo.type = 6;
            }
            callLogInfos.add(callLogInfo);
        }
        cursor.close();
        return callLogInfos;
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment
    public void loadCalllog() {
        super.loadCalllog();
        showConnect();
    }

    private void showIncommingData() {
        this.ib_call_in.setBackgroundColor(1879048447);
        this.ib_call_out.setBackgroundColor(0);
        this.ib_call_missed.setBackgroundColor(0);
        this.mIncommingListView.setVisibility(0);
        this.mMissListView.setVisibility(8);
        this.mOutListView.setVisibility(8);
        GocAppData.getInstance().mComingCalllog.clear();
        GocAppData.getInstance().mComingCalllog.addAll(getCalllog("1"));
        String str = TAG;
        Log.e(str, "ib_call_in<<<<>>>>>" + GocAppData.getInstance().mComingCalllog.size());
        this.mSimpleAdapterIn.notifyDataSetChanged();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_call_in /* 2131165329 */:
                showIncommingData();
                return;
            case R.id.ib_call_missed /* 2131165330 */:
                this.ib_call_in.setBackgroundColor(0);
                this.ib_call_out.setBackgroundColor(0);
                this.ib_call_missed.setBackgroundColor(1879048447);
                this.mIncommingListView.setVisibility(8);
                this.mMissListView.setVisibility(0);
                this.mOutListView.setVisibility(8);
                GocAppData.getInstance().mMissCalllog.clear();
                GocAppData.getInstance().mMissCalllog.addAll(getCalllog("3"));
                String str = TAG;
                Log.e(str, "ib_call_missed<<<<>>>>>" + GocAppData.getInstance().mMissCalllog.size());
                this.mSimpleAdapterMiss.notifyDataSetChanged();
                return;
            case R.id.ib_call_out /* 2131165331 */:
                this.ib_call_in.setBackgroundColor(0);
                this.ib_call_out.setBackgroundColor(1879048447);
                this.ib_call_missed.setBackgroundColor(0);
                this.mIncommingListView.setVisibility(8);
                this.mMissListView.setVisibility(8);
                this.mOutListView.setVisibility(0);
                GocAppData.getInstance().mOutCalllog.clear();
                GocAppData.getInstance().mOutCalllog.addAll(getCalllog("2"));
                String str2 = TAG;
                Log.e(str2, "ib_call_out<<<<>>>>>" + GocAppData.getInstance().mOutCalllog.size());
                this.mSimpleAdapterOut.notifyDataSetChanged();
                return;
            default:
                return;
        }
    }

    private void LoadIncomingData() {
        GocAppData.getInstance().mComingCalllog.clear();
        GocAppData.getInstance().mComingCalllog.addAll(getCalllog("1"));
        this.mSimpleAdapterIn.notifyDataSetChanged();
        this.rl_downloading.setVisibility(8);
        this.mIncommingListView.setVisibility(0);
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment
    public void onConnected(BluetoothDevice device) {
        Log.e("connect", "FragmentCallog: onConnected" + device);
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment
    public void onDisconnected() {
        GocAppData.getInstance().mComingCalllog.clear();
        GocAppData.getInstance().mOutCalllog.clear();
        GocAppData.getInstance().mMissCalllog.clear();
        CalllogAdapter calllogAdapter = this.mSimpleAdapterIn;
        if (calllogAdapter != null) {
            calllogAdapter.notifyDataSetChanged();
        }
        CalllogAdapter calllogAdapter2 = this.mSimpleAdapterMiss;
        if (calllogAdapter2 != null) {
            calllogAdapter2.notifyDataSetChanged();
        }
        CalllogAdapter calllogAdapter3 = this.mSimpleAdapterOut;
        if (calllogAdapter3 != null) {
            calllogAdapter3.notifyDataSetChanged();
        }
        Log.e("connect", "FragmentCallog: onDisconnected");
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment.BluetoothCalllogListener
    public void onOutCalllog(CallLogInfo info) {
        GocAppData.getInstance().mOutCalllog.add(info);
        Log.e("calllog", "onOutCalllog: " + info);
        this.mSimpleAdapterOut.notifyDataSetChanged();
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment.BluetoothCalllogListener
    public void onMissCalllog(CallLogInfo info) {
        GocAppData.getInstance().mMissCalllog.add(info);
        this.mSimpleAdapterMiss.notifyDataSetChanged();
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment.BluetoothCalllogListener
    public void onComingCalllog(CallLogInfo info) {
        GocAppData.getInstance().mComingCalllog.add(info);
        this.mSimpleAdapterMiss.notifyDataSetChanged();
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment.BluetoothCalllogListener
    public void onCalllogStart() {
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment.BluetoothCalllogListener
    public void onCalllogdown() {
        this.rl_downloading.setVisibility(8);
        this.image_animation.clearAnimation();
        this.image_animation.setVisibility(8);
    }

    private void clickItemCall(CalllogAdapter mSimpleAdapter, int position) {
        if (mSimpleAdapter != null) {
            CallLogInfo info = mSimpleAdapter.getItem(position);
            createCallOutDialog(info.name, info.number);
        }
    }

    private void createCallOutDialog(String Name, String Num) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.activity);
        builder.setMessage("确定要拨打吗?" + Name + ":" + Num);
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            /* class com.goodocom.gocsdk.fragment.FragmentCallog.AnonymousClass1 */

            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            /* class com.goodocom.gocsdk.fragment.FragmentCallog.AnonymousClass2 */

            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void setCalllogDownloadListener(CalllogDownloadListener listener) {
        this.mCalllogDownloadListener = listener;
    }
}

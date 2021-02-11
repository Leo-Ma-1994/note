package com.goodocom.gocsdk.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.goodocom.bttek.bt.aidl.GocHfpClientCall;
import com.goodocom.bttek.bt.base.jar.GocJar;
import com.goodocom.bttek.bt.base.listener.GocBluetoothPhoneChangeListener;
import com.goodocom.gocsdk.BuildConfig;
import com.goodocom.gocsdk.GocAppData;
import com.goodocom.gocsdk.GocApplication;
import com.goodocom.gocsdk.R;
import com.goodocom.gocsdk.adapter.FragmentAdapter;
import com.goodocom.gocsdk.db.GocDatabase;
import com.goodocom.gocsdk.fragment.FragmentCallPhone;
import com.goodocom.gocsdk.fragment.FragmentCallog;
import com.goodocom.gocsdk.fragment.FragmentMusic;
import com.goodocom.gocsdk.fragment.FragmentPairedList;
import com.goodocom.gocsdk.fragment.FragmentPhonebookList;
import com.goodocom.gocsdk.fragment.FragmentSearch;
import com.goodocom.gocsdk.fragment.FragmentSetting;
import com.goodocom.gocsdk.key.HomeKey;
import com.goodocom.gocsdk.manager.BluetoothConnectManager;
import com.goodocom.gocsdk.manager.CallingStateListener;
import com.goodocom.gocsdk.originbt.OriginBluetoothService;
import com.goodocom.gocsdk.service.GocsdkCallbackImp;
import com.goodocom.gocsdk.view.ExtendedViewPager;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements GocsdkCallbackImp.GocCallBackListener, CallingStateListener.OnCallStateChangedListener, View.OnClickListener, OriginBluetoothService.MusicPlayListener {
    public static final int FIREST_POSITION = 2;
    public static final int MSG_COMING = 4;
    public static final int MSG_CURRENT_CONNECT_DEVICE_NAME = 29;
    public static final int MSG_DEVICENAME = 11;
    public static final int MSG_DEVICEPINCODE = 12;
    public static final int MSG_HANGUP = 7;
    public static final int MSG_OUTGING = 5;
    public static final int MSG_SET_MICPHONE_OFF = 20;
    public static final int MSG_SET_MICPHONE_ON = 19;
    public static final int MSG_TALKING = 6;
    public static final int MSG_UPDATE_BUTTON = 30;
    public static final int MSG_UPDATE_CALLLOG_DONE = 28;
    public static final int MSG_UPDATE_CALLOUT_CALLLOG = 26;
    public static final int MSG_UPDATE_INCOMING_CALLLOG = 25;
    public static final int MSG_UPDATE_MISSED_CALLLOG = 27;
    public static final int MSG_UPDATE_PHONEBOOK = 17;
    public static final int MSG_UPDATE_PHONEBOOK_DONE = 18;
    private static final String TAG = MainActivity.class.getSimpleName();
    public static String currentDeviceName = "";
    public static Handler hand = null;
    public static String mLocalName = null;
    public static String mPinCode = null;
    public Handler handler = new Handler() {
        /* class com.goodocom.gocsdk.activity.MainActivity.AnonymousClass5 */

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            int i = msg.what;
            if (i != 4) {
                if (i == 5) {
                    String numbers = (String) msg.obj;
                    String str = MainActivity.TAG;
                    Log.e(str, "MSG_OUTGING >>>>>" + IncomingActivity.running + "    " + CallActivity.running);
                    if (!IncomingActivity.running && !CallActivity.running) {
                        Log.e(MainActivity.TAG, "-------MSG_OUTGING------startCall-------");
                        ComponentName cn = new ComponentName(BuildConfig.APPLICATION_ID, "com.goodocom.gocsdk.activity.CallActivity");
                        Intent intent = new Intent();
                        intent.putExtra(GocDatabase.COL_NUMBER, numbers);
                        intent.setComponent(cn);
                        MainActivity.this.startActivity(intent);
                        Log.e(NotificationCompat.CATEGORY_CALL, "start call");
                    }
                } else if (i == 6) {
                    String str2 = (String) msg.obj;
                    String str3 = MainActivity.TAG;
                    Log.e(str3, "MSG_TALKING >>>>>" + IncomingActivity.running + "    " + CallActivity.running);
                } else if (i == 7) {
                } else {
                    if (i == 11) {
                        MainActivity.mLocalName = (String) msg.obj;
                    } else if (i == 12) {
                        MainActivity.mPinCode = (String) msg.obj;
                    } else if (i == 29) {
                        MainActivity.currentDeviceName = (String) msg.obj;
                    } else if (i == 30) {
                        MainActivity.this.updateButton();
                    }
                }
            } else if (!IncomingActivity.running && !CallActivity.running) {
                Intent intent2 = new Intent(MainActivity.this, IncomingActivity.class);
                intent2.putExtra(GocDatabase.COL_NUMBER, (String) msg.obj);
                MainActivity.this.startActivity(intent2);
            }
        }
    };
    private ImageView iv_callog;
    private ImageView iv_callphone;
    private ImageView iv_music;
    private ImageView iv_pair;
    private ImageView iv_phonebook;
    private ImageView iv_search;
    private ImageView iv_settings;
    private OriginBluetoothService.OriginBluetoothConnectStateListener listener = new OriginBluetoothService.OriginBluetoothConnectStateListener() {
        /* class com.goodocom.gocsdk.activity.MainActivity.AnonymousClass3 */

        @Override // com.goodocom.gocsdk.originbt.OriginBluetoothService.OriginBluetoothConnectStateListener
        public void OriginConnected(BluetoothDevice device, boolean isHF) {
            if (device != null) {
                GocAppData.getInstance().mConnectedMap.add(device.getAddress());
                if (MainActivity.this.mFragmentCallog != null) {
                    MainActivity.this.mFragmentCallog.onConnected(device);
                }
                if (MainActivity.this.mFragmentCallPhone != null) {
                    MainActivity.this.mFragmentCallPhone.onConnected(device);
                }
                if (MainActivity.this.mFragmentMusic != null) {
                    MainActivity.this.mFragmentMusic.onConnected(device);
                }
                if (MainActivity.this.mFragmentPhonebookList != null) {
                    MainActivity.this.mFragmentPhonebookList.onConnected(device);
                }
                if (MainActivity.this.mFragmentSearch != null) {
                    MainActivity.this.mFragmentSearch.onConnected(device);
                }
                if (MainActivity.this.mFragmentPairedList != null) {
                    MainActivity.this.mFragmentPairedList.onConnected(device);
                }
                if (MainActivity.this.mFragmentSetting != null) {
                    MainActivity.this.mFragmentSetting.onConnected(device);
                }
            }
        }

        @Override // com.goodocom.gocsdk.originbt.OriginBluetoothService.OriginBluetoothConnectStateListener
        public void OriginDisconnected(BluetoothDevice device, boolean isHF) {
            Log.e("connect", "OriginBluetoothConnectStateListener>>>>OriginDisconnected   ");
            if (isHF) {
                GocAppData.getInstance().mCurrentBluetoothDevice = null;
            } else if (!(device == null || GocAppData.getInstance().mCurrentBluetoothDevice == null || device.getAddress().equals(GocAppData.getInstance().mCurrentBluetoothDevice.getAddress()))) {
                GocAppData.getInstance().mOtherBluetoothDevice = null;
            }
            if (GocAppData.getInstance().mConnectedMap.contains(device.getAddress())) {
                GocAppData.getInstance().mConnectedMap.remove(device.getAddress());
            }
            if (MainActivity.this.mFragmentCallog != null) {
                MainActivity.this.mFragmentCallog.onDisconnected();
            }
            if (MainActivity.this.mFragmentCallPhone != null) {
                MainActivity.this.mFragmentCallPhone.onDisconnected();
            }
            if (MainActivity.this.mFragmentMusic != null) {
                MainActivity.this.mFragmentMusic.onDisconnected();
            }
            if (MainActivity.this.mFragmentPhonebookList != null) {
                MainActivity.this.mFragmentPhonebookList.onDisconnected();
            }
            if (MainActivity.this.mFragmentSearch != null) {
                MainActivity.this.mFragmentSearch.onDisconnected();
            }
            if (MainActivity.this.mFragmentPairedList != null) {
                MainActivity.this.mFragmentPairedList.onDisconnected();
            }
            if (MainActivity.this.mFragmentSetting != null) {
                MainActivity.this.mFragmentSetting.onDisconnected();
            }
        }

        @Override // com.goodocom.gocsdk.originbt.OriginBluetoothService.OriginBluetoothConnectStateListener
        public void OriginPaired(BluetoothDevice device) {
            if (MainActivity.this.mFragmentPairedList != null) {
                MainActivity.this.mFragmentPairedList.onPaired();
            }
        }
    };
    public BluetoothConnectManager mBluetoothConnectManager;
    private CallingStateListener mCallingStateListener;
    private FragmentCallPhone mFragmentCallPhone;
    private FragmentCallog mFragmentCallog;
    private FragmentMusic mFragmentMusic;
    private FragmentPairedList mFragmentPairedList;
    public FragmentPhonebookList mFragmentPhonebookList;
    private FragmentSearch mFragmentSearch;
    private FragmentSetting mFragmentSetting;
    public GocAppData mGocAppData;
    private HomeKey mHomeKey;
    protected OriginBluetoothService mOriginBluetoothService;
    private OriginBluetoothService.PbapDownloadFinishListener mPbapDownloadFinishListener = new OriginBluetoothService.PbapDownloadFinishListener() {
        /* class com.goodocom.gocsdk.activity.MainActivity.AnonymousClass2 */

        @Override // com.goodocom.gocsdk.originbt.OriginBluetoothService.PbapDownloadFinishListener
        public void onContactsLoadFinish() {
            if (MainActivity.this.mFragmentPhonebookList != null) {
                MainActivity.this.mFragmentPhonebookList.loadContacts();
            }
        }

        @Override // com.goodocom.gocsdk.originbt.OriginBluetoothService.PbapDownloadFinishListener
        public void onCalllogLoadFinish() {
            if (MainActivity.this.mFragmentCallog != null) {
                MainActivity.this.mFragmentCallog.loadCalllog();
            }
        }
    };
    private ExtendedViewPager mViewPager;

    public static Handler getHandler() {
        return hand;
    }

    /* access modifiers changed from: protected */
    @Override // android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mGocAppData = GocAppData.getInstance();
        setContentView(R.layout.activity_main);
        setHomeKey();
        request();
        bindOriginService();
        initView();
        hand = this.handler;
        this.mCallingStateListener = new CallingStateListener(this);
        this.mCallingStateListener.setOnCallStateChangedListener(this);
        this.mCallingStateListener.startListener();
        this.mBluetoothConnectManager = BluetoothConnectManager.getInstance(GocApplication.INSTANCE);
        registCallPhone();
    }

    private void bindOriginService() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(BuildConfig.APPLICATION_ID, "com.goodocom.gocsdk.originbt.OriginBluetoothService"));
        GocApplication.INSTANCE.bindService(intent, new ServiceConnection() {
            /* class com.goodocom.gocsdk.activity.MainActivity.AnonymousClass1 */

            @Override // android.content.ServiceConnection
            public /* synthetic */ void onBindingDied(ComponentName componentName) {
                ServiceConnection.-CC.$default$onBindingDied(this, componentName);
            }

            @Override // android.content.ServiceConnection
            public /* synthetic */ void onNullBinding(ComponentName componentName) {
                ServiceConnection.-CC.$default$onNullBinding(this, componentName);
            }

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                MainActivity.this.mOriginBluetoothService = ((OriginBluetoothService.OriginBinder) iBinder).getOriginService();
                Log.e(GocsdkCallbackImp.TAG, " onServiceConnected mOriginBluetoothService: " + MainActivity.this.mOriginBluetoothService);
                if (MainActivity.this.mOriginBluetoothService != null) {
                    MainActivity.this.mOriginBluetoothService.setA2dpMusicInfoListener(MainActivity.this.mFragmentMusic);
                    MainActivity.this.mOriginBluetoothService.setMusicPlayListener(MainActivity.this);
                    MainActivity.this.mOriginBluetoothService.setOriginBluetoothConnectStateListener(MainActivity.this.listener);
                    MainActivity.this.mOriginBluetoothService.setPbapDownloadFinishListener(MainActivity.this.mPbapDownloadFinishListener);
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
            }
        }, 1);
        sendBroadcast(new Intent("notify_bluetooth_bind_gocservice"));
    }

    /* access modifiers changed from: protected */
    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
    }

    /* access modifiers changed from: protected */
    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }

    /* access modifiers changed from: protected */
    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
    }

    /* access modifiers changed from: protected */
    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    public void onStop() {
        Log.d("app", "MainActivity onStop");
        super.onStop();
    }

    /* access modifiers changed from: protected */
    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        this.mHomeKey.stopWatch();
        Log.d("app", "MainActivity onDestroy");
        CallingStateListener callingStateListener = this.mCallingStateListener;
        if (callingStateListener != null) {
            callingStateListener.stopListener();
        }
        super.onDestroy();
    }

    @Override // com.goodocom.gocsdk.service.GocsdkCallbackImp.GocCallBackListener
    public void onAddressUpdate() {
        this.handler.sendEmptyMessage(30);
    }

    public void updateButton() {
        for (int i = 0; i < this.mGocAppData.mConnectList.size(); i++) {
            Log.e(GocsdkCallbackImp.TAG, "updateButton: " + this.mGocAppData.mConnectList.get(i).address + "size : " + this.mGocAppData.mConnectList.size());
        }
    }

    @Override // com.goodocom.gocsdk.manager.CallingStateListener.OnCallStateChangedListener
    public void onCallStateChanged(int state, String number) {
    }

    public void startIncommingCall(String number) {
        Intent intent = new Intent(this, IncomingActivity.class);
        intent.putExtra(GocDatabase.COL_NUMBER, number);
        startActivity(intent);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        Log.e("apps", "view>>>>>>>>");
        switch (view.getId()) {
            case R.id.iv_calllog /* 2131165351 */:
                setSelect(0);
                this.mViewPager.setCurrentItem(0);
                return;
            case R.id.iv_callphone /* 2131165354 */:
                setSelect(2);
                this.mViewPager.setCurrentItem(2);
                return;
            case R.id.iv_music /* 2131165365 */:
                setSelect(3);
                this.mViewPager.setCurrentItem(3);
                return;
            case R.id.iv_pair /* 2131165371 */:
                setSelect(5);
                this.mViewPager.setCurrentItem(5);
                return;
            case R.id.iv_phonebook /* 2131165375 */:
                setSelect(1);
                this.mViewPager.setCurrentItem(1);
                return;
            case R.id.iv_search /* 2131165380 */:
                setSelect(4);
                this.mViewPager.setCurrentItem(4);
                return;
            case R.id.iv_settings /* 2131165382 */:
                setSelect(6);
                this.mViewPager.setCurrentItem(6);
                return;
            default:
                return;
        }
    }

    public void setSelect(int i) {
        boolean isconnect = !GocAppData.getInstance().mConnectedMap.isEmpty();
        switch (i) {
            case 0:
                this.iv_callog.setSelected(true);
                this.iv_phonebook.setSelected(false);
                this.iv_callphone.setSelected(false);
                this.iv_music.setSelected(false);
                this.iv_search.setSelected(false);
                this.iv_pair.setSelected(false);
                this.iv_settings.setSelected(false);
                FragmentCallog fragmentCallog = this.mFragmentCallog;
                if (fragmentCallog == null) {
                    return;
                }
                if (isconnect) {
                    fragmentCallog.showConnect();
                    return;
                } else {
                    fragmentCallog.showDisconnect();
                    return;
                }
            case 1:
                this.iv_callog.setSelected(false);
                this.iv_phonebook.setSelected(true);
                this.iv_callphone.setSelected(false);
                this.iv_music.setSelected(false);
                this.iv_search.setSelected(false);
                this.iv_pair.setSelected(false);
                this.iv_settings.setSelected(false);
                FragmentPhonebookList fragmentPhonebookList = this.mFragmentPhonebookList;
                if (fragmentPhonebookList == null) {
                    return;
                }
                if (!isconnect) {
                    fragmentPhonebookList.showDisconnect();
                    return;
                } else if (fragmentPhonebookList.simpleAdapter == null || this.mFragmentPhonebookList.simpleAdapter.getCount() <= 0) {
                    this.mFragmentPhonebookList.showConnect();
                    return;
                } else {
                    Log.e(TAG, "mFragmentPhonebookList.simpleAdapter.getCount: " + this.mFragmentPhonebookList.simpleAdapter.getCount());
                    this.mFragmentPhonebookList.showData();
                    return;
                }
            case 2:
                this.iv_callog.setSelected(false);
                this.iv_phonebook.setSelected(false);
                this.iv_callphone.setSelected(true);
                this.iv_music.setSelected(false);
                this.iv_search.setSelected(false);
                this.iv_pair.setSelected(false);
                this.iv_settings.setSelected(false);
                return;
            case 3:
                this.iv_callog.setSelected(false);
                this.iv_phonebook.setSelected(false);
                this.iv_callphone.setSelected(false);
                this.iv_music.setSelected(true);
                this.iv_search.setSelected(false);
                this.iv_pair.setSelected(false);
                this.iv_settings.setSelected(false);
                return;
            case 4:
                this.iv_callog.setSelected(false);
                this.iv_phonebook.setSelected(false);
                this.iv_callphone.setSelected(false);
                this.iv_music.setSelected(false);
                this.iv_search.setSelected(true);
                this.iv_pair.setSelected(false);
                this.iv_settings.setSelected(false);
                return;
            case 5:
                this.iv_callog.setSelected(false);
                this.iv_phonebook.setSelected(false);
                this.iv_callphone.setSelected(false);
                this.iv_music.setSelected(false);
                this.iv_search.setSelected(false);
                this.iv_pair.setSelected(true);
                this.iv_settings.setSelected(false);
                FragmentPairedList fragmentPairedList = this.mFragmentPairedList;
                if (fragmentPairedList != null) {
                    fragmentPairedList.PairedChange();
                    this.mFragmentPairedList.initData();
                    return;
                }
                return;
            case 6:
                this.iv_callog.setSelected(false);
                this.iv_phonebook.setSelected(false);
                this.iv_callphone.setSelected(false);
                this.iv_music.setSelected(false);
                this.iv_search.setSelected(false);
                this.iv_pair.setSelected(false);
                this.iv_settings.setSelected(true);
                return;
            default:
                return;
        }
    }

    private void initView() {
        this.iv_callog = (ImageView) findViewById(R.id.iv_calllog);
        this.iv_phonebook = (ImageView) findViewById(R.id.iv_phonebook);
        this.iv_callphone = (ImageView) findViewById(R.id.iv_callphone);
        this.iv_music = (ImageView) findViewById(R.id.iv_music);
        this.iv_search = (ImageView) findViewById(R.id.iv_search);
        this.iv_pair = (ImageView) findViewById(R.id.iv_pair);
        this.iv_settings = (ImageView) findViewById(R.id.iv_settings);
        this.iv_callog.setOnClickListener(this);
        this.iv_phonebook.setOnClickListener(this);
        this.iv_callphone.setOnClickListener(this);
        this.iv_music.setOnClickListener(this);
        this.iv_search.setOnClickListener(this);
        this.iv_pair.setOnClickListener(this);
        this.iv_settings.setOnClickListener(this);
        this.iv_callog.setSelected(true);
        this.mViewPager = (ExtendedViewPager) findViewById(R.id.goc_view_pager);
        List<Fragment> fragmentList = new ArrayList<>(7);
        this.mFragmentCallog = new FragmentCallog();
        this.mFragmentPhonebookList = new FragmentPhonebookList();
        this.mFragmentCallPhone = new FragmentCallPhone();
        this.mFragmentMusic = new FragmentMusic();
        this.mFragmentSearch = new FragmentSearch();
        this.mFragmentPairedList = new FragmentPairedList();
        this.mFragmentSetting = new FragmentSetting();
        fragmentList.add(this.mFragmentCallog);
        fragmentList.add(this.mFragmentPhonebookList);
        fragmentList.add(this.mFragmentCallPhone);
        fragmentList.add(this.mFragmentMusic);
        fragmentList.add(this.mFragmentSearch);
        fragmentList.add(this.mFragmentPairedList);
        fragmentList.add(this.mFragmentSetting);
        this.mViewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragmentList));
        this.mViewPager.setOffscreenPageLimit(7);
        this.mViewPager.setCurrentItem(2);
        setSelect(2);
        this.mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /* class com.goodocom.gocsdk.activity.MainActivity.AnonymousClass4 */

            @Override // android.support.v4.view.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override // android.support.v4.view.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                MainActivity.this.setSelect(i);
            }

            @Override // android.support.v4.view.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    private void setHomeKey() {
        this.mHomeKey = new HomeKey(this);
        this.mHomeKey.setOnHomePressedListener(new HomeKey.OnHomePressedListener() {
            /* class com.goodocom.gocsdk.activity.MainActivity.AnonymousClass6 */

            @Override // com.goodocom.gocsdk.key.HomeKey.OnHomePressedListener
            public void onHomePressed() {
                MainActivity.this.finish();
            }

            @Override // com.goodocom.gocsdk.key.HomeKey.OnHomePressedListener
            public void onHomeLongPressed() {
                MainActivity.this.finish();
            }
        });
        this.mHomeKey.startWatch();
    }

    public void request() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null) {
            finish();
            return;
        }
        if (!adapter.isEnabled()) {
            adapter.enable();
        }
        if (!getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
            finish();
        } else if (Build.VERSION.SDK_INT >= 23) {
            String[] permissions = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.BLUETOOTH", "android.permission.BLUETOOTH_ADMIN", "android.permission.READ_CONTACTS", "android.permission.WRITE_CONTACTS", "android.permission.READ_CALL_LOG", "android.permission.WRITE_CALL_LOG", "android.permission.MODIFY_AUDIO_SETTINGS"};
            for (String str : permissions) {
                if (checkSelfPermission(str) != 0) {
                    requestPermissions(permissions, 111);
                    return;
                }
            }
        }
    }

    @Override // com.goodocom.gocsdk.originbt.OriginBluetoothService.MusicPlayListener
    public void onMusicPlayChange(int state) {
        FragmentMusic fragmentMusic = this.mFragmentMusic;
        if (fragmentMusic != null) {
            fragmentMusic.onMusicPlayChange(state);
        }
    }

    public void registCallPhone() {
        GocJar.registerBluetoothPhoneChangeListener(new GocBluetoothPhoneChangeListener() {
            /* class com.goodocom.gocsdk.activity.MainActivity.AnonymousClass7 */

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothPhoneChangeListener
            public void onHfpCallChanged(String s, GocHfpClientCall gocHfpClientCall) {
                String str = MainActivity.TAG;
                Log.e(str, "s : " + s + "   gocHfpClientCall " + gocHfpClientCall.getNumber() + "   gocHfpClientCall  " + gocHfpClientCall.getState() + "  gocHfpClientCall  " + gocHfpClientCall.isMultiParty() + "   gocHfpClientCall   " + gocHfpClientCall.isOutgoing());
                if (!gocHfpClientCall.isOutgoing()) {
                    if (gocHfpClientCall.getState() == 4) {
                        Log.e(MainActivity.TAG, "---------CALL_STATE_INCOMING START ACTI-----------");
                        ComponentName cn = new ComponentName(BuildConfig.APPLICATION_ID, "com.goodocom.gocsdk.activity.IncomingActivity");
                        Intent intent = new Intent();
                        intent.putExtra(GocDatabase.COL_NUMBER, gocHfpClientCall.getNumber());
                        intent.setComponent(cn);
                        MainActivity.this.startActivity(intent);
                    }
                } else if (gocHfpClientCall.getState() == 2) {
                    Log.e(MainActivity.TAG, "---------CALL_STATE_INCOMING START ACTI-----------");
                    ComponentName cn2 = new ComponentName(BuildConfig.APPLICATION_ID, "com.goodocom.gocsdk.activity.CallActivity");
                    Intent intent2 = new Intent();
                    intent2.putExtra(GocDatabase.COL_NUMBER, gocHfpClientCall.getNumber());
                    intent2.setComponent(cn2);
                    MainActivity.this.startActivity(intent2);
                }
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothPhoneChangeListener
            public void onHfpCallingTimeChanged(String s) {
                String str = MainActivity.TAG;
                Log.e(str, "onHfpCallingTimeChanged : " + s);
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothPhoneChangeListener
            public void onPbapStateChanged(int i) {
            }
        });
    }
}

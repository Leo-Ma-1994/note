package com.goodocom.gocsdk.fragment;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.goodocom.gocsdk.GocAppData;
import com.goodocom.gocsdk.R;
import com.goodocom.gocsdk.activity.MainActivity;
import com.goodocom.gocsdk.adapter.PhoneBookAdapter;
import com.goodocom.gocsdk.db.GocDatabase;
import com.goodocom.gocsdk.domain.PhoneBookInfo;
import com.goodocom.gocsdk.manager.BluetoothConnectManager;
import com.goodocom.gocsdk.manager.BluetoothManager;
import com.goodocom.gocsdk.manager.ContactsChangeManager;
import com.goodocom.gocsdk.manager.GocThreadPoolFactory;
import com.goodocom.gocsdk.service.GocsdkCallbackImp;
import java.util.ArrayList;
import java.util.List;

public class FragmentPhonebookList extends BaseFragment implements View.OnClickListener, ContactsChangeManager.ContactsChangeLinstener {
    public static final String TAG = FragmentPhonebookList.class.getSimpleName();
    private MainActivity activity;
    private ImageView image_animation;
    private ListView lv_content;
    private ContactsChangeManager mContactsChangeManager;
    public PBAPDownloadListener mPBAPDownloadListener;
    private RelativeLayout rl_downloading;
    public PhoneBookAdapter simpleAdapter;
    private TextView tv_contacts_count;
    private TextView tv_device_disconnect;
    private TextView tv_download;

    public interface PBAPDownloadListener {
        void onDownloadPhoneBook();
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment
    public void onConnected(BluetoothDevice device) {
        Log.e("connect", "FragmentPhonebookList: onConnected" + device);
        onPbapAutoDownload();
        showConnect();
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment
    public void onDisconnected() {
        Log.e("connect", "FragmentPhonebookList: onDisconnected");
        Log.e("pbap", "onDisconnected>>>> " + GocAppData.getInstance().mCurrentBluetoothDevice);
        this.activity.mBluetoothConnectManager.disconnectPbap(GocAppData.getInstance().mCurrentBluetoothDevice);
        GocAppData.getInstance().mContacts.clear();
        this.simpleAdapter.notifyDataSetChanged();
        finishAnima();
        showDisconnect();
        GocAppData.getInstance().mIsloadFinished = false;
    }

    public void onGocPhoneBook(PhoneBookInfo phonebook) {
        GocAppData.getInstance().mContacts.add(phonebook);
        TextView textView = this.tv_contacts_count;
        textView.setText("正在更新联系人： " + GocAppData.getInstance().mContacts.size());
        this.simpleAdapter.notifyDataSetChanged();
        Log.e("connect", "contacts: " + GocAppData.getInstance().mContacts.size());
        GocDatabase.getDefault().insertPhonebook(phonebook.name, phonebook.num);
    }

    public void onOriginPhoneBook() {
        finishAnima();
        Log.e(TAG, "showdata>>>>onOriginPhoneBook>");
        showData();
    }

    private void finishAnima() {
        this.rl_downloading.setVisibility(8);
        this.image_animation.clearAnimation();
        this.image_animation.setVisibility(8);
    }

    public void onOriginPhoneBookEnd() {
        hanlder.post(new Runnable() {
            /* class com.goodocom.gocsdk.fragment.FragmentPhonebookList.AnonymousClass1 */

            @Override // java.lang.Runnable
            public void run() {
                FragmentPhonebookList.this.onOriginPhoneBook();
            }
        });
    }

    @Override // com.goodocom.gocsdk.manager.ContactsChangeManager.ContactsChangeLinstener
    public void onContactChange() {
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment, android.support.v4.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = (MainActivity) getActivity();
    }

    @Override // android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(this.activity, R.layout.fragmentmaillist, null);
        initView(view);
        Log.d("app", "GocsdkCallbackImp.hfpStatus=" + GocsdkCallbackImp.hfpStatus);
        init();
        BluetoothManager.getInstance().getConnectStatus(this);
        return view;
    }

    private void init() {
        this.mContactsChangeManager = new ContactsChangeManager(this.activity, this.mBaseHander);
        this.mContactsChangeManager.setContactsChangeLinstener(this);
        registerContentObservers();
        showData();
    }

    private void registerContentObservers() {
        this.activity.getContentResolver().registerContentObserver(ContactsContract.Contacts.CONTENT_URI, true, this.mContactsChangeManager);
    }

    public void showConnect() {
        Log.e(TAG, "showConnect : ");
        this.rl_downloading.setVisibility(8);
        this.lv_content.setVisibility(8);
        this.tv_device_disconnect.setVisibility(8);
        boolean z = false;
        this.tv_download.setVisibility(0);
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("SHOW: ");
        sb.append(this.image_animation.isShown());
        sb.append("   ");
        if (this.image_animation.getVisibility() == 0) {
            z = true;
        }
        sb.append(z);
        Log.e(str, sb.toString());
        if (this.image_animation.getVisibility() == 0) {
            showDownLoading();
        }
    }

    public void showData() {
        Log.e(TAG, "showData : ");
        this.rl_downloading.setVisibility(8);
        this.lv_content.setVisibility(0);
        this.tv_download.setVisibility(8);
        this.tv_device_disconnect.setVisibility(8);
        this.simpleAdapter.notifyDataSetChanged();
        this.lv_content.invalidate();
    }

    public void showDisconnect() {
        Log.e(TAG, "showDisconnect : ");
        GocAppData.getInstance().mContacts.clear();
        this.simpleAdapter.notifyDataSetChanged();
        this.tv_contacts_count.setText("");
        this.rl_downloading.setVisibility(8);
        this.lv_content.setVisibility(8);
        this.tv_device_disconnect.setVisibility(0);
        this.tv_download.setVisibility(8);
    }

    private void initView(View view) {
        this.lv_content = (ListView) view.findViewById(R.id.lv_content);
        this.tv_device_disconnect = (TextView) view.findViewById(R.id.tv_device_disconnect);
        this.lv_content.setSelector(R.drawable.contact_list_item_selector);
        this.rl_downloading = (RelativeLayout) view.findViewById(R.id.rl_downloading);
        this.image_animation = (ImageView) view.findViewById(R.id.image_animation);
        this.tv_contacts_count = (TextView) view.findViewById(R.id.tv_contacts_count);
        this.tv_download = (TextView) view.findViewById(R.id.tv_download);
        this.simpleAdapter = new PhoneBookAdapter(GocAppData.getInstance().mContacts, this.activity);
        this.lv_content.setAdapter((ListAdapter) this.simpleAdapter);
        this.lv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /* class com.goodocom.gocsdk.fragment.FragmentPhonebookList.AnonymousClass2 */

            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            }
        });
        this.tv_download.setOnClickListener(this);
    }

    private void createCallOutDialog(String Name, final String Num) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.activity);
        builder.setMessage("确定要拨打吗?" + Name + ":" + Num);
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            /* class com.goodocom.gocsdk.fragment.FragmentPhonebookList.AnonymousClass3 */

            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.dismiss();
                FragmentPhonebookList.this.calloriginBletooth(Num);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            /* class com.goodocom.gocsdk.fragment.FragmentPhonebookList.AnonymousClass4 */

            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment, android.support.v4.app.Fragment
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        String str = TAG;
        Log.e(str, "onHiddenChanged : " + hidden);
        if (hidden) {
            return;
        }
        if (this.simpleAdapter.getCount() == 0) {
            showConnect();
        } else {
            showData();
        }
    }

    public void onPbapAutoDownload() {
        showDownLoading();
        this.mBaseHander.postDelayed(new Runnable() {
            /* class com.goodocom.gocsdk.fragment.FragmentPhonebookList.AnonymousClass5 */

            @Override // java.lang.Runnable
            public void run() {
                FragmentPhonebookList.this.donwLoadData();
            }
        }, 6000);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void donwLoadData() {
        int conenctState = BluetoothAdapter.getDefaultAdapter().getProfileConnectionState(17);
        Log.e("pbap", "conenctState<<<<<<<<<<<<<<<<<<<<<<<<" + conenctState + "     " + GocAppData.getInstance().mCurrentBluetoothDevice);
        if (conenctState != 2) {
            this.activity.mBluetoothConnectManager.connectPbapToLoadContacts(GocAppData.getInstance().mCurrentBluetoothDevice);
        } else {
            requestContacts();
        }
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment
    public void loadContacts() {
        super.loadContacts();
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("loadContacts : ");
        sb.append(!GocAppData.getInstance().mIsloadFinished);
        Log.e(str, sb.toString());
        if (!GocAppData.getInstance().mIsloadFinished) {
            GocAppData.getInstance().mIsloadFinished = true;
            requestContacts();
            return;
        }
        showData();
    }

    public void requestContacts() {
        GocThreadPoolFactory.getInstance().executeRequest(new Runnable() {
            /* class com.goodocom.gocsdk.fragment.FragmentPhonebookList.AnonymousClass6 */

            @Override // java.lang.Runnable
            public void run() {
                GocAppData.getInstance().mContacts.clear();
                GocAppData.getInstance().mContacts.addAll(FragmentPhonebookList.getAllContact(FragmentPhonebookList.this.activity));
                String str = FragmentPhonebookList.TAG;
                Log.e(str, "list:" + GocAppData.getInstance().mContacts.size());
                FragmentPhonebookList.this.onOriginPhoneBookEnd();
            }
        });
    }

    public static List<PhoneBookInfo> getAllContact(Context context) {
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        List<PhoneBookInfo> phonebooks = new ArrayList<>();
        while (cursor.moveToNext()) {
            String contactId = cursor.getString(cursor.getColumnIndex("_id"));
            String name = cursor.getString(cursor.getColumnIndex("display_name"));
            Uri uri2 = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            Cursor phones = contentResolver.query(uri2, null, "contact_id = " + contactId, null, null);
            while (phones.moveToNext()) {
                String phoneNumber = phones.getString(phones.getColumnIndex("data1"));
                PhoneBookInfo phonebook = new PhoneBookInfo();
                phonebook.name = name;
                phonebook.num = phoneNumber;
                phonebooks.add(phonebook);
            }
            phones.close();
        }
        cursor.close();
        return phonebooks;
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("connect", "onDestroyView : ");
    }

    private void reflashContactsData() {
    }

    private void callOut(String phoneNumber2) {
        placeCall(phoneNumber2);
    }

    private static void placeCall(String mLastNumber) {
    }

    public void calloriginBletooth(String number) {
        if (!TextUtils.isEmpty(number)) {
            BluetoothConnectManager.getInstance(this.activity).mBluetoothHeadsetClient.dial(GocAppData.getInstance().mCurrentBluetoothDevice, number);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (this.tv_download == view) {
            showDownLoading();
            donwLoadData();
        }
    }

    private void showDownLoading() {
        this.rl_downloading.setVisibility(0);
        this.lv_content.setVisibility(8);
        this.tv_device_disconnect.setVisibility(8);
        this.tv_download.setVisibility(8);
        this.image_animation.setVisibility(0);
        TranslateAnimation animation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, 0.8f);
        animation.setDuration(500);
        animation.setFillAfter(false);
        animation.setRepeatCount(-1);
        animation.setRepeatMode(1);
        this.image_animation.startAnimation(animation);
    }

    public void setPBAPDownloadListener(PBAPDownloadListener listener) {
        this.mPBAPDownloadListener = listener;
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment, android.support.v4.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        Log.e("dest", "phone_onDestroy");
    }
}

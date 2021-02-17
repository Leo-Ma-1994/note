package com.goodocom.gocsdk.manager;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.util.Log;

public class ContactsChangeManager extends ContentObserver {
    public ContactsChangeLinstener mContactsChangeLinstener;
    private Context mContext;
    private Handler mHandler;

    public interface ContactsChangeLinstener {
        void onContactChange();
    }

    public ContactsChangeManager(Handler handler) {
        super(handler);
    }

    public ContactsChangeManager(Context context, Handler handler) {
        super(handler);
        this.mContext = context;
        this.mHandler = handler;
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        Log.e("pbap", "onChange " + this.mContactsChangeLinstener);
        ContactsChangeLinstener contactsChangeLinstener = this.mContactsChangeLinstener;
        if (contactsChangeLinstener != null) {
            contactsChangeLinstener.onContactChange();
        }
    }

    public void setContactsChangeLinstener(ContactsChangeLinstener linstener) {
        this.mContactsChangeLinstener = linstener;
    }
}

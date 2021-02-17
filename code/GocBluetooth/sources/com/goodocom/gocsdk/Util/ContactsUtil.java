package com.goodocom.gocsdk.Util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import com.goodocom.gocsdk.manager.Contacts;
import java.util.ArrayList;
import java.util.List;

public class ContactsUtil {
    public static List<Contacts> getLocalContacts(Context context) {
        return getPhoneNumbersByUri(context, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
    }

    private static List<Contacts> getPhoneNumbersByUri(Context context, Uri uri) {
        List<Contacts> _List = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(uri, new String[]{"display_name", "data1", "contact_id"}, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                _List.add(new Contacts(cursor.getString(cursor.getColumnIndex("display_name")), cursor.getString(cursor.getColumnIndex("data1")), false));
            }
        }
        return _List;
    }

    public static boolean insert(Context context, String name, String mobile_number) {
        try {
            ContentValues values = new ContentValues();
            long rawContactId = ContentUris.parseId(context.getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values));
            if (name != "") {
                values.clear();
                values.put("raw_contact_id", Long.valueOf(rawContactId));
                values.put("mimetype", "vnd.android.cursor.item/name");
                values.put("data2", name);
                context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
            }
            if (mobile_number == "") {
                return true;
            }
            values.clear();
            values.put("raw_contact_id", Long.valueOf(rawContactId));
            values.put("mimetype", "vnd.android.cursor.item/phone_v2");
            values.put("data1", mobile_number);
            values.put("data2", (Integer) 2);
            context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean deleteContact(Context context, String contact_id) {
        if (context.getContentResolver().delete(ContactsContract.RawContacts.CONTENT_URI, "_id =?", new String[]{contact_id}) > 0) {
            return true;
        }
        return false;
    }

    public static List<Contacts> ReadAllContacts(Context context) {
        List<Contacts> _List = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        int contactIdIndex = 0;
        int nameIndex = 0;
        if (cursor.getCount() > 0) {
            contactIdIndex = cursor.getColumnIndex("_id");
            nameIndex = cursor.getColumnIndex("display_name");
        }
        while (cursor.moveToNext()) {
            String contactId = cursor.getString(contactIdIndex);
            String name = cursor.getString(nameIndex);
            ContentResolver contentResolver = context.getContentResolver();
            Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            Cursor phones = contentResolver.query(uri, null, "contact_id=" + contactId, null, null);
            int phoneIndex = 0;
            if (phones.getCount() > 0) {
                phoneIndex = phones.getColumnIndex("data1");
            }
            while (phones.moveToNext()) {
                Contacts contact = new Contacts(name, phones.getString(phoneIndex), false);
                contact.setId(contactId);
                _List.add(contact);
            }
        }
        return _List;
    }
}

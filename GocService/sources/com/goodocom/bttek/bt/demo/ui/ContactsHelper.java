package com.goodocom.bttek.bt.demo.ui;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;

public class ContactsHelper {
    private static final String TAG = "ContactsHelper";

    public static void addContacts(Context context, String firstName, String middleName, String lastName, String[] number, int[] phoneType) {
        int pType;
        Log.d(TAG, "addContacts() " + firstName + " " + middleName + " " + lastName);
        ContentValues values = new ContentValues();
        long rawContactId = ContentUris.parseId(context.getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values));
        values.clear();
        values.put("raw_contact_id", Long.valueOf(rawContactId));
        values.put("mimetype", "vnd.android.cursor.item/name");
        if (firstName != null) {
            values.put("data2", firstName);
        }
        if (middleName != null) {
            values.put("data5", middleName);
        }
        if (lastName != null) {
            values.put("data3", lastName);
        }
        context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
        for (int i = 0; i < number.length; i++) {
            switch (phoneType[i]) {
                case 0:
                    pType = 0;
                    break;
                case 1:
                    pType = 0;
                    break;
                case 2:
                    pType = 3;
                    break;
                case 3:
                    pType = 1;
                    break;
                case 4:
                    pType = 7;
                    break;
                case 5:
                    pType = 4;
                    break;
                case 6:
                    pType = 20;
                    break;
                case 7:
                    pType = 2;
                    break;
                case 8:
                    pType = 6;
                    break;
                default:
                    pType = 7;
                    break;
            }
            values.clear();
            values.put("raw_contact_id", Long.valueOf(rawContactId));
            values.put("mimetype", "vnd.android.cursor.item/phone_v2");
            values.put("data1", number[i]);
            values.put("data2", Integer.valueOf(pType));
            context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
        }
    }

    public static void addCallLog(Context context, String name, String number, String timestamp, int calllogType, boolean isNeedDelete) {
        Log.d(TAG, "addCallLog() " + name + " " + number + " " + timestamp);
        long refreshStartTime = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        sb.append("Piggy Check refreshStartTime : ");
        sb.append(refreshStartTime);
        Log.e(TAG, sb.toString());
        ContentResolver calllogResolver = context.getContentResolver();
        int targetType = 0;
        if (calllogType == 4) {
            targetType = 2;
        } else if (calllogType == 2) {
            targetType = 3;
        } else if (calllogType == 3) {
            targetType = 1;
        }
        if (isNeedDelete) {
            Uri uri = CallLog.Calls.CONTENT_URI;
            int deleted = calllogResolver.delete(uri, "type=" + targetType, null);
            Log.e(TAG, "Piggy Check deleted count : " + deleted);
        }
        ContentValues values = new ContentValues();
        values.put("number", number);
        values.put("name", name);
        values.put("date", timestamp);
        values.put("type", Integer.valueOf(targetType));
        Log.e(TAG, "Piggy Check values : " + values);
        calllogResolver.insert(CallLog.Calls.CONTENT_URI, values);
        Log.d(TAG, "Piggy Check refreshEndTime : " + System.currentTimeMillis());
        int result = (int) (System.currentTimeMillis() - refreshStartTime);
        Log.e(TAG, "Piggy Check Used Time : " + result + "ms");
    }
}

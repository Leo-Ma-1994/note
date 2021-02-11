package com.goodocom.bttek.customer.service;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;
import com.goodocom.bttek.BuildConfig;
import com.goodocom.bttek.bt.GocApplication;
import com.goodocom.bttek.bt.res.NfDef;
import com.goodocom.gocDataBase.GocPbapHelper;
import com.goodocom.gocsdkserver.CommandPbapImp;

public class MyContentProvider extends ContentProvider {
    public static final String AUTHORITY = "com.nforetek.bt.mycontentprovider";
    public static final Uri CALLLOG_URI = Uri.parse("content://com.nforetek.bt.mycontentprovider/call_log");
    public static final Uri CONTACTS_CONTENT_URI = Uri.parse("content://com.nforetek.bt.mycontentprovider/contacts");
    private static final int CONTACTS_URI_CODE = 0;
    private static final int NEWCALLOG_URI_CODE = 2;
    private static final int PHONENUMBER_DETAIL = 1;
    public static final Uri PHONENUMBER_DETAIL_URI = Uri.parse("content://com.nforetek.bt.mycontentprovider/PhoneNumberDetail");
    private static final String TAG = "GoodocomProvider";
    private static final UriMatcher uriMatcher = new UriMatcher(-1);
    private Context mContext;

    static {
        uriMatcher.addURI(AUTHORITY, GocPbapHelper.PHONEBOOK_CONTENT, 0);
        uriMatcher.addURI(AUTHORITY, GocPbapHelper.PHONENUMBER_DETAIL, 1);
        uriMatcher.addURI(AUTHORITY, GocPbapHelper.CALLHISTORY_CONTENT, 2);
    }

    private String getTableName(Uri uri) {
        int type = uriMatcher.match(uri);
        Log.e(TAG, "getTableName: " + uri + "     " + type);
        if (type == 0) {
            return GocPbapHelper.PHONEBOOK_CONTENT;
        }
        if (type == 1) {
            return GocPbapHelper.PHONENUMBER_DETAIL;
        }
        if (type != 2) {
            return BuildConfig.FLAVOR;
        }
        return GocPbapHelper.CALLHISTORY_CONTENT;
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        Log.e(TAG, "onCreate: ");
        this.mContext = getContext();
        return true;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (NfDef.DEFAULT_ADDRESS.equals(GocApplication.INSTANCE.mMainAddress)) {
            Log.i(TAG, "query: 没有蓝牙连接，给讯飞返回空");
            return null;
        }
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        int match = uriMatcher.match(uri);
        if (match == 0) {
            queryBuilder.setTables(GocPbapHelper.PHONEBOOK_CONTENT);
        } else if (match == 1) {
            queryBuilder.setTables(GocPbapHelper.PHONENUMBER_DETAIL);
        } else if (match == 2) {
            queryBuilder.setTables(GocPbapHelper.CALLHISTORY_CONTENT);
        } else {
            throw new IllegalArgumentException("Unknow URI: " + uri);
        }
        Cursor cursor = queryBuilder.query(CommandPbapImp.mdb, projection, reSelection(selection), selectionArgs, null, null, sortOrder);
        this.mContext.getContentResolver().notifyChange(uri, null);
        return cursor;
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues values) {
        Log.e(TAG, "insert: ");
        CommandPbapImp.mdb.insert(getTableName(uri), null, values);
        this.mContext.getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.e(TAG, "delete: ");
        int count = CommandPbapImp.mdb.delete(getTableName(uri), reSelection(selection), selectionArgs);
        if (count > 0) {
            this.mContext.getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.e(TAG, "update: ");
        int rows = CommandPbapImp.mdb.update(getTableName(uri), values, selection, selectionArgs);
        if (rows > 0) {
            this.mContext.getContentResolver().notifyChange(uri, null);
        }
        return rows;
    }

    private String reSelection(String selection) {
        if (selection == null || BuildConfig.FLAVOR.equals(selection)) {
            return "addr ='" + GocApplication.INSTANCE.mMainAddress + "'";
        }
        return "addr ='" + GocApplication.INSTANCE.mMainAddress + "' and " + selection;
    }
}

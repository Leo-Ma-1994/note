package com.goodocom.gocsdk.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.goodocom.gocsdk.domain.CallLogInfo;
import com.goodocom.gocsdk.domain.ContactInfo;
import java.util.ArrayList;
import java.util.List;

public class GocDatabase {
    private static final String CALLLOG_TABLE = "calllog";
    public static final String COL_NAME = "name";
    public static final String COL_NUMBER = "number";
    public static final String COL_TYPE = "type";
    private static final String DB_PATH = "/data/data/com.goodocom.gocsdk/BtPhone.db";
    private static GocDatabase DEFAULT = null;
    private static final String PHONEBOOK_TABLE = "phonebook";
    private static final String SQL_CLEAR_CALLLOG = "DELETE FROM calllog";
    private static final String SQL_CLEAR_PHONEBOOK = "DELETE FROM phonebook";
    private static final String SQL_CREATE_CALLLOG_TABLE = "create table if not exists calllog(_id integer primary key autoincrement,name text,number text,type integer)";
    private static final String SQL_CREATE_PHONEBOOK_TABLE = "create table if not exists phonebook(_id integer primary key autoincrement,name text,number text)";
    private SQLiteDatabase mDb = SQLiteDatabase.openOrCreateDatabase(DB_PATH, (SQLiteDatabase.CursorFactory) null);

    public static synchronized GocDatabase getDefault() {
        GocDatabase gocDatabase;
        synchronized (GocDatabase.class) {
            if (DEFAULT == null) {
                DEFAULT = new GocDatabase();
            }
            gocDatabase = DEFAULT;
        }
        return gocDatabase;
    }

    public void close() {
        SQLiteDatabase sQLiteDatabase = this.mDb;
        if (sQLiteDatabase != null) {
            sQLiteDatabase.close();
            this.mDb = null;
        }
    }

    private void createPhonebookTable() {
        SQLiteDatabase sQLiteDatabase = this.mDb;
        if (sQLiteDatabase == null) {
            Log.e("goc", "createPhonebookTable error,mDb == null");
        } else {
            sQLiteDatabase.execSQL(SQL_CREATE_PHONEBOOK_TABLE);
        }
    }

    private void createCalllogTable() {
        SQLiteDatabase sQLiteDatabase = this.mDb;
        if (sQLiteDatabase == null) {
            Log.e("goc", "createCalllogTable error,mDb == null");
        } else {
            sQLiteDatabase.execSQL(SQL_CREATE_CALLLOG_TABLE);
        }
    }

    public boolean insertPhonebook(String name, String number) {
        Log.d("app", "insertPhonebook name:" + name + " number:" + number);
        if (this.mDb == null) {
            Log.e("goc", "insertPhonebook error,mDb == null");
            return false;
        }
        createPhonebookTable();
        ContentValues cValue = new ContentValues();
        cValue.put(COL_NAME, name);
        cValue.put(COL_NUMBER, number);
        if (this.mDb.insert(PHONEBOOK_TABLE, null, cValue) != -1) {
            return true;
        }
        return false;
    }

    public void clearPhonebook() {
        if (this.mDb == null) {
            Log.e("goc", "clearPhonebook error,mDb == null");
            return;
        }
        createPhonebookTable();
        this.mDb.execSQL(SQL_CLEAR_PHONEBOOK);
    }

    public Cursor queryPhonebook() {
        if (this.mDb == null) {
            Log.e("goc", "queryCalllog error,mDb == null");
            return null;
        }
        createPhonebookTable();
        return this.mDb.query(PHONEBOOK_TABLE, new String[]{COL_NAME, COL_NUMBER}, null, null, null, null, null);
    }

    public List<ContactInfo> getAllPhonebook() {
        List<ContactInfo> list = new ArrayList<>();
        Cursor cursor = queryPhonebook();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ContactInfo contactInfo = new ContactInfo();
            contactInfo.name = cursor.getString(cursor.getColumnIndex(COL_NAME));
            contactInfo.number = cursor.getString(cursor.getColumnIndex(COL_NUMBER));
            list.add(contactInfo);
            cursor.moveToNext();
        }
        return list;
    }

    public String getNameByNumber(String number) {
        return number;
    }

    public Cursor queryCalllog(int type) {
        if (this.mDb == null) {
            Log.e("goc", "queryCalllog error,mDb == null");
            return null;
        }
        createCalllogTable();
        SQLiteDatabase sQLiteDatabase = this.mDb;
        String[] strArr = {COL_NAME, COL_NUMBER};
        return sQLiteDatabase.query(CALLLOG_TABLE, strArr, "type=?", new String[]{"" + type}, null, null, null);
    }

    public List<CallLogInfo> getAllCallLog(int type) {
        List<CallLogInfo> list = new ArrayList<>();
        Cursor cursor = queryCalllog(type);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            CallLogInfo info = new CallLogInfo();
            info.name = cursor.getString(cursor.getColumnIndex(COL_NAME));
            info.number = cursor.getString(cursor.getColumnIndex(COL_NUMBER));
            info.type = type;
            list.add(info);
            cursor.moveToNext();
        }
        return list;
    }

    public boolean insertCalllog(int type, String name, String number) {
        Log.d("app", "insertCalllog name:" + name + " number:" + number + " type:" + type);
        if (this.mDb == null) {
            Log.e("goc", "insertCalllog error,mDb == null");
            return false;
        }
        createCalllogTable();
        ContentValues cValue = new ContentValues();
        cValue.put(COL_NAME, name);
        cValue.put(COL_NUMBER, number);
        cValue.put(COL_TYPE, Integer.valueOf(type));
        if (this.mDb.insert(CALLLOG_TABLE, null, cValue) != -1) {
            return true;
        }
        return false;
    }

    public void clearCalllog() {
        if (this.mDb == null) {
            Log.e("goc", "clearPhonebook error,mDb == null");
            return;
        }
        createCalllogTable();
        this.mDb.execSQL(SQL_CLEAR_CALLLOG);
    }
}

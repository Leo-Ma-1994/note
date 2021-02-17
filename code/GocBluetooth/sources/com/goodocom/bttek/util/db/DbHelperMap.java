package com.goodocom.bttek.util.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.goodocom.bttek.bt.res.MsgOutline;
import java.util.ArrayList;

public class DbHelperMap extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "db_map";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "MessageContent";
    private final String SQL_DELETE_All_MESSAGE = "delete from MessageContent";
    private final String SQL_DELETE_FOLDER = "delete from MessageContent where macAddress = ? and folder = ?";
    private final String SQL_DELETE_MESSAGE = "delete from MessageContent where macAddress = ?";
    private final String SQL_DELETE_MESSAGE_BY_FOLDER = "delete from MessageContent where folder = ? and handle = ? and macAddress = ?";
    private final String SQL_DELETE_ONE_MESSAGE = "delete from MessageContent where macAddress = ? and handle = ? and datetime=?";
    private final String SQL_MESSAGE = "select * from MessageContent where condition = ?";
    private final String SQL_SELECT_MESSAGE = "select * from MessageContent where macAddress = ?";
    private final String SQL_SELECT_MESSGE_BY_FOLDER_AND_HANDLE = "select * from MessageContent where folder = ? and handle = ? and macAddress = ?";
    private final String SQL_SELECT_ONE_MESSAGE = "select * from MessageContent where macAddress = ? and handle = ? and folder = ?";
    private String TAG = "DbMapHelper";
    private String _id = "_id";
    private String datetime = "datetime";
    private String folder = "folder";
    private String handle = "handle";
    private Object helper;
    private Context m_context;
    private String macAddress = "macAddress";
    private String message = "message";
    private String read = "read";
    private String recipient_addressing = "recipient_addressing";
    private String sender_addressing = "sender_addressing";
    private String sender_name = "sender_name";
    private String size = "size";
    private String subject = "subject";

    public DbHelperMap(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
        Log.d(this.TAG, "DbHelperMap constucter");
        this.m_context = context;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase db) {
        Log.d(this.TAG, "onCreate() Piggy check");
        db.execSQL("CREATE TABLE IF NOT EXISTS MessageContent (" + this._id + " Integer primary key autoincrement, " + this.folder + " varchar(6), " + this.handle + " varchar(256), " + this.subject + " varchar(256), " + this.message + " varchar(256), " + this.datetime + " varchar(256), " + this.sender_name + " varchar(256), " + this.sender_addressing + " varchar(256), " + this.recipient_addressing + " varchar(256), " + this.read + " varchar(3), " + this.macAddress + " varchar(17)) ");
    }

    public void insertMessageInfo(SQLiteDatabase db, String handle2, int folder2, String subject2, String message2, String date, String senderName, String senderAddr, String recipientAddr, String address, int readStatus) {
        Log.e(this.TAG, "insertMessageInfo");
        ContentValues cv = new ContentValues();
        String str = this.TAG;
        Log.e(str, "insertMessageInfo " + handle2);
        cv.put("Folder", Integer.valueOf(folder2));
        cv.put("Handle", handle2);
        cv.put("Subject", subject2);
        cv.put("Message", message2);
        cv.put("Datetime", date);
        cv.put("Sender_Name", senderName);
        cv.put("Sender_Addressing", senderAddr);
        cv.put("Recipient_Addressing", recipientAddr);
        cv.put("Read", readStatus == 1 ? "yes" : "no");
        cv.put("macAddress", address);
        db.insert(TABLE_NAME, null, cv);
    }

    public void insertMessageInfo(SQLiteDatabase db, MsgOutline msgOutline) {
        Log.e(this.TAG, "insertMessageInfo");
        ContentValues cv = new ContentValues();
        if (msgOutline != null) {
            String str = this.TAG;
            Log.e(str, "insertMessageInfo " + msgOutline.getHandle());
            cv.put("Folder", msgOutline.getFolder());
            cv.put("Handle", msgOutline.getHandle());
            cv.put("Subject", msgOutline.getSubject());
            cv.put("Message", msgOutline.getMessage());
            cv.put("Datetime", msgOutline.getDatetime());
            cv.put("Sender_Name", msgOutline.getSender_name());
            cv.put("Sender_Addressing", msgOutline.getSender_addressing());
            cv.put("Recipient_Addressing", msgOutline.getRecipient_addressing());
            cv.put("Read", msgOutline.getRead());
            cv.put("macAddress", msgOutline.getMacAddress());
            db.insert(TABLE_NAME, null, cv);
        }
    }

    public ArrayList<String> queryMessageInfo(String request, SQLiteDatabase db) {
        String str = null;
        Cursor cursor = db.query(TABLE_NAME, new String[]{request}, str, null, str, str, str);
        int rows_num = cursor.getCount();
        ArrayList<String> sMessage = new ArrayList<>();
        Log.e(this.TAG, "Piggy Check rows counts : " + rows_num);
        if (rows_num != 0) {
            for (int i = 0; i < rows_num; i++) {
                if (i == 0) {
                    cursor.moveToFirst();
                } else {
                    cursor.moveToNext();
                }
                sMessage.add(cursor.getString(0));
            }
            cursor.close();
        }
        return sMessage;
    }

    public Cursor queryMessage(SQLiteDatabase db, String condition) {
        return db.rawQuery("select * from MessageContent where condition = ?", new String[]{condition});
    }

    public void onUpdate(SQLiteDatabase db, String message2) {
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS config");
        onCreate(db);
    }

    public void deleteAllTableContent(SQLiteDatabase db) {
        db.delete(TABLE_NAME, null, null);
    }

    public MsgOutline queryMessageByfolderAndHandle(SQLiteDatabase db, String folder2, String handle2, String address) {
        Cursor cursor = db.rawQuery("select * from MessageContent where folder = ? and handle = ? and macAddress = ?", new String[]{folder2, handle2, address});
        MsgOutline msgOutline = new MsgOutline();
        cursor.moveToFirst();
        msgOutline.setFolder(cursor.getString(1));
        msgOutline.setHandle(cursor.getString(2));
        msgOutline.setSubject(cursor.getString(3));
        msgOutline.setMessage(cursor.getString(4));
        msgOutline.setDatetime(cursor.getString(5));
        msgOutline.setSender_name(cursor.getString(6));
        msgOutline.setSender_addressing(cursor.getString(7));
        msgOutline.setRecipient_addressing(cursor.getString(8));
        msgOutline.setSize(cursor.getString(9));
        msgOutline.setRead(cursor.getString(10));
        cursor.close();
        return msgOutline;
    }

    public Cursor queryMessageByMacAddress(SQLiteDatabase db, String message2) {
        return db.rawQuery("select * from MessageContent where macAddress = ?", new String[]{message2});
    }

    public Cursor queryOneMessageByMacAddress(SQLiteDatabase db, String address, String handle2, String folder2) {
        if (folder2.equals("inbox") || folder2.equals("sent")) {
            return db.rawQuery("select * from MessageContent where macAddress = ? and handle = ? and folder = ?", new String[]{address, handle2, folder2});
        }
        Log.e(this.TAG, "folder parameter error !");
        return null;
    }

    public boolean isMessageInDatabase(SQLiteDatabase db, String address, String handle2, String folder2) {
        String str = this.TAG;
        Log.e(str, "Piggy Check isMessageInDatabase address : " + address + " handle : " + handle2 + " folder : " + folder2);
        if (folder2.equals("inbox") || folder2.equals("sent")) {
            Cursor cursor = db.rawQuery("select * from MessageContent where macAddress = ? and handle = ? and folder = ?", new String[]{address, handle2, folder2});
            String str2 = this.TAG;
            Log.e(str2, "Piggy Check isMessageInDatabase cursor count : " + cursor.getCount());
            cursor.moveToFirst();
            String checkID = cursor.getString(cursor.getColumnIndex("_id"));
            String checkHandle = cursor.getString(cursor.getColumnIndex("handle"));
            String checkDate = cursor.getString(cursor.getColumnIndex("datetime"));
            String str3 = this.TAG;
            Log.e(str3, "Piggy Check ID : " + checkID + " handle : " + checkHandle + " DateTime : " + checkDate);
            if (cursor.getCount() == 1) {
                return true;
            }
            if (cursor.getCount() <= 1) {
                return false;
            }
            Log.e(this.TAG, "Piggy Check have same handle same folder in database. don't know why .");
            return true;
        }
        Log.e(this.TAG, "folder parameter error !");
        return false;
    }

    public void deleteAllMessage(SQLiteDatabase db, String message2) {
        db.execSQL("delete from MessageContent", new String[]{message2});
    }

    public void deleteMessageByMacAddress(SQLiteDatabase db, String address) {
        db.execSQL("delete from MessageContent where macAddress = ?", new String[]{address});
    }

    public void deleteMessageByMacAddressAndFolder(SQLiteDatabase db, String address, int folder2) {
        db.execSQL("delete from MessageContent where macAddress = ? and folder = ?", new String[]{address, "" + folder2});
    }

    public void deleteOneMessageByMacAddress(SQLiteDatabase db, String address) {
        db.execSQL("delete from MessageContent where macAddress = ? and handle = ? and datetime=?", new String[]{address});
    }

    public void deleteMessageByFolderAndHandle(SQLiteDatabase db, int folder2, String handle2, String address) {
        db.execSQL("delete from MessageContent where folder = ? and handle = ? and macAddress = ?", new String[]{"" + folder2, handle2, address});
    }
}

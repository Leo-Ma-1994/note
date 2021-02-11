package com.goodocom.gocDataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;
import com.goodocom.bttek.BuildConfig;
import com.goodocom.bttek.bt.GocApplication;
import com.goodocom.bttek.bt.bean.CallLogs;
import com.goodocom.bttek.bt.bean.Collection;
import com.goodocom.bttek.bt.bean.Contacts;
import com.goodocom.bttek.customer.service.MyContentProvider;
import com.goodocom.utils.CharacterParser;
import com.goodocom.utils.HanyuPinyinHelper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GocPbapHelper extends SQLiteOpenHelper {
    private static final String ADDRESS = "addr";
    private static final String ADDRESS_DETAIL = "AddressDetail";
    public static final String[] ADDRESS_DETAIL_FIELD = {ID, "Content_ID", "AddressType", ADDRESS};
    public static final String CALLHISTORY_CONTENT = "call_log";
    public static final String[] CALLHISTORY_FIELD = {ID, ADDRESS, TYPE, NAME, NUMBER, TIME, FIRSTNAME, LASTNAME, PHONEBOOK_StorageType, HISTORYDATE};
    private static final String COLLEXTION = "collection";
    private static final String DATABASE_NAME = "db_pbap.db";
    private static final String DEVICESADDRESS = "DevicesAddress";
    private static final String DEVICE_ADDRESS_COUNT = "DeviceAddressCount";
    public static final String[] DEVICE_ADDRESS_COUNT_FIELD = {ADDRESS, "Flag"};
    private static final String EMAIL_DETAIL = "EmailDetail";
    public static final String[] EMAIL_DETAIL_FIELD = {ID, "Content_ID", "EmailType", "Email"};
    private static final String FIRSTNAME = "FirstName";
    private static final String HISTORYDATE = "HistoryDate";
    private static final String ID = "_id";
    private static final String IMGPATH = "imgpath";
    private static final String LASTNAME = "LastName";
    private static final String MIDDLENAME = "MiddleName";
    private static final String NAME = "name";
    private static final String NUMBER = "number";
    public static final String PHONEBOOK_CONTENT = "contacts";
    public static final String[] PHONEBOOK_CONTENT_FIELD = {ID, ADDRESS, NAME, NUMBER, TYPE, PINYIN, SZM, IMGPATH, COLLEXTION, FIRSTNAME, MIDDLENAME, LASTNAME, PHONEBOOK_FIRST_STREETADDRESS, PHONEBOOK_FIRST_CITYNAMEADDRESS, PHONEBOOK_FEDERALSTATEADDRESS, PHONEBOOK_FIRST_ZIPCODEADDRESS, PHONEBOOK_FIRST_COUNTTRYADDRESS, PHONEBOOK_SECOUND_STREETADDRESS, PHONEBOOK_SECOUND_CITYNAMEADDRESS, PHONEBOOK_FederalStateAddress, PHONEBOOK_Second_ZipCodeAddress, PHONEBOOK_Second_CountryAddress, PHONEBOOK_StorageType, PHONEBOOK_Org};
    private static final String PHONEBOOK_FEDERALSTATEADDRESS = "First_FederalStateAddress";
    private static final String PHONEBOOK_FIRST_CITYNAMEADDRESS = "First_CityNameAddress";
    private static final String PHONEBOOK_FIRST_COUNTTRYADDRESS = "First_CountryAddress";
    private static final String PHONEBOOK_FIRST_STREETADDRESS = "First_StreetAddress";
    private static final String PHONEBOOK_FIRST_ZIPCODEADDRESS = "First_ZipCodeAddress";
    private static final String PHONEBOOK_FederalStateAddress = "Second_FederalStateAddress";
    private static final String PHONEBOOK_Org = "Org";
    private static final String PHONEBOOK_SECOUND_CITYNAMEADDRESS = "Second_CityNameAddress";
    private static final String PHONEBOOK_SECOUND_STREETADDRESS = "Second_StreetAddress";
    private static final String PHONEBOOK_Second_CountryAddress = "Second_CountryAddress";
    private static final String PHONEBOOK_Second_ZipCodeAddress = "Second_ZipCodeAddress";
    private static final String PHONEBOOK_StorageType = "StorageType";
    public static final String PHONENUMBER_DETAIL = "PhoneNumberDetail";
    public static final String[] PHONENUMBER_DETAIL_FIELD = {ID, "Content_ID", TYPE, NUMBER, ADDRESS};
    private static final String PHONE_TYPE = "PhoneType";
    public static final String[] PHONE_TYPE_FIELD = {TYPE, "TypeName"};
    private static final String PINYIN = "pinyin";
    private static final String SZM = "szm";
    private static final String TIME = "time";
    private static final String TYPE = "type";
    private static GocPbapHelper mInstance = null;
    private final String SQL_DELETE_CONTACTER = "delete from PhoneBookContent where FullName = ?";
    private final String SQL_DELETE_PHONENUMBER = "delete from PhoneNumberDetail where Number = ?";
    private final String SQL_DELETE_PHONENUMBER_BY_FULLNAME = "delete from PhoneNumberDetail where Content_ID in (select _id from PhoneBookContent where FullName = ?)";
    private final String SQL_EXPRESS_TOTAL = "select a.FullName from PhoneBookContent a where a.CellPhone_Address=? and StorageType=? group by a.FullName";
    private final String SQL_QUERY_CALLHISTORY_BY_ADDRESS_STORAGETYPE = "select a._id, a.FullName, a.StorageType, a.PhoneNumber, a.PhoneType, a.HistoryDate, a.HistoryTime from CallHistory a where a.CellPhone_Address = ? and a.StorageType=? order by ";
    private final String SQL_QUERY_CALLHISTORY_BY_SPECIFIED_COLUMNS = "select * from CallHistory a where a.StorageType = ? and a.CellPhone_Address = ?";
    private final String SQL_QUERY_CONTACTER = "select FullName from PhoneBookContent where _id = (select Content_ID from PhoneNumberDetail where Number like ? limit 1)";
    private final String SQL_QUERY_CONTACTERS = "select a._id, a.FullName, a.StorageType, b.Number, b.Type from PhoneBookContent a inner join PhoneNumberDetail b on a._id = b.Content_ID where a.FullName in (select FullName from PhoneBookContent where CellPhone_Address = ? and StorageType=? group by FullName) and StorageType=? and CellPhone_Address = ? order by ";
    private final String SQL_QUERY_FULLNAME_BY_PHONENUM_CELLPHONEADDRESS = "select FullName from PhoneBookContent where _id in (select Content_ID from PhoneNumberDetail where Number = ?) and CellPhone_Address = ?";
    private final String SQL_QUERY_FULLNAME_LIKE_BY_PHONENUM_CELLPHONEADDRESS = "select a.FullName, b.Number from PhoneBookcontent a inner join phonenumberdetail b on a._id = b.Content_id where b.Number like ? and a.cellphone_address = ? order by b.Number;";
    private final String SQL_QUERY_PHONEBOOKCONTENT = "select * from PhoneBookContent where FullName like ? and StorageType = ? and CellPhone_Address = ?";
    private final String SQL_QUERY_PHONEBOOKCONTENT_BY_PHONENUM = "select * from PhoneBookContent where _id in (select Content_ID from PhoneNumberDetail where Number like ?) and StorageType = ? and CellPhone_Address = ?";
    private final String SQL_QUERY_PHONEDATA_BY_PAGE = "select a._id, a.FullName, a.StorageType, b.Number, b.Type from PhoneBookContent a inner join PhoneNumberDetail b on a._id = b.Content_ID where a.FullName in (select FullName from PhoneBookContent where CellPhone_Address = ? and StorageType=? group by FullName limit 10 offset ?) and StorageType=? and CellPhone_Address = ? order by FullName";
    private final String SQL_QUERY_PHONENUMBERDETAIL = "select a.*, b.TypeName from PhoneNumberDetail.a inner join PhoneType b on a.Type = b.Type where Number = ?";
    private final String SQL_QUERY_PHONETYPE_NAME = "select TypeName from PhoneType where Type = ? ";
    private final String SQL_QUERY_PHONE_BY_CONTENT_ID = "select a.*, b.TypeName as TypeName from PhoneNumberDetail a inner join PhoneType b on a.Type = b.Type where a.Content_ID = ?";
    private String TAG = "GoodocomPbapDb";
    private boolean mBataBaseDebug = false;
    private Context mContext;

    private GocPbapHelper(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 5);
        this.mContext = context;
        Log.e(this.TAG, "db_pbap constructor");
    }

    public static synchronized GocPbapHelper getInstance() {
        GocPbapHelper gocPbapHelper;
        synchronized (GocPbapHelper.class) {
            if (mInstance == null) {
                mInstance = new GocPbapHelper(GocApplication.INSTANCE);
            }
            gocPbapHelper = mInstance;
        }
        return gocPbapHelper;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase db) {
        Log.e(this.TAG, "db_pbap onCreate");
        db.execSQL("CREATE TABLE IF NOT EXISTS contacts (" + PHONEBOOK_CONTENT_FIELD[0] + " INTEGER primary key autoincrement, " + PHONEBOOK_CONTENT_FIELD[1] + " varchar(30), " + PHONEBOOK_CONTENT_FIELD[2] + " varchar(24), " + PHONEBOOK_CONTENT_FIELD[3] + " varchar(500), " + PHONEBOOK_CONTENT_FIELD[4] + " varchar(50), " + PHONEBOOK_CONTENT_FIELD[5] + " varchar(24), " + PHONEBOOK_CONTENT_FIELD[6] + " varchar(12), " + PHONEBOOK_CONTENT_FIELD[7] + " varchar(12), " + PHONEBOOK_CONTENT_FIELD[8] + " INTEGER, " + PHONEBOOK_CONTENT_FIELD[9] + " varchar(30), " + PHONEBOOK_CONTENT_FIELD[10] + " varchar(20), " + PHONEBOOK_CONTENT_FIELD[11] + " varchar(12), " + PHONEBOOK_CONTENT_FIELD[12] + " varchar(12), " + PHONEBOOK_CONTENT_FIELD[13] + " varchar(12), " + PHONEBOOK_CONTENT_FIELD[14] + " varchar(30), " + PHONEBOOK_CONTENT_FIELD[15] + " varchar(30), " + PHONEBOOK_CONTENT_FIELD[16] + " varchar(30), " + PHONEBOOK_CONTENT_FIELD[17] + " varchar(30), " + PHONEBOOK_CONTENT_FIELD[18] + " varchar(30), " + PHONEBOOK_CONTENT_FIELD[19] + " varchar(30), " + PHONEBOOK_CONTENT_FIELD[20] + " varchar(30), " + PHONEBOOK_CONTENT_FIELD[21] + " varchar(30), " + PHONEBOOK_CONTENT_FIELD[22] + " varchar(30), " + PHONEBOOK_CONTENT_FIELD[23] + " varchar(30));");
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS PhoneNumberDetail (");
        sb.append(PHONENUMBER_DETAIL_FIELD[0]);
        sb.append(" INTEGER primary key autoincrement, ");
        sb.append(PHONENUMBER_DETAIL_FIELD[1]);
        sb.append(" INTEGER, ");
        sb.append(PHONENUMBER_DETAIL_FIELD[2]);
        sb.append(" nvarchar(5), ");
        sb.append(PHONENUMBER_DETAIL_FIELD[3]);
        sb.append(" nvarchar(20),");
        sb.append(PHONENUMBER_DETAIL_FIELD[4]);
        sb.append(" nvarchar(16),FOREIGN KEY(");
        sb.append(PHONENUMBER_DETAIL_FIELD[1]);
        sb.append(") REFERENCES contacts(");
        sb.append(PHONEBOOK_CONTENT_FIELD[0]);
        sb.append(") );");
        db.execSQL(sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append("CREATE TABLE IF NOT EXISTS AddressDetail (");
        sb2.append(ADDRESS_DETAIL_FIELD[0]);
        sb2.append(" INTEGER primary key autoincrement, ");
        sb2.append(ADDRESS_DETAIL_FIELD[1]);
        sb2.append(" INTEGER, ");
        sb2.append(ADDRESS_DETAIL_FIELD[2]);
        sb2.append(" INTEGER, ");
        sb2.append(ADDRESS_DETAIL_FIELD[3]);
        sb2.append(" nvarchar(100),FOREIGN KEY(");
        sb2.append(ADDRESS_DETAIL_FIELD[1]);
        sb2.append(") REFERENCES contacts(");
        sb2.append(PHONEBOOK_CONTENT_FIELD[0]);
        sb2.append(") );");
        db.execSQL(sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append("CREATE TABLE IF NOT EXISTS EmailDetail (");
        sb3.append(EMAIL_DETAIL_FIELD[0]);
        sb3.append(" INTEGER primary key autoincrement, ");
        sb3.append(EMAIL_DETAIL_FIELD[1]);
        sb3.append(" INTEGER, ");
        sb3.append(EMAIL_DETAIL_FIELD[2]);
        sb3.append(" INTEGER, ");
        sb3.append(EMAIL_DETAIL_FIELD[3]);
        sb3.append(" nvarchar(100),FOREIGN KEY(");
        sb3.append(EMAIL_DETAIL_FIELD[1]);
        sb3.append(") REFERENCES contacts(");
        sb3.append(PHONEBOOK_CONTENT_FIELD[0]);
        sb3.append(") );");
        db.execSQL(sb3.toString());
        db.execSQL("CREATE TABLE IF NOT EXISTS PhoneType (" + PHONE_TYPE_FIELD[0] + " nvarchar(5) , " + PHONE_TYPE_FIELD[1] + " nvarchar(26) );");
        db.execSQL("CREATE TABLE IF NOT EXISTS call_log (" + CALLHISTORY_FIELD[0] + " INTEGER primary key autoincrement, " + CALLHISTORY_FIELD[1] + " nvarchar(30) not null, " + CALLHISTORY_FIELD[2] + " nvarchar(8) not null, " + CALLHISTORY_FIELD[3] + " nvarchar(30), " + CALLHISTORY_FIELD[4] + " nvarchar(30) not null, " + CALLHISTORY_FIELD[5] + " nvarchar(10) unique, " + CALLHISTORY_FIELD[6] + " nvarchar(30) , " + CALLHISTORY_FIELD[7] + " nvarchar(20), " + CALLHISTORY_FIELD[8] + " nvarchar(8), " + CALLHISTORY_FIELD[9] + " nvarchar(6));");
        StringBuilder sb4 = new StringBuilder();
        sb4.append("CREATE TABLE IF NOT EXISTS DevicesAddress (");
        sb4.append(DEVICE_ADDRESS_COUNT_FIELD[0]);
        sb4.append(" nvarchar(30), ");
        sb4.append(DEVICE_ADDRESS_COUNT_FIELD[1]);
        sb4.append(" INTEGER);");
        db.execSQL(sb4.toString());
        db.execSQL("drop table if exists CallHistory");
        db.execSQL("drop table if exists PhoneBookContent");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String str = this.TAG;
        Log.e(str, "db_pbap onUpgrade: " + oldVersion + "  newVersion " + newVersion);
        db.execSQL("drop table if exists contacts");
        db.execSQL("drop table if exists PhoneNumberDetail");
        db.execSQL("drop table if exists AddressDetail");
        db.execSQL("drop table if exists call_log");
        db.execSQL("drop table if exists DevicesAddress");
        db.execSQL("drop table if exists CallHistory");
        db.execSQL("drop table if exists PhoneBookContent");
        onCreate(db);
    }

    public void setCollectByID(SQLiteDatabase db, int _id, String value) {
        Log.e("coll", "_id: " + _id + "   value: " + value);
        ContentValues values = new ContentValues();
        values.put(COLLEXTION, value);
        db.update(PHONEBOOK_CONTENT, values, "_id=?", new String[]{String.valueOf(_id)});
    }

    public synchronized int deleteVcardInfo(SQLiteDatabase db, String address) {
        if (!TextUtils.isEmpty(address)) {
            if (db != null) {
                int delCount_Content = db.delete(PHONEBOOK_CONTENT, "addr=?", new String[]{address});
                int delCount_detail = db.delete(PHONENUMBER_DETAIL, "addr=?", new String[]{address});
                String str = this.TAG;
                Log.e(str, "delCount_Content: " + delCount_Content + "   delCount_detail " + delCount_detail);
                return delCount_Content;
            }
        }
        return -1;
    }

    public synchronized int deleteVcardInfo(SQLiteDatabase db, String address, int StorageType) {
        if (!TextUtils.isEmpty(address)) {
            if (db != null) {
                int delCount_Content = 0 + db.delete(PHONEBOOK_CONTENT, "addr=? and StorageType=?", new String[]{address, String.valueOf(StorageType)});
                db.delete(PHONENUMBER_DETAIL, "addr=?", new String[]{address});
                deleteDeviceAddress(db, address);
                String str = this.TAG;
                Log.e(str, "delCount_Content> " + delCount_Content + "  address " + address);
                return delCount_Content;
            }
        }
        return -1;
    }

    public int deleteCallHistoryInfo(SQLiteDatabase db, String address, int StorageType) {
        return db.delete(CALLHISTORY_CONTENT, "addr = ? and StorageType = ?", new String[]{address, String.valueOf(StorageType)});
    }

    public int deleteAllCallHistoryInfo(SQLiteDatabase db, String address) {
        return db.delete(CALLHISTORY_CONTENT, "addr = ?", new String[]{address});
    }

    public synchronized void insertVcardInfo(SQLiteDatabase db, List<VCardPack> vCardPackList) {
        ContentValues cv;
        int size;
        ContentValues cv2;
        int size2;
        List<VCardPack> list = vCardPackList;
        synchronized (this) {
            if (!(db == null || list == null)) {
                int size3 = vCardPackList.size();
                String str = this.TAG;
                Log.e(str, "insertVcardInfo vCardPackList>>>>>>" + vCardPackList.size());
                if (size3 != 0) {
                    ContentValues cv3 = new ContentValues();
                    db.beginTransaction();
                    int i = 0;
                    while (i < size3) {
                        VCardPack vcardPack = list.get(i);
                        if (vcardPack != null) {
                            String name = vcardPack.getFullName();
                            cv3.put(NAME, name);
                            String pinyins = getPinyin(name);
                            String first = getFisrtPinyin(name);
                            if (this.mBataBaseDebug) {
                                Log.e("first", "first :: " + first + "   pinyins  " + pinyins);
                            }
                            if (!TextUtils.isEmpty(pinyins)) {
                                cv3.put(PINYIN, pinyins);
                            }
                            if (!TextUtils.isEmpty(first)) {
                                cv3.put(SZM, first);
                            }
                            cv3.put(ADDRESS, vcardPack.getCellPhone_Address());
                            cv3.put(PHONEBOOK_StorageType, vcardPack.getStorageType());
                            cv3.put(IMGPATH, vcardPack.photoPath);
                            cv3.put(NUMBER, vcardPack.getNumber());
                            cv3.put(TYPE, vcardPack.getType());
                            long row = db.insertWithOnConflict(PHONEBOOK_CONTENT, null, cv3, 5);
                            for (PhoneInfo phoneInfo : vcardPack.getPhoneNumbers()) {
                                ContentValues _cv = new ContentValues();
                                _cv.put("Content_ID", Long.valueOf(row));
                                _cv.put(TYPE, phoneInfo.getPhoneType());
                                String nm = phoneInfo.getPhoneNumber();
                                if (this.mBataBaseDebug) {
                                    size2 = size3;
                                    StringBuilder sb = new StringBuilder();
                                    cv2 = cv3;
                                    sb.append("nm: ");
                                    sb.append(nm);
                                    Log.e("inseter", sb.toString());
                                } else {
                                    size2 = size3;
                                    cv2 = cv3;
                                }
                                _cv.put(NUMBER, nm);
                                _cv.put(ADDRESS, vcardPack.getCellPhone_Address());
                                db.insertWithOnConflict(PHONENUMBER_DETAIL, null, _cv, 5);
                                size3 = size2;
                                cv3 = cv2;
                            }
                            size = size3;
                            cv = cv3;
                        } else {
                            size = size3;
                            cv = cv3;
                            Log.e("full", "vcard :: null");
                        }
                        i++;
                        list = vCardPackList;
                        size3 = size;
                        cv3 = cv;
                    }
                    db.setTransactionSuccessful();
                    db.endTransaction();
                    try {
                        this.mContext.getContentResolver().notifyChange(MyContentProvider.CONTACTS_CONTENT_URI, null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public String getPinyin(String name) {
        if (TextUtils.isEmpty(name)) {
            return BuildConfig.FLAVOR;
        }
        return CharacterParser.getInstance().getSelling(name);
    }

    public String getFisrtPinyin(String name) {
        if (TextUtils.isEmpty(name)) {
            return BuildConfig.FLAVOR;
        }
        return HanyuPinyinHelper.getFirstLettersUp(name);
    }

    public void insertCallHistoryInfo(SQLiteDatabase db, List<VCardPack> list) {
        int size;
        if (db != null && (size = list.size()) != 0) {
            ContentValues cv = new ContentValues();
            db.beginTransaction();
            for (int i = 0; i < size; i++) {
                VCardPack vcardPack = list.get(i);
                if (vcardPack != null) {
                    String name = vcardPack.getFullName();
                    String str = BuildConfig.FLAVOR;
                    cv.put(NAME, name == null ? str : vcardPack.getFullName());
                    cv.put(ADDRESS, vcardPack.getCellPhone_Address() == null ? str : vcardPack.getCellPhone_Address());
                    cv.put(TYPE, vcardPack.getStorageType());
                    for (PhoneInfo phoneInfo : vcardPack.getPhoneNumbers()) {
                        cv.put(PHONEBOOK_StorageType, phoneInfo.getPhoneType() == null ? str : phoneInfo.getPhoneType());
                        cv.put(NUMBER, phoneInfo.getPhoneNumber() == null ? str : phoneInfo.getPhoneNumber());
                    }
                    cv.put(HISTORYDATE, vcardPack.getHistoryDate() == null ? str : vcardPack.getHistoryDate());
                    Log.e("calltime", "vcardPack.getHistoryTime(): " + vcardPack.getHistoryTime());
                    if (vcardPack.getHistoryTime() != null) {
                        str = vcardPack.getHistoryTime();
                    }
                    cv.put(TIME, str);
                    db.insert(CALLHISTORY_CONTENT, null, cv);
                }
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            try {
                this.mContext.getContentResolver().notifyChange(MyContentProvider.CALLLOG_URI, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized List<Contacts> queryAll(SQLiteDatabase db, String address, String storageType) {
        List<Contacts> items = new ArrayList<>();
        if (db != null && !TextUtils.isEmpty(address)) {
            if (!TextUtils.isEmpty(storageType)) {
                Cursor cursor = db.query(PHONEBOOK_CONTENT, null, "addr=? and StorageType=?", new String[]{address, storageType}, null, null, null);
                if (cursor == null) {
                    return items;
                }
                Log.e("need", "queryAll>>>>>" + cursor.getCount() + "address: " + address + "  storageType: " + storageType);
                if (cursor.moveToFirst()) {
                    do {
                        Contacts pack = new Contacts();
                        pack.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                        pack.setPinyin(cursor.getString(cursor.getColumnIndex(PINYIN)));
                        if (!TextUtils.isEmpty(pack.getPinyin()) && pack.getPinyin().length() > 0) {
                            String szm = cursor.getString(cursor.getColumnIndex(SZM));
                            if (!TextUtils.isEmpty(szm)) {
                                pack.setFirstWord(szm);
                            }
                        }
                        pack.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                        if (cursor.getColumnIndex(COLLEXTION) == -1) {
                            pack.setCollection(0);
                        } else {
                            pack.setCollection(cursor.getInt(cursor.getColumnIndex(COLLEXTION)));
                        }
                        int photoPathIndex = cursor.getColumnIndex(IMGPATH);
                        if (photoPathIndex == -1) {
                            pack.setImgPath(null);
                        } else {
                            pack.setImgPath(cursor.getString(photoPathIndex));
                        }
                        String _number = cursor.getString(cursor.getColumnIndex(NUMBER));
                        String _type = cursor.getString(cursor.getColumnIndex(TYPE));
                        pack.setNumberJSON(_number);
                        pack.setTypeJSON(_type);
                        Log.e("cons", "pack : " + pack.getNumberJSON() + "  pack:  " + pack.getTypeJSON());
                        items.add(pack);
                    } while (cursor.moveToNext());
                }
                cursor.close();
                return items;
            }
        }
        return items;
    }

    public List<Collection> queryAllCollections(SQLiteDatabase db, String address) {
        if (db == null || TextUtils.isEmpty(address)) {
            return null;
        }
        Cursor cursor = db.query(PHONEBOOK_CONTENT, null, "addr=? and collection=?", new String[]{address, "1"}, null, null, null);
        String str = this.TAG;
        Log.d(str, "queryAllCollections:" + address);
        if (cursor == null) {
            Log.e(this.TAG, "c cursor is null");
            return null;
        }
        List<Collection> items = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Collection pack = new Collection();
                int _id = cursor.getInt(cursor.getColumnIndex(ID));
                pack.setId(_id);
                pack.setCid(_id);
                pack.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                String _number = cursor.getString(cursor.getColumnIndex(NUMBER));
                String _type = cursor.getString(cursor.getColumnIndex(TYPE));
                pack.setNumberJSON(_number);
                pack.setTypeJSON(_type);
                items.add(pack);
                String str2 = this.TAG;
                Log.e(str2, "_number : " + _number + "   " + _type);
            } while (cursor.moveToNext());
        }
        return items;
    }

    public synchronized List<CallLogs> queryAllCalllog(SQLiteDatabase db, String address) {
        String str = this.TAG;
        Log.d(str, "queryAllCalllog:" + address);
        String str2 = this.TAG;
        Log.d(str2, "sql:select a._id, a.name, a.StorageType, a.number, a.type, a.HistoryDate, a.time from call_log a where a.addr = ? order by a.time");
        Cursor cursor = db.rawQuery("select a._id, a.name, a.StorageType, a.number, a.type, a.HistoryDate, a.time from call_log a where a.addr = ? order by a.time", new String[]{address});
        String str3 = this.TAG;
        Log.d(str3, "rawQuery over:" + address);
        if (cursor == null) {
            Log.d(this.TAG, "rcursor is null");
            return null;
        }
        List<CallLogs> items = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                CallLogs pack = new CallLogs();
                pack.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                pack.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                pack.setNumber(cursor.getString(cursor.getColumnIndex(NUMBER)));
                pack.setTime(cursor.getString(cursor.getColumnIndex(TIME)));
                pack.setType(cursor.getInt(cursor.getColumnIndex(TYPE)));
                items.add(pack);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return items;
    }

    public void deleteContacterById(SQLiteDatabase db, int id) {
        db.delete(PHONENUMBER_DETAIL, "Content_ID=?", new String[]{String.valueOf(id)});
        db.delete(PHONEBOOK_CONTENT, "_id=?", new String[]{String.valueOf(id)});
        try {
            this.mContext.getContentResolver().notifyChange(MyContentProvider.CONTACTS_CONTENT_URI, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCallHistoryById(SQLiteDatabase db, int id) {
        db.delete(CALLHISTORY_CONTENT, "_id=?", new String[]{String.valueOf(id)});
        try {
            this.mContext.getContentResolver().notifyChange(MyContentProvider.CALLLOG_URI, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int queryTotalAmount(SQLiteDatabase db, String address, String type) {
        Cursor cursor = db.rawQuery("select a.name from contacts a where a.addr=? and StorageType=? group by a.name", new String[]{address, type});
        int amount = cursor.getCount();
        cursor.close();
        return amount;
    }

    public synchronized String queryNameByNum(SQLiteDatabase db, String address, String phoneNum) {
        String str = this.TAG;
        Log.d(str, "queryNameByPhoneNum ,addr : " + address + " , num : " + phoneNum);
        if (TextUtils.isEmpty(address)) {
            return "N/A";
        }
        Cursor cursor = db.query(PHONENUMBER_DETAIL, new String[]{"Content_ID, number"}, "number=?", new String[]{phoneNum}, null, null, null);
        Cursor cursor1 = db.query(PHONEBOOK_CONTENT, new String[]{"_id, name"}, "addr=?", new String[]{address}, null, null, null);
        if (cursor != null) {
            if (cursor.getCount() <= 0) {
                if (!cursor.isClosed()) {
                    cursor.close();
                }
                if (!cursor1.isClosed()) {
                    cursor1.close();
                }
                return "N/A";
            } else if (cursor.moveToFirst()) {
                do {
                    String number = cursor.getString(cursor.getColumnIndex(NUMBER));
                    if (this.mBataBaseDebug) {
                        String str2 = this.TAG;
                        Log.e(str2, "query number  " + number + "     phoneNum  " + phoneNum);
                    }
                    if (number.equals(phoneNum)) {
                        int id = cursor.getInt(cursor.getColumnIndex("Content_ID"));
                        if (this.mBataBaseDebug) {
                            String str3 = this.TAG;
                            Log.e(str3, "id " + id + "   cursor1.getCount():  " + cursor1.getCount());
                        }
                        if (cursor1.getCount() > 0 && cursor1.moveToFirst()) {
                            while (id != cursor1.getInt(cursor1.getColumnIndex(ID))) {
                                if (!cursor1.moveToNext()) {
                                }
                            }
                            String name = cursor1.getString(cursor1.getColumnIndex(NAME));
                            String str4 = this.TAG;
                            Log.d(str4, "queryNameByPhoneNum : " + name);
                            return name;
                        }
                    }
                } while (cursor.moveToNext());
            }
        }
        return "N/A";
    }

    public void deleteAllTableContent(SQLiteDatabase db) {
        if (db == null) {
            db = getWritableDatabase();
        }
        db.delete(PHONENUMBER_DETAIL, null, null);
        db.delete(PHONEBOOK_CONTENT, null, null);
        db.delete(CALLHISTORY_CONTENT, null, null);
        try {
            this.mContext.getContentResolver().notifyChange(MyContentProvider.CONTACTS_CONTENT_URI, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            this.mContext.getContentResolver().notifyChange(MyContentProvider.CALLLOG_URI, null);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            this.mContext.getContentResolver().notifyChange(MyContentProvider.PHONENUMBER_DETAIL_URI, null);
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    public void deleteAllTableContentByType(SQLiteDatabase db, String str_table) {
        db.delete(str_table, null, null);
        if (str_table.equals(PHONEBOOK_CONTENT)) {
            db.delete(PHONENUMBER_DETAIL, null, null);
        }
        try {
            this.mContext.getContentResolver().notifyChange(MyContentProvider.PHONENUMBER_DETAIL_URI, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized boolean queryHasCalllog(SQLiteDatabase db, String address) {
        if (TextUtils.isEmpty(address)) {
            return false;
        }
        boolean z = true;
        Cursor cursor = db.query(CALLHISTORY_CONTENT, null, "addr=?", new String[]{address}, null, null, null);
        if (cursor == null) {
            return false;
        }
        int count = cursor.getCount();
        Log.e("calllog", "database_count: " + count);
        cursor.close();
        if (count <= 0) {
            z = false;
        }
        return z;
    }

    public boolean alreadyDownPhoneBook(SQLiteDatabase db, String address) {
        Cursor cursor = db.query(PHONEBOOK_CONTENT, new String[]{"_id, addr"}, "addr=?", new String[]{address}, null, null, null);
        Log.e("need", "cursor : " + cursor);
        if (cursor != null) {
            Log.e("need", "cursor.getCount() : " + cursor.getCount());
            if (cursor.getCount() > 0) {
                String str = this.TAG;
                Log.d(str, "alreadyDownPhoneBook : " + address + ", count : " + cursor.getCount());
                cursor.close();
                return true;
            }
        }
        cursor.close();
        return false;
    }

    public synchronized void insertDeviceAddress(SQLiteDatabase db, String address) {
        String str = this.TAG;
        Log.d(str, "insertDeviceAddress  address : " + address);
        if (!TextUtils.isEmpty(address)) {
            ContentValues cv = new ContentValues();
            cv.put(ADDRESS, address);
            db.insert(DEVICESADDRESS, null, cv);
        }
    }

    public void deleteDeviceAddress(SQLiteDatabase db, String address) {
        String str = this.TAG;
        Log.e(str, "deleteDeviceAddress:::: " + address);
        if (!TextUtils.isEmpty(address) && db != null) {
            db.delete(DEVICESADDRESS, "addr=?", new String[]{address});
        }
    }

    public boolean hasDevicePhonebookByAddress(SQLiteDatabase db, String address) {
        String str = this.TAG;
        Log.d(str, "hasDevicePhonebookByAddress  address : " + address);
        if (TextUtils.isEmpty(address)) {
            return false;
        }
        Cursor cursor = db.query(DEVICESADDRESS, null, null, null, null, null, null);
        if (cursor != null) {
            if (cursor.getCount() <= 0) {
                cursor.close();
                return false;
            } else if (cursor.moveToFirst()) {
                do {
                    String addressTemp = cursor.getString(cursor.getColumnIndex(ADDRESS));
                    if (!TextUtils.isEmpty(addressTemp) && addressTemp.equals(address)) {
                        cursor.close();
                        return true;
                    }
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        return false;
    }

    public String[] queryAlreadyDownLoadDevice(SQLiteDatabase db) {
        Log.d(this.TAG, "queryAlreadyDownLoadDevice");
        String[] addresss = new String[2];
        Cursor cursor = db.query(DEVICESADDRESS, null, null, null, null, null, null);
        Cursor cursor1 = db.query(PHONEBOOK_CONTENT, null, null, null, null, null, null);
        int num = 0;
        if (cursor == null && cursor1 == null) {
            return addresss;
        }
        String str = this.TAG;
        Log.d(str, "queryAlreadyDownLoadDevice0 : " + cursor.getCount() + ", " + cursor1.getCount());
        if (cursor.getCount() <= 0 || !cursor.moveToNext()) {
            cursor.close();
            String str2 = this.TAG;
            Log.d(str2, "queryAlreadyDownLoadDevice : addresss[0] : " + addresss[0] + ", addresss[1]" + addresss[1]);
            return addresss;
        }
        String str3 = this.TAG;
        Log.d(str3, "queryAlreadyDownLoadDevice : " + cursor.getCount() + ", " + cursor1.getCount());
        do {
            String address = cursor.getString(cursor.getColumnIndex(ADDRESS));
            cursor1.moveToFirst();
            while (true) {
                if (!cursor1.moveToNext()) {
                    break;
                }
                String address1 = cursor1.getString(cursor1.getColumnIndex(ADDRESS));
                if (!address.equals(address1) || (num > 0 && address1.equals(addresss[num - 1]))) {
                }
            }
            addresss[num] = cursor.getString(cursor.getColumnIndex(ADDRESS));
            String str4 = this.TAG;
            Log.d(str4, "Device_Address address[" + num + "] : " + addresss[num]);
            num++;
            if (num >= 2) {
                return addresss;
            }
        } while (cursor.moveToNext());
        cursor.close();
        String str2 = this.TAG;
        Log.d(str2, "queryAlreadyDownLoadDevice : addresss[0] : " + addresss[0] + ", addresss[1]" + addresss[1]);
        return addresss;
    }

    public Set<String> queryDevices(SQLiteDatabase db) {
        Cursor cursor;
        if (db == null || (cursor = db.query(PHONEBOOK_CONTENT, new String[]{ADDRESS}, null, null, null, null, null)) == null || cursor.getCount() == 0) {
            return null;
        }
        HashSet<String> hashSet = new HashSet<>();
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            hashSet.add(cursor.getString(cursor.getColumnIndex(ADDRESS)));
        }
        Log.e("has", "hashSet>>>> " + hashSet.size());
        cursor.close();
        return hashSet;
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x00dc  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00e5  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int queryHasTwoDevices(android.database.sqlite.SQLiteDatabase r13) {
        /*
        // Method dump skipped, instructions count: 257
        */
        throw new UnsupportedOperationException("Method not decompiled: com.goodocom.gocDataBase.GocPbapHelper.queryHasTwoDevices(android.database.sqlite.SQLiteDatabase):int");
    }
}

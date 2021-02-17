package com.goodocom.bttek.util.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.goodocom.bttek.bt.res.PhoneInfo;
import com.goodocom.bttek.bt.res.VCardList;
import com.goodocom.bttek.bt.res.VCardPack;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DbHelper extends SQLiteOpenHelper {
    private static final String CALLHISTORY_CONTENT = "CallHistory";
    private static final String DATABASE_NAME = "db_pbap";
    private static final int DATABASE_VERSION = 1;
    private static final String PHONEBOOK_CONTENT = "PhoneBookContent";
    public static final String[] PHONEBOOK_CONTENT_FIELD = {"_id", "FullName", "FirstName", "LastName", "First_StreetAddress", "First_CityNameAddress", "First_FederalStateAddress", "First_ZipCodeAddress", "First_CountryAddress", "Second_StreetAddress", "Second_CityNameAddress", "Second_FederalStateAddress", "Second_ZipCodeAddress", "Second_CountryAddress", "CellPhone_Address", "StorageType"};
    private static final String PHONENUMBER_DETAIL = "PhoneNumberDetail";
    public static final String[] PHONENUMBER_DETAIL_FIELD = {"_id", "Content_ID", "Type", "Number"};
    private static final String PHONE_TYPE = "PhoneType";
    public static final String[] PHONE_TYPE_FIELD = {"Type", "TypeName"};
    private final boolean D = true;
    private final String SQL_DELETE_CONTACTER = "delete from PhoneBookContent where FullName = ?";
    private final String SQL_DELETE_PHONENUMBER = "delete from PhoneNumberDetail where Number = ?";
    private final String SQL_DELETE_PHONENUMBER_BY_FULLNAME = "delete from PhoneNumberDetail where Content_ID in (select _id from PhoneBookContent where FullName = ?)";
    private final String SQL_EXPRESS_TOTAL = "select a.FullName from PhoneBookContent a where a.CellPhone_Address=? and StorageType=? group by a.FullName";
    private final String SQL_QUERY_CALLHISTORY_BY_ADDRESS_STORAGETYPE = "select a._id, a.FullName, a.StorageType, a.PhoneNumber, a.PhoneType, a.HistoryDate, a.HistoryTime from CallHistory a where a.CellPhone_Address = ? and a.StorageType=? order by ";
    private final String SQL_QUERY_CALLHISTORY_BY_SPECIFIED_COLUMNS = "select * from CallHistory a where a.StorageType = ? and a.CellPhone_Address = ?";
    private final String SQL_QUERY_CONTACTER = "select FullName from PhoneBookContent where _id = (select Content_ID from PhoneNumberDetail where Number like ? limit 1)";
    private final String SQL_QUERY_CONTACTERS = "select a._id, a.FullName, a.StorageType, b.Number, b.Type from PhoneBookContent a inner join PhoneNumberDetail b on a._id = b.Content_ID where a.FullName in (select FullName from PhoneBookContent where CellPhone_Address = ? and StorageType=? group by FullName) and StorageType=? and CellPhone_Address = ? order by ";
    private final String SQL_QUERY_PHONEBOOKCONTENT = "select * from PhoneBookContent where FullName like ? and StorageType = ? and CellPhone_Address = ?";
    private final String SQL_QUERY_PHONEBOOKCONTENT_BY_PHONENUM = "select * from PhoneBookContent where _id in (select Content_ID from PhoneNumberDetail where Number like ?) and StorageType = ? and CellPhone_Address = ?";
    private final String SQL_QUERY_PHONEDATA_BY_PAGE = "select a._id, a.FullName, a.StorageType, b.Number, b.Type from PhoneBookContent a inner join PhoneNumberDetail b on a._id = b.Content_ID where a.FullName in (select FullName from PhoneBookContent where CellPhone_Address = ? and StorageType=? group by FullName limit 10 offset ?) and StorageType=? and CellPhone_Address = ? order by FullName";
    private final String SQL_QUERY_PHONENUMBERDETAIL = "select a.*, b.TypeName from PhoneNumberDetail.a inner join PhoneType b on a.Type = b.Type where Number = ?";
    private final String SQL_QUERY_PHONETYPE_NAME = "select TypeName from PhoneType where Type = ? ";
    private final String SQL_QUERY_PHONE_BY_CONTENT_ID = "select a.*, b.TypeName as TypeName from PhoneNumberDetail a inner join PhoneType b on a.Type = b.Type where a.Content_ID = ?";
    private String TAG = "nfore DBHelper";
    private Context m_context;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
        this.m_context = context;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS PhoneBookContent (" + PHONEBOOK_CONTENT_FIELD[0] + " INTEGER primary key autoincrement, " + PHONEBOOK_CONTENT_FIELD[1] + " varchar(16), " + PHONEBOOK_CONTENT_FIELD[2] + " varchar(8), " + PHONEBOOK_CONTENT_FIELD[3] + " varchar(8), " + PHONEBOOK_CONTENT_FIELD[4] + " varchar(20), " + PHONEBOOK_CONTENT_FIELD[5] + " varchar(12), " + PHONEBOOK_CONTENT_FIELD[6] + " varchar(12), " + PHONEBOOK_CONTENT_FIELD[7] + " varchar(12), " + PHONEBOOK_CONTENT_FIELD[8] + " varchar(30), " + PHONEBOOK_CONTENT_FIELD[9] + " varchar(20), " + PHONEBOOK_CONTENT_FIELD[10] + " varchar(12), " + PHONEBOOK_CONTENT_FIELD[11] + " varchar(12), " + PHONEBOOK_CONTENT_FIELD[12] + " varchar(12), " + PHONEBOOK_CONTENT_FIELD[13] + " varchar(30), " + PHONEBOOK_CONTENT_FIELD[14] + " varchar(30) " + PHONEBOOK_CONTENT_FIELD[15] + " varchar(10) );");
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS PhoneNumberDetail (");
        sb.append(PHONENUMBER_DETAIL_FIELD[0]);
        sb.append(" INTEGER primary key autoincrement, ");
        sb.append(PHONENUMBER_DETAIL_FIELD[1]);
        sb.append(" INTEGER, ");
        sb.append(PHONENUMBER_DETAIL_FIELD[2]);
        sb.append(" nvarchar(5), ");
        sb.append(PHONENUMBER_DETAIL_FIELD[3]);
        sb.append(" nvarchar(20),FOREIGN KEY(");
        sb.append(PHONENUMBER_DETAIL_FIELD[1]);
        sb.append(") REFERENCES PhoneBookContent(");
        sb.append(PHONEBOOK_CONTENT_FIELD[0]);
        sb.append(") );");
        db.execSQL(sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append("CREATE TABLE IF NOT EXISTS PhoneType (");
        sb2.append(PHONE_TYPE_FIELD[0]);
        sb2.append(" nvarchar(5) , ");
        sb2.append(PHONE_TYPE_FIELD[1]);
        sb2.append(" nvarchar(26) );");
        db.execSQL(sb2.toString());
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public VCardList queryContacterInfo(SQLiteDatabase db, String fullName, String phoneNum, int StorageType, String address) {
        HashSet phoneInfos;
        DbHelper dbHelper = this;
        SQLiteDatabase sQLiteDatabase = db;
        boolean gotBoth = true;
        boolean gotName = fullName != null && fullName.trim().length() > 0;
        boolean gotNumber = phoneNum != null && phoneNum.trim().length() > 0;
        if (!gotName || !gotNumber) {
            gotBoth = false;
        }
        Cursor cursor = null;
        if (gotName) {
            cursor = dbHelper.queryContacterByFullName(sQLiteDatabase, fullName, StorageType, address);
        } else if (gotNumber) {
            cursor = dbHelper.queryContacterByPhoneNum(sQLiteDatabase, phoneNum, StorageType, address);
        }
        List<VCardPack> vcardPacks = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Cursor numCursor = dbHelper.queryPhoneNumberByContentId(sQLiteDatabase, cursor.getString(cursor.getColumnIndex("_id")));
                int idxNumer = numCursor.getColumnIndex("Number");
                int idxTypeName = numCursor.getColumnIndex("TypeName");
                HashSet phoneInfos2 = new HashSet();
                while (true) {
                    if (numCursor.moveToNext()) {
                        if (gotBoth && numCursor.getString(idxNumer).trim().indexOf(phoneNum) == -1) {
                            phoneInfos = phoneInfos2;
                            break;
                        }
                        PhoneInfo phoneInfo = new PhoneInfo();
                        phoneInfo.setPhoneNumber(numCursor.getString(idxNumer));
                        phoneInfo.setPhoneTypeName(numCursor.getString(idxTypeName));
                        phoneInfos2.add(phoneInfo);
                    } else {
                        phoneInfos = phoneInfos2;
                        break;
                    }
                }
                if (phoneInfos.size() > 0) {
                    VCardPack vcardPack = new VCardPack(cursor);
                    vcardPack.setPhoneNumbers(phoneInfos);
                    vcardPacks.add(vcardPack);
                }
                dbHelper = this;
                sQLiteDatabase = db;
            }
        }
        VCardList vcardList = new VCardList();
        vcardList.setVcardPacks(vcardPacks);
        return vcardList;
    }

    public Cursor queryPhoneTypeName(SQLiteDatabase db, String phoneType) {
        if (phoneType == null) {
            phoneType = "C";
        }
        return db.rawQuery("select TypeName from PhoneType where Type = ? ", new String[]{phoneType});
    }

    private Cursor queryContacterByFullName(SQLiteDatabase db, String fullName, int StorageType, String address) {
        return db.rawQuery("select * from PhoneBookContent where FullName like ? and StorageType = ? and CellPhone_Address = ?", new String[]{fullName + "%", "" + StorageType, address});
    }

    private Cursor queryContacterByPhoneNum(SQLiteDatabase db, String phoneNum, int StorageType, String address) {
        return db.rawQuery("select * from PhoneBookContent where _id in (select Content_ID from PhoneNumberDetail where Number like ?) and StorageType = ? and CellPhone_Address = ?", new String[]{"%" + phoneNum + "%", "" + StorageType, address});
    }

    public Cursor queryNumberDetailByPhoneNumber(SQLiteDatabase db, String phoneNum) {
        return db.rawQuery("select a.*, b.TypeName from PhoneNumberDetail.a inner join PhoneType b on a.Type = b.Type where Number = ?", new String[]{phoneNum});
    }

    public Cursor queryPhoneNumberByContentId(SQLiteDatabase db, String ContentId) {
        return db.rawQuery("select a.*, b.TypeName as TypeName from PhoneNumberDetail a inner join PhoneType b on a.Type = b.Type where a.Content_ID = ?", new String[]{ContentId});
    }

    public void deleteContacterByFullName(SQLiteDatabase db, String fullName) {
        db.execSQL("delete from PhoneBookContent where FullName = ?", new String[]{fullName});
    }

    public void deletePhoneNumber(SQLiteDatabase db, String phoneNum) {
        db.execSQL("delete from PhoneNumberDetail where Number = ?", new String[]{phoneNum});
    }

    public void deletePhoneNumberByFullName(SQLiteDatabase db, String fullName) {
        db.execSQL("delete from PhoneNumberDetail where Content_ID in (select _id from PhoneBookContent where FullName = ?)", new String[]{fullName});
    }

    /* JADX WARNING: Removed duplicated region for block: B:8:0x004a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int deleteVcardInfo(android.database.sqlite.SQLiteDatabase r13, java.lang.String r14, int r15) {
        /*
            r12 = this;
            r0 = 0
            java.lang.String r1 = "_id"
            java.lang.String[] r4 = new java.lang.String[]{r1}
            r1 = 2
            java.lang.String[] r6 = new java.lang.String[r1]
            r10 = 0
            r6[r10] = r14
            java.lang.String r2 = java.lang.String.valueOf(r15)
            r11 = 1
            r6[r11] = r2
            r2 = 0
            r9 = r2
            java.lang.String r9 = (java.lang.String) r9
            java.lang.String r3 = "PhoneBookContent"
            java.lang.String r5 = "CellPhone_Address=? and StorageType=?"
            r2 = r13
            r7 = r9
            r8 = r9
            android.database.Cursor r2 = r2.query(r3, r4, r5, r6, r7, r8, r9)
            r3 = 0
            int r4 = r2.getCount()
            if (r4 <= 0) goto L_0x0048
            boolean r4 = r2.moveToNext()
            if (r4 == 0) goto L_0x0048
            r4 = 0
        L_0x0031:
            java.lang.String[] r5 = new java.lang.String[r11]
            java.lang.String r6 = r2.getString(r10)
            r5[r10] = r6
            java.lang.String r6 = "PhoneNumberDetail"
            java.lang.String r7 = "Content_ID=?"
            int r5 = r13.delete(r6, r7, r5)
            int r3 = r3 + r5
            boolean r5 = r2.moveToNext()
            if (r5 != 0) goto L_0x0031
        L_0x0048:
            if (r3 < 0) goto L_0x005d
            java.lang.String[] r1 = new java.lang.String[r1]
            r1[r10] = r14
            java.lang.String r4 = java.lang.String.valueOf(r15)
            r1[r11] = r4
            java.lang.String r4 = "PhoneBookContent"
            java.lang.String r5 = "CellPhone_Address=? and StorageType=?"
            int r1 = r13.delete(r4, r5, r1)
            int r0 = r0 + r1
        L_0x005d:
            r2.close()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.goodocom.bttek.util.db.DbHelper.deleteVcardInfo(android.database.sqlite.SQLiteDatabase, java.lang.String, int):int");
    }

    public int deleteCallHistoryInfo(SQLiteDatabase db, String address, int StorageType) {
        return db.delete(CALLHISTORY_CONTENT, "CellPhone_Address=? and StorageType=?", new String[]{address, String.valueOf(StorageType)});
    }

    public void insertVcardInfo(SQLiteDatabase db, VCardPack vcardPack, int type) {
        ContentValues cv = new ContentValues();
        if (vcardPack != null) {
            cv.put("FullName", vcardPack.getFullName());
            cv.put("FirstName", vcardPack.getFirstName());
            cv.put("LastName", vcardPack.getLastName());
            cv.put("First_StreetAddress", vcardPack.getFirst_StreetAddress());
            cv.put("First_CityNameAddress", vcardPack.getFirst_CityNameAddress());
            cv.put("First_FederalStateAddress", vcardPack.getFirst_FederalStateAddress());
            cv.put("First_ZipCodeAddress", vcardPack.getFirst_ZipCodeAddress());
            cv.put("First_CountryAddress", vcardPack.getFirst_CountryAddress());
            cv.put("Second_StreetAddress", vcardPack.getSecond_StreetAddress());
            cv.put("Second_CityNameAddress", vcardPack.getSecond_CityNameAddress());
            cv.put("Second_FederalStateAddress", vcardPack.getSecond_FederalStateAddress());
            cv.put("Second_ZipCodeAddress", vcardPack.getSecond_ZipCodeAddress());
            cv.put("Second_CountryAddress", vcardPack.getSecond_CountryAddress());
            cv.put("CellPhone_Address", vcardPack.getCellPhone_Address());
            cv.put("StorageType", Integer.valueOf(type));
            String str = null;
            long row = db.insert(PHONEBOOK_CONTENT, str, cv);
            for (PhoneInfo phoneInfo : vcardPack.getPhoneNumbers()) {
                ContentValues _cv = new ContentValues();
                _cv.put("Content_ID", Long.valueOf(row));
                _cv.put("Type", phoneInfo.getPhoneType());
                _cv.put("Number", phoneInfo.getPhoneNumber());
                db.insert(PHONENUMBER_DETAIL, str, _cv);
            }
        }
    }

    public void insertCallHistoryInfo(SQLiteDatabase db, VCardPack vcardPack, int type) {
        ContentValues cv = new ContentValues();
        if (vcardPack != null) {
            cv.put("FullName", vcardPack.getFullName());
            cv.put("FirstName", vcardPack.getFirstName());
            cv.put("LastName", vcardPack.getLastName());
            cv.put("CellPhone_Address", vcardPack.getCellPhone_Address());
            cv.put("StorageType", Integer.valueOf(type));
            for (PhoneInfo phoneInfo : vcardPack.getPhoneNumbers()) {
                cv.put(PHONE_TYPE, phoneInfo.getPhoneType());
                cv.put("PhoneNumber", phoneInfo.getPhoneNumber());
            }
            cv.put("HistoryDate", vcardPack.getHistoryDate());
            cv.put("HistoryTime", vcardPack.getHistoryTime());
            db.insert(CALLHISTORY_CONTENT, null, cv);
        }
    }

    public List<VCardPack> queryPhoneDataByPage(SQLiteDatabase db, String address, int whichPage, int pageSize, String storageType) {
        Cursor cursor = db.rawQuery("select a._id, a.FullName, a.StorageType, b.Number, b.Type from PhoneBookContent a inner join PhoneNumberDetail b on a._id = b.Content_ID where a.FullName in (select FullName from PhoneBookContent where CellPhone_Address = ? and StorageType=? group by FullName limit 10 offset ?) and StorageType=? and CellPhone_Address = ? order by FullName", new String[]{address, storageType, String.valueOf(whichPage * pageSize), storageType, address});
        List<VCardPack> vcardPacks = collectionData(cursor);
        cursor.close();
        for (VCardPack pack : vcardPacks) {
            new HashSet();
            for (int x = 0; x < pack.getPhoneNumbers().size(); x++) {
                for (PhoneInfo phoneInfo : pack.getPhoneNumbers()) {
                    Cursor cursor2 = queryPhoneTypeName(db, phoneInfo.getPhoneType());
                    if (cursor2.moveToNext()) {
                        phoneInfo.setPhoneTypeName(cursor2.getString(0));
                    }
                    cursor2.close();
                }
            }
        }
        return vcardPacks;
    }

    public List<VCardPack> queryContactersInfo(SQLiteDatabase db, String address, String storageType, String columnName) {
        Cursor cursor = db.rawQuery("select a._id, a.FullName, a.StorageType, b.Number, b.Type from PhoneBookContent a inner join PhoneNumberDetail b on a._id = b.Content_ID where a.FullName in (select FullName from PhoneBookContent where CellPhone_Address = ? and StorageType=? group by FullName) and StorageType=? and CellPhone_Address = ? order by " + columnName, new String[]{address, storageType, storageType, address});
        List<VCardPack> vcardPacks = collectionData(cursor);
        cursor.close();
        for (VCardPack pack : vcardPacks) {
            new HashSet();
            for (int x = 0; x < pack.getPhoneNumbers().size(); x++) {
                for (PhoneInfo phoneInfo : pack.getPhoneNumbers()) {
                    Cursor cursor2 = queryPhoneTypeName(db, phoneInfo.getPhoneType());
                    if (cursor2.moveToNext()) {
                        phoneInfo.setPhoneTypeName(cursor2.getString(0));
                    }
                    cursor2.close();
                }
            }
        }
        return vcardPacks;
    }

    public VCardList callHistoryBySpecifiedColumns(SQLiteDatabase db, int storageType, String address, String historyDate, String historyTime, String phoneNumber) {
        ArrayList<String> queryStrings = new ArrayList<>();
        queryStrings.add(String.valueOf(storageType));
        queryStrings.add(address);
        String sql = "select * from CallHistory a where a.StorageType = ? and a.CellPhone_Address = ?";
        if (historyDate.trim().length() > 0) {
            sql = sql + " and a.HistoryDate like ?";
            queryStrings.add("%" + historyDate + "%");
        }
        if (historyTime.trim().length() > 0) {
            sql = sql + " and a.HistoryTime like ?";
            queryStrings.add("%" + historyTime + "%");
        }
        if (phoneNumber.trim().length() > 0) {
            sql = sql + " and a.PhoneNumber like ?";
            queryStrings.add("%" + phoneNumber + "%");
        }
        Cursor cursor = db.rawQuery(sql, (String[]) queryStrings.toArray(new String[queryStrings.size()]));
        List<VCardPack> vcardPacks = collectionData(cursor);
        cursor.close();
        VCardList vcardList = new VCardList();
        vcardList.setVcardPacks(vcardPacks);
        return vcardList;
    }

    public List<VCardPack> queryCallHistoryByAddressAndStorageType(SQLiteDatabase db, String address, String storageType, String orderByColumn) {
        Cursor cursor = db.rawQuery("select a._id, a.FullName, a.StorageType, a.PhoneNumber, a.PhoneType, a.HistoryDate, a.HistoryTime from CallHistory a where a.CellPhone_Address = ? and a.StorageType=? order by " + orderByColumn, new String[]{address, storageType});
        List<VCardPack> vcardPacks = collectionData(cursor);
        cursor.close();
        for (VCardPack pack : vcardPacks) {
            new HashSet();
            for (int x = 0; x < pack.getPhoneNumbers().size(); x++) {
                for (PhoneInfo phoneInfo : pack.getPhoneNumbers()) {
                    Cursor cursor2 = queryPhoneTypeName(db, phoneInfo.getPhoneType());
                    if (cursor2.moveToNext()) {
                        phoneInfo.setPhoneTypeName(cursor2.getString(0));
                    }
                    cursor2.close();
                }
            }
        }
        return vcardPacks;
    }

    private List<VCardPack> collectionData(Cursor cursor) {
        List<VCardPack> items = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                VCardPack pack = new VCardPack();
                pack.setFullName(cursor.getString(cursor.getColumnIndex("FullName")));
                pack.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
                PhoneInfo pInfo = new PhoneInfo();
                Set<PhoneInfo> set = new HashSet<>();
                String storageType = cursor.getString(cursor.getColumnIndex("StorageType"));
                int int_StorageType = Integer.parseInt(storageType);
                if (int_StorageType < 3 && int_StorageType > 0) {
                    pInfo.setPhoneNumber(cursor.getString(cursor.getColumnIndex("Number")));
                    pInfo.setPhoneType(cursor.getString(cursor.getColumnIndex("Type")));
                } else if (int_StorageType >= 3 && int_StorageType <= 5) {
                    pInfo.setPhoneNumber(cursor.getString(cursor.getColumnIndex("PhoneNumber")));
                    pInfo.setPhoneType(cursor.getString(cursor.getColumnIndex(PHONE_TYPE)));
                    pack.setHistoryDate(cursor.getString(cursor.getColumnIndex("HistoryDate")));
                    pack.setHistoryTime(cursor.getString(cursor.getColumnIndex("HistoryTime")));
                }
                set.add(pInfo);
                pack.setPhoneNumbers(set);
                pack.setStorageType(storageType);
                items.add(pack);
            } while (cursor.moveToNext());
        }
        return items;
    }

    public void deleteContacterById(SQLiteDatabase db, int id) {
        db.delete(PHONENUMBER_DETAIL, "Content_ID=?", new String[]{"" + id});
        db.delete(PHONEBOOK_CONTENT, "_id=?", new String[]{"" + id});
    }

    public void deleteCallHistoryById(SQLiteDatabase db, int id) {
        db.delete(CALLHISTORY_CONTENT, "_id=?", new String[]{"" + id});
    }

    public int queryTotalAmount(SQLiteDatabase db, String address, String type) {
        Cursor cursor = db.rawQuery("select a.FullName from PhoneBookContent a where a.CellPhone_Address=? and StorageType=? group by a.FullName", new String[]{address, type});
        int amount = cursor.getCount();
        cursor.close();
        return amount;
    }

    public String queryNameByPhoneNum(String phoneNum) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select FullName from PhoneBookContent where _id = (select Content_ID from PhoneNumberDetail where Number like ? limit 1)", new String[]{phoneNum + "%"});
        String contacter = "N/A";
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            contacter = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return contacter;
    }

    public void deleteAllTableContent(SQLiteDatabase db) {
        String str = null;
        String[] strArr = null;
        db.delete(PHONENUMBER_DETAIL, str, strArr);
        db.delete(PHONEBOOK_CONTENT, str, strArr);
        db.delete(CALLHISTORY_CONTENT, str, strArr);
    }
}

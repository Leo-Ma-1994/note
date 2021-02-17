package com.goodocom.bttek.util.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelperAvrcp extends SQLiteOpenHelper {
    private static boolean D = true;
    private static final String DATABASE_NAME = "db_avrcp14";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "nFore_DbHelperAvrcp";
    public final String TABLE_FOLDER_ITEMS = "FolderItems";
    public final String TABLE_MEDIA_ITEMS = "MediaItems";
    public final String TABLE_MEDIA_PLAYER_ITEMS = "MediaPlayerItems";
    public String clearFolderItems = "delete from FolderItems;";
    public String clearMediaItems = "delete from MediaItems;";
    public String clearMediaItemsByScopeId = "delete from MediaItems where ScopeId = ";
    public String clearMediaPlayerItems = "delete from MediaPlayerItems;";
    public String insertFolderItems = "insert into FolderItems value(?, ?, ?, ?, ?);";
    public String insertMediaItems = "insert into MediaItems(UIDcounter, UID, MediaType, Name) value(?, ?, ?, ?);";
    public String insertMediaPlayerItems = "insert into MediaPlayerItems value(?, ?, ?, ?, ?, ?, ?);";
    public String selectFolderItems = "select * from FolderItems order by FolderType, Name;";
    public String selectMediaItems = "select * from MediaItems where ScopeId = ? order by MediaType, Name;";
    public String selectMediaPlayerItems = "select * from MediaPlayerItems order by MajorPlayerType, Name;";
    public String updateMediaItems = "update MediaItems set Title = ? where uid = ?;";

    public DbHelperAvrcp(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase db) {
        if (D) {
            Log.d(TAG, "+++ Begin of onCreate() +++");
        }
        db.execSQL("create table if not exists MediaPlayerItems( _ID INTEGER primary key autoincrement, UIDcounter smallint, PlayerId smallint, MajorPlayerType blob, PlayerSubType int, PlayStatus blob, FeatureBitMask blob, Name nvarchar(20));");
        db.execSQL("create table if not exists FolderItems(_ID INTEGER primary key autoincrement, UIDcounter smallint, UID bigint, FolderType blob, IsPlayable blob, Name nvarchar(20));");
        db.execSQL("create table if not exists MediaItems(_ID INTEGER primary key autoincrement, ScopeId smallint, UIDcounter smallint, UID bigint, MediaType blob, Name nvarchar(20), Title nvarchar(20), Artist nvarchar(20), Album nvarchar(20), TrackNumber nvarchar(5), TotalTracks nvarchar(6), Genre nvarchar(10), Time_ms nvarchar(10));");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (D) {
            Log.d(TAG, "+++ Begin of onUpgrade() +++");
        }
        db.execSQL("DROP TABLE IF EXISTS MediaPlayerItems");
        db.execSQL("DROP TABLE IF EXISTS FolderItems");
        db.execSQL("DROP TABLE IF EXISTS MediaItems");
    }
}

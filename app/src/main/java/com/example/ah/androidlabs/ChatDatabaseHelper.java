package com.example.ah.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "ChatMessages.db";
    private final static int VERSION_NUM = 2;
    final static String TABLE_NAME = "CHAT_MESSAGES";
    final static String KEY_ID = "id";
    final static String KEY_MESSAGE = "message";


    private final static String ACTIVITY_NAME = "ChatDatabaseHelper";
    ChatDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(ACTIVITY_NAME, "Calling onCreate");
        final String query = " CREATE TABLE " + TABLE_NAME +
                "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_MESSAGE + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(ACTIVITY_NAME, "Calling onUpgrade" +
                "oldVersion:" + oldVersion + "newVersion:" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(ACTIVITY_NAME, "Calling onDowngrade" +
                "oldVersion:" + oldVersion + "newVersion:" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

package com.example.ah.androidlabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "ChatMessages.db";
    public final static String TABLE_NAME = "CHAT_MESSAGES";
    private final static int VERSION_NUM = 1;
    private final static String KEY_ID = "id";
    private final static String KEY_MESSAGE = "message";
    final SQLiteDatabase db = getWritableDatabase();

    protected final static String ACTIVITY_NAME = "ChatDatabaseHelper";
    public ChatDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("ChatDatabaseHelper", "Calling onCreate");
        final String query = "CREATE TABLE " + TABLE_NAME +
                " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_MESSAGE + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("ChatDatabaseHelper", "Calling onUpgrade" +
                "oldVersion:" + oldVersion + "newVersion:" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("ChatDatabaseHelper", "Calling onDowngrade" +
                "oldVersion:" + oldVersion + "newVersion:" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertMessage(String message) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_MESSAGE, message);
        db.insert(TABLE_NAME, "", contentValues);
        }

    public List<String> getAllMessage() {

        final List<String> messages = new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_MESSAGE},
                null, null,null, null, null, null);

        while(cursor.moveToNext()) {
            final String message = cursor.getString(cursor.getColumnIndex
                    (ChatDatabaseHelper.KEY_MESSAGE));
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + message);
            messages.add(message);
        }
        cursor.close();
        return messages;
    }
}

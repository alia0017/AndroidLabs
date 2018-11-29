package com.example.ah.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatWindow extends Activity {

    private EditText editText;
    private static List<String> arrayList = new ArrayList<>();
    private static ChatAdapter messageAdapter;
    private ChatDatabaseHelper dbHelper;
    private Boolean checkTablet;
    private static Cursor cursor;
    private static SQLiteDatabase db;

    protected static final String ACTIVITY_NAME = "ChatWindow";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(ACTIVITY_NAME, "User clicked Start Chat");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        checkTablet = ((findViewById(R.id.frame_layout)) !=null);
        dbHelper = new ChatDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        messageAdapter = new ChatAdapter(this);

        editText = findViewById(R.id.editTextId);
        ListView listView = findViewById(R.id.listViewId);

        moveCursor();
        Button sendButton = findViewById(R.id.button4);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(ChatDatabaseHelper.KEY_MESSAGE, editText.getText().toString());
                db.insert(ChatDatabaseHelper.TABLE_NAME, "", contentValues);
                moveCursor();
                editText.setText("");
            }
        });

        listView.setAdapter(messageAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle fragmentArgs = new Bundle();
                fragmentArgs.putLong("id",messageAdapter.getItemId(position));
                fragmentArgs.putString("message", String.valueOf(messageAdapter.getItem(position)));
                fragmentArgs.putBoolean("isTablet", checkTablet);
                if(checkTablet){
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ftrans = fm.beginTransaction();
                    MessageFragment mf = new MessageFragment();
                    mf.setArguments(fragmentArgs);
                    ftrans.replace(R.id.frame_layout, mf);
                    ftrans.commit();
                } else {
                    Intent intent = new Intent(ChatWindow.this, MessageDetails.class);
                    intent.putExtra("id", messageAdapter.getItemId(position));
                    intent.putExtra("message", String.valueOf(messageAdapter.getItem(position)));
                    startActivityForResult(intent,500);
                }
            }
        } );
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 500 && resultCode == RESULT_OK) {
            Long messageID = data.getLongExtra("sendIndex", -1);
            deleteMessage(messageID);
            Log.i(ACTIVITY_NAME, "Deleted: "+ messageID);
        }
    }

    public void deleteMessage (long id){
        Log.i("Delete this Message", dbHelper.TABLE_NAME);
        //db.execSQL("DELETE FROM " + dbHelper.TABLE_NAME + " WHERE " + dbHelper.KEY_ID + " = " + id);
        int numRowsDeleted = db.delete(dbHelper.TABLE_NAME, dbHelper.KEY_ID + " = " + id, null);
        moveCursor();
        Log.i(ACTIVITY_NAME, "Deleted: "+ numRowsDeleted);
        messageAdapter.notifyDataSetChanged();
    }

    private class ChatAdapter extends ArrayAdapter<String> {
        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        //returns the number of row
        public int getCount() {
            return arrayList.size();
        }

        //returns the item to show the list at the position
        public String getItem(int position) {
            return arrayList.get(position);
        }

        //returns the id of the item
        public long getItemId(int position) {
            cursor.moveToPosition(position);
            return cursor.getLong(cursor.getColumnIndex(ChatDatabaseHelper.KEY_ID));
        }

        //returns the layout that will be positioned at the row in the list
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result;
            if (position % 2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            TextView message = result.findViewById(R.id.message_text);
            message.setText(getItem(position));
            return result;
        }
    }

    @Override
    protected void onDestroy(){
        Log.i(ACTIVITY_NAME, "In onDestroy()");
        super.onDestroy();
        db.close();
    }

    public static void moveCursor() {
        arrayList.clear();
        cursor = db.query(true, ChatDatabaseHelper.TABLE_NAME,
                new String[]{ChatDatabaseHelper.KEY_ID, ChatDatabaseHelper.KEY_MESSAGE},
                null, null,null, null, null, null);
        while(cursor.moveToNext()) {
            arrayList.add(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + arrayList);
        }
    }
}

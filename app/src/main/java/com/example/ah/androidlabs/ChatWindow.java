package com.example.ah.androidlabs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {

    //Class Variables
    private EditText editText;
    private Button sendButton;
    private ListView listView;
    private static ArrayList<String> arrayList;
    private static ChatAdapter messageAdapter;

    protected static final String ACTIVITY_NAME = "ChatWindow";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(ACTIVITY_NAME, "User clicked Start Chat");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        // Initialize the variable textView
        editText = (EditText) findViewById(R.id.editTextId);
        // Initialize the variable listView
        listView = (ListView) findViewById(R.id.listViewId);
        // Initialize the variable sendButton
        sendButton = (Button) findViewById(R.id.button4);

        arrayList  = new ArrayList<>();
        //set the data source of the listView to be a new ChatAdapter object
        messageAdapter = new ChatAdapter(this);
        listView.setAdapter(messageAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.add(editText.getText().toString());
                //this restarts the process of getCount() & getView()
                messageAdapter.notifyDataSetChanged();
                editText.setText("");
            }
        });
    }

    private class ChatAdapter extends ArrayAdapter<String> {
        // Constructor
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

        //not using it
        public long getItemId(int position) {
            return position;
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
            message.setText(getItem(position)); // get the string at position
            return result;
        }
    }
}

package com.example.ah.androidlabs;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TestToolbar extends AppCompatActivity {
    private EditText sendMessage;
    private String currentMessage = "You selected item 1";

    protected static final String ACTIVITY_NAME = "TestToolbar";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        setContentView(R.layout.activity_test_toolbar);
        Toolbar lab8_toolBar = findViewById(R.id.lab8_toolbar);
        setSupportActionBar(lab8_toolBar);

        Button snackBar = findViewById(R.id.snackbar);
        snackBar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Snackbar.make(findViewById(R.id.snackbar), "Message to show", Snackbar.LENGTH_SHORT)
                        .show();
            }
        });
    }
    public boolean onCreateOptionsMenu (Menu m){
        getMenuInflater().inflate(R.menu.toolbar_menu, m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi){

        switch (mi.getItemId()){
            case R.id.option_one:
                Log.d("ToolBar","Option 1 selected");
                Snackbar.make(findViewById(R.id.lab8_toolbar), currentMessage, Snackbar.LENGTH_LONG)
                        .show();
                break;
            case R.id.option_two:
                AlertDialog.Builder builder = new AlertDialog.Builder(TestToolbar.this);
                builder.setTitle(R.string.dialog_title);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(TestToolbar.this, StartActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

                break;
            case R.id.option_three:
                final View view = getLayoutInflater().inflate(R.layout.insert_message,null);
                AlertDialog.Builder builder2 = new AlertDialog.Builder(TestToolbar.this);
                builder2.setTitle(R.string.dialog_title);
                builder2.setView(view);
                builder2.setPositiveButton(R.string.new_message, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        sendMessage = view.findViewById(R.id.new_message);
                        currentMessage = sendMessage.getText().toString();
                    }
                });
                builder2.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                AlertDialog dialog2 = builder2.create();
                dialog2.show();
                break;
            case R.id.about:
                Toast.makeText(this, "Version 1.0 by Ahmad", Toast.LENGTH_SHORT)
                        .show();
                break;
        }
        return true;
    }
}

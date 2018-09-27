package com.example.ah.androidlabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemsActivity extends Activity {

    private Switch      switchButton;
    private ImageButton imgButton;
    private CheckBox    checkButton;

    protected static final String ACTIVITY_NAME = "ListItemsActivity";
    protected static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(ACTIVITY_NAME, "In onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        imgButton = findViewById(R.id.imageButton);
        imgButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        switchButton = findViewById(R.id.switch1);
        switchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setOnCheckedChange();
            }
        });

        checkButton = findViewById(R.id.checkBox);
        checkButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent(  );
                resultIntent.putExtra("Response", "Here is my response");
                setResult(Activity.RESULT_OK, resultIntent);
                OnCheckChanged();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgButton.setImageBitmap(imageBitmap);
        }
    }

    protected void setOnCheckedChange() {
        CharSequence textOn = "Switch is On";// "Switch is On"
        CharSequence textOff = "Switch is Off";// "Switch is Off"
        int duration = Toast.LENGTH_SHORT; //= Toast.LENGTH_LONG if Off

        if (switchButton.isChecked()) {
            Toast toast = Toast.makeText(this, textOn, duration); //this is the ListActivity
            toast.show(); //display your message box
        } else {
            Toast toast = Toast.makeText(this, textOff, duration); //this is the ListActivity
            toast.show(); //display your message box
        }
    }

    protected void OnCheckChanged() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
        builder.setMessage(R.string.dialog_message);

                builder.setTitle(R.string.dialog_title);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });

                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                builder.show();
    }

    @Override
    protected void onResume(){
        Log.i(ACTIVITY_NAME, "In onResume()");
        super.onResume();
    }

    @Override
    protected void onStart(){
        Log.i(ACTIVITY_NAME, "In onStart()");
        super.onStart();
    }

    @Override
    protected void onPause(){
        Log.i(ACTIVITY_NAME, "In onPause()");
        super.onPause();
    }

    @Override
    protected void onStop(){
        Log.i(ACTIVITY_NAME, "In onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        Log.i(ACTIVITY_NAME, "In onDestroy()");
        super.onDestroy();
    }
}

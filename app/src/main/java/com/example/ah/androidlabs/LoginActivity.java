package com.example.ah.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

    private final static String MY_PREF = "MY_PREF";
    private final static String DEFAULT_EMAIL_KEY = "DEFAULT_EMAIL_KEY";

    private Button loginButton;
    private EditText emailEditText;
    private SharedPreferences sharedPref;

    protected static final String ACTIVITY_NAME = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(ACTIVITY_NAME, "In onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // reference loginButton
        loginButton = findViewById(R.id.button2);
        // reference emailEditText
        emailEditText = findViewById(R.id.emailId);

        //initial or get SharedPerfence
        sharedPref = getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        //above line is same, just the difference of String
        //sharedPref = getSharedPreferences(getString(R.string.my_pref), Context.MODE_PRIVATE);

        //get value from SharedPref and save it into a variable
        String defaultEmail = sharedPref.getString(DEFAULT_EMAIL_KEY, "email@domain.com");
        emailEditText.setText(defaultEmail);

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intent);

            }
        });
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

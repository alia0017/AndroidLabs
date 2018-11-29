package com.example.ah.androidlabs;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MessageDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);
        Bundle infoToPass  = getIntent().getExtras();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ftrans = fm.beginTransaction();
        MessageFragment mf = new MessageFragment();
        mf.setArguments(infoToPass);
        ftrans.replace(R.id.empty_frame, mf);
        ftrans.commit();
    }
}

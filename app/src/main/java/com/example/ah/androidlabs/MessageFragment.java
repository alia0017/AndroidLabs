package com.example.ah.androidlabs;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MessageFragment extends Fragment {

    private Bundle infoToPass;
    private ChatWindow chatWindow;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        infoToPass = this.getArguments();
        chatWindow = new ChatWindow();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_message_fragment, container, false);

        TextView idView = view.findViewById(R.id.id_here);
        TextView messageView = view.findViewById(R.id.message_here);


        idView.setText(Long.toString(infoToPass.getLong("id")));
        messageView.setText(infoToPass.getString("message"));

        Log.i("ID Selected", Long.toString(infoToPass.getLong("id")));
        Log.i("Message", infoToPass.getString("message"));

        Button deleteButton = view.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (infoToPass.getBoolean("isTablet")) {
                    chatWindow.deleteMessage(infoToPass.getLong("id"));
                    getFragmentManager().beginTransaction().remove(MessageFragment.this).commit();

                    /* Long Version
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ftrans = fm.beginTransaction();
                    ftrans.remove(this);
                    ftrans.commit();*/
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("sendIndex", infoToPass.getLong("id", 0));
                    getActivity().setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                }
            }
        });
        return view;
    }
}

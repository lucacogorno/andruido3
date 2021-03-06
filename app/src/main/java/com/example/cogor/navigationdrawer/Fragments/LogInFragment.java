package com.example.cogor.navigationdrawer.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.cogor.navigationdrawer.R;
import com.example.cogor.navigationdrawer.Tasks.LogInTask;

/**
 * Created by cogor on 06/09/2017.
 */

public class LogInFragment extends Fragment {

    View myView;
    TextView username;
    TextView password;
    String usr;
    String psw;
    Button loginButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.login2, container, false);

        username = (TextView) myView.findViewById(R.id.logUsername);
        password = (TextView) myView.findViewById(R.id.logPsw);
        loginButton = (Button) myView.findViewById(R.id.signInButton);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usr = username.getText().toString();
                psw = password.getText().toString();


                new LogInTask(usr, psw, myView, getActivity()).execute();
            }
        });


        return myView;
    }


}




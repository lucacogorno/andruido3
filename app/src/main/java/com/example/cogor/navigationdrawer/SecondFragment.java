package com.example.cogor.navigationdrawer;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

/**
 * Created by cogor on 06/09/2017.
 */

public class SecondFragment extends Fragment {

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


        Log.d("USername and psw", usr + " " + psw);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usr = username.getText().toString();
                psw = password.getText().toString();

                try {
                    logInTaskCall(usr, psw);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        return myView;
    }


    public void logInTaskCall(String username, String password) throws ExecutionException, InterruptedException {
       boolean response = new LogInTask(username, password).execute().get();
        Log.d("ReturnedFromLogInTask", Boolean.toString(response));
        if(!response)
            Toast.makeText(myView.getContext(), "Log in failed", Toast.LENGTH_SHORT).show();
        else
        {
            Toast.makeText(myView.getContext(), "Log in success", Toast.LENGTH_SHORT).show();
            //impostazioni nel caso di log in effettuato
        }
    }


}



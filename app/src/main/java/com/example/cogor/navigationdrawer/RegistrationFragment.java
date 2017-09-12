package com.example.cogor.navigationdrawer;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.concurrent.ExecutionException;

/**
 * Created by cogor on 06/09/2017.
 */

public class RegistrationFragment extends Fragment {

    View myView;
    TextView username;
    TextView password;
    TextView passwordRep;
    TextView date;
    TextView email;
    Button rButton;
    String usr;
    String psw;
    String pswRep;
    String birth;
    String mail;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.registration, container, false);

        username = (TextView) myView.findViewById(R.id.rUsername);
        password = (TextView) myView.findViewById(R.id.rPassword);
        passwordRep = (TextView) myView.findViewById(R.id.rPasswordRep);
        date = (TextView) myView.findViewById(R.id.rBirthDay);
        email = (TextView) myView.findViewById(R.id.rEmail);

        rButton = (Button) myView.findViewById(R.id.rButton);

        rButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usr = username.getText().toString();
                psw = password.getText().toString();
                pswRep = passwordRep.getText().toString();
                birth = date.getText().toString();
                mail = email.getText().toString();

                if(!psw.equals(pswRep))
                {
                    Toast.makeText(myView.getContext(), "Passwords don't match", Toast.LENGTH_SHORT);
                }
                else {
                         try {
                             RegistrationTaskCall(usr, psw, mail, birth);
                         } catch (ExecutionException e) {
                             e.printStackTrace();
                         } catch (InterruptedException e) {
                             e.printStackTrace();
                         }
                     }
                }
        });




        return myView;
    }

    public void RegistrationTaskCall(String username, String password, String email, String birth) throws ExecutionException, InterruptedException {
            boolean response = new RegistrationTask(username, password, email, birth).execute().get();

            if(!response)
            {
                Toast.makeText(myView.getContext(), "Registration Failed", Toast.LENGTH_SHORT);
            }
            else
            {
                Toast.makeText(myView.getContext(), "Registration Success", Toast.LENGTH_SHORT);
            }

    }

}

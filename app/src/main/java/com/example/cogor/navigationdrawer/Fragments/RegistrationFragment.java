package com.example.cogor.navigationdrawer.Fragments;

import android.app.Fragment;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cogor.navigationdrawer.R;
import com.example.cogor.navigationdrawer.Tasks.RegistrationTask;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by cogor on 06/09/2017.
 */

public class RegistrationFragment extends Fragment {

    private final String[] arraySpinner = new String[] {"m", "f"};

    View myView;
    TextView username;
    TextView password;
    TextView passwordRep;
    TextView date;
    TextView email;
    Spinner genderChoicer;
    Button rButton;
    String usr;
    String psw;
    String pswRep;
    String birth;
    String mail;
    String gender;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.registration, container, false);

        username = (TextView) myView.findViewById(R.id.rUsername);
        password = (TextView) myView.findViewById(R.id.rPassword);
        passwordRep = (TextView) myView.findViewById(R.id.rPasswordRep);
        date = (TextView) myView.findViewById(R.id.rBirthDay);
        email = (TextView) myView.findViewById(R.id.rEmail);
        genderChoicer = (Spinner) myView.findViewById(R.id.genderChoicer);



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(myView.getContext(),
                android.R.layout.simple_spinner_item, arraySpinner);


        genderChoicer.setAdapter(adapter);

        genderChoicer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getItemAtPosition(position).toString();
                Log.d("GENDER", gender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        rButton = (Button) myView.findViewById(R.id.rButton);

        rButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usr = username.getText().toString();
                psw = password.getText().toString();
                pswRep = passwordRep.getText().toString();
                birth = date.getText().toString();
                mail = email.getText().toString().trim();


                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


                String datePattern = "^\\d{4}[\\-\\/\\s]?((((0[13578])|(1[02]))[\\-\\/\\s]?(([0-2][0-9])|(3[01])))|(((0[469])|(11))[\\-\\/\\s]?(([0-2][0-9])|(30)))|(02[\\-\\/\\s]?[0-2][0-9]))$";



                if (!mail.matches(emailPattern)) {
                    Toast.makeText(myView.getContext(), "not valid email", Toast.LENGTH_SHORT).show();
                }
                else if(!birth.matches(datePattern)){
                    Toast.makeText(myView.getContext(), "not valid date", Toast.LENGTH_SHORT).show();
                }
                else if (!psw.equals(pswRep)) {
                    Toast.makeText(myView.getContext(), "Passwords don't match", Toast.LENGTH_SHORT).show();
                }
                else {
                    new RegistrationTask(usr, psw, birth, mail, gender, myView, getActivity()).execute();

                }
                }

        });




        return myView;
    }
    
}

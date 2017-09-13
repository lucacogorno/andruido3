package com.example.cogor.navigationdrawer.Fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.example.cogor.navigationdrawer.Item;
import com.example.cogor.navigationdrawer.R;
import com.example.cogor.navigationdrawer.Tasks.GetItemsTask;
import com.example.cogor.navigationdrawer.Tasks.UserInfoTask;

import java.util.concurrent.ExecutionException;

/**
 * Created by cogor on 30/08/2017.
 */

public class UserFragment extends Fragment implements OnEditorActionListener, OnClickListener{

    TextView UserTitle;
    TextView BirthDay;
    TextView UserEmail;
    String username;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.user_fragment, container, false);
        UserTitle = (TextView) view.findViewById(R.id.userTitle);
        BirthDay = (TextView) view.findViewById(R.id.userBirth);
        UserEmail = (TextView) view.findViewById(R.id.userEmail);


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        username =  prefs.getString("Username", null);
        try {
            String response = new UserInfoTask(username).execute().get();
            Log.d("UserInfoResp", response);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        return false;
    }
}

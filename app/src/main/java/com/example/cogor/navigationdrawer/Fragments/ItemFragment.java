package com.example.cogor.navigationdrawer.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.example.cogor.navigationdrawer.Tasks.GetItemInfoTask;
import com.example.cogor.navigationdrawer.R;

/**
 * Created by cogor on 30/08/2017.
 */

public class ItemFragment extends Fragment implements OnEditorActionListener, OnClickListener {

    View view;
    long id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_item, container, false);

        id = Long.parseLong(getArguments().get("arg").toString());


        new GetItemInfoTask(id, view, getActivity()).execute();


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

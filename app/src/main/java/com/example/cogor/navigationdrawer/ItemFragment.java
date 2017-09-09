package com.example.cogor.navigationdrawer;

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
import android.widget.Toast;

import com.example.cogor.navigationdrawer.R;

import java.util.concurrent.ExecutionException;

/**
 * Created by cogor on 30/08/2017.
 */

public class ItemFragment extends Fragment implements OnEditorActionListener, OnClickListener{

    int id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_item, container, false);
        TextView title = (TextView) view.findViewById(R.id.itemname);
        TextView descr = (TextView) view.findViewById(R.id.itemdescr);

        id = Integer.parseInt(getArguments().get("arg").toString());


        try {
            Item itemToShow = new GetItems().execute().get().get(id);

            title.setText(itemToShow.getName());
            descr.setText(itemToShow.getDescription());
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

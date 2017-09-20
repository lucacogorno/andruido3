package com.example.cogor.navigationdrawer.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.cogor.navigationdrawer.Tasks.GetItemsTask;
import com.example.cogor.navigationdrawer.R;


/**
 * Created by cogor on 06/09/2017.
 */

public class MainFragment extends Fragment {

    SearchView search;
    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.main_fragment, container, false);
        //Task che riceve dal server le informazioni sui prodotti e li inserisce in una listview
        new GetItemsTask(myView, getActivity()).execute();


        //campo di ricerca
        search = (SearchView) myView.findViewById(R.id.searchView);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.setIconified(false);
            }
        });


        return myView;
    }


}

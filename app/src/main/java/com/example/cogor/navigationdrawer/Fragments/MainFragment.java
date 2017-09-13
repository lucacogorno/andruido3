package com.example.cogor.navigationdrawer.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.cogor.navigationdrawer.Tasks.GetItemsTask;
import com.example.cogor.navigationdrawer.Item;
import com.example.cogor.navigationdrawer.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static com.example.cogor.navigationdrawer.R.id.editText;

/**
 * Created by cogor on 06/09/2017.
 */

public class MainFragment extends Fragment {

    ListView lv;
    SearchView search;
    View myView;
    ArrayList<String> stringItems;
    ArrayList<Item> items;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.main_fragment, container, false);
        lv = (ListView) myView.findViewById(R.id.listView);
        stringItems = new ArrayList<>();
        try {

            items = new GetItemsTask().execute().get();

            for(int i=0; i<items.size(); i++)
            {
                stringItems.add(items.get(i).toString());
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(myView.getContext(), android.R.layout.simple_list_item_1, stringItems);

            lv.setAdapter(arrayAdapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int itemId = items.get(position).getId();
                    ItemFragment itemFragment = new ItemFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("arg", itemId);
                    itemFragment.setArguments(bundle);
                    //Position e id indicano l'id nella tabella sul database -1 (per esempio per avere id 1 devo fare position +1)
                    android.app.FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, itemFragment).addToBackStack(null).commit();

                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

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

package com.example.cogor.navigationdrawer.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cogor.navigationdrawer.R;
import com.example.cogor.navigationdrawer.Tasks.UserInfoTask;


/**
 * Created by cogor on 30/08/2017.
 */

public class UserFragment extends Fragment {


    String username;
    ImageView userImage;
    Bitmap photo;
    private static final int CAMERA_REQUEST = 1888;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.user_fragment, container, false);

        userImage = (ImageView) view.findViewById(R.id.userImage);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        username = prefs.getString("Username", null);
        TextView userTitle = (TextView) view.findViewById(R.id.userTitle);
        userTitle.setText(username);

        userImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });


        new UserInfoTask(username, view).execute();


        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");
            userImage.setImageBitmap(photo);
        }
    }
}

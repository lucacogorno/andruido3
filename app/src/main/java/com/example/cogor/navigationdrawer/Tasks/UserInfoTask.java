package com.example.cogor.navigationdrawer.Tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cogor.navigationdrawer.R;
import com.example.cogor.navigationdrawer.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by cogor on 09/08/2017.
 */

public class UserInfoTask extends AsyncTask<Object, Object, User> {
    private static String requestURL = "http://webdev.disi.unige.it/~S4110217/get_user_creds.php";
    private String username;
    private User userToReturn;
    View view;

    public UserInfoTask(String username, View view)
    {
        this.username = username;
        this.view = view;
    }

    @Override
    protected User doInBackground(Object... params) {

        URL reqURL = null;
        String data = "";
        BufferedReader reader;



        try {

            reqURL = new URL(requestURL);

            HttpURLConnection urlConnection = (HttpURLConnection) reqURL.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());

            data += URLEncoder.encode("infoUser", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            Log.d("OutputSentUserInfo", data);
            outputStreamWriter.write(data);
            outputStreamWriter.flush();

            int responseCode = urlConnection.getResponseCode();


            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;



            while((line = reader.readLine()) != null)
            {
                    sb.append(line);
            }

            Log.d("returnedFromLogIn", sb.toString());
            JSONArray jsonArray = new JSONArray(sb.toString());
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            userToReturn = new User(jsonObject.getString("username"),
                                        jsonObject.getString("email"),
                                        jsonObject.getString("birthday"),
                                        jsonObject.getString("gender"));





        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userToReturn;
    }

    @Override
    protected void onPostExecute(User user) {
        if(user == null)
            Toast.makeText(view.getContext(), "Connection error", Toast.LENGTH_SHORT).show();
        else
        {
           TextView userName = (TextView) view.findViewById(R.id.userName);
           TextView birthDay = (TextView) view.findViewById(R.id.userBirth);
           TextView userEmail = (TextView) view.findViewById(R.id.userEmail);
            TextView userGender = (TextView) view.findViewById(R.id.userGender);

            userName.setText(user.getName());
            birthDay.setText(user.getDate());
            userEmail.setText(user.getEmail());
            userGender.setText(user.getGender());



        }

    }
}

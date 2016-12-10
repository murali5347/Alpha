package com.example.murali.alpha.activities;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.murali.alpha.fragment.DailogCreateFargment;
import com.example.murali.alpha.R;
import com.example.murali.alpha.adapters.AlphaAdapter;
import com.example.murali.alpha.pojo.AlphaData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AlphaMainActivity extends AppCompatActivity {


    List<AlphaData> alphaListData;
    ListView listView;
    URL alphaURL;
    TextView wifiStatus;
    String url = "https://alpha-api.app.net/stream/0/posts/stream/global";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alpha_data_activity);

        alphaListData = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);
        wifiStatus= (TextView) findViewById(R.id.wifiStatus);
        alphaURL = buildURL(url);
        /*passing the url to asynctask to fetch the data
          checking wifi connection
         */

        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if(wifiManager.isWifiEnabled()) {
            new AlphaTask().execute(alphaURL);
        }else{
            wifiStatus.setVisibility(View.VISIBLE);
            wifiStatus.setText("PLEASE CONNECT TO WIFI");
        }

    }

    public URL buildURL(String alphaurl){

        try {
            URL url = new URL(alphaurl);
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
        created a logout button on the action bar
        set the functionality
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.logout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.logout){
            new DailogCreateFargment().show(getFragmentManager(),"logout");
        }
        return super.onOptionsItemSelected(item);
    }


    private class AlphaTask extends AsyncTask<URL,Void,JSONObject > {

        @Override
        protected JSONObject doInBackground(URL... params) {

            HttpURLConnection httpURLConnection = null;
            StringBuilder stringBuilder = new StringBuilder();
            try {
                httpURLConnection = (HttpURLConnection) params[0].openConnection();
                int responseCode = httpURLConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK){


                    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()))){

                        String line;

                        while ((line = bufferedReader.readLine())!=null){
                            stringBuilder.append(line);
                        }
                        return new JSONObject(stringBuilder.toString());

                    } catch (Exception exception){
                        // Data returns empty
                        exception.printStackTrace();
                    }
                }

            } catch (IOException e) {
                // URL Exception
                e.printStackTrace();
            } finally {
                if (httpURLConnection!=null){
                    httpURLConnection.disconnect();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            convertJSONToArrayList(jsonObject);
        }
    }

    private void convertJSONToArrayList(JSONObject alphaJSONData){

        String descriptionText="";
        JSONArray rootAlphaData =null;

        try {
            if(alphaJSONData!=null) {
                Log.v("RESPONSE", alphaJSONData.toString());

                rootAlphaData = alphaJSONData.getJSONArray("data");


                for (int i = 0; i < rootAlphaData.length(); i++) {

                    JSONObject data = rootAlphaData.getJSONObject(i); // date
                    JSONObject user = data.getJSONObject("user");// avatarImage, username
                    JSONObject avatarImage = user.getJSONObject("avatar_image");

                    if (user.has("description")) {
                        JSONObject description = user.getJSONObject("description");

                        if (description != null && description.has("text")) {
                            descriptionText = description.getString("text");
                        }
                    }

                    /*
                     Add the json data into the arraylist
                     */
                    alphaListData.add(new AlphaData(data.getString("created_at"), user.getString("username"), descriptionText, avatarImage.getString("url")));
                }
                listView.setAdapter(new AlphaAdapter(AlphaMainActivity.this, alphaListData));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}


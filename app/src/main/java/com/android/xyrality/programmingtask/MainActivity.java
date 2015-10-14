package com.android.xyrality.programmingtask;

import android.app.DownloadManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button mGetGameWorldsButton;
    private ListView mGameWorldsListView;

    private String URL = "http://backend1.lordsandknights.com/XYRALITY/WebObjects/BKLoginServer.woa/wa/worlds";
//    private String URL = "http://my-json-feed";
//    final String URL = "http://volley/resource/12";

    private String login = "android.test@xyrality.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mGetGameWorldsButton = (Button) findViewById(R.id.get_game_worlds_button);
        mGetGameWorldsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetGameWorlds().execute(URL);
            }
        });

        mGameWorldsListView = (ListView) findViewById(R.id.game_worlds_list_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class GetGameWorlds extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String availableWorlds = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(params[0]);

            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
                nameValuePairs.add(new BasicNameValuePair("login", AppDelegate.getSharedPreferences().getString(AppDelegate.USERNAME_TAG, "")));
                nameValuePairs.add(new BasicNameValuePair("password", AppDelegate.getSharedPreferences().getString(AppDelegate.PASSWORD_TAG, "")));
                nameValuePairs.add(new BasicNameValuePair("deviceType", AppDelegate.getDeviceType()));
                nameValuePairs.add(new BasicNameValuePair("deviceId", AppDelegate.getDeviceId(MainActivity.this)));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

//                httppost.setHeader("login", AppDelegate.getSharedPreferences().getString(AppDelegate.USERNAME_TAG, ""));
//                httppost.setHeader("password", AppDelegate.getSharedPreferences().getString(AppDelegate.PASSWORD_TAG, ""));
//                httppost.setHeader("deviceType", AppDelegate.getDeviceType());
//                httppost.setHeader("deviceId", AppDelegate.getDeviceId(MainActivity.this));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                InputStream stream = response.getEntity().getContent();

                StringBuilder stringbuilder = new StringBuilder();
                BufferedReader bfrd = new BufferedReader(new InputStreamReader(stream), 1024);
                String line;
                while ((line = bfrd.readLine()) != null) {
                    stringbuilder.append(line);
                }
                String result = stringbuilder.toString();

                //            result = result.replaceAll("[\\p{Cc}\\p{Cf}\\p{Co}\\p{Cn}]", "");
//            result = result.replaceAll("\"", "\\\"");
                result = result.replaceAll("=", ":");
                result = result.replaceAll(";\t\t\\}", "}");
                result = result.replaceAll(";", ",");
                result = result.replaceAll(",\t\t\\}", "}");
                result = result.replaceAll("\\\\", "\\\\\\\\");

//            result = result.replaceAll("\\\\r\\\\n", "");
//            result = result.replace("\"{", "{");
//            result = result.replace("}\",", "},");
//            result = result.replace("}\"", "}");

//            result = result.replaceFirst("[(]", "[");
//            result = result.lastIndexOf(")");
//            result = result.replace("/\n/g", "\\\\n").replace("/\r/g", "\\\\r").replace("/\t/g", "\\\\t");

                availableWorlds = "[" + result.substring(result.indexOf("(") + 1, result.lastIndexOf(")")) + "]";

            } catch (IOException e) {
                e.printStackTrace();
            }


            return availableWorlds;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONArray jsonArray = new JSONArray(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                JSONObject jsonObject = new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

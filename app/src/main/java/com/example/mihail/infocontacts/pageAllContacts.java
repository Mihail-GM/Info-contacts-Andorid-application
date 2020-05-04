package com.example.mihail.infocontacts;


import android.app.ProgressDialog;
import android.content.Intent;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class pageAllContacts extends AppCompatActivity {
    private ListView listView;
    private DbHellperContacts db;
    private ArrayList<TelephoneContact> contacts;
    private MyAddapter myAddapter;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_all_contacts);

        listView = (ListView) findViewById(R.id.listView);
        db = new DbHellperContacts(getApplicationContext());
        contacts = new ArrayList<>();


        myAddapter = new MyAddapter(contacts, getApplicationContext());

        new JSONTask().execute("https://api.myjson.com/bins/1bbg43");
        //to be sure that data will be recieved for crater security must be done with mutex
        SystemClock.sleep(3000);

        listView.setAdapter(myAddapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(pageAllContacts.this, PopContact.class);

                intent.putExtra("nameContact", contacts.get(i).getNames());
                intent.putExtra("phoneNumber", contacts.get(i).getTelNumber());
                intent.putExtra("imgSource", contacts.get(i).getImageRs());

                startActivity(intent);
            }
        });

    }

    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("friends");

                List<TelephoneContact> movieModelList = new ArrayList<>();

                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);

                    // below single line of code from Gson saves  from writing the json parsing
                    TelephoneContact movieModel = gson.fromJson(finalObject.toString(), TelephoneContact.class); // a single line json parsing using Gson

                    movieModel.setImageRs(finalObject.getString("imgSource"));
                    movieModel.setNames(finalObject.getString("nameContact"));
                    movieModel.setTelNumber( finalObject.getString("phoneNum"));

                    contacts.add(movieModel);

                }
                return "OK";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}

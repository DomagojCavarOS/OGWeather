package com.d42gmail.cavar.ogweather;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

public class ShowMore extends AppCompatActivity {
    ArrayList<MoreCityInfo> MCI = new ArrayList<MoreCityInfo>();
    ListView MCIview;
    ShowMoreAdapter adapter;
    MainActivity main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_more);

        MCIview = (ListView) findViewById(R.id.listView2);

        Bundle b = getIntent().getExtras();
        String n = b.getString("cityName");

        adapter = new ShowMoreAdapter(getApplicationContext(), MCI);
        MCIview.setAdapter(adapter);

        GetMoreTask myTask = new GetMoreTask();
        myTask.execute("http://api.openweathermap.org/data/2.5/forecast/daily?q=" + n + "&appid=2a8fc52212d1d020c4b3ac497469a6ef");
        adapter.notifyDataSetChanged();

        main= new MainActivity();

    }


    private class GetMoreTask extends AsyncTask <String,Integer,MoreCityInfo>{
        @Override
        protected MoreCityInfo doInBackground(String... params) {
            MoreCityInfo current=new MoreCityInfo();
            try {
                URL u = new URL(params[0]);
                HttpURLConnection connection =
                        (HttpURLConnection) u.openConnection();
                connection.setConnectTimeout(15000);
                connection.setReadTimeout(10000);
                connection.setRequestMethod("GET");
                connection.connect();
                InputStream in = connection.getInputStream();
                StringBuilder sBuilder = new StringBuilder();
                BufferedReader bReader = new BufferedReader
                        (new InputStreamReader(in));
                String line;
                while((line = bReader.readLine()) != null){
                    sBuilder.append(line);
                }
                String JSONString = sBuilder.toString();
                current = readJSONfromString(JSONString);
            } catch (Exception e) {
                e.printStackTrace();}
            return current;
        }

        @Override
        protected void onPostExecute(MoreCityInfo moreCityInfo) {
            adapter.notifyDataSetChanged();
        }

        private MoreCityInfo readJSONfromString(String jsonString) {

            for(int j = 8; j>0;
                j--) {
                try {
                    MoreCityInfo MoreObj=new MoreCityInfo();

                    JSONObject completeData = new JSONObject(jsonString);
                    JSONObject complateCity=completeData.getJSONObject("city");
                    MoreObj.setCitaName(complateCity.getString("name"));
                    JSONArray listArray = completeData.getJSONArray("list");
                    JSONObject listData = listArray.getJSONObject(listArray.length() - j);
                    JSONObject tempData = listData.getJSONObject("temp");
                    MoreObj.setDay(new java.util.Date((listData.getLong("dt") * 1000)));
                    MoreObj.setAverageT((int) ((int) tempData.getDouble("day") - 273.15));
                    MoreObj.setMinT((int) ((int) tempData.getDouble("min") - 273.15));
                    MoreObj.setMaxT((int) ((int) tempData.getDouble("max") - 273.15));
                    MoreObj.setNightT((int) ((int) tempData.getDouble("night") - 273.15));
                    JSONArray weatherArray=listData.getJSONArray("weather");
                    JSONObject weatherData = weatherArray.getJSONObject(weatherArray.length()-1);
                    MoreObj.setClouds(weatherData.getString("main"));
                    MoreObj.setDescription(weatherData.getString("description"));
                    MoreObj.setIcoID(main.WeatherIcon(weatherData.getString("icon")));

                    MCI.add(MoreObj);


                } catch (JSONException e) {
                    e.printStackTrace();

                }


            }

            return null;
        }
    }

}

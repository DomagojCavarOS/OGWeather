package com.d42gmail.cavar.ogweather;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView CityList;
    EditText Edit;
    Button Add,Save;
    ArrayList<CityInfo> CityWeather=new ArrayList<CityInfo>();
    ShowAdapter adapter;
    int br=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        CityList= (ListView) findViewById(R.id.listView);
        Add= (Button) findViewById(R.id.button);
        Save= (Button) findViewById(R.id.button2);
        Edit= (EditText) findViewById(R.id.editText);
        adapter=new ShowAdapter(this,CityWeather);
        CityList.setAdapter(adapter);
        Save.setEnabled(false);
        SharedPreferences cityPref=getSharedPreferences("NAMECITY",0);
        int br1=cityPref.getInt("br",0);
        for(int i=0;i<br1;i++)
        {
            String c1=cityPref.getString("ab"+i,"");
            GetWeatherTask myTask2 = new GetWeatherTask();
            myTask2.execute("http://api.openweathermap.org/data/2.5/weather?q=="+c1+"&appid=2a8fc52212d1d020c4b3ac497469a6ef");
            adapter.notifyDataSetChanged();
        }


        Save.setOnClickListener(new View.OnClickListener() {  //save
            @Override
            public void onClick(View v) {
                adapter.notifyDataSetChanged();
                Save.setEnabled(false);
                SharedPreferences cityPref=getSharedPreferences("NAMECITY",0);
                SharedPreferences.Editor editor=cityPref.edit();
                editor.clear();
                for(CityInfo namecity:CityWeather){
                editor.putString("ab"+br,namecity.getTrimName());
                   br++;
                }
                editor.putInt("br",br);
                editor.commit();


            }
        });
        Add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                GetWeatherTask myTask1 = new GetWeatherTask();
                myTask1.execute("http://api.openweathermap.org/data/2.5/weather?q==" + Edit.getText().toString().trim().replace(" ","") + "&appid=2a8fc52212d1d020c4b3ac497469a6ef");
                adapter.notifyDataSetChanged();
                Save.setEnabled(true);

            }
        });

        CityList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CityWeather.remove(position);
                adapter.notifyDataSetChanged();
                Save.setEnabled(true);
                return false;
            }
        });

        CityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CityInfo cty= (CityInfo) adapter.getItem(position);
                Intent intent=new Intent(MainActivity.this,ShowMore.class);
                intent.putExtra("cityName",cty.getTrimName());
                startActivityForResult(intent,0);
            }
        });
    }
    public class GetWeatherTask extends AsyncTask<String, Integer,CityInfo>{

        @Override
        public CityInfo doInBackground(String... params) {
            CityInfo current=new CityInfo();
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
        public  void onPostExecute(CityInfo result) {
            CityWeather.add(result);
            adapter.notifyDataSetChanged();

        }

        public CityInfo readJSONfromString(String jsonString) {
            CityInfo CityObject=new CityInfo();
            try {
                JSONObject completeData = new JSONObject(jsonString);
                CityObject.setCity(completeData.getString("name"));
                JSONObject mainData = completeData.getJSONObject("main");
                CityObject.setMax ((int) ((mainData.getDouble("temp_max"))-273.15));
                JSONArray weatherData = completeData.getJSONArray("weather");
                JSONObject conditionData = weatherData.getJSONObject(weatherData.length()-1);
                Log.i("ii", "" + weatherData.length());
                CityObject.setDescription(conditionData.getString("description"));
                CityObject.setWeatherIco(WeatherIcon(conditionData.getString("icon")));


            } catch (JSONException e) { e.printStackTrace();}
            return CityObject;

        }
    }

    public int WeatherIcon(String ico) {
        if(ico.equals("01d"))
        {
            return R.drawable.b13;
        }
        else if(ico.equals("01n"))
        {
            return R.drawable.b3;
        }
        else if(ico.equals("02d"))
        {
            return R.drawable.b5;
        }
        else if(ico.equals("02n"))
        {
            return R.drawable.b4;
        }
        else if((ico.equals("03d"))||ico.equals("04d")||ico.equals("03n")||ico.equals("04n"))
        {
            return R.drawable.b1;
        }
        else if((ico.equals("09d"))||(ico.equals("09n")))
        {
            return R.drawable.b2;
        }
        else if((ico.equals("10d"))||(ico.equals("10n")))
        {
            return R.drawable.b6;
        }
        else if((ico.equals("11d"))||(ico.equals("11n")))
        {
            return R.drawable.b12;
        }
        else if((ico.equals("13d"))||(ico.equals("13n")))
        {
            return R.drawable.b9;
        }
        else if((ico.equals("50d"))||(ico.equals("50n")))
        {
            return R.drawable.b14;
        }
        else
        {
            return R.drawable.error;
        }
    }
    }





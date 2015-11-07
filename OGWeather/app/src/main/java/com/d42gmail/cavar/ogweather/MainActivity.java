package com.d42gmail.cavar.ogweather;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
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
    Button Add,Refresh;
    ArrayList<CityInfo> CityWeather=new ArrayList<CityInfo>();
    ShowAdapter adapter;
    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        CityList= (ListView) findViewById(R.id.listView);
        Add= (Button) findViewById(R.id.button);
        Refresh= (Button) findViewById(R.id.button2);
        Edit= (EditText) findViewById(R.id.editText);
        adapter=new ShowAdapter(this,CityWeather);
        CityList.setAdapter(adapter);

           GetWeatherTask myTask = new GetWeatherTask();
           myTask.execute("http://api.openweathermap.org/data/2.5/weather?q=Vinkovci&appid=2a8fc52212d1d020c4b3ac497469a6ef");
            adapter.notifyDataSetChanged();

        Refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.notifyDataSetChanged();
            }
        });
        Add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                GetWeatherTask myTask1 = new GetWeatherTask();
                myTask1.execute("http://api.openweathermap.org/data/2.5/weather?q=="+Edit.getText().toString()+"&appid=2a8fc52212d1d020c4b3ac497469a6ef");
                adapter.notifyDataSetChanged();
            }
        });

        CityList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CityWeather.remove(position);
                adapter.notifyDataSetChanged();
                return false;
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
            adapter.notifyDataSetChanged();

        }

        public CityInfo readJSONfromString(String jsonString) {
            CityInfo CityObject=new CityInfo();
            try {
                JSONObject completeData = new JSONObject(jsonString);
                String name=completeData.getString("name");
                JSONObject mainData = completeData.getJSONObject("main");
                int max= (int) ((mainData.getDouble("temp_max"))-273.15);
                JSONArray weatherData = completeData.getJSONArray("weather");
                JSONObject conditionData = weatherData.getJSONObject(weatherData.length() - 1);
                String weatherDescription=conditionData.getString("description");
                String Ico=conditionData.getString("icon");
                int weatherID = conditionData.getInt("id");
                int weatherID1=WeatherIcon(weatherID,Ico);
                CityWeather.add(new CityInfo(name,weatherDescription,Ico,max,weatherID1));
                adapter.notifyDataSetChanged();

            } catch (JSONException e) { e.printStackTrace();}
            return CityObject;

        }
    }

    public int WeatherIcon(int a,String ico) {
        if((a==200)||(a==201)||(a==202)||(a==210)||(a==211)||(a==212)||(a==221)||(a==230)||(a==231)||(a==232))
        {
            return R.drawable.b12;
        }
        else if((a==300)||(a==301)||(a==302)||(a==310)||(a==311)||(a==312)||(a==313)||(a==314)||(a==321))
        {
            return R.drawable.b15;
        }
        else if((a==520)||(a==521)||(a==522)||(a==531))
        {
            return R.drawable.b2;
        }
        else if((a==502)||(a==503)||(a==504)||(a==511))
        {
            return R.drawable.b6;
        }
        else if((a==501))
        {
            return R.drawable.b7;
        }
        else if((a==500))
        {
            return R.drawable.b8;
        }
        else if((a==611)||(a==612)||(a==615)||(a==616))
        {
            return R.drawable.b16;
        }
        else if((a==602)||(a==622))
        {
            return R.drawable.b9;
        }
        else if((a==601)||(a==621))
        {
            return R.drawable.b10;
        }
        else if((a==600)||(a==620))
        {
            return R.drawable.b11;
        }
        else if((a==701)||(a==711)||(a==721)||(a==731)||(a==741)||(a==751)||(a==761)||(a==762)||(a==771)||(a==781))
        {
            return R.drawable.b14;
        }
        else if((a==800)&&(ico.equals("01d")))
        {
            return R.drawable.b13;
        }
        else if((a==800)&&(ico.equals("01n")))
        {
            return R.drawable.b3;
        }
        else if(((a==801)&&(ico.equals("02d")))||((a==802)&&(ico.equals("02d")))||((a==803)&&(ico.equals("02d")))||((a==804)&&(ico.equals("02d"))))
        {
            return R.drawable.b5;
        }
        else if(((a==801)&&(ico.equals("03d")))||((a==802)&&(ico.equals("03d")))||((a==803)&&(ico.equals("03d")))||((a==804)&&(ico.equals("03d"))))
        {
            return R.drawable.b5;
        }
        else if(((a==801)&&(ico.equals("04d")))||((a==802)&&(ico.equals("04d")))||((a==803)&&(ico.equals("04d")))||((a==804)&&(ico.equals("04d"))))
        {
            return R.drawable.b5;
        }
        else if(((a==801)&&(ico.equals("02n")))||((a==802)&&(ico.equals("02n")))||((a==803)&&(ico.equals("02n")))||((a==804)&&(ico.equals("02n"))))
        {
            return R.drawable.b4;
        }
        else if(((a==801)&&(ico.equals("03n")))||((a==802)&&(ico.equals("03n")))||((a==803)&&(ico.equals("03n")))||((a==804)&&(ico.equals("03n"))))
        {
            return R.drawable.b4;
        }
        else if(((a==801)&&(ico.equals("04n")))||((a==802)&&(ico.equals("04n")))||((a==803)&&(ico.equals("04n")))||((a==804)&&(ico.equals("04n"))))
        {
            return R.drawable.b4;
        }
        else
        {
            return R.drawable.error;
            }
    }

}



package com.d42gmail.cavar.ogweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowMore extends AppCompatActivity {
    ArrayList<MoreCityInfo> MCI=new ArrayList<MoreCityInfo>();
    ListView MCIview;
    ShowMoreAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_more);

        MCIview= (ListView) findViewById(R.id.listView2);

        Bundle b=getIntent().getExtras();
        String n=b.getString("cityName");

        MoreCityInfo morecitiinfo=new MoreCityInfo("Vinkovci","7.11.2015","oblacno","kisa",10,3,20,2,1,1);
        MCI.add(morecitiinfo);
        adapter=new ShowMoreAdapter(getApplicationContext(),MCI);
        MCIview.setAdapter(adapter);
    }
}

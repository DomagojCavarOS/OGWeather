package com.d42gmail.cavar.ogweather;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Enigma on 5.11.2015..
 */
public class ShowAdapter extends BaseAdapter {
    Context ctx;
    ArrayList<CityInfo> CityList;

    public ShowAdapter(Context ctx, ArrayList<CityInfo> CityList) {
        super();
        this.ctx=ctx;
        this.CityList=CityList;
    }

    @Override
    public int getCount() {
        return this.CityList.size();
    }

    @Override
    public Object getItem(int position) {
        return CityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=View.inflate(ctx, R.layout.listlayout,null);
        }
        CityInfo current=CityList.get(position);

        ImageView IWeather = (ImageView) convertView.findViewById(R.id.IWeather);
        TextView ICityName = (TextView) convertView.findViewById(R.id.ICityName);
        TextView IData= (TextView) convertView.findViewById(R.id.IData);

        IWeather.setImageResource(current.getWeatherIco());
        ICityName.setText((CharSequence) current.getCity());
        IData.setText(String.format("Temp: %.0fºC, %s ",current.getMax(),current.getDescription()));

        return convertView;
    }
}

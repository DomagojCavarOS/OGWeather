package com.d42gmail.cavar.ogweather;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Enigma on 8.11.2015..
 */
public class ShowMoreAdapter extends BaseAdapter {
    Context ctx;
    ArrayList<MoreCityInfo> MoreCityInfo1;

    public ShowMoreAdapter(Context ctx, ArrayList<MoreCityInfo> MoreCityInfo1) {
        super();
        this.ctx = ctx;
        this.MoreCityInfo1 = MoreCityInfo1;
    }

    @Override
    public int getCount() {
        return this.MoreCityInfo1.size();
    }

    @Override
    public Object getItem(int position) {
        return this.MoreCityInfo1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView=View.inflate(ctx,R.layout.showmore,null);
        }

        MoreCityInfo current=MoreCityInfo1.get(position);
        ImageView Weather= (ImageView) convertView.findViewById(R.id.slija);
        TextView ime= (TextView) convertView.findViewById(R.id.ime);
        TextView opis= (TextView) convertView.findViewById(R.id.opis);
        TextView temp= (TextView) convertView.findViewById(R.id.temp);

        // Weather.setImageResource(current.getImage());
        Weather.setImageResource(R.drawable.error);
        ime.setText("" + current.getCitaName());
        opis.setText(String.format("%s,%s",current.getDescription(),current.getClouds()));
        temp.setText(String.format("Average day temp: %s \n Min day temp: %s \n Max day temp: %s \n Average night temp: %s",current.getAverageT(),current.getMinT(),current.getMaxT(),current.getNightT()));

        return convertView;
    }
}

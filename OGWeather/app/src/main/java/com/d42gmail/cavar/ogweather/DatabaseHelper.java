package com.d42gmail.cavar.ogweather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Enigma on 15.11.2015..
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="Weather.db";
    public static final String TABLE_NAME="weather_table";
    public static final String c_1="ID";
    public static final String c_2="CITY";
    public static final String c_3="DESCRIPTION";
    public static final String c_4="MAX";
    public static final String c_5="WEATHERICON";
    public static final String c_6="ICONINFO";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table" + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, CITY TEXT, DESCRIPTION TEXT, MAX INTEGER, WEATHERICON INTEGER, ICONINFO INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_NAME);
        onCreate(db);

    }

    public boolean addData(CityInfo cityInfo, Context ctx){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(c_2,cityInfo.getCity());
        contentValues.put(c_3,cityInfo.getDescription());
        contentValues.put(c_4,cityInfo.getMax());
        contentValues.put(c_5,cityInfo.getWeatherIco());
        contentValues.put(c_6, cityInfo.getIconInfo());
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result==-1)
        {
            //Toast.makeText(ctx,"Data not saved",Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
          //  Toast.makeText(ctx,"Data saved",Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public ArrayList<CityInfo> getResultDBs() {
        ArrayList<CityInfo> resultDBs = new ArrayList<CityInfo>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null,
                null);
        if (cursor.moveToFirst()) {
            do {

                CityInfo cinfo = new CityInfo( cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4),cursor.getInt(5));
                resultDBs.add(cinfo);
            } while (cursor.moveToNext());
        }
        db.close();
        return resultDBs;
    }

    public Integer delateData(String Id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_NAME,c_1+"=?",new String[]{Id});

    }
}

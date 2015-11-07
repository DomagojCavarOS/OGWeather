package com.d42gmail.cavar.ogweather;

/**
 * Created by Enigma on 5.11.2015..
 */
public class CityInfo {
    String City;
    String Description,IconInfo;
    int max;
    int weatherIco;


    public CityInfo (String City,String Description,String IconInfo, int max,int weatherIco)
    {super();

        this.City=City;
        this.Description=Description;
        this.max=max;
        this.weatherIco=weatherIco;
        this.IconInfo=IconInfo;


    }

    public String getIconInfo() {
        return IconInfo;
    }

    public void setIconInfo(String iconInfo) {
        IconInfo = iconInfo;
    }

    public CityInfo ()
    {
        this.City="N/A";
        this.Description="N/A";
        this.max=0;
        this.weatherIco= R.drawable.error;
    }

    public String getCity() {
        return City;
    }

    public String getDescription() {
        return Description;
    }

    public double getMax() {
        return max;
    }

    public int getWeatherIco() {
        return weatherIco;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public void setDescription(String Description) {
        this.Description=Description;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setWeatherIco(int weatherIco) {
        this.weatherIco = weatherIco;
    }
}

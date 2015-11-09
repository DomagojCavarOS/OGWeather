package com.d42gmail.cavar.ogweather;

import java.util.Date;

/**
 * Created by Enigma on 8.11.2015..
 */
public class MoreCityInfo {
    String CitaName,Clouds,Description;
    Integer AverageT,minT,maxT,nightT,image,icoID;
    Date Day;

    public MoreCityInfo(String CitaName, Date Day, String Clouds,String Description, Integer AverageT, Integer minT, Integer maxT, Integer nightT, Integer image, Integer icoID) {
        this.CitaName = CitaName;
        this.Day = Day;
        this.Clouds = Clouds;
        this.Description=Description;
        this.AverageT = AverageT;
        this.minT = minT;
        this.maxT = maxT;
        this.nightT = nightT;
        this.image=image;
        this.icoID=icoID;
    }



    public MoreCityInfo() {
        this.CitaName = "N/A";
        this.Clouds = "N/A";
        this.AverageT = 0;
        this.minT = 0;
        this.maxT = 0;
        this.nightT = 0;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Integer getIcoID() {
        return icoID;
    }

    public void setIcoID(Integer icoID) {
        this.icoID = icoID;
    }

    public String getCitaName() {
        return CitaName;
    }

    public void setCitaName(String citaName) {
        CitaName = citaName;
    }

    public Date getDay() {
        return Day;
    }

    public void setDay(Date day) {
        Day = day;
    }

    public String getClouds() {
        return Clouds;
    }

    public void setClouds(String clouds) {
        Clouds = clouds;
    }

    public Integer getAverageT() {
        return AverageT;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public void setAverageT(Integer averageT) {
        AverageT = averageT;
    }

    public Integer getMinT() {
        return minT;
    }

    public void setMinT(Integer minT) {
        this.minT = minT;
    }

    public Integer getMaxT() {
        return maxT;
    }

    public void setMaxT(Integer maxT) {
        this.maxT = maxT;
    }

    public Integer getNightT() {
        return nightT;
    }

    public void setNightT(Integer nightT) {
        this.nightT = nightT;
    }
}


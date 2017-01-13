package com.mohrapps.mentormeet;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

/**
 * Created by Katherine on 1/6/2017.
 */
@IgnoreExtraProperties

public class MyUserInfo {
    public String name;
    public String zip;
    public boolean mentorStat;
    public List<String> interests;
    public  boolean hasAPartner;

    @Override
    public String toString() {
        return "MyUserInfo{" +
                "name='" + name + '\'' +
                ", zip='" + zip + '\'' +
                ", mentorStat=" + mentorStat +
                ", interests=" + interests +
                ", hasAPartner=" + hasAPartner +
                '}';
    }

    public MyUserInfo() {
    }

    public MyUserInfo(String name, boolean hasAPartner, List<String> interests, boolean mentorStat, String zip) {
        this.name = name;
        this.hasAPartner = hasAPartner;
        this.interests = interests;
        this.mentorStat = mentorStat;
        this.zip = zip;
    }

    public boolean isMentorStat() {
        return mentorStat;
    }

    public void setMentorStat(boolean mentorStat) {
        this.mentorStat = mentorStat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    public boolean isHasAPartner() {
        return hasAPartner;
    }

    public void setHasAPartner(boolean hasAPartner) {
        this.hasAPartner = hasAPartner;
    }
}

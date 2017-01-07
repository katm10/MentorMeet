package com.mohrapps.mentormeet;

import java.util.List;

/**
 * Created by Katherine on 1/6/2017.
 */
public class MyUserInfo {
    public String name;
    public String zip;
    public boolean mentorStat;
    public List<String> interests;
    public  boolean hasAPartner;

    public MyUserInfo(String name, String zip, boolean mentorStat, List<String> interests, boolean hasAPartner){
        this.name = name;
        this.zip = zip;
        this.mentorStat = mentorStat;
        this.interests = interests;
        this.hasAPartner = hasAPartner;
    }

    public void setMentorStat(boolean mentorStat) {
        this.mentorStat = mentorStat;
    }

    public boolean getMentorStat(){
        return mentorStat;
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

    public void setHasAPartner(boolean hasAPartner) {
        this.hasAPartner = hasAPartner;
    }

    public boolean hasAPartner() {
        return hasAPartner;
    }

}

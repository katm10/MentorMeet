package com.mohrapps.mentormeet;

/**
 * Created by Katherine on 12/4/2016.
 */
public class Contact {
    int id;
    String uname;
    String pass;
    String name;
    String email;
    int isAMentor;

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return this.email;
    }

    public void setPass(String pass){
        this.pass = pass;
    }

    public String getPass(){
        return this.pass;
    }

    public void setUname(String uname){
        this.uname = uname;
    }

    public String getUname(){
        return this.uname;
    }

    public void setAMentor(int isAMentor){
        this.isAMentor = isAMentor;
    }

    public int getMentor(){
        return isAMentor;
    }
}

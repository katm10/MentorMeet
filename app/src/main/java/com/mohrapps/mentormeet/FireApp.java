package com.mohrapps.mentormeet;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Katherine on 1/3/2017.
 */
public class FireApp extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}

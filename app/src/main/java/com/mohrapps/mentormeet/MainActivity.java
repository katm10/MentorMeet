package com.mohrapps.mentormeet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    Button feedbtn;
    Button eventsbtn;
    Button mentorbtn;
    Button profilebtn;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //firebase connections
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRef = new Firebase("https://mentor-meet.firebaseio.com/Users");

        //buttons to go to different activities; might switch to swipe w/ fragments
        feedbtn = (Button) findViewById(R.id.feedButton);
        eventsbtn = (Button) findViewById(R.id.mapButton);
        mentorbtn = (Button) findViewById(R.id.mentorButton);
        profilebtn = (Button) findViewById(R.id.profileButton);

        feedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, feed.class);
                startActivity(intent);
            }
        });
//TODO: create map activity and chat activity
        eventsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, feed.class);
                startActivity(intent);
            }
        });

        mentorbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, feed.class);
                startActivity(intent);
            }
        });

        profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, profilePage.class);
                startActivity(intent);
            }
        });

    }



}

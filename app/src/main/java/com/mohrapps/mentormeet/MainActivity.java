package com.mohrapps.mentormeet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button feedbtn;
    Button eventsbtn;
    Button mentorbtn;
    Button profilebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        feedbtn = (Button)findViewById(R.id.feedButton);
        eventsbtn = (Button)findViewById(R.id.mapButton);
        mentorbtn = (Button)findViewById(R.id.mentorButton);
        profilebtn = (Button)findViewById(R.id.profileButton);

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

package com.mohrapps.mentormeet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button feedbtn;
    Button eventsbtn;
    Button mentorbtn;
    Button profilebtn;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    Firebase mRef;

    List<String> genAreas = new ArrayList<String>();
    //TODO: fix below issue
    HashMap<String, List<String>> specificAreas = new HashMap<>();
    ExpandableListAdapter listAdapter;
    ExpandableListView expandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRef = new Firebase("https://mentor-meet.firebaseio.com/Users");


        fillData();
        expandableListView = (ExpandableListView)findViewById(R.id.expandableListViewInterests);
        listAdapter = new MyExpandableListAdapter(this, genAreas, specificAreas);
        expandableListView.setAdapter(listAdapter);

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


        if(mUser.getDisplayName()==null){

        }
    }

    public void fillData(){
        String[] areasArr = {"Languages", "Sports", "Arts", "Outdoors", "Branches of CS", "Music", "Relaxing Things"};
        String[] langs = {"Java", "JavaScript", "HTML/CSS", "Python", "Ruby", "Ruby on Rails", "C++", "C#", "SQL", "Go", "PHP", "Swift"};
        String[] sports = {"Soccer", "Field Hockey", "Tennis", "American Football", "Basketball", "Golf", "Baseball/Softball", "Volleyball",
                "Table Tennis", "Golf", "Lacrosse", "Cricket", "Swimming", "Water Polo", "Badminton", "Track", "Cross Country", "Gymnastics",
                "Ice Skating", "Ice Hockey", "Martial Arts"};
        String [] arts = {"Drawing", "Painting", "Singing", "Dancing", "Sewing", "Knitting", "Acting", "Ceramics", "Photography"};
        String[] outdoors = {"Hiking", "Rock Climbing", "Dogs", "Cats", "Horses", "Hunting", "Archery", "Biking"};
        String[] branchesCS = {"Medicine", "Machine Learning/AI", "Education", "Social Impact", "Environment", "Games", "Food"};
        String[] music = {"Pop", "Hip Hop", "EDM/Dance", "Country", "Rock", "Classical", "RnB", "Indie", "Soul", "Folk", "Jazz",
                        "Alternative", "Metal", "K-Pop", "J-Pop", "Reggae", "Blues"};
        String[] relax = {"Working Out", "Meditation", "Yoga", "Watching TV", "Watching YouTube", "Cooking", "Baking"};
        String[][] arrays = {langs, sports, arts,outdoors,branchesCS,music,relax };

        for(String area : areasArr){
            genAreas.add(area);
        }
        int count = 0;
        for (String[] array : arrays){
            specificAreas.put(genAreas.get(count), Arrays.asList(array));
            count+= 1;
        }
    }
}

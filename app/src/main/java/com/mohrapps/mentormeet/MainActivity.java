package com.mohrapps.mentormeet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    Button feedbtn;
    Button eventsbtn;
    Button mentorbtn;
    Button profilebtn;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    Firebase mRef;
    Button doneWithQs;
    EditText firstAndLastName;
    EditText zipcode;
    ToggleButton mentorToggle;
    LinearLayout qLayout;
    LinearLayout homeLayout;

    List<String> genAreas = new ArrayList<String>();
    HashMap<String, List<String>> specificAreas = new HashMap<>();
    ExpandableListAdapter listAdapter;
    ExpandableListView expandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //firebase connections
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRef = new Firebase("https://mentor-meet.firebaseio.com/Users");

        //all for the question page
        doneWithQs = (Button)findViewById(R.id.btn_confirm_questions);
        firstAndLastName = (EditText)findViewById(R.id.first_and_last_input);
        zipcode = (EditText)findViewById(R.id.zipcode_edittext);
        mentorToggle = (ToggleButton)findViewById(R.id.switch_mentor);
        qLayout = (LinearLayout)findViewById(R.id.questionLayout);
        homeLayout = (LinearLayout)findViewById(R.id.layout_to_hide_when_asking_starting_questions);
        //make the toggle go yes/no not on/off
        mentorToggle.setText("No");
        mentorToggle.setTextOn("Yes");
        mentorToggle.setTextOff("No");
        //fill and display expandable lists of interests
        fillData();
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListViewInterests);
        listAdapter = new MyExpandableListAdapter(this, genAreas, specificAreas);
        expandableListView.setAdapter(listAdapter);
        //show questions if user doesn't have a display name
        if(mUser.getDisplayName()==null){
            homeLayout.setVisibility(View.GONE);
            qLayout.setVisibility(View.VISIBLE);
            doneWithQs.setVisibility(View.VISIBLE);
        }

        doneWithQs.setOnClickListener(OnDoneWithQs);


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


        if (mUser.getDisplayName() == null) {

        }
    }

    //to display interests when asking questions
    public void fillData() {
        String[] areasArr = {"Languages", "Sports", "Arts", "Outdoors", "Branches of CS", "Music", "Relaxing Things"};
        String[] langs = {"Java", "JavaScript", "HTML/CSS", "Python", "Ruby", "Ruby on Rails", "C++", "C#", "SQL", "Go", "PHP", "Swift"};
        String[] sports = {"Soccer", "Field Hockey", "Tennis", "American Football", "Basketball", "Golf", "Baseball/Softball", "Volleyball",
                "Table Tennis", "Golf", "Lacrosse", "Cricket", "Swimming", "Water Polo", "Badminton", "Track", "Cross Country", "Gymnastics",
                "Ice Skating", "Ice Hockey", "Martial Arts"};
        String[] arts = {"Drawing", "Painting", "Singing", "Dancing", "Sewing", "Knitting", "Acting", "Ceramics", "Photography"};
        String[] outdoors = {"Hiking", "Rock Climbing", "Dogs", "Cats", "Horses", "Hunting", "Archery", "Biking"};
        String[] branchesCS = {"Medicine", "Machine Learning/AI", "Education", "Social Impact", "Environment", "Games", "Food"};
        String[] music = {"Pop", "Hip Hop", "EDM/Dance", "Country", "Rock", "Classical", "RnB", "Indie", "Soul", "Folk", "Jazz",
                "Alternative", "Metal", "K-Pop", "J-Pop", "Reggae", "Blues"};
        String[] relax = {"Working Out", "Meditation", "Yoga", "Watching TV", "Watching YouTube", "Cooking", "Baking"};
        String[][] arrays = {langs, sports, arts, outdoors, branchesCS, music, relax};

        for (String area : areasArr) {
            genAreas.add(area);
        }
        int count = 0;
        for (String[] array : arrays) {
            specificAreas.put(genAreas.get(count), Arrays.asList(array));
            count += 1;
        }
    }

    public View.OnClickListener OnDoneWithQs = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String name = firstAndLastName.getText().toString();
            String zipcodeStr = zipcode.getText().toString();
            if(name.length() < 3){
                Toast.makeText(MainActivity.this, "Name should be more than 3 characters", Toast.LENGTH_SHORT).show();
            }else if(Pattern.matches("[a-zA-Z]+", zipcodeStr) && zipcodeStr.length()!=5){
                Toast.makeText(MainActivity.this, "Zipcode should be 5 digits", Toast.LENGTH_SHORT).show();
            }else if(/*TODO:figure out how to tell how many checks there are*/ true){

            }
        }
    };
}

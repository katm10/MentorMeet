package com.mohrapps.mentormeet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class questionActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    Firebase mRef;
    Button doneWithQs;
    EditText firstAndLastName;
    EditText zipcode;
    ToggleButton mentorToggle;
    LinearLayout qLayout;
    LinearLayout homeLayout;

    List<String> selectedInterests = new ArrayList<String>();
    List<String> genAreas = new ArrayList<String>();
    HashMap<String, List<String>> specificAreas = new HashMap<>();
    MyExpandableListAdapter listAdapter;
    ExpandableListView expandableListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

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
        TextView textView = (TextView)findViewById(R.id.selectedInterestsTextView);
        listAdapter = new MyExpandableListAdapter(this, genAreas, specificAreas, textView);
        expandableListView.setAdapter(listAdapter);

        doneWithQs.setOnClickListener(OnDoneWithQs);
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
            boolean isAMentor = mentorToggle.isActivated();
            selectedInterests = listAdapter.getSelectedInterests();
            if(name.length() < 3){
                Toast.makeText(questionActivity.this, "Name should be more than 3 characters", Toast.LENGTH_SHORT).show();
            }else if(NumberUtils.isNumber(zipcodeStr) && zipcodeStr.length()!=5){
                Toast.makeText(questionActivity.this, "Zipcode should be 5 digits", Toast.LENGTH_SHORT).show();
            }else if(selectedInterests.size()!=5){
                Toast.makeText(questionActivity.this, "Please select 5 interests.", Toast.LENGTH_SHORT).show();
            }else{
                //String name, String zip, boolean isAMentor, List<String> interests, boolean hasAPartner, String randomKey
                MyUserInfo userInfo = new MyUserInfo(name, zipcodeStr, isAMentor, selectedInterests, false);
                mRef.child(mUser.getEmail().replaceAll("[^A-Za-z0-9]", "")).setValue(userInfo);
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build();

                mUser.updateProfile(profileUpdates);
               // TODO:mentor and mentee should have diff layouts
                startActivity(new Intent(questionActivity.this, MainActivity.class));
            }
        }
    };

}

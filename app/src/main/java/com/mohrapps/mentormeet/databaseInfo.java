package com.mohrapps.mentormeet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class databaseInfo extends AppCompatActivity {
    TextView textView;
    String text;
    DatabaseHelper helper = new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_info);
        textView = (TextView)findViewById(R.id.dbInfoText);
        text = helper.getAllInfo();
        textView.setText("Start " + text+ " end");
    }
}

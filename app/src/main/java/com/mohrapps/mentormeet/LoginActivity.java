package com.mohrapps.mentormeet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    public Button register;
    public Button login;
    public EditText username;
    public EditText pass;
    public String unameStr;
    public String passStr;
    public Button goToDb;
    DatabaseHelper helper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        register = (Button)findViewById(R.id.register);
        login = (Button)findViewById(R.id.signInButton);
        username = (EditText)findViewById(R.id.enterKnownPass);
        pass = (EditText)findViewById(R.id.enterKnownUname);
        goToDb = (Button)findViewById(R.id.dbButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unameStr = username.getText().toString();
                passStr = pass.getText().toString();
                // final String realPass = "password";
                String realPass = helper.searchPass(unameStr);

                if(passStr.equals(realPass)){
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    i.putExtra("Username", unameStr);
                    startActivity(i);
                }
                else
                {

                    Toast retry = Toast.makeText(LoginActivity.this, "Username and password don't match! RealPass==" + realPass +" passStr==" + passStr, Toast.LENGTH_SHORT);
                    retry.show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(LoginActivity.this,
                        SignUp.class);
                startActivity(myIntent);
            }
        });

        goToDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(LoginActivity.this,
                        databaseInfo.class);
                startActivity(myIntent);
            }
        });
    }

}

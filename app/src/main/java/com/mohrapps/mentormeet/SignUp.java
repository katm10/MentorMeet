package com.mohrapps.mentormeet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    DatabaseHelper helper = new DatabaseHelper(this);
    Button logIn;
    EditText username;
    EditText pass1;
    EditText pass2;
    EditText name;
    EditText email;
    Switch mentor;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_page);

        logIn = (Button)findViewById(R.id.goToLoginPage);
        username = (EditText)findViewById(R.id.usernameEdit);
        pass1 = (EditText)findViewById(R.id.password);
        pass2 = (EditText)findViewById(R.id.secondPassword);
        name = (EditText)findViewById(R.id.firstNameEdit);
        email = (EditText)findViewById(R.id.emailEdit);
        mentor = (Switch)findViewById(R.id.switch1);
        signUp = (Button)findViewById(R.id.signUpButton);

        logIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(SignUp.this,
                        LoginActivity.class);
                startActivity(myIntent);
            }
        });

    }

    public void onSignUpClick(View v) {
        //getting strings from sign up
        String usernameStr = username.getText().toString();
        String pass1Str = pass1.getText().toString();
        String pass2Str = pass2.getText().toString();
        String nameStr = name.getText().toString();
        String emailStr = email.getText().toString();

        if(!pass1Str.equals(pass2Str)){

            //let user know passwords don't match
            Toast passProb = Toast.makeText(SignUp.this, "pass1str ==" + pass1Str + " pass2str =="+ pass2Str, Toast.LENGTH_SHORT);
            passProb.show();
        }
        else
        {
            //make new contact
            Contact contact = new Contact();
            contact.setEmail(emailStr);
            contact.setName(nameStr);
            contact.setPass(pass1Str);
            contact.setUname(usernameStr);
            if(mentor.isChecked()){
                contact.setAMentor(1);
            }else{
                contact.setAMentor(0);
            }
            Toast signedUp = Toast.makeText(SignUp.this, "You have signed up! Woot woot!", Toast.LENGTH_SHORT);
            signedUp.show();
            helper.insertContact(contact);
        }
    }
}

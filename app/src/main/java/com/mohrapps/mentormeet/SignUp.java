package com.mohrapps.mentormeet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    private TextView logIn;
    private EditText pass1;
   private EditText pass2;
    private EditText name;
    private EditText email;
    private Switch mentor;
    private Button signUp;
    protected FirebaseAuth mAuth;
    protected FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_page);

        mAuth = FirebaseAuth.getInstance();
        logIn = (TextView) findViewById(R.id.link_login);
        pass1 = (EditText)findViewById(R.id.input_password);
        pass2 = (EditText)findViewById(R.id.input_password2);
        name = (EditText)findViewById(R.id.input_name);
        email = (EditText)findViewById(R.id.input_email);
        mentor = (Switch)findViewById(R.id.switch_mentor);
        signUp = (Button)findViewById(R.id.btn_signup);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    Intent myIntent = new Intent(SignUp.this,
                            MainActivity.class);
                    startActivity(myIntent);
                }
            }
        };

        logIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(SignUp.this,
                        LoginActivity.class);
                startActivity(myIntent);
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void onSignUpClick(View view) {

        String nameStr = name.getText().toString();
        String passStr = pass1.getText().toString();
        String passStr2 = pass2.getText().toString();
        String emailStr = email.getText().toString();

        if (nameStr.isEmpty() || name.length() < 3) {
            Toast.makeText(this, "Name must be more than 3 letters.", Toast.LENGTH_SHORT).show();
        } else if(emailStr.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
            Toast.makeText(this, "Enter a valid email address.", Toast.LENGTH_SHORT).show();
        } else if (passStr.isEmpty() || passStr.length() < 6 || passStr.length() > 20) {
            Toast.makeText(this, "Please use between 6 and 25 alphanumeric characters in the password.", Toast.LENGTH_SHORT).show();
        } else if(!passStr.equals(passStr2)){
            Toast.makeText(this, "Passwords don't match!", Toast.LENGTH_SHORT).show();
        }else{
            mAuth.createUserWithEmailAndPassword(emailStr, passStr);
            Toast.makeText(this, "Your account has been created!", Toast.LENGTH_SHORT).show();
        }
    }

}

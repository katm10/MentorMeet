package com.mohrapps.mentormeet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    private TextView logIn;
    private EditText pass1;
    private EditText pass2;
    private EditText email;
    private Button signUp;
    protected FirebaseAuth mAuth;
    protected FirebaseAuth.AuthStateListener mAuthListener;
    Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_page);

        mRef = new Firebase("https://mentor-meet.firebaseio.com/Users");
        mAuth = FirebaseAuth.getInstance();
        logIn = (TextView) findViewById(R.id.link_login);
        pass1 = (EditText) findViewById(R.id.input_password);
        pass2 = (EditText) findViewById(R.id.input_password2);
        email = (EditText) findViewById(R.id.input_email);
        signUp = (Button) findViewById(R.id.btn_signup);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    if(firebaseAuth.getCurrentUser().getDisplayName()!=null) {
                        //TODO: separate mentor/mentee pages
                        //if they have display name, go to appropriate mentor or mentor page
                        Intent myIntent = new Intent(SignUp.this,
                                NavigationActivity.class);
                        startActivity(myIntent);
                    }else{
                        //if no display name, go to questionnaire
                        startActivity(new Intent(SignUp.this, questionActivity.class));
                    }
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
    protected void onStart() {
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

        String passStr = pass1.getText().toString();
        String passStr2 = pass2.getText().toString();
        String emailStr = email.getText().toString();

        if (emailStr.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
            Toast.makeText(this, "Enter a valid email address.", Toast.LENGTH_SHORT).show();
        } else if (passStr.isEmpty() || passStr.length() < 6 || passStr.length() > 20) {
            Toast.makeText(this, "Please use between 6 and 25 alphanumeric characters in the password.", Toast.LENGTH_SHORT).show();
        } else if (!passStr.equals(passStr2)) {
            Toast.makeText(this, "Passwords don't match!", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.createUserWithEmailAndPassword(emailStr, passStr).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(SignUp.this, "An error occurred", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SignUp.this, "Please wait...", Toast.LENGTH_SHORT).show();
                        //createNewUser(task.getResult().getUser());
                    }
                  //  hideProgressDialog();
                }

            });
        }

    }
}

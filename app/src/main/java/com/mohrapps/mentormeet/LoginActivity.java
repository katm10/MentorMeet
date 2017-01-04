package com.mohrapps.mentormeet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    public TextView register;
    public Button login;
    public EditText email;
    public EditText pass;
    public String emailStr;
    public String passStr;
    public Button goToDb;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        mAuth = FirebaseAuth.getInstance();
        register = (TextView) findViewById(R.id.link_signup);
        login = (Button) findViewById(R.id.btn_login);
        pass = (EditText) findViewById(R.id.input_password);
        email = (EditText) findViewById(R.id.input_email);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignIn();
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    Intent myIntent = new Intent(LoginActivity.this,
                            MainActivity.class);
                    startActivity(myIntent);
                }
            }
        };

        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(LoginActivity.this,
                        SignUp.class);
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

    private void startSignIn(){
        emailStr = email.getText().toString();
        passStr = pass.getText().toString();

        if(TextUtils.isEmpty(emailStr) || TextUtils.isEmpty(passStr)){
            Toast.makeText(LoginActivity.this, "Both fields must be filled in", Toast.LENGTH_SHORT).show();
        }else {

            mAuth.signInWithEmailAndPassword(emailStr, passStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Sign in problem :(", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }
}

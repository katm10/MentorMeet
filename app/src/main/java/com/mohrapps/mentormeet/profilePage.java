package com.mohrapps.mentormeet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class profilePage extends AppCompatActivity {

    private Button signOut;
    private TextView welcomeUser;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private Button deleteButton;
    private LinearLayout foregroundChange;
    private LinearLayout visibilityChange;
    private Button confirmDelete;
    private Button cancelDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        if (FirebaseAuth.getInstance() != null) {
            mAuth = FirebaseAuth.getInstance();
            mUser = mAuth.getCurrentUser();
        }
        welcomeUser = (TextView) findViewById(R.id.welcomeUserText);
        welcomeUser.setText("Welcome, " + mUser.getDisplayName());
        signOut = (Button) findViewById(R.id.btn_signout);

        //makes the screen change when they try to delete account
        confirmDelete = (Button) findViewById(R.id.btn_confirm_delete);
        cancelDelete = (Button) findViewById(R.id.btn_cancel_delete);
        foregroundChange = (LinearLayout) findViewById(R.id.layout_to_change_foreground);
        visibilityChange = (LinearLayout) findViewById(R.id.layout_to_make_appear);
        confirmDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthCredential credential = EmailAuthProvider
                        .getCredential("user@example.com", "password1234");

// Prompt the user to re-provide their sign-in credentials
                mUser.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(profilePage.this, "Authenitcated", Toast.LENGTH_SHORT);
                            }
                        });
                mUser.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(profilePage.this, SignUp.class));
                                }


                            }
                        });
            }
        });
        cancelDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visibilityChange.setVisibility(View.GONE);
                foregroundChange.setVisibility(View.VISIBLE);
            }
        });


        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        welcomeUser = (TextView) findViewById(R.id.welcomeUserText);
        welcomeUser.setText("Welcome, " + mUser.getDisplayName());
        signOut = (Button) findViewById(R.id.btn_signout);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(profilePage.this, LoginActivity.class));
            }
        });
        deleteButton = (Button) findViewById(R.id.btn_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visibilityChange.setVisibility(View.VISIBLE);
                foregroundChange.setVisibility(View.INVISIBLE);
            }
        });


    }

}

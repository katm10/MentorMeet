package com.mohrapps.mentormeet;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    private Button signOut;
    private TextView welcomeUser;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private Button deleteButton;
    private LinearLayout foregroundChange;
    private LinearLayout visibilityChange;
    private Button confirmDelete;
    private Button cancelDelete;
    private Firebase mUserRef;
    private OnFragmentInteractionListener mListener;
    private View myView;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       
        myView = inflater.inflate(R.layout.activity_profile_page, container, false);
        if (FirebaseAuth.getInstance() != null) {
            mAuth = FirebaseAuth.getInstance();
            mUser = mAuth.getCurrentUser();
            mUserRef = new Firebase("https://mentor-meet.firebaseio.com/Users/" + mUser.getEmail().replaceAll("[^A-Za-z0-9]", ""));
        }
        welcomeUser = (TextView) myView.findViewById(R.id.welcomeUserText);
        welcomeUser.setText("Welcome, " + mUser.getDisplayName());
        signOut = (Button) myView.findViewById(R.id.btn_signout);
        //makes the screen change when they try to delete account
        confirmDelete = (Button) myView.findViewById(R.id.btn_confirm_delete);
        cancelDelete = (Button) myView.findViewById(R.id.btn_cancel_delete);
        foregroundChange = (LinearLayout) myView.findViewById(R.id.layout_to_change_foreground);
        visibilityChange = (LinearLayout) myView.findViewById(R.id.layout_to_make_appear);
        confirmDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mUser.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getContext(), "If you haven't signed in recently, please sign out and in for safety reasons.", Toast.LENGTH_SHORT).show();
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(getContext(), SignUp.class));
                                    mUserRef.setValue(null);
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
        welcomeUser = (TextView) myView.findViewById(R.id.welcomeUserText);
        welcomeUser.setText("Welcome, " + mUser.getDisplayName());
        signOut = (Button) myView.findViewById(R.id.btn_signout);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
        deleteButton = (Button) myView.findViewById(R.id.btn_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visibilityChange.setVisibility(View.VISIBLE);
                foregroundChange.setVisibility(View.INVISIBLE);
            }
        });


        return myView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

package com.mohrapps.mentormeet;

import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MentorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MentorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MentorFragment extends Fragment {

    private View myView;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    Firebase mRef;
    EditText zipcodeEdit;
    EditText milesFromEdit;
    ArrayList<String> selectedInterests = new ArrayList<>();
    private OnFragmentInteractionListener mListener;
    ArrayAdapter adapter;
    ListView interestsListView;
    TextView interestsText;
    TextView interestsText2;
    EditText search_edittext;
    Button searchButton;
    private ArrayList<String> array_sort = new ArrayList<String>();
    int textlength = 0;
    String[] interestsArray = {
            "Java",
            "JavaScript",
            "HTML/CSS",
            "Python",
            "Ruby",
            "Ruby on Rails",
            "C++",
            "C#",
            "SQL",
            "Go",
            "PHP",
            "Swift",
            "Soccer",
            "Field Hockey",
            "Tennis",
            "American Football",
            "Basketball",
            "Golf",
            "Baseball/Softball",
            "Volleyball",
            "Table Tennis",
            "Golf",
            "Lacrosse",
            "Cricket",
            "Swimming",
            "Water Polo",
            "Badminton",
            "Track",
            "Cross Country",
            "Gymnastics",
            "Ice Skating",
            "Ice Hockey",
            "Martial Arts",
            "Drawing",
            "Painting",
            "Singing",
            "Dancing",
            "Sewing",
            "Knitting",
            "Acting",
            "Ceramics",
            "Photography",
            "Hiking",
            "Rock Climbing",
            "Dogs",
            "Cats",
            "Horses",
            "Hunting",
            "Archery",
            "Biking",
            "Medicine",
            "Machine Learning/AI",
            "Education",
            "Social Impact",
            "Environment",
            "Games",
            "Food",
            "Pop",
            "Hip Hop",
            "EDM/Dance",
            "Country",
            "Rock",
            "Classical",
            "RnB",
            "Indie",
            "Soul",
            "Folk",
            "Jazz",
            "Alternative",
            "Metal",
            "K-Pop",
            "J-Pop",
            "Reggae",
            "Blues",
            "Working Out",
            "Meditation",
            "Yoga",
            "Watching TV",
            "Watching YouTube",
            "Cooking",
            "Baking"
    };

    public MentorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment chatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MentorFragment newInstance() {
        MentorFragment fragment = new MentorFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRef = new Firebase("https://mentor-meet.firebaseio.com/Users");

        try {
            mListener = (OnFragmentInteractionListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.mentor_fragment, container, false);
        interestsListView = (ListView) myView.findViewById(R.id.listView_search_interests);
        search_edittext = (EditText) myView.findViewById(R.id.search_for_interests_edit);
        interestsText = (TextView) myView.findViewById(R.id.searchDisplayTextView);
        interestsText2 = (TextView) myView.findViewById(R.id.searchDisplayTextView2);
        search_edittext.addTextChangedListener(textWatcher);
        milesFromEdit = (EditText) myView.findViewById(R.id.number_of_miles);
        zipcodeEdit = (EditText) myView.findViewById(R.id.zipcode_edittext_search);
        zipcodeEdit.addTextChangedListener(zipcodeWatch);
        searchButton = (Button)myView.findViewById(R.id.search_btn);
        searchButton.setOnClickListener(onSearchButtonClicked);
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

    TextWatcher zipcodeWatch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(zipcodeEdit.getText() != null) {
                String displayText = milesFromEdit.getText().toString() + " miles from " + zipcodeEdit.getText().toString();
                interestsText2.setText(displayText);
            }
        }
    };

    TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            textlength = search_edittext.getText().length();
            array_sort.clear();
            for (int i = 0; i < interestsArray.length; i++) {
                if (textlength <= interestsArray[i].length()) {
                    if (search_edittext.getText().toString().equalsIgnoreCase(
                            (String)
                                    interestsArray[i].subSequence(0,
                                            textlength))) {
                        array_sort.add(interestsArray[i]);
                    }
                }
            }
            interestsListView.setAdapter(new ArrayAdapter<String>
                    (getActivity(), R.layout.search_layout, R.id.search_item_text, array_sort));
            interestsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String topic = (String) interestsListView.getItemAtPosition(i);
                    selectedInterests.add(topic);
                    String display = "You have selected ";
                    for (String interest : selectedInterests) {
                        display += (interest + ", ");
                        interestsText.setText(display + "and ");
                    }
                }

            });


        }
    };

    public Double getDistance(int zip1, int zip2) {
        String url = "https://www.zipcodeapi.com/rest/" + getText(R.string.zipcode_api_code) + "/distance.json/" + zip1 + "/" + zip2 + "/miles";

        HttpClient httpclient = new DefaultHttpClient();

        // make GET request to the given URL
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpclient.execute(new HttpGet(url));
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        // receive response as inputStream
        InputStream inputStream = null;
        try {
            inputStream = httpResponse.getEntity().getContent();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        Double result = 0.00;
        // convert inputstream to string
        if(inputStream != null)
            try {
                result = convertInputStreamToString(inputStream);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

    return result;
}

    // convert inputstream to String
    private static Double convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        Double finalNum = 0.00;
        try {
            JSONObject object = new JSONObject(result);
            finalNum = object.getDouble("distance");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return finalNum;

    }

    // check network connection
    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getContext().getSystemService(getContext().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }


    public void updateListView(int zip, int maxNumOfMiles){
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.child("interests").getChildren().iterator();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public View.OnClickListener onSearchButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Double distance = getDistance(94010, 54128);
            Toast.makeText(getContext(), distance.toString(), Toast.LENGTH_SHORT).show();
        }
    };
}

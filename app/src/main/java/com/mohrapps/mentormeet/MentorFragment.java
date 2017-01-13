package com.mohrapps.mentormeet;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

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

    EditText zipcodeEdit;
    EditText milesFromEdit;
    ArrayList<String> selectedInterests = new ArrayList<>();
    private OnFragmentInteractionListener mListener;
    ArrayAdapter adapter;
    ListView interestsListView;
    TextView interestsText;
    EditText search_edittext;
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
        search_edittext.addTextChangedListener(textWatcher);
        milesFromEdit = (EditText) myView.findViewById(R.id.number_of_miles);
        zipcodeEdit = (EditText) myView.findViewById(R.id.zipcode_edittext_search);
        double temp = getDistance(94066, 54002);
        Toast.makeText(getContext(), String.valueOf(temp), Toast.LENGTH_SHORT).show();
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

    public double getDistance(int zip1, int zip2) {
        String numberStr = new String();
        String url = "https://www.zipcodeapi.com/rest/" + getText(R.string.zipcode_api_code) + "/distance.json/" + zip1 + "/" + zip2 + "/miles";
        String charset = "UTF-8";  // Or in Java 7 and later, use the constant: java.nio.charset.StandardCharsets.UTF_8.name()
        URLConnection connection = null;
        try {
            connection = new URL(url).openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connection.setRequestProperty("Accept-Charset", charset);
        InputStream response = null;
        try {
            response = new URL(url).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (Scanner scanner = new Scanner(response)) {
            String responseBody = scanner.useDelimiter("\\A").next();
            // System.out.println(responseBody);
            for (int i = 12; i < responseBody.length() - 1; i++) {
                numberStr += responseBody.charAt(i);
            }
            return Double.parseDouble(numberStr);
        }
    }
}

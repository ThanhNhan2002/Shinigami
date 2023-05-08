package com.example.shinigami;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment5#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment5 extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment5() {
        // Required empty public constructor
    }

    public static Fragment5 newInstance(String param1, String param2) {
        Fragment5 fragment = new Fragment5();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    private void switchToFragment6() {
        // Create a new instance of the Fragment6 class
        Fragment newFragment = new Fragment6();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.mainLayout, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference devicesRef = db.collection("devices");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_5, container, false);
        ImageButton button1 = rootView.findViewById(R.id.button1);
        ImageButton button2 = rootView.findViewById(R.id.button2);
        ImageButton button3 = rootView.findViewById(R.id.button3);
        ImageButton button4 = rootView.findViewById(R.id.button4);
        ImageButton button5 = rootView.findViewById(R.id.button5);

        // Set onClickListener for each button
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String info = camera.getDeviceName();
                switchToFragment6();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToFragment6();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToFragment6();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToFragment6();
            }

        });



        return rootView;
    }
}
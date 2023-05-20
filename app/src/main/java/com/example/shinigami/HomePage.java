package com.example.shinigami;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomePage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePage extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomePage() {
        // Required empty public constructor
    }

    public static HomePage newInstance(String param1, String param2) {
        HomePage fragment = new HomePage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }





    private void switchToFragment6(int deviceId) {
        // Create a new instance of the Fragment6 class
        Fragment newFragment = new ItemPage();

        Bundle result = new Bundle();

        result.putInt("deviceId", deviceId);

        getParentFragmentManager().setFragmentResult("dataFromHomePage", result);

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

        View rootView = inflater.inflate(R.layout.home_page, container, false);
        ImageButton button1 = rootView.findViewById(R.id.button1);
        ImageButton button2 = rootView.findViewById(R.id.button2);
        ImageButton button3 = rootView.findViewById(R.id.button3);
        ImageButton button4 = rootView.findViewById(R.id.button4);
        ImageButton button5 = rootView.findViewById(R.id.button5);
        Button button6 = rootView.findViewById(R.id.button6);

        // Set onClickListener for each button
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundManager.playButtonSound(getContext());
                switchToFragment6(1);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundManager.playButtonSound(getContext());
                switchToFragment6(2);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundManager.playButtonSound(getContext());
                switchToFragment6(3);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundManager.playButtonSound(getContext());
                switchToFragment6(4);
            }
        });


        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundManager.playButtonSound(getContext());
                NewItemPopup popupFragment = NewItemPopup.newInstance();
                popupFragment.show(getChildFragmentManager(), "PopupFragment");
            }
        });


        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundManager.playButtonSound(getContext());
                LoginPage loginPage = new LoginPage();

                // Replace the existing layout with Fragment2
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.mainLayout, loginPage).commit();
            }
        });




        return rootView;
    }
}
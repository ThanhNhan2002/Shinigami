package com.example.shinigami;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

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

//        private int deviceId;
//        private String deviceName;
//        private String deviceDesc;
//        private boolean isWorking;
//        private HashMap<String, String> deviceStatuses = new HashMap<>();

        Bundle result = new Bundle();
//        result.putString("df1", "test data from home fragment6");
//
//        result.putString("deviceId", true);
//        result.putString("deviceName", true);
//        result.putString("deviceDesc", true);
//        result.putBoolean("isWorking", true);

//        getParentFragmentManager().setFragmentResult("dataFrom1", result);

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.mainLayout, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private void switchToFragment6(Device device) {
        // Create a new instance of the Fragment6 class
        Fragment newFragment = new Fragment6();

//        private int deviceId;
//        private String deviceName;
//        private String deviceDesc;
//        private boolean isWorking;
//        private HashMap<String, String> deviceStatuses = new HashMap<>();

        Bundle result = new Bundle();

        result.putInt("deviceId", device.getDeviceId());
        result.putString("deviceName", device.getDeviceName());
        result.putString("deviceDesc", device.getDeviceDesc());
        result.putBoolean("isWorking", true);

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

        View rootView = inflater.inflate(R.layout.page_two, container, false);
        ImageButton button1 = rootView.findViewById(R.id.button1);
        ImageButton button2 = rootView.findViewById(R.id.button2);
        ImageButton button3 = rootView.findViewById(R.id.button3);
        ImageButton button4 = rootView.findViewById(R.id.button4);
        ImageButton button5 = rootView.findViewById(R.id.button5);

        // Set onClickListener for each button
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SoundManager.playButtonSound(getContext());
                Device device = MainActivity.getDeviceById(1);
                switchToFragment6(device);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundManager.playButtonSound(getContext());
                Device device = MainActivity.getDeviceById(2);
                switchToFragment6(device);
//                switchToFragment6();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundManager.playButtonSound(getContext());
                Device device = MainActivity.getDeviceById(3);
                switchToFragment6(device);
//                switchToFragment6();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundManager.playButtonSound(getContext());
                Device device = MainActivity.getDeviceById(4);
                switchToFragment6(device);
//                switchToFragment6();
            }

        });


        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundManager.playButtonSound(getContext());
                Fragment7 popupFragment = Fragment7.newInstance();
                popupFragment.show(getChildFragmentManager(), "PopupFragment");
            }

        });



        return rootView;
    }
}
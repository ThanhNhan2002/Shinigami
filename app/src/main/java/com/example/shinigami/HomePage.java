package com.example.shinigami;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomePage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePage extends Fragment {
    GoogleSignInOptions googleSignInOptions;

    public HomePage() {
        // Required empty public constructor
    }

    public static HomePage newInstance(String param1, String param2) {
        HomePage fragment = new HomePage();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private void switchToItemPageFragment(int deviceId) {
        // Create a new instance of the ItemPage class
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
            // handle the parameters
        }
    }

    // get an instance of the Firebase database
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    // create a collection reference to devices collection
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
        ImageButton addDeviceButton = rootView.findViewById(R.id.addDeviceButton);
        Button logOutButton = rootView.findViewById(R.id.logOutButton);

        // Set onClickListener for each button
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundManager.playButtonSound(getContext());
                switchToItemPageFragment(1);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundManager.playButtonSound(getContext());
                switchToItemPageFragment(2);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundManager.playButtonSound(getContext());
                switchToItemPageFragment(3);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundManager.playButtonSound(getContext());
                switchToItemPageFragment(4);
            }
        });

        addDeviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundManager.playButtonSound(getContext());
                NewItemPopup popupFragment = NewItemPopup.newInstance();
                popupFragment.show(getChildFragmentManager(), "PopupFragment");
            }
        });

        // log out button
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundManager.playButtonSound(getContext());
                SignOut();

                LoginPage loginPage = new LoginPage();
                // Replace the existing layout with LoginPage
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.mainLayout, loginPage).commit();
            }
        });
        return rootView;
    }
    public void SignOut() {
        GoogleSignInClient googleSignInClient = LoginPage.googleSignInClient;
        googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // finish();
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });
    }
}
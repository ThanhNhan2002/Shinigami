package com.example.shinigami;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

public class RegistrationPage extends Fragment {
    public RegistrationPage() {
        // Required empty public constructor
    }

    public static RegistrationPage newInstance(String param1, String param2) {
        RegistrationPage fragment = new RegistrationPage();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    Button done_button;
    EditText first_name;
    EditText last_name;
    EditText dob;
    TextView header;

    private OnUserInfoEnteredListener onUserInfoEnteredListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // handle the parameters
        }
    }

    public interface OnUserInfoEnteredListener {
        void onUserInfoEntered(String firstName, String lastName, String dob);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onUserInfoEnteredListener = (OnUserInfoEnteredListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnUserInfoEnteredListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.registration_page, container, false);
        done_button = rootView.findViewById(R.id.done_button);
        first_name =  rootView.findViewById(R.id.text_firstname);
        last_name = rootView.findViewById(R.id.text_lastname);
        dob = rootView.findViewById(R.id.dob);
        header = rootView.findViewById(R.id.textView);

        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.animate);
        header.startAnimation(animation);


        first_name.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    last_name.requestFocus();
                    last_name.requestFocusFromTouch();
                    return true;
                }
                return false;
            }
        });

        // Set up the key listener for the last name EditText to proceed to the dob EditText
        last_name.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    dob.requestFocus();
                    return true;
                }
                return false;
            }
        });

        done_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SoundManager.playButtonSound(getContext()); //sound effect

                String firstName = first_name.getText().toString();
                String lastName = last_name.getText().toString();
                String dateOfBirth = dob.getText().toString();

                // Input data into interface to be sent to main activity where it will be stored in firestore

                if (onUserInfoEnteredListener != null) {
                    onUserInfoEnteredListener.onUserInfoEntered(firstName, lastName, dateOfBirth);}

                // Instantiate the new fragment
                Fragment newFragment = new HouseIDPage();

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.mainLayout, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }

        });
        return rootView;
    }
}
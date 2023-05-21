package com.example.shinigami;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewItemPopup#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewItemPopup extends DialogFragment {
    public NewItemPopup() {
        // Required empty public constructor
    }

    public static NewItemPopup newInstance(String param1, String param2) {
        NewItemPopup fragment = new NewItemPopup();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // handle the parameters
        }
    }

    private void switchToFragment5() {
        // Create a new instance of the Fragment6 class
        Fragment newFragment = new NewItemPopup();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.mainLayout, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private EditText mDeviceNameEditText;
    private EditText mDeviceTypeEditText;
    private SwitchCompat mStatusSwitch;

    public static NewItemPopup newInstance() {
        return new NewItemPopup();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.popup_page, container, false);

        // Find the views from the layout
        mDeviceNameEditText = view.findViewById(R.id.device_name_edittext);
        mDeviceTypeEditText = view.findViewById(R.id.device_type_edittext);
        mStatusSwitch = view.findViewById(R.id.status_switch);

        Button doneButton = view.findViewById(R.id.button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add logic for saving data to database or performing other actions
                String deviceName = mDeviceNameEditText.getText().toString();
                String deviceDesc = mDeviceNameEditText.getText().toString();
                boolean isWorking = mStatusSwitch.isChecked();
                Log.d("addDevice", "deviceName: "  + deviceName);
                Log.d("addDevice", "deviceDesc: "  + deviceDesc);
                Log.d("addDevice", "isWorking: "  + isWorking);

                Device newDevice = new Device(5, deviceName, deviceDesc,  isWorking);
                MainActivity.fbHelper.db
                        .collection("Houses")
                        .document(1+"")
                        .collection("houseDevices")
                        .add(newDevice);
                SoundManager.playButtonSound(getContext());
                dismiss();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Set the width and height of the dialog
        if (getDialog() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}


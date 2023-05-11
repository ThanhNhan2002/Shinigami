package com.example.shinigami;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment6#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment6 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int fragmentDeviceId;


    public Fragment6() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment6.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment6 newInstance(String param1, String param2) {
        Fragment6 fragment = new Fragment6();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    static Boolean isTouched = false;
    static Boolean dataFetched = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_6, container, false);
        TextView deviceIdTextView = rootView.findViewById(R.id.deviceIdTextView);
        TextView deviceNameTextView = rootView.findViewById(R.id.deviceNameTextView);
        TextView deviceDescTextView = rootView.findViewById(R.id.deviceDescTextView);
        TextView isWorkingTextView = rootView.findViewById(R.id.isWorkingTextView);
        SwitchCompat isWorkingSwitch = rootView.findViewById(R.id.isWorkingSwitch);



//        isWorkingSwitch.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                isTouched = true;
//                return false;
//            }
//        });
//
//        isWorkingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
//        {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
//            {
//                if (isTouched) {
//                    Log.d("Switch", "Changed due to user");
//                    isTouched = false;
//                    if (isWorkingSwitch.isChecked()) {
//                        Log.d("Switch", "Status: " + isWorkingSwitch.isChecked());
//                        isWorkingSwitch.setChecked(false);
//                        Log.d("Switch", "Toggle Off");
//                    }
//                    else {
//                        Log.d("Switch", "Status: " + isWorkingSwitch.isChecked());
//                        isWorkingSwitch.setChecked(true);
//                        Log.d("Switch", "Toggle On");
//                    }
//                }
//            }
//        });

        isWorkingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                Log.d("Switch", "Status: adsfasdf ");
                    Log.d("Switch", "Status: " + isWorkingSwitch.isChecked());
                    if (isWorkingSwitch.isChecked()) {
                        Log.d("Switch", "Status: " + isWorkingSwitch.isChecked());
                        Log.d("Switch", "Toggled on");
                        MainActivity.fbHelper.db
                                .collection("Houses")
                                .document(1+"")
                                .collection("houseDevices")
                                .document(fragmentDeviceId+"")
                                .update("isWorking", isWorkingSwitch.isChecked());
                    } else {
                        Log.d("Switch", "Status: " + isWorkingSwitch.isChecked());
                        Log.d("Switch", "Toggled Off");
                        MainActivity.fbHelper.db
                                .collection("Houses")
                                .document(1+"")
                                .collection("houseDevices")
                                .document(fragmentDeviceId+"")
                                .update("isWorking", isWorkingSwitch.isChecked());
                    }
            }
        });



        getParentFragmentManager().setFragmentResultListener("dataFromHomePage", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                int deviceId = result.getInt("deviceId");
                MainActivity.fbHelper.db
                        .collection("Houses")
                        .document(1+"")
                        .collection("houseDevices")
                        .document(deviceId+"")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        String deviceId = Objects.requireNonNull(document.get("deviceId")).toString();
                                        String deviceName = Objects.requireNonNull(document.get("deviceName")).toString();
                                        String deviceDesc = Objects.requireNonNull(document.get("deviceDesc")).toString();
                                        String isWorking = Objects.requireNonNull(document.get("isWorking")).toString();
                                        fragmentDeviceId = Integer.parseInt(deviceId);

                                        Map<String, String> deviceStatuses = (Map) document.get("deviceStatuses");
                                        for (Map.Entry<String, String> entry : deviceStatuses.entrySet()) {
                                            Log.d( "getMap", entry.getKey() + ": " + entry.getValue());
                                        }

                                        deviceIdTextView.setText("deviceId: " + deviceId);
                                        deviceNameTextView.setText("deviceName: " + deviceName);
                                        deviceDescTextView.setText("deviceDesc: " + deviceDesc);
                                        isWorkingTextView.setText("isWorking: " + isWorking);
                                        isWorkingSwitch.setChecked(Boolean.parseBoolean(isWorking));
                                        Log.d("Switch", "Staus after reading database" + isWorkingSwitch.isChecked());
                                        dataFetched = true;
                                    } else {
                                        Log.d("DeviceFragment", "No such document");
                                    }
                                } else {
                                    Log.d("DeviceFragment", "Error getting documents: ", task.getException());
                                }
                            }
                        });
            }
        });



        return rootView;
    }

}
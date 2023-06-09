package com.example.shinigami;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ItemPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemPage extends Fragment {
    private int fragmentDeviceId;
    public ItemPage() {
        // Required empty public constructor
    }

    public static ItemPage newInstance(String param1, String param2) {
        ItemPage fragment = new ItemPage();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // handle arguments
        }
    }

    static Boolean dataFetched = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.item_page, container, false);
        TextView deviceIdTextView = rootView.findViewById(R.id.deviceIdTextView);
        TextView deviceNameTextView = rootView.findViewById(R.id.deviceNameTextView);
        TextView deviceDescTextView = rootView.findViewById(R.id.deviceDescTextView);
        TextView isWorkingTextView = rootView.findViewById(R.id.isWorkingTextView);
        SwitchCompat isWorkingSwitch = rootView.findViewById(R.id.isWorkingSwitch);

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
                                        try {
                                            String deviceId = document.get("deviceId") == null ? "null" : document.get("deviceId").toString();
                                            String deviceName = document.get("deviceName") == null ? "null" : document.get("deviceName").toString();

                                            String deviceDesc =  document.get("deviceDesc") == null ? "null" : document.get("deviceDesc").toString();
                                            String isWorking = document.get("isWorking") == null ? "null" : document.get("isWorking").toString();
                                            fragmentDeviceId = Integer.parseInt(deviceId);

                                            Map<String, String> deviceStatuses = (Map) document.get("deviceStatuses");
                                            for (Map.Entry<String, String> entry : deviceStatuses.entrySet()) {
                                                Log.d("getMap", entry.getKey() + ": " + entry.getValue());
                                            }

                                            deviceIdTextView.setText("deviceId: " + deviceId);
                                            deviceNameTextView.setText("deviceName: " + deviceName);
                                            deviceDescTextView.setText("deviceDesc: " + deviceDesc);
                                            isWorkingTextView.setText("isWorking: " + isWorking);
                                            isWorkingSwitch.setChecked(Boolean.parseBoolean(isWorking));
                                            Log.d("Switch", "Staus after reading database" + isWorkingSwitch.isChecked());
                                            dataFetched = true;
                                        } catch (Exception e) {
                                            Toast toast = Toast.makeText(getActivity(), "Error: Some of the field is null, cannot fetch data" , Toast.LENGTH_SHORT);
                                            toast.show();
                                        }
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
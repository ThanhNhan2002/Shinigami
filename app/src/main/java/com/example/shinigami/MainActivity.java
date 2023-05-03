package com.example.shinigami;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class MainActivity extends AppCompatActivity implements Fragment3.OnUserInfoEnteredListener {
    private String TAG = "MainActivity";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userRef = db.collection("Users");

    private FbHelper fbHelper;
    private static ArrayList<User> userList = new ArrayList<User>();
    private static ArrayList<House> houseList = new ArrayList<House>();
    private static ArrayList<Device> deviceList = new ArrayList<Device>();

    // Google Authentication
    ImageView googleButton;

    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;

    public void onUserInfoEntered(String firstName, String lastName, String dateOfBirth) {   //Thanh this function has values already in it from fragment
        User user4 = new User( 4,firstName, lastName, dateOfBirth);

    }



    private Button loginButton;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            loginButton = findViewById(R.id.login_button);

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Instantiate the new fragment
                    Fragment newFragment = new Fragment3();

                    // Replace the existing fragment in the container with the new one
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainLayout, newFragment)
                            .commit();
                }
                });

        //make a FbHelper object
        fbHelper = new FbHelper(db);

        User user1 = new User( 1,"Thanh Nhan", "Nguyen", "11-02-2002");
        User user2 = new User(2,"Hamas", "Massood", "11-02-2002");
        User user3 = new User(3,"Page Test 23-Apr Wireless connection", "Perret", "11-02-2002");
//        Example for Hamas
//        int user1Id = user1.getUserId();
//        String user1FirstName = user1.getFirstName();
//        String user1LastName = user1.getFirstName();
//        String user1Dob = user1.getDob();


        userList.add(user1);
        userList.add(user2);
        userList.add(user3);

        House house1 = new House( 1,"06 Bundoora Test Override");
        House house2 = new House( 2,"07 Bundoora");
        House house3 = new House(3,"08 Bundoora");
//                Example for Hamas
//        int house1Id = house1.getHouseId();
//        String house1Address = house1.getHouseAddress();  // @Hamas you can use the address as the house name
//        ArrayList<User> house1UserList = house1.getUsers();
//        ArrayList<Device> house1DeviceList = house1.getDevices();
        houseList.add(house1);
        houseList.add(house2);
        houseList.add(house3);

// Camera device
        Device camera = new Device(1, "Camera", "Living Room Camera");
        camera.addStatus("isOn", "yes");
        camera.addStatus("motionDetected", "no");
//        Example for Hamas
//        String deviceName = camera.getDeviceName();
//        String deviceDesc = camera.getDeviceDesc();
//        boolean isWorking = camera.getIsWorking();
//        HashMap<String, String> statusList = camera.getDeviceStatuses();

// TV device
        Device tv = new Device(2, "TV", "Bedroom TV");
        tv.addStatus("isOn", "yes");
        tv.addStatus("volume", "50");

// Fridge device
        Device fridge = new Device(3, "Fridge", "Kitchen Fridge");
        fridge.addStatus("isOn", "yes");
        fridge.addStatus("temperature", "4");

// Thermometer device
        Device thermometer = new Device(4, "Thermometer", "Bedroom Thermometer");
        thermometer.addStatus("isOn", "yes");
        thermometer.addStatus("temperature", "22");
        thermometer.addStatus("humidity", "40");


// Add devices to list
        deviceList.add(camera);
        deviceList.add(tv);
        deviceList.add(fridge);
        deviceList.add(thermometer);

        for(House house: houseList) {
            fbHelper.addHouseWithId(house);
        }

        for(User user: userList) {
            fbHelper.addUserWithId(user);
        }

        for(Device device: deviceList) {
            fbHelper.addDeviceWithId(device);
        }

        fbHelper.addUserToHouse(1,1);
        fbHelper.addUserToHouse(1,2);
        fbHelper.addUserToHouse(2,3);

        fbHelper.addDeviceToHouse(1, 1);
        fbHelper.addDeviceToHouse(1, 2);
        fbHelper.addDeviceToHouse(1, 3);
        fbHelper.addDeviceToHouse(1, 4);

        // Google Authentication


        googleButton=findViewById(R.id.googleSigninButton);
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleSignInHelper();
            }
        });
    }


    public static User getUserById(int Id) {
        for (User user: userList) {
            if(user.getUserId() == Id) {
                return user;
            }
        }
        return null;
    }

    public static Device getDeviceById(int Id) {
        for (Device device: deviceList) {
            if(device.getDeviceId() == Id) {
                return device;
            }
        }
        return null;
    }

    // Google authentication
    private void GoogleSignInHelper() {
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                task.getResult(ApiException.class);
                AuthedHomeActivity();
            } catch (ApiException e) {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void AuthedHomeActivity() {
        finish();
        Intent intent = new Intent(getApplicationContext(), AuthedHomeActivity.class);
        startActivity(intent);
    }
}
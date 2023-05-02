package com.example.shinigami;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import android.util.Log;

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


public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userRef = db.collection("Users");

    private FbHelper fbHelper;
    private static ArrayList<User> userList = new ArrayList<User>();
    private static ArrayList<House> houseList = new ArrayList<House>();
    private static ArrayList<Device> deviceList = new ArrayList<Device>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //make a FbHelper object
        fbHelper = new FbHelper(db);

        User user1 = new User( 1,"Thanh Nhan", "Nguyen", "11-02-2002");
        User user2 = new User(2,"Hamas", "Massood", "11-02-2002");
        User user3 = new User(3,"Page Test 23-Apr Wireless connection", "Perret", "11-02-2002");
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);

        House house1 = new House( 1,"06 Bundoora Test Override");
        House house2 = new House( 2,"07 Bundoora");
        House house3 = new House(3,"08 Bundoora");
        houseList.add(house1);
        houseList.add(house2);
        houseList.add(house3);

// Camera device
        Device camera = new Device(1, "Camera", "Living Room Camera");
        camera.addStatus("isOn", "yes");
        camera.addStatus("motionDetected", "no");

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
}
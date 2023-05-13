package com.example.shinigami;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;

import android.media.MediaPlayer;

import android.content.pm.PackageManager;
import android.os.Build;

import android.os.Bundle;

import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity implements Fragment3.OnUserInfoEnteredListener {
    public static FbHelper fbHelper;
    private String TAG = "MainActivity";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userRef = db.collection("Users");
    public static ArrayList<User> userList = new ArrayList<User>();
    private static ArrayList<House> houseList = new ArrayList<House>();
    private static ArrayList<Device> deviceList = new ArrayList<Device>();

    public void onUserInfoEntered(String firstName, String lastName, String dateOfBirth) {   //Thanh this function has values already in it from fragment
        User user4 = new User( "4",firstName, lastName, dateOfBirth);

        fbHelper.addUserWithId(user4);
        fbHelper.addUserToHouse(1, "4");
    }

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hap);
        fbHelper = new FbHelper(db);

//        populateDatabase();

            SoundManager.playIntroSound(this);
            TextView textView = findViewById(R.id.hap_text);
            Animation animation = new AlphaAnimation(0.0f, 1.0f); // Change alpha from fully transparent to fully opaque
            animation.setDuration(1000); // Duration of the animation is 1000 milliseconds (1 second)
            animation.setRepeatCount(3); // Repeat animation infinitely
            animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the TextView will fade back out
            textView.startAnimation(animation);
            // Delay for 2 seconds (2000 milliseconds)
            int delayMillis = 5000;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Create a new instance of Fragment2
                    Fragment2 fragment2 = new Fragment2();

                    // Replace the existing layout with Fragment2
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainLayout, fragment2)
                            .commit();
                }
            }, delayMillis);

            // Google cloud messaging push notification
            MyFirebaseMessagingServices.getInstance().subscribeToTopic("News")
                .addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        String msg = "Done";
                        if (!task.isSuccessful())
                        {
                            msg = "Failed";
                        }
                    }
                });
            }


    private void populateDatabase() {
        User user1 = new User( "1","Thanh Nhan", "Nguyen", "11-02-2002");
        User user2 = new User("2","Hamas", "Massood", "11-02-2002");
        User user3 = new User("3","Page", "Perret", "11-02-2002");

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

        fbHelper.addUserToHouse(1,"1");
        fbHelper.addUserToHouse(1,"2");
        fbHelper.addUserToHouse(2,"3");

        fbHelper.addDeviceToHouse(1, 1);
        fbHelper.addDeviceToHouse(1, 2);
        fbHelper.addDeviceToHouse(1, 3);
        fbHelper.addDeviceToHouse(1, 4);

    }
}



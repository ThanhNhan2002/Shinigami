package com.example.shinigami;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.os.Bundle;

import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;


import java.util.ArrayList;

import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity implements RegistrationPage.OnUserInfoEnteredListener {
    public static FbHelper fbHelper;
    private String TAG = "MainActivity";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userRef = db.collection("Users");
    public static ArrayList<User> userList = new ArrayList<User>();
    private static ArrayList<House> houseList = new ArrayList<House>();
    private static ArrayList<Device> deviceList = new ArrayList<Device>();

    // handle the information that user enters in log in page
    public void onUserInfoEntered(String firstName, String lastName, String dateOfBirth) {
        // this onUserInfoEntered function has values already in it from fragment
        User user4 = new User( "4",firstName, lastName, dateOfBirth);

        fbHelper.addUserWithId(user4);
        fbHelper.addUserToHouse(1, "4");
    }

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hap);
        fbHelper = new FbHelper(db);

            // Sound and Animation control
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
                    LoginPage loginPage = new LoginPage();

                    // Replace the existing layout with Fragment2
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainLayout, loginPage)
                            .commit();
                }
            }, delayMillis);

            // Google cloud messaging push notification
            // Get an instance of MyFirebaseMessagingServices class
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
            // populateDatabase();
    }


    private void populateDatabase() {
        // Create some new users
        User user1 = new User( "1","Thanh Nhan", "Nguyen", "11-02-2002");
        User user2 = new User("2","Hamas", "Massood", "11-02-2002");
        User user3 = new User("3","Page", "Perret", "11-02-2002");

        // Add users to userList
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);

        // Create some new houses
        House house1 = new House( 1,"06 Bundoora Test Override");
        House house2 = new House( 2,"07 Bundoora");
        House house3 = new House(3,"08 Bundoora");

        // Add houses to houseList
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

        // Add houseList to Firebase
        for(House house: houseList) {
            fbHelper.addHouseWithId(house);
        }

        // // Add userList to Firebase
        for(User user: userList) {
            fbHelper.addUserWithId(user);
        }

        // Add deviceList to Firebase
        for(Device device: deviceList) {
            fbHelper.addDeviceWithId(device);
        }

        // Add users to houses
        fbHelper.addUserToHouse(1,"1");
        fbHelper.addUserToHouse(1,"2");
        fbHelper.addUserToHouse(2,"3");

        // Add devices to houses
        fbHelper.addDeviceToHouse(1, 1);
        fbHelper.addDeviceToHouse(1, 2);
        fbHelper.addDeviceToHouse(1, 3);
        fbHelper.addDeviceToHouse(1, 4);
    }
}



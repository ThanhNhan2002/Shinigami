package com.example.shinigami;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.os.Bundle;
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
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userRef = db.collection("Users");

    private String TAG = "FireStore";
    private FbHelper fbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //make a FbHelper object
        fbHelper = new FbHelper(db);
        fbHelper.addUser(new User( 1,"Thanhhh Nhan", "Nguyen", "11-02-2002"));
        fbHelper.addUser(new User( 2,"Hamaaaas", "Masood", "11-02-2002"));
        fbHelper.addUser(new User( 3,"Pppage", "Perret", "11-02-2002"));

        fbHelper.getUsers();
    }
}
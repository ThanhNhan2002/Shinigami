package com.example.shinigami;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FbHelper {
    private FirebaseFirestore db;
    private String TAG = "FbHelper";

    public FbHelper(FirebaseFirestore db) {
        this.db = db;
    }

    public void addUser(User u) {
        Map<String, Object> user = new HashMap<>();
        user.put("id", u.getUserId());
        user.put("firstName ", u.getFirstName());
        user.put("lastName", u.getLastName());
        user.put("dob", u.getDob());

        // Add a new document with a generated ID
        db.collection("Users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "User DocumentSnapshot added with ID: " + documentReference.getId());

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding user document", e);
                    }
                });
    }

    public void addUserWithId(User u) {
        Map<String, Object> user = new HashMap<>();
        user.put("id", u.getUserId());
        user.put("firstName ", u.getFirstName());
        user.put("lastName", u.getLastName());
        user.put("dob", u.getDob());

        // Add a new document with a generated ID
        db.collection("Users").document(""+u.getUserId())
                .set(user);
    }

    public void getUsers () {
//        DocumentReference document = db.collection("Users").document("AOgFm3Si1WXALt1DCj7o");
//        document.get().addOnSuccessListener()
        db.collection("Users")
                .get ( )
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                } ) ;
    }

    public void addHouse(House h) {
        Map<String, Object> house = new HashMap<>();
        house.put("houseId", h.getHouseId());
        house.put("houseAddress ", h.getHouseAddress());

        // Add a new document with a generated ID
        db.collection("Houses")
                .add(house)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "House DocumentSnapshot added with ID: " + documentReference.getId());

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding House document", e);
                    }
                });

//        Map<String, Object> user1 = new HashMap<>();
//        user1.put("houseId", h.getHouseId());
//        user1.put("houseAddress ", h.getHouseAddress());

//        db.collection("Houses").document("HouseUsers").add(house);
    }
    public void addHouseWithId(House h) {
        int houseId = h.getHouseId();
        String houseAdress = h.getHouseAddress();
        ArrayList<User> houseUsers = h.getUsers();

        Map<String, Object> house = new HashMap<>();
        house.put("houseId", houseId);
        house.put("houseAddress ", houseAdress);

        // Add a new document with a generated ID
        db.collection("Houses").document(""+h.getHouseId())
                .set(house);

        for (int i = 0; i <  houseUsers.size(); i++) {
            User currentUser =  houseUsers.get(i);

            int userId = currentUser.getUserId();
            String firstName = currentUser.getFirstName();
            String lastName = currentUser.getLastName();
            String dob = currentUser.getDob();

            Map<String, Object> dbUser = new HashMap<>();
            dbUser.put("userId", userId);
            dbUser.put("firstName", firstName);
            dbUser.put("lastName", lastName);
            dbUser.put("dob", dob);

            db.collection("Houses").document(""+h.getHouseId())
                    .collection("users").document(""+userId).set(dbUser);
        }

//        user1.put("userId", h.getUsers().get(0).getUserId());
//        user1.put("userFirstName", h.getUsers().get(0).getFirstName());





//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d(TAG, "House DocumentSnapshot added with ID: " + documentReference.getId());
//
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w(TAG, "Error adding House document", e);
//                    }
//                });

//        Map<String, Object> user1 = new HashMap<>();
//        user1.put("houseId", h.getHouseId());
//        user1.put("houseAddress ", h.getHouseAddress());

//        db.collection("Houses").document("HouseUsers").add(house);
    }

    public void addUserToHouse(int houseId, int userId) {
        DocumentReference houseReference = db.collection("Houses").document(houseId+"");
        houseReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "House with id"+ houseId +"exists: "+document);
                    } else {
                        Log.d(TAG, "House with id"+ houseId +"does not exists: "+ document);
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });

        DocumentReference userReference = db.collection("Users").document(userId+"");
        userReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "User with id"+ userId +"exists: "+document);
                    } else {
                        Log.d(TAG, "User with id"+ userId +"does not exists: "+ document);
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });
        //Log.d(TAG, "house ref: "+houseReference+"");
    }

}

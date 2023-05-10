package com.example.shinigami;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment2 newInstance(String param1, String param2) {
        Fragment2 fragment = new Fragment2();
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
    }}


    private void switchToFragment3() {
        // Create a new instance of the Fragment6 class
        Fragment newFragment = new Fragment3();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.mainLayout, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    // Google authentication
    private void GoogleSignInHelper() {
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                task.getResult(ApiException.class);
                AuthedHomeActivity();
            } catch (ApiException e) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void AuthedHomeActivity() {
        getActivity().finish();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
        if(account!=null) {
            String name = account.getDisplayName();
            String userId = account.getId();
            String email = account.getEmail();
            String firstName = account.getGivenName();
            String lastName = account.getGivenName();
            String dob = "No data";

            // nameTextView.setText(name + "" + email  +" " + firstName  +" " + lastName +" " + dob) ;
            User newUser = new User(email, firstName,lastName, dob );
            MainActivity.userList.add(newUser);
            MainActivity.fbhelper.addUserWithId(newUser);
        }

        Intent intent = new Intent(getContext(), AuthedHomeActivity.class);
        startActivity(intent);}


    private Button loginButton;
    private Button signupButton;
    private ImageView googleButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_2, container, false);

        loginButton = rootView.findViewById(R.id.login_button);
        signupButton = rootView.findViewById(R.id.signup_button);
        googleButton=rootView.findViewById(R.id.googleSigninButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SoundManager.playButtonSound(getContext());
                // Instantiate the new fragment
                switchToFragment3();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SoundManager.playButtonSound(getContext());

                // Instantiate the new fragment
                switchToFragment3();
            }
        });

        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();

                googleSignInClient = GoogleSignIn.getClient(getContext(), googleSignInOptions);

                GoogleSignInHelper();
            }
        });





        return rootView;
    }
}
package com.example.shinigami;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class HouseIDPage extends Fragment {
    public HouseIDPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment4.
     */
    // TODO: Rename and change types and number of parameters
    public static HouseIDPage newInstance(String param1, String param2) {
        HouseIDPage fragment = new HouseIDPage();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    Button done_button2;

    EditText houseid_input;

    TextView enter_houseid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //handle parameters
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.houseid_page, container, false);
        done_button2 = rootView.findViewById(R.id.done_button2);
        houseid_input =  rootView.findViewById(R.id.houseid_input);
        enter_houseid = rootView.findViewById(R.id.enter_houseid);

        done_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundManager.playButtonSound(getContext());

                // Get the entered house ID
                String houseId = houseid_input.getText().toString();

                getActivity().setTitle(houseId);

                // Create a new instance of the Fragment5 class
                HomePage newFragment = new HomePage();

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.mainLayout, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();}
        });
        return rootView;
    }
}

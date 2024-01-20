package com.shubham.umerapp.User.Fragments;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.graphics.Color;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.database.collection.LLRBNode;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.shubham.umerapp.R;
import com.shubham.umerapp.Utils.helperFunctions;

import java.time.LocalDate;

public class fragment2 extends Fragment {
    TextView day,month, morningStatus, eveningStatus;
    public String rawDate;
    String s_year, s_month, s_day, s_hour, s_min;
    FirebaseFirestore db;
    FirebaseAuth auth;
    public LocalDate currDate;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment2, container, false);

        day = view.findViewById(R.id.txtday);
        month = view.findViewById(R.id.txtmonth);
        morningStatus = view.findViewById(R.id.morningStatus);
        eveningStatus = view.findViewById(R.id.eveningStatus);

        helperFunctions hp = new helperFunctions();
        rawDate = hp.getCurrentTime();

        s_year = rawDate.substring(30, 34);
        s_month = rawDate.substring(4,7);
        s_day = rawDate.substring(8,10);
        s_hour = rawDate.substring(11,13);
        s_min = rawDate.substring(14,16);
        day.setText(s_day);
        month.setText(s_month);
        day.setTextSize(96);
        month.setTextSize(48);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        String emailOfUser = auth.getCurrentUser().getEmail();

        // Morning Session Text Value, and live update

        db.collection("users")
                .whereEqualTo("Email", emailOfUser)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String userId = document.getId();
                            // Continue to the next step with the user's ID (userId)
                            checkMorningSessionDocument(userId);

                            final DocumentReference docRef = db.collection("morningSession").document(userId);
                            docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot snapshot,
                                                    @Nullable FirebaseFirestoreException e) {
                                    if (e != null) {
                                        Log.e(TAG, "Listen failed Morning", e);
                                        return;
                                    }
                                    if (snapshot != null && snapshot.exists()) {

//                                        Log.e("Current data: " , snapshot.getData().toString());
                                        checkMorningSessionDocument(userId);
                                    } else {

                                    }
                                }
                            });

                        }
                    } else {
                        // Handle errors
                        Log.d(TAG, "Morning not success ");
                        Toast.makeText(getContext(), "Morning not success ", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e->{
                    Log.d(TAG, "Morning wrong: " + e);
                    Toast.makeText(getContext(), "Morning wrong: "+e, Toast.LENGTH_SHORT).show();
                });

        // Evening Session Text value, and live update
        db.collection("users")
                .whereEqualTo("Email", emailOfUser)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String userId = document.getId();
                            // Continue to the next step with the user's ID (userId)
                            checkEveningSessionDocument(userId);

                            final DocumentReference docRef = db.collection("eveningSession").document(userId);
                            docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot snapshot,
                                                    @Nullable FirebaseFirestoreException e) {
                                    if (e != null) {
                                        Log.w(TAG, "Listen failed Evening", e);
                                        return;
                                    }
                                    if (snapshot != null && snapshot.exists()) {

//                                        Log.e("Current data: " , snapshot.getData().toString());
                                        checkEveningSessionDocument(userId);
                                    } else {

                                    }
                                }
                            });

                        }
                    } else {
                        // Handle errors
                        Log.d(TAG, "Evening not success");
                        Toast.makeText(getContext(), "Evening not success", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e->{
                    Log.d(TAG, "Morning wrong: " + e);
                    Toast.makeText(getContext(), "Morning wrong: "+e, Toast.LENGTH_SHORT).show();
                });
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void checkMorningSessionDocument(String userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        currDate = LocalDate.now();

        db.collection("morningSession")
                .document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // Document exists, retrieve the current date and boolean value
                            if(document.getData().containsKey(currDate.toString())){

                                boolean booleanValue = document.getBoolean(currDate.toString());

                                if(booleanValue){
                                    morningStatus.setText("Morning Supply: Received");
                                    morningStatus.setTextColor(Color.BLACK);
                                }else{
                                    morningStatus.setText("Morning Supply: Not Received");
                                    morningStatus.setTextColor(Color.RED);
                                }
//                                Log.e("Doc : " , String.valueOf(booleanValue));

                            }else{
                                // Document doesn't exist
                                Log.e(TAG,"Morning date doesn't exist" );
                                morningStatus.setText("Morning Status: Not received");
                                morningStatus.setTextColor(Color.RED);
                            }

                        } else {
                            // Document doesn't exist
                            // Handle the case where the document doesn't exist
                            Log.e(TAG, "Morning doc don't exists");
                        }
                    } else {
                        // Handle errors
                        Log.e(TAG, "Morning else wrong");
                    }
                });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void checkEveningSessionDocument(String userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        currDate = LocalDate.now();

        db.collection("eveningSession")
                .document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // Document exists, retrieve the current date and boolean value
                            if(document.getData().containsKey(currDate.toString())){
                                boolean booleanValue = document.getBoolean(currDate.toString());

                                if(booleanValue){
                                    eveningStatus.setText("Evening Supply: Received");
                                    eveningStatus.setTextColor(Color.BLACK);
                                }else{
                                    eveningStatus.setText("Evening Supply: Not Received");
                                    eveningStatus.setTextColor(Color.RED);
                                }
//                                Log.e("Doc : " , String.valueOf(booleanValue));

                            }else{
                                // Document doesn't exist
                                Log.e(TAG,"Evening date doesn't exist" );
                                eveningStatus.setText("Evening Status: Not received");
                                eveningStatus.setTextColor(Color.RED);
                            }

                        } else {
                            // Document doesn't exist
                            // Handle the case where the document doesn't exist
                            Log.e(TAG, "Evening doc don't exists");
                        }
                    } else {
                        // Handle errors
                        Log.e(TAG, "Evening else wrong");
                    }
                });
    }
}
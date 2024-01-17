package com.shubham.umerapp.User.Fragments;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.MotionEffect;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.PersistableBundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.shubham.umerapp.R;
import com.shubham.umerapp.databinding.FragmentFragment1Binding;
import com.shubham.umerapp.databinding.UserHomeActivityBinding;
import com.shubham.umerapp.helperFunctions;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class fragment1 extends Fragment {
    String documentId = new String();
    FirebaseFirestore db;
    FirebaseAuth auth;
    TextView hii;
    Button yes,no;
    ProgressBar pg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);

        hii = view.findViewById(R.id.hii);
        yes = view.findViewById(R.id.yes);
        no = view.findViewById(R.id.no);
        pg = view.findViewById(R.id.feedbackProgressbar);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        helperFunctions funcs = new helperFunctions();
        hii.setVisibility(View.GONE);

        String emailOfUser = auth.getCurrentUser().getEmail();

                db.collection("users")
                .whereEqualTo("Email", emailOfUser)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            documentId = document.getId().toString();
                            db.collection("users").document(documentId).get().addOnCompleteListener(task1 ->
                            {
                                DocumentSnapshot documentSnapshot = task1.getResult();
                                hii.setVisibility(View.VISIBLE);
                                hii.setText("Hii, "+documentSnapshot.getString("Name"));
                            });
                        }
                    }

                }).addOnFailureListener(e->{
                    Toast.makeText(getContext(), "Something went wrong!!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "error in fetching the document : "+e);
                });

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userEmail = auth.getCurrentUser().getEmail();

                        Map<String , Object> feedback = new HashMap<>();
                        feedback.put(funcs.getCurrentDate(), true);

                        Date currentTime = Calendar.getInstance().getTime();

                        Toast.makeText(getContext(),currentTime.toString(),Toast.LENGTH_SHORT).show();


                        pg.setVisibility(View.VISIBLE);

                        // getting document id of user from email
                        db.collection("users")
                                .whereEqualTo("Email", userEmail)
                                .get()
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            String documentId = document.getId();

                                            // checking if the document id is present in collection
                                            db.collection("morningSession").document(documentId).update(feedback)
                                                    .addOnSuccessListener(aVoid -> {
                                                        Log.d(MotionEffect.TAG, "Feedback sent successfully!");
                                                        Toast.makeText(getContext(), "Feedback sent successfully!", Toast.LENGTH_SHORT).show();
                                                        pg.setVisibility(View.GONE);
                                                    }).addOnFailureListener(e -> {
                                                        // If the document doesn't exist, create a new one
                                                        db.collection("morningSession").document(documentId).set(feedback)
                                                                .addOnSuccessListener(aVoid1 -> {
                                                                    pg.setVisibility(View.GONE);
                                                                    Toast.makeText(getContext(), "Feedback added successfully!", Toast.LENGTH_SHORT).show();
                                                                    Log.d(MotionEffect.TAG, "Feedback added successfully!");
                                                                })
                                                                .addOnFailureListener(e1 -> {
                                                                    pg.setVisibility(View.GONE);
                                                                    Toast.makeText(getContext(), "Something went wrong!!", Toast.LENGTH_SHORT).show();
                                                                    Log.d(MotionEffect.TAG, "Error in creating database : " + e1.getMessage());
                                                                });
                                                    });

                                        }
                                    }
                                    else
                                    {
                                        //  user database does not exist can be created using updating profile
                                        Toast.makeText(getContext(), "Update your profile first..", Toast.LENGTH_SHORT).show();
                                    }

                                }).addOnFailureListener(e -> {
                                    Toast.makeText(getContext(), "Something went wrong!! Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();

                                });

                    }
                });

                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userEmail = auth.getCurrentUser().getEmail();

                        Map<String , Object> feedback = new HashMap<>();
                        feedback.put(funcs.getCurrentDate(), false);


                        pg.setVisibility(View.VISIBLE);

                        // getting document id of user from email
                        db.collection("users")
                                .whereEqualTo("Email", userEmail)
                                .get()
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            String documentId = document.getId();

                                            // checking if the document id is present in collection
                                            db.collection("morningSession").document(documentId).update(feedback)
                                                    .addOnSuccessListener(aVoid -> {
                                                        Log.d(MotionEffect.TAG, "Feedback sent successfully!");
                                                        Toast.makeText(getContext(), "Feedback sent successfully!", Toast.LENGTH_SHORT).show();
                                                        pg.setVisibility(View.GONE);

                                                    })
                                                    .addOnFailureListener(e -> {
                                                        // If the document doesn't exist, create a new one
                                                        db.collection("morningSession").document(documentId).set(feedback)
                                                                .addOnSuccessListener(aVoid1 -> {
                                                                    pg.setVisibility(View.GONE);
                                                                    Toast.makeText(getContext(), "Feedback added successfully!", Toast.LENGTH_SHORT).show();
                                                                    Log.d(MotionEffect.TAG, "Feedback added successfully!");
                                                                })
                                                                .addOnFailureListener(e1 -> {
                                                                    pg.setVisibility(View.GONE);
                                                                    Toast.makeText(getContext(), "Something went wrong!!", Toast.LENGTH_SHORT).show();
                                                                    Log.d(MotionEffect.TAG, "Error in creating database : " + e1.getMessage());
                                                                });
                                                    });

                                        }
                                    }
                                    else
                                    {
                                        //  user database does not exist can be created using updating profile
                                        Toast.makeText(getContext(), "Update your profile first..", Toast.LENGTH_SHORT).show();
                                    }

                                }).addOnFailureListener(e -> {
                                    Toast.makeText(getContext(), "Something went wrong!! Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();


                                });

                    }
                });

        return view;
    }

}



package com.shubham.umerapp.User;

import static android.content.ContentValues.TAG;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.helper.widget.MotionEffect;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.shubham.umerapp.R;
import com.shubham.umerapp.User.Adapters.VPAdapter;
import com.shubham.umerapp.User.Fragments.fragment1;
import com.shubham.umerapp.User.Fragments.fragment2;
import com.shubham.umerapp.User.Fragments.fragment3;
import com.shubham.umerapp.databinding.UserHomeActivityBinding;
import com.shubham.umerapp.helperFunctions;

public class userHomeAcitvity extends AppCompatActivity {
    String documentId = new String();
    TabLayout tabLayout;

    FirebaseFirestore db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserHomeActivityBinding binding = UserHomeActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        db = FirebaseFirestore.getInstance();
//        auth = FirebaseAuth.getInstance();
//
//        helperFunctions funcs = new helperFunctions();
//        binding.hii.setVisibility(View.GONE);
//
//        String emailOfUser = auth.getCurrentUser().getEmail();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        TabLayout tabLayout = findViewById(R.id.tab1);
        ViewPager viewPager = findViewById(R.id.viewpager);

        tabLayout.setupWithViewPager(viewPager);

        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        vpAdapter.addFragment(new fragment1(), "Notifications");
        vpAdapter.addFragment(new fragment2(), "Today's Status");
        vpAdapter.addFragment(new fragment3(), "Monthly Overview");
        viewPager.setAdapter(vpAdapter);

//        db.collection("users")
//                .whereEqualTo("Email", emailOfUser)
//                .get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        for (QueryDocumentSnapshot document : task.getResult()) {
//                            documentId = document.getId().toString();
//                            db.collection("users").document(documentId).get().addOnCompleteListener(task1 ->
//                            {
//                                DocumentSnapshot documentSnapshot = task1.getResult();
////                                binding.hii.setVisibility(View.VISIBLE);
////                                binding.hii.setText("Hii, "+documentSnapshot.getString("Name"));
//                            });
//                        }
//                    }
//
//                }).addOnFailureListener(e->{
//                    Toast.makeText(this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
//                    Log.d(TAG, "error in fetching the document : "+e);
//                });


//        binding.sendFeedback.setOnClickListener(view -> {
//
//            String userEmail = auth.getCurrentUser().getEmail();
//
//            Map<String , Object> feedback = new HashMap<>();
//            feedback.put(funcs.getCurrentDate(), true);
//
//
//            binding.feedbackProgressbar.setVisibility(View.VISIBLE);
//
//            // getting document id of user from email
//            db.collection("users")
//                    .whereEqualTo("Email", userEmail)
//                    .get()
//                    .addOnCompleteListener(task -> {
//                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                String documentId = document.getId();
//
//                                // checking if the document id is present in collection
//                                db.collection("morningSession").document(documentId).update(feedback)
//                                        .addOnSuccessListener(aVoid -> {
//                                            Log.d(MotionEffect.TAG, "Feedback sent successfully!");
//                                            Toast.makeText(getApplicationContext(), "Feedback sent successfully!", Toast.LENGTH_SHORT).show();
//                                            binding.feedbackProgressbar.setVisibility(View.GONE);
//
//                                        })
//                                        .addOnFailureListener(e -> {
//                                            // If the document doesn't exist, create a new one
//                                            db.collection("morningSession").document(documentId).set(feedback)
//                                                    .addOnSuccessListener(aVoid1 -> {
//                                                        binding.feedbackProgressbar.setVisibility(View.GONE);
//                                                        Toast.makeText(getApplicationContext(), "Feedback added successfully!", Toast.LENGTH_SHORT).show();
//                                                        Log.d(MotionEffect.TAG, "Feedback added successfully!");
//                                                    })
//                                                    .addOnFailureListener(e1 -> {
//                                                        binding.feedbackProgressbar.setVisibility(View.GONE);
//                                                        Toast.makeText(getApplicationContext(), "Something went wrong!!", Toast.LENGTH_SHORT).show();
//                                                        Log.d(MotionEffect.TAG, "Error in creating database : " + e1.getMessage());
//                                                    });
//                                        });
//
//                            }
//                        }
//                        else
//                        {
//                            //  user database does not exist can be created using updating profile
//                            Toast.makeText(getApplicationContext(), "Update your profile first..", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }).addOnFailureListener(e -> {
//                        Toast.makeText(getApplicationContext(), "Something went wrong!! Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
//
//
//                    });
//
//        });


    }



}
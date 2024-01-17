package com.shubham.umerapp.User;

import static android.content.ContentValues.TAG;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.shubham.umerapp.Admin.AdminHomeScreen;
//import com.shubham.umerapp.Admin.updateAdminProfile;
import com.shubham.umerapp.R;
import com.shubham.umerapp.databinding.ActivityUpdateUserProfileBinding;

import java.util.HashMap;
import java.util.Map;

public class updateUserProfile extends AppCompatActivity {

    ActivityUpdateUserProfileBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateUserProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        binding.userEmail.setText(firebaseAuth.getCurrentUser().getEmail());



        // before updating admin's profile  show admins information

        String emailOfUser = firebaseAuth.getCurrentUser().getEmail();
        Log.d(TAG, "onCreate: email of user : "+emailOfUser);


        db.collection("users")
                .whereEqualTo("Email", emailOfUser)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String documentId = document.getId();
                            db.collection("users").document(documentId).get().addOnCompleteListener(task12 -> {


                                showDetailsandHideProgressBar(true);

                                DocumentSnapshot documentSnapshot = task12.getResult();
                                binding.userUsername.setText(documentSnapshot.getString("Name"));
                                binding.userPhoneNumber.setText(documentSnapshot.getString("Phone Number"));

                            }).addOnFailureListener(e -> {
                                Toast.makeText(this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                            });

                        }
                    } else {

                        showDetailsandHideProgressBar(true);
                    }

                }).addOnFailureListener(e -> Toast.makeText(updateUserProfile.this, "Something went wrong !! "+e.getMessage(), Toast.LENGTH_SHORT).show());


        // Inside onClick method
        binding.updateUserDetails.setOnClickListener(view -> {
            String name, phoneNumber;
            if (binding.userUsername.getText().toString().isEmpty() || binding.userPhoneNumber.getText().toString().isEmpty() || binding.userPhoneNumber.getText().toString().length() != 10 ) {
                Toast.makeText(getApplicationContext(), "Please add all details correctly", Toast.LENGTH_SHORT).show();
                return;
            } else {
                name = binding.userUsername.getText().toString();
                phoneNumber = binding.userPhoneNumber.getText().toString();

                // collect user details
                Map<String, Object> user = new HashMap<>();
                user.put("Name", name);
                user.put("Email", emailOfUser);
                user.put("Phone Number", phoneNumber);

                db.collection("users")
                        .whereEqualTo("Email", emailOfUser)
                        .get()
                        .addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                for (QueryDocumentSnapshot queryDocumentSnapshot : task1.getResult()) {
                                    String DocumentId = queryDocumentSnapshot.getId();
                                    // Update the existing user's name
                                    db.collection("users").document(DocumentId)
                                            .update(user)
                                            .addOnSuccessListener(aVoid -> {
                                                Log.d(TAG, "DocumentSnapshot successfully updated!");
                                                Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                                            })
                                            .addOnFailureListener(e -> {
                                                Log.w(TAG, "Error updating document", e);
                                                Toast.makeText(getApplicationContext(), "Error updating document" + e, Toast.LENGTH_SHORT).show();
                                            });
                                    return; // Assuming there's only one user per email number
                                }
                                // If no user is found, add a new user


                                db.collection("users")
                                        .add(user)
                                        .addOnSuccessListener(documentReference -> {
                                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                            Toast.makeText(getApplicationContext(), "Information added successfully", Toast.LENGTH_SHORT).show();
                                        })
                                        .addOnFailureListener(e -> {
                                            Log.w(TAG, "Error adding document", e);
                                            Toast.makeText(getApplicationContext(), "Error adding document" + e, Toast.LENGTH_SHORT).show();
                                        });
                            } else {
                                Log.w(TAG, "Error getting documents.", task1.getException());
                            }
                        });
            }


        });


        binding.backtodashboarduser.setOnClickListener(view -> {
            startActivity(new Intent(updateUserProfile.this, userHomeAcitvity.class));
            finish();
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Back is pressed... Finishing the activity
                startActivity(new Intent(updateUserProfile.this, userHomeAcitvity.class));
                finish();
            }
        });

    }
    private void showDetailsandHideProgressBar(boolean arg)
    {
        if (arg)
        {
            binding.progressBarofUserProfile.setVisibility(View.GONE);
            binding.relativeLayoutinUserProfileUpdate.setVisibility(View.VISIBLE);
        }
        else
        {
            binding.progressBarofUserProfile.setVisibility(View.VISIBLE);
            binding.relativeLayoutinUserProfileUpdate.setVisibility(View.GONE);
        }
    }

    }


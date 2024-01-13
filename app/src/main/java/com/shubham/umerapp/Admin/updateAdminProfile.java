package com.shubham.umerapp.Admin;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.shubham.umerapp.R;
import java.util.HashMap;
import java.util.Map;

public class updateAdminProfile extends AppCompatActivity {

    FirebaseFirestore db;
    EditText username, phonenumber;
    RelativeLayout relativeLayout;
    TextView email;
    ImageView back;
    ProgressBar progressBar;
    Button updateDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_admin_profile);
        db = FirebaseFirestore.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        relativeLayout = findViewById(R.id.relativeLayout);
        progressBar = findViewById(R.id.progress_bar_inadminProfile);
        username = findViewById(R.id.adminUsername);
        back = findViewById(R.id.backtodashboardadmin);
        phonenumber = findViewById(R.id.adminPhoneNumber);
        updateDetails = findViewById(R.id.updateAdminDetails);
        email = findViewById(R.id.adminEmail);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        email.setText(firebaseAuth.getCurrentUser().getEmail());



        // before updating admin's profile  show admins information

        String emailOfUser = firebaseAuth.getCurrentUser().getEmail();


        db.collection("Admin")
                .whereEqualTo("Email", emailOfUser)
                .get()
                .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String documentId = document.getId();
                                    db.collection("Admin").document(documentId).get().addOnCompleteListener(task12 -> {

                                        DocumentSnapshot documentSnapshot = task12.getResult();
                                        username.setText(documentSnapshot.getString("Name"));
                                        phonenumber.setText(documentSnapshot.getString("Phone Number"));
                                        showDetailsandHideProgressBar(true);

                                    }).addOnFailureListener(e -> {
                                        Toast.makeText(this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                                    });

                                }
                            } else {
                                showDetailsandHideProgressBar(true);
                            }

                        }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "something went wrong!!", Toast.LENGTH_SHORT).show());


                            // Inside onClick method
                            updateDetails.setOnClickListener(view -> {
                                String name, phoneNumber;
                                if (username.getText().toString().isEmpty() || phonenumber.getText().toString().isEmpty() || phonenumber.getText().toString().length() != 10 || phonenumber.getText().toString().isEmpty()) {
                                    Toast.makeText(getApplicationContext(), "Please add all details correctly", Toast.LENGTH_SHORT).show();
                                    return;
                                } else {
                                    name = username.getText().toString();
                                    phoneNumber = phonenumber.getText().toString();

                                    // collect user details
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("Name", name);
                                    user.put("Email", emailOfUser);
                                    user.put("Phone Number", phoneNumber);

                                    db.collection("Admin")
                                            .whereEqualTo("Email", emailOfUser)
                                            .get()
                                            .addOnCompleteListener(task1 -> {
                                                if (task1.isSuccessful()) {
                                                    for (QueryDocumentSnapshot queryDocumentSnapshot : task1.getResult()) {
                                                        String DocumentId = queryDocumentSnapshot.getId();
                                                        // Update the existing user's name
                                                        db.collection("Admin").document(DocumentId)
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


                                                    db.collection("Admin")
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


                            back.setOnClickListener(view -> {
                                startActivity(new Intent(updateAdminProfile.this, AdminHomeScreen.class));
                                finish();
                            });
                        }



                        private void showDetailsandHideProgressBar( boolean arg)
                        {
                            if (arg)
                            {
                                progressBar.setVisibility(View.GONE);
                                relativeLayout.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                progressBar.setVisibility(View.VISIBLE);
                                relativeLayout.setVisibility(View.GONE);
                            }
                        }
}


package com.shubham.umerapp.Admin;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.shubham.umerapp.R;
import com.shubham.umerapp.login.AddProfileInfo;

import java.util.HashMap;
import java.util.Map;

public class updateAdminProfile extends AppCompatActivity {

    FirebaseFirestore db;
    EditText username, phonenumber;
    TextView email;
    ImageView back;
    Button updateDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_admin_profile);
        db = FirebaseFirestore.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        username = findViewById(R.id.adminUsername);
        back = findViewById(R.id.backtodashboardadmin);
        phonenumber = findViewById(R.id.adminPhoneNumber);
        updateDetails = findViewById(R.id.updateAdminDetails);
        email = findViewById(R.id.adminEmail);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        email.setText(firebaseAuth.getCurrentUser().getEmail());

        // Inside onClick method
        updateDetails.setOnClickListener(view -> {
            String name, phoneNumber;
            if (username.getText().toString().isEmpty() || phonenumber.getText().toString().isEmpty() || phonenumber.getText().toString().length() != 10 || phonenumber.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please add all details correctly", Toast.LENGTH_SHORT).show();
                return;
            } else {
                name = username.getText().toString();
                phoneNumber = phonenumber.getText().toString();

                String emailOfUser = firebaseAuth.getCurrentUser().getEmail();

                // collect user details
                Map<String, Object> user = new HashMap<>();
                user.put("Name", name);
                user.put("Email", emailOfUser);
                user.put("Phone Number", phoneNumber);

                db.collection("Admin")
                        .whereEqualTo("Email", emailOfUser)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String documentId = document.getId();
                                    // Update the existing user's name
                                    db.collection("Admin").document(documentId)
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
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        });
            }


        });


        back.setOnClickListener(view -> {
            startActivity(new Intent(updateAdminProfile.this, AdminHomeScreen.class));
            finish();
        });
    }
    }
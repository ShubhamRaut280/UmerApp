    package com.shubham.umerapp.login;

    import static android.content.ContentValues.TAG;
    import androidx.appcompat.app.AppCompatActivity;

    import android.content.Intent;
    import android.os.Bundle;
    import android.util.Log;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.TextView;
    import android.widget.Toast;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.firestore.FirebaseFirestore;
    import com.google.firebase.firestore.QueryDocumentSnapshot;
    import com.shubham.umerapp.MainActivity;
    import com.shubham.umerapp.R;

    import java.util.HashMap;
    import java.util.Map;

    public class AddProfileInfo extends AppCompatActivity {

        FirebaseFirestore db;
        EditText username, phonenumber;
        TextView email;
        Button updateDetails;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_profile_info);
            db = FirebaseFirestore.getInstance();
            username = findViewById(R.id.username);
            phonenumber = findViewById(R.id.phonenumber);
            updateDetails = findViewById(R.id.update);
            email = findViewById(R.id.email);

            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            email.setText(firebaseAuth.getCurrentUser().getEmail());

        // Inside onClick method
            updateDetails.setOnClickListener(view -> {
                String name, phoneNumber;
                if (username.getText().toString().isEmpty() || phonenumber.getText().toString().isEmpty() || phonenumber.getText().toString().length()!=10 || phonenumber.getText().toString().isEmpty()) {
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

                    db.collection("users")
                            .whereEqualTo("Email", emailOfUser)
                            .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        String documentId = document.getId();
                                        // Update the existing user's name
                                        db.collection("users").document(documentId)
                                                .update(user)
                                                .addOnSuccessListener(aVoid -> {
                                                    Log.d(TAG, "DocumentSnapshot successfully updated!");
                                                    Toast.makeText(AddProfileInfo.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                                                })
                                                .addOnFailureListener(e -> {
                                                    Log.w(TAG, "Error updating document", e);
                                                    Toast.makeText(AddProfileInfo.this, "Error updating document" + e, Toast.LENGTH_SHORT).show();
                                                });
                                        return; // Assuming there's only one user per email number
                                    }
                                    // If no user is found, add a new user


                                    db.collection("users")
                                            .add(user)
                                            .addOnSuccessListener(documentReference -> {
                                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                                Toast.makeText(AddProfileInfo.this, "Information added successfully", Toast.LENGTH_SHORT).show();
                                            })
                                            .addOnFailureListener(e -> {
                                                Log.w(TAG, "Error adding document", e);
                                                Toast.makeText(AddProfileInfo.this, "Error adding document" + e, Toast.LENGTH_SHORT).show();
                                            });
                                } else {
                                    Log.w(TAG, "Error getting documents.", task.getException());
                                }
                            });
                }


            });


        }


    }
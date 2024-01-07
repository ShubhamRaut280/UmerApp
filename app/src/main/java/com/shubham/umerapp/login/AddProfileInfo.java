    package com.shubham.umerapp.login;

    import static android.content.ContentValues.TAG;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.appcompat.app.AppCompatActivity;

    import android.os.Bundle;
    import android.util.Log;
    import android.view.View;
    import android.widget.Button;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.OnFailureListener;
    import com.google.android.gms.tasks.OnSuccessListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.Firebase;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.firestore.DocumentReference;
    import com.google.firebase.firestore.EventListener;
    import com.google.firebase.firestore.FirebaseFirestore;
    import com.google.firebase.firestore.FirebaseFirestoreException;
    import com.google.firebase.firestore.QueryDocumentSnapshot;
    import com.google.firebase.firestore.QuerySnapshot;
    import com.shubham.umerapp.R;

    import java.util.HashMap;
    import java.util.Map;

    public class AddProfileInfo extends AppCompatActivity {

        FirebaseFirestore db;
        TextView username, phonenumber;
        Button updateDetails;
        boolean  isUserPresent = false;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_profile_info);
            db = FirebaseFirestore.getInstance();
            username = findViewById(R.id.username);
            phonenumber = findViewById(R.id.phonenumber);
            updateDetails = findViewById(R.id.update);

            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            phonenumber.setText(firebaseAuth.getCurrentUser().getPhoneNumber());

// Inside onClick method
            updateDetails.setOnClickListener(view -> {
                String name;
                if (username.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please add username", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    name = username.getText().toString();
                }

                String phoneNumber = firebaseAuth.getCurrentUser().getPhoneNumber();

                db.collection("users")
                        .whereEqualTo("Phone Number", phoneNumber)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String documentId = document.getId();
                                    // Update the existing user's name
                                    db.collection("users").document(documentId)
                                            .update("Name", name)
                                            .addOnSuccessListener(aVoid -> {
                                                Log.d(TAG, "DocumentSnapshot successfully updated!");
                                                Toast.makeText(AddProfileInfo.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                                            })
                                            .addOnFailureListener(e -> {
                                                Log.w(TAG, "Error updating document", e);
                                                Toast.makeText(AddProfileInfo.this, "Error updating document" + e, Toast.LENGTH_SHORT).show();
                                            });
                                    return; // Assuming there's only one user per phone number
                                }
                                // If no user is found, add a new user
                                Map<String, Object> user = new HashMap<>();
                                user.put("Name", name);
                                user.put("Phone Number", phoneNumber);

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
            });


        }


    }
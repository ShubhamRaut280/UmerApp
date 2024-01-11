package com.shubham.umerapp;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.shubham.umerapp.login.AddProfileInfo;

public class MainActivity extends AppCompatActivity {

    String documentId = new String();
    userDetails userdetails;
    TextView hii;
    FirebaseFirestore db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar titlebar = getSupportActionBar();
        titlebar.show();
        setContentView(R.layout.activity_main);
        hii = findViewById(R.id.hii);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

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
                                titlebar.setTitle("Hii, "+documentSnapshot.getString("Name"));
                            });
                        }
                    }
                }).addOnFailureListener(e->{
                    Toast.makeText(this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "error in fetching the document : "+e);
                });



    }



}
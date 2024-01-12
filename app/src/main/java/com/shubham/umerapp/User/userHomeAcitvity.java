package com.shubham.umerapp.User;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.shubham.umerapp.Admin.AdminHomeScreen;
import com.shubham.umerapp.Admin.updateAdminProfile;
import com.shubham.umerapp.R;
import com.shubham.umerapp.User.userDetails;
import com.shubham.umerapp.databinding.UserHomeActivityBinding;

public class userHomeAcitvity extends AppCompatActivity {

    String documentId = new String();
    userDetails userdetails;

    UserHomeActivityBinding binding;
    FirebaseFirestore db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserHomeActivityBinding binding = UserHomeActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        String emailOfUser = auth.getCurrentUser().getEmail();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


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
                                binding.hii.setText("Hii, "+documentSnapshot.getString("Name"));
                            });
                        }
                    }

                }).addOnFailureListener(e->{
                    Toast.makeText(this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "error in fetching the document : "+e);
                });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_homepage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.gotoUpdateUserprofile)
        {
            startActivity(new Intent(userHomeAcitvity.this, updateUserProfile.class));
            return true;
        }


        return super.onOptionsItemSelected(item);
    }





}
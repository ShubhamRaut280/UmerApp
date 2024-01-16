package com.shubham.umerapp.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shubham.umerapp.Admin.allUsersRecyclerView.AdapterForAllUsers;
import com.shubham.umerapp.R;
import com.shubham.umerapp.Models.userDetails;

import java.util.ArrayList;

public class AllusersActivity extends AppCompatActivity {

    ArrayList<userDetails> allUserList = new ArrayList<>();
    RecyclerView recyclerView;
    FirebaseFirestore db;
    FirebaseAuth auth;
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allusers);
        recyclerView = findViewById(R.id.recyclerforallusers);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progress_bar_allusers);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        db.collection("users").get().addOnCompleteListener(task -> {
            showDetailsandHideProgressBar(true);
            for (DocumentSnapshot document: task.getResult().getDocuments() ) {
                userDetails user = new userDetails(document.getString("Name"), document.getString("Phone Number"), document.getString("Email"));
                allUserList.add(user);

            }
            recyclerView.setAdapter(new AdapterForAllUsers(allUserList));
        }).addOnFailureListener(e -> {
            showDetailsandHideProgressBar(true);
            Toast.makeText(this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
            Log.d("error in fetching the document : ", "error is "+ e.getMessage());
        });


    }


    private void showDetailsandHideProgressBar( boolean arg)
    {
        if (arg)
        {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        else
        {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }


}
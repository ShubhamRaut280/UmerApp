package com.shubham.umerapp.User.Adapters;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.common.util.concurrent.Atomics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.shubham.umerapp.R;
import com.shubham.umerapp.User.Fragments.fragment3;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>
{
    public final ArrayList<String> daysOfMonth;
    public final OnItemListener onItemListener;
    FirebaseFirestore db;
    FirebaseAuth auth;
    
    public ImageView allDone, oneDone, notDone;

    public CalendarAdapter(ArrayList<String> daysOfMonth, OnItemListener onItemListener)
    {
        this.daysOfMonth = daysOfMonth;
        this.onItemListener = onItemListener;
        
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.133333);

        allDone = view.findViewById(R.id.aDone);
        oneDone = view.findViewById(R.id.oDone);
        notDone = view.findViewById(R.id.notDone);

        allDone.setVisibility(View.GONE);
        oneDone.setVisibility(View.GONE);
        notDone.setVisibility(View.GONE);
        
        return new CalendarViewHolder(view, onItemListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position)
    {
        fragment3 frag = new fragment3();
        String monthYear = frag.mYear();
        String day = daysOfMonth.get(position);
        String currDate;


        String year = monthYear.substring(monthYear.indexOf("2"),monthYear.indexOf("2")+4);
        monthYear = monthYear.substring(0, monthYear.indexOf("2")-1);

        switch(monthYear){
            case "January": currDate = year+"-01-" + day;
                break;
            case "February": currDate = year+"-02-" + day;
                break;
            case "March": currDate = year+"-03-" + day;
                break;
            case "April": currDate = year+"-04-" + day;
                break;
            case "May": currDate = year+"-05-" + day;
                break;
            case "June": currDate = year+"-06-" + day;
                break;
            case "July": currDate = year+"-07-" + day;
                break;
            case "August": currDate = year+"-08-" + day;
                break;
            case "September": currDate = year+"-09-" + day;
                break;
            case "October": currDate = year+"-10-" + day;
                break;
            case "November": currDate = year+"-11-" + day;
                break;
            case "December": currDate = year+"-12-" + day;
                break;

        }

        
        holder.dayOfMonth.setText(daysOfMonth.get(position));
        

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        String emailOfUser = auth.getCurrentUser().getEmail();

        // Morning Session Text Value, and live update

        db.collection("users")
                .whereEqualTo("Email", emailOfUser)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String userId = document.getId();
                            // Continue to the next step with the user's ID (userId)
                            AtomicBoolean morning = checkMorningSessionDocument(userId, "2024-01-18");
                            AtomicBoolean evening = checkEveningSessionDocument(userId, "2024-01-18");

                            if(morning.get() && evening.get()){
                                AtomicBoolean m = new AtomicBoolean(false);
                                AtomicBoolean e = new AtomicBoolean(false);

                                db.collection("morningSession")
                                        .document(userId)
                                        .get()
                                        .addOnCompleteListener(task1 -> {
                                            if (task1.isSuccessful()) {
                                                DocumentSnapshot document1 = task1.getResult();
                                                if (document1.exists()) {
                                                    // Document exists, retrieve the current date and boolean value
                                                    if(document1.getData().containsKey("2024-01-18")){

                                                        boolean booleanValue = document1.getBoolean("2024-01-18");

                                                        if(booleanValue){
                                                            m.set(true);
                                                        }else{
                                                            m.set(false);
                                                        }

                                                    }else{
                                                        // Document doesn't exist
                                                        m.set(false);
                                                        Log.e(TAG,"7" );

                                                    }

                                                } else {
                                                    // Document doesn't exist
                                                    // Handle the case where the document doesn't exist
                                                    Log.e(TAG, "8");
                                                }
                                            } else {
                                                // Handle errors
                                                Log.e(TAG, "9");
                                            }
                                        });

                                db.collection("eveningSession")
                                        .document(userId)
                                        .get()
                                        .addOnCompleteListener(task2 -> {
                                            if (task2.isSuccessful()) {
                                                DocumentSnapshot document2 = task2.getResult();
                                                if (document2.exists()) {
                                                    // Document exists, retrieve the current date and boolean value
                                                    if(document2.getData().containsKey("2024-01-18")){

                                                        boolean booleanValue = document2.getBoolean("2024-01-18");

                                                        if(booleanValue){
                                                            e.set(true);
                                                        }else{
                                                            e.set(false);
                                                        }

                                                    }else{
                                                        // Document doesn't exist
                                                        e.set(false);
                                                        Log.e(TAG,"10" );

                                                    }

                                                } else {
                                                    // Document doesn't exist
                                                    // Handle the case where the document doesn't exist
                                                    Log.e(TAG, "11");
                                                }
                                            } else {
                                                // Handle errors
                                                Log.e(TAG, "12");
                                            }
                                        });

                                if(m.get() && e.get()){
                                    allDone.setVisibility(View.VISIBLE);
                                } else if (m.get() || e.get()) {
                                    oneDone.setVisibility(View.VISIBLE);
                                }else{
                                    notDone.setVisibility(View.VISIBLE);
                                }
                            } else if (morning.get() || evening.get()) {

                                if(morning.get()){
                                    AtomicBoolean m = new AtomicBoolean(false);

                                    db.collection("morningSession")
                                            .document(userId)
                                            .get()
                                            .addOnCompleteListener(task1 -> {
                                                if (task1.isSuccessful()) {
                                                    DocumentSnapshot document1 = task1.getResult();
                                                    if (document1.exists()) {
                                                        // Document exists, retrieve the current date and boolean value
                                                        if(document1.getData().containsKey(day)){

                                                            boolean booleanValue = document1.getBoolean("2024-01-18");

                                                            if(booleanValue){
                                                                m.set(true);
                                                            }else{
                                                                m.set(false);
                                                            }

                                                        }else{
                                                            // Document doesn't exist
                                                            m.set(false);
                                                            Log.e(TAG,"13" );

                                                        }

                                                    } else {
                                                        // Document doesn't exist
                                                        // Handle the case where the document doesn't exist
                                                        Log.e(TAG, "14");
                                                    }
                                                } else {
                                                    // Handle errors
                                                    Log.e(TAG, "15");
                                                }
                                            });

                                    if(m.get()){
                                        oneDone.setVisibility(View.VISIBLE);
                                    }

                                }

                                if(evening.get()){
                                    AtomicBoolean e = new AtomicBoolean(false);
                                    db.collection("eveningSession")
                                            .document(userId)
                                            .get()
                                            .addOnCompleteListener(task2 -> {
                                                if (task2.isSuccessful()) {
                                                    DocumentSnapshot document2 = task2.getResult();
                                                    if (document2.exists()) {
                                                        // Document exists, retrieve the current date and boolean value
                                                        if(document2.getData().containsKey(day)){

                                                            boolean booleanValue = document2.getBoolean("2024-01-18");

                                                            if(booleanValue){
                                                                e.set(true);
                                                            }else{
                                                                e.set(false);
                                                            }

                                                        }else{
                                                            // Document doesn't exist
                                                            e.set(false);
                                                            Log.e(TAG,"16" );

                                                        }

                                                    } else {
                                                        // Document doesn't exist
                                                        // Handle the case where the document doesn't exist
                                                        Log.e(TAG, "17");
                                                    }
                                                } else {
                                                    // Handle errors
                                                    Log.e(TAG, "18");
                                                }
                                            });
                                    if(e.get()){
                                        oneDone.setVisibility(View.VISIBLE);
                                    }
                                }

                            }

//                            final DocumentReference docRef = db.collection("morningSession").document(userId);
//                            docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                                @Override
//                                public void onEvent(@Nullable DocumentSnapshot snapshot,
//                                                    @Nullable FirebaseFirestoreException e) {
//                                    if (e != null) {
//                                        Log.e(TAG, "Listen failed Morning", e);
//                                        return;
//                                    }
//                                    if (snapshot != null && snapshot.exists()) {
//
//                                        Log.e("Current data: " , snapshot.getData().toString());
//                                        checkMorningSessionDocument(userId, currDate);
//                                    } else {
//
//                                    }
//                                }
//                            });

                        }
                    } else {
                        // Handle errors
                        Log.d(TAG, "Morning not success CAL");
//                        Toast.makeText(getApplicationContext(), "Morning not success ", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e->{
                    Log.d(TAG, "Morning wrong CAL: " + e);
//                    Toast.makeText(get, "Morning wrong: "+e, Toast.LENGTH_SHORT).show();
                });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public AtomicBoolean checkMorningSessionDocument(String userId, String currDate) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        AtomicBoolean flag = new AtomicBoolean(false);
        
        db.collection("morningSession")
                .document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // Document exists, retrieve the current date and boolean value
                            if(document.getData().containsKey(currDate)){

                                flag.set(true);

                            }else{
                                // Document doesn't exist
                                flag.set(false);
                                Log.e("OOOOOO","1"+currDate);

                            }

                        } else {
                            // Document doesn't exist
                            // Handle the case where the document doesn't exist
                            Log.e(TAG, "2");
                        }
                    } else {
                        // Handle errors
                        Log.e(TAG, "3");
                    }
                });

        return flag;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public AtomicBoolean checkEveningSessionDocument(String userId, String currDate) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        AtomicBoolean flag = new AtomicBoolean(false);

        db.collection("eveningSession")
                .document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // Document exists, retrieve the current date and boolean value
                            if(document.getData().containsKey(currDate)){

                                flag.set(true);

                            }else{
                                // Document doesn't exist
                                flag.set(false);
                                Log.e(TAG,"4" );

                            }

                        } else {
                            // Document doesn't exist
                            // Handle the case where the document doesn't exist
                            Log.e(TAG, "5");
                        }
                    } else {
                        // Handle errors
                        Log.e(TAG, "6");
                    }
                });

        return flag;

    }

    @Override
    public int getItemCount()
    {
        return daysOfMonth.size();
    }

    public interface  OnItemListener
    {
        void onItemClick(int position, String dayText);
    }
}

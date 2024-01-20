package com.shubham.umerapp.User.Adapters;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.shubham.umerapp.R;
import com.shubham.umerapp.Utils.helperFunctions;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>
{
    public final ArrayList<String> daysOfMonth;
    public final OnItemListener onItemListener;
    public String monthYear;
    public String year;
    public String month;
    FirebaseFirestore db;
    FirebaseAuth auth;
    boolean mStatus, eStatus;
    String emailOfUser;


    public CalendarAdapter(ArrayList<String> daysOfMonth, String monthnYear, OnItemListener onItemListener)
    {
        this.daysOfMonth = daysOfMonth;
        this.onItemListener = onItemListener;
        this.monthYear = monthnYear;

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        emailOfUser = auth.getCurrentUser().getEmail();

        year = monthYear.substring(monthYear.indexOf("2"), monthYear.indexOf("2") + 4);
        month = monthYear.substring(0, monthYear.indexOf("2") - 1);

        switch (month) {
            case "January":
                monthYear = year + "-01-";
                break;
            case "February":
                monthYear = year + "-02-";
                break;
            case "March":
                monthYear = year + "-03-";
                break;
            case "April":
                monthYear = year + "-04-" ;
                break;
            case "May":
                monthYear = year + "-05-";
                break;
            case "June":
                monthYear = year + "-06-";
                break;
            case "July":
                monthYear = year + "-07-";
                break;
            case "August":
                monthYear = year + "-08-";
                break;
            case "September":
                monthYear = year + "-09-";
                break;
            case "October":
                monthYear = year + "-10-";
                break;
            case "November":
                monthYear = year + "-11-";
                break;
            case "December":
                monthYear = year + "-12-";
                break;

        }
    }
    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.133333);



        return new CalendarViewHolder(view, onItemListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {

        holder.dayOfMonth.setText(daysOfMonth.get(position));

        db.collection("users")
                .whereEqualTo("Email", emailOfUser)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String userId = document.getId();
                            String day = daysOfMonth.get(position);

                                    switch(day){
                                    case "1": day = "01";
                                        break;
                                    case "2": day = "02";
                                        break;
                                    case "3": day = "03";
                                        break;
                                    case "4": day = "04";
                                        break;
                                    case "5": day = "05";
                                        break;
                                    case "6": day = "06";
                                        break;
                                    case "7": day = "07";
                                        break;
                                    case "8": day = "08";
                                        break;
                                    case "9": day = "09";
                                        break;
                                }
                            String currDate = monthYear+day;

                            db.collection("morningSession")
                                    .document(userId)
                                    .get()
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            DocumentSnapshot document1 = task1.getResult();
                                            if (document1.exists()) {
                                                // Document exists, retrieve the current date and boolean value
                                                if(document1.getData().containsKey(currDate)){

                                                    boolean booleanValue = document1.getBoolean(currDate);

                                                    if(booleanValue){

                                                        db.collection("eveningSession")
                                                                .document(userId)
                                                                .get()
                                                                .addOnCompleteListener(task2 -> {
                                                                    if (task2.isSuccessful()) {
                                                                        DocumentSnapshot document2 = task2.getResult();
                                                                        if (document2.exists()) {
                                                                            // Document exists, retrieve the current date and boolean value
                                                                            if(document2.getData().containsKey(currDate)){

                                                                                boolean booleanValue1 = document2.getBoolean(currDate);

                                                                                if(booleanValue1){
                                                                                    holder.allDone.setVisibility(View.VISIBLE);
                                                                                }else{
                                                                                    setBoolE(false);
                                                                                    holder.oneDone.setVisibility(View.VISIBLE);
                                                                                }

                                                                            }else{
                                                                                // Document doesn't exist
                                                                                holder.oneDone.setVisibility(View.VISIBLE);
                                                                            }

                                                                        } else {
                                                                            // Document doesn't exist
                                                                            // Handle the case where the document doesn't exist
                                                                            Log.e(TAG, "456");
                                                                        }
                                                                    } else {
                                                                        // Handle errors
                                                                        Log.e(TAG, "789");
                                                                    }
                                                                }).addOnFailureListener(e->{
                                                                    Log.e(TAG, "ZXCVBNM" + e);
                                                                });

                                                    }else{

                                                        db.collection("eveningSession")
                                                                .document(userId)
                                                                .get()
                                                                .addOnCompleteListener(task3 -> {
                                                                    if (task3.isSuccessful()) {
                                                                        DocumentSnapshot document3 = task3.getResult();
                                                                        if (document3.exists()) {
                                                                            // Document exists, retrieve the current date and boolean value
                                                                            if(document3.getData().containsKey(currDate)){

                                                                                boolean booleanValue2 = document3.getBoolean(currDate);

                                                                                if(booleanValue2){
                                                                                    setBoolE(true);

                                                                                    holder.oneDone.setVisibility(View.VISIBLE);
                                                                                }else{
                                                                                    setBoolE(false);
                                                                                    holder.notDone.setVisibility(View.VISIBLE);
                                                                                }

                                                                            }else{
                                                                                // Document doesn't exist
                                                                                holder.oneDone.setVisibility(View.VISIBLE);
                                                                            }

                                                                        } else {
                                                                            // Document doesn't exist
                                                                            // Handle the case where the document doesn't exist
                                                                            Log.e(TAG, "456");
                                                                        }
                                                                    } else {
                                                                        // Handle errors
                                                                        Log.e(TAG, "789");
                                                                    }
                                                                }).addOnFailureListener(e->{
                                                                    Log.e(TAG, "ZXCVBNM" + e);
                                                                });
                                                        setBoolM((false));
                                                    }

                                                }else{
                                                    // Document doesn't exist
                                                }
                                            } else {
                                                // Document doesn't exist
                                                // Handle the case where the document doesn't exist

                                                db.collection("eveningSession")
                                                        .document(userId)
                                                        .get()
                                                        .addOnCompleteListener(task2 -> {
                                                            if (task2.isSuccessful()) {
                                                                DocumentSnapshot document2 = task2.getResult();
                                                                if (document2.exists()) {
                                                                    // Document exists, retrieve the current date and boolean value
                                                                    if(document2.getData().containsKey(currDate)){

                                                                        boolean booleanValue1 = document2.getBoolean(currDate);

                                                                        if(booleanValue1){
                                                                            holder.oneDone.setVisibility(View.VISIBLE);
                                                                        }else{
                                                                            setBoolE(false);
                                                                            holder.oneDone.setVisibility(View.VISIBLE);
                                                                        }

                                                                    }else{
                                                                        // Document doesn't exist
                                                                        holder.notDone.setVisibility(View.GONE);
                                                                        holder.oneDone.setVisibility(View.GONE);
                                                                        holder.allDone.setVisibility(View.GONE);
                                                                    }

                                                                } else {
                                                                    // Document doesn't exist
                                                                    // Handle the case where the document doesn't exist
                                                                    Log.e(TAG, "456");

                                                                }
                                                            } else {
                                                                // Handle errors
                                                                Log.e(TAG, "789");
                                                            }
                                                        }).addOnFailureListener(e->{
                                                            Log.e(TAG, "ZXCVBNM" + e);
                                                        });


                                                Log.e(TAG, "456");
                                            }
                                        } else {
                                            // Handle errors
                                            Log.e(TAG, "789");
                                        }
                                    }).addOnFailureListener(e->{
                                        Log.e(TAG, "ZXCVBNM" + e);
                                    });
                        }
                    } else {
                        // Handle errors
                        Log.d(TAG, "1");
                        //Toast.makeText(getContext(), "Morning not success ", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e->{
                    Log.d(TAG, "2 " + e);
                    //Toast.makeText(getContext(), "Morning wrong: "+e, Toast.LENGTH_SHORT).show();
                });

    }

    public void setBoolM(Boolean val){
        mStatus = val;
    }

    public void setBoolE(Boolean val){
        eStatus = val;
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



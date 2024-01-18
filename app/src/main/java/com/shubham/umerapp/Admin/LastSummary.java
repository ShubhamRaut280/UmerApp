package com.shubham.umerapp.Admin;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shubham.umerapp.Admin.summaryRecyclerview.AdapterForSummary;
import com.shubham.umerapp.Utils.helperFunctions;
import com.shubham.umerapp.databinding.LastSummaryBinding;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class LastSummary extends AppCompatActivity {

    String selectedDate = new String();
    FirebaseAuth auth;

    FirebaseFirestore db;
    LastSummaryBinding binding;
    AdapterForSummary adapterForSummary;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LastSummaryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        helperFunctions func = new helperFunctions();


        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        binding.selectDate.setOnClickListener(view -> { showDatePickerDialog(); });




        helperFunctions.getSummary(db, getApplicationContext(), userSummaryList -> {
            // Handle the userSummaryList here
            Log.d(TAG, "Size of userSummaryList: " + userSummaryList.size());

            binding.getDetails.setOnClickListener(view ->{
                if(binding.selectDate.getText().toString().equals("Select Date"))
                    Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show();
            else
            {
                binding.summaryRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                binding.summaryRecycler.setAdapter(new AdapterForSummary(userSummaryList,binding.selectDate.getText().toString() ));
                binding.m.setVisibility(View.VISIBLE);
                binding.e.setVisibility(View.VISIBLE);
                binding.divider.setVisibility(View.VISIBLE);
            }

            });
        });


    }



    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {
            calendar.set(Calendar.YEAR, selectedYear);
            calendar.set(Calendar.MONTH, selectedMonth);
            calendar.set(Calendar.DAY_OF_MONTH, selectedDayOfMonth);

             selectedDate = dateFormat.format(calendar.getTime());
            binding.selectDate.setText(selectedDate);
        }, year, month, day);

        datePickerDialog.show();
    }


}




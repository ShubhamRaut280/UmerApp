package com.shubham.umerapp.Admin;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shubham.umerapp.Admin.summaryRecyclerview.AdapterForSummary;
import com.shubham.umerapp.Models.userSummary;
import com.shubham.umerapp.Utils.helperFunctions;
import com.shubham.umerapp.databinding.ActivityLastSummaryForMorningBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class LastSummaryForMorning extends AppCompatActivity {
    ActivityLastSummaryForMorningBinding binding;
    String selectedDate = new String();
    FirebaseAuth auth;

    FirebaseFirestore db;
    AdapterForSummary adapterForSummary;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLastSummaryForMorningBinding.inflate(getLayoutInflater());
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
                binding.morningRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                binding.morningRecycler.setAdapter(new AdapterForSummary(userSummaryList,binding.selectDate.getText().toString() ));
            }

            });
        });


    }



    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                calendar.set(Calendar.YEAR, selectedYear);
                calendar.set(Calendar.MONTH, selectedMonth);
                calendar.set(Calendar.DAY_OF_MONTH, selectedDayOfMonth);

                 selectedDate = dateFormat.format(calendar.getTime());
                binding.selectDate.setText(selectedDate);
            }
        }, year, month, day);

        datePickerDialog.show();
    }

    public interface SummaryCallback {
        void onSummaryReceived(ArrayList<userSummary> userSummaryList);
    }

}




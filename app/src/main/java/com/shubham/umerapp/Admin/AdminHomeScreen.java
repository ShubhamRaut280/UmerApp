package com.shubham.umerapp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.shubham.umerapp.login.loginActivity;
import com.shubham.umerapp.R;
import com.shubham.umerapp.databinding.AdminHomeActivityBinding;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AdminHomeScreen extends AppCompatActivity {

ActionBar actionBar;
CardView viewTotaluser;
AdminHomeActivityBinding binding;

TextView currentDateTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AdminHomeActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        binding.currentDateAndTimeinAdminPage.setText(getCurrentDate());


        binding.totalusers.setOnClickListener(view ->
        {
            startActivity(new Intent(AdminHomeScreen.this , AllusersActivity.class));
        });


        binding.viewLastSummaryMorining.setOnClickListener(view -> {
            startActivity(new Intent(AdminHomeScreen.this, LastSummaryForMorning.class));
        });


    }

    public String getCurrentDate()
    {
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, d - MMM", Locale.getDefault());
        String formattedDateTime = sdf.format(currentDate);
        return  formattedDateTime;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_homepage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.updateAdminsProfile)
        {
            startActivity(new Intent(AdminHomeScreen.this, updateAdminProfile.class));
            return true;
        } else if(id==R.id.logoutasAdmin)
        {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(AdminHomeScreen.this, loginActivity.class));
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
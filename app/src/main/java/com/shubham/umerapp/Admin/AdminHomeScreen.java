package com.shubham.umerapp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.shubham.umerapp.R;
import com.shubham.umerapp.databinding.ActivityMainBinding;
import com.shubham.umerapp.login.AddProfileInfo;

public class AdminHomeScreen extends AppCompatActivity {

ActivityMainBinding binding;
ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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
        }


        return super.onOptionsItemSelected(item);
    }
}
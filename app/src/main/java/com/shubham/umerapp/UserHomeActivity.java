package com.shubham.umerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;


public class UserHomeActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_home);

        Bundle extras = getIntent().getExtras();
        String Year = extras.getString("Year");
        String Month = extras.getString("Month");
        String Day = extras.getString("Day");
        String Hour = extras.getString("Hour");
        String Min = extras.getString("Min");

        fragment1 frag1 = new fragment1();
        fragment2 frag2 = new fragment2();
        fragment3 frag3 = new fragment3();

        Bundle bundle = new Bundle();
        bundle.putString("Year", Year);
        bundle.putString("Month", Month);
        bundle.putString("Day", Day);
        bundle.putString("Hour", Hour);
        bundle.putString("Min", Min);

        frag2.setArguments(bundle);

        tabLayout = findViewById(R.id.tab1);
        viewPager = findViewById(R.id.viewpager);

        tabLayout.setupWithViewPager(viewPager);

        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);


        vpAdapter.addFragment(frag1, "Notifications");
        vpAdapter.addFragment(frag2, "Today's Status");
        vpAdapter.addFragment(frag3, "Monthly Overview");
        viewPager.setAdapter(vpAdapter);
    }
}
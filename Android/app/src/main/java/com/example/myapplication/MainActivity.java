package com.example.myapplication;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.myapplication.fragments.MapFragment;
import com.example.myapplication.fragments.ProjectsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
//    private HomeFragment homeFragment;
    private MapFragment mapFragment;
    private ProjectsFragment projectsFragment;
    private BottomNavigationView navView;
    private Fragment activeFragment;
    final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        LoadFragment();
        navView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.navigation_home:
//                fragmentManager.beginTransaction().hide(activeFragment).show(homeFragment).commit();
//                activeFragment = homeFragment;
//                return true;

            case R.id.navigation_dashboard:
                fragmentManager.beginTransaction().hide(activeFragment).show(mapFragment).commit();
                activeFragment = mapFragment;
                return true;

            case R.id.navigation_notifications:
                fragmentManager.beginTransaction().hide(activeFragment).show(projectsFragment).commit();
                activeFragment = projectsFragment;
                return true;
        }
        return false;
    }

    private void LoadFragment() {
        fragmentManager.beginTransaction().add(R.id.nav_host_fragment, projectsFragment, "3").hide(projectsFragment).commit();
        fragmentManager.beginTransaction().add(R.id.nav_host_fragment, mapFragment, "2").commit();
//        fragmentManager.beginTransaction().add(R.id.nav_host_fragment, homeFragment, "1").commit();
    }

    public void initializeViews() {
        navView = findViewById(R.id.nav_view);

//        homeFragment = new HomeFragment();
        mapFragment = new MapFragment();
        projectsFragment = new ProjectsFragment();

        activeFragment = mapFragment;
    }
}
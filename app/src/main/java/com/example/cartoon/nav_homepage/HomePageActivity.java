package com.example.cartoon.nav_homepage;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.example.cartoon.BaseActivity;
import com.example.cartoon.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class HomePageActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        BottomNavigationView navView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this, R.id.nav_homepage);
        NavigationUI.setupWithNavController(navView, navController);
    }
}

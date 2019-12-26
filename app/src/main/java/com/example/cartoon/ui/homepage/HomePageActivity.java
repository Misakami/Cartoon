package com.example.cartoon.ui.homepage;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.cartoon.BaseActivity;
import com.example.cartoon.R;
import com.example.cartoon.ui.homepage.bookshelf.BookShelf;
import com.example.cartoon.ui.homepage.search.SearchCartoon;
import com.example.cartoon.ui.homepage.setup.SetUp;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;


public class HomePageActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        initView();
    }

    private void initView() {
        BottomNavigationView navView = findViewById(R.id.bottomNavigationView);
        final ViewPager viewPager = findViewById(R.id.vp_homepage);
        FragmentManager manager = getSupportFragmentManager();

        HomePageAdapter adapter = new HomePageAdapter(manager, Arrays.asList(new BookShelf(),new SearchCartoon(),new SetUp()));
        viewPager.setAdapter(adapter);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_bookShelf:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.navigation_search:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.navigation_setup:
                        viewPager.setCurrentItem(2);
                    default:
                }
                return true;
            }
        });
    }
}

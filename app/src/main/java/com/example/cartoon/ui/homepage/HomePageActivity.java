package com.example.cartoon.ui.homepage;

import android.Manifest;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.cartoon.BaseActivity;
import com.example.cartoon.R;
import com.example.cartoon.ui.homepage.bookshelf.BookShelf;
import com.example.cartoon.ui.homepage.search.SearchCartoon;
import com.example.cartoon.ui.homepage.setup.SetUp;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class HomePageActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        initView();
        requestPermission();
    }

    @AfterPermissionGranted(1)
    private void requestPermission() {
        if (!EasyPermissions.hasPermissions(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        )) {
            EasyPermissions.requestPermissions(
                    this, "需要磁盘读写权限，系统设置写入权限（亮度调节）", 1, Manifest.permission.WRITE_EXTERNAL_STORAGE
            );
        }
    }

    private void initView() {
        BottomNavigationView navView = findViewById(R.id.bottomNavigationView);
        final ViewPager viewPager = findViewById(R.id.vp_homepage);
        FragmentManager manager = getSupportFragmentManager();

        HomePageAdapter adapter = new HomePageAdapter(manager, Arrays.asList(new BookShelf(), new SearchCartoon(), new SetUp()));
        viewPager.setAdapter(adapter);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        finish();
    }
}

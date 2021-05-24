package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import com.example.doan.adapter.ViewPagerAdapter;
import com.example.doan.common.sqlite;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    String token;
    sqlite db = new sqlite(MainActivity.this, "bone_fish.sqlite", null, 1);
    Cursor cursor;

    BottomNavigationView navigationView;
    ViewPagerAdapter pagerAdapter;
    ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.bottomNavigationView);
        viewPager = findViewById(R.id.viewPager);
        navigationView.setItemIconTintList(null);

        pagerAdapter = new ViewPagerAdapter(this);

        cursor = db.GetData("SELECT * FROM token");
        while (cursor.moveToNext()) {
            token = cursor.getString(1);
            Log.d("runrun","DATA:"+token);
        }
        pagerAdapter.setArrayData(token);

        viewPager.setAdapter(pagerAdapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        navigationView.getMenu().findItem(R.id.tabProduct).setChecked(true);
                        break;
                    case 1:
                        navigationView.getMenu().findItem(R.id.tabCategory).setChecked(true);
                        break;
                    case 2:
                        navigationView.getMenu().findItem(R.id.tabBill).setChecked(true);
                        break;
                    case 3:
                        navigationView.getMenu().findItem(R.id.tabUser).setChecked(true);
                        break;
                }
            }
        });

        navigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.tabProduct:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.tabCategory:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.tabBill:
                    viewPager.setCurrentItem(2);
                    break;
                case R.id.tabUser:
                    viewPager.setCurrentItem(3);
                    break;
            }
            return true;
        });

    }
}
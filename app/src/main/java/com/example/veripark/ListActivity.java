package com.example.veripark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;

import android.view.MenuItem;

import com.example.veripark.UI.Decreasing;
import com.example.veripark.UI.Rising;
import com.example.veripark.UI.StockIndices;
import com.example.veripark.UI.Volume100;
import com.example.veripark.UI.Volume30;
import com.example.veripark.UI.Volume50;
import com.google.android.material.navigation.NavigationView;

public class ListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.stock_indices);

        FragmentManager fragmentManager = getSupportFragmentManager();
        StockIndices fragment = new StockIndices();
        fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        Log.d("TAG",item.getItemId()+"---"+R.id.rising);
        switch (item.getItemId()){
            case R.id.stock_indices:fragment=new StockIndices();
                break;
            case R.id.rising:fragment=new Rising();
                break;
            case R.id.decreasing:fragment=new Decreasing();
                break;
            case R.id.valume30:fragment=new Volume30();
                break;
            case R.id.valume50:fragment=new Volume50();
                break;
            case R.id.valume100:fragment=new Volume100();
                break;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        assert fragment != null;
        fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
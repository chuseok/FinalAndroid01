package com.example.logintest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.logintest.adapter.CardViewAdapter;
import com.example.logintest.domain.Model;
import com.example.logintest.manager.SharedPrefManager;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView leftNavigationView;
    private NavigationView rightNavigationView;
    ViewPager viewPager;
    CardViewAdapter adapter;
    List<Model> models;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.Open, R.string.Close);
        drawer.addDrawerListener(toggle);//hamburger icon create
        toggle.syncState();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        leftNavigationView = (NavigationView)findViewById(R.id.nav_view_left);
        leftNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {

                    case R.id.wordSort:
                        Toast.makeText(MainActivity.this, "wordSort",Toast.LENGTH_SHORT).show();break;
                    case R.id.wordTest:
                        Toast.makeText(MainActivity.this, "My wordTest",Toast.LENGTH_SHORT).show();break;
                    default:
                        return true;
                }

                drawer.closeDrawer(GravityCompat.START);
                return true;

            }
        });

        rightNavigationView = findViewById(R.id.nav_view_right);
        rightNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {

                    case R.id.wordSort:
                        Toast.makeText(MainActivity.this, "wordSort", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.wordTest:
                        Toast.makeText(MainActivity.this, "My wordTest", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        return true;
                }

                drawer.closeDrawer(GravityCompat.END);
                return true;
            }
        });


        //cardView setting
        models = new ArrayList<>();
        models.add(new Model(R.drawable.ic_launcher_background,"1111","22222222222"));
        models.add(new Model(R.drawable.ic_launcher_background,"2222","33333333333"));
        models.add(new Model(R.drawable.ic_launcher_background,"4444","555555555555"));

        adapter = new CardViewAdapter(models,this);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130,0,130,0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_openRight) {
            drawer.openDrawer(GravityCompat.END);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed(){//뒤로가기 클릭시 hamburger 열려있으면 닫히게 하기
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (drawer.isDrawerOpen(GravityCompat.END)) {  /*Closes the Appropriate Drawer*/
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//toolbar에 버튼 추가
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    protected void onDestroy() {
// TODO Auto-generated method stub
        super.onDestroy();
        SharedPrefManager session = SharedPrefManager.getInstance(getApplicationContext());
        session.destorySession();
    }
}
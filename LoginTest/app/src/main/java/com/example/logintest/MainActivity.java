package com.example.logintest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.frag_nav,new MainFragment());
        fragmentTransaction.commit();

        Toolbar toolbar = findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.Open, R.string.Close);
        drawer.addDrawerListener(toggle);//hamburger icon create
        toggle.syncState();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        leftNavigationView = (NavigationView)findViewById(R.id.activity_main_nav_view_left);
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
                    case R.id.dragonList:
                        final FragmentTransaction dragonListFt = fragmentManager.beginTransaction();
                        dragonListFt.replace(R.id.frag_nav,new DragonListFragment());
                        dragonListFt.commit(); break;
                    case R.id.itemList:
                        final FragmentTransaction itemListFt = fragmentManager.beginTransaction();
                        itemListFt.replace(R.id.frag_nav,new ItemListFragment());
                        itemListFt.commit(); break;
                    case R.id.shop:
                        final FragmentTransaction shopFt = fragmentManager.beginTransaction();
                        shopFt.replace(R.id.frag_nav,new ShopFragment());
                        shopFt.commit(); break;
                    default:
                        return true;
                }

                drawer.closeDrawer(GravityCompat.START);
                return true;

            }
        });


        rightNavigationView = findViewById(R.id.activity_main_nav_view_right);
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

        Button setting = findViewById(R.id.nav_left_setting_bt);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SettingActivity.class));
            }
        });

        //navigation header
        View headerView = leftNavigationView.getHeaderView(0);
        TextView userId_nav = (TextView)headerView.findViewById(R.id.nav_left_userId_tv);
        TextView email_nav = (TextView)headerView.findViewById(R.id.nav_left_email_tv);
        userId_nav.setText(SharedPrefManager.getInstance(getApplicationContext()).getUser().getUserId());
        email_nav.setText(SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmail());


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
        getMenuInflater().inflate(R.menu.app_bar_ic_notification, menu);
        return true;
    }
    @Override
    protected void onDestroy() {//세션 데이터 삭제
        super.onDestroy();
        SharedPrefManager session = SharedPrefManager.getInstance(getApplicationContext());
        session.destorySession();
    }
}
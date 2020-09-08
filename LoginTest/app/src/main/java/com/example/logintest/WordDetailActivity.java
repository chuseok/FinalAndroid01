package com.example.logintest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.CollapsingToolbarLayout;

public class WordDetailActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worddetail);

        TextView tx = findViewById(R.id.activity_word_detail_tv_title);
        Intent intent = getIntent();

        final String title = intent.getExtras().getString("param");
        tx.setText(title);

        final Toolbar toolbar = findViewById(R.id.activity_word_detail_toolbar);
        CollapsingToolbarLayout c = findViewById(R.id.activity_word_detail_collapsing_bar);
        //AppBarLayout appbar = (AppBarLayout)findViewById(R.id.app_bar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(true);
        c.setExpandedTitleColor(Color.TRANSPARENT);//헤더 확장될때 타이틀 투명하게 처리
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//헤더 back button
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Button shareBtn = findViewById(R.id.activity_word_detail_bt_share);
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"share",Toast.LENGTH_SHORT).show();
            }
        });


    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @SuppressLint("RestrictedApi")//버그인듯..
    @Override
    public boolean onCreateOptionsMenu(Menu menu){//헤더 우측의 overflow menu 생성
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_bar_overflow,menu);

        if(menu instanceof MenuBuilder){

            MenuBuilder menuBuilder = (MenuBuilder) menu;
            menuBuilder.setOptionalIconsVisible(true);
        }

        return true;
    }
}


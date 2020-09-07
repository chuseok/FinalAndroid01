package com.example.logintest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;
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
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        c.setExpandedTitleColor(Color.TRANSPARENT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}


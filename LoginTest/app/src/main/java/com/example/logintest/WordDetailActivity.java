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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logintest.adapter.WordDetailAdapter;
import com.example.logintest.domain.Word;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

public class WordDetailActivity extends AppCompatActivity{

    private List<Word> wordList;
    private WordDetailAdapter wAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worddetail);

        //recyclerView Layout
        RecyclerView wRecyclerView = (RecyclerView) findViewById(R.id.content_word_rv);
        LinearLayoutManager wLinearLayoutManager = new LinearLayoutManager(this);
        wRecyclerView.setLayoutManager(wLinearLayoutManager);


        /*wordList = new ArrayList<>();
        Word word = new Word("red","빨강");
        wordList.add(word);
        word = new Word("yellow","노랑");
        wordList.add(word);*/

        wAdapter = new WordDetailAdapter(wordList);
        wRecyclerView.setAdapter(wAdapter);



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


        String bookId;
        String bookTitle = title;
        /*
        String url = URLs.URL_STUDY_GETWORDLIST+"?bookId="+ bookId +"&bookTitle="+bookTitle;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);





                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //success
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        */

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


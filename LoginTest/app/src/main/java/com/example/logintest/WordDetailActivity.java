package com.example.logintest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.example.logintest.Utils.DividerItemDecoration;
import com.example.logintest.Utils.MobileSize;
import com.example.logintest.adapter.WordDetailAdapter;
import com.example.logintest.domain.Word;
import com.example.logintest.volley.URLs;
import com.example.logintest.volley.VolleySingleton;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class WordDetailActivity extends AppCompatActivity{

    private List<Word> wordList;
    private WordDetailAdapter wAdapter;
    int bookNum;

    private FloatingActionButton testActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worddetail);

        Intent intent = getIntent();

        final String bookTitle = intent.getExtras().getString("bookTitle");
        final String bookId = intent.getExtras().getString("userId");

        //recyclerView Layout
        RecyclerView wRecyclerView = (RecyclerView) findViewById(R.id.content_word_rv);
        LinearLayoutManager wLinearLayoutManager = new LinearLayoutManager(this);
        wRecyclerView.setLayoutManager(wLinearLayoutManager);
        wRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),R.drawable.line_divider));


        MobileSize mobileSize = new MobileSize();
        mobileSize.getStandardSize(this);
        final float displayXHeight = mobileSize.getStandardSize_X();
        final float displayYHeight = mobileSize.getStandardSize_Y();

        mobileSize.setLayoutMargin(wRecyclerView, (int) (displayXHeight/25),0 , (int) (displayXHeight/25), 0);
        //mobileSize.setLayoutPadding();

        /*wordList = new ArrayList<>();
        Word word = new Word("red","빨강");
        wordList.add(word);
        word = new Word("yellow","노랑");
        wordList.add(word);*/

        final TextView activity_word_detail_tv_title = findViewById(R.id.activity_word_detail_tv_title);
        final TextView activity_word_detail_tv_subtitle = findViewById(R.id.activity_word_detail_tv_subtitle);
        testActionButton = findViewById(R.id.header_fab);

        testActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent WordCardActivity = new Intent(getApplicationContext(), WordCardActivity.class);
                WordCardActivity.putExtra("userId", bookId);
                WordCardActivity.putExtra("bookTitle", bookTitle);
                startActivity(WordCardActivity);
                finish();
            }
        });



        final Toolbar toolbar = findViewById(R.id.activity_word_detail_toolbar);
        CollapsingToolbarLayout c = findViewById(R.id.activity_word_detail_collapsing_bar);
        //AppBarLayout appbar = (AppBarLayout)findViewById(R.id.app_bar);
        toolbar.setTitle(bookTitle);
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



        wordList = new ArrayList<>();

        String url = URLs.URL_STUDY_WORDBOOKS_CONTENTS+"?bookId="+ bookId +"&bookTitle="+bookTitle;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("result", "[" + response + "]");
                        try {
                            JSONArray array = new JSONArray(response);

                            for(int i =0; i<array.length(); i++){

                                Word words;
                                String word = array.getJSONObject(i).getString("word");
                                String meaning = array.getJSONObject(i).getString("meaning");
                                words = new Word(word, meaning);
                                wordList.add(words);
                                wAdapter.notifyDataSetChanged();
                            }
                            bookNum = array.length();
                            Log.d("bookNum", "[" + bookNum + "]");

                            final String tv_subtitle = bookId+" | "+bookNum;

                            activity_word_detail_tv_title.setText(bookTitle);
                            activity_word_detail_tv_subtitle.setText(tv_subtitle);

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
        })  {

            @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String character = null;
                try {
                    character = new String(response.data, "UTF-8");
                    return Response.success(character, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (Exception e) {
                    // log error
                    return Response.error(new ParseError(e));
                }
            }
        };

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);



        wAdapter = new WordDetailAdapter(wordList);
        wRecyclerView.setAdapter(wAdapter);

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


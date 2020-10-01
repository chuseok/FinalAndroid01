package com.example.logintest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.logintest.domain.User;
import com.example.logintest.domain.Word;
import com.example.logintest.manager.SharedPrefManager;
import com.example.logintest.volley.URLs;
import com.example.logintest.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordCardActivity extends AppCompatActivity {

    private TextView wordCountTextView;
    private Toolbar toolbar;
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;

    ArrayList<Word> words = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_card);

        wordCountTextView = findViewById(R.id.activity_word_count_textview);
        toolbar = (Toolbar) findViewById(R.id.activity_word_card_toolbar);
        setToolber();



 /*       Word word1 = new Word("dog", "개");
        Word word2 = new Word("cat", "고양이");
        words.add(word1);
        words.add(word2);*/

        /*Volley 세팅*/
        String userId = SharedPrefManager.getInstance(getApplicationContext()).getUser().getUserId();
        //String bookTitle = "숫자";//받아와야됨 - 언니꺼!!!!!!!!
        String bookTitle = "1";
        String url = URLs.URL_STUDY_GET_LEARNEDWORDBOOK + "?userId=" + userId + "&bookTitle=" + bookTitle;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {

                                Word word = new Word(array.getJSONObject(i).getString("word"), array.getJSONObject(i).getString("meaning"));
                                words.add(word);

                            }
                            pagerAdapter.notifyDataSetChanged();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        viewPager = findViewById(R.id.activity_word_card_pager);
        pagerAdapter = new WordCardPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                wordCountTextView.setText((position + 1) + "/" + words.size());
            }
        });
    }

    private class WordCardPagerAdapter extends FragmentStateAdapter {
        public WordCardPagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {

            return WordCardFragment.newInstance(words, position);
        }

        @Override
        public int getItemCount() {
            return words.size();
        }


    }


    private void setToolber() {
        toolbar.setTitle(R.string.word_card_title);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
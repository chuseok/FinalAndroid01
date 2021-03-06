package com.example.logintest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.example.logintest.domain.Model;
import com.example.logintest.domain.Word;
import com.example.logintest.manager.SharedPrefManager;
import com.example.logintest.volley.URLs;
import com.example.logintest.volley.VolleySingleton;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class WordCardActivity extends AppCompatActivity implements WordCardFragment.OnAnswerSelectedListner {
    private static final String TAG = "WORD_CARD_ACTIVITY";
    private static final String WORD_CARD_FRAGMENT_TAG = "1";
    private static final String STUDY_FINISH_FRAGMENT_TAG = "1";

    private TextView wordCountTextView;
    private TextView unknownTotalTextView, knowTotalTextView;
    private Toolbar toolbar;
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;
    Fragment wordCardFragment;
    FragmentTransaction transaction;
    ArrayList<Word> words = new ArrayList<>();
    String userId;
    String bookTitle;
    int knowTotal, unknownTotal = 0;

    Model model;

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        if (fragment instanceof WordCardFragment) {
            WordCardFragment wordCardFragment = (WordCardFragment) fragment;

            wordCardFragment.setOnAnswerSelectedListner(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_card);

        wordCountTextView = findViewById(R.id.activity_word_count_textview);
        unknownTotalTextView = findViewById(R.id.fragment_unknown_total_textview);
        knowTotalTextView = findViewById(R.id.fragment_know_total_textview);
        toolbar = (Toolbar) findViewById(R.id.activity_word_card_toolbar);
        setToolber();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras == null) {
            Log.d(TAG, "EXTRAS NULL");
        } else if (extras != null) {
            userId = intent.getExtras().getString("userId");
            bookTitle = intent.getExtras().getString("bookTitle");
            Log.d(TAG, "userId : " + userId);
            Log.d(TAG, "bookTitle : " + bookTitle);
            model = new Model(userId, bookTitle);
        }

        /*Volley 세팅*/

        String userId = SharedPrefManager.getInstance(getApplicationContext()).getUser().getUserId();
        String url = URLs.URL_STUDY_WORDBOOKS_CONTENTS_RANDOM + "?userId=" + userId + "&bookTitle=" + bookTitle;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {

                                Word word = new Word(
                                        array.getJSONObject(i).getString("word"),
                                        array.getJSONObject(i).getString("meaning"),
                                        array.getJSONObject(i).getString("learningRate"));
                                words.add(word);

                            }
                            pagerAdapter.notifyDataSetChanged();
                            unknownTotal = words.size();
                            knowTotalTextView.setText(knowTotal + "");
                            unknownTotalTextView.setText(unknownTotal + "");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "wordCardActivity", Toast.LENGTH_SHORT).show();
                    }
                }) {

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

        transaction = getSupportFragmentManager().beginTransaction();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                Intent WordDetailActivity = new Intent(this, WordDetailActivity.class);
                WordDetailActivity.putExtra("userId", userId);
                WordDetailActivity.putExtra("bookTitle", bookTitle);
                startActivity(WordDetailActivity);
                finish();
                overridePendingTransition(R.anim.right_to_left, R.anim.none);

                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAnswerSelected(int position, int wordTotal) {
        if (position + 1 == wordTotal) {
            Log.d(TAG, "End : ");

            viewPager.removeAllViews();
            pagerAdapter.notifyDataSetChanged();//???
            Fragment studyFinishFragment = StudyFinishFragment.newInstance(model);

            if (!studyFinishFragment.isAdded()) {
                transaction.add(R.id.activity_word_card_container, studyFinishFragment);
            }


//            transaction.remove(wordCardFragment);
            transaction.commit();

        }
        viewPager.setCurrentItem(position + 1);
    }

    @Override
    public void onAnswerCorrect(int position) {

        Log.d(TAG, "position: " + position);

        unknownTotal--;
        knowTotal++;

        Log.d(TAG, "knowTotal : " + knowTotal);
        knowTotalTextView.setText(knowTotal + "");
        unknownTotalTextView.setText(unknownTotal + "");

    }

    private class WordCardPagerAdapter extends FragmentStateAdapter {
        public WordCardPagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            wordCardFragment = WordCardFragment.newInstance(words, model, position);

            return wordCardFragment;
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
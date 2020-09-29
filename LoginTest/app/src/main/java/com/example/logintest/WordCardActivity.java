package com.example.logintest;

import androidx.annotation.NonNull;
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

import android.os.Bundle;
import android.os.Parcel;
import android.widget.TextView;

import com.example.logintest.domain.Word;

import java.util.ArrayList;
import java.util.List;

public class WordCardActivity extends AppCompatActivity {

    TextView wordCountTextView;

    private static final int NUM_PAGES = 5;
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;

    ArrayList<Word> words = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_card);

        Word word1 = new Word("dog", "개");
        Word word2 = new Word("cat", "고양이");
        words.add(word1);
        words.add(word2);

        viewPager = findViewById(R.id.activity_word_card_pager);
        pagerAdapter = new WordCardPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                wordCountTextView.setText((position+1) + "/" + words.size());
            }
        });

        wordCountTextView = findViewById(R.id.activity_word_count_textview);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.activity_word_card_toolbar);
        mToolbar.setTitle(R.string.word_card_title);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

}
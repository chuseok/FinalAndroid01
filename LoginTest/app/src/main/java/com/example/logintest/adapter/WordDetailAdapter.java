package com.example.logintest.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logintest.R;
import com.example.logintest.domain.Word;

import java.util.List;

public class WordDetailAdapter extends RecyclerView.Adapter<WordDetailAdapter.CustomViewHolder> {

    private final List<Word> wordList;

    public WordDetailAdapter(List<Word> wordList) {
        this.wordList = wordList;
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView content_word_tv_word;
        protected TextView content_word_tv_meaning;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.content_word_tv_word = (TextView) itemView.findViewById(R.id.content_word_tv_word);
            this.content_word_tv_meaning = (TextView) itemView.findViewById(R.id.content_word_tv_meaning);
        }
    }


    @NonNull
    @Override
    public WordDetailAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_word_item_list,parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull WordDetailAdapter.CustomViewHolder holder, int position) {

        //holder.content_word_tv_word.setGravity(Gravity.CENTER);
        //holder.content_word_tv_meaning.setGravity(Gravity.CENTER);

        holder.content_word_tv_word.setText(wordList.get(position).getWord());
        holder.content_word_tv_meaning.setText(wordList.get(position).getMeaning());
    }

    @Override
    public int getItemCount() { return (null!=wordList?wordList.size():0);
    }


}

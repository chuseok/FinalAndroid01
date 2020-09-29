package com.example.logintest.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.logintest.MainActivity;
import com.example.logintest.R;
import com.example.logintest.Utils.MobileSize;
import com.example.logintest.domain.Collection;
import com.example.logintest.domain.Model;
import com.example.logintest.domain.Word;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYouListener;

import java.util.List;

public class WordTestAdapter extends RecyclerView.Adapter<WordTestAdapter.ItemViewHolder> {
    private List<Word> list;
    private Context context;
    private MainActivity activity;


    public class ItemViewHolder extends RecyclerView.ViewHolder{
        protected LinearLayout main;
        protected TextView title, subTitle;

        public ItemViewHolder(View view){
            super(view);
            main = view.findViewById(R.id.recycler_word_test_main_ll);
            title = view.findViewById(R.id.recycler_word_test_title_tv);
            subTitle = view.findViewById(R.id.recycler_word_test_subTitle_tv);

            MobileSize mobileSize = new MobileSize();
            mobileSize.getStandardSize(activity);
            float displayXHeight = mobileSize.getStandardSize_X();
            float displayYHeight = mobileSize.getStandardSize_Y();
            mobileSize.setLayoutHeight(main,(int)(displayYHeight*0.18));
            mobileSize.setLayoutMargin(main,40,30,30,20);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION){
                        Toast.makeText(context,pos+" 위치가 클릭",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public WordTestAdapter(Context context, List<Word> list, MainActivity activity){
        super();
        this.context = context;
        this.list = list;
        this.activity = activity;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_word_test,parent,false);
        ItemViewHolder viewHolder = new ItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.title.setText(list.get(position).getWord());
        holder.subTitle.setText(list.get(position).getMeaning());
    }

    @Override
    public int getItemCount() {
        return (null!=list?list.size():0);
    }


}

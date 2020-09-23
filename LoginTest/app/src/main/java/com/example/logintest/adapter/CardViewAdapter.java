package com.example.logintest.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.logintest.R;
import com.example.logintest.WordDetailActivity;
import com.example.logintest.domain.Model;

import java.util.List;

public class CardViewAdapter extends PagerAdapter {
    private static final String TAG = "CARDVIEW_ADAPTER";

    private List<Model> models;
    private LayoutInflater layoutInflater;
    private Context context;

    public CardViewAdapter(List<Model> models, Context context){
        this.models = models;
        this.context = context;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cardview_item_main, container, false);

        ImageView imageView;
        TextView title, desc;
        ProgressBar learningRate;

        View card = view.findViewById(R.id.card_target);
        title = view.findViewById(R.id.ac_dragonDetail_invenImage_iv);
        desc = view.findViewById(R.id.ac_dragonDetail_invenCnt_tv);
        learningRate = view.findViewById(R.id.ac_dragonDetail_progress);

        title.setText(models.get(position).getTitle());
        desc.setText(models.get(position).getDesc()+"%");
        Log.d(TAG, "models.get(position).getDesc() : " + models.get(position).getDesc());
        learningRate.setProgress(models.get(position).getDesc());


        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //Toast.makeText(v.getContext(),"click1",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, WordDetailActivity.class);
                    intent.putExtra("title", models.get(position).getTitle());
                    intent.putExtra("id", models.get(position).getId());
                    context.startActivity(intent);
            }

        });

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}

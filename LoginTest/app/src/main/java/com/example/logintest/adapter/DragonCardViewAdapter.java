package com.example.logintest.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.logintest.DragonDetailActivity;
import com.example.logintest.DragonListFragment;
import com.example.logintest.R;
import com.example.logintest.domain.Dragon;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYouListener;

import java.io.Serializable;
import java.util.List;

public class DragonCardViewAdapter extends PagerAdapter{
    private List<Dragon> dragonList;
    private LayoutInflater layoutInflater;
    private Context context;
    ImageView imageView;
    ProgressBar progressBar;
    private DragonListFragment dragonListFragment;

    public DragonCardViewAdapter(List<Dragon> dragonList, Context context){
        this.dragonList = dragonList;
        this.context = context;
    }

    public void updateResults(List<Dragon> dragonList){
        this.dragonList = dragonList;
        notifyDataSetChanged();

    }


    @Override
    public int getCount() {
        return dragonList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cardview_item_dragon, container, false);



        View card = view.findViewById(R.id.card_target);
        imageView = view.findViewById(R.id.frag_dragonList_dragonImage_iv);
        progressBar = view.findViewById(R.id.frag_dragonList_hungry_pb);

        if(dragonList.get(position).getImagePath().contains(".svg")){
            GlideToVectorYou
                    .init()
                    .with(context)
                    .withListener(new GlideToVectorYouListener() {
                        @Override
                        public void onLoadFailed() {
                            System.out.println("Image Failed");
                        }

                        @Override
                        public void onResourceReady() {
                            System.out.println("Image Ready");
                        }
                    })
                    .load(Uri.parse(dragonList.get(position).getImagePath()), imageView);
        }else if(dragonList.get(position).getImagePath().contains(".png")){
            Glide.with(context).load(dragonList.get(position).getImagePath()).into(imageView);
        }



        progressBar.setProgress(dragonList.get(position).getProgress());


        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(context, DragonDetailActivity.class);
                    intent.putExtra("dragon", (Serializable)dragonList.get(position));
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
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;

    }

}


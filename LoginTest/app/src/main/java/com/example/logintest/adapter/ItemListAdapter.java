package com.example.logintest.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.logintest.MainActivity;
import com.example.logintest.R;
import com.example.logintest.Utils.MobileSize;
import com.example.logintest.domain.Collection;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYouListener;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {
    private List<Collection> list;
    private Context context;
    private MainActivity activity;


    public class ItemViewHolder extends RecyclerView.ViewHolder{
        protected CardView cardView;
        protected ImageView image;
        protected TextView text;

        public ItemViewHolder(View view){
            super(view);
            this.cardView = view.findViewById(R.id.recycler_item_list_cardView);
            this.image = view.findViewById(R.id.recycler_item_list_image_iv);
            this.text = view.findViewById(R.id.recycler_item_list_name_tv);
            MobileSize mobileSize = new MobileSize();
            mobileSize.getStandardSize(activity);
            float displayXHeight = mobileSize.getStandardSize_X();
            float displayYHeight = mobileSize.getStandardSize_Y();
            mobileSize.setLayoutWidth(this.image,(int)(displayXHeight*0.2));
            mobileSize.setLayoutHeight(this.image,(int)(displayXHeight*0.2));
        }
    }

    public ItemListAdapter(Context context, List<Collection> list, MainActivity activity){
        super();
        this.context = context;
        this.list = list;
        this.activity = activity;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item_list,parent,false);
        ItemViewHolder viewHolder = new ItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        if(list.get(position).getImage().contains(".svg")){
            GlideToVectorYou
                    .init()
                    .with(context)
                    .withListener(new GlideToVectorYouListener() {
                        @Override
                        public void onLoadFailed() { System.out.println("failed load image"); }

                        @Override
                        public void onResourceReady() { }
                    })
                    .load(Uri.parse(list.get(position).getImage()), holder.image);
        }else if(list.get(position).getImage().contains(".png")){
            Glide.with(context).load(list.get(position).getImage()).into(holder.image);
        }

        if(!list.get(position).isPossession()){
            holder.cardView.setElevation(0f);
            holder.cardView.setCardBackgroundColor(Color.parseColor("#4D8C8C8C"));
            holder.image.setColorFilter(R.color.imageDim);
        }

        holder.text.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return (null!=list?list.size():0);
    }


}

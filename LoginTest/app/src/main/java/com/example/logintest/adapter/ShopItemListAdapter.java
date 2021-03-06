package com.example.logintest.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.logintest.MainActivity;
import com.example.logintest.R;
import com.example.logintest.ShopDetailActivity;
import com.example.logintest.TabFragment.ShopEggTabFragment;
import com.example.logintest.Utils.MobileSize;
import com.example.logintest.domain.Collection;
import com.example.logintest.domain.ShopItem;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYouListener;

import java.io.Serializable;
import java.util.List;

public class ShopItemListAdapter extends RecyclerView.Adapter<ShopItemListAdapter.ItemViewHolder> {

    private List<ShopItem> shopList;
    private Context context;
    private MainActivity mainActivity;

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        protected LinearLayout linearLayout;
        protected ImageView image;
        protected TextView name;
        protected TextView price;
        protected Button buyButton;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.linearLayout = itemView.findViewById(R.id.recycler_shop_main_ll);
            this.image = itemView.findViewById(R.id.recycler_shop_image_iv);
            this.name = itemView.findViewById(R.id.recycler_shop_name_tv);
            this.price = itemView.findViewById(R.id.recycler_shop_price_tv);
            this.buyButton = itemView.findViewById(R.id.recycler_shop_buy_btn);

            MobileSize mobileSize = new MobileSize();
            mobileSize.getStandardSize(mainActivity);
            float displayXHeight = mobileSize.getStandardSize_X();
            float displayYHeight = mobileSize.getStandardSize_Y();
            mobileSize.setLayoutHeight(this.linearLayout,(int)((displayYHeight-100)*0.2));
            mobileSize.setLayoutHeight(this.image,(int)(displayXHeight*0.2));
            mobileSize.setLayoutWidth(this.image,(int)(displayXHeight*0.2));
        }
    }

    public ShopItemListAdapter(Context context, List<ShopItem> shopList, MainActivity mainActivity) {
        super();
        this.context = context;
        this.shopList = shopList;
        this.mainActivity = mainActivity;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_shop,parent,false);
        ItemViewHolder viewHolder = new ItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {
        if(shopList.get(position).getImage().contains(".svg")){
            GlideToVectorYou
                    .init()
                    .with(context)
                    .withListener(new GlideToVectorYouListener() {
                        @Override
                        public void onLoadFailed() { System.out.println("failed load image"); }

                        @Override
                        public void onResourceReady() { }
                    })
                    .load(Uri.parse(shopList.get(position).getImage()), holder.image);
        }else if(shopList.get(position).getImage().contains(".png")){
            Glide.with(context).load(shopList.get(position).getImage()).into(holder.image);
        }

        holder.name.setText(shopList.get(position).getName());
        holder.price.setText(shopList.get(position).getPrice()+"");
        holder.buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShopDetailActivity.class);
                intent.putExtra("name", (Serializable) shopList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null!=shopList?shopList.size():0);
    }


}

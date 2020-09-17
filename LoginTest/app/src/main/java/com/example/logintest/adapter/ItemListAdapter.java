package com.example.logintest.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logintest.R;
import com.example.logintest.domain.Collection;
import com.example.logintest.domain.Inven;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYouListener;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {
    private List<Collection> list;
    private Context context;

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        protected ImageView image;
        protected TextView text;

        public ItemViewHolder(View view){
            super(view);
            this.image = view.findViewById(R.id.recycler_image_iv);
            this.text = view.findViewById(R.id.recycler_text_tv);
        }
    }

    public ItemListAdapter(Context context, List<Collection> list){
        super();
        this.context = context;
        this.list = list;

    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_row,parent,false);
        ItemViewHolder viewHolder = new ItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
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
        holder.text.setText(list.get(position).getName());
        System.out.println(list.get(position).isPossession());
    }

    @Override
    public int getItemCount() {
        return (null!=list?list.size():0);
    }
}

package com.example.logintest.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.logintest.R;
import com.example.logintest.domain.Inven;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYouListener;

import java.util.List;

public class InventoryCardViewAdapter extends PagerAdapter {
    private List<Inven> invenList;
    private LayoutInflater layoutInflater;
    private Context context;

    public InventoryCardViewAdapter(List<Inven> invenList, Context context){
        this.invenList = invenList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return invenList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cardview_item_inventory, container, false);

        ImageView imageView;
        TextView title, invenCnt;

        View card = view.findViewById(R.id.card_target);
        imageView = view.findViewById(R.id.ac_dragonDetail_invenImage_iv);
        invenCnt = view.findViewById(R.id.ac_dragonDetail_invenCnt_tv);

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
                .load(Uri.parse(invenList.get(position).getImagePath()), imageView);
        invenCnt.setText("X"+invenList.get(position).getCount());


        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    /*Intent intent = new Intent(context, MainActivity.class);
                    //intent.putExtra("param", models.get(position).getTitle());
                    context.startActivity(intent);*/

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
    public float getPageWidth(final int position) {
        // this will have 3 pages in a single view
        return 0.32f;
    }
}

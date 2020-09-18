package com.example.logintest.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.logintest.R;
import com.example.logintest.domain.Inven;
import com.example.logintest.manager.SharedPrefManager;
import com.example.logintest.volley.URLs;
import com.example.logintest.volley.VolleySingleton;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYouListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryCardViewAdapter extends PagerAdapter {
    private List<Inven> invenList;
    private LayoutInflater layoutInflater;
    private Context context;
    private InventoryAdapterCallBack listener;

    public void setOnShareClickedListener(InventoryAdapterCallBack listener) {
        this.listener = listener;
    }

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
        //View detailView = layoutInflater.inflate(R.layout.activity_dragon_detail, container, false);

        final ImageView imageView;
        final TextView invenCnt;

        View card = view.findViewById(R.id.card_target);
        imageView = view.findViewById(R.id.ac_dragonDetail_invenImage_iv);
        invenCnt = view.findViewById(R.id.ac_dragonDetail_invenCnt_tv);

        if(invenList.get(position).getImagePath().contains(".svg")){
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
        }else if(invenList.get(position).getImagePath().contains(".png")){
            Glide.with(context).load(invenList.get(position).getImagePath()).into(imageView);
        }
        invenCnt.setText("X"+invenList.get(position).getCount());

        final int dragonId = invenList.get(position).getDragonId();
        final String userId = SharedPrefManager.getInstance(context).getUser().getUserId();

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest useStringRequest = new StringRequest(Request.Method.POST, URLs.URL_INVEN_USE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                invenList.clear();
                                try {
                                    JSONArray array = new JSONArray(response);
                                    for (int i = 0; i < array.length()-1; i++) {
                                        int productId = Integer.parseInt(array.getJSONObject(i).getString("productId"));
                                        String imagePath = array.getJSONObject(i).getString("productImage").replace("../",URLs.ROOT_URL);
                                        int count = Integer.parseInt(array.getJSONObject(i).getString("count"));


                                        invenList.add(new Inven(productId,imagePath,count, dragonId));
                                        //System.out.println("위치 : "+position+"->"+invenList.get(position).getCount());
                                        notifyDataSetChanged();
                                    }
                                    JSONObject object = array.getJSONObject(array.length()-1);
                                    int levelValue = Integer.parseInt(object.getString("dragonLevelValue"));
                                    int dragonLevel = Integer.parseInt(object.getString("dragonTotalLevel"));
                                    int hungryValue = Integer.parseInt(object.getString("hungryValue"));
                                    listener.setLevelValue(levelValue);
                                    listener.setFoodValue(hungryValue);
                                    listener.setLevelText(dragonLevel);
                                    listener.setDotIndicator(invenList.size());
                                    listener.setDragonImage(object.getString("dragonImage"));
                                    System.out.println(object.getString("dragonImage"));


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("userId", userId);
                        params.put("productId", invenList.get(position).getProductId()+"");
                        params.put("dragonId", dragonId+"");
                        return params;
                    }
                };

                VolleySingleton.getInstance(context).addToRequestQueue(useStringRequest);
                if(invenList.get(position).getImagePath().contains(".svg")){
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
                }else if(invenList.get(position).getImagePath().contains(".png")){
                    Glide.with(context).load(invenList.get(position).getImagePath()).into(imageView);
                }

                invenCnt.setText("X"+invenList.get(position).getCount());

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
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;

    }


}

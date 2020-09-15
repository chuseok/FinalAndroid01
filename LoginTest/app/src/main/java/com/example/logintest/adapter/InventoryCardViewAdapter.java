package com.example.logintest.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dinuscxj.progressbar.CircleProgressBar;
import com.example.logintest.R;
import com.example.logintest.domain.Inven;
import com.example.logintest.manager.SharedPrefManager;
import com.example.logintest.volley.URLs;
import com.example.logintest.volley.VolleySingleton;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYouListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        final ImageView imageView;
        final TextView levelText, invenCnt;
        final CircleProgressBar circleProgressBar;
        final ProgressBar hungryProgress;

        View card = view.findViewById(R.id.card_target);
        imageView = view.findViewById(R.id.ac_dragonDetail_invenImage_iv);
        levelText = view.findViewById(R.id.ac_dragonDetail_Level_tv);
        invenCnt = view.findViewById(R.id.ac_dragonDetail_invenCnt_tv);
        circleProgressBar = view.findViewById(R.id.cpb_circlebar);
        hungryProgress = view.findViewById(R.id.ac_dragonDetail_hungry_pb);

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

        final int dragonId = invenList.get(position).getDragonId();
        final String userId = SharedPrefManager.getInstance(context).getUser().getUserId();

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(invenList.get(position).getProductId());

                StringRequest useStringRequest = new StringRequest(Request.Method.POST, URLs.URL_INVEN_USE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                invenList.clear();
                                try {
                                    JSONArray array = new JSONArray(response);
                                    for (int i = 0; i < array.length(); i++) {
                                        int productId = Integer.parseInt(array.getJSONObject(i).getString("productId"));
                                        String imagePath = array.getJSONObject(i).getString("productImage").replace("../",URLs.ROOT_URL);
                                        int count = Integer.parseInt(array.getJSONObject(i).getString("count"));


                                        invenList.add(new Inven(productId,imagePath,count, dragonId));
                                        //System.out.println("위치 : "+position+"->"+invenList.get(position).getCount());
                                        notifyDataSetChanged();
                                    }

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
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("userId", userId);
                        params.put("productId", invenList.get(position).getProductId()+"");
                        params.put("dragonId", dragonId+"");
                        return params;
                    }
                };
                String url = URLs.URL_DRAGON_GET+"?userId="+ userId +"&dragonId="+dragonId;
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject object = new JSONObject(response);
                                    int levelValue = Integer.parseInt(object.getString("dragonLevelValue"));
                                    int dragonLevel = Integer.parseInt(object.getString("dragonTotalLevel"));
                                    int hungryValue = Integer.parseInt(object.getString("hungryValue"));
                                    int coin = Integer.parseInt(object.getString("coin"));
                                    circleProgressBar.setProgress(levelValue);
                                    levelText.setText(dragonLevel+"");
                                    hungryProgress.setProgress(hungryValue);
                                    String replaceUrl = object.getString("dragonImage").replace("../",URLs.ROOT_URL);
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
                                            .load(Uri.parse(replaceUrl), imageView);

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
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        //params.put("userId", "111111");
                        //params.put("dragonId", "3");
                        return params;
                    }
                };

                VolleySingleton.getInstance(context).addToRequestQueue(useStringRequest);
                VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
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
